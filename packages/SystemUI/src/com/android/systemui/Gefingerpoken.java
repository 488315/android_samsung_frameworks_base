package com.android.systemui;

import android.view.MotionEvent;

public interface Gefingerpoken {
    default boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    boolean onTouchEvent(MotionEvent motionEvent);
}
