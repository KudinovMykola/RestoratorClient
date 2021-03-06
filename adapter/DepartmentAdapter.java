package com.kudinov.restoratorclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ToggleButton;

import com.kudinov.restoratorclient.R;
import com.kudinov.restoratorclient.datawaiter.Table;
import com.kudinov.restoratorclient.model.Category;
import com.kudinov.restoratorclient.model.Department;

import java.util.List;

public class DepartmentAdapter extends BaseAdapter{
    private Context ctx;
    private LayoutInflater lInflater;
    private List<Department> objects;
    private int checkPosition;

    public DepartmentAdapter(Context context, List<Department> departments, int checkPos) {
        ctx = context;
        objects = departments;
        checkPosition = checkPos;
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
            view = lInflater.inflate(R.layout.department, parent, false);
        }

        Department someDepartment = (Department) getItem(position);

        ToggleButton bttn = (ToggleButton) view;
        bttn.setText(someDepartment.getName());
        bttn.setTextOff(someDepartment.getName());
        bttn.setTextOn(someDepartment.getName());

        bttn.setFocusable(false);
        bttn.setClickable(false);

        if(position == checkPosition)
            bttn.setChecked(true);
        else
            bttn.setChecked(false);

        return bttn;
    }

    public int getCheckPosition() {
        return checkPosition;
    }
    public void setCheckPosition(int checkPosition) {
        this.checkPosition = checkPosition;
    }
}
