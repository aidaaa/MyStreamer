package com.example.data;

import com.example.model.EPGModelTO;

import java.util.List;

import io.reactivex.Observable;

public interface RestApi
{
    Observable<List<EPGModelTO>> getObservableEPG(String cName);
}
