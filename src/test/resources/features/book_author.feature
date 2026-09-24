Feature: Author of a book in OpenLibrary
  As a user I want to verify that the author shown on the book page
  matches the author returned by the OpenLibrary API.
  Scenario Outline: Author shown on the web matches the author from the API
    Given user goes to the OpenLibrary page
    And user sets website in English
    When user searches using Title option for book "<bookTitle>"
    And user chooses book published in <publishedYear>
    And user gets the author from the API
    Then the author from the API matches the author on the book page
    Examples:
      | bookTitle | publishedYear |
      | The Lord of the Rings | 1954 |