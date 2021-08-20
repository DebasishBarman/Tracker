package com.debasish.trackers.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.debasish.trackers.R;
import com.debasish.trackers.model.CountryModel;

import java.util.List;

public class countryAdapter extends ArrayAdapter<CountryModel> {
    Context context;
    List<CountryModel> countryModelList;


    public countryAdapter(@NonNull Context context,List<CountryModel> countryModelList) {
        super(context, R.layout.countrylist,countryModelList);
        this.context=context;
        this.countryModelList = countryModelList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.countrylist,null,false);
//        ImageView cflag=v.findViewById(R.id.flag);
//        TextView cname=v.findViewById(R.id.countryNameList);
//        cname.setText(countryModelList.get(position).getCountry());
//        Glide.with(context).load(countryModelList.get(position).getFlag()).into(cflag);
//
        return v;
    }
}
