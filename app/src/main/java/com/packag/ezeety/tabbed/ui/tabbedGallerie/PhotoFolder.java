package com.packag.ezeety.tabbed.ui.tabbedGallerie;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.GridView;

import com.packag.ezeety.R;
import com.packag.ezeety.adpter.GridViewAdapter;

public class PhotoFolder extends AppCompatActivity {

    int int_position;
    private GridView gridView;
    GridViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_fragment);
//        gridView = (GridView)findViewById(R.id.gridview_image);
        int_position = getIntent().getIntExtra("value", 0);
        adapter = new GridViewAdapter(this,tab_galleri_activity.allImages,int_position);
        gridView.setAdapter(adapter);
    }
}
