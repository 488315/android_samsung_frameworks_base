package com.android.systemui.appops;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AppOpItem {
    public final String mAttributionTag;
    public final int mCode;
    public boolean mIsDisabled;
    public final String mPackageName;
    public final StringBuilder mState;
    public final long mTimeStartedElapsed;
    public final int mUid;

    public AppOpItem(int i, int i2, String str, long j) {
        this.mCode = i;
        this.mUid = i2;
        this.mPackageName = str;
        this.mTimeStartedElapsed = j;
        this.mAttributionTag = "";
        StringBuilder sb = new StringBuilder();
        sb.append("AppOpItem(Op code=");
        sb.append(i);
        sb.append(", UID=");
        sb.append(i2);
        sb.append(", Package name=");
        sb.append(str);
        sb.append(", Paused=");
        this.mState = sb;
    }

    public final String toString() {
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(this.mState, this.mIsDisabled, ")");
    }

    public AppOpItem(int i, int i2, String str, long j, String str2) {
        this.mCode = i;
        this.mUid = i2;
        this.mPackageName = str;
        this.mTimeStartedElapsed = j;
        this.mAttributionTag = str2;
        StringBuilder sb = new StringBuilder();
        sb.append("AppOpItem(Op code=");
        sb.append(i);
        sb.append(", UID=");
        sb.append(i2);
        sb.append(", AttributionTag=");
        AppOpItem$$ExternalSyntheticOutline0.m97m(sb, str2, ", Package name=", str, ", Paused=");
        this.mState = sb;
    }
}
