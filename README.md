# OpenLibrary acceptance tests

Acceptance test for [OpenLibrary](https://openlibrary.org) that mixes **web** and **API**
steps in a single scenario: it searches for a book on the website, opens it, gets its author
from the OpenLibrary API and checks that both authors match.

Built with **Gradle**, **Groovy**, **Cucumber**, **Geb** (on top of Selenium) and **REST Assured**.

## Requirements

- Java 17 or newer
- Google Chrome

You don't need to install Gradle: the project includes the Gradle Wrapper. Selenium
downloads the right ChromeDriver automatically.

## How to run

I recommend running it **with the browser visible** (see *"Verify you are human"* below):

```bash
./gradlew test -Pheadless=false        # Linux / macOS
gradlew.bat test -Pheadless=false      # Windows
```

Headless (no browser window):

```bash
./gradlew test
```

Run only some scenarios using their tags:

```bash
./gradlew test -Pheadless=false -Ptags=@author
```

## Reports

- **Cucumber report** (each step in green or red): `build/reports/cucumber/report.html`
- **Gradle test report** (summary and full output): `build/reports/tests/test/index.html`

## The scenario

```gherkin
Given user goes to the OpenLibrary page
And user sets website in English
When user searches using Title option for book "<bookTitle>"
And user chooses book published in <publishedYear>
And user gets the author from the API
Then the author from the API matches the author on the book page
```

Books are defined in the `Examples` table of `src/test/resources/features/book_author.feature`.
Adding a new book is just adding a new row, no code needed.

How it works, step by step:

1. Opens the OpenLibrary home page.
2. Switches the website to English using the language list in the footer.
3. Goes to Advanced Search, types the title and searches.
4. Picks the result that says *"First published in <year>"* and opens it.
5. Takes the book id from the URL (for example `OL27448W`) and asks the API:
   `GET /works/{id}.json` gives the author key, and `GET /authors/{key}.json` gives the name.
6. Compares that name with the author shown on the book page.

## Project structure

```
build.gradle                               dependencies and test configuration
src/test/resources/
  features/book_author.feature             the scenario, in Gherkin
  GebConfig.groovy                         browser configuration (base URL, waits, headless)
src/test/groovy/
  runner/RunCucumberTest.groovy            starts Cucumber from JUnit 5 (and generates the reports)
  steps/BookAuthorSteps.groovy             step definitions: connect the scenario with pages and API
  pages/                                   Page Objects, one class per screen
    HomePage.groovy                        home page and language switch
    AdvancedSearchPage.groovy              search by title
    SearchResultsPage.groovy               list of results, choose by year
    BookPage.groovy                        book page: author and book id
  api/OpenLibraryApi.groovy                calls to the OpenLibrary API with REST Assured
```

The idea is that each part has one job: the feature describes *what* is tested, the steps
connect it with the code, the Page Objects know *where* things are on each screen, and the
API client knows *how* to call the API. So if the website changes, only the Page Object of
that screen needs to change.

## Things I found along the way

While building this test against the real OpenLibrary website, a few things didn't work
the way the task description expected. Here is what I found and what I decided to do.

### 1. The "Title" search option is gone from the header

The task says *"user searches using Title option"*. The search box in the header is now a
web component inside a **shadow DOM** (which has given me a few nightmares in other
projects!), and it no longer has a "Title" option: it only does a general search.

So I use the **Advanced Search** page instead (`/advancedsearch`). It has a real *Title*
field, and it produces exactly the same search: `/search?title=...`. The intent of the step
stays the same, and the test is simpler and more stable.

### 2. "Verify you are human"

OpenLibrary is a busy public site, and sometimes it shows a **"Verify you are human"** page
to automated browsers. This is a security feature of their site, so I decided **not to
bypass it**. Instead, the test detects it:

- With the browser visible, it waits (up to 2 minutes) for a person to click the button,
  and then carries on.
- In headless mode it can't do that, so it stops with a clear message explaining why.

If you see the verification page, just click the button and the test will continue.

One more detail: after the verification, OpenLibrary sends you back to the search page but
loses the search itself (you land on an empty `/search`). When that happens, the test simply
runs the search again.

### 3. Switching the website to English

The test chooses the book by the text *"First published in 1954"*, which only appears in
English. If the browser opens OpenLibrary in another language (mine is in Spanish), that
text is translated and the book can't be found. That's why the scenario sets the website
to English first.

Two small lessons here:

- I click the **"English"** link in the footer using its `data-lang-id="en"` attribute.
  My first version used the link's `title`, but it turned out that the title is translated
  too (it says "Inglés" when the site is in Spanish), so it only worked if the site was
  already in English. For the same reason, the test never checks the page title: in Spanish
  even the site name is translated ("Biblioteca Abierta").
- Sometimes the first click on "English" doesn't change the language. So the test waits
  for the page to finish loading, clicks, and checks that the page is really in English
  (`<html lang="en">`). If not, it tries again, up to 3 times, and you'll see a message
  like this in the output:

```
>>> Website language is still 'es' after clicking 'English' (attempt 1 of 3)
```

None of this changes what the test checks: the author shown on the book page must match
the author returned by the API. These are just the things I had to deal with to make it
work reliably on the real website.

## How to extend it

- **New book:** add a row to `Examples` in the feature file.
- **New screen:** add a Page Object in `src/test/groovy/pages/`.
- **New API call:** add a method to `OpenLibraryApi.groovy`.
- **New steps:** add them to the steps class (or a new class in `src/test/groovy/steps/`).
- **Run a subset:** tag the scenario (for example `@smoke`) and run with `-Ptags=@smoke`.
