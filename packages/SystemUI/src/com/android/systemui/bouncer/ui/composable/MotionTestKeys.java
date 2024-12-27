package com.android.systemui.bouncer.ui.composable;

import platform.test.motion.compose.values.MotionTestValueKey;

public final class MotionTestKeys {
    public static final MotionTestKeys INSTANCE = null;
    public static final MotionTestValueKey dotAppearFadeIn = null;
    public static final MotionTestValueKey dotAppearMoveUp = null;
    public static final MotionTestValueKey dotScaling = null;
    public static final MotionTestValueKey entryCompleted = null;

    static {
        new MotionTestKeys();
        new MotionTestValueKey("PinBouncer::entryAnimationCompleted");
        new MotionTestValueKey("PinBouncer::dotAppearFadeIn");
        new MotionTestValueKey("PinBouncer::dotAppearMoveUp");
        new MotionTestValueKey("PinBouncer::dotScaling");
    }

    private MotionTestKeys() {
    }
}
