package com.samsung.android.os;

import android.content.Context;

/* loaded from: classes6.dex */
public class SemDvfsHyPerManager extends SemDvfsManager {
    protected SemDvfsHyPerManager(Context context, String str, int i) {
        super(context, str, i);
        this.LOG_TAG = "SemDvfsHyPerManager";
        this.mName = "HyPer";
    }

    @Override // com.samsung.android.os.SemDvfsManager
    public void setDvfsValue(int i) {
        if (this.mType == -999) {
            return;
        }
        this.acquireHash.put(Integer.valueOf(this.mType), Integer.valueOf(i));
    }

    @Override // com.samsung.android.os.SemDvfsManager
    public void acquire() {
        acquire(-999);
    }
}
