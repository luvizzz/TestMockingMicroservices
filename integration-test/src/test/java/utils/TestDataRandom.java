package utils;

import java.util.UUID;

public class TestDataRandom {

    public static String randomCustomerId() {
        return UUID.randomUUID().toString();
    }

    public static String randomCarId() {
        return UUID.randomUUID().toString();
    }
}
