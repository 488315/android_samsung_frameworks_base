package com.android.server.am;

import android.os.SystemClock;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class FgsTempAllowList {
    public final SparseArray mTempAllowList = new SparseArray();
    public final int mMaxSize = 100;
    public final Object mLock = new Object();

    public final void add(int i, long j, Object obj) {
        synchronized (this.mLock) {
            try {
                if (j <= 0) {
                    Slog.e("ActivityManager", "FgsTempAllowList bad duration:" + j + " key: " + i);
                    return;
                }
                long elapsedRealtime = SystemClock.elapsedRealtime();
                int size = this.mTempAllowList.size();
                if (size > this.mMaxSize) {
                    Slog.w(
                            "ActivityManager",
                            "FgsTempAllowList length:" + size + " exceeds maxSize" + this.mMaxSize);
                    for (int i2 = size + (-1); i2 >= 0; i2--) {
                        if (((Long) ((Pair) this.mTempAllowList.valueAt(i2)).first).longValue()
                                < elapsedRealtime) {
                            this.mTempAllowList.removeAt(i2);
                        }
                    }
                }
                Pair pair = (Pair) this.mTempAllowList.get(i);
                long j2 = elapsedRealtime + j;
                if (pair == null || ((Long) pair.first).longValue() < j2) {
                    this.mTempAllowList.put(i, new Pair(Long.valueOf(j2), obj));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void forEach(
            ActivityManagerService$$ExternalSyntheticLambda27
                    activityManagerService$$ExternalSyntheticLambda27) {
        synchronized (this.mLock) {
            for (int i = 0; i < this.mTempAllowList.size(); i++) {
                try {
                    int keyAt = this.mTempAllowList.keyAt(i);
                    Pair pair = (Pair) this.mTempAllowList.valueAt(i);
                    if (pair != null) {
                        activityManagerService$$ExternalSyntheticLambda27.accept(
                                Integer.valueOf(keyAt), pair);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final Pair get(int i) {
        synchronized (this.mLock) {
            try {
                int indexOfKey = this.mTempAllowList.indexOfKey(i);
                if (indexOfKey < 0) {
                    return null;
                }
                if (((Long) ((Pair) this.mTempAllowList.valueAt(indexOfKey)).first).longValue()
                        < SystemClock.elapsedRealtime()) {
                    this.mTempAllowList.removeAt(indexOfKey);
                    return null;
                }
                return (Pair) this.mTempAllowList.valueAt(indexOfKey);
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
