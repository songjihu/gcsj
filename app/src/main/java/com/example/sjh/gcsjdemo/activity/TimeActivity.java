package com.example.sjh.gcsjdemo.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.sjh.gcsjdemo.R;
import com.example.sjh.gcsjdemo.utils.Utils;

public class TimeActivity extends AppCompatActivity {
    private int timeText = 0;//默认时间0秒
    private CheckBox mCbTimeStart;
    private CheckBox mCbTimeEnd;
    private TextView mTvTimeShow;
    private boolean isEnd = false;//是否结束
    private boolean isPause = true;//是否暂停

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    if (!isPause || isEnd){
                        timeText++;
                        String timeFormat = Utils.formatTime(timeText);
                        mTvTimeShow.setText("您今日一共学习了"+timeFormat);
                        sendEmptyMessageDelayed(0,1000);
                    }
                    break;
                case 1:
                    if (!isEPause || isEEnd){
                        timeEText++;
                        String timeEFormat = Utils.formatTime(timeEText);
                        mTvEnterShow.setText("您今日一共娱乐了"+timeEFormat);
                        sendEmptyMessageDelayed(1,1000);
                    }
                    break;
                default:
                    break;
            }
        }
    };
    private CheckBox mCbEnterStart;
    private CheckBox mCbEnterEnd;
    private TextView mTvEnterShow;
    private int timeEText = 0;//默认时间0秒
    private boolean isEEnd = false;//是否结束
    private boolean isEPause = true;//是否暂停

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        initView();
        initAction();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mCbTimeStart = (CheckBox)findViewById(R.id.cb_time_start);
        mCbTimeEnd = (CheckBox)findViewById(R.id.cb_time_end);
        mTvTimeShow = (TextView)findViewById(R.id.tv_time_show_text);
        mCbEnterStart = (CheckBox)findViewById(R.id.cb_entertainment_start);
        mCbEnterEnd = (CheckBox)findViewById(R.id.cb_entertainment_end);
        mTvEnterShow = (TextView)findViewById(R.id.tv_entertainment_show_text);
    }

    /**
     * 设置监听
     */
    private void initAction() {
        mCbTimeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeMessages(0);
                if (Utils.isRepeatClick()){
                    return;
                }
                if (mCbTimeStart.isChecked() || isEnd){
                    handler.sendEmptyMessageDelayed(0,1000);
                    isPause = false;
                }else {
                    isPause = true;
                }
                isEnd = false;
            }
        });

        mCbTimeEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isRepeatClick()){
                    return;
                }
                handler.removeMessages(0);
                timeText = 0;
                isEnd = true;
                isPause = true;
            }
        });


        mCbEnterStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeMessages(1);
                if (Utils.isRepeatClick()){
                    return;
                }
                if (mCbEnterStart.isChecked() || isEEnd){
                    handler.sendEmptyMessageDelayed(1,1000);
                    isEPause = false;
                }else {
                    isEPause = true;
                }
                isEEnd = false;
            }
        });

        mCbEnterEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isRepeatClick()){
                    return;
                }
                handler.removeMessages(1);
                timeEText = 0;
                isEEnd = true;
                isEPause = true;
            }
        });
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler!=null){
            handler.removeMessages(0);
            handler.removeMessages(1);
        }
    }

}
