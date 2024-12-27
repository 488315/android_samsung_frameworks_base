package com.android.systemui.controls;

import android.graphics.drawable.Icon;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface SecControlInterface {
    boolean getIconWithoutPadding();

    boolean getIconWithoutShadowBg();

    int getLottieIconAnimationEndFrame();

    String getLottieIconAnimationJson();

    String getLottieIconAnimationJsonCache();

    int getLottieIconAnimationRepeatCount();

    int getLottieIconAnimationStartFrame();

    Icon getOverlayCustomIcon();
}
