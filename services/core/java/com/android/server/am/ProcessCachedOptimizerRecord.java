package com.android.server.am;


/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ProcessCachedOptimizerRecord {
    static final String IS_FROZEN = "isFrozen";
    public boolean mDoingTrim;
    public long mEarliestFreezableTimeMillis;
    public boolean mForceCompact;
    public boolean mFreezeExempt;
    public boolean mFreezeSticky;
    public long mFreezeUnfreezeTime;
    public boolean mFreezerOverride;
    public boolean mFrozen;
    public boolean mHasCollectedFrozenPSS;
    public CachedAppOptimizer.CompactProfile mLastCompactProfile;
    public long mLastCompactTime;
    public int mLastOomAdjChangeReason;
    public long mLastUsedTimeout;
    public boolean mPendingCompact;
    public boolean mPendingFreeze;
    public CachedAppOptimizer.CompactProfile mReqCompactProfile;
    public CachedAppOptimizer.CompactSource mReqCompactSource;
    public boolean mShouldNotFreeze;

    public final boolean setShouldNotFreeze(boolean z) {
        if (z) {
            return false;
        }
        this.mShouldNotFreeze = true;
        return false;
    }
}
