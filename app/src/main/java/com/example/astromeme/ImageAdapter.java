package com.example.astromeme;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    public Context mContext;

    //TODO: get new images
    public int[] imageArray = {
            R.drawable.cat //used as example only
            , R.drawable.evil_girl
            , R.drawable.fish
            , R.drawable.general
            , R.drawable.girl_turns
            , R.drawable.jackie
            , R.drawable.lisa
            , R.drawable.lisa_sad
            , R.drawable.man
            , R.drawable.many_harold
            , R.drawable.nose_dude
            , R.drawable.sad_dog
            , R.drawable.screaming
            , R.drawable.spiderman
            , R.drawable.windowlife

    };

    public ImageAdapter(Context mContext){
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return imageArray.length;
    }

    @Override
    public Object getItem(int position) {
        return imageArray[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(imageArray[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(340, 350));

        return imageView;
    }
}
