package com.example.domain.usecase;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class UseCase<T> {
    private CompositeDisposable compositeDisposable;

    public UseCase() {
        compositeDisposable =new CompositeDisposable();
    }

    public abstract Observable<T> buildObservable();

    public void execute(DisposableObserver<T> tObserver)
    {
        Observable<T> tObservable=buildObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        setCompositeDisposable(tObservable.subscribeWith(tObserver));
    }

    public void setCompositeDisposable(Disposable disposable)
    {
        compositeDisposable.add(disposable);
    }
}
