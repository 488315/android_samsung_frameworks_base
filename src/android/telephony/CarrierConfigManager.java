package android.telephony;

import android.annotation.SystemApi;
import android.app.job.JobInfo;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.SemSystemProperties;
import android.service.carrier.CarrierIdentifier;
import android.telecom.TelecomManager;
import android.telephony.data.ApnSetting;
import android.text.TextUtils;
import android.view.WindowManager;
import com.android.ims.internal.uce.common.CapInfo;
import com.android.internal.hidden_from_bootclasspath.android.location.flags.Flags;
import com.android.internal.telephony.DctConstants;
import com.android.internal.telephony.ICarrierConfigLoader;
import com.android.internal.telephony.TelephonyFeatures;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.android.media.SemExtendedFormat;
import com.samsung.android.transcode.constants.EncodeConstants;
import com.sec.android.allshare.iface.message.EventMsg;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* loaded from: classes4.dex */
public class CarrierConfigManager {
    public static final String ACTION_CARRIER_CONFIG_CHANGED = "android.telephony.action.CARRIER_CONFIG_CHANGED";
    public static final int CARRIER_NR_AVAILABILITY_NSA = 1;
    public static final int CARRIER_NR_AVAILABILITY_SA = 2;
    public static final int CARRIER_ROAMING_NTN_CONNECT_AUTOMATIC = 0;
    public static final int CARRIER_ROAMING_NTN_CONNECT_MANUAL = 1;
    public static final int CROSS_SIM_SPN_FORMAT_CARRIER_NAME_ONLY = 0;
    public static final int CROSS_SIM_SPN_FORMAT_CARRIER_NAME_WITH_BRANDING = 1;
    public static final int DATA_CYCLE_THRESHOLD_DISABLED = -2;
    public static final int DATA_CYCLE_USE_PLATFORM_DEFAULT = -1;
    public static final String ENABLE_EAP_METHOD_PREFIX_BOOL = "enable_eap_method_prefix_bool";
    public static final String EXTRA_REBROADCAST_ON_UNLOCK = "android.telephony.extra.REBROADCAST_ON_UNLOCK";
    public static final String EXTRA_SLOT_INDEX = "android.telephony.extra.SLOT_INDEX";
    public static final String EXTRA_SUBSCRIPTION_INDEX = "android.telephony.extra.SUBSCRIPTION_INDEX";

    @SystemApi
    public static final int GBA_DIGEST = 3;

    @SystemApi
    public static final int GBA_ME = 1;

    @SystemApi
    public static final int GBA_U = 2;
    public static final String IMSI_CARRIER_PUBLIC_KEY_EPDG_STRING = "imsi_carrier_public_key_epdg_string";
    public static final String IMSI_CARRIER_PUBLIC_KEY_WLAN_STRING = "imsi_carrier_public_key_wlan_string";
    public static final String IMSI_KEY_AVAILABILITY_INT = "imsi_key_availability_int";
    public static final String IMSI_KEY_DOWNLOAD_URL_STRING = "imsi_key_download_url_string";
    public static final String KEY_4G_ONLY_BOOL = "4g_only_bool";
    public static final String KEY_5G_ICON_CONFIGURATION_STRING = "5g_icon_configuration_string";
    public static final String KEY_5G_ICON_CONFIGURATION_STRING_2ND = "5g_icon_configuration_string_2nd";
    public static final String KEY_5G_ICON_DISPLAY_GRACE_PERIOD_STRING = "5g_icon_display_grace_period_string";
    public static final String KEY_5G_ICON_DISPLAY_GRACE_PERIOD_STRING_2ND = "5g_icon_display_grace_period_string_2nd";
    public static final String KEY_5G_ICON_DISPLAY_SECONDARY_GRACE_PERIOD_STRING = "5g_icon_display_secondary_grace_period_string";
    public static final String KEY_5G_ICON_DISPLAY_SECONDARY_GRACE_PERIOD_STRING_2ND = "5g_icon_display_secondary_grace_period_string_2nd";
    public static final String KEY_5G_NR_SSRSRP_THRESHOLDS_INT_ARRAY = "5g_nr_ssrsrp_thresholds_int_array";
    public static final String KEY_5G_NR_SSRSRQ_THRESHOLDS_INT_ARRAY = "5g_nr_ssrsrq_thresholds_int_array";
    public static final String KEY_5G_NR_SSSINR_THRESHOLDS_INT_ARRAY = "5g_nr_sssinr_thresholds_int_array";
    public static final String KEY_5G_WATCHDOG_TIME_MS_LONG = "5g_watchdog_time_ms_long";
    public static final String KEY_ADDITIONAL_CALL_SETTING_BOOL = "additional_call_setting_bool";
    public static final String KEY_ADDITIONAL_NR_ADVANCED_BANDS_INT_ARRAY = "additional_nr_advanced_bands_int_array";
    public static final String KEY_ADDITIONAL_SETTINGS_CALLER_ID_VISIBILITY_BOOL = "additional_settings_caller_id_visibility_bool";
    public static final String KEY_ADDITIONAL_SETTINGS_CALL_WAITING_VISIBILITY_BOOL = "additional_settings_call_waiting_visibility_bool";
    public static final String KEY_ALLOWED_INITIAL_ATTACH_APN_TYPES_STRING_ARRAY = "allowed_initial_attach_apn_types_string_array";
    public static final String KEY_ALLOW_ADDING_APNS_BOOL = "allow_adding_apns_bool";
    public static final String KEY_ALLOW_ADD_CALL_DURING_VIDEO_CALL_BOOL = "allow_add_call_during_video_call";
    public static final String KEY_ALLOW_EMERGENCY_NUMBERS_IN_CALL_LOG_BOOL = "allow_emergency_numbers_in_call_log_bool";
    public static final String KEY_ALLOW_EMERGENCY_VIDEO_CALLS_BOOL = "allow_emergency_video_calls_bool";

    @Deprecated
    public static final String KEY_ALLOW_ERI_BOOL = "allow_cdma_eri_bool";
    public static final String KEY_ALLOW_HOLD_CALL_DURING_EMERGENCY_BOOL = "allow_hold_call_during_emergency_bool";
    public static final String KEY_ALLOW_HOLD_IN_IMS_CALL_BOOL = "allow_hold_in_ims_call";
    public static final String KEY_ALLOW_HOLD_VIDEO_CALL_BOOL = "allow_hold_video_call_bool";
    public static final String KEY_ALLOW_LOCAL_DTMF_TONES_BOOL = "allow_local_dtmf_tones_bool";
    public static final String KEY_ALLOW_MERGE_WIFI_CALLS_WHEN_VOWIFI_OFF_BOOL = "allow_merge_wifi_calls_when_vowifi_off_bool";
    public static final String KEY_ALLOW_MERGING_RTT_CALLS_BOOL = "allow_merging_rtt_calls_bool";
    public static final String KEY_ALLOW_METERED_NETWORK_FOR_CERT_DOWNLOAD_BOOL = "allow_metered_network_for_cert_download_bool";
    public static final String KEY_ALLOW_NON_EMERGENCY_CALLS_IN_ECM_BOOL = "allow_non_emergency_calls_in_ecm_bool";
    public static final String KEY_ALLOW_USSD_REQUESTS_VIA_TELEPHONY_MANAGER_BOOL = "allow_ussd_requests_via_telephony_manager_bool";
    public static final String KEY_ALLOW_VIDEO_CALLING_FALLBACK_BOOL = "allow_video_calling_fallback_bool";
    public static final String KEY_ALWAYS_PLAY_REMOTE_HOLD_TONE_BOOL = "always_play_remote_hold_tone_bool";
    public static final String KEY_ALWAYS_SHOW_DATA_RAT_ICON_BOOL = "always_show_data_rat_icon_bool";

    @Deprecated
    public static final String KEY_ALWAYS_SHOW_EMERGENCY_ALERT_ONOFF_BOOL = "always_show_emergency_alert_onoff_bool";
    public static final String KEY_ALWAYS_SHOW_PRIMARY_SIGNAL_BAR_IN_OPPORTUNISTIC_NETWORK_BOOLEAN = "always_show_primary_signal_bar_in_opportunistic_network_boolean";
    public static final String KEY_APN_EXPAND_BOOL = "apn_expand_bool";
    public static final String KEY_APN_SETTINGS_DEFAULT_APN_TYPES_STRING_ARRAY = "apn_settings_default_apn_types_string_array";
    public static final String KEY_ASCII_7_BIT_SUPPORT_FOR_LONG_MESSAGE_BOOL = "ascii_7_bit_support_for_long_message_bool";
    public static final String KEY_AUTO_CANCEL_CS_REJECT_NOTIFICATION = "carrier_auto_cancel_cs_notification";
    public static final String KEY_AUTO_DATA_SWITCH_RAT_SIGNAL_SCORE_BUNDLE = "auto_data_switch_rat_signal_score_string_bundle";
    public static final String KEY_AUTO_RETRY_ENABLED_BOOL = "auto_retry_enabled_bool";
    public static final String KEY_AUTO_RETRY_FAILED_WIFI_EMERGENCY_CALL = "auto_retry_failed_wifi_emergency_call";
    public static final String KEY_BANDWIDTH_NR_NSA_USE_LTE_VALUE_FOR_UPLINK_BOOL = "bandwidth_nr_nsa_use_lte_value_for_uplink_bool";
    public static final String KEY_BANDWIDTH_STRING_ARRAY = "bandwidth_string_array";
    public static final String KEY_BOOSTED_LTE_EARFCNS_STRING_ARRAY = "boosted_lte_earfcns_string_array";
    public static final String KEY_BOOSTED_NRARFCNS_STRING_ARRAY = "boosted_nrarfcns_string_array";
    public static final String KEY_BROADCAST_EMERGENCY_CALL_STATE_CHANGES_BOOL = "broadcast_emergency_call_state_changes_bool";
    public static final String KEY_CALLER_ID_OVER_UT_WARNING_BOOL = "caller_id_over_ut_warning_bool";
    public static final String KEY_CALL_BARRING_DEFAULT_SERVICE_CLASS_INT = "call_barring_default_service_class_int";
    public static final String KEY_CALL_BARRING_OVER_UT_WARNING_BOOL = "call_barring_over_ut_warning_bool";
    public static final String KEY_CALL_BARRING_SUPPORTS_DEACTIVATE_ALL_BOOL = "call_barring_supports_deactivate_all_bool";
    public static final String KEY_CALL_BARRING_SUPPORTS_PASSWORD_CHANGE_BOOL = "call_barring_supports_password_change_bool";
    public static final String KEY_CALL_BARRING_VISIBILITY_BOOL = "call_barring_visibility_bool";
    public static final String KEY_CALL_COMPOSER_PICTURE_SERVER_URL_STRING = "call_composer_picture_server_url_string";
    public static final String KEY_CALL_FORWARDING_BLOCKS_WHILE_ROAMING_STRING_ARRAY = "call_forwarding_blocks_while_roaming_string_array";
    public static final String KEY_CALL_FORWARDING_MAP_NON_NUMBER_TO_VOICEMAIL_BOOL = "call_forwarding_map_non_number_to_voicemail_bool";
    public static final String KEY_CALL_FORWARDING_OVER_UT_WARNING_BOOL = "call_forwarding_over_ut_warning_bool";
    public static final String KEY_CALL_FORWARDING_VISIBILITY_BOOL = "call_forwarding_visibility_bool";
    public static final String KEY_CALL_FORWARDING_WHEN_BUSY_SUPPORTED_BOOL = "call_forwarding_when_busy_supported_bool";
    public static final String KEY_CALL_FORWARDING_WHEN_UNANSWERED_SUPPORTED_BOOL = "call_forwarding_when_unanswered_supported_bool";
    public static final String KEY_CALL_FORWARDING_WHEN_UNREACHABLE_SUPPORTED_BOOL = "call_forwarding_when_unreachable_supported_bool";
    public static final String KEY_CALL_REDIRECTION_SERVICE_COMPONENT_NAME_STRING = "call_redirection_service_component_name_string";
    public static final String KEY_CALL_WAITING_OVER_UT_WARNING_BOOL = "call_waiting_over_ut_warning_bool";
    public static final String KEY_CALL_WAITING_SERVICE_CLASS_INT = "call_waiting_service_class_int";
    public static final String KEY_CAPABILITIES_EXEMPT_FROM_SINGLE_DC_CHECK_INT_ARRAY = "capabilities_exempt_from_single_dc_check_int_array";
    public static final String KEY_CARRIER_ALLOW_DEFLECT_IMS_CALL_BOOL = "carrier_allow_deflect_ims_call_bool";
    public static final String KEY_CARRIER_ALLOW_TRANSFER_IMS_CALL_BOOL = "carrier_allow_transfer_ims_call_bool";
    public static final String KEY_CARRIER_ALLOW_TURNOFF_IMS_BOOL = "carrier_allow_turnoff_ims_bool";
    public static final String KEY_CARRIER_APP_NO_WAKE_SIGNAL_CONFIG_STRING_ARRAY = "carrier_app_no_wake_signal_config";
    public static final String KEY_CARRIER_APP_REQUIRED_DURING_SIM_SETUP_BOOL = "carrier_app_required_during_setup_bool";
    public static final String KEY_CARRIER_APP_WAKE_SIGNAL_CONFIG_STRING_ARRAY = "carrier_app_wake_signal_config";
    public static final String KEY_CARRIER_CALL_SCREENING_APP_STRING = "call_screening_app";
    public static final String KEY_CARRIER_CERTIFICATE_STRING_ARRAY = "carrier_certificate_string_array";
    public static final String KEY_CARRIER_CONFIG_APPLIED_BOOL = "carrier_config_applied_bool";
    public static final String KEY_CARRIER_CONFIG_VERSION_STRING = "carrier_config_version_string";
    public static final String KEY_CARRIER_CROSS_SIM_IMS_AVAILABLE_BOOL = "carrier_cross_sim_ims_available_bool";
    public static final String KEY_CARRIER_DATA_CALL_APN_RETRY_AFTER_DISCONNECT_LONG = "carrier_data_call_apn_retry_after_disconnect_long";

    @Deprecated
    public static final String KEY_CARRIER_DATA_CALL_PERMANENT_FAILURE_STRINGS = "carrier_data_call_permanent_failure_strings";
    public static final String KEY_CARRIER_DATA_SERVICE_WLAN_CLASS_OVERRIDE_STRING = "carrier_data_service_wlan_class_override_string";
    public static final String KEY_CARRIER_DATA_SERVICE_WLAN_PACKAGE_OVERRIDE_STRING = "carrier_data_service_wlan_package_override_string";
    public static final String KEY_CARRIER_DATA_SERVICE_WWAN_CLASS_OVERRIDE_STRING = "carrier_data_service_wwan_class_override_string";
    public static final String KEY_CARRIER_DATA_SERVICE_WWAN_PACKAGE_OVERRIDE_STRING = "carrier_data_service_wwan_package_override_string";
    public static final String KEY_CARRIER_DEFAULT_ACTIONS_ON_DCFAILURE_STRING_ARRAY = "carrier_default_actions_on_dcfailure_string_array";
    public static final String KEY_CARRIER_DEFAULT_ACTIONS_ON_DEFAULT_NETWORK_AVAILABLE = "carrier_default_actions_on_default_network_available_string_array";
    public static final String KEY_CARRIER_DEFAULT_ACTIONS_ON_REDIRECTION_STRING_ARRAY = "carrier_default_actions_on_redirection_string_array";
    public static final String KEY_CARRIER_DEFAULT_ACTIONS_ON_RESET = "carrier_default_actions_on_reset_string_array";
    public static final String KEY_CARRIER_DEFAULT_DATA_ROAMING_ENABLED_BOOL = "carrier_default_data_roaming_enabled_bool";
    public static final String KEY_CARRIER_DEFAULT_INTERNATIONAL_DATA_ROAMING_ENABLED_BOOL = "carrier_default_international_data_roaming_enabled_bool";
    public static final String KEY_CARRIER_DEFAULT_REDIRECTION_URL_STRING_ARRAY = "carrier_default_redirection_url_string_array";
    public static final String KEY_CARRIER_DEFAULT_WFC_IMS_ENABLED_BOOL = "carrier_default_wfc_ims_enabled_bool";
    public static final String KEY_CARRIER_DEFAULT_WFC_IMS_MODE_INT = "carrier_default_wfc_ims_mode_int";
    public static final String KEY_CARRIER_DEFAULT_WFC_IMS_ROAMING_ENABLED_BOOL = "carrier_default_wfc_ims_roaming_enabled_bool";
    public static final String KEY_CARRIER_DEFAULT_WFC_IMS_ROAMING_MODE_INT = "carrier_default_wfc_ims_roaming_mode_int";

    @Deprecated
    public static final String KEY_CARRIER_ERI_FILE_NAME_STRING = "carrier_eri_file_name_string";

    @Deprecated
    public static final String KEY_CARRIER_FORCE_DISABLE_ETWS_CMAS_TEST_BOOL = "carrier_force_disable_etws_cmas_test_bool";
    public static final String KEY_CARRIER_IMS_GBA_REQUIRED_BOOL = "carrier_ims_gba_required_bool";
    public static final String KEY_CARRIER_INSTANT_LETTERING_AVAILABLE_BOOL = "carrier_instant_lettering_available_bool";
    public static final String KEY_CARRIER_INSTANT_LETTERING_ENCODING_STRING = "carrier_instant_lettering_encoding_string";
    public static final String KEY_CARRIER_INSTANT_LETTERING_ESCAPED_CHARS_STRING = "carrier_instant_lettering_escaped_chars_string";
    public static final String KEY_CARRIER_INSTANT_LETTERING_INVALID_CHARS_STRING = "carrier_instant_lettering_invalid_chars_string";
    public static final String KEY_CARRIER_INSTANT_LETTERING_LENGTH_LIMIT_INT = "carrier_instant_lettering_length_limit_int";
    public static final String KEY_CARRIER_METERED_APN_TYPES_STRINGS = "carrier_metered_apn_types_strings";
    public static final String KEY_CARRIER_METERED_ROAMING_APN_TYPES_STRINGS = "carrier_metered_roaming_apn_types_strings";
    public static final String KEY_CARRIER_NAME_OVERRIDE_BOOL = "carrier_name_override_bool";
    public static final String KEY_CARRIER_NAME_STRING = "carrier_name_string";
    public static final String KEY_CARRIER_NETWORK_SERVICE_WLAN_CLASS_OVERRIDE_STRING = "carrier_network_service_wlan_class_override_string";
    public static final String KEY_CARRIER_NETWORK_SERVICE_WLAN_PACKAGE_OVERRIDE_STRING = "carrier_network_service_wlan_package_override_string";
    public static final String KEY_CARRIER_NETWORK_SERVICE_WWAN_CLASS_OVERRIDE_STRING = "carrier_network_service_wwan_class_override_string";
    public static final String KEY_CARRIER_NETWORK_SERVICE_WWAN_PACKAGE_OVERRIDE_STRING = "carrier_network_service_wwan_package_override_string";
    public static final String KEY_CARRIER_NR_AVAILABILITIES_INT_ARRAY = "carrier_nr_availabilities_int_array";
    public static final String KEY_CARRIER_PROMOTE_WFC_ON_CALL_FAIL_BOOL = "carrier_promote_wfc_on_call_fail_bool";
    public static final String KEY_CARRIER_PROVISIONING_APP_STRING = "carrier_provisioning_app_string";
    public static final String KEY_CARRIER_PROVISIONS_WIFI_MERGED_NETWORKS_BOOL = "carrier_provisions_wifi_merged_networks_bool";
    public static final String KEY_CARRIER_QUALIFIED_NETWORKS_SERVICE_CLASS_OVERRIDE_STRING = "carrier_qualified_networks_service_class_override_string";
    public static final String KEY_CARRIER_QUALIFIED_NETWORKS_SERVICE_PACKAGE_OVERRIDE_STRING = "carrier_qualified_networks_service_package_override_string";
    public static final String KEY_CARRIER_RCS_PROVISIONING_REQUIRED_BOOL = "carrier_rcs_provisioning_required_bool";
    public static final String KEY_CARRIER_ROAMING_NTN_CONNECT_TYPE_INT = "carrier_roaming_ntn_connect_type_int";
    public static final String KEY_CARRIER_ROAMING_NTN_EMERGENCY_CALL_TO_SATELLITE_HANDOVER_TYPE_INT = "carrier_roaming_ntn_emergency_call_to_satellite_handover_type_int";
    public static final String KEY_CARRIER_ROAMING_SATELLITE_DEFAULT_SERVICES_INT_ARRAY = "carrier_roaming_satellite_default_services_int_array";
    public static final String KEY_CARRIER_SERVICE_NAME_STRING_ARRAY = "carrier_service_name_array";
    public static final String KEY_CARRIER_SERVICE_NUMBER_STRING_ARRAY = "carrier_service_number_array";
    public static final String KEY_CARRIER_SETTINGS_ACTIVITY_COMPONENT_NAME_STRING = "carrier_settings_activity_component_name_string";
    public static final String KEY_CARRIER_SETTINGS_ENABLE_BOOL = "carrier_settings_enable_bool";

    @SystemApi
    public static final String KEY_CARRIER_SETUP_APP_STRING = "carrier_setup_app_string";
    public static final String KEY_CARRIER_SUPPORTED_SATELLITE_NOTIFICATION_HYSTERESIS_SEC_INT = "carrier_supported_satellite_notification_hysteresis_sec_int";
    public static final String KEY_CARRIER_SUPPORTED_SATELLITE_SERVICES_PER_PROVIDER_BUNDLE = "carrier_supported_satellite_services_per_provider_bundle";
    public static final String KEY_CARRIER_SUPPORTS_CALLER_ID_VERTICAL_SERVICE_CODES_BOOL = "carrier_supports_caller_id_vertical_service_codes_bool";
    public static final String KEY_CARRIER_SUPPORTS_OPP_DATA_AUTO_PROVISIONING_BOOL = "carrier_supports_opp_data_auto_provisioning_bool";
    public static final String KEY_CARRIER_SUPPORTS_SS_OVER_UT_BOOL = "carrier_supports_ss_over_ut_bool";
    public static final String KEY_CARRIER_SUPPORTS_TETHERING_BOOL = "carrier_supports_tethering_bool";
    public static final String KEY_CARRIER_USE_IMS_FIRST_FOR_EMERGENCY_BOOL = "carrier_use_ims_first_for_emergency_bool";
    public static final String KEY_CARRIER_USSD_METHOD_INT = "carrier_ussd_method_int";

    @Deprecated
    public static final String KEY_CARRIER_UT_PROVISIONING_REQUIRED_BOOL = "carrier_ut_provisioning_required_bool";
    public static final String KEY_CARRIER_VOLTE_AVAILABLE_BOOL = "carrier_volte_available_bool";
    public static final String KEY_CARRIER_VOLTE_OVERRIDE_WFC_PROVISIONING_BOOL = "carrier_volte_override_wfc_provisioning_bool";

    @Deprecated
    public static final String KEY_CARRIER_VOLTE_PROVISIONED_BOOL = "carrier_volte_provisioned_bool";

    @Deprecated
    public static final String KEY_CARRIER_VOLTE_PROVISIONING_REQUIRED_BOOL = "carrier_volte_provisioning_required_bool";
    public static final String KEY_CARRIER_VOLTE_TTY_SUPPORTED_BOOL = "carrier_volte_tty_supported_bool";
    public static final String KEY_CARRIER_VOWIFI_TTY_SUPPORTED_BOOL = "carrier_vowifi_tty_supported_bool";
    public static final String KEY_CARRIER_VT_AVAILABLE_BOOL = "carrier_vt_available_bool";

    @Deprecated
    public static final String KEY_CARRIER_VVM_PACKAGE_NAME_STRING = "carrier_vvm_package_name_string";
    public static final String KEY_CARRIER_VVM_PACKAGE_NAME_STRING_ARRAY = "carrier_vvm_package_name_string_array";
    public static final String KEY_CARRIER_WFC_IMS_AVAILABLE_BOOL = "carrier_wfc_ims_available_bool";
    public static final String KEY_CARRIER_WFC_SUPPORTS_WIFI_ONLY_BOOL = "carrier_wfc_supports_wifi_only_bool";

    @Deprecated
    public static final String KEY_CDMA_3WAYCALL_FLASH_DELAY_INT = "cdma_3waycall_flash_delay_int";

    @Deprecated
    public static final String KEY_CDMA_DTMF_TONE_DELAY_INT = "cdma_dtmf_tone_delay_int";

    @Deprecated
    public static final String KEY_CDMA_ENHANCED_ROAMING_INDICATOR_FOR_HOME_NETWORK_INT_ARRAY = "cdma_enhanced_roaming_indicator_for_home_network_int_array";

    @Deprecated
    public static final String KEY_CDMA_HOME_REGISTERED_PLMN_NAME_OVERRIDE_BOOL = "cdma_home_registered_plmn_name_override_bool";

    @Deprecated
    public static final String KEY_CDMA_HOME_REGISTERED_PLMN_NAME_STRING = "cdma_home_registered_plmn_name_string";

    @Deprecated
    public static final String KEY_CDMA_NONROAMING_NETWORKS_STRING_ARRAY = "cdma_nonroaming_networks_string_array";

    @Deprecated
    public static final String KEY_CDMA_ROAMING_MODE_INT = "cdma_roaming_mode_int";

    @Deprecated
    public static final String KEY_CDMA_ROAMING_NETWORKS_STRING_ARRAY = "cdma_roaming_networks_string_array";
    public static final String KEY_CELLULAR_SERVICE_CAPABILITIES_INT_ARRAY = "cellular_service_capabilities_int_array";
    public static final String KEY_CELLULAR_USAGE_SETTING_INT = "cellular_usage_setting_int";
    public static final String KEY_CHECK_PRICING_WITH_CARRIER_FOR_DATA_ROAMING_BOOL = "check_pricing_with_carrier_data_roaming_bool";
    public static final String KEY_CI_ACTION_ON_SYS_UPDATE_BOOL = "ci_action_on_sys_update_bool";
    public static final String KEY_CI_ACTION_ON_SYS_UPDATE_EXTRA_STRING = "ci_action_on_sys_update_extra_string";
    public static final String KEY_CI_ACTION_ON_SYS_UPDATE_EXTRA_VAL_STRING = "ci_action_on_sys_update_extra_val_string";
    public static final String KEY_CI_ACTION_ON_SYS_UPDATE_INTENT_STRING = "ci_action_on_sys_update_intent_string";
    public static final String KEY_CONFIG_IMS_MMTEL_PACKAGE_OVERRIDE_STRING = "config_ims_mmtel_package_override_string";

    @Deprecated
    public static final String KEY_CONFIG_IMS_PACKAGE_OVERRIDE_STRING = "config_ims_package_override_string";
    public static final String KEY_CONFIG_IMS_RCS_PACKAGE_OVERRIDE_STRING = "config_ims_rcs_package_override_string";
    public static final String KEY_CONFIG_PLANS_PACKAGE_OVERRIDE_STRING = "config_plans_package_override_string";

    @Deprecated
    public static final String KEY_CONFIG_SHOW_ORIG_DIAL_STRING_FOR_CDMA_BOOL = "config_show_orig_dial_string_for_cdma";
    public static final String KEY_CONFIG_TELEPHONY_USE_OWN_NUMBER_FOR_VOICEMAIL_BOOL = "config_telephony_use_own_number_for_voicemail_bool";
    public static final String KEY_CONFIG_WIFI_DISABLE_IN_ECBM = "config_wifi_disable_in_ecbm";

    @Deprecated
    public static final String KEY_CONVERT_CDMA_CALLER_ID_MMI_CODES_WHILE_ROAMING_ON_3GPP_BOOL = "convert_cdma_caller_id_mmi_codes_while_roaming_on_3gpp_bool";
    public static final String KEY_CROSS_SIM_SPN_FORMAT_INT = "cross_sim_spn_format_int";
    public static final String KEY_CSP_ENABLED_BOOL = "csp_enabled_bool";
    public static final String KEY_DATA_CONNECTED_ROAMING_NOTIFICATION_EXCLUDED_MCCS_STRING_ARRAY = "data_connected_roaming_notification_excluded_mccs_string_array";
    public static final String KEY_DATA_CONNECTED_ROAMING_NOTIFICATION_INCLUDED_MCC_MNCS_STRING_ARRAY = "data_connected_roaming_notification_included_mcc_mncs_string_array";
    public static final String KEY_DATA_LIMIT_NOTIFICATION_BOOL = "data_limit_notification_bool";
    public static final String KEY_DATA_LIMIT_THRESHOLD_BYTES_LONG = "data_limit_threshold_bytes_long";
    public static final String KEY_DATA_RAPID_NOTIFICATION_BOOL = "data_rapid_notification_bool";
    public static final String KEY_DATA_STALL_RECOVERY_SHOULD_SKIP_BOOL_ARRAY = "data_stall_recovery_should_skip_bool_array";
    public static final String KEY_DATA_STALL_RECOVERY_TIMERS_LONG_ARRAY = "data_stall_recovery_timers_long_array";

    @Deprecated
    public static final String KEY_DATA_SWITCH_VALIDATION_MIN_GAP_LONG = "data_switch_validation_min_gap_long";
    public static final String KEY_DATA_SWITCH_VALIDATION_MIN_INTERVAL_MILLIS_LONG = "data_switch_validation_min_gap_long";
    public static final String KEY_DATA_SWITCH_VALIDATION_TIMEOUT_LONG = "data_switch_validation_timeout_long";
    public static final String KEY_DATA_WARNING_NOTIFICATION_BOOL = "data_warning_notification_bool";
    public static final String KEY_DATA_WARNING_THRESHOLD_BYTES_LONG = "data_warning_threshold_bytes_long";
    public static final String KEY_DEFAULT_MTU_INT = "default_mtu_int";
    public static final String KEY_DEFAULT_PREFERRED_APN_NAME_STRING = "default_preferred_apn_name_string";
    public static final String KEY_DEFAULT_RTT_MODE_INT = "default_rtt_mode_int";
    public static final String KEY_DEFAULT_SIM_CALL_MANAGER_STRING = "default_sim_call_manager_string";
    public static final String KEY_DEFAULT_VM_NUMBER_ROAMING_AND_IMS_UNREGISTERED_STRING = "default_vm_number_roaming_and_ims_unregistered_string";
    public static final String KEY_DEFAULT_VM_NUMBER_ROAMING_STRING = "default_vm_number_roaming_string";
    public static final String KEY_DEFAULT_VM_NUMBER_STRING = "default_vm_number_string";
    public static final String KEY_DELAY_IMS_TEAR_DOWN_UNTIL_CALL_END_BOOL = "delay_ims_tear_down_until_call_end_bool";
    public static final String KEY_DIAL_STRING_REPLACE_STRING_ARRAY = "dial_string_replace_string_array";

    @Deprecated
    public static final String KEY_DISABLE_CDMA_ACTIVATION_CODE_BOOL = "disable_cdma_activation_code_bool";
    public static final String KEY_DISABLE_CHARGE_INDICATION_BOOL = "disable_charge_indication_bool";
    public static final String KEY_DISABLE_DUN_APN_WHILE_ROAMING_WITH_PRESET_APN_BOOL = "disable_dun_apn_while_roaming_with_preset_apn_bool";
    public static final String KEY_DISABLE_SUPPLEMENTARY_SERVICES_IN_AIRPLANE_MODE_BOOL = "disable_supplementary_services_in_airplane_mode_bool";
    public static final String KEY_DISABLE_VOICE_BARRING_NOTIFICATION_BOOL = "disable_voice_barring_notification_bool";
    public static final String KEY_DISCONNECT_CAUSE_PLAY_BUSYTONE_INT_ARRAY = "disconnect_cause_play_busytone_int_array";
    public static final String KEY_DISPLAY_CALL_STRENGTH_INDICATOR_BOOL = "display_call_strength_indicator_bool";
    public static final String KEY_DISPLAY_HD_AUDIO_PROPERTY_BOOL = "display_hd_audio_property_bool";
    public static final String KEY_DISPLAY_NO_DATA_NOTIFICATION_ON_PERMANENT_FAILURE_BOOL = "display_no_data_notification_on_permanent_failure_bool";
    public static final String KEY_DISPLAY_VOICEMAIL_NUMBER_AS_DEFAULT_CALL_FORWARDING_NUMBER_BOOL = "display_voicemail_number_as_default_call_forwarding_number";
    public static final String KEY_DROP_VIDEO_CALL_WHEN_ANSWERING_AUDIO_CALL_BOOL = "drop_video_call_when_answering_audio_call_bool";
    public static final String KEY_DTMF_TYPE_ENABLED_BOOL = "dtmf_type_enabled_bool";
    public static final String KEY_DURATION_BLOCKING_DISABLED_AFTER_EMERGENCY_INT = "duration_blocking_disabled_after_emergency_int";
    public static final String KEY_EDITABLE_ENHANCED_4G_LTE_BOOL = "editable_enhanced_4g_lte_bool";
    public static final String KEY_EDITABLE_VOICEMAIL_NUMBER_BOOL = "editable_voicemail_number_bool";
    public static final String KEY_EDITABLE_VOICEMAIL_NUMBER_SETTING_BOOL = "editable_voicemail_number_setting_bool";
    public static final String KEY_EDITABLE_WFC_MODE_BOOL = "editable_wfc_mode_bool";
    public static final String KEY_EDITABLE_WFC_ROAMING_MODE_BOOL = "editable_wfc_roaming_mode_bool";
    public static final String KEY_EHPLMN_OVERRIDE_STRING_ARRAY = "ehplmn_override_string_array";
    public static final String KEY_EMERGENCY_CALL_TO_SATELLITE_T911_HANDOVER_TIMEOUT_MILLIS_INT = "emergency_call_to_satellite_t911_handover_timeout_millis_int";
    public static final String KEY_EMERGENCY_MESSAGING_SUPPORTED_BOOL = "emergency_messaging_supported_bool";
    public static final String KEY_EMERGENCY_NOTIFICATION_DELAY_INT = "emergency_notification_delay_int";
    public static final String KEY_EMERGENCY_NUMBER_PREFIX_STRING_ARRAY = "emergency_number_prefix_string_array";
    public static final String KEY_EMERGENCY_SMS_MODE_TIMER_MS_INT = "emergency_sms_mode_timer_ms_int";
    public static final String KEY_ENABLE_4G_OPPORTUNISTIC_NETWORK_SCAN_BOOL = "enabled_4g_opportunistic_network_scan_bool";
    public static final String KEY_ENABLE_APPS_STRING_ARRAY = "enable_apps_string_array";
    public static final String KEY_ENABLE_CARRIER_DISPLAY_NAME_RESOLVER_BOOL = "enable_carrier_display_name_resolver_bool";
    public static final String KEY_ENABLE_CROSS_SIM_CALLING_ON_OPPORTUNISTIC_DATA_BOOL = "enable_cross_sim_calling_on_opportunistic_data_bool";
    public static final String KEY_ENABLE_DIALER_KEY_VIBRATION_BOOL = "enable_dialer_key_vibration_bool";
    public static final String KEY_ENABLE_NR_ADVANCED_WHILE_ROAMING_BOOL = "enable_nr_advanced_for_roaming_bool";
    public static final String KEY_ENABLE_OVERRIDE_NETWORK_TYPE_BY_CP_ICON_TYPE = "enable_override_network_type_by_cp_icon_type";
    public static final String KEY_ENHANCED_4G_LTE_ON_BY_DEFAULT_BOOL = "enhanced_4g_lte_on_by_default_bool";

