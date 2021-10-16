package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private EditText mUsername;
    private EditText mPassword;
    private Button mLogin;
    private UserBiz mUserBiz = new UserBiz();
    private ProgressDialog mLoadingDialoig;
    private List<Userbean> list;
    private  Userbean userbean=new Userbean();
    //private Userbean userbean;

    final OkHttpClient client = new OkHttpClient();
    // .connectTimeout(10,TimeUnit.MINUTES)
      //          .readTimeout(10, TimeUnit.MINUTES) // 读取超时
            //    .writeTimeout(10, TimeUnit.MINUTES) // 写超时
             //   .build();







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initEvent();
    }

    private void initEvent() {
        mLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String username =mUsername.getText().toString();
                String password = mPassword.getText().toString();
                Log.d("MainActivity",username);
                Log.d("MainActivity",password);
                if(username.equals("")||password.equals(""))
                {
                    Toast.makeText(MainActivity.this,"账号或者密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                mLoadingDialoig =new ProgressDialog(MainActivity.this);
                mLoadingDialoig.setMessage("加载中");
                satartLoadingProgress();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MediaType JSON = MediaType.parse("application/json;charset=utf-8");
                        JSONObject jsonObject = new JSONObject();
                        OkHttpClient httpClient = new OkHttpClient();
                        try {
                            jsonObject.put("id",username);
                            jsonObject.put("pwd",password);
                            jsonObject.put("token","SOSD");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        RequestBody requestBody = RequestBody.create(JSON, String.valueOf(jsonObject));
                       String url = "http://59.77.134.5:4999/";



                        Request request = new Request.Builder()
                                .url(url)
                                .post(requestBody)
                                .build();

                        Call call = httpClient.newCall(request);
                        call.enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.d("whq登录",e.getMessage());
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String MyResult = response.body().string();
                                Log.d("whq登录",response+"---------response---------");
                                Log.d("whq登录",MyResult);
                                Gson gson = new Gson();
                                Userbean muserbean = gson.fromJson(MyResult, Userbean.class);
                                Log.d("MainActivity",muserbean.getMessage());
                                Log.d("MainActivity",muserbean.getName());
                                Log.d("MainActivity",muserbean.getSno());
                                userbean.setMessage(muserbean.getMessage());
                                userbean.setName(muserbean.getName());
                                userbean.setSno(muserbean.getSno());

                                if(userbean.getMessage().equals("登陆成功") ) {
                                    responseActivitity();

                                    stopLoadingProgress();
                                    Looper.prepare();
                                    Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                    Looper.loop();

                                }

                                else {
                                    stopLoadingProgress();
                                    Looper.prepare();
                                    Toast.makeText(MainActivity.this, "密码或账号错误，请重新输入", Toast.LENGTH_SHORT).show();
                                }   Looper.loop();



                                Log.d("Main",userbean.getMessage());



                            }
                        });
                    }
                }).start();


                /*mUserBiz.login(username,password, new CommonCallback<User>() {
                    @Override
                    public void onError(Exception e) {
                        stopLoadingProgress();
                        Toast.makeText(MainActivity.this,"账号或密码错误",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onResponse(User response) {
                        satartLoadingProgress();
                        Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                    }
                });*/
                //先检查是否登录成功
                //Log.d("MainActivity",userbean.getMessage());



            }
        });
    }


    private void stopLoadingProgress() {
        if(mLoadingDialoig!= null&&mLoadingDialoig.isShowing())
        {
            mLoadingDialoig.dismiss();
        }
    }

    private void satartLoadingProgress() {
        mLoadingDialoig.show();
    }

    private void responseActivitity() {
        Intent intent = new Intent(this,ResponseActivity.class);
        startActivity(intent);
        finish();
    }

    private void initView() {
        mUsername = (EditText) findViewById(R.id.edt_uid);
        mPassword=(EditText) findViewById(R.id.edt_upwd);
        mLogin = (Button) findViewById(R.id.btn_login);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopLoadingProgress();
        mLoadingDialoig=null;
    }




}