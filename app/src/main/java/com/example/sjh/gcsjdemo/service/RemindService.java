package com.example.sjh.gcsjdemo.service;

import com.example.sjh.gcsjdemo.dbmanager.RemindUtil;
import com.example.sjh.gcsjdemo.entity.Remind;
import com.example.sjh.gcsjdemo.remotedao.RemoteRemindDao;
import com.example.sjh.gcsjdemo.utils.DateUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public int  dataCheck(String title, String con, String time){

        if(title.length()==0) return 1;

        if(con.length()==0) return 2;

        if(time.length()!=19) return 3;

        return 0;
    }

    public void remoteToLocalService(String obj, String userId){
        RemoteRemindDao rrd = new RemoteRemindDao();
        RemindUtil ru = null;
        List<Remind> remindList = null;
        try {
            remindList =  rrd.getMyRemoteReminds(obj,userId, DateUtil.getCurrentDateStr());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(remindList != null){
            ru.remoteToLocal(remindList);
        }
    }

}
