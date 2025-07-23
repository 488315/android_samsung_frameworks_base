package com.android.server.telecom.flags;

/* loaded from: classes6.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ADD_CALL_URI_FOR_MISSED_CALLS = "com.android.server.telecom.flags.add_call_uri_for_missed_calls";
    public static final String FLAG_ALLOW_SYSTEM_APPS_RESOLVE_VOIP_CALLS = "com.android.server.telecom.flags.allow_system_apps_resolve_voip_calls";
    public static final String FLAG_ASSOCIATED_USER_REFACTOR_FOR_WORK_PROFILE = "com.android.server.telecom.flags.associated_user_refactor_for_work_profile";
    public static final String FLAG_AVAILABLE_ROUTES_NEVER_UPDATED_AFTER_SET_SYSTEM_AUDIO_STATE = "com.android.server.telecom.flags.available_routes_never_updated_after_set_system_audio_state";
    public static final String FLAG_BUSINESS_CALL_COMPOSER = "com.android.server.telecom.flags.business_call_composer";
    public static final String FLAG_BUS_DEVICE_IS_A_SPEAKER = "com.android.server.telecom.flags.bus_device_is_a_speaker";
    public static final String FLAG_BYPASS_HOLD_FOR_ECC_DIAL = "com.android.server.telecom.flags.bypass_hold_for_ecc_dial";
    public static final String FLAG_CACHE_CALL_AUDIO_CALLBACKS = "com.android.server.telecom.flags.cache_call_audio_callbacks";
    public static final String FLAG_CACHE_CALL_EVENTS = "com.android.server.telecom.flags.cache_call_events";
    public static final String FLAG_CALL_AUDIO_COMMUNICATION_DEVICE_REFACTOR = "com.android.server.telecom.flags.call_audio_communication_device_refactor";
    public static final String FLAG_CALL_AUDIO_ROUTING_PERFORMANCE_IMPROVEMENENT = "com.android.server.telecom.flags.call_audio_routing_performance_improvemenent";
    public static final String FLAG_CALL_DETAILS_ID_CHANGES = "com.android.server.telecom.flags.call_details_id_changes";
    public static final String FLAG_CALL_SEQUENCING_CALL_RESUME_FAILED = "com.android.server.telecom.flags.call_sequencing_call_resume_failed";
    public static final String FLAG_CANCEL_REMOVAL_ON_EMERGENCY_REDIAL = "com.android.server.telecom.flags.cancel_removal_on_emergency_redial";
    public static final String FLAG_CHECK_COMPLETED_FILTERS_ON_TIMEOUT = "com.android.server.telecom.flags.check_completed_filters_on_timeout";
    public static final String FLAG_CHECK_DEVICE_TYPE_ON_ROUTE_CHANGE = "com.android.server.telecom.flags.check_device_type_on_route_change";
    public static final String FLAG_CLEAR_COMMUNICATION_DEVICE_AFTER_AUDIO_OPS_COMPLETE = "com.android.server.telecom.flags.clear_communication_device_after_audio_ops_complete";
    public static final String FLAG_COMMUNICATION_DEVICE_PROTECTED_BY_LOCK = "com.android.server.telecom.flags.communication_device_protected_by_lock";
    public static final String FLAG_CSW_SERVICE_INTERFACE_IS_NULL = "com.android.server.telecom.flags.csw_service_interface_is_null";
    public static final String FLAG_DISCONNECT_SELF_MANAGED_STUCK_STARTUP_CALLS = "com.android.server.telecom.flags.disconnect_self_managed_stuck_startup_calls";
    public static final String FLAG_DONT_TIMEOUT_DESTROYED_CALLS = "com.android.server.telecom.flags.dont_timeout_destroyed_calls";
    public static final String FLAG_DONT_USE_COMMUNICATION_DEVICE_TRACKER = "com.android.server.telecom.flags.dont_use_communication_device_tracker";
    public static final String FLAG_DO_NOT_SEND_CALL_TO_NULL_ICS = "com.android.server.telecom.flags.do_not_send_call_to_null_ics";
    public static final String FLAG_EARLY_BINDING_TO_INCALL_SERVICE = "com.android.server.telecom.flags.early_binding_to_incall_service";
    public static final String FLAG_EARLY_UPDATE_INTERNAL_CALL_AUDIO_STATE = "com.android.server.telecom.flags.early_update_internal_call_audio_state";
    public static final String FLAG_ECC_KEYGUARD = "com.android.server.telecom.flags.ecc_keyguard";
    public static final String FLAG_ENABLE_CALL_AUDIO_WATCHDOG = "com.android.server.telecom.flags.enable_call_audio_watchdog";
    public static final String FLAG_ENABLE_CALL_EXCEPTION_ANOM_REPORTS = "com.android.server.telecom.flags.enable_call_exception_anom_reports";
    public static final String FLAG_ENABLE_CALL_SEQUENCING = "com.android.server.telecom.flags.enable_call_sequencing";
    public static final String FLAG_ENABLE_RESPOND_VIA_SMS_MANAGER_ASYNC = "com.android.server.telecom.flags.enable_respond_via_sms_manager_async";
    public static final String FLAG_END_SESSION_IMPROVEMENTS = "com.android.server.telecom.flags.end_session_improvements";
    public static final String FLAG_ENFORCE_TRANSACTIONAL_EXCLUSIVITY = "com.android.server.telecom.flags.enforce_transactional_exclusivity";
    public static final String FLAG_ENSURE_AUDIO_MODE_UPDATES_ON_FOREGROUND_CALL_CHANGE = "com.android.server.telecom.flags.ensure_audio_mode_updates_on_foreground_call_change";
    public static final String FLAG_ENSURE_IN_CAR_RINGING = "com.android.server.telecom.flags.ensure_in_car_ringing";
    public static final String FLAG_FIX_AUDIO_FLICKER_FOR_OUTGOING_CALLS = "com.android.server.telecom.flags.fix_audio_flicker_for_outgoing_calls";
    public static final String FLAG_FIX_USER_REQUEST_BASELINE_ROUTE_VIDEO_CALL = "com.android.server.telecom.flags.fix_user_request_baseline_route_video_call";
    public static final String FLAG_GEN_ANOM_REPORT_ON_FOCUS_TIMEOUT = "com.android.server.telecom.flags.gen_anom_report_on_focus_timeout";
    public static final String FLAG_GET_LAST_KNOWN_CELL_IDENTITY = "com.android.server.telecom.flags.get_last_known_cell_identity";
    public static final String FLAG_GET_REGISTERED_PHONE_ACCOUNTS = "com.android.server.telecom.flags.get_registered_phone_accounts";
    public static final String FLAG_GET_RINGER_MODE_ANOM_REPORT = "com.android.server.telecom.flags.get_ringer_mode_anom_report";
    public static final String FLAG_IGNORE_AUTO_ROUTE_TO_WATCH_DEVICE = "com.android.server.telecom.flags.ignore_auto_route_to_watch_device";
    public static final String FLAG_IS_NEW_OUTGOING_CALL_BROADCAST_UNBLOCKING = "com.android.server.telecom.flags.is_new_outgoing_call_broadcast_unblocking";
    public static final String FLAG_KEEP_BLUETOOTH_DEVICES_CACHE_UPDATED = "com.android.server.telecom.flags.keep_bluetooth_devices_cache_updated";
    public static final String FLAG_MAYBE_DEFAULT_SPEAKER_AFTER_UNHOLD = "com.android.server.telecom.flags.maybe_default_speaker_after_unhold";
    public static final String FLAG_NEW_AUDIO_PATH_SPEAKER_BROADCAST_AND_UNFOCUSED_ROUTING = "com.android.server.telecom.flags.new_audio_path_speaker_broadcast_and_unfocused_routing";
    public static final String FLAG_ONLY_CLEAR_COMMUNICATION_DEVICE_ON_INACTIVE = "com.android.server.telecom.flags.only_clear_communication_device_on_inactive";
    public static final String FLAG_ONLY_UPDATE_TELEPHONY_ON_VALID_SUB_IDS = "com.android.server.telecom.flags.only_update_telephony_on_valid_sub_ids";
    public static final String FLAG_ON_CALL_ENDPOINT_CHANGED_ICS_ON_CONNECTED = "com.android.server.telecom.flags.on_call_endpoint_changed_ics_on_connected";
    public static final String FLAG_POSTPONE_REGISTER_TO_LEAUDIO = "com.android.server.telecom.flags.postpone_register_to_leaudio";
    public static final String FLAG_PREVENT_REDUNDANT_LOCATION_PERMISSION_GRANT_AND_REVOKE = "com.android.server.telecom.flags.prevent_redundant_location_permission_grant_and_revoke";
    public static final String FLAG_PROFILE_USER_SUPPORT = "com.android.server.telecom.flags.profile_user_support";
    public static final String FLAG_REMAP_TRANSACTIONAL_CAPABILITIES = "com.android.server.telecom.flags.remap_transactional_capabilities";
    public static final String FLAG_RESET_MUTE_WHEN_ENTERING_QUIESCENT_BT_ROUTE = "com.android.server.telecom.flags.reset_mute_when_entering_quiescent_bt_route";
    public static final String FLAG_RESOLVE_ACTIVE_BT_ROUTING_AND_BT_TIMING_ISSUE = "com.android.server.telecom.flags.resolve_active_bt_routing_and_bt_timing_issue";
    public static final String FLAG_RESOLVE_SWITCHING_BT_DEVICES_COMPUTATION = "com.android.server.telecom.flags.resolve_switching_bt_devices_computation";
    public static final String FLAG_SELECT_PHONE_ACCOUNT_BEFORE_MAKING_ROOM = "com.android.server.telecom.flags.select_phone_account_before_making_room";
    public static final String FLAG_SEPARATELY_BIND_TO_BT_INCALL_SERVICE = "com.android.server.telecom.flags.separately_bind_to_bt_incall_service";
    public static final String FLAG_SET_AUDIO_MODE_BEFORE_ABANDON_FOCUS = "com.android.server.telecom.flags.set_audio_mode_before_abandon_focus";
    public static final String FLAG_SET_MUTE_STATE = "com.android.server.telecom.flags.set_mute_state";
    public static final String FLAG_SET_REMOTE_CONNECTION_CALL_ID = "com.android.server.telecom.flags.set_remote_connection_call_id";
    public static final String FLAG_SKIP_BASELINE_SWITCH_WHEN_ROUTE_NOT_BLUETOOTH = "com.android.server.telecom.flags.skip_baseline_switch_when_route_not_bluetooth";
    public static final String FLAG_SKIP_FILTER_PHONE_ACCOUNT_PERFORM_DND_FILTER = "com.android.server.telecom.flags.skip_filter_phone_account_perform_dnd_filter";
    public static final String FLAG_TELECOM_APP_LABEL_PROXY_HSUM_AWARE = "com.android.server.telecom.flags.telecom_app_label_proxy_hsum_aware";
    public static final String FLAG_TELECOM_LOG_EXTERNAL_WEARABLE_CALLS = "com.android.server.telecom.flags.telecom_log_external_wearable_calls";
    public static final String FLAG_TELECOM_MAINLINE_BLOCKED_NUMBERS_MANAGER = "com.android.server.telecom.flags.telecom_mainline_blocked_numbers_manager";
    public static final String FLAG_TELECOM_MAIN_USER_IN_BLOCK_CHECK = "com.android.server.telecom.flags.telecom_main_user_in_block_check";
    public static final String FLAG_TELECOM_MAIN_USER_IN_GET_RESPOND_MESSAGE_APP = "com.android.server.telecom.flags.telecom_main_user_in_get_respond_message_app";
    public static final String FLAG_TELECOM_METRICS_SUPPORT = "com.android.server.telecom.flags.telecom_metrics_support";
    public static final String FLAG_TELECOM_RESOLVE_HIDDEN_DEPENDENCIES = "com.android.server.telecom.flags.telecom_resolve_hidden_dependencies";
    public static final String FLAG_TELECOM_SKIP_LOG_BASED_ON_EXTRA = "com.android.server.telecom.flags.telecom_skip_log_based_on_extra";
    public static final String FLAG_TELEPHONY_HAS_DEFAULT_BUT_TELECOM_DOES_NOT = "com.android.server.telecom.flags.telephony_has_default_but_telecom_does_not";
    public static final String FLAG_TRANSACTIONAL_CS_VERIFIER = "com.android.server.telecom.flags.transactional_cs_verifier";
    public static final String FLAG_TRANSACTIONAL_HOLD_DISCONNECTS_UNHOLDABLE = "com.android.server.telecom.flags.transactional_hold_disconnects_unholdable";
    public static final String FLAG_TRANSACTIONAL_VIDEO_STATE = "com.android.server.telecom.flags.transactional_video_state";
    public static final String FLAG_TRANSIT_ROUTE_BEFORE_AUDIO_DISCONNECT_BT = "com.android.server.telecom.flags.transit_route_before_audio_disconnect_bt";
    public static final String FLAG_UNREGISTER_UNRESOLVABLE_ACCOUNTS = "com.android.server.telecom.flags.unregister_unresolvable_accounts";
    public static final String FLAG_UPDATED_RCS_CALL_COUNT_TRACKING = "com.android.server.telecom.flags.updated_rcs_call_count_tracking";
    public static final String FLAG_UPDATE_PREFERRED_AUDIO_DEVICE_LOGIC = "com.android.server.telecom.flags.update_preferred_audio_device_logic";
    public static final String FLAG_UPDATE_ROUTE_MASK_WHEN_BT_CONNECTED = "com.android.server.telecom.flags.update_route_mask_when_bt_connected";
    public static final String FLAG_USE_ACTUAL_ADDRESS_TO_ENTER_CONNECTING_STATE = "com.android.server.telecom.flags.use_actual_address_to_enter_connecting_state";
    public static final String FLAG_USE_DEVICE_PROVIDED_SERIALIZED_RINGER_VIBRATION = "com.android.server.telecom.flags.use_device_provided_serialized_ringer_vibration";
    public static final String FLAG_USE_IMPROVED_LISTENER_ORDER = "com.android.server.telecom.flags.use_improved_listener_order";
    public static final String FLAG_USE_REFACTORED_AUDIO_ROUTE_SWITCHING = "com.android.server.telecom.flags.use_refactored_audio_route_switching";
    public static final String FLAG_USE_STREAM_VOICE_CALL_TONES = "com.android.server.telecom.flags.use_stream_voice_call_tones";
    public static final String FLAG_VOIP_APP_ACTIONS_SUPPORT = "com.android.server.telecom.flags.voip_app_actions_support";
    public static final String FLAG_VOIP_CALL_MONITOR_REFACTOR = "com.android.server.telecom.flags.voip_call_monitor_refactor";

    public static boolean addCallUriForMissedCalls() {
        return FEATURE_FLAGS.addCallUriForMissedCalls();
    }

    public static boolean allowSystemAppsResolveVoipCalls() {
        return FEATURE_FLAGS.allowSystemAppsResolveVoipCalls();
    }

    public static boolean associatedUserRefactorForWorkProfile() {
        return FEATURE_FLAGS.associatedUserRefactorForWorkProfile();
    }

    public static boolean availableRoutesNeverUpdatedAfterSetSystemAudioState() {
        return FEATURE_FLAGS.availableRoutesNeverUpdatedAfterSetSystemAudioState();
    }

    public static boolean busDeviceIsASpeaker() {
        return FEATURE_FLAGS.busDeviceIsASpeaker();
    }

    public static boolean businessCallComposer() {
        return FEATURE_FLAGS.businessCallComposer();
    }

    public static boolean bypassHoldForEccDial() {
        return FEATURE_FLAGS.bypassHoldForEccDial();
    }

    public static boolean cacheCallAudioCallbacks() {
        return FEATURE_FLAGS.cacheCallAudioCallbacks();
    }

    public static boolean cacheCallEvents() {
        return FEATURE_FLAGS.cacheCallEvents();
    }

    public static boolean callAudioCommunicationDeviceRefactor() {
        return FEATURE_FLAGS.callAudioCommunicationDeviceRefactor();
    }

    public static boolean callAudioRoutingPerformanceImprovemenent() {
        return FEATURE_FLAGS.callAudioRoutingPerformanceImprovemenent();
    }

    public static boolean callDetailsIdChanges() {
        return FEATURE_FLAGS.callDetailsIdChanges();
    }

    public static boolean callSequencingCallResumeFailed() {
        return FEATURE_FLAGS.callSequencingCallResumeFailed();
    }

    public static boolean cancelRemovalOnEmergencyRedial() {
        return FEATURE_FLAGS.cancelRemovalOnEmergencyRedial();
    }

    public static boolean checkCompletedFiltersOnTimeout() {
        return FEATURE_FLAGS.checkCompletedFiltersOnTimeout();
    }

    public static boolean checkDeviceTypeOnRouteChange() {
        return FEATURE_FLAGS.checkDeviceTypeOnRouteChange();
    }

    public static boolean clearCommunicationDeviceAfterAudioOpsComplete() {
        return FEATURE_FLAGS.clearCommunicationDeviceAfterAudioOpsComplete();
    }

    public static boolean communicationDeviceProtectedByLock() {
        return FEATURE_FLAGS.communicationDeviceProtectedByLock();
    }

    public static boolean cswServiceInterfaceIsNull() {
        return FEATURE_FLAGS.cswServiceInterfaceIsNull();
    }

    public static boolean disconnectSelfManagedStuckStartupCalls() {
        return FEATURE_FLAGS.disconnectSelfManagedStuckStartupCalls();
    }

    public static boolean doNotSendCallToNullIcs() {
        return FEATURE_FLAGS.doNotSendCallToNullIcs();
    }

    public static boolean dontTimeoutDestroyedCalls() {
        return FEATURE_FLAGS.dontTimeoutDestroyedCalls();
    }

    public static boolean dontUseCommunicationDeviceTracker() {
        return FEATURE_FLAGS.dontUseCommunicationDeviceTracker();
    }

    public static boolean earlyBindingToIncallService() {
        return FEATURE_FLAGS.earlyBindingToIncallService();
    }

    public static boolean earlyUpdateInternalCallAudioState() {
        return FEATURE_FLAGS.earlyUpdateInternalCallAudioState();
    }

    public static boolean eccKeyguard() {
        return FEATURE_FLAGS.eccKeyguard();
    }

    public static boolean enableCallAudioWatchdog() {
        return FEATURE_FLAGS.enableCallAudioWatchdog();
    }

    public static boolean enableCallExceptionAnomReports() {
        return FEATURE_FLAGS.enableCallExceptionAnomReports();
    }

    public static boolean enableCallSequencing() {
        return FEATURE_FLAGS.enableCallSequencing();
    }

    public static boolean enableRespondViaSmsManagerAsync() {
        return FEATURE_FLAGS.enableRespondViaSmsManagerAsync();
    }

    public static boolean endSessionImprovements() {
        return FEATURE_FLAGS.endSessionImprovements();
    }

    public static boolean enforceTransactionalExclusivity() {
        return FEATURE_FLAGS.enforceTransactionalExclusivity();
    }

    public static boolean ensureAudioModeUpdatesOnForegroundCallChange() {
        return FEATURE_FLAGS.ensureAudioModeUpdatesOnForegroundCallChange();
    }

    public static boolean ensureInCarRinging() {
        return FEATURE_FLAGS.ensureInCarRinging();
    }

    public static boolean fixAudioFlickerForOutgoingCalls() {
        return FEATURE_FLAGS.fixAudioFlickerForOutgoingCalls();
    }

    public static boolean fixUserRequestBaselineRouteVideoCall() {
        return FEATURE_FLAGS.fixUserRequestBaselineRouteVideoCall();
    }

    public static boolean genAnomReportOnFocusTimeout() {
        return FEATURE_FLAGS.genAnomReportOnFocusTimeout();
    }

    public static boolean getLastKnownCellIdentity() {
        return FEATURE_FLAGS.getLastKnownCellIdentity();
    }

    public static boolean getRegisteredPhoneAccounts() {
        return FEATURE_FLAGS.getRegisteredPhoneAccounts();
    }

    public static boolean getRingerModeAnomReport() {
        return FEATURE_FLAGS.getRingerModeAnomReport();
    }

    public static boolean ignoreAutoRouteToWatchDevice() {
        return FEATURE_FLAGS.ignoreAutoRouteToWatchDevice();
    }

    public static boolean isNewOutgoingCallBroadcastUnblocking() {
        return FEATURE_FLAGS.isNewOutgoingCallBroadcastUnblocking();
    }

    public static boolean keepBluetoothDevicesCacheUpdated() {
        return FEATURE_FLAGS.keepBluetoothDevicesCacheUpdated();
    }

    public static boolean maybeDefaultSpeakerAfterUnhold() {
        return FEATURE_FLAGS.maybeDefaultSpeakerAfterUnhold();
    }

    public static boolean newAudioPathSpeakerBroadcastAndUnfocusedRouting() {
        return FEATURE_FLAGS.newAudioPathSpeakerBroadcastAndUnfocusedRouting();
    }

    public static boolean onCallEndpointChangedIcsOnConnected() {
        return FEATURE_FLAGS.onCallEndpointChangedIcsOnConnected();
    }

    public static boolean onlyClearCommunicationDeviceOnInactive() {
        return FEATURE_FLAGS.onlyClearCommunicationDeviceOnInactive();
    }

    public static boolean onlyUpdateTelephonyOnValidSubIds() {
        return FEATURE_FLAGS.onlyUpdateTelephonyOnValidSubIds();
    }

    public static boolean postponeRegisterToLeaudio() {
        return FEATURE_FLAGS.postponeRegisterToLeaudio();
    }

    public static boolean preventRedundantLocationPermissionGrantAndRevoke() {
        return FEATURE_FLAGS.preventRedundantLocationPermissionGrantAndRevoke();
    }

    public static boolean profileUserSupport() {
        return FEATURE_FLAGS.profileUserSupport();
    }

    public static boolean remapTransactionalCapabilities() {
        return FEATURE_FLAGS.remapTransactionalCapabilities();
    }

    public static boolean resetMuteWhenEnteringQuiescentBtRoute() {
        return FEATURE_FLAGS.resetMuteWhenEnteringQuiescentBtRoute();
    }

    public static boolean resolveActiveBtRoutingAndBtTimingIssue() {
        return FEATURE_FLAGS.resolveActiveBtRoutingAndBtTimingIssue();
    }

    public static boolean resolveSwitchingBtDevicesComputation() {
        return FEATURE_FLAGS.resolveSwitchingBtDevicesComputation();
    }

    public static boolean selectPhoneAccountBeforeMakingRoom() {
        return FEATURE_FLAGS.selectPhoneAccountBeforeMakingRoom();
    }

    public static boolean separatelyBindToBtIncallService() {
        return FEATURE_FLAGS.separatelyBindToBtIncallService();
    }

    public static boolean setAudioModeBeforeAbandonFocus() {
        return FEATURE_FLAGS.setAudioModeBeforeAbandonFocus();
    }

    public static boolean setMuteState() {
        return FEATURE_FLAGS.setMuteState();
    }

    public static boolean setRemoteConnectionCallId() {
        return FEATURE_FLAGS.setRemoteConnectionCallId();
    }

    public static boolean skipBaselineSwitchWhenRouteNotBluetooth() {
        return FEATURE_FLAGS.skipBaselineSwitchWhenRouteNotBluetooth();
    }

    public static boolean skipFilterPhoneAccountPerformDndFilter() {
        return FEATURE_FLAGS.skipFilterPhoneAccountPerformDndFilter();
    }

    public static boolean telecomAppLabelProxyHsumAware() {
        return FEATURE_FLAGS.telecomAppLabelProxyHsumAware();
    }

    public static boolean telecomLogExternalWearableCalls() {
        return FEATURE_FLAGS.telecomLogExternalWearableCalls();
    }

    public static boolean telecomMainUserInBlockCheck() {
        return FEATURE_FLAGS.telecomMainUserInBlockCheck();
    }

    public static boolean telecomMainUserInGetRespondMessageApp() {
        return FEATURE_FLAGS.telecomMainUserInGetRespondMessageApp();
    }

    public static boolean telecomMainlineBlockedNumbersManager() {
        return FEATURE_FLAGS.telecomMainlineBlockedNumbersManager();
    }

    public static boolean telecomMetricsSupport() {
        return FEATURE_FLAGS.telecomMetricsSupport();
    }

    public static boolean telecomResolveHiddenDependencies() {
        return FEATURE_FLAGS.telecomResolveHiddenDependencies();
    }

    public static boolean telecomSkipLogBasedOnExtra() {
        return FEATURE_FLAGS.telecomSkipLogBasedOnExtra();
    }

    public static boolean telephonyHasDefaultButTelecomDoesNot() {
        return FEATURE_FLAGS.telephonyHasDefaultButTelecomDoesNot();
    }

    public static boolean transactionalCsVerifier() {
        return FEATURE_FLAGS.transactionalCsVerifier();
    }

    public static boolean transactionalHoldDisconnectsUnholdable() {
        return FEATURE_FLAGS.transactionalHoldDisconnectsUnholdable();
    }

    public static boolean transactionalVideoState() {
        return FEATURE_FLAGS.transactionalVideoState();
    }

    public static boolean transitRouteBeforeAudioDisconnectBt() {
        return FEATURE_FLAGS.transitRouteBeforeAudioDisconnectBt();
    }

    public static boolean unregisterUnresolvableAccounts() {
        return FEATURE_FLAGS.unregisterUnresolvableAccounts();
    }

    public static boolean updatePreferredAudioDeviceLogic() {
        return FEATURE_FLAGS.updatePreferredAudioDeviceLogic();
    }

    public static boolean updateRouteMaskWhenBtConnected() {
        return FEATURE_FLAGS.updateRouteMaskWhenBtConnected();
    }

    public static boolean updatedRcsCallCountTracking() {
        return FEATURE_FLAGS.updatedRcsCallCountTracking();
    }

    public static boolean useActualAddressToEnterConnectingState() {
        return FEATURE_FLAGS.useActualAddressToEnterConnectingState();
    }

    public static boolean useDeviceProvidedSerializedRingerVibration() {
        return FEATURE_FLAGS.useDeviceProvidedSerializedRingerVibration();
    }

    public static boolean useImprovedListenerOrder() {
        return FEATURE_FLAGS.useImprovedListenerOrder();
    }

    public static boolean useRefactoredAudioRouteSwitching() {
        return FEATURE_FLAGS.useRefactoredAudioRouteSwitching();
    }

    public static boolean useStreamVoiceCallTones() {
        return FEATURE_FLAGS.useStreamVoiceCallTones();
    }

    public static boolean voipAppActionsSupport() {
        return FEATURE_FLAGS.voipAppActionsSupport();
    }

    public static boolean voipCallMonitorRefactor() {
        return FEATURE_FLAGS.voipCallMonitorRefactor();
    }
}
