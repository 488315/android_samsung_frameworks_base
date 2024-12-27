package com.android.server.backup.restore;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class RestoreEngine {
    public final AtomicBoolean mRunning = new AtomicBoolean(false);
    public final AtomicInteger mResult = new AtomicInteger(0);

    public final void setRunning(boolean z) {
        synchronized (this.mRunning) {
            this.mRunning.set(z);
            this.mRunning.notifyAll();
        }
    }
}
