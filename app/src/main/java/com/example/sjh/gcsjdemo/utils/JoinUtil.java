package com.example.sjh.gcsjdemo.utils;


public class JoinUtil {
    private static int flg;
    private static JoinUtil joinflg=null;
    public static synchronized JoinUtil getInstance(){
        //初始化XMPPTCPConnection相关配置
        return joinflg;
    }

}
