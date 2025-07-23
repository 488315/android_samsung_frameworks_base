package io.reactivex.disposables;

import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.schedulers.TrampolineScheduler;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class Disposables {
    private Disposables() {
        throw new IllegalStateException("No instances!");
    }

    public static Disposable fromRunnable(TrampolineScheduler.TrampolineWorker.AppendToQueueTask appendToQueueTask) {
        int i = ObjectHelper.$r8$clinit;
        return new RunnableDisposable(appendToQueueTask);
    }
}
