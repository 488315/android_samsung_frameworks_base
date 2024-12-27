package com.android.server.wm;

import java.util.function.Consumer;

public final /* synthetic */ class WindowManagerConstants$$ExternalSyntheticLambda3
        implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        DisplayContent displayContent = (DisplayContent) obj;
        displayContent.mSystemGestureExclusionLimit =
                (displayContent.mWmService.mConstants.mSystemGestureExclusionLimitDp
                                * displayContent.mDisplayMetrics.densityDpi)
                        / 160;
        displayContent.updateSystemGestureExclusion();
    }
}
