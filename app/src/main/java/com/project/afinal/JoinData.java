package com.project.afinal;

import com.google.gson.annotations.SerializedName;

public class JoinData {
    @SerializedName("id")
    private String id;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    public JoinData(String userName,  String userPwd, String userEmail) {
        this.id = userName;
        this.email = userEmail;
        this.password = userPwd;
    }
}
