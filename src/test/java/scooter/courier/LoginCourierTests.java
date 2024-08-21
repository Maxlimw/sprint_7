package scooter.courier;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import ru.practicum.data.Courier;
import ru.practicum.data.CourierDataGen;
import scooter.base.Spec;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTests {
    private Courier courierForTest;

    @Before
    public void setUp() {
        CourierDataGen courierDataGen = new CourierDataGen();
        courierForTest = courierDataGen.courierGen();

        given()
                .spec(Spec.requestSpecification())
                .and()
                .body(courierForTest)
                .when()
                .post("/api/v1/courier")
                .then()
                .assertThat()
                .statusCode(201)
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Проверка успешной авторизации курьера с правильным логином и паролем")
    @Step("Отправить POST на /api/v1/courier/login с правильным логином и паролем")
    public void courierCanLoginWithValidCredentialsTest() {
        Courier loginCourier = new Courier();
        loginCourier.setLogin(courierForTest.getLogin());
        loginCourier.setPassword(courierForTest.getPassword());

        given()
                .spec(Spec.requestSpecification())
                .and()
                .body(loginCourier)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Проверка обязательности всех полей для авторизации")
    @Step("Отправить POST на /api/v1/courier/login без логина")
    public void courierCannotLoginWithoutLoginTest() {
        Courier loginCourier = new Courier();
        loginCourier.setPassword(courierForTest.getPassword());

        given()
                .spec(Spec.requestSpecification())
                .and()
                .body(loginCourier)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .log().all()
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Проверка, что система вернёт ошибку при неправильном логине")
    @Step("Отправить POST на /api/v1/courier/login с неправильным логином")
    public void courierCannotLoginWithInvalidLoginTest() {
        Courier loginCourier = new Courier();
        loginCourier.setLogin("invalidLogin");
        loginCourier.setPassword(courierForTest.getPassword());

        given()
                .spec(Spec.requestSpecification())
                .and()
                .body(loginCourier)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .log().all()
                .assertThat()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Проверка, что система вернёт ошибку при неправильном пароле")
    @Step("Отправить POST на /api/v1/courier/login с неправильным паролем")
    public void courierCannotLoginWithInvalidPasswordTest() {
        Courier loginCourier = new Courier();
        loginCourier.setLogin(courierForTest.getLogin());
        loginCourier.setPassword("invalidPassword");

        given()
                .spec(Spec.requestSpecification())
                .and()
                .body(loginCourier)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .log().all()
                .assertThat()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Проверка авторизации под несуществующим пользователем")
    @Step("Отправить POST на /api/v1/courier/login с несуществующим пользователем")
    public void courierCannotLoginWithNonExistentUserTest() {
        Courier loginCourier = new Courier();
        loginCourier.setLogin("nonExistentLogin");
        loginCourier.setPassword("nonExistentPassword");

        given()
                .spec(Spec.requestSpecification())
                .and()
                .body(loginCourier)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .log().all()
                .assertThat()
                .statusCode(404);
    }
}
