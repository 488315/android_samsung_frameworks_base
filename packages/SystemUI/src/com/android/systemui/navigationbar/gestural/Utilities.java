package com.android.systemui.navigationbar.gestural;

import android.view.MotionEvent;

/* loaded from: classes2.dex */
public final class Utilities {
    public static boolean isTrackpadThreeFingerSwipe(MotionEvent motionEvent) {
        return motionEvent.getClassification() == 4 && motionEvent.getAxisValue(53) == 3.0f;
    }
}
