import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements


fun main() {
    print("This is nice")
    getFile()
}

fun getFile() {

    val htmlDocument: Document = Jsoup.connect("http://blacpythoz.insomnia247.nl/files").get()


    htmlDocument.removeClass("sidebar")
    htmlDocument.removeAttr("nav")


    val directoryListingDotComlistItems =
        htmlDocument.getElementById("directory-listing li") + htmlDocument.getElementsByClass("directory-listing li")


    if (directoryListingDotComlistItems?.isNotEmpty() ?: false) {
        directoryListingDotComlistItems?.isEmpty()
//        return ParseDirectoryListingDoctComDirectoryListing(baseUrl, parsedWebDirectory, directoryListingDotComlistItems, checkParents);
    }


//        val h5aiTableRows = htmlDocument.QuerySelectorAll("#fallback table tr");

//    htmlDocument.QuerySelectorAll("#sidebar").ToList().ForEach(e => e.Remove());
//    htmlDocument.QuerySelectorAll("nav").ToList().ForEach(e => e.Remove());

//    IHtmlCollection<IElement> directoryListingDotComlistItems = htmlDocument.QuerySelectorAll("#directory-listing li, .directory-listing li");

//    print("Doc is $htmlDocument")
//
//    IHtmlCollection<IElement> h5aiTableRows = htmlDocument.QuerySelectorAll("#fallback table tr");
//
//    if (h5aiTableRows.Any())
//    {
//        return ParseH5aiDirectoryListing(baseUrl, parsedWebDirectory, h5aiTableRows, checkParents);
//    }

}

private operator fun Element?.plus(elementsByClass: Elements?): Elements? {
    elementsByClass?.add(this)
    return elementsByClass
}
