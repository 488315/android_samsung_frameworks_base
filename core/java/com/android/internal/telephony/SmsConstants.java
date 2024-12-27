package com.android.internal.telephony;

public class SmsConstants {
    public static final byte DCN_STATUS_CS_ONLY = 0;
    public static final byte DCN_STATUS_EMERGENCY_CALL_START = 16;
    public static final byte DCN_STATUS_EMERGENCY_CALL_STOP = 32;
    public static final int EMERGENCY_MODE_CANCELED = 3;
    public static final int EMERGENCY_MODE_DEFAULT = 0;
    public static final int EMERGENCY_MODE_END = 2;
    public static final int EMERGENCY_MODE_END_WITH_ECBM = 5;
    public static final int EMERGENCY_MODE_FAILED = 4;
    public static final int EMERGENCY_MODE_START = 1;
    public static final int EMERGENCY_SMS_CALLBACK_MODE_ENTER = 1;
    public static final int EMERGENCY_SMS_CALLBACK_MODE_EXIT = 0;
    public static final int ENCODING_16BIT = 3;
    public static final int ENCODING_7BIT = 1;
    public static final int ENCODING_8BIT = 2;
    public static final int ENCODING_KSC5601 = 4;
    public static final int ENCODING_UNKNOWN = 0;
    public static final String FORMAT_3GPP = "3gpp";
    public static final String FORMAT_3GPP2 = "3gpp2";
    public static final String FORMAT_UNKNOWN = "unknown";
    public static final int MAX_DATA_LEN_WITH_SEGMENT_SEPERATOR = 154;
    public static final int MAX_USER_DATA_BYTES = 140;
    public static final int MAX_USER_DATA_BYTES_WITH_HEADER = 134;
    public static final int MAX_USER_DATA_SEPTETS = 160;
    public static final int MAX_USER_DATA_SEPTETS_WITH_HEADER = 153;
    public static final String SMS_3GPP2_LGT_NETWORK = "sms_3gpp2_lgt_network";
    public static final String SMS_ALLOW_EMAIL_SMS_ADDRESS = "sms_allow_email_sms_address";
    public static final String SMS_CDMA_COPY_TO_RUIM = "sms_cdma_copy_to_ruim";
    public static final String SMS_CDMA_SENT_FAIL_DISPLAY = "sms_cdma_sent_fail_display";
    public static final String SMS_CHECK_DUPLICATE_PORT_OMADM_WAPPUSH =
            "sms_check_duplicate_port_omadm_wappush";
    public static final String SMS_CHECK_ECM_MODE = "sms_check_ecm_mode";
    public static final String SMS_DISABLE_SMS_VOICEMAIL = "sms_disable_sms_voicemail";
    public static final String SMS_DISPLAY_POLICY_PARTIAL_LONG_SMS =
            "sms_display_policy_partial_long_sms";
    public static final String SMS_ECM_INCOMING_SMS = "sms_ecm_incoming_sms";
    public static final String SMS_ERROR_CLASS_RETRY = "sms_error_class_retry";
    public static final String SMS_GLOBAL_MODE_SMS_ADDRESS_RULE =
            "sms_global_mode_sms_address_rule";
    public static final String SMS_LINK_WARNING_INDICATION = "sms_link_warning_indication";
    public static final String SMS_MAX_RETRIES_ONE = "sms_max_retries_one";
    public static final String SMS_MAX_RETRIES_ZERO = "sms_max_retries_zero";
    public static final String SMS_MMS_UAP_BUILD_ID = "sms_mms_uap_build_id";
    public static final String SMS_NETWORK_SEARCH_FOR_E911 = "sms_network_search_for_e911";
    public static final String SMS_NOT_COUNT_VOICEMAIL = "sms_not_count_voicemail";
    public static final String SMS_NOT_RECEIVE_CMAS_WITHOUT_SIM =
            "sms_not_receive_cmas_without_sim";
    public static final String SMS_NOT_USED_VALIDITY_PERIOD_FORMAT =
            "sms_not_used_validity_period_format";
    public static final String SMS_NSRI_SECURITY_SOLUTION = "sms_nsri_security_solution";
    public static final String SMS_QMI_CDMA_GSM = "sms_qmi_cdma_gsm";
    public static final String SMS_READ_CONFIRM = "sms_read_confirm";
    public static final String SMS_RECEIVE_SMS_WITHOUT_SMSCAPABLE =
            "sms_receive_sms_without_smscapable";
    public static final String SMS_RP_SMMA_NOT_SUPPORTED = "sms_rp_smma_not_supported";
    public static final String SMS_SAFE_MESSAGE_INDICATION = "sms_safe_message_indication";
    public static final String SMS_SEGMENTED_SMS = "sms_segmented_sms";
    public static final String SMS_SHOW_HIDDEN_MENU_SMS_PREF_MODE =
            "sms_show_hidden_menu_sms_pref_mode";
    public static final String SMS_SMSP = "sms_smsp ";
    public static final String SMS_SPECIAL_ADDRESS_HANDLING_FOR =
            "sms_special_address_handling_for";
    public static final String SMS_SUPPORT_GSM_8BIT_SMS = "sms_support_gsm_8bit_sms";
    public static final String SMS_SUPPORT_KSC5601 = "sms_support_ksc5601";
    public static final String SMS_SUPPORT_REPLY_ADDRESS = "sms_support_reply_address";
    public static final String SMS_WAP_PUSH_FORMAT_SMS = "sms_wap_push_format_sms";
    public static final String SMS_WRITE_UICC_SUPPORTED = "sms_write_uicc_supported";

    public enum MessageClass {
        UNKNOWN,
        CLASS_0,
        CLASS_1,
        CLASS_2,
        CLASS_3
    }
}
