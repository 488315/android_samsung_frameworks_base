package com.android.internal.hidden_from_bootclasspath.android.crashrecovery.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ALLOW_RESCUE_PARTY_FLAG_RESETS =
            "android.crashrecovery.flags.allow_rescue_party_flag_resets";
    public static final String FLAG_ENABLE_CRASHRECOVERY =
            "android.crashrecovery.flags.enable_crashrecovery";
    public static final String FLAG_RECOVERABILITY_DETECTION =
            "android.crashrecovery.flags.recoverability_detection";
    public static final String FLAG_REENABLE_SETTINGS_RESETS =
            "android.crashrecovery.flags.reenable_settings_resets";

    public static boolean allowRescuePartyFlagResets() {
        return FEATURE_FLAGS.allowRescuePartyFlagResets();
    }

    public static boolean enableCrashrecovery() {
        return FEATURE_FLAGS.enableCrashrecovery();
    }

    public static boolean recoverabilityDetection() {
        return FEATURE_FLAGS.recoverabilityDetection();
    }

    public static boolean reenableSettingsResets() {
        return FEATURE_FLAGS.reenableSettingsResets();
    }
}
