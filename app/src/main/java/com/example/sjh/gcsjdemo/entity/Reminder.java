package com.example.sjh.gcsjdemo.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 修改于 19/4/14.
 * 修改为待提示信息
 */
public class Reminder implements Parcelable {
    private String msg;

    public Reminder(String msg) {
        this.msg = msg;
    }

    protected Reminder(Parcel in) {
        msg = in.readString();
    }

    public static final Creator<Reminder> CREATOR = new Creator<Reminder>() {
        @Override
        public Reminder createFromParcel(Parcel in) {
            return new Reminder(in);
        }

        @Override
        public Reminder[] newArray(int size) {
            return new Reminder[size];
        }
    };

    public String getMsg() {
        return msg;
    }

    public void setMsg(String title) {
        this.msg = msg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(msg);
    }
}
