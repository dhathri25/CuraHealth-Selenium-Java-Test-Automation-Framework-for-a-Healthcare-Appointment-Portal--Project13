
package com.srm.curahealth.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class DateUtil {

    private static final DateTimeFormatter APP_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private DateUtil() {
    }

    public static String futureDate(int daysAhead) {
        return LocalDate.now().plusDays(daysAhead).format(APP_DATE_FORMAT);
    }

    public static String pastDate(int daysBehind) {
        return LocalDate.now().minusDays(daysBehind).format(APP_DATE_FORMAT);
    }
}
