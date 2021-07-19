Feature: Get Article.

  Scenario Outline: As a user, when I fetch non-existing article, I should get exception.
    Given Fetch article using id of "<id>"
    Then Should have status of 404
    And "message" should be equal to "article not found."

    Examples:
      | id                   |
      | some-random-id       |
      | some-other-id        |
      | some-non-existent-id |