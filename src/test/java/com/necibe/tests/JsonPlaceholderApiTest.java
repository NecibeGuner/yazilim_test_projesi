//C:\Users\Necibe\yazilim_test_projesi\src\test\java\com\necibe\tests\JsonPlaceholderApiTest.java

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
    public void testGetPosts() {
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
                .extract()
                .time();

        System.out.println("✅ GET /posts/1 response time: " + responseTime + " ms");
        assert responseTime < 1000 : "❗ GET isteği 1 saniyeden uzun sürdü! Süre: " + responseTime + " ms";
    }

    @Test
    public void testCreatePost() {
        String requestBody = """
        {
            "title": "REST Assured testi",
            "body": "Bu bir test gönderisidir.",
            "userId": 10
        }
        """;

        long responseTime = given()
                .header("Content-type", "application/json; charset=UTF-8")
                .and()
                .body(requestBody)
                .when()
                .post("/posts")
                .then()
                .log().all()
                .statusCode(201)
                .body("title", equalTo("REST Assured testi"))
                .body("body", equalTo("Bu bir test gönderisidir."))
                .body("userId", equalTo(10))
                .body("id", notNullValue())
                .extract()
                .time();

        System.out.println("✅ POST /posts response time: " + responseTime + " ms");
        assert responseTime < 1000 : "❗ POST isteği 1 saniyeden uzun sürdü! Süre: " + responseTime + " ms";
    }
}

