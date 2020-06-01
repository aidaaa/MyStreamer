package com.example.presenter.view.mystreamer.dagger.app;

import android.app.Application;

import androidx.appcompat.app.AppCompatActivity;

import com.example.presenter.view.mystreamer.dagger.DaggerPlayerComponent;
import com.example.presenter.view.mystreamer.dagger.PlayerComponent;

public class App extends Application
{
    private PlayerComponent playerComponent;
    public static App getApp()
    {
        return new App();
    }

    public PlayerComponent getPlayerComponent(AppCompatActivity appCompatActivity)
    {
        playerComponent= DaggerPlayerComponent.builder()
                .context(appCompatActivity.getApplicationContext())
                .build();
        return playerComponent;
    }

}
