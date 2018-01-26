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
import android.widget.Toast;

import com.kudinov.restoratorclient.adapter.CategoryAdapter;
import com.kudinov.restoratorclient.adapter.DepartmentAdapter;
import com.kudinov.restoratorclient.adapter.OrderAdapter;
import com.kudinov.restoratorclient.adapter.ProductAdapter;
import com.kudinov.restoratorclient.fakedata.FakeDataRequest;
import com.kudinov.restoratorclient.item.CategoryListItem;
import com.kudinov.restoratorclient.item.OrderItem;
import com.kudinov.restoratorclient.model.Category;
import com.kudinov.restoratorclient.model.Department;
import com.kudinov.restoratorclient.model.Product;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    //data
    private FakeDataRequest data;

    //view's
    private LinearLayout llPanel;

    private ListView lvDoneOrders;
    private ListView lvReserveOrders;
    private ListView lvCurrentOrders;

    private GridView gvDepartments;
    private ListView lvCategories;
    private GridView gvProducts;
    //Adapter's
    private OrderAdapter adapterDoneOrders;
    private OrderAdapter adapterReserveOrders;
    private OrderAdapter adapterCurrentOrders;

    private DepartmentAdapter adapterDepartments;
    private CategoryAdapter adapterCategories;
    private ProductAdapter adapterProduct;

    //list's
    private List<OrderItem> orderDoneList;
    private List<OrderItem> orderReserveList;
    private List<OrderItem> orderCurrentList;

    private List<List<CategoryListItem>> pointCategories;
    private List<CategoryListItem> emptyItemList;
    private List<Product> productList;

    //info
    private Integer countOrderItems;
    private Float currentSum;
    private Float totalSum;

    private TextView txtOrderedSum;
    private TextView txtReserveSum;
    private TextView txtCurrentSum;
    private TextView txtTotalSum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        currentSum = 0f;
        totalSum = 0f;
        countOrderItems = 1;

        data = new FakeDataRequest();
        this.createItemList();

        this.findStartElements();
        loadAdapters();
        refreshInfo();

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
                addCurrentOrderItem(item);
                currentSum += item.getPrice();
                totalSum += item.getPrice();

                txtCurrentSum.setText(currentSum.toString());
                txtTotalSum.setText(totalSum.toString());
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
        lvDoneOrders = findViewById(R.id.done_order);
        lvReserveOrders = findViewById(R.id.reserve_order);
        lvCurrentOrders = findViewById(R.id.current_order);

        gvDepartments = findViewById(R.id.department_panel);
        lvCategories = findViewById(R.id.category_panel);
        gvProducts = findViewById(R.id.product_panel);

        llPanel = findViewById(R.id.choose_panel);

        txtOrderedSum = findViewById(R.id.order_sum);
        txtReserveSum = findViewById(R.id.reserve_sum);
        txtCurrentSum = findViewById(R.id.current_sum);
        txtTotalSum = findViewById(R.id.total_sum);
    }
    private void loadAdapters() {
        orderDoneList = new ArrayList<OrderItem>();
        adapterDoneOrders = new OrderAdapter(OrderActivity.this, R.layout.done_order,orderDoneList);
        lvDoneOrders.setAdapter(adapterDoneOrders);

        orderReserveList = new ArrayList<OrderItem>();
        adapterReserveOrders = new OrderAdapter(OrderActivity.this,R.layout.reserve_order,orderReserveList);
        lvReserveOrders.setAdapter(adapterReserveOrders);

        orderCurrentList = new ArrayList<OrderItem>();
        adapterCurrentOrders = new OrderAdapter(OrderActivity.this, R.layout.current_order,orderCurrentList);
        lvCurrentOrders.setAdapter(adapterCurrentOrders);


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
    public void addCurrentOrderItem(Product product) {
        addOrderItem(orderCurrentList,product,1);
        adapterCurrentOrders.notifyDataSetChanged();
    }

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

    //info methods
    public Float getTotalSum() {
        Float res = 0f;
        res += getSumOfList(orderDoneList);
        res += getSumOfList(orderReserveList);
        res += getSumOfList(orderCurrentList);

        return res;
    }
    public Float getSumOfList(List<OrderItem> list) {
        Float sum = 0f;
        for(OrderItem item: list) {
            sum += item.getTotal();
        }
        return sum;
    }

    public void setPositionOnLists() {
        countOrderItems = 1;
        for(OrderItem item: orderDoneList) {
            item.setPosition(countOrderItems++);
        }

        for(OrderItem item: orderReserveList) {
            item.setPosition(countOrderItems++);
        }

        for(OrderItem item: orderCurrentList) {
            item.setPosition(countOrderItems++);
        }
    }
    public void refreshInfo() {
        setPositionOnLists();

        Float orderedSum = getSumOfList(orderDoneList);
        Float reserveSum = getSumOfList(orderReserveList);
        Float currSum = getSumOfList(orderCurrentList);
        Float totalSum = orderedSum + reserveSum + currentSum;

        this.currentSum = currSum;

        txtOrderedSum.setText(orderedSum.toString());
        txtReserveSum.setText(reserveSum.toString());
        txtCurrentSum.setText(currSum.toString());
        txtTotalSum.setText(totalSum.toString());
    }

    //support order list's
    public void complementOrderList(List<OrderItem> baseList, List<OrderItem> addedList) {
        if (addedList == null & addedList.size() == 0)
            return;

        if(baseList.size() == 0) {
            baseList.addAll(addedList);
        } else {
            for (OrderItem item: addedList) {
                addOrderItem(baseList, item.getProduct(), item.getCount());
            }
        }
    }
    public void addOrderItem(List<OrderItem> itemList, Product product, Integer count) {
        OrderItem item = findOrderItemByProduct(itemList,product);
        if(item != null) {
            Integer curCount = item.getCount();
            item.setCount(curCount += count);
            return;
        }

        item = new OrderItem(countOrderItems++, product, count);
        itemList.add(item);

    }
    private OrderItem findOrderItemByProduct(List<OrderItem> itemList, Product product) {
        for(OrderItem item: itemList) {
            if(product.getId() == item.getProduct().getId()) {
                return item;
            }
        }
        return null;
    }

    //buttons
    public void sendOrder(View view) {
        List<OrderItem> sended = new ArrayList<>();

        complementOrderList(sended,orderReserveList);
        orderReserveList.clear();
        complementOrderList(sended,orderCurrentList);
        orderCurrentList.clear();

        //send sended to Serve todo

        complementOrderList(orderDoneList, sended);

        refreshInfo();

        adapterDoneOrders.notifyDataSetChanged();
    }

    public void createReserve(View view) {

        complementOrderList(orderReserveList,orderCurrentList);
        orderCurrentList.clear();


        currentSum = 0f;
        refreshInfo();

        adapterCurrentOrders.notifyDataSetChanged();
        adapterReserveOrders.notifyDataSetChanged();
    }

    public void deleteCurrentOrder(View view) {
        orderCurrentList.clear();
        adapterCurrentOrders.notifyDataSetChanged();

        currentSum = 0f;
        refreshInfo();
    }
}
