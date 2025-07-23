package com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_ACTION_SIM_PREFERENCE_SETTINGS, Flags.FLAG_ADD_IMS_REDIAL_CODES_FOR_EMERGENCY_CALLS, Flags.FLAG_ADD_RAT_RELATED_SUGGESTED_ACTION_TO_IMS_REGISTRATION, Flags.FLAG_ANSWER_AUDIO_ONLY_WHEN_ANSWERING_VIA_MMI_CODE, Flags.FLAG_AP_DOMAIN_SELECTION_ENABLED, Flags.FLAG_ASYNC_INIT_CARRIER_PRIVILEGES_TRACKER, Flags.FLAG_AUTO_DATA_SWITCH_ENHANCED, Flags.FLAG_AVOID_DELETING_IMS_OBJECT_FROM_CACHE, Flags.FLAG_CALL_EXTRA_FOR_NON_HOLD_SUPPORTED_CARRIERS, Flags.FLAG_CARRIER_CONFIG_CHANGED_CALLBACK_FIX, Flags.FLAG_CARRIER_ENABLED_SATELLITE_FLAG, Flags.FLAG_CARRIER_ID_FROM_CARRIER_IDENTIFIER, Flags.FLAG_CARRIER_RESTRICTION_RULES_ENHANCEMENT, Flags.FLAG_CARRIER_RESTRICTION_STATUS, Flags.FLAG_CARRIER_ROAMING_NB_IOT_NTN, Flags.FLAG_CELLULAR_IDENTIFIER_DISCLOSURE_INDICATIONS, Flags.FLAG_CHANGE_METHOD_OF_OBTAINING_IMS_REGISTRATION_RADIO_TECH, Flags.FLAG_CLEANUP_CARRIER_APP_UPDATE_ENABLED_STATE_LOGIC, Flags.FLAG_CLEANUP_CDMA, Flags.FLAG_CLEAR_CACHED_IMS_PHONE_NUMBER_WHEN_DEVICE_LOST_IMS_REGISTRATION, Flags.FLAG_CONFERENCE_HOLD_UNHOLD_CHANGED_TO_SEND_MESSAGE, Flags.FLAG_DATA_ONLY_CELLULAR_SERVICE, Flags.FLAG_DATA_SERVICE_CHECK, Flags.FLAG_DDS_CALLBACK, Flags.FLAG_DELAY_PHONE_ACCOUNT_REGISTRATION, Flags.FLAG_DEPRECATE_CDMA, Flags.FLAG_DISABLE_CARRIER_NETWORK_CHANGE_ON_CARRIER_APP_LOST, Flags.FLAG_DISABLE_ECBM_BASED_ON_RAT, Flags.FLAG_DO_NOT_OVERRIDE_PRECISE_LABEL, Flags.FLAG_DOMAIN_SELECTION_METRICS_ENABLED, Flags.FLAG_DYNAMIC_DO_NOT_ASK_AGAIN_TEXT, Flags.FLAG_EMERGENCY_CALLBACK_MODE_NOTIFICATION, Flags.FLAG_EMERGENCY_REGISTRATION_STATE, Flags.FLAG_ENABLE_AEAD_ALGORITHMS, Flags.FLAG_ENABLE_MULTIPLE_SA_PROPOSALS, Flags.FLAG_ENABLE_SIP_SUBSCRIBE_RETRY, Flags.FLAG_ENABLE_WPS_CHECK_API_FLAG, Flags.FLAG_ENFORCE_SUBSCRIPTION_USER_FILTER, Flags.FLAG_ENSURE_ACCESS_TO_CALL_SETTINGS_IS_RESTRICTED, Flags.FLAG_ESIM_AVAILABLE_MEMORY, Flags.FLAG_ESIM_BOOTSTRAP_PROVISIONING_FLAG, Flags.FLAG_FORCE_IMSI_CERTIFICATE_DELETE, Flags.FLAG_GEOFENCE_ENHANCEMENT_FOR_BETTER_UX, Flags.FLAG_GET_GROUP_ID_LEVEL2, Flags.FLAG_HANGUP_ACTIVE_CALL_BASED_ON_EMERGENCY_CALL_DOMAIN, Flags.FLAG_HANGUP_EMERGENCY_CALL_FOR_CROSS_SIM_REDIALING, Flags.FLAG_HIDE_PREFER_3G_ITEM, Flags.FLAG_HSUM_BROADCAST, Flags.FLAG_HSUM_PACKAGE_MANAGER, Flags.FLAG_IGNORE_ALREADY_TERMINATED_INCOMING_CALL_BEFORE_REGISTERING_LISTENER, Flags.FLAG_IGNORE_CARRIERID_RESET_FOR_SIM_REMOVAL, Flags.FLAG_IGNORE_MCC_MNC_FROM_OPERATOR_FOR_LOCALE, Flags.FLAG_IMS_RESOLVER_USER_AWARE, Flags.FLAG_IMSI_KEY_RETRY_DOWNLOAD_ON_PHONE_UNLOCK, Flags.FLAG_INCALL_HANDOVER_POLICY, Flags.FLAG_LOG_MMS_SMS_DATABASE_ACCESS_INFO, Flags.FLAG_MMS_DISABLED_ERROR, Flags.FLAG_MMS_GET_APN_FROM_PDSC, Flags.FLAG_NATIONAL_COUNTRY_CODE_FORMATTING_FOR_LOCAL_CALLS, Flags.FLAG_NETWORK_REGISTRATION_INFO_REJECT_CAUSE, Flags.FLAG_NETWORK_VALIDATION, Flags.FLAG_NOTIFY_INITIAL_IMS_PROVISIONING_STATUS, Flags.FLAG_OEM_ENABLED_SATELLITE_FLAG, Flags.FLAG_OEM_ENABLED_SATELLITE_PHASE_2, Flags.FLAG_OEM_PAID_PRIVATE, Flags.FLAG_OPTIMIZATION_APDU_SENDER, Flags.FLAG_PASS_COPIED_CALL_STATE_LIST, Flags.FLAG_PERFORM_CROSS_STACK_REDIAL_CHECK_FOR_EMERGENCY_CALL, Flags.FLAG_PHONE_TYPE_CLEANUP, Flags.FLAG_POWER_DOWN_RACE_FIX, Flags.FLAG_PREVENT_HANGUP_DURING_CALL_MERGE, Flags.FLAG_PREVENT_INVOCATION_REPEAT_OF_RIL_CALL_WHEN_DEVICE_DOES_NOT_SUPPORT_VOICE, Flags.FLAG_PREVENT_SYSTEM_SERVER_AND_PHONE_DEADLOCK, Flags.FLAG_REMAP_DISCONNECT_CAUSE_SIP_REQUEST_CANCELLED, Flags.FLAG_REMOVE_COUNTRY_CODE_FROM_LOCAL_SINGAPORE_CALLS, Flags.FLAG_RESET_MOBILE_NETWORK_SETTINGS, Flags.FLAG_ROBUST_NUMBER_VERIFICATION, Flags.FLAG_SATELLITE_25Q4_APIS, Flags.FLAG_SATELLITE_DATA_METRICS, Flags.FLAG_SATELLITE_EXIT_P2P_SESSION_OUTSIDE_GEOFENCE, Flags.FLAG_SATELLITE_PERSISTENT_LOGGING, Flags.FLAG_SATELLITE_STATE_CHANGE_LISTENER, Flags.FLAG_SATELLITE_SYSTEM_APIS, Flags.FLAG_SECURITY_ALGORITHMS_UPDATE_INDICATIONS, Flags.FLAG_SET_CARRIER_RESTRICTION_STATUS, Flags.FLAG_SET_NO_REPLY_TIMER_FOR_CFNRY, Flags.FLAG_SET_NUMBER_OF_SIM_FOR_IMS_ENABLE, Flags.FLAG_SHOW_CALL_FAIL_NOTIFICATION_FOR_2G_TOGGLE, Flags.FLAG_SIMULTANEOUS_CALLING_INDICATIONS, Flags.FLAG_SKIP_MMI_CODE_CHECK_FOR_EMERGENCY_CALL, Flags.FLAG_SLICING_ADDITIONAL_ERROR_CODES, Flags.FLAG_SMS_DOMAIN_SELECTION_ENABLED, Flags.FLAG_SMS_MMS_DELIVER_BROADCASTS_REDIRECT_TO_MAIN_USER, Flags.FLAG_STARLINK_DATA_BUGFIX, Flags.FLAG_STOP_SPAMMING_EMERGENCY_NOTIFICATION, Flags.FLAG_SUBSCRIPTION_PLAN_ALLOW_STATUS_AND_END_DATE, Flags.FLAG_SUBSCRIPTION_USER_ASSOCIATION_QUERY, Flags.FLAG_SUPPORT_CARRIER_SERVICES_FOR_HSUM, Flags.FLAG_SUPPORT_IMS_MMTEL_INTERFACE, Flags.FLAG_SUPPORT_ISIM_RECORD, Flags.FLAG_SUPPORT_PSIM_TO_ESIM_CONVERSION, Flags.FLAG_SUPPORT_SMS_OVER_IMS_APIS, Flags.FLAG_TEMPORARY_FAILURES_IN_CARRIER_MESSAGING_SERVICE, Flags.FLAG_TERMINATE_ACTIVE_VIDEO_CALL_WHEN_ACCEPTING_SECOND_VIDEO_CALL_AS_AUDIO_ONLY, Flags.FLAG_THREAD_SHRED, Flags.FLAG_UICC_APP_COUNT_CHECK_TO_CREATE_CHANNEL, Flags.FLAG_UNREGISTER_SMS_BROADCAST_RECEIVER_FROM_CAT_SERVICE, Flags.FLAG_UPDATE_IMS_SERVICE_BY_GATHERING_PROVISIONING_CHANGES, Flags.FLAG_UPDATE_ROAMING_STATE_TO_SET_WFC_MODE, Flags.FLAG_USE_AOSP_DOMAIN_SELECTION_SERVICE, Flags.FLAG_USE_CARRIER_CONFIG_FOR_CFNRY_TIME_VIA_MMI, Flags.FLAG_USE_I18N_FOR_MCC_MAPPING, Flags.FLAG_USE_OEM_DOMAIN_SELECTION_SERVICE, Flags.FLAG_USE_RELAXED_ID_MATCH, Flags.FLAG_VENDOR_SPECIFIC_CELLULAR_IDENTIFIER_DISCLOSURE_INDICATIONS, Flags.FLAG_WORK_PROFILE_API_SPLIT, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean actionSimPreferenceSettings() {
        return getValue(Flags.FLAG_ACTION_SIM_PREFERENCE_SETTINGS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda22
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).actionSimPreferenceSettings();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean addImsRedialCodesForEmergencyCalls() {
        return getValue(Flags.FLAG_ADD_IMS_REDIAL_CODES_FOR_EMERGENCY_CALLS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda92
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).addImsRedialCodesForEmergencyCalls();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean addRatRelatedSuggestedActionToImsRegistration() {
        return getValue(Flags.FLAG_ADD_RAT_RELATED_SUGGESTED_ACTION_TO_IMS_REGISTRATION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda108
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).addRatRelatedSuggestedActionToImsRegistration();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean answerAudioOnlyWhenAnsweringViaMmiCode() {
        return getValue(Flags.FLAG_ANSWER_AUDIO_ONLY_WHEN_ANSWERING_VIA_MMI_CODE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda103
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).answerAudioOnlyWhenAnsweringViaMmiCode();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean apDomainSelectionEnabled() {
        return getValue(Flags.FLAG_AP_DOMAIN_SELECTION_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda42
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).apDomainSelectionEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean asyncInitCarrierPrivilegesTracker() {
        return getValue(Flags.FLAG_ASYNC_INIT_CARRIER_PRIVILEGES_TRACKER, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda23
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).asyncInitCarrierPrivilegesTracker();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean autoDataSwitchEnhanced() {
        return getValue(Flags.FLAG_AUTO_DATA_SWITCH_ENHANCED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda80
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).autoDataSwitchEnhanced();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean avoidDeletingImsObjectFromCache() {
        return getValue(Flags.FLAG_AVOID_DELETING_IMS_OBJECT_FROM_CACHE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda93
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).avoidDeletingImsObjectFromCache();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean callExtraForNonHoldSupportedCarriers() {
        return getValue(Flags.FLAG_CALL_EXTRA_FOR_NON_HOLD_SUPPORTED_CARRIERS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda95
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).callExtraForNonHoldSupportedCarriers();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean carrierConfigChangedCallbackFix() {
        return getValue(Flags.FLAG_CARRIER_CONFIG_CHANGED_CALLBACK_FIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda101
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).carrierConfigChangedCallbackFix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean carrierEnabledSatelliteFlag() {
        return getValue(Flags.FLAG_CARRIER_ENABLED_SATELLITE_FLAG, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).carrierEnabledSatelliteFlag();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean carrierIdFromCarrierIdentifier() {
        return getValue(Flags.FLAG_CARRIER_ID_FROM_CARRIER_IDENTIFIER, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda24
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).carrierIdFromCarrierIdentifier();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean carrierRestrictionRulesEnhancement() {
        return getValue(Flags.FLAG_CARRIER_RESTRICTION_RULES_ENHANCEMENT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda33
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).carrierRestrictionRulesEnhancement();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean carrierRestrictionStatus() {
        return getValue(Flags.FLAG_CARRIER_RESTRICTION_STATUS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).carrierRestrictionStatus();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean carrierRoamingNbIotNtn() {
        return getValue(Flags.FLAG_CARRIER_ROAMING_NB_IOT_NTN, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).carrierRoamingNbIotNtn();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean cellularIdentifierDisclosureIndications() {
        return getValue(Flags.FLAG_CELLULAR_IDENTIFIER_DISCLOSURE_INDICATIONS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda21
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cellularIdentifierDisclosureIndications();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean changeMethodOfObtainingImsRegistrationRadioTech() {
        return getValue(Flags.FLAG_CHANGE_METHOD_OF_OBTAINING_IMS_REGISTRATION_RADIO_TECH, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).changeMethodOfObtainingImsRegistrationRadioTech();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean cleanupCarrierAppUpdateEnabledStateLogic() {
        return getValue(Flags.FLAG_CLEANUP_CARRIER_APP_UPDATE_ENABLED_STATE_LOGIC, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda25
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cleanupCarrierAppUpdateEnabledStateLogic();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean cleanupCdma() {
        return getValue(Flags.FLAG_CLEANUP_CDMA, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda51
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cleanupCdma();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean clearCachedImsPhoneNumberWhenDeviceLostImsRegistration() {
        return getValue(Flags.FLAG_CLEAR_CACHED_IMS_PHONE_NUMBER_WHEN_DEVICE_LOST_IMS_REGISTRATION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).clearCachedImsPhoneNumberWhenDeviceLostImsRegistration();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean conferenceHoldUnholdChangedToSendMessage() {
        return getValue(Flags.FLAG_CONFERENCE_HOLD_UNHOLD_CHANGED_TO_SEND_MESSAGE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda77
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).conferenceHoldUnholdChangedToSendMessage();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean dataOnlyCellularService() {
        return getValue(Flags.FLAG_DATA_ONLY_CELLULAR_SERVICE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda47
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).dataOnlyCellularService();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean dataServiceCheck() {
        return getValue(Flags.FLAG_DATA_SERVICE_CHECK, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda81
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).dataServiceCheck();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean ddsCallback() {
        return getValue(Flags.FLAG_DDS_CALLBACK, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda18
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).ddsCallback();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean delayPhoneAccountRegistration() {
        return getValue(Flags.FLAG_DELAY_PHONE_ACCOUNT_REGISTRATION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda112
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).delayPhoneAccountRegistration();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean deprecateCdma() {
        return getValue(Flags.FLAG_DEPRECATE_CDMA, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda70
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).deprecateCdma();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean disableCarrierNetworkChangeOnCarrierAppLost() {
        return getValue(Flags.FLAG_DISABLE_CARRIER_NETWORK_CHANGE_ON_CARRIER_APP_LOST, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda46
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).disableCarrierNetworkChangeOnCarrierAppLost();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean disableEcbmBasedOnRat() {
        return getValue(Flags.FLAG_DISABLE_ECBM_BASED_ON_RAT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda37
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).disableEcbmBasedOnRat();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean doNotOverridePreciseLabel() {
        return getValue(Flags.FLAG_DO_NOT_OVERRIDE_PRECISE_LABEL, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda61
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).doNotOverridePreciseLabel();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean domainSelectionMetricsEnabled() {
        return getValue(Flags.FLAG_DOMAIN_SELECTION_METRICS_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).domainSelectionMetricsEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean dynamicDoNotAskAgainText() {
        return getValue(Flags.FLAG_DYNAMIC_DO_NOT_ASK_AGAIN_TEXT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda85
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).dynamicDoNotAskAgainText();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean emergencyCallbackModeNotification() {
        return getValue(Flags.FLAG_EMERGENCY_CALLBACK_MODE_NOTIFICATION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).emergencyCallbackModeNotification();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean emergencyRegistrationState() {
        return getValue(Flags.FLAG_EMERGENCY_REGISTRATION_STATE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda29
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).emergencyRegistrationState();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean enableAeadAlgorithms() {
        return getValue(Flags.FLAG_ENABLE_AEAD_ALGORITHMS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda38
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableAeadAlgorithms();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean enableMultipleSaProposals() {
        return getValue(Flags.FLAG_ENABLE_MULTIPLE_SA_PROPOSALS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableMultipleSaProposals();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean enableSipSubscribeRetry() {
        return getValue(Flags.FLAG_ENABLE_SIP_SUBSCRIBE_RETRY, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda48
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableSipSubscribeRetry();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean enableWpsCheckApiFlag() {
        return getValue(Flags.FLAG_ENABLE_WPS_CHECK_API_FLAG, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda90
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableWpsCheckApiFlag();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean enforceSubscriptionUserFilter() {
        return getValue(Flags.FLAG_ENFORCE_SUBSCRIPTION_USER_FILTER, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda54
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enforceSubscriptionUserFilter();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean ensureAccessToCallSettingsIsRestricted() {
        return getValue(Flags.FLAG_ENSURE_ACCESS_TO_CALL_SETTINGS_IS_RESTRICTED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda98
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).ensureAccessToCallSettingsIsRestricted();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean esimAvailableMemory() {
        return getValue(Flags.FLAG_ESIM_AVAILABLE_MEMORY, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).esimAvailableMemory();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean esimBootstrapProvisioningFlag() {
        return getValue(Flags.FLAG_ESIM_BOOTSTRAP_PROVISIONING_FLAG, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda86
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).esimBootstrapProvisioningFlag();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean forceImsiCertificateDelete() {
        return getValue(Flags.FLAG_FORCE_IMSI_CERTIFICATE_DELETE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda45
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).forceImsiCertificateDelete();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean geofenceEnhancementForBetterUx() {
        return getValue(Flags.FLAG_GEOFENCE_ENHANCEMENT_FOR_BETTER_UX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda62
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).geofenceEnhancementForBetterUx();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean getGroupIdLevel2() {
        return getValue(Flags.FLAG_GET_GROUP_ID_LEVEL2, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda52
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).getGroupIdLevel2();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean hangupActiveCallBasedOnEmergencyCallDomain() {
        return getValue(Flags.FLAG_HANGUP_ACTIVE_CALL_BASED_ON_EMERGENCY_CALL_DOMAIN, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda53
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).hangupActiveCallBasedOnEmergencyCallDomain();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean hangupEmergencyCallForCrossSimRedialing() {
        return getValue(Flags.FLAG_HANGUP_EMERGENCY_CALL_FOR_CROSS_SIM_REDIALING, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda75
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).hangupEmergencyCallForCrossSimRedialing();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean hidePrefer3gItem() {
        return getValue(Flags.FLAG_HIDE_PREFER_3G_ITEM, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda71
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).hidePrefer3gItem();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean hsumBroadcast() {
        return getValue(Flags.FLAG_HSUM_BROADCAST, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).hsumBroadcast();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean hsumPackageManager() {
        return getValue(Flags.FLAG_HSUM_PACKAGE_MANAGER, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).hsumPackageManager();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean ignoreAlreadyTerminatedIncomingCallBeforeRegisteringListener() {
        return getValue(Flags.FLAG_IGNORE_ALREADY_TERMINATED_INCOMING_CALL_BEFORE_REGISTERING_LISTENER, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda72
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).ignoreAlreadyTerminatedIncomingCallBeforeRegisteringListener();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean ignoreCarrieridResetForSimRemoval() {
        return getValue(Flags.FLAG_IGNORE_CARRIERID_RESET_FOR_SIM_REMOVAL, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda50
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).ignoreCarrieridResetForSimRemoval();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean ignoreMccMncFromOperatorForLocale() {
        return getValue(Flags.FLAG_IGNORE_MCC_MNC_FROM_OPERATOR_FOR_LOCALE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda109
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).ignoreMccMncFromOperatorForLocale();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean imsResolverUserAware() {
        return getValue(Flags.FLAG_IMS_RESOLVER_USER_AWARE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).imsResolverUserAware();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean imsiKeyRetryDownloadOnPhoneUnlock() {
        return getValue(Flags.FLAG_IMSI_KEY_RETRY_DOWNLOAD_ON_PHONE_UNLOCK, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).imsiKeyRetryDownloadOnPhoneUnlock();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean incallHandoverPolicy() {
        return getValue(Flags.FLAG_INCALL_HANDOVER_POLICY, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda97
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).incallHandoverPolicy();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean logMmsSmsDatabaseAccessInfo() {
        return getValue(Flags.FLAG_LOG_MMS_SMS_DATABASE_ACCESS_INFO, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda74
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).logMmsSmsDatabaseAccessInfo();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean mmsDisabledError() {
        return getValue(Flags.FLAG_MMS_DISABLED_ERROR, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda19
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).mmsDisabledError();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean mmsGetApnFromPdsc() {
        return getValue(Flags.FLAG_MMS_GET_APN_FROM_PDSC, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda110
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).mmsGetApnFromPdsc();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean nationalCountryCodeFormattingForLocalCalls() {
        return getValue(Flags.FLAG_NATIONAL_COUNTRY_CODE_FORMATTING_FOR_LOCAL_CALLS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda35
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).nationalCountryCodeFormattingForLocalCalls();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean networkRegistrationInfoRejectCause() {
        return getValue(Flags.FLAG_NETWORK_REGISTRATION_INFO_REJECT_CAUSE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda20
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).networkRegistrationInfoRejectCause();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean networkValidation() {
        return getValue(Flags.FLAG_NETWORK_VALIDATION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda113
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).networkValidation();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean notifyInitialImsProvisioningStatus() {
        return getValue(Flags.FLAG_NOTIFY_INITIAL_IMS_PROVISIONING_STATUS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda26
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).notifyInitialImsProvisioningStatus();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean oemEnabledSatelliteFlag() {
        return getValue(Flags.FLAG_OEM_ENABLED_SATELLITE_FLAG, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda44
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).oemEnabledSatelliteFlag();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean oemEnabledSatellitePhase2() {
        return getValue(Flags.FLAG_OEM_ENABLED_SATELLITE_PHASE_2, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda56
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).oemEnabledSatellitePhase2();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean oemPaidPrivate() {
        return getValue(Flags.FLAG_OEM_PAID_PRIVATE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda100
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).oemPaidPrivate();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean optimizationApduSender() {
        return getValue(Flags.FLAG_OPTIMIZATION_APDU_SENDER, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda73
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).optimizationApduSender();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean passCopiedCallStateList() {
        return getValue(Flags.FLAG_PASS_COPIED_CALL_STATE_LIST, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda34
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).passCopiedCallStateList();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean performCrossStackRedialCheckForEmergencyCall() {
        return getValue(Flags.FLAG_PERFORM_CROSS_STACK_REDIAL_CHECK_FOR_EMERGENCY_CALL, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda102
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).performCrossStackRedialCheckForEmergencyCall();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean phoneTypeCleanup() {
        return getValue(Flags.FLAG_PHONE_TYPE_CLEANUP, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda114
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).phoneTypeCleanup();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean powerDownRaceFix() {
        return getValue(Flags.FLAG_POWER_DOWN_RACE_FIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda58
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).powerDownRaceFix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean preventHangupDuringCallMerge() {
        return getValue(Flags.FLAG_PREVENT_HANGUP_DURING_CALL_MERGE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda49
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).preventHangupDuringCallMerge();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean preventInvocationRepeatOfRilCallWhenDeviceDoesNotSupportVoice() {
        return getValue(Flags.FLAG_PREVENT_INVOCATION_REPEAT_OF_RIL_CALL_WHEN_DEVICE_DOES_NOT_SUPPORT_VOICE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda99
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).preventInvocationRepeatOfRilCallWhenDeviceDoesNotSupportVoice();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean preventSystemServerAndPhoneDeadlock() {
        return getValue(Flags.FLAG_PREVENT_SYSTEM_SERVER_AND_PHONE_DEADLOCK, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda115
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).preventSystemServerAndPhoneDeadlock();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean remapDisconnectCauseSipRequestCancelled() {
        return getValue(Flags.FLAG_REMAP_DISCONNECT_CAUSE_SIP_REQUEST_CANCELLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda82
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).remapDisconnectCauseSipRequestCancelled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean removeCountryCodeFromLocalSingaporeCalls() {
        return getValue(Flags.FLAG_REMOVE_COUNTRY_CODE_FROM_LOCAL_SINGAPORE_CALLS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda105
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).removeCountryCodeFromLocalSingaporeCalls();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean resetMobileNetworkSettings() {
        return getValue(Flags.FLAG_RESET_MOBILE_NETWORK_SETTINGS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda84
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).resetMobileNetworkSettings();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean robustNumberVerification() {
        return getValue(Flags.FLAG_ROBUST_NUMBER_VERIFICATION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda94
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).robustNumberVerification();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean satellite25q4Apis() {
        return getValue(Flags.FLAG_SATELLITE_25Q4_APIS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).satellite25q4Apis();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean satelliteDataMetrics() {
        return getValue(Flags.FLAG_SATELLITE_DATA_METRICS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda67
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).satelliteDataMetrics();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean satelliteExitP2pSessionOutsideGeofence() {
        return getValue(Flags.FLAG_SATELLITE_EXIT_P2P_SESSION_OUTSIDE_GEOFENCE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda83
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).satelliteExitP2pSessionOutsideGeofence();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean satellitePersistentLogging() {
        return getValue(Flags.FLAG_SATELLITE_PERSISTENT_LOGGING, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda27
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).satellitePersistentLogging();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean satelliteStateChangeListener() {
        return getValue(Flags.FLAG_SATELLITE_STATE_CHANGE_LISTENER, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda30
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).satelliteStateChangeListener();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean satelliteSystemApis() {
        return getValue(Flags.FLAG_SATELLITE_SYSTEM_APIS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda64
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).satelliteSystemApis();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean securityAlgorithmsUpdateIndications() {
        return getValue(Flags.FLAG_SECURITY_ALGORITHMS_UPDATE_INDICATIONS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda69
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).securityAlgorithmsUpdateIndications();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean setCarrierRestrictionStatus() {
        return getValue(Flags.FLAG_SET_CARRIER_RESTRICTION_STATUS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda78
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).setCarrierRestrictionStatus();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean setNoReplyTimerForCfnry() {
        return getValue(Flags.FLAG_SET_NO_REPLY_TIMER_FOR_CFNRY, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda32
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).setNoReplyTimerForCfnry();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean setNumberOfSimForImsEnable() {
        return getValue(Flags.FLAG_SET_NUMBER_OF_SIM_FOR_IMS_ENABLE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda41
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).setNumberOfSimForImsEnable();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean showCallFailNotificationFor2gToggle() {
        return getValue(Flags.FLAG_SHOW_CALL_FAIL_NOTIFICATION_FOR_2G_TOGGLE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda65
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).showCallFailNotificationFor2gToggle();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean simultaneousCallingIndications() {
        return getValue(Flags.FLAG_SIMULTANEOUS_CALLING_INDICATIONS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda31
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).simultaneousCallingIndications();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean skipMmiCodeCheckForEmergencyCall() {
        return getValue(Flags.FLAG_SKIP_MMI_CODE_CHECK_FOR_EMERGENCY_CALL, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda57
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).skipMmiCodeCheckForEmergencyCall();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean slicingAdditionalErrorCodes() {
        return getValue(Flags.FLAG_SLICING_ADDITIONAL_ERROR_CODES, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda76
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).slicingAdditionalErrorCodes();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean smsDomainSelectionEnabled() {
        return getValue(Flags.FLAG_SMS_DOMAIN_SELECTION_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda96
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).smsDomainSelectionEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean smsMmsDeliverBroadcastsRedirectToMainUser() {
        return getValue(Flags.FLAG_SMS_MMS_DELIVER_BROADCASTS_REDIRECT_TO_MAIN_USER, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda89
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).smsMmsDeliverBroadcastsRedirectToMainUser();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean starlinkDataBugfix() {
        return getValue(Flags.FLAG_STARLINK_DATA_BUGFIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda106
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).starlinkDataBugfix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean stopSpammingEmergencyNotification() {
        return getValue(Flags.FLAG_STOP_SPAMMING_EMERGENCY_NOTIFICATION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda66
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).stopSpammingEmergencyNotification();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean subscriptionPlanAllowStatusAndEndDate() {
        return getValue(Flags.FLAG_SUBSCRIPTION_PLAN_ALLOW_STATUS_AND_END_DATE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda59
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).subscriptionPlanAllowStatusAndEndDate();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean subscriptionUserAssociationQuery() {
        return getValue(Flags.FLAG_SUBSCRIPTION_USER_ASSOCIATION_QUERY, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).subscriptionUserAssociationQuery();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean supportCarrierServicesForHsum() {
        return getValue(Flags.FLAG_SUPPORT_CARRIER_SERVICES_FOR_HSUM, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).supportCarrierServicesForHsum();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean supportImsMmtelInterface() {
        return getValue(Flags.FLAG_SUPPORT_IMS_MMTEL_INTERFACE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda55
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).supportImsMmtelInterface();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean supportIsimRecord() {
        return getValue(Flags.FLAG_SUPPORT_ISIM_RECORD, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda104
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).supportIsimRecord();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean supportPsimToEsimConversion() {
        return getValue(Flags.FLAG_SUPPORT_PSIM_TO_ESIM_CONVERSION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda107
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).supportPsimToEsimConversion();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean supportSmsOverImsApis() {
        return getValue(Flags.FLAG_SUPPORT_SMS_OVER_IMS_APIS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda88
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).supportSmsOverImsApis();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean temporaryFailuresInCarrierMessagingService() {
        return getValue(Flags.FLAG_TEMPORARY_FAILURES_IN_CARRIER_MESSAGING_SERVICE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda91
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).temporaryFailuresInCarrierMessagingService();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean terminateActiveVideoCallWhenAcceptingSecondVideoCallAsAudioOnly() {
        return getValue(Flags.FLAG_TERMINATE_ACTIVE_VIDEO_CALL_WHEN_ACCEPTING_SECOND_VIDEO_CALL_AS_AUDIO_ONLY, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda87
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).terminateActiveVideoCallWhenAcceptingSecondVideoCallAsAudioOnly();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean threadShred() {
        return getValue(Flags.FLAG_THREAD_SHRED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).threadShred();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean uiccAppCountCheckToCreateChannel() {
        return getValue(Flags.FLAG_UICC_APP_COUNT_CHECK_TO_CREATE_CHANNEL, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda39
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).uiccAppCountCheckToCreateChannel();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean unregisterSmsBroadcastReceiverFromCatService() {
        return getValue(Flags.FLAG_UNREGISTER_SMS_BROADCAST_RECEIVER_FROM_CAT_SERVICE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda79
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).unregisterSmsBroadcastReceiverFromCatService();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean updateImsServiceByGatheringProvisioningChanges() {
        return getValue(Flags.FLAG_UPDATE_IMS_SERVICE_BY_GATHERING_PROVISIONING_CHANGES, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda60
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).updateImsServiceByGatheringProvisioningChanges();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean updateRoamingStateToSetWfcMode() {
        return getValue(Flags.FLAG_UPDATE_ROAMING_STATE_TO_SET_WFC_MODE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda36
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).updateRoamingStateToSetWfcMode();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean useAospDomainSelectionService() {
        return getValue(Flags.FLAG_USE_AOSP_DOMAIN_SELECTION_SERVICE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda68
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useAospDomainSelectionService();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean useCarrierConfigForCfnryTimeViaMmi() {
        return getValue(Flags.FLAG_USE_CARRIER_CONFIG_FOR_CFNRY_TIME_VIA_MMI, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda40
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useCarrierConfigForCfnryTimeViaMmi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean useI18nForMccMapping() {
        return getValue(Flags.FLAG_USE_I18N_FOR_MCC_MAPPING, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda63
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useI18nForMccMapping();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean useOemDomainSelectionService() {
        return getValue(Flags.FLAG_USE_OEM_DOMAIN_SELECTION_SERVICE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda28
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useOemDomainSelectionService();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean useRelaxedIdMatch() {
        return getValue(Flags.FLAG_USE_RELAXED_ID_MATCH, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useRelaxedIdMatch();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean vendorSpecificCellularIdentifierDisclosureIndications() {
        return getValue(Flags.FLAG_VENDOR_SPECIFIC_CELLULAR_IDENTIFIER_DISCLOSURE_INDICATIONS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda111
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).vendorSpecificCellularIdentifierDisclosureIndications();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags
    public boolean workProfileApiSplit() {
        return getValue(Flags.FLAG_WORK_PROFILE_API_SPLIT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda43
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).workProfileApiSplit();
            }
        });
    }

    public boolean isFlagReadOnlyOptimized(String str) {
        return this.mReadOnlyFlagsSet.contains(str) && isOptimizationEnabled();
    }

    protected boolean getValue(String str, Predicate<FeatureFlags> predicate) {
        return this.mGetValueImpl.test(str, predicate);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(Flags.FLAG_ACTION_SIM_PREFERENCE_SETTINGS, Flags.FLAG_ADD_IMS_REDIAL_CODES_FOR_EMERGENCY_CALLS, Flags.FLAG_ADD_RAT_RELATED_SUGGESTED_ACTION_TO_IMS_REGISTRATION, Flags.FLAG_ANSWER_AUDIO_ONLY_WHEN_ANSWERING_VIA_MMI_CODE, Flags.FLAG_AP_DOMAIN_SELECTION_ENABLED, Flags.FLAG_ASYNC_INIT_CARRIER_PRIVILEGES_TRACKER, Flags.FLAG_AUTO_DATA_SWITCH_ENHANCED, Flags.FLAG_AVOID_DELETING_IMS_OBJECT_FROM_CACHE, Flags.FLAG_CALL_EXTRA_FOR_NON_HOLD_SUPPORTED_CARRIERS, Flags.FLAG_CARRIER_CONFIG_CHANGED_CALLBACK_FIX, Flags.FLAG_CARRIER_ENABLED_SATELLITE_FLAG, Flags.FLAG_CARRIER_ID_FROM_CARRIER_IDENTIFIER, Flags.FLAG_CARRIER_RESTRICTION_RULES_ENHANCEMENT, Flags.FLAG_CARRIER_RESTRICTION_STATUS, Flags.FLAG_CARRIER_ROAMING_NB_IOT_NTN, Flags.FLAG_CELLULAR_IDENTIFIER_DISCLOSURE_INDICATIONS, Flags.FLAG_CHANGE_METHOD_OF_OBTAINING_IMS_REGISTRATION_RADIO_TECH, Flags.FLAG_CLEANUP_CARRIER_APP_UPDATE_ENABLED_STATE_LOGIC, Flags.FLAG_CLEANUP_CDMA, Flags.FLAG_CLEAR_CACHED_IMS_PHONE_NUMBER_WHEN_DEVICE_LOST_IMS_REGISTRATION, Flags.FLAG_CONFERENCE_HOLD_UNHOLD_CHANGED_TO_SEND_MESSAGE, Flags.FLAG_DATA_ONLY_CELLULAR_SERVICE, Flags.FLAG_DATA_SERVICE_CHECK, Flags.FLAG_DDS_CALLBACK, Flags.FLAG_DELAY_PHONE_ACCOUNT_REGISTRATION, Flags.FLAG_DEPRECATE_CDMA, Flags.FLAG_DISABLE_CARRIER_NETWORK_CHANGE_ON_CARRIER_APP_LOST, Flags.FLAG_DISABLE_ECBM_BASED_ON_RAT, Flags.FLAG_DO_NOT_OVERRIDE_PRECISE_LABEL, Flags.FLAG_DOMAIN_SELECTION_METRICS_ENABLED, Flags.FLAG_DYNAMIC_DO_NOT_ASK_AGAIN_TEXT, Flags.FLAG_EMERGENCY_CALLBACK_MODE_NOTIFICATION, Flags.FLAG_EMERGENCY_REGISTRATION_STATE, Flags.FLAG_ENABLE_AEAD_ALGORITHMS, Flags.FLAG_ENABLE_MULTIPLE_SA_PROPOSALS, Flags.FLAG_ENABLE_SIP_SUBSCRIBE_RETRY, Flags.FLAG_ENABLE_WPS_CHECK_API_FLAG, Flags.FLAG_ENFORCE_SUBSCRIPTION_USER_FILTER, Flags.FLAG_ENSURE_ACCESS_TO_CALL_SETTINGS_IS_RESTRICTED, Flags.FLAG_ESIM_AVAILABLE_MEMORY, Flags.FLAG_ESIM_BOOTSTRAP_PROVISIONING_FLAG, Flags.FLAG_FORCE_IMSI_CERTIFICATE_DELETE, Flags.FLAG_GEOFENCE_ENHANCEMENT_FOR_BETTER_UX, Flags.FLAG_GET_GROUP_ID_LEVEL2, Flags.FLAG_HANGUP_ACTIVE_CALL_BASED_ON_EMERGENCY_CALL_DOMAIN, Flags.FLAG_HANGUP_EMERGENCY_CALL_FOR_CROSS_SIM_REDIALING, Flags.FLAG_HIDE_PREFER_3G_ITEM, Flags.FLAG_HSUM_BROADCAST, Flags.FLAG_HSUM_PACKAGE_MANAGER, Flags.FLAG_IGNORE_ALREADY_TERMINATED_INCOMING_CALL_BEFORE_REGISTERING_LISTENER, Flags.FLAG_IGNORE_CARRIERID_RESET_FOR_SIM_REMOVAL, Flags.FLAG_IGNORE_MCC_MNC_FROM_OPERATOR_FOR_LOCALE, Flags.FLAG_IMS_RESOLVER_USER_AWARE, Flags.FLAG_IMSI_KEY_RETRY_DOWNLOAD_ON_PHONE_UNLOCK, Flags.FLAG_INCALL_HANDOVER_POLICY, Flags.FLAG_LOG_MMS_SMS_DATABASE_ACCESS_INFO, Flags.FLAG_MMS_DISABLED_ERROR, Flags.FLAG_MMS_GET_APN_FROM_PDSC, Flags.FLAG_NATIONAL_COUNTRY_CODE_FORMATTING_FOR_LOCAL_CALLS, Flags.FLAG_NETWORK_REGISTRATION_INFO_REJECT_CAUSE, Flags.FLAG_NETWORK_VALIDATION, Flags.FLAG_NOTIFY_INITIAL_IMS_PROVISIONING_STATUS, Flags.FLAG_OEM_ENABLED_SATELLITE_FLAG, Flags.FLAG_OEM_ENABLED_SATELLITE_PHASE_2, Flags.FLAG_OEM_PAID_PRIVATE, Flags.FLAG_OPTIMIZATION_APDU_SENDER, Flags.FLAG_PASS_COPIED_CALL_STATE_LIST, Flags.FLAG_PERFORM_CROSS_STACK_REDIAL_CHECK_FOR_EMERGENCY_CALL, Flags.FLAG_PHONE_TYPE_CLEANUP, Flags.FLAG_POWER_DOWN_RACE_FIX, Flags.FLAG_PREVENT_HANGUP_DURING_CALL_MERGE, Flags.FLAG_PREVENT_INVOCATION_REPEAT_OF_RIL_CALL_WHEN_DEVICE_DOES_NOT_SUPPORT_VOICE, Flags.FLAG_PREVENT_SYSTEM_SERVER_AND_PHONE_DEADLOCK, Flags.FLAG_REMAP_DISCONNECT_CAUSE_SIP_REQUEST_CANCELLED, Flags.FLAG_REMOVE_COUNTRY_CODE_FROM_LOCAL_SINGAPORE_CALLS, Flags.FLAG_RESET_MOBILE_NETWORK_SETTINGS, Flags.FLAG_ROBUST_NUMBER_VERIFICATION, Flags.FLAG_SATELLITE_25Q4_APIS, Flags.FLAG_SATELLITE_DATA_METRICS, Flags.FLAG_SATELLITE_EXIT_P2P_SESSION_OUTSIDE_GEOFENCE, Flags.FLAG_SATELLITE_PERSISTENT_LOGGING, Flags.FLAG_SATELLITE_STATE_CHANGE_LISTENER, Flags.FLAG_SATELLITE_SYSTEM_APIS, Flags.FLAG_SECURITY_ALGORITHMS_UPDATE_INDICATIONS, Flags.FLAG_SET_CARRIER_RESTRICTION_STATUS, Flags.FLAG_SET_NO_REPLY_TIMER_FOR_CFNRY, Flags.FLAG_SET_NUMBER_OF_SIM_FOR_IMS_ENABLE, Flags.FLAG_SHOW_CALL_FAIL_NOTIFICATION_FOR_2G_TOGGLE, Flags.FLAG_SIMULTANEOUS_CALLING_INDICATIONS, Flags.FLAG_SKIP_MMI_CODE_CHECK_FOR_EMERGENCY_CALL, Flags.FLAG_SLICING_ADDITIONAL_ERROR_CODES, Flags.FLAG_SMS_DOMAIN_SELECTION_ENABLED, Flags.FLAG_SMS_MMS_DELIVER_BROADCASTS_REDIRECT_TO_MAIN_USER, Flags.FLAG_STARLINK_DATA_BUGFIX, Flags.FLAG_STOP_SPAMMING_EMERGENCY_NOTIFICATION, Flags.FLAG_SUBSCRIPTION_PLAN_ALLOW_STATUS_AND_END_DATE, Flags.FLAG_SUBSCRIPTION_USER_ASSOCIATION_QUERY, Flags.FLAG_SUPPORT_CARRIER_SERVICES_FOR_HSUM, Flags.FLAG_SUPPORT_IMS_MMTEL_INTERFACE, Flags.FLAG_SUPPORT_ISIM_RECORD, Flags.FLAG_SUPPORT_PSIM_TO_ESIM_CONVERSION, Flags.FLAG_SUPPORT_SMS_OVER_IMS_APIS, Flags.FLAG_TEMPORARY_FAILURES_IN_CARRIER_MESSAGING_SERVICE, Flags.FLAG_TERMINATE_ACTIVE_VIDEO_CALL_WHEN_ACCEPTING_SECOND_VIDEO_CALL_AS_AUDIO_ONLY, Flags.FLAG_THREAD_SHRED, Flags.FLAG_UICC_APP_COUNT_CHECK_TO_CREATE_CHANNEL, Flags.FLAG_UNREGISTER_SMS_BROADCAST_RECEIVER_FROM_CAT_SERVICE, Flags.FLAG_UPDATE_IMS_SERVICE_BY_GATHERING_PROVISIONING_CHANGES, Flags.FLAG_UPDATE_ROAMING_STATE_TO_SET_WFC_MODE, Flags.FLAG_USE_AOSP_DOMAIN_SELECTION_SERVICE, Flags.FLAG_USE_CARRIER_CONFIG_FOR_CFNRY_TIME_VIA_MMI, Flags.FLAG_USE_I18N_FOR_MCC_MAPPING, Flags.FLAG_USE_OEM_DOMAIN_SELECTION_SERVICE, Flags.FLAG_USE_RELAXED_ID_MATCH, Flags.FLAG_VENDOR_SPECIFIC_CELLULAR_IDENTIFIER_DISCLOSURE_INDICATIONS, Flags.FLAG_WORK_PROFILE_API_SPLIT);
    }
}
