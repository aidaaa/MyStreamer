package com.example.mystreamer.dagger;

import com.example.mystreamer.MainActivity;

import dagger.Component;

@Component(modules = {AndroidModule.class,
                        PlayerModule.class})
public interface PlayerComponent
{
    void getPlayer(MainActivity mainActivity);
}
