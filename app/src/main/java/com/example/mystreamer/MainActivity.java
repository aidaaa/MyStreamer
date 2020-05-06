package com.example.mystreamer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mystreamer.adapter.ShowChannelAdapter;
import com.example.mystreamer.dagger.app.App;
import com.example.mystreamer.viewmodel.PlayerViewModel;
import com.example.mystreamer.xml.Xml;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;


import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements Observer<List<ArrayList<String>>>, ShowChannelAdapter.OnItemClickListener {

    TextView txt;
    PlayerView pw;
    ArrayList<String> urls;
    ArrayList<String> chName;
    RecyclerView rv;
    RelativeLayout layout;
    ProgressBar prg_bar;
    int pos = 0;
    Xml xml = new Xml();
    boolean isStop = false;
    RecyclerView recyclerView;
    boolean chList = false;
    boolean showRv = false;
    SharedPreferences.Editor editor;
    SharedPreferences prefs;
    int textSize;
    ArrayList<Integer> textSizeList = new ArrayList<>();

    MutableLiveData<String> base_url=new MutableLiveData<>();

    SimpleExoPlayer simpleExoPlayer;
    @Inject
    DefaultTrackSelector trackSelector;
    @Inject
    DataSource.Factory daFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        App.getApp().getPlayerComponent(this).getPlayer(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        layout = findViewById(R.id.layout);

        recyclerView = findViewById(R.id.rv);
        txt = findViewById(R.id.txt);

        pw = findViewById(R.id.pW1);
        prg_bar = findViewById(R.id.prg_bar);
        prg_bar.setVisibility(View.VISIBLE);

        editor = getSharedPreferences("lastCh", MODE_PRIVATE).edit();
        prefs = getSharedPreferences("lastCh", MODE_PRIVATE);
        pos = prefs.getInt("pos", 0);

        Observable<List<ArrayList<String>>> observable = xml.getObservableXml("5.160.10.54:8090");
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);


        pw.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {

            //up
            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                if (pos == urls.size() - 1) {
                } else {
                    pos++;
                    setUpView(pos);
                }
            }

            //down
            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                if (pos == 0) {
                } else {
                    pos--;
                    setUpView(pos);
                }
            }

            //show channel list
            @Override
            public void onClick() {
                super.onClick();
                if (chList) {
                    if (!showRv) {
                        recyclerView.setVisibility(View.VISIBLE);
                        showRv = true;
                    } else if (showRv) {
                        recyclerView.setVisibility(View.INVISIBLE);
                        showRv = false;
                    }
                }
            }

            //hide channel list
            @Override
            public void onDoubleClick() {
                super.onDoubleClick();
                //TODO --->for archive
            }
        });

        setUpViewUrl("http://192.168.10.85:3030");

        base_url.observe(this, new androidx.lifecycle.Observer<String>() {
            @Override
            public void onChanged(String s) {
                setUpViewUrl(s);
            }
        });
    }

    public void setUpView(int pos) {
        prg_bar.setVisibility(View.VISIBLE);
        if (pos < urls.size()) {
            if (simpleExoPlayer != null) {
                simpleExoPlayer.release();
                simpleExoPlayer = null;
            }
            // trackSelector=new DefaultTrackSelector();
            Toast.makeText(this, chName.get(pos).toString(), Toast.LENGTH_SHORT).show();
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
            pw.setPlayer(simpleExoPlayer);
            // DataSource.Factory daFactory=new DefaultDataSourceFactory(this, Util.getUserAgent(this,"EXOPlayer"));

            Uri audioUri = Uri.parse(urls.get(pos));

            MediaSource mediaSource = new ProgressiveMediaSource.Factory(daFactory).createMediaSource(audioUri);

            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.setPlayWhenReady(true);

            simpleExoPlayer.addListener(new Player.EventListener() {
                @Override
                public void onIsPlayingChanged(boolean isPlaying) {
                    if (isPlaying)
                        prg_bar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onPlayerError(ExoPlaybackException error) {
                    prg_bar.setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity.this, "در پخش مشکلی پیش آمده مجدد تلاش کنید", Toast.LENGTH_LONG).show();
                    System.out.println(error.getMessage());
                }
            });
        }
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(List<ArrayList<String>> arrayLists) {
        urls = arrayLists.get(0);

        String str = urls.get(0);

        if (str.equals("error")) {
            Toast.makeText(this, "فایل xml یافت نشد", Toast.LENGTH_SHORT).show();
            prg_bar.setVisibility(View.INVISIBLE);
        } else {
            urls = arrayLists.get(0);
            chName = arrayLists.get(1);

            for (int i = 0; i < chName.size(); i++) {
                String name = chName.get(i);
                textSize = name.length();
                if (textSize < 15)
                    textSizeList.add(textSize);
            }
            int maxValue = textSizeList.get(0);
            int index = 0;
            for (int j = 0; j < textSizeList.size(); j++) {
                if (textSizeList.get(j) > maxValue) {
                    maxValue = textSizeList.get(j);
                    index = j;
                }
            }
            txt.setText(chName.get(index));
            txt.measure(0, 0);
            int width = txt.getMeasuredWidth();

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            ShowChannelAdapter adapter = new ShowChannelAdapter(this, chName, width);
            recyclerView.setAdapter(adapter);
            chList = true;
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {
        if (chName.size() > 0) {

            if (pos < urls.size())
                setUpView(pos);
            else
                setUpView(0);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (simpleExoPlayer != null)
            simpleExoPlayer.release();
        // wl.release();
        editor.putInt("pos", pos);
        editor.apply();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (simpleExoPlayer != null)
            simpleExoPlayer.release();
        isStop = true;

        editor.putInt("pos", pos);
        editor.apply();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isStop) {
            setUpView(pos);
            isStop = false;
        }
        pos = prefs.getInt("pos", 0);
    }

    @Override
    public void onClick(String name) {
        for (int i = 0; i < chName.size(); i++) {
            String ch = chName.get(i);
            if (ch.equals(name)) {
                if (pos == i) {
                    return;
                } else {
                    pos = i;
                    setUpView(pos);
                    return;
                }
            }
        }
    }

    public void liveClick(View view) {
        CallAPI callAPI=new CallAPI();
        try {
           base_url = callAPI.execute("http://192.168.10.85:3030").get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

       /* PlayerViewModel playerViewModel=new PlayerViewModel(getApplication(),"http://192.168.10.85:3030");
         playerViewModel.playLive();*/
    }

    public void timeShift(View view)
    {
        CallAPI callAPI=new CallAPI();
        try {
            base_url = callAPI.execute("http://192.168.10.85:3030/2020_05_06-13:28:00").get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setUpViewUrl(String url) {
        prg_bar.setVisibility(View.VISIBLE);
        if (simpleExoPlayer != null) {
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
            pw.setPlayer(simpleExoPlayer);
            // DataSource.Factory daFactory=new DefaultDataSourceFactory(this, Util.getUserAgent(this,"EXOPlayer"));

            Uri audioUri = Uri.parse(url);

            MediaSource mediaSource = new ProgressiveMediaSource.Factory(daFactory).createMediaSource(audioUri);

            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.setPlayWhenReady(true);

            simpleExoPlayer.addListener(new Player.EventListener() {
                @Override
                public void onIsPlayingChanged(boolean isPlaying) {
                    if (isPlaying)
                        prg_bar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onPlayerError(ExoPlaybackException error) {
                    prg_bar.setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity.this, "در پخش مشکلی پیش آمده مجدد تلاش کنید", Toast.LENGTH_LONG).show();
                    System.out.println(error.getMessage());
                }
            });
        }


    public class CallAPI extends AsyncTask<String, String, MutableLiveData<String>> {

        public CallAPI() {
            //set context variables if required
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected MutableLiveData<String> doInBackground(String... params) {
            String urlString = params[0]; // URL to call
            //String data = params[1]; //data to post
            OutputStream out = null;

            try {
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");

               /* out = new BufferedOutputStream(urlConnection.getOutputStream());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                writer.write(data);
                writer.flush();
                writer.close();
                out.close();*/

                urlConnection.connect();
                base_url.postValue(urlString);
                return base_url;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return null;
            }
        }
    }
}
