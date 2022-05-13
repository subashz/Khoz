package com.pie8.khoz.common

import com.pie8.khoz.common.model.DirectoryListingModel01
import com.pie8.khoz.common.shared.WebDirectory
import io.ktor.client.*
import io.ktor.http.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.lang.Exception
import java.net.URI

object DirectoryParser {


    suspend fun parseHtml(
        webDirectory: WebDirectory,
        html: String,
        httpClient: HttpClient?,
        checkParents: Boolean = true
    ): WebDirectory? {

        var baseUrl = webDirectory.url

        if (!baseUrl.endsWith("/") && webDirectory.uri.query.isNullOrEmpty() && baseUrl.getUrlExtension()
                .isNullOrEmpty()
        ) {
            baseUrl += "/"
        }

        val parsedWebDirectory =
            webDirectory.parentDirectory?.let { WebDirectory(it) } ?: WebDirectory()
        parsedWebDirectory.url = baseUrl
        parsedWebDirectory.name = Constants.Root


        try {

            val htmlDocument = Jsoup.parse(html)

            if (webDirectory.uri.host == "ipfs.io" || webDirectory.uri.host == "gateway.ipfs.io") {
                return parseIpfsDirectoryListing(
                    baseUrl,
                    parsedWebDirectory,
                    htmlDocument,
                    checkParents
                )
            }

            if (webDirectory.uri.host == Constants.BlitzfilesTechDomain) {
//
            }

            if (httpClient != null) {

            }

            htmlDocument.select("#sidebar").forEach { it.remove() }
            htmlDocument.select("nav").forEach { it.remove() }


            // The order of the checks is very important.
            val directoryListingDotComListItems =
                htmlDocument.select("#directory-listing li, .directory-listing li")

            if (directoryListingDotComListItems.any()) {
                return parseDirectoryListingDoctComDirectoryListing(
                    baseUrl,
                    parsedWebDirectory,
                    directoryListingDotComListItems,
                    checkParents
                )
            }


            val h5aiTableRows = htmlDocument.select("#fallback table tr")

            if (h5aiTableRows.any()) {
                return parseH5aiDirectoryListing(
                    baseUrl,
                    parsedWebDirectory,
                    h5aiTableRows,
                    checkParents
                )
            }

            // sniff directory listing..
            val snifTableRows = htmlDocument.select("table.snif tr")

            if (snifTableRows.any()) {
                return parseSnifDirectoryListing(
                    baseUrl,
                    parsedWebDirectory,
                    snifTableRows,
                    checkParents
                )
            }

            // Godir - https://gitlab.com/Montessquiio/godir
            val pureTableRows = htmlDocument.select("table.listing-table tbody tr")

            if (pureTableRows.any()) {
                return parsePureDirectoryListing(
                    baseUrl,
                    parsedWebDirectory,
                    htmlDocument,
                    pureTableRows,
                    checkParents
                )
            }

            // Remove it after ParsePureDictoryListing (.breadcrumb is used in it)

            htmlDocument.select(".breadcrumb").forEach { it.remove() }

            // Custom directory listing
            var divElements = htmlDocument.select("div#listing div")
            if (divElements.any()) {
                return parseCustomDivListing(
                    baseUrl,
                    parsedWebDirectory,
                    htmlDocument,
                    divElements,
                    checkParents
                )
            }

            // custom directory listing 2
            divElements =
                htmlDocument.select("div#filelist .tb-row.folder,div#filelist .tb-row.afile")

            if (divElements.any()) {
                return parseCustomDivListing2(
                    baseUrl,
                    parsedWebDirectory,
                    htmlDocument,
                    divElements,
                    checkParents
                )
            }

            // HFS
            divElements = htmlDocument.select("div#files .item")

            if (divElements.any()) {
                return parseHfsListing(
                    baseUrl,
                    webDirectory,
                    htmlDocument,
                    divElements,
                    checkParents
                )
            }

            val pres = htmlDocument.select("pre")

            if (pres.any()) {

                val result = parseHfsListing(
                    baseUrl,
                    webDirectory,
                    htmlDocument,
                    divElements,
                    checkParents
                )

                if (result.files.any() || result.subdirectories.any() || result.error) {
                    return result
                }
            }

            val parsedJavaScripDrawn = parseJavaScriptDrawn(baseUrl, parsedWebDirectory, html)

            if (parsedJavaScripDrawn.parsedSuccessfully && (parsedJavaScripDrawn.files.any() || parsedJavaScripDrawn.subdirectories.any())) {
                return parsedJavaScripDrawn
            }


            val listItems = htmlDocument.select("ul#root li")
            if (listItems.any()) {
                val result = parseListItemsDirectoryListing(
                    baseUrl,
                    parsedWebDirectory,
                    listItems,
                    checkParents
                )

                if (result.parsedSuccessfully || result.error) {
                    return result
                }
            }

            val tables = htmlDocument.select("table")
            if (tables.any()) {
                val result =
                    parseTablesDirectoryListing(baseUrl, parsedWebDirectory, tables, checkParents)

                if (result.files.any() || result.subdirectories.any() || result.error) {
                    return result
                }
            }

            val materialDesignListItems = htmlDocument.select("ul.mdui-list li")






            return null
//            val directoryListingDot

        } catch (e: Exception) {

        }


        return null;

    }


