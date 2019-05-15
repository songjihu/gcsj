package com.example.sjh.gcsjdemo.person;

import android.os.Parcel;
import android.os.Parcelable;

public class dCourse implements Parcelable {
   private String d_one;
    private String d_two;
    private String d_three;
    private String d_four;
    private String d_five;

    public dCourse() {
        super();
        // TODO Auto-generated constructor stub
    }

    public dCourse(String d_one, String d_two, String d_three, String d_four, String d_five) {
        super();
        this.d_one = d_one;
        this.d_two = d_two;
        this.d_three = d_three;
        this.d_four=d_four;
        this.d_five=d_five;


    }

    public String getD_one() {
        return d_one;
    }

    public void setD_one(String d_one) {
        this.d_one = d_one;
    }

    public String getD_two() {
        return d_two;
    }

    public void setD_two(String d_two) {
        this.d_two = d_two;
    }

    public String getD_three() {
        return d_three;
    }

    public void setD_three(String d_three) {
        this.d_three = d_three;
    }

    public String getD_four() {
        return d_four;
    }

    public void setD_four(String d_four) {
        this.d_four = d_four;
    }

    public String getD_five() {
        return d_five;
    }

    public void setD_five(String d_five) {
        this.d_five = d_five;
    }


    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(d_one);
        parcel.writeString(d_two);
        parcel.writeString(d_three);
        parcel.writeString(d_four);
        parcel.writeString(d_five);

    }

    public static final Parcelable.Creator<dCourse> CREATOR = new Parcelable.Creator<dCourse>() {
        @Override
        public dCourse createFromParcel(Parcel source) {

            dCourse dcourse = new dCourse();
            dcourse.d_one=source.readString();
            dcourse.d_two=source.readString();
            dcourse.d_three=source.readString();
            dcourse.d_four=source.readString();
            dcourse.d_five=source.readString();

            return dcourse;
        }
        @Override
        public dCourse[] newArray(int size) {
            return new dCourse[size];
        }
    };



}
