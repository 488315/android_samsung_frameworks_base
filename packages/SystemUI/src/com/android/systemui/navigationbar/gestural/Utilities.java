package com.android.systemui.navigationbar.gestural;

import android.view.MotionEvent;

public final class Utilities {
    public static boolean isTrackpadScroll(MotionEvent motionEvent) {
        return motionEvent.getClassification() == 3;
    }

    public static boolean isTrackpadThreeFingerSwipe(MotionEvent motionEvent) {
        return motionEvent.getClassification() == 4 && motionEvent.getAxisValue(53) == 3.0f;
    }
}
