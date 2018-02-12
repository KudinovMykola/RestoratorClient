package com.kudinov.restoratorclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kudinov.restoratorclient.adapter.TestCatAdapter;
import com.kudinov.restoratorclient.adapter.TestDepartmentAdapter;
import com.kudinov.restoratorclient.adapter.TestProdAdapter;
import com.kudinov.restoratorclient.data.InternalDatabase;
import com.kudinov.restoratorclient.data.entities.CategoryEntity;
import com.kudinov.restoratorclient.data.entities.DepartmentEntity;
import com.kudinov.restoratorclient.data.entities.ProductEntity;
import com.kudinov.restoratorclient.datawaiter.Menu;
import com.kudinov.restoratorclient.fakedata.FakeDataRequest;
import com.kudinov.restoratorclient.model.Category;
import com.kudinov.restoratorclient.model.Department;
import com.kudinov.restoratorclient.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TestRoomActivity extends AppCompatActivity {

    //1.UI elements
    private TextView _txtId;
    private TextView _txtName;
    private TextView _txtParent;

    private Button _bttnPush;

    private ListView _lvDepartment;
    private TestDepartmentAdapter _depAdapter;
    private List<Department> _departmentList;

    private ListView _lvCategory;
    private TestCatAdapter _catAdapter;
    private List<Category> _catList;

    private ListView _lvProduct;
    private TestProdAdapter _prodAdapter;
    private List<Product> _productList;

    private String strErr;

    private static final String TAG = "myLogs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_room);

        //3. Connect UI elements
        _txtId = findViewById(R.id.r_txtId);
        _txtName = findViewById(R.id.r_txtName);
        _txtParent = findViewById(R.id.r_txtParent);

        _lvDepartment = findViewById(R.id.r_department_listview);
        _lvCategory = findViewById(R.id.r_category_listview);
        _lvProduct = findViewById(R.id.r_product_listview);

        _bttnPush = findViewById(R.id.bttnUp);

        //6. AdapterWork
        _departmentList = new ArrayList<>();
        _depAdapter = new TestDepartmentAdapter(TestRoomActivity.this, R.layout.firebase_test, _departmentList);
        _lvDepartment.setAdapter(_depAdapter);

        _catList = new ArrayList<>();
        _catAdapter = new TestCatAdapter(TestRoomActivity.this, R.layout.f_category_test, _catList);
        _lvCategory.setAdapter(_catAdapter);

        _productList = new ArrayList<>();
        _prodAdapter = new TestProdAdapter(TestRoomActivity.this, R.layout.f_category_test, _productList);
        _lvProduct.setAdapter(_prodAdapter);

        _bttnPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                          //  pushProduct();
                          //  pushCategories();
                          //  pushDepartments();
                        } catch (Exception ex) {
                            Log.d(TAG, ex.getMessage());
                            return;
                        }
                        Log.d(TAG, "HERE");

                    }
                });

                th.start();
            }
        });

        Thread thRefresh = new Thread(new Runnable() {
            @Override
            public void run() {
               printDepartment();
               printCategory();
               printProduct();
            }
        });

        thRefresh.start();
    }

    private void pushProduct() {
        List<Product> productList = new FakeDataRequest().getAllProduct();
        for (Product p : productList) {
            ProductEntity entity = new ProductEntity(p.getId(), p.getName(), p.getPrice(), p.getCategory_id());
            Menu.getInstance().get_db().productDao().insertProduct(entity);
        }
    }
    private void pushCategories() {
        List<Category> categoryList = new FakeDataRequest().getAllCategory();
        for(Category c: categoryList){
            CategoryEntity entity = new CategoryEntity(c.getId(),c.getName(),c.getDepartment_id());
            Menu.getInstance().get_db().categoryDao().insertCategory(entity);
        }
    }
    private void pushDepartments() {
        List<Department> departmentList = new FakeDataRequest().getAllDepartment();
        for(Department d: departmentList){
            DepartmentEntity entity = new DepartmentEntity(d.getId(),d.getName());
            Menu.getInstance().get_db().departmentDao().insertDepartment(entity);
        }
    }

    private void printProduct() {
        List<ProductEntity> entityList = Menu.getInstance().get_db().productDao().getAll();
        for (ProductEntity entity : entityList) {
            Product product = new Product(entity.getId(), entity.getName(), entity.getPrice(), entity.getCategory_id());
            _prodAdapter.add(product);
        }
    }
    private void printCategory() {
        List<CategoryEntity> entityList = Menu.getInstance().get_db().categoryDao().getAll();
        for (CategoryEntity entity : entityList) {
            Category category = new Category(entity.getId(), entity.getName(), entity.getDepartment_id());
            _catAdapter.add(category);
        }
    }
    private void printDepartment() {
        List<DepartmentEntity> entityList = Menu.getInstance().get_db().departmentDao().getAll();
        for (DepartmentEntity entity : entityList) {
            Department department = new Department(entity.getId(), entity.getName());
            _depAdapter.add(department);
        }
    }
}
