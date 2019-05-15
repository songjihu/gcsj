package com.example.sjh.gcsjdemo.group;

public class GroupInfo {
    private String grpno;
    private String grpname;
    private String owner;
    private int minnum;
    private int maxnum;
    private int currentnum;
    private int status;

    public String getGrpno() {
        return grpno;
    }

    public void setGrpno(String grpno) {
        this.grpno = grpno;
    }

    public String getGrpname() {
        return grpname;
    }

    public void setGrpname(String grpname) {
        this.grpname = grpname;
    }

    public String getOwner() {
        return owner;
    }

    public int getMinnum() {
        return minnum;
    }

    public void setMinnum(int minnum) {
        this.minnum = minnum;
    }

    public int getMaxnum() { return maxnum; }

    public void setMaxnumnum(int maxnumnum) {
        this.maxnum = maxnum;
    }

    public int getCurrentnum() { return currentnum; }

    public int getStatus() {
        return status;
    }


}