    private fun parseJavaScriptDrawn(
        baseUrl: String, parsedWebDirectory: WebDirectory, html: String
    ): WebDirectory {
        return WebDirectory()

    }

    suspend private fun parseDirectoryListingModel01(
        baseUrl: String,
        parsedWebDirectory: WebDirectory,
        directoryListingModel01: DirectoryListingModel01
    ): WebDirectory {
        return WebDirectory()

    }

    private fun convertDirectoryListingModel01(
        baseUrl: String,
        parsedWebDirectory: WebDirectory,
        directoryListingModel01: DirectoryListingModel01
    ): WebDirectory {
        return WebDirectory()

    }


    private fun parseCustomDivListing(
        baseUrl: String,
        parsedWebDirectory: WebDirectory,
        htmlDocument: Document,
        divElements: Elements,
        checkParents: Boolean
    ): WebDirectory {
        return WebDirectory()

    }

    private fun parseCustomDivListing2(
        baseUrl: String,
        parsedWebDirectory: WebDirectory,
        htmlDocument: Document,
        divElements: Elements,
        checkParents: Boolean
    ): WebDirectory {
        return WebDirectory()

    }


    private fun parseHfsListing(
        baseUrl: String,
        webDirectory: WebDirectory,
        htmlDocument: Document,
        divElements: Elements,
        checkParents: Boolean
    ): WebDirectory {
        return WebDirectory()
    }


    private fun parseIpfsDirectoryListing(
        baseUrl: String,
        parsedWebDirectory: WebDirectory,
        htmlDocument: Document,
        checkParents: Boolean
    ): WebDirectory {
        return WebDirectory()
    }


    private fun parseDirectoryListingDoctComDirectoryListing(
        baseUrl: String,
        parsedWebDirectory: WebDirectory,
        divElements: Elements,
        checkParents: Boolean
    ): WebDirectory {
        return WebDirectory()
    }


    private fun parseSnifDirectoryListing(
        baseUrl: String,
        parsedWebDirectory: WebDirectory,
        snifTableRows: Elements,
        checkParents: Boolean
    ): WebDirectory {
        return WebDirectory()
    }


    private fun parsePureDirectoryListing(
        baseUrl: String,
        parsedWebDirectory: WebDirectory,
        htmlDocument: Document,
        pureTableRows: Elements,
        checkParents: Boolean
    ): WebDirectory {
        return WebDirectory()
    }


    private fun parseH5aiDirectoryListing(
        baseUrl: String,
        parsedWebDirectory: WebDirectory,
        h5aiTablRows: Elements,
        checkParents: Boolean
    ): WebDirectory {
        return WebDirectory()
    }


    private fun parseTablesDirectoryListing(
        baseUrl: String,
        parsedWebDirectory: WebDirectory,
        tables: Elements,
        checkParents: Boolean
    ): WebDirectory {


        //dirty solution
        val hasSeperateDirectoryAndFilesTable = false

        val results = mutableListOf<WebDirectory>()

        for (k in tables) {

            val webDirectoryCopy = WebDirectory()
            webDirectoryCopy.parentDirectory = parsedWebDirectory.parentDirectory

            val tableHeaders: Map<Int, HeaderInfo> = getTable


        }

        return WebDirectory()
    }


