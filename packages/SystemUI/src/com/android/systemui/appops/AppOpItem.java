package com.android.systemui.appops;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;

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
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(this.mState, this.mIsDisabled, ")");
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
        ConstraintWidget$$ExternalSyntheticOutline0.m(sb, str2, ", Package name=", str, ", Paused=");
        this.mState = sb;
    }
}
