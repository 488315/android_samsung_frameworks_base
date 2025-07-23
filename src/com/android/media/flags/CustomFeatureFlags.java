package com.android.media.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes6.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_ADJUST_VOLUME_FOR_FOREGROUND_APP_PLAYING_AUDIO_WITHOUT_MEDIA_SESSION, Flags.FLAG_DISABLE_SET_BLUETOOTH_AD2P_ON_CALLS, Flags.FLAG_DISABLE_TRANSFER_WHEN_APPS_DO_NOT_SUPPORT, Flags.FLAG_ENABLE_AUDIO_INPUT_DEVICE_ROUTING_AND_VOLUME_CONTROL, Flags.FLAG_ENABLE_AUDIO_POLICIES_DEVICE_AND_BLUETOOTH_CONTROLLER, Flags.FLAG_ENABLE_BUILT_IN_SPEAKER_ROUTE_SUITABILITY_STATUSES, Flags.FLAG_ENABLE_CROSS_USER_ROUTING_IN_MEDIA_ROUTER2, Flags.FLAG_ENABLE_FIX_FOR_EMPTY_SYSTEM_ROUTES_CRASH, Flags.FLAG_ENABLE_FULL_SCAN_WITH_MEDIA_CONTENT_CONTROL, Flags.FLAG_ENABLE_GET_TRANSFERABLE_ROUTES, Flags.FLAG_ENABLE_MEDIA_ROUTE_2_INFO_PROVIDER_PACKAGE_NAME, Flags.FLAG_ENABLE_MIRRORING_IN_MEDIA_ROUTER_2, Flags.FLAG_ENABLE_MR2_SERVICE_NON_MAIN_BG_THREAD, Flags.FLAG_ENABLE_NEW_MEDIA_ROUTE_2_INFO_TYPES, Flags.FLAG_ENABLE_NEW_WIRED_MEDIA_ROUTE_2_INFO_TYPES, Flags.FLAG_ENABLE_NOTIFYING_ACTIVITY_MANAGER_WITH_MEDIA_SESSION_STATUS_CHANGE, Flags.FLAG_ENABLE_NULL_SESSION_IN_MEDIA_BROWSER_SERVICE, Flags.FLAG_ENABLE_OUTPUT_SWITCHER_DEVICE_GROUPING, Flags.FLAG_ENABLE_OUTPUT_SWITCHER_PERSONAL_AUDIO_SHARING, Flags.FLAG_ENABLE_OUTPUT_SWITCHER_REDESIGN, Flags.FLAG_ENABLE_OUTPUT_SWITCHER_SESSION_GROUPING, Flags.FLAG_ENABLE_PREVENTION_OF_KEEP_ALIVE_ROUTE_PROVIDERS, Flags.FLAG_ENABLE_PREVENTION_OF_MANAGER_SCANS_WHEN_NO_APPS_SCAN, Flags.FLAG_ENABLE_PRIVILEGED_ROUTING_FOR_MEDIA_ROUTING_CONTROL, Flags.FLAG_ENABLE_RLP_CALLBACKS_IN_MEDIA_ROUTER2, Flags.FLAG_ENABLE_ROUTE_VISIBILITY_CONTROL_API, Flags.FLAG_ENABLE_SCREEN_OFF_SCANNING, Flags.FLAG_ENABLE_SUGGESTED_DEVICE_API, Flags.FLAG_ENABLE_USE_OF_BLUETOOTH_DEVICE_GET_ALIAS_FOR_MR2INFO_GET_NAME, Flags.FLAG_ENABLE_USE_OF_SINGLETON_AUDIO_MANAGER_ROUTE_CONTROLLER, Flags.FLAG_ENABLE_WAITING_STATE_FOR_SYSTEM_SESSION_CREATION_REQUEST, Flags.FLAG_FALLBACK_TO_DEFAULT_HANDLING_WHEN_MEDIA_SESSION_HAS_FIXED_VOLUME_HANDLING, Flags.FLAG_FIX_OUTPUT_MEDIA_ITEM_LIST_INDEX_OUT_OF_BOUNDS_EXCEPTION, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean adjustVolumeForForegroundAppPlayingAudioWithoutMediaSession() {
        return getValue(Flags.FLAG_ADJUST_VOLUME_FOR_FOREGROUND_APP_PLAYING_AUDIO_WITHOUT_MEDIA_SESSION, new Predicate() { // from class: com.android.media.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).adjustVolumeForForegroundAppPlayingAudioWithoutMediaSession();
            }
        });
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean disableSetBluetoothAd2pOnCalls() {
        return getValue(Flags.FLAG_DISABLE_SET_BLUETOOTH_AD2P_ON_CALLS, new Predicate() { // from class: com.android.media.flags.CustomFeatureFlags$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).disableSetBluetoothAd2pOnCalls();
            }
        });
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean disableTransferWhenAppsDoNotSupport() {
        return getValue(Flags.FLAG_DISABLE_TRANSFER_WHEN_APPS_DO_NOT_SUPPORT, new Predicate() { // from class: com.android.media.flags.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).disableTransferWhenAppsDoNotSupport();
            }
        });
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableAudioInputDeviceRoutingAndVolumeControl() {
        return getValue(Flags.FLAG_ENABLE_AUDIO_INPUT_DEVICE_ROUTING_AND_VOLUME_CONTROL, new Predicate() { // from class: com.android.media.flags.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableAudioInputDeviceRoutingAndVolumeControl();
            }
        });
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableAudioPoliciesDeviceAndBluetoothController() {
        return getValue(Flags.FLAG_ENABLE_AUDIO_POLICIES_DEVICE_AND_BLUETOOTH_CONTROLLER, new Predicate() { // from class: com.android.media.flags.CustomFeatureFlags$$ExternalSyntheticLambda24
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableAudioPoliciesDeviceAndBluetoothController();
            }
        });
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableBuiltInSpeakerRouteSuitabilityStatuses() {
        return getValue(Flags.FLAG_ENABLE_BUILT_IN_SPEAKER_ROUTE_SUITABILITY_STATUSES, new Predicate() { // from class: com.android.media.flags.CustomFeatureFlags$$ExternalSyntheticLambda22
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableBuiltInSpeakerRouteSuitabilityStatuses();
            }
        });
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableCrossUserRoutingInMediaRouter2() {
        return getValue(Flags.FLAG_ENABLE_CROSS_USER_ROUTING_IN_MEDIA_ROUTER2, new Predicate() { // from class: com.android.media.flags.CustomFeatureFlags$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableCrossUserRoutingInMediaRouter2();
            }
        });
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableFixForEmptySystemRoutesCrash() {
        return getValue(Flags.FLAG_ENABLE_FIX_FOR_EMPTY_SYSTEM_ROUTES_CRASH, new Predicate() { // from class: com.android.media.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableFixForEmptySystemRoutesCrash();
            }
        });
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableFullScanWithMediaContentControl() {
        return getValue(Flags.FLAG_ENABLE_FULL_SCAN_WITH_MEDIA_CONTENT_CONTROL, new Predicate() { // from class: com.android.media.flags.CustomFeatureFlags$$ExternalSyntheticLambda26
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableFullScanWithMediaContentControl();
            }
        });
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableGetTransferableRoutes() {
        return getValue(Flags.FLAG_ENABLE_GET_TRANSFERABLE_ROUTES, new Predicate() { // from class: com.android.media.flags.CustomFeatureFlags$$ExternalSyntheticLambda28
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableGetTransferableRoutes();
            }
        });
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableMediaRoute2InfoProviderPackageName() {
        return getValue(Flags.FLAG_ENABLE_MEDIA_ROUTE_2_INFO_PROVIDER_PACKAGE_NAME, new Predicate() { // from class: com.android.media.flags.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableMediaRoute2InfoProviderPackageName();
            }
        });
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableMirroringInMediaRouter2() {
        return getValue(Flags.FLAG_ENABLE_MIRRORING_IN_MEDIA_ROUTER_2, new Predicate() { // from class: com.android.media.flags.CustomFeatureFlags$$ExternalSyntheticLambda25
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableMirroringInMediaRouter2();
            }
        });
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableMr2ServiceNonMainBgThread() {
        return getValue(Flags.FLAG_ENABLE_MR2_SERVICE_NON_MAIN_BG_THREAD, new Predicate() { // from class: com.android.media.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableMr2ServiceNonMainBgThread();
            }
        });
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableNewMediaRoute2InfoTypes() {
        return getValue(Flags.FLAG_ENABLE_NEW_MEDIA_ROUTE_2_INFO_TYPES, new Predicate() { // from class: com.android.media.flags.CustomFeatureFlags$$ExternalSyntheticLambda18
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableNewMediaRoute2InfoTypes();
            }
        });
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableNewWiredMediaRoute2InfoTypes() {
        return getValue(Flags.FLAG_ENABLE_NEW_WIRED_MEDIA_ROUTE_2_INFO_TYPES, new Predicate() { // from class: com.android.media.flags.CustomFeatureFlags$$ExternalSyntheticLambda19
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableNewWiredMediaRoute2InfoTypes();
            }
        });
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableNotifyingActivityManagerWithMediaSessionStatusChange() {
        return getValue(Flags.FLAG_ENABLE_NOTIFYING_ACTIVITY_MANAGER_WITH_MEDIA_SESSION_STATUS_CHANGE, new Predicate() { // from class: com.android.media.flags.CustomFeatureFlags$$ExternalSyntheticLambda20
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableNotifyingActivityManagerWithMediaSessionStatusChange();
            }
        });
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableNullSessionInMediaBrowserService() {
        return getValue(Flags.FLAG_ENABLE_NULL_SESSION_IN_MEDIA_BROWSER_SERVICE, new Predicate() { // from class: com.android.media.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableNullSessionInMediaBrowserService();
            }
        });
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableOutputSwitcherDeviceGrouping() {
        return getValue(Flags.FLAG_ENABLE_OUTPUT_SWITCHER_DEVICE_GROUPING, new Predicate() { // from class: com.android.media.flags.CustomFeatureFlags$$ExternalSyntheticLambda31
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableOutputSwitcherDeviceGrouping();
            }
        });
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableOutputSwitcherPersonalAudioSharing() {
        return getValue(Flags.FLAG_ENABLE_OUTPUT_SWITCHER_PERSONAL_AUDIO_SHARING, new Predicate() { // from class: com.android.media.flags.CustomFeatureFlags$$ExternalSyntheticLambda32
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableOutputSwitcherPersonalAudioSharing();
            }
        });
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableOutputSwitcherRedesign() {
        return getValue(Flags.FLAG_ENABLE_OUTPUT_SWITCHER_REDESIGN, new Predicate() { // from class: com.android.media.flags.CustomFeatureFlags$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableOutputSwitcherRedesign();
            }
        });
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableOutputSwitcherSessionGrouping() {
        return getValue(Flags.FLAG_ENABLE_OUTPUT_SWITCHER_SESSION_GROUPING, new Predicate() { // from class: com.android.media.flags.CustomFeatureFlags$$ExternalSyntheticLambda29
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableOutputSwitcherSessionGrouping();
            }
        });
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enablePreventionOfKeepAliveRouteProviders() {
        return getValue(Flags.FLAG_ENABLE_PREVENTION_OF_KEEP_ALIVE_ROUTE_PROVIDERS, new Predicate() { // from class: com.android.media.flags.CustomFeatureFlags$$ExternalSyntheticLambda21
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enablePreventionOfKeepAliveRouteProviders();
            }
        });
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enablePreventionOfManagerScansWhenNoAppsScan() {
        return getValue(Flags.FLAG_ENABLE_PREVENTION_OF_MANAGER_SCANS_WHEN_NO_APPS_SCAN, new Predicate() { // from class: com.android.media.flags.CustomFeatureFlags$$ExternalSyntheticLambda30
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enablePreventionOfManagerScansWhenNoAppsScan();
            }
        });
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enablePrivilegedRoutingForMediaRoutingControl() {
        return getValue(Flags.FLAG_ENABLE_PRIVILEGED_ROUTING_FOR_MEDIA_ROUTING_CONTROL, new Predicate() { // from class: com.android.media.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enablePrivilegedRoutingForMediaRoutingControl();
            }
        });
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableRlpCallbacksInMediaRouter2() {
        return getValue(Flags.FLAG_ENABLE_RLP_CALLBACKS_IN_MEDIA_ROUTER2, new Predicate() { // from class: com.android.media.flags.CustomFeatureFlags$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableRlpCallbacksInMediaRouter2();
            }
        });
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableRouteVisibilityControlApi() {
        return getValue(Flags.FLAG_ENABLE_ROUTE_VISIBILITY_CONTROL_API, new Predicate() { // from class: com.android.media.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableRouteVisibilityControlApi();
            }
        });
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableScreenOffScanning() {
        return getValue(Flags.FLAG_ENABLE_SCREEN_OFF_SCANNING, new Predicate() { // from class: com.android.media.flags.CustomFeatureFlags$$ExternalSyntheticLambda27
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableScreenOffScanning();
            }
        });
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableSuggestedDeviceApi() {
        return getValue(Flags.FLAG_ENABLE_SUGGESTED_DEVICE_API, new Predicate() { // from class: com.android.media.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableSuggestedDeviceApi();
            }
        });
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableUseOfBluetoothDeviceGetAliasForMr2infoGetName() {
        return getValue(Flags.FLAG_ENABLE_USE_OF_BLUETOOTH_DEVICE_GET_ALIAS_FOR_MR2INFO_GET_NAME, new Predicate() { // from class: com.android.media.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableUseOfBluetoothDeviceGetAliasForMr2infoGetName();
            }
        });
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableUseOfSingletonAudioManagerRouteController() {
        return getValue(Flags.FLAG_ENABLE_USE_OF_SINGLETON_AUDIO_MANAGER_ROUTE_CONTROLLER, new Predicate() { // from class: com.android.media.flags.CustomFeatureFlags$$ExternalSyntheticLambda23
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableUseOfSingletonAudioManagerRouteController();
            }
        });
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableWaitingStateForSystemSessionCreationRequest() {
        return getValue(Flags.FLAG_ENABLE_WAITING_STATE_FOR_SYSTEM_SESSION_CREATION_REQUEST, new Predicate() { // from class: com.android.media.flags.CustomFeatureFlags$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableWaitingStateForSystemSessionCreationRequest();
            }
        });
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean fallbackToDefaultHandlingWhenMediaSessionHasFixedVolumeHandling() {
        return getValue(Flags.FLAG_FALLBACK_TO_DEFAULT_HANDLING_WHEN_MEDIA_SESSION_HAS_FIXED_VOLUME_HANDLING, new Predicate() { // from class: com.android.media.flags.CustomFeatureFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fallbackToDefaultHandlingWhenMediaSessionHasFixedVolumeHandling();
            }
        });
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean fixOutputMediaItemListIndexOutOfBoundsException() {
        return getValue(Flags.FLAG_FIX_OUTPUT_MEDIA_ITEM_LIST_INDEX_OUT_OF_BOUNDS_EXCEPTION, new Predicate() { // from class: com.android.media.flags.CustomFeatureFlags$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fixOutputMediaItemListIndexOutOfBoundsException();
            }
        });
    }

    public boolean isFlagReadOnlyOptimized(String str) {
        return this.mReadOnlyFlagsSet.contains(str) && isOptimizationEnabled();
    }

    protected boolean getValue(String str, Predicate<FeatureFlags> predicate) {
        return this.mGetValueImpl.test(str, predicate);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(Flags.FLAG_ADJUST_VOLUME_FOR_FOREGROUND_APP_PLAYING_AUDIO_WITHOUT_MEDIA_SESSION, Flags.FLAG_DISABLE_SET_BLUETOOTH_AD2P_ON_CALLS, Flags.FLAG_DISABLE_TRANSFER_WHEN_APPS_DO_NOT_SUPPORT, Flags.FLAG_ENABLE_AUDIO_INPUT_DEVICE_ROUTING_AND_VOLUME_CONTROL, Flags.FLAG_ENABLE_AUDIO_POLICIES_DEVICE_AND_BLUETOOTH_CONTROLLER, Flags.FLAG_ENABLE_BUILT_IN_SPEAKER_ROUTE_SUITABILITY_STATUSES, Flags.FLAG_ENABLE_CROSS_USER_ROUTING_IN_MEDIA_ROUTER2, Flags.FLAG_ENABLE_FIX_FOR_EMPTY_SYSTEM_ROUTES_CRASH, Flags.FLAG_ENABLE_FULL_SCAN_WITH_MEDIA_CONTENT_CONTROL, Flags.FLAG_ENABLE_GET_TRANSFERABLE_ROUTES, Flags.FLAG_ENABLE_MEDIA_ROUTE_2_INFO_PROVIDER_PACKAGE_NAME, Flags.FLAG_ENABLE_MIRRORING_IN_MEDIA_ROUTER_2, Flags.FLAG_ENABLE_MR2_SERVICE_NON_MAIN_BG_THREAD, Flags.FLAG_ENABLE_NEW_MEDIA_ROUTE_2_INFO_TYPES, Flags.FLAG_ENABLE_NEW_WIRED_MEDIA_ROUTE_2_INFO_TYPES, Flags.FLAG_ENABLE_NOTIFYING_ACTIVITY_MANAGER_WITH_MEDIA_SESSION_STATUS_CHANGE, Flags.FLAG_ENABLE_NULL_SESSION_IN_MEDIA_BROWSER_SERVICE, Flags.FLAG_ENABLE_OUTPUT_SWITCHER_DEVICE_GROUPING, Flags.FLAG_ENABLE_OUTPUT_SWITCHER_PERSONAL_AUDIO_SHARING, Flags.FLAG_ENABLE_OUTPUT_SWITCHER_REDESIGN, Flags.FLAG_ENABLE_OUTPUT_SWITCHER_SESSION_GROUPING, Flags.FLAG_ENABLE_PREVENTION_OF_KEEP_ALIVE_ROUTE_PROVIDERS, Flags.FLAG_ENABLE_PREVENTION_OF_MANAGER_SCANS_WHEN_NO_APPS_SCAN, Flags.FLAG_ENABLE_PRIVILEGED_ROUTING_FOR_MEDIA_ROUTING_CONTROL, Flags.FLAG_ENABLE_RLP_CALLBACKS_IN_MEDIA_ROUTER2, Flags.FLAG_ENABLE_ROUTE_VISIBILITY_CONTROL_API, Flags.FLAG_ENABLE_SCREEN_OFF_SCANNING, Flags.FLAG_ENABLE_SUGGESTED_DEVICE_API, Flags.FLAG_ENABLE_USE_OF_BLUETOOTH_DEVICE_GET_ALIAS_FOR_MR2INFO_GET_NAME, Flags.FLAG_ENABLE_USE_OF_SINGLETON_AUDIO_MANAGER_ROUTE_CONTROLLER, Flags.FLAG_ENABLE_WAITING_STATE_FOR_SYSTEM_SESSION_CREATION_REQUEST, Flags.FLAG_FALLBACK_TO_DEFAULT_HANDLING_WHEN_MEDIA_SESSION_HAS_FIXED_VOLUME_HANDLING, Flags.FLAG_FIX_OUTPUT_MEDIA_ITEM_LIST_INDEX_OUT_OF_BOUNDS_EXCEPTION);
    }
}
