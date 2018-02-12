package com.kudinov.restoratorclient.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kudinov.restoratorclient.R;
import com.kudinov.restoratorclient.model.Department;

import java.util.List;



public class TestDepartmentAdapter extends ArrayAdapter<Department> {

    public TestDepartmentAdapter(@NonNull Context context, int resource, List<Department> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.firebase_test, parent, false);
        }

        TextView txtId = convertView.findViewById(R.id.dep_id);
        TextView txtName = convertView.findViewById(R.id.dep_name);

        Department dep = getItem(position);

        txtId.setText(String.valueOf(dep.getId()));
        txtName.setText(dep.getName());

        return convertView;
    }
}
