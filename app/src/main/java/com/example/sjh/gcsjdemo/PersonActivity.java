package com.example.sjh.gcsjdemo;

import android.app.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sjh.gcsjdemo.person.DBUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class PersonActivity extends Activity {

     private Button btn;
    private Button btn1;

    Connection conn=null;
    Statement stmt = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_info);
        btn = (Button) findViewById(R.id.per_first_s);

        Intent intent = getIntent();
        String stuNo = intent.getStringExtra("stuNo");
        String stuName = intent.getStringExtra("stuName");
        String stuSex = intent.getStringExtra("stuSex");
        String stuCla = intent.getStringExtra("stuCla");
        String stuDep = intent.getStringExtra("stuDep");
        String stuSch = intent.getStringExtra("stuSch");

        TextView tv1=(TextView)findViewById(R.id.user_val);
        tv1.setText(stuNo);
        TextView tv2=(TextView)findViewById(R.id.user_name);
        tv2.setText(stuName);
        TextView tv3=(TextView)findViewById(R.id.per_fourth_s);
        tv3.setText(stuSex);
        TextView tv4=(TextView)findViewById(R.id.per_sixth_s);
        tv4.setText(stuCla);
        TextView tv5=(TextView)findViewById(R.id.per_sixth_t);
        tv5.setText(stuDep);
        TextView tv6=(TextView)findViewById(R.id.per_eight_s);
        tv6.setText(stuSch);

    }








    public void clickUpdate(View view){
        Toast.makeText(getApplicationContext(),"修改头像功能暂未接入", Toast.LENGTH_SHORT).show();
    }




}


