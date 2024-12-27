package com.android.server.locales;

public final class AppSupportedLocalesChangedAtomRecord {
    public final int mCallingUid;
    public int mTargetUid = -1;
    public int mNumLocales = -1;
    public boolean mOverrideRemoved = false;
    public boolean mSameAsResConfig = false;
    public boolean mSameAsPrevConfig = false;
    public int mStatus = 0;

    public AppSupportedLocalesChangedAtomRecord(int i) {
        this.mCallingUid = i;
    }
}
