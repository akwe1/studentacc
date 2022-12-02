package com.example.personalaccount;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MarksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MarksFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MarksFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MarksFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MarksFragment newInstance(String param1, String param2) {
        MarksFragment fragment = new MarksFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_marks, container, false);
        CalendarView calendarView = view.findViewById(R.id.calendarViews);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String selectedDate = (i) + "-" + (i1+1) + "-" + i2 + " ";
                Map<String, String> p=new HashMap<>();
                p.put("date", selectedDate);
                VolleyAction action=new VolleyAction( "https://iat.10store.ru/akwei/marks.php",
                        p, getContext());
                action.execute(new ServerCallback() {
                    @Override
                    public void onSuccess(String result) {
                        RecyclerView rv=view.findViewById(R.id.rv);
                        LinearLayoutManager layout =new LinearLayoutManager(getContext());
                        layout.setOrientation(RecyclerView.VERTICAL);
                        rv.setLayoutManager(layout);
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<Mark>>(){}.getType();
                        List<Mark> posts = gson.fromJson(result, listType);
                        MarksAdapter adapter=new MarksAdapter(getContext(), posts);
                        rv.setAdapter(adapter);
                    }
                });
            }
        });

        return view;
    }


}