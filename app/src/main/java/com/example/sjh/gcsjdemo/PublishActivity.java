package com.example.sjh.gcsjdemo;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.sjh.gcsjdemo.dbmanager.RemindUtil;
import com.example.sjh.gcsjdemo.entity.Remind;
import com.example.sjh.gcsjdemo.service.RemindService;
import com.example.sjh.gcsjdemo.utils.DateUtil;

import org.greenrobot.eventbus.EventBus;

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

    private EditText dateText;
    private EditText timeText;
    private EditText editText;
    private EditText editTitle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        userId = bundle.getString("userId");
    }

    String titleError = "请填写标题！";
    String conError = "请填写内容！";
    String timeError = "时间格式填写有误，请检查！";
    String success = "添加成功！";


    public void Pub(View view){

        String msg;
        editTitle = (EditText)findViewById(R.id.pub_title);
        editText = (EditText)findViewById(R.id.pub_text);
        dateText = (EditText)findViewById(R.id.dateEditor);
        timeText = (EditText)findViewById(R.id.timeEditor);

        remind_id = DateUtil.getNowDateStr();
        remind_time =dateText.getText().toString()+" "+timeText.getText().toString()+":00";
        title = editTitle.getText().toString();
        context_text = editText.getText().toString();

        RemindService rs = new RemindService();
        RemindUtil remindUtil = null;
        Remind rm = null;
        switch (rs.dataCheck(title,context_text,remind_time)){
            case 1: msg = "请填写标题！";  break;
            case 2: msg = "请填写内容！";  break;
            case 3: msg = "时间格式填写有误，请检查！";  break;
            default:msg = "提醒添加成功!";
                remindUtil = new RemindUtil();
                rm = new Remind(remind_id,userId,remind_time,title,context_text);
                remindUtil.insertRemind(rm);
        }
        Toast.makeText(PublishActivity.this,msg,Toast.LENGTH_SHORT).show();
        if(msg.equals("提醒添加成功!"))
            finish();
/*
        if(remindUtil.insertRemind(remind)){
            Toast.makeText(PublishActivity.this,"提醒添加成功",Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(PublishActivity.this,"提醒添加失败",Toast.LENGTH_SHORT).show();
        }*/

       // Toast.makeText(PublishActivity.this,"你点击了onClick触发 button",Toast.LENGTH_SHORT).show();




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
