package com.android.server.wm;

import android.os.Bundle;

public final class WmScreenshotInfo {
    public Bundle mBundle;
    public int mDisplayId;
    public int mOrigin;
    public int mSweepDirection;
    public int mType;

    public final String toString() {
        return "ScreenshotInfo{type="
                + this.mType
                + ", sweepDirection="
                + this.mSweepDirection
                + ", display="
                + this.mDisplayId
                + ", origin="
                + this.mOrigin
                + ", bundle="
                + this.mBundle
                + "}";
    }
}
