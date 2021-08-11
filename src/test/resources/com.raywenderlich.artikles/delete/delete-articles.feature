Feature: Delete article.

  Scenario: As a user, I should be able to delete an existing article.
    Given Create an article with following fields
      | title | Cucumber                                                                                                                                                                                                                                                                                                                                       |
      | body  | Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. |
    Then Should succeed

    When Fetch article by id
    Then Should succeed

    When Delete article
    Then Should succeed
    # Fetching article that has already been deleted
    When Fetch article by id
    Then Should have status of 404