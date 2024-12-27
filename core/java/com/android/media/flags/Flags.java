package com.android.media.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ADJUST_VOLUME_FOR_FOREGROUND_APP_PLAYING_AUDIO_WITHOUT_MEDIA_SESSION = "com.android.media.flags.adjust_volume_for_foreground_app_playing_audio_without_media_session";
    public static final String FLAG_ENABLE_AUDIO_POLICIES_DEVICE_AND_BLUETOOTH_CONTROLLER = "com.android.media.flags.enable_audio_policies_device_and_bluetooth_controller";
    public static final String FLAG_ENABLE_BUILT_IN_SPEAKER_ROUTE_SUITABILITY_STATUSES = "com.android.media.flags.enable_built_in_speaker_route_suitability_statuses";
    public static final String FLAG_ENABLE_CROSS_USER_ROUTING_IN_MEDIA_ROUTER2 = "com.android.media.flags.enable_cross_user_routing_in_media_router2";
    public static final String FLAG_ENABLE_FULL_SCAN_WITH_MEDIA_CONTENT_CONTROL = "com.android.media.flags.enable_full_scan_with_media_content_control";
    public static final String FLAG_ENABLE_GET_TRANSFERABLE_ROUTES = "com.android.media.flags.enable_get_transferable_routes";
    public static final String FLAG_ENABLE_MR2_SERVICE_NON_MAIN_BG_THREAD = "com.android.media.flags.enable_mr2_service_non_main_bg_thread";
    public static final String FLAG_ENABLE_NEW_MEDIA_ROUTE_2_INFO_TYPES = "com.android.media.flags.enable_new_media_route_2_info_types";
    public static final String FLAG_ENABLE_NOTIFYING_ACTIVITY_MANAGER_WITH_MEDIA_SESSION_STATUS_CHANGE = "com.android.media.flags.enable_notifying_activity_manager_with_media_session_status_change";
    public static final String FLAG_ENABLE_NULL_SESSION_IN_MEDIA_BROWSER_SERVICE = "com.android.media.flags.enable_null_session_in_media_browser_service";
    public static final String FLAG_ENABLE_PREVENTION_OF_KEEP_ALIVE_ROUTE_PROVIDERS = "com.android.media.flags.enable_prevention_of_keep_alive_route_providers";
    public static final String FLAG_ENABLE_PREVENTION_OF_MANAGER_SCANS_WHEN_NO_APPS_SCAN = "com.android.media.flags.enable_prevention_of_manager_scans_when_no_apps_scan";
    public static final String FLAG_ENABLE_PRIVILEGED_ROUTING_FOR_MEDIA_ROUTING_CONTROL = "com.android.media.flags.enable_privileged_routing_for_media_routing_control";
    public static final String FLAG_ENABLE_RLP_CALLBACKS_IN_MEDIA_ROUTER2 = "com.android.media.flags.enable_rlp_callbacks_in_media_router2";
    public static final String FLAG_ENABLE_SCREEN_OFF_SCANNING = "com.android.media.flags.enable_screen_off_scanning";
    public static final String FLAG_ENABLE_USE_OF_BLUETOOTH_DEVICE_GET_ALIAS_FOR_MR2INFO_GET_NAME = "com.android.media.flags.enable_use_of_bluetooth_device_get_alias_for_mr2info_get_name";
    public static final String FLAG_ENABLE_WAITING_STATE_FOR_SYSTEM_SESSION_CREATION_REQUEST = "com.android.media.flags.enable_waiting_state_for_system_session_creation_request";
    public static final String FLAG_FALLBACK_TO_DEFAULT_HANDLING_WHEN_MEDIA_SESSION_HAS_FIXED_VOLUME_HANDLING = "com.android.media.flags.fallback_to_default_handling_when_media_session_has_fixed_volume_handling";

    public static boolean adjustVolumeForForegroundAppPlayingAudioWithoutMediaSession() {
        return FEATURE_FLAGS.adjustVolumeForForegroundAppPlayingAudioWithoutMediaSession();
    }

    public static boolean enableAudioPoliciesDeviceAndBluetoothController() {
        return FEATURE_FLAGS.enableAudioPoliciesDeviceAndBluetoothController();
    }

    public static boolean enableBuiltInSpeakerRouteSuitabilityStatuses() {
        return FEATURE_FLAGS.enableBuiltInSpeakerRouteSuitabilityStatuses();
    }

    public static boolean enableCrossUserRoutingInMediaRouter2() {
        return FEATURE_FLAGS.enableCrossUserRoutingInMediaRouter2();
    }

    public static boolean enableFullScanWithMediaContentControl() {
        return FEATURE_FLAGS.enableFullScanWithMediaContentControl();
    }

    public static boolean enableGetTransferableRoutes() {
        return FEATURE_FLAGS.enableGetTransferableRoutes();
    }

    public static boolean enableMr2ServiceNonMainBgThread() {
        return FEATURE_FLAGS.enableMr2ServiceNonMainBgThread();
    }

    public static boolean enableNewMediaRoute2InfoTypes() {
        return FEATURE_FLAGS.enableNewMediaRoute2InfoTypes();
    }

    public static boolean enableNotifyingActivityManagerWithMediaSessionStatusChange() {
        return FEATURE_FLAGS.enableNotifyingActivityManagerWithMediaSessionStatusChange();
    }

    public static boolean enableNullSessionInMediaBrowserService() {
        return FEATURE_FLAGS.enableNullSessionInMediaBrowserService();
    }

    public static boolean enablePreventionOfKeepAliveRouteProviders() {
        return FEATURE_FLAGS.enablePreventionOfKeepAliveRouteProviders();
    }

    public static boolean enablePreventionOfManagerScansWhenNoAppsScan() {
        return FEATURE_FLAGS.enablePreventionOfManagerScansWhenNoAppsScan();
    }

    public static boolean enablePrivilegedRoutingForMediaRoutingControl() {
        return FEATURE_FLAGS.enablePrivilegedRoutingForMediaRoutingControl();
    }

    public static boolean enableRlpCallbacksInMediaRouter2() {
        return FEATURE_FLAGS.enableRlpCallbacksInMediaRouter2();
    }

    public static boolean enableScreenOffScanning() {
        return FEATURE_FLAGS.enableScreenOffScanning();
    }

    public static boolean enableUseOfBluetoothDeviceGetAliasForMr2infoGetName() {
        return FEATURE_FLAGS.enableUseOfBluetoothDeviceGetAliasForMr2infoGetName();
    }

    public static boolean enableWaitingStateForSystemSessionCreationRequest() {
        return FEATURE_FLAGS.enableWaitingStateForSystemSessionCreationRequest();
    }

    public static boolean fallbackToDefaultHandlingWhenMediaSessionHasFixedVolumeHandling() {
        return FEATURE_FLAGS.fallbackToDefaultHandlingWhenMediaSessionHasFixedVolumeHandling();
    }
}
