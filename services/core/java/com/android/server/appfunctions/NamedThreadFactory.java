package com.android.server.appfunctions;

import com.android.server.pm.PackageManagerShellCommandDataLoader;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public final class NamedThreadFactory implements ThreadFactory {
    public final String mBaseName;
    public final AtomicInteger mCount = new AtomicInteger(0);
    public final ThreadFactory mDefaultThreadFactory = Executors.defaultThreadFactory();

    public NamedThreadFactory(String str) {
        this.mBaseName = str;
    }

    @Override // java.util.concurrent.ThreadFactory
    public final Thread newThread(Runnable runnable) {
        Thread newThread = this.mDefaultThreadFactory.newThread(runnable);
        newThread.setName(
                this.mBaseName
                        + PackageManagerShellCommandDataLoader.STDIN_PATH
                        + this.mCount.getAndIncrement());
        return newThread;
    }
}
