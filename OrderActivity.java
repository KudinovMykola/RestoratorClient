package com.kudinov.restoratorclient;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.kudinov.restoratorclient.adapter.CategoryAdapter;
import com.kudinov.restoratorclient.adapter.DepartmentAdapter;
import com.kudinov.restoratorclient.adapter.ProductAdapter;
import com.kudinov.restoratorclient.adapter.ReceiptAdapter;
import com.kudinov.restoratorclient.datawaiter.Hall;
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
    private final String DIALOG_COUNT_SHOW = "d";
    private final String POSITION_SELECT_ITEM = "p";
    private final String COUNT_DIALOG = "c";

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

    AlertDialog dialog;
    EditText countCurrentitem;
    ReceiptItem selectedReceipt;
    int positionSelectReceiptItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        data = new FakeDataRequest();
        int posTable = ((Hall)getApplicationContext()).getCheckTable();
        currentTable = ((Hall)getApplicationContext()).getTables().get(posTable);

        receiptList = new ArrayList<>();

        this.findStartElements();
        departmentList.addAll(data.getAllDepartment());
        loadAdapters();
        createCountDialog();

        refreshReceipt();
        positionSelectReceiptItem = 0;
        refreshMenu();


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

        lvReceipts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                positionSelectReceiptItem = position;
                selectedReceipt = receiptList.get(position);
                if(selectedReceipt.get_type() == ReceiptItem.TypeReceipt.SUM)
                    return;

                setCountDialog();
                dialog.show();
            }
        });
    }

    //dialog
    private void createCountDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);
        builder.setTitle("");
        View dialogView = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_set_count, null);
        countCurrentitem = (EditText)dialogView.findViewById(R.id.count_product_dialog);

        Button bttnMinus = (Button)dialogView.findViewById(R.id.minus_count_dialog);
        bttnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer count = 0;
                String strCount = countCurrentitem.getText().toString();
                if(strCount.compareTo("") != 0)
                    count = Integer.parseInt(strCount);

                if(count != 0)
                    count--;
                countCurrentitem.setText(count.toString());
            }
        });

        Button bttnPlus = (Button) dialogView.findViewById(R.id.plus_count_dialog);
        bttnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer count = 0;
                String strCount = countCurrentitem.getText().toString();
                if(strCount.compareTo("") != 0)
                    count = Integer.parseInt(strCount);

                count++;
                countCurrentitem.setText(count.toString());
            }
        });


        Button bttnOk = (Button)dialogView.findViewById(R.id.ok_count_dialog);
        bttnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedReceipt == null)
                    return;

                Integer count = 0;
                String strCount = countCurrentitem.getText().toString();
                if(strCount.compareTo("") != 0)
                    count = Integer.parseInt(strCount);


                if(selectedReceipt.get_type() == ReceiptItem.TypeReceipt.ORDERED) {
                    if(count != 0)
                        currentTable.addProductToCurrentList(data.getProductById(selectedReceipt.get_id_product()), count);
                } else {

                    List<OrderElement> elementList = null;
                    if(selectedReceipt.get_type() == ReceiptItem.TypeReceipt.RESERVE) {
                        elementList = currentTable.getReserveList();
                    } else {
                        elementList = currentTable.getCurrentList();
                    }

                    OrderElement element = null;
                    for(OrderElement item: elementList) {
                        if(item.getProduct_id().equals(selectedReceipt.get_id_product())) {
                            element = item;
                            break;
                        }
                    }

                    if(element != null) {
                        if(count != 0)
                            element.setCount(count);
                        else
                            elementList.remove(element);
                    }
                }

                refreshReceipt();
                dialog.dismiss();
            }
        });

        builder.setView(dialogView);
        dialog = builder.create();
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            params.gravity = Gravity.BOTTOM;
            params.y = 50;
        } else {
            params.gravity = Gravity.RIGHT;
            params.x = 30;
        }
        dialog.getWindow().setAttributes(params);
    }
    private void setCountDialog() {
        int count = 0;
        StringBuilder titleBuilder = new StringBuilder();
        if(selectedReceipt.get_type() == ReceiptItem.TypeReceipt.ORDERED)
            titleBuilder.append(getString(R.string.add_count_dialog)).append(" ");
        else {
            count = selectedReceipt.get_count();
            titleBuilder.append(getString(R.string.change_count_dialog)).append(" ");

            if(selectedReceipt.get_type() == ReceiptItem.TypeReceipt.RESERVE)
                titleBuilder.append(getString(R.string.reserve_count_dialog)).append(" ");
            else
                titleBuilder.append(getString(R.string.current_count_dialog)).append(" ");
        }
        titleBuilder.append(selectedReceipt.get_title());

        dialog.setTitle(titleBuilder.toString());
        countCurrentitem.setText(String.valueOf(count));
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

        if(currentTable.getCheckDepartmentPosition() == Table.NON_CHECKED_POSITION)
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
        Float currentSum = 0f;
        Float totalSum = 0f;
        receiptList.clear();

        if(currentTable.getOrderedList().size() != 0) {
            for(OrderElement order: currentTable.getOrderedList()) {
                Product product = data.getProductById(order.getProduct_id());
                position++;
                Float itemTotal = product.getPrice() * order.getCount();
                receiptList.add(new ReceiptItem(position, product.getId(),
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
                receiptList.add(new ReceiptItem(position, product.getId(),
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
                receiptList.add(new ReceiptItem(position, product.getId(),
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
        //outState.putParcelable("table",currentTable);
        if(dialog.isShowing()) {
            outState.putBoolean(DIALOG_COUNT_SHOW,true);
            outState.putInt(POSITION_SELECT_ITEM,positionSelectReceiptItem);
            outState.putString(COUNT_DIALOG,countCurrentitem.getText().toString());
        } else
            outState.putBoolean(DIALOG_COUNT_SHOW,false);

    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState.getBoolean(DIALOG_COUNT_SHOW)) {
            positionSelectReceiptItem = savedInstanceState.getInt(POSITION_SELECT_ITEM);
            selectedReceipt = receiptList.get(positionSelectReceiptItem);
            setCountDialog();
            countCurrentitem.setText(savedInstanceState.getString(COUNT_DIALOG));
            dialog.show();
        }
    }

    @Override
    public void onBackPressed() {
        ((Hall)getApplicationContext()).setCheckTable(Table.NON_CHECKED_POSITION);
        Intent intent = new Intent(OrderActivity.this, HallActivity.class);
        startActivity(intent);
    }

    public void showPayActivity(View view) {
        if(currentTable.getOrderedSum() == 0f)
            Toast.makeText(OrderActivity.this, getText(R.string.toast_empty_receipt), Toast.LENGTH_LONG).show();
        else {
            Intent intent = new Intent(OrderActivity.this, PayActivity.class);
            startActivity(intent);
        }
    }
}
