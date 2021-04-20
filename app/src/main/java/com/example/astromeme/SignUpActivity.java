package com.example.astromeme;

import android.app.Application;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SignUpActivity extends AppCompatActivity {
    public static final String TAG = "SignUpActivity";
    private EditText userId;
    private TextView dateBirth;
    private EditText email;
    private EditText pswd;
    private EditText confPswd;
    private Button signUpBtn;

    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        userId = findViewById(R.id.userId);
        dateBirth = findViewById(R.id.dateBirth);
        email = findViewById(R.id.email);
        pswd = findViewById(R.id.pswd);
        confPswd = findViewById(R.id.confPswd);
        signUpBtn = findViewById(R.id.signUpBtn);

        //allows user to click text and choose their birthday
        dateBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        SignUpActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = month + "/" + dayOfMonth + "/" + year;
                Log.d(TAG, "onDateSet: date: " + date);
                dateBirth.setText(date);
            }
        };

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "sign up button pressed");
                String user_id = userId.getText().toString();
                String user_dob = dateBirth.getText().toString();
                String user_email = email.getText().toString();
                String user_pswd = pswd.getText().toString();
                String user_conf = confPswd.getText().toString();

                //check if any fields are empty
                if (user_id.isEmpty()){
                    showToast("Fill in User Name field");
                    emptyPassword();
                    return;
                }
                if (user_dob.isEmpty()){
                    showToast("Enter your Date of Birth");
                    emptyPassword();
                    return;
                }
                if (user_email.isEmpty()){
                    showToast("Fill in Email field");
                    emptyPassword();
                    return;
                }
                if (user_pswd.isEmpty()){
                    showToast("Fill in Password field");
                    emptyPassword();
                    return;
                }
                if (user_conf.isEmpty()){
                    showToast("Fill in Confirm Password field");
                    emptyPassword();
                    return;
                }

                //compare passwords to make sure they are correct
                if (user_pswd.compareTo(user_conf) != 0){
                    showToast("Passwords are not equivalent");
                    emptyPassword();
                    return;
                }

                //set up date object
                Date date = parseDate(user_dob);
                if (date == null){ //in case parseDate encounters an error
                    showToast("Parsing error occured for Date");
                    return;
                }

                //submit and login with parse
                ParseUser user = new ParseUser();
                user.setUsername(user_id);
                user.setPassword(user_pswd);
                user.setEmail(user_email);
                user.put("dob", date);

                user.signUpInBackground(e -> {
                    if (e == null) {
                        // Hooray! Let them use the app now.
                        showToast("Successfully created your account!");
                        goMainActivity();
                    } else {
                        // Sign up didn't succeed. Look at the ParseException
                        // to figure out what went wrong
                        showToast(e.getMessage());
                    }
                });


            }
        });
    }

    public void emptyPassword(){
        pswd.setText("");
        confPswd.setText("");
    }

    public static Date parseDate(String dob){
        //converts month/day/year to yyyy-MM-dd
        String[] arrayOfDob = dob.split("/", 3);
        String month = arrayOfDob[0];
        if (month.length() == 1){
            month = "0" + month;
        }
        String day = arrayOfDob[1];
        if (day.length() == 1){
            day = "0" + day;
        }
        String year = arrayOfDob[2];
        String date = year + "-" + month + "-" + day;

        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (java.text.ParseException e) {
            return null;
        }
    }

    private void goMainActivity(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void showToast(String toast){
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }

}
