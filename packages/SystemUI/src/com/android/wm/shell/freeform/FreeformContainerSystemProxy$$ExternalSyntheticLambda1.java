package com.android.wm.shell.freeform;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;

/* loaded from: classes3.dex */
public final /* synthetic */ class FreeformContainerSystemProxy$$ExternalSyntheticLambda1 implements ThreadFactory {
    @Override // java.util.concurrent.ThreadFactory
    public final Thread newThread(Runnable runnable) {
        ExecutorService executorService = FreeformContainerSystemProxy.mExecutor;
        return new Thread(runnable, "FreeformContainerSystemProxy");
    }
}
