import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestPost {


    @Test
    public void testGet() {
       String answer;
        RestAssured.baseURI = "https://postman-echo.com/";
        Response response = given()
                .param("foo1", "bar1")
                .param("foo2", "bar2")
                .get("get")
                .then()
                .contentType(ContentType.JSON)
                .extract().response();

        assertEquals(response.getStatusCode(), 200);

        answer = response.asString();
        JsonPath jsonPath = new JsonPath(answer);
        assertTrue(jsonPath.getString("args.foo1").equals("bar1"));
        assertTrue(jsonPath.getString("args.foo2").equals("bar2"));
        assertTrue(jsonPath.getString("headers.host").equals("postman-echo.com"));
        assertTrue(jsonPath.getString("headers.x-request-start") != "");
        assertTrue(jsonPath.getString("headers.connection").equals("close"));
        assertTrue(jsonPath.getString("headers.x-forwarded-proto").equals("https"));
        assertTrue(jsonPath.getString("headers.x-forwarded-port").equals("443"));
        assertTrue(jsonPath.getString("headers.x-amzn-trace-id") != "");
        assertTrue(jsonPath.getString("headers.accept").equals("*/*"));
        assertTrue(jsonPath.getString("headers.user-agent").equals("Apache-HttpClient/4.5.13 (Java/17.0.12)"));
        assertTrue(jsonPath.getString("headers.accept-encoding").equals("gzip,deflate"));
        assertTrue(jsonPath.getString("url").equals("https://postman-echo.com/get?foo1=bar1&foo2=bar2"));

        /*assertTrue(answer.contains("postman-echo.com"));
        assertTrue(answer.contains("close"));
        assertTrue(answer.contains("https"));
        assertTrue(answer.contains("443"));*/

    }

    @Test
    public void testPost() {
        String answer;
        RestAssured.baseURI = "https://postman-echo.com";
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body("{\"args\":{\"files\":\"5\"}}")
                .when()
                .post("/post")
                .then()
                .extract().response();

        answer = response.asString();
        JsonPath jsonPath = new JsonPath(answer);
        assertEquals(response.getStatusCode(),200);
        assertTrue(jsonPath.getString("json.args.files").equals("5"));
    }




}
