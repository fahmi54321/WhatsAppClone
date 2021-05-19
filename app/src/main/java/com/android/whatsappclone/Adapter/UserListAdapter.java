package com.android.whatsappclone.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.whatsappclone.Model.UserObject;
import com.android.whatsappclone.R;

import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserListRecyclerViewHolder> {

    ArrayList<UserObject> userObjects;

    public UserListAdapter(ArrayList<UserObject> userObjects) {
        this.userObjects = userObjects;
    }

    @NonNull
    @Override
    public UserListRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, null, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(layoutParams);

        UserListRecyclerViewHolder recyclerViewHolder = new UserListRecyclerViewHolder(layoutView);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserListRecyclerViewHolder holder, int position) {
        holder.name.setText(userObjects.get(position).getName());
        holder.phone.setText(userObjects.get(position).getPhone());
    }

    @Override
    public int getItemCount() {
        return userObjects.size();
    }

    public class UserListRecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView name, phone;

        public UserListRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            phone = itemView.findViewById(R.id.phone);

        }
    }
}
