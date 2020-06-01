package com.example.presenter.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.data.RepositoryImpl;
import com.example.data.RestApi;
import com.example.data.RestApiImpl;
import com.example.domain.repository.EPGRepository;
import com.example.domain.usecase.UseCaseImpl;
import com.example.model.EPGModelTO;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

public class EPGViewModel extends AndroidViewModel {

    MutableLiveData<List<EPGModelTO>> liveData=new MutableLiveData<>();

    public EPGViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<EPGModelTO>> getLiveData(String cName)
    {
        RestApiImpl restApi=new RestApiImpl();
        RepositoryImpl epgRepository=new RepositoryImpl(restApi);
        UseCaseImpl useCase=new UseCaseImpl(epgRepository,cName);
        useCase.execute(new DisposableObserver<List<EPGModelTO>>() {
            @Override
            public void onNext(List<EPGModelTO> epgModelTOS) {
                liveData.postValue(epgModelTOS);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        return liveData;
    }
}
