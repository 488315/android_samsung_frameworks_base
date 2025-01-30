package com.android.systemui;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class UiOffloadThread {
    public final ExecutorService mExecutorService = Executors.newSingleThreadExecutor();

    public final void execute(Runnable runnable) {
        this.mExecutorService.submit(runnable);
    }
}
