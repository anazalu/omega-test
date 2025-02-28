package com.example;

import java.util.stream.Stream;


public class TestData {
    public static Stream<String> provideValidRequestBody() {
        return Stream.of("""                
                {
                    "firstName": "John",
                    "lastName": "Doe",
                    "email": "john.doe@example.com",
                    "dateOfBirth": "1985-10-01",
                    "personalIdDocument":
                    { 
                        "documentId": "AB123456",
                        "countryOfIssue": "US",
                        "validUntil": "2030-12-31"
                    }   
                }
            """);
    }
    public static Stream<String> provideInvalidRequestBodies() {
        return Stream.of(
                """
                {
                    "firstName": "",
                    "lastName": "Doe",
                    "email": "john.doe@example.com",
                    "dateOfBirth": "1985-10-01",
                    "personalIdDocument":
                    { 
                        "documentId": "AB123456",
                        "countryOfIssue": "US",
                        "validUntil": "2030-12-31"
                    }   
                }
                """,

                """
                {
                    "firstName": "John",
                    "lastName": "",
                    "email": "john.doe@example.com",
                    "dateOfBirth": "1985-10-01",
                    "personalIdDocument":
                    { 
                        "documentId": "AB123456",
                        "countryOfIssue": "US",
                        "validUntil": "2030-12-31"
                    }
                }
                """,

                """
                {
                    "firstName": "John",
                    "lastName": "Doe",
                    "email": "john.doeexample.com",
                    "dateOfBirth": "1985-10-01",
                    "personalIdDocument":
                    { 
                        "documentId": "AB123456",
                        "countryOfIssue": "US",
                        "validUntil": "2030-12-31"
                    }
                }
                """,

                """
                {
                    "firstName": "John",
                    "lastName": "Doe",
                    "email": "john.doe@example.com",
                    "dateOfBirth": "",
                    "personalIdDocument":
                    { 
                        "documentId": "AB123456",
                        "countryOfIssue": "US",
                        "validUntil": "2030-12-31"
                    }
                }
                """,

                """
                {
                    "firstName": "John",
                    "lastName": "Doe",
                    "email": "john.doe@example.com",
                    "dateOfBirth": "1985-10-01",
                    "personalIdDocument":
                    {                         
                    }
                }
                """,

                """
                {
                    "firstName": "John",
                    "lastName": "Doe",
                    "email": "john.doe@example.com",
                    "dateOfBirth": "1985-10-01",
                    "personalIdDocument":
                    { 
                        "documentId": "",
                        "countryOfIssue": "US",
                        "validUntil": "2030-12-31"
                    }
                }
                """,

                """
                {
                    "firstName": "John",
                    "lastName": "Doe",
                    "email": "john.doe@example.com",
                    "dateOfBirth": "1985-10-01",
                    "personalIdDocument":
                    { 
                        "documentId": "AB123456",
                        "countryOfIssue": "",
                        "validUntil": "2030-12-31"
                    }
                }
                """,

                """
                {
                    "firstName": "John",
                    "lastName": "Doe",
                    "email": "john.doe@example.com",
                    "dateOfBirth": "1985-10-01",
                    "personalIdDocument":
                    { 
                        "documentId": "AB123456",
                        "countryOfIssue": "US",
                        "validUntil": ""
                    }
                }
                """,

                """
                {
                    "firstName": "John",
                    "lastName": "Doe",
                    "email": "john.doe@example.com",
                    "dateOfBirth": "1985-10-01",
                    "personalIdDocument":
                    { 
                        "documentId": "AB123456",
                        "countryOfIssue": "USA",
                        "validUntil": "2030-12-31"
                    }
                }
                """,

                """
                {
                    "firstName": "J",
                    "lastName": "Doe",
                    "email": "john.doe@example.com",
                    "dateOfBirth": "1985-10-01",
                    "personalIdDocument":
                    { 
                        "documentId": "AB123456",
                        "countryOfIssue": "US",
                        "validUntil": "2030-12-31"
                    }
                }
                """,

                """
                {
                    "firstName": "John",
                    "lastName": "D",
                    "email": "john.doe@example.com",
                    "dateOfBirth": "1985-10-01",
                    "personalIdDocument":
                    { 
                        "documentId": "AB123456",
                        "countryOfIssue": "US",
                        "validUntil": "2030-12-31"
                    }
                }
                """,

                """
                {
                    "firstName": "John",
                    "lastName": "Doe",
                    "email": "john.doe@example.com",
                    "dateOfBirth": "10-01-1985",
                    "personalIdDocument":
                    { 
                        "documentId": "AB123456",
                        "countryOfIssue": "US",
                        "validUntil": "2030-12-31"
                    }
                }
                """,

                """
                {
                    "firstName": "John",
                    "lastName": "Doe",
                    "email": "john.doe@example.com",
                    "dateOfBirth": "1985-10-01",
                    "personalIdDocument":
                    { 
                        "documentId": "AB1",
                        "countryOfIssue": "US",
                        "validUntil": "2030-12-31"
                    }
                }
                """,

                """
                {
                    "firstName": "John",
                    "lastName": "Doe",
                    "email": "john.doe@example.com",
                    "dateOfBirth": "1985-10-01",
                    "personalIdDocument":
                    { 
                        "documentId": "AB1234567890123456789012",
                        "countryOfIssue": "US",
                        "validUntil": "2030-12-31"
                    }
                }
                """,

                """
                {
                    "firstName": "John",
                    "lastName": "Doe",
                    "email": "john.doe@example.com",
                    "dateOfBirth": "1985-10-01",
                    "personalIdDocument":
                    { 
                        "documentId": "AB12345",
                        "countryOfIssue": "US",
                        "validUntil": "12-12-2030"
                    }
                }
                """,

                """
                {
                    "firstName": "John",
                    "lastName": "Doe",
                    "email": "john.doe@example.com",
                    "dateOfBirth": "1985-10-01",
                    "personalIdDocument":
                    { 
                        "documentId": "AB12345",
                        "countryOfIssue": "US",
                        "validUntil": "2030-28-01"
                    }
                }
                """);
    }
}
