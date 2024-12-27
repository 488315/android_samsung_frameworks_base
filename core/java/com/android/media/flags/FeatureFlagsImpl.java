package com.android.media.flags;

/* loaded from: classes5.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.media.flags.FeatureFlags
    public boolean adjustVolumeForForegroundAppPlayingAudioWithoutMediaSession() {
        return true;
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableAudioPoliciesDeviceAndBluetoothController() {
        return true;
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableBuiltInSpeakerRouteSuitabilityStatuses() {
        return true;
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableCrossUserRoutingInMediaRouter2() {
        return true;
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableFullScanWithMediaContentControl() {
        return true;
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableGetTransferableRoutes() {
        return true;
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableMr2ServiceNonMainBgThread() {
        return true;
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableNewMediaRoute2InfoTypes() {
        return true;
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableNotifyingActivityManagerWithMediaSessionStatusChange() {
        return false;
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableNullSessionInMediaBrowserService() {
        return true;
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enablePreventionOfKeepAliveRouteProviders() {
        return true;
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enablePreventionOfManagerScansWhenNoAppsScan() {
        return true;
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enablePrivilegedRoutingForMediaRoutingControl() {
        return true;
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableRlpCallbacksInMediaRouter2() {
        return true;
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableScreenOffScanning() {
        return true;
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableUseOfBluetoothDeviceGetAliasForMr2infoGetName() {
        return true;
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableWaitingStateForSystemSessionCreationRequest() {
        return true;
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean fallbackToDefaultHandlingWhenMediaSessionHasFixedVolumeHandling() {
        return true;
    }
}
