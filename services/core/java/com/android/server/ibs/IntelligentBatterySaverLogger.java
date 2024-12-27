package com.android.server.ibs;

import android.util.LocalLog;

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
