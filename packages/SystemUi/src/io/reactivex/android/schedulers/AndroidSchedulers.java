package io.reactivex.android.schedulers;

import android.os.Handler;
import android.os.Looper;
import io.reactivex.Scheduler;
import io.reactivex.internal.util.ExceptionHelper;
import java.util.concurrent.Callable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class AndroidSchedulers {
    public static final Scheduler MAIN_THREAD;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MainHolder {
        public static final HandlerScheduler DEFAULT = new HandlerScheduler(new Handler(Looper.getMainLooper()), false);

        private MainHolder() {
        }
    }

    static {
        try {
            Scheduler scheduler = (Scheduler) new Callable() { // from class: io.reactivex.android.schedulers.AndroidSchedulers.1
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    return MainHolder.DEFAULT;
                }
            }.call();
            if (scheduler == null) {
                throw new NullPointerException("Scheduler Callable returned null");
            }
            MAIN_THREAD = scheduler;
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
