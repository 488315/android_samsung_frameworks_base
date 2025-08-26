package com.android.systemui.brightness.ui.compose;

import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.TweenSpec;

/* loaded from: classes.dex */
public final class AnimationSpecs {
    public static final AnimationSpecs INSTANCE = new AnimationSpecs();
    public static final TweenSpec IconAppearSpec = AnimationSpecKt.tween$default(100, 33, null, 4);
    public static final TweenSpec IconDisappearSpec = AnimationSpecKt.tween$default(50, 0, null, 6);

    private AnimationSpecs() {
    }
}
