package utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateUtils {
    private static final DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");

    public static String formatToRentalServiceDate(DateTime dateTime) {
        return dateTime.toString(format);
    }

    public static String formatToLeaseServiceDate(DateTime dateTime) {
        return dateTime.toString(format);
    }

    public static Boolean datesAreEqual(String date1, String date2) {
        DateTime date1AsDate = DateTime.parse(date1);
        DateTime date2AsDate = DateTime.parse(date2);
        return date1AsDate.isEqual(date2AsDate);
    }
}