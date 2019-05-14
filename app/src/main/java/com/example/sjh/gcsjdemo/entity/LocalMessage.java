package com.example.sjh.gcsjdemo.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 修改于 19/4/27.
 * 修改为本地聊天信息
 */
public class LocalMessage implements Parcelable {
    private String msg;//聊天

    public LocalMessage(String msg) {
        this.msg = msg;
    }

    protected LocalMessage(Parcel in) {
        msg = in.readString();
    }

    public static final Creator<LocalMessage> CREATOR = new Creator<LocalMessage>() {
        @Override
        public LocalMessage createFromParcel(Parcel in) {
            return new LocalMessage(in);
        }

        @Override
        public LocalMessage[] newArray(int size) {
            return new LocalMessage[size];
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
