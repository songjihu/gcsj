package com.example.sjh.gcsjdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.sjh.gcsjdemo.dbmanager.RemindUtil;
import com.example.sjh.gcsjdemo.entity.Remind;
import com.example.sjh.gcsjdemo.service.RemindService;
import com.example.sjh.gcsjdemo.utils.DateUtil;

import java.util.Calendar;

/**
 * @ProjectName: gcsj
 * @Package: com.example.sjh.gcsjdemo
 * @ClassName: RemindActivity
 * @Description: java类作用描述
 * @Author: WX
 * @CreateDate: 2019/5/17 2:18
 * @Version: 1.0
 */
public class RemindActivity extends AppCompatActivity implements View.OnClickListener,DatePicker.OnDateChangedListener, TimePicker.OnTimeChangedListener {
    private Context context;
    private LinearLayout llDate, llTime;
    private TextView tvDate, tvTime;
    private int year, month, day, hour, minute;
    //在TextView上显示的字符
    private StringBuffer date, time;


    private String  title;
    private String  context_text;
    private String  remind_id;
    private String  remind_time;
    private String  userId;

    private EditText editText;
    private EditText editTitle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind1);
        context = this;
        date = new StringBuffer();
        time = new StringBuffer();
        initView();
        initDateTime();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        userId = bundle.getString("userId");
    }


    private void initView() {
        llDate = (LinearLayout) findViewById(R.id.ll_date);
        tvDate = (TextView) findViewById(R.id.tv_date);
        llTime = (LinearLayout) findViewById(R.id.ll_time);
        tvTime = (TextView) findViewById(R.id.tv_time);
        llDate.setOnClickListener(this);
        llTime.setOnClickListener(this);
    }

    private void initDateTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        Log.v("时间日期",year+":"+month+":"+day+":"+hour+":"+minute);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_date:
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (date.length() > 0) { //清除上次记录的日期
                            date.delete(0, date.length());
                        }
                        tvDate.setText(date.append(String.valueOf(year)).append("年").append(String.valueOf(month+1)).append("月").append(day).append("日"));
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                final AlertDialog dialog = builder.create();
                View dialogView = View.inflate(context, R.layout.dialog_date, null);
                final DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.datePicker);

                dialog.setTitle("设置日期");
                dialog.setView(dialogView);
                dialog.show();
                //初始化日期监听事件
                datePicker.init(year, month, day, this);
                break;
            case R.id.ll_time:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
                builder2.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (time.length() > 0) { //清除上次记录的日期
                            time.delete(0, time.length());
                        }
                        tvTime.setText(time.append(String.valueOf(hour)).append("时").append(String.valueOf(minute)).append("分"));
                        dialog.dismiss();
                    }
                });
                builder2.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog2 = builder2.create();
                View dialogView2 = View.inflate(context, R.layout.dialog_time, null);
                TimePicker timePicker = (TimePicker) dialogView2.findViewById(R.id.timePicker);
                timePicker.setCurrentHour(hour);
                timePicker.setCurrentMinute(minute);
                timePicker.setIs24HourView(true); //设置24小时制
                timePicker.setOnTimeChangedListener(this);
                dialog2.setTitle("设置时间");
                dialog2.setView(dialogView2);
                dialog2.show();
                break;

        }
    }


    public void Pub(View view){

        String msg;
        editTitle = (EditText)findViewById(R.id.pub_title);
        editText = (EditText)findViewById(R.id.pub_text);
        //dateText = (TextView) findViewById(R.id.tv_date);
        //timeText = (TextView) findViewById(R.id.tv_time);



        remind_id = DateUtil.getNowDateStr();
        remind_time =year+"-";
        if(month+1<10) {
            remind_time = remind_time+"0"+(month+1)+"-";
        }else {
            remind_time = remind_time+month+"-";
        }
        if(day<10){
            remind_time = remind_time+"0"+day+" ";
        }else {
            remind_time = remind_time+day+" ";
        }
        if (hour<10){
            remind_time = remind_time+"0"+hour+":";
        }else {
            remind_time = remind_time+hour+":";
        }
        if(minute<10){
            remind_time = remind_time+"0"+minute+":00";
        }else {
            remind_time = remind_time+minute+":00";
        }

        Log.v("时间","--->"+remind_time+"长度"+remind_time.length());
        title = editTitle.getText().toString();
        context_text = editText.getText().toString();


        RemindService rs = new RemindService();
        RemindUtil remindUtil = null;
        Remind rm = null;
        switch (rs.dataCheck(title,context_text,remind_time)){
            case 1: msg = "请填写标题！";  break;
            case 2: msg = "请填写内容！";  break;
            case 3: msg = "提醒时间已过，请重新设置！";  break;
            default:msg = "提醒添加成功,请下拉刷新!";
                remindUtil = new RemindUtil();
                rm = new Remind(remind_id,userId,remind_time,title,context_text);
                remindUtil.insertRemind(rm);
        }
        Toast.makeText(RemindActivity.this,msg,Toast.LENGTH_SHORT).show();
        if(msg.equals("提醒添加成功,请下拉刷新!"))
            finish();
        // Toast.makeText(PublishActivity.this,"你点击了onClick触发 button",Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 日期改变的监听事件
     *
     * @param view
     * @param year
     * @param monthOfYear
     * @param dayOfMonth
     */
    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        this.month = monthOfYear;
        this.day = dayOfMonth;
    }

    /**
     * 时间改变的监听事件
     *
     * @param view
     * @param hourOfDay
     * @param minute
     */
    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        this.hour = hourOfDay;
        this.minute = minute;
    }
}


