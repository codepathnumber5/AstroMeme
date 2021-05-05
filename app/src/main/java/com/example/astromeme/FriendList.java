package com.example.astromeme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class FriendList extends AppCompatActivity {

    public static final String TAG = "FriendList";
    List<Friend> allFriends;
    FriendAdapter friendAdapter;
    Button addFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_list);

        addFriend = findViewById(R.id.add_friend);
        RecyclerView rvFriends = findViewById(R.id.friends);
        allFriends = new ArrayList<>();

        queryFriends();
        // Create an adapter
        friendAdapter = new FriendAdapter(FriendList.this, allFriends);

        // Set the adapter on to recycler view
        rvFriends.setAdapter(friendAdapter);

        // Set layout manager on to recycler view
        rvFriends.setLayoutManager(new LinearLayoutManager(FriendList.this));

        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNewFriendActivity();
            }
        });

        //TODO something else here to get friends from database??


    }

    private void queryFriends(){
        ParseQuery<Friend> query = ParseQuery.getQuery(Friend.class);
        ParseObject user = ParseUser.getCurrentUser();
        query.whereEqualTo("User", user);
        query.include(Friend.KEY_NAME);
        query.findInBackground(new FindCallback<Friend>() {
            @Override
            public void done(List<Friend> friends, ParseException e) {
                if(e!=null){
                    Log.e(TAG, "Issue with getting friends", e);
                    return;
                }
                Log.i(TAG, "All: "+friends);
                allFriends.addAll(friends);
                friendAdapter.notifyDataSetChanged();
            }
        });
    }

    private void goToNewFriendActivity() {
        Intent i = new Intent(this, NewFriendActivity.class);
        startActivity(i);
    }
}