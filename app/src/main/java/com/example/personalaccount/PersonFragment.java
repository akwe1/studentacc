package com.example.personalaccount;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PersonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PersonFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PersonFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PersonFragment newInstance(String param1, String param2) {
        PersonFragment fragment = new PersonFragment();
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
    String group;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_person, container, false);
        Map<String, String> p=new HashMap<>();
        VolleyAction action=new VolleyAction("https://iat.10store.ru/akwei/index.php", p, getContext());
        action.execute(new ServerCallback() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Student>>(){}.getType();
                List<Student> posts = gson.fromJson(result, listType);
                Student student=posts.get(0);
                TextView info=view.findViewById(R.id.info);
                info.setText("ФИО: "+student.getSurname()+" "+student.getFirstName()+" "+
                        student.getFatherName()+"\n"+"Дата рождения: "+student.getBirthDate()+"\n"+
                        "Номер курса: "+student.getCourseNumber()+"\n"+"Группа: "+student.getName()+"");
                ImageView avatar=view.findViewById(R.id.avatar);
                Picasso.get().load("https://iat.10store.ru/akwei/avatars/"+student.getAvatar()).into(avatar);
                group = student.getName();
                Map<String, String> p1=new HashMap<>();
                p1.put("group", group);
                VolleyAction action1=new VolleyAction("https://iat.10store.ru/akwei/tutors.php", p1, getContext());
                action1.execute(new ServerCallback() {
                    @Override
                    public void onSuccess(String result) {
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<Tutor>>(){}.getType();
                        List<Tutor> posts = gson.fromJson(result, listType);
                        Tutor tutor=posts.get(0);
                        TextView infotutor=view.findViewById(R.id.infotutor);
                        infotutor.setText("Фамилия: "+tutor.getSurname()+"\n"+"Имя: "+tutor.getFirstName()+"\n"+
                                "Отчество: "+tutor.getFatherName()+"\n\n"+"Номер телефона: "+tutor.getPhoneNumber()+"\n");
                    }
                });
            }
        });


        return view;
    }
}