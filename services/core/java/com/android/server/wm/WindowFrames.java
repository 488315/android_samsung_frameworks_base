package com.android.server.wm;

import android.graphics.Rect;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class WindowFrames {
    public static final StringBuilder sTmpSB = new StringBuilder();
    public boolean mContentChanged;
    public boolean mInsetsChanged;
    public boolean mParentFrameWasClippedByDisplayCutout;
    public final Rect mParentFrame = new Rect();
    public final Rect mDisplayFrame = new Rect();
    public final Rect mFrame = new Rect();
    public final Rect mLastFrame = new Rect();
    public final Rect mRelFrame = new Rect();
    public final Rect mLastRelFrame = new Rect();
    public boolean mFrameSizeChanged = false;
    public final Rect mCompatFrame = new Rect();
    public boolean mLastForceReportingResized = false;
    public boolean mForceReportingResized = false;

    public final boolean didFrameSizeChange() {
        return (this.mLastFrame.width() == this.mFrame.width() && this.mLastFrame.height() == this.mFrame.height()) ? false : true;
    }
}
