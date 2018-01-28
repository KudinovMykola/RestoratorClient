package com.kudinov.restoratorclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kudinov.restoratorclient.R;
import com.kudinov.restoratorclient.item.ReceiptItem;
import com.kudinov.restoratorclient.list.ReceiptList;

import java.text.DecimalFormat;

public class ReceiptAdapter extends BaseAdapter {

    private Context ctx;
    private ReceiptList objects;
    private LayoutInflater lInflater;
    private Integer res;

    public ReceiptAdapter(Context context, int resourse, ReceiptList receiptItems) {
        ctx = context;
        res = resourse;
        objects = receiptItems;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.getReceiptItems().size();
    }

    @Override
    public Object getItem(int position) {
        return objects.getReceiptItems().get(position);
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
        String pattern = "##0.00";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);

        TextView pos =  view.findViewById(R.id.number_position);
        TextView name = view.findViewById(R.id.title_item);
        TextView count = view.findViewById(R.id.count_product);
        TextView price = view.findViewById(R.id.price_product);
        TextView total = view.findViewById(R.id.total_price);

        if (receiptItem.getType() == ReceiptItem.TypeItem.ORDER) {
            pos.setText(String.valueOf(receiptItem.getOrder().getPosition()));
            name.setText(receiptItem.getOrder().getName());
            count.setText(String.valueOf(receiptItem.getOrder().getCount()));
            price.setText(decimalFormat.format(receiptItem.getOrder().getPrice()));
            total.setText(decimalFormat.format(receiptItem.getOrder().getTotal()));

            view.setBackgroundColor(ctx.getResources()
                    .getColor(getResByPosition(position)));
        } else {
            pos.setText("");
            name.setText(receiptItem.getSum().getTitle());
            count.setText("");
            price.setText("");
            total.setText(decimalFormat.format(receiptItem.getSum().getSum()));

            view.setBackgroundColor(ctx.getResources().getColor(R.color.categoryNonFocus));
        }
        return view;
    }

    private int getResByPosition(int position) {
        Integer orderedS = objects.getOrderedList().size();
        Integer reserveS = objects.getReserveList().size();

        if(orderedS == 0 && reserveS == 0)
            return R.color.currentOrder;

        if(orderedS == 0 && reserveS < position)
            return R.color.currentOrder;

        if(reserveS == 0 && orderedS < position )
            return R.color.currentOrder;

        if(orderedS == 0 && reserveS > position)
            return R.color.reserveOrder;

        if(reserveS == 0 && orderedS > position )
            return R.color.doneOrder;

        if(position < orderedS)
            return R.color.doneOrder;

        if(position < reserveS + orderedS +1 )
            return  R.color.reserveOrder;


        return R.color.currentOrder;

    }
}
