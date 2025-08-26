package com.android.systemui.shade;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;

/* loaded from: classes3.dex */
public final class StatusBarLongPressGestureDetector {
    public final ShadeViewController shadeViewController;

    public StatusBarLongPressGestureDetector(Context context, ShadeViewController shadeViewController) {
        this.shadeViewController = shadeViewController;
        new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() { // from class: com.android.systemui.shade.StatusBarLongPressGestureDetector$gestureDetector$1
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public final void onLongPress(MotionEvent motionEvent) {
                this.this$0.shadeViewController.onStatusBarLongPress();
            }
        });
    }
}
