package com.packag.ezeety.tabbed.ui.tabbedGallerie;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TabHost;

import com.packag.ezeety.R;
import com.packag.ezeety.ezeety_profil_getstart;

import java.io.File;
import java.util.ArrayList;

public class ezeety_gallery_activity extends AppCompatActivity implements TabHost.OnTabChangeListener {
    GridView gridViewImge;
    ArrayList<File> ListImageFile;
    public static String imageUri;
    TabHost tabhost;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ezeety_tab_galleri);
        gridViewImge = findViewById(R.id.gridview_image);
        ListImageFile = AllimageReader(Environment.getExternalStorageDirectory());
        gridViewImge.setAdapter(new galleryAdapter());
        gridViewImge.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                imageUri = ListImageFile.get(i).toString();
                Intent intentProfile = new Intent(ezeety_gallery_activity.this, ezeety_profil_getstart.class);
                startActivity(intentProfile);
            }
        });
        tabhost = findViewById(R.id.tabHostgalleri);
        tabhost.setOnTabChangedListener(this);
        tabhost.setup();
        addTabs("Galleri","GALLERI",R.id.tabGalleri);
        addTabs("Photo","PHOTO",R.id.tabPhoto);

    }

    private void addTabs(String tag, String title, int Content) {
        TabHost.TabSpec tabSpec = tabhost.newTabSpec(tag);
        tabSpec.setIndicator(title,null);
        tabSpec.setContent(Content);
        tabhost.addTab(tabSpec);
    }

    @Override
    public void onTabChanged(String s) {

    }


    public class galleryAdapter extends BaseAdapter{

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
                imageViewItem.setImageURI(Uri.parse(ListImageFile.get(i).toString()));
            }
            return convertView;
        }
    }
    private ArrayList<File> AllimageReader(File externalStorageDirectory) {
        ArrayList<File> AllImageFile = new ArrayList<>();
        File[] Files = externalStorageDirectory.listFiles();
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


}
