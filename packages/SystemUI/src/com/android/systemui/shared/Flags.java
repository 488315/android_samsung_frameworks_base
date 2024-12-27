package com.android.systemui.shared;

public final class Flags {
    public static final FeatureFlagsImpl FEATURE_FLAGS = new FeatureFlagsImpl();

    public static void returnAnimationFrameworkLibrary() {
        FEATURE_FLAGS.getClass();
    }
}
