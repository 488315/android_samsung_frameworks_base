package com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ACTION_SIM_PREFERENCE_SETTINGS = "com.android.internal.telephony.flags.action_sim_preference_settings";
    public static final String FLAG_ADD_IMS_REDIAL_CODES_FOR_EMERGENCY_CALLS = "com.android.internal.telephony.flags.add_ims_redial_codes_for_emergency_calls";
    public static final String FLAG_ADD_RAT_RELATED_SUGGESTED_ACTION_TO_IMS_REGISTRATION = "com.android.internal.telephony.flags.add_rat_related_suggested_action_to_ims_registration";
    public static final String FLAG_ANSWER_AUDIO_ONLY_WHEN_ANSWERING_VIA_MMI_CODE = "com.android.internal.telephony.flags.answer_audio_only_when_answering_via_mmi_code";
    public static final String FLAG_AP_DOMAIN_SELECTION_ENABLED = "com.android.internal.telephony.flags.ap_domain_selection_enabled";
    public static final String FLAG_ASYNC_INIT_CARRIER_PRIVILEGES_TRACKER = "com.android.internal.telephony.flags.async_init_carrier_privileges_tracker";
    public static final String FLAG_AUTO_DATA_SWITCH_ENHANCED = "com.android.internal.telephony.flags.auto_data_switch_enhanced";
    public static final String FLAG_AVOID_DELETING_IMS_OBJECT_FROM_CACHE = "com.android.internal.telephony.flags.avoid_deleting_ims_object_from_cache";
    public static final String FLAG_CALL_EXTRA_FOR_NON_HOLD_SUPPORTED_CARRIERS = "com.android.internal.telephony.flags.call_extra_for_non_hold_supported_carriers";
    public static final String FLAG_CARRIER_CONFIG_CHANGED_CALLBACK_FIX = "com.android.internal.telephony.flags.carrier_config_changed_callback_fix";
    public static final String FLAG_CARRIER_ENABLED_SATELLITE_FLAG = "com.android.internal.telephony.flags.carrier_enabled_satellite_flag";
    public static final String FLAG_CARRIER_ID_FROM_CARRIER_IDENTIFIER = "com.android.internal.telephony.flags.carrier_id_from_carrier_identifier";
    public static final String FLAG_CARRIER_RESTRICTION_RULES_ENHANCEMENT = "com.android.internal.telephony.flags.carrier_restriction_rules_enhancement";
    public static final String FLAG_CARRIER_RESTRICTION_STATUS = "com.android.internal.telephony.flags.carrier_restriction_status";
    public static final String FLAG_CARRIER_ROAMING_NB_IOT_NTN = "com.android.internal.telephony.flags.carrier_roaming_nb_iot_ntn";
    public static final String FLAG_CELLULAR_IDENTIFIER_DISCLOSURE_INDICATIONS = "com.android.internal.telephony.flags.cellular_identifier_disclosure_indications";
    public static final String FLAG_CHANGE_METHOD_OF_OBTAINING_IMS_REGISTRATION_RADIO_TECH = "com.android.internal.telephony.flags.change_method_of_obtaining_ims_registration_radio_tech";
    public static final String FLAG_CLEANUP_CARRIER_APP_UPDATE_ENABLED_STATE_LOGIC = "com.android.internal.telephony.flags.cleanup_carrier_app_update_enabled_state_logic";
    public static final String FLAG_CLEANUP_CDMA = "com.android.internal.telephony.flags.cleanup_cdma";
    public static final String FLAG_CLEAR_CACHED_IMS_PHONE_NUMBER_WHEN_DEVICE_LOST_IMS_REGISTRATION = "com.android.internal.telephony.flags.clear_cached_ims_phone_number_when_device_lost_ims_registration";
    public static final String FLAG_CONFERENCE_HOLD_UNHOLD_CHANGED_TO_SEND_MESSAGE = "com.android.internal.telephony.flags.conference_hold_unhold_changed_to_send_message";
    public static final String FLAG_DATA_ONLY_CELLULAR_SERVICE = "com.android.internal.telephony.flags.data_only_cellular_service";
    public static final String FLAG_DATA_SERVICE_CHECK = "com.android.internal.telephony.flags.data_service_check";
    public static final String FLAG_DDS_CALLBACK = "com.android.internal.telephony.flags.dds_callback";
    public static final String FLAG_DELAY_PHONE_ACCOUNT_REGISTRATION = "com.android.internal.telephony.flags.delay_phone_account_registration";
    public static final String FLAG_DEPRECATE_CDMA = "com.android.internal.telephony.flags.deprecate_cdma";
    public static final String FLAG_DISABLE_CARRIER_NETWORK_CHANGE_ON_CARRIER_APP_LOST = "com.android.internal.telephony.flags.disable_carrier_network_change_on_carrier_app_lost";
    public static final String FLAG_DISABLE_ECBM_BASED_ON_RAT = "com.android.internal.telephony.flags.disable_ecbm_based_on_rat";
    public static final String FLAG_DOMAIN_SELECTION_METRICS_ENABLED = "com.android.internal.telephony.flags.domain_selection_metrics_enabled";
    public static final String FLAG_DO_NOT_OVERRIDE_PRECISE_LABEL = "com.android.internal.telephony.flags.do_not_override_precise_label";
    public static final String FLAG_DYNAMIC_DO_NOT_ASK_AGAIN_TEXT = "com.android.internal.telephony.flags.dynamic_do_not_ask_again_text";
    public static final String FLAG_EMERGENCY_CALLBACK_MODE_NOTIFICATION = "com.android.internal.telephony.flags.emergency_callback_mode_notification";
    public static final String FLAG_EMERGENCY_REGISTRATION_STATE = "com.android.internal.telephony.flags.emergency_registration_state";
    public static final String FLAG_ENABLE_AEAD_ALGORITHMS = "com.android.internal.telephony.flags.enable_aead_algorithms";
    public static final String FLAG_ENABLE_MULTIPLE_SA_PROPOSALS = "com.android.internal.telephony.flags.enable_multiple_sa_proposals";
    public static final String FLAG_ENABLE_SIP_SUBSCRIBE_RETRY = "com.android.internal.telephony.flags.enable_sip_subscribe_retry";
    public static final String FLAG_ENABLE_WPS_CHECK_API_FLAG = "com.android.internal.telephony.flags.enable_wps_check_api_flag";
    public static final String FLAG_ENFORCE_SUBSCRIPTION_USER_FILTER = "com.android.internal.telephony.flags.enforce_subscription_user_filter";
    public static final String FLAG_ENSURE_ACCESS_TO_CALL_SETTINGS_IS_RESTRICTED = "com.android.internal.telephony.flags.ensure_access_to_call_settings_is_restricted";
    public static final String FLAG_ESIM_AVAILABLE_MEMORY = "com.android.internal.telephony.flags.esim_available_memory";
    public static final String FLAG_ESIM_BOOTSTRAP_PROVISIONING_FLAG = "com.android.internal.telephony.flags.esim_bootstrap_provisioning_flag";
    public static final String FLAG_FORCE_IMSI_CERTIFICATE_DELETE = "com.android.internal.telephony.flags.force_imsi_certificate_delete";
    public static final String FLAG_GEOFENCE_ENHANCEMENT_FOR_BETTER_UX = "com.android.internal.telephony.flags.geofence_enhancement_for_better_ux";
    public static final String FLAG_GET_GROUP_ID_LEVEL2 = "com.android.internal.telephony.flags.get_group_id_level2";
    public static final String FLAG_HANGUP_ACTIVE_CALL_BASED_ON_EMERGENCY_CALL_DOMAIN = "com.android.internal.telephony.flags.hangup_active_call_based_on_emergency_call_domain";
    public static final String FLAG_HANGUP_EMERGENCY_CALL_FOR_CROSS_SIM_REDIALING = "com.android.internal.telephony.flags.hangup_emergency_call_for_cross_sim_redialing";
    public static final String FLAG_HIDE_PREFER_3G_ITEM = "com.android.internal.telephony.flags.hide_prefer_3g_item";
    public static final String FLAG_HSUM_BROADCAST = "com.android.internal.telephony.flags.hsum_broadcast";
    public static final String FLAG_HSUM_PACKAGE_MANAGER = "com.android.internal.telephony.flags.hsum_package_manager";
    public static final String FLAG_IGNORE_ALREADY_TERMINATED_INCOMING_CALL_BEFORE_REGISTERING_LISTENER = "com.android.internal.telephony.flags.ignore_already_terminated_incoming_call_before_registering_listener";
    public static final String FLAG_IGNORE_CARRIERID_RESET_FOR_SIM_REMOVAL = "com.android.internal.telephony.flags.ignore_carrierid_reset_for_sim_removal";
    public static final String FLAG_IGNORE_MCC_MNC_FROM_OPERATOR_FOR_LOCALE = "com.android.internal.telephony.flags.ignore_mcc_mnc_from_operator_for_locale";
    public static final String FLAG_IMSI_KEY_RETRY_DOWNLOAD_ON_PHONE_UNLOCK = "com.android.internal.telephony.flags.imsi_key_retry_download_on_phone_unlock";
    public static final String FLAG_IMS_RESOLVER_USER_AWARE = "com.android.internal.telephony.flags.ims_resolver_user_aware";
    public static final String FLAG_INCALL_HANDOVER_POLICY = "com.android.internal.telephony.flags.incall_handover_policy";
    public static final String FLAG_LOG_MMS_SMS_DATABASE_ACCESS_INFO = "com.android.internal.telephony.flags.log_mms_sms_database_access_info";
    public static final String FLAG_MMS_DISABLED_ERROR = "com.android.internal.telephony.flags.mms_disabled_error";
    public static final String FLAG_MMS_GET_APN_FROM_PDSC = "com.android.internal.telephony.flags.mms_get_apn_from_pdsc";
    public static final String FLAG_NATIONAL_COUNTRY_CODE_FORMATTING_FOR_LOCAL_CALLS = "com.android.internal.telephony.flags.national_country_code_formatting_for_local_calls";
    public static final String FLAG_NETWORK_REGISTRATION_INFO_REJECT_CAUSE = "com.android.internal.telephony.flags.network_registration_info_reject_cause";
    public static final String FLAG_NETWORK_VALIDATION = "com.android.internal.telephony.flags.network_validation";
    public static final String FLAG_NOTIFY_INITIAL_IMS_PROVISIONING_STATUS = "com.android.internal.telephony.flags.notify_initial_ims_provisioning_status";
    public static final String FLAG_OEM_ENABLED_SATELLITE_FLAG = "com.android.internal.telephony.flags.oem_enabled_satellite_flag";
    public static final String FLAG_OEM_ENABLED_SATELLITE_PHASE_2 = "com.android.internal.telephony.flags.oem_enabled_satellite_phase_2";
    public static final String FLAG_OEM_PAID_PRIVATE = "com.android.internal.telephony.flags.oem_paid_private";
    public static final String FLAG_OPTIMIZATION_APDU_SENDER = "com.android.internal.telephony.flags.optimization_apdu_sender";
    public static final String FLAG_PASS_COPIED_CALL_STATE_LIST = "com.android.internal.telephony.flags.pass_copied_call_state_list";
    public static final String FLAG_PERFORM_CROSS_STACK_REDIAL_CHECK_FOR_EMERGENCY_CALL = "com.android.internal.telephony.flags.perform_cross_stack_redial_check_for_emergency_call";
    public static final String FLAG_PHONE_TYPE_CLEANUP = "com.android.internal.telephony.flags.phone_type_cleanup";
    public static final String FLAG_POWER_DOWN_RACE_FIX = "com.android.internal.telephony.flags.power_down_race_fix";
    public static final String FLAG_PREVENT_HANGUP_DURING_CALL_MERGE = "com.android.internal.telephony.flags.prevent_hangup_during_call_merge";
    public static final String FLAG_PREVENT_INVOCATION_REPEAT_OF_RIL_CALL_WHEN_DEVICE_DOES_NOT_SUPPORT_VOICE = "com.android.internal.telephony.flags.prevent_invocation_repeat_of_ril_call_when_device_does_not_support_voice";
    public static final String FLAG_PREVENT_SYSTEM_SERVER_AND_PHONE_DEADLOCK = "com.android.internal.telephony.flags.prevent_system_server_and_phone_deadlock";
    public static final String FLAG_REMAP_DISCONNECT_CAUSE_SIP_REQUEST_CANCELLED = "com.android.internal.telephony.flags.remap_disconnect_cause_sip_request_cancelled";
    public static final String FLAG_REMOVE_COUNTRY_CODE_FROM_LOCAL_SINGAPORE_CALLS = "com.android.internal.telephony.flags.remove_country_code_from_local_singapore_calls";
    public static final String FLAG_RESET_MOBILE_NETWORK_SETTINGS = "com.android.internal.telephony.flags.reset_mobile_network_settings";
    public static final String FLAG_ROBUST_NUMBER_VERIFICATION = "com.android.internal.telephony.flags.robust_number_verification";
    public static final String FLAG_SATELLITE_25Q4_APIS = "com.android.internal.telephony.flags.satellite_25q4_apis";
    public static final String FLAG_SATELLITE_DATA_METRICS = "com.android.internal.telephony.flags.satellite_data_metrics";
    public static final String FLAG_SATELLITE_EXIT_P2P_SESSION_OUTSIDE_GEOFENCE = "com.android.internal.telephony.flags.satellite_exit_p2p_session_outside_geofence";
    public static final String FLAG_SATELLITE_PERSISTENT_LOGGING = "com.android.internal.telephony.flags.satellite_persistent_logging";
    public static final String FLAG_SATELLITE_STATE_CHANGE_LISTENER = "com.android.internal.telephony.flags.satellite_state_change_listener";
    public static final String FLAG_SATELLITE_SYSTEM_APIS = "com.android.internal.telephony.flags.satellite_system_apis";
    public static final String FLAG_SECURITY_ALGORITHMS_UPDATE_INDICATIONS = "com.android.internal.telephony.flags.security_algorithms_update_indications";
    public static final String FLAG_SET_CARRIER_RESTRICTION_STATUS = "com.android.internal.telephony.flags.set_carrier_restriction_status";
    public static final String FLAG_SET_NO_REPLY_TIMER_FOR_CFNRY = "com.android.internal.telephony.flags.set_no_reply_timer_for_cfnry";
    public static final String FLAG_SET_NUMBER_OF_SIM_FOR_IMS_ENABLE = "com.android.internal.telephony.flags.set_number_of_sim_for_ims_enable";
    public static final String FLAG_SHOW_CALL_FAIL_NOTIFICATION_FOR_2G_TOGGLE = "com.android.internal.telephony.flags.show_call_fail_notification_for_2g_toggle";
    public static final String FLAG_SIMULTANEOUS_CALLING_INDICATIONS = "com.android.internal.telephony.flags.simultaneous_calling_indications";
    public static final String FLAG_SKIP_MMI_CODE_CHECK_FOR_EMERGENCY_CALL = "com.android.internal.telephony.flags.skip_mmi_code_check_for_emergency_call";
    public static final String FLAG_SLICING_ADDITIONAL_ERROR_CODES = "com.android.internal.telephony.flags.slicing_additional_error_codes";
    public static final String FLAG_SMS_DOMAIN_SELECTION_ENABLED = "com.android.internal.telephony.flags.sms_domain_selection_enabled";
    public static final String FLAG_SMS_MMS_DELIVER_BROADCASTS_REDIRECT_TO_MAIN_USER = "com.android.internal.telephony.flags.sms_mms_deliver_broadcasts_redirect_to_main_user";
    public static final String FLAG_STARLINK_DATA_BUGFIX = "com.android.internal.telephony.flags.starlink_data_bugfix";
    public static final String FLAG_STOP_SPAMMING_EMERGENCY_NOTIFICATION = "com.android.internal.telephony.flags.stop_spamming_emergency_notification";
    public static final String FLAG_SUBSCRIPTION_PLAN_ALLOW_STATUS_AND_END_DATE = "com.android.internal.telephony.flags.subscription_plan_allow_status_and_end_date";
    public static final String FLAG_SUBSCRIPTION_USER_ASSOCIATION_QUERY = "com.android.internal.telephony.flags.subscription_user_association_query";
    public static final String FLAG_SUPPORT_CARRIER_SERVICES_FOR_HSUM = "com.android.internal.telephony.flags.support_carrier_services_for_hsum";
    public static final String FLAG_SUPPORT_IMS_MMTEL_INTERFACE = "com.android.internal.telephony.flags.support_ims_mmtel_interface";
    public static final String FLAG_SUPPORT_ISIM_RECORD = "com.android.internal.telephony.flags.support_isim_record";
    public static final String FLAG_SUPPORT_PSIM_TO_ESIM_CONVERSION = "com.android.internal.telephony.flags.support_psim_to_esim_conversion";
    public static final String FLAG_SUPPORT_SMS_OVER_IMS_APIS = "com.android.internal.telephony.flags.support_sms_over_ims_apis";
    public static final String FLAG_TEMPORARY_FAILURES_IN_CARRIER_MESSAGING_SERVICE = "com.android.internal.telephony.flags.temporary_failures_in_carrier_messaging_service";
    public static final String FLAG_TERMINATE_ACTIVE_VIDEO_CALL_WHEN_ACCEPTING_SECOND_VIDEO_CALL_AS_AUDIO_ONLY = "com.android.internal.telephony.flags.terminate_active_video_call_when_accepting_second_video_call_as_audio_only";
    public static final String FLAG_THREAD_SHRED = "com.android.internal.telephony.flags.thread_shred";
    public static final String FLAG_UICC_APP_COUNT_CHECK_TO_CREATE_CHANNEL = "com.android.internal.telephony.flags.uicc_app_count_check_to_create_channel";
    public static final String FLAG_UNREGISTER_SMS_BROADCAST_RECEIVER_FROM_CAT_SERVICE = "com.android.internal.telephony.flags.unregister_sms_broadcast_receiver_from_cat_service";
    public static final String FLAG_UPDATE_IMS_SERVICE_BY_GATHERING_PROVISIONING_CHANGES = "com.android.internal.telephony.flags.update_ims_service_by_gathering_provisioning_changes";
    public static final String FLAG_UPDATE_ROAMING_STATE_TO_SET_WFC_MODE = "com.android.internal.telephony.flags.update_roaming_state_to_set_wfc_mode";
    public static final String FLAG_USE_AOSP_DOMAIN_SELECTION_SERVICE = "com.android.internal.telephony.flags.use_aosp_domain_selection_service";
    public static final String FLAG_USE_CARRIER_CONFIG_FOR_CFNRY_TIME_VIA_MMI = "com.android.internal.telephony.flags.use_carrier_config_for_cfnry_time_via_mmi";
    public static final String FLAG_USE_I18N_FOR_MCC_MAPPING = "com.android.internal.telephony.flags.use_i18n_for_mcc_mapping";
    public static final String FLAG_USE_OEM_DOMAIN_SELECTION_SERVICE = "com.android.internal.telephony.flags.use_oem_domain_selection_service";
    public static final String FLAG_USE_RELAXED_ID_MATCH = "com.android.internal.telephony.flags.use_relaxed_id_match";
    public static final String FLAG_VENDOR_SPECIFIC_CELLULAR_IDENTIFIER_DISCLOSURE_INDICATIONS = "com.android.internal.telephony.flags.vendor_specific_cellular_identifier_disclosure_indications";
    public static final String FLAG_WORK_PROFILE_API_SPLIT = "com.android.internal.telephony.flags.work_profile_api_split";

    public static boolean actionSimPreferenceSettings() {
        return FEATURE_FLAGS.actionSimPreferenceSettings();
    }

    public static boolean addImsRedialCodesForEmergencyCalls() {
        return FEATURE_FLAGS.addImsRedialCodesForEmergencyCalls();
    }

    public static boolean addRatRelatedSuggestedActionToImsRegistration() {
        return FEATURE_FLAGS.addRatRelatedSuggestedActionToImsRegistration();
    }

    public static boolean answerAudioOnlyWhenAnsweringViaMmiCode() {
        return FEATURE_FLAGS.answerAudioOnlyWhenAnsweringViaMmiCode();
    }

    public static boolean apDomainSelectionEnabled() {
        return FEATURE_FLAGS.apDomainSelectionEnabled();
    }

    public static boolean asyncInitCarrierPrivilegesTracker() {
        return FEATURE_FLAGS.asyncInitCarrierPrivilegesTracker();
    }

    public static boolean autoDataSwitchEnhanced() {
        return FEATURE_FLAGS.autoDataSwitchEnhanced();
    }

    public static boolean avoidDeletingImsObjectFromCache() {
        return FEATURE_FLAGS.avoidDeletingImsObjectFromCache();
    }

    public static boolean callExtraForNonHoldSupportedCarriers() {
        return FEATURE_FLAGS.callExtraForNonHoldSupportedCarriers();
    }

    public static boolean carrierConfigChangedCallbackFix() {
        return FEATURE_FLAGS.carrierConfigChangedCallbackFix();
    }

    public static boolean carrierEnabledSatelliteFlag() {
        return FEATURE_FLAGS.carrierEnabledSatelliteFlag();
    }

    public static boolean carrierIdFromCarrierIdentifier() {
        return FEATURE_FLAGS.carrierIdFromCarrierIdentifier();
    }

    public static boolean carrierRestrictionRulesEnhancement() {
        return FEATURE_FLAGS.carrierRestrictionRulesEnhancement();
    }

    public static boolean carrierRestrictionStatus() {
        return FEATURE_FLAGS.carrierRestrictionStatus();
    }

    public static boolean carrierRoamingNbIotNtn() {
        return FEATURE_FLAGS.carrierRoamingNbIotNtn();
    }

    public static boolean cellularIdentifierDisclosureIndications() {
        return FEATURE_FLAGS.cellularIdentifierDisclosureIndications();
    }

    public static boolean changeMethodOfObtainingImsRegistrationRadioTech() {
        return FEATURE_FLAGS.changeMethodOfObtainingImsRegistrationRadioTech();
    }

    public static boolean cleanupCarrierAppUpdateEnabledStateLogic() {
        return FEATURE_FLAGS.cleanupCarrierAppUpdateEnabledStateLogic();
    }

    public static boolean cleanupCdma() {
        return FEATURE_FLAGS.cleanupCdma();
    }

    public static boolean clearCachedImsPhoneNumberWhenDeviceLostImsRegistration() {
        return FEATURE_FLAGS.clearCachedImsPhoneNumberWhenDeviceLostImsRegistration();
    }

    public static boolean conferenceHoldUnholdChangedToSendMessage() {
        return FEATURE_FLAGS.conferenceHoldUnholdChangedToSendMessage();
    }

    public static boolean dataOnlyCellularService() {
        return FEATURE_FLAGS.dataOnlyCellularService();
    }

    public static boolean dataServiceCheck() {
        return FEATURE_FLAGS.dataServiceCheck();
    }

    public static boolean ddsCallback() {
        return FEATURE_FLAGS.ddsCallback();
    }

    public static boolean delayPhoneAccountRegistration() {
        return FEATURE_FLAGS.delayPhoneAccountRegistration();
    }

    public static boolean deprecateCdma() {
        return FEATURE_FLAGS.deprecateCdma();
    }

    public static boolean disableCarrierNetworkChangeOnCarrierAppLost() {
        return FEATURE_FLAGS.disableCarrierNetworkChangeOnCarrierAppLost();
    }

    public static boolean disableEcbmBasedOnRat() {
        return FEATURE_FLAGS.disableEcbmBasedOnRat();
    }

    public static boolean doNotOverridePreciseLabel() {
        return FEATURE_FLAGS.doNotOverridePreciseLabel();
    }

    public static boolean domainSelectionMetricsEnabled() {
        return FEATURE_FLAGS.domainSelectionMetricsEnabled();
    }

    public static boolean dynamicDoNotAskAgainText() {
        return FEATURE_FLAGS.dynamicDoNotAskAgainText();
    }

    public static boolean emergencyCallbackModeNotification() {
        return FEATURE_FLAGS.emergencyCallbackModeNotification();
    }

    public static boolean emergencyRegistrationState() {
        return FEATURE_FLAGS.emergencyRegistrationState();
    }

    public static boolean enableAeadAlgorithms() {
        return FEATURE_FLAGS.enableAeadAlgorithms();
    }

    public static boolean enableMultipleSaProposals() {
        return FEATURE_FLAGS.enableMultipleSaProposals();
    }

    public static boolean enableSipSubscribeRetry() {
        return FEATURE_FLAGS.enableSipSubscribeRetry();
    }

    public static boolean enableWpsCheckApiFlag() {
        return FEATURE_FLAGS.enableWpsCheckApiFlag();
    }

    public static boolean enforceSubscriptionUserFilter() {
        return FEATURE_FLAGS.enforceSubscriptionUserFilter();
    }

    public static boolean ensureAccessToCallSettingsIsRestricted() {
        return FEATURE_FLAGS.ensureAccessToCallSettingsIsRestricted();
    }

    public static boolean esimAvailableMemory() {
        return FEATURE_FLAGS.esimAvailableMemory();
    }

    public static boolean esimBootstrapProvisioningFlag() {
        return FEATURE_FLAGS.esimBootstrapProvisioningFlag();
    }

    public static boolean forceImsiCertificateDelete() {
        return FEATURE_FLAGS.forceImsiCertificateDelete();
    }

    public static boolean geofenceEnhancementForBetterUx() {
        return FEATURE_FLAGS.geofenceEnhancementForBetterUx();
    }

    public static boolean getGroupIdLevel2() {
        return FEATURE_FLAGS.getGroupIdLevel2();
    }

    public static boolean hangupActiveCallBasedOnEmergencyCallDomain() {
        return FEATURE_FLAGS.hangupActiveCallBasedOnEmergencyCallDomain();
    }

    public static boolean hangupEmergencyCallForCrossSimRedialing() {
        return FEATURE_FLAGS.hangupEmergencyCallForCrossSimRedialing();
    }

    public static boolean hidePrefer3gItem() {
        return FEATURE_FLAGS.hidePrefer3gItem();
    }

    public static boolean hsumBroadcast() {
        return FEATURE_FLAGS.hsumBroadcast();
    }

    public static boolean hsumPackageManager() {
        return FEATURE_FLAGS.hsumPackageManager();
    }

    public static boolean ignoreAlreadyTerminatedIncomingCallBeforeRegisteringListener() {
        return FEATURE_FLAGS.ignoreAlreadyTerminatedIncomingCallBeforeRegisteringListener();
    }

    public static boolean ignoreCarrieridResetForSimRemoval() {
        return FEATURE_FLAGS.ignoreCarrieridResetForSimRemoval();
    }

    public static boolean ignoreMccMncFromOperatorForLocale() {
        return FEATURE_FLAGS.ignoreMccMncFromOperatorForLocale();
    }

    public static boolean imsResolverUserAware() {
        return FEATURE_FLAGS.imsResolverUserAware();
    }

    public static boolean imsiKeyRetryDownloadOnPhoneUnlock() {
        return FEATURE_FLAGS.imsiKeyRetryDownloadOnPhoneUnlock();
    }

    public static boolean incallHandoverPolicy() {
        return FEATURE_FLAGS.incallHandoverPolicy();
    }

    public static boolean logMmsSmsDatabaseAccessInfo() {
        return FEATURE_FLAGS.logMmsSmsDatabaseAccessInfo();
    }

    public static boolean mmsDisabledError() {
        return FEATURE_FLAGS.mmsDisabledError();
    }

    public static boolean mmsGetApnFromPdsc() {
        return FEATURE_FLAGS.mmsGetApnFromPdsc();
    }

    public static boolean nationalCountryCodeFormattingForLocalCalls() {
        return FEATURE_FLAGS.nationalCountryCodeFormattingForLocalCalls();
    }

    public static boolean networkRegistrationInfoRejectCause() {
        return FEATURE_FLAGS.networkRegistrationInfoRejectCause();
    }

    public static boolean networkValidation() {
        return FEATURE_FLAGS.networkValidation();
    }

    public static boolean notifyInitialImsProvisioningStatus() {
        return FEATURE_FLAGS.notifyInitialImsProvisioningStatus();
    }

    public static boolean oemEnabledSatelliteFlag() {
        return FEATURE_FLAGS.oemEnabledSatelliteFlag();
    }

    public static boolean oemEnabledSatellitePhase2() {
        return FEATURE_FLAGS.oemEnabledSatellitePhase2();
    }

    public static boolean oemPaidPrivate() {
        return FEATURE_FLAGS.oemPaidPrivate();
    }

    public static boolean optimizationApduSender() {
        return FEATURE_FLAGS.optimizationApduSender();
    }

    public static boolean passCopiedCallStateList() {
        return FEATURE_FLAGS.passCopiedCallStateList();
    }

    public static boolean performCrossStackRedialCheckForEmergencyCall() {
        return FEATURE_FLAGS.performCrossStackRedialCheckForEmergencyCall();
    }

    public static boolean phoneTypeCleanup() {
        return FEATURE_FLAGS.phoneTypeCleanup();
    }

    public static boolean powerDownRaceFix() {
        return FEATURE_FLAGS.powerDownRaceFix();
    }

    public static boolean preventHangupDuringCallMerge() {
        return FEATURE_FLAGS.preventHangupDuringCallMerge();
    }

    public static boolean preventInvocationRepeatOfRilCallWhenDeviceDoesNotSupportVoice() {
        return FEATURE_FLAGS.preventInvocationRepeatOfRilCallWhenDeviceDoesNotSupportVoice();
    }

    public static boolean preventSystemServerAndPhoneDeadlock() {
        return FEATURE_FLAGS.preventSystemServerAndPhoneDeadlock();
    }

    public static boolean remapDisconnectCauseSipRequestCancelled() {
        return FEATURE_FLAGS.remapDisconnectCauseSipRequestCancelled();
    }

    public static boolean removeCountryCodeFromLocalSingaporeCalls() {
        return FEATURE_FLAGS.removeCountryCodeFromLocalSingaporeCalls();
    }

    public static boolean resetMobileNetworkSettings() {
        return FEATURE_FLAGS.resetMobileNetworkSettings();
    }

    public static boolean robustNumberVerification() {
        return FEATURE_FLAGS.robustNumberVerification();
    }

    public static boolean satellite25q4Apis() {
        return FEATURE_FLAGS.satellite25q4Apis();
    }

    public static boolean satelliteDataMetrics() {
        return FEATURE_FLAGS.satelliteDataMetrics();
    }

    public static boolean satelliteExitP2pSessionOutsideGeofence() {
        return FEATURE_FLAGS.satelliteExitP2pSessionOutsideGeofence();
    }

    public static boolean satellitePersistentLogging() {
        return FEATURE_FLAGS.satellitePersistentLogging();
    }

    public static boolean satelliteStateChangeListener() {
        return FEATURE_FLAGS.satelliteStateChangeListener();
    }

    public static boolean satelliteSystemApis() {
        return FEATURE_FLAGS.satelliteSystemApis();
    }

    public static boolean securityAlgorithmsUpdateIndications() {
        return FEATURE_FLAGS.securityAlgorithmsUpdateIndications();
    }

    public static boolean setCarrierRestrictionStatus() {
        return FEATURE_FLAGS.setCarrierRestrictionStatus();
    }

    public static boolean setNoReplyTimerForCfnry() {
        return FEATURE_FLAGS.setNoReplyTimerForCfnry();
    }

    public static boolean setNumberOfSimForImsEnable() {
        return FEATURE_FLAGS.setNumberOfSimForImsEnable();
    }

    public static boolean showCallFailNotificationFor2gToggle() {
        return FEATURE_FLAGS.showCallFailNotificationFor2gToggle();
    }

    public static boolean simultaneousCallingIndications() {
        return FEATURE_FLAGS.simultaneousCallingIndications();
    }

    public static boolean skipMmiCodeCheckForEmergencyCall() {
        return FEATURE_FLAGS.skipMmiCodeCheckForEmergencyCall();
    }

    public static boolean slicingAdditionalErrorCodes() {
        return FEATURE_FLAGS.slicingAdditionalErrorCodes();
    }

    public static boolean smsDomainSelectionEnabled() {
        return FEATURE_FLAGS.smsDomainSelectionEnabled();
    }

    public static boolean smsMmsDeliverBroadcastsRedirectToMainUser() {
        return FEATURE_FLAGS.smsMmsDeliverBroadcastsRedirectToMainUser();
    }

    public static boolean starlinkDataBugfix() {
        return FEATURE_FLAGS.starlinkDataBugfix();
    }

    public static boolean stopSpammingEmergencyNotification() {
        return FEATURE_FLAGS.stopSpammingEmergencyNotification();
    }

    public static boolean subscriptionPlanAllowStatusAndEndDate() {
        return FEATURE_FLAGS.subscriptionPlanAllowStatusAndEndDate();
    }

    public static boolean subscriptionUserAssociationQuery() {
        return FEATURE_FLAGS.subscriptionUserAssociationQuery();
    }

    public static boolean supportCarrierServicesForHsum() {
        return FEATURE_FLAGS.supportCarrierServicesForHsum();
    }

    public static boolean supportImsMmtelInterface() {
        return FEATURE_FLAGS.supportImsMmtelInterface();
    }

    public static boolean supportIsimRecord() {
        return FEATURE_FLAGS.supportIsimRecord();
    }

    public static boolean supportPsimToEsimConversion() {
        return FEATURE_FLAGS.supportPsimToEsimConversion();
    }

    public static boolean supportSmsOverImsApis() {
        return FEATURE_FLAGS.supportSmsOverImsApis();
    }

    public static boolean temporaryFailuresInCarrierMessagingService() {
        return FEATURE_FLAGS.temporaryFailuresInCarrierMessagingService();
    }

    public static boolean terminateActiveVideoCallWhenAcceptingSecondVideoCallAsAudioOnly() {
        return FEATURE_FLAGS.terminateActiveVideoCallWhenAcceptingSecondVideoCallAsAudioOnly();
    }

    public static boolean threadShred() {
        return FEATURE_FLAGS.threadShred();
    }

    public static boolean uiccAppCountCheckToCreateChannel() {
        return FEATURE_FLAGS.uiccAppCountCheckToCreateChannel();
    }

    public static boolean unregisterSmsBroadcastReceiverFromCatService() {
        return FEATURE_FLAGS.unregisterSmsBroadcastReceiverFromCatService();
    }

    public static boolean updateImsServiceByGatheringProvisioningChanges() {
        return FEATURE_FLAGS.updateImsServiceByGatheringProvisioningChanges();
    }

    public static boolean updateRoamingStateToSetWfcMode() {
        return FEATURE_FLAGS.updateRoamingStateToSetWfcMode();
    }

    public static boolean useAospDomainSelectionService() {
        return FEATURE_FLAGS.useAospDomainSelectionService();
    }

    public static boolean useCarrierConfigForCfnryTimeViaMmi() {
        return FEATURE_FLAGS.useCarrierConfigForCfnryTimeViaMmi();
    }

    public static boolean useI18nForMccMapping() {
        return FEATURE_FLAGS.useI18nForMccMapping();
    }

    public static boolean useOemDomainSelectionService() {
        return FEATURE_FLAGS.useOemDomainSelectionService();
    }

    public static boolean useRelaxedIdMatch() {
        return FEATURE_FLAGS.useRelaxedIdMatch();
    }

    public static boolean vendorSpecificCellularIdentifierDisclosureIndications() {
        return FEATURE_FLAGS.vendorSpecificCellularIdentifierDisclosureIndications();
    }

    public static boolean workProfileApiSplit() {
        return FEATURE_FLAGS.workProfileApiSplit();
    }
}
