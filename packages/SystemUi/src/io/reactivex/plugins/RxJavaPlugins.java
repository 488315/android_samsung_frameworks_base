package io.reactivex.plugins;

import io.reactivex.Scheduler;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.exceptions.OnErrorNotImplementedException;
import io.reactivex.exceptions.UndeliverableException;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.ExceptionHelper;
import java.util.concurrent.Callable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class RxJavaPlugins {
    private RxJavaPlugins() {
        throw new IllegalStateException("No instances!");
    }

    public static Scheduler callRequireNonNull(Callable callable) {
        try {
            Object call = callable.call();
            ObjectHelper.requireNonNull(call, "Scheduler Callable result can't be null");
            return (Scheduler) call;
        } catch (Throwable th) {
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }

    public static void onError(Throwable th) {
        if (th == null) {
            th = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
        } else {
            if (!((th instanceof OnErrorNotImplementedException) || (th instanceof MissingBackpressureException) || (th instanceof IllegalStateException) || (th instanceof NullPointerException) || (th instanceof IllegalArgumentException) || (th instanceof CompositeException))) {
                th = new UndeliverableException(th);
            }
        }
        th.printStackTrace();
        Thread currentThread = Thread.currentThread();
        currentThread.getUncaughtExceptionHandler().uncaughtException(currentThread, th);
    }

    public static void onSchedule(Runnable runnable) {
        int i = ObjectHelper.$r8$clinit;
        if (runnable == null) {
            throw new NullPointerException("run is null");
        }
    }
}
