package com.example.childrenmusic.badi;



import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.childrenmusic.R;
import com.example.childrenmusic.badi.adapter.BadyAdapter;
import com.example.childrenmusic.badi.datamodel.BadyDataModel;
import com.example.childrenmusic.badi.generator.DataGenerator;
import com.example.childrenmusic.badi.player.SurnaActivity;

public class BadyMainActivity extends AppCompatActivity implements BadyAdapter.OnItemClickListener {

    RecyclerView recyclerView;
    CollapsingToolbarLayout collapsingToolbarLayout;
    NestedScrollView net;
    Toolbar toolbar1;
    MediaPlayer mp;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bady_main);

        //full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mp=MediaPlayer.create(this,R.raw.click);
        mp.setLooping(false);

        recyclerView=findViewById(R.id.recycler);
        net=findViewById(R.id.netset);
        toolbar1=findViewById(R.id.toolbar1);
        collapsingToolbarLayout=findViewById(R.id.collapsingToolbarLayout);

        ViewCompat.setNestedScrollingEnabled(recyclerView, false);
        recyclerView.setHasFixedSize(true);

        BadyAdapter adapter=new BadyAdapter(this, DataGenerator.getAlbumDataModel(this),this);
        adapter.notifyDataSetChanged();

        //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);

        //large
        if ((getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) ==
                Configuration.SCREENLAYOUT_SIZE_LARGE)
        {
            BitmapDrawable bd = (BitmapDrawable) this.getResources().getDrawable(R.drawable.sorna);

            int imageHeight = bd.getBitmap().getHeight();
            int finalImageHeghit=imageHeight+imageHeight+imageHeight;
            int imageWidth = bd.getBitmap().getWidth();


            BitmapDrawable bd1 = (BitmapDrawable) this.getResources().getDrawable(R.drawable.bady_inter_menu);
            int imageHeight1 = bd1.getBitmap().getHeight();
            int imageWidth1 = bd1.getBitmap().getWidth();

            CollapsingToolbarLayout.LayoutParams layoutParams = (CollapsingToolbarLayout.LayoutParams) toolbar1.getLayoutParams();
            //int a=layoutParams.MATCH_PARENT-finalImageHeghit;
            layoutParams.height =imageHeight1-finalImageHeghit;
            toolbar1.setLayoutParams(layoutParams);

        }

        //xlarge
        if ((getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) ==
                Configuration.SCREENLAYOUT_SIZE_XLARGE)
        {
            BitmapDrawable bd = (BitmapDrawable) this.getResources().getDrawable(R.drawable.sorna);

            int imageHeight = bd.getBitmap().getHeight();
            int finalImageHeghit=imageHeight+imageHeight+imageHeight;
            int imageWidth = bd.getBitmap().getWidth();


            BitmapDrawable bd1 = (BitmapDrawable) this.getResources().getDrawable(R.drawable.bady_inter_menu);
            int imageHeight1 = bd1.getBitmap().getHeight();
            int imageWidth1 = bd1.getBitmap().getWidth();

            CollapsingToolbarLayout.LayoutParams layoutParams = (CollapsingToolbarLayout.LayoutParams) toolbar1.getLayoutParams();
            //int a=layoutParams.MATCH_PARENT-finalImageHeghit;
            layoutParams.height =imageHeight1-finalImageHeghit;
            toolbar1.setLayoutParams(layoutParams);
        }

        //normal
        if ((getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) ==
                Configuration.SCREENLAYOUT_SIZE_NORMAL)
        {
            Bi