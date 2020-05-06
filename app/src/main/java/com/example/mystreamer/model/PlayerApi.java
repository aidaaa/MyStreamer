package com.example.mystreamer.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;


public interface PlayerApi
{
    @GET
    Call live(@Url String url);

    @GET
    Call timeShift(@Url String url);

}
