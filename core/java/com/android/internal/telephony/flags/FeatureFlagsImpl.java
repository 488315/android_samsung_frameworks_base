package com.android.internal.telephony.flags;

/* loaded from: classes5.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean addAnomalyWhenNotifyConfigChangedWithInvalidPhone() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean addRatRelatedSuggestedActionToImsRegistration() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean allowMmtelInNonVops() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean answerAudioOnlyWhenAnsweringViaMmiCode() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean apDomainSelectionEnabled() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean apnSettingFieldSupportFlag() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean autoDataSwitchAllowRoaming() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean autoDataSwitchUsesDataEnabled() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean backupAndRestoreForEnable2g() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean callExtraForNonHoldSupportedCarriers() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean carrierEnabledSatelliteFlag() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean carrierRestrictionRulesEnhancement() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean carrierRestrictionStatus() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean carrierRoamingNbIotNtn() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean changeMethodOfObtainingImsRegistrationRadioTech() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean cleanupOpenLogicalChannelRecordOnDispose() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean clearCachedImsPhoneNumberWhenDeviceLostImsRegistration() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean combineRilDeathHandle() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean conferenceHoldUnholdChangedToSendMessage() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean dataCallSessionStatsCapturesCrossSimCalling() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean dataOnlyCellularService() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean dataOnlyServiceAllowEmergencyCallOnly() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean dataRatMetricEnabled() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean dismissNetworkSelectionNotificationOnSimDisable() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean doNotOverridePreciseLabel() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean domainSelectionMetricsEnabled() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean dsrsDiagnosticsEnabled() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean emergencyRegistrationState() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enableAeadAlgorithms() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enableCarrierConfigN1ControlAttempt2() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enableIdentifierDisclosureTransparency() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enableIdentifierDisclosureTransparencyUnsolEvents() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enableModemCipherTransparency() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enableModemCipherTransparencyUnsolEvents() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enableMultipleSaProposals() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enableSipSubscribeRetry() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enableTelephonyAnalytics() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enableWpsCheckApiFlag() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enforceSubscriptionUserFilter() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enforceTelephonyFeatureMapping() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enforceTelephonyFeatureMappingForPublicApis() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean ensureAccessToCallSettingsIsRestricted() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean esimAvailableMemory() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean esimBootstrapProvisioningFlag() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean fixCrashOnGettingConfigWhenPhoneIsGone() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean forceIwlanMms() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean geofenceEnhancementForBetterUx() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean hidePrefer3gItem() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean hidePreinstalledCarrierAppAtMostOnce() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean hideRoamingIcon() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean ignoreAlreadyTerminatedIncomingCallBeforeRegisteringListener() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean ignoreExistingNetworksForInternetAllowedChecking() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean imsiKeyRetryDownloadOnPhoneUnlock() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean keepEmptyRequestsNetwork() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean logMmsSmsDatabaseAccessInfo() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean meteredEmbbUrlcc() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean minimalTelephonyCdmCheck() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean minimalTelephonyManagersConditionalOnFeatures() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean mmsDisabledError() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean mmsGetApnFromPdsc() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean networkRegistrationInfoRejectCause() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean networkValidation() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean notifyDataActivityChangedWithSlot() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean notifyInitialImsProvisioningStatus() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean oemEnabledSatelliteFlag() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean preventInvocationRepeatOfRilCallWhenDeviceDoesNotSupportVoice() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean preventSystemServerAndPhoneDeadlock() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean reconnectQualifiedNetwork() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean refinePreferredDataProfileSelection() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean relaxHoTeardown() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean removeCountryCodeFromLocalSingaporeCalls() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean reorganizeRoamingNotification() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean resetMobileNetworkSettings() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean resetPrimarySimDefaultValues() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean roamingNotificationForSingleDataNetwork() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean saferGetPhoneNumber() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean satelliteInternet() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean satellitePersistentLogging() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean setCarrierRestrictionStatus() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean setNoReplyTimerForCfnry() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean setNumberOfSimForImsEnable() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean showCallFailNotificationFor2gToggle() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean showCallIdAndCallWaitingInAdditionalSettingsMenu() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean simultaneousCallingIndications() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean slicingAdditionalErrorCodes() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean smsDomainSelectionEnabled() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean stopSpammingEmergencyNotification() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean subscriptionUserAssociationQuery() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean supportNrSaRrcIdle() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean supportPsimToEsimConversion() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean terminateActiveVideoCallWhenAcceptingSecondVideoCallAsAudioOnly() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean unregisterSmsBroadcastReceiverFromCatService() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean unthrottleCheckTransport() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean updateImsServiceByGatheringProvisioningChanges() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean updateRoamingStateToSetWfcMode() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean useAlarmCallback() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean useAospDomainSelectionService() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean useOemDomainSelectionService() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean useRelaxedIdMatch() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean vonrEnabledMetric() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean workProfileApiSplit() {
        return false;
    }
}
