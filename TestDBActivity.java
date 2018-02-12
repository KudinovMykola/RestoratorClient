package com.kudinov.restoratorclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kudinov.restoratorclient.adapter.TestCatAdapter;
import com.kudinov.restoratorclient.adapter.TestDepartmentAdapter;
import com.kudinov.restoratorclient.adapter.TestProdAdapter;
import com.kudinov.restoratorclient.data.firebase.DepartmentFirebase;
import com.kudinov.restoratorclient.fakedata.FakeDataRequest;
import com.kudinov.restoratorclient.model.Category;
import com.kudinov.restoratorclient.model.Department;
import com.kudinov.restoratorclient.model.Product;

import java.util.ArrayList;
import java.util.List;


public class TestDBActivity extends AppCompatActivity {

    //1.UI elements
    private TextView _txtId;
    private TextView _txtName;
    private TextView _txtParent;

    private Button _bttnPush;

    private ListView _lvFirebase;
    private TestDepartmentAdapter _adapter;
    private List<Department> firebaseList;

    private ListView _lvCategory;
    private TestCatAdapter _catAdapter;
    private List<Category> _catList;

    private ListView _lvProduct;
    private TestProdAdapter _prodAdapter;
    private List<Product> _productList;

    //2.FireBase
    private FirebaseDatabase _firebaseDatabase;

    private DatabaseReference _departmentReference;
    private ChildEventListener _childEventListener;

    private DatabaseReference _categoryReference;
    private ChildEventListener _catEventListener;

    private DatabaseReference _productReference;
    private ChildEventListener _prodEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_db);

        //3. Connect UI elements
        _txtId = findViewById(R.id.txtId);
        _txtName = findViewById(R.id.txtName);
        _txtParent = findViewById(R.id.txtParent);

        _lvFirebase = findViewById(R.id.firebase_listview);


        _bttnPush = findViewById(R.id.bttnPush);

        _lvCategory = findViewById(R.id.f_category_listview);
        _lvProduct = findViewById(R.id.f_product_listview);
        //4. Init database
        _firebaseDatabase = FirebaseDatabase.getInstance();

        _departmentReference = _firebaseDatabase.getReference().child("departments");
        _categoryReference = _firebaseDatabase.getReference().child("categories");
        _productReference = _firebaseDatabase.getReference().child("products");

        //6. AdapterWork
        firebaseList = new ArrayList<>();
        _adapter = new TestDepartmentAdapter(TestDBActivity.this, R.layout.firebase_test, firebaseList);
        _lvFirebase.setAdapter(_adapter);

        _catList = new ArrayList<>();
        _catAdapter = new TestCatAdapter(TestDBActivity.this, R.layout.f_category_test, _catList);
        _lvCategory.setAdapter(_catAdapter);

        _productList = new ArrayList<>();
        _prodAdapter = new TestProdAdapter(TestDBActivity.this, R.layout.f_category_test, _productList);
        _lvProduct.setAdapter(_prodAdapter);

        //5. Create database listener
        if(_prodEventListener == null) {
            _prodEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Product prod = dataSnapshot.getValue(Product.class);
                    _prodAdapter.add(prod);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };

            _productReference.addChildEventListener(_prodEventListener);
        }

        if(_catEventListener == null) {
            _catEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Category cat = dataSnapshot.getValue(Category.class);
                    _catAdapter.add(cat);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };

            _categoryReference.addChildEventListener(_catEventListener);
        }


        if(_childEventListener == null) {
            _childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Department dep = dataSnapshot.getValue(Department.class);
                    _adapter.add(dep);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };

            _departmentReference.addChildEventListener(_childEventListener);
        }



        //7. add work
/*
        _bttnPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Category> catList = new FakeDataRequest().getAllCategory();
                for(Category cat: catList) {
                    _txtId.setText(String.valueOf(cat.getId()));
                    _txtName.setText(cat.getName());
                    _txtParent.setText(String.valueOf(cat.getDepartment_id()));

                    _categoryReference.push().setValue(cat);
                }

                List<Product> prodList = new FakeDataRequest().getAllProduct();
                for(Product prod: prodList) {
                    _txtId.setText(String.valueOf(prod.getId()));
                    _txtName.setText(prod.getName());
                    _txtParent.setText(String.valueOf(prod.getCategory_id()));

                    _productReference.push().setValue(prod);
                }

                _txtId.setText("");
                _txtName.setText("FINISH");
                _txtParent.setText("");
            }
        });
*/
    }

    //8. delete listener
    @Override
    protected void onDestroy() {
        if(_childEventListener != null) {
            _departmentReference.removeEventListener(_childEventListener);
            _childEventListener = null;
        }

        if(_catEventListener != null) {
            _categoryReference.removeEventListener(_catEventListener);
            _catEventListener = null;
        }

        if(_prodEventListener != null) {
            _productReference.removeEventListener(_prodEventListener);
            _prodEventListener = null;
        }
        super.onDestroy();
    }
}
