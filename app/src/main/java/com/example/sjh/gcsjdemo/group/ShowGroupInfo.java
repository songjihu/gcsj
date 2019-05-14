package com.example.sjh.gcsjdemo.group;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sjh.gcsjdemo.R;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.CountDownLatch;

public class ShowGroupInfo extends AppCompatActivity {

    private String mUserid;
    private String grpid;
    private String sgrp_name;
    private String sgrp_owner;
    private int sgrp_current;
    private int sgrp_max;
    private int sgrp_status;
    private String sgrp_course_id;
    private String sgrp_ownername;
    private boolean isempty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_group_info);
        Intent intent =getIntent();
        Bundle bundle=intent.getExtras();
        mUserid=bundle.getString("userid");
        grpid= bundle.getString("grpno");


        // Log.i("ShowGroupInfo","接收到的群组号为"+grpid);
        TextView textView1=findViewById(R.id.show_text1);
        TextView textView2=findViewById(R.id.show_text2);
        TextView textView3=findViewById(R.id.show_text3);
        TextView textView4=findViewById(R.id.show_text4);
        TextView textView5=findViewById(R.id.show_text5);
        TextView textView_grpname=findViewById(R.id.show_grpname);
        TextView textView_owner=findViewById(R.id.show_owner);
        TextView textView_status=findViewById(R.id.show_status);
        TextView textView_rest=findViewById(R.id.show_rest);
        Button bt=findViewById(R.id.show_join);
        showsearch();
        getname();
        textView_grpname.setText(sgrp_name);
        textView_owner.setText(sgrp_ownername);
        if(sgrp_status==0){
            textView_status.setText("组建中");
        }
        else{
            textView_status.setText("已成立");
        }


        if(sgrp_max==sgrp_current)
        {
            textView_rest.setText("已满员");
            bt.setClickable(false);
            bt.setVisibility(View.INVISIBLE);
            Toast.makeText(ShowGroupInfo.this, "该群组成员已满，请选择其他群组",Toast.LENGTH_SHORT).show();

        }
        else{
            int temp=sgrp_max-sgrp_current;
            String stemp=temp+"人";
            textView_rest.setText(stemp);
        }

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 这个地方留出来放申请加组通知的信息
                 */
                if(isempty){

                    showjoin();
                    Toast.makeText(ShowGroupInfo.this,"加入成功",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ShowGroupInfo.this, "已加入该课程的其他群组，请先退出后再加入新的群组",Toast.LENGTH_LONG).show();
                }

                finish();
            }
        });
    }

    private void showsearch() {

        final CountDownLatch countDownLatch = new CountDownLatch(1);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    java.sql.Connection cn= DriverManager.getConnection("jdbc:mysql://182.254.161.189/gcsj","root","mypwd");
                    String sql="SELECT * FROM team_info WHERE team_id = "+grpid;
                    Statement st=(Statement)cn.createStatement();
                    ResultSet rs=st.executeQuery(sql);
                    while(rs.next()){
                        sgrp_name=rs.getString("team_name");
                        sgrp_owner=rs.getString("team_owner");
                        sgrp_current=rs.getInt("currentnum");
                        sgrp_max=rs.getInt("maxnum");
                        sgrp_status=rs.getInt("status");
                        sgrp_course_id=rs.getString("team_course");

                    }
                    cn.close();
                    st.close();
                    rs.close();
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
        Log.i("ShowGroupInfo","查询到的课程id为"+sgrp_course_id);



//另开线程，检验是否已加入其他群组
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    java.sql.Connection cn= DriverManager.getConnection("jdbc:mysql://182.254.161.189/gcsj","root","mypwd");
                    String sql="SELECT C_"+sgrp_course_id+" FROM user_team WHERE user_id = "+mUserid;
                    Statement st=(Statement)cn.createStatement();
                    ResultSet rs=st.executeQuery(sql);
                    while(rs.next()){
                        String no=rs.getString(1);
                        // Log.i("ShowGroupInfo","获取到的加组情况为"+no);
                        if(no==null ){
                            //加入新群组
                            isempty=true;
                        }
                        else{
                            isempty=false;

                        }
                    }

                    cn.close();
                    st.close();
                    rs.close();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            }
        }).start();

        //LoginThread thread = new LoginThread(email,password);
        //thread.start();
        //uuu=thread.getUu();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void getname(){
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        //另加线程，获取创建者姓名
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    java.sql.Connection cn= DriverManager.getConnection("jdbc:mysql://182.254.161.189/gcsj","root","mypwd");
                    String sql="SELECT user_name FROM user WHERE user_id = "+sgrp_owner;
                    Statement st=(Statement)cn.createStatement();
                    ResultSet rs=st.executeQuery(sql);
                    if(rs.next()){
                        sgrp_ownername=rs.getString(1);
                    }
                    cn.close();
                    st.close();
                    rs.close();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            }
        }).start();

        //LoginThread thread = new LoginThread(email,password);
        //thread.start();
        //uuu=thread.getUu();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private  void showjoin(){
        final int tcurrent;
        tcurrent=sgrp_current+1;
        final CountDownLatch countDownLatch = new CountDownLatch(1);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    java.sql.Connection cn= DriverManager.getConnection("jdbc:mysql://182.254.161.189/gcsj","root","mypwd");
                    String sql="update team_info set currentnum= "+tcurrent+" where team_id="+grpid;
                    Statement st=(Statement)cn.createStatement();
                    st.execute(sql);
                    String sql1="update user_team set C_"+sgrp_course_id+" = '"+grpid+"' where user_id="+mUserid;
                    st.execute(sql1);

                    cn.close();
                    st.close();


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
    }




}
