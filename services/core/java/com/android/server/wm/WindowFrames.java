package com.android.server.wm;

import android.graphics.Rect;

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
        return (this.mLastFrame.width() == this.mFrame.width()
                        && this.mLastFrame.height() == this.mFrame.height())
                ? false
                : true;
    }
}
