package com.android.systemui.navigationbar.views.buttons;

import android.graphics.drawable.Drawable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