    @Deprecated
    public static final String KEY_ENHANCED_4G_LTE_TITLE_VARIANT_BOOL = "enhanced_4g_lte_title_variant_bool";
    public static final String KEY_ENHANCED_4G_LTE_TITLE_VARIANT_INT = "enhanced_4g_lte_title_variant_int";
    public static final String KEY_ESIM_DOWNLOAD_RETRY_BACKOFF_TIMER_SEC_INT = "esim_download_retry_backoff_timer_sec_int";
    public static final String KEY_ESIM_MAX_DOWNLOAD_RETRY_ATTEMPTS_INT = "esim_max_download_retry_attempts_int";
    public static final String KEY_EUTRAN_RSRP_HYSTERESIS_DB_INT = "eutran_rsrp_hysteresis_db_int";
    public static final String KEY_EUTRAN_RSRQ_HYSTERESIS_DB_INT = "eutran_rsrq_hysteresis_db_int";
    public static final String KEY_EUTRAN_RSSNR_HYSTERESIS_DB_INT = "eutran_rssnr_hysteresis_db_int";
    public static final String KEY_FDN_NUMBER_LENGTH_LIMIT_INT = "fdn_number_length_limit_int";
    public static final String KEY_FEATURE_ACCESS_CODES_STRING_ARRAY = "feature_access_codes_string_array";
    public static final String KEY_FILTERED_CNAP_NAMES_STRING_ARRAY = "filtered_cnap_names_string_array";
    public static final String KEY_FORCE_HOME_NETWORK_BOOL = "force_home_network_bool";

    @Deprecated
    public static final String KEY_FORCE_IMEI_BOOL = "force_imei_bool";
    public static final String KEY_FORMAT_INCOMING_NUMBER_TO_NATIONAL_FOR_JP_BOOL = "format_incoming_number_to_national_for_jp_bool";

    @SystemApi
    public static final String KEY_GBA_MODE_INT = "gba_mode_int";

    @SystemApi
    public static final String KEY_GBA_UA_SECURITY_ORGANIZATION_INT = "gba_ua_security_organization_int";

    @SystemApi
    public static final String KEY_GBA_UA_SECURITY_PROTOCOL_INT = "gba_ua_security_protocol_int";

    @SystemApi
    public static final String KEY_GBA_UA_TLS_CIPHER_SUITE_INT = "gba_ua_tls_cipher_suite_int";
    public static final String KEY_GERAN_RSSI_HYSTERESIS_DB_INT = "geran_rssi_hysteresis_db_int";
    public static final String KEY_GET_PHONE_NUMBER_TS43_BOOL = "get_phone_number_ts43_bool";
    public static final String KEY_GSM_CDMA_CALLS_CAN_BE_HD_AUDIO = "gsm_cdma_calls_can_be_hd_audio";
    public static final String KEY_GSM_DTMF_TONE_DELAY_INT = "gsm_dtmf_tone_delay_int";
    public static final String KEY_GSM_NONROAMING_NETWORKS_STRING_ARRAY = "gsm_nonroaming_networks_string_array";
    public static final String KEY_GSM_ROAMING_NETWORKS_STRING_ARRAY = "gsm_roaming_networks_string_array";
    public static final String KEY_GSM_RSSI_THRESHOLDS_INT_ARRAY = "gsm_rssi_thresholds_int_array";
    public static final String KEY_HAS_IN_CALL_NOISE_SUPPRESSION_BOOL = "has_in_call_noise_suppression_bool";
    public static final String KEY_HIDE_CARRIER_NETWORK_SETTINGS_BOOL = "hide_carrier_network_settings_bool";
    public static final String KEY_HIDE_DIGITS_HELPER_TEXT_ON_STK_INPUT_SCREEN_BOOL = "hide_digits_helper_text_on_stk_input_screen_bool";

    @Deprecated
    public static final String KEY_HIDE_ENABLE_2G = "hide_enable_2g_bool";
    public static final String KEY_HIDE_ENHANCED_4G_LTE_BOOL = "hide_enhanced_4g_lte_bool";
    public static final String KEY_HIDE_IMS_APN_BOOL = "hide_ims_apn_bool";
    public static final String KEY_HIDE_LTE_PLUS_DATA_ICON_BOOL = "hide_lte_plus_data_icon_bool";
    public static final String KEY_HIDE_PREFERRED_NETWORK_TYPE_BOOL = "hide_preferred_network_type_bool";
    public static final String KEY_HIDE_PRESET_APN_DETAILS_BOOL = "hide_preset_apn_details_bool";
    public static final String KEY_HIDE_SIM_LOCK_SETTINGS_BOOL = "hide_sim_lock_settings_bool";
    public static final String KEY_HIDE_TTY_HCO_VCO_WITH_RTT_BOOL = "hide_tty_hco_vco_with_rtt";
    public static final String KEY_HIDE_VOICEMAIL_NUMBER_SETTING_BOOL = "hide_voicemail_number_setting_bool";
    public static final String KEY_HIGH_BANDWIDTH_DATA_TRANSFER_THRESHOLD_KBPS_INT = "high_bandwidth_data_transfer_threshold_kbps_int";
    public static final String KEY_IDENTIFY_HIGH_DEFINITION_CALLS_IN_CALL_LOG_BOOL = "identify_high_definition_calls_in_call_log_bool";
    public static final String KEY_IGNORE_DATA_ENABLED_CHANGED_FOR_VIDEO_CALLS = "ignore_data_enabled_changed_for_video_calls";
    public static final String KEY_IGNORE_RTT_MODE_SETTING_BOOL = "ignore_rtt_mode_setting_bool";
    public static final String KEY_IGNORE_SIM_NETWORK_LOCKED_EVENTS_BOOL = "ignore_sim_network_locked_events_bool";
    public static final String KEY_IMS_CONFERENCE_SIZE_LIMIT_INT = "ims_conference_size_limit_int";
    public static final String KEY_IMS_DTMF_TONE_DELAY_INT = "ims_dtmf_tone_delay_int";
    public static final String KEY_IMS_REASONINFO_MAPPING_STRING_ARRAY = "ims_reasoninfo_mapping_string_array";
    public static final String KEY_INCLUDE_LTE_FOR_NR_ADVANCED_THRESHOLD_BANDWIDTH_BOOL = "include_lte_for_nr_advanced_threshold_bandwidth_bool";
    public static final String KEY_INFLATE_SIGNAL_STRENGTH_BOOL = "inflate_signal_strength_bool";
    public static final String KEY_INTERNATIONAL_ROAMING_DIAL_STRING_REPLACE_STRING_ARRAY = "international_roaming_dial_string_replace_string_array";
    public static final String KEY_IS_IMS_CONFERENCE_SIZE_ENFORCED_BOOL = "is_ims_conference_size_enforced_bool";
    public static final String KEY_IS_OPPORTUNISTIC_SUBSCRIPTION_BOOL = "is_opportunistic_subscription_bool";
    public static final String KEY_IWLAN_HANDOVER_POLICY_STRING_ARRAY = "iwlan_handover_policy_string_array";
    public static final String KEY_LIMITED_SIM_FUNCTION_NOTIFICATION_FOR_DSDS_BOOL = "limited_sim_function_notification_for_dsds_bool";
    public static final String KEY_LOCAL_DISCONNECT_EMPTY_IMS_CONFERENCE_BOOL = "local_disconnect_empty_ims_conference_bool";
    public static final String KEY_LTE_EARFCNS_RSRP_BOOST_INT = "lte_earfcns_rsrp_boost_int";
    public static final String KEY_LTE_ENABLED_BOOL = "lte_enabled_bool";
    public static final String KEY_LTE_ENDC_USING_USER_DATA_FOR_RRC_DETECTION_BOOL = "lte_endc_using_user_data_for_rrc_detection_bool";
    public static final String KEY_LTE_PLUS_THRESHOLD_BANDWIDTH_KHZ_INT = "lte_plus_threshold_bandwidth_khz_int";
    public static final String KEY_LTE_RSRP_THRESHOLDS_INT_ARRAY = "lte_rsrp_thresholds_int_array";
    public static final String KEY_LTE_RSRQ_THRESHOLDS_INT_ARRAY = "lte_rsrq_thresholds_int_array";
    public static final String KEY_LTE_RSSNR_THRESHOLDS_INT_ARRAY = "lte_rssnr_thresholds_int_array";
    public static final String KEY_MDN_IS_ADDITIONAL_VOICEMAIL_NUMBER_BOOL = "mdn_is_additional_voicemail_number_bool";

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final String KEY_MIN_UDP_PORT_4500_NAT_TIMEOUT_SEC_INT = "min_udp_port_4500_nat_timeout_sec_int";
    public static final String KEY_MISSED_INCOMING_CALL_SMS_ORIGINATOR_STRING_ARRAY = "missed_incoming_call_sms_originator_string_array";
    public static final String KEY_MISSED_INCOMING_CALL_SMS_PATTERN_STRING_ARRAY = "missed_incoming_call_sms_pattern_string_array";
    public static final String KEY_MMI_TWO_DIGIT_NUMBER_PATTERN_STRING_ARRAY = "mmi_two_digit_number_pattern_string_array";
    public static final String KEY_MMS_ALIAS_ENABLED_BOOL = "aliasEnabled";
    public static final String KEY_MMS_ALIAS_MAX_CHARS_INT = "aliasMaxChars";
    public static final String KEY_MMS_ALIAS_MIN_CHARS_INT = "aliasMinChars";
    public static final String KEY_MMS_ALLOW_ATTACH_AUDIO_BOOL = "allowAttachAudio";
    public static final String KEY_MMS_APPEND_TRANSACTION_ID_BOOL = "enabledTransID";
    public static final String KEY_MMS_CLOSE_CONNECTION_BOOL = "mmsCloseConnection";
    public static final String KEY_MMS_EMAIL_GATEWAY_NUMBER_STRING = "emailGatewayNumber";
    public static final String KEY_MMS_GROUP_MMS_ENABLED_BOOL = "enableGroupMms";
    public static final String KEY_MMS_HTTP_PARAMS_STRING = "httpParams";
    public static final String KEY_MMS_HTTP_SOCKET_TIMEOUT_INT = "httpSocketTimeout";
    public static final String KEY_MMS_MAX_IMAGE_HEIGHT_INT = "maxImageHeight";
    public static final String KEY_MMS_MAX_IMAGE_WIDTH_INT = "maxImageWidth";
    public static final String KEY_MMS_MAX_MESSAGE_SIZE_INT = "maxMessageSize";
    public static final String KEY_MMS_MAX_NTN_PAYLOAD_SIZE_BYTES_INT = "mms_max_ntn_payload_size_bytes_int";
    public static final String KEY_MMS_MESSAGE_TEXT_MAX_SIZE_INT = "maxMessageTextSize";
    public static final String KEY_MMS_MMS_DELIVERY_REPORT_ENABLED_BOOL = "enableMMSDeliveryReports";
    public static final String KEY_MMS_MMS_ENABLED_BOOL = "enabledMMS";
    public static final String KEY_MMS_MMS_READ_REPORT_ENABLED_BOOL = "enableMMSReadReports";
    public static final String KEY_MMS_MULTIPART_SMS_ENABLED_BOOL = "enableMultipartSMS";
    public static final String KEY_MMS_NAI_SUFFIX_STRING = "naiSuffix";
    public static final String KEY_MMS_NETWORK_RELEASE_TIMEOUT_MILLIS_INT = "mms_network_release_timeout_millis_int";
    public static final String KEY_MMS_NOTIFY_WAP_MMSC_ENABLED_BOOL = "enabledNotifyWapMMSC";
    public static final String KEY_MMS_RECIPIENT_LIMIT_INT = "recipientLimit";
    public static final String KEY_MMS_SEND_MULTIPART_SMS_AS_SEPARATE_MESSAGES_BOOL = "sendMultipartSmsAsSeparateMessages";
    public static final String KEY_MMS_SHOW_CELL_BROADCAST_APP_LINKS_BOOL = "config_cellBroadcastAppLinks";
    public static final String KEY_MMS_SMS_DELIVERY_REPORT_ENABLED_BOOL = "enableSMSDeliveryReports";
    public static final String KEY_MMS_SMS_TO_MMS_TEXT_LENGTH_THRESHOLD_INT = "smsToMmsTextLengthThreshold";
    public static final String KEY_MMS_SMS_TO_MMS_TEXT_THRESHOLD_INT = "smsToMmsTextThreshold";
    public static final String KEY_MMS_SUBJECT_MAX_LENGTH_INT = "maxSubjectLength";
    public static final String KEY_MMS_SUPPORT_HTTP_CHARSET_HEADER_BOOL = "supportHttpCharsetHeader";
    public static final String KEY_MMS_SUPPORT_MMS_CONTENT_DISPOSITION_BOOL = "supportMmsContentDisposition";
    public static final String KEY_MMS_UA_PROF_TAG_NAME_STRING = "uaProfTagName";
    public static final String KEY_MMS_UA_PROF_URL_STRING = "uaProfUrl";
    public static final String KEY_MMS_USER_AGENT_STRING = "userAgent";
    public static final String KEY_MONTHLY_DATA_CYCLE_DAY_INT = "monthly_data_cycle_day_int";
    public static final String KEY_NETWORK_TEMP_NOT_METERED_SUPPORTED_BOOL = "network_temp_not_metered_supported_bool";
    public static final String KEY_NGRAN_SSRSRP_HYSTERESIS_DB_INT = "ngran_ssrsrp_hysteresis_db_int";
    public static final String KEY_NGRAN_SSRSRQ_HYSTERESIS_DB_INT = "ngran_ssrsrq_hysteresis_db_int";
    public static final String KEY_NGRAN_SSSINR_HYSTERESIS_DB_INT = "ngran_sssinr_hysteresis_db_int";
    public static final String KEY_NON_ROAMING_OPERATOR_STRING_ARRAY = "non_roaming_operator_string_array";
    public static final String KEY_NOTIFY_HANDOVER_VIDEO_FROM_LTE_TO_WIFI_BOOL = "notify_handover_video_from_lte_to_wifi_bool";
    public static final String KEY_NOTIFY_HANDOVER_VIDEO_FROM_WIFI_TO_LTE_BOOL = "notify_handover_video_from_wifi_to_lte_bool";
    public static final String KEY_NOTIFY_INTERNATIONAL_CALL_ON_WFC_BOOL = "notify_international_call_on_wfc_bool";
    public static final String KEY_NOTIFY_VT_HANDOVER_TO_WIFI_FAILURE_BOOL = "notify_vt_handover_to_wifi_failure_bool";
    public static final String KEY_NO_REPLY_TIMER_FOR_CFNRY_SEC_INT = "no_reply_timer_for_cfnry_sec_int";
    public static final String KEY_NRARFCNS_RSRP_BOOST_INT_ARRAY = "nrarfcns_rsrp_boost_int_array";
    public static final String KEY_NRSA_ENTITLEMENT_APP_NAME_STRING = "nrsa_entitlement_app_name_string";
    public static final String KEY_NRSA_ENTITLEMENT_SERVER_URL_STRING = "nrsa_entitlement_server_url_string";
    public static final String KEY_NR_ADVANCED_BANDS_SECONDARY_TIMER_SECONDS_INT = "nr_advanced_bands_secondary_timer_seconds_int";
    public static final String KEY_NR_ADVANCED_CAPABLE_PCO_ID_INT = "nr_advanced_capable_pco_id_int";
    public static final String KEY_NR_ADVANCED_PCI_CHANGE_SECONDARY_TIMER_SECONDS_INT = "nr_advanced_pci_change_secondary_timer_seconds_int";
    public static final String KEY_NR_ADVANCED_THRESHOLD_BANDWIDTH_KHZ_INT = "nr_advanced_threshold_bandwidth_khz_int";
    public static final String KEY_NR_TIMERS_RESET_IF_NON_ENDC_AND_RRC_IDLE_BOOL = "nr_timers_reset_if_non_endc_and_rrc_idle_bool";
    public static final String KEY_NR_TIMERS_RESET_ON_PLMN_CHANGE_BOOL = "nr_timers_reset_on_plmn_change_bool";
    public static final String KEY_NR_TIMERS_RESET_ON_VOICE_QOS_BOOL = "nr_timers_reset_on_voice_qos_bool";
    public static final String KEY_NTN_LTE_RSRP_THRESHOLDS_INT_ARRAY = "ntn_lte_rsrp_thresholds_int_array";
    public static final String KEY_NTN_LTE_RSRQ_THRESHOLDS_INT_ARRAY = "ntn_lte_rsrq_thresholds_int_array";
    public static final String KEY_NTN_LTE_RSSNR_THRESHOLDS_INT_ARRAY = "ntn_lte_rssnr_thresholds_int_array";
    public static final String KEY_ONLY_AUTO_SELECT_IN_HOME_NETWORK_BOOL = "only_auto_select_in_home_network";
    public static final String KEY_ONLY_SINGLE_DC_ALLOWED_INT_ARRAY = "only_single_dc_allowed_int_array";
    public static final String KEY_OPERATOR_NAME_FILTER_PATTERN_STRING = "operator_name_filter_pattern_string";
    public static final String KEY_OPERATOR_SELECTION_EXPAND_BOOL = "operator_selection_expand_bool";
    public static final String KEY_OPL_OVERRIDE_STRING_ARRAY = "opl_override_opl_string_array";
    public static final String KEY_OPPORTUNISTIC_CARRIER_IDS_INT_ARRAY = "opportunistic_carrier_ids_int_array";
    public static final String KEY_OPPORTUNISTIC_ESIM_DOWNLOAD_VIA_WIFI_ONLY_BOOL = "opportunistic_esim_download_via_wifi_only_bool";
    public static final String KEY_OPPORTUNISTIC_NETWORK_BACKOFF_TIME_LONG = "opportunistic_network_backoff_time_long";
    public static final String KEY_OPPORTUNISTIC_NETWORK_DATA_SWITCH_EXIT_HYSTERESIS_TIME_LONG = "opportunistic_network_data_switch_exit_hysteresis_time_long";
    public static final String KEY_OPPORTUNISTIC_NETWORK_DATA_SWITCH_HYSTERESIS_TIME_LONG = "opportunistic_network_data_switch_hysteresis_time_long";
    public static final String KEY_OPPORTUNISTIC_NETWORK_ENTRY_OR_EXIT_HYSTERESIS_TIME_LONG = "opportunistic_network_entry_or_exit_hysteresis_time_long";
    public static final String KEY_OPPORTUNISTIC_NETWORK_ENTRY_THRESHOLD_BANDWIDTH_INT = "opportunistic_network_entry_threshold_bandwidth_int";
    public static final String KEY_OPPORTUNISTIC_NETWORK_ENTRY_THRESHOLD_RSRP_INT = "opportunistic_network_entry_threshold_rsrp_int";
    public static final String KEY_OPPORTUNISTIC_NETWORK_ENTRY_THRESHOLD_RSSNR_INT = "opportunistic_network_entry_threshold_rssnr_int";
    public static final String KEY_OPPORTUNISTIC_NETWORK_EXIT_THRESHOLD_RSRP_INT = "opportunistic_network_exit_threshold_rsrp_int";
    public static final String KEY_OPPORTUNISTIC_NETWORK_EXIT_THRESHOLD_RSSNR_INT = "opportunistic_network_exit_threshold_rssnr_int";
    public static final String KEY_OPPORTUNISTIC_NETWORK_MAX_BACKOFF_TIME_LONG = "opportunistic_network_max_backoff_time_long";
    public static final String KEY_OPPORTUNISTIC_NETWORK_PING_PONG_TIME_LONG = "opportunistic_network_ping_pong_time_long";
    public static final String KEY_OPPORTUNISTIC_NETWORK_SCAN_DEFAULT_PERIODIC_INT = "opportunistic_network_scan_default_periodic_int";
    public static final String KEY_OPPORTUNISTIC_TIME_TO_SCAN_AFTER_CAPABILITY_SWITCH_TO_PRIMARY_LONG = "opportunistic_time_to_scan_after_capability_switch_to_primary_long";
    public static final String KEY_OVERRIDE_WFC_ROAMING_MODE_WHILE_USING_NTN_BOOL = "override_wfc_roaming_mode_while_using_ntn_bool";
    public static final String KEY_PARAMETERS_USED_FOR_LTE_SIGNAL_BAR_INT = "parameters_used_for_lte_signal_bar_int";
    public static final String KEY_PARAMETERS_USED_FOR_NTN_LTE_SIGNAL_BAR_INT = "parameters_used_for_ntn_lte_signal_bar_int";
    public static final String KEY_PARAMETERS_USE_FOR_5G_NR_SIGNAL_BAR_INT = "parameters_use_for_5g_nr_signal_bar_int";
    public static final String KEY_PING_TEST_BEFORE_DATA_SWITCH_BOOL = "ping_test_before_data_switch_bool";
    public static final String KEY_PLAY_CALL_RECORDING_TONE_BOOL = "play_call_recording_tone_bool";
    public static final String KEY_PNN_OVERRIDE_STRING_ARRAY = "pnn_override_string_array";
    public static final String KEY_PREFERRED_IKE_PROTOCOL_INT = "preferred_ike_protocol_int";
    public static final String KEY_PREFER_2G_BOOL = "prefer_2g_bool";
    public static final String KEY_PREFER_3G_VISIBILITY_BOOL = "prefer_3g_visibility_bool";
    public static final String KEY_PREFER_IN_SERVICE_SIM_FOR_NORMAL_ROUTED_EMERGENCY_CALLS_BOOL = "prefer_in_service_sim_for_normal_routed_emergency_calls_bool";
    public static final String KEY_PREF_NETWORK_NOTIFICATION_DELAY_INT = "network_notification_delay_int";
    public static final String KEY_PREMIUM_CAPABILITY_MAXIMUM_DAILY_NOTIFICATION_COUNT_INT = "premium_capability_maximum_daily_notification_count_int";
    public static final String KEY_PREMIUM_CAPABILITY_MAXIMUM_MONTHLY_NOTIFICATION_COUNT_INT = "premium_capability_maximum_monthly_notification_count_int";
    public static final String KEY_PREMIUM_CAPABILITY_NETWORK_SETUP_TIME_MILLIS_LONG = "premium_capability_network_setup_time_millis_long";
    public static final String KEY_PREMIUM_CAPABILITY_NOTIFICATION_BACKOFF_HYSTERESIS_TIME_MILLIS_LONG = "premium_capability_notification_backoff_hysteresis_time_millis_long";
    public static final String KEY_PREMIUM_CAPABILITY_NOTIFICATION_DISPLAY_TIMEOUT_MILLIS_LONG = "premium_capability_notification_display_timeout_millis_long";
    public static final String KEY_PREMIUM_CAPABILITY_PURCHASE_CONDITION_BACKOFF_HYSTERESIS_TIME_MILLIS_LONG = "premium_capability_purchase_condition_backoff_hysteresis_time_millis_long";
    public static final String KEY_PREMIUM_CAPABILITY_PURCHASE_URL_STRING = "premium_capability_purchase_url_string";
    public static final String KEY_PREMIUM_CAPABILITY_SUPPORTED_ON_LTE_BOOL = "premium_capability_supported_on_lte_bool";
    public static final String KEY_PREVENT_CLIR_ACTIVATION_AND_DEACTIVATION_CODE_BOOL = "prevent_clir_activation_and_deactivation_code_bool";
    public static final String KEY_RADIO_RESTART_FAILURE_CAUSES_INT_ARRAY = "radio_restart_failure_causes_int_array";
    public static final String KEY_RATCHET_NR_ADVANCED_BANDWIDTH_IF_RRC_IDLE_BOOL = "ratchet_nr_advanced_bandwidth_if_rrc_idle_bool";
    public static final String KEY_RATCHET_RAT_FAMILIES = "ratchet_rat_families";
    public static final String KEY_RCS_CONFIG_SERVER_URL_STRING = "rcs_config_server_url_string";
    public static final String KEY_READ_ONLY_APN_FIELDS_STRING_ARRAY = "read_only_apn_fields_string_array";
    public static final String KEY_READ_ONLY_APN_TYPES_STRING_ARRAY = "read_only_apn_types_string_array";
    public static final String KEY_REGIONAL_SATELLITE_EARFCN_BUNDLE = "regional_satellite_earfcn_bundle";
    public static final String KEY_REMOVE_SATELLITE_PLMN_IN_MANUAL_NETWORK_SCAN_BOOL = "remove_satellite_plmn_in_manual_network_scan_bool";
    public static final String KEY_REQUIRE_ENTITLEMENT_CHECKS_BOOL = "require_entitlement_checks_bool";

    @Deprecated
    public static final String KEY_RESTART_RADIO_ON_PDP_FAIL_REGULAR_DEACTIVATION_BOOL = "restart_radio_on_pdp_fail_regular_deactivation_bool";
    public static final String KEY_ROAMING_OPERATOR_STRING_ARRAY = "roaming_operator_string_array";
    public static final String KEY_ROAMING_UNMETERED_NETWORK_TYPES_STRING_ARRAY = "roaming_unmetered_network_types_string_array";
    public static final String KEY_RTT_AUTO_UPGRADE_BOOL = "rtt_auto_upgrade_bool";
    public static final String KEY_RTT_DOWNGRADE_SUPPORTED_BOOL = "rtt_downgrade_supported_bool";
    public static final String KEY_RTT_SUPPORTED_BOOL = "rtt_supported_bool";
    public static final String KEY_RTT_SUPPORTED_FOR_VT_BOOL = "rtt_supported_for_vt_bool";
    public static final String KEY_RTT_SUPPORTED_WHILE_ROAMING_BOOL = "rtt_supported_while_roaming_bool";
    public static final String KEY_RTT_UPGRADE_SUPPORTED_BOOL = "rtt_upgrade_supported_bool";
    public static final String KEY_RTT_UPGRADE_SUPPORTED_FOR_DOWNGRADED_VT_CALL_BOOL = "rtt_upgrade_supported_for_downgraded_vt_call";
    public static final String KEY_SATELLITE_ATTACH_SUPPORTED_BOOL = "satellite_attach_supported_bool";
    public static final String KEY_SATELLITE_CONNECTED_NOTIFICATION_THROTTLE_MILLIS_INT = "satellite_connected_notification_throttle_millis_int";
    public static final String KEY_SATELLITE_CONNECTION_HYSTERESIS_SEC_INT = "satellite_connection_hysteresis_sec_int";
    public static final String KEY_SATELLITE_DATA_SUPPORT_MODE_INT = "satellite_data_support_mode_int";
    public static final String KEY_SATELLITE_DISPLAY_NAME_STRING = "satellite_display_name_string";
    public static final String KEY_SATELLITE_ENTITLEMENT_APP_NAME_STRING = "satellite_entitlement_app_name_string";
    public static final String KEY_SATELLITE_ENTITLEMENT_STATUS_REFRESH_DAYS_INT = "satellite_entitlement_status_refresh_days_int";
    public static final String KEY_SATELLITE_ENTITLEMENT_SUPPORTED_BOOL = "satellite_entitlement_supported_bool";
    public static final String KEY_SATELLITE_ESOS_SUPPORTED_BOOL = "satellite_esos_supported_bool";
    public static final String KEY_SATELLITE_IGNORE_DATA_ROAMING_SETTING_BOOL = "satellite_ignore_data_roaming_setting_bool";
    public static final String KEY_SATELLITE_INFORMATION_REDIRECT_URL_STRING = "satellite_information_redirect_url_string";
    public static final String KEY_SATELLITE_NIDD_APN_NAME_STRING = "satellite_nidd_apn_name_string";
    public static final String KEY_SATELLITE_ROAMING_ESOS_INACTIVITY_TIMEOUT_SEC_INT = "satellite_roaming_esos_inactivity_timeout_sec_int";
    public static final String KEY_SATELLITE_ROAMING_P2P_SMS_INACTIVITY_TIMEOUT_SEC_INT = "satellite_roaming_p2p_sms_inactivity_timeout_sec_int";
    public static final String KEY_SATELLITE_ROAMING_P2P_SMS_SUPPORTED_BOOL = "satellite_roaming_p2p_sms_supported_bool";
    public static final String KEY_SATELLITE_ROAMING_SCREEN_OFF_INACTIVITY_TIMEOUT_SEC_INT = "satellite_roaming_screen_off_inactivity_timeout_sec_int";
    public static final String KEY_SATELLITE_ROAMING_TURN_OFF_SESSION_FOR_EMERGENCY_CALL_BOOL = "satellite_roaming_turn_off_session_for_emergency_call_bool";
    public static final String KEY_SATELLITE_SOS_MAX_DATAGRAM_SIZE_BYTES_INT = "satellite_sos_max_datagram_size_bytes_int";
    public static final String KEY_SATELLITE_SUPPORTED_MSG_APPS_STRING_ARRAY = "satellite_supported_msg_apps_string_array";
    public static final String KEY_SHOW_4GLTE_FOR_LTE_DATA_ICON_BOOL = "show_4glte_for_lte_data_icon_bool";
    public static final String KEY_SHOW_4G_FOR_3G_DATA_ICON_BOOL = "show_4g_for_3g_data_icon_bool";
    public static final String KEY_SHOW_4G_FOR_LTE_DATA_ICON_BOOL = "show_4g_for_lte_data_icon_bool";
    public static final String KEY_SHOW_5G_SLICE_ICON_BOOL = "show_5g_slice_icon_bool";

    @Deprecated
    public static final String KEY_SHOW_APN_SETTING_CDMA_BOOL = "show_apn_setting_cdma_bool";
    public static final String KEY_SHOW_BLOCKING_PAY_PHONE_OPTION_BOOL = "show_blocking_pay_phone_option_bool";
    public static final String KEY_SHOW_CALL_BLOCKING_DISABLED_NOTIFICATION_ALWAYS_BOOL = "show_call_blocking_disabled_notification_always_bool";
    public static final String KEY_SHOW_CARRIER_DATA_ICON_PATTERN_STRING = "show_carrier_data_icon_pattern_string";

