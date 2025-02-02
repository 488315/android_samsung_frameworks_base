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

/* loaded from: classes.dex */
public final class LottieTask<T> {
    public static Executor EXECUTOR = Executors.newCachedThreadPool();
    private final Set<LottieListener<Throwable>> failureListeners;
    private final Handler handler;
    private volatile LottieResult<T> result;
    private final Set<LottieListener<T>> successListeners;

    private class LottieFutureTask extends FutureTask<LottieResult<T>> {
        LottieFutureTask(Callable<LottieResult<T>> callable) {
            super(callable);
        }

        @Override // java.util.concurrent.FutureTask
        protected final void done() {
            if (isCancelled()) {
                return;
            }
            try {
                LottieTask.this.setResult(get());
            } catch (InterruptedException | ExecutionException e) {
                LottieTask.this.setResult(new LottieResult(e));
            }
        }
    }

    public LottieTask() {
        throw null;
    }

    LottieTask(Callable<LottieResult<T>> callable, boolean z) {
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
            setResult(new LottieResult<>(th));
        }
    }

    static void access$100(LottieTask lottieTask, Object obj) {
        synchronized (lottieTask) {
            Iterator it = new ArrayList(lottieTask.successListeners).iterator();
            while (it.hasNext()) {
                ((LottieListener) it.next()).onResult(obj);
            }
        }
    }

    static void access$200(LottieTask lottieTask, Throwable th) {
        synchronized (lottieTask) {
            ArrayList arrayList = new ArrayList(lottieTask.failureListeners);
            if (arrayList.isEmpty()) {
                Logger.warning("Lottie encountered an error but no failure listener was added:", th);
                return;
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ((LottieListener) it.next()).onResult(th);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setResult(LottieResult<T> lottieResult) {
        if (this.result != null) {
            throw new IllegalStateException("A task may only be set once.");
        }
        this.result = lottieResult;
        this.handler.post(new Runnable() { // from class: com.airbnb.lottie.LottieTask.1
            @Override // java.lang.Runnable
            public final void run() {
                if (LottieTask.this.result == null) {
                    return;
                }
                LottieResult lottieResult2 = LottieTask.this.result;
                if (lottieResult2.getValue() != null) {
                    LottieTask.access$100(LottieTask.this, lottieResult2.getValue());
                } else {
                    LottieTask.access$200(LottieTask.this, lottieResult2.getException());
                }
            }
        });
    }

    public final synchronized void addFailureListener(LottieListener lottieListener) {
        if (this.result != null && this.result.getException() != null) {
            lottieListener.onResult(this.result.getException());
        }
        this.failureListeners.add(lottieListener);
    }

    public final synchronized void addListener(LottieListener lottieListener) {
        if (this.result != null && this.result.getValue() != null) {
            lottieListener.onResult(this.result.getValue());
        }
        this.successListeners.add(lottieListener);
    }

    public final synchronized void removeFailureListener(LottieListener lottieListener) {
        this.failureListeners.remove(lottieListener);
    }

    public final synchronized void removeListener(LottieListener lottieListener) {
        this.successListeners.remove(lottieListener);
    }
}
