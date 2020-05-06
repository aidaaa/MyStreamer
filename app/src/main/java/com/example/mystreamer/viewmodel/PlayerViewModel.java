package com.example.mystreamer.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.mystreamer.model.PlayerRepository;

public class PlayerViewModel  extends AndroidViewModel
{

    PlayerRepository playerRepository;
    String base_url;

    public PlayerViewModel(@NonNull Application application, String base_url) {
        super(application);
        this.base_url=base_url;
        if (base_url!=null)
            playerRepository=PlayerRepository.getInstance(base_url);
    }

    public void playLive()
    {
        if (base_url!=null)
        playerRepository.playLive();
    }

    public void playWithTimeShitf()
    {
        if (base_url!=null)
        playerRepository.playWithTimeShitf();
    }
}
