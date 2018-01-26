package com.kudinov.restoratorclient.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.ToggleButton;

import com.kudinov.restoratorclient.R;
import com.kudinov.restoratorclient.item.CategoryListItem;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {
    private Context ctx;
    private LayoutInflater lInflater;
    private List<CategoryListItem> objects;
    private CategoryListItem checkItem;
    private ToggleButton checkedView;

    public CategoryAdapter(Context context, List<CategoryListItem> categories) {
        ctx = context;
        objects = categories;

        checkItem = null;
        checkedView = null;

        for(CategoryListItem item: categories) {
            if(item.isChecked()) {
                checkItem = item;
                break;
            }
        }

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
            view = lInflater.inflate(R.layout.category, parent, false);
        }

        CategoryListItem someCategory = (CategoryListItem)getItem(position);

        ToggleButton bttn = (ToggleButton) view;
        bttn.setText(someCategory.getCategory().getName());
        bttn.setTextOff(someCategory.getCategory().getName());
        bttn.setTextOn(someCategory.getCategory().getName());

        bttn.setFocusable(false);
        bttn.setClickable(false);


        if(someCategory.isChecked() == true){
            bttn.setChecked(true);
        } else {
            bttn.setChecked(false);
        }

        return bttn;
    }

    public void setCheckItem(CategoryListItem item) {
        if(checkItem != null) {
            checkItem.unCheck();
        }
        this.checkItem = item;

        item.check();
        this.notifyDataSetChanged();
    }

}
