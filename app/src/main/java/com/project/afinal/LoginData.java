package com.project.afinal;

import com.google.gson.annotations.SerializedName;

public class LoginData {
    @SerializedName("id")
    private String id;

    @SerializedName("password")
    private String password;
    public LoginData(String id, String password){
        this.id=id;
        this.password=password;

    }
}
