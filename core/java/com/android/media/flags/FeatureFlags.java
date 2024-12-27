package com.android.media.flags;

/* loaded from: classes5.dex */
public interface FeatureFlags {
    boolean adjustVolumeForForegroundAppPlayingAudioWithoutMediaSession();

    boolean enableAudioPoliciesDeviceAndBluetoothController();

    boolean enableBuiltInSpeakerRouteSuitabilityStatuses();

    boolean enableCrossUserRoutingInMediaRouter2();

    boolean enableFullScanWithMediaContentControl();

    boolean enableGetTransferableRoutes();

    boolean enableMr2ServiceNonMainBgThread();

    boolean enableNewMediaRoute2InfoTypes();

    boolean enableNotifyingActivityManagerWithMediaSessionStatusChange();

    boolean enableNullSessionInMediaBrowserService();

    boolean enablePreventionOfKeepAliveRouteProviders();

    boolean enablePreventionOfManagerScansWhenNoAppsScan();

    boolean enablePrivilegedRoutingForMediaRoutingControl();

    boolean enableRlpCallbacksInMediaRouter2();

    boolean enableScreenOffScanning();

    boolean enableUseOfBluetoothDeviceGetAliasForMr2infoGetName();

    boolean enableWaitingStateForSystemSessionCreationRequest();

    boolean fallbackToDefaultHandlingWhenMediaSessionHasFixedVolumeHandling();
}
