package com.example.data;

import com.example.model.EPGModelTO;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class RestApiImpl implements RestApi {
    @Override
    public Observable<List<EPGModelTO>> getObservableEPG(String cName) {
        return Observable.create(new ObservableOnSubscribe<List<EPGModelTO>>() {
            @Override
            public void subscribe(ObservableEmitter<List<EPGModelTO>> emitter) throws Exception {
                List<EPGModelTO> epgModelTOS=getEpgModelTOS(cName);
                if (!emitter.isDisposed() && epgModelTOS!=null)
                {
                    emitter.onNext(epgModelTOS);
                    emitter.onComplete();
                }
                else
                    emitter.onError(new Throwable());
            }
        });
    }
    public List<EPGModelTO> getEpgModelTOS(String cName)
    {
        Api api=ApiService.getInstance("http://192.168.10.85:8042/web_war_exploded/").getRetrofit().create(Api.class);
        try {
            return api.getEPG(cName).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
