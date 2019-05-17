package com.example.sjh.gcsjdemo.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sjh.gcsjdemo.R;
import com.example.sjh.gcsjdemo.dbmanager.RemindUtil;
import com.example.sjh.gcsjdemo.entity.Remind;
import com.example.sjh.gcsjdemo.service.RemindService;
import com.example.sjh.gcsjdemo.utils.DateUtil;

import org.greenrobot.eventbus.EventBus;

import java.sql.SQLException;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;


/**
 * @ProjectName: gcsj
 * @Package: com.example.sjh.gcsjdemo
 * @ClassName: PublishActivity
 * @Description: java类作用描述
 * @Author: WX
 * @CreateDate: 2019/4/21 23:30
 * @Version: 1.0
 */
public class PublishActivity extends Activity {
    private String  title;
    private String  context_text;
    private String  remind_id;
    private String  remind_time;
    private String  userId;
    private String  teamId;


    private EditText dateText;
    private EditText timeText;
    private EditText editText;
    private EditText editTitle;
    private EditText teamIdText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        userId = bundle.getString("userId");
    }
        /*
        String titleError = "请填写标题！";
        String conError = "请填写内容！";
        String timeError = "时间格式填写有误，请检查！";
        String success = "添加成功！";
        */

    public void Pub(View view){

        String msg;
        editTitle = (EditText)findViewById(R.id.pub_title);
        editText = (EditText)findViewById(R.id.pub_text);
        dateText = (EditText)findViewById(R.id.dateEditor);
        timeText = (EditText)findViewById(R.id.timeEditor);
        teamIdText = (EditText)findViewById(R.id.teamEditor);

        remind_id = DateUtil.getNowDateStr();
        remind_time =dateText.getText().toString()+" "+timeText.getText().toString()+":00";
        Log.v("时间：","==》"+remind_time);
        title = editTitle.getText().toString();
        context_text = editText.getText().toString();
        teamId = teamIdText.getText().toString();


        RemindService rs = new RemindService();
        RemindUtil remindUtil = null;
        Remind rm = null;
        switch (rs.dataPublishCheck(title,context_text,remind_time,teamId)){
            case 1: msg = "请填写标题！";  break;
            case 2: msg = "请填写内容！";  break;
            case 3: msg = "时间格式填写有误，请检查！";  break;
            case 4: msg = "小组id有误，请检查！";  break;
            default:msg = "提醒添加成功!";
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        RemindService rs = new RemindService();
                        rs.insertIntoRemoteService(title,userId,context_text,remind_time,teamId);
                    }
                }).start();

                break;
        }
        Toast.makeText(PublishActivity.this,msg,Toast.LENGTH_SHORT).show();
        if(msg.equals("提醒添加成功!"))
            finish();


       // Toast.makeText(PublishActivity.this,"你点击了onClick触发 button",Toast.LENGTH_SHORT).show();




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
