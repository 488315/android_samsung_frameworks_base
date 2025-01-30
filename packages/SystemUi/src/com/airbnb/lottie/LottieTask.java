package com.airbnb.lottie;

import android.os.Handler;
import android.os.Looper;
import com.airbnb.lottie.utils.Logger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class LottieTask {
    public static final Executor EXECUTOR = Executors.newCachedThreadPool();
    public final Set failureListeners;
    public final Handler handler;
    public volatile LottieResult result;
    public final Set successListeners;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class LottieFutureTask extends FutureTask {
        public LottieFutureTask(Callable<LottieResult> callable) {
            super(callable);
        }

        @Override // java.util.concurrent.FutureTask
        public final void done() {
            if (isCancelled()) {
                return;
            }
            try {
                LottieTask lottieTask = LottieTask.this;
                LottieResult lottieResult = (LottieResult) get();
                Executor executor = LottieTask.EXECUTOR;
                lottieTask.setResult(lottieResult);
            } catch (InterruptedException | ExecutionException e) {
                LottieTask lottieTask2 = LottieTask.this;
                LottieResult lottieResult2 = new LottieResult(e);
                Executor executor2 = LottieTask.EXECUTOR;
                lottieTask2.setResult(lottieResult2);
            }
        }
    }

    public LottieTask(Callable<LottieResult> callable) {
        this(callable, false);
    }

    public final synchronized void addFailureListener(LottieListener lottieListener) {
        Throwable th;
        LottieResult lottieResult = this.result;
        if (lottieResult != null && (th = lottieResult.exception) != null) {
            lottieListener.onResult(th);
        }
        this.failureListeners.add(lottieListener);
    }

    public final synchronized void addListener(LottieListener lottieListener) {
        Object obj;
        LottieResult lottieResult = this.result;
        if (lottieResult != null && (obj = lottieResult.value) != null) {
            lottieListener.onResult(obj);
        }
        this.successListeners.add(lottieListener);
    }

    public final void setResult(LottieResult lottieResult) {
        if (this.result != null) {
            throw new IllegalStateException("A task may only be set once.");
        }
        this.result = lottieResult;
        this.handler.post(new Runnable() { // from class: com.airbnb.lottie.LottieTask$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                LottieTask lottieTask = LottieTask.this;
                LottieResult lottieResult2 = lottieTask.result;
                if (lottieResult2 == null) {
                    return;
                }
                Object obj = lottieResult2.value;
                if (obj != null) {
                    synchronized (lottieTask) {
                        Iterator it = new ArrayList(lottieTask.successListeners).iterator();
                        while (it.hasNext()) {
                            ((LottieListener) it.next()).onResult(obj);
                        }
                    }
                    return;
                }
                Throwable th = lottieResult2.exception;
                synchronized (lottieTask) {
                    ArrayList arrayList = new ArrayList(lottieTask.failureListeners);
                    if (arrayList.isEmpty()) {
                        Logger.warning("Lottie encountered an error but no failure listener was added:", th);
                        return;
                    }
                    Iterator it2 = arrayList.iterator();
                    while (it2.hasNext()) {
                        ((LottieListener) it2.next()).onResult(th);
                    }
                }
            }
        });
    }

    public LottieTask(Callable<LottieResult> callable, boolean z) {
        this.successListeners = new LinkedHashSet(1);
        this.failureListeners = new LinkedHashSet(1);
        this.handler = new Handler(Looper.getMainLooper());
        this.result = null;
        if (!z) {
            EXECUTOR.execute(new LottieFutureTask(callable));
            return;
        }
        try {
            setResult(callable.call());
        } catch (Throwable th) {
            setResult(new LottieResult(th));
        }
    }
}
