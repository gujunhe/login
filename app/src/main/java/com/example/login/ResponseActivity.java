package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;


import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.yalantis.ucrop.view.OverlayView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ResponseActivity extends AppCompatActivity {
    private Button selectPhotoButton;
    private ImageView mImageView;
    private Button uploadPhoto;
    private static final int CAMERA_CODE = 1;
    private static final int GALLERY_CODE = 2;
    private static final int CROP_CODE = 3;
    ArrayList<String> photo_path=new ArrayList<String>();
    ArrayList<String> photo_name=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);
        selectPhotoButton = (Button) findViewById(R.id.select_photo_btn);
        mImageView = (ImageView)findViewById(R.id.image);


        uploadPhoto = (Button)findViewById(R.id.upload_photo);
        uploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResponseActivity.this,display.class);
                Bundle b=new Bundle();
                String a=photo_name.size()+" ";
                Log.d("hhh",a);
                b.putStringArrayList("photoname",photo_name);
                intent.putExtras(b);

                b.putStringArrayList("photopath",photo_path);
                intent.putExtras(b);
                startActivity(intent);


            }
        });


        selectPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PictureSelector.create(ResponseActivity.this)
                        .openGallery(PictureMimeType.ofImage())
                        .imageEngine(GlideEngine.createGlideEngine()) // 请参考Demo GlideEngine.java
                        .enableCrop(true)// 是否裁剪 true or false
                        //.compress(false)// 是否压缩 true or false
                        //.glideOverride(200, 200)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                        //.withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                        //.hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示 true or false
                        //.isGif(false)// 是否显示gif图片 true or false
                        .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                        //.circleDimmedLayer(false)// 是否圆形裁剪 true or false
                        //.showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                       // .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                       // .openClickSound(true)// 是否开启点击声音 true or false
                        //.selectionMedia(null)// 是否传入已选图片 List<LocalMedia> list
                       // .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                        //.scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                        .isDragFrame(true)// 是否可拖动裁剪框(固定)
                        .isMultipleSkipCrop(true)//多图裁剪是否支持跳过

                        .forResult(new OnResultCallbackListener<LocalMedia>() {
                            @Override
                            public void onResult(List<LocalMedia> result) {
                                // 例如 LocalMedia 里面返回五种path
                                // 1.media.getPath(); 原图path，但在Android Q版本上返回的是content:// Uri类型
                                // 2.media.getCutPath();裁剪后path，需判断media.isCut();切勿直接使用
                                // 3.media.getCompressPath();压缩后path，需判断media.isCompressed();切勿直接使用
                                // 4.media.getOriginalPath()); media.isOriginal());为true时此字段才有值
                                // 5.media.getAndroidQToPath();Android Q版本特有返回的字段，但如果开启了压缩或裁剪还是取裁剪或压缩路
                                //径；注意：.isAndroidQTransform(false);此字段将返回空
                                // 如果同时开启裁剪和压缩，则取压缩路径为准因为是先裁剪后压缩
                                // TODO 可以通过PictureSelectorExternalUtils.getExifInterface();方法获取一些额外的资源信息，如旋转角度、经纬度等信息

                                for (LocalMedia media : result) {
                                    Log.i("ResponseActivity", "是否压缩:" + media.isCompressed());
                                    Log.i("ResponseActivity", "压缩:" + media.getCompressPath());
                                    Log.i("ResponseActivity", "原图:" + media.getPath());
                                    Log.i("ResponseActivity", "是否裁剪:" + media.isCut());
                                    Log.i("ResponseActivity", "裁剪:" + media.getCutPath());
                                    Log.i("ResponseActivity", "是否开启原图:" + media.isOriginal());
                                    Log.i("ResponseActivity", "原图路径:" + media.getOriginalPath());
                                    Log.i("ResponseActivity", "Android Q 特有Path:" + media.getAndroidQToPath());
                                    if(media.isCut()) {
                                        photo_path.add(media.getCutPath());
                                    }
                                    else {
                                        photo_path.add(media.getRealPath());
                                    }
                                    photo_name.add(media.getFileName());


                                   // Bitmap bitmap = BitmapFactory.decodeFile(media.getCutPath());
                                   // mImageView = (ImageView) findViewById(R.id.image) ;
                                   // mImageView.setImageBitmap(bitmap);
                                }
                            }

                            @Override
                            public void onCancel() {
                                Log.i("ResponseActivity", "PictureSelector Cancel");

                            }
                        });

            }
        });


    }






}