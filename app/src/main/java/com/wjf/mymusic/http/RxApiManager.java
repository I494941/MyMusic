package com.wjf.mymusic.http;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by ws on 2017/11/6 0006.
 */

public class RxApiManager {

    private CompositeDisposable disposables;

    public RxApiManager() {
        if (disposables == null)
            disposables = new CompositeDisposable();
    }

    public void add(Disposable disposable) {
        if (disposables != null)
            disposables.add(disposable);
    }

    public void clear() {
        if (disposables != null) {
            disposables.clear();
        }
    }
}
