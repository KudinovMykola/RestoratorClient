package com.kudinov.restoratorclient.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kudinov.restoratorclient.R;
import com.kudinov.restoratorclient.model.Category;
import com.kudinov.restoratorclient.model.Product;

import java.util.List;

public class TestProdAdapter extends ArrayAdapter<Product> {
    public TestProdAdapter(@NonNull Context context, int resource, @NonNull List<Product> objects) {
        super(context, resource, objects);
    }


    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            convertView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.f_category_test,parent,false);
        }

        TextView txtId = convertView.findViewById(R.id.cat_id);
        TextView txtName = convertView.findViewById(R.id.cat_name);
        TextView txtDep = convertView.findViewById(R.id.cat_dep);

        Product prod = getItem(position);
        txtId.setText(String.valueOf(prod.getId()));
        txtName.setText(prod.getName());
        txtDep.setText(String.valueOf(prod.getCategory_id()));

        return  convertView;
    }
}
