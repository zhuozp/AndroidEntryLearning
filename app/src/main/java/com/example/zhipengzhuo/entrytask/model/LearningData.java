package com.example.zhipengzhuo.entrytask.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class LearningData {
    @Id(autoincrement = true)
    Long id;
    int serialNo;
    String name;
    int process;



    @Generated(hash = 1360452552)
    public LearningData(Long id, int serialNo, String name, int process) {
        this.id = id;
        this.serialNo = serialNo;
        this.name = name;
        this.process = process;
    }

    @Generated(hash = 835972493)
    public LearningData() {
    }

    

    public String getName() {
        return name;
    }

    public int getProcess() {
        return process;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProcess(int process) {
        this.process = process;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSerialNo() {
        return this.serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    public LearningData clone() {
        LearningData data = new LearningData(this.id, this.serialNo, this.name, this.process);
        return data;
    }
}
