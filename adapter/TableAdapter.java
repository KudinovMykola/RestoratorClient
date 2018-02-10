package com.kudinov.restoratorclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.kudinov.restoratorclient.R;
import com.kudinov.restoratorclient.datawaiter.Hall;
import com.kudinov.restoratorclient.datawaiter.Table;

import java.util.List;


public class TableAdapter extends BaseAdapter {
    private Context ctx;
    private LayoutInflater lInflater;
    private List<Table> tableList;
    private int selectPosition;

    public TableAdapter(Context context) {
        ctx = context;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        tableList = ((Hall)ctx.getApplicationContext()).getTables();
        selectPosition = Table.NON_CHECKED_POSITION;
    }

    @Override
    public int getCount() {
        return tableList.size();
    }

    @Override
    public Object getItem(int i) {
        return tableList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.hall_tablelist_item, parent, false);
        }

        Table currTable = (Table) getItem(i);
        TextView txtTitle = view.findViewById(R.id.tablelist_item_name);
        TextView txtPrice = view.findViewById(R.id.tablelist_item_sum);

        txtTitle.setText(currTable.getName());
        txtPrice.setText(String.valueOf(currTable.getOrderedSum()));

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

        view.setMinimumWidth(width);
        view.setMinimumHeight(height);

        if(selectPosition == Table.NON_CHECKED_POSITION)
            view.setBackgroundColor(ctx.getResources().getColor(R.color.categoryFocus));
        else{
            if(i == selectPosition)
                view.setBackgroundColor(ctx.getResources().getColor(R.color.product));
            else
                view.setBackgroundColor(ctx.getResources().getColor(R.color.categoryFocus));
        }

        return view;
    }

    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }
}
