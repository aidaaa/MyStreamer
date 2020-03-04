package com.example.childrenmusic.kobeie;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.childrenmusic.R;
import com.example.childrenmusic.badi.BadyMainActivity;
import com.example.childrenmusic.badi.player.SurnaActivity;
import com.example.childrenmusic.kobeie.adapter.KobeieAdapter;
import com.example.childrenmusic.kobeie.datamodel.KobeieDataModel;
import com.example.childrenmusic.kobeie.generator.KobeieDataGenerator;
import com.example.childrenmusic.kobeie.player.DoholActivity;

public class KobeieMainActivity extends AppCompatActivity implements KobeieAdapter.KobeieOnClick {

    private RecyclerView recyclerView;
    Toolbar toolbar_k;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kobeie_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        recyclerView=findViewById(R.id.recycler_kobeie);
        toolbar_k=findViewById(R.id.toolbar_k);
        mp=MediaPlayer.create(this,R.raw.click);
        mp.setLooping(false);

        ViewCompat.setNestedScrollingEnabled(recyclerView, false);
        recyclerView.setHasFixedSize(true);

        KobeieAdapter adapter=new KobeieAdapter(this, KobeieDataGenerator.getKobeieDataModel(this),this);
        adapter.notifyDataSetChanged();

        recyclerView.setLayoutManager(new GridLayoutManager(this, 