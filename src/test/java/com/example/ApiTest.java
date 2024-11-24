package com.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.junit.jupiter.api.Test;

// import dev.failsafe.internal.util.Assert;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ApiTest {

    private List<Map<String, Object>> getAllEvents() {
        List<Map<String, Object>> events = new ArrayList<>();
        Response responseAllEvents = RestAssured.get("https://omega-vismatestingapp.azurewebsites.net/api/documents");
        assertEquals(200, responseAllEvents.getStatusCode(), "GET request failed");
        JsonPath jsonPath = responseAllEvents.jsonPath();
        events = jsonPath.getList("$");
        return events;
    }

    private Integer getEventId(List<Map<String, Object>> events, String eventTitle) {
        for (Map<String, Object> event : events) {
            if (event.get("title").equals(eventTitle)) {
                return (Integer)event.get("id");
            }
        }
        return null;
    }

    @Test
    public void deleteEventSucess() {
        List<Map<String, Object>> events = getAllEvents();
        int eventCountBeforePost = events.size();
        String EVENTTITLE = "New Unique Title 241124";
        String eventDate = LocalDate.now().plusDays(7).format(DateTimeFormatter.ISO_DATE);
        RestAssured.baseURI = "https://omega-vismatestingapp.azurewebsites.net/api";

        String requestBodyTemplate = """
                    {
                        "title": "%s",
                        "targetDate": "%s",
                        "text": "New text content"
                    }
                """;
        String requestBody = String.format(requestBodyTemplate, EVENTTITLE, eventDate);

        // POST request to create a new event
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
        assertEquals(eventCountBeforePost + 1, eventCountAfterPost, "Event count has not increased.");
        
        // Retrieve the ID of the new event
        Integer eventId = getEventId(events, EVENTTITLE);
        assertNotNull(eventId, "The new event not found.");

        // DELETE request to delete the new event
        Response responseDeleteEvent = RestAssured.delete("/documents/" + eventId);
        assertEquals(200, responseDeleteEvent.getStatusCode(), "DELETE request failed.");

        // Validate that the new event was actually deleted
        events = getAllEvents();
        int eventCountAfterDelete = events.size();
        assertEquals(eventCountBeforePost, eventCountAfterDelete, "Event count has not decreased.");
        eventId = getEventId(events, EVENTTITLE);
        assertNull(eventId, "The new event was not deleted.");
    }
}
