package com.example.consultation4.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.consultation4.model.Commune;

import java.util.List;

public class SpinnerCommuneAdapter extends BaseAdapter {

    private LayoutInflater flater;
    private List<Commune> list;
    private int listItemLayoutResource;
    private int textViewLabelId;
    private int textViewCodeId;

    public SpinnerCommuneAdapter(Activity context, List<Commune> list, int listItemLayoutResource, int textViewLabelId, int textViewCodeId) {
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
        Commune commune = (Commune) this.getItem(i);
        Log.d("GET ITEM DISTRICT !! ", "-- "+ commune.getId_commune());
        return commune.getId_commune();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Commune commune = (Commune) getItem(i);
        View rowView = this.flater.inflate(this.listItemLayoutResource, null,true);
        TextView textViewItemName = (TextView) rowView.findViewById(this.textViewLabelId);
        textViewItemName.setText(commune.getLabel_commune());
        TextView textViewItemPercent = (TextView) rowView.findViewById(textViewCodeId);
        textViewItemPercent.setText(commune.getCode_commune());
        textViewItemPercent.setVisibility(View.INVISIBLE);

        return rowView;
    }
}
