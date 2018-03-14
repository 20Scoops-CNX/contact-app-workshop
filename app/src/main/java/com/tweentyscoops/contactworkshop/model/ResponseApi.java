package com.tweentyscoops.contactworkshop.model;

import com.google.gson.annotations.SerializedName;

public class ResponseApi {
    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private Data data;

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
