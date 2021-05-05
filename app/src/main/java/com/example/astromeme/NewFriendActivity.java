package com.example.astromeme;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewFriendActivity extends AppCompatActivity {
    public static final String TAG = "NEWFRIENDACTIVITY";
    private EditText newNameField;
    private Button cancelNewFriend;
    private Button getNewSign;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_friend);
        initDatePicker();

        newNameField = findViewById(R.id.new_name_field);
        cancelNewFriend = findViewById(R.id.cancel_new_friend);
        getNewSign = findViewById(R.id.get_new_sign);
        dateButton = findViewById(R.id.date_button);

        dateButton.setText(getTodaysDate());

        cancelNewFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goMainActivity();
            }
        });

        getNewSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String friendName = newNameField.getText().toString();
                String friendDate = dateButton.getText().toString();
                String friendZodiac = new Astrology().getSign(friendDate);
                /*
                String friendMonth = pickerMonth.getText().toString();
                String friendDay = pickerDay.getText().toString();

                // Can be adjusted later, when decided on date format we will use
                String friendDate = friendDay+"/"+friendMonth;
                String friendZodiac = "generic";
                if (friendName.isEmpty()) {
                    Toast.makeText(NewFriendActivity.this, "Please enter a name", Toast.LENGTH_SHORT).show();
                    return;
                }
                 */
                saveFriend(friendName, friendDate, friendZodiac);
            }
        });
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String monthString = new DateFormatSymbols().getShortMonths()[month];
                String dateString = monthString + " " + dayOfMonth + " " + year;
                dateButton.setText(dateString);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = R.style.Theme_MaterialComponents_Light_Dialog_Alert;
        datePickerDialog = new DatePickerDialog(this,dateSetListener,year,month,day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    public String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String monthString = new DateFormatSymbols().getShortMonths()[month];
        String dateString = monthString + " " + day + " " + year;
        return dateString;
    }
    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

    private void saveFriend(String name, String date, String zodiac){
        Friend friend = new Friend();
        friend.setUser(ParseUser.getCurrentUser());
        friend.setKeyName(name);
        friend.setKeyDate(date);
        friend.setKeyZodiac(zodiac);
        friend.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e!=null){
                    Log.e(TAG, "Error while saving friend ", e);
                    Toast.makeText(NewFriendActivity.this, "Error saving friend ", Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "Friend was saved! ");
                // TODO move to the friend_sign activity with the name and the sign for this friend
                goFriendSign(friend);
            }
        });
    }

    private void goMainActivity(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);

    }

    private void goFriendSign(Friend friend){
        Intent i = new Intent(NewFriendActivity.this, FriendSign.class);
        i.putExtra("friend_object", friend);
        startActivity(i);
    }

}
