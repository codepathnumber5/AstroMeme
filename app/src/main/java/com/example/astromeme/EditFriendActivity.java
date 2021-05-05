package com.example.astromeme;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditFriendActivity extends AppCompatActivity {
    public static final String TAG = "EDITFRIENDACTIVITY";
    private Friend friend;

    private Button cancel;
    private Button getSign;
    private EditText editNameField;
    private Button editFriendDateButton;
    private Button editSave;
    private DatePickerDialog datePickerDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_friend);
        initDatePicker();

        friend = getIntent().getParcelableExtra("friend_object");

        cancel = findViewById(R.id.cancel_edit_friend);
        getSign = findViewById(R.id.get_edit_sign);
        editNameField = findViewById(R.id.edit_name_field);
        editFriendDateButton = findViewById(R.id.edit_friend_date_button);
        editSave = findViewById(R.id.edit_save);


        editNameField.setText(friend.getKeyName());
        editFriendDateButton.setText(friend.getKeyDate());

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFriendList();
            }
        });

        getSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGetSign(friend);
            }
        });

        editSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String friendName = editNameField.getText().toString();
                String friendDate = editFriendDateButton.getText().toString();
                String friendZodiac = new Astrology().getSign(friendDate);

                updateFriend(friendName, friendDate, friendZodiac);
            }
        });

    }

    public String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("MMM dd yyyy");
        String dateString = format.format(cal.getTime());
        //int year = cal.get(Calendar.YEAR);
        //int month = cal.get(Calendar.MONTH);
        //int day = cal.get(Calendar.DAY_OF_MONTH);
        //String monthString = new DateFormatSymbols().getShortMonths()[month];
        //String dateString = monthString + " " + day + " " + year;
        return dateString;
    }
    public void openEditDatePicker(View view) {
        datePickerDialog.show();
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat format = new SimpleDateFormat("MMM dd yyyy");
                String dateString = format.format(cal.getTime());
                //String monthString = new DateFormatSymbols().getShortMonths()[month];

                //String dateString = monthString + " " + dayOfMonth + " " + year;
                editFriendDateButton.setText(dateString);
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

    private void updateFriend(String name, String date, String zodiac) {
        friend.setKeyDate(date);
        friend.setKeyName(name);
        friend.setKeyZodiac(zodiac);
        friend.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e!=null) {
                    Log.e(TAG, "Error while updating friend ", e);
                    Toast.makeText(EditFriendActivity.this, "Error updating friend ", Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "Friend was updated");
                Toast.makeText(EditFriendActivity.this, "Friend was successfully updated", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void goToGetSign(Friend friend) {
        Intent i = new Intent(this, FriendSign.class);
        i.putExtra("friend_object", friend);
        startActivity(i);
    }

    private void goToFriendList() {
        Intent i = new Intent(this, FriendList.class);
        startActivity(i);
    }
}
