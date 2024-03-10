package com.project.afinal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class parking {

    @SerializedName("parkname")
    @Expose
    private String parkname; //주차장 이름
    @SerializedName("parkx")
    @Expose
    private String parkx; //주차장 x좌표 값
    @SerializedName("parky")
    @Expose
    private String parky; //주차장 y좌표 값
    @SerializedName("parkempty")
    @Expose
    private String parkempty; //주차장 빈자리 수
    @SerializedName("parkspace")
    @Expose
    private String parkspace; //주차 가능 자리 수

    public String getParkname() {
        return parkname;
    }

    public void setParkname(String parkname) {
        this.parkname = parkname;
    }

    public String getParkx() {
        return parkx;
    }

    public void setParkx(String parkx) {
        this.parkx = parkx;
    }

    public String getParky() {
        return parky;
    }

    public void setParky(String parky) {
        this.parky = parky;
    }

    public String getParkempty() {
        return parkempty;
    }

    public void setParkempty(String parkempty) {
        this.parkempty = parkempty;
    }

    public String getParkspace() {
        return parkspace;
    }

    public void setParkspace(String parkspace) {
        this.parkspace = parkspace;
    }

}
