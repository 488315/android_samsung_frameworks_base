package com.samsung.systemui.splugins.pluginlock;

import android.view.MotionEvent;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface PluginLockTouchManager {
    boolean isIntercepting();

    boolean isTouchOnItemViewArea(MotionEvent motionEvent);

    boolean onAnimatorTouchEvent(MotionEvent motionEvent);

    boolean onTouchEvent(MotionEvent motionEvent);

    void setFullscreenMode(boolean z);

    void setIntercept(boolean z);
}
