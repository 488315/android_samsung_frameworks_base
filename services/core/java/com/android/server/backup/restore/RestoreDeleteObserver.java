package com.android.server.backup.restore;

import android.content.pm.IPackageDeleteObserver;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class RestoreDeleteObserver extends IPackageDeleteObserver.Stub {
    public final AtomicBoolean mDone = new AtomicBoolean();

    public final void packageDeleted(String str, int i) {
        synchronized (this.mDone) {
            this.mDone.set(true);
            this.mDone.notifyAll();
        }
    }

    public final void waitForCompletion() {
        synchronized (this.mDone) {
            while (!this.mDone.get()) {
                try {
                    this.mDone.wait();
                } catch (InterruptedException unused) {
                }
            }
        }
    }
}
