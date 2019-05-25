package com.example.sjh.gcsjdemo.group;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.sjh.gcsjdemo.R;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class CreateGroup extends AppCompatActivity {

    private  String mUserid=null;
    private String grp_name=null;
    private String courseid;
    //群号grpno=课程号course_id+随机数grpid
    private  RandomN randomN=new RandomN();
    String grpid=null;
    String grpno=null;
    //获取已有课程id号并获取课程名称
    ArrayList<String> course_id = new ArrayList<>();
    ArrayList<String> course_name =new ArrayList<>();
    private Spinner spinner_course;
    private String result_course_name=null;
    private int r_min=2;
    private int r_max=0;
    private String grp_c;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);


        Intent intent =getIntent();
        Bundle bundle=intent.getExtras();
        mUserid= bundle.getString("userid");

        Button btcreate = findViewById(R.id.new_grpcreate);
        Button btcreatecancel= findViewById(R.id.new_grpcreate_cancel);

        TextView textView_c1=findViewById(R.id.textView_c1);
        TextView textView_c2=findViewById(R.id.textView_c2);
        TextView textView_c3=findViewById(R.id.textView_c3);
        TextView textView_c4=findViewById(R.id.textView_c4);
        TextView textView_c5=findViewById(R.id.textView_c5);

        editText = findViewById(R.id.grpname1);
        editText.setHint("请输入群名称：（2~14字）");

        spinner_course =findViewById(R.id.course_choice);
        final Spinner spinner_minnum =findViewById(R.id.Minnum);
        final Spinner spinner_maxnum =findViewById(R.id.Maxnum);
        String[] maxnum={"4","6","8","10","12","14","16","18","20","22","24","26","28","30","32","34","36","38","40"};
        String[] minnum={"2","3","4","5","6","7","8","9"};
        ArrayList<String> max=new ArrayList<>();
        ArrayList<String> min=new ArrayList<>();
        for(int i=0;i<maxnum.length;i++)
        {
            max.add(maxnum[i]);
        }
        for(int i=0;i<minnum.length;i++)
        {
            min.add(minnum[i]);
        }
        ArrayAdapter<String> max_adapter= new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,max);
        ArrayAdapter<String> min_adapter= new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,min);
        max_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        min_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_maxnum.setAdapter(max_adapter);
        spinner_minnum.setAdapter(min_adapter);//设置适配器完成
        //设置适配器标题
        spinner_maxnum.setPrompt("请选择小组最大成员限制");
        spinner_minnum.setPrompt("请选择小组最小成员限制");
        // spinner_minnum.getSelectedItem();
        //  spinner_maxnum.getSelectedItem();
        // spinner_course.getSelectedItem();
        final CountDownLatch countDownLatch = new CountDownLatch(1);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    java.sql.Connection cn= DriverManager.getConnection("jdbc:mysql://182.254.161.189/gcsj","root","mypwd");
                    String sql="SELECT * FROM course_info WHERE depart_id = "+"24";
                    Statement st=(Statement)cn.createStatement();
                    ResultSet rs=st.executeQuery(sql);
                    while(rs.next()){
                        course_id.add(rs.getString("course_id"));
                        course_name.add(rs.getString("course_name"));
                        //Log.i("CreateGroup","查询成果、、、、、");
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

        //spinner监听器，最后结果存至result_min和result_max中
        ArrayAdapter<String> course_name_adapter= new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,course_name);
        course_name_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_course.setAdapter(course_name_adapter);

        spinner_course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String result_course= parent.getItemAtPosition(position).toString();
                result_course_name=spinner_course.getSelectedItem().toString();
                //  Log.i("CreateGroup",result_course_name);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(CreateGroup.this,"未选择课程！",Toast.LENGTH_SHORT).show();
            }

        });
        spinner_minnum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String result_min= parent.getItemAtPosition(position).toString();
                r_min =Integer.parseInt(spinner_minnum.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(CreateGroup.this,"未选择最小人数！",Toast.LENGTH_SHORT).show();
            }

        });
        spinner_maxnum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String result_max= parent.getItemAtPosition(position).toString();
                r_max =Integer.parseInt(spinner_maxnum.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(CreateGroup.this,"未选择最大人数！",Toast.LENGTH_SHORT).show();
            }

        });

