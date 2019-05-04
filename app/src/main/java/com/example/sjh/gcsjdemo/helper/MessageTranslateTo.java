package com.example.sjh.gcsjdemo.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 消息打包规则
 * 要传入来源去向和内容
 */

public class MessageTranslateTo {
    private String msgFrom;//消息发送者
    private String msgTo;//消息接收群组id
    private String msgDate;//消息发送时间
    private String msgContent;//消息内容
    private String msgJson;//转换结果


    public String MessageTranslateTo(String msgFrom, String msgTo, String msgContent){
        /*Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,0);
        calendar.add(Calendar.MINUTE, 0);*/
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        this.msgFrom = msgFrom;
        this.msgTo = msgTo;
        this.msgDate = formatter.format(currentTime);
        this.msgContent = msgContent;
        this.msgJson = "{\"msgFrom\":\""+this.msgFrom+"\",\"msgTo\":\""+this.msgTo+"\",\"msgDate\":\""+this.msgDate+"\",\"msgContent\":\""+this.msgContent+"\"}";
        return this.msgJson;

    }

    public void setMsgFrom(String msgFrom) {
        this.msgFrom = msgFrom;
    }

    public void setMsgTo(String msgTo) {
        this.msgTo = msgTo;
    }

    public void setMsgDate(String msgDate) {
        this.msgDate = msgDate;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public void setMsgJson(String msgJson) {
        this.msgJson = msgJson;
    }

    public String getMsgFrom() {
        return msgFrom;
    }

    public String getMsgTo() {
        return msgTo;
    }

    public String getMsgDate() {
        return msgDate;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public String getMsgJson() {
        return msgJson;
    }

}
