package com.android.systemui.controls;

import android.graphics.drawable.Icon;

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
