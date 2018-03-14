package com.tweentyscoops.contactworkshop.http;

import com.tweentyscoops.contactworkshop.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpConnection {

    public static ContactApi request() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            logging.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://846df25d.ngrok.io/contact-api-workshop/public/api/")
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ContactApi.class);
    }
}
