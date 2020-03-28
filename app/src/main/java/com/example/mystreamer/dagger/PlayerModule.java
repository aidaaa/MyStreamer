package com.example.mystreamer.dagger;

import android.content.Context;
import android.net.Uri;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


import dagger.Module;
import dagger.Provides;

@Module(includes = {AndroidModule.class})
public class PlayerModule
{
    @Provides
    public DefaultTrackSelector getDefaultTrackSelector()
    {
        return new DefaultTrackSelector();
    }

    @Provides
    public SimpleExoPlayer getSimpleExoPlayer(Context context,DefaultTrackSelector trackSelector)
    {
        return ExoPlayerFactory.newSimpleInstance(context,trackSelector);
    }

    @Provides
    public DataSource.Factory getgetDataSourceFactory(Context context)
    {
        return new DefaultHttpDataSourceFactory(Util.getUserAgent(context,"exoplayer"));
    }

    @Provides
    public MediaSource getMediaSource(DataSource.Factory dFactory, Uri uri)
    {
        return new ProgressiveMediaSource.Factory(dFactory).createMediaSource(uri);
    }
}
