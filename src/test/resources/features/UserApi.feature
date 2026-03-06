@api
Feature: User API Validation

  Scenario: Verify user details via API
    Given I prepare a request for the users endpoint
    When I send a GET request to fetch user with ID 2
    Then the API status code should be 200
    And the response body should contain the email "janet.weaver@reqres.in"