package com.android.media.flags;

/* loaded from: classes6.dex */
public interface FeatureFlags {
    boolean adjustVolumeForForegroundAppPlayingAudioWithoutMediaSession();

    boolean disableSetBluetoothAd2pOnCalls();

    boolean disableTransferWhenAppsDoNotSupport();

    boolean enableAudioInputDeviceRoutingAndVolumeControl();

    boolean enableAudioPoliciesDeviceAndBluetoothController();

    boolean enableBuiltInSpeakerRouteSuitabilityStatuses();

    boolean enableCrossUserRoutingInMediaRouter2();

    boolean enableFixForEmptySystemRoutesCrash();

    boolean enableFullScanWithMediaContentControl();

    boolean enableGetTransferableRoutes();

    boolean enableMediaRoute2InfoProviderPackageName();

    boolean enableMirroringInMediaRouter2();

    boolean enableMr2ServiceNonMainBgThread();

    boolean enableNewMediaRoute2InfoTypes();

    boolean enableNewWiredMediaRoute2InfoTypes();

    boolean enableNotifyingActivityManagerWithMediaSessionStatusChange();

    boolean enableNullSessionInMediaBrowserService();

    boolean enableOutputSwitcherDeviceGrouping();

    boolean enableOutputSwitcherPersonalAudioSharing();

    boolean enableOutputSwitcherRedesign();

    boolean enableOutputSwitcherSessionGrouping();

    boolean enablePreventionOfKeepAliveRouteProviders();

    boolean enablePreventionOfManagerScansWhenNoAppsScan();

    boolean enablePrivilegedRoutingForMediaRoutingControl();

    boolean enableRlpCallbacksInMediaRouter2();

    boolean enableRouteVisibilityControlApi();

    boolean enableScreenOffScanning();

    boolean enableSuggestedDeviceApi();

    boolean enableUseOfBluetoothDeviceGetAliasForMr2infoGetName();

    boolean enableUseOfSingletonAudioManagerRouteController();

    boolean enableWaitingStateForSystemSessionCreationRequest();

    boolean fallbackToDefaultHandlingWhenMediaSessionHasFixedVolumeHandling();

    boolean fixOutputMediaItemListIndexOutOfBoundsException();
}
