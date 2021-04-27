package com.example.astromeme;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.OutputStream;

public class FriendMeme extends AppCompatActivity {
    public static final String TAG = "FriendMeme";
    int GRID_ACTIVITY_CODE = 1;
    private Button sendMemeBtn, loadImageBtn;
    private EditText topText, bottomText;
    private ImageView memeImage;
    private TextView topTextView, bottomTextView;
    ImageAdapter imageAdapter;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_meme);

        imageAdapter = new ImageAdapter(this);

        loadImageBtn = findViewById(R.id.loadImageBtn);
        sendMemeBtn = findViewById(R.id.sendMemeBtn);
        topText = findViewById(R.id.topText);
        bottomText = findViewById(R.id.bottomText);
        memeImage = findViewById(R.id.memeImage);
        topTextView = findViewById(R.id.topTextView);
        bottomTextView = findViewById(R.id.bottomTextView);
        relativeLayout = findViewById(R.id.relativeLayout);

        //allows the editTexts to edit the textViews in real time
        topText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                topTextView.setText(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        bottomText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bottomTextView.setText(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        loadImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FriendMeme.this, ImageGrid.class);
                startActivityForResult(intent, GRID_ACTIVITY_CODE);
            }
        });

        //will get bitmap from relativeLayout, convert to PNG, and send it
        sendMemeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = createBitmap(relativeLayout);

                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "title");
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/PNG");
                Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                OutputStream outputStream;
                try {
                    outputStream = getContentResolver().openOutputStream(uri);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    outputStream.close();
                } catch (Exception e){
                    Log.e(TAG, "Error in sendMemeBtn");
                }

                String message = "Meme created using AstroMeme!";
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Astrology Meme!");
                intent.putExtra(Intent.EXTRA_TEXT, message);
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                intent.setType("image/PNG");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(intent, "Send to:"));
            }
        });
    }



    public static Bitmap createBitmap(View view){
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //result from going to the image grid to choose a background image for the meme
        if(requestCode == GRID_ACTIVITY_CODE){
            //user decided to use an image as a background
            if(resultCode == Activity.RESULT_OK){
                int position = data.getIntExtra("position", -1);
                if (position == -1){
                    return;
                }

                Log.v(TAG, "We got position: " + position);
                memeImage.setImageResource(imageAdapter.imageArray[position]);
            }
            else{
                //user backed out
            }

        }
    }
}
