package com.android.systemui.statusbar.gesture;

import android.content.Context;
import android.view.MotionEvent;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.statusbar.window.StatusBarWindowControllerImpl;
import com.android.systemui.statusbar.window.StatusBarWindowControllerStore;

/* loaded from: classes3.dex */
public final class SwipeStatusBarAwayGestureHandler extends SwipeUpGestureHandler {
    public final StatusBarWindowControllerStore statusBarWindowControllerStore;

    public SwipeStatusBarAwayGestureHandler(Context context, DisplayTracker displayTracker, SwipeUpGestureLogger swipeUpGestureLogger, StatusBarWindowControllerStore statusBarWindowControllerStore) {
        super(context, displayTracker, swipeUpGestureLogger, "SwipeStatusBarAway");
        this.statusBarWindowControllerStore = statusBarWindowControllerStore;
    }

    @Override // com.android.systemui.statusbar.gesture.SwipeUpGestureHandler
    public final boolean startOfGestureIsWithinBounds(MotionEvent motionEvent) {
        StatusBarWindowController statusBarWindowController = (StatusBarWindowController) this.statusBarWindowControllerStore.getDefaultDisplay();
        return motionEvent.getY() >= ((float) ((StatusBarWindowControllerImpl) statusBarWindowController).mBarHeight) && motionEvent.getY() <= ((float) (((StatusBarWindowControllerImpl) statusBarWindowController).mBarHeight * 3));
    }
}
