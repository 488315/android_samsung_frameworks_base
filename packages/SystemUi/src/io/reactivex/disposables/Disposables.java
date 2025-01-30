package io.reactivex.disposables;

import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.schedulers.TrampolineScheduler;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class Disposables {
    private Disposables() {
        throw new IllegalStateException("No instances!");
    }

    public static Disposable fromRunnable(TrampolineScheduler.TrampolineWorker.AppendToQueueTask appendToQueueTask) {
        int i = ObjectHelper.$r8$clinit;
        return new RunnableDisposable(appendToQueueTask);
    }
}
