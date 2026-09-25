package steps
import geb.Browser
import io.cucumber.java.After
import io.cucumber.java.Before
import io.cucumber.java.PendingException
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When

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
        throw new PendingException()
    }
    @Given("user sets website in English")
    void userSetsWebsiteInEnglish() {
        throw new PendingException()
    }
    @When("user searches using Title option for book {string}")
    void userSearchesByTitle(String bookTitle) {
        throw new PendingException()
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