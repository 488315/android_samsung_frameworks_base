package com.android.server.enterprise.firewall;

import android.util.SparseBooleanArray;

public final class KnoxNetIdManager {
    public int mLastNetId;
    public final int mMinNetId;
    public final SparseBooleanArray mNetIdInUse;

    public KnoxNetIdManager() {
        this(40000);
    }

    public KnoxNetIdManager(int i) {
        this.mNetIdInUse = new SparseBooleanArray();
        this.mLastNetId = 64510;
        this.mMinNetId = i;
    }

    public final void releaseNetId(int i) {
        synchronized (this.mNetIdInUse) {
            this.mNetIdInUse.delete(i);
        }
    }

    public final int reserveNetId() {
        int i;
        synchronized (this.mNetIdInUse) {
            try {
                int i2 = this.mLastNetId;
                SparseBooleanArray sparseBooleanArray = this.mNetIdInUse;
                int i3 = 64510;
                while (true) {
                    int i4 = this.mMinNetId;
                    if (i3 < i4) {
                        throw new IllegalStateException("No free netIds");
                    }
                    i2 = i2 > i4 ? i2 - 1 : 64510;
                    if (sparseBooleanArray.get(i2)) {
                        i3--;
                    } else {
                        this.mLastNetId = i2;
                        this.mNetIdInUse.put(i2, true);
                        i = this.mLastNetId;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return i;
    }
}
