package com.android.systemui.keyguard.ui;

import android.content.Context;
import android.view.MotionEvent;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.statusbar.gesture.SwipeUpGestureHandler;
import com.android.systemui.statusbar.gesture.SwipeUpGestureLogger;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
