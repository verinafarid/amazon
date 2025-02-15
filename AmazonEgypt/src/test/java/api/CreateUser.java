package api;

import org.testng.Assert;
import org.testng.annotations.Test;
import base.ApiTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class CreateUser extends ApiTestBase{
	
	
	   @Test
	    public void testCreateUser() {
	        String requestBody = "{ \"name\": \"John\", \"job\": \"QA Engineer\" }";

	        Response response = given()
	                .contentType(ContentType.JSON)
	                .body(requestBody)
	                .when()
	                .post("/users")
	                .then()
	                .statusCode(201)  // Validate HTTP status 201 (Created)
	                .extract()
	                .response();

	        // Extract user details from response
	        String name = response.jsonPath().getString("name");
	        String job = response.jsonPath().getString("job");

	        // Assertions to validate response data
	        Assert.assertEquals(name, "John");
	        Assert.assertEquals(job, "QA Engineer");

	        // Print response body
	        System.out.println("Response: " + response.getBody().asString());
	    }

	   @Test
	   public void testCreateUserInvalidData() {
	       String invalidRequestBody = "{ \"invalidField\": \"value\" }";  // Incorrect JSON structure

	       Response response = given()
	               .contentType(ContentType.JSON)
	               .body(invalidRequestBody)
	               .when()
	               .post("/users")
	               .then()
	               .log().all()  // Logs full request & response
	               .statusCode(400)  // Validate that a 400 Bad Request is returned
	               .extract()
	               .response();

	       // Log response body for debugging
	       response.prettyPrint();

	       // Assert the response contains an error message (if applicable)
	       Assert.assertTrue(response.getBody().asString().contains("error"), "Error message not found!");
	   }

}
