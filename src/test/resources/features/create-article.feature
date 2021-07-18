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


  Scenario: As a user, I should be able to edit existing article.
    Given Create an article with following fields
      | title | Cucumber                                                                                                                                                                                                                                                                                                                                       |
      | body  | Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. |
    Then Should succeed

    When Fetch article by id
    Then Should succeed

    When Update article with following fields
      | title       | Cucumber (Update) |
      | articleType | PAID              |
    Then Should succeed

    When Fetch article by id
    Then Should succeed
    And "title" should be equal to "Cucumber (Update)"
    And "articleType" should be equal to "PAID"


  Scenario: As a user, when I fetch non-existing article, I should get exception.
    Given Fetch article using id of "some-random-non-existence-id"
    Then Should have status of 404
    And "message" should be equal to "article not found."