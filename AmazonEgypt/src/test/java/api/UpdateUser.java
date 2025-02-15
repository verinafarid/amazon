package api;

import base.ApiTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class UpdateUser extends ApiTestBase {
	 @Test
	    public void testUpdateUser() {
	        int userId = 2;  // Example user ID
	        String requestBody = "{ \"name\": \"John Updated\", \"job\": \"Senior QA Engineer\" }";

	        Response response = given()
	                .contentType(ContentType.JSON)
	                .body(requestBody)
	                .when()
	                .put("/users/" + userId)
	                .then()
	                .statusCode(200)  // Validate HTTP status 200 (OK)
	                .extract()
	                .response();

	        // Extract updated values
	        String updatedName = response.jsonPath().getString("name");
	        String updatedJob = response.jsonPath().getString("job");

	        // Assertions
	        Assert.assertEquals(updatedName, "John Updated");
	        Assert.assertEquals(updatedJob, "Senior QA Engineer");

	        System.out.println("Updated Response: " + response.getBody().asString());
	    }

	 
	 @Test
	 public void testUpdateUserWithInvalidData() {
	     int userId = 2;
	     String invalidRequestBody = "{ \"name\": \"\" }";  // Missing required job field

	     Response response = given()
	             .contentType(ContentType.JSON)
	             .body(invalidRequestBody)
	             .when()
	             .put("/users/" + userId)
	             .then()
	             .log().all()  // Log request & response
	             .statusCode(400)  // Expecting 400 Bad Request
	             .extract()
	             .response();

	     // Log response for debugging
	     response.prettyPrint();

	     // Assert the error message is present
	     Assert.assertTrue(response.getBody().asString().contains("error"), "Expected error message not found!");
	 }
}
