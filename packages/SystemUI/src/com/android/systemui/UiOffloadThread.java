package com.android.systemui;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class UiOffloadThread {
    public final ExecutorService mExecutorService = Executors.newSingleThreadExecutor();

    public final void execute(Runnable runnable) {
        this.mExecutorService.submit(runnable);
    }
}
