package com.example.presenter.view.mystreamer.dagger;

import android.content.Context;

import com.example.presenter.view.mystreamer.MainActivity;

import dagger.BindsInstance;
import dagger.Component;

@Component(modules = {AndroidModule.class,
                        PlayerModule.class})
public interface PlayerComponent
{
    void getPlayer(MainActivity mainActivity);

    @Component.Builder
    interface Builder
    {
        @BindsInstance
        Builder context(Context context);
        PlayerComponent build();
    }
}
