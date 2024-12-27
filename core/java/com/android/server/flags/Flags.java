package com.android.server.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_DISABLE_SYSTEM_COMPACTION =
            "com.android.server.flags.disable_system_compaction";
    public static final String FLAG_ENABLE_ODP_FEATURE_GUARD =
            "com.android.server.flags.enable_odp_feature_guard";
    public static final String FLAG_NEW_BUGREPORT_KEYBOARD_SHORTCUT =
            "com.android.server.flags.new_bugreport_keyboard_shortcut";
    public static final String FLAG_PIN_WEBVIEW = "com.android.server.flags.pin_webview";
    public static final String FLAG_PKG_TARGETED_BATTERY_CHANGED_NOT_STICKY =
            "com.android.server.flags.pkg_targeted_battery_changed_not_sticky";
    public static final String FLAG_SKIP_HOME_ART_PINS =
            "com.android.server.flags.skip_home_art_pins";

    public static boolean disableSystemCompaction() {
        return FEATURE_FLAGS.disableSystemCompaction();
    }

    public static boolean enableOdpFeatureGuard() {
        return FEATURE_FLAGS.enableOdpFeatureGuard();
    }

    public static boolean newBugreportKeyboardShortcut() {
        return FEATURE_FLAGS.newBugreportKeyboardShortcut();
    }

    public static boolean pinWebview() {
        return FEATURE_FLAGS.pinWebview();
    }

    public static boolean pkgTargetedBatteryChangedNotSticky() {
        return FEATURE_FLAGS.pkgTargetedBatteryChangedNotSticky();
    }

    public static boolean skipHomeArtPins() {
        return FEATURE_FLAGS.skipHomeArtPins();
    }
}
