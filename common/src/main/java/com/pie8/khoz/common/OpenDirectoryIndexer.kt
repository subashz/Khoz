package com.pie8.khoz.common

import com.pie8.khoz.common.helpers.FileSizeHelpder
import com.pie8.khoz.common.model.CommandLineOptions
import com.pie8.khoz.common.shared.Session
import com.pie8.khoz.common.shared.WebDirectory
import com.pie8.khoz.common.shared.WebFile
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.HttpHeaders.ContentEncoding
import kotlinx.coroutines.*
import java.util.*

class OpenDirectoryIndexer(var openDirectoryIndexerSettings: OpenDirectoryIndexerSettings) {

    companion object {
        var session: Session = Session(WebDirectory())
    }

    val webDirectoriesQueue = LinkedList<WebDirectory>()

    var runningWebDirectoryThreads: Int = 0

    var webDirectoryProcessors: MutableList<Job> = mutableListOf()

    var webDirectoryProcessorInfo: Map<String, WebDirectory> = mapOf()

    var webFilesFileSizeQueue = LinkedList<WebDirectory>()
    var runningWebFileFileSizeThreads = 0
    var webFileFileSizeProcessor: MutableList<Job> = mutableListOf()


    var indexingTask: Job? = null

    var firstRequest: Boolean = true

    //        private HttpClientHandler HttpClientHandler { get; set; }
    var httpClient: HttpClient? = null


    var knownErrorPaths = listOf("cgi-bin/", "lost%2Bfound/")

//        var WebDirectoryProcessorInfoLock:ob = new object();


//    public static Session Session { get; set; }
//
//    public OpenDirectoryIndexerSettings OpenDirectoryIndexerSettings { get; set; }
//
//    public ConcurrentQueue<WebDirectory> WebDirectoriesQueue { get; set; } = new ConcurrentQueue<WebDirectory>();
//    public int RunningWebDirectoryThreads;
//    public Task[] WebDirectoryProcessors;
//    public Dictionary<string, WebDirectory> WebDirectoryProcessorInfo = new Dictionary<string, WebDirectory>();
//    public readonly object WebDirectoryProcessorInfoLock = new object();
//
//    public ConcurrentQueue<WebFile> WebFilesFileSizeQueue { get; set; } = new ConcurrentQueue<WebFile>();
//    public int RunningWebFileFileSizeThreads;
//    public Task[] WebFileFileSizeProcessors;
//
//    public CancellationTokenSource IndexingTaskCTS { get; set; }
//    public Task IndexingTask { get; set; }
//
//    private bool FirstRequest { get; set; } = true;
//
//    private HttpClientHandler HttpClientHandler { get; set; }
//    private HttpClient HttpClient { get; set; }
//    private System.Timers.Timer TimerStatistics { get; set; }
//
//    private static readonly Random Jitterer = new Random();
//
//


    init {

        httpClient = HttpClient(CIO) {

            install(ContentEncoding) {
//                deflate(1.0F)
//                gzip(0.9F)
            }

            engine {
                threadsCount = 4

                endpoint {
                    // this: EndpointConfig
                    maxConnectionsPerRoute = 100
                    pipelineMaxSize = 20
                    keepAliveTime = 5000
                    connectTimeout = 5000
                    connectAttempts = 5
                }

            }

//            install(Logging) {
//                logger = Logger.DEFAULT
//                level = LogLevel.HEADERS
//            }

            install(HttpRequestRetry) {
                retryOnServerErrors(maxRetries = 5)
                exponentialDelay()
            }


            install(HttpCookies)
        }

        webDirectoryProcessors =
            List(openDirectoryIndexerSettings.threads) { Job() }.toMutableList()
        webFileFileSizeProcessor =
            List(openDirectoryIndexerSettings.threads) { Job() }.toMutableList()

    }


//    CookieContainer cookieContainer = new CookieContainer();
//
//    HttpClientHandler = new HttpClientHandler
//    {
//        ServerCertificateCustomValidationCallback = (message, cert, chain, errors) => true,
//        AutomaticDecompression = DecompressionMethods.GZip | DecompressionMethods.Deflate | DecompressionMethods.Brotli,
//        CookieContainer = cookieContainer
//    };


