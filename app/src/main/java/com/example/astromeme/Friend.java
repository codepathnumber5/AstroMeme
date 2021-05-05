package com.example.astromeme;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

@ParseClassName("Friend")
public class Friend extends ParseObject {

public static final String KEY_NAME = "username";
public static final String KEY_DATE = "birthday";
public static final String KEY_ZODIAC = "zodiac";
public static final String ID = "User";

public Friend(){
    super();
}

public void setUser(ParseUser user){
    put("User",user);
}

public String getKeyName(){
    return getString(KEY_NAME);
}

public void setKeyName(String name){
    put(KEY_NAME, name);
}

public String getKeyDate(){
    return getString(KEY_DATE);
}

public void setKeyDate(String date){
    put(KEY_DATE, date);
}

public String getKeyZodiac(){
    return getString(KEY_ZODIAC);
}

public void setKeyZodiac(String zodiac){
    put(KEY_ZODIAC, zodiac);
}

}
