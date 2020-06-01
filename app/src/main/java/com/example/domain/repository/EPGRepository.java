package com.example.domain.repository;

import com.example.model.EPGModelTO;

import java.util.List;

import io.reactivex.Observable;

public interface EPGRepository
{
    Observable<List<EPGModelTO>> getEPG(String cName);
}
