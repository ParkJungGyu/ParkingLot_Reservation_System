package com.project.afinal;


import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;

public class one_park_space {
    @SerializedName("parkempty")
    @Expose
    private String parkempty;
    @SerializedName("parkspace")
    @Expose
    private String parkspace;

    public String getParkempty() {
        return parkempty;
    }

    public String getParkspace() {
        return parkspace;
    }

}
