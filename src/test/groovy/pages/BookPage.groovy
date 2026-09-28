package pages
import geb.Page
/**
 * Página de un libro (/works/OL12345W/Titulo).
 */
class BookPage extends Page {
    static at = { waitFor { authorLinks.size() > 0 } }
    static content = {
// El autor sale dos veces (versión móvil y escritorio); una de ellas está oculta
        authorLinks { $("h2.edition-byline a[itemprop=author]") }
    }
/** Nombre del autor visible en la página. */
    String getAuthorName() {
        authorLinks.find { it.displayed }.text().trim()
    }
/** Extrae "OL12345W" de la URL actual. */
    String getWorkId() {
        def matcher = (browser.currentUrl =~ /\/works\/(OL\d+W)/)
        assert matcher.find() : "Current URL does not contain a work id: ${browser.currentUrl}"
        return matcher.group(1)
    }
}
