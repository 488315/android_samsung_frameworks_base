package com.android.server.backup.restore;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

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
