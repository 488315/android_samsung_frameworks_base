package io.reactivex.android.schedulers;

import android.os.Handler;
import android.os.Looper;
import io.reactivex.Scheduler;
import io.reactivex.internal.util.ExceptionHelper;
import java.util.concurrent.Callable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class AndroidSchedulers {
    public static final Scheduler MAIN_THREAD;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class MainHolder {
        public static final HandlerScheduler DEFAULT = new HandlerScheduler(new Handler(Looper.getMainLooper()), false);

        private MainHolder() {
        }
    }

    static {
        new Callable() { // from class: io.reactivex.android.schedulers.AndroidSchedulers.1
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return MainHolder.DEFAULT;
            }
        };
        try {
            HandlerScheduler handlerScheduler = MainHolder.DEFAULT;
            if (handlerScheduler == null) {
                throw new NullPointerException("Scheduler Callable returned null");
            }
            MAIN_THREAD = handlerScheduler;
        } catch (Throwable th) {
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }

    private AndroidSchedulers() {
        throw new AssertionError("No instances.");
    }

    public static Scheduler mainThread() {
        Scheduler scheduler = MAIN_THREAD;
        if (scheduler != null) {
            return scheduler;
        }
        throw new NullPointerException("scheduler == null");
    }
}
