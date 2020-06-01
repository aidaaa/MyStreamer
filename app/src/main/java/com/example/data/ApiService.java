package com.example.data;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
    private String base_url;

    private ApiService(String base_url) {
        this.base_url = base_url;
    }

    public static ApiService getInstance(String base_url)
    {
        return new ApiService(base_url);
    }
    public Retrofit getRetrofit()
    {
        OkHttpClient.Builder client=new OkHttpClient.Builder();

        client.readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60,TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS);

        return new Retrofit.Builder()
                .baseUrl(this.base_url)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
