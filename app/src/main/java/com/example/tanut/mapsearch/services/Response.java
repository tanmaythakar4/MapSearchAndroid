package com.example.tanut.mapsearch.services;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tanut on 10/28/2017.
 */

public class Response {

    @SerializedName("status")
    public String status;

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @SuppressWarnings({"unused", "used by Retrofit"})
    public Response() {
    }

    public Response(String status) {
        this.status = status;
    }
}
