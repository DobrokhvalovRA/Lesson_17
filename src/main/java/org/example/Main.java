package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;
import static org.hamcrest.Matchers.equalTo;

public class Main {
    public static void main(String[] args) {
        RestAssured.baseURI = "https://postman-echo.com/";
        Response response = given()
                .contentType("application/json")
                .when()
                .get("get?foo1=bar1&foo2=bar2")
                .then()
                //.body("foo1", equalTo(response.get))
                .extract().response();


        System.out.println(response.getStatusCode());
        String answer = response.asString();
        System.out.println(answer);

        JsonPath jsonPath = new JsonPath(answer);
        String values = jsonPath.getString("args.foo2");
        System.out.println("Значение: " + values);
    }
}