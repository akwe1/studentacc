package com.example.personalaccount;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView button;
        button = findViewById(R.id.buttonlogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setEnabled(false);
                EditText login=findViewById(R.id.edittextlogin);
                EditText password=findViewById(R.id.edittextpass);
                Map<String, String> map=new HashMap<>();
                map.put("login", login.getText().toString());
                map.put("password", password.getText().toString());
                VolleyAction action=new VolleyAction("https://iat.10store.ru/akwei/login.php",
                        map, MainActivity.this);
                action.execute(new ServerCallback() {
                    @Override
                    public void onSuccess(String result) {
                        view.setEnabled(true);
                        if(result.equals("1")){
                            SharedPreferences preferences=getSharedPreferences("sPref", MODE_PRIVATE);
                            SharedPreferences.Editor editor=preferences.edit();
                            editor.putString("login", login.getText().toString());
                            editor.putString("password", password.getText().toString());
                            editor.apply();
                            startActivity(new Intent(MainActivity.this, PersonalActivity.class));
                            finish();
                        }
                        else {
                            Toast.makeText(MainActivity.this,
                                    "Неправильный логин или пароль", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}