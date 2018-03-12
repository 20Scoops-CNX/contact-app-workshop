package com.tweentyscoops.contactworkshop.model;

import com.google.gson.annotations.SerializedName;

public class ResponseApi {
    @SerializedName("success")private String success;
    @SerializedName("data")private Data data;
    public String getSuccess() {
        return success;
    }
    public void setSuccess(String success) {
        this.success = success;
    }
    public Data getData() {
        return data;
    }
    public void setData(Data data) {
        this.data = data;
    }
}
