package com.android.server.accessibility.magnification;

import android.view.MotionEvent;

public final class MotionEventInfo {
    public MotionEvent mEvent;
    public int mPolicyFlags;
    public MotionEvent mRawEvent;

    public final String toString() {
        return MotionEvent.actionToString(this.mEvent.getAction()).replace("ACTION_", "");
    }
}
