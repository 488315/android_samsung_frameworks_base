package com.android.server.flags;

/* loaded from: classes6.dex */
public interface FeatureFlags {
    boolean certpininstallerRemoval();

    boolean consolidateBatteryChangeEvents();

    boolean datetimeNotifications();

    boolean disableSystemCompaction();

    boolean earlyDataManagerInit();

    boolean enableOdpFeatureGuard();

    boolean modifierShortcutManagerMultiuser();

    boolean newBugreportKeyboardShortcut();

    boolean optionalBackgroundInstallControl();

    boolean pinGlobalQuota();

    boolean pinWebview();

    boolean pkgTargetedBatteryChangedNotSticky();

    boolean rateLimitBatteryChangedBroadcast();

    boolean traceBatteryChangedBroadcastEvent();
}
