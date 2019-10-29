package com.packag.ezeety.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.packag.ezeety.R;
import com.packag.ezeety.adpter.ShardPreferenceManager;
import com.packag.ezeety.adpter.TabAdapter;
import com.packag.ezeety.ezeety_profil_getstart;
import com.packag.ezeety.fragment.CameraFragment;
import com.packag.ezeety.fragment.GalleryFragment;
import com.packag.ezeety.model.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class TabAcrivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        EventBus.getDefault().register(this);
        viewPager = findViewById(R.id.viewpager);
        stupeView(viewPager);
        tabLayout = findViewById(R.id.view_tablayout);
        tabLayout.setupWithViewPager(viewPager);
        Toolbar toolbar = findViewById(R.id.toobar_view);
        if (toolbar ==null) return;
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_vert_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        textView = findViewById(R.id.nextTv);

        //textView.setOnClickListener(v -> goTOprofilgetStarted());


    }

    private void goTOprofilgetStarted() {
        //eventReceiver();
    }

    public void stupeView(ViewPager paramView){
        TabAdapter adapter = new TabAdapter(this.getSupportFragmentManager());
        adapter.addFrag(new CameraFragment(), "PHOTO");
        adapter.addFrag(new GalleryFragment(), "GALERIE");
        paramView.setAdapter(adapter);
    }
    @Subscribe(sticky = true)
    public void eventReceiver(MessageEvent event) {
        //Log.d("ZZZZZZZ","TESTS");
        if (event != null){
            ezeety_profil_getstart.imageViewProfilePicture.setRotation(-90);
            textView.setVisibility(View.VISIBLE);
            textView.setOnClickListener(view -> {
                Intent intent = new Intent(getApplicationContext(), ezeety_profil_getstart.class);

                intent.putExtra("uri",event.getMessage());
                startActivity(intent);
            });
        }else {
            textView.setVisibility(View.GONE);
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
