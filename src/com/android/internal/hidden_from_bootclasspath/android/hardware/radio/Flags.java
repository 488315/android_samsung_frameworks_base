package com.android.internal.hidden_from_bootclasspath.android.hardware.radio;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_HD_RADIO_EMERGENCY_ALERT_SYSTEM = "android.hardware.radio.hd_radio_emergency_alert_system";
    public static final String FLAG_HD_RADIO_IMPROVED = "android.hardware.radio.hd_radio_improved";

    public static boolean hdRadioEmergencyAlertSystem() {
        return FEATURE_FLAGS.hdRadioEmergencyAlertSystem();
    }

    public static boolean hdRadioImproved() {
        return FEATURE_FLAGS.hdRadioImproved();
    }
}
