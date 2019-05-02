package com.example.sjh.gcsjdemo.ui.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.sjh.gcsjdemo.R;
import com.example.sjh.gcsjdemo.Utils;
import com.example.sjh.gcsjdemo.utils.db.DBHelper;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActiveFragment extends Fragment {

    private View footView;
    private int timeText = 0;//默认时间0秒
    private CheckBox mCbTimeStart;
    private CheckBox mCbTimeEnd;
    private TextView mTvTimeShow;
    private boolean isEnd = false;//是否结束
    private boolean isPause = true;//是否暂停
    private boolean isRecordStudyTime = false;//是否开始记录学习时间
    private boolean isRecordEntryTime = false;//是否开始记录娱乐时间
    private boolean isRecordSleepTime = false;//是否开始记录睡觉时间
    private String studyStartTime;
    private String entryStartTime;
    private String sleepStartTime;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (!isPause || isEnd) {
                        timeText++;
                        String timeFormat = Utils.formatTime(timeText);
                        mTvTimeShow.setText("您今日一共学习了" + timeFormat);
                        sendEmptyMessageDelayed(0, 1000);
                    }
                    break;
                case 1:
                    if (!isEPause || isEEnd) {
                        timeEText++;
                        String timeEFormat = Utils.formatTime(timeEText);
                        mTvEnterShow.setText("您今日一共娱乐了" + timeEFormat);
                        sendEmptyMessageDelayed(1, 1000);
                    }
                    break;
                case 2:
                    if (!isSPause || isSEnd) {
                        timeSText++;
                        String timeSFormat = Utils.formatTime(timeSText);
                        mTvSleepShow.setText("您今日一共睡眠了" + timeSFormat);
                        sendEmptyMessageDelayed(2, 1000);
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
    private DBHelper db;
    private int timeSText = 0;//默认时间0秒
    private boolean isSEnd = false;//是否结束
    private boolean isSPause = true;//是否暂停
    private CheckBox mCbSleepStart;
    private CheckBox mCbSleepEnd;
    private TextView mTvSleepShow;


    public ActiveFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        footView = inflater.inflate(R.layout.fragment_active, container, false);
        initView();
        initAction();
        return footView;
    }


    /**
     * 初始化控件
     */
    private void initView() {
        db = DBHelper.getInstance(getContext());
        mCbTimeStart = (CheckBox) footView.findViewById(R.id.cb_time_start);
        mCbTimeEnd = (CheckBox) footView.findViewById(R.id.cb_time_end);
        mTvTimeShow = (TextView) footView.findViewById(R.id.tv_time_show_text);

        mCbEnterStart = (CheckBox) footView.findViewById(R.id.cb_entertainment_start);
        mCbEnterEnd = (CheckBox) footView.findViewById(R.id.cb_entertainment_end);
        mTvEnterShow = (TextView) footView.findViewById(R.id.tv_entertainment_show_text);

        mCbSleepStart = (CheckBox) footView.findViewById(R.id.cb_sleep_start);
        mCbSleepEnd = (CheckBox) footView.findViewById(R.id.cb_sleep_end);
        mTvSleepShow = (TextView) footView.findViewById(R.id.tv_sleep_show_text);
    }

    /**
     * 设置监听
     */
    private void initAction() {
        //学习开始
        mCbTimeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeMessages(0);
                if (Utils.isRepeatClick()) {
                    return;
                }

                if (!isRecordStudyTime) {
                    studyStartTime = Utils.obtianTime();
                    isRecordStudyTime = true;
                }

                if (mCbTimeStart.isChecked() || isEnd) {
                    handler.sendEmptyMessageDelayed(0, 1000);
                    isPause = false;
                } else {
                    isPause = true;
                }
                isEnd = false;
            }
        });
        //学习结束
        mCbTimeEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isRepeatClick()) {
                    return;
                }
                db.insertData("学习", studyStartTime, Utils.obtianTime(), Utils.formatTime(timeText), timeText);
                handler.removeMessages(0);
                timeText = 0;
                isEnd = true;
                isPause = true;
                isRecordStudyTime = false;

            }
        });

        //娱乐开始
        mCbEnterStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeMessages(1);
                if (Utils.isRepeatClick()) {
                    return;
                }

                if (!isRecordEntryTime) {
                    entryStartTime = Utils.obtianTime();
                    isRecordEntryTime = true;
                }

                if (mCbEnterStart.isChecked() || isEEnd) {
                    handler.sendEmptyMessageDelayed(1, 1000);
                    isEPause = false;
                } else {
                    isEPause = true;
                }
                isEEnd = false;
            }
        });
        //娱乐结束
        mCbEnterEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isRepeatClick()) {
                    return;
                }
                db.insertData("娱乐", entryStartTime, Utils.obtianTime(), Utils.formatTime(timeEText), timeEText);
                handler.removeMessages(1);
                timeEText = 0;
                isEEnd = true;
                isEPause = true;
                isRecordEntryTime = false;
            }
        });

        //娱乐开始
        mCbSleepStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeMessages(2);
                if (Utils.isRepeatClick()) {
                    return;
                }

                if (!isRecordSleepTime) {
                    sleepStartTime = Utils.obtianTime();
                    isRecordSleepTime = true;
                }

                if (mCbSleepStart.isChecked() || isSEnd) {
                    handler.sendEmptyMessageDelayed(2, 1000);
                    isSPause = false;
                } else {
                    isSPause = true;
                }
                isSEnd = false;
            }
        });
        //娱乐结束
        mCbSleepEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isRepeatClick()) {
                    return;
                }
                db.insertData("睡眠", sleepStartTime, Utils.obtianTime(), Utils.formatTime(timeSText), timeSText);
                handler.removeMessages(2);
                timeSText = 0;
                isSEnd = true;
                isSPause = true;
                isRecordSleepTime = false;
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (handler != null) {
            handler.removeMessages(0);
            handler.removeMessages(1);
            handler.removeMessages(2);
        }
    }
}
