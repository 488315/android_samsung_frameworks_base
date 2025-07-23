package com.android.wm.shell.freeform;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class FreeformContainerSystemProxy$$ExternalSyntheticLambda1 implements ThreadFactory {
    @Override // java.util.concurrent.ThreadFactory
    public final Thread newThread(Runnable runnable) {
        ExecutorService executorService = FreeformContainerSystemProxy.mExecutor;
        return new Thread(runnable, "FreeformContainerSystemProxy");
    }
}
