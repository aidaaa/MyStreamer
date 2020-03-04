package com.example.mystreamer.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mystreamer.R;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>
{
    ArrayList<String> urls=new ArrayList<>();
    Context context;
    PlayerView pw;

    public PlayerAdapter(ArrayList<String> urls, Context context) {
        this.urls = urls;
        this.context = context;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.rv_item,parent,false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        setPlayer(urls.get(position));
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    public void setPlayer(String url)
    {
        TrackSelector trackSelector=new DefaultTrackSelector();
        SimpleExoPlayer simpleExoPlayer= ExoPlayerFactory.newSimpleInstance(context,trackSelector);
        pw.setPlayer(simpleExoPlayer);
        DataSource.Factory daFactory=new DefaultDataSourceFactory(context, Util.getUserAgent(context,"EXOPlayer"));


        Uri audioUri=Uri.parse(url);

        MediaSource mediaSource=new ProgressiveMediaSource.Factory(daFactory).createMediaSource(audioUri);

        simpleExoPlayer.prepare(mediaSource);
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onPlayerError(ExoPlaybackException error) {
                System.out.println(error.getMessage());
            }
        });
    }

    public class PlayerViewHolder extends RecyclerView.ViewHolder
    {
        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            pw=itemView.findViewById(R.id.rv_pw);
        }
    }
}
