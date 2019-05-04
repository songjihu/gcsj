package com.example.sjh.gcsjdemo.utils;

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
}
