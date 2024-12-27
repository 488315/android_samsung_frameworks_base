package com.android.systemui.statusbar.gesture;

import android.content.Context;
import android.view.MotionEvent;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.statusbar.window.StatusBarWindowController;

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
