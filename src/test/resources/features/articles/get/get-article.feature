Feature: Get Article.

  Scenario Outline: As a user, when I fetch non-existing article, I should get exception.
    Given Fetch article using id of "<id>"
    Then Should have status of 404
    And "errors" should include "article not found."

    Examples:
      | id                   |
      | some-random-id       |
      | some-other-id        |
      | some-non-existent-id |


  Scenario: As a user, I should be able to get all articles.
    Given Bulk create articles with following fields
      | title    | body                    |
      | Hamcrest | A testing library       |
      | AssertJ  | Fluent testing library  |
      | TDD      | Test Driven Development |
    When Fetch all articles
    Then Should succeed
    And Should have size of 3

  Scenario: As a user, I should be able to view a single article.
    Given Create an article with following fields
      | title | Cucumber                                                                                                                                                                                                                                                                                                                                       |
      | body  | Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. |
    Then Should succeed

    When Fetch article by id
    Then Should succeed
    And "title" should be equal to "Cucumber"
    And "id" should be equal to differentiator