package com.example.sjh.gcsjdemo.utils.db;

/**
 * Created by Libaoming on 2/5/2019.
 * 11 hour 20 minute
 * project_name : gcsj
 */

public class StudyRecordDao {
    private int id;
    private String startTime;
    private String endTime;
    private String name;
    private String totalTime;
    private int timeLength;

    public StudyRecordDao(String name, String startTime, String endTime, String totalTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.name = name;
        this.totalTime = totalTime;
    }
    public StudyRecordDao(String name, String startTime, String endTime, String totalTime,int timeLength) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.name = name;
        this.totalTime = totalTime;
        this.timeLength = timeLength;
    }

    public int getTimeLength() {
        return timeLength;
    }

    public void setTimeLength(int timeLength) {
        this.timeLength = timeLength;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }
}
