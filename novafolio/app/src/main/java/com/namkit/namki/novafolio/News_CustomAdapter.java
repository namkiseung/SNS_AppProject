package com.namkit.namki.novafolio;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by namki on 2018-03-10.
 */

public class News_CustomAdapter extends ArrayAdapter {
    Activity activity;
    int layout;
    ArrayList<News_MainItem> arrSinhVien;

    public News_CustomAdapter(@NonNull Activity activity, @LayoutRes int layout, ArrayList<News_MainItem> arrSinhVien) {
        super(activity, layout, arrSinhVien);
        this.activity = activity;
        this.layout = layout;
        this.arrSinhVien = arrSinhVien;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        convertView = layoutInflater.inflate(layout, null);

        ImageView avatar = (ImageView) convertView.findViewById(R.id.avatar);
        TextView ten = (TextView)convertView.findViewById(R.id.text_ten);
        TextView sdt = (TextView)convertView.findViewById(R.id.text_sdt);
        //CheckBox check = convertView.findViewById(R.id.check);

        avatar.setImageResource(arrSinhVien.get(position).getAvatar());
        ten.setText(arrSinhVien.get(position).getTenSinhVien());
        sdt.setText(arrSinhVien.get(position).getSdtSinhVien());
        //check.setClickable(false);
        //check.setFocusable(false);
        //check.setChecked(arrSinhVien.get(position).isChecked());

        return convertView;
    }
}
