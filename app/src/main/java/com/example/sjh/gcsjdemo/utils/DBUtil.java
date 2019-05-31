package com.example.sjh.gcsjdemo.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
	public static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	
	public static final String URL = "jdbc:mysql://182.254.161.189:3306/gcsj";
	
	public static final String USER ="root";
	
	public static final String PASSWORD = "mypwd";
	
	
	
	public static Connection getDBConn(){
		Connection conn = null;
		try {
			Class.forName(DB_DRIVER);
			conn =  DriverManager.getConnection(URL+"?user="+USER+"&password="+PASSWORD+"&useUnicode=true&characterEncoding=utf-8");
		} catch (ClassNotFoundException e) {
		
			System.out.println("驱动加载异常:"+e);
		
		} catch (SQLException e) {
			
			System.out.println("数据库链接出现异常:"+e);
			
		}
		return conn;

	}
	
	
	public static void closeConn(Connection conn){
		try{
			if(conn!=null){
				conn.close();
			}
		}catch(SQLException e){
			System.out.println("数据库链接关闭出现异常：" + e);
		}
	}
	
	public static void closeStatement(Statement state){
		try{
			if(state!=null){
				state.close();
			}
		}catch(SQLException e){
			System.out.println("数据库链接关闭出现异常：" + e);
		}
	}
	
	
	public static void closeResultSet(ResultSet rs){
		try{
			if(rs!=null){
				rs.close();
			}
		}catch(SQLException e){
			System.out.println("数据库链接关闭出现异常：" + e);
		}
	}
	
	
	
}
