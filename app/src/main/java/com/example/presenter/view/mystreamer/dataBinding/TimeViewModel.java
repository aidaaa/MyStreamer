package com.example.presenter.view.mystreamer.dataBinding;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

public class TimeViewModel extends BaseObservable
{
    String min;
    String sec;
    String hour;

    @Bindable
    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
        notifyPropertyChanged(BR.min);
    }

    @Bindable
    public String getSec() {
        return sec;
    }

    public void setSec(String sec) {
        this.sec = sec;
        notifyPropertyChanged(BR.sec);
    }

    @Bindable
    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
        notifyPropertyChanged(BR.hour);
    }
}
