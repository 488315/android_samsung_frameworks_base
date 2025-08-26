package com.android.systemui;

import android.view.MotionEvent;

/* loaded from: classes.dex */
public interface Gefingerpoken {
    default boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return false;
    }
}
