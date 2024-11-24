package com.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
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

public class ApiTest {
    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://omega-vismatestingapp.azurewebsites.net/api";
    }

    private List<Map<String, Object>> getAllEvents() {
        List<Map<String, Object>> events = new ArrayList<>();
        Response responseAllEvents = RestAssured.get("/documents");
        assertEquals(200, responseAllEvents.getStatusCode(), "GET request failed");
        JsonPath jsonPath = responseAllEvents.jsonPath();
        events = jsonPath.getList("$");
        return events;
    }

    private Integer getEventId(List<Map<String, Object>> events, String eventTitle) {
        for (Map<String, Object> event : events) {
            if (event.get("title").equals(eventTitle)) {
                return (Integer) event.get("id");
            }
        }
        return null;
    }

    @Test
    public void deleteEventIncorrectEndpoint() {
        Response responseDeleteEvent = RestAssured.delete("/documents/");
        assertEquals(405, responseDeleteEvent.getStatusCode(), "Unexpected result for incorrect DELETE request.");
    }

    @Test
    public void deleteEventIncorrectIdType() {
        Response responseDeleteEvent = RestAssured.delete("/documents/id=001");
        assertEquals(404, responseDeleteEvent.getStatusCode(),
                "Unexpected result for DELETE request with incorrect ID type.");
    }

    static Stream<String> provideInvalidRequestBodies() {
        return Stream.of(
                """
                {
                    "targetDate": "2024-12-29",
                    "text": "Text content without title"
                }
                """,
                """
                {
                    "title": "Title with missing text",
                    "targetDate": "2024-12-29"
                }
                """);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidRequestBodies")
    public void postRequestWithMissingData(String requestBody) {
        given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/documents")
                .then()
                .assertThat()
                .statusCode(400);
    }

    @Test
    public void postWithIncorrectDateType() {
        String requestBodyTemplate = """
                    {
                        "title": "Title with incorrect date",
                        "targetDate": "10 December 2024",
                        "text": "Text content without title"
                    }
                """;
        String requestBody = String.format(requestBodyTemplate);

        given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/documents")
                .then()
                .assertThat()
                .statusCode(400);
    }

    @Test
    public void postAndDeleteEventSuccess() {
        List<Map<String, Object>> events = getAllEvents();
        int eventCountBeforePost = events.size();
        String EVENTTITLE = "New Unique Title 241124";
        String eventDate = LocalDate.now().plusDays(7).format(DateTimeFormatter.ISO_DATE);

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
