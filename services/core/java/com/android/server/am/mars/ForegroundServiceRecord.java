package com.android.server.am.mars;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
