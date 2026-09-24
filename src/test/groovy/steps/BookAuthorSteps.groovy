package steps
import io.cucumber.java.PendingException
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
/**
 * Step definitions del escenario. Cucumber crea una instancia nueva por escenario,
 * así que los campos de esta clase son la "memoria" de cada ejecución.
 */
class BookAuthorSteps {
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