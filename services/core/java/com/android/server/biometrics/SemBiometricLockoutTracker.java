package com.android.server.biometrics;

import android.util.SparseArray;
import android.util.SparseIntArray;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SemBiometricLockoutTracker {
    public static SemBiometricLockoutTracker sInstance;
    public final SparseArray mFailedAttempts = new SparseArray();

    public static synchronized SemBiometricLockoutTracker get() {
        SemBiometricLockoutTracker semBiometricLockoutTracker;
        synchronized (SemBiometricLockoutTracker.class) {
            try {
                if (sInstance == null) {
                    sInstance = new SemBiometricLockoutTracker();
                }
                semBiometricLockoutTracker = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return semBiometricLockoutTracker;
    }

    public final synchronized void addFailedAttempt(int i, int i2) {
        try {
            SparseIntArray sparseIntArray = (SparseIntArray) this.mFailedAttempts.get(i2);
            if (sparseIntArray == null) {
                SparseIntArray sparseIntArray2 = new SparseIntArray();
                sparseIntArray2.put(i, 1);
                this.mFailedAttempts.put(i2, sparseIntArray2);
            } else {
                sparseIntArray.put(i, sparseIntArray.get(i) + 1);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized int getFailedAttempts(int i) {
        int i2;
        i2 = 0;
        for (int i3 = 0; i3 < this.mFailedAttempts.size(); i3++) {
            i2 += ((SparseIntArray) this.mFailedAttempts.valueAt(i3)).get(i);
        }
        return i2;
    }

    public synchronized void reset() {
        this.mFailedAttempts.clear();
    }

    public final synchronized void resetFailedAttempts(int i, int i2) {
        SparseIntArray sparseIntArray = (SparseIntArray) this.mFailedAttempts.get(i2);
        if (sparseIntArray != null) {
            sparseIntArray.put(i, 0);
        }
    }
}
