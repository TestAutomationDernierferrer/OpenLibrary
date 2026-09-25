package pages
import geb.Page
/**
 * Lista de resultados de búsqueda (/search?title=...).
 */
class SearchResultsPage extends Page {
    static at = { waitFor { results.size() > 0 } }
    static content = {
// Cada resultado es un <li class="searchResultItem">
        results { $("li.searchResultItem") }
    }
}
