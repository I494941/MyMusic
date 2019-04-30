package com.wjf.mymusic.ui.myDemo.workManager;

import androidx.annotation.NonNull;
import androidx.work.Worker;

/**
 * Created by wjf on 2019/4/24.
 */
public class MyWorker extends Worker {

    @NonNull
    @Override
    public Result doWork() {
        return null;
    }

    @Override
    public void onStopped(boolean cancelled) {
        super.onStopped(cancelled);
    }
}
