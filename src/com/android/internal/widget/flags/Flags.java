package com.android.internal.widget.flags;

/* loaded from: classes6.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_HIDE_LAST_CHAR_WITH_PHYSICAL_INPUT = "com.android.internal.widget.flags.hide_last_char_with_physical_input";

    public static boolean hideLastCharWithPhysicalInput() {
        return FEATURE_FLAGS.hideLastCharWithPhysicalInput();
    }
}
