package com.example.astromeme;

import java.time.Month;
import java.time.MonthDay;

public class Astrology {
    public Astrology() {

    }

    public String getSign(int month, int day) {
        MonthDay md = MonthDay.of(month,day);
        String sign = "";
        if (MonthDay.of(3,21).compareTo(md) * md.compareTo(MonthDay.of(4,19)) >= 0) {
            sign = "Aries";
        }
        else if (MonthDay.of(4,20).compareTo(md) * md.compareTo(MonthDay.of(5,20)) >= 0) {
            sign = "Taurus";
        }
        else if (MonthDay.of(5,21).compareTo(md) * md.compareTo(MonthDay.of(6,20)) >= 0) {
            sign = "Gemini";
        }
        else if (MonthDay.of(6,21).compareTo(md) * md.compareTo(MonthDay.of(7,22)) >= 0) {
            sign = "Cancer";
        }
        else if (MonthDay.of(7,23).compareTo(md) * md.compareTo(MonthDay.of(8,22)) >= 0) {
            sign = "Leo";
        }
        else if (MonthDay.of(8,23).compareTo(md) * md.compareTo(MonthDay.of(9,22)) >= 0) {
            sign = "Virgo";
        }
        else if (MonthDay.of(9,23).compareTo(md) * md.compareTo(MonthDay.of(10,22)) >= 0) {
            sign = "Libra";
        }
        else if (MonthDay.of(10,23).compareTo(md) * md.compareTo(MonthDay.of(11,21)) >= 0) {
            sign = "Scorpio";
        }
        else if (MonthDay.of(11,22).compareTo(md) * md.compareTo(MonthDay.of(12,21)) >= 0) {
            sign = "Sagittarius";
        }
        else if (MonthDay.of(12,22).compareTo(md) * md.compareTo(MonthDay.of(1,19)) >= 0) {
            sign = "Capricorn";
        }
        else if (MonthDay.of(1,20).compareTo(md) * md.compareTo(MonthDay.of(2,18)) >= 0) {
            sign = "Aquarius";
        }
        else if (MonthDay.of(2,19).compareTo(md) * md.compareTo(MonthDay.of(3,20)) >= 0) {
            sign = "Pisces";
        }

        return sign;
    }

    

}
