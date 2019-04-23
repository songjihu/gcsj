package com.example.sjh.gcsjdemo.model;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.sjh.gcsjdemo.entity.Article;


/**
 * Created by Administrator on 2017/7/14.
 */

public class Friend implements Parcelable{
    private String jid;
    private String name;

    public Friend() {
    }

    public Friend(String jid, String name) {
        this.jid = jid;
        this.name = name;
    }

    protected Friend(Parcel in) {
        jid = in.readString();
        name = in.readString();

    }

    public static final Creator<Friend> CREATOR = new Creator<Friend>() {
        @Override
        public Friend createFromParcel(Parcel in) {
            return new Friend(in);
        }

        @Override
        public Friend[] newArray(int size) {
            return new Friend[size];
        }
    };

    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(jid);
        dest.writeString(name);
    }
}
