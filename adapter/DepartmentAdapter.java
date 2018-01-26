package com.kudinov.restoratorclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ToggleButton;

import com.kudinov.restoratorclient.R;
import com.kudinov.restoratorclient.model.Department;

import java.util.List;

public class DepartmentAdapter extends BaseAdapter{
    private Context ctx;
    private LayoutInflater lInflater;
    private List<Department> objects;
    private ToggleButton checkedView;

    public DepartmentAdapter(Context context, List<Department> departments) {
        ctx = context;
        objects = departments;
        checkedView = null;
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

        return bttn;
    }

    public void setCheckItem(Department item, ToggleButton bttn) {
        if(checkedView != null) {
            checkedView.setChecked(false);
        }
        this.checkedView = bttn;
        bttn.setChecked(true);
    }
}
