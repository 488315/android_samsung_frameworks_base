package com.android.server.telecom.flags;

/* loaded from: classes6.dex */
public interface FeatureFlags {
    boolean addCallUriForMissedCalls();

    boolean allowSystemAppsResolveVoipCalls();

    boolean associatedUserRefactorForWorkProfile();

    boolean availableRoutesNeverUpdatedAfterSetSystemAudioState();

    boolean busDeviceIsASpeaker();

    boolean businessCallComposer();

    boolean bypassHoldForEccDial();

    boolean cacheCallAudioCallbacks();

    boolean cacheCallEvents();

    boolean callAudioCommunicationDeviceRefactor();

    boolean callAudioRoutingPerformanceImprovemenent();

    boolean callDetailsIdChanges();

    boolean callSequencingCallResumeFailed();

    boolean cancelRemovalOnEmergencyRedial();

    boolean checkCompletedFiltersOnTimeout();

    boolean checkDeviceTypeOnRouteChange();

    boolean clearCommunicationDeviceAfterAudioOpsComplete();

    boolean communicationDeviceProtectedByLock();

    boolean cswServiceInterfaceIsNull();

    boolean disconnectSelfManagedStuckStartupCalls();

    boolean doNotSendCallToNullIcs();

    boolean dontTimeoutDestroyedCalls();

    boolean dontUseCommunicationDeviceTracker();

    boolean earlyBindingToIncallService();

    boolean earlyUpdateInternalCallAudioState();

    boolean eccKeyguard();

    boolean enableCallAudioWatchdog();

    boolean enableCallExceptionAnomReports();

    boolean enableCallSequencing();

    boolean enableRespondViaSmsManagerAsync();

    boolean endSessionImprovements();

    boolean enforceTransactionalExclusivity();

    boolean ensureAudioModeUpdatesOnForegroundCallChange();

    boolean ensureInCarRinging();

    boolean fixAudioFlickerForOutgoingCalls();

    boolean fixUserRequestBaselineRouteVideoCall();

    boolean genAnomReportOnFocusTimeout();

    boolean getLastKnownCellIdentity();

    boolean getRegisteredPhoneAccounts();

    boolean getRingerModeAnomReport();

    boolean ignoreAutoRouteToWatchDevice();

    boolean isNewOutgoingCallBroadcastUnblocking();

    boolean keepBluetoothDevicesCacheUpdated();

    boolean maybeDefaultSpeakerAfterUnhold();

    boolean newAudioPathSpeakerBroadcastAndUnfocusedRouting();

    boolean onCallEndpointChangedIcsOnConnected();

    boolean onlyClearCommunicationDeviceOnInactive();

    boolean onlyUpdateTelephonyOnValidSubIds();

    boolean postponeRegisterToLeaudio();

    boolean preventRedundantLocationPermissionGrantAndRevoke();

    boolean profileUserSupport();

    boolean remapTransactionalCapabilities();

    boolean resetMuteWhenEnteringQuiescentBtRoute();

    boolean resolveActiveBtRoutingAndBtTimingIssue();

    boolean resolveSwitchingBtDevicesComputation();

    boolean selectPhoneAccountBeforeMakingRoom();

    boolean separatelyBindToBtIncallService();

    boolean setAudioModeBeforeAbandonFocus();

    boolean setMuteState();

    boolean setRemoteConnectionCallId();

    boolean skipBaselineSwitchWhenRouteNotBluetooth();

    boolean skipFilterPhoneAccountPerformDndFilter();

    boolean telecomAppLabelProxyHsumAware();

    boolean telecomLogExternalWearableCalls();

    boolean telecomMainUserInBlockCheck();

    boolean telecomMainUserInGetRespondMessageApp();

    boolean telecomMainlineBlockedNumbersManager();

    boolean telecomMetricsSupport();

    boolean telecomResolveHiddenDependencies();

    boolean telecomSkipLogBasedOnExtra();

    boolean telephonyHasDefaultButTelecomDoesNot();

    boolean transactionalCsVerifier();

    boolean transactionalHoldDisconnectsUnholdable();

    boolean transactionalVideoState();

    boolean transitRouteBeforeAudioDisconnectBt();

    boolean unregisterUnresolvableAccounts();

    boolean updatePreferredAudioDeviceLogic();

    boolean updateRouteMaskWhenBtConnected();

    boolean updatedRcsCallCountTracking();

    boolean useActualAddressToEnterConnectingState();

    boolean useDeviceProvidedSerializedRingerVibration();

    boolean useImprovedListenerOrder();

    boolean useRefactoredAudioRouteSwitching();

    boolean useStreamVoiceCallTones();

    boolean voipAppActionsSupport();

    boolean voipCallMonitorRefactor();
}
