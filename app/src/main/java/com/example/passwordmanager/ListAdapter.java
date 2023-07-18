package com.example.passwordmanager;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;


public class ListAdapter extends BaseAdapter {

    Context context;
    private final String [] platformName;
    private final String [] emails;
    private final int [] images;
    //private final String [] password;

    public ListAdapter(Context context, String[] values, String[] numbers, int[] images){
        //super(context, R.layout.single_list_app_item, utilsArrayList);
        this.context = context;
        this.platformName = values;
        this.emails = numbers;
        this.images = images;
        //this.password = password;
    }

    @Override
    public int getCount() {
        return platformName.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder.platformNameTV = (TextView) convertView.findViewById(R.id.idTVPlatformNameLogIn);
            viewHolder.emailTV = (TextView) convertView.findViewById(R.id.idTVEmailLogIn);
            viewHolder.iconIV = (ImageView) convertView.findViewById(R.id.platformIcon);
            //viewHolder.passwordTV = (TextView) convertView.findViewById(R.id.idTVProfilePassword);


            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.platformNameTV.setText(platformName[position]);
        viewHolder.emailTV.setText("Email: "+emails[position]);
        viewHolder.iconIV.setImageResource(images[position]);
       // viewHolder.passwordTV.setText(password[position]);

        return convertView;
    }

    private static class ViewHolder {

        //TextView passwordTV;
        TextView platformNameTV;
        TextView emailTV;
        ImageView iconIV;

    }

}
