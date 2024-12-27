package com.samsung.android.server.hwrs.common;

import java.util.concurrent.ThreadFactory;

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
