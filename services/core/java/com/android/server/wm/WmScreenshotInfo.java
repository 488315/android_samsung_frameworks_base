package com.android.server.wm;

import android.os.Bundle;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
