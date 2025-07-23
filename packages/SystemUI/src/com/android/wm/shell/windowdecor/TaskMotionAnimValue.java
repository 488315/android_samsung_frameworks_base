package com.android.wm.shell.windowdecor;

import android.graphics.Rect;
import android.view.SurfaceControl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class TaskMotionAnimValue {
    public final int mAnimType;
    public final Rect mEndBounds;
    public final FreeformStashState mFreeformStashState;
    public final Rect mStartBounds;
    public final float mTargetScale;
    public final SurfaceControl mTaskSurface;

    public TaskMotionAnimValue(int i, FreeformStashState freeformStashState, SurfaceControl surfaceControl, Rect rect, Rect rect2, float f) {
        this.mAnimType = i;
        this.mFreeformStashState = freeformStashState;
        this.mTaskSurface = surfaceControl;
        this.mStartBounds = rect;
        this.mEndBounds = rect2;
        this.mTargetScale = f;
    }
}
