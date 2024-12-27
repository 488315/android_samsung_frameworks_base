package com.android.internal.telephony.flags;

public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ADD_ANOMALY_WHEN_NOTIFY_CONFIG_CHANGED_WITH_INVALID_PHONE =
            "com.android.internal.telephony.flags.add_anomaly_when_notify_config_changed_with_invalid_phone";
    public static final String FLAG_ADD_RAT_RELATED_SUGGESTED_ACTION_TO_IMS_REGISTRATION =
            "com.android.internal.telephony.flags.add_rat_related_suggested_action_to_ims_registration";
    public static final String FLAG_ALLOW_MMTEL_IN_NON_VOPS =
            "com.android.internal.telephony.flags.allow_mmtel_in_non_vops";
    public static final String FLAG_ANSWER_AUDIO_ONLY_WHEN_ANSWERING_VIA_MMI_CODE =
            "com.android.internal.telephony.flags.answer_audio_only_when_answering_via_mmi_code";
    public static final String FLAG_APN_SETTING_FIELD_SUPPORT_FLAG =
            "com.android.internal.telephony.flags.apn_setting_field_support_flag";
    public static final String FLAG_AP_DOMAIN_SELECTION_ENABLED =
            "com.android.internal.telephony.flags.ap_domain_selection_enabled";
    public static final String FLAG_AUTO_DATA_SWITCH_ALLOW_ROAMING =
            "com.android.internal.telephony.flags.auto_data_switch_allow_roaming";
    public static final String FLAG_AUTO_DATA_SWITCH_USES_DATA_ENABLED =
            "com.android.internal.telephony.flags.auto_data_switch_uses_data_enabled";
    public static final String FLAG_BACKUP_AND_RESTORE_FOR_ENABLE_2G =
            "com.android.internal.telephony.flags.backup_and_restore_for_enable_2g";
    public static final String FLAG_CALL_EXTRA_FOR_NON_HOLD_SUPPORTED_CARRIERS =
            "com.android.internal.telephony.flags.call_extra_for_non_hold_supported_carriers";
    public static final String FLAG_CARRIER_ENABLED_SATELLITE_FLAG =
            "com.android.internal.telephony.flags.carrier_enabled_satellite_flag";
    public static final String FLAG_CARRIER_RESTRICTION_RULES_ENHANCEMENT =
            "com.android.internal.telephony.flags.carrier_restriction_rules_enhancement";
    public static final String FLAG_CARRIER_RESTRICTION_STATUS =
            "com.android.internal.telephony.flags.carrier_restriction_status";
    public static final String FLAG_CARRIER_ROAMING_NB_IOT_NTN =
            "com.android.internal.telephony.flags.carrier_roaming_nb_iot_ntn";
    public static final String FLAG_CHANGE_METHOD_OF_OBTAINING_IMS_REGISTRATION_RADIO_TECH =
            "com.android.internal.telephony.flags.change_method_of_obtaining_ims_registration_radio_tech";
    public static final String FLAG_CLEANUP_OPEN_LOGICAL_CHANNEL_RECORD_ON_DISPOSE =
            "com.android.internal.telephony.flags.cleanup_open_logical_channel_record_on_dispose";
    public static final String
            FLAG_CLEAR_CACHED_IMS_PHONE_NUMBER_WHEN_DEVICE_LOST_IMS_REGISTRATION =
                    "com.android.internal.telephony.flags.clear_cached_ims_phone_number_when_device_lost_ims_registration";
    public static final String FLAG_COMBINE_RIL_DEATH_HANDLE =
            "com.android.internal.telephony.flags.combine_ril_death_handle";
    public static final String FLAG_CONFERENCE_HOLD_UNHOLD_CHANGED_TO_SEND_MESSAGE =
            "com.android.internal.telephony.flags.conference_hold_unhold_changed_to_send_message";
    public static final String FLAG_DATA_CALL_SESSION_STATS_CAPTURES_CROSS_SIM_CALLING =
            "com.android.internal.telephony.flags.data_call_session_stats_captures_cross_sim_calling";
    public static final String FLAG_DATA_ONLY_CELLULAR_SERVICE =
            "com.android.internal.telephony.flags.data_only_cellular_service";
    public static final String FLAG_DATA_ONLY_SERVICE_ALLOW_EMERGENCY_CALL_ONLY =
            "com.android.internal.telephony.flags.data_only_service_allow_emergency_call_only";
    public static final String FLAG_DATA_RAT_METRIC_ENABLED =
            "com.android.internal.telephony.flags.data_rat_metric_enabled";
    public static final String FLAG_DISMISS_NETWORK_SELECTION_NOTIFICATION_ON_SIM_DISABLE =
            "com.android.internal.telephony.flags.dismiss_network_selection_notification_on_sim_disable";
    public static final String FLAG_DOMAIN_SELECTION_METRICS_ENABLED =
            "com.android.internal.telephony.flags.domain_selection_metrics_enabled";
    public static final String FLAG_DO_NOT_OVERRIDE_PRECISE_LABEL =
            "com.android.internal.telephony.flags.do_not_override_precise_label";
    public static final String FLAG_DSRS_DIAGNOSTICS_ENABLED =
            "com.android.internal.telephony.flags.dsrs_diagnostics_enabled";
    public static final String FLAG_EMERGENCY_REGISTRATION_STATE =
            "com.android.internal.telephony.flags.emergency_registration_state";
    public static final String FLAG_ENABLE_AEAD_ALGORITHMS =
            "com.android.internal.telephony.flags.enable_aead_algorithms";
    public static final String FLAG_ENABLE_CARRIER_CONFIG_N1_CONTROL_ATTEMPT2 =
            "com.android.internal.telephony.flags.enable_carrier_config_n1_control_attempt2";
    public static final String FLAG_ENABLE_IDENTIFIER_DISCLOSURE_TRANSPARENCY =
            "com.android.internal.telephony.flags.enable_identifier_disclosure_transparency";
    public static final String FLAG_ENABLE_IDENTIFIER_DISCLOSURE_TRANSPARENCY_UNSOL_EVENTS =
            "com.android.internal.telephony.flags.enable_identifier_disclosure_transparency_unsol_events";
    public static final String FLAG_ENABLE_MODEM_CIPHER_TRANSPARENCY =
            "com.android.internal.telephony.flags.enable_modem_cipher_transparency";
    public static final String FLAG_ENABLE_MODEM_CIPHER_TRANSPARENCY_UNSOL_EVENTS =
            "com.android.internal.telephony.flags.enable_modem_cipher_transparency_unsol_events";
    public static final String FLAG_ENABLE_MULTIPLE_SA_PROPOSALS =
            "com.android.internal.telephony.flags.enable_multiple_sa_proposals";
    public static final String FLAG_ENABLE_SIP_SUBSCRIBE_RETRY =
            "com.android.internal.telephony.flags.enable_sip_subscribe_retry";
    public static final String FLAG_ENABLE_TELEPHONY_ANALYTICS =
            "com.android.internal.telephony.flags.enable_telephony_analytics";
    public static final String FLAG_ENABLE_WPS_CHECK_API_FLAG =
            "com.android.internal.telephony.flags.enable_wps_check_api_flag";
    public static final String FLAG_ENFORCE_SUBSCRIPTION_USER_FILTER =
            "com.android.internal.telephony.flags.enforce_subscription_user_filter";
    public static final String FLAG_ENFORCE_TELEPHONY_FEATURE_MAPPING =
            "com.android.internal.telephony.flags.enforce_telephony_feature_mapping";
    public static final String FLAG_ENFORCE_TELEPHONY_FEATURE_MAPPING_FOR_PUBLIC_APIS =
            "com.android.internal.telephony.flags.enforce_telephony_feature_mapping_for_public_apis";
    public static final String FLAG_ENSURE_ACCESS_TO_CALL_SETTINGS_IS_RESTRICTED =
            "com.android.internal.telephony.flags.ensure_access_to_call_settings_is_restricted";
    public static final String FLAG_ESIM_AVAILABLE_MEMORY =
            "com.android.internal.telephony.flags.esim_available_memory";
    public static final String FLAG_ESIM_BOOTSTRAP_PROVISIONING_FLAG =
            "com.android.internal.telephony.flags.esim_bootstrap_provisioning_flag";
    public static final String FLAG_FIX_CRASH_ON_GETTING_CONFIG_WHEN_PHONE_IS_GONE =
            "com.android.internal.telephony.flags.fix_crash_on_getting_config_when_phone_is_gone";
    public static final String FLAG_FORCE_IWLAN_MMS =
            "com.android.internal.telephony.flags.force_iwlan_mms";
    public static final String FLAG_GEOFENCE_ENHANCEMENT_FOR_BETTER_UX =
            "com.android.internal.telephony.flags.geofence_enhancement_for_better_ux";
    public static final String FLAG_HIDE_PREFER_3G_ITEM =
            "com.android.internal.telephony.flags.hide_prefer_3g_item";
    public static final String FLAG_HIDE_PREINSTALLED_CARRIER_APP_AT_MOST_ONCE =
            "com.android.internal.telephony.flags.hide_preinstalled_carrier_app_at_most_once";
    public static final String FLAG_HIDE_ROAMING_ICON =
            "com.android.internal.telephony.flags.hide_roaming_icon";
    public static final String
            FLAG_IGNORE_ALREADY_TERMINATED_INCOMING_CALL_BEFORE_REGISTERING_LISTENER =
                    "com.android.internal.telephony.flags.ignore_already_terminated_incoming_call_before_registering_listener";
    public static final String FLAG_IGNORE_EXISTING_NETWORKS_FOR_INTERNET_ALLOWED_CHECKING =
            "com.android.internal.telephony.flags.ignore_existing_networks_for_internet_allowed_checking";
    public static final String FLAG_IMSI_KEY_RETRY_DOWNLOAD_ON_PHONE_UNLOCK =
            "com.android.internal.telephony.flags.imsi_key_retry_download_on_phone_unlock";
    public static final String FLAG_KEEP_EMPTY_REQUESTS_NETWORK =
            "com.android.internal.telephony.flags.keep_empty_requests_network";
    public static final String FLAG_LOG_MMS_SMS_DATABASE_ACCESS_INFO =
            "com.android.internal.telephony.flags.log_mms_sms_database_access_info";
    public static final String FLAG_METERED_EMBB_URLCC =
            "com.android.internal.telephony.flags.metered_embb_urlcc";
    public static final String FLAG_MINIMAL_TELEPHONY_CDM_CHECK =
            "com.android.internal.telephony.flags.minimal_telephony_cdm_check";
    public static final String FLAG_MINIMAL_TELEPHONY_MANAGERS_CONDITIONAL_ON_FEATURES =
            "com.android.internal.telephony.flags.minimal_telephony_managers_conditional_on_features";
    public static final String FLAG_MMS_DISABLED_ERROR =
            "com.android.internal.telephony.flags.mms_disabled_error";
    public static final String FLAG_MMS_GET_APN_FROM_PDSC =
            "com.android.internal.telephony.flags.mms_get_apn_from_pdsc";
    public static final String FLAG_NETWORK_REGISTRATION_INFO_REJECT_CAUSE =
            "com.android.internal.telephony.flags.network_registration_info_reject_cause";
    public static final String FLAG_NETWORK_VALIDATION =
            "com.android.internal.telephony.flags.network_validation";
    public static final String FLAG_NOTIFY_DATA_ACTIVITY_CHANGED_WITH_SLOT =
            "com.android.internal.telephony.flags.notify_data_activity_changed_with_slot";
    public static final String FLAG_NOTIFY_INITIAL_IMS_PROVISIONING_STATUS =
            "com.android.internal.telephony.flags.notify_initial_ims_provisioning_status";
    public static final String FLAG_OEM_ENABLED_SATELLITE_FLAG =
            "com.android.internal.telephony.flags.oem_enabled_satellite_flag";
    public static final String
            FLAG_PREVENT_INVOCATION_REPEAT_OF_RIL_CALL_WHEN_DEVICE_DOES_NOT_SUPPORT_VOICE =
                    "com.android.internal.telephony.flags.prevent_invocation_repeat_of_ril_call_when_device_does_not_support_voice";
    public static final String FLAG_PREVENT_SYSTEM_SERVER_AND_PHONE_DEADLOCK =
            "com.android.internal.telephony.flags.prevent_system_server_and_phone_deadlock";
    public static final String FLAG_RECONNECT_QUALIFIED_NETWORK =
            "com.android.internal.telephony.flags.reconnect_qualified_network";
    public static final String FLAG_REFINE_PREFERRED_DATA_PROFILE_SELECTION =
            "com.android.internal.telephony.flags.refine_preferred_data_profile_selection";
    public static final String FLAG_RELAX_HO_TEARDOWN =
            "com.android.internal.telephony.flags.relax_ho_teardown";
    public static final String FLAG_REMOVE_COUNTRY_CODE_FROM_LOCAL_SINGAPORE_CALLS =
            "com.android.internal.telephony.flags.remove_country_code_from_local_singapore_calls";
    public static final String FLAG_REORGANIZE_ROAMING_NOTIFICATION =
            "com.android.internal.telephony.flags.reorganize_roaming_notification";
    public static final String FLAG_RESET_MOBILE_NETWORK_SETTINGS =
            "com.android.internal.telephony.flags.reset_mobile_network_settings";
    public static final String FLAG_RESET_PRIMARY_SIM_DEFAULT_VALUES =
            "com.android.internal.telephony.flags.reset_primary_sim_default_values";
    public static final String FLAG_ROAMING_NOTIFICATION_FOR_SINGLE_DATA_NETWORK =
            "com.android.internal.telephony.flags.roaming_notification_for_single_data_network";
    public static final String FLAG_SAFER_GET_PHONE_NUMBER =
            "com.android.internal.telephony.flags.safer_get_phone_number";
    public static final String FLAG_SATELLITE_INTERNET =
            "com.android.internal.telephony.flags.satellite_internet";
    public static final String FLAG_SATELLITE_PERSISTENT_LOGGING =
            "com.android.internal.telephony.flags.satellite_persistent_logging";
    public static final String FLAG_SET_CARRIER_RESTRICTION_STATUS =
            "com.android.internal.telephony.flags.set_carrier_restriction_status";
    public static final String FLAG_SET_NO_REPLY_TIMER_FOR_CFNRY =
            "com.android.internal.telephony.flags.set_no_reply_timer_for_cfnry";
    public static final String FLAG_SET_NUMBER_OF_SIM_FOR_IMS_ENABLE =
            "com.android.internal.telephony.flags.set_number_of_sim_for_ims_enable";
    public static final String FLAG_SHOW_CALL_FAIL_NOTIFICATION_FOR_2G_TOGGLE =
            "com.android.internal.telephony.flags.show_call_fail_notification_for_2g_toggle";
    public static final String FLAG_SHOW_CALL_ID_AND_CALL_WAITING_IN_ADDITIONAL_SETTINGS_MENU =
            "com.android.internal.telephony.flags.show_call_id_and_call_waiting_in_additional_settings_menu";
    public static final String FLAG_SIMULTANEOUS_CALLING_INDICATIONS =
            "com.android.internal.telephony.flags.simultaneous_calling_indications";
    public static final String FLAG_SLICING_ADDITIONAL_ERROR_CODES =
            "com.android.internal.telephony.flags.slicing_additional_error_codes";
    public static final String FLAG_SMS_DOMAIN_SELECTION_ENABLED =
            "com.android.internal.telephony.flags.sms_domain_selection_enabled";
    public static final String FLAG_STOP_SPAMMING_EMERGENCY_NOTIFICATION =
            "com.android.internal.telephony.flags.stop_spamming_emergency_notification";
    public static final String FLAG_SUBSCRIPTION_USER_ASSOCIATION_QUERY =
            "com.android.internal.telephony.flags.subscription_user_association_query";
    public static final String FLAG_SUPPORT_NR_SA_RRC_IDLE =
            "com.android.internal.telephony.flags.support_nr_sa_rrc_idle";
    public static final String FLAG_SUPPORT_PSIM_TO_ESIM_CONVERSION =
            "com.android.internal.telephony.flags.support_psim_to_esim_conversion";
    public static final String
            FLAG_TERMINATE_ACTIVE_VIDEO_CALL_WHEN_ACCEPTING_SECOND_VIDEO_CALL_AS_AUDIO_ONLY =
                    "com.android.internal.telephony.flags.terminate_active_video_call_when_accepting_second_video_call_as_audio_only";
    public static final String FLAG_UNREGISTER_SMS_BROADCAST_RECEIVER_FROM_CAT_SERVICE =
            "com.android.internal.telephony.flags.unregister_sms_broadcast_receiver_from_cat_service";
    public static final String FLAG_UNTHROTTLE_CHECK_TRANSPORT =
            "com.android.internal.telephony.flags.unthrottle_check_transport";
    public static final String FLAG_UPDATE_IMS_SERVICE_BY_GATHERING_PROVISIONING_CHANGES =
            "com.android.internal.telephony.flags.update_ims_service_by_gathering_provisioning_changes";
    public static final String FLAG_UPDATE_ROAMING_STATE_TO_SET_WFC_MODE =
            "com.android.internal.telephony.flags.update_roaming_state_to_set_wfc_mode";
    public static final String FLAG_USE_ALARM_CALLBACK =
            "com.android.internal.telephony.flags.use_alarm_callback";
    public static final String FLAG_USE_AOSP_DOMAIN_SELECTION_SERVICE =
            "com.android.internal.telephony.flags.use_aosp_domain_selection_service";
    public static final String FLAG_USE_OEM_DOMAIN_SELECTION_SERVICE =
            "com.android.internal.telephony.flags.use_oem_domain_selection_service";
    public static final String FLAG_USE_RELAXED_ID_MATCH =
            "com.android.internal.telephony.flags.use_relaxed_id_match";
    public static final String FLAG_VONR_ENABLED_METRIC =
            "com.android.internal.telephony.flags.vonr_enabled_metric";
    public static final String FLAG_WORK_PROFILE_API_SPLIT =
            "com.android.internal.telephony.flags.work_profile_api_split";

    public static boolean addAnomalyWhenNotifyConfigChangedWithInvalidPhone() {
        return FEATURE_FLAGS.addAnomalyWhenNotifyConfigChangedWithInvalidPhone();
    }

    public static boolean addRatRelatedSuggestedActionToImsRegistration() {
        return FEATURE_FLAGS.addRatRelatedSuggestedActionToImsRegistration();
    }

    public static boolean allowMmtelInNonVops() {
        return FEATURE_FLAGS.allowMmtelInNonVops();
    }

    public static boolean answerAudioOnlyWhenAnsweringViaMmiCode() {
        return FEATURE_FLAGS.answerAudioOnlyWhenAnsweringViaMmiCode();
    }

    public static boolean apDomainSelectionEnabled() {
        return FEATURE_FLAGS.apDomainSelectionEnabled();
    }

    public static boolean apnSettingFieldSupportFlag() {
        return FEATURE_FLAGS.apnSettingFieldSupportFlag();
    }

    public static boolean autoDataSwitchAllowRoaming() {
        return FEATURE_FLAGS.autoDataSwitchAllowRoaming();
    }

    public static boolean autoDataSwitchUsesDataEnabled() {
        return FEATURE_FLAGS.autoDataSwitchUsesDataEnabled();
    }

    public static boolean backupAndRestoreForEnable2g() {
        return FEATURE_FLAGS.backupAndRestoreForEnable2g();
    }

    public static boolean callExtraForNonHoldSupportedCarriers() {
        return FEATURE_FLAGS.callExtraForNonHoldSupportedCarriers();
    }

    public static boolean carrierEnabledSatelliteFlag() {
        return FEATURE_FLAGS.carrierEnabledSatelliteFlag();
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

    public static boolean changeMethodOfObtainingImsRegistrationRadioTech() {
        return FEATURE_FLAGS.changeMethodOfObtainingImsRegistrationRadioTech();
    }

    public static boolean cleanupOpenLogicalChannelRecordOnDispose() {
        return FEATURE_FLAGS.cleanupOpenLogicalChannelRecordOnDispose();
    }

    public static boolean clearCachedImsPhoneNumberWhenDeviceLostImsRegistration() {
        return FEATURE_FLAGS.clearCachedImsPhoneNumberWhenDeviceLostImsRegistration();
    }

    public static boolean combineRilDeathHandle() {
        return FEATURE_FLAGS.combineRilDeathHandle();
    }

    public static boolean conferenceHoldUnholdChangedToSendMessage() {
        return FEATURE_FLAGS.conferenceHoldUnholdChangedToSendMessage();
    }

    public static boolean dataCallSessionStatsCapturesCrossSimCalling() {
        return FEATURE_FLAGS.dataCallSessionStatsCapturesCrossSimCalling();
    }

    public static boolean dataOnlyCellularService() {
        return FEATURE_FLAGS.dataOnlyCellularService();
    }

    public static boolean dataOnlyServiceAllowEmergencyCallOnly() {
        return FEATURE_FLAGS.dataOnlyServiceAllowEmergencyCallOnly();
    }

    public static boolean dataRatMetricEnabled() {
        return FEATURE_FLAGS.dataRatMetricEnabled();
    }

    public static boolean dismissNetworkSelectionNotificationOnSimDisable() {
        return FEATURE_FLAGS.dismissNetworkSelectionNotificationOnSimDisable();
    }

    public static boolean doNotOverridePreciseLabel() {
        return FEATURE_FLAGS.doNotOverridePreciseLabel();
    }

    public static boolean domainSelectionMetricsEnabled() {
        return FEATURE_FLAGS.domainSelectionMetricsEnabled();
    }

    public static boolean dsrsDiagnosticsEnabled() {
        return FEATURE_FLAGS.dsrsDiagnosticsEnabled();
    }

    public static boolean emergencyRegistrationState() {
        return FEATURE_FLAGS.emergencyRegistrationState();
    }

    public static boolean enableAeadAlgorithms() {
        return FEATURE_FLAGS.enableAeadAlgorithms();
    }

    public static boolean enableCarrierConfigN1ControlAttempt2() {
        return FEATURE_FLAGS.enableCarrierConfigN1ControlAttempt2();
    }

    public static boolean enableIdentifierDisclosureTransparency() {
        return FEATURE_FLAGS.enableIdentifierDisclosureTransparency();
    }

    public static boolean enableIdentifierDisclosureTransparencyUnsolEvents() {
        return FEATURE_FLAGS.enableIdentifierDisclosureTransparencyUnsolEvents();
    }

    public static boolean enableModemCipherTransparency() {
        return FEATURE_FLAGS.enableModemCipherTransparency();
    }

    public static boolean enableModemCipherTransparencyUnsolEvents() {
        return FEATURE_FLAGS.enableModemCipherTransparencyUnsolEvents();
    }

    public static boolean enableMultipleSaProposals() {
        return FEATURE_FLAGS.enableMultipleSaProposals();
    }

    public static boolean enableSipSubscribeRetry() {
        return FEATURE_FLAGS.enableSipSubscribeRetry();
    }

    public static boolean enableTelephonyAnalytics() {
        return FEATURE_FLAGS.enableTelephonyAnalytics();
    }

    public static boolean enableWpsCheckApiFlag() {
        return FEATURE_FLAGS.enableWpsCheckApiFlag();
    }

    public static boolean enforceSubscriptionUserFilter() {
        return FEATURE_FLAGS.enforceSubscriptionUserFilter();
    }

    public static boolean enforceTelephonyFeatureMapping() {
        return FEATURE_FLAGS.enforceTelephonyFeatureMapping();
    }

    public static boolean enforceTelephonyFeatureMappingForPublicApis() {
        return FEATURE_FLAGS.enforceTelephonyFeatureMappingForPublicApis();
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

    public static boolean fixCrashOnGettingConfigWhenPhoneIsGone() {
        return FEATURE_FLAGS.fixCrashOnGettingConfigWhenPhoneIsGone();
    }

    public static boolean forceIwlanMms() {
        return FEATURE_FLAGS.forceIwlanMms();
    }

    public static boolean geofenceEnhancementForBetterUx() {
        return FEATURE_FLAGS.geofenceEnhancementForBetterUx();
    }

    public static boolean hidePrefer3gItem() {
        return FEATURE_FLAGS.hidePrefer3gItem();
    }

    public static boolean hidePreinstalledCarrierAppAtMostOnce() {
        return FEATURE_FLAGS.hidePreinstalledCarrierAppAtMostOnce();
    }

    public static boolean hideRoamingIcon() {
        return FEATURE_FLAGS.hideRoamingIcon();
    }

    public static boolean ignoreAlreadyTerminatedIncomingCallBeforeRegisteringListener() {
        return FEATURE_FLAGS.ignoreAlreadyTerminatedIncomingCallBeforeRegisteringListener();
    }

    public static boolean ignoreExistingNetworksForInternetAllowedChecking() {
        return FEATURE_FLAGS.ignoreExistingNetworksForInternetAllowedChecking();
    }

    public static boolean imsiKeyRetryDownloadOnPhoneUnlock() {
        return FEATURE_FLAGS.imsiKeyRetryDownloadOnPhoneUnlock();
    }

    public static boolean keepEmptyRequestsNetwork() {
        return FEATURE_FLAGS.keepEmptyRequestsNetwork();
    }

    public static boolean logMmsSmsDatabaseAccessInfo() {
        return FEATURE_FLAGS.logMmsSmsDatabaseAccessInfo();
    }

    public static boolean meteredEmbbUrlcc() {
        return FEATURE_FLAGS.meteredEmbbUrlcc();
    }

    public static boolean minimalTelephonyCdmCheck() {
        return FEATURE_FLAGS.minimalTelephonyCdmCheck();
    }

    public static boolean minimalTelephonyManagersConditionalOnFeatures() {
        return FEATURE_FLAGS.minimalTelephonyManagersConditionalOnFeatures();
    }

    public static boolean mmsDisabledError() {
        return FEATURE_FLAGS.mmsDisabledError();
    }

    public static boolean mmsGetApnFromPdsc() {
        return FEATURE_FLAGS.mmsGetApnFromPdsc();
    }

    public static boolean networkRegistrationInfoRejectCause() {
        return FEATURE_FLAGS.networkRegistrationInfoRejectCause();
    }

    public static boolean networkValidation() {
        return FEATURE_FLAGS.networkValidation();
    }

    public static boolean notifyDataActivityChangedWithSlot() {
        return FEATURE_FLAGS.notifyDataActivityChangedWithSlot();
    }

    public static boolean notifyInitialImsProvisioningStatus() {
        return FEATURE_FLAGS.notifyInitialImsProvisioningStatus();
    }

    public static boolean oemEnabledSatelliteFlag() {
        return FEATURE_FLAGS.oemEnabledSatelliteFlag();
    }

    public static boolean preventInvocationRepeatOfRilCallWhenDeviceDoesNotSupportVoice() {
        return FEATURE_FLAGS.preventInvocationRepeatOfRilCallWhenDeviceDoesNotSupportVoice();
    }

    public static boolean preventSystemServerAndPhoneDeadlock() {
        return FEATURE_FLAGS.preventSystemServerAndPhoneDeadlock();
    }

    public static boolean reconnectQualifiedNetwork() {
        return FEATURE_FLAGS.reconnectQualifiedNetwork();
    }

    public static boolean refinePreferredDataProfileSelection() {
        return FEATURE_FLAGS.refinePreferredDataProfileSelection();
    }

    public static boolean relaxHoTeardown() {
        return FEATURE_FLAGS.relaxHoTeardown();
    }

    public static boolean removeCountryCodeFromLocalSingaporeCalls() {
        return FEATURE_FLAGS.removeCountryCodeFromLocalSingaporeCalls();
    }

    public static boolean reorganizeRoamingNotification() {
        return FEATURE_FLAGS.reorganizeRoamingNotification();
    }

    public static boolean resetMobileNetworkSettings() {
        return FEATURE_FLAGS.resetMobileNetworkSettings();
    }

    public static boolean resetPrimarySimDefaultValues() {
        return FEATURE_FLAGS.resetPrimarySimDefaultValues();
    }

    public static boolean roamingNotificationForSingleDataNetwork() {
        return FEATURE_FLAGS.roamingNotificationForSingleDataNetwork();
    }

    public static boolean saferGetPhoneNumber() {
        return FEATURE_FLAGS.saferGetPhoneNumber();
    }

    public static boolean satelliteInternet() {
        return FEATURE_FLAGS.satelliteInternet();
    }

    public static boolean satellitePersistentLogging() {
        return FEATURE_FLAGS.satellitePersistentLogging();
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

    public static boolean showCallIdAndCallWaitingInAdditionalSettingsMenu() {
        return FEATURE_FLAGS.showCallIdAndCallWaitingInAdditionalSettingsMenu();
    }

    public static boolean simultaneousCallingIndications() {
        return FEATURE_FLAGS.simultaneousCallingIndications();
    }

    public static boolean slicingAdditionalErrorCodes() {
        return FEATURE_FLAGS.slicingAdditionalErrorCodes();
    }

    public static boolean smsDomainSelectionEnabled() {
        return FEATURE_FLAGS.smsDomainSelectionEnabled();
    }

    public static boolean stopSpammingEmergencyNotification() {
        return FEATURE_FLAGS.stopSpammingEmergencyNotification();
    }

    public static boolean subscriptionUserAssociationQuery() {
        return FEATURE_FLAGS.subscriptionUserAssociationQuery();
    }

    public static boolean supportNrSaRrcIdle() {
        return FEATURE_FLAGS.supportNrSaRrcIdle();
    }

    public static boolean supportPsimToEsimConversion() {
        return FEATURE_FLAGS.supportPsimToEsimConversion();
    }

    public static boolean terminateActiveVideoCallWhenAcceptingSecondVideoCallAsAudioOnly() {
        return FEATURE_FLAGS.terminateActiveVideoCallWhenAcceptingSecondVideoCallAsAudioOnly();
    }

    public static boolean unregisterSmsBroadcastReceiverFromCatService() {
        return FEATURE_FLAGS.unregisterSmsBroadcastReceiverFromCatService();
    }

    public static boolean unthrottleCheckTransport() {
        return FEATURE_FLAGS.unthrottleCheckTransport();
    }

    public static boolean updateImsServiceByGatheringProvisioningChanges() {
        return FEATURE_FLAGS.updateImsServiceByGatheringProvisioningChanges();
    }

    public static boolean updateRoamingStateToSetWfcMode() {
        return FEATURE_FLAGS.updateRoamingStateToSetWfcMode();
    }

    public static boolean useAlarmCallback() {
        return FEATURE_FLAGS.useAlarmCallback();
    }

    public static boolean useAospDomainSelectionService() {
        return FEATURE_FLAGS.useAospDomainSelectionService();
    }

    public static boolean useOemDomainSelectionService() {
        return FEATURE_FLAGS.useOemDomainSelectionService();
    }

    public static boolean useRelaxedIdMatch() {
        return FEATURE_FLAGS.useRelaxedIdMatch();
    }

    public static boolean vonrEnabledMetric() {
        return FEATURE_FLAGS.vonrEnabledMetric();
    }

    public static boolean workProfileApiSplit() {
        return FEATURE_FLAGS.workProfileApiSplit();
    }
}
