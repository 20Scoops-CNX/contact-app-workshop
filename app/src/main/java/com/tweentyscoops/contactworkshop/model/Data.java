package com.tweentyscoops.contactworkshop.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MSI-Gl62 on 12/3/2561.
 */

public class Data {
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @SerializedName("message")private String message;
}
