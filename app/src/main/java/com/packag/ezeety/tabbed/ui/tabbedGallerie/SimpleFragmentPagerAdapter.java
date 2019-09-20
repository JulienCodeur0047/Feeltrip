package com.packag.ezeety.tabbed.ui.tabbedGallerie;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.Switch;

import com.packag.ezeety.R;

public class SimpleFragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {

    private Context mContext;

    public SimpleFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }
    @Override
    public Fragment getItem(int i) {
        if(i==0) return new tab_galleri();
        return new tab_photo();
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
            default:
                return null;
        }
    }

}
