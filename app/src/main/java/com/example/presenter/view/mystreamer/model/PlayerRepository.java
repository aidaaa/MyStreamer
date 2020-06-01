package com.example.presenter.view.mystreamer.model;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PlayerRepository
{
    Retrofit retrofit;
    public static PlayerRepository playerRepository;
    PlayerApi playerApi;
    String base_url;

    public static PlayerRepository getInstance(String base_url)
    {
        if (playerRepository==null)
            playerRepository=new PlayerRepository(base_url);
        return playerRepository;
    }
    public PlayerRepository(String base_url)
    {
        if (base_url!=null)
        {
            retrofit=PlayerApiService.create(base_url).getRetrofit();
            playerApi=retrofit.create(PlayerApi.class);
        }
    }

    public void setBase_url(String base_url)
    {
        this.base_url=base_url;
    }

    public void playLive()
    {
        playerApi.live(base_url).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

    public void playWithTimeShitf()
    {
        playerApi.timeShift(base_url).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }
}
