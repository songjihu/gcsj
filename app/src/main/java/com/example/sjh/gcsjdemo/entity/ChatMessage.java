package com.example.sjh.gcsjdemo.entity;

import com.example.sjh.gcsjdemo.media.data.model.Message;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * 修改于 2019/5/7
 * sjh
 * 消息本地数据库
 * 存储 自增id和msg
 */

@Entity
public class ChatMessage {
    @Id(autoincrement = true)
    private Long msgId;//消息id

    @NotNull
    private String msg;//消息


    public ChatMessage(String msg) {
        this.msg = msg;
    }


    @Generated(hash = 1574058549)
    public ChatMessage(Long msgId, @NotNull String msg) {
        this.msgId = msgId;
        this.msg = msg;
    }


    @Generated(hash = 2271208)
    public ChatMessage() {
    }


    public Long getMsgId() {
        return this.msgId;
    }


    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }


    public String getMsg() {
        return this.msg;
    }


    public void setMsg(String msg) {
        this.msg = msg;
    }




}
