package com.project.afinal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class parkiu_list {
    @SerializedName("parkiu")
    @Expose
    private List<parkiu> parkiu;

    public List<parkiu> getparkiu() {
        return parkiu;
    }

    public void setparkiu(List<parkiu> parkiu) {
        this.parkiu = parkiu;
    }
}
