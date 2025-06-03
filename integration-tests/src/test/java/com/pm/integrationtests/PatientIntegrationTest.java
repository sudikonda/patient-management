package com.pm.integrationtests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PatientIntegrationTest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost:4004";
    }

    @Test
    void shouldReturnPatientsWithValidToken() {
        String loginPayload = """
                {
                    "email": "testuser@test.com",
                    "password": "password123"
                }
                """;
        Response response = RestAssured.given()
                .contentType("application/json")
                .body(loginPayload)
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .response();
        String token = response.jsonPath().getString("token");
        System.out.println("generated token = " + token);
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .get("api/patients")
                .then()
                .statusCode(200)
                .body("patients.size()", org.hamcrest.Matchers.greaterThan(0));
    }
}
