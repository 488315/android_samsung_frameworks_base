package com.android.server.telecom.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes6.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_ADD_CALL_URI_FOR_MISSED_CALLS, Flags.FLAG_ALLOW_SYSTEM_APPS_RESOLVE_VOIP_CALLS, Flags.FLAG_ASSOCIATED_USER_REFACTOR_FOR_WORK_PROFILE, Flags.FLAG_AVAILABLE_ROUTES_NEVER_UPDATED_AFTER_SET_SYSTEM_AUDIO_STATE, Flags.FLAG_BUS_DEVICE_IS_A_SPEAKER, Flags.FLAG_BUSINESS_CALL_COMPOSER, Flags.FLAG_BYPASS_HOLD_FOR_ECC_DIAL, Flags.FLAG_CACHE_CALL_AUDIO_CALLBACKS, Flags.FLAG_CACHE_CALL_EVENTS, Flags.FLAG_CALL_AUDIO_COMMUNICATION_DEVICE_REFACTOR, Flags.FLAG_CALL_AUDIO_ROUTING_PERFORMANCE_IMPROVEMENENT, Flags.FLAG_CALL_DETAILS_ID_CHANGES, Flags.FLAG_CALL_SEQUENCING_CALL_RESUME_FAILED, Flags.FLAG_CANCEL_REMOVAL_ON_EMERGENCY_REDIAL, Flags.FLAG_CHECK_COMPLETED_FILTERS_ON_TIMEOUT, Flags.FLAG_CHECK_DEVICE_TYPE_ON_ROUTE_CHANGE, Flags.FLAG_CLEAR_COMMUNICATION_DEVICE_AFTER_AUDIO_OPS_COMPLETE, Flags.FLAG_COMMUNICATION_DEVICE_PROTECTED_BY_LOCK, Flags.FLAG_CSW_SERVICE_INTERFACE_IS_NULL, Flags.FLAG_DISCONNECT_SELF_MANAGED_STUCK_STARTUP_CALLS, Flags.FLAG_DO_NOT_SEND_CALL_TO_NULL_ICS, Flags.FLAG_DONT_TIMEOUT_DESTROYED_CALLS, Flags.FLAG_DONT_USE_COMMUNICATION_DEVICE_TRACKER, Flags.FLAG_EARLY_BINDING_TO_INCALL_SERVICE, Flags.FLAG_EARLY_UPDATE_INTERNAL_CALL_AUDIO_STATE, Flags.FLAG_ECC_KEYGUARD, Flags.FLAG_ENABLE_CALL_AUDIO_WATCHDOG, Flags.FLAG_ENABLE_CALL_EXCEPTION_ANOM_REPORTS, Flags.FLAG_ENABLE_CALL_SEQUENCING, Flags.FLAG_ENABLE_RESPOND_VIA_SMS_MANAGER_ASYNC, Flags.FLAG_END_SESSION_IMPROVEMENTS, Flags.FLAG_ENFORCE_TRANSACTIONAL_EXCLUSIVITY, Flags.FLAG_ENSURE_AUDIO_MODE_UPDATES_ON_FOREGROUND_CALL_CHANGE, Flags.FLAG_ENSURE_IN_CAR_RINGING, Flags.FLAG_FIX_AUDIO_FLICKER_FOR_OUTGOING_CALLS, Flags.FLAG_FIX_USER_REQUEST_BASELINE_ROUTE_VIDEO_CALL, Flags.FLAG_GEN_ANOM_REPORT_ON_FOCUS_TIMEOUT, Flags.FLAG_GET_LAST_KNOWN_CELL_IDENTITY, Flags.FLAG_GET_REGISTERED_PHONE_ACCOUNTS, Flags.FLAG_GET_RINGER_MODE_ANOM_REPORT, Flags.FLAG_IGNORE_AUTO_ROUTE_TO_WATCH_DEVICE, Flags.FLAG_IS_NEW_OUTGOING_CALL_BROADCAST_UNBLOCKING, Flags.FLAG_KEEP_BLUETOOTH_DEVICES_CACHE_UPDATED, Flags.FLAG_MAYBE_DEFAULT_SPEAKER_AFTER_UNHOLD, Flags.FLAG_NEW_AUDIO_PATH_SPEAKER_BROADCAST_AND_UNFOCUSED_ROUTING, Flags.FLAG_ON_CALL_ENDPOINT_CHANGED_ICS_ON_CONNECTED, Flags.FLAG_ONLY_CLEAR_COMMUNICATION_DEVICE_ON_INACTIVE, Flags.FLAG_ONLY_UPDATE_TELEPHONY_ON_VALID_SUB_IDS, Flags.FLAG_POSTPONE_REGISTER_TO_LEAUDIO, Flags.FLAG_PREVENT_REDUNDANT_LOCATION_PERMISSION_GRANT_AND_REVOKE, Flags.FLAG_PROFILE_USER_SUPPORT, Flags.FLAG_REMAP_TRANSACTIONAL_CAPABILITIES, Flags.FLAG_RESET_MUTE_WHEN_ENTERING_QUIESCENT_BT_ROUTE, Flags.FLAG_RESOLVE_ACTIVE_BT_ROUTING_AND_BT_TIMING_ISSUE, Flags.FLAG_RESOLVE_SWITCHING_BT_DEVICES_COMPUTATION, Flags.FLAG_SELECT_PHONE_ACCOUNT_BEFORE_MAKING_ROOM, Flags.FLAG_SEPARATELY_BIND_TO_BT_INCALL_SERVICE, Flags.FLAG_SET_AUDIO_MODE_BEFORE_ABANDON_FOCUS, Flags.FLAG_SET_MUTE_STATE, Flags.FLAG_SET_REMOTE_CONNECTION_CALL_ID, Flags.FLAG_SKIP_BASELINE_SWITCH_WHEN_ROUTE_NOT_BLUETOOTH, Flags.FLAG_SKIP_FILTER_PHONE_ACCOUNT_PERFORM_DND_FILTER, Flags.FLAG_TELECOM_APP_LABEL_PROXY_HSUM_AWARE, Flags.FLAG_TELECOM_LOG_EXTERNAL_WEARABLE_CALLS, Flags.FLAG_TELECOM_MAIN_USER_IN_BLOCK_CHECK, Flags.FLAG_TELECOM_MAIN_USER_IN_GET_RESPOND_MESSAGE_APP, Flags.FLAG_TELECOM_MAINLINE_BLOCKED_NUMBERS_MANAGER, Flags.FLAG_TELECOM_METRICS_SUPPORT, Flags.FLAG_TELECOM_RESOLVE_HIDDEN_DEPENDENCIES, Flags.FLAG_TELECOM_SKIP_LOG_BASED_ON_EXTRA, Flags.FLAG_TELEPHONY_HAS_DEFAULT_BUT_TELECOM_DOES_NOT, Flags.FLAG_TRANSACTIONAL_CS_VERIFIER, Flags.FLAG_TRANSACTIONAL_HOLD_DISCONNECTS_UNHOLDABLE, Flags.FLAG_TRANSACTIONAL_VIDEO_STATE, Flags.FLAG_TRANSIT_ROUTE_BEFORE_AUDIO_DISCONNECT_BT, Flags.FLAG_UNREGISTER_UNRESOLVABLE_ACCOUNTS, Flags.FLAG_UPDATE_PREFERRED_AUDIO_DEVICE_LOGIC, Flags.FLAG_UPDATE_ROUTE_MASK_WHEN_BT_CONNECTED, Flags.FLAG_UPDATED_RCS_CALL_COUNT_TRACKING, Flags.FLAG_USE_ACTUAL_ADDRESS_TO_ENTER_CONNECTING_STATE, Flags.FLAG_USE_DEVICE_PROVIDED_SERIALIZED_RINGER_VIBRATION, Flags.FLAG_USE_IMPROVED_LISTENER_ORDER, Flags.FLAG_USE_REFACTORED_AUDIO_ROUTE_SWITCHING, Flags.FLAG_USE_STREAM_VOICE_CALL_TONES, Flags.FLAG_VOIP_APP_ACTIONS_SUPPORT, Flags.FLAG_VOIP_CALL_MONITOR_REFACTOR, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean addCallUriForMissedCalls() {
        return getValue(Flags.FLAG_ADD_CALL_URI_FOR_MISSED_CALLS, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda50
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).addCallUriForMissedCalls();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean allowSystemAppsResolveVoipCalls() {
        return getValue(Flags.FLAG_ALLOW_SYSTEM_APPS_RESOLVE_VOIP_CALLS, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda80
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).allowSystemAppsResolveVoipCalls();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean associatedUserRefactorForWorkProfile() {
        return getValue(Flags.FLAG_ASSOCIATED_USER_REFACTOR_FOR_WORK_PROFILE, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda72
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).associatedUserRefactorForWorkProfile();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean availableRoutesNeverUpdatedAfterSetSystemAudioState() {
        return getValue(Flags.FLAG_AVAILABLE_ROUTES_NEVER_UPDATED_AFTER_SET_SYSTEM_AUDIO_STATE, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda48
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).availableRoutesNeverUpdatedAfterSetSystemAudioState();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean busDeviceIsASpeaker() {
        return getValue(Flags.FLAG_BUS_DEVICE_IS_A_SPEAKER, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda43
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).busDeviceIsASpeaker();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean businessCallComposer() {
        return getValue(Flags.FLAG_BUSINESS_CALL_COMPOSER, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).businessCallComposer();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean bypassHoldForEccDial() {
        return getValue(Flags.FLAG_BYPASS_HOLD_FOR_ECC_DIAL, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda30
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).bypassHoldForEccDial();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean cacheCallAudioCallbacks() {
        return getValue(Flags.FLAG_CACHE_CALL_AUDIO_CALLBACKS, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda36
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cacheCallAudioCallbacks();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean cacheCallEvents() {
        return getValue(Flags.FLAG_CACHE_CALL_EVENTS, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda66
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cacheCallEvents();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean callAudioCommunicationDeviceRefactor() {
        return getValue(Flags.FLAG_CALL_AUDIO_COMMUNICATION_DEVICE_REFACTOR, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda23
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).callAudioCommunicationDeviceRefactor();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean callAudioRoutingPerformanceImprovemenent() {
        return getValue(Flags.FLAG_CALL_AUDIO_ROUTING_PERFORMANCE_IMPROVEMENENT, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda37
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).callAudioRoutingPerformanceImprovemenent();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean callDetailsIdChanges() {
        return getValue(Flags.FLAG_CALL_DETAILS_ID_CHANGES, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda21
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).callDetailsIdChanges();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean callSequencingCallResumeFailed() {
        return getValue(Flags.FLAG_CALL_SEQUENCING_CALL_RESUME_FAILED, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda34
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).callSequencingCallResumeFailed();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean cancelRemovalOnEmergencyRedial() {
        return getValue(Flags.FLAG_CANCEL_REMOVAL_ON_EMERGENCY_REDIAL, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda45
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cancelRemovalOnEmergencyRedial();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean checkCompletedFiltersOnTimeout() {
        return getValue(Flags.FLAG_CHECK_COMPLETED_FILTERS_ON_TIMEOUT, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda64
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).checkCompletedFiltersOnTimeout();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean checkDeviceTypeOnRouteChange() {
        return getValue(Flags.FLAG_CHECK_DEVICE_TYPE_ON_ROUTE_CHANGE, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda74
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).checkDeviceTypeOnRouteChange();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean clearCommunicationDeviceAfterAudioOpsComplete() {
        return getValue(Flags.FLAG_CLEAR_COMMUNICATION_DEVICE_AFTER_AUDIO_OPS_COMPLETE, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda20
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).clearCommunicationDeviceAfterAudioOpsComplete();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean communicationDeviceProtectedByLock() {
        return getValue(Flags.FLAG_COMMUNICATION_DEVICE_PROTECTED_BY_LOCK, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda61
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).communicationDeviceProtectedByLock();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean cswServiceInterfaceIsNull() {
        return getValue(Flags.FLAG_CSW_SERVICE_INTERFACE_IS_NULL, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda79
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cswServiceInterfaceIsNull();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean disconnectSelfManagedStuckStartupCalls() {
        return getValue(Flags.FLAG_DISCONNECT_SELF_MANAGED_STUCK_STARTUP_CALLS, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).disconnectSelfManagedStuckStartupCalls();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean doNotSendCallToNullIcs() {
        return getValue(Flags.FLAG_DO_NOT_SEND_CALL_TO_NULL_ICS, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).doNotSendCallToNullIcs();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean dontTimeoutDestroyedCalls() {
        return getValue(Flags.FLAG_DONT_TIMEOUT_DESTROYED_CALLS, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda25
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).dontTimeoutDestroyedCalls();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean dontUseCommunicationDeviceTracker() {
        return getValue(Flags.FLAG_DONT_USE_COMMUNICATION_DEVICE_TRACKER, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda65
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).dontUseCommunicationDeviceTracker();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean earlyBindingToIncallService() {
        return getValue(Flags.FLAG_EARLY_BINDING_TO_INCALL_SERVICE, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda40
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).earlyBindingToIncallService();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean earlyUpdateInternalCallAudioState() {
        return getValue(Flags.FLAG_EARLY_UPDATE_INTERNAL_CALL_AUDIO_STATE, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda44
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).earlyUpdateInternalCallAudioState();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean eccKeyguard() {
        return getValue(Flags.FLAG_ECC_KEYGUARD, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda71
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).eccKeyguard();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean enableCallAudioWatchdog() {
        return getValue(Flags.FLAG_ENABLE_CALL_AUDIO_WATCHDOG, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda78
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableCallAudioWatchdog();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean enableCallExceptionAnomReports() {
        return getValue(Flags.FLAG_ENABLE_CALL_EXCEPTION_ANOM_REPORTS, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda26
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableCallExceptionAnomReports();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean enableCallSequencing() {
        return getValue(Flags.FLAG_ENABLE_CALL_SEQUENCING, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda67
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableCallSequencing();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean enableRespondViaSmsManagerAsync() {
        return getValue(Flags.FLAG_ENABLE_RESPOND_VIA_SMS_MANAGER_ASYNC, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda58
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableRespondViaSmsManagerAsync();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean endSessionImprovements() {
        return getValue(Flags.FLAG_END_SESSION_IMPROVEMENTS, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda42
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).endSessionImprovements();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean enforceTransactionalExclusivity() {
        return getValue(Flags.FLAG_ENFORCE_TRANSACTIONAL_EXCLUSIVITY, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda38
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enforceTransactionalExclusivity();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean ensureAudioModeUpdatesOnForegroundCallChange() {
        return getValue(Flags.FLAG_ENSURE_AUDIO_MODE_UPDATES_ON_FOREGROUND_CALL_CHANGE, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda39
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).ensureAudioModeUpdatesOnForegroundCallChange();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean ensureInCarRinging() {
        return getValue(Flags.FLAG_ENSURE_IN_CAR_RINGING, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda46
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).ensureInCarRinging();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean fixAudioFlickerForOutgoingCalls() {
        return getValue(Flags.FLAG_FIX_AUDIO_FLICKER_FOR_OUTGOING_CALLS, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda76
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fixAudioFlickerForOutgoingCalls();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean fixUserRequestBaselineRouteVideoCall() {
        return getValue(Flags.FLAG_FIX_USER_REQUEST_BASELINE_ROUTE_VIDEO_CALL, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fixUserRequestBaselineRouteVideoCall();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean genAnomReportOnFocusTimeout() {
        return getValue(Flags.FLAG_GEN_ANOM_REPORT_ON_FOCUS_TIMEOUT, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda31
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).genAnomReportOnFocusTimeout();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean getLastKnownCellIdentity() {
        return getValue(Flags.FLAG_GET_LAST_KNOWN_CELL_IDENTITY, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).getLastKnownCellIdentity();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean getRegisteredPhoneAccounts() {
        return getValue(Flags.FLAG_GET_REGISTERED_PHONE_ACCOUNTS, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda85
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).getRegisteredPhoneAccounts();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean getRingerModeAnomReport() {
        return getValue(Flags.FLAG_GET_RINGER_MODE_ANOM_REPORT, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).getRingerModeAnomReport();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean ignoreAutoRouteToWatchDevice() {
        return getValue(Flags.FLAG_IGNORE_AUTO_ROUTE_TO_WATCH_DEVICE, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda29
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).ignoreAutoRouteToWatchDevice();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean isNewOutgoingCallBroadcastUnblocking() {
        return getValue(Flags.FLAG_IS_NEW_OUTGOING_CALL_BROADCAST_UNBLOCKING, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda24
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).isNewOutgoingCallBroadcastUnblocking();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean keepBluetoothDevicesCacheUpdated() {
        return getValue(Flags.FLAG_KEEP_BLUETOOTH_DEVICES_CACHE_UPDATED, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda69
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).keepBluetoothDevicesCacheUpdated();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean maybeDefaultSpeakerAfterUnhold() {
        return getValue(Flags.FLAG_MAYBE_DEFAULT_SPEAKER_AFTER_UNHOLD, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).maybeDefaultSpeakerAfterUnhold();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean newAudioPathSpeakerBroadcastAndUnfocusedRouting() {
        return getValue(Flags.FLAG_NEW_AUDIO_PATH_SPEAKER_BROADCAST_AND_UNFOCUSED_ROUTING, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda28
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).newAudioPathSpeakerBroadcastAndUnfocusedRouting();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean onCallEndpointChangedIcsOnConnected() {
        return getValue(Flags.FLAG_ON_CALL_ENDPOINT_CHANGED_ICS_ON_CONNECTED, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda49
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).onCallEndpointChangedIcsOnConnected();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean onlyClearCommunicationDeviceOnInactive() {
        return getValue(Flags.FLAG_ONLY_CLEAR_COMMUNICATION_DEVICE_ON_INACTIVE, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda47
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).onlyClearCommunicationDeviceOnInactive();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean onlyUpdateTelephonyOnValidSubIds() {
        return getValue(Flags.FLAG_ONLY_UPDATE_TELEPHONY_ON_VALID_SUB_IDS, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).onlyUpdateTelephonyOnValidSubIds();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean postponeRegisterToLeaudio() {
        return getValue(Flags.FLAG_POSTPONE_REGISTER_TO_LEAUDIO, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda19
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).postponeRegisterToLeaudio();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean preventRedundantLocationPermissionGrantAndRevoke() {
        return getValue(Flags.FLAG_PREVENT_REDUNDANT_LOCATION_PERMISSION_GRANT_AND_REVOKE, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda63
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).preventRedundantLocationPermissionGrantAndRevoke();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean profileUserSupport() {
        return getValue(Flags.FLAG_PROFILE_USER_SUPPORT, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda54
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).profileUserSupport();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean remapTransactionalCapabilities() {
        return getValue(Flags.FLAG_REMAP_TRANSACTIONAL_CAPABILITIES, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).remapTransactionalCapabilities();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean resetMuteWhenEnteringQuiescentBtRoute() {
        return getValue(Flags.FLAG_RESET_MUTE_WHEN_ENTERING_QUIESCENT_BT_ROUTE, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda52
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).resetMuteWhenEnteringQuiescentBtRoute();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean resolveActiveBtRoutingAndBtTimingIssue() {
        return getValue(Flags.FLAG_RESOLVE_ACTIVE_BT_ROUTING_AND_BT_TIMING_ISSUE, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda56
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).resolveActiveBtRoutingAndBtTimingIssue();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean resolveSwitchingBtDevicesComputation() {
        return getValue(Flags.FLAG_RESOLVE_SWITCHING_BT_DEVICES_COMPUTATION, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda60
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).resolveSwitchingBtDevicesComputation();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean selectPhoneAccountBeforeMakingRoom() {
        return getValue(Flags.FLAG_SELECT_PHONE_ACCOUNT_BEFORE_MAKING_ROOM, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda59
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).selectPhoneAccountBeforeMakingRoom();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean separatelyBindToBtIncallService() {
        return getValue(Flags.FLAG_SEPARATELY_BIND_TO_BT_INCALL_SERVICE, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda55
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).separatelyBindToBtIncallService();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean setAudioModeBeforeAbandonFocus() {
        return getValue(Flags.FLAG_SET_AUDIO_MODE_BEFORE_ABANDON_FOCUS, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda57
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).setAudioModeBeforeAbandonFocus();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean setMuteState() {
        return getValue(Flags.FLAG_SET_MUTE_STATE, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda41
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).setMuteState();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean setRemoteConnectionCallId() {
        return getValue(Flags.FLAG_SET_REMOTE_CONNECTION_CALL_ID, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda84
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).setRemoteConnectionCallId();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean skipBaselineSwitchWhenRouteNotBluetooth() {
        return getValue(Flags.FLAG_SKIP_BASELINE_SWITCH_WHEN_ROUTE_NOT_BLUETOOTH, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).skipBaselineSwitchWhenRouteNotBluetooth();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean skipFilterPhoneAccountPerformDndFilter() {
        return getValue(Flags.FLAG_SKIP_FILTER_PHONE_ACCOUNT_PERFORM_DND_FILTER, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda68
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).skipFilterPhoneAccountPerformDndFilter();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean telecomAppLabelProxyHsumAware() {
        return getValue(Flags.FLAG_TELECOM_APP_LABEL_PROXY_HSUM_AWARE, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda62
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).telecomAppLabelProxyHsumAware();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean telecomLogExternalWearableCalls() {
        return getValue(Flags.FLAG_TELECOM_LOG_EXTERNAL_WEARABLE_CALLS, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).telecomLogExternalWearableCalls();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean telecomMainUserInBlockCheck() {
        return getValue(Flags.FLAG_TELECOM_MAIN_USER_IN_BLOCK_CHECK, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda83
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).telecomMainUserInBlockCheck();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean telecomMainUserInGetRespondMessageApp() {
        return getValue(Flags.FLAG_TELECOM_MAIN_USER_IN_GET_RESPOND_MESSAGE_APP, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda32
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).telecomMainUserInGetRespondMessageApp();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean telecomMainlineBlockedNumbersManager() {
        return getValue(Flags.FLAG_TELECOM_MAINLINE_BLOCKED_NUMBERS_MANAGER, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda75
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).telecomMainlineBlockedNumbersManager();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean telecomMetricsSupport() {
        return getValue(Flags.FLAG_TELECOM_METRICS_SUPPORT, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).telecomMetricsSupport();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean telecomResolveHiddenDependencies() {
        return getValue(Flags.FLAG_TELECOM_RESOLVE_HIDDEN_DEPENDENCIES, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda81
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).telecomResolveHiddenDependencies();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean telecomSkipLogBasedOnExtra() {
        return getValue(Flags.FLAG_TELECOM_SKIP_LOG_BASED_ON_EXTRA, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).telecomSkipLogBasedOnExtra();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean telephonyHasDefaultButTelecomDoesNot() {
        return getValue(Flags.FLAG_TELEPHONY_HAS_DEFAULT_BUT_TELECOM_DOES_NOT, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda35
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).telephonyHasDefaultButTelecomDoesNot();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean transactionalCsVerifier() {
        return getValue(Flags.FLAG_TRANSACTIONAL_CS_VERIFIER, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda27
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).transactionalCsVerifier();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean transactionalHoldDisconnectsUnholdable() {
        return getValue(Flags.FLAG_TRANSACTIONAL_HOLD_DISCONNECTS_UNHOLDABLE, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda53
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).transactionalHoldDisconnectsUnholdable();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean transactionalVideoState() {
        return getValue(Flags.FLAG_TRANSACTIONAL_VIDEO_STATE, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda70
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).transactionalVideoState();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean transitRouteBeforeAudioDisconnectBt() {
        return getValue(Flags.FLAG_TRANSIT_ROUTE_BEFORE_AUDIO_DISCONNECT_BT, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).transitRouteBeforeAudioDisconnectBt();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean unregisterUnresolvableAccounts() {
        return getValue(Flags.FLAG_UNREGISTER_UNRESOLVABLE_ACCOUNTS, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).unregisterUnresolvableAccounts();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean updatePreferredAudioDeviceLogic() {
        return getValue(Flags.FLAG_UPDATE_PREFERRED_AUDIO_DEVICE_LOGIC, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda51
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).updatePreferredAudioDeviceLogic();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean updateRouteMaskWhenBtConnected() {
        return getValue(Flags.FLAG_UPDATE_ROUTE_MASK_WHEN_BT_CONNECTED, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).updateRouteMaskWhenBtConnected();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean updatedRcsCallCountTracking() {
        return getValue(Flags.FLAG_UPDATED_RCS_CALL_COUNT_TRACKING, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda73
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).updatedRcsCallCountTracking();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean useActualAddressToEnterConnectingState() {
        return getValue(Flags.FLAG_USE_ACTUAL_ADDRESS_TO_ENTER_CONNECTING_STATE, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda33
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useActualAddressToEnterConnectingState();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean useDeviceProvidedSerializedRingerVibration() {
        return getValue(Flags.FLAG_USE_DEVICE_PROVIDED_SERIALIZED_RINGER_VIBRATION, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useDeviceProvidedSerializedRingerVibration();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean useImprovedListenerOrder() {
        return getValue(Flags.FLAG_USE_IMPROVED_LISTENER_ORDER, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda22
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useImprovedListenerOrder();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean useRefactoredAudioRouteSwitching() {
        return getValue(Flags.FLAG_USE_REFACTORED_AUDIO_ROUTE_SWITCHING, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useRefactoredAudioRouteSwitching();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean useStreamVoiceCallTones() {
        return getValue(Flags.FLAG_USE_STREAM_VOICE_CALL_TONES, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda82
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useStreamVoiceCallTones();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean voipAppActionsSupport() {
        return getValue(Flags.FLAG_VOIP_APP_ACTIONS_SUPPORT, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda77
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).voipAppActionsSupport();
            }
        });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean voipCallMonitorRefactor() {
        return getValue(Flags.FLAG_VOIP_CALL_MONITOR_REFACTOR, new Predicate() { // from class: com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda18
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).voipCallMonitorRefactor();
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
        return Arrays.asList(Flags.FLAG_ADD_CALL_URI_FOR_MISSED_CALLS, Flags.FLAG_ALLOW_SYSTEM_APPS_RESOLVE_VOIP_CALLS, Flags.FLAG_ASSOCIATED_USER_REFACTOR_FOR_WORK_PROFILE, Flags.FLAG_AVAILABLE_ROUTES_NEVER_UPDATED_AFTER_SET_SYSTEM_AUDIO_STATE, Flags.FLAG_BUS_DEVICE_IS_A_SPEAKER, Flags.FLAG_BUSINESS_CALL_COMPOSER, Flags.FLAG_BYPASS_HOLD_FOR_ECC_DIAL, Flags.FLAG_CACHE_CALL_AUDIO_CALLBACKS, Flags.FLAG_CACHE_CALL_EVENTS, Flags.FLAG_CALL_AUDIO_COMMUNICATION_DEVICE_REFACTOR, Flags.FLAG_CALL_AUDIO_ROUTING_PERFORMANCE_IMPROVEMENENT, Flags.FLAG_CALL_DETAILS_ID_CHANGES, Flags.FLAG_CALL_SEQUENCING_CALL_RESUME_FAILED, Flags.FLAG_CANCEL_REMOVAL_ON_EMERGENCY_REDIAL, Flags.FLAG_CHECK_COMPLETED_FILTERS_ON_TIMEOUT, Flags.FLAG_CHECK_DEVICE_TYPE_ON_ROUTE_CHANGE, Flags.FLAG_CLEAR_COMMUNICATION_DEVICE_AFTER_AUDIO_OPS_COMPLETE, Flags.FLAG_COMMUNICATION_DEVICE_PROTECTED_BY_LOCK, Flags.FLAG_CSW_SERVICE_INTERFACE_IS_NULL, Flags.FLAG_DISCONNECT_SELF_MANAGED_STUCK_STARTUP_CALLS, Flags.FLAG_DO_NOT_SEND_CALL_TO_NULL_ICS, Flags.FLAG_DONT_TIMEOUT_DESTROYED_CALLS, Flags.FLAG_DONT_USE_COMMUNICATION_DEVICE_TRACKER, Flags.FLAG_EARLY_BINDING_TO_INCALL_SERVICE, Flags.FLAG_EARLY_UPDATE_INTERNAL_CALL_AUDIO_STATE, Flags.FLAG_ECC_KEYGUARD, Flags.FLAG_ENABLE_CALL_AUDIO_WATCHDOG, Flags.FLAG_ENABLE_CALL_EXCEPTION_ANOM_REPORTS, Flags.FLAG_ENABLE_CALL_SEQUENCING, Flags.FLAG_ENABLE_RESPOND_VIA_SMS_MANAGER_ASYNC, Flags.FLAG_END_SESSION_IMPROVEMENTS, Flags.FLAG_ENFORCE_TRANSACTIONAL_EXCLUSIVITY, Flags.FLAG_ENSURE_AUDIO_MODE_UPDATES_ON_FOREGROUND_CALL_CHANGE, Flags.FLAG_ENSURE_IN_CAR_RINGING, Flags.FLAG_FIX_AUDIO_FLICKER_FOR_OUTGOING_CALLS, Flags.FLAG_FIX_USER_REQUEST_BASELINE_ROUTE_VIDEO_CALL, Flags.FLAG_GEN_ANOM_REPORT_ON_FOCUS_TIMEOUT, Flags.FLAG_GET_LAST_KNOWN_CELL_IDENTITY, Flags.FLAG_GET_REGISTERED_PHONE_ACCOUNTS, Flags.FLAG_GET_RINGER_MODE_ANOM_REPORT, Flags.FLAG_IGNORE_AUTO_ROUTE_TO_WATCH_DEVICE, Flags.FLAG_IS_NEW_OUTGOING_CALL_BROADCAST_UNBLOCKING, Flags.FLAG_KEEP_BLUETOOTH_DEVICES_CACHE_UPDATED, Flags.FLAG_MAYBE_DEFAULT_SPEAKER_AFTER_UNHOLD, Flags.FLAG_NEW_AUDIO_PATH_SPEAKER_BROADCAST_AND_UNFOCUSED_ROUTING, Flags.FLAG_ON_CALL_ENDPOINT_CHANGED_ICS_ON_CONNECTED, Flags.FLAG_ONLY_CLEAR_COMMUNICATION_DEVICE_ON_INACTIVE, Flags.FLAG_ONLY_UPDATE_TELEPHONY_ON_VALID_SUB_IDS, Flags.FLAG_POSTPONE_REGISTER_TO_LEAUDIO, Flags.FLAG_PREVENT_REDUNDANT_LOCATION_PERMISSION_GRANT_AND_REVOKE, Flags.FLAG_PROFILE_USER_SUPPORT, Flags.FLAG_REMAP_TRANSACTIONAL_CAPABILITIES, Flags.FLAG_RESET_MUTE_WHEN_ENTERING_QUIESCENT_BT_ROUTE, Flags.FLAG_RESOLVE_ACTIVE_BT_ROUTING_AND_BT_TIMING_ISSUE, Flags.FLAG_RESOLVE_SWITCHING_BT_DEVICES_COMPUTATION, Flags.FLAG_SELECT_PHONE_ACCOUNT_BEFORE_MAKING_ROOM, Flags.FLAG_SEPARATELY_BIND_TO_BT_INCALL_SERVICE, Flags.FLAG_SET_AUDIO_MODE_BEFORE_ABANDON_FOCUS, Flags.FLAG_SET_MUTE_STATE, Flags.FLAG_SET_REMOTE_CONNECTION_CALL_ID, Flags.FLAG_SKIP_BASELINE_SWITCH_WHEN_ROUTE_NOT_BLUETOOTH, Flags.FLAG_SKIP_FILTER_PHONE_ACCOUNT_PERFORM_DND_FILTER, Flags.FLAG_TELECOM_APP_LABEL_PROXY_HSUM_AWARE, Flags.FLAG_TELECOM_LOG_EXTERNAL_WEARABLE_CALLS, Flags.FLAG_TELECOM_MAIN_USER_IN_BLOCK_CHECK, Flags.FLAG_TELECOM_MAIN_USER_IN_GET_RESPOND_MESSAGE_APP, Flags.FLAG_TELECOM_MAINLINE_BLOCKED_NUMBERS_MANAGER, Flags.FLAG_TELECOM_METRICS_SUPPORT, Flags.FLAG_TELECOM_RESOLVE_HIDDEN_DEPENDENCIES, Flags.FLAG_TELECOM_SKIP_LOG_BASED_ON_EXTRA, Flags.FLAG_TELEPHONY_HAS_DEFAULT_BUT_TELECOM_DOES_NOT, Flags.FLAG_TRANSACTIONAL_CS_VERIFIER, Flags.FLAG_TRANSACTIONAL_HOLD_DISCONNECTS_UNHOLDABLE, Flags.FLAG_TRANSACTIONAL_VIDEO_STATE, Flags.FLAG_TRANSIT_ROUTE_BEFORE_AUDIO_DISCONNECT_BT, Flags.FLAG_UNREGISTER_UNRESOLVABLE_ACCOUNTS, Flags.FLAG_UPDATE_PREFERRED_AUDIO_DEVICE_LOGIC, Flags.FLAG_UPDATE_ROUTE_MASK_WHEN_BT_CONNECTED, Flags.FLAG_UPDATED_RCS_CALL_COUNT_TRACKING, Flags.FLAG_USE_ACTUAL_ADDRESS_TO_ENTER_CONNECTING_STATE, Flags.FLAG_USE_DEVICE_PROVIDED_SERIALIZED_RINGER_VIBRATION, Flags.FLAG_USE_IMPROVED_LISTENER_ORDER, Flags.FLAG_USE_REFACTORED_AUDIO_ROUTE_SWITCHING, Flags.FLAG_USE_STREAM_VOICE_CALL_TONES, Flags.FLAG_VOIP_APP_ACTIONS_SUPPORT, Flags.FLAG_VOIP_CALL_MONITOR_REFACTOR);
    }
}
