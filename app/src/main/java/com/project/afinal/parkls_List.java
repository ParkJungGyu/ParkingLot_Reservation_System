package com.project.afinal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class parkls_List {
    @SerializedName("parkls")
    @Expose
    private List<parkls> parklsinfo;

    public List<parkls> getparkls() {
        return parklsinfo;
    }

    public void setparkls(List<parkls> parklsinfo) {
        this.parklsinfo = parklsinfo;
    }
}
