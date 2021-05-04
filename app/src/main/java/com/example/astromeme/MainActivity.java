package com.example.astromeme;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    ImageView zodiacImage;
    TextView zodiacText, signText;
    Button friendListBtn, logoutBtn;
    Astrology astrology = new Astrology();
    String sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        //get views
        zodiacImage = findViewById(R.id.zodiacImage);
        zodiacText = findViewById(R.id.zodiacText);
        friendListBtn = findViewById(R.id.friendListBtn);
        logoutBtn = findViewById(R.id.logoutBtn);
        signText = findViewById(R.id.signText);

        ParseUser user = ParseUser.getCurrentUser();

        //gets sign
        Date birthday = user.getDate("dob");
        DateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy");
        String dateString = dateFormat.format(birthday);
        sign = astrology.getSign(dateString);

        signText.setText(sign);

        //sets up image view
        zodiacImage.setImageResource(getSignImage(sign));

        //grabs json
        JSONObject json = astrology.callAztroAPI(sign);
        if (json == null){
            zodiacText.setText("[Error Loading Horoscope]");
        }
        else{
            //grab description data
            try {
                String horoscope = json.getString("description");
                zodiacText.setText(horoscope);
            }
            catch (JSONException e){
                zodiacText.setText("[Error Calling Zodiac API]");
                e.printStackTrace();
            }

        }


        friendListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goFriendList();
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void goFriendList(){
        Intent i = new Intent(this, FriendList.class);
        startActivity(i);
    }


    private void logout(){
        ParseUser.logOutInBackground();
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    private int getSignImage(String sign){
        if (sign.equals("Aquarius")) return R.drawable.aquarius;
        else if (sign.equals("Aries")) return R.drawable.aries;
        else if (sign.equals("Cancer")) return R.drawable.cancer;
        else if (sign.equals("Capricorn")) return R.drawable.capricorn;
        else if (sign.equals("Gemini")) return R.drawable.gemini;
        else if (sign.equals("Leo")) return R.drawable.leo;
        else if (sign.equals("Libra")) return R.drawable.libra;
        else if (sign.equals("Pisces")) return R.drawable.pices;
        else if (sign.equals("Sagittarius")) return R.drawable.sagitarius;
        else if (sign.equals("Scorpio")) return R.drawable.scorpio;
        else if (sign.equals("Taurus")) return R.drawable.taurus;
        else if (sign.equals("Virgo")) return R.drawable.virgo;
        return 0;
    }

}