    @OptIn(DelicateCoroutinesApi::class)
    suspend fun startIndexing() {

        print("Starting index")
        val fromFile: Boolean = openDirectoryIndexerSettings.fileName.isNotEmpty()


        if (fromFile) {


        } else {
            session = Session(
                started = Date(),
                root = WebDirectory().apply {
                    name = Constants.Root
                    url = openDirectoryIndexerSettings.url
                },
                maxThreads = openDirectoryIndexerSettings.threads
            )
        }

        session.maxThreads = openDirectoryIndexerSettings.threads


        if (session.root.uri.scheme == Constants.UriScheme.Ftp || session.root.uri.scheme == Constants.UriScheme.Ftps) {

        }
        println("Retrieving FTP(S) software!");


        if (session.root.uri.scheme == Constants.UriScheme.Ftps) {
            if (session.root.uri.port == -1) {
                println("Using default port (990) for FTPS");

                val urlBuilder = URLBuilder(session.root.url).apply { port = 990 }

                session.root.url = urlBuilder.build().toString()
            }

//            val serverInfo = FtpParser.GetFtpServerInfo(session.root, openDirectoryIndexerSettings.userName, openDirectoryIndexerSettings.password);

//            awaitAll(serverInfo)

        }

        indexingTask = GlobalScope.launch {


            try {

                print("Using webdirectory processor")

                if (fromFile) {


                } else {

                    webDirectoriesQueue.add(session.root)


                }

                webDirectoryProcessors.forEachIndexed { index, job ->
                    webDirectoryProcessors[index] =
                        getWebDirectoryProcessor(webDirectoriesQueue, "P $index")
                }



                webDirectoryProcessors.joinAll()


//                webFileFileSizeProcessor.forEachIndexed { index, job ->
//                    webFileFileSizeProcessor[index] =
//                        getWebFileFileSizeProcessor(webFilesFileSizeQueue, "P $index", webDirectoryProcessors)
//                }


//                for (int i = 1; i <= WebFileFileSizeProcessors.Length; i++)
//                {
//                    string processorId = i . ToString ();
//                    WebFileFileSizeProcessors[i - 1] =
//                        WebFileFileSizeProcessor(WebFilesFileSizeQueue, $"P{processorId}", WebDirectoryProcessors, IndexingTaskCTS.Token);
//                }

//                webFileFileSizeProcessor.joinAll()

            } catch (e: Exception) {
                e.printStackTrace()
            }


            indexingTask?.join()

        }


    }


    @OptIn(DelicateCoroutinesApi::class)
    suspend fun getWebFileFileSizeProcessor(
        queue: LinkedList<WebDirectory>,
        name: String,
    ): Job {


        println("Some long running web tsk")

        delay(500)

        return Job()

    }


    @OptIn(DelicateCoroutinesApi::class)
    suspend fun getWebDirectoryProcessor(
        queue: LinkedList<WebDirectory>,
        name: String,
    ): Job {


        return GlobalScope.launch {


            println("WebDirectory processors :)")
            val maxConnections: Boolean = false

            do {


                if (runningWebDirectoryThreads + 1 > session.maxThreads) {
                    println("Stopped thread because it's there are more threads ({RunningWebDirectoryThreads + 1}) running than wanted ({Session.MaxThreads})")
                    break
                }


                val webDirectory = queue.first()


                if (session.processedUrls?.contains(webDirectory.url) == false) {

                    session.processedUrls!!.add(webDirectory.url)
                    webDirectory.startTime = Date();


                    if (session.root.uri.scheme == Constants.UriScheme.Ftp || session.root.uri.scheme == Constants.UriScheme.Ftps) {
//                    val parseWebDirectory = Ftp
                    } else if (session.root.uri.host == Constants.GoogleDriveDomain) {
                        if (webDirectory.uri.path.contains("folderview")) {

                        }
                    } else {
                        if (session.root.uri.host == Constants.BlitzfilesTechDomain || true) {


                            processWebDirectoryAsync(name, webDirectory)

                        } else {
                            session.skipped++
                        }
                    }


                }


            } while ((queue.isNotEmpty() || runningWebDirectoryThreads > 0) && !maxConnections)

//        while (!cancellationToken.IsCancellationRequested && (!queue.IsEmpty || RunningWebDirectoryThreads > 0) && !maxConnections);

        }
    }


