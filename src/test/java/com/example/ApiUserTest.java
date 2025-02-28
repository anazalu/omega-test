package com.example;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.basic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class ApiUserTest {
    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost:8080/api";
        RestAssured.authentication = basic("username", "password");
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        // RestAssured.defaultParser = Parser.JSON;
        // You can reset to the standard baseURI (localhost), basePath (empty), standard port (8080), standard root path (""), default authentication scheme (none) 
    }

    private List<Map<String, Object>> getAllUsers() {
        List<Map<String, Object>> users = new ArrayList<>();
        Response responseAllUsers = RestAssured.get("/users");
        assertEquals(200, responseAllUsers.getStatusCode(), "GET request failed");
        JsonPath jsonPath = responseAllUsers.jsonPath();
        users = jsonPath.getList("$");
        return users;
    }

    @Test
    @Tag("Negative scenario")
    @DisplayName("Get - non existant User ID")
    public void getUserWrongId() {
        given().
            header("Content-Type", "application/json").
        when().
            get("users/not-an-existing-user-id").
        then().
            statusCode(404);
    }

    @Test
    @Tag("Negative scenario")
    @DisplayName("Delete - nonexistent User ID")
    public void deleteUserWrongId() {
        given().
            header("Content-Type", "application/json").
        when().
            delete("users/not-an-existing-user-id").
        then().
            statusCode(404);
    }
   
    @ParameterizedTest
    // @Tag("Negative scenario")
    // @DisplayName("Post - invalid input")
    @MethodSource("com.example.TestData#provideInvalidRequestBodies")
    public void postRequestWithInvalidInput(String requestBody) {
        given().
            header("Content-Type", "application/json").
            body(requestBody).
        when().
            post("/users").
        then().
            statusCode(400);
    }
   
    @ParameterizedTest
    @MethodSource("com.example.TestData#provideValidRequestBody")
    public void postRequestWithValidInput(String requestBody) {
        List<Map<String, Object>> users = getAllUsers();
        int userCountBeforePost = users.size();
        
        Response response =
        given().
            header("Content-Type", "application/json").
            body(requestBody).
        when().
            post("/users").
        then().
            statusCode(201).
        extract().
            response();
        
        String userIdString = response.path("id");
        System.out.println(userIdString);

        users = getAllUsers();
        int userCountAfterPost = users.size();
        assertEquals(userCountBeforePost + 1, userCountAfterPost, "Quantity of users failed to increase.");

        given().
            header("Content-Type", "application/json").
        when().
            get("users/" + userIdString).
        then().
            statusCode(200);

        given().
            header("Content-Type", "application/json").
        when().
            delete("users/" + userIdString).
        then().
            statusCode(204);

        users = getAllUsers();
        int userCountAfterdelete = users.size();
        assertEquals(userCountAfterPost - 1, userCountAfterdelete, "Quantity of users failed to decrease.");

        given().
            header("Content-Type", "application/json").
        when().
            get("users/" + userIdString).
        then().
            statusCode(404);
    }
}
