package com.android.systemui.controls;

import android.graphics.drawable.Icon;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
