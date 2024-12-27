package com.android.systemui.bouncer.ui.composable;

import platform.test.motion.compose.values.MotionTestValueKey;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
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
