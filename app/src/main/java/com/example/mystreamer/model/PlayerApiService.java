package com.example.mystreamer.model;

import retrofit2.Retrofit;

public class PlayerApiService
{
    public static String base_url;


    public PlayerApiService(String base_url) {
        this.base_url = base_url;
    }

    static PlayerApiService create(String base_url)
    {
        return new PlayerApiService(base_url);
    }

    public static Retrofit getRetrofit()
    {
        return new Retrofit.Builder()
                .baseUrl(base_url)
                .build();
    }
}
