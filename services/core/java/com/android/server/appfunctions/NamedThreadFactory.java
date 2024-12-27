package com.android.server.appfunctions;

import com.android.server.pm.PackageManagerShellCommandDataLoader;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
