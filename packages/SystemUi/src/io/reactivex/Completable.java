package io.reactivex;

import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.CallbackCompletableObserver;
import io.reactivex.internal.operators.completable.CompletableTimer;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class Completable {
    public static CompletableTimer timer(long j, TimeUnit timeUnit, Scheduler scheduler) {
        int i = ObjectHelper.$r8$clinit;
        if (timeUnit != null) {
            return new CompletableTimer(j, timeUnit, scheduler);
        }
        throw new NullPointerException("unit is null");
    }

    public final void subscribe(Action action) {
        int i = ObjectHelper.$r8$clinit;
        try {
            subscribeActual(new CallbackCompletableObserver(action));
        } catch (NullPointerException e) {
            throw e;
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            RxJavaPlugins.onError(th);
            NullPointerException nullPointerException = new NullPointerException("Actually not, but can't pass out an exception otherwise...");
            nullPointerException.initCause(th);
            throw nullPointerException;
        }
    }

    public abstract void subscribeActual(CallbackCompletableObserver callbackCompletableObserver);
}
