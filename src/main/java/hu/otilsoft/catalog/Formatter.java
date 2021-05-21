package hu.otilsoft.catalog;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Formatter {
    private Formatter() {
    }

    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.format(DateTimeFormatter.ofPattern("y-MM-dd hh:mm:ss")) : "";
    }
}
