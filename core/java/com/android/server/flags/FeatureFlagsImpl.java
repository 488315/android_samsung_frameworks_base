package com.android.server.flags;

/* loaded from: classes5.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.server.flags.FeatureFlags
    public boolean disableSystemCompaction() {
        return true;
    }

    @Override // com.android.server.flags.FeatureFlags
    public boolean enableOdpFeatureGuard() {
        return true;
    }

    @Override // com.android.server.flags.FeatureFlags
    public boolean newBugreportKeyboardShortcut() {
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
    public boolean skipHomeArtPins() {
        return false;
    }
}