    @Deprecated
    public static final String KEY_SHOW_CDMA_CHOICES_BOOL = "show_cdma_choices_bool";
    public static final String KEY_SHOW_DATA_CONNECTED_ROAMING_NOTIFICATION_BOOL = "show_data_connected_roaming_notification";
    public static final String KEY_SHOW_FORWARDED_NUMBER_BOOL = "show_forwarded_number_bool";
    public static final String KEY_SHOW_ICCID_IN_SIM_STATUS_BOOL = "show_iccid_in_sim_status_bool";
    public static final String KEY_SHOW_IMS_REGISTRATION_STATUS_BOOL = "show_ims_registration_status_bool";
    public static final String KEY_SHOW_ONSCREEN_DIAL_BUTTON_BOOL = "show_onscreen_dial_button_bool";
    public static final String KEY_SHOW_OPERATOR_NAME_IN_STATUSBAR_BOOL = "show_operator_name_in_statusbar_bool";
    public static final String KEY_SHOW_PRECISE_FAILED_CAUSE_BOOL = "show_precise_failed_cause_bool";
    public static final String KEY_SHOW_ROAMING_INDICATOR_BOOL = "show_roaming_indicator_bool";
    public static final String KEY_SHOW_SIGNAL_STRENGTH_IN_SIM_STATUS_BOOL = "show_signal_strength_in_sim_status_bool";
    public static final String KEY_SHOW_SINGLE_OPERATOR_ROW_IN_CHOOSE_NETWORK_SETTING_BOOL = "show_single_operator_row_in_choose_network_setting_bool";
    public static final String KEY_SHOW_SPN_FOR_HOME_IN_CHOOSE_NETWORK_SETTING_BOOL = "show_spn_for_home_in_choose_network_setting_bool";
    public static final String KEY_SHOW_VIDEO_CALL_CHARGES_ALERT_DIALOG_BOOL = "show_video_call_charges_alert_dialog_bool";
    public static final String KEY_SHOW_WFC_LOCATION_PRIVACY_POLICY_BOOL = "show_wfc_location_privacy_policy_bool";
    public static final String KEY_SHOW_WIFI_CALLING_ICON_IN_STATUS_BAR_BOOL = "show_wifi_calling_icon_in_status_bar_bool";
    public static final String KEY_SIGNAL_STRENGTH_NR_NSA_USE_LTE_AS_PRIMARY_BOOL = "signal_strength_nr_nsa_use_lte_as_primary_bool";

    @Deprecated
    public static final String KEY_SIMPLIFIED_NETWORK_SETTINGS_BOOL = "simplified_network_settings_bool";
    public static final String KEY_SIM_COUNTRY_ISO_OVERRIDE_STRING = "sim_country_iso_override_string";
    public static final String KEY_SIM_NETWORK_UNLOCK_ALLOW_DISMISS_BOOL = "sim_network_unlock_allow_dismiss_bool";
    public static final String KEY_SKIP_CF_FAIL_TO_DISABLE_DIALOG_BOOL = "skip_cf_fail_to_disable_dialog_bool";
    public static final String KEY_SMART_FORWARDING_CONFIG_COMPONENT_NAME_STRING = "smart_forwarding_config_component_name_string";
    public static final String KEY_SMDP_SERVER_ADDRESS_STRING = "smdp_server_address_string";
    public static final String KEY_SMS_REQUIRES_DESTINATION_NUMBER_CONVERSION_BOOL = "sms_requires_destination_number_conversion_bool";
    public static final String KEY_SOLE_BANDWIDTH_BANDS_INT_ARRAY = "sole_bandwidth_bands_int_array";
    public static final String KEY_SPDI_OVERRIDE_STRING_ARRAY = "spdi_override_string_array";
    public static final String KEY_SPN_DISPLAY_CONDITION_OVERRIDE_INT = "spn_display_condition_override_int";
    public static final String KEY_SPN_DISPLAY_RULE_USE_ROAMING_FROM_SERVICE_STATE_BOOL = "spn_display_rule_use_roaming_from_service_state_bool";
    public static final String KEY_STK_DISABLE_LAUNCH_BROWSER_BOOL = "stk_disable_launch_browser_bool";
    public static final String KEY_STORE_SIM_PIN_FOR_UNATTENDED_REBOOT_BOOL = "store_sim_pin_for_unattended_reboot_bool";
    public static final String KEY_SUBSCRIPTION_GROUP_UUID_STRING = "subscription_group_uuid_string";
    public static final String KEY_SUPPORTED_PREMIUM_CAPABILITIES_INT_ARRAY = "supported_premium_capabilities_int_array";
    public static final String KEY_SUPPORTS_BUSINESS_CALL_COMPOSER_BOOL = "supports_business_call_composer_bool";
    public static final String KEY_SUPPORTS_CALL_COMPOSER_BOOL = "supports_call_composer_bool";
    public static final String KEY_SUPPORTS_DEVICE_TO_DEVICE_COMMUNICATION_USING_DTMF_BOOL = "supports_device_to_device_communication_using_dtmf_bool";
    public static final String KEY_SUPPORTS_DEVICE_TO_DEVICE_COMMUNICATION_USING_RTP_BOOL = "supports_device_to_device_communication_using_rtp_bool";
    public static final String KEY_SUPPORTS_SDP_NEGOTIATION_OF_D2D_RTP_HEADER_EXTENSIONS_BOOL = "supports_sdp_negotiation_of_d2d_rtp_header_extensions_bool";
    public static final String KEY_SUPPORT_3GPP_CALL_FORWARDING_WHILE_ROAMING_BOOL = "support_3gpp_call_forwarding_while_roaming_bool";
    public static final String KEY_SUPPORT_ADD_CONFERENCE_PARTICIPANTS_BOOL = "support_add_conference_participants_bool";
    public static final String KEY_SUPPORT_ADHOC_CONFERENCE_CALLS_BOOL = "support_adhoc_conference_calls_bool";

    @SystemApi
    @Deprecated
    public static final String KEY_SUPPORT_CDMA_1X_VOICE_CALLS_BOOL = "support_cdma_1x_voice_calls_bool";
    public static final String KEY_SUPPORT_CLIR_NETWORK_DEFAULT_BOOL = "support_clir_network_default_bool";
    public static final String KEY_SUPPORT_CONFERENCE_CALL_BOOL = "support_conference_call_bool";
    public static final String KEY_SUPPORT_DIRECT_FDN_DIALING_BOOL = "support_direct_fdn_dialing_bool";
    public static final String KEY_SUPPORT_DOWNGRADE_VT_TO_AUDIO_BOOL = "support_downgrade_vt_to_audio_bool";
    public static final String KEY_SUPPORT_EMERGENCY_DIALER_SHORTCUT_BOOL = "support_emergency_dialer_shortcut_bool";
    public static final String KEY_SUPPORT_EMERGENCY_SMS_OVER_IMS_BOOL = "support_emergency_sms_over_ims_bool";
    public static final String KEY_SUPPORT_ENHANCED_CALL_BLOCKING_BOOL = "support_enhanced_call_blocking_bool";
    public static final String KEY_SUPPORT_IMS_CALL_FORWARDING_WHILE_ROAMING_BOOL = "support_ims_call_forwarding_while_roaming_bool";
    public static final String KEY_SUPPORT_IMS_CONFERENCE_CALL_BOOL = "support_ims_conference_call_bool";
    public static final String KEY_SUPPORT_IMS_CONFERENCE_EVENT_PACKAGE_BOOL = "support_ims_conference_event_package_bool";
    public static final String KEY_SUPPORT_IMS_CONFERENCE_EVENT_PACKAGE_ON_PEER_BOOL = "support_ims_conference_event_package_on_peer_bool";
    public static final String KEY_SUPPORT_MANAGE_IMS_CONFERENCE_CALL_BOOL = "support_manage_ims_conference_call_bool";
    public static final String KEY_SUPPORT_NO_REPLY_TIMER_FOR_CFNRY_BOOL = "support_no_reply_timer_for_cfnry_bool";
    public static final String KEY_SUPPORT_PAUSE_IMS_VIDEO_CALLS_BOOL = "support_pause_ims_video_calls_bool";

    @Deprecated
    public static final String KEY_SUPPORT_SS_OVER_CDMA_BOOL = "support_ss_over_cdma_bool";

    @Deprecated
    public static final String KEY_SUPPORT_SWAP_AFTER_MERGE_BOOL = "support_swap_after_merge_bool";
    public static final String KEY_SUPPORT_TDSCDMA_BOOL = "support_tdscdma_bool";
    public static final String KEY_SUPPORT_TDSCDMA_ROAMING_NETWORKS_STRING_ARRAY = "support_tdscdma_roaming_networks_string_array";
    public static final String KEY_SUPPORT_VIDEO_CONFERENCE_CALL_BOOL = "support_video_conference_call_bool";
    public static final String KEY_SUPPORT_WPS_OVER_IMS_BOOL = "support_wps_over_ims_bool";
    public static final String KEY_SWITCH_DATA_TO_PRIMARY_IF_PRIMARY_IS_OOS_BOOL = "switch_data_to_primary_if_primary_is_oos_bool";
    public static final String KEY_TELEPHONY_DATA_HANDOVER_RETRY_RULES_STRING_ARRAY = "telephony_data_handover_retry_rules_string_array";
    public static final String KEY_TELEPHONY_DATA_SETUP_RETRY_RULES_STRING_ARRAY = "telephony_data_setup_retry_rules_string_array";
    public static final String KEY_TELEPHONY_NETWORK_CAPABILITY_PRIORITIES_STRING_ARRAY = "telephony_network_capability_priorities_string_array";
    public static final String KEY_TIME_TO_SWITCH_BACK_TO_PRIMARY_IF_OPPORTUNISTIC_OOS_LONG = "time_to_switch_back_to_primary_if_opportunistic_oos_long";
    public static final String KEY_TREAT_DOWNGRADED_VIDEO_CALLS_AS_VIDEO_CALLS_BOOL = "treat_downgraded_video_calls_as_video_calls_bool";
    public static final String KEY_TTY_SUPPORTED_BOOL = "tty_supported_bool";
    public static final String KEY_UNDELIVERED_SMS_MESSAGE_EXPIRATION_TIME = "undelivered_sms_message_expiration_time";
    public static final String KEY_UNLOGGABLE_NUMBERS_STRING_ARRAY = "unloggable_numbers_string_array";
    public static final String KEY_UNMETERED_NETWORK_TYPES_STRING_ARRAY = "unmetered_network_types_string_array";
    public static final String KEY_UNTHROTTLE_DATA_RETRY_WHEN_TAC_CHANGES_BOOL = "unthrottle_data_retry_when_tac_changes_bool";
    public static final String KEY_USE_ACS_FOR_RCS_BOOL = "use_acs_for_rcs_bool";
    public static final String KEY_USE_CALLER_ID_USSD_BOOL = "use_caller_id_ussd_bool";
    public static final String KEY_USE_CALL_FORWARDING_USSD_BOOL = "use_call_forwarding_ussd_bool";
    public static final String KEY_USE_CALL_WAITING_USSD_BOOL = "use_call_waiting_ussd_bool";
    public static final String KEY_USE_HFA_FOR_PROVISIONING_BOOL = "use_hfa_for_provisioning_bool";
    public static final String KEY_USE_IP_FOR_CALLING_INDICATOR_BOOL = "use_ip_for_calling_indicator_bool";
    public static final String KEY_USE_ONLY_DIALED_SIM_ECC_LIST_BOOL = "use_only_dialed_sim_ecc_list_bool";

    @Deprecated
    public static final String KEY_USE_ONLY_RSRP_FOR_LTE_SIGNAL_BAR_BOOL = "use_only_rsrp_for_lte_signal_bar_bool";

    @Deprecated
    public static final String KEY_USE_OTASP_FOR_PROVISIONING_BOOL = "use_otasp_for_provisioning_bool";

    @Deprecated
    public static final String KEY_USE_RCS_PRESENCE_BOOL = "use_rcs_presence_bool";
    public static final String KEY_USE_RCS_SIP_OPTIONS_BOOL = "use_rcs_sip_options_bool";
    public static final String KEY_USE_USIM_BOOL = "use_usim_bool";
    public static final String KEY_USE_WFC_HOME_NETWORK_MODE_IN_ROAMING_NETWORK_BOOL = "use_wfc_home_network_mode_in_roaming_network_bool";
    public static final String KEY_UTRAN_ECNO_HYSTERESIS_DB_INT = "utran_ecno_hysteresis_db_int";
    public static final String KEY_UTRAN_RSCP_HYSTERESIS_DB_INT = "utran_rscp_hysteresis_db_int";
    public static final String KEY_UW_DISQUALIFICATION_ENABLED_BOOL = "uw_disqualification_enabled_bool";
    public static final String KEY_VIDEO_CALLS_CAN_BE_HD_AUDIO = "video_calls_can_be_hd_audio";
    public static final String KEY_VILTE_DATA_IS_METERED_BOOL = "vilte_data_is_metered_bool";
    public static final String KEY_VOICEMAIL_NOTIFICATION_PERSISTENT_BOOL = "voicemail_notification_persistent_bool";
    public static final String KEY_VOICE_PRIVACY_DISABLE_UI_BOOL = "voice_privacy_disable_ui_bool";
    public static final String KEY_VOLTE_5G_LIMITED_ALERT_DIALOG_BOOL = "volte_5g_limited_alert_dialog_bool";
    public static final String KEY_VOLTE_REPLACEMENT_RAT_INT = "volte_replacement_rat_int";
    public static final String KEY_VONR_ENABLED_BOOL = "vonr_enabled_bool";
    public static final String KEY_VONR_ON_BY_DEFAULT_BOOL = "vonr_on_by_default_bool";
    public static final String KEY_VONR_SETTING_VISIBILITY_BOOL = "vonr_setting_visibility_bool";
    public static final String KEY_VT_UPGRADE_SUPPORTED_FOR_DOWNGRADED_RTT_CALL_BOOL = "vt_upgrade_supported_for_downgraded_rtt_call";
    public static final String KEY_VVM_CELLULAR_DATA_REQUIRED_BOOL = "vvm_cellular_data_required_bool";
    public static final String KEY_VVM_CLIENT_PREFIX_STRING = "vvm_client_prefix_string";
    public static final String KEY_VVM_DESTINATION_NUMBER_STRING = "vvm_destination_number_string";
    public static final String KEY_VVM_DISABLED_CAPABILITIES_STRING_ARRAY = "vvm_disabled_capabilities_string_array";
    public static final String KEY_VVM_LEGACY_MODE_ENABLED_BOOL = "vvm_legacy_mode_enabled_bool";
    public static final String KEY_VVM_PORT_NUMBER_INT = "vvm_port_number_int";
    public static final String KEY_VVM_PREFETCH_BOOL = "vvm_prefetch_bool";
    public static final String KEY_VVM_SSL_ENABLED_BOOL = "vvm_ssl_enabled_bool";
    public static final String KEY_VVM_TYPE_STRING = "vvm_type_string";
    public static final String KEY_WCDMA_DEFAULT_SIGNAL_STRENGTH_MEASUREMENT_STRING = "wcdma_default_signal_strength_measurement_string";
    public static final String KEY_WCDMA_ECNO_THRESHOLDS_INT_ARRAY = "wcdma_ecno_thresholds_int_array";
    public static final String KEY_WCDMA_RSCP_THRESHOLDS_INT_ARRAY = "wcdma_rscp_thresholds_int_array";
    public static final String KEY_WEAR_CONNECTIVITY_BT_TO_CELL_DELAY_MS_INT = "proxy_connectivity_delay_cell";
    public static final String KEY_WEAR_CONNECTIVITY_EXTEND_BT_TO_CELL_DELAY_ON_WIFI_MS_INT = "wifi_connectivity_extend_cell_delay";
    public static final String KEY_WFC_CARRIER_NAME_OVERRIDE_BY_PNN_BOOL = "wfc_carrier_name_override_by_pnn_bool";
    public static final String KEY_WFC_DATA_SPN_FORMAT_IDX_INT = "wfc_data_spn_format_idx_int";
    public static final String KEY_WFC_EMERGENCY_ADDRESS_CARRIER_APP_STRING = "wfc_emergency_address_carrier_app_string";
    public static final String KEY_WFC_FLIGHT_MODE_SPN_FORMAT_IDX_INT = "wfc_flight_mode_spn_format_idx_int";
    public static final String KEY_WFC_OPERATOR_ERROR_CODES_STRING_ARRAY = "wfc_operator_error_codes_string_array";
    public static final String KEY_WFC_SPN_FORMAT_IDX_INT = "wfc_spn_format_idx_int";
    public static final String KEY_WFC_SPN_USE_ROOT_LOCALE = "wfc_spn_use_root_locale";
    public static final String KEY_WIFI_CALLS_CAN_BE_HD_AUDIO = "wifi_calls_can_be_hd_audio";
    public static final String KEY_WORLD_MODE_ENABLED_BOOL = "world_mode_enabled_bool";
    public static final String KEY_WORLD_PHONE_BOOL = "world_phone_bool";
    public static final String REMOVE_GROUP_UUID_STRING = "00000000-0000-0000-0000-000000000000";
    public static final int SATELLITE_DATA_SUPPORT_ALL = 2;
    public static final int SATELLITE_DATA_SUPPORT_BANDWIDTH_CONSTRAINED = 1;
    public static final int SATELLITE_DATA_SUPPORT_ONLY_RESTRICTED = 0;
    public static final int SERVICE_CLASS_NONE = 0;
    public static final int SERVICE_CLASS_VOICE = 1;
    private static final String SPN_EMPTY_MATCH = "null";
    private static final String TAG = "CarrierConfigManager";
    public static final int USSD_OVER_CS_ONLY = 2;
    public static final int USSD_OVER_CS_PREFERRED = 0;
    public static final int USSD_OVER_IMS_ONLY = 3;
    public static final int USSD_OVER_IMS_PREFERRED = 1;
    private static final PersistableBundle sDefaults;
    private final Context mContext;

    public @interface CARRIER_ROAMING_NTN_CONNECT_TYPE {
    }

    public interface CarrierConfigChangeListener {
        void onCarrierConfigChanged(int i, int i2, int i3, int i4);
    }

    public @interface SATELLITE_DATA_SUPPORT_MODE {
    }

    public CarrierConfigManager(Context context) {
        this.mContext = context;
    }

    public static final class Apn {

        @Deprecated
        public static final String KEY_PREFIX = "apn.";
        public static final String KEY_SETTINGS_DEFAULT_PROTOCOL_STRING = "apn.settings_default_protocol_string";
        public static final String KEY_SETTINGS_DEFAULT_ROAMING_PROTOCOL_STRING = "apn.settings_default_roaming_protocol_string";
        public static final String PROTOCOL_IPV4 = "IP";
        public static final String PROTOCOL_IPV4V6 = "IPV4V6";
        public static final String PROTOCOL_IPV6 = "IPV6";

        private Apn() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static PersistableBundle getDefaults() {
            PersistableBundle persistableBundle = new PersistableBundle();
            persistableBundle.putString(KEY_SETTINGS_DEFAULT_PROTOCOL_STRING, "");
            persistableBundle.putString(KEY_SETTINGS_DEFAULT_ROAMING_PROTOCOL_STRING, "");
            return persistableBundle;
        }
    }

    public static class OpportunisticNetwork {
        public static final String KEY_5G_BACKOFF_TIME_LONG = "opportunistic.5g_backoff_time_long";
        public static final String KEY_5G_DATA_SWITCH_EXIT_HYSTERESIS_TIME_LONG = "opportunistic.5g_data_switch_exit_hysteresis_time_long";
        public static final String KEY_5G_DATA_SWITCH_EXIT_HYSTERESIS_TIME_LONG_BUNDLE = "opportunistic.5g_data_switch_exit_hysteresis_time_long_bundle";
        public static final String KEY_5G_DATA_SWITCH_HYSTERESIS_TIME_LONG = "opportunistic.5g_data_switch_hysteresis_time_long";
        public static final String KEY_5G_DATA_SWITCH_HYSTERESIS_TIME_LONG_BUNDLE = "opportunistic.5g_data_switch_hysteresis_time_long_bundle";
        public static final String KEY_5G_MAX_BACKOFF_TIME_LONG = "opportunistic.5g_max_backoff_time_long";
        public static final String KEY_5G_PING_PONG_TIME_LONG = "opportunistic.5g_ping_pong_time_long";
        public static final String KEY_ENTRY_THRESHOLD_SS_RSRP_INT = "opportunistic.entry_threshold_ss_rsrp_int";
        public static final String KEY_ENTRY_THRESHOLD_SS_RSRP_INT_BUNDLE = "opportunistic.entry_threshold_ss_rsrp_int_bundle";
        public static final String KEY_ENTRY_THRESHOLD_SS_RSRQ_DOUBLE = "opportunistic.entry_threshold_ss_rsrq_double";
        public static final String KEY_ENTRY_THRESHOLD_SS_RSRQ_DOUBLE_BUNDLE = "opportunistic.entry_threshold_ss_rsrq_double_bundle";
        public static final String KEY_EXIT_THRESHOLD_SS_RSRP_INT = "opportunistic.exit_threshold_ss_rsrp_int";
        public static final String KEY_EXIT_THRESHOLD_SS_RSRP_INT_BUNDLE = "opportunistic.exit_threshold_ss_rsrp_int_bundle";
        public static final String KEY_EXIT_THRESHOLD_SS_RSRQ_DOUBLE = "opportunistic.exit_threshold_ss_rsrq_double";
        public static final String KEY_EXIT_THRESHOLD_SS_RSRQ_DOUBLE_BUNDLE = "opportunistic.exit_threshold_ss_rsrq_double_bundle";
        public static final String PREFIX = "opportunistic.";

        /* JADX INFO: Access modifiers changed from: private */
        public static PersistableBundle getDefaults() {
            PersistableBundle persistableBundle = new PersistableBundle();
            CarrierConfigManager.sDefaults.putInt(KEY_ENTRY_THRESHOLD_SS_RSRP_INT, PackageManager.INSTALL_FAILED_USER_RESTRICTED);
            CarrierConfigManager.sDefaults.putPersistableBundle(KEY_ENTRY_THRESHOLD_SS_RSRP_INT_BUNDLE, PersistableBundle.EMPTY);
            CarrierConfigManager.sDefaults.putDouble(KEY_ENTRY_THRESHOLD_SS_RSRQ_DOUBLE, -18.5d);
            CarrierConfigManager.sDefaults.putPersistableBundle(KEY_ENTRY_THRESHOLD_SS_RSRQ_DOUBLE_BUNDLE, PersistableBundle.EMPTY);
            CarrierConfigManager.sDefaults.putInt(KEY_EXIT_THRESHOLD_SS_RSRP_INT, -120);
            CarrierConfigManager.sDefaults.putPersistableBundle(KEY_EXIT_THRESHOLD_SS_RSRP_INT_BUNDLE, PersistableBundle.EMPTY);
            CarrierConfigManager.sDefaults.putDouble(KEY_EXIT_THRESHOLD_SS_RSRQ_DOUBLE, -18.5d);
            CarrierConfigManager.sDefaults.putPersistableBundle(KEY_EXIT_THRESHOLD_SS_RSRQ_DOUBLE_BUNDLE, PersistableBundle.EMPTY);
            persistableBundle.putLong(KEY_5G_DATA_SWITCH_HYSTERESIS_TIME_LONG, 2000L);
            persistableBundle.putPersistableBundle(KEY_5G_DATA_SWITCH_HYSTERESIS_TIME_LONG_BUNDLE, PersistableBundle.EMPTY);
            persistableBundle.putLong(KEY_5G_DATA_SWITCH_EXIT_HYSTERESIS_TIME_LONG, 2000L);
            persistableBundle.putPersistableBundle(KEY_5G_DATA_SWITCH_EXIT_HYSTERESIS_TIME_LONG_BUNDLE, PersistableBundle.EMPTY);
            CarrierConfigManager.sDefaults.putLong(KEY_5G_BACKOFF_TIME_LONG, JobInfo.MIN_BACKOFF_MILLIS);
            CarrierConfigManager.sDefaults.putLong(KEY_5G_MAX_BACKOFF_TIME_LONG, 60000L);
            CarrierConfigManager.sDefaults.putLong(KEY_5G_PING_PONG_TIME_LONG, 60000L);
            return persistableBundle;
        }
    }

    public static final class ImsServiceEntitlement {
        public static final String KEY_DEFAULT_SERVICE_ENTITLEMENT_STATUS_BOOL = "imsserviceentitlement.default_service_entitlement_status_bool";
        public static final String KEY_ENTITLEMENT_SERVER_URL_STRING = "imsserviceentitlement.entitlement_server_url_string";
        public static final String KEY_ENTITLEMENT_VERSION_INT = "imsserviceentitlement.entitlement_version_int";
        public static final String KEY_FCM_SENDER_ID_STRING = "imsserviceentitlement.fcm_sender_id_string";
        public static final String KEY_IMS_PROVISIONING_BOOL = "imsserviceentitlement.ims_provisioning_bool";
        public static final String KEY_PREFIX = "imsserviceentitlement.";
        public static final String KEY_SHOW_VOWIFI_WEBVIEW_BOOL = "imsserviceentitlement.show_vowifi_webview_bool";
        public static final String KEY_SKIP_WFC_ACTIVATION_BOOL = "imsserviceentitlement.skip_wfc_activation_bool";

        private ImsServiceEntitlement() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static PersistableBundle getDefaults() {
            PersistableBundle persistableBundle = new PersistableBundle();
            persistableBundle.putString(KEY_ENTITLEMENT_SERVER_URL_STRING, "");
            persistableBundle.putString(KEY_FCM_SENDER_ID_STRING, "");
            persistableBundle.putBoolean(KEY_SHOW_VOWIFI_WEBVIEW_BOOL, false);
            persistableBundle.putBoolean(KEY_IMS_PROVISIONING_BOOL, false);
            persistableBundle.putBoolean(KEY_DEFAULT_SERVICE_ENTITLEMENT_STATUS_BOOL, false);
            persistableBundle.putBoolean(KEY_SKIP_WFC_ACTIVATION_BOOL, false);
            persistableBundle.putInt(KEY_ENTITLEMENT_VERSION_INT, 2);
            return persistableBundle;
        }
    }

    public static final class Gps {
        public static final String KEY_A_GLONASS_POS_PROTOCOL_SELECT_STRING = "gps.a_glonass_pos_protocol_select";
        public static final String KEY_ENABLE_NI_SUPL_MESSAGE_INJECTION_BOOL = "gps.enable_ni_supl_message_injection_bool";
        public static final String KEY_ES_EXTENSION_SEC_STRING = "gps.es_extension_sec";
        public static final String KEY_ES_SUPL_CONTROL_PLANE_SUPPORT_INT = "gps.es_supl_control_plane_support_int";
        public static final String KEY_ES_SUPL_DATA_PLANE_ONLY_ROAMING_PLMN_STRING_ARRAY = "gps.es_supl_data_plane_only_roaming_plmn_string_array";
        public static final String KEY_GPS_LOCK_STRING = "gps.gps_lock";
        public static final String KEY_LPP_PROFILE_STRING = "gps.lpp_profile";
        public static final String KEY_NFW_PROXY_APPS_STRING = "gps.nfw_proxy_apps";
        public static final String KEY_PERSIST_LPP_MODE_BOOL = "gps.persist_lpp_mode_bool";
        public static final String KEY_PREFIX = "gps.";
        public static final String KEY_SUPL_ES_STRING = "gps.supl_es";
        public static final String KEY_SUPL_HOST_STRING = "gps.supl_host";
        public static final String KEY_SUPL_MODE_STRING = "gps.supl_mode";
        public static final String KEY_SUPL_PORT_STRING = "gps.supl_port";
        public static final String KEY_SUPL_VER_STRING = "gps.supl_ver";
        public static final String KEY_USE_EMERGENCY_PDN_FOR_EMERGENCY_SUPL_STRING = "gps.use_emergency_pdn_for_emergency_supl";
        public static final int SUPL_EMERGENCY_MODE_TYPE_CP_FALLBACK = 1;
        public static final int SUPL_EMERGENCY_MODE_TYPE_CP_ONLY = 0;
        public static final int SUPL_EMERGENCY_MODE_TYPE_DP_ONLY = 2;

        private Gps() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static PersistableBundle getDefaults() {
            PersistableBundle persistableBundle = new PersistableBundle();
            persistableBundle.putBoolean(KEY_PERSIST_LPP_MODE_BOOL, true);
            persistableBundle.putString(KEY_SUPL_HOST_STRING, "supl.google.com");
            persistableBundle.putString(KEY_SUPL_PORT_STRING, "7275");
            persistableBundle.putString(KEY_SUPL_VER_STRING, "0x20000");
            persistableBundle.putString(KEY_SUPL_MODE_STRING, "1");
            persistableBundle.putString(KEY_SUPL_ES_STRING, "1");
            persistableBundle.putString(KEY_LPP_PROFILE_STRING, "2");
            persistableBundle.putString(KEY_USE_EMERGENCY_PDN_FOR_EMERGENCY_SUPL_STRING, "1");
            persistableBundle.putString(KEY_A_GLONASS_POS_PROTOCOL_SELECT_STRING, "0");
            persistableBundle.putString(KEY_GPS_LOCK_STRING, "3");
            persistableBundle.putString(KEY_ES_EXTENSION_SEC_STRING, "0");
            persistableBundle.putString(KEY_NFW_PROXY_APPS_STRING, "com.sec.location.nfwlocationprivacy");
            persistableBundle.putInt(KEY_ES_SUPL_CONTROL_PLANE_SUPPORT_INT, 0);
            persistableBundle.putStringArray(KEY_ES_SUPL_DATA_PLANE_ONLY_ROAMING_PLMN_STRING_ARRAY, null);
            if (Flags.enableNiSuplMessageInjectionByCarrierConfigBugfix()) {
                persistableBundle.putBoolean(KEY_ENABLE_NI_SUPL_MESSAGE_INJECTION_BOOL, false);
            }
            return persistableBundle;
        }
    }

