package com.samsung.android.server.hwrs.common;

import java.util.concurrent.ThreadFactory;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class HwrsUtils$$ExternalSyntheticLambda0 implements ThreadFactory {
    @Override // java.util.concurrent.ThreadFactory
    public final Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setPriority(4);
        thread.setDaemon(true);
        thread.setName("Executor IO:" + thread.getId());
        return thread;
    }
}
