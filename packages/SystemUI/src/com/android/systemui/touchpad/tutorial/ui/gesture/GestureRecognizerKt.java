package com.android.systemui.touchpad.tutorial.ui.gesture;

import android.view.MotionEvent;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class GestureRecognizerKt {
    public static final boolean isMultifingerTouchpadSwipe(MotionEvent motionEvent) {
        return motionEvent.getClassification() == 4 || motionEvent.getClassification() == 3;
    }

    public static final boolean isNFingerTouchpadSwipe(MotionEvent motionEvent, int i) {
        return motionEvent.getClassification() == 4 && motionEvent.getAxisValue(53) == ((float) i);
    }
}
