package com.android.internal.hidden_from_bootclasspath.android.hardware.radio;

public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_HD_RADIO_IMPROVED = "android.hardware.radio.hd_radio_improved";

    public static boolean hdRadioImproved() {
        return FEATURE_FLAGS.hdRadioImproved();
    }
}
