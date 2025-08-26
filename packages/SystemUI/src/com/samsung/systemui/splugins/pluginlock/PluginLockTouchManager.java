package com.samsung.systemui.splugins.pluginlock;

import android.view.MotionEvent;

/* loaded from: classes4.dex */
public interface PluginLockTouchManager {
    boolean isIntercepting();

    boolean isTouchOnItemViewArea(MotionEvent motionEvent);

    boolean onAnimatorTouchEvent(MotionEvent motionEvent);

    boolean onTouchEvent(MotionEvent motionEvent);

    void setFullscreenMode(boolean z);

    void setIntercept(boolean z);
}
