package com.example.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomeAdapterUserlist extends RecyclerView.Adapter<com.example.login.ViewHolder> {

    Userlist UserList;
    List<com.example.login.Model> modelList;
    Context context;

    public CustomeAdapterUserlist(com.example.login.Userlist userList, List<com.example.login.Model> modelList) {
        this.UserList = userList;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public com.example.login.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.userslist, parent, false);

        com.example.login.ViewHolder viewHolder = new com.example.login.ViewHolder(itemView);
        viewHolder.setOnClickListener(new com.example.login.ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                UserList.nextActivity(position);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.login.ViewHolder holder, int position) {
        holder.mName.setText(modelList.get(position).getName());
        holder.mPhonenumber.setText(modelList.get(position).getPhoneno());
        holder.mBalance.setText(modelList.get(position).getBalance());
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}
