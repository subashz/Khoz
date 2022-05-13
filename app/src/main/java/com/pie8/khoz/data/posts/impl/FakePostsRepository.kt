package com.pie8.khoz.data.posts.impl

import com.pie8.khoz.data.model.RFileDirectory
import com.pie8.khoz.data.model.RFileModel
import com.pie8.khoz.ui.utils.addOrRemove
import com.pie8.khoz.data.posts.PostsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import com.pie8.khoz.data.model.Result
import org.apache.commons.net.ftp.FTPClient
import org.apache.commons.net.ftp.FTPReply

/**
 * Implementation of PostsRepository that returns a hardcoded list of
 * posts with resources after some delay in a background thread.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class FakePostsRepository : PostsRepository {

    // for now, store these in memory
    private val favorites = MutableStateFlow<Set<String>>(setOf())

    // Used to make suspend functions that read and update state safe to call from any thread
    private val mutex = Mutex()

    private val ftp: FTPClient = FTPClient()

    init {
    }

    override suspend fun getPost(postId: String?): Result<RFileModel> {
        return withContext(Dispatchers.IO) {
            Result.Error(Exception())
//            val post = posts.allPosts.find { it.id == postId }
//            if (post == null) {
//                Result.Error(IllegalArgumentException("Post not found"))
//            } else {
//                Result.Success(post)
//            }
        }
    }

    override suspend fun getPostsFeed(): Result<RFileDirectory> {
        return withContext(Dispatchers.IO) {

            try {
                ftp.connect("https://blacpythoz.insomnia247.nl/files/")
                var reply = ftp.replyCode
                if (!FTPReply.isPositiveCompletion(reply)) {
                    ftp.disconnect()
                    Result.Error(java.lang.Exception())

                } else {

                    val data = ftp.listFiles().map {
                        RFileModel(it.user, it.name)
                    }
                    Result.Success(RFileDirectory(data.first(), data))

                }

            }catch (e:Exception) {
                e.printStackTrace()
                Result.Error(e)
            }
        }
    }

    override fun observeFavorites(): Flow<Set<String>> = favorites

    override suspend fun toggleFavorite(postId: String) {
        mutex.withLock {
            val set = favorites.value.toMutableSet()
            set.addOrRemove(postId)
            favorites.value = set.toSet()
        }
    }

    // used to drive "random" failure in a predictable pattern, making the first request always
    // succeed
    private var requestCount = 0

    /**
     * Randomly fail some loads to simulate a real network.
     *
     * This will fail deterministically every 5 requests
     */
    private fun shouldRandomlyFail(): Boolean = ++requestCount % 5 == 0
}
