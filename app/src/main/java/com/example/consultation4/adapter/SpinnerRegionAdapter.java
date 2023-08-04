package com.example.consultation4.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.consultation4.model.Region;

import java.util.List;

public class SpinnerRegionAdapter extends BaseAdapter {

    private LayoutInflater flater;
    private List<Region> list;
    private int listItemLayoutResource;
    private int textViewLabelId;
    private int textViewCodeId;

    public SpinnerRegionAdapter(Activity context, List<Region> list, int listItemLayoutResource, int textViewLabelId, int textViewCodeId) {
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
        Region region = (Region) this.getItem(i);
        Log.d("GET ITEM REGION !! ", "-- "+ region.getId_region());
        return region.getId_region();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Region region = (Region) getItem(i);
        View rowView = this.flater.inflate(this.listItemLayoutResource, null,true);
        TextView textViewItemName = (TextView) rowView.findViewById(this.textViewLabelId);
        textViewItemName.setText(region.getLabel_region());
        TextView textViewItemPercent = (TextView) rowView.findViewById(textViewCodeId);
        textViewItemPercent.setText(region.getCode_region());
        textViewItemPercent.setVisibility(View.INVISIBLE);

        return rowView;
    }
}
