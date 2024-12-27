package com.android.server.timezonedetector.location;

public final /* synthetic */ class ThreadingDomain$SingleRunnableQueue$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ ThreadingDomain$SingleRunnableQueue f$0;
    public final /* synthetic */ Runnable f$1;

    public /* synthetic */ ThreadingDomain$SingleRunnableQueue$$ExternalSyntheticLambda0(
            ThreadingDomain$SingleRunnableQueue threadingDomain$SingleRunnableQueue,
            Runnable runnable) {
        this.f$0 = threadingDomain$SingleRunnableQueue;
        this.f$1 = runnable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ThreadingDomain$SingleRunnableQueue threadingDomain$SingleRunnableQueue = this.f$0;
        Runnable runnable = this.f$1;
        threadingDomain$SingleRunnableQueue.mIsQueued = false;
        threadingDomain$SingleRunnableQueue.mDelayMillis = -2L;
        runnable.run();
    }
}
