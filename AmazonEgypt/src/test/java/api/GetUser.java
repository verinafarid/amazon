package api;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import base.ApiTestBase;

public class GetUser extends ApiTestBase {
	@Test
    public void testRetrieveUser() {
        int userId = 2;  // Example user ID

        Response response = given()
                .when()
                .get("/users/" + userId)
                .then()
                .statusCode(200)  // Validate HTTP status 200 (OK)
                .extract()
                .response();

        // Validate that user exists
        Assert.assertNotNull(response.jsonPath().getString("data.id"));

        // Print response
        System.out.println("User Details: " + response.getBody().asString());
    }
	@Test
	public void testRetrieveNonExistentUser() {
	    int nonExistentUserId = 9999;  // User ID that doesn't exist

	    Response response = given()
	            .when()
	            .get("/users/" + nonExistentUserId)
	            .then()
	            .log().all()  // Log request & response
	            .statusCode(404)  // Validate 404 Not Found
	            .extract()
	            .response();

	    // Log response body for debugging
	    response.prettyPrint();

	    // Validate that response does not contain user data
	    Assert.assertNull(response.jsonPath().get("data"), "Unexpected data found!");
	}
}
