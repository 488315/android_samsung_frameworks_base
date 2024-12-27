package com.android.systemui.bouncer.ui.composable;

import platform.test.motion.compose.values.MotionTestValueKey;

public final class BouncerMotionTestKeys {
    public static final BouncerMotionTestKeys INSTANCE = null;
    public static final MotionTestValueKey swapAnimationEnd = null;

    static {
        new BouncerMotionTestKeys();
        new MotionTestValueKey("swapAnimationEnd");
    }

    private BouncerMotionTestKeys() {
    }
}
