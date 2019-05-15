package com.example.sjh.gcsjdemo.person;

import android.os.Parcel;
import android.os.Parcelable;

public class dCourseInfo implements Parcelable {

private String cName;
private String cTeacherName;
private String cAddress;
private String scId;
private String day;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getScId() {
        return scId;
    }

    public void setScId(String scId) {
        this.scId = scId;
    }

    public dCourseInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

    public dCourseInfo(String cName, String cTeacherName, String cAddress,String scId,String day) {
        super();
        this.cName = cName;
        this.cTeacherName = cTeacherName;
        this.cAddress = cAddress;
        this.scId=scId;
        this.day=day;



    }
    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcTeacherName() {
        return cTeacherName;
    }

    public void setcTeacherName(String cTeacherName) {
        this.cTeacherName = cTeacherName;
    }

    public String getcAddress() {
        return cAddress;
    }

    public void setcAddress(String cAddress) {
        this.cAddress = cAddress;
    }


    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(cName);
        parcel.writeString(cTeacherName);
        parcel.writeString(cAddress);
        parcel.writeString(scId);
        parcel.writeString(day);

    }

    public static final Parcelable.Creator<dCourseInfo> CREATOR = new Parcelable.Creator<dCourseInfo>() {
        @Override
        public dCourseInfo createFromParcel(Parcel source) {

            dCourseInfo dcourseinfo = new dCourseInfo();
            dcourseinfo.cName=source.readString();
            dcourseinfo.cTeacherName=source.readString();
            dcourseinfo.cAddress=source.readString();
            dcourseinfo.scId=source.readString();
            dcourseinfo.day=source.readString();



            return dcourseinfo;
        }
        @Override
        public dCourseInfo[] newArray(int size) {
            return new dCourseInfo[size];
        }
    };




}
