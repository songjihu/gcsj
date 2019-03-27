package MyThread;

import android.util.Log;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Entity.UserInfo;


public class LoginThread extends Thread {
    public UserInfo getUu() {
        return uu;
    }

    public void setUu(UserInfo uu) {
        this.uu = uu;
    }

    private UserInfo uu;



    public LoginThread(String id, String password)
    {
        uu.setUserId(id);
        uu.setUserPwd(password);

    }
    public void run()
    {


        try {
            Class.forName("com.mysql.jdbc.Driver");
            java.sql.Connection cn= DriverManager.getConnection("jdbc:mysql://182.254.161.189/kcsj","root","mypwd");
            String sql="SELECT userpwd FROM `user` WHERE userid = "+uu.getUserId();
            Statement st=(Statement)cn.createStatement();
            ResultSet rs=st.executeQuery(sql);
            while(rs.next()){
                uu.setRpwd(rs.getString("userpwd"));

                Log.i("LoginActivity",uu.getRpwd());
            }
            cn.close();
            st.close();
            rs.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //System.out.println("hello " + name);
    }
/*
    public String getRt() {
        if(this.rpwd.equals(this.userpwd))
            return this.userpwd;
        else
            return this.rpwd;
    }
*/
    public static void main(String[] args)
    {

    }
}
