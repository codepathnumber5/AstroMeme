package com.example.astromeme;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.SaveCallback;

import java.util.Calendar;

public class NewFriendActivity extends AppCompatActivity {
    public static final String TAG = "NEWFRIENDACTIVITY";
    private EditText newNameField;
    private EditText pickerMonth;
    private EditText pickerDay;
    private Button cancelNewFriend;
    private Button getNewSign;

    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_friend);

        newNameField = findViewById(R.id.new_name_field);
        cancelNewFriend = findViewById(R.id.cancel_new_friend);
        getNewSign = findViewById(R.id.get_new_sign);
        pickerMonth = findViewById(R.id.picker_month);
        pickerDay = findViewById(R.id.picker_day);

        Calendar calendar = Calendar.getInstance();
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);



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
                String friendMonth = pickerMonth.getText().toString();
                String friendDay = pickerDay.getText().toString();

                // Can be adjusted later, when decided on date format we will use
                String friendDate = friendDay+"/"+friendMonth;
                String friendZodiac = "generic";
                if (friendName.isEmpty()) {
                    Toast.makeText(NewFriendActivity.this, "Please enter a name", Toast.LENGTH_SHORT).show();
                    return;
                }
                saveFriend(friendName, friendDate, friendZodiac);
            }
        });
    }

    private void saveFriend(String name, String date, String zodiac){
        Friend friend = new Friend();
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
                goFriendSign();
            }
        });
    }

    private void goMainActivity(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);

    }

    private void goFriendSign(){
        Intent i = new Intent(NewFriendActivity.this, FriendSign.class);
        startActivity(i);
    }

}
