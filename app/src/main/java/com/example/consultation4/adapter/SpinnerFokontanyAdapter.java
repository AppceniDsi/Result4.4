package com.example.consultation4.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.consultation4.model.Fokontany;

import java.util.List;

public class SpinnerFokontanyAdapter extends BaseAdapter {

    private LayoutInflater flater;
    private List<Fokontany> list;
    private int listItemLayoutResource;
    private int textViewLabelId;
    private int textViewCodeId;

    public SpinnerFokontanyAdapter(Activity context, List<Fokontany> list, int listItemLayoutResource, int textViewLabelId, int textViewCodeId) {
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
        Fokontany fokontany = (Fokontany) this.getItem(i);
        Log.d("GET ITEM DISTRICT !! ", "-- "+ fokontany.getId_fokontany());
        return fokontany.getId_fokontany();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Fokontany fokontany = (Fokontany) getItem(i);
        View rowView = this.flater.inflate(this.listItemLayoutResource, null,true);
        TextView textViewItemName = (TextView) rowView.findViewById(this.textViewLabelId);
        textViewItemName.setText(fokontany.getLabel_fokontany());
        TextView textViewItemPercent = (TextView) rowView.findViewById(textViewCodeId);
        textViewItemPercent.setText(fokontany.getCode_fokontany());
        textViewItemPercent.setVisibility(View.INVISIBLE);

        return rowView;
    }
}
