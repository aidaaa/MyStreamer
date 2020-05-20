package com.example.mystreamer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mystreamer.adapter.ShowChannelAdapter;
import com.example.mystreamer.dagger.app.App;
import com.example.mystreamer.dataBinding.DateViewModel;
import com.example.mystreamer.dataBinding.TimeViewModel;
import com.example.mystreamer.databinding.ActivityMainBinding;
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


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements Observer<List<ArrayList<String>>>, ShowChannelAdapter.OnItemClickListener {

    TextView txt;
    PlayerView pw;
    ArrayList<String> urls;
    ArrayList<String> chName;
    RecyclerView rv;
    RelativeLayout layout;
    ProgressBar prg_bar;
    LinearLayout control;
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
    boolean error = false;
    DisposableObserver<Long> observer;

    MutableLiveData<String> base_url;

    boolean isLive = false;

    SimpleExoPlayer simpleExoPlayer;
    @Inject
    DefaultTrackSelector trackSelector;
    @Inject
    DataSource.Factory daFactory;

    EditText edt_date, edt_time;
    // int day, mon, year, hour, min, sec;

    File fileToBeDecrypted;
    private int hours, minutes, seconds, yeaer, month, daay;
    DateViewModel dateViewModel = new DateViewModel();
    TimeViewModel timeViewModel = new TimeViewModel();
    // private int hour,min,sec,day,mon,year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainBinding.setActivity(this);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        App.getApp().getPlayerComponent(this).getPlayer(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Calendar calendar = Calendar.getInstance();
        hours = calendar.get(Calendar.HOUR_OF_DAY);
        minutes = calendar.get(Calendar.MINUTE);
        seconds = calendar.get(Calendar.SECOND);
        yeaer = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        daay = calendar.get(Calendar.DAY_OF_MONTH);

        if (seconds < 10)
            timeViewModel.setSec(String.valueOf("0" + seconds));
        else
            timeViewModel.setSec(String.valueOf(seconds));
        if (minutes < 10)
            timeViewModel.setMin(String.valueOf("0" + minutes));
        else
            timeViewModel.setMin(String.valueOf(minutes));
        if (hours < 10)
            timeViewModel.setHour(String.valueOf("0" + hours));
        else
            timeViewModel.setHour(String.valueOf(hours));
        if (daay < 10)
            dateViewModel.setDay(String.valueOf("0" + daay));
        else
            dateViewModel.setDay(String.valueOf(daay));
        if (month < 10)
            dateViewModel.setMonth(String.valueOf("0" + month));
        else
            dateViewModel.setMonth(String.valueOf(month));


        dateViewModel.setYear(String.valueOf(yeaer));
        // dateViewModel.setMonth(String.valueOf(month));
        // dateViewModel.setDay(String.valueOf(daay));

        mainBinding.setDateVm(dateViewModel);
        mainBinding.setTimeVm(timeViewModel);

        fileToBeDecrypted = new File(this.getExternalFilesDir(null), "down.mp4");

        layout = mainBinding.layout;
        control = mainBinding.control;
        recyclerView = mainBinding.rv;
        txt = mainBinding.txt;
        pw = mainBinding.pW1;
        prg_bar = mainBinding.prgBar;
        prg_bar.setVisibility(View.VISIBLE);
        edt_time = mainBinding.edtTime;
        edt_date = mainBinding.edtDate;

        base_url = new MutableLiveData<>();

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
                    if (recyclerView.getVisibility() == View.INVISIBLE) {
                        recyclerView.setVisibility(View.VISIBLE);
                        showRv = true;
                    } else if (recyclerView.getVisibility() == View.VISIBLE) {
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
                if (control.getVisibility() == View.INVISIBLE) {
                    control.setVisibility(View.VISIBLE);
                } else if (control.getVisibility() == View.VISIBLE) {
                    control.setVisibility(View.INVISIBLE);
                }
            }
        });

        //setUpViewUrl("http://192.168.10.85:3030");

        base_url.observe(this, new androidx.lifecycle.Observer<String>() {
            @Override
            public void onChanged(String s) {
                //   if (isLive)
                setUpViewUrl(s);
                //  else
                //  getObservable(s);
                /*observer = Observable.just(1).timer(5000, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(getObserver());*/
            }
        });

        edt_time.addTextChangedListener(new TextWatcher() {

            private String current = "";
            private String ddmmyyyy = "HHMMSS";
            private Calendar cal = Calendar.getInstance();

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 6) {
                        clean = clean + ddmmyyyy.substring(clean.length());
                    } else {
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        hours = Integer.parseInt(clean.substring(0, 2));
                        minutes = Integer.parseInt(clean.substring(2, 4));
                        seconds = Integer.parseInt(clean.substring(4, 6));

                        if (hours > 23) hours = 00;
                        //cal.set(Calendar.MONTH, hour - 1);
                        if (minutes > 59) minutes = 00;
                        if (seconds > 59) seconds = 00;
                        // cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        //  day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
                        clean = String.format("%02d%02d%02d", hours, minutes, seconds);
                    }

                    clean = String.format("%s:%s:%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 6));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    edt_time.setText(current);
                    edt_time.setSelection(sel < current.length() ? sel : current.length());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });

        edt_date.addTextChangedListener(new TextWatcher() {

            private String current = "";
            private String yyyymmdd = "YYYYMMDD";
            private Calendar cal = Calendar.getInstance();

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8) {
                        clean = clean + yyyymmdd.substring(clean.length());
                    } else {
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        yeaer = Integer.parseInt(clean.substring(0, 4));
                        month = Integer.parseInt(clean.substring(4, 6));
                        daay = Integer.parseInt(clean.substring(6, 8));

                        if (month > 12) month = 12;
                        cal.set(Calendar.MONTH, month - 1);
                        yeaer = (yeaer < 1900) ? 1900 : (yeaer > 2100) ? 2100 : yeaer;
                        cal.set(Calendar.YEAR, yeaer);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        daay = (daay > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : daay;
                        clean = String.format("%02d%02d%02d", yeaer, month, daay);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 4),
                            clean.substring(4, 6),
                            clean.substring(6, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    edt_date.setText(current);
                    edt_date.setSelection(sel < current.length() ? sel : current.length());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                System.out.println(s);
                String a = dateViewModel.getDay();
                System.out.println(a);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });
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
            if (!error) {
                setUpView(pos);
                isStop = false;
            }
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

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(List<ArrayList<String>> arrayLists) {
        urls = arrayLists.get(0);

        String str = urls.get(0);

        if (str.equals("error")) {
            error = true;
            Toast.makeText(this, "فایل xml یافت نشد", Toast.LENGTH_SHORT).show();
            prg_bar.setVisibility(View.INVISIBLE);
        } else {
            error = false;
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
        if (!error) {
            if (chName.size() > 0) {

                if (pos < urls.size())
                    setUpView(pos);
                else
                    setUpView(0);
            }
        }
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

    public void liveClick(View view) {
        LiveAPi callAPI = new LiveAPi();
        isLive = true;
        try {
            base_url = callAPI.execute().get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

       /*PlayerViewModel playerViewModel=new PlayerViewModel(getApplication(),"http://192.168.10.85:3030");
         playerViewModel.playLive();*/
    }

    public boolean checkDate(String date) {
        try {
            boolean resault = true;
            Date c = Calendar.getInstance().getTime();

            SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd-hh:mm:ss");

            Date date1 = df.parse(date);


            if (date1.after(c)) {
                resault = false;
            }
            if (date1.before(c)) {
                resault = true;
            }
            return resault;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }



   /* public void timeSelect(View view) {

        isLive = false;

        if (observer != null) {
            if (!observer.isDisposed())
                observer.dispose();
        }

        CallAPI callAPI = new CallAPI();
        try {
            StringBuilder url = new StringBuilder().append("http://192.168.10.85:3030/");
            StringBuilder time = new StringBuilder();
            time.append(year)
                    .append("_");

            if (mon < 10) {
                time.append("0")
                        .append(mon)
                        .append("_");
            } else {
                time.append(mon)
                        .append("_");
            }

            if (day < 10) {
                time.append("0")
                        .append(day)
                        .append("-");
            } else {
                time.append(day)
                        .append("-");
            }

              *//*  time.append("13")
                        .append(":")
                        .append(min)
                        .append(":")
                        .append("00");*//*

            if (hour < 10) {
                time.append("0")
                        .append(hour)
                        .append(":");
            } else {
                time.append(hour)
                        .append(":");
            }
            if (min < 10) {
                time.append("0")
                        .append(min)
                        .append(":");
            } else {
                time.append(min)
                        .append(":");
            }
            if (sec < 10) {
                time.append("0")
                        .append(sec);
            } else {
                time.append(sec);
            }


            url.append(time);

            if (checkDate(time.toString())) {
                base_url = callAPI.execute(url.toString()).get();
            }
             *//*else {
                base_url = callAPI.execute("http://192.168.10.85:3030/2020_05_09-09:00:00").get();
            }*//*
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/

    public void timeShiftForWardSec(View view) {

        seconds = 10 + seconds;
        if (seconds >= 60) {
            seconds = seconds % 60;
            minutes++;
            if (minutes >= 60) {
                minutes = minutes % 60;
                hours++;
            }

            if (hours == 24) {
                daay++;
                hours = 0;
            }

            if (month <= 6) {
                if (daay == 31) {
                    month++;
                    daay = 1;
                }
            }
            if (month > 6 && month < 12) {
                if (daay == 30) {
                    month++;
                    daay = 1;
                }
            }

            if (month == 12) {
                if (daay > 29) {
                    yeaer++;
                    daay = 1;
                    month = 1;
                }
            }
        }

        if (seconds < 10)
            timeViewModel.setSec("0" + seconds);
        else
            timeViewModel.setSec(String.valueOf(seconds));
        if (minutes < 10)
            timeViewModel.setMin("0" + minutes);
        else
            timeViewModel.setMin(String.valueOf(minutes));
        if (hours < 10)
            timeViewModel.setHour("0" + hours);
        else
            timeViewModel.setHour(String.valueOf(hours));
        if (daay < 10)
            dateViewModel.setDay("0" + daay);
        else
            dateViewModel.setDay(String.valueOf(daay));
        if (month < 10)
            dateViewModel.setMonth("0" + month);
        else
            dateViewModel.setMonth(String.valueOf(month));
        dateViewModel.setYear(String.valueOf(yeaer));

    }

    public void timeShiftForWardMin(View view) {

        minutes = 1 + minutes;
        if (minutes > 59) {
            minutes = 60 - minutes;
            hours++;

            if (hours == 24) {
                daay++;
                hours = 0;
            }

            if (month <= 6) {
                if (daay == 31) {
                    month++;
                    daay = 1;
                }
            }
            if (month > 6 && month < 12) {
                if (daay == 30) {
                    month++;
                    daay = 1;
                }
            }

            if (month == 12) {
                if (daay > 29) {
                    yeaer++;
                    daay = 1;
                    month = 1;
                }
            }
        }

        if (minutes < 10)
            timeViewModel.setMin(String.valueOf("0" + minutes));
        else
            timeViewModel.setMin(String.valueOf(minutes));

        if (hours < 10)
            timeViewModel.setHour(String.valueOf("0" + hours));
        else
            timeViewModel.setHour(String.valueOf(hours));

        if (daay < 10)
            dateViewModel.setDay("0" + daay);
        else
            dateViewModel.setDay(String.valueOf(daay));

        if (month < 10)
            dateViewModel.setMonth("0" + month);
        else
            dateViewModel.setMonth(String.valueOf(month));
        dateViewModel.setYear(String.valueOf(yeaer));

    }

    public void timeShiftBackWardSec(View view) {

        seconds = seconds - 10;
        if (seconds <= 0) {
            seconds = 60 + seconds;
            minutes--;

            if (seconds == 0) {
                seconds = 59;
                minutes--;
            }

            if (minutes < 0) {
                minutes = 60 + minutes;
                if (hours == 0) {
                    hours = 24;
                }
                hours--;
            }

            if (minutes == 0) {
                minutes = 59;
                if (hours == 0) {
                    hours = 24;
                }
                hours--;
            }


            if (month <= 6 && month > 1) {
                if (daay == 0) {
                    month--;
                    daay = 31;
                }
            }
            if (month > 6 && month <= 12) {
                if (daay == 0) {
                    month--;
                    daay = 30;
                }
            }

            if (month == 1) {
                if (daay > 0) {
                    daay = 29;
                    month = 12;
                    yeaer--;
                }
            }
        }

        if (seconds < 10)
            timeViewModel.setSec(String.valueOf("0" + seconds));
        else
            timeViewModel.setSec(String.valueOf(seconds));
        if (minutes < 10)
            timeViewModel.setMin(String.valueOf("0" + minutes));
        else
            timeViewModel.setMin(String.valueOf(minutes));
        if (hours < 10)
            timeViewModel.setHour(String.valueOf("0" + hours));
        else
            timeViewModel.setHour(String.valueOf(hours));
        if (hours == 24) {
            timeViewModel.setHour("00");
            hours = 24;
        }

        if (daay < 10)
            dateViewModel.setDay("0" + daay);
        else
            dateViewModel.setDay(String.valueOf(daay));

        if (month < 10)
            dateViewModel.setMonth("0" + month);
        else
            dateViewModel.setMonth(String.valueOf(month));
        dateViewModel.setYear(String.valueOf(yeaer));

    }

    public void timeShiftBackWardMin(View view) {

        minutes = minutes - 1;
        if (minutes <= 0) {
            minutes = 60 + minutes;
            if (hours == 0) {
                hours = 24;
            }
            hours--;

            if (minutes == 0) {
                minutes = 59;
                if (hours == 0) {
                    hours = 24;
                }
                hours--;
            }

            if (month <= 6 && month > 1) {
                if (daay == 0) {
                    month--;
                    daay = 31;
                }
            }
            if (month > 6 && month <= 12) {
                if (daay == 0) {
                    month--;
                    daay = 30;
                }
            }

            if (month == 1) {
                if (daay > 0) {
                    daay = 29;
                    month = 12;
                    yeaer--;
                }
            }
        }

        if (minutes < 10)
            timeViewModel.setMin(String.valueOf("0" + minutes));
        else
            timeViewModel.setMin(String.valueOf(minutes));
        if (hours < 10)
            timeViewModel.setHour(String.valueOf("0" + hours));
        else
            timeViewModel.setHour(String.valueOf(hours));
        if (hours == 24 ) {
            timeViewModel.setHour("00");
        }
        if (daay < 10)
            dateViewModel.setDay("0" + daay);
        else
            dateViewModel.setDay(String.valueOf(daay));

        if (month < 10)
            dateViewModel.setMonth("0" + month);
        else
            dateViewModel.setMonth(String.valueOf(month));
        dateViewModel.setYear(String.valueOf(yeaer));

    }

    public void playFile(View view)
    {
        StringBuilder url = new StringBuilder().append("http://192.168.10.85:3030/");
        StringBuilder time = new StringBuilder();
        StringBuilder date=new StringBuilder();


        time.append(dateViewModel.getYear())
                .append("_")
                .append(dateViewModel.getMonth())
                .append("_")
                .append(dateViewModel.getDay())
                .append("-")
                .append(timeViewModel.getHour())
                .append(":")
                .append(timeViewModel.getMin())
                .append(":")
                .append(timeViewModel.getSec());

        url.append(time);
        if (!checkDate(time.toString()))
        {
            Toast.makeText(this, "تاریخ خارج از محدوده", Toast.LENGTH_SHORT).show();
        }
        else {
            CallAPI callAPI = new CallAPI();
            callAPI.execute(url.toString());
        }


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

    public class LiveAPi extends AsyncTask<Void, Void, MutableLiveData<String>> {

        @Override
        protected MutableLiveData<String> doInBackground(Void... voids) {
            //String data = params[1]; //data to post
            OutputStream out = null;
            int count;

            try {
                //  URL url = new URL(urlString);
                URL url = new URL("http://192.168.10.85:3030");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream input = new BufferedInputStream(url.openStream(), 8192);


                OutputStream output = new FileOutputStream(fileToBeDecrypted);

                byte data[] = new byte[1024];
                long totlal = 0;

                while ((count = input.read(data)) != -1) {
                    totlal += count;

                    output.write(data, 0, count);
                }
                out.flush();
                input.close();
                base_url.postValue(fileToBeDecrypted.getPath());

            } catch (Exception e) {
                System.out.println(e.getMessage());
                base_url.postValue(null);
            }
            return base_url;
        }
    }
}

