package com.packag.ezeety.adpter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.packag.ezeety.R;
import com.packag.ezeety.model.PhotoItem;

import java.util.ArrayList;

public class GalleryAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<PhotoItem> photoItems;
    private onImageListener onImageListener;

    public GalleryAdapter(Context context, ArrayList<PhotoItem> photoItems, GalleryAdapter.onImageListener onImageListener) {
        this.context = context;
        this.photoItems = photoItems;
        this.onImageListener = onImageListener;
    }

    @Override
    public int getCount() {
        return photoItems.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder {
        ImageView img;
    }
    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View convertView1 = convertView;
        Holder viewHolder = null;
        if (convertView1 == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView1 = inflater.inflate(R.layout.single_galery_item, parent, false);
            viewHolder = new Holder();
            viewHolder.img = convertView1.findViewById(R.id.pathIV);
            convertView1.setTag(viewHolder);
        } else {
            viewHolder = (Holder) convertView1.getTag();
        }
        Glide.with(context).load(photoItems.get(position).getPath()).into(viewHolder.img);
        viewHolder.img.setOnClickListener(view -> onImageListener.doPressed(photoItems.get(position)));
        return convertView1;
    }
    public interface onImageListener{
        void doPressed(PhotoItem photoItem);
    }

}