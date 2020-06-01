package com.example.presenter.view.mystreamer.dagger;

import android.content.Context;

import javax.inject.Inject;

public class ContextClass
{
    public Context context;

    @Inject
    public ContextClass(Context context) {
        this.context = context;
    }
}
