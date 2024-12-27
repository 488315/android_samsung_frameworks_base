package com.android.server.pm;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DumpState {
    public boolean mBrief;
    public boolean mCheckIn;
    public boolean mFullPreferred;
    public int mOptions;
    public SharedUserSetting mSharedUser;
    public String mTargetPackageName;
    public boolean mTitlePrinted;
    public int mTypes;

    public final boolean isDumping(int i) {
        int i2 = this.mTypes;
        return (i2 == 0 && i != 8192) || (i2 & i) != 0;
    }

    public final boolean isOptionEnabled(int i) {
        return (this.mOptions & i) != 0;
    }

    public final void setDump(int i) {
        this.mTypes = i | this.mTypes;
    }
}
