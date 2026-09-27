package pages
import geb.Page

class BookPage extends Page {
    static at = { waitFor { authorLinks.size() > 0 } }
    static content = {
        authorLinks { $("h2.edition-byline a[itemprop=author]") }
    }
    String getWorkId() {
        def matcher = (browser.currentUrl =~ /\/works\/(OL\d+W)/)
        assert matcher.find() : "Current URL does not contain a work id: ${browser.currentUrl}"
        return matcher.group(1)
    }
}