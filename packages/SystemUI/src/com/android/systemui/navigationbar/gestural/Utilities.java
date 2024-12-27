package com.android.systemui.navigationbar.gestural;

import android.view.MotionEvent;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class Utilities {
    public static boolean isTrackpadScroll(MotionEvent motionEvent) {
        return motionEvent.getClassification() == 3;
    }

    public static boolean isTrackpadThreeFingerSwipe(MotionEvent motionEvent) {
        return motionEvent.getClassification() == 4 && motionEvent.getAxisValue(53) == 3.0f;
    }
}
