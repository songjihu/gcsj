package com.example.sjh.gcsjdemo.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by YoKeyword on 16/2/1.
 * 修改于 19/4/8.
 * 修改为未上课程的提示信息
 */
public class Article implements Parcelable {
    private String title;
    private String content;
    private String status;



    public Article(String title, String status) {
        this.title = title;
        this.status = status;
    }

    protected Article(Parcel in) {
        title = in.readString();
        content = in.readString();
        status = in.readString();
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCheckInStatus() {
        return status;
    }

    public void setImgRes(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(status);
    }
}
