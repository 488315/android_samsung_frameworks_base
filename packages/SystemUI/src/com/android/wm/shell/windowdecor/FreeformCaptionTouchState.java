package com.android.wm.shell.windowdecor;

import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class FreeformCaptionTouchState {
    public final int mMaximumFlingVelocity;
    public final int mMinimumFlingVelocity;
    public final PointF mVelocity = new PointF(0.0f, 0.0f);
    public VelocityTracker mVelocityTracker;

    public FreeformCaptionTouchState(ViewConfiguration viewConfiguration) {
        this.mMinimumFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaximumFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
    }

    public final void addMovementToVelocityTracker(MotionEvent motionEvent) {
        if (this.mVelocityTracker == null || motionEvent.getToolType(0) == 3) {
            return;
        }
        float rawX = motionEvent.getRawX() - motionEvent.getX();
        float rawY = motionEvent.getRawY() - motionEvent.getY();
        motionEvent.offsetLocation(rawX, rawY);
        this.mVelocityTracker.addMovement(motionEvent);
        motionEvent.offsetLocation(-rawX, -rawY);
    }
}
