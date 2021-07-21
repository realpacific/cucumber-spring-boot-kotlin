Feature: Update article.

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
