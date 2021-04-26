package com.example.astromeme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Main activity";
    List<Friend> friends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_list);

        RecyclerView rvFriends = findViewById(R.id.friends);
        friends = new ArrayList<>();

        // Create an adapter
        FriendAdapter friendAdapter = new FriendAdapter(this, friends);

        // Set the adapter on to recycler view
        rvFriends.setAdapter(friendAdapter);

        // Set layout manager on to recycler view
        rvFriends.setLayoutManager(new LinearLayoutManager(this));

        //TODO something else here to get friends from database??
    }


}