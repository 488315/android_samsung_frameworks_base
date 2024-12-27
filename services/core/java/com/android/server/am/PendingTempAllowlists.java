package com.android.server.am;

import android.util.SparseArray;

public final class PendingTempAllowlists {
    public final SparseArray mPendingTempAllowlist = new SparseArray();

    public final int size() {
        int size;
        synchronized (this.mPendingTempAllowlist) {
            size = this.mPendingTempAllowlist.size();
        }
        return size;
    }

    public final ActivityManagerService.PendingTempAllowlist valueAt(int i) {
        ActivityManagerService.PendingTempAllowlist pendingTempAllowlist;
        synchronized (this.mPendingTempAllowlist) {
            pendingTempAllowlist =
                    (ActivityManagerService.PendingTempAllowlist)
                            this.mPendingTempAllowlist.valueAt(i);
        }
        return pendingTempAllowlist;
    }
}
