package com.example.sjh.gcsjdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.example.sjh.gcsjdemo.ui.fragment.ActiveFragment;
import com.example.sjh.gcsjdemo.ui.fragment.DataStatisticsFragment;
import com.example.sjh.gcsjdemo.ui.fragment.RecordFragment;

public class TimeActivity extends AppCompatActivity implements View.OnClickListener {
    private RadioButton mBtActive;
    private RadioButton mBtRecord;
    private RadioButton mBtDatastatis;
    private FrameLayout mFlContent;
    private FragmentTransaction frt;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        initView();
        initAction();
    }

    private void initView() {
        mBtActive = (RadioButton) findViewById(R.id.rb_active);
        mBtRecord = (RadioButton) findViewById(R.id.rb_record);
        mBtDatastatis = (RadioButton) findViewById(R.id.rb_datastatis);
        mFlContent = (FrameLayout) findViewById(R.id.fl_content);
        replaceFragment(new ActiveFragment());
    }

    private void initAction() {
        mBtActive.setOnClickListener(this);
        mBtRecord.setOnClickListener(this);
        mBtDatastatis.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rb_active:
                replaceFragment(new ActiveFragment());
                break;
            case R.id.rb_record:
                replaceFragment(new RecordFragment());
                break;
            case R.id.rb_datastatis:
                replaceFragment(new DataStatisticsFragment());
                break;
        }
    }

    private void replaceFragment(Fragment ft){
        fm = getSupportFragmentManager();
        frt = fm.beginTransaction();
        frt.replace(R.id.fl_content,ft);
        frt.commit();
    }
}
