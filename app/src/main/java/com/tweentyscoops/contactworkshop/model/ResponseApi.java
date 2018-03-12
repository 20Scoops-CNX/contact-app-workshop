package com.tweentyscoops.contactworkshop.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MSI-Gl62 on 12/3/2561.
 */

public class ResponseApi {
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

    @SerializedName("success")private String success;
    @SerializedName("data")private Data data;

}
