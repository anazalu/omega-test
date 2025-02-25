package com.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ApiUserTest {
    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost:8080/api";
    }

    @Test
    @Tag("Delete")
    @DisplayName("Delete - incorrect endpoint")
    public void deleteUserIncorrectEndpoint() {
        Response responseDeleteUser = RestAssured.delete("/wrongpath/");
        assertEquals(404, responseDeleteUser.getStatusCode(), "User not found.");
    }

   

    @ParameterizedTest
    @MethodSource("com.example.TestData#provideInvalidRequestBodies")
    public void postRequestWithMissingData(String requestBody) {
        given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/users")
                .then()
                .assertThat()
                .statusCode(400);
    }
}
