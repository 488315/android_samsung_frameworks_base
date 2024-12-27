package com.android.server.hdmi;

import android.os.Binder;

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
