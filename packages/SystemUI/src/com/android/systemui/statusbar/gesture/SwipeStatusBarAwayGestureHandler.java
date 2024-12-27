package com.android.systemui.statusbar.gesture;

import android.content.Context;
import android.view.MotionEvent;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.statusbar.window.StatusBarWindowController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
