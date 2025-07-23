package com.android.systemui.touchpad.tutorial.ui.gesture;

import android.view.MotionEvent;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DistanceTracker {
    public float startX;
    public float startY;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public DistanceTracker() {
        /*
            r3 = this;
            r0 = 3
            r1 = 0
            r2 = 0
            r3.<init>(r2, r2, r0, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.touchpad.tutorial.ui.gesture.DistanceTracker.<init>():void");
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
