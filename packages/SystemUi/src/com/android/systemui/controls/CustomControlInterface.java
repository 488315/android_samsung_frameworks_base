package com.android.systemui.controls;

import android.content.res.ColorStateList;
import android.graphics.drawable.Icon;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface CustomControlInterface {
    ColorStateList getCustomColor();

    int getCustomIconAnimationEndFrame();

    String getCustomIconAnimationJson();

    String getCustomIconAnimationJsonCache();

    int getCustomIconAnimationRepeatCount();

    int getCustomIconAnimationStartFrame();

    Icon getOverlayCustomIcon();

    boolean getUseCustomIconWithoutPadding();

    boolean getUseCustomIconWithoutShadowBg();
}
