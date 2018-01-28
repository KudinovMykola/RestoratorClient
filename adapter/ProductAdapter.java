package com.kudinov.restoratorclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
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
        GridView parentGrid = (GridView)parent;

        int columnCount = parentGrid.getNumColumns();
        int parentWidth = parentGrid.getWidth();
        int parentHeight = parentGrid.getHeight();

        int rowCount = parentGrid.getCount()/ columnCount;
        if(parentGrid.getCount()%columnCount != 0)
            rowCount++;

        int width = parentWidth/columnCount;
        int height = parentHeight/rowCount;

        if(width > ctx.getResources().getDimension(R.dimen.max_width_button))
            width = (int) ctx.getResources().getDimension(R.dimen.max_width_button);

        if(height > ctx.getResources().getDimension(R.dimen.max_height_button))
            height = (int) ctx.getResources().getDimension(R.dimen.max_height_button);


        TextView text = (TextView) view;
        text.setText(someProduct.getName());
        text.setWidth(width);
        text.setHeight(height);

        return text;
    }
}
