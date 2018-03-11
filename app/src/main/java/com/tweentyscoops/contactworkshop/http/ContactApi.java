package com.tweentyscoops.contactworkshop.http;

import com.tweentyscoops.contactworkshop.model.UserModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ContactApi {

    @FormUrlEncoded
    @POST("login")
    Call<String> login();

    @POST("sing-in")
    Call<UserModel> loginEmail(@Body UserModel user);

    @POST("sing-up")
    Call<UserModel> crataRegister(@Body UserModel user);

}
