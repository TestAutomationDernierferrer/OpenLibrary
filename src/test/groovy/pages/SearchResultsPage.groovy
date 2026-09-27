package pages
import geb.Page

class SearchResultsPage extends Page {
    static at = { waitFor { results.size() > 0 } }
    static content = {
        results { $("li.searchResultItem") }
    }
    void chooseBookPublishedIn(int year) {
        def book = results.find { it.text().contains("First published in $year") }
        assert book != null : "No result found with 'First published in $year'"
        book.find("h3.booktitle a").click()
    }
}