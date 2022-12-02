package com.example.personalaccount;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ScheduleFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_schedule, container, false);
        Map<String, String>p=new HashMap<>();
        VolleyAction action=new VolleyAction("https://iat.10store.ru/akwei/schedule.php", p, getContext());
        action.execute(new ServerCallback() {
            @Override
            public void onSuccess(String result) {
                RecyclerView rv=view.findViewById(R.id.rv);
                LinearLayoutManager layout =new LinearLayoutManager(getContext());
                layout.setOrientation(RecyclerView.VERTICAL);
                rv.setLayoutManager(layout);
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Schedule>>(){}.getType();
                List<Schedule> posts = gson.fromJson(result, listType);
                ScheduleAdapter adapter=new ScheduleAdapter(getContext(), posts);
                rv.setAdapter(adapter);
            }
        });
        return view;
    }
}