package utils;

import java.util.Random;
import java.util.UUID;

public class TestDataRandom {

    public static String randomCustomerId() {
        return UUID.randomUUID().toString();
    }

    public static Integer randomRentId() {
        return new Random().nextInt(Integer.MAX_VALUE);
    }

    public static Integer randomLeaseId() {
        return new Random().nextInt(Integer.MAX_VALUE);
    }

    public static String randomCarId() {
        return UUID.randomUUID().toString();
    }
}
