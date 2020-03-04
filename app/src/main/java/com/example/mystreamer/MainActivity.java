package com.example.mystreamer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mystreamer.adapter.PlayerAdapter;
import com.example.mystreamer.xml.Xml;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements Observer<List<ArrayList<String>>> {

    PlayerView pw;
    SimpleExoPlayer simpleExoPlayer;
    DefaultTrackSelector trackSelector;
    ArrayList<String> urls;
    RecyclerView rv;
    RelativeLayout layout;
    int pos=0;
    Xml xml=new Xml();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        layout=findViewById(R.id.layout);
        pw=findViewById(R.id.pW);

        Observable<List<ArrayList<String>>> observable=xml.getObservableXml("5.160.10.54:8090");
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);

        pw.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this)
        {
            //up
            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                if (pos==urls.size())
                {}
                else
                {
                    pos++;
                    setUpView(pos);
                }
            }

            //down
            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                if (pos==0)
                {}
                else
                {
                    pos--;
                    setUpView(pos);
                }
            }
        });

    }

    public void setUpView(int pos)
    {
       if (pos<urls.size())
       {
           trackSelector=new DefaultTrackSelector();
           simpleExoPlayer= ExoPlayerFactory.newSimpleInstance(this,trackSelector);
           pw.setPlayer(simpleExoPlayer);
           DataSource.Factory daFactory=new DefaultDataSourceFactory(this, Util.getUserAgent(this,"EXOPlayer"));


           Uri audioUri=Uri.parse(urls.get(pos));

           MediaSource mediaSource=new ProgressiveMediaSource.Factory(daFactory).createMediaSource(audioUri);

           simpleExoPlayer.prepare(mediaSource);
           simpleExoPlayer.setPlayWhenReady(true);

           simpleExoPlayer.addListener(new Player.EventListener() {
               @Override
               public void onPlayerError(ExoPlaybackException error) {
                   Toast.makeText(MainActivity.this, "در پخش مشکلی پیش آمده مجدد تلاش کنید", Toast.LENGTH_LONG).show();
               }
           });
       }
    }

    public ArrayList<String> getUrl()
    {
        ArrayList<String> filePath=new ArrayList<>();
         filePath.add("http://5.160.10.54:1010");
                filePath.add("http://5.160.10.54:1011");
                filePath.add("http://5.160.10.54:1012");

        return filePath;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(List<ArrayList<String>> arrayLists) {
        urls=arrayLists.get(0);
        String str=urls.get(0);

        if (str.equals("error"))
        {
            Toast.makeText(this, "فایل xml یافت نشد", Toast.LENGTH_SHORT).show();
        }
        else {
            urls = arrayLists.get(0);
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {
        setUpView(0);
    }
}
