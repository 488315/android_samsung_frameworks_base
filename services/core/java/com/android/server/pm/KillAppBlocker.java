package com.android.server.pm;

import android.app.ActivityManagerInternal;
import android.app.UidObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class KillAppBlocker {
    public final CountDownLatch mUidsGoneCountDownLatch = new CountDownLatch(1);
    public final List mActiveUids = new ArrayList();
    public boolean mRegistered = false;
    public final AnonymousClass1 mUidObserver =
            new UidObserver() { // from class: com.android.server.pm.KillAppBlocker.1
                public final void onUidGone(int i, boolean z) {
                    synchronized (this) {
                        try {
                            ((ArrayList) KillAppBlocker.this.mActiveUids)
                                    .remove(Integer.valueOf(i));
                            if (((ArrayList) KillAppBlocker.this.mActiveUids).size() == 0) {
                                KillAppBlocker.this.mUidsGoneCountDownLatch.countDown();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            };

    public final void waitAppProcessGone(
            ActivityManagerInternal activityManagerInternal,
            Computer computer,
            UserManagerService userManagerService,
            String str) {
        if (this.mRegistered) {
            synchronized (this) {
                try {
                    for (int i : userManagerService.getUserIds()) {
                        int packageUidInternal =
                                computer.getPackageUidInternal(i, 1000, 131072L, str);
                        if (packageUidInternal != -1
                                && activityManagerInternal.getUidProcessState(packageUidInternal)
                                        != 20) {
                            ((ArrayList) this.mActiveUids).add(Integer.valueOf(packageUidInternal));
                        }
                    }
                    if (((ArrayList) this.mActiveUids).size() == 0) {
                        return;
                    }
                    try {
                        this.mUidsGoneCountDownLatch.await(1000L, TimeUnit.MILLISECONDS);
                    } catch (InterruptedException unused) {
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }
}
