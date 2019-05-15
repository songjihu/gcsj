package com.example.sjh.gcsjdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.sjh.gcsjdemo.person.AddCourseActivity;
import com.example.sjh.gcsjdemo.person.DBUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class test extends Activity {

    String stuNo;
    String stuName="徐至";
    Connection conn=null;
    Statement stmt = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test2);


    }

    public void tiao(View view){
        new Thread(new Runnable(){
            public void run(){

                conn = DBUtils.getConn();

                String sql ="select * from user where user_name= '徐至'";


                try {
                    stmt = conn.createStatement();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    ResultSet rs = stmt.executeQuery(sql);
                    while (rs.next()){
                        stuNo= rs.getString("user_id");

                    }



                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }).start();
        System.out.print("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        Intent intent=new Intent();
        intent.setClass(this, PersonActivity.class);
        startActivity(intent);



    }




}