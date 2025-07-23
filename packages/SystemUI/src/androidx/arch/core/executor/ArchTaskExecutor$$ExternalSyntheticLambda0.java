package androidx.arch.core.executor;

import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class ArchTaskExecutor$$ExternalSyntheticLambda0 implements Executor {
    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        ArchTaskExecutor.getInstance().mDelegate.mDiskIO.execute(runnable);
    }
}
