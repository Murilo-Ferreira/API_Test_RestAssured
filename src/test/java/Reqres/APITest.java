package Reqres;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class APITest {

    @Test(description = "Validate \"CREATE\" script with \"POST” method covering cobertura de testes em Rest-Assured da API")
    public void testGivenValidateCreateWithMethodPost() {

        User user = new User("morpheus","leader");

        RestAssured.baseURI = "https://reqres.in/api";

        //Using information from class User
        String requestBody = String.format(
                "{\n" +
                "    \"name\": \"%s\",\n" +
                "    \"job\": \"%s\"\n" +
                "}", user.getName(), user.getJob());

        //Create a new user with POST method
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post("/users")
                .then()
                .log().all()
                .extract().response();

        //Asserting that information provided is right
        Assertions.assertEquals(201, response.statusCode());
        Assertions.assertEquals("morpheus", response.jsonPath().getString("name"));
        Assertions.assertEquals("leader", response.jsonPath().getString("job"));

    }

    @Test(description = "Validate StatusCode, coverage required fields and Contract.")
    public void testGivenValidateStatusCodeCoverageAndContract() {

        User user = new User("morpheus", "leader");

        RestAssured.baseURI = "https://reqres.in/api";

        //Using information from class User
        String requestBody = String.format(
                "{\n" +
                        "    \"name\": \"%s\",\n" +
                        "    \"job\":  \"%s\"\n" +
                        "}", user.getName(), user.getJob());

        //Validating StatusCode for some HTTP methods in this API
        //List a single user
        given()
                .get("/users/2")
                .then()
                .statusCode(200);

        //Update using PUT method
        given()
                .body(requestBody)
                .contentType(ContentType.JSON)
                .when()
                .put("/users/2")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);

        //Update using PATCH method
        given()
                .body(requestBody)
                .contentType(ContentType.JSON)
                .when()
                .patch("/users/2")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);

        //Delete user
        given()
                .when()
                .delete("/users/2")
                .then()
                .log().all()
                .assertThat()
                .statusCode(204);

        //Register unsuccessful a user
        given()
               .body("{\n" +
                       "    \"email\": \"sydney@fife\"\n" +
                       "}")
               .contentType(ContentType.JSON)
               .when()
               .post("/register")
                .then()
                .log().all()
                .assertThat()
                .statusCode(400);

        //

    }

}