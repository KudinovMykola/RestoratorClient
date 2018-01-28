package com.kudinov.restoratorclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.kudinov.restoratorclient.adapter.CategoryAdapter;
import com.kudinov.restoratorclient.adapter.DepartmentAdapter;
import com.kudinov.restoratorclient.adapter.ProductAdapter;
import com.kudinov.restoratorclient.adapter.ReceiptAdapter;
import com.kudinov.restoratorclient.fakedata.FakeDataRequest;
import com.kudinov.restoratorclient.item.CategoryListItem;
import com.kudinov.restoratorclient.list.ReceiptList;
import com.kudinov.restoratorclient.model.Category;
import com.kudinov.restoratorclient.model.Department;
import com.kudinov.restoratorclient.model.Product;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    //data
    private FakeDataRequest data;

    //view's

    private ListView lvReceipts;

    private GridView gvDepartments;
    private ListView lvCategories;
    private GridView gvProducts;
    //Adapter's
    private ReceiptAdapter adapterReceipt;

    private DepartmentAdapter adapterDepartments;
    private CategoryAdapter adapterCategories;
    private ProductAdapter adapterProduct;

    //list's
    private ReceiptList receiptList;

    private List<List<CategoryListItem>> pointCategories;
    private List<CategoryListItem> emptyItemList;
    private List<Product> productList;

    TextView txtTotalSum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        data = new FakeDataRequest();
        this.createItemList();

        this.findStartElements();
        loadAdapters();
        txtTotalSum.setText(receiptList.getTotalSum().toString());

        gvDepartments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Department item = (Department) parent.getAdapter().getItem(position);
                adapterDepartments.setCheckItem(item, (ToggleButton)view);
                changeDepartment(item);
            }
        });

        lvCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CategoryListItem item = (CategoryListItem) parent.getAdapter().getItem(position);
                if(!item.isChecked()) {
                    adapterCategories.setCheckItem(item);
                    loadProducts(data.getProductByCategory(item.getCategory()));
                }
            }
        });

        gvProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product item = (Product) parent.getAdapter().getItem(position);
                receiptList.addToCurrentList(item, 1);
                adapterReceipt.notifyDataSetChanged();
                txtTotalSum.setText(String.valueOf(receiptList.getTotalSum()));
                lvReceipts.smoothScrollToPosition(receiptList.getCountItems());

            }
        });
    }

    //Create data
    private List<CategoryListItem> CategoryToCategoryListItem(List<Category>categories) {
        List<CategoryListItem> categoryListItems = new ArrayList<CategoryListItem>();
        for(Category category: categories) {
            categoryListItems.add(new CategoryListItem(category));
        }
        return categoryListItems;
    }
    private void createItemList() {
        pointCategories = new ArrayList<List<CategoryListItem>>();

        List<CategoryListItem> categoryItems = null;
        for(Department dep: data.getAllDepartment()) {
            categoryItems = CategoryToCategoryListItem(data.getCategoryByDepartment(dep));
            pointCategories.add(categoryItems);
        }
    }

    //Set ListViews and Adapters
    private void findStartElements(){
        lvReceipts = findViewById(R.id.receipt);

        gvDepartments = findViewById(R.id.department_panel);
        lvCategories = findViewById(R.id.category_panel);
        gvProducts = findViewById(R.id.product_panel);

        txtTotalSum = findViewById(R.id.total_sum);
    }
    private void loadAdapters() {
        receiptList = new ReceiptList(getString(R.string.ordered), getString(R.string.on_reserve), getString(R.string.current));
        adapterReceipt = new ReceiptAdapter(OrderActivity.this, R.layout.receipt_item, receiptList);
        lvReceipts.setAdapter(adapterReceipt);

        adapterDepartments = new DepartmentAdapter(OrderActivity.this, data.getAllDepartment());
        gvDepartments.setAdapter(adapterDepartments);

        emptyItemList = new ArrayList<CategoryListItem>();
        adapterCategories = new CategoryAdapter(OrderActivity.this, emptyItemList);
        lvCategories.setAdapter(adapterCategories);

        productList = new ArrayList<Product>();
        adapterProduct = new ProductAdapter(OrderActivity.this, productList);
        gvProducts.setAdapter(adapterProduct);
    }

    //ListViews control
    public void changeDepartment(Department department) {

        if(data.getCategoryByDepartment(department).size() < 1){
            clearProductView();
            clearCategoryView();
            return;
        }

        List<CategoryListItem> selectedList = pointCategories.get(department.getId());
        if (selectedList != null && selectedList.size() > 0) {
            loadCategories(selectedList);
            CategoryListItem checkItem = null;
            for(CategoryListItem item : selectedList) {
                if(item.isChecked())
                    checkItem = item;
            }

            if(checkItem == null) {
                clearProductView();
            }
            else
                loadProducts(data.getProductByCategory(checkItem.getCategory()));
        } else {
            clearProductView();
        }

    }

    private void loadCategories(List<CategoryListItem> categories){
        adapterCategories = new CategoryAdapter(OrderActivity.this, categories);
        lvCategories.setAdapter(adapterCategories);
    }
    private void clearCategoryView() {
        adapterCategories = new CategoryAdapter(OrderActivity.this, emptyItemList);
        lvCategories.setAdapter(adapterCategories);
    }

    private void loadProducts(List<Product> products){
        productList.clear();
        productList.addAll(products);
        adapterProduct.notifyDataSetChanged();

    }
    private void clearProductView() {
        if(productList != null){
            productList.clear();
            if(adapterProduct != null)
                adapterProduct.notifyDataSetChanged();
        }
    }

    //buttons
    public void sendOrder(View view) {
        receiptList.allToOrderList();
        adapterReceipt.notifyDataSetChanged();
    }
    public void createReserve(View view) {
        receiptList.currentToReserve();
        adapterReceipt.notifyDataSetChanged();
    }
    public void deleteCurrentOrder(View view) {
        receiptList.clearCurrentAndReserve();
        adapterReceipt.notifyDataSetChanged();


        txtTotalSum.setText(String.valueOf(receiptList.getTotalSum()));
    }
}
