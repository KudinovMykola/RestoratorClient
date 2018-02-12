package com.kudinov.restoratorclient.data.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "department")
public class DepartmentEntity {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    public DepartmentEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Ignore
    public DepartmentEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
