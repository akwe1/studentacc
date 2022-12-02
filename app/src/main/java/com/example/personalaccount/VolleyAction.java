package com.example.personalaccount;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.Map;


public class VolleyAction {
    String requestUrl;
    Map<String, String> postMap;
    Context context;
    ServerCallback callback;
    Thread thread;
    public VolleyAction(String requestUrl, Map<String, String> postMap, Context context) {
        this.postMap = postMap;
        this.requestUrl = requestUrl;
        this.context = context;
    }
    private final Runnable doBackgroundThreadProcessing = new Runnable() {
        public void run() {
            backgroundThreadProcessing(callback);
        }
    };
    private void backgroundThreadProcessing(final ServerCallback callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("VolleyResponse", response);
                callback.onSuccess(response);
                if (thread != null) {
                    Thread dummy = thread;
                    thread = null;
                    dummy.interrupt();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if(!requestUrl.equals("https://iat.10store.ru/akwei/login.php")){
                    SharedPreferences preferences=context.getSharedPreferences("sPref", Context.MODE_PRIVATE);
                    postMap.put("login", preferences.getString("login", ""));
                    postMap.put("password", preferences.getString("password", ""));
                }
                return postMap;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(stringRequest);
    }
    public void execute(final ServerCallback callback) {
        this.callback=callback;
            thread = new Thread(null, doBackgroundThreadProcessing,
                    "Background");
            thread.setPriority(10);
            thread.start();
    }
}