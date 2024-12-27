package com.android.server.biometrics.sensors;

import android.util.SparseArray;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PerformanceTracker {
    public static SparseArray sTrackers;
    public final SparseArray mAllUsersInfo = new SparseArray();
    public int mHALDeathCount;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Info {
        public int mAccept;
        public int mAcceptCrypto;
        public int mAcquire;
        public int mAcquireCrypto;
        public SparseArray mNoMatchReason;
        public int mPermanentLockout;
        public int mQuality;
        public int mQualityCrypto;
        public int mReject;
        public int mRejectCrypto;
        public int mTimedLockout;
    }

    public static synchronized PerformanceTracker getInstanceForSensorId(int i) {
        PerformanceTracker performanceTracker;
        synchronized (PerformanceTracker.class) {
            try {
                if (sTrackers == null) {
                    sTrackers = new SparseArray();
                }
                if (!sTrackers.contains(i)) {
                    sTrackers.put(i, new PerformanceTracker());
                }
                performanceTracker = (PerformanceTracker) sTrackers.get(i);
            } catch (Throwable th) {
                throw th;
            }
        }
        return performanceTracker;
    }

    public final void createUserEntryIfNecessary(int i) {
        if (this.mAllUsersInfo.contains(i)) {
            return;
        }
        Info info = new Info();
        info.mNoMatchReason = new SparseArray();
        this.mAllUsersInfo.put(i, info);
    }

    public final void incrementAcquireForUser(int i, boolean z) {
        createUserEntryIfNecessary(i);
        if (z) {
            ((Info) this.mAllUsersInfo.get(i)).mAcquireCrypto++;
        } else {
            ((Info) this.mAllUsersInfo.get(i)).mAcquire++;
        }
    }

    public final void incrementPermanentLockoutForUser(int i) {
        createUserEntryIfNecessary(i);
        ((Info) this.mAllUsersInfo.get(i)).mPermanentLockout++;
    }

    public final void incrementTimedLockoutForUser(int i) {
        createUserEntryIfNecessary(i);
        ((Info) this.mAllUsersInfo.get(i)).mTimedLockout++;
    }
}
