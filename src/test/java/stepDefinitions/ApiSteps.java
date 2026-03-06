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
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
    }

    @When("I send a GET request to fetch user with ID {int}")
    public void i_send_a_get_request(int userId) throws InterruptedException {
        response = request.get(BASE_URL + "/users/" + userId);

        // If we get a 403, wait and retry once
        if (response.getStatusCode() == 403) {
            System.out.println("Got a 403, retrying in 2 seconds...");
            Thread.sleep(2000);
            response = request.get(BASE_URL + "/users/" + userId);
        }
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