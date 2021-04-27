package com.example.astromeme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ImageGrid extends AppCompatActivity {
    GridView gridView;
    private static final String TAG = "ImageGrid";
    int REQUEST_CODE_FULLSCREENACTIVITY = 69;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_grid);

        gridView = findViewById(R.id.grid_view);
        gridView.setAdapter(new ImageAdapter(this));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), FullScreenActivity.class);
                intent.putExtra("id", position);
                startActivityForResult(intent, REQUEST_CODE_FULLSCREENACTIVITY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //result from going to the full screen activity to view image and choose it as a background
        if(requestCode == REQUEST_CODE_FULLSCREENACTIVITY){
            //user decided to use an image as a background
            if(resultCode == Activity.RESULT_OK){
                int position = data.getIntExtra("position", -1);
                if (position == -1){
                    return;
                }

                Intent returnIntent = new Intent();
                returnIntent.putExtra("position", position);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
            //user went back
            else{

            }

        }
    }
}
