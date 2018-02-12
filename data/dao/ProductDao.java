package com.kudinov.restoratorclient.data.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.kudinov.restoratorclient.data.entities.ProductEntity;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertProduct(ProductEntity productEntity);
    @Query("SELECT * FROM product")
    List<ProductEntity> getAll();
}
