package com.samsung.android.photoremasterservice;

import com.samsung.android.photoremaster.util.LogUtil;

/* loaded from: classes6.dex */
public class ProgressObserver {
    static final String TAG = "ProgressObserver";
    private boolean updated = false;

    public synchronized void update() {
        LogUtil.d(TAG, "update is called.");
        this.updated = true;
    }

    public synchronized boolean wasUpdateAndClear() {
        boolean ret;
        ret = this.updated;
        this.updated = false;
        return ret;
    }
}
