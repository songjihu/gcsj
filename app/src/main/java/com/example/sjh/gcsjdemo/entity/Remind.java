
package com.example.sjh.gcsjdemo.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

import java.util.Date;

/**
 * @ClassName: Remind
 * @Description: 实体Remind
 * @Author: ZZUWX
 * @Date: 2019/4/17 22:18
 */

@Entity
public class Remind {
    @Id
     private String remindId;
    @NotNull
     private String remindTime;
    @NotNull
     private String title;
    @NotNull
     private String con;


    @Generated(hash = 1989415835)
    public Remind(String remindId, @NotNull String remindTime,
            @NotNull String title, @NotNull String con) {
        this.remindId = remindId;
        this.remindTime = remindTime;
        this.title = title;
        this.con = con;
    }

    @Generated(hash = 1173539496)
    public Remind() {
    }


    public void setRemindId(String remindId) {
        this.remindId = remindId;
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

    public String getRemindId() {
        return this.remindId;
    }
}
