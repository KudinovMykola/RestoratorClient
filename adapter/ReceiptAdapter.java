package com.kudinov.restoratorclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kudinov.restoratorclient.R;
import com.kudinov.restoratorclient.item.ReceiptItem;

import java.text.DecimalFormat;
import java.util.List;

public class ReceiptAdapter extends BaseAdapter {

    private Context ctx;
    private List<ReceiptItem> objects;
    private LayoutInflater lInflater;
    private Integer res;

    public ReceiptAdapter(Context context, int resourse, List<ReceiptItem> receiptItems) {
        ctx = context;
        res = resourse;
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
            view = lInflater.inflate(res, parent, false);
        }


        TextView pos =  view.findViewById(R.id.number_position);
        TextView name = view.findViewById(R.id.title_item);
        TextView count = view.findViewById(R.id.count_product);
        TextView price = view.findViewById(R.id.price_product);
        TextView total = view.findViewById(R.id.total_price);

        total.setText(receiptItem.get_total());
        name.setText(receiptItem.get_title());
        if(receiptItem.get_type() == ReceiptItem.TypeReceipt.SUM) {
            pos.setText("");
            count.setText("");
            price.setText("");

            view.setBackgroundColor(ctx.getResources().getColor(R.color.categoryNonFocus));
            return view;
        }

        pos.setText(receiptItem.get_position());
        count.setText(receiptItem.get_count());
        price.setText(receiptItem.get_price());

        if (receiptItem.get_type() == ReceiptItem.TypeReceipt.ORDERED) {
            view.setBackgroundColor(ctx.getResources().getColor(R.color.doneOrder));
        } else if(receiptItem.get_type() == ReceiptItem.TypeReceipt.RESERVE){
            view.setBackgroundColor(ctx.getResources().getColor(R.color.reserveOrder));
        } else {
            view.setBackgroundColor(ctx.getResources().getColor(R.color.currentOrder));
        }

        return view;
    }
}
