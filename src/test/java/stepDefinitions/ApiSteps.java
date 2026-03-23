package stepDefinitions;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.User;
import utils.ApiBase;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.Assert.*;

public class ApiSteps extends ApiBase { // Inherit the specs
    private Response response;
    private User user;

    @Given("I prepare a request for the users endpoint")
    public void i_prepare_a_request() {
        // We use the requestSpec inherited from ApiBase
        // No need to set headers or base URI here anymore!
    }

    @When("I send a GET request to fetch user with ID {int}")
    public void i_send_a_get_request(int userId) {
        response = RestAssured.given()
                .spec(requestSpec) // Plug in the enterprise spec
                .get("/users/" + userId);

        // This is the "Enterprise" magic:
        // Mapping the JSON body directly to our Java Object
        // Only map to POJO if the request was successful
        if (response.getStatusCode() == 200) {
            user = response.as(User.class);
        } else {
            System.out.println("Non-200 response received: " + response.getStatusCode());
        }
    }

    @Then("the API status code should be {int}")
    public void the_status_code_should_be(int expectedCode) {
        // We validate the response against our Response Spec (timing, content type)
        // AND check the status code
        response.then().spec(responseSpec);
        assertEquals(expectedCode, response.getStatusCode());
    }

    @Then("the API response should match the user schema")
    public void the_api_response_should_match_schema() {
        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/user-schema.json"));

        System.out.println("Schema Validation Passed!");
    }

    @Then("the response body should contain the email {string}")
    public void the_response_body_should_contain_email(String expectedEmail) {
        // Now we use standard Java methods instead of JSON paths
        assertEquals("Email mismatch!", expectedEmail, user.getEmail());
        System.out.println("Validated User POJO: " + user.getName());
    }
}