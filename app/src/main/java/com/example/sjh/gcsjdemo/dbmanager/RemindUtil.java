package com.example.sjh.gcsjdemo.dbmanager;

import android.database.Cursor;
import android.util.Log;

import com.example.sjh.database.greenDao.db.DaoSession;
import com.example.sjh.database.greenDao.db.RemindDao;
import com.example.sjh.gcsjdemo.entity.Remind;
import com.example.sjh.gcsjdemo.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: gcsj
 * @Package: com.example.sjh.gcsjdemo.dbmanager
 * @ClassName: RemindUtil
 * @Description: java类作用描述
 * @Author: WX
 * @CreateDate: 2019/4/22 10:08
 * @Version: 1.0
 */
public class RemindUtil {

    //获取数据库
   private RemindDao remindDao ;

   private DaoSession daoSession;

    public RemindUtil(){
        remindDao = GreenDaoManager.getInstance().getSession().getRemindDao();
    }


    /**
     *  插入一条数据，插入成功返回true
     * @param remind
     * @return
     */
    public boolean insertRemind(Remind remind){

        boolean flag;
       flag = remindDao.insert(remind)==1?true:false ;//成功插入返回1

       return flag;
    }

    /**
     * 按remind_id删除数据
     * @param remind_id
     */
    public void deleteRemind(String remind_id){
        remindDao.deleteByKey(remind_id);
    }

    /**
     * 删除所有remind表里面的数据
     */
    public void deleteAllReminds(){
        remindDao.deleteAll();
    }


    /**
     * 更新remind的一条数据
     * @param remind
     */
    public void updateRemind(Remind remind){
        remindDao.update(remind);
    }


    /**
     *  获取今天和今天之后的reminds
     *  这里面的sql是自己写的
     * @return
     */
    public List<Remind> querryReminds(){

        daoSession = GreenDaoManager.getInstance().getSession();
        List<Remind>  remindList = new ArrayList<Remind>();
        String nowDate = DateUtil.getToDayStr();
        String sql = "select * from REMIND where substr(REMIND_TIME,1,10) > "+nowDate+" group by substr(REMIND_TIME, 1, 10)";//自己写的sql
        Cursor cursor = daoSession.getDatabase().rawQuery(sql,null);
        Remind remind;
        while (cursor.moveToNext()){
            remind = new Remind();
            remind.setRemindId(cursor.getString(0));    //获取数据库的第一列
            remind.setRemindTime(cursor.getString(1));  //第二列
            remind.setTitle(cursor.getString(2));       //第三列
            remind.setCon(cursor.getString(3));         //第四列
            remindList.add(remind);
        }

        return remindList;
    }




}
