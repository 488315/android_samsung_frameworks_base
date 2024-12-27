package com.android.systemui;

import android.view.MotionEvent;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public interface Gefingerpoken {
    default boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    boolean onTouchEvent(MotionEvent motionEvent);
}
