package steps

import api.OpenLibraryApi
import geb.Browser
import io.cucumber.java.After
import io.cucumber.java.Before
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import pages.AdvancedSearchPage
import pages.BookPage
import pages.HomePage
import pages.SearchResultsPage

class BookAuthorSteps {

    Browser browser
    OpenLibraryApi api = new OpenLibraryApi()
    String authorFromApi

    @Before
    void openBrowser() {
        browser = new Browser()
    }

    @After
    void closeBrowser() {
        browser?.quit()
    }

    @Given("user goes to the OpenLibrary page")
    void userGoesToOpenLibrary() {
        browser.via(HomePage)
        waitForHumanVerificationIfShown()
        browser.at(HomePage)
    }

    @Given("user sets website in English")
    void userSetsWebsiteInEnglish() {
        browser.page(HomePage).setLanguageToEnglish()
    }

    @When("user searches using Title option for book {string}")
    void userSearchesByTitle(String bookTitle) {
        searchByTitleFromAdvancedSearch(bookTitle)
        if (!browser.currentUrl.contains("title=")) {
            searchByTitleFromAdvancedSearch(bookTitle)
        }
        browser.at(SearchResultsPage)
    }

    private void searchByTitleFromAdvancedSearch(String bookTitle) {
        browser.via(AdvancedSearchPage)
        waitForHumanVerificationIfShown()
        browser.at(AdvancedSearchPage)
        browser.page(AdvancedSearchPage).searchByTitle(bookTitle)
        browser.waitFor { browser.currentUrl.contains("/search?") || browser.currentUrl.contains("/verify_human") }
        waitForHumanVerificationIfShown()
    }

    @When("user chooses book published in {int}")
    void userChoosesBookPublishedIn(int year) {
        browser.page(SearchResultsPage).chooseBookPublishedIn(year)
        browser.waitFor { browser.currentUrl.contains("/works/") || browser.currentUrl.contains("/verify_human") }
        waitForHumanVerificationIfShown()
        browser.at(BookPage)
    }

    // ---------- Verificación humana de OpenLibrary ----------

    private void waitForHumanVerificationIfShown() {
        if (!browser.currentUrl.contains("/verify_human")) {
            return
        }
        boolean headless = System.getProperty("geb.env") == "chromeHeadless"
        assert !headless : "OpenLibrary is asking for human verification (/verify_human). " +
                "It is not solved automatically. Run with -Pheadless=false and click the button manually."

        println ">>> OpenLibrary is asking for human verification: click 'Verify you are human' in the browser (2 minutes)."
        browser.waitFor(120) { !browser.currentUrl.contains("/verify_human") }
    }

    // ---------- API ----------

    @When("user gets the author from the API")
    void userGetsAuthorFromApi() {
        String workId = browser.page(BookPage).workId
        println "Work opened on the web: $workId (${browser.currentUrl})"
        authorFromApi = api.getAuthorNameForWork(workId)
        println "Author from API: $authorFromApi"
    }

    // ---------- Comprobación ----------

    @Then("the author from the API matches the author on the book page")
    void authorsMatch() {
        String authorOnPage = browser.page(BookPage).authorName
        println "Author on page: $authorOnPage"
        assert authorOnPage == authorFromApi : "Expected '$authorFromApi' but page shows '$authorOnPage'"
    }
}