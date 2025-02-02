package androidx.appcompat.app;

import java.util.concurrent.Executor;

/* loaded from: classes.dex */
final class AppLocalesStorageHelper$ThreadPerTaskExecutor implements Executor {
    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        new Thread(runnable).start();
    }
}
