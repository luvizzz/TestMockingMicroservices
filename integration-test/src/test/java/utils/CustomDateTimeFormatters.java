package utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class CustomDateTimeFormatters {
    private static final DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");

    public static String formatToRentalServiceDate(DateTime dateTime) {
        return dateTime.toString(format);
    }

    public static String formatToLeaseServiceDate(DateTime dateTime) {
        return dateTime.toString(format);
    }
}