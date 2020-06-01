package com.example.presenter.view.mystreamer.dataBinding;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

public class DateViewModel extends BaseObservable
{
    String day,month,year;

    @Bindable
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
        notifyPropertyChanged(BR.day);
    }

    @Bindable
    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
        notifyPropertyChanged(BR.month);
    }

    @Bindable
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
        notifyPropertyChanged(BR.year);
    }
}