    public static final class Ims {
        public static final int E911_RTCP_INACTIVITY_ON_CONNECTED = 3;
        public static final int E911_RTP_INACTIVITY_ON_CONNECTED = 4;
        public static final int GEOLOCATION_PIDF_FOR_EMERGENCY_ON_CELLULAR = 4;
        public static final int GEOLOCATION_PIDF_FOR_EMERGENCY_ON_WIFI = 2;
        public static final int GEOLOCATION_PIDF_FOR_NON_EMERGENCY_ON_CELLULAR = 3;
        public static final int GEOLOCATION_PIDF_FOR_NON_EMERGENCY_ON_WIFI = 1;
        public static final int IPSEC_AUTHENTICATION_ALGORITHM_HMAC_MD5 = 0;
        public static final int IPSEC_AUTHENTICATION_ALGORITHM_HMAC_SHA1 = 1;
        public static final int IPSEC_ENCRYPTION_ALGORITHM_AES_CBC = 2;
        public static final int IPSEC_ENCRYPTION_ALGORITHM_DES_EDE3_CBC = 1;
        public static final int IPSEC_ENCRYPTION_ALGORITHM_NULL = 0;
        public static final String KEY_ALLOW_NON_GLOBAL_PHONE_NUMBER_FORMAT_BOOL = "ims.allow_non_global_phone_number_format_bool";
        public static final String KEY_CAPABILITY_TYPE_CALL_COMPOSER_INT_ARRAY = "ims.capability_type_call_composer_int_array";
        public static final String KEY_CAPABILITY_TYPE_OPTIONS_UCE_INT_ARRAY = "ims.capability_type_options_uce_int_array";
        public static final String KEY_CAPABILITY_TYPE_PRESENCE_UCE_INT_ARRAY = "ims.capability_type_presence_uce_int_array";
        public static final String KEY_CAPABILITY_TYPE_SMS_INT_ARRAY = "ims.capability_type_sms_int_array";
        public static final String KEY_CAPABILITY_TYPE_UT_INT_ARRAY = "ims.capability_type_ut_int_array";
        public static final String KEY_CAPABILITY_TYPE_VIDEO_INT_ARRAY = "ims.capability_type_video_int_array";
        public static final String KEY_CAPABILITY_TYPE_VOICE_INT_ARRAY = "ims.capability_type_voice_int_array";
        public static final String KEY_ENABLE_PRESENCE_CAPABILITY_EXCHANGE_BOOL = "ims.enable_presence_capability_exchange_bool";
        public static final String KEY_ENABLE_PRESENCE_GROUP_SUBSCRIBE_BOOL = "ims.enable_presence_group_subscribe_bool";
        public static final String KEY_ENABLE_PRESENCE_PUBLISH_BOOL = "ims.enable_presence_publish_bool";
        public static final String KEY_GEOLOCATION_PIDF_IN_SIP_INVITE_SUPPORT_INT_ARRAY = "ims.geolocation_pidf_in_sip_invite_support_int_array";
        public static final String KEY_GEOLOCATION_PIDF_IN_SIP_REGISTER_SUPPORT_INT_ARRAY = "ims.geolocation_pidf_in_sip_register_support_int_array";
        public static final String KEY_GRUU_ENABLED_BOOL = "ims.gruu_enabled_bool";
        public static final String KEY_IMS_PDN_ENABLED_IN_NO_VOPS_SUPPORT_INT_ARRAY = "ims.ims_pdn_enabled_in_no_vops_support_int_array";
        public static final String KEY_IMS_SINGLE_REGISTRATION_REQUIRED_BOOL = "ims.ims_single_registration_required_bool";
        public static final String KEY_IMS_USER_AGENT_STRING = "ims.ims_user_agent_string";
        public static final String KEY_IPSEC_AUTHENTICATION_ALGORITHMS_INT_ARRAY = "ims.ipsec_authentication_algorithms_int_array";
        public static final String KEY_IPSEC_ENCRYPTION_ALGORITHMS_INT_ARRAY = "ims.ipsec_encryption_algorithms_int_array";
        public static final String KEY_IPV4_SIP_MTU_SIZE_CELLULAR_INT = "ims.ipv4_sip_mtu_size_cellular_int";
        public static final String KEY_IPV6_SIP_MTU_SIZE_CELLULAR_INT = "ims.ipv6_sip_mtu_size_cellular_int";
        public static final String KEY_KEEP_PDN_UP_IN_NO_VOPS_BOOL = "ims.keep_pdn_up_in_no_vops_bool";
        public static final String KEY_MMTEL_REQUIRES_PROVISIONING_BUNDLE = "ims.mmtel_requires_provisioning_bundle";
        public static final String KEY_NON_RCS_CAPABILITIES_CACHE_EXPIRATION_SEC_INT = "ims.non_rcs_capabilities_cache_expiration_sec_int";
        public static final String KEY_NR_SA_DISABLE_POLICY_INT = "ims.sa_disable_policy_int";
        public static final String KEY_PHONE_CONTEXT_DOMAIN_NAME_STRING = "ims.phone_context_domain_name_string";
        public static final String KEY_PREFIX = "ims.";
        public static final String KEY_PUBLISH_SERVICE_DESC_FEATURE_TAG_MAP_OVERRIDE_STRING_ARRAY = "ims.publish_service_desc_feature_tag_map_override_string_array";
        public static final String KEY_RCS_BULK_CAPABILITY_EXCHANGE_BOOL = "ims.rcs_bulk_capability_exchange_bool";
        public static final String KEY_RCS_FEATURE_TAG_ALLOWED_STRING_ARRAY = "ims.rcs_feature_tag_allowed_string_array";
        public static final String KEY_RCS_REQUEST_FORBIDDEN_BY_SIP_489_BOOL = "ims.rcs_request_forbidden_by_sip_489_bool";
        public static final String KEY_RCS_REQUEST_RETRY_INTERVAL_MILLIS_LONG = "ims.rcs_request_retry_interval_millis_long";
        public static final String KEY_RCS_REQUIRES_PROVISIONING_BUNDLE = "ims.rcs_requires_provisioning_bundle";
        public static final String KEY_REGISTRATION_EVENT_PACKAGE_SUPPORTED_BOOL = "ims.registration_event_package_supported_bool";
        public static final String KEY_REGISTRATION_EXPIRY_TIMER_SEC_INT = "ims.registration_expiry_timer_sec_int";
        public static final String KEY_REGISTRATION_RETRY_BASE_TIMER_MILLIS_INT = "ims.registration_retry_base_timer_millis_int";
        public static final String KEY_REGISTRATION_RETRY_MAX_TIMER_MILLIS_INT = "ims.registration_retry_max_timer_millis_int";
        public static final String KEY_REGISTRATION_SUBSCRIBE_EXPIRY_TIMER_SEC_INT = "ims.registration_subscribe_expiry_timer_sec_int";
        public static final String KEY_REQUEST_URI_TYPE_INT = "ims.request_uri_type_int";
        public static final String KEY_SIP_OVER_IPSEC_ENABLED_BOOL = "ims.sip_over_ipsec_enabled_bool";
        public static final String KEY_SIP_PREFERRED_TRANSPORT_INT = "ims.sip_preferred_transport_int";
        public static final String KEY_SIP_SERVER_PORT_NUMBER_INT = "ims.sip_server_port_number_int";
        public static final String KEY_SIP_TIMER_B_MILLIS_INT = "ims.sip_timer_b_millis_int";
        public static final String KEY_SIP_TIMER_C_MILLIS_INT = "ims.sip_timer_c_millis_int";
        public static final String KEY_SIP_TIMER_D_MILLIS_INT = "ims.sip_timer_d_millis_int";
        public static final String KEY_SIP_TIMER_F_MILLIS_INT = "ims.sip_timer_f_millis_int";
        public static final String KEY_SIP_TIMER_H_MILLIS_INT = "ims.sip_timer_h_millis_int";
        public static final String KEY_SIP_TIMER_J_MILLIS_INT = "ims.sip_timer_j_millis_int";
        public static final String KEY_SIP_TIMER_T1_MILLIS_INT = "ims.sip_timer_t1_millis_int";
        public static final String KEY_SIP_TIMER_T2_MILLIS_INT = "ims.sip_timer_t2_millis_int";
        public static final String KEY_SIP_TIMER_T4_MILLIS_INT = "ims.sip_timer_t4_millis_int";
        public static final String KEY_SUBSCRIBE_RETRY_DURATION_MILLIS_LONG = "ims.subscribe_retry_duration_millis_long";
        public static final String KEY_SUPPORTED_RATS_INT_ARRAY = "ims.supported_rats_int_array";
        public static final String KEY_USE_SIP_URI_FOR_PRESENCE_SUBSCRIBE_BOOL = "ims.use_sip_uri_for_presence_subscribe_bool";
        public static final String KEY_USE_TEL_URI_FOR_PIDF_XML_BOOL = "ims.use_tel_uri_for_pidf_xml";
        public static final String KEY_WIFI_OFF_DEFERRING_TIME_MILLIS_INT = "ims.wifi_off_deferring_time_millis_int";
        public static final int NETWORK_TYPE_HOME = 0;
        public static final int NETWORK_TYPE_ROAMING = 1;
        public static final int NR_SA_DISABLE_POLICY_NONE = 0;
        public static final int NR_SA_DISABLE_POLICY_VOWIFI_REGISTERED = 3;
        public static final int NR_SA_DISABLE_POLICY_WFC_ESTABLISHED = 1;
        public static final int NR_SA_DISABLE_POLICY_WFC_ESTABLISHED_WHEN_VONR_DISABLED = 2;
        public static final int PREFERRED_TRANSPORT_DYNAMIC_UDP_TCP = 2;
        public static final int PREFERRED_TRANSPORT_TCP = 1;
        public static final int PREFERRED_TRANSPORT_TLS = 3;
        public static final int PREFERRED_TRANSPORT_UDP = 0;
        public static final int REQUEST_URI_FORMAT_SIP = 1;
        public static final int REQUEST_URI_FORMAT_TEL = 0;
        public static final int RTCP_INACTIVITY_ON_CONNECTED = 1;
        public static final int RTCP_INACTIVITY_ON_HOLD = 0;
        public static final int RTP_INACTIVITY_ON_CONNECTED = 2;

        public @interface GeolocationPidfAllowedType {
        }

        public @interface IpsecAuthenticationAlgorithmType {
        }

        public @interface IpsecEncryptionAlgorithmType {
        }

        public @interface MediaInactivityReason {
        }

        public @interface NetworkType {
        }

        public @interface NrSaDisablePolicy {
        }

        public @interface PreferredTransportType {
        }

        public @interface RequestUriFormatType {
        }

        private Ims() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static PersistableBundle getDefaults() {
            PersistableBundle persistableBundle = new PersistableBundle();
            persistableBundle.putInt(KEY_WIFI_OFF_DEFERRING_TIME_MILLIS_INT, 4000);
            persistableBundle.putBoolean(KEY_IMS_SINGLE_REGISTRATION_REQUIRED_BOOL, false);
            persistableBundle.putBoolean(KEY_ENABLE_PRESENCE_PUBLISH_BOOL, false);
            persistableBundle.putStringArray(KEY_PUBLISH_SERVICE_DESC_FEATURE_TAG_MAP_OVERRIDE_STRING_ARRAY, new String[0]);
            persistableBundle.putBoolean(KEY_ENABLE_PRESENCE_CAPABILITY_EXCHANGE_BOOL, false);
            persistableBundle.putBoolean(KEY_RCS_BULK_CAPABILITY_EXCHANGE_BOOL, false);
            persistableBundle.putBoolean(KEY_ENABLE_PRESENCE_GROUP_SUBSCRIBE_BOOL, false);
            persistableBundle.putLong(KEY_SUBSCRIBE_RETRY_DURATION_MILLIS_LONG, -1L);
            persistableBundle.putBoolean(KEY_USE_SIP_URI_FOR_PRESENCE_SUBSCRIBE_BOOL, false);
            persistableBundle.putInt(KEY_NON_RCS_CAPABILITIES_CACHE_EXPIRATION_SEC_INT, 2592000);
            persistableBundle.putBoolean(KEY_RCS_REQUEST_FORBIDDEN_BY_SIP_489_BOOL, false);
            persistableBundle.putLong(KEY_RCS_REQUEST_RETRY_INTERVAL_MILLIS_LONG, 1200000L);
            persistableBundle.putStringArray(KEY_RCS_FEATURE_TAG_ALLOWED_STRING_ARRAY, new String[]{"+g.3gpp.icsi-ref=\"urn%3Aurn-7%3A3gpp-service.ims.icsi.oma.cpm.msg\"", "+g.3gpp.icsi-ref=\"urn%3Aurn-7%3A3gpp-service.ims.icsi.oma.cpm.largemsg\"", "+g.3gpp.icsi-ref=\"urn%3Aurn-7%3A3gpp-service.ims.icsi.oma.cpm.deferred\"", "+g.gsma.rcs.cpm.pager-large", "+g.3gpp.icsi-ref=\"urn%3Aurn-7%3A3gpp-service.ims.icsi.oma.cpm.session\"", "+g.3gpp.icsi-ref=\"urn%3Aurn-7%3A3gpp-service.ims.icsi.oma.cpm.filetransfer\"", CapInfo.FILE_TRANSFER_HTTP, "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.ftsms\"", "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-service.ims.icsi.gsma.callcomposer\"", CapInfo.MMTEL_CALLCOMPOSER, "+g.3gpp.icsi-ref=\"urn%3Aurn-7%3A3gpp-service.ims.icsi.gsma.callunanswered\"", "+g.3gpp.icsi-ref=\"urn%3Aurn-7%3A3gpp-service.ims.icsi.gsma.sharedmap\"", "+g.3gpp.icsi-ref=\"urn%3Aurn-7%3A3gpp-service.ims.icsi.gsma.sharedsketch\"", CapInfo.GEOPUSH, "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.geosms\"", "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.chatbot\"", CapInfo.STANDALONE_CHATBOT, "+g.gsma.rcs.botversion=\"#=1,#=2\"", "+g.gsma.rcs.cpimext"});
            persistableBundle.putPersistableBundle(KEY_MMTEL_REQUIRES_PROVISIONING_BUNDLE, new PersistableBundle());
            persistableBundle.putPersistableBundle(KEY_RCS_REQUIRES_PROVISIONING_BUNDLE, new PersistableBundle());
            persistableBundle.putBoolean(KEY_GRUU_ENABLED_BOOL, false);
            persistableBundle.putBoolean(KEY_SIP_OVER_IPSEC_ENABLED_BOOL, true);
            persistableBundle.putBoolean(KEY_KEEP_PDN_UP_IN_NO_VOPS_BOOL, false);
            persistableBundle.putBoolean(KEY_REGISTRATION_EVENT_PACKAGE_SUPPORTED_BOOL, true);
            persistableBundle.putInt(KEY_SIP_TIMER_T1_MILLIS_INT, 2000);
            persistableBundle.putInt(KEY_SIP_TIMER_T2_MILLIS_INT, 16000);
            persistableBundle.putInt(KEY_SIP_TIMER_T4_MILLIS_INT, EncodeConstants.BitRate.MM_BITRATE_AVC_FHD_30);
            persistableBundle.putInt(KEY_SIP_TIMER_B_MILLIS_INT, 128000);
            persistableBundle.putInt(KEY_SIP_TIMER_C_MILLIS_INT, 210000);
            persistableBundle.putInt(KEY_SIP_TIMER_D_MILLIS_INT, SemDualAppManager.SepVersionInt.SEP_VER_13_0_INT);
            persistableBundle.putInt(KEY_SIP_TIMER_F_MILLIS_INT, 128000);
            persistableBundle.putInt(KEY_SIP_TIMER_H_MILLIS_INT, 128000);
            persistableBundle.putInt(KEY_SIP_TIMER_J_MILLIS_INT, 128000);
            persistableBundle.putInt(KEY_SIP_SERVER_PORT_NUMBER_INT, 5060);
            persistableBundle.putInt(KEY_REQUEST_URI_TYPE_INT, 0);
            persistableBundle.putInt(KEY_SIP_PREFERRED_TRANSPORT_INT, 2);
            persistableBundle.putInt(KEY_IPV4_SIP_MTU_SIZE_CELLULAR_INT, 1500);
            persistableBundle.putInt(KEY_IPV6_SIP_MTU_SIZE_CELLULAR_INT, 1500);
            persistableBundle.putInt(KEY_REGISTRATION_EXPIRY_TIMER_SEC_INT, Build.VERSION_CODES_FULL.ECLAIR_0_1);
            persistableBundle.putInt(KEY_REGISTRATION_RETRY_BASE_TIMER_MILLIS_INT, 30000);
            persistableBundle.putInt(KEY_REGISTRATION_RETRY_MAX_TIMER_MILLIS_INT, Build.VERSION_CODES_FULL.JELLY_BEAN_MR2);
            persistableBundle.putInt(KEY_REGISTRATION_SUBSCRIBE_EXPIRY_TIMER_SEC_INT, Build.VERSION_CODES_FULL.ECLAIR_0_1);
            persistableBundle.putInt(KEY_NR_SA_DISABLE_POLICY_INT, 0);
            persistableBundle.putIntArray(KEY_IPSEC_AUTHENTICATION_ALGORITHMS_INT_ARRAY, new int[]{0, 1});
            persistableBundle.putIntArray(KEY_IPSEC_ENCRYPTION_ALGORITHMS_INT_ARRAY, new int[]{0, 1, 2});
            persistableBundle.putIntArray(KEY_IMS_PDN_ENABLED_IN_NO_VOPS_SUPPORT_INT_ARRAY, new int[0]);
            persistableBundle.putIntArray(KEY_GEOLOCATION_PIDF_IN_SIP_REGISTER_SUPPORT_INT_ARRAY, new int[]{2});
            persistableBundle.putIntArray(KEY_GEOLOCATION_PIDF_IN_SIP_INVITE_SUPPORT_INT_ARRAY, new int[]{2});
            persistableBundle.putIntArray(KEY_SUPPORTED_RATS_INT_ARRAY, new int[]{6, 3, 5});
            persistableBundle.putString(KEY_PHONE_CONTEXT_DOMAIN_NAME_STRING, "");
            persistableBundle.putString(KEY_IMS_USER_AGENT_STRING, "#MANUFACTURER#_#MODEL#_Android#AV#_#BUILD#");
            persistableBundle.putBoolean(KEY_ALLOW_NON_GLOBAL_PHONE_NUMBER_FORMAT_BOOL, false);
            return persistableBundle;
        }
    }

    public static final class ImsVoice {
        public static final int ALERTING_SRVCC_SUPPORT = 1;
        public static final int BANDWIDTH_EFFICIENT = 0;
        public static final int BASIC_SRVCC_SUPPORT = 0;
        public static final int CONFERENCE_SUBSCRIBE_TYPE_IN_DIALOG = 0;
        public static final int CONFERENCE_SUBSCRIBE_TYPE_OUT_OF_DIALOG = 1;
        public static final int EVS_ENCODED_BW_TYPE_FB = 3;
        public static final int EVS_ENCODED_BW_TYPE_NB = 0;
        public static final int EVS_ENCODED_BW_TYPE_NB_WB = 4;
        public static final int EVS_ENCODED_BW_TYPE_NB_WB_SWB = 5;
        public static final int EVS_ENCODED_BW_TYPE_NB_WB_SWB_FB = 6;
        public static final int EVS_ENCODED_BW_TYPE_SWB = 2;
        public static final int EVS_ENCODED_BW_TYPE_WB = 1;
        public static final int EVS_ENCODED_BW_TYPE_WB_SWB = 7;
        public static final int EVS_ENCODED_BW_TYPE_WB_SWB_FB = 8;
        public static final int EVS_OPERATIONAL_MODE_AMRWB_IO = 1;
        public static final int EVS_OPERATIONAL_MODE_PRIMARY = 0;
        public static final int EVS_PRIMARY_MODE_BITRATE_128_0_KBPS = 11;
        public static final int EVS_PRIMARY_MODE_BITRATE_13_2_KBPS = 4;
        public static final int EVS_PRIMARY_MODE_BITRATE_16_4_KBPS = 5;
        public static final int EVS_PRIMARY_MODE_BITRATE_24_4_KBPS = 6;
        public static final int EVS_PRIMARY_MODE_BITRATE_32_0_KBPS = 7;
        public static final int EVS_PRIMARY_MODE_BITRATE_48_0_KBPS = 8;
        public static final int EVS_PRIMARY_MODE_BITRATE_5_9_KBPS = 0;
        public static final int EVS_PRIMARY_MODE_BITRATE_64_0_KBPS = 9;
        public static final int EVS_PRIMARY_MODE_BITRATE_7_2_KBPS = 1;
        public static final int EVS_PRIMARY_MODE_BITRATE_8_0_KBPS = 2;
        public static final int EVS_PRIMARY_MODE_BITRATE_96_0_KBPS = 10;
        public static final int EVS_PRIMARY_MODE_BITRATE_9_6_KBPS = 3;
        public static final String KEY_AMRNB_PAYLOAD_DESCRIPTION_BUNDLE = "imsvoice.amrnb_payload_description_bundle";
        public static final String KEY_AMRNB_PAYLOAD_TYPE_INT_ARRAY = "imsvoice.amrnb_payload_type_int_array";
        public static final String KEY_AMRWB_PAYLOAD_DESCRIPTION_BUNDLE = "imsvoice.amrwb_payload_description_bundle";
        public static final String KEY_AMRWB_PAYLOAD_TYPE_INT_ARRAY = "imsvoice.amrwb_payload_type_int_array";
        public static final String KEY_AMR_CODEC_ATTRIBUTE_MODESET_INT_ARRAY = "imsvoice.amr_codec_attribute_modeset_int_array";
        public static final String KEY_AMR_CODEC_ATTRIBUTE_PAYLOAD_FORMAT_INT = "imsvoice.amr_codec_attribute_payload_format_int";
        public static final String KEY_AUDIO_AS_BANDWIDTH_KBPS_INT = "imsvoice.audio_as_bandwidth_kbps_int";
        public static final String KEY_AUDIO_CODEC_CAPABILITY_PAYLOAD_TYPES_BUNDLE = "imsvoice.audio_codec_capability_payload_types_bundle";
        public static final String KEY_AUDIO_INACTIVITY_CALL_END_REASONS_INT_ARRAY = "imsvoice.audio_inactivity_call_end_reasons_int_array";
        public static final String KEY_AUDIO_RR_BANDWIDTH_BPS_INT = "imsvoice.audio_rr_bandwidth_bps_int";
        public static final String KEY_AUDIO_RS_BANDWIDTH_BPS_INT = "imsvoice.audio_rs_bandwidth_bps_int";
        public static final String KEY_AUDIO_RTCP_INACTIVITY_TIMER_MILLIS_INT = "imsvoice.audio_rtcp_inactivity_timer_millis_int";
        public static final String KEY_AUDIO_RTP_INACTIVITY_TIMER_MILLIS_INT = "imsvoice.audio_rtp_inactivity_timer_millis_int";
        public static final String KEY_CARRIER_VOLTE_ROAMING_AVAILABLE_BOOL = "imsvoice.carrier_volte_roaming_available_bool";
        public static final String KEY_CODEC_ATTRIBUTE_MODE_CHANGE_CAPABILITY_INT = "imsvoice.codec_attribute_mode_change_capability_int";
        public static final String KEY_CODEC_ATTRIBUTE_MODE_CHANGE_NEIGHBOR_INT = "imsvoice.codec_attribute_mode_change_neighbor_int";
        public static final String KEY_CODEC_ATTRIBUTE_MODE_CHANGE_PERIOD_INT = "imsvoice.codec_attribute_mode_change_period_int";
        public static final String KEY_CONFERENCE_FACTORY_URI_STRING = "imsvoice.conference_factory_uri_string";
        public static final String KEY_CONFERENCE_SUBSCRIBE_TYPE_INT = "imsvoice.conference_subscribe_type_int";
        public static final String KEY_DEDICATED_BEARER_WAIT_TIMER_MILLIS_INT = "imsvoice.dedicated_bearer_wait_timer_millis_int";
        public static final String KEY_DTMFNB_PAYLOAD_TYPE_INT_ARRAY = "imsvoice.dtmfnb_payload_type_int_array";
        public static final String KEY_DTMFWB_PAYLOAD_TYPE_INT_ARRAY = "imsvoice.dtmfwb_payload_type_int_array";
        public static final String KEY_EVS_CODEC_ATTRIBUTE_BANDWIDTH_INT = "imsvoice.evs_codec_attribute_bandwidth_int";
        public static final String KEY_EVS_CODEC_ATTRIBUTE_BITRATE_INT_ARRAY = "imsvoice.evs_codec_attribute_bitrate_int_array";
        public static final String KEY_EVS_CODEC_ATTRIBUTE_CHANNELS_INT = "imsvoice.evs_codec_attribute_channels_int";
        public static final String KEY_EVS_CODEC_ATTRIBUTE_CH_AW_RECV_INT = "imsvoice.evs_codec_attribute_ch_aw_recv_int";
        public static final String KEY_EVS_CODEC_ATTRIBUTE_CMR_INT = "imsvoice.codec_attribute_cmr_int";
        public static final String KEY_EVS_CODEC_ATTRIBUTE_DTX_BOOL = "imsvoice.evs_codec_attribute_dtx_bool";
        public static final String KEY_EVS_CODEC_ATTRIBUTE_DTX_RECV_BOOL = "imsvoice.evs_codec_attribute_dtx_recv_bool";
        public static final String KEY_EVS_CODEC_ATTRIBUTE_HF_ONLY_INT = "imsvoice.evs_codec_attribute_hf_only_int";
        public static final String KEY_EVS_CODEC_ATTRIBUTE_MODE_SWITCH_INT = "imsvoice.evs_codec_attribute_mode_switch_int";
        public static final String KEY_EVS_PAYLOAD_DESCRIPTION_BUNDLE = "imsvoice.evs_payload_description_bundle";
        public static final String KEY_EVS_PAYLOAD_TYPE_INT_ARRAY = "imsvoice.evs_payload_type_int_array";
        public static final String KEY_INCLUDE_CALLER_ID_SERVICE_CODES_IN_SIP_INVITE_BOOL = "imsvoice.include_caller_id_service_codes_in_sip_invite_bool";
        public static final String KEY_MINIMUM_SESSION_EXPIRES_TIMER_SEC_INT = "imsvoice.minimum_session_expires_timer_sec_int";
        public static final String KEY_MO_CALL_REQUEST_TIMEOUT_MILLIS_INT = "imsvoice.mo_call_request_timeout_millis_int";
        public static final String KEY_MULTIENDPOINT_SUPPORTED_BOOL = "imsvoice.multiendpoint_supported_bool";
        public static final String KEY_OIP_SOURCE_FROM_HEADER_BOOL = "imsvoice.oip_source_from_header_bool";
        public static final String KEY_PRACK_SUPPORTED_FOR_18X_BOOL = "imsvoice.prack_supported_for_18x_bool";
        public static final String KEY_PREFIX = "imsvoice.";
        public static final String KEY_RINGBACK_TIMER_MILLIS_INT = "imsvoice.ringback_timer_millis_int";
        public static final String KEY_RINGING_TIMER_MILLIS_INT = "imsvoice.ringing_timer_millis_int";
        public static final String KEY_SESSION_EXPIRES_TIMER_SEC_INT = "imsvoice.session_expires_timer_sec_int";
        public static final String KEY_SESSION_PRIVACY_TYPE_INT = "imsvoice.session_privacy_type_int";
        public static final String KEY_SESSION_REFRESHER_TYPE_INT = "imsvoice.session_refresher_type_int";
        public static final String KEY_SESSION_REFRESH_METHOD_INT = "imsvoice.session_refresh_method_int";
        public static final String KEY_SESSION_TIMER_SUPPORTED_BOOL = "imsvoice.session_timer_supported_bool";
        public static final String KEY_SRVCC_TYPE_INT_ARRAY = "imsvoice.srvcc_type_int_array";
        public static final String KEY_VOICE_ON_DEFAULT_BEARER_SUPPORTED_BOOL = "imsvoice.voice_on_default_bearer_supported_bool";
        public static final String KEY_VOICE_QOS_PRECONDITION_SUPPORTED_BOOL = "imsvoice.voice_qos_precondition_supported_bool";
        public static final String KEY_VOICE_RTP_INACTIVITY_TIME_THRESHOLD_MILLIS_LONG = "imsvoice.rtp_inactivity_time_threshold_millis_long";
        public static final String KEY_VOICE_RTP_JITTER_THRESHOLD_MILLIS_INT = "imsvoice.rtp_jitter_threshold_millis_int";
        public static final String KEY_VOICE_RTP_PACKET_LOSS_RATE_THRESHOLD_INT = "imsvoice.rtp_packet_loss_rate_threshold_int";
        public static final int MIDCALL_SRVCC_SUPPORT = 3;
        public static final int OCTET_ALIGNED = 1;
        public static final int PREALERTING_SRVCC_SUPPORT = 2;
        public static final int SESSION_PRIVACY_TYPE_HEADER = 0;
        public static final int SESSION_PRIVACY_TYPE_ID = 2;
        public static final int SESSION_PRIVACY_TYPE_NONE = 1;
        public static final int SESSION_REFRESHER_TYPE_UAC = 1;
        public static final int SESSION_REFRESHER_TYPE_UAS = 2;
        public static final int SESSION_REFRESHER_TYPE_UNKNOWN = 0;
        public static final int SESSION_REFRESH_METHOD_INVITE = 0;
        public static final int SESSION_REFRESH_METHOD_UPDATE_PREFERRED = 1;

        public @interface AmrPayloadFormat {
        }

        public @interface ConferenceSubscribeType {
        }

        public @interface EvsEncodedBwType {
        }

        public @interface EvsOperationalMode {
        }

        public @interface EvsPrimaryModeBitRate {
        }

        public @interface SessionPrivacyType {
        }

        public @interface SessionRefreshMethod {
        }

        public @interface SessionRefresherType {
        }

        public @interface SrvccType {
        }

        private ImsVoice() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static PersistableBundle getDefaults() {
            PersistableBundle persistableBundle = new PersistableBundle();
            persistableBundle.putBoolean(KEY_CARRIER_VOLTE_ROAMING_AVAILABLE_BOOL, true);
            persistableBundle.putBoolean(KEY_INCLUDE_CALLER_ID_SERVICE_CODES_IN_SIP_INVITE_BOOL, false);
            persistableBundle.putBoolean(KEY_MULTIENDPOINT_SUPPORTED_BOOL, false);
            persistableBundle.putBoolean(KEY_SESSION_TIMER_SUPPORTED_BOOL, true);
            persistableBundle.putBoolean(KEY_OIP_SOURCE_FROM_HEADER_BOOL, false);
            persistableBundle.putBoolean(KEY_PRACK_SUPPORTED_FOR_18X_BOOL, false);
            persistableBundle.putBoolean(KEY_VOICE_QOS_PRECONDITION_SUPPORTED_BOOL, true);
            persistableBundle.putBoolean(KEY_VOICE_ON_DEFAULT_BEARER_SUPPORTED_BOOL, false);
            persistableBundle.putInt(KEY_SESSION_REFRESHER_TYPE_INT, 1);
            persistableBundle.putInt(KEY_SESSION_PRIVACY_TYPE_INT, 0);
            persistableBundle.putInt(KEY_SESSION_REFRESH_METHOD_INT, 1);
            persistableBundle.putInt(KEY_CONFERENCE_SUBSCRIBE_TYPE_INT, 1);
            persistableBundle.putInt(KEY_AUDIO_RTP_INACTIVITY_TIMER_MILLIS_INT, 20000);
            persistableBundle.putInt(KEY_AUDIO_RTCP_INACTIVITY_TIMER_MILLIS_INT, 20000);
            persistableBundle.putInt(KEY_DEDICATED_BEARER_WAIT_TIMER_MILLIS_INT, 8000);
            persistableBundle.putInt(KEY_RINGING_TIMER_MILLIS_INT, 90000);
            persistableBundle.putInt(KEY_RINGBACK_TIMER_MILLIS_INT, 90000);
            persistableBundle.putInt(KEY_MO_CALL_REQUEST_TIMEOUT_MILLIS_INT, 5000);
            persistableBundle.putInt(KEY_SESSION_EXPIRES_TIMER_SEC_INT, 1800);
            persistableBundle.putInt(KEY_MINIMUM_SESSION_EXPIRES_TIMER_SEC_INT, 90);
            persistableBundle.putInt(KEY_AUDIO_AS_BANDWIDTH_KBPS_INT, 41);
            persistableBundle.putInt(KEY_AUDIO_RS_BANDWIDTH_BPS_INT, 600);
            persistableBundle.putInt(KEY_AUDIO_RR_BANDWIDTH_BPS_INT, 2000);
            persistableBundle.putInt(KEY_VOICE_RTP_PACKET_LOSS_RATE_THRESHOLD_INT, 40);
            persistableBundle.putInt(KEY_VOICE_RTP_JITTER_THRESHOLD_MILLIS_INT, 120);
            persistableBundle.putLong(KEY_VOICE_RTP_INACTIVITY_TIME_THRESHOLD_MILLIS_LONG, 5000L);
            persistableBundle.putIntArray(KEY_AUDIO_INACTIVITY_CALL_END_REASONS_INT_ARRAY, new int[]{1, 2, 3, 0});
            persistableBundle.putIntArray(KEY_SRVCC_TYPE_INT_ARRAY, new int[]{0, 1, 2, 3});
            persistableBundle.putString(KEY_CONFERENCE_FACTORY_URI_STRING, "");
            PersistableBundle persistableBundle2 = new PersistableBundle();
            persistableBundle2.putIntArray(KEY_AMRWB_PAYLOAD_TYPE_INT_ARRAY, new int[]{97, 98});
            persistableBundle2.putIntArray(KEY_AMRNB_PAYLOAD_TYPE_INT_ARRAY, new int[]{99, 100});
            persistableBundle2.putIntArray(KEY_DTMFWB_PAYLOAD_TYPE_INT_ARRAY, new int[]{101});
            persistableBundle2.putIntArray(KEY_DTMFNB_PAYLOAD_TYPE_INT_ARRAY, new int[]{102});
            persistableBundle.putPersistableBundle(KEY_AUDIO_CODEC_CAPABILITY_PAYLOAD_TYPES_BUNDLE, persistableBundle2);
            PersistableBundle persistableBundle3 = new PersistableBundle();
            persistableBundle3.putPersistableBundle("97", new PersistableBundle());
            PersistableBundle persistableBundle4 = new PersistableBundle();
            persistableBundle4.putInt(KEY_AMR_CODEC_ATTRIBUTE_PAYLOAD_FORMAT_INT, 1);
            persistableBundle3.putPersistableBundle("98", persistableBundle4);
            persistableBundle.putPersistableBundle(KEY_AMRWB_PAYLOAD_DESCRIPTION_BUNDLE, persistableBundle3);
            PersistableBundle persistableBundle5 = new PersistableBundle();
            persistableBundle5.putPersistableBundle("99", new PersistableBundle());
            PersistableBundle persistableBundle6 = new PersistableBundle();
            persistableBundle6.putInt(KEY_AMR_CODEC_ATTRIBUTE_PAYLOAD_FORMAT_INT, 1);
            persistableBundle5.putPersistableBundle("100", persistableBundle6);
            persistableBundle.putPersistableBundle(KEY_AMRNB_PAYLOAD_DESCRIPTION_BUNDLE, persistableBundle5);
            return persistableBundle;
        }
    }

    public static final class ImsSms {
        public static final String KEY_PREFIX = "imssms.";
        public static final String KEY_SMS_CSFB_RETRY_ON_FAILURE_BOOL = "imssms.sms_csfb_retry_on_failure_bool";
        public static final String KEY_SMS_MAX_RETRY_COUNT_INT = "imssms.sms_max_retry_count_int";
        public static final String KEY_SMS_MAX_RETRY_OVER_IMS_COUNT_INT = "imssms.sms_max_retry_over_ims_count_int";
        public static final String KEY_SMS_OVER_IMS_FORMAT_INT = "imssms.sms_over_ims_format_int";
        public static final String KEY_SMS_OVER_IMS_SEND_RETRY_DELAY_MILLIS_INT = "imssms.sms_over_ims_send_retry_delay_millis_int";
        public static final String KEY_SMS_OVER_IMS_SUPPORTED_BOOL = "imssms.sms_over_ims_supported_bool";
        public static final String KEY_SMS_OVER_IMS_SUPPORTED_RATS_INT_ARRAY = "imssms.sms_over_ims_supported_rats_int_array";
        public static final String KEY_SMS_RP_CAUSE_VALUES_TO_FALLBACK_INT_ARRAY = "imssms.sms_rp_cause_values_to_fallback_int_array";
        public static final String KEY_SMS_RP_CAUSE_VALUES_TO_RETRY_OVER_IMS_INT_ARRAY = "imssms.sms_rp_cause_values_to_retry_over_ims_int_array";
        public static final String KEY_SMS_TR1_TIMER_MILLIS_INT = "imssms.sms_tr1_timer_millis_int";
        public static final String KEY_SMS_TR2_TIMER_MILLIS_INT = "imssms.sms_tr2_timer_millis_int";
        public static final int SMS_FORMAT_3GPP = 0;
        public static final int SMS_FORMAT_3GPP2 = 1;

        public @interface SmsFormat {
        }

