package com.example.astromeme;

import com.parse.Parse;
import com.parse.ParseObject;

import android.app.Application;

public class ParseApplication extends Application {

    // Initializes Parse SDK as soon as the application is created
    @Override
    public void onCreate() {
        super.onCreate();

        //Register Friend Parse Object
        ParseObject.registerSubclass(Friend.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("xcF1xYEmcBGVuyfatUYLkqOgAOUFZr7QdOuq1gAd")
                .clientKey("cElogXFcicIAA8jm6VKWzoSyOu6EwM1U58ND72Kc")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}