package stepDefinitions;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.ApiBase;
import static org.junit.Assert.*;

public class ApiSteps extends ApiBase { // Inherit the specs
    private Response response;

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
    }

    @Then("the API status code should be {int}")
    public void the_status_code_should_be(int expectedCode) {
        // We validate the response against our Response Spec (timing, content type)
        // AND check the status code
        response.then().spec(responseSpec);
        assertEquals(expectedCode, response.getStatusCode());
    }

    @Then("the response body should contain the email {string}")
    public void the_response_body_should_contain_email(String expectedEmail) {
        String actualEmail = response.jsonPath().getString("email");
        assertEquals("Email mismatch!", expectedEmail, actualEmail);
    }
}