package com.android.server.wm;

import android.view.Display;

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
