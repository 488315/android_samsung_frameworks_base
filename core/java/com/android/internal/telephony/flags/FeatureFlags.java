package com.android.internal.telephony.flags;

public interface FeatureFlags {
    boolean addAnomalyWhenNotifyConfigChangedWithInvalidPhone();

    boolean addRatRelatedSuggestedActionToImsRegistration();

    boolean allowMmtelInNonVops();

    boolean answerAudioOnlyWhenAnsweringViaMmiCode();

    boolean apDomainSelectionEnabled();

    boolean apnSettingFieldSupportFlag();

    boolean autoDataSwitchAllowRoaming();

    boolean autoDataSwitchUsesDataEnabled();

    boolean backupAndRestoreForEnable2g();

    boolean callExtraForNonHoldSupportedCarriers();

    boolean carrierEnabledSatelliteFlag();

    boolean carrierRestrictionRulesEnhancement();

    boolean carrierRestrictionStatus();

    boolean carrierRoamingNbIotNtn();

    boolean changeMethodOfObtainingImsRegistrationRadioTech();

    boolean cleanupOpenLogicalChannelRecordOnDispose();

    boolean clearCachedImsPhoneNumberWhenDeviceLostImsRegistration();

    boolean combineRilDeathHandle();

    boolean conferenceHoldUnholdChangedToSendMessage();

    boolean dataCallSessionStatsCapturesCrossSimCalling();

    boolean dataOnlyCellularService();

    boolean dataOnlyServiceAllowEmergencyCallOnly();

    boolean dataRatMetricEnabled();

    boolean dismissNetworkSelectionNotificationOnSimDisable();

    boolean doNotOverridePreciseLabel();

    boolean domainSelectionMetricsEnabled();

    boolean dsrsDiagnosticsEnabled();

    boolean emergencyRegistrationState();

    boolean enableAeadAlgorithms();

    boolean enableCarrierConfigN1ControlAttempt2();

    boolean enableIdentifierDisclosureTransparency();

    boolean enableIdentifierDisclosureTransparencyUnsolEvents();

    boolean enableModemCipherTransparency();

    boolean enableModemCipherTransparencyUnsolEvents();

    boolean enableMultipleSaProposals();

    boolean enableSipSubscribeRetry();

    boolean enableTelephonyAnalytics();

    boolean enableWpsCheckApiFlag();

    boolean enforceSubscriptionUserFilter();

    boolean enforceTelephonyFeatureMapping();

    boolean enforceTelephonyFeatureMappingForPublicApis();

    boolean ensureAccessToCallSettingsIsRestricted();

    boolean esimAvailableMemory();

    boolean esimBootstrapProvisioningFlag();

    boolean fixCrashOnGettingConfigWhenPhoneIsGone();

    boolean forceIwlanMms();

    boolean geofenceEnhancementForBetterUx();

    boolean hidePrefer3gItem();

    boolean hidePreinstalledCarrierAppAtMostOnce();

    boolean hideRoamingIcon();

    boolean ignoreAlreadyTerminatedIncomingCallBeforeRegisteringListener();

    boolean ignoreExistingNetworksForInternetAllowedChecking();

    boolean imsiKeyRetryDownloadOnPhoneUnlock();

    boolean keepEmptyRequestsNetwork();

    boolean logMmsSmsDatabaseAccessInfo();

    boolean meteredEmbbUrlcc();

    boolean minimalTelephonyCdmCheck();

    boolean minimalTelephonyManagersConditionalOnFeatures();

    boolean mmsDisabledError();

    boolean mmsGetApnFromPdsc();

    boolean networkRegistrationInfoRejectCause();

    boolean networkValidation();

    boolean notifyDataActivityChangedWithSlot();

    boolean notifyInitialImsProvisioningStatus();

    boolean oemEnabledSatelliteFlag();

    boolean preventInvocationRepeatOfRilCallWhenDeviceDoesNotSupportVoice();

    boolean preventSystemServerAndPhoneDeadlock();

    boolean reconnectQualifiedNetwork();

    boolean refinePreferredDataProfileSelection();

    boolean relaxHoTeardown();

    boolean removeCountryCodeFromLocalSingaporeCalls();

    boolean reorganizeRoamingNotification();

    boolean resetMobileNetworkSettings();

    boolean resetPrimarySimDefaultValues();

    boolean roamingNotificationForSingleDataNetwork();

    boolean saferGetPhoneNumber();

    boolean satelliteInternet();

    boolean satellitePersistentLogging();

    boolean setCarrierRestrictionStatus();

    boolean setNoReplyTimerForCfnry();

    boolean setNumberOfSimForImsEnable();

    boolean showCallFailNotificationFor2gToggle();

    boolean showCallIdAndCallWaitingInAdditionalSettingsMenu();

    boolean simultaneousCallingIndications();

    boolean slicingAdditionalErrorCodes();

    boolean smsDomainSelectionEnabled();

    boolean stopSpammingEmergencyNotification();

    boolean subscriptionUserAssociationQuery();

    boolean supportNrSaRrcIdle();

    boolean supportPsimToEsimConversion();

    boolean terminateActiveVideoCallWhenAcceptingSecondVideoCallAsAudioOnly();

    boolean unregisterSmsBroadcastReceiverFromCatService();

    boolean unthrottleCheckTransport();

    boolean updateImsServiceByGatheringProvisioningChanges();

    boolean updateRoamingStateToSetWfcMode();

    boolean useAlarmCallback();

    boolean useAospDomainSelectionService();

    boolean useOemDomainSelectionService();

    boolean useRelaxedIdMatch();

    boolean vonrEnabledMetric();

    boolean workProfileApiSplit();
}
