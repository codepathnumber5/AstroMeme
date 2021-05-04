package com.example.astromeme;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Astrology {

    public static final String TAG = "ASTROLOGY";
    private JSONObject returnJson;
    public Astrology() {

    }

    public String getSign(String dateString) {

        //String[] values = date.split("");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
            /*
            Date date = new SimpleDateFormat("MMM dd yyyy").parse(dateString);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int month = cal.get(Calendar.MONTH);
            Log.i(TAG,String.valueOf(month));
            int day = cal.get(Calendar.DAY_OF_MONTH);

             */

        MonthDay md = MonthDay.parse(dateString, dateTimeFormatter);//MonthDay.of(month,day);

        String sign = "";
        if (MonthDay.of(Month.MARCH,21).compareTo(md) * md.compareTo(MonthDay.of(Month.APRIL,19)) >= 0) {
            sign = "Aries";
        }
        else if (MonthDay.of(Month.APRIL,20).compareTo(md) * md.compareTo(MonthDay.of(Month.MAY,20)) >= 0) {
            sign = "Taurus";
        }
        else if (MonthDay.of(Month.MAY,21).compareTo(md) * md.compareTo(MonthDay.of(Month.JUNE,20)) >= 0) {
            sign = "Gemini";
        }
        else if (MonthDay.of(Month.JUNE,21).compareTo(md) * md.compareTo(MonthDay.of(Month.JULY,22)) >= 0) {
            sign = "Cancer";
        }
        else if (MonthDay.of(Month.JULY,23).compareTo(md) * md.compareTo(MonthDay.of(Month.AUGUST,22)) >= 0) {
            sign = "Leo";
        }
        else if (MonthDay.of(Month.AUGUST,23).compareTo(md) * md.compareTo(MonthDay.of(Month.SEPTEMBER,22)) >= 0) {
            sign = "Virgo";
        }
        else if (MonthDay.of(Month.SEPTEMBER,23).compareTo(md) * md.compareTo(MonthDay.of(Month.OCTOBER,22)) >= 0) {
            sign = "Libra";
        }
        else if (MonthDay.of(Month.OCTOBER,23).compareTo(md) * md.compareTo(MonthDay.of(Month.NOVEMBER,21)) >= 0) {
            sign = "Scorpio";
        }
        else if (MonthDay.of(Month.NOVEMBER,22).compareTo(md) * md.compareTo(MonthDay.of(Month.DECEMBER,21)) >= 0) {
            sign = "Sagittarius";
        }
        else if (MonthDay.of(Month.DECEMBER,22).compareTo(md) * md.compareTo(MonthDay.of(Month.JANUARY,19)) >= 0) {
            sign = "Capricorn";
        }
        else if (MonthDay.of(Month.JANUARY,20).compareTo(md) * md.compareTo(MonthDay.of(Month.FEBRUARY,18)) >= 0) {
            sign = "Aquarius";
        }
        else if (MonthDay.of(Month.FEBRUARY,19).compareTo(md) * md.compareTo(MonthDay.of(Month.MARCH,20)) >= 0) {
            sign = "Pisces";
        }

        return sign;

    }

    public JSONObject callAztroAPI(String sign){
        OkHttpClient client = new OkHttpClient();
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Callable<JSONObject> callable = new Callable<JSONObject>() {
            @Override
            public JSONObject call() throws Exception {
                try{
                    RequestBody emptyBody = RequestBody.create(null, new byte[0]);
                    Request request = new Request.Builder()
                            .url(String.format("https://sameer-kumar-aztro-v1.p.rapidapi.com/?sign=%s&day=today", sign))
                            .post(emptyBody)
                            .addHeader("x-rapidapi-key", BuildConfig.AZTRO_API_KEY)
                            .addHeader("x-rapidapi-host", "sameer-kumar-aztro-v1.p.rapidapi.com")
                            .build();

                    Response response = client.newCall(request).execute();
                    String JsonData = response.body().string();
                    JSONObject jsonObject = new JSONObject(JsonData);
                    Log.v(TAG, "JSON IS : " + jsonObject.toString());
                    return jsonObject;

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        Future<JSONObject> future = executor.submit(callable);

        try {
            return future.get();
        }
        catch (ExecutionException e){
            e.printStackTrace();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }

        return null;
    }







}
