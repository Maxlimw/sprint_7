package ru.practicum.data;
import org.apache.commons.lang3.RandomStringUtils;


public class CourierDataGen {

    public String getRandomLogin(){
        return RandomStringUtils.randomAlphabetic(5);
    }

    public String getRandomPassword(){
       return RandomStringUtils.randomNumeric(5);
    }

    public String getRandomFirstName() {
        return RandomStringUtils.randomAlphabetic(10);
    }

    public Courier courierGen() {
        Courier newCourier = new Courier();
        newCourier.setLogin(getRandomLogin());
        newCourier.setPassword(getRandomPassword());
        newCourier.setFirstName(getRandomFirstName());
        return newCourier;
    }
}
