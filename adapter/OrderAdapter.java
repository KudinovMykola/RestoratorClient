package com.kudinov.restoratorclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kudinov.restoratorclient.R;
import com.kudinov.restoratorclient.item.OrderItem;
import com.kudinov.restoratorclient.model.Product;

import java.text.DecimalFormat;
import java.util.List;

public class OrderAdapter extends BaseAdapter {
    private Context ctx;
    private int res;
    private LayoutInflater lInflater;
    private List<OrderItem> objects;


    public OrderAdapter(Context context,int resourse, List<OrderItem> orderItems) {
        ctx = context;
        res = resourse;
        objects = orderItems;
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
            view = lInflater.inflate(res, parent, false);
        }

        OrderItem orderItem = (OrderItem) getItem(position);

        String pattern = "##0.00";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);

        TextView pos = (TextView)view.findViewById(R.id.number_position);
        pos.setText(orderItem.getPosition().toString());

        TextView product = (TextView)view.findViewById(R.id.product);
        product.setText(orderItem.getName());

        TextView price = (TextView)view.findViewById(R.id.price_product);
        price.setText(decimalFormat.format(orderItem.getPrice()));

        TextView count = (TextView)view.findViewById(R.id.count_product);
        count.setText(orderItem.getCount().toString());

        TextView total  = (TextView)view.findViewById(R.id.total_price);
        total.setText(decimalFormat.format(orderItem.getTotal()));

        return view;
    }
}
