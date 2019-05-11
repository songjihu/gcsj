package com.example.sjh.gcsjdemo.group;
/**
 * 产生随机5位数群号码
 */

import java.util.Random;

public class RandomN {

    public RandomN(){}


    public String Generate()
    {
        int t;
        String n=null;
        Random r = new Random(10);
        t = r.nextInt(99999);
        if(t < 100)
        {
            n = "000" + Integer.toString(t);
            t = Integer.parseInt(n);
        }
        else if(t < 1000)
        {
            n = "00" + Integer.toString(t);
            t = Integer.parseInt(n);             }
        else if(t < 10000)
        {
            n = "0" + Integer.toString(t);
            t = Integer.parseInt(n);
        }
        else
            n=Integer.toString(t);
        return n;

    }



}
