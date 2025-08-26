package com.samsung.android.sdk.scs.base.tasks;

import java.util.concurrent.Executor;

/* loaded from: classes4.dex */
public final class BasicExecutor implements Executor {
    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        runnable.run();
    }
}
