package com.example.personalaccount;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {

    private List<Subject> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    SubjectAdapter(Context context, List<Subject> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_subject, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Subject subject=mData.get(position);
        switch (position){
            case 0: holder.time.setText("8:30 - 10:00"); break;
            case 1: holder.time.setText("10:10 - 11:40"); break;
            case 2: holder.time.setText("12:10 - 13:40"); break;
        }
        holder.subject.setText(String.valueOf(subject.getName()));
        holder.aud.setText("Аудитория: "+subject.getAudience());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView subject;
        TextView time;
        TextView aud;

        ViewHolder(View itemView) {
            super(itemView);
            subject = itemView.findViewById(R.id.subject);
            aud = itemView.findViewById(R.id.aud);
            time = itemView.findViewById(R.id.time);
        }
    }
}