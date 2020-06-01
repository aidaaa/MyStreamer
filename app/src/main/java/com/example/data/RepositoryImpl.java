package com.example.data;

import com.example.domain.repository.EPGRepository;
import com.example.model.EPGModelTO;

import java.util.List;

import io.reactivex.Observable;

public class RepositoryImpl implements EPGRepository {
    RestApiImpl restApi;

    public RepositoryImpl(RestApiImpl restApi) {
        this.restApi = restApi;
    }

    @Override
    public Observable<List<EPGModelTO>> getEPG(String cName) {
        return restApi.getObservableEPG(cName);
    }
}
