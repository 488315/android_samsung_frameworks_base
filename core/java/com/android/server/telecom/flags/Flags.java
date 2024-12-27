package com.android.server.telecom.flags;

public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ADD_CALL_URI_FOR_MISSED_CALLS =
            "com.android.server.telecom.flags.add_call_uri_for_missed_calls";
    public static final String FLAG_ASSOCIATED_USER_REFACTOR_FOR_WORK_PROFILE =
            "com.android.server.telecom.flags.associated_user_refactor_for_work_profile";
    public static final String FLAG_AVAILABLE_ROUTES_NEVER_UPDATED_AFTER_SET_SYSTEM_AUDIO_STATE =
            "com.android.server.telecom.flags.available_routes_never_updated_after_set_system_audio_state";
    public static final String FLAG_BUSINESS_CALL_COMPOSER =
            "com.android.server.telecom.flags.business_call_composer";
    public static final String FLAG_CACHE_CALL_AUDIO_CALLBACKS =
            "com.android.server.telecom.flags.cache_call_audio_callbacks";
    public static final String FLAG_CALL_AUDIO_COMMUNICATION_DEVICE_REFACTOR =
            "com.android.server.telecom.flags.call_audio_communication_device_refactor";
    public static final String FLAG_CALL_DETAILS_ID_CHANGES =
            "com.android.server.telecom.flags.call_details_id_changes";
    public static final String FLAG_CANCEL_REMOVAL_ON_EMERGENCY_REDIAL =
            "com.android.server.telecom.flags.cancel_removal_on_emergency_redial";
    public static final String FLAG_CLEAR_COMMUNICATION_DEVICE_AFTER_AUDIO_OPS_COMPLETE =
            "com.android.server.telecom.flags.clear_communication_device_after_audio_ops_complete";
    public static final String FLAG_COMMUNICATION_DEVICE_PROTECTED_BY_LOCK =
            "com.android.server.telecom.flags.communication_device_protected_by_lock";
    public static final String FLAG_EARLY_BINDING_TO_INCALL_SERVICE =
            "com.android.server.telecom.flags.early_binding_to_incall_service";
    public static final String FLAG_EARLY_UPDATE_INTERNAL_CALL_AUDIO_STATE =
            "com.android.server.telecom.flags.early_update_internal_call_audio_state";
    public static final String FLAG_ECC_KEYGUARD = "com.android.server.telecom.flags.ecc_keyguard";
    public static final String FLAG_ENABLE_CALL_SEQUENCING =
            "com.android.server.telecom.flags.enable_call_sequencing";
    public static final String FLAG_ENSURE_AUDIO_MODE_UPDATES_ON_FOREGROUND_CALL_CHANGE =
            "com.android.server.telecom.flags.ensure_audio_mode_updates_on_foreground_call_change";
    public static final String FLAG_FIX_AUDIO_FLICKER_FOR_OUTGOING_CALLS =
            "com.android.server.telecom.flags.fix_audio_flicker_for_outgoing_calls";
    public static final String FLAG_GEN_ANOM_REPORT_ON_FOCUS_TIMEOUT =
            "com.android.server.telecom.flags.gen_anom_report_on_focus_timeout";
    public static final String FLAG_GET_LAST_KNOWN_CELL_IDENTITY =
            "com.android.server.telecom.flags.get_last_known_cell_identity";
    public static final String FLAG_GET_REGISTERED_PHONE_ACCOUNTS =
            "com.android.server.telecom.flags.get_registered_phone_accounts";
    public static final String FLAG_IGNORE_AUTO_ROUTE_TO_WATCH_DEVICE =
            "com.android.server.telecom.flags.ignore_auto_route_to_watch_device";
    public static final String FLAG_IS_NEW_OUTGOING_CALL_BROADCAST_UNBLOCKING =
            "com.android.server.telecom.flags.is_new_outgoing_call_broadcast_unblocking";
    public static final String FLAG_ONLY_UPDATE_TELEPHONY_ON_VALID_SUB_IDS =
            "com.android.server.telecom.flags.only_update_telephony_on_valid_sub_ids";
    public static final String FLAG_POSTPONE_REGISTER_TO_LEAUDIO =
            "com.android.server.telecom.flags.postpone_register_to_leaudio";
    public static final String FLAG_PROFILE_USER_SUPPORT =
            "com.android.server.telecom.flags.profile_user_support";
    public static final String FLAG_RESET_MUTE_WHEN_ENTERING_QUIESCENT_BT_ROUTE =
            "com.android.server.telecom.flags.reset_mute_when_entering_quiescent_bt_route";
    public static final String FLAG_RESOLVE_SWITCHING_BT_DEVICES_COMPUTATION =
            "com.android.server.telecom.flags.resolve_switching_bt_devices_computation";
    public static final String FLAG_SEPARATELY_BIND_TO_BT_INCALL_SERVICE =
            "com.android.server.telecom.flags.separately_bind_to_bt_incall_service";
    public static final String FLAG_SET_AUDIO_MODE_BEFORE_ABANDON_FOCUS =
            "com.android.server.telecom.flags.set_audio_mode_before_abandon_focus";
    public static final String FLAG_SET_MUTE_STATE =
            "com.android.server.telecom.flags.set_mute_state";
    public static final String FLAG_SET_REMOTE_CONNECTION_CALL_ID =
            "com.android.server.telecom.flags.set_remote_connection_call_id";
    public static final String FLAG_SKIP_FILTER_PHONE_ACCOUNT_PERFORM_DND_FILTER =
            "com.android.server.telecom.flags.skip_filter_phone_account_perform_dnd_filter";
    public static final String FLAG_TELECOM_LOG_EXTERNAL_WEARABLE_CALLS =
            "com.android.server.telecom.flags.telecom_log_external_wearable_calls";
    public static final String FLAG_TELECOM_MAINLINE_BLOCKED_NUMBERS_MANAGER =
            "com.android.server.telecom.flags.telecom_mainline_blocked_numbers_manager";
    public static final String FLAG_TELECOM_RESOLVE_HIDDEN_DEPENDENCIES =
            "com.android.server.telecom.flags.telecom_resolve_hidden_dependencies";
    public static final String FLAG_TELECOM_SKIP_LOG_BASED_ON_EXTRA =
            "com.android.server.telecom.flags.telecom_skip_log_based_on_extra";
    public static final String FLAG_TELEPHONY_HAS_DEFAULT_BUT_TELECOM_DOES_NOT =
            "com.android.server.telecom.flags.telephony_has_default_but_telecom_does_not";
    public static final String FLAG_TRANSACTIONAL_CS_VERIFIER =
            "com.android.server.telecom.flags.transactional_cs_verifier";
    public static final String FLAG_TRANSACTIONAL_HOLD_DISCONNECTS_UNHOLDABLE =
            "com.android.server.telecom.flags.transactional_hold_disconnects_unholdable";
    public static final String FLAG_TRANSACTIONAL_VIDEO_STATE =
            "com.android.server.telecom.flags.transactional_video_state";
    public static final String FLAG_TRANSIT_ROUTE_BEFORE_AUDIO_DISCONNECT_BT =
            "com.android.server.telecom.flags.transit_route_before_audio_disconnect_bt";
    public static final String FLAG_UNREGISTER_UNRESOLVABLE_ACCOUNTS =
            "com.android.server.telecom.flags.unregister_unresolvable_accounts";
    public static final String FLAG_UPDATED_RCS_CALL_COUNT_TRACKING =
            "com.android.server.telecom.flags.updated_rcs_call_count_tracking";
    public static final String FLAG_UPDATE_ROUTE_MASK_WHEN_BT_CONNECTED =
            "com.android.server.telecom.flags.update_route_mask_when_bt_connected";
    public static final String FLAG_USE_ACTUAL_ADDRESS_TO_ENTER_CONNECTING_STATE =
            "com.android.server.telecom.flags.use_actual_address_to_enter_connecting_state";
    public static final String FLAG_USE_DEVICE_PROVIDED_SERIALIZED_RINGER_VIBRATION =
            "com.android.server.telecom.flags.use_device_provided_serialized_ringer_vibration";
    public static final String FLAG_USE_IMPROVED_LISTENER_ORDER =
            "com.android.server.telecom.flags.use_improved_listener_order";
    public static final String FLAG_USE_REFACTORED_AUDIO_ROUTE_SWITCHING =
            "com.android.server.telecom.flags.use_refactored_audio_route_switching";
    public static final String FLAG_VOIP_APP_ACTIONS_SUPPORT =
            "com.android.server.telecom.flags.voip_app_actions_support";

    public static boolean addCallUriForMissedCalls() {
        return FEATURE_FLAGS.addCallUriForMissedCalls();
    }

    public static boolean associatedUserRefactorForWorkProfile() {
        return FEATURE_FLAGS.associatedUserRefactorForWorkProfile();
    }

    public static boolean availableRoutesNeverUpdatedAfterSetSystemAudioState() {
        return FEATURE_FLAGS.availableRoutesNeverUpdatedAfterSetSystemAudioState();
    }

    public static boolean businessCallComposer() {
        return FEATURE_FLAGS.businessCallComposer();
    }

    public static boolean cacheCallAudioCallbacks() {
        return FEATURE_FLAGS.cacheCallAudioCallbacks();
    }

    public static boolean callAudioCommunicationDeviceRefactor() {
        return FEATURE_FLAGS.callAudioCommunicationDeviceRefactor();
    }

    public static boolean callDetailsIdChanges() {
        return FEATURE_FLAGS.callDetailsIdChanges();
    }

    public static boolean cancelRemovalOnEmergencyRedial() {
        return FEATURE_FLAGS.cancelRemovalOnEmergencyRedial();
    }

    public static boolean clearCommunicationDeviceAfterAudioOpsComplete() {
        return FEATURE_FLAGS.clearCommunicationDeviceAfterAudioOpsComplete();
    }

    public static boolean communicationDeviceProtectedByLock() {
        return FEATURE_FLAGS.communicationDeviceProtectedByLock();
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

    public static boolean enableCallSequencing() {
        return FEATURE_FLAGS.enableCallSequencing();
    }

    public static boolean ensureAudioModeUpdatesOnForegroundCallChange() {
        return FEATURE_FLAGS.ensureAudioModeUpdatesOnForegroundCallChange();
    }

    public static boolean fixAudioFlickerForOutgoingCalls() {
        return FEATURE_FLAGS.fixAudioFlickerForOutgoingCalls();
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

    public static boolean ignoreAutoRouteToWatchDevice() {
        return FEATURE_FLAGS.ignoreAutoRouteToWatchDevice();
    }

    public static boolean isNewOutgoingCallBroadcastUnblocking() {
        return FEATURE_FLAGS.isNewOutgoingCallBroadcastUnblocking();
    }

    public static boolean onlyUpdateTelephonyOnValidSubIds() {
        return FEATURE_FLAGS.onlyUpdateTelephonyOnValidSubIds();
    }

    public static boolean postponeRegisterToLeaudio() {
        return FEATURE_FLAGS.postponeRegisterToLeaudio();
    }

    public static boolean profileUserSupport() {
        return FEATURE_FLAGS.profileUserSupport();
    }

    public static boolean resetMuteWhenEnteringQuiescentBtRoute() {
        return FEATURE_FLAGS.resetMuteWhenEnteringQuiescentBtRoute();
    }

    public static boolean resolveSwitchingBtDevicesComputation() {
        return FEATURE_FLAGS.resolveSwitchingBtDevicesComputation();
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

    public static boolean skipFilterPhoneAccountPerformDndFilter() {
        return FEATURE_FLAGS.skipFilterPhoneAccountPerformDndFilter();
    }

    public static boolean telecomLogExternalWearableCalls() {
        return FEATURE_FLAGS.telecomLogExternalWearableCalls();
    }

    public static boolean telecomMainlineBlockedNumbersManager() {
        return FEATURE_FLAGS.telecomMainlineBlockedNumbersManager();
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

    public static boolean voipAppActionsSupport() {
        return FEATURE_FLAGS.voipAppActionsSupport();
    }
}
