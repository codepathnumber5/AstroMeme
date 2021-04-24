package com.example.astromeme;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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


                if (friendName.isEmpty()) {
                    //Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show();
                }
                //TODO go to friend_sign activity
            }
        });
    }

    private void goMainActivity(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }


}
