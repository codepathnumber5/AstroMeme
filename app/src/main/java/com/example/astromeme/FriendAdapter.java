package com.example.astromeme;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {

    Context context;
    List<Friend> friends;

    public FriendAdapter(Context context, List<Friend> friends){
        this.context = context;
        this.friends = friends;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("Friend adapter", "onCreateViewHolder");
        View friendView = LayoutInflater.from(context).inflate(R.layout.friend_list_item, parent, false);
        return new ViewHolder(friendView);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendAdapter.ViewHolder holder, int position) {
        Log.d("FriendAdapter", "onBindViewHolder "+ position);
        Friend friend = friends.get(position);
        holder.bind(friend);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("FriendAdapter", "clicked friend at pos + " + position);
                //Intent i = new Intent(context, FriendSign.class);
                //i.putExtra("friend_object", friend);
                //context.startActivity(i);
            }
        });
        holder.removeFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Friend removedFriend = friends.get(position);

                //removes friend from database
                deleteFriend(removedFriend);

                //remove from list
                friends.remove(position);
                notifyItemRemoved(position);

            }
        });
    }

    private void deleteFriend(Friend friend){
        friend.deleteInBackground(e -> {
           if (e == null){
               Toast.makeText(context, "Delete Successful!", Toast.LENGTH_SHORT).show();
           }
           else{
               Toast.makeText(context, "Delete Unsuccessful: " + e.getMessage(), Toast.LENGTH_SHORT).show();
           }
        });
    }

    // Return total count
    @Override
    public int getItemCount() {
        return friends.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout container;
        ImageView sign;
        TextView name;
        Button removeFriend;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sign = itemView.findViewById(R.id.f_list_sign);
            name = itemView.findViewById(R.id.f_list_name);
            removeFriend = itemView.findViewById(R.id.f_remove);
            container = itemView.findViewById(R.id.friend_container);

            removeFriend.setText("X");

        }

        public void bind(Friend friend) {
            name.setText(friend.getKeyName());

        }
    }
}
