package com.android.internal.telephony.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_ADD_ANOMALY_WHEN_NOTIFY_CONFIG_CHANGED_WITH_INVALID_PHONE, Flags.FLAG_ADD_RAT_RELATED_SUGGESTED_ACTION_TO_IMS_REGISTRATION, Flags.FLAG_ALLOW_MMTEL_IN_NON_VOPS, Flags.FLAG_ANSWER_AUDIO_ONLY_WHEN_ANSWERING_VIA_MMI_CODE, Flags.FLAG_AP_DOMAIN_SELECTION_ENABLED, Flags.FLAG_APN_SETTING_FIELD_SUPPORT_FLAG, Flags.FLAG_AUTO_DATA_SWITCH_ALLOW_ROAMING, Flags.FLAG_AUTO_DATA_SWITCH_USES_DATA_ENABLED, Flags.FLAG_BACKUP_AND_RESTORE_FOR_ENABLE_2G, Flags.FLAG_CALL_EXTRA_FOR_NON_HOLD_SUPPORTED_CARRIERS, Flags.FLAG_CARRIER_ENABLED_SATELLITE_FLAG, Flags.FLAG_CARRIER_RESTRICTION_RULES_ENHANCEMENT, Flags.FLAG_CARRIER_RESTRICTION_STATUS, Flags.FLAG_CARRIER_ROAMING_NB_IOT_NTN, Flags.FLAG_CHANGE_METHOD_OF_OBTAINING_IMS_REGISTRATION_RADIO_TECH, Flags.FLAG_CLEANUP_OPEN_LOGICAL_CHANNEL_RECORD_ON_DISPOSE, Flags.FLAG_CLEAR_CACHED_IMS_PHONE_NUMBER_WHEN_DEVICE_LOST_IMS_REGISTRATION, Flags.FLAG_COMBINE_RIL_DEATH_HANDLE, Flags.FLAG_CONFERENCE_HOLD_UNHOLD_CHANGED_TO_SEND_MESSAGE, Flags.FLAG_DATA_CALL_SESSION_STATS_CAPTURES_CROSS_SIM_CALLING, Flags.FLAG_DATA_ONLY_CELLULAR_SERVICE, Flags.FLAG_DATA_ONLY_SERVICE_ALLOW_EMERGENCY_CALL_ONLY, Flags.FLAG_DATA_RAT_METRIC_ENABLED, Flags.FLAG_DISMISS_NETWORK_SELECTION_NOTIFICATION_ON_SIM_DISABLE, Flags.FLAG_DO_NOT_OVERRIDE_PRECISE_LABEL, Flags.FLAG_DOMAIN_SELECTION_METRICS_ENABLED, Flags.FLAG_DSRS_DIAGNOSTICS_ENABLED, Flags.FLAG_EMERGENCY_REGISTRATION_STATE, Flags.FLAG_ENABLE_AEAD_ALGORITHMS, Flags.FLAG_ENABLE_CARRIER_CONFIG_N1_CONTROL_ATTEMPT2, Flags.FLAG_ENABLE_IDENTIFIER_DISCLOSURE_TRANSPARENCY, Flags.FLAG_ENABLE_IDENTIFIER_DISCLOSURE_TRANSPARENCY_UNSOL_EVENTS, Flags.FLAG_ENABLE_MODEM_CIPHER_TRANSPARENCY, Flags.FLAG_ENABLE_MODEM_CIPHER_TRANSPARENCY_UNSOL_EVENTS, Flags.FLAG_ENABLE_MULTIPLE_SA_PROPOSALS, Flags.FLAG_ENABLE_SIP_SUBSCRIBE_RETRY, Flags.FLAG_ENABLE_TELEPHONY_ANALYTICS, Flags.FLAG_ENABLE_WPS_CHECK_API_FLAG, Flags.FLAG_ENFORCE_SUBSCRIPTION_USER_FILTER, Flags.FLAG_ENFORCE_TELEPHONY_FEATURE_MAPPING, Flags.FLAG_ENFORCE_TELEPHONY_FEATURE_MAPPING_FOR_PUBLIC_APIS, Flags.FLAG_ENSURE_ACCESS_TO_CALL_SETTINGS_IS_RESTRICTED, Flags.FLAG_ESIM_AVAILABLE_MEMORY, Flags.FLAG_ESIM_BOOTSTRAP_PROVISIONING_FLAG, Flags.FLAG_FIX_CRASH_ON_GETTING_CONFIG_WHEN_PHONE_IS_GONE, Flags.FLAG_FORCE_IWLAN_MMS, Flags.FLAG_GEOFENCE_ENHANCEMENT_FOR_BETTER_UX, Flags.FLAG_HIDE_PREFER_3G_ITEM, Flags.FLAG_HIDE_PREINSTALLED_CARRIER_APP_AT_MOST_ONCE, Flags.FLAG_HIDE_ROAMING_ICON, Flags.FLAG_IGNORE_ALREADY_TERMINATED_INCOMING_CALL_BEFORE_REGISTERING_LISTENER, Flags.FLAG_IGNORE_EXISTING_NETWORKS_FOR_INTERNET_ALLOWED_CHECKING, Flags.FLAG_IMSI_KEY_RETRY_DOWNLOAD_ON_PHONE_UNLOCK, Flags.FLAG_KEEP_EMPTY_REQUESTS_NETWORK, Flags.FLAG_LOG_MMS_SMS_DATABASE_ACCESS_INFO, Flags.FLAG_METERED_EMBB_URLCC, Flags.FLAG_MINIMAL_TELEPHONY_CDM_CHECK, Flags.FLAG_MINIMAL_TELEPHONY_MANAGERS_CONDITIONAL_ON_FEATURES, Flags.FLAG_MMS_DISABLED_ERROR, Flags.FLAG_MMS_GET_APN_FROM_PDSC, Flags.FLAG_NETWORK_REGISTRATION_INFO_REJECT_CAUSE, Flags.FLAG_NETWORK_VALIDATION, Flags.FLAG_NOTIFY_DATA_ACTIVITY_CHANGED_WITH_SLOT, Flags.FLAG_NOTIFY_INITIAL_IMS_PROVISIONING_STATUS, Flags.FLAG_OEM_ENABLED_SATELLITE_FLAG, Flags.FLAG_PREVENT_INVOCATION_REPEAT_OF_RIL_CALL_WHEN_DEVICE_DOES_NOT_SUPPORT_VOICE, Flags.FLAG_PREVENT_SYSTEM_SERVER_AND_PHONE_DEADLOCK, Flags.FLAG_RECONNECT_QUALIFIED_NETWORK, Flags.FLAG_REFINE_PREFERRED_DATA_PROFILE_SELECTION, Flags.FLAG_RELAX_HO_TEARDOWN, Flags.FLAG_REMOVE_COUNTRY_CODE_FROM_LOCAL_SINGAPORE_CALLS, Flags.FLAG_REORGANIZE_ROAMING_NOTIFICATION, Flags.FLAG_RESET_MOBILE_NETWORK_SETTINGS, Flags.FLAG_RESET_PRIMARY_SIM_DEFAULT_VALUES, Flags.FLAG_ROAMING_NOTIFICATION_FOR_SINGLE_DATA_NETWORK, Flags.FLAG_SAFER_GET_PHONE_NUMBER, Flags.FLAG_SATELLITE_INTERNET, Flags.FLAG_SATELLITE_PERSISTENT_LOGGING, Flags.FLAG_SET_CARRIER_RESTRICTION_STATUS, Flags.FLAG_SET_NO_REPLY_TIMER_FOR_CFNRY, Flags.FLAG_SET_NUMBER_OF_SIM_FOR_IMS_ENABLE, Flags.FLAG_SHOW_CALL_FAIL_NOTIFICATION_FOR_2G_TOGGLE, Flags.FLAG_SHOW_CALL_ID_AND_CALL_WAITING_IN_ADDITIONAL_SETTINGS_MENU, Flags.FLAG_SIMULTANEOUS_CALLING_INDICATIONS, Flags.FLAG_SLICING_ADDITIONAL_ERROR_CODES, Flags.FLAG_SMS_DOMAIN_SELECTION_ENABLED, Flags.FLAG_STOP_SPAMMING_EMERGENCY_NOTIFICATION, Flags.FLAG_SUBSCRIPTION_USER_ASSOCIATION_QUERY, Flags.FLAG_SUPPORT_NR_SA_RRC_IDLE, Flags.FLAG_SUPPORT_PSIM_TO_ESIM_CONVERSION, Flags.FLAG_TERMINATE_ACTIVE_VIDEO_CALL_WHEN_ACCEPTING_SECOND_VIDEO_CALL_AS_AUDIO_ONLY, Flags.FLAG_UNREGISTER_SMS_BROADCAST_RECEIVER_FROM_CAT_SERVICE, Flags.FLAG_UNTHROTTLE_CHECK_TRANSPORT, Flags.FLAG_UPDATE_IMS_SERVICE_BY_GATHERING_PROVISIONING_CHANGES, Flags.FLAG_UPDATE_ROAMING_STATE_TO_SET_WFC_MODE, Flags.FLAG_USE_ALARM_CALLBACK, Flags.FLAG_USE_AOSP_DOMAIN_SELECTION_SERVICE, Flags.FLAG_USE_OEM_DOMAIN_SELECTION_SERVICE, Flags.FLAG_USE_RELAXED_ID_MATCH, Flags.FLAG_VONR_ENABLED_METRIC, Flags.FLAG_WORK_PROFILE_API_SPLIT, ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean addAnomalyWhenNotifyConfigChangedWithInvalidPhone() {
        return getValue(Flags.FLAG_ADD_ANOMALY_WHEN_NOTIFY_CONFIG_CHANGED_WITH_INVALID_PHONE, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).addAnomalyWhenNotifyConfigChangedWithInvalidPhone();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean addRatRelatedSuggestedActionToImsRegistration() {
        return getValue(Flags.FLAG_ADD_RAT_RELATED_SUGGESTED_ACTION_TO_IMS_REGISTRATION, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda81
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).addRatRelatedSuggestedActionToImsRegistration();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean allowMmtelInNonVops() {
        return getValue(Flags.FLAG_ALLOW_MMTEL_IN_NON_VOPS, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda80
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).allowMmtelInNonVops();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean answerAudioOnlyWhenAnsweringViaMmiCode() {
        return getValue(Flags.FLAG_ANSWER_AUDIO_ONLY_WHEN_ANSWERING_VIA_MMI_CODE, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda21
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).answerAudioOnlyWhenAnsweringViaMmiCode();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean apDomainSelectionEnabled() {
        return getValue(Flags.FLAG_AP_DOMAIN_SELECTION_ENABLED, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).apDomainSelectionEnabled();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean apnSettingFieldSupportFlag() {
        return getValue(Flags.FLAG_APN_SETTING_FIELD_SUPPORT_FLAG, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda39
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).apnSettingFieldSupportFlag();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean autoDataSwitchAllowRoaming() {
        return getValue(Flags.FLAG_AUTO_DATA_SWITCH_ALLOW_ROAMING, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda97
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).autoDataSwitchAllowRoaming();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean autoDataSwitchUsesDataEnabled() {
        return getValue(Flags.FLAG_AUTO_DATA_SWITCH_USES_DATA_ENABLED, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda68
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).autoDataSwitchUsesDataEnabled();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean backupAndRestoreForEnable2g() {
        return getValue(Flags.FLAG_BACKUP_AND_RESTORE_FOR_ENABLE_2G, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda46
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).backupAndRestoreForEnable2g();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean callExtraForNonHoldSupportedCarriers() {
        return getValue(Flags.FLAG_CALL_EXTRA_FOR_NON_HOLD_SUPPORTED_CARRIERS, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda31
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).callExtraForNonHoldSupportedCarriers();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean carrierEnabledSatelliteFlag() {
        return getValue(Flags.FLAG_CARRIER_ENABLED_SATELLITE_FLAG, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda79
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).carrierEnabledSatelliteFlag();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean carrierRestrictionRulesEnhancement() {
        return getValue(Flags.FLAG_CARRIER_RESTRICTION_RULES_ENHANCEMENT, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda42
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).carrierRestrictionRulesEnhancement();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean carrierRestrictionStatus() {
        return getValue(Flags.FLAG_CARRIER_RESTRICTION_STATUS, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda49
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).carrierRestrictionStatus();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean carrierRoamingNbIotNtn() {
        return getValue(Flags.FLAG_CARRIER_ROAMING_NB_IOT_NTN, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda98
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).carrierRoamingNbIotNtn();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean changeMethodOfObtainingImsRegistrationRadioTech() {
        return getValue(Flags.FLAG_CHANGE_METHOD_OF_OBTAINING_IMS_REGISTRATION_RADIO_TECH, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda73
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).changeMethodOfObtainingImsRegistrationRadioTech();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean cleanupOpenLogicalChannelRecordOnDispose() {
        return getValue(Flags.FLAG_CLEANUP_OPEN_LOGICAL_CHANNEL_RECORD_ON_DISPOSE, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda82
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cleanupOpenLogicalChannelRecordOnDispose();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean clearCachedImsPhoneNumberWhenDeviceLostImsRegistration() {
        return getValue(Flags.FLAG_CLEAR_CACHED_IMS_PHONE_NUMBER_WHEN_DEVICE_LOST_IMS_REGISTRATION, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda37
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).clearCachedImsPhoneNumberWhenDeviceLostImsRegistration();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean combineRilDeathHandle() {
        return getValue(Flags.FLAG_COMBINE_RIL_DEATH_HANDLE, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda90
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).combineRilDeathHandle();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean conferenceHoldUnholdChangedToSendMessage() {
        return getValue(Flags.FLAG_CONFERENCE_HOLD_UNHOLD_CHANGED_TO_SEND_MESSAGE, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda20
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).conferenceHoldUnholdChangedToSendMessage();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean dataCallSessionStatsCapturesCrossSimCalling() {
        return getValue(Flags.FLAG_DATA_CALL_SESSION_STATS_CAPTURES_CROSS_SIM_CALLING, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).dataCallSessionStatsCapturesCrossSimCalling();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean dataOnlyCellularService() {
        return getValue(Flags.FLAG_DATA_ONLY_CELLULAR_SERVICE, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda65
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).dataOnlyCellularService();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean dataOnlyServiceAllowEmergencyCallOnly() {
        return getValue(Flags.FLAG_DATA_ONLY_SERVICE_ALLOW_EMERGENCY_CALL_ONLY, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).dataOnlyServiceAllowEmergencyCallOnly();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean dataRatMetricEnabled() {
        return getValue(Flags.FLAG_DATA_RAT_METRIC_ENABLED, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda34
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).dataRatMetricEnabled();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean dismissNetworkSelectionNotificationOnSimDisable() {
        return getValue(Flags.FLAG_DISMISS_NETWORK_SELECTION_NOTIFICATION_ON_SIM_DISABLE, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda54
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).dismissNetworkSelectionNotificationOnSimDisable();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean doNotOverridePreciseLabel() {
        return getValue(Flags.FLAG_DO_NOT_OVERRIDE_PRECISE_LABEL, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda93
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).doNotOverridePreciseLabel();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean domainSelectionMetricsEnabled() {
        return getValue(Flags.FLAG_DOMAIN_SELECTION_METRICS_ENABLED, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda88
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).domainSelectionMetricsEnabled();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean dsrsDiagnosticsEnabled() {
        return getValue(Flags.FLAG_DSRS_DIAGNOSTICS_ENABLED, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda40
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).dsrsDiagnosticsEnabled();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean emergencyRegistrationState() {
        return getValue(Flags.FLAG_EMERGENCY_REGISTRATION_STATE, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda55
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).emergencyRegistrationState();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enableAeadAlgorithms() {
        return getValue(Flags.FLAG_ENABLE_AEAD_ALGORITHMS, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda36
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableAeadAlgorithms();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enableCarrierConfigN1ControlAttempt2() {
        return getValue(Flags.FLAG_ENABLE_CARRIER_CONFIG_N1_CONTROL_ATTEMPT2, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda58
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableCarrierConfigN1ControlAttempt2();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enableIdentifierDisclosureTransparency() {
        return getValue(Flags.FLAG_ENABLE_IDENTIFIER_DISCLOSURE_TRANSPARENCY, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda28
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableIdentifierDisclosureTransparency();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enableIdentifierDisclosureTransparencyUnsolEvents() {
        return getValue(Flags.FLAG_ENABLE_IDENTIFIER_DISCLOSURE_TRANSPARENCY_UNSOL_EVENTS, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda60
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableIdentifierDisclosureTransparencyUnsolEvents();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enableModemCipherTransparency() {
        return getValue(Flags.FLAG_ENABLE_MODEM_CIPHER_TRANSPARENCY, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda64
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableModemCipherTransparency();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enableModemCipherTransparencyUnsolEvents() {
        return getValue(Flags.FLAG_ENABLE_MODEM_CIPHER_TRANSPARENCY_UNSOL_EVENTS, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda38
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableModemCipherTransparencyUnsolEvents();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enableMultipleSaProposals() {
        return getValue(Flags.FLAG_ENABLE_MULTIPLE_SA_PROPOSALS, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda23
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableMultipleSaProposals();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enableSipSubscribeRetry() {
        return getValue(Flags.FLAG_ENABLE_SIP_SUBSCRIBE_RETRY, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda77
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableSipSubscribeRetry();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enableTelephonyAnalytics() {
        return getValue(Flags.FLAG_ENABLE_TELEPHONY_ANALYTICS, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableTelephonyAnalytics();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enableWpsCheckApiFlag() {
        return getValue(Flags.FLAG_ENABLE_WPS_CHECK_API_FLAG, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableWpsCheckApiFlag();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enforceSubscriptionUserFilter() {
        return getValue(Flags.FLAG_ENFORCE_SUBSCRIPTION_USER_FILTER, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enforceSubscriptionUserFilter();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enforceTelephonyFeatureMapping() {
        return getValue(Flags.FLAG_ENFORCE_TELEPHONY_FEATURE_MAPPING, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda87
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enforceTelephonyFeatureMapping();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enforceTelephonyFeatureMappingForPublicApis() {
        return getValue(Flags.FLAG_ENFORCE_TELEPHONY_FEATURE_MAPPING_FOR_PUBLIC_APIS, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda53
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enforceTelephonyFeatureMappingForPublicApis();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean ensureAccessToCallSettingsIsRestricted() {
        return getValue(Flags.FLAG_ENSURE_ACCESS_TO_CALL_SETTINGS_IS_RESTRICTED, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda78
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).ensureAccessToCallSettingsIsRestricted();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean esimAvailableMemory() {
        return getValue(Flags.FLAG_ESIM_AVAILABLE_MEMORY, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda47
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).esimAvailableMemory();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean esimBootstrapProvisioningFlag() {
        return getValue(Flags.FLAG_ESIM_BOOTSTRAP_PROVISIONING_FLAG, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda94
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).esimBootstrapProvisioningFlag();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean fixCrashOnGettingConfigWhenPhoneIsGone() {
        return getValue(Flags.FLAG_FIX_CRASH_ON_GETTING_CONFIG_WHEN_PHONE_IS_GONE, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda59
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fixCrashOnGettingConfigWhenPhoneIsGone();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean forceIwlanMms() {
        return getValue(Flags.FLAG_FORCE_IWLAN_MMS, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda29
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).forceIwlanMms();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean geofenceEnhancementForBetterUx() {
        return getValue(Flags.FLAG_GEOFENCE_ENHANCEMENT_FOR_BETTER_UX, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda92
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).geofenceEnhancementForBetterUx();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean hidePrefer3gItem() {
        return getValue(Flags.FLAG_HIDE_PREFER_3G_ITEM, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda70
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).hidePrefer3gItem();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean hidePreinstalledCarrierAppAtMostOnce() {
        return getValue(Flags.FLAG_HIDE_PREINSTALLED_CARRIER_APP_AT_MOST_ONCE, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda63
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).hidePreinstalledCarrierAppAtMostOnce();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean hideRoamingIcon() {
        return getValue(Flags.FLAG_HIDE_ROAMING_ICON, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda67
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).hideRoamingIcon();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean ignoreAlreadyTerminatedIncomingCallBeforeRegisteringListener() {
        return getValue(Flags.FLAG_IGNORE_ALREADY_TERMINATED_INCOMING_CALL_BEFORE_REGISTERING_LISTENER, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda18
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).ignoreAlreadyTerminatedIncomingCallBeforeRegisteringListener();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean ignoreExistingNetworksForInternetAllowedChecking() {
        return getValue(Flags.FLAG_IGNORE_EXISTING_NETWORKS_FOR_INTERNET_ALLOWED_CHECKING, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda24
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).ignoreExistingNetworksForInternetAllowedChecking();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean imsiKeyRetryDownloadOnPhoneUnlock() {
        return getValue(Flags.FLAG_IMSI_KEY_RETRY_DOWNLOAD_ON_PHONE_UNLOCK, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda51
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).imsiKeyRetryDownloadOnPhoneUnlock();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean keepEmptyRequestsNetwork() {
        return getValue(Flags.FLAG_KEEP_EMPTY_REQUESTS_NETWORK, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda100
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).keepEmptyRequestsNetwork();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean logMmsSmsDatabaseAccessInfo() {
        return getValue(Flags.FLAG_LOG_MMS_SMS_DATABASE_ACCESS_INFO, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda19
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).logMmsSmsDatabaseAccessInfo();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean meteredEmbbUrlcc() {
        return getValue(Flags.FLAG_METERED_EMBB_URLCC, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda45
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).meteredEmbbUrlcc();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean minimalTelephonyCdmCheck() {
        return getValue(Flags.FLAG_MINIMAL_TELEPHONY_CDM_CHECK, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).minimalTelephonyCdmCheck();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean minimalTelephonyManagersConditionalOnFeatures() {
        return getValue(Flags.FLAG_MINIMAL_TELEPHONY_MANAGERS_CONDITIONAL_ON_FEATURES, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda32
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).minimalTelephonyManagersConditionalOnFeatures();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean mmsDisabledError() {
        return getValue(Flags.FLAG_MMS_DISABLED_ERROR, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda22
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).mmsDisabledError();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean mmsGetApnFromPdsc() {
        return getValue(Flags.FLAG_MMS_GET_APN_FROM_PDSC, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).mmsGetApnFromPdsc();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean networkRegistrationInfoRejectCause() {
        return getValue(Flags.FLAG_NETWORK_REGISTRATION_INFO_REJECT_CAUSE, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda33
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).networkRegistrationInfoRejectCause();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean networkValidation() {
        return getValue(Flags.FLAG_NETWORK_VALIDATION, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda72
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).networkValidation();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean notifyDataActivityChangedWithSlot() {
        return getValue(Flags.FLAG_NOTIFY_DATA_ACTIVITY_CHANGED_WITH_SLOT, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).notifyDataActivityChangedWithSlot();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean notifyInitialImsProvisioningStatus() {
        return getValue(Flags.FLAG_NOTIFY_INITIAL_IMS_PROVISIONING_STATUS, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda86
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).notifyInitialImsProvisioningStatus();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean oemEnabledSatelliteFlag() {
        return getValue(Flags.FLAG_OEM_ENABLED_SATELLITE_FLAG, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda71
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).oemEnabledSatelliteFlag();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean preventInvocationRepeatOfRilCallWhenDeviceDoesNotSupportVoice() {
        return getValue(Flags.FLAG_PREVENT_INVOCATION_REPEAT_OF_RIL_CALL_WHEN_DEVICE_DOES_NOT_SUPPORT_VOICE, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).preventInvocationRepeatOfRilCallWhenDeviceDoesNotSupportVoice();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean preventSystemServerAndPhoneDeadlock() {
        return getValue(Flags.FLAG_PREVENT_SYSTEM_SERVER_AND_PHONE_DEADLOCK, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda48
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).preventSystemServerAndPhoneDeadlock();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean reconnectQualifiedNetwork() {
        return getValue(Flags.FLAG_RECONNECT_QUALIFIED_NETWORK, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).reconnectQualifiedNetwork();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean refinePreferredDataProfileSelection() {
        return getValue(Flags.FLAG_REFINE_PREFERRED_DATA_PROFILE_SELECTION, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda99
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).refinePreferredDataProfileSelection();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean relaxHoTeardown() {
        return getValue(Flags.FLAG_RELAX_HO_TEARDOWN, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda74
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).relaxHoTeardown();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean removeCountryCodeFromLocalSingaporeCalls() {
        return getValue(Flags.FLAG_REMOVE_COUNTRY_CODE_FROM_LOCAL_SINGAPORE_CALLS, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda43
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).removeCountryCodeFromLocalSingaporeCalls();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean reorganizeRoamingNotification() {
        return getValue(Flags.FLAG_REORGANIZE_ROAMING_NOTIFICATION, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).reorganizeRoamingNotification();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean resetMobileNetworkSettings() {
        return getValue(Flags.FLAG_RESET_MOBILE_NETWORK_SETTINGS, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).resetMobileNetworkSettings();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean resetPrimarySimDefaultValues() {
        return getValue(Flags.FLAG_RESET_PRIMARY_SIM_DEFAULT_VALUES, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda83
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).resetPrimarySimDefaultValues();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean roamingNotificationForSingleDataNetwork() {
        return getValue(Flags.FLAG_ROAMING_NOTIFICATION_FOR_SINGLE_DATA_NETWORK, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda61
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).roamingNotificationForSingleDataNetwork();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean saferGetPhoneNumber() {
        return getValue(Flags.FLAG_SAFER_GET_PHONE_NUMBER, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda85
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).saferGetPhoneNumber();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean satelliteInternet() {
        return getValue(Flags.FLAG_SATELLITE_INTERNET, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda35
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).satelliteInternet();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean satellitePersistentLogging() {
        return getValue(Flags.FLAG_SATELLITE_PERSISTENT_LOGGING, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).satellitePersistentLogging();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean setCarrierRestrictionStatus() {
        return getValue(Flags.FLAG_SET_CARRIER_RESTRICTION_STATUS, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda44
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).setCarrierRestrictionStatus();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean setNoReplyTimerForCfnry() {
        return getValue(Flags.FLAG_SET_NO_REPLY_TIMER_FOR_CFNRY, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda52
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).setNoReplyTimerForCfnry();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean setNumberOfSimForImsEnable() {
        return getValue(Flags.FLAG_SET_NUMBER_OF_SIM_FOR_IMS_ENABLE, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda66
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).setNumberOfSimForImsEnable();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean showCallFailNotificationFor2gToggle() {
        return getValue(Flags.FLAG_SHOW_CALL_FAIL_NOTIFICATION_FOR_2G_TOGGLE, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda84
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).showCallFailNotificationFor2gToggle();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean showCallIdAndCallWaitingInAdditionalSettingsMenu() {
        return getValue(Flags.FLAG_SHOW_CALL_ID_AND_CALL_WAITING_IN_ADDITIONAL_SETTINGS_MENU, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda62
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).showCallIdAndCallWaitingInAdditionalSettingsMenu();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean simultaneousCallingIndications() {
        return getValue(Flags.FLAG_SIMULTANEOUS_CALLING_INDICATIONS, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda76
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).simultaneousCallingIndications();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean slicingAdditionalErrorCodes() {
        return getValue(Flags.FLAG_SLICING_ADDITIONAL_ERROR_CODES, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).slicingAdditionalErrorCodes();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean smsDomainSelectionEnabled() {
        return getValue(Flags.FLAG_SMS_DOMAIN_SELECTION_ENABLED, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda27
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).smsDomainSelectionEnabled();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean stopSpammingEmergencyNotification() {
        return getValue(Flags.FLAG_STOP_SPAMMING_EMERGENCY_NOTIFICATION, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda75
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).stopSpammingEmergencyNotification();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean subscriptionUserAssociationQuery() {
        return getValue(Flags.FLAG_SUBSCRIPTION_USER_ASSOCIATION_QUERY, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda30
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).subscriptionUserAssociationQuery();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean supportNrSaRrcIdle() {
        return getValue(Flags.FLAG_SUPPORT_NR_SA_RRC_IDLE, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda50
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).supportNrSaRrcIdle();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean supportPsimToEsimConversion() {
        return getValue(Flags.FLAG_SUPPORT_PSIM_TO_ESIM_CONVERSION, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).supportPsimToEsimConversion();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean terminateActiveVideoCallWhenAcceptingSecondVideoCallAsAudioOnly() {
        return getValue(Flags.FLAG_TERMINATE_ACTIVE_VIDEO_CALL_WHEN_ACCEPTING_SECOND_VIDEO_CALL_AS_AUDIO_ONLY, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda56
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).terminateActiveVideoCallWhenAcceptingSecondVideoCallAsAudioOnly();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean unregisterSmsBroadcastReceiverFromCatService() {
        return getValue(Flags.FLAG_UNREGISTER_SMS_BROADCAST_RECEIVER_FROM_CAT_SERVICE, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda41
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).unregisterSmsBroadcastReceiverFromCatService();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean unthrottleCheckTransport() {
        return getValue(Flags.FLAG_UNTHROTTLE_CHECK_TRANSPORT, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda57
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).unthrottleCheckTransport();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean updateImsServiceByGatheringProvisioningChanges() {
        return getValue(Flags.FLAG_UPDATE_IMS_SERVICE_BY_GATHERING_PROVISIONING_CHANGES, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda91
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).updateImsServiceByGatheringProvisioningChanges();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean updateRoamingStateToSetWfcMode() {
        return getValue(Flags.FLAG_UPDATE_ROAMING_STATE_TO_SET_WFC_MODE, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda95
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).updateRoamingStateToSetWfcMode();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean useAlarmCallback() {
        return getValue(Flags.FLAG_USE_ALARM_CALLBACK, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useAlarmCallback();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean useAospDomainSelectionService() {
        return getValue(Flags.FLAG_USE_AOSP_DOMAIN_SELECTION_SERVICE, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda25
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useAospDomainSelectionService();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean useOemDomainSelectionService() {
        return getValue(Flags.FLAG_USE_OEM_DOMAIN_SELECTION_SERVICE, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda69
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useOemDomainSelectionService();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean useRelaxedIdMatch() {
        return getValue(Flags.FLAG_USE_RELAXED_ID_MATCH, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda26
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useRelaxedIdMatch();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean vonrEnabledMetric() {
        return getValue(Flags.FLAG_VONR_ENABLED_METRIC, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda96
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).vonrEnabledMetric();
            }
        });
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean workProfileApiSplit() {
        return getValue(Flags.FLAG_WORK_PROFILE_API_SPLIT, new Predicate() { // from class: com.android.internal.telephony.flags.CustomFeatureFlags$$ExternalSyntheticLambda89
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).workProfileApiSplit();
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
        return Arrays.asList(Flags.FLAG_ADD_ANOMALY_WHEN_NOTIFY_CONFIG_CHANGED_WITH_INVALID_PHONE, Flags.FLAG_ADD_RAT_RELATED_SUGGESTED_ACTION_TO_IMS_REGISTRATION, Flags.FLAG_ALLOW_MMTEL_IN_NON_VOPS, Flags.FLAG_ANSWER_AUDIO_ONLY_WHEN_ANSWERING_VIA_MMI_CODE, Flags.FLAG_AP_DOMAIN_SELECTION_ENABLED, Flags.FLAG_APN_SETTING_FIELD_SUPPORT_FLAG, Flags.FLAG_AUTO_DATA_SWITCH_ALLOW_ROAMING, Flags.FLAG_AUTO_DATA_SWITCH_USES_DATA_ENABLED, Flags.FLAG_BACKUP_AND_RESTORE_FOR_ENABLE_2G, Flags.FLAG_CALL_EXTRA_FOR_NON_HOLD_SUPPORTED_CARRIERS, Flags.FLAG_CARRIER_ENABLED_SATELLITE_FLAG, Flags.FLAG_CARRIER_RESTRICTION_RULES_ENHANCEMENT, Flags.FLAG_CARRIER_RESTRICTION_STATUS, Flags.FLAG_CARRIER_ROAMING_NB_IOT_NTN, Flags.FLAG_CHANGE_METHOD_OF_OBTAINING_IMS_REGISTRATION_RADIO_TECH, Flags.FLAG_CLEANUP_OPEN_LOGICAL_CHANNEL_RECORD_ON_DISPOSE, Flags.FLAG_CLEAR_CACHED_IMS_PHONE_NUMBER_WHEN_DEVICE_LOST_IMS_REGISTRATION, Flags.FLAG_COMBINE_RIL_DEATH_HANDLE, Flags.FLAG_CONFERENCE_HOLD_UNHOLD_CHANGED_TO_SEND_MESSAGE, Flags.FLAG_DATA_CALL_SESSION_STATS_CAPTURES_CROSS_SIM_CALLING, Flags.FLAG_DATA_ONLY_CELLULAR_SERVICE, Flags.FLAG_DATA_ONLY_SERVICE_ALLOW_EMERGENCY_CALL_ONLY, Flags.FLAG_DATA_RAT_METRIC_ENABLED, Flags.FLAG_DISMISS_NETWORK_SELECTION_NOTIFICATION_ON_SIM_DISABLE, Flags.FLAG_DO_NOT_OVERRIDE_PRECISE_LABEL, Flags.FLAG_DOMAIN_SELECTION_METRICS_ENABLED, Flags.FLAG_DSRS_DIAGNOSTICS_ENABLED, Flags.FLAG_EMERGENCY_REGISTRATION_STATE, Flags.FLAG_ENABLE_AEAD_ALGORITHMS, Flags.FLAG_ENABLE_CARRIER_CONFIG_N1_CONTROL_ATTEMPT2, Flags.FLAG_ENABLE_IDENTIFIER_DISCLOSURE_TRANSPARENCY, Flags.FLAG_ENABLE_IDENTIFIER_DISCLOSURE_TRANSPARENCY_UNSOL_EVENTS, Flags.FLAG_ENABLE_MODEM_CIPHER_TRANSPARENCY, Flags.FLAG_ENABLE_MODEM_CIPHER_TRANSPARENCY_UNSOL_EVENTS, Flags.FLAG_ENABLE_MULTIPLE_SA_PROPOSALS, Flags.FLAG_ENABLE_SIP_SUBSCRIBE_RETRY, Flags.FLAG_ENABLE_TELEPHONY_ANALYTICS, Flags.FLAG_ENABLE_WPS_CHECK_API_FLAG, Flags.FLAG_ENFORCE_SUBSCRIPTION_USER_FILTER, Flags.FLAG_ENFORCE_TELEPHONY_FEATURE_MAPPING, Flags.FLAG_ENFORCE_TELEPHONY_FEATURE_MAPPING_FOR_PUBLIC_APIS, Flags.FLAG_ENSURE_ACCESS_TO_CALL_SETTINGS_IS_RESTRICTED, Flags.FLAG_ESIM_AVAILABLE_MEMORY, Flags.FLAG_ESIM_BOOTSTRAP_PROVISIONING_FLAG, Flags.FLAG_FIX_CRASH_ON_GETTING_CONFIG_WHEN_PHONE_IS_GONE, Flags.FLAG_FORCE_IWLAN_MMS, Flags.FLAG_GEOFENCE_ENHANCEMENT_FOR_BETTER_UX, Flags.FLAG_HIDE_PREFER_3G_ITEM, Flags.FLAG_HIDE_PREINSTALLED_CARRIER_APP_AT_MOST_ONCE, Flags.FLAG_HIDE_ROAMING_ICON, Flags.FLAG_IGNORE_ALREADY_TERMINATED_INCOMING_CALL_BEFORE_REGISTERING_LISTENER, Flags.FLAG_IGNORE_EXISTING_NETWORKS_FOR_INTERNET_ALLOWED_CHECKING, Flags.FLAG_IMSI_KEY_RETRY_DOWNLOAD_ON_PHONE_UNLOCK, Flags.FLAG_KEEP_EMPTY_REQUESTS_NETWORK, Flags.FLAG_LOG_MMS_SMS_DATABASE_ACCESS_INFO, Flags.FLAG_METERED_EMBB_URLCC, Flags.FLAG_MINIMAL_TELEPHONY_CDM_CHECK, Flags.FLAG_MINIMAL_TELEPHONY_MANAGERS_CONDITIONAL_ON_FEATURES, Flags.FLAG_MMS_DISABLED_ERROR, Flags.FLAG_MMS_GET_APN_FROM_PDSC, Flags.FLAG_NETWORK_REGISTRATION_INFO_REJECT_CAUSE, Flags.FLAG_NETWORK_VALIDATION, Flags.FLAG_NOTIFY_DATA_ACTIVITY_CHANGED_WITH_SLOT, Flags.FLAG_NOTIFY_INITIAL_IMS_PROVISIONING_STATUS, Flags.FLAG_OEM_ENABLED_SATELLITE_FLAG, Flags.FLAG_PREVENT_INVOCATION_REPEAT_OF_RIL_CALL_WHEN_DEVICE_DOES_NOT_SUPPORT_VOICE, Flags.FLAG_PREVENT_SYSTEM_SERVER_AND_PHONE_DEADLOCK, Flags.FLAG_RECONNECT_QUALIFIED_NETWORK, Flags.FLAG_REFINE_PREFERRED_DATA_PROFILE_SELECTION, Flags.FLAG_RELAX_HO_TEARDOWN, Flags.FLAG_REMOVE_COUNTRY_CODE_FROM_LOCAL_SINGAPORE_CALLS, Flags.FLAG_REORGANIZE_ROAMING_NOTIFICATION, Flags.FLAG_RESET_MOBILE_NETWORK_SETTINGS, Flags.FLAG_RESET_PRIMARY_SIM_DEFAULT_VALUES, Flags.FLAG_ROAMING_NOTIFICATION_FOR_SINGLE_DATA_NETWORK, Flags.FLAG_SAFER_GET_PHONE_NUMBER, Flags.FLAG_SATELLITE_INTERNET, Flags.FLAG_SATELLITE_PERSISTENT_LOGGING, Flags.FLAG_SET_CARRIER_RESTRICTION_STATUS, Flags.FLAG_SET_NO_REPLY_TIMER_FOR_CFNRY, Flags.FLAG_SET_NUMBER_OF_SIM_FOR_IMS_ENABLE, Flags.FLAG_SHOW_CALL_FAIL_NOTIFICATION_FOR_2G_TOGGLE, Flags.FLAG_SHOW_CALL_ID_AND_CALL_WAITING_IN_ADDITIONAL_SETTINGS_MENU, Flags.FLAG_SIMULTANEOUS_CALLING_INDICATIONS, Flags.FLAG_SLICING_ADDITIONAL_ERROR_CODES, Flags.FLAG_SMS_DOMAIN_SELECTION_ENABLED, Flags.FLAG_STOP_SPAMMING_EMERGENCY_NOTIFICATION, Flags.FLAG_SUBSCRIPTION_USER_ASSOCIATION_QUERY, Flags.FLAG_SUPPORT_NR_SA_RRC_IDLE, Flags.FLAG_SUPPORT_PSIM_TO_ESIM_CONVERSION, Flags.FLAG_TERMINATE_ACTIVE_VIDEO_CALL_WHEN_ACCEPTING_SECOND_VIDEO_CALL_AS_AUDIO_ONLY, Flags.FLAG_UNREGISTER_SMS_BROADCAST_RECEIVER_FROM_CAT_SERVICE, Flags.FLAG_UNTHROTTLE_CHECK_TRANSPORT, Flags.FLAG_UPDATE_IMS_SERVICE_BY_GATHERING_PROVISIONING_CHANGES, Flags.FLAG_UPDATE_ROAMING_STATE_TO_SET_WFC_MODE, Flags.FLAG_USE_ALARM_CALLBACK, Flags.FLAG_USE_AOSP_DOMAIN_SELECTION_SERVICE, Flags.FLAG_USE_OEM_DOMAIN_SELECTION_SERVICE, Flags.FLAG_USE_RELAXED_ID_MATCH, Flags.FLAG_VONR_ENABLED_METRIC, Flags.FLAG_WORK_PROFILE_API_SPLIT);
    }
}
