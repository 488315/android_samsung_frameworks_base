package com.android.server.wm;

import com.android.internal.util.ToBooleanFunction;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

public final /* synthetic */ class ActivityRecord$$ExternalSyntheticLambda18
        implements ToBooleanFunction {
    public final /* synthetic */ int $r8$classId;

    public final boolean apply(Object obj) {
        WindowState windowState = (WindowState) obj;
        switch (this.$r8$classId) {
            case 0:
                WindowStateAnimator windowStateAnimator = windowState.mWinAnimator;
                return windowStateAnimator != null
                        && windowStateAnimator.getShown()
                        && windowState.mWinAnimator.mLastAlpha
                                > FullScreenMagnificationGestureHandler.MAX_SCALE;
            default:
                return windowState.isSecureLocked();
        }
    }
}
