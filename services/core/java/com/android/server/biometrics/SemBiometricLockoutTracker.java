package com.android.server.biometrics;

import android.util.SparseArray;
import android.util.SparseIntArray;

/* loaded from: classes.dex */
public class SemBiometricLockoutTracker {
    public static SemBiometricLockoutTracker sInstance;
    public final SparseArray mFailedAttempts = new SparseArray();

    public static synchronized SemBiometricLockoutTracker get() {
        SemBiometricLockoutTracker semBiometricLockoutTracker;
        synchronized (SemBiometricLockoutTracker.class) {
            if (sInstance == null) {
                sInstance = new SemBiometricLockoutTracker();
            }
            semBiometricLockoutTracker = sInstance;
        }
        return semBiometricLockoutTracker;
    }

    public synchronized void reset() {
        this.mFailedAttempts.clear();
    }

    public synchronized int getFailedAttempts(int i) {
        int i2;
        i2 = 0;
        for (int i3 = 0; i3 < this.mFailedAttempts.size(); i3++) {
            i2 += ((SparseIntArray) this.mFailedAttempts.valueAt(i3)).get(i);
        }
        return i2;
    }

    public synchronized void addFailedAttempt(int i, int i2) {
        SparseIntArray sparseIntArray = (SparseIntArray) this.mFailedAttempts.get(i2);
        if (sparseIntArray == null) {
            SparseIntArray sparseIntArray2 = new SparseIntArray();
            sparseIntArray2.put(i, 1);
            this.mFailedAttempts.put(i2, sparseIntArray2);
        } else {
            sparseIntArray.put(i, sparseIntArray.get(i) + 1);
        }
    }

    public synchronized void resetFailedAttempts(int i, int i2) {
        SparseIntArray sparseIntArray = (SparseIntArray) this.mFailedAttempts.get(i2);
        if (sparseIntArray != null) {
            sparseIntArray.put(i, 0);
        }
    }
}
