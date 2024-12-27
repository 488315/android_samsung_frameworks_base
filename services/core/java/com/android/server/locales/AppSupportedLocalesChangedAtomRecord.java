package com.android.server.locales;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
