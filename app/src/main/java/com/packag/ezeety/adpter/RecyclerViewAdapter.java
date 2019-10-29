package com.packag.ezeety.adpter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.packag.ezeety.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    private List<String> TypeLieuDescriptions = new ArrayList<String>();
    private List<String> imageUrls = new ArrayList<String>();
    private Context context;

    public RecyclerViewAdapter(List<String> typeLieuDescriptions, List<String> imageUrlss, Context contexts) {
        TypeLieuDescriptions = typeLieuDescriptions;
        imageUrls = imageUrlss;
        context = contexts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_ezzety_type_lieu, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context)
                .asBitmap()
                .load(imageUrls.get(position))
                .into(holder.imageViewTypeLieu);

       //TODO EVENT CLICKED
    }

    @Override
    public int getItemCount() {
        return TypeLieuDescriptions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageViewTypeLieu;
        TextView textViewDescription;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewTypeLieu = itemView.findViewById(R.id.imageViewDescriptTypeLieu);
            textViewDescription = itemView.findViewById(R.id.textViewDescriptTypeLieu);
        }
    }
}
