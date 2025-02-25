package com.example;

import java.util.stream.Stream;
// import org.junit.jupiter.params.provider.DataProvider;


public class TestData {
    // @DataProvider
    public static Stream<String> provideInvalidRequestBodies() {
        return Stream.of(
                """
                {
                    "id": "2024-12-29",
                    "text": "Text content without title"
                }
                """,
                """
                {
                    "id": "",
                    "targetDate": "2024-12-29"
                }
                """);
    }

    /* 
    id: "123456"
    firstName: "John"
    lastName: "Doe"
    email: "john.doe@example.com"
    dateOfBirth: "1985-10-01"
    personalIdDocument:
        documentId: "AB123456"
        countryOfIssue: "US"
        validUntil: "2030-12-31"
*/
}