    suspend fun processWebDirectoryAsync(name: String, webDirectory: WebDirectory) {


        if (session.parameters.containsKey(Constants.Parameters_GdIndex_RootId)) {

            return
        }

        if (!openDirectoryIndexerSettings.commandLineOptions?.userAgent.isNullOrEmpty()) {
//                httpClient.
        }


        var httpResponseMessage: HttpResponse? = null

        try {
            httpResponseMessage = httpClient!!.request(webDirectory.url) {

            }
        } catch (e: Exception) {

        }


        if (httpResponseMessage?.status == HttpStatusCode.Forbidden && httpResponseMessage.headers["server"]?.lowercase() == "cloudflare") {


//            val cloudFlareHtml = getHtml(httpResponseMessage)

            println("Data is $httpResponseMessage")

        }

        if (httpResponseMessage?.status == HttpStatusCode.ServiceUnavailable && httpResponseMessage.headers["server"]?.lowercase() == "cloudflare") {


            // Cloudflare protection detected..
        }

        if (httpResponseMessage?.status == HttpStatusCode.Forbidden) {

        }


        if (httpResponseMessage?.status == HttpStatusCode.MovedPermanently) {

        }

        if ((httpResponseMessage?.contentLength() ?: 0) > 20 * Constants.Megabyte) {

            convertDirectoryToFile(webDirectory, httpResponseMessage)
        }

        var html: String? = null;

        if (httpResponseMessage?.status?.isSuccess() == true) {
            setRootUrl(httpResponseMessage)

            html = httpResponseMessage.bodyAsText()


            convertDirectoryToFile(webDirectory, httpResponseMessage)

        }


        if (firstRequest && (httpResponseMessage == null || !httpResponseMessage.status.isSuccess() || httpResponseMessage.status.isSuccess())) {

        }


        if (httpResponseMessage == null) {
            throw Exception("Error retriving directory listing for ${webDirectory.url}")
        }


        val calibreDetected = false
        val calibreVersionString = ""

        if (httpResponseMessage.status.isSuccess()) {

        }

        if (calibreDetected) {

        }

        if (httpResponseMessage.status.isSuccess() && webDirectory.url != httpResponseMessage.request.url.toString()) {

            // Soft rate limit
        }

        println(
            "$name Finish download [HTTP ${httpResponseMessage.status} ${webDirectory.url} and size: ${
                FileSizeHelpder.toHumanReadable(
                    html?.length?.toLong() ?: 0L
                )
            }"
        )


        // Process only same site

        if (httpResponseMessage.request.url.toURI().host == session.root.uri.host) {

            println("Processing...")

            val httpStatusCode = httpResponseMessage.status.value
            if (!session.httpStatusCode.containsKey(httpStatusCode)) {
                session.httpStatusCode[httpStatusCode] = 0
            }

            session.httpStatusCode[httpStatusCode] =
                session.httpStatusCode[httpStatusCode]!!.toInt() + 1


            if (httpResponseMessage.status.isSuccess()) {
                if (html == null) {
                    html = getHtml(httpResponseMessage)
                }

                if (html.length > Constants.Megabyte) {
                    println("Large response of ${FileSizeHelpder.toHumanReadable(html.length.toLong())} for ${webDirectory.url}")
                }

                session.totalHttpTraffice += html.length

                val parsedWebDirectory = DirectoryParser.parseHtml(webDirectory, html, httpClient)
                val processSubDirectories = parsedWebDirectory?.parser != "DirectoryListingModel01"
//                addProcessedWebDirectory(webDirectory, parsedWebDirectory, processSubDirectories)
            }


        } else {
            println("$name Skipped result of ${webDirectory.url} which points to ${httpResponseMessage.request.url}")
            session.skipped++
        }


//        val parsedWebDirectory = DirectoryParser.parseHtml(webDirectory, html, httpClient)
//        val processSubDirectories = parsedWebDirectory.parse == ""
//        addProcessedWebDirectory(webDirectory, parsedWebDirectory, processSubDirectories)

    }

    private fun addProcessedWebDirectory(
        webDirectory: WebDirectory,
        parsedWebDirectory: WebDirectory,
        processSubDirectories: Boolean
    ) {


    }


    private fun setRootUrl(httpResponseMessage: HttpResponse) {
        if (firstRequest) {
            if (session.root.url != httpResponseMessage.request.url.toString()) {

                if (session.root.uri.host != httpResponseMessage.request.url.toURI().host) {
                    print("Response is not from request host but from ..")
                }
                session.root.url = httpResponseMessage.request.url.toString()
                print("Retrived URL : ${session.root.url}")

            }
        }
    }

    private fun convertDirectoryToFile(
        webDirectory: WebDirectory,
        httpResponseMessage: HttpResponse?
    ) {

        webDirectory.parentDirectory?.subdirectories?.remove(webDirectory)

        webDirectory.parentDirectory?.files?.add(
            WebFile(
                url = webDirectory.url,
                fileName = webDirectory.name,
                fileSize = httpResponseMessage?.contentLength() ?: Constants.NoFileSize,
                ""
            )
        )

        webDirectory

        print("Convert file $webDirectory")

    }


    suspend fun getHtml(httpResponseMessage: HttpResponse): String {

        return httpResponseMessage.bodyAsText()
    }

}


data class OpenDirectoryIndexerSettings(
    val url: String,
    val fileName: String = "",
    val threads: Int = 5,
    val timeOut: Int = 100,
    val userName: String = "",
    val password: String = "",
    val determineFileSizeByDownload: Boolean = false,
    val commandLineOptions: CommandLineOptions? = null
)