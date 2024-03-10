package com.project.afinal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ParkInfoData {
        @SerializedName("parking")
        @Expose
        private List<parking> parkinfo;

        public List<parking> getparkinfo() {
            return parkinfo;
        }

        public void setparkinfo(List<parking> parkinfo) {
            this.parkinfo = parkinfo;
        }
}
