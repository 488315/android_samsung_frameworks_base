package io.reactivex.disposables;

import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.schedulers.TrampolineScheduler;

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
