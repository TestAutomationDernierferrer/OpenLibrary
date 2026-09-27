package pages
import geb.Page

class BookPage extends Page {
    static at = { waitFor { authorLinks.size() > 0 } }
    static content = {
        authorLinks { $("h2.edition-byline a[itemprop=author]") }
    }
}