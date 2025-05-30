package com.necibe.tests;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;

public class JsonPlaceholderApiTest {

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void testGetPostById() {
        long responseTime = given()
                .log().all()
            .when()
                .get("/posts/1")
            .then()
                .log().all()
                .statusCode(200)
                .body("userId", equalTo(1))
                .body("id", equalTo(1))
                .body("title", notNullValue())
                .body("body", notNullValue())
                .extract().time();

        System.out.println("✅ GET /posts/1 response time: " + responseTime + " ms");
        assert responseTime < 1000 : "❗ GET isteği 1 saniyeden uzun sürdü! Süre: " + responseTime + " ms";
    }

    @Test
    public void testCreatePost() {
        String requestBody = """
        {
            "title": "REST Assured testi",
            "body": "Bu bir test gonderisidir.",
            "userId": 10
        }
        """;

        long responseTime = given()
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(requestBody)
            .when()
                .post("/posts")
            .then()
                .log().all()
                .statusCode(201)
                .body("title", equalTo("REST Assured testi"))
                .body("body", equalTo("Bu bir test gonderisidir."))
                .body("userId", equalTo(10))
                .body("id", notNullValue())
                .extract().time();

        System.out.println("✅ POST /posts response time: " + responseTime + " ms");
        assert responseTime < 1000 : "❗ POST isteği 1 saniyeden uzun sürdü! Süre: " + responseTime + " ms";
    }

    @Test
    public void testUpdatePostById() {
        String updateBody = """
        {
            "id": 1,
            "title": "Guncellenmis Baslik",
            "body": "Bu icerik guncellendi.",
            "userId": 1
        }
        """;

        long responseTime = given()
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(updateBody)
            .when()
                .put("/posts/1")
            .then()
                .log().all()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("title", equalTo("Guncellenmis Baslik"))
                .body("body", equalTo("Bu icerik guncellendi."))
                .body("userId", equalTo(1))
                .extract().time();

        System.out.println("✅ PUT /posts/1 response time: " + responseTime + " ms");
        assert responseTime < 1000 : "❗ Update isteği 1 saniyeden uzun sürdü! Süre: " + responseTime + " ms";
    }
}
