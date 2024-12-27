package com.android.server.flags;

public interface FeatureFlags {
    boolean disableSystemCompaction();

    boolean enableOdpFeatureGuard();

    boolean newBugreportKeyboardShortcut();

    boolean pinWebview();

    boolean pkgTargetedBatteryChangedNotSticky();

    boolean skipHomeArtPins();
}
