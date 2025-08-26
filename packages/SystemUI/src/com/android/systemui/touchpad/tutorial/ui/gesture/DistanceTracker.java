package com.android.systemui.touchpad.tutorial.ui.gesture;

import android.view.MotionEvent;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class DistanceTracker {
    public float startX;
    public float startY;

    /* JADX WARN: Illegal instructions before constructor call */
    public DistanceTracker() {
        float f = 0.0f;
        this(f, f, 3, null);
    }

    public final DistanceGestureState processEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.startX = motionEvent.getX();
            this.startY = motionEvent.getY();
            return new Started(motionEvent.getX(), motionEvent.getY());
        }
        if (actionMasked == 1) {
            return new Finished(motionEvent.getX() - this.startX, motionEvent.getY() - this.startY);
        }
        if (actionMasked != 2) {
            return null;
        }
        return new Moving(motionEvent.getX() - this.startX, motionEvent.getY() - this.startY);
    }

    public DistanceTracker(float f, float f2) {
        this.startX = f;
        this.startY = f2;
    }

    public /* synthetic */ DistanceTracker(float f, float f2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 0.0f : f, (i & 2) != 0 ? 0.0f : f2);
    }
}
