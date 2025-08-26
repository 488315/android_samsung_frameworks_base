package com.android.systemui.animation;

import android.graphics.Rect;

/* loaded from: classes.dex */
public interface LaunchableView {
    default Rect getPaddingForLaunchAnimation() {
        return new Rect();
    }

    void setShouldBlockVisibilityChanges(boolean z);

    default void onActivityLaunchAnimationEnd() {
    }
}
