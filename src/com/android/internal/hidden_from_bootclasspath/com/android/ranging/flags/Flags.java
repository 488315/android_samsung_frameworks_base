package com.android.internal.hidden_from_bootclasspath.com.android.ranging.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_RANGING_CS_ENABLED = "com.android.ranging.flags.ranging_cs_enabled";
    public static final String FLAG_RANGING_RTT_ENABLED = "com.android.ranging.flags.ranging_rtt_enabled";
    public static final String FLAG_RANGING_STACK_ENABLED = "com.android.ranging.flags.ranging_stack_enabled";
    public static final String FLAG_RANGING_STACK_UPDATES_25Q4 = "com.android.ranging.flags.ranging_stack_updates_25q4";

    public static boolean rangingCsEnabled() {
        return true;
    }

    public static boolean rangingStackEnabled() {
        return true;
    }

    public static boolean rangingRttEnabled() {
        return FEATURE_FLAGS.rangingRttEnabled();
    }

    public static boolean rangingStackUpdates25q4() {
        return FEATURE_FLAGS.rangingStackUpdates25q4();
    }
}
