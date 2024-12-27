package com.android.server.telecom.flags;

public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean addCallUriForMissedCalls() {
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
    public boolean businessCallComposer() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean cacheCallAudioCallbacks() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean callAudioCommunicationDeviceRefactor() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean callDetailsIdChanges() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean cancelRemovalOnEmergencyRedial() {
        return true;
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
    public boolean earlyBindingToIncallService() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean earlyUpdateInternalCallAudioState() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean eccKeyguard() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean enableCallSequencing() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean ensureAudioModeUpdatesOnForegroundCallChange() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean fixAudioFlickerForOutgoingCalls() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean genAnomReportOnFocusTimeout() {
        return false;
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
    public boolean ignoreAutoRouteToWatchDevice() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean isNewOutgoingCallBroadcastUnblocking() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean onlyUpdateTelephonyOnValidSubIds() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean postponeRegisterToLeaudio() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean profileUserSupport() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean resetMuteWhenEnteringQuiescentBtRoute() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean resolveSwitchingBtDevicesComputation() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean separatelyBindToBtIncallService() {
        return false;
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
    public boolean skipFilterPhoneAccountPerformDndFilter() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean telecomLogExternalWearableCalls() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean telecomMainlineBlockedNumbersManager() {
        return false;
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
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean transactionalCsVerifier() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean transactionalHoldDisconnectsUnholdable() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean transactionalVideoState() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean transitRouteBeforeAudioDisconnectBt() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean unregisterUnresolvableAccounts() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean updateRouteMaskWhenBtConnected() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean updatedRcsCallCountTracking() {
        return false;
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
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean voipAppActionsSupport() {
        return true;
    }
}
