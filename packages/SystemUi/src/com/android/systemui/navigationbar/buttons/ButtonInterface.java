package com.android.systemui.navigationbar.buttons;

import android.graphics.drawable.Drawable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface ButtonInterface {
    void abortCurrentGesture();

    void setDarkIntensity(float f);

    void setImageDrawable(Drawable drawable);

    void setVertical();

    default void abortCurrentGestureByA11yGesture(boolean z) {
    }

    default void setCurrentRotation(int i, boolean z) {
    }
}
