package com.android.systemui;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes.dex */
public class UiOffloadThread {
    public final ExecutorService mExecutorService = Executors.newSingleThreadExecutor();

    public final void execute(Runnable runnable) {
        this.mExecutorService.submit(runnable);
    }
}
