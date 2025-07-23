package com.android.systemui;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class UiOffloadThread {
    public final ExecutorService mExecutorService = Executors.newSingleThreadExecutor();

    public final void execute(Runnable runnable) {
        this.mExecutorService.submit(runnable);
    }
}