        private ImsSms() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static PersistableBundle getDefaults() {
            PersistableBundle persistableBundle = new PersistableBundle();
            persistableBundle.putBoolean(KEY_SMS_OVER_IMS_SUPPORTED_BOOL, true);
            persistableBundle.putBoolean(KEY_SMS_CSFB_RETRY_ON_FAILURE_BOOL, true);
            persistableBundle.putInt(KEY_SMS_OVER_IMS_FORMAT_INT, 0);
            persistableBundle.putInt(KEY_SMS_MAX_RETRY_COUNT_INT, 3);
            persistableBundle.putInt(KEY_SMS_MAX_RETRY_OVER_IMS_COUNT_INT, 3);
            persistableBundle.putInt(KEY_SMS_OVER_IMS_SEND_RETRY_DELAY_MILLIS_INT, 2000);
            persistableBundle.putInt(KEY_SMS_TR1_TIMER_MILLIS_INT, SemDualAppManager.SepVersionInt.SEP_VER_13_0_INT);
            persistableBundle.putInt(KEY_SMS_TR2_TIMER_MILLIS_INT, EncodeConstants.BitRate.MM_BITRATE_10_HEVC_HD_30);
            persistableBundle.putIntArray(KEY_SMS_RP_CAUSE_VALUES_TO_RETRY_OVER_IMS_INT_ARRAY, new int[]{41});
            persistableBundle.putIntArray(KEY_SMS_RP_CAUSE_VALUES_TO_FALLBACK_INT_ARRAY, new int[]{1, 8, 10, 11, 21, 27, 28, 29, 30, 38, 42, 47, 50, 69, 81, 95, 96, 97, 98, 99, 111, 127});
            persistableBundle.putIntArray(KEY_SMS_OVER_IMS_SUPPORTED_RATS_INT_ARRAY, new int[]{3, 5});
            return persistableBundle;
        }
    }

    public static final class ImsRtt {
        public static final String KEY_PREFIX = "imsrtt.";
        public static final String KEY_RED_PAYLOAD_TYPE_INT = "imsrtt.red_payload_type_int";
        public static final String KEY_T140_PAYLOAD_TYPE_INT = "imsrtt.t140_payload_type_int";
        public static final String KEY_TEXT_AS_BANDWIDTH_KBPS_INT = "imsrtt.text_as_bandwidth_kbps_int";
        public static final String KEY_TEXT_CODEC_CAPABILITY_PAYLOAD_TYPES_BUNDLE = "imsrtt.text_codec_capability_payload_types_bundle";
        public static final String KEY_TEXT_ON_DEFAULT_BEARER_SUPPORTED_BOOL = "imsrtt.text_on_default_bearer_supported_bool";
        public static final String KEY_TEXT_QOS_PRECONDITION_SUPPORTED_BOOL = "imsrtt.text_qos_precondition_supported_bool";
        public static final String KEY_TEXT_RR_BANDWIDTH_BPS_INT = "imsrtt.text_rr_bandwidth_bps_int";
        public static final String KEY_TEXT_RS_BANDWIDTH_BPS_INT = "imsrtt.text_rs_bandwidth_bps_int";

        private ImsRtt() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static PersistableBundle getDefaults() {
            PersistableBundle persistableBundle = new PersistableBundle();
            persistableBundle.putBoolean(KEY_TEXT_ON_DEFAULT_BEARER_SUPPORTED_BOOL, false);
            persistableBundle.putBoolean(KEY_TEXT_QOS_PRECONDITION_SUPPORTED_BOOL, true);
            persistableBundle.putInt(KEY_TEXT_AS_BANDWIDTH_KBPS_INT, 4);
            persistableBundle.putInt(KEY_TEXT_RS_BANDWIDTH_BPS_INT, 100);
            persistableBundle.putInt(KEY_TEXT_RR_BANDWIDTH_BPS_INT, 300);
            PersistableBundle persistableBundle2 = new PersistableBundle();
            persistableBundle2.putInt(KEY_RED_PAYLOAD_TYPE_INT, 112);
            persistableBundle2.putInt(KEY_T140_PAYLOAD_TYPE_INT, 111);
            persistableBundle.putPersistableBundle(KEY_TEXT_CODEC_CAPABILITY_PAYLOAD_TYPES_BUNDLE, persistableBundle2);
            return persistableBundle;
        }
    }

    public static final class ImsEmergency {
        public static final int DOMAIN_CS = 1;
        public static final int DOMAIN_PS_3GPP = 2;
        public static final int DOMAIN_PS_NON_3GPP = 3;
        public static final String KEY_CROSS_STACK_REDIAL_TIMER_SEC_INT = "imsemergency.cross_stack_redial_timer_sec_int";
        public static final String KEY_EMERGENCY_CALLBACK_MODE_SUPPORTED_BOOL = "imsemergency.emergency_callback_mode_supported_bool";
        public static final String KEY_EMERGENCY_CALL_SETUP_TIMER_ON_CURRENT_NETWORK_SEC_INT = "imsemergency.emergency_call_setup_timer_on_current_network_sec_int";
        public static final String KEY_EMERGENCY_CDMA_PREFERRED_NUMBERS_STRING_ARRAY = "imsemergency.emergency_cdma_preferred_numbers_string_array";
        public static final String KEY_EMERGENCY_DOMAIN_PREFERENCE_INT_ARRAY = "imsemergency.emergency_domain_preference_int_array";
        public static final String KEY_EMERGENCY_DOMAIN_PREFERENCE_ROAMING_INT_ARRAY = "imsemergency.emergency_domain_preference_roaming_int_array";
        public static final String KEY_EMERGENCY_LTE_PREFERRED_AFTER_NR_FAILED_BOOL = "imsemergency.emergency_lte_preferred_after_nr_failed_bool";
        public static final String KEY_EMERGENCY_NETWORK_SCAN_TYPE_INT = "imsemergency.emergency_network_scan_type_int";
        public static final String KEY_EMERGENCY_OVER_CS_ROAMING_SUPPORTED_ACCESS_NETWORK_TYPES_INT_ARRAY = "imsemergency.emergency_over_cs_roaming_supported_access_network_types_int_array";
        public static final String KEY_EMERGENCY_OVER_CS_SUPPORTED_ACCESS_NETWORK_TYPES_INT_ARRAY = "imsemergency.emergency_over_cs_supported_access_network_types_int_array";
        public static final String KEY_EMERGENCY_OVER_IMS_ROAMING_SUPPORTED_3GPP_NETWORK_TYPES_INT_ARRAY = "imsemergency.emergency_over_ims_roaming_supported_3gpp_network_types_int_array";
        public static final String KEY_EMERGENCY_OVER_IMS_SUPPORTED_3GPP_NETWORK_TYPES_INT_ARRAY = "imsemergency.emergency_over_ims_supported_3gpp_network_types_int_array";
        public static final String KEY_EMERGENCY_OVER_IMS_SUPPORTED_RATS_INT_ARRAY = "imsemergency.emergency_over_ims_supported_rats_int_array";
        public static final String KEY_EMERGENCY_QOS_PRECONDITION_SUPPORTED_BOOL = "imsemergency.emergency_qos_precondition_supported_bool";
        public static final String KEY_EMERGENCY_REGISTRATION_TIMER_MILLIS_INT = "imsemergency.emergency_registration_timer_millis_int";
        public static final String KEY_EMERGENCY_REQUIRES_IMS_REGISTRATION_BOOL = "imsemergency.emergency_requires_ims_registration_bool";
        public static final String KEY_EMERGENCY_REQUIRES_VOLTE_ENABLED_BOOL = "imsemergency.emergency_requires_volte_enabled_bool";
        public static final String KEY_EMERGENCY_SCAN_TIMER_SEC_INT = "imsemergency.emergency_scan_timer_sec_int";
        public static final String KEY_EMERGENCY_VOWIFI_REQUIRES_CONDITION_INT = "imsemergency.emergency_vowifi_requires_condition_int";
        public static final String KEY_IMS_REASONINFO_CODE_TO_RETRY_EMERGENCY_INT_ARRAY = "imsemergency.ims_reasoninfo_code_to_retry_emergency_int_array";
        public static final String KEY_MAXIMUM_CELLULAR_SEARCH_TIMER_SEC_INT = "imsemergency.maximum_cellular_search_timer_sec_int";
        public static final String KEY_MAXIMUM_NUMBER_OF_EMERGENCY_TRIES_OVER_VOWIFI_INT = "imsemergency.maximum_number_of_emergency_tries_over_vowifi_int";
        public static final String KEY_PREFER_IMS_EMERGENCY_WHEN_VOICE_CALLS_ON_CS_BOOL = "imsemergency.prefer_ims_emergency_when_voice_calls_on_cs_bool";
        public static final String KEY_PREFIX = "imsemergency.";
        public static final String KEY_QUICK_CROSS_STACK_REDIAL_TIMER_SEC_INT = "imsemergency.quick_cross_stack_redial_timer_sec_int";
        public static final String KEY_REFRESH_GEOLOCATION_TIMEOUT_MILLIS_INT = "imsemergency.refresh_geolocation_timeout_millis_int";
        public static final String KEY_RETRY_EMERGENCY_ON_IMS_PDN_BOOL = "imsemergency.retry_emergency_on_ims_pdn_bool";
        public static final String KEY_SCAN_LIMITED_SERVICE_AFTER_VOLTE_FAILURE_BOOL = "imsemergency.scan_limited_service_after_volte_failure_bool";
        public static final String KEY_START_QUICK_CROSS_STACK_REDIAL_TIMER_WHEN_REGISTERED_BOOL = "imsemergency.start_quick_cross_stack_redial_timer_when_registered_bool";
        public static final int REDIAL_TIMER_DISABLED = 0;
        public static final int SCAN_TYPE_FULL_SERVICE = 1;
        public static final int SCAN_TYPE_FULL_SERVICE_FOLLOWED_BY_LIMITED_SERVICE = 2;
        public static final int SCAN_TYPE_NO_PREFERENCE = 0;
        public static final int VOWIFI_REQUIRES_NONE = 0;
        public static final int VOWIFI_REQUIRES_SETTING_ENABLED = 1;
        public static final int VOWIFI_REQUIRES_VALID_EID = 2;

        public @interface EmergencyDomain {
        }

        public @interface EmergencyScanType {
        }

        public @interface VoWiFiRequires {
        }

        private ImsEmergency() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static PersistableBundle getDefaults() {
            PersistableBundle persistableBundle = new PersistableBundle();
            persistableBundle.putBoolean(KEY_RETRY_EMERGENCY_ON_IMS_PDN_BOOL, false);
            persistableBundle.putBoolean(KEY_EMERGENCY_CALLBACK_MODE_SUPPORTED_BOOL, false);
            persistableBundle.putBoolean(KEY_EMERGENCY_QOS_PRECONDITION_SUPPORTED_BOOL, true);
            persistableBundle.putIntArray(KEY_EMERGENCY_OVER_IMS_SUPPORTED_RATS_INT_ARRAY, new int[]{3, 5});
            persistableBundle.putInt(KEY_EMERGENCY_REGISTRATION_TIMER_MILLIS_INT, 10000);
            persistableBundle.putInt(KEY_REFRESH_GEOLOCATION_TIMEOUT_MILLIS_INT, 5000);
            persistableBundle.putIntArray(KEY_EMERGENCY_OVER_IMS_SUPPORTED_3GPP_NETWORK_TYPES_INT_ARRAY, new int[]{3});
            persistableBundle.putIntArray(KEY_EMERGENCY_OVER_IMS_ROAMING_SUPPORTED_3GPP_NETWORK_TYPES_INT_ARRAY, new int[]{3});
            persistableBundle.putIntArray(KEY_EMERGENCY_OVER_CS_SUPPORTED_ACCESS_NETWORK_TYPES_INT_ARRAY, new int[]{2, 1});
            persistableBundle.putIntArray(KEY_EMERGENCY_OVER_CS_ROAMING_SUPPORTED_ACCESS_NETWORK_TYPES_INT_ARRAY, new int[]{2, 1});
            persistableBundle.putIntArray(KEY_EMERGENCY_DOMAIN_PREFERENCE_INT_ARRAY, new int[]{2, 1, 3});
            persistableBundle.putIntArray(KEY_EMERGENCY_DOMAIN_PREFERENCE_ROAMING_INT_ARRAY, new int[]{2, 1, 3});
            persistableBundle.putBoolean(KEY_PREFER_IMS_EMERGENCY_WHEN_VOICE_CALLS_ON_CS_BOOL, false);
            persistableBundle.putInt(KEY_EMERGENCY_VOWIFI_REQUIRES_CONDITION_INT, 0);
            persistableBundle.putInt(KEY_MAXIMUM_NUMBER_OF_EMERGENCY_TRIES_OVER_VOWIFI_INT, 1);
            persistableBundle.putInt(KEY_EMERGENCY_SCAN_TIMER_SEC_INT, 10);
            persistableBundle.putInt(KEY_MAXIMUM_CELLULAR_SEARCH_TIMER_SEC_INT, 0);
            persistableBundle.putInt(KEY_EMERGENCY_NETWORK_SCAN_TYPE_INT, 0);
            persistableBundle.putInt(KEY_EMERGENCY_CALL_SETUP_TIMER_ON_CURRENT_NETWORK_SEC_INT, 0);
            persistableBundle.putBoolean(KEY_EMERGENCY_REQUIRES_IMS_REGISTRATION_BOOL, false);
            persistableBundle.putBoolean(KEY_EMERGENCY_LTE_PREFERRED_AFTER_NR_FAILED_BOOL, false);
            persistableBundle.putBoolean(KEY_EMERGENCY_REQUIRES_VOLTE_ENABLED_BOOL, false);
            persistableBundle.putStringArray(KEY_EMERGENCY_CDMA_PREFERRED_NUMBERS_STRING_ARRAY, new String[0]);
            persistableBundle.putInt(KEY_CROSS_STACK_REDIAL_TIMER_SEC_INT, 120);
            persistableBundle.putInt(KEY_QUICK_CROSS_STACK_REDIAL_TIMER_SEC_INT, 0);
            persistableBundle.putBoolean(KEY_START_QUICK_CROSS_STACK_REDIAL_TIMER_WHEN_REGISTERED_BOOL, true);
            persistableBundle.putBoolean(KEY_SCAN_LIMITED_SERVICE_AFTER_VOLTE_FAILURE_BOOL, false);
            persistableBundle.putIntArray(KEY_IMS_REASONINFO_CODE_TO_RETRY_EMERGENCY_INT_ARRAY, new int[0]);
            return persistableBundle;
        }
    }

    public static final class ImsVt {
        public static final String KEY_H264_PAYLOAD_DESCRIPTION_BUNDLE = "imsvt.h264_payload_description_bundle";
        public static final String KEY_H264_PAYLOAD_TYPE_INT_ARRAY = "imsvt.h264_payload_type_int_array";
        public static final String KEY_H264_VIDEO_CODEC_ATTRIBUTE_PROFILE_LEVEL_ID_STRING = "imsvt.h264_video_codec_attribute_profile_level_id_string";
        public static final String KEY_PREFIX = "imsvt.";
        public static final String KEY_VIDEO_AS_BANDWIDTH_KBPS_INT = "imsvt.video_as_bandwidth_kbps_int";
        public static final String KEY_VIDEO_CODEC_ATTRIBUTE_FRAME_RATE_INT = "imsvt.video_codec_attribute_frame_rate_int";
        public static final String KEY_VIDEO_CODEC_ATTRIBUTE_PACKETIZATION_MODE_INT = "imsvt.video_codec_attribute_packetization_mode_int";
        public static final String KEY_VIDEO_CODEC_ATTRIBUTE_RESOLUTION_INT_ARRAY = "imsvt.video_codec_attribute_resolution_int_array";
        public static final String KEY_VIDEO_CODEC_CAPABILITY_PAYLOAD_TYPES_BUNDLE = "imsvt.video_codec_capability_payload_types_bundle";
        public static final String KEY_VIDEO_ON_DEFAULT_BEARER_SUPPORTED_BOOL = "imsvt.video_on_default_bearer_supported_bool";
        public static final String KEY_VIDEO_QOS_PRECONDITION_SUPPORTED_BOOL = "imsvt.video_qos_precondition_supported_bool";
        public static final String KEY_VIDEO_RR_BANDWIDTH_BPS_INT = "imsvt.video_rr_bandwidth_bps_int";
        public static final String KEY_VIDEO_RS_BANDWIDTH_BPS_INT = "imsvt.video_rs_bandwidth_bps_int";
        public static final String KEY_VIDEO_RTCP_INACTIVITY_TIMER_MILLIS_INT = "imsvt.video_rtcp_inactivity_timer_millis_int";
        public static final String KEY_VIDEO_RTP_DSCP_INT = "imsvt.video_rtp_dscp_int";
        public static final String KEY_VIDEO_RTP_INACTIVITY_TIMER_MILLIS_INT = "imsvt.video_rtp_inactivity_timer_millis_int";

        private ImsVt() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static PersistableBundle getDefaults() {
            PersistableBundle persistableBundle = new PersistableBundle();
            persistableBundle.putBoolean(KEY_VIDEO_ON_DEFAULT_BEARER_SUPPORTED_BOOL, false);
            persistableBundle.putBoolean(KEY_VIDEO_QOS_PRECONDITION_SUPPORTED_BOOL, true);
            persistableBundle.putInt(KEY_VIDEO_RTP_INACTIVITY_TIMER_MILLIS_INT, 0);
            persistableBundle.putInt(KEY_VIDEO_RTCP_INACTIVITY_TIMER_MILLIS_INT, 0);
            persistableBundle.putInt(KEY_VIDEO_AS_BANDWIDTH_KBPS_INT, EncodeConstants.Resolution.MM_360_EXPORT_HEIGHT_960);
            persistableBundle.putInt(KEY_VIDEO_RS_BANDWIDTH_BPS_INT, 8000);
            persistableBundle.putInt(KEY_VIDEO_RR_BANDWIDTH_BPS_INT, 6000);
            persistableBundle.putInt(KEY_VIDEO_RTP_DSCP_INT, 40);
            PersistableBundle persistableBundle2 = new PersistableBundle();
            persistableBundle2.putIntArray(KEY_H264_PAYLOAD_TYPE_INT_ARRAY, new int[]{99, 100});
            persistableBundle.putPersistableBundle(KEY_VIDEO_CODEC_CAPABILITY_PAYLOAD_TYPES_BUNDLE, persistableBundle2);
            PersistableBundle persistableBundle3 = new PersistableBundle();
            persistableBundle3.putPersistableBundle("99", new PersistableBundle());
            PersistableBundle persistableBundle4 = new PersistableBundle();
            persistableBundle4.putInt(KEY_VIDEO_CODEC_ATTRIBUTE_PACKETIZATION_MODE_INT, 0);
            persistableBundle3.putPersistableBundle("100", persistableBundle4);
            persistableBundle.putPersistableBundle(KEY_H264_PAYLOAD_DESCRIPTION_BUNDLE, persistableBundle3);
            return persistableBundle;
        }
    }

    public static final class ImsWfc {
        public static final String KEY_EMERGENCY_CALL_OVER_EMERGENCY_PDN_BOOL = "imswfc.emergency_call_over_emergency_pdn_bool";
        public static final String KEY_PIDF_SHORT_CODE_STRING_ARRAY = "imswfc.pidf_short_code_string_array";
        public static final String KEY_PREFIX = "imswfc.";

        private ImsWfc() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static PersistableBundle getDefaults() {
            PersistableBundle persistableBundle = new PersistableBundle();
            persistableBundle.putBoolean(KEY_EMERGENCY_CALL_OVER_EMERGENCY_PDN_BOOL, false);
            persistableBundle.putStringArray(KEY_PIDF_SHORT_CODE_STRING_ARRAY, new String[0]);
            return persistableBundle;
        }
    }

    public static final class ImsSs {
        public static final int CALL_WAITING_SYNC_FIRST_CHANGE = 3;
        public static final int CALL_WAITING_SYNC_FIRST_POWER_UP = 2;
        public static final int CALL_WAITING_SYNC_IMS_ONLY = 4;
        public static final int CALL_WAITING_SYNC_MAX = 4;
        public static final int CALL_WAITING_SYNC_NONE = 0;
        public static final int CALL_WAITING_SYNC_USER_CHANGE = 1;
        public static final String KEY_NETWORK_INITIATED_USSD_OVER_IMS_SUPPORTED_BOOL = "imsss.network_initiated_ussd_over_ims_supported_bool";
        public static final String KEY_PREFIX = "imsss.";
        public static final String KEY_TERMINAL_BASED_CALL_WAITING_DEFAULT_ENABLED_BOOL = "imsss.terminal_based_call_waiting_default_enabled_bool";
        public static final String KEY_TERMINAL_BASED_CALL_WAITING_SYNC_TYPE_INT = "imsss.terminal_based_call_waiting_sync_type_int";
        public static final String KEY_USE_CSFB_ON_XCAP_OVER_UT_FAILURE_BOOL = "imsss.use_csfb_on_xcap_over_ut_failure_bool";
        public static final String KEY_UT_AS_SERVER_FQDN_STRING = "imsss.ut_as_server_fqdn_string";
        public static final String KEY_UT_AS_SERVER_PORT_INT = "imsss.ut_as_server_port_int";
        public static final String KEY_UT_IPTYPE_HOME_INT = "imsss.ut_iptype_home_int";
        public static final String KEY_UT_IPTYPE_ROAMING_INT = "imsss.ut_iptype_roaming_int";
        public static final String KEY_UT_REQUIRES_IMS_REGISTRATION_BOOL = "imsss.ut_requires_ims_registration_bool";
        public static final String KEY_UT_SERVER_BASED_SERVICES_INT_ARRAY = "imsss.ut_server_based_services_int_array";
        public static final String KEY_UT_SUPPORTED_WHEN_PS_DATA_OFF_BOOL = "imsss.ut_supported_when_ps_data_off_bool";
        public static final String KEY_UT_SUPPORTED_WHEN_ROAMING_BOOL = "imsss.ut_supported_when_roaming_bool";
        public static final String KEY_UT_TERMINAL_BASED_SERVICES_INT_ARRAY = "imsss.ut_terminal_based_services_int_array";
        public static final String KEY_UT_TRANSPORT_TYPE_INT = "imsss.ut_transport_type_int";
        public static final String KEY_XCAP_OVER_UT_SUPPORTED_RATS_INT_ARRAY = "imsss.xcap_over_ut_supported_rats_int_array";
        public static final int SUPPLEMENTARY_SERVICE_CB_ACR = 20;
        public static final int SUPPLEMENTARY_SERVICE_CB_ALL = 12;
        public static final int SUPPLEMENTARY_SERVICE_CB_BAIC = 18;
        public static final int SUPPLEMENTARY_SERVICE_CB_BAOC = 14;
        public static final int SUPPLEMENTARY_SERVICE_CB_BIC_ROAM = 19;
        public static final int SUPPLEMENTARY_SERVICE_CB_BIL = 21;
        public static final int SUPPLEMENTARY_SERVICE_CB_BOIC = 15;
        public static final int SUPPLEMENTARY_SERVICE_CB_BOIC_EXHC = 16;
        public static final int SUPPLEMENTARY_SERVICE_CB_IBS = 17;
        public static final int SUPPLEMENTARY_SERVICE_CB_OBS = 13;
        public static final int SUPPLEMENTARY_SERVICE_CF_ALL = 1;
        public static final int SUPPLEMENTARY_SERVICE_CF_ALL_CONDITONAL_FORWARDING = 3;
        public static final int SUPPLEMENTARY_SERVICE_CF_CFB = 4;
        public static final int SUPPLEMENTARY_SERVICE_CF_CFNL = 7;
        public static final int SUPPLEMENTARY_SERVICE_CF_CFNRC = 6;
        public static final int SUPPLEMENTARY_SERVICE_CF_CFNRY = 5;
        public static final int SUPPLEMENTARY_SERVICE_CF_CFU = 2;
        public static final int SUPPLEMENTARY_SERVICE_CW = 0;
        public static final int SUPPLEMENTARY_SERVICE_IDENTIFICATION_OIP = 8;
        public static final int SUPPLEMENTARY_SERVICE_IDENTIFICATION_OIR = 10;
        public static final int SUPPLEMENTARY_SERVICE_IDENTIFICATION_TIP = 9;
        public static final int SUPPLEMENTARY_SERVICE_IDENTIFICATION_TIR = 11;

        public @interface CwSyncType {
        }

        public @interface SsType {
        }

        private ImsSs() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static PersistableBundle getDefaults() {
            PersistableBundle persistableBundle = new PersistableBundle();
            persistableBundle.putBoolean(KEY_UT_REQUIRES_IMS_REGISTRATION_BOOL, false);
            persistableBundle.putBoolean(KEY_USE_CSFB_ON_XCAP_OVER_UT_FAILURE_BOOL, true);
            persistableBundle.putBoolean(KEY_UT_SUPPORTED_WHEN_PS_DATA_OFF_BOOL, true);
            persistableBundle.putBoolean(KEY_NETWORK_INITIATED_USSD_OVER_IMS_SUPPORTED_BOOL, true);
            persistableBundle.putBoolean(KEY_UT_SUPPORTED_WHEN_ROAMING_BOOL, true);
            persistableBundle.putInt(KEY_UT_IPTYPE_HOME_INT, 2);
            persistableBundle.putInt(KEY_UT_IPTYPE_ROAMING_INT, 2);
            persistableBundle.putInt(KEY_UT_AS_SERVER_PORT_INT, 80);
            persistableBundle.putInt(KEY_UT_TRANSPORT_TYPE_INT, 1);
            persistableBundle.putIntArray(KEY_UT_SERVER_BASED_SERVICES_INT_ARRAY, new int[]{0, 1, 2, 6, 3, 4, 5, 7, 8, 9, 10, 11, 12, 13, 17, 14, 15, 16, 18, 19, 20, 21});
            persistableBundle.putIntArray(KEY_UT_TERMINAL_BASED_SERVICES_INT_ARRAY, new int[0]);
            persistableBundle.putIntArray(KEY_XCAP_OVER_UT_SUPPORTED_RATS_INT_ARRAY, new int[]{3, 5, 6});
            persistableBundle.putString(KEY_UT_AS_SERVER_FQDN_STRING, "");
            persistableBundle.putBoolean(KEY_TERMINAL_BASED_CALL_WAITING_DEFAULT_ENABLED_BOOL, true);
            persistableBundle.putInt(KEY_TERMINAL_BASED_CALL_WAITING_SYNC_TYPE_INT, 3);
            return persistableBundle;
        }
    }

    public static final class Bsf {
        public static final String KEY_BSF_SERVER_FQDN_STRING = "bsf.bsf_server_fqdn_string";
        public static final String KEY_BSF_SERVER_PORT_INT = "bsf.bsf_server_port_int";
        public static final String KEY_BSF_TRANSPORT_TYPE_INT = "bsf.bsf_transport_type_int";
        public static final String KEY_PREFIX = "bsf.";

        private Bsf() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static PersistableBundle getDefaults() {
            PersistableBundle persistableBundle = new PersistableBundle();
            persistableBundle.putInt(KEY_BSF_SERVER_PORT_INT, 80);
            persistableBundle.putInt(KEY_BSF_TRANSPORT_TYPE_INT, 1);
            persistableBundle.putString(KEY_BSF_SERVER_FQDN_STRING, "");
            return persistableBundle;
        }
    }

    public static final class Iwlan {
        public static final int AUTHENTICATION_METHOD_CERT = 1;
        public static final int AUTHENTICATION_METHOD_EAP_ONLY = 0;
        public static final int EPDG_ADDRESS_CELLULAR_LOC = 3;
        public static final int EPDG_ADDRESS_IPV4_ONLY = 2;
        public static final int EPDG_ADDRESS_IPV4_PREFERRED = 0;
        public static final int EPDG_ADDRESS_IPV6_ONLY = 3;
        public static final int EPDG_ADDRESS_IPV6_PREFERRED = 1;
        public static final int EPDG_ADDRESS_PCO = 2;
        public static final int EPDG_ADDRESS_PLMN = 1;
        public static final int EPDG_ADDRESS_STATIC = 0;
        public static final int EPDG_ADDRESS_SYSTEM_PREFERRED = 4;
        public static final int EPDG_ADDRESS_VISITED_COUNTRY = 4;
        public static final int EPDG_PLMN_EHPLMN_ALL = 2;
        public static final int EPDG_PLMN_EHPLMN_FIRST = 3;
        public static final int EPDG_PLMN_HPLMN = 1;
        public static final int EPDG_PLMN_RPLMN = 0;
        public static final int ID_TYPE_FQDN = 2;
        public static final int ID_TYPE_KEY_ID = 11;
        public static final int ID_TYPE_RFC822_ADDR = 3;
        public static final String KEY_ADD_KE_TO_CHILD_SESSION_REKEY_BOOL = "iwlan.add_ke_to_child_session_rekey_bool";
        public static final String KEY_CHILD_SA_REKEY_HARD_TIMER_SEC_INT = "iwlan.child_sa_rekey_hard_timer_sec_int";
        public static final String KEY_CHILD_SA_REKEY_SOFT_TIMER_SEC_INT = "iwlan.child_sa_rekey_soft_timer_sec_int";
        public static final String KEY_CHILD_SESSION_AES_CBC_KEY_SIZE_INT_ARRAY = "iwlan.child_session_aes_cbc_key_size_int_array";
        public static final String KEY_CHILD_SESSION_AES_CTR_KEY_SIZE_INT_ARRAY = "iwlan.child_session_aes_ctr_key_size_int_array";
        public static final String KEY_CHILD_SESSION_AES_GCM_KEY_SIZE_INT_ARRAY = "iwlan.child_session_aes_gcm_key_size_int_array";
        public static final String KEY_DIFFIE_HELLMAN_GROUPS_INT_ARRAY = "iwlan.diffie_hellman_groups_int_array";
        public static final String KEY_DPD_TIMER_SEC_INT = "iwlan.dpd_timer_sec_int";
        public static final String KEY_EPDG_ADDRESS_IP_TYPE_PREFERENCE_INT = "iwlan.epdg_address_ip_type_preference_int";
        public static final String KEY_EPDG_ADDRESS_PRIORITY_INT_ARRAY = "iwlan.epdg_address_priority_int_array";
        public static final String KEY_EPDG_AUTHENTICATION_METHOD_INT = "iwlan.epdg_authentication_method_int";
        public static final String KEY_EPDG_PCO_ID_IPV4_INT = "iwlan.epdg_pco_id_ipv4_int";
        public static final String KEY_EPDG_PCO_ID_IPV6_INT = "iwlan.epdg_pco_id_ipv6_int";
        public static final String KEY_EPDG_PLMN_PRIORITY_INT_ARRAY = "iwlan.epdg_plmn_priority_int_array";
        public static final String KEY_EPDG_STATIC_ADDRESS_ROAMING_STRING = "iwlan.epdg_static_address_roaming_string";
        public static final String KEY_EPDG_STATIC_ADDRESS_STRING = "iwlan.epdg_static_address_string";
        public static final String KEY_IKE_LOCAL_ID_TYPE_INT = "iwlan.ike_local_id_type_int";
        public static final String KEY_IKE_REKEY_HARD_TIMER_SEC_INT = "iwlan.ike_rekey_hard_timer_in_sec";
        public static final String KEY_IKE_REKEY_SOFT_TIMER_SEC_INT = "iwlan.ike_rekey_soft_timer_sec_int";
        public static final String KEY_IKE_REMOTE_ID_TYPE_INT = "iwlan.ike_remote_id_type_int";
        public static final String KEY_IKE_SESSION_AES_CBC_KEY_SIZE_INT_ARRAY = "iwlan.ike_session_encryption_aes_cbc_key_size_int_array";
        public static final String KEY_IKE_SESSION_AES_CTR_KEY_SIZE_INT_ARRAY = "iwlan.ike_session_encryption_aes_ctr_key_size_int_array";
        public static final String KEY_IKE_SESSION_AES_GCM_KEY_SIZE_INT_ARRAY = "iwlan.ike_session_encryption_aes_gcm_key_size_int_array";
        public static final String KEY_MAX_RETRIES_INT = "iwlan.max_retries_int";
        public static final String KEY_MCC_MNCS_STRING_ARRAY = "iwlan.mcc_mncs_string_array";
        public static final String KEY_NATT_KEEP_ALIVE_TIMER_SEC_INT = "iwlan.natt_keep_alive_timer_sec_int";
        public static final String KEY_PREFIX = "iwlan.";
        public static final String KEY_RETRANSMIT_TIMER_MSEC_INT_ARRAY = "iwlan.retransmit_timer_sec_int_array";
        public static final String KEY_SUPPORTED_CHILD_SESSION_AEAD_ALGORITHMS_INT_ARRAY = "iwlan.supported_child_session_aead_algorithms_int_array";
        public static final String KEY_SUPPORTED_CHILD_SESSION_ENCRYPTION_ALGORITHMS_INT_ARRAY = "iwlan.supported_child_session_encryption_algorithms_int_array";
        public static final String KEY_SUPPORTED_IKE_SESSION_AEAD_ALGORITHMS_INT_ARRAY = "iwlan.supported_ike_session_aead_algorithms_int_array";
        public static final String KEY_SUPPORTED_IKE_SESSION_ENCRYPTION_ALGORITHMS_INT_ARRAY = "iwlan.supported_ike_session_encryption_algorithms_int_array";
        public static final String KEY_SUPPORTED_INTEGRITY_ALGORITHMS_INT_ARRAY = "iwlan.supported_integrity_algorithms_int_array";
        public static final String KEY_SUPPORTED_PRF_ALGORITHMS_INT_ARRAY = "iwlan.supported_prf_algorithms_int_array";
        public static final String KEY_SUPPORTS_CHILD_SESSION_MULTIPLE_SA_PROPOSALS_BOOL = "iwlan.supports_child_session_multiple_sa_proposals_bool";
        public static final String KEY_SUPPORTS_EAP_AKA_FAST_REAUTH_BOOL = "iwlan.supports_eap_aka_fast_reauth_bool";
        public static final String KEY_SUPPORTS_IKE_SESSION_MULTIPLE_SA_PROPOSALS_BOOL = "iwlan.supports_ike_session_multiple_sa_proposals_bool";

