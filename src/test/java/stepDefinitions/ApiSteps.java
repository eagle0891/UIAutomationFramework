package stepDefinitions;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static org.junit.Assert.*;

public class ApiSteps {
    private Response response;
    private RequestSpecification request;
    private static final String BASE_URL = "https://reqres.in/api";

    @Given("I prepare a request for the users endpoint")
    public void i_prepare_a_request() {
        request = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("User-Agent", "PostmanRuntime/7.32.2"); // Pretending to be a common tool
    }

    @When("I send a GET request to fetch user with ID {int}")
    public void i_send_a_get_request(int userId) {
        response = request.get(BASE_URL + "/users/" + userId);
    }

    @Then("the API status code should be {int}")
    public void the_status_code_should_be(int expectedCode) {
        assertEquals("Status code mismatch!", expectedCode, response.getStatusCode());
    }

    @Then("the response body should contain the email {string}")
    public void the_response_body_should_contain_email(String expectedEmail) {
        String actualEmail = response.jsonPath().getString("data.email");
        assertEquals("Email mismatch!", expectedEmail, actualEmail);
        System.out.println("Validated User Email: " + actualEmail);
    }
}