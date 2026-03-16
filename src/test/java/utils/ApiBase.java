package utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static org.hamcrest.Matchers.lessThan; // Import this for the time validation

public class ApiBase {
    protected static RequestSpecification requestSpec;
    protected static ResponseSpecification responseSpec;

    static {
        // Defines how EVERY request should look
        String env = System.getProperty("env", "qa"); // Get 'qa' from Jenkins
        String baseUri = env.equalsIgnoreCase("qa") ? "https://jsonplaceholder.typicode.com" : "https://dev-api.example.com";

        requestSpec = new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setContentType(ContentType.JSON)
                .addHeader("User-Agent", "Enterprise-Test-Framework")
                .log(LogDetail.ALL) // Enterprise standard: log everything for debugging
                .build();

        // Defines what a "Healthy" response should look like
        responseSpec = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan(3000L)) // Fail if > 3s
                .log(LogDetail.ALL)
                .build();
    }
}