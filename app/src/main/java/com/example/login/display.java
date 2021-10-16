package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.util.ArrayList;

public class display extends AppCompatActivity {
    private Context mContext;
    private GridView grid_photo;
   // private BaseAdapter mAdapter = null;
    private ArrayList<Photo> mData = null;
    ArrayList<String> photo_path;
    ArrayList<String> photo_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        mContext = display.this;
        grid_photo = (GridView) findViewById(R.id.grid_photo);
        Intent intent = getIntent();
        Bundle b=intent.getExtras();
        photo_path=new ArrayList<String>();
        photo_name=new ArrayList<String>();
        photo_name=b.getStringArrayList("photoname");


        photo_path=b.getStringArrayList("photopath");
        String a= photo_path.size()+" ";

        Log.d("xxx",a);


        mData = new ArrayList<Photo>();
        for(int i=0;i<photo_path.size();i++)
        {
            mData.add(new Photo(photo_name.get(i), photo_path.get(i)));
        }
       ImageAdapter mAdapter = new ImageAdapter(display.this,mData);

        grid_photo.setAdapter(mAdapter);
        };








    }
