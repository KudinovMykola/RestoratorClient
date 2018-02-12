package com.kudinov.restoratorclient.datawaiter;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.kudinov.restoratorclient.fakedata.FakeDataRequest;

import java.util.ArrayList;
import java.util.List;

public class Hall extends Application {
    private List<Table> tables;
    private int checkTable;
    private int paidTableCount;
    private float output;


    public Hall() {
        tables = new ArrayList<>();
        tables.add(new Table("Стол",new FakeDataRequest().getAllDepartment().size()));
        checkTable = 0;
        paidTableCount = 0;
        output = 0f;
    }

    public int getCheckTable() {
        return checkTable;
    }
    public void setCheckTable(int checkTable) {
        this.checkTable = checkTable;
    }

    public void payTable(int positionTable) {
        paidTableCount++;
        output += tables.get(positionTable).getOrderedSum();
        tables.remove(positionTable);

    }
    public Boolean reset() {
        if(tables.size() != 0) {
            for(Table table: tables) {
                if(table.getOrderedSum() != 0)
                    return false;
            }
            tables.clear();
        }

        paidTableCount = 0;
        output = 0f;
        checkTable = Table.NON_CHECKED_POSITION;
        return true;
    }

    public List<Table> getTables() {
        return tables;
    }
    public int getPaidTableCount() {
        return paidTableCount;
    }
    public float getOutput() {
        return output;
    }
}
