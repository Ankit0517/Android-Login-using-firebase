package com.example.login;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView mName, mPhonenumber, mBalance, mRupee, mRupeeslash, mName1, mName2, mDate, mTransc_status;
    ImageView mPhone, mArrow;
    View mView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;

        mName = itemView.findViewById(R.id.username);
        mPhonenumber = itemView.findViewById(R.id.userphonenumber);
        mBalance = itemView.findViewById(R.id.balance);
        mRupee = itemView.findViewById(R.id.rupee);
        mRupeeslash = itemView.findViewById(R.id.rupeeslash);
        mPhone = itemView.findViewById(R.id.phone);


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mClickListener.onItemClick(view, getAdapterPosition());
            }
        });

    }
    private com.example.login.ViewHolder.ClickListener mClickListener;
    public interface  ClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnClickListener(com.example.login.ViewHolder.ClickListener clickListener){
       mClickListener = clickListener;
    }
}
