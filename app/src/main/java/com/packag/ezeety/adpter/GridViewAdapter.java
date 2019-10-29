package com.packag.ezeety.adpter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.packag.ezeety.model.ModelImage;
import com.packag.ezeety.R;

import java.util.ArrayList;

/*import android.support.v7.widget.RecyclerView;*/

public class GridViewAdapter extends ArrayAdapter<ModelImage> {
    Context context;
    ViewHolder viewHolder;
    ArrayList<ModelImage> allMenu = new ArrayList<>();
    int int_position;

    public GridViewAdapter(Context context, ArrayList<ModelImage> allMenu,int int_position){
        super(context, R.layout.layout_ezeety_photo_folder, allMenu);
        this.allMenu = allMenu;
        this.context = context;
        this.int_position = int_position;

    }


    @Override
    public int getCount() {

        Log.e("ADAPTER LIST SIZE", allMenu.get(int_position).getAllimagePath().size() + "");
        return allMenu.get(int_position).getAllimagePath().size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        if (allMenu.get(int_position).getAllimagePath().size() > 0) {
            return allMenu.get(int_position).getAllimagePath().size();
        } else {
            return 1;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_ezeety_photo_folder, parent, false);
            /*viewHolder.tv_foldern = (TextView) convertView.findViewById(R.id.tv_folder);
            viewHolder.tv_foldersize = (TextView) convertView.findViewById(R.id.tv_folder2);
            viewHolder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);*/


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_foldern.setVisibility(View.GONE);
        viewHolder.tv_foldersize.setVisibility(View.GONE);
        Glide.with(context).load("file://" + allMenu.get(int_position).getAllimagePath().get(position))
                .into(viewHolder.iv_image);


        return convertView;

    }

    private static class ViewHolder {
        TextView tv_foldern, tv_foldersize;
        ImageView iv_image;


    }
}
