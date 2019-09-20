package com.packag.ezeety.tabbed.ui.tabbedGallerie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.packag.ezeety.R;
import com.packag.ezeety.SettingView.GridViewAdapter;

public class PhotoFolder extends AppCompatActivity {

    int int_position;
    private GridView gridView;
    GridViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ezeety_tab_galleri);
        gridView = (GridView)findViewById(R.id.gridview_folder);
        int_position = getIntent().getIntExtra("value", 0);
        adapter = new GridViewAdapter(this,tab_galleri.allImages,int_position);
        gridView.setAdapter(adapter);
    }
}
