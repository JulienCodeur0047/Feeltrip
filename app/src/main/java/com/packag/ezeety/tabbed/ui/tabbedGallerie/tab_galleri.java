package com.packag.ezeety.tabbed.ui.tabbedGallerie;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.packag.ezeety.R;

public class tab_galleri extends Fragment {



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.layout_ezeety_tab_galleri,container, false);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(getActivity(),tab_galleri_activity.class);
        startActivity(intent);
    }
}
