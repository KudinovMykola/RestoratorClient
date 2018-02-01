package com.kudinov.restoratorclient.datawaiter;

import java.util.ArrayList;
import java.util.List;

public class Hall {
    private List<Table> tables;
    private int checkTable;

    public Hall() {
        tables = new ArrayList<>();
        checkTable = Table.NON_CHECKED_POSITION;
    }

    public int getCheckTable() {
        return checkTable;
    }

    public void setCheckTable(int checkTable) {
        this.checkTable = checkTable;
    }

    public List<Table> getTables() {
        return tables;
    }
}
