package com.example.sjh.gcsjdemo.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * 修改于 2019/4/28
 * sjh
 */

@Entity
public class ChatMessage {
    @Id
    private String msgId;
    @NotNull
    private String msgContent;
    @NotNull
    private int from;
    @NotNull
    private String msgTimetag;
    @NotNull
    private String msgTeam;


    public ChatMessage(String msgContent, int from) {
        this.msgContent = msgContent;
        this.from = from;
    }

    @Generated(hash = 1547860739)
    public ChatMessage(String msgId, @NotNull String msgContent, int from,
            @NotNull String msgTimetag, @NotNull String msgTeam) {
        this.msgId = msgId;
        this.msgContent = msgContent;
        this.from = from;
        this.msgTimetag = msgTimetag;
        this.msgTeam = msgTeam;
    }

    @Generated(hash = 2271208)
    public ChatMessage() {
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public String getMsgId() {
        return this.msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getMsgTimetag() {
        return this.msgTimetag;
    }

    public void setMsgTimetag(String msgTimetag) {
        this.msgTimetag = msgTimetag;
    }

    public String getMsgTeam() {
        return this.msgTeam;
    }

    public void setMsgTeam(String msgTeam) {
        this.msgTeam = msgTeam;
    }
}
