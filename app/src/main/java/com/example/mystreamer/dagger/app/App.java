package com.example.mystreamer.dagger.app;

import android.app.Application;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mystreamer.dagger.AndroidModule;
import com.example.mystreamer.dagger.DaggerPlayerComponent;
import com.example.mystreamer.dagger.PlayerComponent;
import com.example.mystreamer.dagger.PlayerModule;

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
