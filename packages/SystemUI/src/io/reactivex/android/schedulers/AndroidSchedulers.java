package io.reactivex.android.schedulers;

import android.os.Handler;
import android.os.Looper;
import io.reactivex.Scheduler;
import io.reactivex.internal.util.ExceptionHelper;
import java.util.concurrent.Callable;

/* loaded from: classes4.dex */
public final class AndroidSchedulers {
    public static final Scheduler MAIN_THREAD;

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
