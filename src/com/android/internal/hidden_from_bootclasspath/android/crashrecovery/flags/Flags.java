package com.android.internal.hidden_from_bootclasspath.android.crashrecovery.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ALLOW_RESCUE_PARTY_FLAG_RESETS = "android.crashrecovery.flags.allow_rescue_party_flag_resets";
    public static final String FLAG_DEPRECATE_FLAGS_AND_SETTINGS_RESETS = "android.crashrecovery.flags.deprecate_flags_and_settings_resets";
    public static final String FLAG_ENABLE_CRASHRECOVERY = "android.crashrecovery.flags.enable_crashrecovery";
    public static final String FLAG_RECOVERABILITY_DETECTION = "android.crashrecovery.flags.recoverability_detection";
    public static final String FLAG_REFACTOR_CRASHRECOVERY = "android.crashrecovery.flags.refactor_crashrecovery";
    public static final String FLAG_SYNCHRONOUS_REBOOT_IN_RESCUE_PARTY = "android.crashrecovery.flags.synchronous_reboot_in_rescue_party";

    public static boolean allowRescuePartyFlagResets() {
        return FEATURE_FLAGS.allowRescuePartyFlagResets();
    }

    public static boolean deprecateFlagsAndSettingsResets() {
        return FEATURE_FLAGS.deprecateFlagsAndSettingsResets();
    }

    public static boolean enableCrashrecovery() {
        return FEATURE_FLAGS.enableCrashrecovery();
    }

    public static boolean recoverabilityDetection() {
        return FEATURE_FLAGS.recoverabilityDetection();
    }

    public static boolean refactorCrashrecovery() {
        return FEATURE_FLAGS.refactorCrashrecovery();
    }

    public static boolean synchronousRebootInRescueParty() {
        return FEATURE_FLAGS.synchronousRebootInRescueParty();
    }
}
