Feature: Create Article.

  Scenario: As a user, I should be able to create new article.
  New article should be free by default and the created article should be viewable.
    Given Create an article with following fields
      | title | Cucumber                                                                                                                                                                                                                                                                                                                                       |
      | body  | Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. |
    Then Should succeed
    And "id" should not be null
    And "title" should not be null
    And "body" should not be null
    And "lastUpdatedOn" should not be null
    And "createdOn" should not be null
    And "title" should be equal to "Cucumber"
    And "articleType" should be equal to "FREE"
    And "title" should be same as that in payload

    When Fetch article by id
    Then Should succeed
    And "title" should be equal to "Cucumber"
    And "id" should be equal to differentiator



  Scenario: As a user, I should not be allowed to create articles with invalid fields.
    Error message should show which field is the cause of the errors.
    Given Create an article with following fields
      | title |                                                         |
      | body  | Lorem ipsum dolor sit amet, consectetur adipiscing elit |
    Then Should have status of 400
    And "errors" should include "Title is empty"
