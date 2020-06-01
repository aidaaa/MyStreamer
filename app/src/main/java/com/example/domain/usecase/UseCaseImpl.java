package com.example.domain.usecase;

import com.example.domain.repository.EPGRepository;
import com.example.model.EPGModelTO;

import java.util.List;

import io.reactivex.Observable;

public class UseCaseImpl extends UseCase<List<EPGModelTO>> {
    EPGRepository repository;
    String cName;

    public UseCaseImpl( EPGRepository repository,String cName) {
        this.repository=repository;
        this.cName=cName;
    }

    @Override
    public Observable<List<EPGModelTO>> buildObservable() {
        return repository.getEPG(cName);
    }
}
