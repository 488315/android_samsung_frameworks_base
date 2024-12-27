package com.android.server.telecom.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet =
            new HashSet(
                    Arrays.asList(
                            Flags.FLAG_ADD_CALL_URI_FOR_MISSED_CALLS,
                            Flags.FLAG_ASSOCIATED_USER_REFACTOR_FOR_WORK_PROFILE,
                            Flags.FLAG_AVAILABLE_ROUTES_NEVER_UPDATED_AFTER_SET_SYSTEM_AUDIO_STATE,
                            Flags.FLAG_BUSINESS_CALL_COMPOSER,
                            Flags.FLAG_CACHE_CALL_AUDIO_CALLBACKS,
                            Flags.FLAG_CALL_AUDIO_COMMUNICATION_DEVICE_REFACTOR,
                            Flags.FLAG_CALL_DETAILS_ID_CHANGES,
                            Flags.FLAG_CANCEL_REMOVAL_ON_EMERGENCY_REDIAL,
                            Flags.FLAG_CLEAR_COMMUNICATION_DEVICE_AFTER_AUDIO_OPS_COMPLETE,
                            Flags.FLAG_COMMUNICATION_DEVICE_PROTECTED_BY_LOCK,
                            Flags.FLAG_EARLY_BINDING_TO_INCALL_SERVICE,
                            Flags.FLAG_EARLY_UPDATE_INTERNAL_CALL_AUDIO_STATE,
                            Flags.FLAG_ECC_KEYGUARD,
                            Flags.FLAG_ENABLE_CALL_SEQUENCING,
                            Flags.FLAG_ENSURE_AUDIO_MODE_UPDATES_ON_FOREGROUND_CALL_CHANGE,
                            Flags.FLAG_FIX_AUDIO_FLICKER_FOR_OUTGOING_CALLS,
                            Flags.FLAG_GEN_ANOM_REPORT_ON_FOCUS_TIMEOUT,
                            Flags.FLAG_GET_LAST_KNOWN_CELL_IDENTITY,
                            Flags.FLAG_GET_REGISTERED_PHONE_ACCOUNTS,
                            Flags.FLAG_IGNORE_AUTO_ROUTE_TO_WATCH_DEVICE,
                            Flags.FLAG_IS_NEW_OUTGOING_CALL_BROADCAST_UNBLOCKING,
                            Flags.FLAG_ONLY_UPDATE_TELEPHONY_ON_VALID_SUB_IDS,
                            Flags.FLAG_POSTPONE_REGISTER_TO_LEAUDIO,
                            Flags.FLAG_PROFILE_USER_SUPPORT,
                            Flags.FLAG_RESET_MUTE_WHEN_ENTERING_QUIESCENT_BT_ROUTE,
                            Flags.FLAG_RESOLVE_SWITCHING_BT_DEVICES_COMPUTATION,
                            Flags.FLAG_SEPARATELY_BIND_TO_BT_INCALL_SERVICE,
                            Flags.FLAG_SET_AUDIO_MODE_BEFORE_ABANDON_FOCUS,
                            Flags.FLAG_SET_MUTE_STATE,
                            Flags.FLAG_SET_REMOTE_CONNECTION_CALL_ID,
                            Flags.FLAG_SKIP_FILTER_PHONE_ACCOUNT_PERFORM_DND_FILTER,
                            Flags.FLAG_TELECOM_LOG_EXTERNAL_WEARABLE_CALLS,
                            Flags.FLAG_TELECOM_MAINLINE_BLOCKED_NUMBERS_MANAGER,
                            Flags.FLAG_TELECOM_RESOLVE_HIDDEN_DEPENDENCIES,
                            Flags.FLAG_TELECOM_SKIP_LOG_BASED_ON_EXTRA,
                            Flags.FLAG_TELEPHONY_HAS_DEFAULT_BUT_TELECOM_DOES_NOT,
                            Flags.FLAG_TRANSACTIONAL_CS_VERIFIER,
                            Flags.FLAG_TRANSACTIONAL_HOLD_DISCONNECTS_UNHOLDABLE,
                            Flags.FLAG_TRANSACTIONAL_VIDEO_STATE,
                            Flags.FLAG_TRANSIT_ROUTE_BEFORE_AUDIO_DISCONNECT_BT,
                            Flags.FLAG_UNREGISTER_UNRESOLVABLE_ACCOUNTS,
                            Flags.FLAG_UPDATE_ROUTE_MASK_WHEN_BT_CONNECTED,
                            Flags.FLAG_UPDATED_RCS_CALL_COUNT_TRACKING,
                            Flags.FLAG_USE_ACTUAL_ADDRESS_TO_ENTER_CONNECTING_STATE,
                            Flags.FLAG_USE_DEVICE_PROVIDED_SERIALIZED_RINGER_VIBRATION,
                            Flags.FLAG_USE_IMPROVED_LISTENER_ORDER,
                            Flags.FLAG_USE_REFACTORED_AUDIO_ROUTE_SWITCHING,
                            Flags.FLAG_VOIP_APP_ACTIONS_SUPPORT,
                            ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean addCallUriForMissedCalls() {
        return getValue(
                Flags.FLAG_ADD_CALL_URI_FOR_MISSED_CALLS,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda28
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).addCallUriForMissedCalls();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean associatedUserRefactorForWorkProfile() {
        return getValue(
                Flags.FLAG_ASSOCIATED_USER_REFACTOR_FOR_WORK_PROFILE,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda40
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).associatedUserRefactorForWorkProfile();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean availableRoutesNeverUpdatedAfterSetSystemAudioState() {
        return getValue(
                Flags.FLAG_AVAILABLE_ROUTES_NEVER_UPDATED_AFTER_SET_SYSTEM_AUDIO_STATE,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda27
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj)
                                .availableRoutesNeverUpdatedAfterSetSystemAudioState();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean businessCallComposer() {
        return getValue(
                Flags.FLAG_BUSINESS_CALL_COMPOSER,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).businessCallComposer();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean cacheCallAudioCallbacks() {
        return getValue(
                Flags.FLAG_CACHE_CALL_AUDIO_CALLBACKS,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda21
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).cacheCallAudioCallbacks();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean callAudioCommunicationDeviceRefactor() {
        return getValue(
                Flags.FLAG_CALL_AUDIO_COMMUNICATION_DEVICE_REFACTOR,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda14
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).callAudioCommunicationDeviceRefactor();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean callDetailsIdChanges() {
        return getValue(
                Flags.FLAG_CALL_DETAILS_ID_CHANGES,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda12
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).callDetailsIdChanges();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean cancelRemovalOnEmergencyRedial() {
        return getValue(
                Flags.FLAG_CANCEL_REMOVAL_ON_EMERGENCY_REDIAL,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda26
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).cancelRemovalOnEmergencyRedial();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean clearCommunicationDeviceAfterAudioOpsComplete() {
        return getValue(
                Flags.FLAG_CLEAR_COMMUNICATION_DEVICE_AFTER_AUDIO_OPS_COMPLETE,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda11
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).clearCommunicationDeviceAfterAudioOpsComplete();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean communicationDeviceProtectedByLock() {
        return getValue(
                Flags.FLAG_COMMUNICATION_DEVICE_PROTECTED_BY_LOCK,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda35
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).communicationDeviceProtectedByLock();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean earlyBindingToIncallService() {
        return getValue(
                Flags.FLAG_EARLY_BINDING_TO_INCALL_SERVICE,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda23
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).earlyBindingToIncallService();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean earlyUpdateInternalCallAudioState() {
        return getValue(
                Flags.FLAG_EARLY_UPDATE_INTERNAL_CALL_AUDIO_STATE,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda25
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).earlyUpdateInternalCallAudioState();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean eccKeyguard() {
        return getValue(
                Flags.FLAG_ECC_KEYGUARD,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda39
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).eccKeyguard();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean enableCallSequencing() {
        return getValue(
                Flags.FLAG_ENABLE_CALL_SEQUENCING,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda36
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableCallSequencing();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean ensureAudioModeUpdatesOnForegroundCallChange() {
        return getValue(
                Flags.FLAG_ENSURE_AUDIO_MODE_UPDATES_ON_FOREGROUND_CALL_CHANGE,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda22
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).ensureAudioModeUpdatesOnForegroundCallChange();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean fixAudioFlickerForOutgoingCalls() {
        return getValue(
                Flags.FLAG_FIX_AUDIO_FLICKER_FOR_OUTGOING_CALLS,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda43
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).fixAudioFlickerForOutgoingCalls();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean genAnomReportOnFocusTimeout() {
        return getValue(
                Flags.FLAG_GEN_ANOM_REPORT_ON_FOCUS_TIMEOUT,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda18
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).genAnomReportOnFocusTimeout();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean getLastKnownCellIdentity() {
        return getValue(
                Flags.FLAG_GET_LAST_KNOWN_CELL_IDENTITY,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).getLastKnownCellIdentity();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean getRegisteredPhoneAccounts() {
        return getValue(
                Flags.FLAG_GET_REGISTERED_PHONE_ACCOUNTS,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda47
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).getRegisteredPhoneAccounts();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean ignoreAutoRouteToWatchDevice() {
        return getValue(
                Flags.FLAG_IGNORE_AUTO_ROUTE_TO_WATCH_DEVICE,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda17
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).ignoreAutoRouteToWatchDevice();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean isNewOutgoingCallBroadcastUnblocking() {
        return getValue(
                Flags.FLAG_IS_NEW_OUTGOING_CALL_BROADCAST_UNBLOCKING,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda15
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).isNewOutgoingCallBroadcastUnblocking();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean onlyUpdateTelephonyOnValidSubIds() {
        return getValue(
                Flags.FLAG_ONLY_UPDATE_TELEPHONY_ON_VALID_SUB_IDS,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).onlyUpdateTelephonyOnValidSubIds();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean postponeRegisterToLeaudio() {
        return getValue(
                Flags.FLAG_POSTPONE_REGISTER_TO_LEAUDIO,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda10
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).postponeRegisterToLeaudio();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean profileUserSupport() {
        return getValue(
                Flags.FLAG_PROFILE_USER_SUPPORT,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda31
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).profileUserSupport();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean resetMuteWhenEnteringQuiescentBtRoute() {
        return getValue(
                Flags.FLAG_RESET_MUTE_WHEN_ENTERING_QUIESCENT_BT_ROUTE,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda29
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).resetMuteWhenEnteringQuiescentBtRoute();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean resolveSwitchingBtDevicesComputation() {
        return getValue(
                Flags.FLAG_RESOLVE_SWITCHING_BT_DEVICES_COMPUTATION,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda34
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).resolveSwitchingBtDevicesComputation();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean separatelyBindToBtIncallService() {
        return getValue(
                Flags.FLAG_SEPARATELY_BIND_TO_BT_INCALL_SERVICE,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda32
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).separatelyBindToBtIncallService();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean setAudioModeBeforeAbandonFocus() {
        return getValue(
                Flags.FLAG_SET_AUDIO_MODE_BEFORE_ABANDON_FOCUS,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda33
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).setAudioModeBeforeAbandonFocus();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean setMuteState() {
        return getValue(
                Flags.FLAG_SET_MUTE_STATE,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda24
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).setMuteState();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean setRemoteConnectionCallId() {
        return getValue(
                Flags.FLAG_SET_REMOTE_CONNECTION_CALL_ID,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda46
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).setRemoteConnectionCallId();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean skipFilterPhoneAccountPerformDndFilter() {
        return getValue(
                Flags.FLAG_SKIP_FILTER_PHONE_ACCOUNT_PERFORM_DND_FILTER,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda37
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).skipFilterPhoneAccountPerformDndFilter();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean telecomLogExternalWearableCalls() {
        return getValue(
                Flags.FLAG_TELECOM_LOG_EXTERNAL_WEARABLE_CALLS,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).telecomLogExternalWearableCalls();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean telecomMainlineBlockedNumbersManager() {
        return getValue(
                Flags.FLAG_TELECOM_MAINLINE_BLOCKED_NUMBERS_MANAGER,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda42
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).telecomMainlineBlockedNumbersManager();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean telecomResolveHiddenDependencies() {
        return getValue(
                Flags.FLAG_TELECOM_RESOLVE_HIDDEN_DEPENDENCIES,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda45
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).telecomResolveHiddenDependencies();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean telecomSkipLogBasedOnExtra() {
        return getValue(
                Flags.FLAG_TELECOM_SKIP_LOG_BASED_ON_EXTRA,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).telecomSkipLogBasedOnExtra();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean telephonyHasDefaultButTelecomDoesNot() {
        return getValue(
                Flags.FLAG_TELEPHONY_HAS_DEFAULT_BUT_TELECOM_DOES_NOT,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda20
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).telephonyHasDefaultButTelecomDoesNot();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean transactionalCsVerifier() {
        return getValue(
                Flags.FLAG_TRANSACTIONAL_CS_VERIFIER,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda16
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).transactionalCsVerifier();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean transactionalHoldDisconnectsUnholdable() {
        return getValue(
                Flags.FLAG_TRANSACTIONAL_HOLD_DISCONNECTS_UNHOLDABLE,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda30
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).transactionalHoldDisconnectsUnholdable();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean transactionalVideoState() {
        return getValue(
                Flags.FLAG_TRANSACTIONAL_VIDEO_STATE,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda38
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).transactionalVideoState();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean transitRouteBeforeAudioDisconnectBt() {
        return getValue(
                Flags.FLAG_TRANSIT_ROUTE_BEFORE_AUDIO_DISCONNECT_BT,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).transitRouteBeforeAudioDisconnectBt();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean unregisterUnresolvableAccounts() {
        return getValue(
                Flags.FLAG_UNREGISTER_UNRESOLVABLE_ACCOUNTS,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda9
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).unregisterUnresolvableAccounts();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean updateRouteMaskWhenBtConnected() {
        return getValue(
                Flags.FLAG_UPDATE_ROUTE_MASK_WHEN_BT_CONNECTED,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda8
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).updateRouteMaskWhenBtConnected();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean updatedRcsCallCountTracking() {
        return getValue(
                Flags.FLAG_UPDATED_RCS_CALL_COUNT_TRACKING,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda41
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).updatedRcsCallCountTracking();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean useActualAddressToEnterConnectingState() {
        return getValue(
                Flags.FLAG_USE_ACTUAL_ADDRESS_TO_ENTER_CONNECTING_STATE,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda19
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).useActualAddressToEnterConnectingState();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean useDeviceProvidedSerializedRingerVibration() {
        return getValue(
                Flags.FLAG_USE_DEVICE_PROVIDED_SERIALIZED_RINGER_VIBRATION,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).useDeviceProvidedSerializedRingerVibration();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean useImprovedListenerOrder() {
        return getValue(
                Flags.FLAG_USE_IMPROVED_LISTENER_ORDER,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda13
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).useImprovedListenerOrder();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean useRefactoredAudioRouteSwitching() {
        return getValue(
                Flags.FLAG_USE_REFACTORED_AUDIO_ROUTE_SWITCHING,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).useRefactoredAudioRouteSwitching();
                    }
                });
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean voipAppActionsSupport() {
        return getValue(
                Flags.FLAG_VOIP_APP_ACTIONS_SUPPORT,
                new Predicate() { // from class:
                                  // com.android.server.telecom.flags.CustomFeatureFlags$$ExternalSyntheticLambda44
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).voipAppActionsSupport();
                    }
                });
    }

    public boolean isFlagReadOnlyOptimized(String flagName) {
        if (this.mReadOnlyFlagsSet.contains(flagName) && isOptimizationEnabled()) {
            return true;
        }
        return false;
    }

    private boolean isOptimizationEnabled() {
        return false;
    }

    protected boolean getValue(String flagName, Predicate<FeatureFlags> getter) {
        return this.mGetValueImpl.test(flagName, getter);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(
                Flags.FLAG_ADD_CALL_URI_FOR_MISSED_CALLS,
                Flags.FLAG_ASSOCIATED_USER_REFACTOR_FOR_WORK_PROFILE,
                Flags.FLAG_AVAILABLE_ROUTES_NEVER_UPDATED_AFTER_SET_SYSTEM_AUDIO_STATE,
                Flags.FLAG_BUSINESS_CALL_COMPOSER,
                Flags.FLAG_CACHE_CALL_AUDIO_CALLBACKS,
                Flags.FLAG_CALL_AUDIO_COMMUNICATION_DEVICE_REFACTOR,
                Flags.FLAG_CALL_DETAILS_ID_CHANGES,
                Flags.FLAG_CANCEL_REMOVAL_ON_EMERGENCY_REDIAL,
                Flags.FLAG_CLEAR_COMMUNICATION_DEVICE_AFTER_AUDIO_OPS_COMPLETE,
                Flags.FLAG_COMMUNICATION_DEVICE_PROTECTED_BY_LOCK,
                Flags.FLAG_EARLY_BINDING_TO_INCALL_SERVICE,
                Flags.FLAG_EARLY_UPDATE_INTERNAL_CALL_AUDIO_STATE,
                Flags.FLAG_ECC_KEYGUARD,
                Flags.FLAG_ENABLE_CALL_SEQUENCING,
                Flags.FLAG_ENSURE_AUDIO_MODE_UPDATES_ON_FOREGROUND_CALL_CHANGE,
                Flags.FLAG_FIX_AUDIO_FLICKER_FOR_OUTGOING_CALLS,
                Flags.FLAG_GEN_ANOM_REPORT_ON_FOCUS_TIMEOUT,
                Flags.FLAG_GET_LAST_KNOWN_CELL_IDENTITY,
                Flags.FLAG_GET_REGISTERED_PHONE_ACCOUNTS,
                Flags.FLAG_IGNORE_AUTO_ROUTE_TO_WATCH_DEVICE,
                Flags.FLAG_IS_NEW_OUTGOING_CALL_BROADCAST_UNBLOCKING,
                Flags.FLAG_ONLY_UPDATE_TELEPHONY_ON_VALID_SUB_IDS,
                Flags.FLAG_POSTPONE_REGISTER_TO_LEAUDIO,
                Flags.FLAG_PROFILE_USER_SUPPORT,
                Flags.FLAG_RESET_MUTE_WHEN_ENTERING_QUIESCENT_BT_ROUTE,
                Flags.FLAG_RESOLVE_SWITCHING_BT_DEVICES_COMPUTATION,
                Flags.FLAG_SEPARATELY_BIND_TO_BT_INCALL_SERVICE,
                Flags.FLAG_SET_AUDIO_MODE_BEFORE_ABANDON_FOCUS,
                Flags.FLAG_SET_MUTE_STATE,
                Flags.FLAG_SET_REMOTE_CONNECTION_CALL_ID,
                Flags.FLAG_SKIP_FILTER_PHONE_ACCOUNT_PERFORM_DND_FILTER,
                Flags.FLAG_TELECOM_LOG_EXTERNAL_WEARABLE_CALLS,
                Flags.FLAG_TELECOM_MAINLINE_BLOCKED_NUMBERS_MANAGER,
                Flags.FLAG_TELECOM_RESOLVE_HIDDEN_DEPENDENCIES,
                Flags.FLAG_TELECOM_SKIP_LOG_BASED_ON_EXTRA,
                Flags.FLAG_TELEPHONY_HAS_DEFAULT_BUT_TELECOM_DOES_NOT,
                Flags.FLAG_TRANSACTIONAL_CS_VERIFIER,
                Flags.FLAG_TRANSACTIONAL_HOLD_DISCONNECTS_UNHOLDABLE,
                Flags.FLAG_TRANSACTIONAL_VIDEO_STATE,
                Flags.FLAG_TRANSIT_ROUTE_BEFORE_AUDIO_DISCONNECT_BT,
                Flags.FLAG_UNREGISTER_UNRESOLVABLE_ACCOUNTS,
                Flags.FLAG_UPDATE_ROUTE_MASK_WHEN_BT_CONNECTED,
                Flags.FLAG_UPDATED_RCS_CALL_COUNT_TRACKING,
                Flags.FLAG_USE_ACTUAL_ADDRESS_TO_ENTER_CONNECTING_STATE,
                Flags.FLAG_USE_DEVICE_PROVIDED_SERIALIZED_RINGER_VIBRATION,
                Flags.FLAG_USE_IMPROVED_LISTENER_ORDER,
                Flags.FLAG_USE_REFACTORED_AUDIO_ROUTE_SWITCHING,
                Flags.FLAG_VOIP_APP_ACTIONS_SUPPORT);
    }
}
