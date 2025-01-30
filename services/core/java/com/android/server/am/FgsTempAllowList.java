package com.android.server.am;

import android.os.SystemClock;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import java.util.function.BiConsumer;

/* loaded from: classes.dex */
public class FgsTempAllowList {
    public final SparseArray mTempAllowList = new SparseArray();
    public int mMaxSize = 100;
    public final Object mLock = new Object();

    public void add(int i, long j, Object obj) {
        synchronized (this.mLock) {
            if (j <= 0) {
                Slog.e("ActivityManager", "FgsTempAllowList bad duration:" + j + " key: " + i);
                return;
            }
            long elapsedRealtime = SystemClock.elapsedRealtime();
            int size = this.mTempAllowList.size();
            if (size > this.mMaxSize) {
                Slog.w("ActivityManager", "FgsTempAllowList length:" + size + " exceeds maxSize" + this.mMaxSize);
                for (int i2 = size + (-1); i2 >= 0; i2--) {
                    if (((Long) ((Pair) this.mTempAllowList.valueAt(i2)).first).longValue() < elapsedRealtime) {
                        this.mTempAllowList.removeAt(i2);
                    }
                }
            }
            Pair pair = (Pair) this.mTempAllowList.get(i);
            long j2 = elapsedRealtime + j;
            if (pair == null || ((Long) pair.first).longValue() < j2) {
                this.mTempAllowList.put(i, new Pair(Long.valueOf(j2), obj));
            }
        }
    }

    public Pair get(int i) {
        synchronized (this.mLock) {
            int indexOfKey = this.mTempAllowList.indexOfKey(i);
            if (indexOfKey < 0) {
                return null;
            }
            if (((Long) ((Pair) this.mTempAllowList.valueAt(indexOfKey)).first).longValue() < SystemClock.elapsedRealtime()) {
                this.mTempAllowList.removeAt(indexOfKey);
                return null;
            }
            return (Pair) this.mTempAllowList.valueAt(indexOfKey);
        }
    }

    public boolean isAllowed(int i) {
        return get(i) != null;
    }

    public void removeUid(int i) {
        synchronized (this.mLock) {
            this.mTempAllowList.remove(i);
        }
    }

    public void forEach(BiConsumer biConsumer) {
        synchronized (this.mLock) {
            for (int i = 0; i < this.mTempAllowList.size(); i++) {
                int keyAt = this.mTempAllowList.keyAt(i);
                Pair pair = (Pair) this.mTempAllowList.valueAt(i);
                if (pair != null) {
                    biConsumer.accept(Integer.valueOf(keyAt), pair);
                }
            }
        }
    }
}
