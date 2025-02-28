package com.example;

import java.util.stream.Stream;

public class TestDataOm {
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
}
