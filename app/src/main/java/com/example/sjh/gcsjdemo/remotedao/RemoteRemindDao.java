package com.example.sjh.gcsjdemo.remotedao;

import com.example.sjh.gcsjdemo.entity.Remind;
import com.example.sjh.gcsjdemo.utils.DBUtil;
import com.example.sjh.gcsjdemo.utils.DateUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: gcsj
 * @Package: com.example.sjh.gcsjdemo.remotedao
 * @ClassName: RemoteRemindDao
 * @Description: 远程数据库关于Remind的操作
 * @Author: WX
 * @CreateDate: 2019/5/15 18:37
 * @Version: 1.0
 */
public class RemoteRemindDao {

    /**
     *  按条件把远程数据库的reminds查询出来
     * @param obj
     * @param userId
     * @param nowDate
     * @return
     */
    public List<Remind> getMyRemoteReminds(String obj,String userId , String nowDate) throws SQLException {
        List<Remind> remindList = new ArrayList<Remind>();

        String sql = "SELECT * FROM REMIND WHERE OBJ = ? and DATE_TIME >= ?";
        Connection conn = null ;
        PreparedStatement pstate = null;
        ResultSet rs = null;
        try{
            conn = DBUtil.getDBConn();
            pstate = conn.prepareStatement(sql);
            pstate.setString(1,obj);
            pstate.setString(2,nowDate);
            rs = pstate.executeQuery();
            Remind rm = null;
            while (rs.next()){
                rm = new Remind();
                rm.setRemindId(rs.getString("remind_id"));
                rm.setRemindTime(DateUtil.formatDate(rs.getDate("remind_time"),"yyyy-MM-dd hh:mm:ss"));
                rm.setTitle(rs.getString("title"));
                rm.setCon(rs.getString("con"));
                rm.setUserId(userId);
                remindList.add(rm);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(rs!=null) rs.close();
            if(pstate!=null) pstate.close();
            if(conn!=null) conn.close();
        }

        return remindList;
    }

    /**
     * 发布公告
     * @param rm
     * @param obj
     * @return
     * @throws SQLException
     */

    public boolean insertRemindToRemote(Remind rm , String obj) throws SQLException {
        boolean flag = false;

        String sql = "INSERT INTO REMIND ( REMIND_ID, REMIND_TIME, TITLE, CON, OBJ) VALUES(?,?,?,?,?)";
        Connection conn = null ;
        PreparedStatement pstate = null;
        try{
            conn = DBUtil.getDBConn();
            pstate = conn.prepareStatement(sql);
            pstate.setString(1,rm.getRemindId());
            pstate.setString(2,rm.getRemindTime());
            pstate.setString(3,rm.getTitle());
            pstate.setString(4,rm.getCon());
            pstate.setString(5,obj);
            if (pstate.executeUpdate() != 0) flag = true;
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            if(pstate!=null) pstate.close();
            if(conn!=null) conn.close();
        }

        return flag;
    }


}
