package com.example.consultation4.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.consultation4.model.District;

import java.util.List;

public class SpinnerDistrictAdapter extends BaseAdapter {

    private LayoutInflater flater;
    private List<District> list;
    private int listItemLayoutResource;
    private int textViewLabelId;
    private int textViewCodeId;

    public SpinnerDistrictAdapter(Activity context, List<District> list, int listItemLayoutResource, int textViewLabelId, int textViewCodeId) {
        this.flater = context.getLayoutInflater();
        this.list = list;
        this.listItemLayoutResource = listItemLayoutResource;
        this.textViewLabelId = textViewLabelId;
        this.textViewCodeId = textViewCodeId;
    }

    @Override
    public int getCount() {
        if(this.list == null)  {
            return 0;
        }
        return this.list.size();
    }

    @Override
    public Object getItem(int i) {
        return this.list.get(i);
    }

    @Override
    public long getItemId(int i) {
        District district = (District) this.getItem(i);
        Log.d("GET ITEM DISTRICT !! ", "-- "+ district.getId_district());
        return district.getId_district();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        District district = (District) getItem(i);
        View rowView = this.flater.inflate(this.listItemLayoutResource, null,true);
        TextView textViewItemName = (TextView) rowView.findViewById(this.textViewLabelId);
        textViewItemName.setText(district.getLabel_district());
        TextView textViewItemPercent = (TextView) rowView.findViewById(textViewCodeId);
        textViewItemPercent.setText(district.getCode_district());
        textViewItemPercent.setVisibility(View.INVISIBLE);

        return rowView;
    }
}
