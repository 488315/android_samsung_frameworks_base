package com.android.server.am;

import android.util.SparseArray;
import com.android.server.am.ActivityManagerService;

/* loaded from: classes.dex */
public final class PendingTempAllowlists {
    public final SparseArray mPendingTempAllowlist = new SparseArray();
    public ActivityManagerService mService;

    public PendingTempAllowlists(ActivityManagerService activityManagerService) {
        this.mService = activityManagerService;
    }

    public void put(int i, ActivityManagerService.PendingTempAllowlist pendingTempAllowlist) {
        synchronized (this.mPendingTempAllowlist) {
            this.mPendingTempAllowlist.put(i, pendingTempAllowlist);
        }
    }

    public void removeAt(int i) {
        synchronized (this.mPendingTempAllowlist) {
            this.mPendingTempAllowlist.removeAt(i);
        }
    }

    public ActivityManagerService.PendingTempAllowlist get(int i) {
        ActivityManagerService.PendingTempAllowlist pendingTempAllowlist;
        synchronized (this.mPendingTempAllowlist) {
            pendingTempAllowlist = (ActivityManagerService.PendingTempAllowlist) this.mPendingTempAllowlist.get(i);
        }
        return pendingTempAllowlist;
    }

    public int size() {
        int size;
        synchronized (this.mPendingTempAllowlist) {
            size = this.mPendingTempAllowlist.size();
        }
        return size;
    }

    public ActivityManagerService.PendingTempAllowlist valueAt(int i) {
        ActivityManagerService.PendingTempAllowlist pendingTempAllowlist;
        synchronized (this.mPendingTempAllowlist) {
            pendingTempAllowlist = (ActivityManagerService.PendingTempAllowlist) this.mPendingTempAllowlist.valueAt(i);
        }
        return pendingTempAllowlist;
    }

    public int indexOfKey(int i) {
        int indexOfKey;
        synchronized (this.mPendingTempAllowlist) {
            indexOfKey = this.mPendingTempAllowlist.indexOfKey(i);
        }
        return indexOfKey;
    }
}
