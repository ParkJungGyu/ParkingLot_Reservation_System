package com.project.afinal;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Park_User_Data implements Serializable {
    @SerializedName("id")
    private String id; //유저 id
    @SerializedName("parkname")
    private String parkName; //주차장 이름
    @SerializedName("parkempty")
    private String parkempty; //주차 빈 자리 수
    @SerializedName("parkspace")
    private String parkspace; //주차 가능 자리 수
    @SerializedName("P_number")
    private String P_number; // 주차장 번호

    public Park_User_Data(String id,String parkName){
        this.id=id;
        this.parkName=parkName;
    }
    public void setParkempty(String parkempty){
        this.parkempty=parkempty;
    }
    public void setParkspace(String parkspace){
        this.parkspace=parkspace;
    }
    public void setP_number(String P_number){
        this.P_number=P_number;
    }
    public String getP_number(){
        return P_number;
    }
    public String getid(){
        return id;
    }
    public String getParkName(){
        return parkName;
    }
    public String getParkempty(){
        return parkempty;
    }
    public String getParkspace(){
        return parkspace;
    }
}
