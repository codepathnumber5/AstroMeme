package com.example.astromeme;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.MonthDay;
import java.util.Calendar;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Astrology {
    public Astrology() {

    }

    public String getSign(String dateString) {

        //String[] values = date.split("");
        try {
            Date date = new SimpleDateFormat("MMM dd yyyy").parse(dateString);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
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
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    private JSONObject callAztroAPI(String sign) {
        OkHttpClient client = new OkHttpClient();

        try {
            Request request = new Request.Builder()
                    .url(String.format("https://sameer-kumar-aztro-v1.p.rapidapi.com/?sign=%s&day=today", sign))
                    .post(null)
                    .addHeader("x-rapidapi-key", BuildConfig.AZTRO_API_KEY)
                    .addHeader("x-rapidapi-host", "sameer-kumar-aztro-v1.p.rapidapi.com")
                    .build();

            Response response = client.newCall(request).execute();
            String JsonData = response.body().string();
            JSONObject jsonObject = new JSONObject(JsonData);
            return jsonObject;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }




}
