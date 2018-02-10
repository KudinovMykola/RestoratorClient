package com.kudinov.restoratorclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.kudinov.restoratorclient.adapter.PayAdapter;
import com.kudinov.restoratorclient.datawaiter.Hall;
import com.kudinov.restoratorclient.datawaiter.OrderElement;
import com.kudinov.restoratorclient.datawaiter.Table;
import com.kudinov.restoratorclient.fakedata.FakeDataRequest;
import com.kudinov.restoratorclient.item.ReceiptItem;
import com.kudinov.restoratorclient.model.Product;

import java.util.ArrayList;
import java.util.List;

public class PayActivity extends AppCompatActivity {
    ListView lvReceipt;
    TextView txtTotal;

    FakeDataRequest dataRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        int posTable = ((Hall)getApplicationContext()).getCheckTable();

        dataRequest = new FakeDataRequest();
        lvReceipt = findViewById(R.id.pay_receipt);
        txtTotal = findViewById(R.id.pay_total);

        Table table = ((Hall)getApplicationContext()).getTables().get(posTable);
        List<OrderElement> orders = table.getOrderedList();

        List<ReceiptItem> receipt = createList(orders);
        PayAdapter adapter = new PayAdapter(PayActivity.this, receipt);
        lvReceipt.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        txtTotal.setText(String.valueOf(table.getOrderedSum()));
    }

    private List<ReceiptItem> createList(List<OrderElement> orderList) {
        List<ReceiptItem> itemList = new ArrayList<>();
        int posItem = 0;

        for(OrderElement order: orderList) {
            Product product = dataRequest.getProductById(order.getProduct_id());
            if(product == null)
                continue;

            posItem++;
            float total = order.getPrice() * order.getCount();
            itemList.add(new ReceiptItem(posItem, order.getProduct_id(), product.getName(), order.getPrice(),
                    order.getCount(), total, ReceiptItem.TypeReceipt.ORDERED));
        }

        return itemList;
    }

    public void showPayDialog(View view) {
        Hall hall = (Hall) getApplicationContext();

        int pos = hall.getCheckTable();
        hall.setCheckTable(Table.NON_CHECKED_POSITION);
        hall.payTable(pos);

        Intent intent = new Intent(PayActivity.this, HallActivity.class);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PayActivity.this, OrderActivity.class);
        startActivity(intent);
    }
}