    fun getTableHeader(table: Element): MutableMap<Int, HeaderInfo> {

        val tableHeaders = mutableMapOf<Int, HeaderInfo>()

        var headers = table.select("th")?.parents()?.select("th")

        var removeFirstRow = false

        if (headers != null && headers.first()?.hasAttr("colspan") == true) {
            headers = null
        }

        if (headers == null) {
            headers = table.select(".snHeading").select("td")
        }

        if (headers.isNullOrEmpty()) {
            headers = table.select("thead td, thead th")
        }

        if (headers.isNullOrEmpty()) {
            headers = table.select("tr:nth-child(1) > th")
        }

        if (headers.isNullOrEmpty()) {
            headers = table.select("tr:nth-child(1) > td")

            if (headers.isEmpty()) {
                removeFirstRow = true
            }
        }

        if (headers?.any() == true) {
            var headerIndex = 1

            for (header in headers) {

                if (!header.select("table").isNullOrEmpty()) {
                    continue
                }

                val tableHeaderInfo = getHeaderInfo(header)

                tableHeaders.set(headerIndex, tableHeaderInfo)

                if (header.hasAttr("colspan")) {
                    headerIndex += Integer.parseInt(header.attr("colspan")) - 1
                }

                headerIndex++
            }

            // Dynamically guess column types
            if (tableHeaders.all { it.value.type == HeaderType.Unknown }) {

                tableHeaders.clear()

                val fileNameColumnIndex = mutableListOf<Int>()
                val dateColumnIndex = mutableListOf<Int>()
                val fileSizeColumnIndex = mutableListOf<Int>()
                val typeColumnIndex = mutableListOf<Int>()

                val maxColumns = 0

                table.select("tr").forEach {

                }
                if (fileNameColumnIndex.any()) {

                }
                if (dateColumnIndex.any()) {

                }
                if (fileSizeColumnIndex.any()) {

                }
                if (typeColumnIndex.any()) {

                }

            } else {
                if (tableHeaders.any { it.value.type == HeaderType.FileName && tableHeaders.any { it.value.type == HeaderType.FileSize } } && removeFirstRow) {
                    table.select("tr:nth-child(1)").remove()
                }
            }
            return tableHeaders

        }

        return tableHeaders

    }

    fun getHeaderInfo(header: Element): HeaderInfo {
        var headerName = header.text().trim()

        val headerInfo = HeaderInfo(headerName)
        headerName = headerName.lowercase()

        headerName = headerName.replace(Regex.fromLiteral(""), "")

        if (headerName == "last modified" || headerName == "modified" || headerName.contains("date") || headerName.contains(
                "last modification"
            ) || headerName.contains("time")
        ) {

        }

        if (headerName == "type") {
            headerInfo.type = HeaderType.Type
        }


        if (headerName.contains(
                "size"
            ) || headerName.contains("file size") ||
            headerName.contains("filesize") || headerName.contains("taille")
        ) {

            headerInfo.type = HeaderType.FileSize
        }

        if (headerName == "description") {
            headerInfo.type = HeaderType.Description
        }


        // Check this as last one because of generic 'file' in it..

        if (headerInfo.type == HeaderType.Unknown && (
                    headerName == "file" ||
                            headerName == "name" ||
                            headerName.contains("file name") ||
                            headerName.contains("filename") ||
                            headerName == "directory" ||
                            headerName.contains("link") ||
                            headerName.contains("nom")
//                            headerName.contains("") ||
//                            headerName.contains("")
                    )
        ) {
            headerInfo.type = HeaderType.FileName
        }

        return headerInfo

    }

    data class HeaderInfo(
        val header: String,
        var type: HeaderType = HeaderType.Unknown
    )

    public enum class HeaderType {
        Unknown,
        FileName,
        FileSize,
        Modified,
        Description,
        Type
    }


    private fun processUrl(
        baseUrl: String,
        link: Element,
        linkHref: String,
        uri: URI,
        fullUrl: String
    ) {

    }


    private fun parsePreDirectoryListing(
        baseUrl: String,
        parsedWebDirectory: WebDirectory,
        pres: Elements,
        checkParents: Boolean
    ): WebDirectory {
        return WebDirectory()
    }

    private fun parseMaterialDesignListItemsDirectoryListing(
        baseUrl: String,
        parsedWebDirectory: WebDirectory,
        listItems: Elements,
        checkParents: Boolean
    ): WebDirectory {
        return WebDirectory()
    }

    private fun parseDirectoryListerDirectoryListing(
        baseUrl: String,
        parsedWebDirectory: WebDirectory,
        htmlDocument: Document,
        checkParents: Boolean
    ): WebDirectory {
        return WebDirectory()
    }


    private fun parseListItemsDirectoryListing(
        baseUrl: String,
        parsedWebDirectory: WebDirectory,
        listItems: Elements,
        checkParents: Boolean
    ): WebDirectory {
        return WebDirectory()
    }

    private fun parseLinksDirectoryListing(
        baseUrl: String,
        parsedWebDirectory: WebDirectory,
        links: Elements,
        checkParents: Boolean
    ): WebDirectory {
        return WebDirectory()
    }


    fun sameHostAndDirectoryFile(baseUri: URI, checkUri: URI) {

        var checkUrlWithoutFileName = checkUri.path
        checkUrlWithoutFileName = replaceCommonDefaultFileNames(checkUrlWithoutFileName)
//        val checkUrlFileName = Path.get


    }

    fun replaceCommonDefaultFileNames(input: String): String {
        return input.replace("index.shtml", "").replace("index.php", "")
            .replace("DirectoryList.asp", "")
    }


    fun String.getUrlExtension(): String {
        return substring(lastIndexOf("."));
    }
}