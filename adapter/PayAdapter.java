package com.kudinov.restoratorclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kudinov.restoratorclient.R;
import com.kudinov.restoratorclient.item.ReceiptItem;

import java.util.List;

public class PayAdapter extends BaseAdapter{
    private Context ctx;
    private List<ReceiptItem> objects;
    private LayoutInflater lInflater;

    public PayAdapter(Context context, List<ReceiptItem> receiptItems) {
        ctx = context;
        objects = receiptItems;
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
        ReceiptItem receiptItem = (ReceiptItem) getItem(position);

        if (view == null) {
            view = lInflater.inflate(R.layout.pay_item, parent, false);
        }

        TextView pos =  view.findViewById(R.id.number_position);
        TextView name = view.findViewById(R.id.title_item);
        TextView count = view.findViewById(R.id.count_product);
        TextView price = view.findViewById(R.id.price_product);
        TextView total = view.findViewById(R.id.total_price);

        pos.setText(String.valueOf(receiptItem.get_position()));
        name.setText(receiptItem.get_title());
        count.setText(String.valueOf(receiptItem.get_count()));
        price.setText(String.valueOf(receiptItem.get_price()));
        total.setText(String.valueOf(receiptItem.get_total()));

        return view;
    }
}
