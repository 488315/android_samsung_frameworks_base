package com.android.server.devicepolicy;

import android.content.pm.IPackageDeleteObserver;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;

import java.util.concurrent.CountDownLatch;

public final class NonRequiredPackageDeleteObserver extends IPackageDeleteObserver.Stub {
    public boolean mFailed = false;
    public final CountDownLatch mLatch;

    public NonRequiredPackageDeleteObserver(int i) {
        this.mLatch = new CountDownLatch(i);
    }

    public final void packageDeleted(String str, int i) {
        if (i != 1) {
            BootReceiver$$ExternalSyntheticOutline0.m(
                    "Failed to delete package: ", str, "DevicePolicyManager");
            this.mFailed = true;
        }
        this.mLatch.countDown();
    }
}
