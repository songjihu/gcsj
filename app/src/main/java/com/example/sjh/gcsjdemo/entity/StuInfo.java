package com.example.sjh.gcsjdemo.entity;

import android.util.Log;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.CountDownLatch;

public class StuInfo {
    private String userId;
    private String rpwd;
    private String userName;
    private String classId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRpwd() {
        return rpwd;
    }

    public void setRpwd(String rpwd) {
        this.rpwd = rpwd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setClassId(String email){
        this.classId=email;
    }

    //根据教师号获取班级id
    public String getclassId(String email){
        final StuInfo a=new StuInfo();
        a.setClassId(email);
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Log.i("LLLLL",email);
                    Class.forName("com.mysql.jdbc.Driver");
                    java.sql.Connection cn= DriverManager.getConnection("jdbc:mysql://182.254.161.189/gcsj","root","mypwd");
                    String sql="SELECT class_id FROM `stu_info` WHERE stu_no = "+a.classId;
                    Statement st=(Statement)cn.createStatement();
                    ResultSet rs=st.executeQuery(sql);
                    while(rs.next()){
                        classId=rs.getString("class_id");
                    }
                    cn.close();
                    st.close();
                    //rs.close();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            }
        }).start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return classId;
    }
}