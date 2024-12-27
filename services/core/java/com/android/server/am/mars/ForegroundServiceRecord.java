package com.android.server.am.mars;

public final class ForegroundServiceRecord {
    public long mFGSEndTime;
    public long mFGSStartTime;
    public int mForegroundType;
    public final String mPackageName;
    public int mUsingForegroundType;

    public ForegroundServiceRecord(String str) {
        this.mPackageName = str;
    }
}
