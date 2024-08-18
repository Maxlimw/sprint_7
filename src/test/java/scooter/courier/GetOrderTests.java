package scooter.courier;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import scooter.base.Spec;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class GetOrderTests {

    @Test
    @DisplayName("Проверка, что возвращается список заказов для станций метро без указания курьера")
    @Step("Отправить GET на /api/v1/orders с параметром nearestStation")
    public void getOrdersWithStationFilterTest() {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("nearestStation", "[\"3\"]");
        queryParams.put("limit", 30);
        queryParams.put("page", 2);

        given()
                .spec(Spec.requestSpecification())
                .queryParams(queryParams)
                .when()
                .get("/api/v1/orders")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("orders", notNullValue());
    }

    @Test
    @DisplayName("Проверка, что возвращается список заказов без указания параметров")
    @Step("Отправить GET на /api/v1/orders без параметров")
    public void getOrdersWithoutAnyParametersTest() {
        given()
                .spec(Spec.requestSpecification())
                .when()
                .get("/api/v1/orders")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("orders", notNullValue());
    }
}
