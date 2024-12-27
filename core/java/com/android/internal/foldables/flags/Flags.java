package com.android.internal.foldables.flags;

public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_FOLD_GRACE_PERIOD_ENABLED =
            "com.android.internal.foldables.flags.fold_grace_period_enabled";
    public static final String FLAG_FOLD_LOCK_SETTING_ENABLED =
            "com.android.internal.foldables.flags.fold_lock_setting_enabled";

    public static boolean foldGracePeriodEnabled() {
        return FEATURE_FLAGS.foldGracePeriodEnabled();
    }

    public static boolean foldLockSettingEnabled() {
        return FEATURE_FLAGS.foldLockSettingEnabled();
    }
}
