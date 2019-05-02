package com.example.sjh.gcsjdemo;

import java.util.Calendar;

/**
 * Created by PSBC-26 on 2019/4/16.
 */

public class Utils {
    private static long first = 0;
    private static long second = 0;
    public static String formatTime(int time){
        String formatTime = "";
        if (time<60){//小于一分钟
            formatTime = Integer.toString(time)+"秒";
            return formatTime;
        }else{
            int hour = time/(60*60);
            int minute = (time-hour*60*60)/60;
            int second = time%60;
            formatTime = hour+"小时"+minute+"分"+second+"秒";
            return formatTime;
        }
    }

    public static boolean isRepeatClick(){
        second = System.currentTimeMillis();
        if (second - first < 500) {
            first = second;
            return true;
        }
        first = second;
        return false;
    }

    /**
     * 获取小时分钟（19:20）
     * @return
     */
    public static String obtianTime(){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        String hourStr = "";
        String minuteStr = "";
        if (hour<10){
            hourStr = "0"+hour;
        }else {
            hourStr = Integer.toString(hour);
        }
        if (minute<10){
            minuteStr = "0"+minute;
        }else {
            minuteStr = Integer.toString(minute);
        }
        return hourStr+":"+minuteStr;
    }
}
