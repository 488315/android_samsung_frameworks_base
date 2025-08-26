package com.android.systemui.bouncer.ui.composable;

import platform.test.motion.compose.values.MotionTestValueKey;

/* loaded from: classes.dex */
public final class MotionTestKeys {
    public static final MotionTestKeys INSTANCE = new MotionTestKeys();
    public static final MotionTestValueKey entryCompleted = new MotionTestValueKey("PinBouncer::entryAnimationCompleted");
    public static final MotionTestValueKey dotAppearFadeIn = new MotionTestValueKey("PinBouncer::dotAppearFadeIn");
    public static final MotionTestValueKey dotAppearMoveUp = new MotionTestValueKey("PinBouncer::dotAppearMoveUp");
    public static final MotionTestValueKey dotScaling = new MotionTestValueKey("PinBouncer::dotScaling");

    private MotionTestKeys() {
    }
}
