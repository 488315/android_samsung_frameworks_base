package com.airbnb.lottie.utils;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class LottieThreadFactory implements ThreadFactory {
    public static final AtomicInteger poolNumber = new AtomicInteger(1);
    public final ThreadGroup group;
    public final String namePrefix;
    public final AtomicInteger threadNumber = new AtomicInteger(1);

    public LottieThreadFactory() {
        SecurityManager securityManager = System.getSecurityManager();
        this.group = securityManager == null ? Thread.currentThread().getThreadGroup() : securityManager.getThreadGroup();
        this.namePrefix = "lottie-" + poolNumber.getAndIncrement() + "-thread-";
    }

    @Override // java.util.concurrent.ThreadFactory
    public final Thread newThread(Runnable runnable) {
        Thread thread = new Thread(this.group, runnable, this.namePrefix + this.threadNumber.getAndIncrement(), 0L);
        thread.setDaemon(false);
        thread.setPriority(10);
        return thread;
    }
}
