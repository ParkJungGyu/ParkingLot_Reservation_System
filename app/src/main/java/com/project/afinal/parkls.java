package com.project.afinal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class parkls {

    @SerializedName("is_park")
    @Expose
    String is_park; //주차 빈자리 여부

    @SerializedName("p_number")
    @Expose
    String p_number; //주차장 자리 번호

    @SerializedName("Reserv")
    @Expose
    String Reserv; //주차장 예약 여부

    public void setIs_park(String is_park) {
        this.is_park = is_park;
    }
    public void setP_number(String p_number) {
        this.p_number = p_number;
    }
    public void setReserv(String Reserv) {
        this.Reserv = Reserv;
    }
    public String getIs_park(){ return is_park;}
    public String getP_number(){ return p_number;}
    public String getReserv(){ return Reserv;}

}
