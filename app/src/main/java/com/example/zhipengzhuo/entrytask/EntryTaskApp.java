package com.example.zhipengzhuo.entrytask;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.zhipengzhuo.entrytask.database.DaoMaster;
import com.example.zhipengzhuo.entrytask.database.DaoSession;

public class EntryTaskApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initDaoSession();
    }

    private DaoSession mDaoSession;

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    private void initDaoSession() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "test.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
    }
}
