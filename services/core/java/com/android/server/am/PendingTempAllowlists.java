package com.android.server.am;

import android.util.SparseArray;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
