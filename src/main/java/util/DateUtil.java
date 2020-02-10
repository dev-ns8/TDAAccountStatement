package util;

import java.time.LocalDate;

public class DateUtil {

    private DateUtil() {
    }

    public static LocalDate getThreeMonthsAgo() {
        LocalDate today = LocalDate.now();
        return today.minusMonths(3L);
    }

    public static LocalDate getOneYearAgo() {
        LocalDate today = LocalDate.now();
        return today.minusYears(1L);
    }
}
