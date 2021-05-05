package com.example.astromeme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FriendSign extends AppCompatActivity {
    public static final String TAG = "FRIENDSIGN";

    private TextView gotSign;
    private TextView gotName;
    private ImageView gotSignPic;
    private Button homeSign;
    private Button getMemeButton;
    private Button hamburger;

    private Friend friend;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_sign);

        friend = getIntent().getParcelableExtra("friend_object");

        gotSign = findViewById(R.id.got_sign);
        gotName = findViewById(R.id.got_name);
        gotSignPic = findViewById(R.id.got_sign_pic);

        gotSign.setText(friend.getKeyZodiac());
        gotName.setText(friend.getKeyName());
        gotSignPic.setImageResource(getSignImage(friend.getKeyZodiac()));

        homeSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivity();
            }
        });

        getMemeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFriendMemeActivity();
            }
        });

        hamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToEditFriend();
            }
        });


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

    private void goToMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void goToFriendMemeActivity() {
        Intent i = new Intent(this, FriendMeme.class);
        startActivity(i);
    }

    private void goToEditFriend() {

    }
}
