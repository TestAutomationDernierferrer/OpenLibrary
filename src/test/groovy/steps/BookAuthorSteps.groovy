package steps
import geb.Browser
import io.cucumber.java.After
import io.cucumber.java.Before
import io.cucumber.java.PendingException
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import pages.AdvancedSearchPage
import pages.HomePage
import pages.SearchResultsPage

class BookAuthorSteps {
    Browser browser
    @Before
    void openBrowser() {
        browser = new Browser()
    }
    @After
    void closeBrowser() {
        browser?.quit()
    }
// ---------- WEB ----------
    @Given("user goes to the OpenLibrary page")
    void userGoesToOpenLibrary() {
        browser.to(HomePage)
    }
    @Given("user sets website in English")
    void userSetsWebsiteInEnglish() {
        browser.page(HomePage).setLanguageToEnglish()
    }
    @When("user searches using Title option for book {string}")
    void userSearchesByTitle(String bookTitle) {
        browser.to(AdvancedSearchPage)
        browser.page(AdvancedSearchPage).searchByTitle(bookTitle)
        browser.at(SearchResultsPage)
    }
    @When("user chooses book published in {int}")
    void userChoosesBookPublishedIn(int year) {
        throw new PendingException()
    }
// ---------- API ----------
    @When("user gets the author from the API")
    void userGetsAuthorFromApi() {
        throw new PendingException()
    }
// ---------- Comprobación ----------
    @Then("the author from the API matches the author on the book page")
    void authorsMatch() {
        throw new PendingException()
    }
}
