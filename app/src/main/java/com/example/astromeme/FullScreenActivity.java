package com.example.astromeme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FullScreenActivity extends AppCompatActivity {
    ImageView imageView;
    Button selectBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_activity);

        imageView = findViewById(R.id.image_view);
        selectBtn = findViewById(R.id.selectBtn);

        Intent i = getIntent();
        int position = i.getExtras().getInt("id");

        ImageAdapter imageAdapter = new ImageAdapter(this);

        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("position", position);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });

        imageView.setImageResource(imageAdapter.imageArray[position]);
    }
}
