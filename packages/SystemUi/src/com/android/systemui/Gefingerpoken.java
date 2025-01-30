package com.android.systemui;

import android.view.MotionEvent;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface Gefingerpoken {
    default boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    boolean onTouchEvent(MotionEvent motionEvent);
}
