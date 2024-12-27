package com.android.systemui.navigationbar.buttons;

import android.graphics.drawable.Drawable;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
