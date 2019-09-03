package com.packag.ezeety.SettingView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.packag.ezeety.Pojos.Lieux;
import com.packag.ezeety.R;
import com.packag.ezeety.ezeety_home;

public class AdapterHomeLieu extends BaseAdapter {

    ezeety_home mezeety_home;
    Lieux mLieu;
    public AdapterHomeLieu(ezeety_home ezeety_home, Lieux lieu) {

        this.mezeety_home = ezeety_home;
        this.mLieu = lieu;

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mezeety_home);
        View view = inflater.inflate(R.layout.layout_ezzety_type_lieu,null);
        TextView textViewDescription = view.findViewById(R.id.textViewDescriptTypeLieu);
        ImageView imageViewDescritLieu = view.findViewById(R.id.imageViewDescriptTypeLieu);

        textViewDescription.setText(mLieu.getListLieu().get(position).getType());
        imageViewDescritLieu.setImageResource(Integer.parseInt(mLieu.getListLieu().get(position).getPhoto()));
        return view;
    }
}
