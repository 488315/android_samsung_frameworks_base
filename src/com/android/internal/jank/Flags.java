package com.android.internal.jank;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_IGNORE_HWUI_IS_FIRST_FRAME = "com.android.internal.jank.ignore_hwui_is_first_frame";
    public static final String FLAG_USE_SF_FRAME_DURATION = "com.android.internal.jank.use_sf_frame_duration";

    public static boolean ignoreHwuiIsFirstFrame() {
        return FEATURE_FLAGS.ignoreHwuiIsFirstFrame();
    }

    public static boolean useSfFrameDuration() {
        return FEATURE_FLAGS.useSfFrameDuration();
    }
}