        public @interface AuthenticationMethodType {
        }

        public @interface EpdgAddressIpPreference {
        }

        public @interface EpdgAddressPlmnType {
        }

        public @interface EpdgAddressType {
        }

        public @interface IkeIdType {
        }

        private Iwlan() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static PersistableBundle getDefaults() {
            PersistableBundle persistableBundle = new PersistableBundle();
            persistableBundle.putInt(KEY_IKE_REKEY_SOFT_TIMER_SEC_INT, 7200);
            persistableBundle.putInt(KEY_IKE_REKEY_HARD_TIMER_SEC_INT, 14400);
            persistableBundle.putInt(KEY_CHILD_SA_REKEY_SOFT_TIMER_SEC_INT, 3600);
            persistableBundle.putInt(KEY_CHILD_SA_REKEY_HARD_TIMER_SEC_INT, 7200);
            persistableBundle.putBoolean(KEY_SUPPORTS_IKE_SESSION_MULTIPLE_SA_PROPOSALS_BOOL, false);
            persistableBundle.putBoolean(KEY_SUPPORTS_CHILD_SESSION_MULTIPLE_SA_PROPOSALS_BOOL, false);
            persistableBundle.putIntArray(KEY_RETRANSMIT_TIMER_MSEC_INT_ARRAY, new int[]{500, 1000, 2000, 4000, 8000});
            persistableBundle.putInt(KEY_DPD_TIMER_SEC_INT, 120);
            persistableBundle.putInt(KEY_MAX_RETRIES_INT, 3);
            persistableBundle.putIntArray(KEY_DIFFIE_HELLMAN_GROUPS_INT_ARRAY, new int[]{2, 5, 14});
            persistableBundle.putIntArray(KEY_SUPPORTED_IKE_SESSION_ENCRYPTION_ALGORITHMS_INT_ARRAY, new int[]{12});
            persistableBundle.putIntArray(KEY_SUPPORTED_IKE_SESSION_AEAD_ALGORITHMS_INT_ARRAY, new int[0]);
            persistableBundle.putIntArray(KEY_SUPPORTED_CHILD_SESSION_ENCRYPTION_ALGORITHMS_INT_ARRAY, new int[]{12});
            persistableBundle.putIntArray(KEY_SUPPORTED_CHILD_SESSION_AEAD_ALGORITHMS_INT_ARRAY, new int[0]);
            persistableBundle.putIntArray(KEY_SUPPORTED_INTEGRITY_ALGORITHMS_INT_ARRAY, new int[]{5, 2, 12, 13, 14});
            persistableBundle.putIntArray(KEY_SUPPORTED_PRF_ALGORITHMS_INT_ARRAY, new int[]{2, 4, 5, 6, 7});
            persistableBundle.putInt(KEY_EPDG_AUTHENTICATION_METHOD_INT, 0);
            persistableBundle.putString(KEY_EPDG_STATIC_ADDRESS_STRING, "");
            persistableBundle.putString(KEY_EPDG_STATIC_ADDRESS_ROAMING_STRING, "");
            persistableBundle.putInt(KEY_NATT_KEEP_ALIVE_TIMER_SEC_INT, 20);
            persistableBundle.putIntArray(KEY_IKE_SESSION_AES_CBC_KEY_SIZE_INT_ARRAY, new int[]{128, 192, 256});
            persistableBundle.putIntArray(KEY_CHILD_SESSION_AES_CBC_KEY_SIZE_INT_ARRAY, new int[]{128, 192, 256});
            persistableBundle.putIntArray(KEY_IKE_SESSION_AES_CTR_KEY_SIZE_INT_ARRAY, new int[]{128, 192, 256});
            persistableBundle.putIntArray(KEY_CHILD_SESSION_AES_CTR_KEY_SIZE_INT_ARRAY, new int[]{128, 192, 256});
            persistableBundle.putIntArray(KEY_IKE_SESSION_AES_GCM_KEY_SIZE_INT_ARRAY, new int[0]);
            persistableBundle.putIntArray(KEY_CHILD_SESSION_AES_GCM_KEY_SIZE_INT_ARRAY, new int[0]);
            persistableBundle.putIntArray(KEY_EPDG_ADDRESS_PRIORITY_INT_ARRAY, new int[]{1, 0});
            persistableBundle.putIntArray(KEY_EPDG_PLMN_PRIORITY_INT_ARRAY, new int[]{0, 1, 2});
            persistableBundle.putStringArray(KEY_MCC_MNCS_STRING_ARRAY, new String[0]);
            persistableBundle.putInt(KEY_IKE_LOCAL_ID_TYPE_INT, 3);
            persistableBundle.putInt(KEY_IKE_REMOTE_ID_TYPE_INT, 2);
            persistableBundle.putBoolean(KEY_ADD_KE_TO_CHILD_SESSION_REKEY_BOOL, false);
            persistableBundle.putInt(KEY_EPDG_PCO_ID_IPV6_INT, 0);
            persistableBundle.putInt(KEY_EPDG_PCO_ID_IPV4_INT, 0);
            persistableBundle.putBoolean(KEY_SUPPORTS_EAP_AKA_FAST_REAUTH_BOOL, false);
            persistableBundle.putInt(KEY_EPDG_ADDRESS_IP_TYPE_PREFERENCE_INT, 0);
            return persistableBundle;
        }
    }

