package com.android.systemui.keyguard.ui;

import android.content.Context;
import android.view.MotionEvent;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.statusbar.gesture.SwipeUpGestureHandler;
import com.android.systemui.statusbar.gesture.SwipeUpGestureLogger;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SwipeUpAnywhereGestureHandler extends SwipeUpGestureHandler {
    public SwipeUpAnywhereGestureHandler(Context context, DisplayTracker displayTracker, SwipeUpGestureLogger swipeUpGestureLogger) {
        super(context, displayTracker, swipeUpGestureLogger, "SwipeUpAnywhereGestureHandler");
    }

    @Override // com.android.systemui.statusbar.gesture.SwipeUpGestureHandler
    public final boolean startOfGestureIsWithinBounds(MotionEvent motionEvent) {
        return true;
    }
}
