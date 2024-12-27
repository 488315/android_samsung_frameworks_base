package android.database;

import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public abstract class ExecutorContentObserver extends ContentObserver {
    public ExecutorContentObserver(Executor executor) {
        super(executor, 0);
    }
}
