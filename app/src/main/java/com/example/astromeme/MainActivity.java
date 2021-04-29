package com.example.astromeme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Main activity";
    List<Friend> allFriends;
    FriendAdapter friendAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_list);

        RecyclerView rvFriends = findViewById(R.id.friends);
        allFriends = new ArrayList<>();

        queryFriends();
        // Create an adapter
        friendAdapter = new FriendAdapter(MainActivity.this, allFriends);

        // Set the adapter on to recycler view
        rvFriends.setAdapter(friendAdapter);

        // Set layout manager on to recycler view
        rvFriends.setLayoutManager(new LinearLayoutManager(MainActivity.this));



        //TODO something else here to get friends from database??


    }

    private void queryFriends(){
        ParseQuery<Friend> query = ParseQuery.getQuery(Friend.class);
        query.include(Friend.KEY_ZODIAC);
        query.findInBackground(new FindCallback<Friend>() {
            @Override
            public void done(List<Friend> friends, ParseException e) {
                if(e!=null){
                    Log.e(TAG, "Issue with getting friends", e);
                    return;
                }
                for(Friend friend: friends){
                    Log.i(TAG, "Friend: "+ friend.getKeyName()+ friend.getKeyDate());
                    allFriends.add(friend);
                }
                Log.i(TAG, "All: "+friends);
                allFriends.addAll(friends);
                friendAdapter.notifyDataSetChanged();
            }
        });
    }
}