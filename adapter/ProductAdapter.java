package com.kudinov.restoratorclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kudinov.restoratorclient.R;
import com.kudinov.restoratorclient.model.Product;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    private Context ctx;
    private LayoutInflater lInflater;
    private List<Product> objects;

    public ProductAdapter(Context context, List<Product> categories) {
        ctx = context;
        objects = categories;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return objects.size();
    }
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.product, parent, false);
        }

        Product someProduct = (Product) getItem(position);

        TextView bttn = (TextView) view;
        bttn.setText(someProduct.getName());

        return bttn;
    }
}
