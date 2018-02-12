package com.kudinov.restoratorclient.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.kudinov.restoratorclient.data.entities.DepartmentEntity;
import com.kudinov.restoratorclient.model.Department;

import java.util.List;

@Dao
public interface DepartmentDao {

    @Insert
    void insertDepartment(DepartmentEntity departmentEntity);

    @Update
    void updateDepartment(DepartmentEntity departmentEntity);

    @Delete
    void deleteDepartment(DepartmentEntity departmentEntity);

    @Query("SELECT * FROM department WHERE id = :id")
    DepartmentEntity getById(int id);

    @Query("SELECT * FROM department")
    List<DepartmentEntity> getAll();
}