    static {
        PersistableBundle persistableBundle = new PersistableBundle();
        sDefaults = persistableBundle;
        persistableBundle.putString(KEY_CARRIER_CONFIG_VERSION_STRING, "");
        persistableBundle.putBoolean(KEY_ALLOW_HOLD_IN_IMS_CALL_BOOL, true);
        persistableBundle.putBoolean(KEY_CARRIER_ALLOW_DEFLECT_IMS_CALL_BOOL, false);
        persistableBundle.putBoolean(KEY_CARRIER_ALLOW_TRANSFER_IMS_CALL_BOOL, false);
        persistableBundle.putBoolean(KEY_ALWAYS_PLAY_REMOTE_HOLD_TONE_BOOL, false);
        persistableBundle.putBoolean(KEY_AUTO_RETRY_FAILED_WIFI_EMERGENCY_CALL, false);
        persistableBundle.putBoolean(KEY_ADDITIONAL_CALL_SETTING_BOOL, true);
        persistableBundle.putBoolean(KEY_ALLOW_EMERGENCY_NUMBERS_IN_CALL_LOG_BOOL, false);
        persistableBundle.putStringArray(KEY_UNLOGGABLE_NUMBERS_STRING_ARRAY, null);
        persistableBundle.putBoolean(KEY_ALLOW_LOCAL_DTMF_TONES_BOOL, true);
        persistableBundle.putBoolean(KEY_PLAY_CALL_RECORDING_TONE_BOOL, false);
        persistableBundle.putBoolean(KEY_APN_EXPAND_BOOL, true);
        persistableBundle.putBoolean(KEY_AUTO_RETRY_ENABLED_BOOL, false);
        persistableBundle.putBoolean(KEY_CARRIER_SETTINGS_ENABLE_BOOL, false);
        persistableBundle.putBoolean(KEY_CARRIER_VOLTE_AVAILABLE_BOOL, false);
        persistableBundle.putBoolean(KEY_CARRIER_VT_AVAILABLE_BOOL, false);
        persistableBundle.putInt(KEY_CARRIER_USSD_METHOD_INT, 0);
        persistableBundle.putBoolean(KEY_VOLTE_5G_LIMITED_ALERT_DIALOG_BOOL, false);
        persistableBundle.putBoolean(KEY_NOTIFY_HANDOVER_VIDEO_FROM_WIFI_TO_LTE_BOOL, false);
        persistableBundle.putBoolean(KEY_ALLOW_MERGING_RTT_CALLS_BOOL, false);
        persistableBundle.putBoolean(KEY_NOTIFY_HANDOVER_VIDEO_FROM_LTE_TO_WIFI_BOOL, false);
        persistableBundle.putBoolean(KEY_SUPPORT_DOWNGRADE_VT_TO_AUDIO_BOOL, true);
        persistableBundle.putString(KEY_DEFAULT_VM_NUMBER_STRING, "");
        persistableBundle.putString(KEY_DEFAULT_VM_NUMBER_ROAMING_STRING, "");
        persistableBundle.putString(KEY_DEFAULT_VM_NUMBER_ROAMING_AND_IMS_UNREGISTERED_STRING, "");
        persistableBundle.putBoolean(KEY_CONFIG_TELEPHONY_USE_OWN_NUMBER_FOR_VOICEMAIL_BOOL, false);
        persistableBundle.putBoolean(KEY_IGNORE_DATA_ENABLED_CHANGED_FOR_VIDEO_CALLS, true);
        persistableBundle.putBoolean(KEY_VILTE_DATA_IS_METERED_BOOL, true);
        persistableBundle.putBoolean(KEY_CARRIER_WFC_IMS_AVAILABLE_BOOL, false);
        persistableBundle.putBoolean(KEY_CARRIER_CROSS_SIM_IMS_AVAILABLE_BOOL, false);
        persistableBundle.putBoolean(KEY_ENABLE_CROSS_SIM_CALLING_ON_OPPORTUNISTIC_DATA_BOOL, false);
        persistableBundle.putBoolean(KEY_CARRIER_WFC_SUPPORTS_WIFI_ONLY_BOOL, false);
        persistableBundle.putBoolean(KEY_CARRIER_DEFAULT_WFC_IMS_ENABLED_BOOL, false);
        persistableBundle.putBoolean(KEY_CARRIER_DEFAULT_WFC_IMS_ROAMING_ENABLED_BOOL, false);
        persistableBundle.putBoolean(KEY_CARRIER_PROMOTE_WFC_ON_CALL_FAIL_BOOL, false);
        persistableBundle.putInt(KEY_CARRIER_DEFAULT_WFC_IMS_MODE_INT, 2);
        persistableBundle.putInt(KEY_CARRIER_DEFAULT_WFC_IMS_ROAMING_MODE_INT, 2);
        persistableBundle.putBoolean(KEY_CARRIER_FORCE_DISABLE_ETWS_CMAS_TEST_BOOL, false);
        persistableBundle.putBoolean(KEY_CARRIER_RCS_PROVISIONING_REQUIRED_BOOL, true);
        persistableBundle.putBoolean(KEY_CARRIER_VOLTE_PROVISIONING_REQUIRED_BOOL, false);
        persistableBundle.putBoolean(KEY_CARRIER_UT_PROVISIONING_REQUIRED_BOOL, false);
        persistableBundle.putBoolean(KEY_CARRIER_SUPPORTS_SS_OVER_UT_BOOL, false);
        persistableBundle.putBoolean(KEY_CARRIER_VOLTE_OVERRIDE_WFC_PROVISIONING_BOOL, false);
        persistableBundle.putBoolean(KEY_CARRIER_VOLTE_TTY_SUPPORTED_BOOL, true);
        persistableBundle.putBoolean(KEY_CARRIER_VOWIFI_TTY_SUPPORTED_BOOL, true);
        persistableBundle.putBoolean(KEY_CARRIER_ALLOW_TURNOFF_IMS_BOOL, true);
        persistableBundle.putBoolean(KEY_CARRIER_IMS_GBA_REQUIRED_BOOL, false);
        persistableBundle.putBoolean(KEY_CARRIER_INSTANT_LETTERING_AVAILABLE_BOOL, false);
        persistableBundle.putBoolean(KEY_CARRIER_USE_IMS_FIRST_FOR_EMERGENCY_BOOL, true);
        persistableBundle.putBoolean(KEY_PREFER_IN_SERVICE_SIM_FOR_NORMAL_ROUTED_EMERGENCY_CALLS_BOOL, false);
        persistableBundle.putBoolean(KEY_USE_ONLY_DIALED_SIM_ECC_LIST_BOOL, false);
        persistableBundle.putString(KEY_CARRIER_NETWORK_SERVICE_WWAN_PACKAGE_OVERRIDE_STRING, "");
        persistableBundle.putString(KEY_CARRIER_NETWORK_SERVICE_WLAN_PACKAGE_OVERRIDE_STRING, "");
        persistableBundle.putString(KEY_CARRIER_NETWORK_SERVICE_WLAN_CLASS_OVERRIDE_STRING, "");
        persistableBundle.putString(KEY_CARRIER_NETWORK_SERVICE_WWAN_CLASS_OVERRIDE_STRING, "");
        persistableBundle.putString(KEY_CARRIER_QUALIFIED_NETWORKS_SERVICE_PACKAGE_OVERRIDE_STRING, "");
        persistableBundle.putString(KEY_CARRIER_QUALIFIED_NETWORKS_SERVICE_CLASS_OVERRIDE_STRING, "");
        persistableBundle.putString(KEY_CARRIER_DATA_SERVICE_WWAN_PACKAGE_OVERRIDE_STRING, "");
        persistableBundle.putString(KEY_CARRIER_DATA_SERVICE_WLAN_PACKAGE_OVERRIDE_STRING, "");
        persistableBundle.putString(KEY_CARRIER_DATA_SERVICE_WWAN_CLASS_OVERRIDE_STRING, "");
        persistableBundle.putString(KEY_CARRIER_DATA_SERVICE_WLAN_CLASS_OVERRIDE_STRING, "");
        persistableBundle.putString(KEY_CONFIG_PLANS_PACKAGE_OVERRIDE_STRING, "");
        persistableBundle.putString(KEY_CARRIER_INSTANT_LETTERING_INVALID_CHARS_STRING, "");
        persistableBundle.putString(KEY_CARRIER_INSTANT_LETTERING_ESCAPED_CHARS_STRING, "");
        persistableBundle.putString(KEY_CARRIER_INSTANT_LETTERING_ENCODING_STRING, "");
        persistableBundle.putInt(KEY_CARRIER_INSTANT_LETTERING_LENGTH_LIMIT_INT, 64);
        persistableBundle.putBoolean(KEY_DISABLE_CDMA_ACTIVATION_CODE_BOOL, false);
        persistableBundle.putBoolean(KEY_DTMF_TYPE_ENABLED_BOOL, false);
        persistableBundle.putBoolean(KEY_ENABLE_DIALER_KEY_VIBRATION_BOOL, true);
        persistableBundle.putBoolean(KEY_HAS_IN_CALL_NOISE_SUPPRESSION_BOOL, false);
        persistableBundle.putBoolean(KEY_HIDE_CARRIER_NETWORK_SETTINGS_BOOL, false);
        persistableBundle.putBoolean(KEY_ONLY_AUTO_SELECT_IN_HOME_NETWORK_BOOL, false);
        persistableBundle.putBoolean(KEY_SHOW_SINGLE_OPERATOR_ROW_IN_CHOOSE_NETWORK_SETTING_BOOL, true);
        persistableBundle.putBoolean(KEY_SHOW_SPN_FOR_HOME_IN_CHOOSE_NETWORK_SETTING_BOOL, false);
        persistableBundle.putBoolean(KEY_SIMPLIFIED_NETWORK_SETTINGS_BOOL, false);
        persistableBundle.putBoolean(KEY_HIDE_SIM_LOCK_SETTINGS_BOOL, false);
        persistableBundle.putBoolean(KEY_CARRIER_VOLTE_PROVISIONED_BOOL, false);
        persistableBundle.putBoolean(KEY_CALL_BARRING_VISIBILITY_BOOL, false);
        persistableBundle.putBoolean(KEY_CALL_BARRING_SUPPORTS_PASSWORD_CHANGE_BOOL, true);
        persistableBundle.putBoolean(KEY_CALL_BARRING_SUPPORTS_DEACTIVATE_ALL_BOOL, true);
        persistableBundle.putInt(KEY_CALL_BARRING_DEFAULT_SERVICE_CLASS_INT, 1);
        persistableBundle.putBoolean(KEY_SUPPORT_SS_OVER_CDMA_BOOL, false);
        persistableBundle.putBoolean(KEY_CALL_FORWARDING_VISIBILITY_BOOL, true);
        persistableBundle.putBoolean(KEY_CALL_FORWARDING_WHEN_UNREACHABLE_SUPPORTED_BOOL, true);
        persistableBundle.putBoolean(KEY_CALL_FORWARDING_WHEN_UNANSWERED_SUPPORTED_BOOL, true);
        persistableBundle.putBoolean(KEY_CALL_FORWARDING_WHEN_BUSY_SUPPORTED_BOOL, true);
        persistableBundle.putBoolean(KEY_ADDITIONAL_SETTINGS_CALLER_ID_VISIBILITY_BOOL, true);
        persistableBundle.putBoolean(KEY_ADDITIONAL_SETTINGS_CALL_WAITING_VISIBILITY_BOOL, true);
        persistableBundle.putBoolean(KEY_DISABLE_SUPPLEMENTARY_SERVICES_IN_AIRPLANE_MODE_BOOL, false);
        persistableBundle.putBoolean(KEY_IGNORE_SIM_NETWORK_LOCKED_EVENTS_BOOL, false);
        persistableBundle.putBoolean(KEY_MDN_IS_ADDITIONAL_VOICEMAIL_NUMBER_BOOL, false);
        persistableBundle.putBoolean(KEY_OPERATOR_SELECTION_EXPAND_BOOL, true);
        persistableBundle.putBoolean(KEY_PREFER_2G_BOOL, false);
        persistableBundle.putBoolean(KEY_PREFER_3G_VISIBILITY_BOOL, true);
        persistableBundle.putBoolean(KEY_4G_ONLY_BOOL, false);
        persistableBundle.putBoolean(KEY_SHOW_APN_SETTING_CDMA_BOOL, false);
        persistableBundle.putBoolean(KEY_SHOW_CDMA_CHOICES_BOOL, false);
        persistableBundle.putBoolean(KEY_SMS_REQUIRES_DESTINATION_NUMBER_CONVERSION_BOOL, false);
        persistableBundle.putBoolean(KEY_SUPPORT_EMERGENCY_SMS_OVER_IMS_BOOL, false);
        persistableBundle.putBoolean(KEY_SHOW_ONSCREEN_DIAL_BUTTON_BOOL, true);
        persistableBundle.putBoolean(KEY_SIM_NETWORK_UNLOCK_ALLOW_DISMISS_BOOL, true);
        persistableBundle.putBoolean(KEY_SUPPORT_PAUSE_IMS_VIDEO_CALLS_BOOL, false);
        persistableBundle.putBoolean(KEY_SUPPORT_SWAP_AFTER_MERGE_BOOL, true);
        persistableBundle.putBoolean(KEY_USE_HFA_FOR_PROVISIONING_BOOL, false);
        persistableBundle.putBoolean(KEY_EDITABLE_VOICEMAIL_NUMBER_SETTING_BOOL, true);
        persistableBundle.putBoolean(KEY_EDITABLE_VOICEMAIL_NUMBER_BOOL, false);
        persistableBundle.putBoolean(KEY_HIDE_VOICEMAIL_NUMBER_SETTING_BOOL, false);
        persistableBundle.putBoolean(KEY_USE_OTASP_FOR_PROVISIONING_BOOL, false);
        persistableBundle.putBoolean(KEY_VOICEMAIL_NOTIFICATION_PERSISTENT_BOOL, false);
        persistableBundle.putBoolean(KEY_VOICE_PRIVACY_DISABLE_UI_BOOL, false);
        persistableBundle.putBoolean(KEY_WORLD_PHONE_BOOL, false);
        persistableBundle.putBoolean(KEY_REQUIRE_ENTITLEMENT_CHECKS_BOOL, true);
        persistableBundle.putBoolean(KEY_CARRIER_SUPPORTS_TETHERING_BOOL, true);
        persistableBundle.putBoolean(KEY_RESTART_RADIO_ON_PDP_FAIL_REGULAR_DEACTIVATION_BOOL, false);
        persistableBundle.putIntArray(KEY_RADIO_RESTART_FAILURE_CAUSES_INT_ARRAY, new int[0]);
        persistableBundle.putInt(KEY_VOLTE_REPLACEMENT_RAT_INT, 0);
        persistableBundle.putString(KEY_DEFAULT_SIM_CALL_MANAGER_STRING, "");
        persistableBundle.putString(KEY_VVM_DESTINATION_NUMBER_STRING, "");
        persistableBundle.putInt(KEY_VVM_PORT_NUMBER_INT, 0);
        persistableBundle.putString(KEY_VVM_TYPE_STRING, "");
        persistableBundle.putBoolean(KEY_VVM_CELLULAR_DATA_REQUIRED_BOOL, false);
        persistableBundle.putString(KEY_VVM_CLIENT_PREFIX_STRING, VisualVoicemailSmsFilterSettings.DEFAULT_CLIENT_PREFIX);
        persistableBundle.putBoolean(KEY_VVM_SSL_ENABLED_BOOL, false);
        persistableBundle.putStringArray(KEY_VVM_DISABLED_CAPABILITIES_STRING_ARRAY, null);
        persistableBundle.putBoolean(KEY_VVM_LEGACY_MODE_ENABLED_BOOL, false);
        persistableBundle.putBoolean(KEY_VVM_PREFETCH_BOOL, true);
        persistableBundle.putString(KEY_CARRIER_VVM_PACKAGE_NAME_STRING, "");
        persistableBundle.putStringArray(KEY_CARRIER_VVM_PACKAGE_NAME_STRING_ARRAY, null);
        persistableBundle.putBoolean(KEY_SHOW_ICCID_IN_SIM_STATUS_BOOL, false);
        persistableBundle.putBoolean(KEY_SHOW_SIGNAL_STRENGTH_IN_SIM_STATUS_BOOL, true);
        persistableBundle.putBoolean(KEY_INFLATE_SIGNAL_STRENGTH_BOOL, false);
        persistableBundle.putBoolean(KEY_CI_ACTION_ON_SYS_UPDATE_BOOL, false);
        persistableBundle.putString(KEY_CI_ACTION_ON_SYS_UPDATE_INTENT_STRING, "");
        persistableBundle.putString(KEY_CI_ACTION_ON_SYS_UPDATE_EXTRA_STRING, "");
        persistableBundle.putString(KEY_CI_ACTION_ON_SYS_UPDATE_EXTRA_VAL_STRING, "");
        persistableBundle.putBoolean(KEY_CSP_ENABLED_BOOL, false);
        persistableBundle.putBoolean(KEY_ALLOW_ADDING_APNS_BOOL, true);
        persistableBundle.putStringArray(KEY_READ_ONLY_APN_TYPES_STRING_ARRAY, new String[]{"dun"});
        persistableBundle.putStringArray(KEY_READ_ONLY_APN_FIELDS_STRING_ARRAY, null);
        persistableBundle.putStringArray(KEY_APN_SETTINGS_DEFAULT_APN_TYPES_STRING_ARRAY, null);
        persistableBundle.putAll(Apn.getDefaults());
        persistableBundle.putBoolean(KEY_BROADCAST_EMERGENCY_CALL_STATE_CHANGES_BOOL, false);
        persistableBundle.putBoolean(KEY_ALWAYS_SHOW_EMERGENCY_ALERT_ONOFF_BOOL, false);
        persistableBundle.putInt(KEY_DEFAULT_MTU_INT, 1500);
        persistableBundle.putLong(KEY_CARRIER_DATA_CALL_APN_RETRY_AFTER_DISCONNECT_LONG, 3000L);
        persistableBundle.putString(KEY_CARRIER_ERI_FILE_NAME_STRING, "eri.xml");
        persistableBundle.putInt(KEY_DURATION_BLOCKING_DISABLED_AFTER_EMERGENCY_INT, 7200);
        persistableBundle.putStringArray(KEY_CARRIER_METERED_APN_TYPES_STRINGS, new String[]{"default", "mms", "dun", "supl", ApnSetting.TYPE_ENTERPRISE_STRING});
        persistableBundle.putStringArray(KEY_CARRIER_METERED_ROAMING_APN_TYPES_STRINGS, new String[]{"default", "mms", "dun", "supl", ApnSetting.TYPE_ENTERPRISE_STRING});
        persistableBundle.putIntArray(KEY_ONLY_SINGLE_DC_ALLOWED_INT_ARRAY, new int[]{4, 7, 5, 6, 12});
        persistableBundle.putIntArray(KEY_CAPABILITIES_EXEMPT_FROM_SINGLE_DC_CHECK_INT_ARRAY, new int[]{4});
        persistableBundle.putStringArray(KEY_GSM_ROAMING_NETWORKS_STRING_ARRAY, null);
        persistableBundle.putStringArray(KEY_GSM_NONROAMING_NETWORKS_STRING_ARRAY, null);
        persistableBundle.putString(KEY_CONFIG_IMS_PACKAGE_OVERRIDE_STRING, null);
        persistableBundle.putString(KEY_CONFIG_IMS_MMTEL_PACKAGE_OVERRIDE_STRING, null);
        persistableBundle.putString(KEY_CONFIG_IMS_RCS_PACKAGE_OVERRIDE_STRING, null);
        persistableBundle.putStringArray(KEY_CDMA_ROAMING_NETWORKS_STRING_ARRAY, null);
        persistableBundle.putStringArray(KEY_CDMA_NONROAMING_NETWORKS_STRING_ARRAY, null);
        persistableBundle.putStringArray(KEY_DIAL_STRING_REPLACE_STRING_ARRAY, null);
        persistableBundle.putStringArray(KEY_INTERNATIONAL_ROAMING_DIAL_STRING_REPLACE_STRING_ARRAY, null);
        persistableBundle.putBoolean(KEY_FORCE_HOME_NETWORK_BOOL, false);
        persistableBundle.putInt(KEY_GSM_DTMF_TONE_DELAY_INT, 0);
        persistableBundle.putInt(KEY_IMS_DTMF_TONE_DELAY_INT, 0);
        persistableBundle.putInt(KEY_CDMA_DTMF_TONE_DELAY_INT, 100);
        persistableBundle.putBoolean(KEY_CALL_FORWARDING_MAP_NON_NUMBER_TO_VOICEMAIL_BOOL, false);
        persistableBundle.putBoolean(KEY_IGNORE_RTT_MODE_SETTING_BOOL, true);
        persistableBundle.putInt(KEY_CDMA_3WAYCALL_FLASH_DELAY_INT, 0);
        persistableBundle.putBoolean(KEY_SUPPORT_ADHOC_CONFERENCE_CALLS_BOOL, false);
        persistableBundle.putBoolean(KEY_SUPPORT_ADD_CONFERENCE_PARTICIPANTS_BOOL, false);
        persistableBundle.putBoolean(KEY_SUPPORT_CONFERENCE_CALL_BOOL, true);
        persistableBundle.putBoolean(KEY_SUPPORT_IMS_CONFERENCE_CALL_BOOL, true);
        persistableBundle.putBoolean(KEY_LOCAL_DISCONNECT_EMPTY_IMS_CONFERENCE_BOOL, false);
        persistableBundle.putBoolean(KEY_SUPPORT_MANAGE_IMS_CONFERENCE_CALL_BOOL, true);
        persistableBundle.putBoolean(KEY_SUPPORT_IMS_CONFERENCE_EVENT_PACKAGE_BOOL, true);
        persistableBundle.putBoolean(KEY_SUPPORT_IMS_CONFERENCE_EVENT_PACKAGE_ON_PEER_BOOL, true);
        persistableBundle.putBoolean(KEY_SUPPORTS_DEVICE_TO_DEVICE_COMMUNICATION_USING_RTP_BOOL, false);
        persistableBundle.putBoolean(KEY_SUPPORTS_SDP_NEGOTIATION_OF_D2D_RTP_HEADER_EXTENSIONS_BOOL, false);
        persistableBundle.putBoolean(KEY_SUPPORTS_DEVICE_TO_DEVICE_COMMUNICATION_USING_DTMF_BOOL, false);
        persistableBundle.putBoolean(KEY_SUPPORT_VIDEO_CONFERENCE_CALL_BOOL, false);
        persistableBundle.putBoolean(KEY_IS_IMS_CONFERENCE_SIZE_ENFORCED_BOOL, false);
        persistableBundle.putInt(KEY_IMS_CONFERENCE_SIZE_LIMIT_INT, 5);
        persistableBundle.putBoolean(KEY_DISPLAY_HD_AUDIO_PROPERTY_BOOL, true);
        persistableBundle.putBoolean(KEY_EDITABLE_ENHANCED_4G_LTE_BOOL, true);
        persistableBundle.putBoolean(KEY_HIDE_ENHANCED_4G_LTE_BOOL, false);
        persistableBundle.putBoolean(KEY_ENHANCED_4G_LTE_ON_BY_DEFAULT_BOOL, true);
        persistableBundle.putBoolean(KEY_HIDE_IMS_APN_BOOL, false);
        persistableBundle.putBoolean(KEY_HIDE_PREFERRED_NETWORK_TYPE_BOOL, false);
        persistableBundle.putBoolean(KEY_ALLOW_EMERGENCY_VIDEO_CALLS_BOOL, false);
        persistableBundle.putStringArray(KEY_ENABLE_APPS_STRING_ARRAY, null);
        persistableBundle.putBoolean(KEY_EDITABLE_WFC_MODE_BOOL, true);
        persistableBundle.putStringArray(KEY_WFC_OPERATOR_ERROR_CODES_STRING_ARRAY, null);
        persistableBundle.putInt(KEY_WFC_SPN_FORMAT_IDX_INT, 0);
        persistableBundle.putInt(KEY_WFC_DATA_SPN_FORMAT_IDX_INT, 0);
        persistableBundle.putInt(KEY_WFC_FLIGHT_MODE_SPN_FORMAT_IDX_INT, -1);
        persistableBundle.putBoolean(KEY_WFC_SPN_USE_ROOT_LOCALE, false);
        persistableBundle.putString(KEY_WFC_EMERGENCY_ADDRESS_CARRIER_APP_STRING, "");
        persistableBundle.putBoolean(KEY_CONFIG_WIFI_DISABLE_IN_ECBM, false);
        persistableBundle.putBoolean(KEY_CARRIER_NAME_OVERRIDE_BOOL, false);
        persistableBundle.putString(KEY_CARRIER_NAME_STRING, "");
        persistableBundle.putBoolean(KEY_WFC_CARRIER_NAME_OVERRIDE_BY_PNN_BOOL, false);
        persistableBundle.putInt(KEY_CROSS_SIM_SPN_FORMAT_INT, 1);
        persistableBundle.putInt(KEY_SPN_DISPLAY_CONDITION_OVERRIDE_INT, -1);
        persistableBundle.putStringArray(KEY_SPDI_OVERRIDE_STRING_ARRAY, null);
        persistableBundle.putStringArray(KEY_PNN_OVERRIDE_STRING_ARRAY, null);
        persistableBundle.putStringArray(KEY_OPL_OVERRIDE_STRING_ARRAY, null);
        persistableBundle.putStringArray(KEY_EHPLMN_OVERRIDE_STRING_ARRAY, null);
        persistableBundle.putBoolean(KEY_ALLOW_ERI_BOOL, false);
        persistableBundle.putBoolean(KEY_ENABLE_CARRIER_DISPLAY_NAME_RESOLVER_BOOL, false);
        persistableBundle.putString(KEY_SIM_COUNTRY_ISO_OVERRIDE_STRING, "");
        persistableBundle.putString(KEY_CARRIER_CALL_SCREENING_APP_STRING, "");
        persistableBundle.putString(KEY_CALL_REDIRECTION_SERVICE_COMPONENT_NAME_STRING, null);
        persistableBundle.putBoolean(KEY_CDMA_HOME_REGISTERED_PLMN_NAME_OVERRIDE_BOOL, false);
        persistableBundle.putString(KEY_CDMA_HOME_REGISTERED_PLMN_NAME_STRING, "");
        persistableBundle.putBoolean(KEY_SUPPORT_DIRECT_FDN_DIALING_BOOL, false);
        persistableBundle.putInt(KEY_FDN_NUMBER_LENGTH_LIMIT_INT, 20);
        persistableBundle.putBoolean(KEY_CARRIER_DEFAULT_DATA_ROAMING_ENABLED_BOOL, false);
        persistableBundle.putBoolean(KEY_CARRIER_DEFAULT_INTERNATIONAL_DATA_ROAMING_ENABLED_BOOL, false);
        persistableBundle.putBoolean(KEY_SKIP_CF_FAIL_TO_DISABLE_DIALOG_BOOL, false);
        persistableBundle.putBoolean(KEY_SUPPORT_ENHANCED_CALL_BLOCKING_BOOL, true);
        persistableBundle.putBoolean("aliasEnabled", false);
        persistableBundle.putBoolean("allowAttachAudio", true);
        persistableBundle.putBoolean("enabledTransID", false);
        persistableBundle.putBoolean("enableGroupMms", true);
        persistableBundle.putBoolean("enableMMSDeliveryReports", false);
        persistableBundle.putBoolean("enabledMMS", true);
        persistableBundle.putBoolean("enableMMSReadReports", false);
        persistableBundle.putBoolean("enableMultipartSMS", true);
        persistableBundle.putBoolean("enabledNotifyWapMMSC", false);
        persistableBundle.putBoolean("sendMultipartSmsAsSeparateMessages", false);
        persistableBundle.putBoolean("config_cellBroadcastAppLinks", true);
        persistableBundle.putBoolean("enableSMSDeliveryReports", true);
        persistableBundle.putBoolean("supportHttpCharsetHeader", false);
        persistableBundle.putBoolean("supportMmsContentDisposition", true);
        persistableBundle.putBoolean("mmsCloseConnection", false);
        persistableBundle.putInt("aliasMaxChars", 48);
        persistableBundle.putInt("aliasMinChars", 2);
        persistableBundle.putInt("httpSocketTimeout", 60000);
        persistableBundle.putInt("maxImageHeight", 480);
        persistableBundle.putInt("maxImageWidth", 640);
        persistableBundle.putInt("maxMessageTextSize", -1);
        persistableBundle.putInt("smsToMmsTextLengthThreshold", -1);
        persistableBundle.putInt("maxSubjectLength", 40);
        persistableBundle.putInt(KEY_MMS_NETWORK_RELEASE_TIMEOUT_MILLIS_INT, 5000);
        persistableBundle.putInt(KEY_MMS_MAX_NTN_PAYLOAD_SIZE_BYTES_INT, -1);
        persistableBundle.putString("emailGatewayNumber", "");
        persistableBundle.putString("httpParams", "");
        persistableBundle.putString("naiSuffix", "");
        persistableBundle.putString("uaProfTagName", "x-wap-profile");
        persistableBundle.putString("uaProfUrl", "");
        persistableBundle.putString("userAgent", "");
        persistableBundle.putBoolean(KEY_ALLOW_NON_EMERGENCY_CALLS_IN_ECM_BOOL, true);
        persistableBundle.putInt(KEY_EMERGENCY_SMS_MODE_TIMER_MS_INT, 0);
        persistableBundle.putBoolean(KEY_ALLOW_HOLD_CALL_DURING_EMERGENCY_BOOL, true);
        persistableBundle.putBoolean(KEY_USE_RCS_PRESENCE_BOOL, false);
        persistableBundle.putBoolean(KEY_USE_RCS_SIP_OPTIONS_BOOL, false);
        persistableBundle.putBoolean(KEY_FORCE_IMEI_BOOL, false);
        persistableBundle.putInt(KEY_CDMA_ROAMING_MODE_INT, -1);
        persistableBundle.putBoolean(KEY_SUPPORT_CDMA_1X_VOICE_CALLS_BOOL, true);
        persistableBundle.putString(KEY_RCS_CONFIG_SERVER_URL_STRING, "");
        if ("US".equalsIgnoreCase(SemSystemProperties.getCountryIso())) {
            persistableBundle.putInt("maxMessageSize", 1024000);
            persistableBundle.putInt("recipientLimit", 10);
            persistableBundle.putInt("smsToMmsTextThreshold", 5);
        } else {
            persistableBundle.putInt("maxMessageSize", 307200);
            persistableBundle.putInt("recipientLimit", 20);
            persistableBundle.putInt("smsToMmsTextThreshold", 3);
        }
        persistableBundle.putString(KEY_CARRIER_SETUP_APP_STRING, "");
        persistableBundle.putStringArray(KEY_CARRIER_APP_WAKE_SIGNAL_CONFIG_STRING_ARRAY, new String[]{"com.android.carrierdefaultapp/.CarrierDefaultBroadcastReceiver:com.android.internal.telephony.CARRIER_SIGNAL_RESET"});
        persistableBundle.putStringArray(KEY_CARRIER_APP_NO_WAKE_SIGNAL_CONFIG_STRING_ARRAY, null);
        persistableBundle.putBoolean(KEY_CARRIER_APP_REQUIRED_DURING_SIM_SETUP_BOOL, false);
        persistableBundle.putStringArray(KEY_CARRIER_DEFAULT_ACTIONS_ON_REDIRECTION_STRING_ARRAY, new String[]{"9, 4, 1"});
        persistableBundle.putStringArray(KEY_CARRIER_DEFAULT_ACTIONS_ON_RESET, new String[]{"6, 8"});
        persistableBundle.putStringArray(KEY_CARRIER_DEFAULT_ACTIONS_ON_DEFAULT_NETWORK_AVAILABLE, new String[]{"false: 7", "true: 8"});
        persistableBundle.putStringArray(KEY_CARRIER_DEFAULT_REDIRECTION_URL_STRING_ARRAY, null);
        persistableBundle.putInt(KEY_MONTHLY_DATA_CYCLE_DAY_INT, -1);
        persistableBundle.putLong(KEY_DATA_WARNING_THRESHOLD_BYTES_LONG, -1L);
        persistableBundle.putBoolean(KEY_DATA_WARNING_NOTIFICATION_BOOL, true);
        persistableBundle.putBoolean(KEY_LIMITED_SIM_FUNCTION_NOTIFICATION_FOR_DSDS_BOOL, false);
        persistableBundle.putLong(KEY_DATA_LIMIT_THRESHOLD_BYTES_LONG, -1L);
        persistableBundle.putBoolean(KEY_DATA_LIMIT_NOTIFICATION_BOOL, true);
        persistableBundle.putBoolean(KEY_DATA_RAPID_NOTIFICATION_BOOL, true);
        persistableBundle.putStringArray(KEY_RATCHET_RAT_FAMILIES, new String[]{"1,2", "7,8,12", "3,11,9,10,15", "14,19"});
        persistableBundle.putBoolean(KEY_TREAT_DOWNGRADED_VIDEO_CALLS_AS_VIDEO_CALLS_BOOL, false);
        persistableBundle.putBoolean(KEY_DROP_VIDEO_CALL_WHEN_ANSWERING_AUDIO_CALL_BOOL, false);
        persistableBundle.putBoolean(KEY_ALLOW_MERGE_WIFI_CALLS_WHEN_VOWIFI_OFF_BOOL, true);
        persistableBundle.putBoolean(KEY_ALLOW_ADD_CALL_DURING_VIDEO_CALL_BOOL, true);
        persistableBundle.putBoolean(KEY_ALLOW_HOLD_VIDEO_CALL_BOOL, true);
        persistableBundle.putBoolean(KEY_WIFI_CALLS_CAN_BE_HD_AUDIO, true);
        persistableBundle.putBoolean(KEY_VIDEO_CALLS_CAN_BE_HD_AUDIO, true);
        persistableBundle.putBoolean(KEY_GSM_CDMA_CALLS_CAN_BE_HD_AUDIO, false);
        persistableBundle.putBoolean(KEY_ALLOW_VIDEO_CALLING_FALLBACK_BOOL, true);
        persistableBundle.putStringArray(KEY_IMS_REASONINFO_MAPPING_STRING_ARRAY, null);
        persistableBundle.putBoolean(KEY_ENHANCED_4G_LTE_TITLE_VARIANT_BOOL, false);
        persistableBundle.putInt(KEY_ENHANCED_4G_LTE_TITLE_VARIANT_INT, 0);
        persistableBundle.putBoolean(KEY_NOTIFY_VT_HANDOVER_TO_WIFI_FAILURE_BOOL, false);
        persistableBundle.putStringArray(KEY_FILTERED_CNAP_NAMES_STRING_ARRAY, null);
        persistableBundle.putBoolean(KEY_EDITABLE_WFC_ROAMING_MODE_BOOL, false);
        persistableBundle.putBoolean(KEY_SHOW_BLOCKING_PAY_PHONE_OPTION_BOOL, false);
        persistableBundle.putBoolean(KEY_USE_WFC_HOME_NETWORK_MODE_IN_ROAMING_NETWORK_BOOL, false);
        persistableBundle.putBoolean(KEY_STK_DISABLE_LAUNCH_BROWSER_BOOL, false);
        persistableBundle.putBoolean(KEY_ALLOW_METERED_NETWORK_FOR_CERT_DOWNLOAD_BOOL, false);
        persistableBundle.putBoolean(KEY_HIDE_DIGITS_HELPER_TEXT_ON_STK_INPUT_SCREEN_BOOL, true);
        persistableBundle.putInt(KEY_PREF_NETWORK_NOTIFICATION_DELAY_INT, -1);
        persistableBundle.putInt(KEY_EMERGENCY_NOTIFICATION_DELAY_INT, -1);
        persistableBundle.putBoolean(KEY_ALLOW_USSD_REQUESTS_VIA_TELEPHONY_MANAGER_BOOL, true);
        persistableBundle.putBoolean(KEY_SUPPORT_3GPP_CALL_FORWARDING_WHILE_ROAMING_BOOL, true);
        persistableBundle.putBoolean(KEY_DISPLAY_VOICEMAIL_NUMBER_AS_DEFAULT_CALL_FORWARDING_NUMBER_BOOL, false);
        persistableBundle.putBoolean(KEY_NOTIFY_INTERNATIONAL_CALL_ON_WFC_BOOL, false);
        persistableBundle.putBoolean(KEY_HIDE_PRESET_APN_DETAILS_BOOL, false);
        persistableBundle.putBoolean(KEY_SHOW_VIDEO_CALL_CHARGES_ALERT_DIALOG_BOOL, false);
        persistableBundle.putStringArray(KEY_CALL_FORWARDING_BLOCKS_WHILE_ROAMING_STRING_ARRAY, null);
        persistableBundle.putBoolean(KEY_SUPPORT_IMS_CALL_FORWARDING_WHILE_ROAMING_BOOL, true);
        persistableBundle.putInt(KEY_LTE_EARFCNS_RSRP_BOOST_INT, 0);
        persistableBundle.putStringArray(KEY_BOOSTED_LTE_EARFCNS_STRING_ARRAY, null);
        persistableBundle.putIntArray(KEY_NRARFCNS_RSRP_BOOST_INT_ARRAY, null);
        persistableBundle.putStringArray(KEY_BOOSTED_NRARFCNS_STRING_ARRAY, null);
        persistableBundle.putBoolean(KEY_USE_ONLY_RSRP_FOR_LTE_SIGNAL_BAR_BOOL, false);
        persistableBundle.putBoolean(KEY_DISABLE_VOICE_BARRING_NOTIFICATION_BOOL, false);
        persistableBundle.putInt(IMSI_KEY_AVAILABILITY_INT, 0);
        persistableBundle.putString(IMSI_KEY_DOWNLOAD_URL_STRING, null);
        persistableBundle.putString(IMSI_CARRIER_PUBLIC_KEY_EPDG_STRING, null);
        persistableBundle.putString(IMSI_CARRIER_PUBLIC_KEY_WLAN_STRING, null);
        persistableBundle.putBoolean(KEY_CONVERT_CDMA_CALLER_ID_MMI_CODES_WHILE_ROAMING_ON_3GPP_BOOL, false);
        persistableBundle.putStringArray(KEY_NON_ROAMING_OPERATOR_STRING_ARRAY, null);
        persistableBundle.putStringArray(KEY_ROAMING_OPERATOR_STRING_ARRAY, null);
        persistableBundle.putBoolean(KEY_SHOW_ROAMING_INDICATOR_BOOL, true);
        persistableBundle.putBoolean(KEY_SHOW_IMS_REGISTRATION_STATUS_BOOL, false);
        persistableBundle.putBoolean(KEY_RTT_SUPPORTED_BOOL, false);
        persistableBundle.putBoolean(KEY_TTY_SUPPORTED_BOOL, true);
        persistableBundle.putBoolean(KEY_RTT_AUTO_UPGRADE_BOOL, false);
        persistableBundle.putBoolean(KEY_RTT_SUPPORTED_FOR_VT_BOOL, false);
        persistableBundle.putBoolean(KEY_RTT_UPGRADE_SUPPORTED_BOOL, false);
        persistableBundle.putBoolean(KEY_RTT_DOWNGRADE_SUPPORTED_BOOL, false);
        persistableBundle.putBoolean(KEY_HIDE_TTY_HCO_VCO_WITH_RTT_BOOL, false);
        persistableBundle.putBoolean(KEY_RTT_SUPPORTED_WHILE_ROAMING_BOOL, false);
        persistableBundle.putBoolean(KEY_RTT_UPGRADE_SUPPORTED_FOR_DOWNGRADED_VT_CALL_BOOL, true);
        persistableBundle.putBoolean(KEY_VT_UPGRADE_SUPPORTED_FOR_DOWNGRADED_RTT_CALL_BOOL, true);
        persistableBundle.putBoolean(KEY_DISABLE_CHARGE_INDICATION_BOOL, false);
        persistableBundle.putBoolean(KEY_SUPPORT_NO_REPLY_TIMER_FOR_CFNRY_BOOL, true);
        persistableBundle.putInt(KEY_NO_REPLY_TIMER_FOR_CFNRY_SEC_INT, 20);
        persistableBundle.putStringArray(KEY_FEATURE_ACCESS_CODES_STRING_ARRAY, null);
        persistableBundle.putBoolean(KEY_IDENTIFY_HIGH_DEFINITION_CALLS_IN_CALL_LOG_BOOL, false);
        persistableBundle.putBoolean(KEY_SHOW_PRECISE_FAILED_CAUSE_BOOL, false);
        persistableBundle.putBoolean(KEY_SPN_DISPLAY_RULE_USE_ROAMING_FROM_SERVICE_STATE_BOOL, false);
        persistableBundle.putBoolean(KEY_ALWAYS_SHOW_DATA_RAT_ICON_BOOL, false);
        persistableBundle.putBoolean(KEY_SHOW_4G_FOR_LTE_DATA_ICON_BOOL, false);
        persistableBundle.putBoolean(KEY_SHOW_4GLTE_FOR_LTE_DATA_ICON_BOOL, false);
        persistableBundle.putBoolean(KEY_SHOW_4G_FOR_3G_DATA_ICON_BOOL, false);
        persistableBundle.putString(KEY_OPERATOR_NAME_FILTER_PATTERN_STRING, "");
        persistableBundle.putString(KEY_SHOW_CARRIER_DATA_ICON_PATTERN_STRING, "");
        persistableBundle.putBoolean(KEY_HIDE_LTE_PLUS_DATA_ICON_BOOL, true);
        persistableBundle.putBoolean(KEY_SHOW_5G_SLICE_ICON_BOOL, true);
        persistableBundle.putInt(KEY_LTE_PLUS_THRESHOLD_BANDWIDTH_KHZ_INT, 20000);
        persistableBundle.putInt(KEY_NR_ADVANCED_THRESHOLD_BANDWIDTH_KHZ_INT, 0);
        persistableBundle.putBoolean(KEY_INCLUDE_LTE_FOR_NR_ADVANCED_THRESHOLD_BANDWIDTH_BOOL, false);
        persistableBundle.putBoolean(KEY_RATCHET_NR_ADVANCED_BANDWIDTH_IF_RRC_IDLE_BOOL, false);
        persistableBundle.putIntArray(KEY_CARRIER_NR_AVAILABILITIES_INT_ARRAY, new int[]{1, 2});
        persistableBundle.putBoolean(KEY_LTE_ENABLED_BOOL, true);
        persistableBundle.putBoolean(KEY_SUPPORT_TDSCDMA_BOOL, false);
        persistableBundle.putStringArray(KEY_SUPPORT_TDSCDMA_ROAMING_NETWORKS_STRING_ARRAY, null);
        persistableBundle.putBoolean(KEY_WORLD_MODE_ENABLED_BOOL, false);
        persistableBundle.putString(KEY_CARRIER_SETTINGS_ACTIVITY_COMPONENT_NAME_STRING, "");
        persistableBundle.putBoolean(KEY_SHOW_OPERATOR_NAME_IN_STATUSBAR_BOOL, false);
        persistableBundle.putBoolean(KEY_CARRIER_CONFIG_APPLIED_BOOL, false);
        persistableBundle.putBoolean(KEY_CHECK_PRICING_WITH_CARRIER_FOR_DATA_ROAMING_BOOL, false);
        persistableBundle.putBoolean(KEY_SHOW_DATA_CONNECTED_ROAMING_NOTIFICATION_BOOL, false);
        persistableBundle.putStringArray(KEY_DATA_CONNECTED_ROAMING_NOTIFICATION_EXCLUDED_MCCS_STRING_ARRAY, new String[0]);
        persistableBundle.putStringArray(KEY_DATA_CONNECTED_ROAMING_NOTIFICATION_INCLUDED_MCC_MNCS_STRING_ARRAY, new String[0]);
        persistableBundle.putIntArray(KEY_LTE_RSRP_THRESHOLDS_INT_ARRAY, new int[]{-128, PackageManager.INSTALL_FAILED_BAD_SIGNATURE, -108, -98});
        persistableBundle.putIntArray(KEY_LTE_RSRQ_THRESHOLDS_INT_ARRAY, new int[]{-20, -17, -14, -11});
        persistableBundle.putIntArray(KEY_LTE_RSSNR_THRESHOLDS_INT_ARRAY, new int[]{-3, 1, 5, 13});
        persistableBundle.putIntArray(KEY_WCDMA_RSCP_THRESHOLDS_INT_ARRAY, new int[]{PackageManager.INSTALL_FAILED_ABORTED, -105, -95, -85});
        persistableBundle.putIntArray(KEY_WCDMA_ECNO_THRESHOLDS_INT_ARRAY, new int[]{-24, -14, -6, 1});
        persistableBundle.putIntArray(KEY_5G_NR_SSRSRP_THRESHOLDS_INT_ARRAY, new int[]{-110, -90, -80, -65});
        persistableBundle.putIntArray(KEY_5G_NR_SSRSRQ_THRESHOLDS_INT_ARRAY, new int[]{-31, -19, -7, 6});
        persistableBundle.putIntArray(KEY_5G_NR_SSSINR_THRESHOLDS_INT_ARRAY, new int[]{-5, 5, 15, 30});
        persistableBundle.putInt(KEY_GERAN_RSSI_HYSTERESIS_DB_INT, 2);
        persistableBundle.putInt(KEY_UTRAN_RSCP_HYSTERESIS_DB_INT, 2);
        persistableBundle.putInt(KEY_EUTRAN_RSRP_HYSTERESIS_DB_INT, 2);
        persistableBundle.putInt(KEY_EUTRAN_RSRQ_HYSTERESIS_DB_INT, 2);
        persistableBundle.putInt(KEY_EUTRAN_RSSNR_HYSTERESIS_DB_INT, 2);
        persistableBundle.putInt(KEY_NGRAN_SSRSRP_HYSTERESIS_DB_INT, 2);
        persistableBundle.putInt(KEY_NGRAN_SSRSRQ_HYSTERESIS_DB_INT, 2);
        persistableBundle.putInt(KEY_NGRAN_SSSINR_HYSTERESIS_DB_INT, 2);
        persistableBundle.putInt(KEY_UTRAN_ECNO_HYSTERESIS_DB_INT, 2);
        persistableBundle.putInt(KEY_PARAMETERS_USE_FOR_5G_NR_SIGNAL_BAR_INT, 1);
        persistableBundle.putBoolean(KEY_SIGNAL_STRENGTH_NR_NSA_USE_LTE_AS_PRIMARY_BOOL, true);
        persistableBundle.putStringArray(KEY_BANDWIDTH_STRING_ARRAY, new String[]{"GPRS:24,24", "EDGE:70,18", "UMTS:115,115", "CDMA:14,14", "1xRTT:30,30", "EvDo_0:750,48", "EvDo_A:950,550", "HSDPA:4300,620", "HSUPA:4300,1800", "HSPA:4300,1800", "EvDo_B:1500,550", "eHRPD:750,48", "iDEN:14,14", "LTE:30000,15000", "HSPA+:13000,3400", "GSM:24,24", "TD_SCDMA:115,115", "LTE_CA:30000,15000", "NR_NSA:47000,18000", "NR_NSA_MMWAVE:145000,60000", "NR_SA:145000,60000", "NR_SA_MMWAVE:145000,60000"});
        persistableBundle.putBoolean(KEY_BANDWIDTH_NR_NSA_USE_LTE_VALUE_FOR_UPLINK_BOOL, false);
        persistableBundle.putString(KEY_WCDMA_DEFAULT_SIGNAL_STRENGTH_MEASUREMENT_STRING, CellSignalStrengthWcdma.LEVEL_CALCULATION_METHOD_RSSI);
        persistableBundle.putLong(KEY_UNDELIVERED_SMS_MESSAGE_EXPIRATION_TIME, 604800000L);
        persistableBundle.putBoolean(KEY_CONFIG_SHOW_ORIG_DIAL_STRING_FOR_CDMA_BOOL, false);
        persistableBundle.putBoolean(KEY_SHOW_CALL_BLOCKING_DISABLED_NOTIFICATION_ALWAYS_BOOL, false);
        persistableBundle.putBoolean(KEY_CALL_FORWARDING_OVER_UT_WARNING_BOOL, false);
        persistableBundle.putBoolean(KEY_CALL_BARRING_OVER_UT_WARNING_BOOL, false);
        persistableBundle.putBoolean(KEY_CALLER_ID_OVER_UT_WARNING_BOOL, false);
        persistableBundle.putBoolean(KEY_CALL_WAITING_OVER_UT_WARNING_BOOL, false);
        persistableBundle.putBoolean(KEY_SUPPORT_CLIR_NETWORK_DEFAULT_BOOL, true);
        persistableBundle.putBoolean(KEY_SUPPORT_EMERGENCY_DIALER_SHORTCUT_BOOL, true);
        persistableBundle.putBoolean(KEY_USE_CALL_FORWARDING_USSD_BOOL, false);
        persistableBundle.putBoolean(KEY_USE_CALLER_ID_USSD_BOOL, false);
        persistableBundle.putBoolean(KEY_USE_CALL_WAITING_USSD_BOOL, false);
        persistableBundle.putInt(KEY_CALL_WAITING_SERVICE_CLASS_INT, 1);
        persistableBundle.putString(KEY_5G_ICON_DISPLAY_GRACE_PERIOD_STRING, "");
        persistableBundle.putString(KEY_5G_ICON_DISPLAY_SECONDARY_GRACE_PERIOD_STRING, "");
        persistableBundle.putString(KEY_5G_ICON_CONFIGURATION_STRING_2ND, "connected_mmwave:5G,connected:5G,not_restricted_rrc_idle:5G,not_restricted_rrc_con:5G");
        persistableBundle.putString(KEY_5G_ICON_DISPLAY_GRACE_PERIOD_STRING_2ND, "");
        persistableBundle.putString(KEY_5G_ICON_DISPLAY_SECONDARY_GRACE_PERIOD_STRING_2ND, "");
        persistableBundle.putInt(KEY_NR_ADVANCED_PCI_CHANGE_SECONDARY_TIMER_SECONDS_INT, 0);
        persistableBundle.putInt(KEY_NR_ADVANCED_BANDS_SECONDARY_TIMER_SECONDS_INT, 0);
        persistableBundle.putBoolean(KEY_NR_TIMERS_RESET_IF_NON_ENDC_AND_RRC_IDLE_BOOL, false);
        persistableBundle.putBoolean(KEY_NR_TIMERS_RESET_ON_VOICE_QOS_BOOL, false);
        persistableBundle.putBoolean(KEY_NR_TIMERS_RESET_ON_PLMN_CHANGE_BOOL, false);
        persistableBundle.putLong(KEY_5G_WATCHDOG_TIME_MS_LONG, 3600000L);
        persistableBundle.putIntArray(KEY_ADDITIONAL_NR_ADVANCED_BANDS_INT_ARRAY, new int[0]);
        persistableBundle.putIntArray(KEY_SOLE_BANDWIDTH_BANDS_INT_ARRAY, new int[0]);
        persistableBundle.putInt(KEY_NR_ADVANCED_CAPABLE_PCO_ID_INT, 0);
        persistableBundle.putBoolean(KEY_ENABLE_NR_ADVANCED_WHILE_ROAMING_BOOL, true);
        persistableBundle.putBoolean(KEY_UW_DISQUALIFICATION_ENABLED_BOOL, false);
        persistableBundle.putInt(KEY_HIGH_BANDWIDTH_DATA_TRANSFER_THRESHOLD_KBPS_INT, 0);
        persistableBundle.putBoolean(KEY_LTE_ENDC_USING_USER_DATA_FOR_RRC_DETECTION_BOOL, false);
        persistableBundle.putStringArray(KEY_UNMETERED_NETWORK_TYPES_STRING_ARRAY, new String[]{DctConstants.RAT_NAME_NR_NSA, DctConstants.RAT_NAME_NR_NSA_MMWAVE, "NR_SA", "NR_SA_MMWAVE"});
        persistableBundle.putStringArray(KEY_ROAMING_UNMETERED_NETWORK_TYPES_STRING_ARRAY, new String[0]);
        persistableBundle.putBoolean(KEY_ASCII_7_BIT_SUPPORT_FOR_LONG_MESSAGE_BOOL, false);
        persistableBundle.putBoolean(KEY_SHOW_WIFI_CALLING_ICON_IN_STATUS_BAR_BOOL, false);
        persistableBundle.putBoolean(KEY_CARRIER_SUPPORTS_OPP_DATA_AUTO_PROVISIONING_BOOL, false);
        persistableBundle.putString(KEY_SMDP_SERVER_ADDRESS_STRING, "");
        persistableBundle.putInt(KEY_ESIM_MAX_DOWNLOAD_RETRY_ATTEMPTS_INT, 5);
        persistableBundle.putInt(KEY_ESIM_DOWNLOAD_RETRY_BACKOFF_TIMER_SEC_INT, 60);
        persistableBundle.putIntArray(KEY_OPPORTUNISTIC_CARRIER_IDS_INT_ARRAY, new int[]{0});
        persistableBundle.putBoolean(KEY_OPPORTUNISTIC_ESIM_DOWNLOAD_VIA_WIFI_ONLY_BOOL, false);
        persistableBundle.putInt(KEY_OPPORTUNISTIC_NETWORK_ENTRY_THRESHOLD_RSRP_INT, -108);
        persistableBundle.putInt(KEY_OPPORTUNISTIC_NETWORK_EXIT_THRESHOLD_RSRP_INT, PackageManager.INSTALL_FAILED_BAD_SIGNATURE);
        persistableBundle.putInt(KEY_OPPORTUNISTIC_NETWORK_ENTRY_THRESHOLD_RSSNR_INT, 5);
        persistableBundle.putInt(KEY_OPPORTUNISTIC_NETWORK_EXIT_THRESHOLD_RSSNR_INT, 1);
        persistableBundle.putInt(KEY_OPPORTUNISTIC_NETWORK_ENTRY_THRESHOLD_BANDWIDTH_INT, 1024);
        persistableBundle.putLong(KEY_OPPORTUNISTIC_NETWORK_ENTRY_OR_EXIT_HYSTERESIS_TIME_LONG, JobInfo.MIN_BACKOFF_MILLIS);
        persistableBundle.putLong(KEY_OPPORTUNISTIC_NETWORK_DATA_SWITCH_HYSTERESIS_TIME_LONG, JobInfo.MIN_BACKOFF_MILLIS);
        persistableBundle.putLong(KEY_OPPORTUNISTIC_NETWORK_DATA_SWITCH_EXIT_HYSTERESIS_TIME_LONG, 3000L);
        persistableBundle.putAll(OpportunisticNetwork.getDefaults());
        persistableBundle.putBoolean(KEY_PING_TEST_BEFORE_DATA_SWITCH_BOOL, true);
        persistableBundle.putBoolean(KEY_SWITCH_DATA_TO_PRIMARY_IF_PRIMARY_IS_OOS_BOOL, true);
        persistableBundle.putLong(KEY_OPPORTUNISTIC_NETWORK_PING_PONG_TIME_LONG, 60000L);
        persistableBundle.putLong(KEY_OPPORTUNISTIC_NETWORK_BACKOFF_TIME_LONG, JobInfo.MIN_BACKOFF_MILLIS);
        persistableBundle.putLong(KEY_OPPORTUNISTIC_NETWORK_MAX_BACKOFF_TIME_LONG, 60000L);
        persistableBundle.putInt(KEY_OPPORTUNISTIC_NETWORK_SCAN_DEFAULT_PERIODIC_INT, 120);
        persistableBundle.putBoolean(KEY_ENABLE_4G_OPPORTUNISTIC_NETWORK_SCAN_BOOL, true);
        persistableBundle.putLong(KEY_TIME_TO_SWITCH_BACK_TO_PRIMARY_IF_OPPORTUNISTIC_OOS_LONG, 60000L);
        persistableBundle.putLong(KEY_OPPORTUNISTIC_TIME_TO_SCAN_AFTER_CAPABILITY_SWITCH_TO_PRIMARY_LONG, TelecomManager.MEDIUM_CALL_TIME_MS);
        persistableBundle.putAll(ImsServiceEntitlement.getDefaults());
        persistableBundle.putAll(Gps.getDefaults());
        persistableBundle.putIntArray(KEY_CDMA_ENHANCED_ROAMING_INDICATOR_FOR_HOME_NETWORK_INT_ARRAY, new int[]{1});
        persistableBundle.putStringArray(KEY_EMERGENCY_NUMBER_PREFIX_STRING_ARRAY, new String[0]);
        persistableBundle.putBoolean(KEY_CARRIER_SUPPORTS_CALLER_ID_VERTICAL_SERVICE_CODES_BOOL, false);
        persistableBundle.putBoolean(KEY_USE_USIM_BOOL, false);
        persistableBundle.putBoolean(KEY_SHOW_WFC_LOCATION_PRIVACY_POLICY_BOOL, false);
        persistableBundle.putBoolean(KEY_AUTO_CANCEL_CS_REJECT_NOTIFICATION, true);
        persistableBundle.putString(KEY_SMART_FORWARDING_CONFIG_COMPONENT_NAME_STRING, "");
        persistableBundle.putBoolean(KEY_ALWAYS_SHOW_PRIMARY_SIGNAL_BAR_IN_OPPORTUNISTIC_NETWORK_BOOLEAN, false);
        persistableBundle.putString(KEY_SUBSCRIPTION_GROUP_UUID_STRING, "");
        persistableBundle.putBoolean(KEY_IS_OPPORTUNISTIC_SUBSCRIPTION_BOOL, false);
        persistableBundle.putIntArray(KEY_GSM_RSSI_THRESHOLDS_INT_ARRAY, new int[]{-107, -103, -97, -89});
        persistableBundle.putBoolean(KEY_SUPPORT_WPS_OVER_IMS_BOOL, true);
        persistableBundle.putAll(Ims.getDefaults());
        persistableBundle.putAll(ImsVoice.getDefaults());
        persistableBundle.putAll(ImsSms.getDefaults());
        persistableBundle.putAll(ImsRtt.getDefaults());
        persistableBundle.putAll(ImsEmergency.getDefaults());
        persistableBundle.putAll(ImsVt.getDefaults());
        persistableBundle.putAll(ImsWfc.getDefaults());
        persistableBundle.putAll(ImsSs.getDefaults());
        persistableBundle.putAll(Bsf.getDefaults());
        persistableBundle.putAll(Iwlan.getDefaults());
        persistableBundle.putStringArray(KEY_CARRIER_CERTIFICATE_STRING_ARRAY, new String[0]);
        persistableBundle.putBoolean(KEY_FORMAT_INCOMING_NUMBER_TO_NATIONAL_FOR_JP_BOOL, false);
        if (com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.Flags.doNotOverridePreciseLabel()) {
            persistableBundle.putIntArray(KEY_DISCONNECT_CAUSE_PLAY_BUSYTONE_INT_ARRAY, new int[0]);
        } else {
            persistableBundle.putIntArray(KEY_DISCONNECT_CAUSE_PLAY_BUSYTONE_INT_ARRAY, new int[]{4});
        }
        persistableBundle.putBoolean(KEY_PREVENT_CLIR_ACTIVATION_AND_DEACTIVATION_CODE_BOOL, false);
        persistableBundle.putLong(KEY_DATA_SWITCH_VALIDATION_TIMEOUT_LONG, 5000L);
        persistableBundle.putStringArray(KEY_MMI_TWO_DIGIT_NUMBER_PATTERN_STRING_ARRAY, new String[0]);
        persistableBundle.putInt(KEY_PARAMETERS_USED_FOR_LTE_SIGNAL_BAR_INT, 1);
        persistableBundle.putInt(KEY_MIN_UDP_PORT_4500_NAT_TIMEOUT_SEC_INT, 300);
        persistableBundle.putInt(KEY_PREFERRED_IKE_PROTOCOL_INT, -1);
        persistableBundle.putAll(Wifi.getDefaults());
        persistableBundle.putBoolean(ENABLE_EAP_METHOD_PREFIX_BOOL, false);
        persistableBundle.putInt(KEY_GBA_MODE_INT, 1);
        persistableBundle.putInt(KEY_GBA_UA_SECURITY_ORGANIZATION_INT, 1);
        persistableBundle.putInt(KEY_GBA_UA_SECURITY_PROTOCOL_INT, 65536);
        persistableBundle.putInt(KEY_GBA_UA_TLS_CIPHER_SUITE_INT, 0);
        persistableBundle.putBoolean(KEY_SHOW_FORWARDED_NUMBER_BOOL, false);
        persistableBundle.putLong("data_switch_validation_min_gap_long", TimeUnit.DAYS.toMillis(1L));
        persistableBundle.putStringArray(KEY_MISSED_INCOMING_CALL_SMS_ORIGINATOR_STRING_ARRAY, new String[0]);
        persistableBundle.putStringArray(KEY_TELEPHONY_NETWORK_CAPABILITY_PRIORITIES_STRING_ARRAY, new String[]{"eims:90", "supl:80", "mms:70", "xcap:70", "cbs:50", "mcx:50", "fota:50", "ims:40", "rcs:40", "dun:30", "enterprise:20", "internet:20", "prioritize_bandwidth:20", "prioritize_latency:20"});
        persistableBundle.putStringArray(KEY_TELEPHONY_DATA_SETUP_RETRY_RULES_STRING_ARRAY, new String[]{"capabilities=eims, retry_interval=1000, maximum_retries=20", "permanent_fail_causes=8|27|28|29|30|32|33|35|50|51|111|-5|-6|65537|65538|-3|65543|65547|2252|2253|2254, retry_interval=2500", "capabilities=mms|supl|cbs|rcs, retry_interval=2000", "capabilities=internet|enterprise|dun|ims|fota|xcap|mcx|prioritize_bandwidth|prioritize_latency, retry_interval=2500|3000|5000|10000|15000|20000|40000|60000|120000|240000|600000|1200000|1800000, maximum_retries=20"});
        persistableBundle.putStringArray(KEY_TELEPHONY_DATA_HANDOVER_RETRY_RULES_STRING_ARRAY, new String[]{"retry_interval=1000|2000|4000|8000|16000, maximum_retries=5"});
        persistableBundle.putBoolean(KEY_DELAY_IMS_TEAR_DOWN_UNTIL_CALL_END_BOOL, false);
        persistableBundle.putStringArray(KEY_MISSED_INCOMING_CALL_SMS_PATTERN_STRING_ARRAY, new String[0]);
        persistableBundle.putPersistableBundle(KEY_CARRIER_SUPPORTED_SATELLITE_SERVICES_PER_PROVIDER_BUNDLE, PersistableBundle.EMPTY);
        persistableBundle.putPersistableBundle(KEY_REGIONAL_SATELLITE_EARFCN_BUNDLE, PersistableBundle.EMPTY);
        persistableBundle.putBoolean(KEY_SATELLITE_ATTACH_SUPPORTED_BOOL, false);
        persistableBundle.putInt(KEY_SATELLITE_CONNECTION_HYSTERESIS_SEC_INT, 180);
        persistableBundle.putIntArray(KEY_NTN_LTE_RSRP_THRESHOLDS_INT_ARRAY, new int[]{-128, PackageManager.INSTALL_FAILED_BAD_SIGNATURE, -108, -98});
        persistableBundle.putIntArray(KEY_NTN_LTE_RSRQ_THRESHOLDS_INT_ARRAY, new int[]{-20, -17, -14, -11});
        persistableBundle.putIntArray(KEY_NTN_LTE_RSSNR_THRESHOLDS_INT_ARRAY, new int[]{-3, 1, 5, 13});
        persistableBundle.putInt(KEY_PARAMETERS_USED_FOR_NTN_LTE_SIGNAL_BAR_INT, 1);
        persistableBundle.putBoolean(KEY_REMOVE_SATELLITE_PLMN_IN_MANUAL_NETWORK_SCAN_BOOL, true);
        persistableBundle.putInt(KEY_SATELLITE_DATA_SUPPORT_MODE_INT, 0);
        persistableBundle.putBoolean(KEY_SATELLITE_IGNORE_DATA_ROAMING_SETTING_BOOL, false);
        persistableBundle.putBoolean(KEY_OVERRIDE_WFC_ROAMING_MODE_WHILE_USING_NTN_BOOL, true);
        persistableBundle.putInt(KEY_SATELLITE_ENTITLEMENT_STATUS_REFRESH_DAYS_INT, 7);
        persistableBundle.putBoolean(KEY_SATELLITE_ENTITLEMENT_SUPPORTED_BOOL, false);
        persistableBundle.putString(KEY_SATELLITE_ENTITLEMENT_APP_NAME_STRING, "androidSatmode");
        persistableBundle.putString(KEY_SATELLITE_INFORMATION_REDIRECT_URL_STRING, "");
        persistableBundle.putIntArray(KEY_CARRIER_ROAMING_SATELLITE_DEFAULT_SERVICES_INT_ARRAY, new int[]{3, 6});
        persistableBundle.putStringArray(KEY_SATELLITE_SUPPORTED_MSG_APPS_STRING_ARRAY, new String[]{"com.google.android.apps.messaging"});
        persistableBundle.putBoolean(KEY_DISABLE_DUN_APN_WHILE_ROAMING_WITH_PRESET_APN_BOOL, false);
        persistableBundle.putBoolean(KEY_EMERGENCY_MESSAGING_SUPPORTED_BOOL, false);
        persistableBundle.putInt(KEY_EMERGENCY_CALL_TO_SATELLITE_T911_HANDOVER_TIMEOUT_MILLIS_INT, (int) TimeUnit.SECONDS.toMillis(30L));
        persistableBundle.putString(KEY_SATELLITE_DISPLAY_NAME_STRING, "");
        persistableBundle.putBoolean(KEY_SATELLITE_ESOS_SUPPORTED_BOOL, false);
        persistableBundle.putBoolean(KEY_SATELLITE_ROAMING_P2P_SMS_SUPPORTED_BOOL, false);
        persistableBundle.putString(KEY_SATELLITE_NIDD_APN_NAME_STRING, "");
        persistableBundle.putBoolean(KEY_SATELLITE_ROAMING_TURN_OFF_SESSION_FOR_EMERGENCY_CALL_BOOL, true);
        persistableBundle.putInt(KEY_CARRIER_ROAMING_NTN_CONNECT_TYPE_INT, 0);
        persistableBundle.putInt(KEY_CARRIER_ROAMING_NTN_EMERGENCY_CALL_TO_SATELLITE_HANDOVER_TYPE_INT, 2);
        persistableBundle.putString(KEY_NRSA_ENTITLEMENT_SERVER_URL_STRING, "");
        persistableBundle.putString(KEY_NRSA_ENTITLEMENT_APP_NAME_STRING, "NrsaEntitlement");
        persistableBundle.putInt(KEY_CARRIER_SUPPORTED_SATELLITE_NOTIFICATION_HYSTERESIS_SEC_INT, 180);
        if (com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.Flags.starlinkDataBugfix()) {
            persistableBundle.putLong(KEY_SATELLITE_CONNECTED_NOTIFICATION_THROTTLE_MILLIS_INT, TimeUnit.DAYS.toMillis(7L));
        }
        persistableBundle.putInt(KEY_SATELLITE_ROAMING_SCREEN_OFF_INACTIVITY_TIMEOUT_SEC_INT, 30);
        persistableBundle.putInt(KEY_SATELLITE_ROAMING_P2P_SMS_INACTIVITY_TIMEOUT_SEC_INT, 180);
        persistableBundle.putInt(KEY_SATELLITE_ROAMING_ESOS_INACTIVITY_TIMEOUT_SEC_INT, 600);
        persistableBundle.putString(KEY_DEFAULT_PREFERRED_APN_NAME_STRING, "");
        persistableBundle.putBoolean(KEY_SUPPORTS_CALL_COMPOSER_BOOL, false);
        persistableBundle.putBoolean(KEY_SUPPORTS_BUSINESS_CALL_COMPOSER_BOOL, false);
        persistableBundle.putString(KEY_CALL_COMPOSER_PICTURE_SERVER_URL_STRING, "");
        persistableBundle.putBoolean(KEY_USE_ACS_FOR_RCS_BOOL, false);
        persistableBundle.putBoolean(KEY_NETWORK_TEMP_NOT_METERED_SUPPORTED_BOOL, true);
        persistableBundle.putInt(KEY_DEFAULT_RTT_MODE_INT, 0);
        persistableBundle.putBoolean(KEY_STORE_SIM_PIN_FOR_UNATTENDED_REBOOT_BOOL, true);
        persistableBundle.putBoolean(KEY_HIDE_ENABLE_2G, false);
        persistableBundle.putStringArray(KEY_ALLOWED_INITIAL_ATTACH_APN_TYPES_STRING_ARRAY, new String[]{"ia", "default"});
        persistableBundle.putBoolean(KEY_CARRIER_PROVISIONS_WIFI_MERGED_NETWORKS_BOOL, false);
        persistableBundle.putBoolean(KEY_USE_IP_FOR_CALLING_INDICATOR_BOOL, false);
        persistableBundle.putBoolean(KEY_DISPLAY_CALL_STRENGTH_INDICATOR_BOOL, true);
        persistableBundle.putString(KEY_CARRIER_PROVISIONING_APP_STRING, "");
        persistableBundle.putBoolean(KEY_DISPLAY_NO_DATA_NOTIFICATION_ON_PERMANENT_FAILURE_BOOL, false);
        persistableBundle.putBoolean(KEY_UNTHROTTLE_DATA_RETRY_WHEN_TAC_CHANGES_BOOL, false);
        persistableBundle.putBoolean(KEY_VONR_SETTING_VISIBILITY_BOOL, true);
        persistableBundle.putBoolean(KEY_VONR_ENABLED_BOOL, false);
        persistableBundle.putBoolean(KEY_VONR_ON_BY_DEFAULT_BOOL, true);
        persistableBundle.putIntArray(KEY_SUPPORTED_PREMIUM_CAPABILITIES_INT_ARRAY, new int[0]);
        persistableBundle.putLong(KEY_PREMIUM_CAPABILITY_NOTIFICATION_DISPLAY_TIMEOUT_MILLIS_LONG, TimeUnit.MINUTES.toMillis(30L));
        persistableBundle.putLong(KEY_PREMIUM_CAPABILITY_NOTIFICATION_BACKOFF_HYSTERESIS_TIME_MILLIS_LONG, TimeUnit.MINUTES.toMillis(30L));
        persistableBundle.putInt(KEY_PREMIUM_CAPABILITY_MAXIMUM_DAILY_NOTIFICATION_COUNT_INT, 2);
        persistableBundle.putInt(KEY_PREMIUM_CAPABILITY_MAXIMUM_MONTHLY_NOTIFICATION_COUNT_INT, 10);
        persistableBundle.putLong(KEY_PREMIUM_CAPABILITY_PURCHASE_CONDITION_BACKOFF_HYSTERESIS_TIME_MILLIS_LONG, TimeUnit.MINUTES.toMillis(30L));
        persistableBundle.putLong(KEY_PREMIUM_CAPABILITY_NETWORK_SETUP_TIME_MILLIS_LONG, TimeUnit.MINUTES.toMillis(5L));
        persistableBundle.putString(KEY_PREMIUM_CAPABILITY_PURCHASE_URL_STRING, null);
        persistableBundle.putBoolean(KEY_PREMIUM_CAPABILITY_SUPPORTED_ON_LTE_BOOL, false);
        persistableBundle.putStringArray(KEY_IWLAN_HANDOVER_POLICY_STRING_ARRAY, new String[]{"source=GERAN|UTRAN|EUTRAN|NGRAN|IWLAN, target=GERAN|UTRAN|EUTRAN|NGRAN|IWLAN, type=disallowed, capabilities=MMS|XCAP|CBS|SUPL|DUN|BIP|FOTA"});
        PersistableBundle persistableBundle2 = new PersistableBundle();
        persistableBundle2.putIntArray("NR_SA_MMWAVE", new int[]{6300, 10227, 16000, 18488, 19017});
        persistableBundle2.putIntArray(DctConstants.RAT_NAME_NR_NSA_MMWAVE, new int[]{5700, 9227, 12488, 13517, 15978});
        persistableBundle2.putIntArray(DctConstants.RAT_NAME_LTE, new int[]{3731, 5965, 8618, 11179, 13384});
        persistableBundle2.putIntArray("LTE_CA", new int[]{3831, 6065, 8718, 11379, 14484});
        persistableBundle2.putIntArray("NR_SA", new int[]{SemExtendedFormat.DataType.SPORTS_SHOT_INFO, 6795, 6955, 7562, 15484});
        persistableBundle2.putIntArray(DctConstants.RAT_NAME_NR_NSA, new int[]{2463, 6827, 8029, 9007, 15884});
        persistableBundle2.putIntArray("UMTS", new int[]{100, 169, 183, 192, 300});
        persistableBundle2.putIntArray("eHRPD", new int[]{10, 400, 600, 800, 1000});
        persistableBundle2.putIntArray("TD_SCDMA", new int[]{1, 50, 100, 500, 1000});
        persistableBundle2.putIntArray("iDEN", new int[]{1, 2, 10, 50, 100});
        persistableBundle2.putIntArray("EvDo_B", new int[]{1000, 1495, 2186, 2532, WindowManager.LayoutParams.TYPE_ONE_HAND_OP_CONTROLLER});
        persistableBundle2.putIntArray("HSPA+", new int[]{1619, 2500, 3393, 4129, 4212});
        persistableBundle2.putIntArray("HSPA", new int[]{1000, 1495, 2186, 2532, SemExtendedFormat.DataType.PRO_MODE_INFO});
        persistableBundle2.putIntArray("HSUPA", new int[]{1500, 1919, 2132, 2362, SemExtendedFormat.DataType.JPEG_360_HDR_NOTSTITCHED});
        persistableBundle2.putIntArray("HSDPA", new int[]{1500, 1732, 4000, EventMsg.UEVENT_UNZIP_PROFILE, 8000});
        persistableBundle2.putIntArray("EvDo_A", new int[]{600, 840, 1200, 1300, 1400});
        persistableBundle2.putIntArray("EvDo_0", new int[]{300, 600, 1000, 1500, 2000});
        persistableBundle2.putIntArray("1xRTT", new int[]{50, 60, 70, 80, 90});
        persistableBundle2.putIntArray("EDGE", new int[]{154, 169, 183, 192, 267});
        persistableBundle2.putIntArray("GPRS", new int[]{15, 30, 40, 45, 50});
        persistableBundle2.putIntArray("CDMA", new int[]{1, 50, 100, 300, 2000});
        persistableBundle2.putIntArray("GSM", new int[]{1, 2, 10, 50, 100});
        persistableBundle.putPersistableBundle(KEY_AUTO_DATA_SWITCH_RAT_SIGNAL_SCORE_BUNDLE, persistableBundle2);
        persistableBundle.putInt(KEY_CELLULAR_USAGE_SETTING_INT, -1);
        persistableBundle.putLongArray(KEY_DATA_STALL_RECOVERY_TIMERS_LONG_ARRAY, new long[]{180000, 180000, 180000, 180000});
        persistableBundle.putBooleanArray(KEY_DATA_STALL_RECOVERY_SHOULD_SKIP_BOOL_ARRAY, new boolean[]{false, false, true, false, false});
        persistableBundle.putStringArray(KEY_CARRIER_SERVICE_NAME_STRING_ARRAY, new String[0]);
        persistableBundle.putStringArray(KEY_CARRIER_SERVICE_NUMBER_STRING_ARRAY, new String[0]);
        persistableBundle.putIntArray(KEY_CELLULAR_SERVICE_CAPABILITIES_INT_ARRAY, new int[]{1, 2, 3});
        persistableBundle.putInt(KEY_WEAR_CONNECTIVITY_BT_TO_CELL_DELAY_MS_INT, -1);
        persistableBundle.putInt(KEY_WEAR_CONNECTIVITY_EXTEND_BT_TO_CELL_DELAY_ON_WIFI_MS_INT, -1);
        persistableBundle.putInt(KEY_SATELLITE_SOS_MAX_DATAGRAM_SIZE_BYTES_INT, 255);
    }

