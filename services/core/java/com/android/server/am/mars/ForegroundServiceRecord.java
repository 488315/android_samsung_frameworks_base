package com.android.server.am.mars;

/* loaded from: classes.dex */
public class ForegroundServiceRecord {
    public long mFGSEndTime;
    public long mFGSStartTime;
    public int mForegroundType;
    public String mPackageName;
    public int mUid;
    public int mUsingForegroundType;

    public ForegroundServiceRecord(String str, int i) {
        this.mPackageName = str;
        this.mUid = i;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public int getFGSType() {
        return this.mForegroundType;
    }

    public void setFGSType(int i) {
        this.mForegroundType = i | this.mForegroundType;
    }

    public int getUsingFGSType() {
        return this.mUsingForegroundType;
    }

    public void setUsingFGSType(int i) {
        this.mUsingForegroundType = i | this.mUsingForegroundType;
    }

    public long getFGSStartTime() {
        return this.mFGSStartTime;
    }

    public void setFGSStartTime(long j) {
        this.mFGSStartTime = j;
    }

    public long getFGSEndTime() {
        return this.mFGSEndTime;
    }

    public void setFGSEndTime(long j) {
        this.mFGSEndTime = j;
    }
}
