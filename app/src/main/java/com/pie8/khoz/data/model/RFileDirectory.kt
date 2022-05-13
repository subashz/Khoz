package com.pie8.khoz.data.model


data class RFileDirectory(
    val highlightedFile: RFileModel,
    val allFiles: List<RFileModel>,
) {
    /**
     * Returns a flattened list of all posts contained in the feed.
     */
    val allPosts: List<RFileModel> = listOf()
}
