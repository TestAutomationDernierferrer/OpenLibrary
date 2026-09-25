package pages
import geb.Page


class AdvancedSearchPage extends Page {
    static url = "advancedsearch"
    static at = { titleInput.displayed }
    static content = {
        titleInput { $("input", name: "title") }
        searchButton { $("button.cta-btn--search") }
    }
    void searchByTitle(String bookTitle) {
        titleInput.value(bookTitle)
        searchButton.click()
    }
}
