Feature: User Login

  Scenario: Successful login with valid credentials
    Given the user is on the login page
    When the user enters "standard_user" and "secret_sauce"
    And clicks the login button
    Then the user should be redirected to the dashboard