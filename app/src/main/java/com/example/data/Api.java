package com.example.data;

import com.example.model.EPGModelTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    @GET("EPGShow")
    Call<List<EPGModelTO>> getEPG(@Query("cName") String cName);
}
