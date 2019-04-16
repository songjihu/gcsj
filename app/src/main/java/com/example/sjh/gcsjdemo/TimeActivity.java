package com.example.sjh.gcsjdemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

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
                default:
                    break;
            }
        }
    };
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
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler!=null){
            handler.removeMessages(0);
        }
    }

}
