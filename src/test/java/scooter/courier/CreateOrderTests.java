package scooter.courier;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import scooter.base.Spec;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTests {

    private final List<String> colours;


    public CreateOrderTests(List<String> colours) {
        this.colours = colours;
    }

    @Parameterized.Parameters
    public static Object[][] getColour() {
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("GREY", "BLACK")},
                {List.of()},
        };
    }

    @Test
    @DisplayName("Проверка создания заказа с цветами ")
    @Step("Отправить POST на /api/v1/orders")
    public void createOrderWithColourTest() {
        Map<String, Object> order = new HashMap<>();
        order.put("firstName", "Иван");
        order.put("lastName", "Пацанов");
        order.put("address", "Ленина 123");
        order.put("metroStation", "Комсомольская");
        order.put("phone", "+7 999 999 99 99");
        order.put("rentTime", 3);
        order.put("deliveryDate", "2024-08-18");
        order.put("comment", "Я гей");
        order.put("color", colours);


        given()
                .spec(Spec.requestSpecification())
                .and()
                .body(order)
                .when()
                .post("/api/v1/orders")
                .then()
                .log().all()
                .assertThat()
                .statusCode(201)
                .body("track", notNullValue());
    }
}
