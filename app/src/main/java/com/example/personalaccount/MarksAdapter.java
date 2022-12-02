package com.example.personalaccount;

import static android.graphics.Color.parseColor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MarksAdapter extends RecyclerView.Adapter<MarksAdapter.ViewHolder> {

    private List<Mark> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    MarksAdapter(Context context, List<Mark> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_mark, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Mark mark=mData.get(position);
        holder.mark.setText(String.valueOf(mark.getMark()));
        holder.subject.setText(String.valueOf(mark.getName()));
        switch (mark.getMark()){
            case 0: //Н
                holder.mark.setBackgroundResource(R.drawable.redbg);
                holder.mark.setText("Н");
                // holder.mark.setTextColor(parseColor("#d6211d"));
                break;
            case 2: //Оценка 2
                holder.mark.setBackgroundResource(R.drawable.redbg);
                //holder.mark.setTextColor(parseColor("#df291e"));
                break;
            case 3: //Оценка 3
                holder.mark.setBackgroundResource(R.drawable.orangebg);
                //holder.mark.setTextColor(parseColor("#e5a53f"));
            break;
            case 4: //Оценка 4
                holder.mark.setBackgroundResource(R.drawable.bluebg);
                //holder.mark.setTextColor(parseColor("#1e50dd"));
            break;
            case 5: //Оценка 5
                holder.mark.setBackgroundResource(R.drawable.greenbg);
                //holder.mark.setTextColor(parseColor("#1bc71b"));
                break;
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView subject;
        TextView mark;

        ViewHolder(View itemView) {
            super(itemView);
            subject = itemView.findViewById(R.id.subject);
            mark = itemView.findViewById(R.id.mark);
        }
    }
}