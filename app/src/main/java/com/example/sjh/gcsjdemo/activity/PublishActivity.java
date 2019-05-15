package com.example.sjh.gcsjdemo.activity;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sjh.gcsjdemo.R;
import com.example.sjh.gcsjdemo.dbmanager.RemindUtil;
import com.example.sjh.gcsjdemo.entity.Remind;
import com.example.sjh.gcsjdemo.utils.DateUtil;



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


    private EditText dateText;
    private EditText timeText;
    private EditText editText;
    private EditText editTitle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
    }



    public void Pub(View view){

        editTitle = (EditText)findViewById(R.id.pub_title);
        editText = (EditText)findViewById(R.id.pub_text);
        dateText = (EditText)findViewById(R.id.dateEditor);
        timeText = (EditText)findViewById(R.id.timeEditor);

        remind_id = DateUtil.getNowDateStr();
        remind_time =dateText.getText().toString()+timeText.getText().toString();
        title = editTitle.getText().toString();
        context_text = editText.getText().toString();

        RemindUtil remindUtil = new RemindUtil();
        Remind remind = new Remind(remind_id,remind_time,title,context_text);

        if(remindUtil.insertRemind(remind)){
            Toast.makeText(PublishActivity.this,"提醒添加成功",Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(PublishActivity.this,"提醒添加失败",Toast.LENGTH_SHORT).show();
        }

       // Toast.makeText(PublishActivity.this,"你点击了onClick触发 button",Toast.LENGTH_SHORT).show();




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
