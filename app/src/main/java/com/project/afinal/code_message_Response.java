package com.project.afinal;

import com.google.gson.annotations.SerializedName;

public class code_message_Response {
    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
