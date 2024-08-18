package scooter.base;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Spec {

    public static RequestSpecification requestSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri(RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru")
                .setContentType(ContentType.JSON)
                .build();
    }
}
