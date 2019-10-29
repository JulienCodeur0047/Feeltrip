package com.packag.ezeety.tabbed.ui.tabbedGallerie;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.packag.ezeety.model.ModelImage;
import com.packag.ezeety.R;
import com.packag.ezeety.adpter.AdapterPhotoFolder;
import com.packag.ezeety.adpter.GridViewAdapter;
import com.packag.ezeety.adpter.SettingImageBitmap;
import com.packag.ezeety.ezeety_profil_getstart;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class ezeety_gallery_activity extends AppCompatActivity implements TabHost.OnTabChangeListener {
    public static ArrayList<ModelImage> allImages = new ArrayList<>();

    GridView gridViewImge;
    TextView textViewBacktoProfilStarted, textViewNexSetoPicture;
    LinearLayout linearLayoutFlotingImage;
    ImageView imageViewSelectedImage, imageViewbutonCamera, imageViewPreviewCamera;
    ArrayList<File> ListImageFile;
    AdapterPhotoFolder obj_adapter;
    GridViewAdapter gridViewAdapterOr;
    int position;
    private static final int CAMERA_REQUEST = 1888;
    private static final int REQUEST_PERMISSIONS = 100;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    public static String imageUri;
    public static File imageFilePick;
    public static Uri imageuricomplet;
    TabHost tabhost;
    public static Bitmap photoFromCamera;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListImageFile = AllimageReader(Environment.getExternalStorageDirectory());
        setContentView(R.layout.camera_fragment);
//        textViewBacktoProfilStarted = findViewById(R.id.textViewButtonBackToProfilStarted);
      //  textViewNexSetoPicture = findViewById(R.id.textViewNexttoSetPicture);
      //  linearLayoutFlotingImage = (LinearLayout) findViewById(R.id.linearLayoutFlotingImage);
      //  imageViewSelectedImage = findViewById(R.id.imageViewSelectedImage);
        imageViewbutonCamera = findViewById(R.id.imageViewbuttoncamera);
        imageViewPreviewCamera = findViewById(R.id.imageViewpreviewfromcamera);
      //  gridViewImge = findViewById(R.id.gridview_image);

        imageViewbutonCamera.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(ezeety_gallery_activity.this,Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });

        gridViewImge.setAdapter(new galleryAdapter(ezeety_gallery_activity.this));

        gridViewImge.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                imageFilePick = ListImageFile.get(i);
                imageUri = ListImageFile.get(i).toString();
                textViewNexSetoPicture.setEnabled(true);
                textViewNexSetoPicture.setTextColor(getResources().getColor(R.color.colorAccent));
                imageViewSelectedImage.setImageBitmap(SettingImageBitmap.decodeSampleBitmapFromFile(ezeety_gallery_activity.this.getResources(),
                        imageUri,300,300));
                linearLayoutFlotingImage.setVisibility(View.VISIBLE);
            }
        });
        textViewNexSetoPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(photoFromCamera!=null){
                    photoFromCamera = Bitmap.createScaledBitmap(photoFromCamera,960,540,false);
                    imageuricomplet = getImageUri(ezeety_gallery_activity.this,photoFromCamera);
                    if (imageuricomplet!=null){
                        imageFilePick = new File(getRealPathFromURI(imageuricomplet));
                    }
                }



                Intent intentProfil = new Intent(ezeety_gallery_activity.this, ezeety_profil_getstart.class);
                startActivity(intentProfil);
                deleteCache(ezeety_gallery_activity.this);
            }
        });

        textViewBacktoProfilStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCache(ezeety_gallery_activity.this);
                if (imageUri != ""){
                    imageUri = "";
                    Intent intentProfil = new Intent(ezeety_gallery_activity.this, ezeety_profil_getstart.class);
                    startActivity(intentProfil);
                } else {
                    finish();
                }
            }
        });



      //  tabhost = findViewById(R.id.tabHostgalleri);
        tabhost.setOnTabChangedListener(this);
        tabhost.setup();
        addTabs("Photo","PHOTO",R.id.tabPhoto);
       // addTabs("Galleri","GALERIE",R.id.tabGalleri);




    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageUri = data.getData().toString();
            imageViewPreviewCamera.setImageURI(Uri.parse(imageUri));
            photoFromCamera = photo;
            textViewNexSetoPicture.setEnabled(true);
            textViewNexSetoPicture.setTextColor(getResources().getColor(R.color.colorAccent));

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

            /*for (int i = 0; i < allImages.size(); i++) {
                if (allImages.get(i).getStr_folder().equals(cursor.getString(column_index_folder_name))) {
                    boolean_folder = true;
                    int_position = i;
                    break;
                } else {
                    boolean_folder = false;
                }
            }*/


            /*if (boolean_folder) {

                ArrayList<String> al_path = new ArrayList<>();
                al_path.addAll(al_images.get(int_position).getAl_imagepath());
                al_path.add(absolutePathOfImage);
                al_images.get(int_position).setAl_imagepath(al_path);

            } else {*/
                ArrayList<String> al_path = new ArrayList<>();
                al_path.add(absolutePathOfImage);
                ModelImage obj_model = new ModelImage();
                //obj_model.setStr_folder(cursor.getString(column_index_folder_name));
                obj_model.setAllimagePath(al_path);

                allImages.add(obj_model);


           // }*/


        }


        for (int i = 0; i < allImages.size(); i++) {
            for (int j = 0; j < allImages.get(i).getAllimagePath().size(); j++) {
                Log.e("FILE", allImages.get(i).getAllimagePath().get(j));
            }
        }
        obj_adapter = new AdapterPhotoFolder(getApplicationContext(),allImages);
        gridViewImge.setAdapter(obj_adapter);
        return allImages;
    }
    private void addTabs(String tag, String title, int Content) {
        TabHost.TabSpec tabSpec = tabhost.newTabSpec(tag);
        tabSpec.setIndicator(title,null);
        tabSpec.setContent(Content);
        tabhost.addTab(tabSpec);

    }


    @Override
    public void onTabChanged(String s) {
        //imageUri = "";
        photoFromCamera = null;
        imageUri = null;
        imageFilePick = null;
        textViewNexSetoPicture.setEnabled(false);
        imageViewSelectedImage.setImageBitmap(SettingImageBitmap.decodeSampleBitmapFromFile(ezeety_gallery_activity.this.getResources(),
                imageUri,300,300));
        linearLayoutFlotingImage.setVisibility(View.INVISIBLE);
        imageViewPreviewCamera.setImageURI(null);
    }
    public class galleryAdapter extends BaseAdapter{

        Context context;
        public galleryAdapter (Context context){
            super();
            this.context = context;
        }

        ThumbnailUtils mThumbnail;
        @Override
        public int getCount() {
            return ListImageFile.size();
        }

        @Override
        public Object getItem(int i) {
            return ListImageFile.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View convertView =null;
            if (convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.layout_ezeety_photo_folder, viewGroup,false);
                ImageView imageViewItem = (ImageView) convertView.findViewById(R.id.imageViewItem);
                imageViewItem.setImageBitmap(SettingImageBitmap.decodeSampleBitmapFromFile(context.getResources(),
                        ListImageFile.get(i).toString(),
                        80,80));
            }
            Glide.with(ezeety_gallery_activity.this).load(ListImageFile.get(i))
                    .into(imageViewSelectedImage);
            return convertView;
        }
    }
    private ArrayList<File> AllimageReader(File externalStorageDirectory) {
        ArrayList<File> AllImageFile = new ArrayList<>();
        File[] Files = externalStorageDirectory.listFiles();

        Arrays.sort(Files, new Comparator<File>() {
            @Override
            public int compare(File file, File t1) {
                return Long.valueOf(file.lastModified()).compareTo(t1.lastModified());
            }
        });
        for(int i=0; i<Files.length;i++){
            if(Files[i].isDirectory()){
                AllImageFile.addAll(AllimageReader(Files[i]));
            }else {
                if (Files[i].getName().endsWith(".jpg")){
                    AllImageFile.add(Files[i]);
                }
            }
        }
        return AllImageFile;
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) { e.printStackTrace();}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }


}
