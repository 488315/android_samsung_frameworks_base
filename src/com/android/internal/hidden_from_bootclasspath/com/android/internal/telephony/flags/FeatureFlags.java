package com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags;

/* loaded from: classes5.dex */
public interface FeatureFlags {
    boolean actionSimPreferenceSettings();

    boolean addImsRedialCodesForEmergencyCalls();

    boolean addRatRelatedSuggestedActionToImsRegistration();

    boolean answerAudioOnlyWhenAnsweringViaMmiCode();

    boolean apDomainSelectionEnabled();

    boolean asyncInitCarrierPrivilegesTracker();

    boolean autoDataSwitchEnhanced();

    boolean avoidDeletingImsObjectFromCache();

    boolean callExtraForNonHoldSupportedCarriers();

    boolean carrierConfigChangedCallbackFix();

    boolean carrierEnabledSatelliteFlag();

    boolean carrierIdFromCarrierIdentifier();

    boolean carrierRestrictionRulesEnhancement();

    boolean carrierRestrictionStatus();

    boolean carrierRoamingNbIotNtn();

    boolean cellularIdentifierDisclosureIndications();

    boolean changeMethodOfObtainingImsRegistrationRadioTech();

    boolean cleanupCarrierAppUpdateEnabledStateLogic();

    boolean cleanupCdma();

    boolean clearCachedImsPhoneNumberWhenDeviceLostImsRegistration();

    boolean conferenceHoldUnholdChangedToSendMessage();

    boolean dataOnlyCellularService();

    boolean dataServiceCheck();

    boolean ddsCallback();

    boolean delayPhoneAccountRegistration();

    boolean deprecateCdma();

    boolean disableCarrierNetworkChangeOnCarrierAppLost();

    boolean disableEcbmBasedOnRat();

    boolean doNotOverridePreciseLabel();

    boolean domainSelectionMetricsEnabled();

    boolean dynamicDoNotAskAgainText();

    boolean emergencyCallbackModeNotification();

    boolean emergencyRegistrationState();

    boolean enableAeadAlgorithms();

    boolean enableMultipleSaProposals();

    boolean enableSipSubscribeRetry();

    boolean enableWpsCheckApiFlag();

    boolean enforceSubscriptionUserFilter();

    boolean ensureAccessToCallSettingsIsRestricted();

    boolean esimAvailableMemory();

    boolean esimBootstrapProvisioningFlag();

    boolean forceImsiCertificateDelete();

    boolean geofenceEnhancementForBetterUx();

    boolean getGroupIdLevel2();

    boolean hangupActiveCallBasedOnEmergencyCallDomain();

    boolean hangupEmergencyCallForCrossSimRedialing();

    boolean hidePrefer3gItem();

    boolean hsumBroadcast();

    boolean hsumPackageManager();

    boolean ignoreAlreadyTerminatedIncomingCallBeforeRegisteringListener();

    boolean ignoreCarrieridResetForSimRemoval();

    boolean ignoreMccMncFromOperatorForLocale();

    boolean imsResolverUserAware();

    boolean imsiKeyRetryDownloadOnPhoneUnlock();

    boolean incallHandoverPolicy();

    boolean logMmsSmsDatabaseAccessInfo();

    boolean mmsDisabledError();

    boolean mmsGetApnFromPdsc();

    boolean nationalCountryCodeFormattingForLocalCalls();

    boolean networkRegistrationInfoRejectCause();

    boolean networkValidation();

    boolean notifyInitialImsProvisioningStatus();

    boolean oemEnabledSatelliteFlag();

    boolean oemEnabledSatellitePhase2();

    boolean oemPaidPrivate();

    boolean optimizationApduSender();

    boolean passCopiedCallStateList();

    boolean performCrossStackRedialCheckForEmergencyCall();

    boolean phoneTypeCleanup();

    boolean powerDownRaceFix();

    boolean preventHangupDuringCallMerge();

    boolean preventInvocationRepeatOfRilCallWhenDeviceDoesNotSupportVoice();

    boolean preventSystemServerAndPhoneDeadlock();

    boolean remapDisconnectCauseSipRequestCancelled();

    boolean removeCountryCodeFromLocalSingaporeCalls();

    boolean resetMobileNetworkSettings();

    boolean robustNumberVerification();

    boolean satellite25q4Apis();

    boolean satelliteDataMetrics();

    boolean satelliteExitP2pSessionOutsideGeofence();

    boolean satellitePersistentLogging();

    boolean satelliteStateChangeListener();

    boolean satelliteSystemApis();

    boolean securityAlgorithmsUpdateIndications();

    boolean setCarrierRestrictionStatus();

    boolean setNoReplyTimerForCfnry();

    boolean setNumberOfSimForImsEnable();

    boolean showCallFailNotificationFor2gToggle();

    boolean simultaneousCallingIndications();

    boolean skipMmiCodeCheckForEmergencyCall();

    boolean slicingAdditionalErrorCodes();

    boolean smsDomainSelectionEnabled();

    boolean smsMmsDeliverBroadcastsRedirectToMainUser();

    boolean starlinkDataBugfix();

    boolean stopSpammingEmergencyNotification();

    boolean subscriptionPlanAllowStatusAndEndDate();

    boolean subscriptionUserAssociationQuery();

    boolean supportCarrierServicesForHsum();

    boolean supportImsMmtelInterface();

    boolean supportIsimRecord();

    boolean supportPsimToEsimConversion();

    boolean supportSmsOverImsApis();

    boolean temporaryFailuresInCarrierMessagingService();

    boolean terminateActiveVideoCallWhenAcceptingSecondVideoCallAsAudioOnly();

    boolean threadShred();

    boolean uiccAppCountCheckToCreateChannel();

    boolean unregisterSmsBroadcastReceiverFromCatService();

    boolean updateImsServiceByGatheringProvisioningChanges();

    boolean updateRoamingStateToSetWfcMode();

    boolean useAospDomainSelectionService();

    boolean useCarrierConfigForCfnryTimeViaMmi();

    boolean useI18nForMccMapping();

    boolean useOemDomainSelectionService();

    boolean useRelaxedIdMatch();

    boolean vendorSpecificCellularIdentifierDisclosureIndications();

    boolean workProfileApiSplit();
}
