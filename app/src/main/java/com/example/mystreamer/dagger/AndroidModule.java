package com.example.mystreamer.dagger;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import dagger.Module;
import dagger.Provides;

@Module
public class AndroidModule
{
    /*private Context context;

    public AndroidModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context getContext()
    {
        return context;
    }*/

    @Provides
    public SharedPreferences getSharedPreferences(ContextClass contextClass)
    {
        return PreferenceManager.getDefaultSharedPreferences(contextClass.context);
    }
}
