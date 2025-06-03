package com.pm.integrationtests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AuthIntegrationTest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost:4004";
    }

    @Test
    void shouldReturnOkWithValidToken() {
        String loginPayload = """
                {
                    "email": "testuser@test.com",
                    "password": "password123"
                }
                """;
        RestAssured.given()
                .contentType("application/json")
                .body(loginPayload)
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    @Test
    void shouldReturnUnauthorizedWithInvalidToken() {
        String loginPayload = """
                {
                    "email": "testuser@test.com",
                    "password": "wrongpassword"
                }
                """;
        RestAssured.given()
                .contentType("application/json")
                .body(loginPayload)
                .post("/auth/login")
                .then()
                .statusCode(401);

    }
}
