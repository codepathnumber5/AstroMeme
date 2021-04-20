package com.example.astromeme;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";
    private EditText userId;
    private EditText pswd;
    private Button logInBtn;
    private Button initSignUpBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        userId = findViewById(R.id.userId);
        pswd = findViewById(R.id.pswd);
        logInBtn = findViewById(R.id.logInBtn);
        initSignUpBtn = findViewById(R.id.initSignUpBtn);

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "login button pressed");
                String username = userId.getText().toString();
                String password = pswd.getText().toString();
                loginUser(username, password);
            }
        });

        initSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "login button pressed");
                goSignUpActivity();
            }
        });

    }

    private void loginUser(String username, String password){
        Log.i(TAG, "attempting to log in user");
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {

                if (e != null){ //something went wrong
                    Log.e(TAG, "Issue with login: ", e);
                    return;
                }

                //go to main activity is login is successful
                goMainActivity();
                Toast.makeText(LoginActivity.this, "Successful Login!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goMainActivity(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void goSignUpActivity() {
        Intent i = new Intent(this, SignUpActivity.class);
        startActivity(i);
    }
}
