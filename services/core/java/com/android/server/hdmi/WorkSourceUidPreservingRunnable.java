package com.android.server.hdmi;

import android.os.Binder;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class WorkSourceUidPreservingRunnable implements Runnable {
    public final Runnable mRunnable;
    public final int mUid = Binder.getCallingWorkSourceUid();

    public WorkSourceUidPreservingRunnable(Runnable runnable) {
        this.mRunnable = runnable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        long callingWorkSourceUid = Binder.setCallingWorkSourceUid(this.mUid);
        try {
            this.mRunnable.run();
        } finally {
            Binder.restoreCallingWorkSource(callingWorkSourceUid);
        }
    }
}
