package com.android.server.telecom.flags;

/* loaded from: classes6.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean addCallUriForMissedCalls() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean allowSystemAppsResolveVoipCalls() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean associatedUserRefactorForWorkProfile() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean availableRoutesNeverUpdatedAfterSetSystemAudioState() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean busDeviceIsASpeaker() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean businessCallComposer() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean bypassHoldForEccDial() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean cacheCallAudioCallbacks() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean cacheCallEvents() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean callAudioCommunicationDeviceRefactor() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean callAudioRoutingPerformanceImprovemenent() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean callDetailsIdChanges() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean callSequencingCallResumeFailed() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean cancelRemovalOnEmergencyRedial() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean checkCompletedFiltersOnTimeout() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean checkDeviceTypeOnRouteChange() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean clearCommunicationDeviceAfterAudioOpsComplete() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean communicationDeviceProtectedByLock() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean cswServiceInterfaceIsNull() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean disconnectSelfManagedStuckStartupCalls() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean doNotSendCallToNullIcs() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean dontTimeoutDestroyedCalls() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean dontUseCommunicationDeviceTracker() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean earlyBindingToIncallService() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean earlyUpdateInternalCallAudioState() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean eccKeyguard() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean enableCallAudioWatchdog() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean enableCallExceptionAnomReports() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean enableCallSequencing() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean enableRespondViaSmsManagerAsync() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean endSessionImprovements() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean enforceTransactionalExclusivity() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean ensureAudioModeUpdatesOnForegroundCallChange() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean ensureInCarRinging() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean fixAudioFlickerForOutgoingCalls() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean fixUserRequestBaselineRouteVideoCall() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean genAnomReportOnFocusTimeout() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean getLastKnownCellIdentity() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean getRegisteredPhoneAccounts() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean getRingerModeAnomReport() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean ignoreAutoRouteToWatchDevice() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean isNewOutgoingCallBroadcastUnblocking() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean keepBluetoothDevicesCacheUpdated() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean maybeDefaultSpeakerAfterUnhold() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean newAudioPathSpeakerBroadcastAndUnfocusedRouting() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean onCallEndpointChangedIcsOnConnected() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean onlyClearCommunicationDeviceOnInactive() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean onlyUpdateTelephonyOnValidSubIds() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean postponeRegisterToLeaudio() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean preventRedundantLocationPermissionGrantAndRevoke() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean profileUserSupport() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean remapTransactionalCapabilities() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean resetMuteWhenEnteringQuiescentBtRoute() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean resolveActiveBtRoutingAndBtTimingIssue() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean resolveSwitchingBtDevicesComputation() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean selectPhoneAccountBeforeMakingRoom() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean separatelyBindToBtIncallService() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean setAudioModeBeforeAbandonFocus() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean setMuteState() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean setRemoteConnectionCallId() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean skipBaselineSwitchWhenRouteNotBluetooth() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean skipFilterPhoneAccountPerformDndFilter() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean telecomAppLabelProxyHsumAware() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean telecomLogExternalWearableCalls() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean telecomMainUserInBlockCheck() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean telecomMainUserInGetRespondMessageApp() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean telecomMainlineBlockedNumbersManager() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean telecomMetricsSupport() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean telecomResolveHiddenDependencies() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean telecomSkipLogBasedOnExtra() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean telephonyHasDefaultButTelecomDoesNot() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean transactionalCsVerifier() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean transactionalHoldDisconnectsUnholdable() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean transactionalVideoState() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean transitRouteBeforeAudioDisconnectBt() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean unregisterUnresolvableAccounts() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean updatePreferredAudioDeviceLogic() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean updateRouteMaskWhenBtConnected() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean updatedRcsCallCountTracking() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean useActualAddressToEnterConnectingState() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean useDeviceProvidedSerializedRingerVibration() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean useImprovedListenerOrder() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean useRefactoredAudioRouteSwitching() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean useStreamVoiceCallTones() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean voipAppActionsSupport() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean voipCallMonitorRefactor() {
        return true;
    }
}