//创建并向数据库提交数据

        btcreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCreatgrp();
                insert();
//                System.out.println("生成的群组号为"+grpno);
//                System.out.println("课程为"+grpno);
//                System.out.println("owner为"+mUserid);
//                System.out.println("最小人数为"+String.valueOf(r_min));
//                System.out.println("最大人数为"+String.valueOf(r_max));

                if(isSuccess()){
                    Toast.makeText(CreateGroup.this,"创建成功！",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(CreateGroup.this,"创建失败，请稍后重试",Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(CreateGroup.this, MyGroup.class);//将该页面的学号返回至下个页面
                Bundle  bundle =new Bundle();
                bundle.putString("userid",  mUserid.toString());
                intent.putExtras(bundle);
                startActivity(intent);
                finish();

            }
        });
        btcreatecancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateGroup.this, MyGroup.class);//将该页面的学号返回至下个页面
                Bundle  bundle =new Bundle();
                bundle.putString("userid",  mUserid.toString());
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }

    private void myCreatgrp() {

        grpid= randomN.Generate();
        //获取随机产生的五位数

        if(result_course_name!= null){
            for(int i= 0;i<course_name.size();i++)
                if(result_course_name==course_name.get(i)) {
                    grpno = course_id.get(i) + grpid;
                    courseid=course_id.get(i);
                    grp_c = course_id.get(i);
                }
        }//群组号生成结束
        //  Log.i("CreateGroup",String.valueOf(grpno));


    }
    private void insert(){
        //判断非空开始生成
        grp_name=editText.getText().toString();
        if(r_max==0){ Toast.makeText(CreateGroup.this,"未选择最小人数！",Toast.LENGTH_SHORT).show();}
        else{
            if(result_course_name==null){ Toast.makeText(CreateGroup.this,"未选择课程！",Toast.LENGTH_SHORT).show(); }
            else{
                if(grp_name==null){Toast.makeText(CreateGroup.this,"未输入群名称！",Toast.LENGTH_SHORT).show();}
                else{
                    int t_lengtn=0;
                    t_lengtn= grp_name.length();
                    if(t_lengtn>=4&&t_lengtn<=28){
                        final int status =0;
                        final int currentnum=1;
                        final CountDownLatch countDownLatch = new CountDownLatch(1);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Class.forName("com.mysql.jdbc.Driver");
                                    java.sql.Connection cn= DriverManager.getConnection("jdbc:mysql://182.254.161.189/gcsj","root","mypwd");
                                    String sql="insert into team_info values ('"+ grpno+"','"+grp_name+"','"+mUserid+"', "+r_min+","+r_max+","+currentnum+","+status+",'"+courseid+"')";
                                    Statement st=(Statement)cn.createStatement();
                                    st.execute(sql);
                                    String sql1="update user_team set C_"+grp_c+" = '"+grpno+"' where user_id="+mUserid;
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

                        //LoginThread thread = new LoginThread(email,password);
                        //thread.start();
                        //uuu=thread.getUu();

                        try {
                            countDownLatch.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("当前人数为"+String.valueOf(currentnum));
                        System.out.println("群组状态为"+String.valueOf(status));
                    }
                    else {
                        Toast.makeText(CreateGroup.this,"群名称长度不符合要求！",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

    }
    //判断是否插入更新
    private boolean isSuccess(){
        final boolean[] sign = {false};
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    java.sql.Connection cn= DriverManager.getConnection("jdbc:mysql://182.254.161.189/gcsj","root","mypwd");
                    String sql="SELECT * FROM team_info WHERE team_id = "+grpno;
                    Statement st=(Statement)cn.createStatement();
                    ResultSet rs=st.executeQuery(sql);
                    if(rs.next()){
                        sign[0] =true;
                        Log.i("CreateGroup", String.valueOf(sign[0]));
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
        return sign[0];
    }


}

