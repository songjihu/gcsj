package com.example.sjh.gcsjdemo.helper;

import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParsePosition;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * 消息解压规则
 * 解出来源，去向，时间和内容
 */


public class MessageTranslateBack {
    private String msgFrom;//消息发送者
    private String msgFromId;//消息发送者
    private String msgTo;//消息接收群组id
    private Date msgDate;//消息发送时间
    private String msgContent;//消息内容



    public MessageTranslateBack(String msgJson){

        String body = msgJson;
        boolean left = body.substring(0, 1).equals("{");
        boolean right = body.substring(body.length()-1, body.length()).equals("}");
        if(left&&right){
            try {
                JSONObject obj = new JSONObject(body);

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                ParsePosition pos = new ParsePosition(0);
                String dateString = obj.getString("msgDate");

                this.msgFrom = obj.getString("msgFrom");
                this.msgFromId = obj.getString("msgFromId");
                this.msgTo = obj.getString("msgTo");
                this.msgDate = formatter.parse(dateString, pos);
                this.msgContent = obj.getString("msgContent");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    public Date getMsgDate() {
        return msgDate;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public String getMsgTo() {
        return msgTo;
    }

    public String getMsgFrom() {
        return msgFrom;
    }

    public String getMsgFromId() {
        return msgFromId;
    }

    public void setMsgFromId(String msgFromId) {
        this.msgFromId = msgFromId;
    }

    public void setMsgFrom(String msgFrom) {
        this.msgFrom = msgFrom;
    }

    public void setMsgTo(String msgTo) {
        this.msgTo = msgTo;
    }

    public void setMsgDate(Date msgDate) {
        this.msgDate = msgDate;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

}
