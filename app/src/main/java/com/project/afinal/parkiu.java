package com.project.afinal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class parkiu {
    @SerializedName("parkname")
    @Expose
    String parkname; //주차장 이름

    @SerializedName("p_number")
    @Expose
    String p_number; //주차장 번호

    @SerializedName("date")
    @Expose
    String date; //예약 날짜,시간

    public void setparkname(String parkname) {
        this.parkname = parkname;
    }
    public void setP_number(String p_number) {
        this.p_number = p_number;
    }
    public void setdate(String date) {
        this.date = date;
    }
    public String getparkname(){ return parkname;}
    public String getP_number(){ return p_number;}
    public String getRdate(){ return date;}
}
