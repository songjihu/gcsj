package com.example.sjh.gcsjdemo.service;

import android.util.Log;

import com.example.sjh.gcsjdemo.dbmanager.RemindUtil;
import com.example.sjh.gcsjdemo.entity.Remind;
import com.example.sjh.gcsjdemo.remotedao.RemoteRemindDao;
import com.example.sjh.gcsjdemo.utils.DateUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ProjectName: gcsj
 * @Package: com.example.sjh.gcsjdemo.service
 * @ClassName: RemindService
 * @Description: Remind相关的业务逻辑处理
 * @Author: WX
 * @CreateDate: 2019/5/15 20:19
 * @Version: 1.0
 */
public class RemindService {

    /**
     * 对发布信息填写的检测
     * @param title
     * @param con
     * @param time
     * @return
     */
    public int  dataCheck(String title, String con, String time){

        if(title.length()==0) return 1;

        if(con.length()==0) return 2;

        if(time.length()!=19) return 3;

        return 0;
    }
    public int  dataPublishCheck(String title, String con, String time, String teamId){

        if(title.length()==0) return 1;

        if(con.length()==0) return 2;

        if(time.length()!=19) return 3;

        if(teamId.length()==0) return 4;

        return 0;
    }


    /**
     *  服务器端的Remind 加载到本地数据库
     * @param userId
     */
    public void remoteToLocalService( String userId){
        RemoteRemindDao rrd = new RemoteRemindDao();
        List<String> obj = null;
        RemindUtil ru = null;
        List<Remind> remindList = null;
        try {
            obj = rrd.getMyTeams(userId);
            remindList =  rrd.getMyRemoteReminds(obj,userId, DateUtil.getCurrentDateStr());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(remindList != null && !remindList.isEmpty()){
            ru = new RemindUtil();
            Log.v("远程数据库是否有数据","--------you ");
            ru.remoteToLocal(remindList);
        }
    }

    /**
     * 获取班级的ID和名称
     * @param hm
     * @return
     */
    public Set getClassName(Map< String,String> hm){
        Set< Map.Entry< String,String > > st;
        st = hm.entrySet();
        return st;
    }

    public String getClass(String userId){
        String classId = null;
        RemoteRemindDao rrd = new RemoteRemindDao();
        try {
            classId = rrd.getMyClassId(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classId;
    }

    public List<String> getMyManageTeamService(String userId) throws SQLException {
        RemoteRemindDao rrd = new RemoteRemindDao();
        return rrd.getMyManageTeams(userId);
    }


    public void  insertIntoRemoteService(String title, String userId, String con, String time, String obj){
        RemoteRemindDao rrd = new RemoteRemindDao();
        Remind rm = new Remind(DateUtil.getNowDateStr(),userId,time,title,con);
        try {
            rrd.insertRemindToRemote(rm,obj);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
