package com.android.server.wm;

import com.android.internal.util.ToBooleanFunction;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
