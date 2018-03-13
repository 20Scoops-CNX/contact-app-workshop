package com.tweentyscoops.contactworkshop.http;

import com.tweentyscoops.contactworkshop.model.ResponseApi;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ContactApi {

    @FormUrlEncoded
    @POST("sing-in")
    Call<ResponseApi> login(@Field("email") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("sing-up")
    Call<ResponseApi> crataRegister(@Field("email") String username, @Field("password") String password);

}