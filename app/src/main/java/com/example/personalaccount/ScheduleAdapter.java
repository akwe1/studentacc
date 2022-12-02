package com.example.personalaccount;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    private List<Schedule> mData;
    private LayoutInflater mInflater;
    Context context;
    ScheduleAdapter(Context context, List<Schedule> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context=context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_schedule, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Schedule schedule=mData.get(position);
        holder.date.setText(schedule.getDate());
        Map<String, String> p=new HashMap<>();
        p.put("id", schedule.getId()+"");
        VolleyAction action=new VolleyAction("https://iat.10store.ru/akwei/subjects.php", p, context);
        action.execute(new ServerCallback() {
            @Override
            public void onSuccess(String result) {
                LinearLayoutManager layout =new LinearLayoutManager(context);
                layout.setOrientation(RecyclerView.VERTICAL);
                holder.rv.setLayoutManager(layout);
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Subject>>(){}.getType();
                List<Subject> posts = gson.fromJson(result, listType);
                SubjectAdapter adapter=new SubjectAdapter(context, posts);
                holder.rv.setAdapter(adapter);
            }
        });
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rv;
        TextView date;
        ViewHolder(View itemView) {
            super(itemView);
            rv=itemView.findViewById(R.id.rv);
            date=itemView.findViewById(R.id.date);
        }
    }
}