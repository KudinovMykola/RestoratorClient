package com.kudinov.restoratorclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;

import com.kudinov.restoratorclient.adapter.TableAdapter;
import com.kudinov.restoratorclient.datawaiter.Hall;
import com.kudinov.restoratorclient.datawaiter.Table;

public class HallActivity extends AppCompatActivity {
    final String DIALOG_SHOW = "d";
    final String POSITION_SELECT_ITEM = "p";
    final String NAME_DIALOG = "n";

    GridView gvTableList;
    TableAdapter tableAdapter;

    TextView txtCountTable;
    TextView txtOutput;
    TextView txtOrder;
    TextView txtReserve;
    TextView txtCurrent;
    TextView txtTotal;
    View tableInfo;
    Button bttnDelete;

    AlertDialog dialog;
    EditText nameForNewTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall);

        if(((Hall)getApplicationContext()).getCheckTable() != Table.NON_CHECKED_POSITION) {
            Intent intent = new Intent(HallActivity.this, OrderActivity.class);
            startActivity(intent);
        }

        gvTableList = findViewById(R.id.hall_tablelist);
        tableAdapter = new TableAdapter(HallActivity.this);
        gvTableList.setAdapter(tableAdapter);
        createDialog();
        findInfoElements();
        refreshInfo();

        gvTableList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(tableAdapter.getSelectPosition() != i) {
                    tableAdapter.setSelectPosition(i);
                    tableAdapter.notifyDataSetChanged();
                    refreshInfo();
                } else {
                    ((Hall)getApplicationContext()).setCheckTable(i);
                    Intent intent = new Intent(HallActivity.this, OrderActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void createDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(HallActivity.this);
        builder.setTitle(getString(R.string.dialog_setnametable_title));
        View dialogView = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_set_nametable, null);
        nameForNewTable = (EditText) dialogView.findViewById(R.id.name_new_table);

        Button bttn = (Button) dialogView.findViewById(R.id.add_table);

        bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameForNewTable.getText().toString();
                if(name.length() == 0) {
                    Toast.makeText(HallActivity.this, getString(R.string.toast_enter_tablename), Toast.LENGTH_SHORT).show();
                    return;
                }

                if(name.length() > 30) {
                    Toast.makeText(HallActivity.this, getString(R.string.toast_max_symbols_tablename), Toast.LENGTH_SHORT).show();
                    return;
                }

                Hall hall = (Hall)getApplicationContext();
                hall.getTables().add(new Table(name, 4));
                hall.setCheckTable(hall.getTables().size() - 1);
                Intent intent = new Intent(HallActivity.this, OrderActivity.class);
                startActivity(intent);
            }
        });
        builder.setView(dialogView);
        dialog = builder.create();
    }
    private void findInfoElements() {
        txtCountTable = findViewById(R.id.hall_table_count);
        txtOutput = findViewById(R.id.hall_daily_output);

        tableInfo = findViewById(R.id.hall_tableinfo);
        txtOrder = findViewById(R.id.hall_table_ordered);
        txtReserve = findViewById(R.id.hall_table_reserve);
        txtCurrent = findViewById(R.id.hall_table_current);
        txtTotal = findViewById(R.id.hall_table_total);

        bttnDelete = findViewById(R.id.hall_delete_button);
    }

    private void refreshInfo() {
        Hall hall = (Hall)getApplicationContext();
        txtCountTable.setText(String.valueOf(hall.getPaidTableCount()));
        txtOutput.setText(String.valueOf(hall.getOutput()));

        int selectedPosition = tableAdapter.getSelectPosition();
        if(selectedPosition != Table.NON_CHECKED_POSITION) {
            Table table = hall.getTables().get(selectedPosition);
            tableInfo.setVisibility(View.VISIBLE);

            txtOrder.setText(String.valueOf(table.getOrderedSum()));
            txtReserve.setText(String.valueOf(table.getReserveSum()));
            txtCurrent.setText(String.valueOf(table.getCurrentSum()));
            txtTotal.setText(String.valueOf(table.getTotal()));

            if(table.getOrderedSum() == 0)
                bttnDelete.setVisibility(View.VISIBLE);
            else
                bttnDelete.setVisibility(View.INVISIBLE);


        } else {
            tableInfo.setVisibility(View.INVISIBLE);
            bttnDelete.setVisibility(View.INVISIBLE);
        }

    }

    public void deleteTable(View view) {
        Hall hall = (Hall)getApplicationContext();
        int pos = tableAdapter.getSelectPosition();


        if(hall.getTables().get(pos).getOrderedSum() == 0f){
            tableAdapter.setSelectPosition(Table.NON_CHECKED_POSITION);
            hall.getTables().remove(pos);
            tableAdapter.notifyDataSetChanged();
            refreshInfo();
        }
        else
            Toast.makeText(HallActivity.this,getString(R.string.toast_impossible_delete), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION_SELECT_ITEM,tableAdapter.getSelectPosition());
        if(dialog.isShowing()) {
            outState.putBoolean(DIALOG_SHOW,true);
            outState.putString(NAME_DIALOG,nameForNewTable.getText().toString());
        } else
            outState.putBoolean(DIALOG_SHOW,false);

    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        int pos = savedInstanceState.getInt(POSITION_SELECT_ITEM);
        tableAdapter.setSelectPosition(pos);
        if(pos != Table.NON_CHECKED_POSITION)
            refreshInfo();

        if(savedInstanceState.getBoolean(DIALOG_SHOW)) {
            nameForNewTable.setText(savedInstanceState.getString(NAME_DIALOG));
            dialog.show();
        }
    }

    public void showDialog(View view) {
        dialog.show();
    }
}
