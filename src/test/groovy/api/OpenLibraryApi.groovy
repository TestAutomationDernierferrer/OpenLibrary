package api
import static io.restassured.RestAssured.given
class OpenLibraryApi {
    static final String BASE_URL = "https://openlibrary.org"

    String getAuthorNameForWork(String workId) {
        String authorKey = given()
                .baseUri(BASE_URL)
                .header("User-Agent", "openlibrary-acceptance-tests (derniernaim2@gmail.com)")
                .when()
                .get("/works/${workId}.json")
                .then()
                .statusCode(200)
                .extract().path("authors[0].author.key")
        String authorName = given()
                .baseUri(BASE_URL)
                .header("User-Agent", "openlibrary-acceptance-tests (derniernaim2@gmail.com)")
                .when()
                .get("${authorKey}.json")
                .then()
                .statusCode(200)
                .extract().path("name")
        return authorName
    }
}
