package com.packag.ezeety.fragment;

import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.MergeCursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.packag.ezeety.R;
import com.packag.ezeety.Services.ImageLoaderService;
import com.packag.ezeety.adpter.EzeetyHelpers;
import com.packag.ezeety.adpter.GalleryAdapter;
import com.packag.ezeety.model.MessageEvent;
import com.packag.ezeety.model.PhotoItem;
import com.packag.ezeety.model.TaskCompletion;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class GalleryFragment extends Fragment implements TaskCompletion,GalleryAdapter.onImageListener {
    private GridView gridView;
    private GalleryAdapter galleryAdapter;
    private ImageView imageView;
    CameraFragment cameraFragment = new CameraFragment();
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        return inflater.inflate(R.layout.gallery_fragment,container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gridView = view.findViewById(R.id.gridview);
        imageView = view.findViewById(R.id.tIV);
        EventBus.getDefault().register(this);
        EventBus.getDefault().postSticky(true);
        new ImageLoaderService(getActivity(),this).execute();
    }
    @Override
    public void onSuccess(ArrayList<PhotoItem> photoItems) {
        gridView.setAdapter(new GalleryAdapter(getActivity(), photoItems, this));
    }
    @Override
    public void doPressed(PhotoItem photoItem) {
        imageView.setVisibility(View.VISIBLE);
        //imageView.setImageBitmap(EzeetyHelpers.decodeFile(photoItem.getPath()),);
        Bitmap bitmap = EzeetyHelpers.decodeFile(photoItem.getPath());
        Uri uriImage = Uri.parse(photoItem.getPath());
        Uri selectedImage = EzeetyHelpers.getImageUri(getActivity(),bitmap);
        imageView.setImageURI(uriImage);
        if(uriImage != null){
            cameraFragment.uriImage = uriImage;
            cameraFragment.imageFile = new File(uriImage.toString());
        }
        MessageEvent messageEvent = new MessageEvent(selectedImage);
        EventBus.getDefault().postSticky(messageEvent);

    }
    @Subscribe(sticky = true)
    public void eventReceiver(Boolean event) {
        if (event){ imageView.setVisibility(View.GONE);}
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
