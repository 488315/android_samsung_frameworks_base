package com.android.systemui.statusbar.events;

import androidx.core.animation.PathInterpolator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class SystemStatusAnimationSchedulerKt {
    public static final PathInterpolator STATUS_BAR_X_MOVE_OUT = new PathInterpolator(0.33f, 0.0f, 0.0f, 1.0f);
    public static final PathInterpolator STATUS_BAR_X_MOVE_IN = new PathInterpolator(0.0f, 0.0f, 0.0f, 1.0f);
    public static final PathInterpolator STATUS_CHIP_WIDTH_TO_DOT_KEYFRAME_1 = new PathInterpolator(0.44f, 0.0f, 0.25f, 1.0f);
    public static final PathInterpolator STATUS_CHIP_WIDTH_TO_DOT_KEYFRAME_2 = new PathInterpolator(0.3f, 0.0f, 0.26f, 1.0f);
    public static final PathInterpolator STATUS_CHIP_HEIGHT_TO_DOT_KEYFRAME_1 = new PathInterpolator(0.4f, 0.0f, 0.17f, 1.0f);
    public static final PathInterpolator STATUS_CHIP_HEIGHT_TO_DOT_KEYFRAME_2 = new PathInterpolator(0.3f, 0.0f, 0.0f, 1.0f);
    public static final PathInterpolator STATUS_CHIP_MOVE_TO_DOT = new PathInterpolator(0.0f, 0.0f, 0.05f, 1.0f);
    public static final long DEBOUNCE_DELAY = 500;
    public static final long DISPLAY_LENGTH = 1500;
}
