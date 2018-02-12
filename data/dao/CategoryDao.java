package com.kudinov.restoratorclient.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.kudinov.restoratorclient.data.entities.CategoryEntity;

import java.util.List;

@Dao
public interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCategory(CategoryEntity categoryEntity);

    @Query("SELECT * FROM category")
    List<CategoryEntity> getAll();
}
