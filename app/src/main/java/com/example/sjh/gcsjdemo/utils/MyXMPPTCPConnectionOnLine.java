package com.example.sjh.gcsjdemo.utils;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;


public class MyXMPPTCPConnectionOnLine extends XMPPTCPConnection {
    private static MyXMPPTCPConnectionOnLine connection;
    private MyXMPPTCPConnectionOnLine(XMPPTCPConnectionConfiguration config) {
        super(config);
    }
    public static synchronized MyXMPPTCPConnectionOnLine getInstance(){
        //初始化XMPPTCPConnection相关配置
        if(connection == null){
            XMPPTCPConnectionConfiguration.Builder builder = XMPPTCPConnectionConfiguration.builder();
            //设置连接超时的最大时间
            builder.setConnectTimeout(10000);
            //设置登录openfire的用户名和密码
            builder.setUsernameAndPassword("20162430722", "123");
            //设置安全模式
            builder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
            builder.setResource("Android");
            //设置服务器名称
            builder.setServiceName("182.254.161.189");
            //设置主机地址
            builder.setHost("182.254.161.189");
            //设置端口号
            builder.setPort(5222);
            //是否查看debug日志
            builder.setDebuggerEnabled(true);
            //设置为在线
            builder.setSendPresence(true);
            connection = new MyXMPPTCPConnectionOnLine(builder.build());
            connection.setPacketReplyTimeout(10000);
        }
        return connection;
    }
}
