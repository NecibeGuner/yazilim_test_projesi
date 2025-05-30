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
        // testler başlamadan önce base URI ayarlar
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void testGetPostById() {
        // GET isteği ile /posts/1 endpoint'ine istek atıyor
        long responseTime = given()
                .log().all()                       
            .when()
                .get("/posts/1")                   
            .then()
                .log().all()                   
                .statusCode(200)                   // HTTP 200 OK bekle
                .body("userId", equalTo(1))        // JSON içindeki userId alanını kontrol eder
                .body("id", equalTo(1))            // JSON içindeki id alanını kontrol eder
                .body("title", notNullValue())     // title alanı boş olmamalı
                .body("body", notNullValue())      // body alanı boş olmamalı
                .extract().time();                 // yanıt süresini ölçer 

        System.out.println("✅ GET /posts/1 response time: " + responseTime + " ms");
        assert responseTime < 1000 
            : "❗ GET isteği 1 saniyeden uzun sürdü! Süre: " + responseTime + " ms";
    }

    @Test
    public void testCreatePost() {
        // Yeni gönderi için JSON gövdesi
        String requestBody = """
        {
            "title": "<<REST Assured testi>>",
            "body": "Bu bir test gonderisidir.",
            "userId": 10
        }
        """;

        // POST isteği ile /posts endpoint ine veri gönderir
        long responseTime = given()
                .header("Content-Type", "application/json; charset=UTF-8")  // İstek içeriğinin JSON olduğunu belirtir
                .body(requestBody)                                          
            .when()
                .post("/posts")                                             
            .then()
                .log().all()                                                
                .statusCode(201)                                            // HTTP 201 Created bekle
                .body("title", equalTo("<<REST Assured testi>>"))               // Dönen title'ı kontrol et
                .body("body", equalTo("Bu bir test gonderisidir."))         // Dönen body'i kontrol et
                .body("userId", equalTo(10))                                // Dönen userId'yi kontrol et
                .body("id", notNullValue())                                 // API bir id döndürmeli
                .extract().time();                                          // yanıt süresini ölçwr

        System.out.println("✅ POST /posts response time: " + responseTime + " ms");
        assert responseTime < 1000 
            : "❗ POST isteği 1 saniyeden uzun sürdü! Süre: " + responseTime + " ms";
    }

    @Test
    public void testUpdatePostById() {
        // Güncelleme için JSON gövdesi
        String updateBody = """
        {
            "id": 1,
            "title": "<<Guncellenmis Baslik>>",
            "body": "Bu icerik guncellendi.",
            "userId": 1
        }
        """;

        // PUT isteği ile /posts/1 endpoint ine güncelleme talebi gönderir
        long responseTime = given()
                .header("Content-Type", "application/json; charset=UTF-8")  // İçerik tipini JSON olarak ayarla
                .body(updateBody)                                          // Güncelleme verisini ekle
            .when()
                .put("/posts/1")                                           // İsteği gönder
            .then()
                .log().all()                                               // Cevabı konsola yaz
                .statusCode(200)                                           // HTTP 200 OK bekle
                .body("id", equalTo(1))                                    // id değişmemiş olmalı
                .body("title", equalTo("<<Guncellenmis Baslik>>"))             // title güncellendi mi?
                .body("body", equalTo("Bu icerik guncellendi."))          // body güncellendi mi?
                .body("userId", equalTo(1))                                // userId sabit kalmalı
                .extract().time();                                         // Yanıt süresini ölç

        System.out.println("✅ PUT /posts/1 response time: " + responseTime + " ms");
        assert responseTime < 1000 
            : "❗ Update isteği 1 saniyeden uzun sürdü! Süre: " + responseTime + " ms";
    }
}
