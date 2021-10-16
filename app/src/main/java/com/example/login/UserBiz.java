package com.example.login;

import com.zhy.http.okhttp.OkHttpUtils;

public class UserBiz {

    public void login(String username,String password,CommonCallback<User>commonCallback)

    {
        OkHttpUtils.post()
                .url("http://59.77.134.5:4999/")

                .addParams("id","username")
                .addParams("pwd","password")
                .addParams("token","password")
                .build()
                .execute(commonCallback);

    }

}
