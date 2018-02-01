package com.kudinov.restoratorclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.kudinov.restoratorclient.adapter.CategoryAdapter;
import com.kudinov.restoratorclient.adapter.DepartmentAdapter;
import com.kudinov.restoratorclient.adapter.ProductAdapter;
import com.kudinov.restoratorclient.adapter.ReceiptAdapter;
import com.kudinov.restoratorclient.datawaiter.OrderElement;
import com.kudinov.restoratorclient.datawaiter.Table;
import com.kudinov.restoratorclient.fakedata.FakeDataRequest;
import com.kudinov.restoratorclient.item.ReceiptItem;
import com.kudinov.restoratorclient.model.Category;
import com.kudinov.restoratorclient.model.Department;
import com.kudinov.restoratorclient.model.Product;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    //data
    private FakeDataRequest data;
    private Table currentTable;
    private Float total;
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
    private List<ReceiptItem> receiptList;
    final private List<Department> departmentList = new ArrayList<>();
    final private List<Category> categoryList = new ArrayList<>();
    final private List<Product> productList = new ArrayList<>();

    TextView txtTotalSum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        data = new FakeDataRequest();
        currentTable = new Table("Test Table", data.getAllDepartment().size());

        createMenu();
        receiptList = new ArrayList<>();

        this.findStartElements();
        departmentList.addAll(data.getAllDepartment());
        loadAdapters();


        gvDepartments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentTable.setCheckDepartmentPosition(position);
                refreshMenu();
            }
        });

        lvCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentTable.getArrCheckCategory()[currentTable.getCheckDepartmentPosition()] = position;
                refreshMenu();
            }
        });

        gvProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product item = (Product) parent.getAdapter().getItem(position);
                currentTable.addProductToCurrentList(item,1);
                refreshReceipt();
                lvReceipts.smoothScrollToPosition(receiptList.size() - 1);
            }
        });
    }

    //refresh info
    private void refreshReceipt() {
        createReceiptList();
        adapterReceipt.notifyDataSetChanged();

        txtTotalSum.setText(String.valueOf(total));
    }
    private void refreshMenu() {
        createMenu();

        adapterDepartments.setCheckPosition(currentTable.getCheckDepartmentPosition());
        adapterDepartments.notifyDataSetChanged();


        if(currentTable.getArrCheckCategory()[currentTable.getCheckDepartmentPosition()] == Table.NON_CHECKED_POSITION)
            adapterCategories.setCheckPosition(Table.NON_CHECKED_POSITION);
        else
            adapterCategories.setCheckPosition(currentTable.getArrCheckCategory()[currentTable.getCheckDepartmentPosition()]);
        adapterCategories.notifyDataSetChanged();

        adapterProduct.notifyDataSetChanged();

    }

    //Set ListViews and Adapters
    private void findStartElements(){
        lvReceipts = findViewById(R.id.receipt);

        gvDepartments = findViewById(R.id.department_panel);
        lvCategories = findViewById(R.id.category_panel);
        gvProducts = findViewById(R.id.product_panel);

        txtTotalSum = findViewById(R.id.total_sum);
    }
    private void createReceiptList() {
        Integer position = 0;
        //Float itemTotal = 0f;
        Float currentSum = 0f;
        Float totalSum = 0f;
        receiptList.clear();
        //Product product = null;

        if(currentTable.getOrderedList().size() != 0) {
            for(OrderElement order: currentTable.getOrderedList()) {
                Product product = data.getProductById(order.getProduct_id());
                position++;
                Float itemTotal = product.getPrice() * order.getCount();
                receiptList.add(new ReceiptItem(position,
                        product.getName(), product.getPrice(),
                        order.getCount(), itemTotal, ReceiptItem.TypeReceipt.ORDERED));
                currentSum += itemTotal;
            }
            receiptList.add(new ReceiptItem(getString(R.string.ordered), currentSum));
            totalSum += currentSum;
            currentSum = 0f;
        }

        if(currentTable.getReserveList().size() != 0) {
            for(OrderElement order: currentTable.getReserveList()) {
                Product product = data.getProductById(order.getProduct_id());
                position++;
                Float itemTotal = product.getPrice() * order.getCount();
                receiptList.add(new ReceiptItem(position,
                        product.getName(), product.getPrice(),
                        order.getCount(), itemTotal, ReceiptItem.TypeReceipt.RESERVE));
                currentSum += itemTotal;
            }
            receiptList.add(new ReceiptItem(getString(R.string.on_reserve), currentSum));
            totalSum += currentSum;
            currentSum = 0f;
        }

        if(currentTable.getCurrentList().size() != 0) {
            for(OrderElement order: currentTable.getCurrentList()) {
                Product product = data.getProductById(order.getProduct_id());
                position++;
                Float itemTotal = product.getPrice() * order.getCount();
                receiptList.add(new ReceiptItem(position,
                        product.getName(), product.getPrice(),
                        order.getCount(), itemTotal, ReceiptItem.TypeReceipt.CURRENT));
                currentSum += itemTotal;
            }
            receiptList.add(new ReceiptItem(getString(R.string.current), currentSum));
            totalSum += currentSum;
        }

        total = totalSum;
    }
    private void createMenu() {
        categoryList.clear();
        productList.clear();

        if(currentTable.getCheckDepartmentPosition() >= departmentList.size())
            currentTable.setCheckDepartmentPosition(Table.NON_CHECKED_POSITION);

        if(currentTable.getCheckDepartmentPosition() != Table.NON_CHECKED_POSITION) {
            categoryList.addAll(data.getCategoryByDepartment(departmentList.get(
                    currentTable.getCheckDepartmentPosition())));


            if(currentTable.getArrCheckCategory()[currentTable.getCheckDepartmentPosition()] >= categoryList.size())
                currentTable.getArrCheckCategory()[currentTable.getCheckDepartmentPosition()] = Table.NON_CHECKED_POSITION;

            if(currentTable.getArrCheckCategory()[currentTable.getCheckDepartmentPosition()] != Table.NON_CHECKED_POSITION){
                productList.addAll(data.getProductByCategory(categoryList.get(
                        currentTable.getArrCheckCategory()[currentTable.getCheckDepartmentPosition()])));
            }
        }
    }
    private void loadAdapters() {
        createReceiptList();
        adapterReceipt = new ReceiptAdapter(OrderActivity.this, R.layout.receipt_item, receiptList);
        lvReceipts.setAdapter(adapterReceipt);

        adapterDepartments = new DepartmentAdapter(OrderActivity.this, departmentList,
                currentTable.getCheckDepartmentPosition());
        gvDepartments.setAdapter(adapterDepartments);

        adapterCategories = new CategoryAdapter(OrderActivity.this, categoryList,
                Table.NON_CHECKED_POSITION);
        lvCategories.setAdapter(adapterCategories);

        adapterProduct = new ProductAdapter(OrderActivity.this, productList);
        gvProducts.setAdapter(adapterProduct);
    }

    //buttons
    public void sendOrder(View view) {
        currentTable.allToOrderList();
        refreshReceipt();
    }
    public void createReserve(View view) {
        currentTable.currentToReserve();
        refreshReceipt();
    }
    public void deleteCurrentOrder(View view) {
        currentTable.clearCurrentAndReserve();
        refreshReceipt();
    }

    //instanse work
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("table",currentTable);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentTable = savedInstanceState.getParcelable("table");
        refreshReceipt();
        refreshMenu();

    }
}
