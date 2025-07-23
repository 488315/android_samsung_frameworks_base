package com.android.systemui.shade;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class StatusBarLongPressGestureDetector {
    public final ShadeViewController shadeViewController;

    public StatusBarLongPressGestureDetector(Context context, ShadeViewController shadeViewController) {
        this.shadeViewController = shadeViewController;
        new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() { // from class: com.android.systemui.shade.StatusBarLongPressGestureDetector$gestureDetector$1
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public final void onLongPress(MotionEvent motionEvent) {
                StatusBarLongPressGestureDetector.this.shadeViewController.onStatusBarLongPress();
            }
        });
    }
}
