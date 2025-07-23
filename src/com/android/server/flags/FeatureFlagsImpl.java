package com.android.server.flags;

/* loaded from: classes6.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.server.flags.FeatureFlags
    public boolean certpininstallerRemoval() {
        return false;
    }

    @Override // com.android.server.flags.FeatureFlags
    public boolean consolidateBatteryChangeEvents() {
        return true;
    }

    @Override // com.android.server.flags.FeatureFlags
    public boolean datetimeNotifications() {
        return false;
    }

    @Override // com.android.server.flags.FeatureFlags
    public boolean disableSystemCompaction() {
        return true;
    }

    @Override // com.android.server.flags.FeatureFlags
    public boolean earlyDataManagerInit() {
        return true;
    }

    @Override // com.android.server.flags.FeatureFlags
    public boolean enableOdpFeatureGuard() {
        return true;
    }

    @Override // com.android.server.flags.FeatureFlags
    public boolean modifierShortcutManagerMultiuser() {
        return true;
    }

    @Override // com.android.server.flags.FeatureFlags
    public boolean newBugreportKeyboardShortcut() {
        return true;
    }

    @Override // com.android.server.flags.FeatureFlags
    public boolean optionalBackgroundInstallControl() {
        return true;
    }

    @Override // com.android.server.flags.FeatureFlags
    public boolean pinGlobalQuota() {
        return true;
    }

    @Override // com.android.server.flags.FeatureFlags
    public boolean pinWebview() {
        return true;
    }

    @Override // com.android.server.flags.FeatureFlags
    public boolean pkgTargetedBatteryChangedNotSticky() {
        return true;
    }

    @Override // com.android.server.flags.FeatureFlags
    public boolean rateLimitBatteryChangedBroadcast() {
        return true;
    }

    @Override // com.android.server.flags.FeatureFlags
    public boolean traceBatteryChangedBroadcastEvent() {
        return true;
    }
}
