package com.android.systemui.statusbar.gesture;

import android.content.Context;
import android.view.MotionEvent;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.statusbar.window.StatusBarWindowController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SwipeStatusBarAwayGestureHandler extends SwipeUpGestureHandler {
    public final StatusBarWindowController statusBarWindowController;

    public SwipeStatusBarAwayGestureHandler(Context context, DisplayTracker displayTracker, SwipeUpGestureLogger swipeUpGestureLogger, StatusBarWindowController statusBarWindowController) {
        super(context, displayTracker, swipeUpGestureLogger, "SwipeStatusBarAway");
        this.statusBarWindowController = statusBarWindowController;
    }

    @Override // com.android.systemui.statusbar.gesture.SwipeUpGestureHandler
    public final boolean startOfGestureIsWithinBounds(MotionEvent motionEvent) {
        float y = motionEvent.getY();
        StatusBarWindowController statusBarWindowController = this.statusBarWindowController;
        return y >= ((float) statusBarWindowController.mBarHeight) && motionEvent.getY() <= ((float) (statusBarWindowController.mBarHeight * 3));
    }
}
