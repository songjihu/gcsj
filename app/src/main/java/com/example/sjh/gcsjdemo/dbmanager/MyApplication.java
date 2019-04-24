package com.example.sjh.gcsjdemo.dbmanager;

import android.app.Application;
import android.content.Context;

/**
 * @ProjectName: gcsj
 * @Package: com.example.sjh.gcsjdemo.dbmanager
 * @ClassName: MyApplication
 * @Description: 初始化数据库
 * @Author: WX
 * @CreateDate: 2019/4/21 17:28
 * @Version: 1.0
 */
public class MyApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        GreenDaoManager.getInstance();
    }

    public static Context getContext() {
        return mContext;
    }
}

