package com.packag.ezeety.tabbed.ui.tabbedGallerie;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TabHost;
import android.widget.Toast;

import com.packag.ezeety.Pojos.ModelImage;
import com.packag.ezeety.R;
import com.packag.ezeety.SettingView.AdapterPhotoFolder;

import java.util.ArrayList;

    public class tab_galleri_activity extends AppCompatActivity implements TabHost.OnTabChangeListener {



    public static ArrayList<ModelImage> allImages = new ArrayList<>();

    boolean booleanFolder;
    GridView gridViewFolder;
    TabHost tabhost;
    private static final int REQUEST_PERMISSIONS = 100;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ezeety_tab_galleri);
        gridViewFolder = (GridView) findViewById(R.id.gridview_image);
        tabhost = findViewById(R.id.tabHostgalleri);
        tabhost.setOnTabChangedListener(this);
        tabhost.setup();
        addTabs("Galleri","GALLERI",R.id.tabGalleri);
        addTabs("Photo","PHOTO",R.id.tabPhoto);


        gridViewFolder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), PhotoFolder.class);
                intent.putExtra("value",i);
                startActivity(intent);

            }
        });

        if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(tab_galleri_activity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(tab_galleri_activity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE))) {

            } else {
                ActivityCompat.requestPermissions(tab_galleri_activity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }
        }else {
            Log.e("Else","Else");
            fn_imagespath();
        }
    }

    public ArrayList<ModelImage> fn_imagespath() {
        allImages.clear();

        int int_position = 0;
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;

        String absolutePathOfImage = null;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, orderBy + " DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
            Log.e("Column", absolutePathOfImage);
            Log.e("Folder", cursor.getString(column_index_folder_name));

            for (int i = 0; i < allImages.size(); i++) {
                if (allImages.get(i).getFolderName().equals(cursor.getString(column_index_folder_name))) {
                    booleanFolder = true;
                    int_position = i;
                    break;
                } else {
                    booleanFolder = false;
                }
            }


            if (booleanFolder) {

                ArrayList<String> al_path = new ArrayList<>();
                al_path.addAll(allImages.get(int_position).getAllimagePath());
                al_path.add(absolutePathOfImage);
                allImages.get(int_position).setAllimagePath(al_path);

            } else {
                ArrayList<String> al_path = new ArrayList<>();
                al_path.add(absolutePathOfImage);
                ModelImage obj_model = new ModelImage();
                obj_model.setFolderName(cursor.getString(column_index_folder_name));
                obj_model.setAllimagePath(al_path);

                allImages.add(obj_model);


            }


        }


        for (int i = 0; i < allImages.size(); i++) {
            Log.e("FOLDER", allImages.get(i).getFolderName());
            for (int j = 0; j < allImages.get(i).getAllimagePath().size(); j++) {
                Log.e("FILE", allImages.get(i).getAllimagePath().get(j));
            }
        }
        AdapterPhotoFolder obj_adapter = new AdapterPhotoFolder(getApplicationContext(),allImages);
        gridViewFolder.setAdapter(obj_adapter);
        return allImages;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        fn_imagespath();
                    } else {
                        Toast.makeText(tab_galleri_activity.this, "The app was not allowed to read or write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

        @Override
        public void onTabChanged(String s) {

        }

        private void addTabs(String tag, String title, int Content){
            TabHost.TabSpec tabSpec = tabhost.newTabSpec(tag);
            tabSpec.setIndicator(title,null);
            tabSpec.setContent(Content);
            tabhost.addTab(tabSpec);
        }
    }
