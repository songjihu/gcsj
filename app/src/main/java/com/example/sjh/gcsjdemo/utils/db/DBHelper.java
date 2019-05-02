package com.example.sjh.gcsjdemo.utils.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Libaoming on 2/5/2019.
 * 10 hour 44 minute
 * project_name : gcsj
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final int version = 1;
    private static final String name = "studyRecord.db";
    private static DBHelper dbHelper;
    private String sql = "CREATE TABLE IF NOT EXISTS srecord(id integer primary key autoincrement," +
            "name varchar(20)," +
            "startTime varchar(50)," +
            "endTime varchar(50)," +
            "totalTime varchar(20)," +
            "timeLength integer)";
    public static DBHelper getInstance(Context context){
        if (dbHelper == null){
            dbHelper = new DBHelper(context);
        }
        return dbHelper;
    }

    private DBHelper(Context context){
        super(context,name,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     *
     * @param name
     * @param startTime
     * @param endTime
     * @param totalTime
     * @return
     */
    public long insertData(String name,String startTime,String endTime,String totalTime,int timeLength){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("startTime",startTime);
        values.put("endTime",endTime);
        values.put("totalTime",totalTime);
        values.put("timeLength",timeLength);
        long result = db.insert("srecord",null,values);
        db.close();
        return result;
    }

    /**
     *
     * @return
     */
    public List<StudyRecordDao> select(){
        List<StudyRecordDao> daoList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "select * from srecord";
        Cursor cursor = db.rawQuery(sql,null);
        StudyRecordDao dao;
        while (cursor.moveToNext()){
            String name = cursor.getString(1);
            String startTime = cursor.getString(2);
            String endTime = cursor.getString(3);
            String totalTIme = cursor.getString(4);
            int timeLength = cursor.getInt(5);
            dao = new StudyRecordDao(name,startTime,endTime,totalTIme,timeLength);
            daoList.add(dao);
        }
        db.close();
        return daoList;
    }

}
