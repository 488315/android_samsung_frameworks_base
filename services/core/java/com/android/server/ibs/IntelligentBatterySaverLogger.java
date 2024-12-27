package com.android.server.ibs;

import android.util.LocalLog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class IntelligentBatterySaverLogger {
    public static IntelligentBatterySaverLogger sInstance;
    public LocalLog mIBSLog;
    public boolean mIsUsed;

    public final void add(String str) {
        if (!this.mIsUsed) {
            this.mIsUsed = true;
        }
        this.mIBSLog.log(str);
    }
}
