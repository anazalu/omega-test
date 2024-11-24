package com.example;

import java.lang.RuntimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.junit.jupiter.api.Test;

import dev.failsafe.internal.util.Assert;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ApiTest {
    
    private List<Map<String, Object>> getAllEvents() {
        List<Map<String, Object>> events = new ArrayList<>();
        Response responseAllEvents = RestAssured.get("https://omega-vismatestingapp.azurewebsites.net/api/documents");
        if (responseAllEvents.getStatusCode() == 200) {
            JsonPath jsonPath = responseAllEvents.jsonPath();
            events = jsonPath.getList("$");
            if (events.size() > 0) {
                // System.out.println(events.get(0).get("id"));
                for (Map<String, Object> event : events) {
                    // System.out.println(event.get("title"));
                }
            }
        } else {
            throw new RuntimeException("Error - received status code: " + responseAllEvents.getStatusCode());
        }
        return events;
    }
    
    @Test
    public void deleteEventSucess() {
        List<Map<String, Object>> events = getAllEvents();
        int eventCountBeforePost = events.size();
        String eventDate = LocalDate.now().plusDays(7).format(DateTimeFormatter.ISO_DATE);
        System.out.println(eventDate);

        RestAssured.baseURI = "https://omega-vismatestingapp.azurewebsites.net/api"; 

        String requestBodyTemplate = """
            {
                "title": "New Unique Title 241124",
                "targetDate": "%s",
                "text": "New text content"
            }
        """;

        String requestBody = String.format(requestBodyTemplate, eventDate);

        given()
            .header("Content-Type", "application/json") 
            .body(requestBody) 
        .when()
            .post("/documents") 
        .then()
            .assertThat()
            .statusCode(200);

        events = getAllEvents();
        int eventCountAfterPost = events.size();

        Assert.isTrue(eventCountBeforePost + 1 == eventCountAfterPost, "Event count has not increased.");

        // int eventCountAfterDelete = events.size();
        System.out.println(eventCountBeforePost);
        System.out.println(eventCountAfterPost);
    }

}
