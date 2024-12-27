package com.android.server.telecom.flags;

public interface FeatureFlags {
    boolean addCallUriForMissedCalls();

    boolean associatedUserRefactorForWorkProfile();

    boolean availableRoutesNeverUpdatedAfterSetSystemAudioState();

    boolean businessCallComposer();

    boolean cacheCallAudioCallbacks();

    boolean callAudioCommunicationDeviceRefactor();

    boolean callDetailsIdChanges();

    boolean cancelRemovalOnEmergencyRedial();

    boolean clearCommunicationDeviceAfterAudioOpsComplete();

    boolean communicationDeviceProtectedByLock();

    boolean earlyBindingToIncallService();

    boolean earlyUpdateInternalCallAudioState();

    boolean eccKeyguard();

    boolean enableCallSequencing();

    boolean ensureAudioModeUpdatesOnForegroundCallChange();

    boolean fixAudioFlickerForOutgoingCalls();

    boolean genAnomReportOnFocusTimeout();

    boolean getLastKnownCellIdentity();

    boolean getRegisteredPhoneAccounts();

    boolean ignoreAutoRouteToWatchDevice();

    boolean isNewOutgoingCallBroadcastUnblocking();

    boolean onlyUpdateTelephonyOnValidSubIds();

    boolean postponeRegisterToLeaudio();

    boolean profileUserSupport();

    boolean resetMuteWhenEnteringQuiescentBtRoute();

    boolean resolveSwitchingBtDevicesComputation();

    boolean separatelyBindToBtIncallService();

    boolean setAudioModeBeforeAbandonFocus();

    boolean setMuteState();

    boolean setRemoteConnectionCallId();

    boolean skipFilterPhoneAccountPerformDndFilter();

    boolean telecomLogExternalWearableCalls();

    boolean telecomMainlineBlockedNumbersManager();

    boolean telecomResolveHiddenDependencies();

    boolean telecomSkipLogBasedOnExtra();

    boolean telephonyHasDefaultButTelecomDoesNot();

    boolean transactionalCsVerifier();

    boolean transactionalHoldDisconnectsUnholdable();

    boolean transactionalVideoState();

    boolean transitRouteBeforeAudioDisconnectBt();

    boolean unregisterUnresolvableAccounts();

    boolean updateRouteMaskWhenBtConnected();

    boolean updatedRcsCallCountTracking();

    boolean useActualAddressToEnterConnectingState();

    boolean useDeviceProvidedSerializedRingerVibration();

    boolean useImprovedListenerOrder();

    boolean useRefactoredAudioRouteSwitching();

    boolean voipAppActionsSupport();
}
