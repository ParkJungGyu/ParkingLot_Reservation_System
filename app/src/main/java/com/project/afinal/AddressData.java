package com.project.afinal;

import android.util.Log;

import androidx.annotation.Keep;


import java.util.Arrays;
import com.google.gson.annotations.SerializedName;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddressData {
    Data[] addresses;

    @Keep
    private class Data {
        String x;
        String y;
    }

    @Override
    public String toString() {

        return addresses[0].x+"\n"+addresses[0].y;
    }
}
