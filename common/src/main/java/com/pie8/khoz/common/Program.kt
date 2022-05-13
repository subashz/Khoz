package com.pie8.khoz.common

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.net.URI

fun main() {


    setConsoleTitle("OpenDirectoryDownloader")

    val openDirectoryIndexerSettings = OpenDirectoryIndexerSettings("https://blacpythoz.insomnia247.nl/files/")
    setConsoleTitle("${URI(openDirectoryIndexerSettings.url).host.replace("www.", "")} - {ConsoleTitle}");


    val openDirectoryIndexer = OpenDirectoryIndexer(openDirectoryIndexerSettings);


    runBlocking {
        openDirectoryIndexer.startIndexing()

        delay(60000)
    }


}