    @SystemApi
    public static final class Wifi {
        public static final String KEY_AVOID_5GHZ_SOFTAP_FOR_LAA_BOOL = "wifi.avoid_5ghz_softap_for_laa_bool";
        public static final String KEY_AVOID_5GHZ_WIFI_DIRECT_FOR_LAA_BOOL = "wifi.avoid_5ghz_wifi_direct_for_laa_bool";
        public static final String KEY_HOTSPOT_MAX_CLIENT_COUNT = "wifi.hotspot_maximum_client_count";
        public static final String KEY_PREFIX = "wifi.";
        public static final String KEY_SUGGESTION_SSID_LIST_WITH_MAC_RANDOMIZATION_DISABLED = "wifi.suggestion_ssid_list_with_mac_randomization_disabled";

        /* JADX INFO: Access modifiers changed from: private */
        public static PersistableBundle getDefaults() {
            PersistableBundle persistableBundle = new PersistableBundle();
            persistableBundle.putInt(KEY_HOTSPOT_MAX_CLIENT_COUNT, 0);
            persistableBundle.putStringArray(KEY_SUGGESTION_SSID_LIST_WITH_MAC_RANDOMIZATION_DISABLED, new String[0]);
            persistableBundle.putBoolean(KEY_AVOID_5GHZ_SOFTAP_FOR_LAA_BOOL, false);
            persistableBundle.putBoolean(KEY_AVOID_5GHZ_WIFI_DIRECT_FOR_LAA_BOOL, false);
            return persistableBundle;
        }

        private Wifi() {
        }
    }

    @Deprecated
    public PersistableBundle getConfigForSubId(int i) {
        if (TelephonyFeatures.IS_WIFI_ONLY) {
            return getDefaultConfig();
        }
        try {
            ICarrierConfigLoader iCarrierConfigLoader = getICarrierConfigLoader();
            if (iCarrierConfigLoader != null) {
                return iCarrierConfigLoader.getConfigForSubIdWithFeature(i, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            }
            com.android.telephony.Rlog.w(TAG, "Error getting config for subId " + i + " ICarrierConfigLoader is null");
            return null;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "Error getting config for subId " + i + ": " + e);
            return null;
        }
    }

    public PersistableBundle getConfigForSubId(int i, String... strArr) {
        Objects.requireNonNull(strArr, "Config keys should be non-null");
        for (String str : strArr) {
            Objects.requireNonNull(str, "Config key should be non-null");
        }
        if (TelephonyFeatures.IS_WIFI_ONLY) {
            return getDefaultConfig();
        }
        try {
            ICarrierConfigLoader iCarrierConfigLoader = getICarrierConfigLoader();
            if (iCarrierConfigLoader == null) {
                com.android.telephony.Rlog.w(TAG, "Error getting config for subId " + i + " ICarrierConfigLoader is null");
                throw new IllegalStateException("Carrier config loader is not available.");
            }
            return iCarrierConfigLoader.getConfigSubsetForSubIdWithFeature(i, this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), strArr);
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "Error getting config for subId " + i + ": " + e);
            e.rethrowAsRuntimeException();
            return new PersistableBundle();
        }
    }

    @SystemApi
    public void overrideConfig(int i, PersistableBundle persistableBundle) {
        overrideConfig(i, persistableBundle, false);
    }

    public void overrideConfig(int i, PersistableBundle persistableBundle, boolean z) {
        try {
            ICarrierConfigLoader iCarrierConfigLoader = getICarrierConfigLoader();
            if (iCarrierConfigLoader != null) {
                iCarrierConfigLoader.overrideConfig(i, persistableBundle, z);
                return;
            }
            com.android.telephony.Rlog.w(TAG, "Error setting config for subId " + i + " ICarrierConfigLoader is null");
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "Error setting config for subId " + i + ": " + e);
        }
    }

    @Deprecated
    public PersistableBundle getConfig() {
        return getConfigForSubId(SubscriptionManager.getDefaultSubscriptionId());
    }

    public PersistableBundle getConfig(String... strArr) {
        return getConfigForSubId(SubscriptionManager.getDefaultSubscriptionId(), strArr);
    }

    public static boolean isConfigForIdentifiedCarrier(PersistableBundle persistableBundle) {
        return persistableBundle != null && persistableBundle.getBoolean(KEY_CARRIER_CONFIG_APPLIED_BOOL);
    }

    public void notifyConfigChangedForSubId(int i) {
        try {
            ICarrierConfigLoader iCarrierConfigLoader = getICarrierConfigLoader();
            if (iCarrierConfigLoader != null) {
                iCarrierConfigLoader.notifyConfigChangedForSubId(i);
                return;
            }
            com.android.telephony.Rlog.w(TAG, "Error reloading config for subId=" + i + " ICarrierConfigLoader is null");
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "Error reloading config for subId=" + i + ": " + e);
        }
    }

    @SystemApi
    public void updateConfigForPhoneId(int i, String str) {
        try {
            ICarrierConfigLoader iCarrierConfigLoader = getICarrierConfigLoader();
            if (iCarrierConfigLoader != null) {
                iCarrierConfigLoader.updateConfigForPhoneId(i, str);
                return;
            }
            com.android.telephony.Rlog.w(TAG, "Error updating config for phoneId=" + i + " ICarrierConfigLoader is null");
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "Error updating config for phoneId=" + i + ": " + e);
        }
    }

    @SystemApi
    public String getDefaultCarrierServicePackageName() {
        try {
            ICarrierConfigLoader iCarrierConfigLoader = getICarrierConfigLoader();
            if (iCarrierConfigLoader == null) {
                com.android.telephony.Rlog.w(TAG, "getDefaultCarrierServicePackageName ICarrierConfigLoader is null");
                return "";
            }
            return iCarrierConfigLoader.getDefaultCarrierServicePackageName();
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getDefaultCarrierServicePackageName ICarrierConfigLoader is null" + e);
            e.rethrowAsRuntimeException();
            return "";
        }
    }

    @SystemApi
    public static PersistableBundle getDefaultConfig() {
        return new PersistableBundle(sDefaults);
    }

    private ICarrierConfigLoader getICarrierConfigLoader() {
        return ICarrierConfigLoader.Stub.asInterface(TelephonyFrameworkInitializer.getTelephonyServiceManager().getCarrierConfigServiceRegisterer().get());
    }

    public PersistableBundle getConfigByComponentForSubId(String str, int i) {
        PersistableBundle configForSubId = getConfigForSubId(i);
        if (configForSubId == null) {
            return null;
        }
        PersistableBundle persistableBundle = new PersistableBundle();
        for (String str2 : configForSubId.keySet()) {
            if (str2.startsWith(str)) {
                addConfig(str2, configForSubId.get(str2), persistableBundle);
            }
        }
        return persistableBundle;
    }

    private void addConfig(String str, Object obj, PersistableBundle persistableBundle) {
        if (obj instanceof String) {
            persistableBundle.putString(str, (String) obj);
            return;
        }
        if (obj instanceof String[]) {
            persistableBundle.putStringArray(str, (String[]) obj);
            return;
        }
        if (obj instanceof Integer) {
            persistableBundle.putInt(str, ((Integer) obj).intValue());
            return;
        }
        if (obj instanceof Long) {
            persistableBundle.putLong(str, ((Long) obj).longValue());
            return;
        }
        if (obj instanceof Double) {
            persistableBundle.putDouble(str, ((Double) obj).doubleValue());
            return;
        }
        if (obj instanceof Boolean) {
            persistableBundle.putBoolean(str, ((Boolean) obj).booleanValue());
            return;
        }
        if (obj instanceof int[]) {
            persistableBundle.putIntArray(str, (int[]) obj);
            return;
        }
        if (obj instanceof double[]) {
            persistableBundle.putDoubleArray(str, (double[]) obj);
            return;
        }
        if (obj instanceof boolean[]) {
            persistableBundle.putBooleanArray(str, (boolean[]) obj);
        } else if (obj instanceof long[]) {
            persistableBundle.putLongArray(str, (long[]) obj);
        } else if (obj instanceof PersistableBundle) {
            persistableBundle.putPersistableBundle(str, (PersistableBundle) obj);
        }
    }

    public void registerCarrierConfigChangeListener(Executor executor, CarrierConfigChangeListener carrierConfigChangeListener) {
        Objects.requireNonNull(executor, "Executor should be non-null.");
        Objects.requireNonNull(carrierConfigChangeListener, "Listener should be non-null.");
        TelephonyRegistryManager telephonyRegistryManager = (TelephonyRegistryManager) this.mContext.getSystemService(TelephonyRegistryManager.class);
        if (telephonyRegistryManager == null) {
            throw new IllegalStateException("Telephony registry service is null");
        }
        telephonyRegistryManager.addCarrierConfigChangedListener(executor, carrierConfigChangeListener);
    }

    public void unregisterCarrierConfigChangeListener(CarrierConfigChangeListener carrierConfigChangeListener) {
        Objects.requireNonNull(carrierConfigChangeListener, "Listener should be non-null.");
        TelephonyRegistryManager telephonyRegistryManager = (TelephonyRegistryManager) this.mContext.getSystemService(TelephonyRegistryManager.class);
        if (telephonyRegistryManager == null) {
            throw new IllegalStateException("Telephony registry service is null");
        }
        telephonyRegistryManager.removeCarrierConfigChangedListener(carrierConfigChangeListener);
    }

    public static PersistableBundle getCarrierConfigSubset(Context context, int i, String... strArr) {
        PersistableBundle persistableBundle;
        if (TelephonyFeatures.IS_WIFI_ONLY) {
            return getDefaultConfig();
        }
        CarrierConfigManager carrierConfigManager = (CarrierConfigManager) context.getSystemService(CarrierConfigManager.class);
        if (carrierConfigManager == null) {
            return new PersistableBundle();
        }
        try {
            persistableBundle = carrierConfigManager.getConfigForSubId(i, strArr);
        } catch (RuntimeException unused) {
            com.android.telephony.Rlog.w(TAG, "CarrierConfigLoader is not available.");
            persistableBundle = null;
        }
        return persistableBundle != null ? persistableBundle : new PersistableBundle();
    }

    public boolean overrideCarrierConfigFromCarrierFeature(int i) {
        int subscriptionId = SubscriptionManager.getSubscriptionId(i);
        if (!SubscriptionManager.isValidSubscriptionId(subscriptionId)) {
            com.android.telephony.Rlog.d(TAG, "overrideCarrierConfigFromCarrierFeature subId is not valid, subId: " + subscriptionId);
            return false;
        }
        String str = "carrier_config_from_carrier_feature_string" + subscriptionId;
        String str2 = "satellite_support_from_carrier_feature_bool" + subscriptionId;
        try {
            PersistableBundle configForSubId = getConfigForSubId(subscriptionId, str, str2, KEY_SATELLITE_ATTACH_SUPPORTED_BOOL);
            boolean z = configForSubId.getBoolean(KEY_SATELLITE_ATTACH_SUPPORTED_BOOL, false);
            String string = configForSubId.getString(str, "");
            boolean z2 = configForSubId.getBoolean(str2, z);
            String string2 = SemCarrierFeature.getInstance().getString(i, "CarrierFeature_RIL_UpdateCarrierConfig", "", true);
            boolean z3 = SemCarrierFeature.getInstance().getBoolean(i, "CarrierFeature_Common_Support_Satellite", false, true);
            PersistableBundle persistableBundle = new PersistableBundle();
            com.android.telephony.Rlog.d(TAG, "overrideCarrierConfigFromCarrierFeature phoneId: " + i + ", subId: " + subscriptionId + ", supportSatellite: " + z3);
            StringBuilder sb = new StringBuilder("cur config: ");
            sb.append(string);
            com.android.telephony.Rlog.d(TAG, sb.toString());
            StringBuilder sb2 = new StringBuilder("new config: ");
            sb2.append(string2);
            com.android.telephony.Rlog.d(TAG, sb2.toString());
            com.android.telephony.Rlog.d(TAG, "curSupportSatellite: " + z2 + ", newSupportSatellite: " + z3);
            if (string2.equals(string) && z2 == z3) {
                com.android.telephony.Rlog.d(TAG, "overrideCarrierConfigFromCarrierFeature skipped. value not changed.");
                return false;
            }
            if (!string2.isEmpty()) {
                CarrierIdentifier carrierIdentifierForPhoneId = getCarrierIdentifierForPhoneId(i);
                com.android.telephony.Rlog.d(TAG, "carrierIdentifier: " + carrierIdentifierForPhoneId);
                try {
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(string2.getBytes());
                    XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
                    newPullParser.setInput(byteArrayInputStream, null);
                    persistableBundle = readConfigFromXml(newPullParser, carrierIdentifierForPhoneId, "");
                } catch (IOException | XmlPullParserException e) {
                    com.android.telephony.Rlog.e(TAG, "overrideCarrierConfigFromCarrierFeature XML parsing failed: " + e);
                    return false;
                }
            }
            if (!z3) {
                persistableBundle.putBoolean(KEY_SATELLITE_ATTACH_SUPPORTED_BOOL, false);
                com.android.telephony.Rlog.d(TAG, "Disable satellite_attach_supported_bool");
            }
            overrideConfig(subscriptionId, null, true);
            persistableBundle.putString(str, string2);
            persistableBundle.putBoolean(str2, z3);
            overrideConfig(subscriptionId, persistableBundle, true);
            com.android.telephony.Rlog.d(TAG, "overrideCarrierConfigFromCarrierFeature done.");
            return true;
        } catch (Exception e2) {
            com.android.telephony.Rlog.e(TAG, "overrideCarrierConfigFromCarrierFeature getConfigForSubId failed: " + e2);
            return false;
        }
    }

    private CarrierIdentifier getCarrierIdentifierForPhoneId(int i) {
        String str;
        String str2;
        TelephonyManager createForSubscriptionId = ((TelephonyManager) this.mContext.getSystemService(TelephonyManager.class)).createForSubscriptionId(SubscriptionManager.getSubscriptionId(i));
        String subscriberId = createForSubscriptionId.getSubscriberId();
        String groupIdLevel1 = createForSubscriptionId.getGroupIdLevel1();
        String groupIdLevel2 = createForSubscriptionId.getGroupIdLevel2();
        String simOperatorNameForPhone = createForSubscriptionId.getSimOperatorNameForPhone(i);
        String simOperatorNumericForPhone = createForSubscriptionId.getSimOperatorNumericForPhone(i);
        int simCarrierId = createForSubscriptionId.getSimCarrierId();
        int simSpecificCarrierId = createForSubscriptionId.getSimSpecificCarrierId();
        if (simOperatorNumericForPhone != null && simOperatorNumericForPhone.length() >= 3) {
            str = simOperatorNumericForPhone.substring(0, 3);
            str2 = simOperatorNumericForPhone.substring(3);
        } else {
            str = "";
            str2 = str;
        }
        return new CarrierIdentifier(str, str2, simOperatorNameForPhone, subscriberId, groupIdLevel1, groupIdLevel2, simCarrierId, simSpecificCarrierId);
    }

    private PersistableBundle readConfigFromXml(XmlPullParser xmlPullParser, CarrierIdentifier carrierIdentifier, String str) throws IOException, XmlPullParserException {
        PersistableBundle persistableBundle = new PersistableBundle();
        if (xmlPullParser != null) {
            while (true) {
                int next = xmlPullParser.next();
                if (next == 1) {
                    break;
                }
                if (next == 2 && "carrier_config".equals(xmlPullParser.getName()) && checkFilters(xmlPullParser, carrierIdentifier, str)) {
                    persistableBundle.putAll(PersistableBundle.restoreFromXml(xmlPullParser));
                }
            }
        }
        return persistableBundle;
    }

    /* JADX WARN: Code restructure failed: missing block: B:51:0x0100, code lost:
    
        if (matchOnImsi(r6, r11) == false) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x010e, code lost:
    
        if (r6.equalsIgnoreCase(r11.getGid2()) == false) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x011b, code lost:
    
        if (r6.equalsIgnoreCase(r11.getGid1()) == false) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0124, code lost:
    
        if (matchOnSP(r6, r11) == false) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0136, code lost:
    
        if (r6.equals(r11.getMnc()) == false) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0143, code lost:
    
        if (r6.equals(r11.getMcc()) == false) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x015a, code lost:
    
        if (java.lang.Integer.parseInt(r6) != r11.getSpecificCarrierId()) goto L62;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean checkFilters(org.xmlpull.v1.XmlPullParser r10, android.service.carrier.CarrierIdentifier r11, java.lang.String r12) {
        /*
            Method dump skipped, instructions count: 458
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.telephony.CarrierConfigManager.checkFilters(org.xmlpull.v1.XmlPullParser, android.service.carrier.CarrierIdentifier, java.lang.String):boolean");
    }

    private boolean matchOnImsi(String str, CarrierIdentifier carrierIdentifier) {
        String imsi = carrierIdentifier.getImsi();
        if (imsi != null) {
            return Pattern.compile(str, 2).matcher(imsi).matches();
        }
        return false;
    }

    private boolean matchOnSP(String str, CarrierIdentifier carrierIdentifier) {
        String spn = carrierIdentifier.getSpn();
        if ("null".equalsIgnoreCase(str)) {
            return TextUtils.isEmpty(spn);
        }
        if (spn != null) {
            return Pattern.compile(str, 2).matcher(spn).matches();
        }
        return false;
    }
}
