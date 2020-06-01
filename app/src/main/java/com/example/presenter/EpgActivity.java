package com.example.presenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.example.model.EPGModelTO;
import com.example.presenter.view.mystreamer.R;
import com.example.presenter.viewmodel.EPGViewModel;

import java.util.List;

public class EpgActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epg);

        EPGViewModel epgViewModel= ViewModelProviders.of(this).get(EPGViewModel.class);
        epgViewModel.getLiveData("IRIB POOYA-IRIB").observe(this, new Observer<List<EPGModelTO>>() {
            @Override
            public void onChanged(List<EPGModelTO> epgModelTOS) {
                for (int i = 0; i < epgModelTOS.size(); i++) {
                    System.out.println(epgModelTOS.get(i).getTitle()+"aida");
                }
            }
        });
    }
}
