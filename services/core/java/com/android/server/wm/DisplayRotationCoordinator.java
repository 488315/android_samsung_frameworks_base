package com.android.server.wm;

import android.view.Display;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DisplayRotationCoordinator {
    public int mDefaultDisplayCurrentRotation;
    public int mDefaultDisplayDefaultRotation;
    Runnable mDefaultDisplayRotationChangedCallback;

    public static boolean isSecondaryInternalDisplay(DisplayContent displayContent) {
        Display display;
        return (displayContent.isDefaultDisplay
                        || (display = displayContent.mDisplay) == null
                        || display.getType() != 1)
                ? false
                : true;
    }
}
