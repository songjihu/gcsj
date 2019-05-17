package com.example.sjh.gcsjdemo.remotedao;

import android.util.Log;

import com.example.sjh.gcsjdemo.entity.Remind;
import com.example.sjh.gcsjdemo.utils.DBUtil;
import com.example.sjh.gcsjdemo.utils.DateUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
     *
     * @param userId
     * @param nowDate
     * @return
     */
    public List<Remind> getMyRemoteReminds(List<String> objList,String userId , String nowDate) throws SQLException {
        List<Remind> remindList = new ArrayList<Remind>();



        String sql = "SELECT * FROM remind WHERE remind_time >= '"+nowDate+"'  AND obj IN (";

        String[] classIds = new String[objList.size()];

        int i = 0;
        for(String ls:objList){
            classIds[i] = ls;
            i++;
        }
        for(int a=0;a<classIds.length-1;a++){
            sql = sql+classIds[a]+",";
        }
        sql = sql+classIds[classIds.length-1]+")";




      //  String sql = "SELECT * FROM remind WHERE obj = ?  and remind_time >= ? ";//
        Log.v("sql语句",sql);
        Connection conn = null ;
        Statement pstate = null;
        //Statement state = null;
        ResultSet rs = null;
        try{
            conn = DBUtil.getDBConn();
            pstate = conn.createStatement();
            rs = pstate.executeQuery(sql);
            Remind rm = null;
            while (rs.next()){
                rm = new Remind();
                rm.setRemindId(rs.getString("remind_id"));
                rm.setRemindTime(DateUtil.formatDate(rs.getDate("remind_time"),"yyyy-MM-dd hh:mm:ss"));
                rm.setTitle(rs.getString("title"));
                rm.setCon(rs.getString("con"));
                rm.setUserId(userId);
                Log.v("远程查出来的数据",rm.toString());
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

        String sql = "INSERT INTO remind ( remind_id, remind_time, title, con, obj) VALUES(?,?,?,?,?)";
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


    /**
     * 获取这个老师所教的班级id
     * @param teacherNo
     * @return
     * @throws SQLException
     */
    public List<String> getClassIds(String teacherNo) throws SQLException {
        List<String> classIds= new ArrayList<String>();

        String sql = "SELECT * FROM t_course WHERE teacher_id = ?";
        Connection conn = null ;
        PreparedStatement pstate = null;
        ResultSet rs = null;
        try{
            conn = DBUtil.getDBConn();
            pstate = conn.prepareStatement(sql);
            pstate.setString(1,teacherNo);
            rs = pstate.executeQuery();
            while (rs.next()){
               classIds.add(rs.getString("course_id"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(rs!=null) rs.close();
            if(pstate!=null) pstate.close();
            if(conn!=null) conn.close();
        }

        return classIds;
    }

    /**
     * 老师发布通知时 要根据自己所教班级来发通知 所以需要按教职工号来获取他所授课的班级的名称
     * @return
     */
    public Map<String, String> getMyClass(List<String> list) throws SQLException {

        String sql = "SELECT * FROM class_info WHERE class_id IN (";

        String[] classIds = new String[list.size()];

        int i = 0;
        for(String ls:list){
            classIds[i] = ls;
            i++;
        }
        for(int a=0;a<classIds.length-1;a++){
            sql = sql+classIds[a]+",";
        }
        sql = sql+classIds[classIds.length-1]+")";


        Map< String,String> hm = new HashMap< String,String>();


        Connection conn = null ;
        Statement state = null;
        ResultSet rs = null;
        try{
            conn = DBUtil.getDBConn();
            state = conn.createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()){
               hm.put(rs.getString("class_id"),rs.getString("class_name"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(rs!=null) rs.close();
            if(state!=null) state.close();
            if(conn!=null) conn.close();
        }

        return hm;

    }


    /**
     *  学生发布通知时 要根据自己所在班级来发通知 所以需要按学号来获取他所在的班级
     * @param userId
     * @return
     */
    public String getMyClassId(String userId) throws SQLException {

        String classId = null;
        String sql = "select class_id from stu_info where stu_no = ?";
        Connection conn = null ;
        PreparedStatement pstate = null;
        ResultSet rs = null;
        try{
            conn = DBUtil.getDBConn();
            pstate = conn.prepareStatement(sql);
            pstate.setString(1,userId);
            rs = pstate.executeQuery();

            if (rs.next()){
                classId = rs.getString("class_id");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(rs!=null) rs.close();
            if(pstate!=null) pstate.close();
            if(conn!=null) conn.close();
        }

        return classId;
    }


    /**
     * 获取我所所在的teamids
     * @param userId
     * @return
     * @throws SQLException
     */
    public List<String> getMyTeams(String userId) throws SQLException {
        List<String> ls = null;
        String classId = null;
        String sql = "select * from user_team where user_id = ?";

        Connection conn = null ;
        PreparedStatement pstate = null;
        ResultSet rs = null;
        try{
            conn = DBUtil.getDBConn();
            pstate = conn.prepareStatement(sql);
            pstate.setString(1,userId);
            rs = pstate.executeQuery();
            ls = new ArrayList<>();
            if (rs.next()){
                if(rs.getString("C_001")!=null &&  !rs.getString("C_001").isEmpty())
                    ls.add(rs.getString("C_001"));

                if(rs.getString("C_002")!=null &&  !rs.getString("C_002").isEmpty())
                    ls.add(rs.getString("C_002"));

                if(rs.getString("C_003")!=null &&  !rs.getString("C_003").isEmpty())
                    ls.add(rs.getString("C_003"));

                if(rs.getString("C_004")!=null &&  !rs.getString("C_004").isEmpty())
                    ls.add(rs.getString("C_004"));

                if(rs.getString("C_005")!=null &&  !rs.getString("C_005").isEmpty())
                    ls.add(rs.getString("C_005"));

                if(rs.getString("C_006")!=null &&  !rs.getString("C_006").isEmpty())
                    ls.add(rs.getString("C_006"));

                if(rs.getString("C_007")!=null &&  !rs.getString("C_007").isEmpty())
                    ls.add(rs.getString("C_007"));

                if(rs.getString("C_008")!=null &&  !rs.getString("C_008").isEmpty())
                    ls.add(rs.getString("C_008"));

                if(rs.getString("C_009")!=null &&  !rs.getString("C_009").isEmpty())
                    ls.add(rs.getString("C_009"));

                if(rs.getString("C_010")!=null &&  !rs.getString("C_010").isEmpty())
                    ls.add(rs.getString("C_010"));

                if(rs.getString("C_011")!=null &&  !rs.getString("C_011").isEmpty())
                    ls.add(rs.getString("C_001"));

                if(rs.getString("C_012")!=null &&  !rs.getString("C_012").isEmpty())
                    ls.add(rs.getString("C_012"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(rs!=null) rs.close();
            if(pstate!=null) pstate.close();
            if(conn!=null) conn.close();
        }

        return ls;
    }

    /**
     * 获取我管理的群组
     * @param userId
     * @return
     * @throws SQLException
     */
    public List<String > getMyManageTeams(String userId) throws SQLException{
        List<String> ls = null;
        String sql = "select * from team_info where team_owner = ? and status =1";

        Connection conn = null ;
        PreparedStatement pstate = null;
        ResultSet rs = null;
        try{
            ls = new ArrayList<>();
            conn = DBUtil.getDBConn();
            pstate = conn.prepareStatement(sql);
            pstate.setString(1,userId);
            rs = pstate.executeQuery();
            while(rs.next()){
                ls.add(rs.getString("team_id"));
                Log.v("我管理的teamid",""+rs.getString("team_id"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(rs!=null) rs.close();
            if(pstate!=null) pstate.close();
            if(conn!=null) conn.close();
        }

        return ls;
    }

}
