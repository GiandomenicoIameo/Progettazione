package org.example.demo.view.support;

import java.time.*;
import java.time.temporal.ChronoUnit;

public class Date {
    private final long day;
    private final long month;
    private final long year;

    private final long hour;
    private final long minute;

    public Date(LocalDateTime utc) {
        LocalDateTime now = LocalDateTime
                .now(ZoneOffset.UTC);

        year   = ChronoUnit.
                YEARS.between(utc, now);
        month  = ChronoUnit
                .MONTHS.between(utc, now);
        day    = ChronoUnit
                .DAYS.between(utc, now);
        hour   = ChronoUnit
                .HOURS.between(utc, now);
        minute = ChronoUnit
                .MINUTES.between(utc, now);
    }

    public String toString(long n, String one, String more) {
        return n + " " + (n == 1 ? one : more);
    }

    public String year(long n) {
        return toString(n, "year",
                "years") + " ago";
    }

    public String month(long n) {
        return toString(n, "month",
                "months") + " ago";
    }

    public String day(long n) {
        return toString(n, "day",
                "days") + " ago";
    }

    public String hour(long n) {
        return toString(n, "hour",
                "hours") + " ago";
    }

    public String minute(long n) {
        return toString(n, "minute",
                "minutes") + " ago";
    }

    public String week(long n) {
        return toString(n, "week",
                "weeks") + " ago";
    }

    @Override
    public String toString() {
        if(this.year > 0)
            return year(this.year);
        if(this.month > 0)
            return month(this.month);
        if(this.day >= 7)
            return week(this.day / 7);
        if(this.day > 0)
            return day(this.day);
        if(this.hour > 0)
            return hour(this.hour);
        if(this.minute > 0)
            return minute(this.minute);
        return "Just now";
    }
}
