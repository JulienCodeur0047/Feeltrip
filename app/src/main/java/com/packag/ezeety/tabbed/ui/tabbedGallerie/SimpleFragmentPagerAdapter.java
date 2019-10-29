package com.packag.ezeety.tabbed.ui.tabbedGallerie;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.packag.ezeety.R;
import com.packag.ezeety.fragment.CameraFragment;
import com.packag.ezeety.fragment.GalleryFragment;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public SimpleFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }
    @Override
    public Fragment getItem(int i) {
        if(i==0) return new GalleryFragment();
        return new CameraFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.title_tab_gallerie);
            case 1:
                return mContext.getString(R.string.title_tab_photo);
            default: return null;
        }
    }

}
