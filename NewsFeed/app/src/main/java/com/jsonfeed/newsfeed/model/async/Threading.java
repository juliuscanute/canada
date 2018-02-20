package com.jsonfeed.newsfeed.model.async;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import java.util.concurrent.Callable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
/**
 * Created by juliuscanute on 20/02/18.
 */

public class Threading {
    private static final String TAG = "Threading";

    private Threading(){}

    public static void dispatchMain(Action block) {
        Handler mainHandler = new Handler(Looper.getMainLooper());
        mainHandler.post(() -> {
            try {
                block.run();
            } catch (Exception e) {
                Log.e(TAG,"Exception Occurred: ",e);
            }
        });
    }

    public static <T> Disposable async(Callable<T> task) {
        return async(task, null, null, Schedulers.io());
    }

    public static <T> Disposable async(Callable<T> task, Consumer<T> finished) {
        return async(task, finished, null, Schedulers.io());
    }

    public static <T> Disposable async(Callable<T> task, Consumer<T> finished, Consumer<Throwable> onError) {
        return async(task, finished, onError, Schedulers.io());
    }

    public static <T> Disposable async(Callable<T> task, Consumer<T> finished, Consumer<Throwable> onError, Scheduler scheduler) {
        finished = finished != null ? finished
                : (a) -> {};
        onError = onError != null ? onError
                : throwable -> {};

        return Single.fromCallable(task)
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(finished, onError);
    }
}
