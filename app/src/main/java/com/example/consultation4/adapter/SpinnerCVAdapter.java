package com.example.consultation4.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.consultation4.model.CV;

import java.util.List;

public class SpinnerCVAdapter extends BaseAdapter {

    private LayoutInflater flater;
    private List<CV> list;
    private int listItemLayoutResource;
    private int textViewLabelId;
    private int textViewCodeId;

    public SpinnerCVAdapter(Activity context, List<CV> list, int listItemLayoutResource, int textViewLabelId, int textViewCodeId) {
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
        CV cv = (CV) this.getItem(i);
        Log.d("GET ITEM CV !! ", "-- "+ cv.getId_cv());
        return cv.getId_cv();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        CV cv = (CV) getItem(i);
        View rowView = this.flater.inflate(this.listItemLayoutResource, null,true);
        TextView textViewItemName = (TextView) rowView.findViewById(this.textViewLabelId);
        textViewItemName.setText(cv.getLabel_cv());
        TextView textViewItemPercent = (TextView) rowView.findViewById(textViewCodeId);
        textViewItemPercent.setText(cv.getCode_cv());
        textViewItemPercent.setVisibility(View.INVISIBLE);

        return rowView;
    }
}
