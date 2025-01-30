package com.android.server.wm;

import android.os.Bundle;

/* loaded from: classes3.dex */
public class WmScreenshotInfo {
    public int mType = 1;
    public int mSweepDirection = 1;
    public int mDisplayId = 0;
    public int mOrigin = 1;
    public Bundle mBundle = null;

    public void set(int i, int i2, int i3, int i4, Bundle bundle) {
        this.mType = i;
        this.mSweepDirection = i2;
        this.mDisplayId = i3;
        this.mOrigin = i4;
        this.mBundle = bundle;
    }

    public int getType() {
        return this.mType;
    }

    public int getSweepDirection() {
        return this.mSweepDirection;
    }

    public int getDisplayId() {
        return this.mDisplayId;
    }

    public int getOrigin() {
        return this.mOrigin;
    }

    public Bundle getBundle() {
        return this.mBundle;
    }

    public String toString() {
        return "ScreenshotInfo{type=" + this.mType + ", sweepDirection=" + this.mSweepDirection + ", display=" + this.mDisplayId + ", origin=" + this.mOrigin + ", bundle=" + this.mBundle + "}";
    }
}
