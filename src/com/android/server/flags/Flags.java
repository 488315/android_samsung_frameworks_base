package com.android.server.flags;

/* loaded from: classes6.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_CERTPININSTALLER_REMOVAL = "com.android.server.flags.certpininstaller_removal";
    public static final String FLAG_CONSOLIDATE_BATTERY_CHANGE_EVENTS = "com.android.server.flags.consolidate_battery_change_events";
    public static final String FLAG_DATETIME_NOTIFICATIONS = "com.android.server.flags.datetime_notifications";
    public static final String FLAG_DISABLE_SYSTEM_COMPACTION = "com.android.server.flags.disable_system_compaction";
    public static final String FLAG_EARLY_DATA_MANAGER_INIT = "com.android.server.flags.early_data_manager_init";
    public static final String FLAG_ENABLE_ODP_FEATURE_GUARD = "com.android.server.flags.enable_odp_feature_guard";
    public static final String FLAG_MODIFIER_SHORTCUT_MANAGER_MULTIUSER = "com.android.server.flags.modifier_shortcut_manager_multiuser";
    public static final String FLAG_NEW_BUGREPORT_KEYBOARD_SHORTCUT = "com.android.server.flags.new_bugreport_keyboard_shortcut";
    public static final String FLAG_OPTIONAL_BACKGROUND_INSTALL_CONTROL = "com.android.server.flags.optional_background_install_control";
    public static final String FLAG_PIN_GLOBAL_QUOTA = "com.android.server.flags.pin_global_quota";
    public static final String FLAG_PIN_WEBVIEW = "com.android.server.flags.pin_webview";
    public static final String FLAG_PKG_TARGETED_BATTERY_CHANGED_NOT_STICKY = "com.android.server.flags.pkg_targeted_battery_changed_not_sticky";
    public static final String FLAG_RATE_LIMIT_BATTERY_CHANGED_BROADCAST = "com.android.server.flags.rate_limit_battery_changed_broadcast";
    public static final String FLAG_TRACE_BATTERY_CHANGED_BROADCAST_EVENT = "com.android.server.flags.trace_battery_changed_broadcast_event";

    public static boolean certpininstallerRemoval() {
        return FEATURE_FLAGS.certpininstallerRemoval();
    }

    public static boolean consolidateBatteryChangeEvents() {
        return FEATURE_FLAGS.consolidateBatteryChangeEvents();
    }

    public static boolean datetimeNotifications() {
        return FEATURE_FLAGS.datetimeNotifications();
    }

    public static boolean disableSystemCompaction() {
        return FEATURE_FLAGS.disableSystemCompaction();
    }

    public static boolean earlyDataManagerInit() {
        return FEATURE_FLAGS.earlyDataManagerInit();
    }

    public static boolean enableOdpFeatureGuard() {
        return FEATURE_FLAGS.enableOdpFeatureGuard();
    }

    public static boolean modifierShortcutManagerMultiuser() {
        return FEATURE_FLAGS.modifierShortcutManagerMultiuser();
    }

    public static boolean newBugreportKeyboardShortcut() {
        return FEATURE_FLAGS.newBugreportKeyboardShortcut();
    }

    public static boolean optionalBackgroundInstallControl() {
        return FEATURE_FLAGS.optionalBackgroundInstallControl();
    }

    public static boolean pinGlobalQuota() {
        return FEATURE_FLAGS.pinGlobalQuota();
    }

    public static boolean pinWebview() {
        return FEATURE_FLAGS.pinWebview();
    }

    public static boolean pkgTargetedBatteryChangedNotSticky() {
        return FEATURE_FLAGS.pkgTargetedBatteryChangedNotSticky();
    }

    public static boolean rateLimitBatteryChangedBroadcast() {
        return FEATURE_FLAGS.rateLimitBatteryChangedBroadcast();
    }

    public static boolean traceBatteryChangedBroadcastEvent() {
        return FEATURE_FLAGS.traceBatteryChangedBroadcastEvent();
    }
}
