package com.example.zhipengzhuo.entrytask.data;

import com.example.zhipengzhuo.entrytask.EntryTaskApp;
import com.example.zhipengzhuo.entrytask.database.DaoSession;
import com.example.zhipengzhuo.entrytask.model.LearningData;

import java.util.List;

public class DataManager {
    public static void initData(EntryTaskApp app) {
        if (app == null) {
            return;
        }
        DaoSession daoSession = app.getDaoSession();
        for (int i = 0; i < 100; i++) {
            LearningData user = new LearningData();
            user.setId((long) i);
            user.setName("hanmeimei" + i);
            user.setProcess(i % 2);
            user.setSerialNo(i);
            daoSession.insertOrReplace(user);
        }
    }

    public static List<LearningData> getDatas(EntryTaskApp app, String where, String args) {
        if (app == null) {
            return null;
        }
        DaoSession daoSession = app.getDaoSession();
        List<LearningData> datas = daoSession.queryRaw(LearningData.class, where, args);
        return  datas;

    }

    public static void updateData(EntryTaskApp app, LearningData data, int process) {
        if (app == null) {
            return;
        }
        DaoSession daoSession = app.getDaoSession();
        LearningData tmp = data.clone();
        tmp.setProcess(process);
        daoSession.update(tmp);
    }
}
