package ru.javawebinar.topjava.util;

import org.springframework.cglib.core.Local;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T value, T start, T end) {
        return (start == null || value.compareTo(start) >= 0) && (end == null || value.compareTo(end) < 0);
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static LocalDate parseLocalDate(String date) {
        return StringUtils.isEmpty(date) ? null : LocalDate.parse(date);
    }

    public static LocalTime parseLocalTime(String time) {
        return StringUtils.isEmpty(time) ? null : LocalTime.parse(time);
    }
}

