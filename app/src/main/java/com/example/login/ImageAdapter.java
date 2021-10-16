package com.example.login;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private List<Photo> photoList;

    public ImageAdapter(Context context,List<Photo> photoList) {
        this.mContext = context;
        this.photoList = photoList;
    }


    @Override
    public int getCount() {
        return photoList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;


        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list, parent, false);
            holder = new ViewHolder();
            holder.itemImg = (ImageView) convertView.findViewById(R.id.iv_gridView_item);
            holder.itemtext = (TextView) convertView.findViewById(R.id.tv_gridView_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String path=photoList.get(position).getPhotoPath();
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        holder.itemImg.setImageBitmap(bitmap);
        holder.itemtext.setText(photoList.get(position).getPhotoName());
        return convertView;



    }


    class ViewHolder  {
        ImageView itemImg;
        TextView itemtext;
    }
    public ImageAdapter() {
    }

}


