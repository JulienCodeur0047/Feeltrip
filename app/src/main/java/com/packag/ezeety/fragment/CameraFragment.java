package com.packag.ezeety.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.packag.ezeety.R;
import com.packag.ezeety.adpter.EzeetyHelpers;
import com.packag.ezeety.adpter.ShardPreferenceManager;
import com.packag.ezeety.ezeety_profil_getstart;
import com.packag.ezeety.model.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Objects;

public class CameraFragment extends Fragment {
    private static final int REQUEST_IMAGE_CAPTURE = 100;
    private ImageView takePhotoIV,selectedIV;
    public static File imageFile;
    public static Uri uriImage;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        return inflater.inflate(R.layout.camera_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        takePhotoIV = view.findViewById(R.id.imageViewbuttoncamera);
        selectedIV = view.findViewById(R.id.imageViewpreviewfromcamera);
        takePhotoIV.setOnClickListener(v -> takePhoto());
        EventBus.getDefault().register(this);
        EventBus.getDefault().postSticky(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
               Bitmap photo = (Bitmap) data.getExtras().get("data");
               // Uri uri = getImageUri(Objects.requireNonNull(getContext()),photo);
                Uri uri2 = data.getData();
                selectedIV.setImageURI(uri2);
                selectedIV.setRotation(-90);
               //selectedIV.setImageBitmap(photo);
                imageFile = new File(uri2.toString());
                uriImage = uri2;
                Uri selectedImage = EzeetyHelpers.getImageUri(getContext(),photo);
                MessageEvent messageEvent = new MessageEvent(selectedImage);
                EventBus.getDefault().postSticky(messageEvent);

            }
        }
    }

    private void takePhoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Fragment frag = this;
        frag.startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    @Subscribe(sticky = true)
    public void eventReceiver(Boolean event) {
       if (event){ selectedIV.setImageResource(R.drawable.grayscale_background);}
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
