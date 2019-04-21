package com.example.sjh.gcsjdemo.dbmanager;

import com.example.sjh.database.greenDao.db.DaoMaster;
import com.example.sjh.database.greenDao.db.DaoSession;

/**
 * @ProjectName: gcsj
 * @Package: com.example.sjh.gcsjdemo.dbmanager
 * @ClassName: GreenDaoManager
 * @Description: java类作用描述
 * @Author: WX
 * @CreateDate: 2019/4/21 17:27
 * @Version: 1.0
 */
public class GreenDaoManager {
    private static GreenDaoManager mInstance;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private DaoMaster.DevOpenHelper devOpenHelper;


    public GreenDaoManager() {
        //创建一个数据库

        devOpenHelper = new DaoMaster.DevOpenHelper(MyApplication.getContext(), "greendao-db", null);
        DaoMaster mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
    }

    public static GreenDaoManager getInstance() {
        if (mInstance == null) {
            mInstance = new GreenDaoManager();
        }
        return mInstance;
    }

    public DaoMaster getMaster() {
        return mDaoMaster;
    }

    public DaoSession getSession() {
        return mDaoSession;
    }

    public DaoSession getNewSession() {
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }

    /**
     * 关闭数据连接
     */
    public void close() {
        if (devOpenHelper != null) {
            devOpenHelper.close();
        }
    }
}



