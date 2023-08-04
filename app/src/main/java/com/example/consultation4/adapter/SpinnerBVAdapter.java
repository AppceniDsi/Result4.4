package com.example.consultation4.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.consultation4.model.BV;

import java.util.List;

public class SpinnerBVAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<BV> list;
    private int listItemLayoutResource;
    private int textViewLabelId;
    private int textViewCodeId;

    public SpinnerBVAdapter(Activity context, List<BV> list, int listItemLayoutResource, int textViewLabelId, int textViewCodeId) {
        this.layoutInflater = context.getLayoutInflater();
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
        BV bv = (BV) this.getItem(i);
        Log.d("GET ITEM BV !! ", "-- "+ bv.getId_bv());
        return bv.getId_bv();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        BV bv = (BV) getItem(i);
        View rowView = this.layoutInflater.inflate(this.listItemLayoutResource, null,true);
        TextView textViewItemName = (TextView) rowView.findViewById(this.textViewLabelId);
        textViewItemName.setText(bv.getLabel_bv());
        TextView textViewItemPercent = (TextView) rowView.findViewById(textViewCodeId);
        textViewItemPercent.setText(bv.getCode_cv());
        textViewItemPercent.setVisibility(View.INVISIBLE);

        return rowView;
    }
}
