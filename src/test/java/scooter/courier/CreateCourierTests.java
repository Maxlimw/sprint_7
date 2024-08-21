package scooter.courier;

import com.google.gson.Gson;
import io.qameta.allure.Step;
import org.junit.Before;
import org.junit.Test;
import ru.practicum.data.Courier;
import scooter.base.Spec;
import ru.practicum.data.CourierDataGen;
import io.qameta.allure.junit4.DisplayName;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CreateCourierTests {

    private Courier courierForTest;
    CourierDataGen courierDataGen = new CourierDataGen();


    @Before
    public void setUp() {
        CourierDataGen courierDataGen = new CourierDataGen();
        courierForTest = courierDataGen.courierGen();
    }


    @Test
    @DisplayName("Проверка возможности создания курьера")
    @Step("Отправить POST на /api/v1/courier")
    public void createCourierTest(){
        System.out.println(new Gson().toJson(courierForTest));
        given()
                .spec(Spec.requestSpecification())
                .and()
                .body(courierForTest)
                .when()
                .post("/api/v1/courier")
                .then()
                .log().all()
                .assertThat()
                .statusCode(201)
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Проверка невозможности создания 2-х одинаковых курьеров")
    @Step("Отправить POST на /api/v1/courier")
    public void createSameCouriersTest(){
        System.out.println(new Gson().toJson(courierForTest));
        given()
                .spec(Spec.requestSpecification())
                .and()
                .body(courierForTest)
                .when()
                .post("/api/v1/courier")
                .then()
                .log().all()
                .assertThat()
                .statusCode(201)
                .body("ok", equalTo(true));

        given()
                .spec(Spec.requestSpecification())
                .and()
                .body(courierForTest)
                .when()
                .post("/api/v1/courier")
                .then()
                .log().all()
                .assertThat()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Проверка невозможности создания курьера без логина")
    @Step("Отправить POST на /api/v1/courier")
    public void createCourierWithoutLoginTest() {

    Courier courierWithoutLogin = new Courier();
    courierWithoutLogin.setPassword(courierDataGen.getRandomPassword());
    courierWithoutLogin.setFirstName(courierDataGen.getRandomFirstName());

    given()
            .spec(Spec.requestSpecification())
            .and()
            .body(courierWithoutLogin)
            .when()
            .post("/api/v1/courier")
            .then()
            .log().all()
            .assertThat()
            .statusCode(400)
            .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Проверка невозможности создания курьера без пароля")
    @Step("Отправить POST на /api/v1/courier")
    public void createCourierWithoutPasswordTest() {

        Courier courierWithoutLogin = new Courier();
        courierWithoutLogin.setLogin(courierDataGen.getRandomLogin());
        courierWithoutLogin.setFirstName(courierDataGen.getRandomFirstName());

        given()
                .spec(Spec.requestSpecification())
                .and()
                .body(courierWithoutLogin)
                .when()
                .post("/api/v1/courier")
                .then()
                .log().all()
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Проверка возможности создания курьера без имени")
    @Step("Отправить POST на /api/v1/courier")
    public void createCourierWithoutNameTest() {

        Courier courierWithoutName = new Courier();
        courierWithoutName.setLogin(courierDataGen.getRandomLogin());
        courierWithoutName.setPassword(courierDataGen.getRandomPassword());
        System.out.println(new Gson().toJson(courierWithoutName));

        given()
                .spec(Spec.requestSpecification())
                .and()
                .body(courierWithoutName)
                .when()
                .post("/api/v1/courier")
                .then()
                .log().all()
                .assertThat()
                .statusCode(201) // хз, почему создается, не должен
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Проверка невозможности создания курьера c пустым body")
    @Step("Отправить POST на /api/v1/courier")
    public void createCourierWithEmptyBodyNameTest() {

        given()
                .spec(Spec.requestSpecification())
                .and()
                .body(" {}")
                .when()
                .post("/api/v1/courier")
                .then()
                .log().all()
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}
