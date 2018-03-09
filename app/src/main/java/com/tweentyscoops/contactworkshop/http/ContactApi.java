package com.tweentyscoops.contactworkshop.http;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ContactApi {

    @FormUrlEncoded
    @POST("login")
    Call<String> login();
}
