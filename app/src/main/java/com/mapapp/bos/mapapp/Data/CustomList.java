package com.mapapp.bos.mapapp.Data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mapapp.bos.mapapp.R;
import com.mapapp.bos.mapapp.gson.PlacesResult;

/**
 * Created by Bos on 2017-01-02.
 */
public class CustomList extends ArrayAdapter<String> {

    private final Context context;
    private final String[] list;

    public CustomList(Context context,
                      String [] values) {
        super(context, R.layout.list_single, values);
        this.context=context;
        this.list = values;


    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rowView= inflater.inflate(R.layout.list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
                txtTitle.setText(list[position]);

        return rowView;
    }
}