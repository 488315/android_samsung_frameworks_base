package com.android.systemui.navigationbar.views.buttons;

import android.graphics.drawable.Drawable;

/* loaded from: classes2.dex */
public interface ButtonInterface {
    void abortCurrentGesture();

    void setDarkIntensity(float f);

    void setImageDrawable(Drawable drawable);

    default void abortCurrentGestureByA11yGesture(boolean z) {
    }

    default void setCurrentRotation(int i, boolean z) {
    }

    default void animateLongPress(boolean z, boolean z2, long j) {
    }
}
