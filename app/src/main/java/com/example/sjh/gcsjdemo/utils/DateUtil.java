package com.example.sjh.gcsjdemo.utils;


import com.mysql.jdbc.StringUtils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateUtil {


	/**
	 * 	把给定的一个Date类型时间date转换成给定格式format的String类型并返回
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(Date date,String format){
		String result="";
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		if(date!=null){
			result=sdf.format(date);
		}
		return result;
	}

	/**
	 *	把给定的一个时间字符串str按给定的格式format转换成date类型并返回
	 * @param str :要转换的时间字符串
	 * @param format :给定转换的格式
	 * @return
	 * @throws Exception
	 */
	public static Date formatString(String str,String format)throws ParseException{
		if(StringUtils.isEmptyOrWhitespaceOnly(str)){
			return null;
		}
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		return sdf.parse(str);
	}

	/**
	 * 获取当前时间并以字符串（yyyy-MM-dd hh:mm:ss）的形式返回
	 * @return
	 * @throws Exception
	 */
	public static String getCurrentDateStr(){
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return sdf.format(date);
	}
	/**
	 * 获取当前时间并以字符串（yyyyMMddhhmmss）的形式返回
	 * @return
	 * @throws Exception
	 */
	public static String getNowDateStr(){
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmss");
		return sdf.format(date);
	}

	/**
	 * 获取今天的日期以字符串的形式返回
	 * @return
	 */
	public static String getToDayStr(){
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		return sdf.format(date);
	}

}
