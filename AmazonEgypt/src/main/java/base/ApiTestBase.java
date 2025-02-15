package base;

import org.testng.annotations.BeforeClass;

import io.restassured.RestAssured;

public class ApiTestBase {
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://reqres.in/api";
    }

}
