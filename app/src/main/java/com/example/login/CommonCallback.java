package com.example.login;


import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

public abstract  class CommonCallback<T>extends StringCallback {


//网络不可连接，超时的error
    @Override
    public void onError(okhttp3.Call call, Exception e, int id) {
        Log.d("CommonCallback",e.getMessage());

        onError(e);
    }

    @Override
    public void onResponse(String response, int id) {
        try {
            Log.d("CommonCallback", "666");
            JSONObject resp =new JSONObject(response);
            String message= resp.getString("message");

            if(true){
            {
                String name = resp.getString("name");
                String sno = resp.getString("sno");


                Log.d("CommonCallback", "name");
            }
            }
            else {
                onError(new RuntimeException(message));
               // Log.d("CommonCallback", "555");
            }
        } catch (JSONException e) {
            //Log.d("CommonCallback","hhh");
            e.printStackTrace();
            onError(e);
        }

    }
    public abstract void onError(Exception e);
    public abstract void onResponse(T response);
}
