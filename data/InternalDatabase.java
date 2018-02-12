package com.kudinov.restoratorclient.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.support.annotation.NonNull;

import com.kudinov.restoratorclient.data.dao.CategoryDao;
import com.kudinov.restoratorclient.data.dao.DepartmentDao;
import com.kudinov.restoratorclient.data.dao.ProductDao;
import com.kudinov.restoratorclient.data.entities.CategoryEntity;
import com.kudinov.restoratorclient.data.entities.DepartmentEntity;
import com.kudinov.restoratorclient.data.entities.ProductEntity;

@Database(entities = {DepartmentEntity.class, CategoryEntity.class, ProductEntity.class}, version = 2, exportSchema = false)
public abstract class InternalDatabase extends RoomDatabase {

    public abstract DepartmentDao departmentDao();
    public abstract CategoryDao categoryDao();
    public abstract ProductDao productDao();
}
