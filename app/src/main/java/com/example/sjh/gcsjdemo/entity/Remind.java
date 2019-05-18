
package com.example.sjh.gcsjdemo.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

import java.util.Date;

/**
 * @ClassName: Remind
 * @Description: 实体Remind
 * @Author: ZZUWX
 * @Date: 2019/4/17 22:18
 */

@Entity(nameInDb = "REMIND", indexes = {
        @Index(value = "remindId DESC,userId DESC", unique = true)
})
public class Remind {
    @Id
     private String remindId;
    @NotNull
     private String userId;
     @NotNull
     private String remindTime;
    @NotNull
     private String title;
    @NotNull
     private String con;




    @Generated(hash = 1700286399)
    public Remind(String remindId, @NotNull String userId,
            @NotNull String remindTime, @NotNull String title,
            @NotNull String con) {
        this.remindId = remindId;
        this.userId = userId;
        this.remindTime = remindTime;
        this.title = title;
        this.con = con;
    }

    @Generated(hash = 1173539496)
    public Remind() {
    }




    public String getRemindId() {
        return this.remindId;
    }

    public void setRemindId(String remindId) {
        this.remindId = remindId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String  getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(String remindTime) {
        this.remindTime = remindTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con;
    }


    @Override
    public String toString() {
        return "Remind{" +
                "remindId='" + remindId + '\'' +
                ", userId='" + userId + '\'' +
                ", remindTime='" + remindTime + '\'' +
                ", title='" + title + '\'' +
                ", con='" + con + '\'' +
                '}';
    }

    //按格式显示事件
    public String toTypeString(){
        return "事件："+ title+ "\n" +
               "内容："+ con+"\n" +
                "①\n" + "②\n" + "③\n"+
                "时间："+remindTime+"\n" ;
    }




}
