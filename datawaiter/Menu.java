package com.kudinov.restoratorclient.datawaiter;


import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.SharedPreferences;
import android.util.Log;

import com.kudinov.restoratorclient.data.InternalDatabase;

public class Menu extends Application {
    final private static String TAG = "myLogs";
    public static Menu INSTANCE;

    private static final String DATABASE_NAME = "InternalDB4";
    private static final String PREFERENCES = "RoomDemo.preferences";
    private static final String KEY_FORSE_UPDATE = "forse_update";

    private InternalDatabase _db;

    public static Menu getInstance()
    {
    //    Log.d(TAG, "instanse");
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        _db = Room.databaseBuilder(getApplicationContext(),
                InternalDatabase.class,
                DATABASE_NAME).build();

        INSTANCE = this;
       // Log.d(TAG, "createFinish");
    }

    public InternalDatabase get_db() {
       // Log.d(TAG,"getDB");
        return _db;
    }

    public boolean getForceUpdate() {
        return getSP().getBoolean(KEY_FORSE_UPDATE, true);
    }

    public void setForceUpdate(boolean force) {
        SharedPreferences.Editor edit = getSP().edit();
        edit.putBoolean(KEY_FORSE_UPDATE, force);
        edit.apply();
    }

    private SharedPreferences getSP() {
        return getSharedPreferences(PREFERENCES, MODE_PRIVATE);
    }
}
