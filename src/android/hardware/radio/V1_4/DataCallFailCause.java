package android.hardware.radio.V1_4;

import android.security.keystore.KeyProperties;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class DataCallFailCause {
    public static final int ACCESS_ATTEMPT_ALREADY_IN_PROGRESS = 2219;
    public static final int ACCESS_BLOCK = 2087;
    public static final int ACCESS_BLOCK_ALL = 2088;
    public static final int ACCESS_CLASS_DSAC_REJECTION = 2108;
    public static final int ACCESS_CONTROL_LIST_CHECK_FAILURE = 2128;
    public static final int ACTIVATION_REJECTED_BCM_VIOLATION = 48;
    public static final int ACTIVATION_REJECT_GGSN = 30;
    public static final int ACTIVATION_REJECT_UNSPECIFIED = 31;
    public static final int APN_DISABLED = 2045;
    public static final int APN_DISALLOWED_ON_ROAMING = 2059;
    public static final int APN_MISMATCH = 2054;
    public static final int APN_PARAMETERS_CHANGED = 2060;
    public static final int APN_PENDING_HANDOVER = 2041;
    public static final int APN_TYPE_CONFLICT = 112;
    public static final int AUTH_FAILURE_ON_EMERGENCY_CALL = 122;
    public static final int BEARER_HANDLING_NOT_SUPPORTED = 60;
    public static final int CALL_DISALLOWED_IN_ROAMING = 2068;
    public static final int CALL_PREEMPT_BY_EMERGENCY_APN = 127;
    public static final int CANNOT_ENCODE_OTA_MESSAGE = 2159;
    public static final int CDMA_ALERT_STOP = 2077;
    public static final int CDMA_INCOMING_CALL = 2076;
    public static final int CDMA_INTERCEPT = 2073;
    public static final int CDMA_LOCK = 2072;
    public static final int CDMA_RELEASE_DUE_TO_SO_REJECTION = 2075;
    public static final int CDMA_REORDER = 2074;
    public static final int CDMA_RETRY_ORDER = 2086;
    public static final int CHANNEL_ACQUISITION_FAILURE = 2078;
    public static final int CLOSE_IN_PROGRESS = 2030;
    public static final int COLLISION_WITH_NETWORK_INITIATED_REQUEST = 56;
    public static final int COMPANION_IFACE_IN_USE = 118;
    public static final int CONCURRENT_SERVICES_INCOMPATIBLE = 2083;
    public static final int CONCURRENT_SERVICES_NOT_ALLOWED = 2091;
    public static final int CONCURRENT_SERVICE_NOT_SUPPORTED_BY_BASE_STATION = 2080;
    public static final int CONDITIONAL_IE_ERROR = 100;
    public static final int CONGESTION = 2106;
    public static final int CONNECTION_RELEASED = 2113;
    public static final int CS_DOMAIN_NOT_AVAILABLE = 2181;
    public static final int CS_FALLBACK_CALL_ESTABLISHMENT_NOT_ALLOWED = 2188;
    public static final int DATA_PLAN_EXPIRED = 2198;
    public static final int DATA_REGISTRATION_FAIL = -2;
    public static final int DATA_ROAMING_SETTINGS_DISABLED = 2064;
    public static final int DATA_SETTINGS_DISABLED = 2063;
    public static final int DBM_OR_SMS_IN_PROGRESS = 2211;
    public static final int DDS_SWITCHED = 2065;
    public static final int DDS_SWITCH_IN_PROGRESS = 2067;
    public static final int DRB_RELEASED_BY_RRC = 2112;
    public static final int DS_EXPLICIT_DEACTIVATION = 2125;
    public static final int DUAL_SWITCH = 2227;
    public static final int DUN_CALL_DISALLOWED = 2056;
    public static final int DUPLICATE_BEARER_ID = 2118;
    public static final int EHRPD_TO_HRPD_FALLBACK = 2049;
    public static final int EMBMS_NOT_ENABLED = 2193;
    public static final int EMBMS_REGULAR_DEACTIVATION = 2195;
    public static final int EMERGENCY_IFACE_ONLY = 116;
    public static final int EMERGENCY_MODE = 2221;
    public static final int EMM_ACCESS_BARRED = 115;
    public static final int EMM_ACCESS_BARRED_INFINITE_RETRY = 121;
    public static final int EMM_ATTACH_FAILED = 2115;
    public static final int EMM_ATTACH_STARTED = 2116;
    public static final int EMM_DETACHED = 2114;
    public static final int EMM_T3417_EXPIRED = 2130;
    public static final int EMM_T3417_EXT_EXPIRED = 2131;
    public static final int EPS_SERVICES_AND_NON_EPS_SERVICES_NOT_ALLOWED = 2178;
    public static final int EPS_SERVICES_NOT_ALLOWED_IN_PLMN = 2179;
    public static final int ERROR_UNSPECIFIED = 65535;
    public static final int ESM_BAD_OTA_MESSAGE = 2122;
    public static final int ESM_BEARER_DEACTIVATED_TO_SYNC_WITH_NETWORK = 2120;
    public static final int ESM_COLLISION_SCENARIOS = 2119;
    public static final int ESM_CONTEXT_TRANSFERRED_DUE_TO_IRAT = 2124;
    public static final int ESM_DOWNLOAD_SERVER_REJECTED_THE_CALL = 2123;
    public static final int ESM_FAILURE = 2182;
    public static final int ESM_INFO_NOT_RECEIVED = 53;
    public static final int ESM_LOCAL_CAUSE_NONE = 2126;
    public static final int ESM_NW_ACTIVATED_DED_BEARER_WITH_ID_OF_DEF_BEARER = 2121;
    public static final int ESM_PROCEDURE_TIME_OUT = 2155;
    public static final int ESM_UNKNOWN_EPS_BEARER_CONTEXT = 2111;
    public static final int EVDO_CONNECTION_DENY_BY_BILLING_OR_AUTHENTICATION_FAILURE = 2201;
    public static final int EVDO_CONNECTION_DENY_BY_GENERAL_OR_NETWORK_BUSY = 2200;
    public static final int EVDO_HDR_CHANGED = 2202;
    public static final int EVDO_HDR_CONNECTION_SETUP_TIMEOUT = 2206;
    public static final int EVDO_HDR_EXITED = 2203;
    public static final int EVDO_HDR_NO_SESSION = 2204;
    public static final int EVDO_USING_GPS_FIX_INSTEAD_OF_HDR_CALL = 2205;
    public static final int FADE = 2217;
    public static final int FAILED_TO_ACQUIRE_COLOCATED_HDR = 2207;
    public static final int FEATURE_NOT_SUPP = 40;
    public static final int FILTER_SEMANTIC_ERROR = 44;
    public static final int FILTER_SYTAX_ERROR = 45;
    public static final int FORBIDDEN_APN_NAME = 2066;
    public static final int GPRS_SERVICES_AND_NON_GPRS_SERVICES_NOT_ALLOWED = 2097;
    public static final int GPRS_SERVICES_NOT_ALLOWED = 2098;
    public static final int GPRS_SERVICES_NOT_ALLOWED_IN_THIS_PLMN = 2103;
    public static final int HANDOFF_PREFERENCE_CHANGED = 2251;
    public static final int HDR_ACCESS_FAILURE = 2213;
    public static final int HDR_FADE = 2212;
    public static final int HDR_NO_LOCK_GRANTED = 2210;
    public static final int IFACE_AND_POL_FAMILY_MISMATCH = 120;
    public static final int IFACE_MISMATCH = 117;
    public static final int ILLEGAL_ME = 2096;
    public static final int ILLEGAL_MS = 2095;
    public static final int IMEI_NOT_ACCEPTED = 2177;
    public static final int IMPLICITLY_DETACHED = 2100;
    public static final int IMSI_UNKNOWN_IN_HOME_SUBSCRIBER_SERVER = 2176;
    public static final int INCOMING_CALL_REJECTED = 2092;
    public static final int INSUFFICIENT_RESOURCES = 26;
    public static final int INTERFACE_IN_USE = 2058;
    public static final int INTERNAL_CALL_PREEMPT_BY_HIGH_PRIO_APN = 114;
    public static final int INTERNAL_EPC_NONEPC_TRANSITION = 2057;
    public static final int INVALID_CONNECTION_ID = 2156;
    public static final int INVALID_DNS_ADDR = 123;
    public static final int INVALID_EMM_STATE = 2190;
    public static final int INVALID_MANDATORY_INFO = 96;
    public static final int INVALID_MODE = 2223;
    public static final int INVALID_PCSCF_ADDR = 113;
    public static final int INVALID_PCSCF_OR_DNS_ADDRESS = 124;
    public static final int INVALID_PRIMARY_NSAPI = 2158;
    public static final int INVALID_SIM_STATE = 2224;
    public static final int INVALID_TRANSACTION_ID = 81;
    public static final int IPV6_ADDRESS_TRANSFER_FAILED = 2047;
    public static final int IPV6_PREFIX_UNAVAILABLE = 2250;
    public static final int IP_ADDRESS_MISMATCH = 119;
    public static final int IP_VERSION_MISMATCH = 2055;
    public static final int IRAT_HANDOVER_FAILED = 2194;
    public static final int IS707B_MAX_ACCESS_PROBES = 2089;
    public static final int LIMITED_TO_IPV4 = 2234;
    public static final int LIMITED_TO_IPV6 = 2235;
    public static final int LLC_SNDCP = 25;
    public static final int LOCAL_END = 2215;
    public static final int LOCATION_AREA_NOT_ALLOWED = 2102;
    public static final int LOWER_LAYER_REGISTRATION_FAILURE = 2197;
    public static final int LOW_POWER_MODE_OR_POWERING_DOWN = 2044;
    public static final int LTE_NAS_SERVICE_REQUEST_FAILED = 2117;
    public static final int LTE_THROTTLING_NOT_REQUIRED = 2127;
    public static final int MAC_FAILURE = 2183;
    public static final int MAXIMIUM_NSAPIS_EXCEEDED = 2157;
    public static final int MAXINUM_SIZE_OF_L2_MESSAGE_EXCEEDED = 2166;
    public static final int MAX_ACCESS_PROBE = 2079;
    public static final int MAX_ACTIVE_PDP_CONTEXT_REACHED = 65;
    public static final int MAX_IPV4_CONNECTIONS = 2052;
    public static final int MAX_IPV6_CONNECTIONS = 2053;
    public static final int MAX_PPP_INACTIVITY_TIMER_EXPIRED = 2046;
    public static final int MESSAGE_INCORRECT_SEMANTIC = 95;
    public static final int MESSAGE_TYPE_UNSUPPORTED = 97;
    public static final int MIP_CONFIG_FAILURE = 2050;
    public static final int MIP_FA_ADMIN_PROHIBITED = 2001;
    public static final int MIP_FA_DELIVERY_STYLE_NOT_SUPPORTED = 2012;
    public static final int MIP_FA_ENCAPSULATION_UNAVAILABLE = 2008;
    public static final int MIP_FA_HOME_AGENT_AUTHENTICATION_FAILURE = 2004;
    public static final int MIP_FA_INSUFFICIENT_RESOURCES = 2002;
    public static final int MIP_FA_MALFORMED_REPLY = 2007;
    public static final int MIP_FA_MALFORMED_REQUEST = 2006;
    public static final int MIP_FA_MISSING_CHALLENGE = 2017;
    public static final int MIP_FA_MISSING_HOME_ADDRESS = 2015;
    public static final int MIP_FA_MISSING_HOME_AGENT = 2014;
    public static final int MIP_FA_MISSING_NAI = 2013;
    public static final int MIP_FA_MOBILE_NODE_AUTHENTICATION_FAILURE = 2003;
    public static final int MIP_FA_REASON_UNSPECIFIED = 2000;
    public static final int MIP_FA_REQUESTED_LIFETIME_TOO_LONG = 2005;
    public static final int MIP_FA_REVERSE_TUNNEL_IS_MANDATORY = 2011;
    public static final int MIP_FA_REVERSE_TUNNEL_UNAVAILABLE = 2010;
    public static final int MIP_FA_STALE_CHALLENGE = 2018;
    public static final int MIP_FA_UNKNOWN_CHALLENGE = 2016;
    public static final int MIP_FA_VJ_HEADER_COMPRESSION_UNAVAILABLE = 2009;
    public static final int MIP_HA_ADMIN_PROHIBITED = 2020;
    public static final int MIP_HA_ENCAPSULATION_UNAVAILABLE = 2029;
    public static final int MIP_HA_FOREIGN_AGENT_AUTHENTICATION_FAILURE = 2023;
    public static final int MIP_HA_INSUFFICIENT_RESOURCES = 2021;
    public static final int MIP_HA_MALFORMED_REQUEST = 2025;
    public static final int MIP_HA_MOBILE_NODE_AUTHENTICATION_FAILURE = 2022;
    public static final int MIP_HA_REASON_UNSPECIFIED = 2019;
    public static final int MIP_HA_REGISTRATION_ID_MISMATCH = 2024;
    public static final int MIP_HA_REVERSE_TUNNEL_IS_MANDATORY = 2028;
    public static final int MIP_HA_REVERSE_TUNNEL_UNAVAILABLE = 2027;
    public static final int MIP_HA_UNKNOWN_HOME_AGENT_ADDRESS = 2026;
    public static final int MISSING_UKNOWN_APN = 27;
    public static final int MODEM_APP_PREEMPTED = 2032;
    public static final int MODEM_RESTART = 2037;
    public static final int MSC_TEMPORARILY_NOT_REACHABLE = 2180;
    public static final int MSG_AND_PROTOCOL_STATE_UNCOMPATIBLE = 101;
    public static final int MSG_TYPE_NONCOMPATIBLE_STATE = 98;
    public static final int MS_IDENTITY_CANNOT_BE_DERIVED_BY_THE_NETWORK = 2099;
    public static final int MULTIPLE_PDP_CALL_NOT_ALLOWED = 2192;
    public static final int MULTI_CONN_TO_SAME_PDN_NOT_ALLOWED = 55;
    public static final int NAS_LAYER_FAILURE = 2191;
    public static final int NAS_REQUEST_REJECTED_BY_NETWORK = 2167;
    public static final int NAS_SIGNALLING = 14;
    public static final int NETWORK_FAILURE = 38;
    public static final int NETWORK_INITIATED_DETACH_NO_AUTO_REATTACH = 2154;
    public static final int NETWORK_INITIATED_DETACH_WITH_AUTO_REATTACH = 2153;
    public static final int NETWORK_INITIATED_TERMINATION = 2031;
    public static final int NONE = 0;
    public static final int NON_IP_NOT_SUPPORTED = 2069;
    public static final int NORMAL_RELEASE = 2218;
    public static final int NO_CDMA_SERVICE = 2084;
    public static final int NO_COLLOCATED_HDR = 2225;
    public static final int NO_EPS_BEARER_CONTEXT_ACTIVATED = 2189;
    public static final int NO_GPRS_CONTEXT = 2094;
    public static final int NO_HYBRID_HDR_SERVICE = 2209;
    public static final int NO_PDP_CONTEXT_ACTIVATED = 2107;
    public static final int NO_RESPONSE_FROM_BASE_STATION = 2081;
    public static final int NO_SERVICE = 2216;
    public static final int NO_SERVICE_ON_GATEWAY = 2093;
    public static final int NSAPI_IN_USE = 35;
    public static final int NULL_APN_DISALLOWED = 2061;
    public static final int OEM_DCFAILCAUSE_1 = 4097;
    public static final int OEM_DCFAILCAUSE_10 = 4106;
    public static final int OEM_DCFAILCAUSE_11 = 4107;
    public static final int OEM_DCFAILCAUSE_12 = 4108;
    public static final int OEM_DCFAILCAUSE_13 = 4109;
    public static final int OEM_DCFAILCAUSE_14 = 4110;
    public static final int OEM_DCFAILCAUSE_15 = 4111;
    public static final int OEM_DCFAILCAUSE_2 = 4098;
    public static final int OEM_DCFAILCAUSE_3 = 4099;
    public static final int OEM_DCFAILCAUSE_4 = 4100;
    public static final int OEM_DCFAILCAUSE_5 = 4101;
    public static final int OEM_DCFAILCAUSE_6 = 4102;
    public static final int OEM_DCFAILCAUSE_7 = 4103;
    public static final int OEM_DCFAILCAUSE_8 = 4104;
    public static final int OEM_DCFAILCAUSE_9 = 4105;
    public static final int ONLY_IPV4V6_ALLOWED = 57;
    public static final int ONLY_IPV4_ALLOWED = 50;
    public static final int ONLY_IPV6_ALLOWED = 51;
    public static final int ONLY_NON_IP_ALLOWED = 58;
    public static final int ONLY_SINGLE_BEARER_ALLOWED = 52;
    public static final int OPERATOR_BARRED = 8;
    public static final int OTASP_COMMIT_IN_PROGRESS = 2208;
    public static final int PDN_CONN_DOES_NOT_EXIST = 54;
    public static final int PDN_INACTIVITY_TIMER_EXPIRED = 2051;
    public static final int PDN_IPV4_CALL_DISALLOWED = 2033;
    public static final int PDN_IPV4_CALL_THROTTLED = 2034;
    public static final int PDN_IPV6_CALL_DISALLOWED = 2035;
    public static final int PDN_IPV6_CALL_THROTTLED = 2036;
    public static final int PDN_NON_IP_CALL_DISALLOWED = 2071;
    public static final int PDN_NON_IP_CALL_THROTTLED = 2070;
    public static final int PDP_ACTIVATE_MAX_RETRY_FAILED = 2109;
    public static final int PDP_DUPLICATE = 2104;
    public static final int PDP_ESTABLISH_TIMEOUT_EXPIRED = 2161;
    public static final int PDP_INACTIVE_TIMEOUT_EXPIRED = 2163;
    public static final int PDP_LOWERLAYER_ERROR = 2164;
    public static final int PDP_MODIFY_COLLISION = 2165;
    public static final int PDP_MODIFY_TIMEOUT_EXPIRED = 2162;
    public static final int PDP_PPP_NOT_SUPPORTED = 2038;
    public static final int PDP_WITHOUT_ACTIVE_TFT = 46;
    public static final int PHONE_IN_USE = 2222;
    public static final int PHYSICAL_LINK_CLOSE_IN_PROGRESS = 2040;
    public static final int PLMN_NOT_ALLOWED = 2101;
    public static final int PPP_AUTH_FAILURE = 2229;
    public static final int PPP_CHAP_FAILURE = 2232;
    public static final int PPP_CLOSE_IN_PROGRESS = 2233;
    public static final int PPP_OPTION_MISMATCH = 2230;
    public static final int PPP_PAP_FAILURE = 2231;
    public static final int PPP_TIMEOUT = 2228;
    public static final int PREF_RADIO_TECH_CHANGED = -4;
    public static final int PROFILE_BEARER_INCOMPATIBLE = 2042;
    public static final int PROTOCOL_ERRORS = 111;
    public static final int QOS_NOT_ACCEPTED = 37;
    public static final int RADIO_ACCESS_BEARER_FAILURE = 2110;
    public static final int RADIO_ACCESS_BEARER_SETUP_FAILURE = 2160;
    public static final int RADIO_POWER_OFF = -5;
    public static final int REDIRECTION_OR_HANDOFF_IN_PROGRESS = 2220;
    public static final int REGULAR_DEACTIVATION = 36;
    public static final int REJECTED_BY_BASE_STATION = 2082;
    public static final int RRC_CONNECTION_ABORTED_AFTER_HANDOVER = 2173;
    public static final int RRC_CONNECTION_ABORTED_AFTER_IRAT_CELL_CHANGE = 2174;
    public static final int RRC_CONNECTION_ABORTED_DUE_TO_IRAT_CHANGE = 2171;
    public static final int RRC_CONNECTION_ABORTED_DURING_IRAT_CELL_CHANGE = 2175;
    public static final int RRC_CONNECTION_ABORT_REQUEST = 2151;
    public static final int RRC_CONNECTION_ACCESS_BARRED = 2139;
    public static final int RRC_CONNECTION_ACCESS_STRATUM_FAILURE = 2137;
    public static final int RRC_CONNECTION_ANOTHER_PROCEDURE_IN_PROGRESS = 2138;
    public static final int RRC_CONNECTION_CELL_NOT_CAMPED = 2144;
    public static final int RRC_CONNECTION_CELL_RESELECTION = 2140;
    public static final int RRC_CONNECTION_CONFIG_FAILURE = 2141;
    public static final int RRC_CONNECTION_INVALID_REQUEST = 2168;
    public static final int RRC_CONNECTION_LINK_FAILURE = 2143;
    public static final int RRC_CONNECTION_NORMAL_RELEASE = 2147;
    public static final int RRC_CONNECTION_OUT_OF_SERVICE_DURING_CELL_REGISTER = 2150;
    public static final int RRC_CONNECTION_RADIO_LINK_FAILURE = 2148;
    public static final int RRC_CONNECTION_REESTABLISHMENT_FAILURE = 2149;
    public static final int RRC_CONNECTION_REJECT_BY_NETWORK = 2146;
    public static final int RRC_CONNECTION_RELEASED_SECURITY_NOT_ACTIVE = 2172;
    public static final int RRC_CONNECTION_RF_UNAVAILABLE = 2170;
    public static final int RRC_CONNECTION_SYSTEM_INFORMATION_BLOCK_READ_ERROR = 2152;
    public static final int RRC_CONNECTION_SYSTEM_INTERVAL_FAILURE = 2145;
    public static final int RRC_CONNECTION_TIMER_EXPIRED = 2142;
    public static final int RRC_CONNECTION_TRACKING_AREA_ID_CHANGED = 2169;
    public static final int RRC_UPLINK_CONNECTION_RELEASE = 2134;
    public static final int RRC_UPLINK_DATA_TRANSMISSION_FAILURE = 2132;
    public static final int RRC_UPLINK_DELIVERY_FAILED_DUE_TO_HANDOVER = 2133;
    public static final int RRC_UPLINK_ERROR_REQUEST_FROM_NAS = 2136;
    public static final int RRC_UPLINK_RADIO_LINK_FAILURE = 2135;
    public static final int RUIM_NOT_PRESENT = 2085;
    public static final int SECURITY_MODE_REJECTED = 2186;
    public static final int SERVICE_NOT_ALLOWED_ON_PLMN = 2129;
    public static final int SERVICE_OPTION_NOT_SUBSCRIBED = 33;
    public static final int SERVICE_OPTION_NOT_SUPPORTED = 32;
    public static final int SERVICE_OPTION_OUT_OF_ORDER = 34;
    public static final int SIGNAL_LOST = -3;
    public static final int SIM_CARD_CHANGED = 2043;
    public static final int SYNCHRONIZATION_FAILURE = 2184;
    public static final int TEST_LOOPBACK_REGULAR_DEACTIVATION = 2196;
    public static final int TETHERED_CALL_ACTIVE = -6;
    public static final int TFT_SEMANTIC_ERROR = 41;
    public static final int TFT_SYTAX_ERROR = 42;
    public static final int THERMAL_EMERGENCY = 2090;
    public static final int THERMAL_MITIGATION = 2062;
    public static final int TRAT_SWAP_FAILED = 2048;
    public static final int UE_INITIATED_DETACH_OR_DISCONNECT = 128;
    public static final int UE_IS_ENTERING_POWERSAVE_MODE = 2226;
    public static final int UE_RAT_CHANGE = 2105;
    public static final int UE_SECURITY_CAPABILITIES_MISMATCH = 2185;
    public static final int UMTS_HANDOVER_TO_IWLAN = 2199;
    public static final int UMTS_REACTIVATION_REQ = 39;
    public static final int UNACCEPTABLE_NON_EPS_AUTHENTICATION = 2187;
    public static final int UNKNOWN_INFO_ELEMENT = 99;
    public static final int UNKNOWN_PDP_ADDRESS_TYPE = 28;
    public static final int UNKNOWN_PDP_CONTEXT = 43;
    public static final int UNPREFERRED_RAT = 2039;
    public static final int UNSUPPORTED_1X_PREV = 2214;
    public static final int UNSUPPORTED_APN_IN_CURRENT_PLMN = 66;
    public static final int UNSUPPORTED_QCI_VALUE = 59;
    public static final int USER_AUTHENTICATION = 29;
    public static final int VOICE_REGISTRATION_FAIL = -1;
    public static final int VSNCP_ADMINISTRATIVELY_PROHIBITED = 2245;
    public static final int VSNCP_APN_UNATHORIZED = 2238;
    public static final int VSNCP_GEN_ERROR = 2237;
    public static final int VSNCP_INSUFFICIENT_PARAMETERS = 2243;
    public static final int VSNCP_NO_PDN_GATEWAY_ADDRESS = 2240;
    public static final int VSNCP_PDN_EXISTS_FOR_THIS_APN = 2248;
    public static final int VSNCP_PDN_GATEWAY_REJECT = 2242;
    public static final int VSNCP_PDN_GATEWAY_UNREACHABLE = 2241;
    public static final int VSNCP_PDN_ID_IN_USE = 2246;
    public static final int VSNCP_PDN_LIMIT_EXCEEDED = 2239;
    public static final int VSNCP_RECONNECT_NOT_ALLOWED = 2249;
    public static final int VSNCP_RESOURCE_UNAVAILABLE = 2244;
    public static final int VSNCP_SUBSCRIBER_LIMITATION = 2247;
    public static final int VSNCP_TIMEOUT = 2236;

    public static final String toString(int i) {
        if (i == 0) {
            return KeyProperties.DIGEST_NONE;
        }
        if (i == 8) {
            return "OPERATOR_BARRED";
        }
        if (i == 14) {
            return "NAS_SIGNALLING";
        }
        if (i == 26) {
            return "INSUFFICIENT_RESOURCES";
        }
        if (i == 27) {
            return "MISSING_UKNOWN_APN";
        }
        if (i == 28) {
            return "UNKNOWN_PDP_ADDRESS_TYPE";
        }
        if (i == 29) {
            return "USER_AUTHENTICATION";
        }
        if (i == 30) {
            return "ACTIVATION_REJECT_GGSN";
        }
        if (i == 31) {
            return "ACTIVATION_REJECT_UNSPECIFIED";
        }
        if (i == 32) {
            return "SERVICE_OPTION_NOT_SUPPORTED";
        }
        if (i == 33) {
            return "SERVICE_OPTION_NOT_SUBSCRIBED";
        }
        if (i == 34) {
            return "SERVICE_OPTION_OUT_OF_ORDER";
        }
        if (i == 35) {
            return "NSAPI_IN_USE";
        }
        if (i == 36) {
            return "REGULAR_DEACTIVATION";
        }
        if (i == 37) {
            return "QOS_NOT_ACCEPTED";
        }
        if (i == 38) {
            return "NETWORK_FAILURE";
        }
        if (i == 39) {
            return "UMTS_REACTIVATION_REQ";
        }
        if (i == 40) {
            return "FEATURE_NOT_SUPP";
        }
        if (i == 41) {
            return "TFT_SEMANTIC_ERROR";
        }
        if (i == 42) {
            return "TFT_SYTAX_ERROR";
        }
        if (i == 43) {
            return "UNKNOWN_PDP_CONTEXT";
        }
        if (i == 44) {
            return "FILTER_SEMANTIC_ERROR";
        }
        if (i == 45) {
            return "FILTER_SYTAX_ERROR";
        }
        if (i == 46) {
            return "PDP_WITHOUT_ACTIVE_TFT";
        }
        if (i == 50) {
            return "ONLY_IPV4_ALLOWED";
        }
        if (i == 51) {
            return "ONLY_IPV6_ALLOWED";
        }
        if (i == 52) {
            return "ONLY_SINGLE_BEARER_ALLOWED";
        }
        if (i == 53) {
            return "ESM_INFO_NOT_RECEIVED";
        }
        if (i == 54) {
            return "PDN_CONN_DOES_NOT_EXIST";
        }
        if (i == 55) {
            return "MULTI_CONN_TO_SAME_PDN_NOT_ALLOWED";
        }
        if (i == 65) {
            return "MAX_ACTIVE_PDP_CONTEXT_REACHED";
        }
        if (i == 66) {
            return "UNSUPPORTED_APN_IN_CURRENT_PLMN";
        }
        if (i == 81) {
            return "INVALID_TRANSACTION_ID";
        }
        if (i == 95) {
            return "MESSAGE_INCORRECT_SEMANTIC";
        }
        if (i == 96) {
            return "INVALID_MANDATORY_INFO";
        }
        if (i == 97) {
            return "MESSAGE_TYPE_UNSUPPORTED";
        }
        if (i == 98) {
            return "MSG_TYPE_NONCOMPATIBLE_STATE";
        }
        if (i == 99) {
            return "UNKNOWN_INFO_ELEMENT";
        }
        if (i == 100) {
            return "CONDITIONAL_IE_ERROR";
        }
        if (i == 101) {
            return "MSG_AND_PROTOCOL_STATE_UNCOMPATIBLE";
        }
        if (i == 111) {
            return "PROTOCOL_ERRORS";
        }
        if (i == 112) {
            return "APN_TYPE_CONFLICT";
        }
        if (i == 113) {
            return "INVALID_PCSCF_ADDR";
        }
        if (i == 114) {
            return "INTERNAL_CALL_PREEMPT_BY_HIGH_PRIO_APN";
        }
        if (i == 115) {
            return "EMM_ACCESS_BARRED";
        }
        if (i == 116) {
            return "EMERGENCY_IFACE_ONLY";
        }
        if (i == 117) {
            return "IFACE_MISMATCH";
        }
        if (i == 118) {
            return "COMPANION_IFACE_IN_USE";
        }
        if (i == 119) {
            return "IP_ADDRESS_MISMATCH";
        }
        if (i == 120) {
            return "IFACE_AND_POL_FAMILY_MISMATCH";
        }
        if (i == 121) {
            return "EMM_ACCESS_BARRED_INFINITE_RETRY";
        }
        if (i == 122) {
            return "AUTH_FAILURE_ON_EMERGENCY_CALL";
        }
        if (i == 4097) {
            return "OEM_DCFAILCAUSE_1";
        }
        if (i == 4098) {
            return "OEM_DCFAILCAUSE_2";
        }
        if (i == 4099) {
            return "OEM_DCFAILCAUSE_3";
        }
        if (i == 4100) {
            return "OEM_DCFAILCAUSE_4";
        }
        if (i == 4101) {
            return "OEM_DCFAILCAUSE_5";
        }
        if (i == 4102) {
            return "OEM_DCFAILCAUSE_6";
        }
        if (i == 4103) {
            return "OEM_DCFAILCAUSE_7";
        }
        if (i == 4104) {
            return "OEM_DCFAILCAUSE_8";
        }
        if (i == 4105) {
            return "OEM_DCFAILCAUSE_9";
        }
        if (i == 4106) {
            return "OEM_DCFAILCAUSE_10";
        }
        if (i == 4107) {
            return "OEM_DCFAILCAUSE_11";
        }
        if (i == 4108) {
            return "OEM_DCFAILCAUSE_12";
        }
        if (i == 4109) {
            return "OEM_DCFAILCAUSE_13";
        }
        if (i == 4110) {
            return "OEM_DCFAILCAUSE_14";
        }
        if (i == 4111) {
            return "OEM_DCFAILCAUSE_15";
        }
        if (i == -1) {
            return "VOICE_REGISTRATION_FAIL";
        }
        if (i == -2) {
            return "DATA_REGISTRATION_FAIL";
        }
        if (i == -3) {
            return "SIGNAL_LOST";
        }
        if (i == -4) {
            return "PREF_RADIO_TECH_CHANGED";
        }
        if (i == -5) {
            return "RADIO_POWER_OFF";
        }
        if (i == -6) {
            return "TETHERED_CALL_ACTIVE";
        }
        if (i == 65535) {
            return "ERROR_UNSPECIFIED";
        }
        if (i == 25) {
            return "LLC_SNDCP";
        }
        if (i == 48) {
            return "ACTIVATION_REJECTED_BCM_VIOLATION";
        }
        if (i == 56) {
            return "COLLISION_WITH_NETWORK_INITIATED_REQUEST";
        }
        if (i == 57) {
            return "ONLY_IPV4V6_ALLOWED";
        }
        if (i == 58) {
            return "ONLY_NON_IP_ALLOWED";
        }
        if (i == 59) {
            return "UNSUPPORTED_QCI_VALUE";
        }
        if (i == 60) {
            return "BEARER_HANDLING_NOT_SUPPORTED";
        }
        if (i == 123) {
            return "INVALID_DNS_ADDR";
        }
        if (i == 124) {
            return "INVALID_PCSCF_OR_DNS_ADDRESS";
        }
        if (i == 127) {
            return "CALL_PREEMPT_BY_EMERGENCY_APN";
        }
        if (i == 128) {
            return "UE_INITIATED_DETACH_OR_DISCONNECT";
        }
        if (i == 2000) {
            return "MIP_FA_REASON_UNSPECIFIED";
        }
        if (i == 2001) {
            return "MIP_FA_ADMIN_PROHIBITED";
        }
        if (i == 2002) {
            return "MIP_FA_INSUFFICIENT_RESOURCES";
        }
        if (i == 2003) {
            return "MIP_FA_MOBILE_NODE_AUTHENTICATION_FAILURE";
        }
        if (i == 2004) {
            return "MIP_FA_HOME_AGENT_AUTHENTICATION_FAILURE";
        }
        if (i == 2005) {
            return "MIP_FA_REQUESTED_LIFETIME_TOO_LONG";
        }
        if (i == 2006) {
            return "MIP_FA_MALFORMED_REQUEST";
        }
        if (i == 2007) {
            return "MIP_FA_MALFORMED_REPLY";
        }
        if (i == 2008) {
            return "MIP_FA_ENCAPSULATION_UNAVAILABLE";
        }
        if (i == 2009) {
            return "MIP_FA_VJ_HEADER_COMPRESSION_UNAVAILABLE";
        }
        if (i == 2010) {
            return "MIP_FA_REVERSE_TUNNEL_UNAVAILABLE";
        }
        if (i == 2011) {
            return "MIP_FA_REVERSE_TUNNEL_IS_MANDATORY";
        }
        if (i == 2012) {
            return "MIP_FA_DELIVERY_STYLE_NOT_SUPPORTED";
        }
        if (i == 2013) {
            return "MIP_FA_MISSING_NAI";
        }
        if (i == 2014) {
            return "MIP_FA_MISSING_HOME_AGENT";
        }
        if (i == 2015) {
            return "MIP_FA_MISSING_HOME_ADDRESS";
        }
        if (i == 2016) {
            return "MIP_FA_UNKNOWN_CHALLENGE";
        }
        if (i == 2017) {
            return "MIP_FA_MISSING_CHALLENGE";
        }
        if (i == 2018) {
            return "MIP_FA_STALE_CHALLENGE";
        }
        if (i == 2019) {
            return "MIP_HA_REASON_UNSPECIFIED";
        }
        if (i == 2020) {
            return "MIP_HA_ADMIN_PROHIBITED";
        }
        if (i == 2021) {
            return "MIP_HA_INSUFFICIENT_RESOURCES";
        }
        if (i == 2022) {
            return "MIP_HA_MOBILE_NODE_AUTHENTICATION_FAILURE";
        }
        if (i == 2023) {
            return "MIP_HA_FOREIGN_AGENT_AUTHENTICATION_FAILURE";
        }
        if (i == 2024) {
            return "MIP_HA_REGISTRATION_ID_MISMATCH";
        }
        if (i == 2025) {
            return "MIP_HA_MALFORMED_REQUEST";
        }
        if (i == 2026) {
            return "MIP_HA_UNKNOWN_HOME_AGENT_ADDRESS";
        }
        if (i == 2027) {
            return "MIP_HA_REVERSE_TUNNEL_UNAVAILABLE";
        }
        if (i == 2028) {
            return "MIP_HA_REVERSE_TUNNEL_IS_MANDATORY";
        }
        if (i == 2029) {
            return "MIP_HA_ENCAPSULATION_UNAVAILABLE";
        }
        if (i == 2030) {
            return "CLOSE_IN_PROGRESS";
        }
        if (i == 2031) {
            return "NETWORK_INITIATED_TERMINATION";
        }
        if (i == 2032) {
            return "MODEM_APP_PREEMPTED";
        }
        if (i == 2033) {
            return "PDN_IPV4_CALL_DISALLOWED";
        }
        if (i == 2034) {
            return "PDN_IPV4_CALL_THROTTLED";
        }
        if (i == 2035) {
            return "PDN_IPV6_CALL_DISALLOWED";
        }
        if (i == 2036) {
            return "PDN_IPV6_CALL_THROTTLED";
        }
        if (i == 2037) {
            return "MODEM_RESTART";
        }
        if (i == 2038) {
            return "PDP_PPP_NOT_SUPPORTED";
        }
        if (i == 2039) {
            return "UNPREFERRED_RAT";
        }
        if (i == 2040) {
            return "PHYSICAL_LINK_CLOSE_IN_PROGRESS";
        }
        if (i == 2041) {
            return "APN_PENDING_HANDOVER";
        }
        if (i == 2042) {
            return "PROFILE_BEARER_INCOMPATIBLE";
        }
        if (i == 2043) {
            return "SIM_CARD_CHANGED";
        }
        if (i == 2044) {
            return "LOW_POWER_MODE_OR_POWERING_DOWN";
        }
        if (i == 2045) {
            return "APN_DISABLED";
        }
        if (i == 2046) {
            return "MAX_PPP_INACTIVITY_TIMER_EXPIRED";
        }
        if (i == 2047) {
            return "IPV6_ADDRESS_TRANSFER_FAILED";
        }
        if (i == 2048) {
            return "TRAT_SWAP_FAILED";
        }
        if (i == 2049) {
            return "EHRPD_TO_HRPD_FALLBACK";
        }
        if (i == 2050) {
            return "MIP_CONFIG_FAILURE";
        }
        if (i == 2051) {
            return "PDN_INACTIVITY_TIMER_EXPIRED";
        }
        if (i == 2052) {
            return "MAX_IPV4_CONNECTIONS";
        }
        if (i == 2053) {
            return "MAX_IPV6_CONNECTIONS";
        }
        if (i == 2054) {
            return "APN_MISMATCH";
        }
        if (i == 2055) {
            return "IP_VERSION_MISMATCH";
        }
        if (i == 2056) {
            return "DUN_CALL_DISALLOWED";
        }
        if (i == 2057) {
            return "INTERNAL_EPC_NONEPC_TRANSITION";
        }
        if (i == 2058) {
            return "INTERFACE_IN_USE";
        }
        if (i == 2059) {
            return "APN_DISALLOWED_ON_ROAMING";
        }
        if (i == 2060) {
            return "APN_PARAMETERS_CHANGED";
        }
        if (i == 2061) {
            return "NULL_APN_DISALLOWED";
        }
        if (i == 2062) {
            return "THERMAL_MITIGATION";
        }
        if (i == 2063) {
            return "DATA_SETTINGS_DISABLED";
        }
        if (i == 2064) {
            return "DATA_ROAMING_SETTINGS_DISABLED";
        }
        if (i == 2065) {
            return "DDS_SWITCHED";
        }
        if (i == 2066) {
            return "FORBIDDEN_APN_NAME";
        }
        if (i == 2067) {
            return "DDS_SWITCH_IN_PROGRESS";
        }
        if (i == 2068) {
            return "CALL_DISALLOWED_IN_ROAMING";
        }
        if (i == 2069) {
            return "NON_IP_NOT_SUPPORTED";
        }
        if (i == 2070) {
            return "PDN_NON_IP_CALL_THROTTLED";
        }
        if (i == 2071) {
            return "PDN_NON_IP_CALL_DISALLOWED";
        }
        if (i == 2072) {
            return "CDMA_LOCK";
        }
        if (i == 2073) {
            return "CDMA_INTERCEPT";
        }
        if (i == 2074) {
            return "CDMA_REORDER";
        }
        if (i == 2075) {
            return "CDMA_RELEASE_DUE_TO_SO_REJECTION";
        }
        if (i == 2076) {
            return "CDMA_INCOMING_CALL";
        }
        if (i == 2077) {
            return "CDMA_ALERT_STOP";
        }
        if (i == 2078) {
            return "CHANNEL_ACQUISITION_FAILURE";
        }
        if (i == 2079) {
            return "MAX_ACCESS_PROBE";
        }
        if (i == 2080) {
            return "CONCURRENT_SERVICE_NOT_SUPPORTED_BY_BASE_STATION";
        }
        if (i == 2081) {
            return "NO_RESPONSE_FROM_BASE_STATION";
        }
        if (i == 2082) {
            return "REJECTED_BY_BASE_STATION";
        }
        if (i == 2083) {
            return "CONCURRENT_SERVICES_INCOMPATIBLE";
        }
        if (i == 2084) {
            return "NO_CDMA_SERVICE";
        }
        if (i == 2085) {
            return "RUIM_NOT_PRESENT";
        }
        if (i == 2086) {
            return "CDMA_RETRY_ORDER";
        }
        if (i == 2087) {
            return "ACCESS_BLOCK";
        }
        if (i == 2088) {
            return "ACCESS_BLOCK_ALL";
        }
        if (i == 2089) {
            return "IS707B_MAX_ACCESS_PROBES";
        }
        if (i == 2090) {
            return "THERMAL_EMERGENCY";
        }
        if (i == 2091) {
            return "CONCURRENT_SERVICES_NOT_ALLOWED";
        }
        if (i == 2092) {
            return "INCOMING_CALL_REJECTED";
        }
        if (i == 2093) {
            return "NO_SERVICE_ON_GATEWAY";
        }
        if (i == 2094) {
            return "NO_GPRS_CONTEXT";
        }
        if (i == 2095) {
            return "ILLEGAL_MS";
        }
        if (i == 2096) {
            return "ILLEGAL_ME";
        }
        if (i == 2097) {
            return "GPRS_SERVICES_AND_NON_GPRS_SERVICES_NOT_ALLOWED";
        }
        if (i == 2098) {
            return "GPRS_SERVICES_NOT_ALLOWED";
        }
        if (i == 2099) {
            return "MS_IDENTITY_CANNOT_BE_DERIVED_BY_THE_NETWORK";
        }
        if (i == 2100) {
            return "IMPLICITLY_DETACHED";
        }
        if (i == 2101) {
            return "PLMN_NOT_ALLOWED";
        }
        if (i == 2102) {
            return "LOCATION_AREA_NOT_ALLOWED";
        }
        if (i == 2103) {
            return "GPRS_SERVICES_NOT_ALLOWED_IN_THIS_PLMN";
        }
        if (i == 2104) {
            return "PDP_DUPLICATE";
        }
        if (i == 2105) {
            return "UE_RAT_CHANGE";
        }
        if (i == 2106) {
            return "CONGESTION";
        }
        if (i == 2107) {
            return "NO_PDP_CONTEXT_ACTIVATED";
        }
        if (i == 2108) {
            return "ACCESS_CLASS_DSAC_REJECTION";
        }
        if (i == 2109) {
            return "PDP_ACTIVATE_MAX_RETRY_FAILED";
        }
        if (i == 2110) {
            return "RADIO_ACCESS_BEARER_FAILURE";
        }
        if (i == 2111) {
            return "ESM_UNKNOWN_EPS_BEARER_CONTEXT";
        }
        if (i == 2112) {
            return "DRB_RELEASED_BY_RRC";
        }
        if (i == 2113) {
            return "CONNECTION_RELEASED";
        }
        if (i == 2114) {
            return "EMM_DETACHED";
        }
        if (i == 2115) {
            return "EMM_ATTACH_FAILED";
        }
        if (i == 2116) {
            return "EMM_ATTACH_STARTED";
        }
        if (i == 2117) {
            return "LTE_NAS_SERVICE_REQUEST_FAILED";
        }
        if (i == 2118) {
            return "DUPLICATE_BEARER_ID";
        }
        if (i == 2119) {
            return "ESM_COLLISION_SCENARIOS";
        }
        if (i == 2120) {
            return "ESM_BEARER_DEACTIVATED_TO_SYNC_WITH_NETWORK";
        }
        if (i == 2121) {
            return "ESM_NW_ACTIVATED_DED_BEARER_WITH_ID_OF_DEF_BEARER";
        }
        if (i == 2122) {
            return "ESM_BAD_OTA_MESSAGE";
        }
        if (i == 2123) {
            return "ESM_DOWNLOAD_SERVER_REJECTED_THE_CALL";
        }
        if (i == 2124) {
            return "ESM_CONTEXT_TRANSFERRED_DUE_TO_IRAT";
        }
        if (i == 2125) {
            return "DS_EXPLICIT_DEACTIVATION";
        }
        if (i == 2126) {
            return "ESM_LOCAL_CAUSE_NONE";
        }
        if (i == 2127) {
            return "LTE_THROTTLING_NOT_REQUIRED";
        }
        if (i == 2128) {
            return "ACCESS_CONTROL_LIST_CHECK_FAILURE";
        }
        if (i == 2129) {
            return "SERVICE_NOT_ALLOWED_ON_PLMN";
        }
        if (i == 2130) {
            return "EMM_T3417_EXPIRED";
        }
        if (i == 2131) {
            return "EMM_T3417_EXT_EXPIRED";
        }
        if (i == 2132) {
            return "RRC_UPLINK_DATA_TRANSMISSION_FAILURE";
        }
        if (i == 2133) {
            return "RRC_UPLINK_DELIVERY_FAILED_DUE_TO_HANDOVER";
        }
        if (i == 2134) {
            return "RRC_UPLINK_CONNECTION_RELEASE";
        }
        if (i == 2135) {
            return "RRC_UPLINK_RADIO_LINK_FAILURE";
        }
        if (i == 2136) {
            return "RRC_UPLINK_ERROR_REQUEST_FROM_NAS";
        }
        if (i == 2137) {
            return "RRC_CONNECTION_ACCESS_STRATUM_FAILURE";
        }
        if (i == 2138) {
            return "RRC_CONNECTION_ANOTHER_PROCEDURE_IN_PROGRESS";
        }
        if (i == 2139) {
            return "RRC_CONNECTION_ACCESS_BARRED";
        }
        if (i == 2140) {
            return "RRC_CONNECTION_CELL_RESELECTION";
        }
        if (i == 2141) {
            return "RRC_CONNECTION_CONFIG_FAILURE";
        }
        if (i == 2142) {
            return "RRC_CONNECTION_TIMER_EXPIRED";
        }
        if (i == 2143) {
            return "RRC_CONNECTION_LINK_FAILURE";
        }
        if (i == 2144) {
            return "RRC_CONNECTION_CELL_NOT_CAMPED";
        }
        if (i == 2145) {
            return "RRC_CONNECTION_SYSTEM_INTERVAL_FAILURE";
        }
        if (i == 2146) {
            return "RRC_CONNECTION_REJECT_BY_NETWORK";
        }
        if (i == 2147) {
            return "RRC_CONNECTION_NORMAL_RELEASE";
        }
        if (i == 2148) {
            return "RRC_CONNECTION_RADIO_LINK_FAILURE";
        }
        if (i == 2149) {
            return "RRC_CONNECTION_REESTABLISHMENT_FAILURE";
        }
        if (i == 2150) {
            return "RRC_CONNECTION_OUT_OF_SERVICE_DURING_CELL_REGISTER";
        }
        if (i == 2151) {
            return "RRC_CONNECTION_ABORT_REQUEST";
        }
        if (i == 2152) {
            return "RRC_CONNECTION_SYSTEM_INFORMATION_BLOCK_READ_ERROR";
        }
        if (i == 2153) {
            return "NETWORK_INITIATED_DETACH_WITH_AUTO_REATTACH";
        }
        if (i == 2154) {
            return "NETWORK_INITIATED_DETACH_NO_AUTO_REATTACH";
        }
        if (i == 2155) {
            return "ESM_PROCEDURE_TIME_OUT";
        }
        if (i == 2156) {
            return "INVALID_CONNECTION_ID";
        }
        if (i == 2157) {
            return "MAXIMIUM_NSAPIS_EXCEEDED";
        }
        if (i == 2158) {
            return "INVALID_PRIMARY_NSAPI";
        }
        if (i == 2159) {
            return "CANNOT_ENCODE_OTA_MESSAGE";
        }
        if (i == 2160) {
            return "RADIO_ACCESS_BEARER_SETUP_FAILURE";
        }
        if (i == 2161) {
            return "PDP_ESTABLISH_TIMEOUT_EXPIRED";
        }
        if (i == 2162) {
            return "PDP_MODIFY_TIMEOUT_EXPIRED";
        }
        if (i == 2163) {
            return "PDP_INACTIVE_TIMEOUT_EXPIRED";
        }
        if (i == 2164) {
            return "PDP_LOWERLAYER_ERROR";
        }
        if (i == 2165) {
            return "PDP_MODIFY_COLLISION";
        }
        if (i == 2166) {
            return "MAXINUM_SIZE_OF_L2_MESSAGE_EXCEEDED";
        }
        if (i == 2167) {
            return "NAS_REQUEST_REJECTED_BY_NETWORK";
        }
        if (i == 2168) {
            return "RRC_CONNECTION_INVALID_REQUEST";
        }
        if (i == 2169) {
            return "RRC_CONNECTION_TRACKING_AREA_ID_CHANGED";
        }
        if (i == 2170) {
            return "RRC_CONNECTION_RF_UNAVAILABLE";
        }
        if (i == 2171) {
            return "RRC_CONNECTION_ABORTED_DUE_TO_IRAT_CHANGE";
        }
        if (i == 2172) {
            return "RRC_CONNECTION_RELEASED_SECURITY_NOT_ACTIVE";
        }
        if (i == 2173) {
            return "RRC_CONNECTION_ABORTED_AFTER_HANDOVER";
        }
        if (i == 2174) {
            return "RRC_CONNECTION_ABORTED_AFTER_IRAT_CELL_CHANGE";
        }
        if (i == 2175) {
            return "RRC_CONNECTION_ABORTED_DURING_IRAT_CELL_CHANGE";
        }
        if (i == 2176) {
            return "IMSI_UNKNOWN_IN_HOME_SUBSCRIBER_SERVER";
        }
        if (i == 2177) {
            return "IMEI_NOT_ACCEPTED";
        }
        if (i == 2178) {
            return "EPS_SERVICES_AND_NON_EPS_SERVICES_NOT_ALLOWED";
        }
        if (i == 2179) {
            return "EPS_SERVICES_NOT_ALLOWED_IN_PLMN";
        }
        if (i == 2180) {
            return "MSC_TEMPORARILY_NOT_REACHABLE";
        }
        if (i == 2181) {
            return "CS_DOMAIN_NOT_AVAILABLE";
        }
        if (i == 2182) {
            return "ESM_FAILURE";
        }
        if (i == 2183) {
            return "MAC_FAILURE";
        }
        if (i == 2184) {
            return "SYNCHRONIZATION_FAILURE";
        }
        if (i == 2185) {
            return "UE_SECURITY_CAPABILITIES_MISMATCH";
        }
        if (i == 2186) {
            return "SECURITY_MODE_REJECTED";
        }
        if (i == 2187) {
            return "UNACCEPTABLE_NON_EPS_AUTHENTICATION";
        }
        if (i == 2188) {
            return "CS_FALLBACK_CALL_ESTABLISHMENT_NOT_ALLOWED";
        }
        if (i == 2189) {
            return "NO_EPS_BEARER_CONTEXT_ACTIVATED";
        }
        if (i == 2190) {
            return "INVALID_EMM_STATE";
        }
        if (i == 2191) {
            return "NAS_LAYER_FAILURE";
        }
        if (i == 2192) {
            return "MULTIPLE_PDP_CALL_NOT_ALLOWED";
        }
        if (i == 2193) {
            return "EMBMS_NOT_ENABLED";
        }
        if (i == 2194) {
            return "IRAT_HANDOVER_FAILED";
        }
        if (i == 2195) {
            return "EMBMS_REGULAR_DEACTIVATION";
        }
        if (i == 2196) {
            return "TEST_LOOPBACK_REGULAR_DEACTIVATION";
        }
        if (i == 2197) {
            return "LOWER_LAYER_REGISTRATION_FAILURE";
        }
        if (i == 2198) {
            return "DATA_PLAN_EXPIRED";
        }
        if (i == 2199) {
            return "UMTS_HANDOVER_TO_IWLAN";
        }
        if (i == 2200) {
            return "EVDO_CONNECTION_DENY_BY_GENERAL_OR_NETWORK_BUSY";
        }
        if (i == 2201) {
            return "EVDO_CONNECTION_DENY_BY_BILLING_OR_AUTHENTICATION_FAILURE";
        }
        if (i == 2202) {
            return "EVDO_HDR_CHANGED";
        }
        if (i == 2203) {
            return "EVDO_HDR_EXITED";
        }
        if (i == 2204) {
            return "EVDO_HDR_NO_SESSION";
        }
        if (i == 2205) {
            return "EVDO_USING_GPS_FIX_INSTEAD_OF_HDR_CALL";
        }
        if (i == 2206) {
            return "EVDO_HDR_CONNECTION_SETUP_TIMEOUT";
        }
        if (i == 2207) {
            return "FAILED_TO_ACQUIRE_COLOCATED_HDR";
        }
        if (i == 2208) {
            return "OTASP_COMMIT_IN_PROGRESS";
        }
        if (i == 2209) {
            return "NO_HYBRID_HDR_SERVICE";
        }
        if (i == 2210) {
            return "HDR_NO_LOCK_GRANTED";
        }
        if (i == 2211) {
            return "DBM_OR_SMS_IN_PROGRESS";
        }
        if (i == 2212) {
            return "HDR_FADE";
        }
        if (i == 2213) {
            return "HDR_ACCESS_FAILURE";
        }
        if (i == 2214) {
            return "UNSUPPORTED_1X_PREV";
        }
        if (i == 2215) {
            return "LOCAL_END";
        }
        if (i == 2216) {
            return "NO_SERVICE";
        }
        if (i == 2217) {
            return "FADE";
        }
        if (i == 2218) {
            return "NORMAL_RELEASE";
        }
        if (i == 2219) {
            return "ACCESS_ATTEMPT_ALREADY_IN_PROGRESS";
        }
        if (i == 2220) {
            return "REDIRECTION_OR_HANDOFF_IN_PROGRESS";
        }
        if (i == 2221) {
            return "EMERGENCY_MODE";
        }
        if (i == 2222) {
            return "PHONE_IN_USE";
        }
        if (i == 2223) {
            return "INVALID_MODE";
        }
        if (i == 2224) {
            return "INVALID_SIM_STATE";
        }
        if (i == 2225) {
            return "NO_COLLOCATED_HDR";
        }
        if (i == 2226) {
            return "UE_IS_ENTERING_POWERSAVE_MODE";
        }
        if (i == 2227) {
            return "DUAL_SWITCH";
        }
        if (i == 2228) {
            return "PPP_TIMEOUT";
        }
        if (i == 2229) {
            return "PPP_AUTH_FAILURE";
        }
        if (i == 2230) {
            return "PPP_OPTION_MISMATCH";
        }
        if (i == 2231) {
            return "PPP_PAP_FAILURE";
        }
        if (i == 2232) {
            return "PPP_CHAP_FAILURE";
        }
        if (i == 2233) {
            return "PPP_CLOSE_IN_PROGRESS";
        }
        if (i == 2234) {
            return "LIMITED_TO_IPV4";
        }
        if (i == 2235) {
            return "LIMITED_TO_IPV6";
        }
        if (i == 2236) {
            return "VSNCP_TIMEOUT";
        }
        if (i == 2237) {
            return "VSNCP_GEN_ERROR";
        }
        if (i == 2238) {
            return "VSNCP_APN_UNATHORIZED";
        }
        if (i == 2239) {
            return "VSNCP_PDN_LIMIT_EXCEEDED";
        }
        if (i == 2240) {
            return "VSNCP_NO_PDN_GATEWAY_ADDRESS";
        }
        if (i == 2241) {
            return "VSNCP_PDN_GATEWAY_UNREACHABLE";
        }
        if (i == 2242) {
            return "VSNCP_PDN_GATEWAY_REJECT";
        }
        if (i == 2243) {
            return "VSNCP_INSUFFICIENT_PARAMETERS";
        }
        if (i == 2244) {
            return "VSNCP_RESOURCE_UNAVAILABLE";
        }
        if (i == 2245) {
            return "VSNCP_ADMINISTRATIVELY_PROHIBITED";
        }
        if (i == 2246) {
            return "VSNCP_PDN_ID_IN_USE";
        }
        if (i == 2247) {
            return "VSNCP_SUBSCRIBER_LIMITATION";
        }
        if (i == 2248) {
            return "VSNCP_PDN_EXISTS_FOR_THIS_APN";
        }
        if (i == 2249) {
            return "VSNCP_RECONNECT_NOT_ALLOWED";
        }
        if (i == 2250) {
            return "IPV6_PREFIX_UNAVAILABLE";
        }
        if (i == 2251) {
            return "HANDOFF_PREFERENCE_CHANGED";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(KeyProperties.DIGEST_NONE);
        int i2 = 8;
        if ((i & 8) == 8) {
            arrayList.add("OPERATOR_BARRED");
        } else {
            i2 = 0;
        }
        if ((i & 14) == 14) {
            arrayList.add("NAS_SIGNALLING");
            i2 = 14;
        }
        if ((i & 26) == 26) {
            arrayList.add("INSUFFICIENT_RESOURCES");
            i2 |= 26;
        }
        if ((i & 27) == 27) {
            arrayList.add("MISSING_UKNOWN_APN");
            i2 |= 27;
        }
        if ((i & 28) == 28) {
            arrayList.add("UNKNOWN_PDP_ADDRESS_TYPE");
            i2 |= 28;
        }
        if ((i & 29) == 29) {
            arrayList.add("USER_AUTHENTICATION");
            i2 |= 29;
        }
        if ((i & 30) == 30) {
            arrayList.add("ACTIVATION_REJECT_GGSN");
            i2 |= 30;
        }
        if ((i & 31) == 31) {
            arrayList.add("ACTIVATION_REJECT_UNSPECIFIED");
            i2 = 31;
        }
        if ((i & 32) == 32) {
            arrayList.add("SERVICE_OPTION_NOT_SUPPORTED");
            i2 |= 32;
        }
        if ((i & 33) == 33) {
            arrayList.add("SERVICE_OPTION_NOT_SUBSCRIBED");
            i2 |= 33;
        }
        if ((i & 34) == 34) {
            arrayList.add("SERVICE_OPTION_OUT_OF_ORDER");
            i2 |= 34;
        }
        if ((i & 35) == 35) {
            arrayList.add("NSAPI_IN_USE");
            i2 |= 35;
        }
        if ((i & 36) == 36) {
            arrayList.add("REGULAR_DEACTIVATION");
            i2 |= 36;
        }
        if ((i & 37) == 37) {
            arrayList.add("QOS_NOT_ACCEPTED");
            i2 |= 37;
        }
        if ((i & 38) == 38) {
            arrayList.add("NETWORK_FAILURE");
            i2 |= 38;
        }
        if ((i & 39) == 39) {
            arrayList.add("UMTS_REACTIVATION_REQ");
            i2 |= 39;
        }
        if ((i & 40) == 40) {
            arrayList.add("FEATURE_NOT_SUPP");
            i2 |= 40;
        }
        if ((i & 41) == 41) {
            arrayList.add("TFT_SEMANTIC_ERROR");
            i2 |= 41;
        }
        if ((i & 42) == 42) {
            arrayList.add("TFT_SYTAX_ERROR");
            i2 |= 42;
        }
        if ((i & 43) == 43) {
            arrayList.add("UNKNOWN_PDP_CONTEXT");
            i2 |= 43;
        }
        if ((i & 44) == 44) {
            arrayList.add("FILTER_SEMANTIC_ERROR");
            i2 |= 44;
        }
        if ((i & 45) == 45) {
            arrayList.add("FILTER_SYTAX_ERROR");
            i2 |= 45;
        }
        if ((i & 46) == 46) {
            arrayList.add("PDP_WITHOUT_ACTIVE_TFT");
            i2 |= 46;
        }
        if ((i & 50) == 50) {
            arrayList.add("ONLY_IPV4_ALLOWED");
            i2 |= 50;
        }
        if ((i & 51) == 51) {
            arrayList.add("ONLY_IPV6_ALLOWED");
            i2 |= 51;
        }
        if ((i & 52) == 52) {
            arrayList.add("ONLY_SINGLE_BEARER_ALLOWED");
            i2 |= 52;
        }
        if ((i & 53) == 53) {
            arrayList.add("ESM_INFO_NOT_RECEIVED");
            i2 |= 53;
        }
        if ((i & 54) == 54) {
            arrayList.add("PDN_CONN_DOES_NOT_EXIST");
            i2 |= 54;
        }
        if ((i & 55) == 55) {
            arrayList.add("MULTI_CONN_TO_SAME_PDN_NOT_ALLOWED");
            i2 |= 55;
        }
        if ((i & 65) == 65) {
            arrayList.add("MAX_ACTIVE_PDP_CONTEXT_REACHED");
            i2 |= 65;
        }
        if ((i & 66) == 66) {
            arrayList.add("UNSUPPORTED_APN_IN_CURRENT_PLMN");
            i2 |= 66;
        }
        if ((i & 81) == 81) {
            arrayList.add("INVALID_TRANSACTION_ID");
            i2 |= 81;
        }
        if ((i & 95) == 95) {
            arrayList.add("MESSAGE_INCORRECT_SEMANTIC");
            i2 |= 95;
        }
        if ((i & 96) == 96) {
            arrayList.add("INVALID_MANDATORY_INFO");
            i2 |= 96;
        }
        if ((i & 97) == 97) {
            arrayList.add("MESSAGE_TYPE_UNSUPPORTED");
            i2 |= 97;
        }
        if ((i & 98) == 98) {
            arrayList.add("MSG_TYPE_NONCOMPATIBLE_STATE");
            i2 |= 98;
        }
        if ((i & 99) == 99) {
            arrayList.add("UNKNOWN_INFO_ELEMENT");
            i2 |= 99;
        }
        if ((i & 100) == 100) {
            arrayList.add("CONDITIONAL_IE_ERROR");
            i2 |= 100;
        }
        if ((i & 101) == 101) {
            arrayList.add("MSG_AND_PROTOCOL_STATE_UNCOMPATIBLE");
            i2 |= 101;
        }
        if ((i & 111) == 111) {
            arrayList.add("PROTOCOL_ERRORS");
            i2 |= 111;
        }
        if ((i & 112) == 112) {
            arrayList.add("APN_TYPE_CONFLICT");
            i2 |= 112;
        }
        if ((i & 113) == 113) {
            arrayList.add("INVALID_PCSCF_ADDR");
            i2 |= 113;
        }
        if ((i & 114) == 114) {
            arrayList.add("INTERNAL_CALL_PREEMPT_BY_HIGH_PRIO_APN");
            i2 |= 114;
        }
        if ((i & 115) == 115) {
            arrayList.add("EMM_ACCESS_BARRED");
            i2 |= 115;
        }
        if ((i & 116) == 116) {
            arrayList.add("EMERGENCY_IFACE_ONLY");
            i2 |= 116;
        }
        if ((i & 117) == 117) {
            arrayList.add("IFACE_MISMATCH");
            i2 |= 117;
        }
        if ((i & 118) == 118) {
            arrayList.add("COMPANION_IFACE_IN_USE");
            i2 |= 118;
        }
        if ((i & 119) == 119) {
            arrayList.add("IP_ADDRESS_MISMATCH");
            i2 |= 119;
        }
        if ((i & 120) == 120) {
            arrayList.add("IFACE_AND_POL_FAMILY_MISMATCH");
            i2 |= 120;
        }
        if ((i & 121) == 121) {
            arrayList.add("EMM_ACCESS_BARRED_INFINITE_RETRY");
            i2 |= 121;
        }
        if ((i & 122) == 122) {
            arrayList.add("AUTH_FAILURE_ON_EMERGENCY_CALL");
            i2 |= 122;
        }
        if ((i & 4097) == 4097) {
            arrayList.add("OEM_DCFAILCAUSE_1");
            i2 |= 4097;
        }
        if ((i & 4098) == 4098) {
            arrayList.add("OEM_DCFAILCAUSE_2");
            i2 |= 4098;
        }
        if ((i & 4099) == 4099) {
            arrayList.add("OEM_DCFAILCAUSE_3");
            i2 |= 4099;
        }
        if ((i & 4100) == 4100) {
            arrayList.add("OEM_DCFAILCAUSE_4");
            i2 |= 4100;
        }
        if ((i & 4101) == 4101) {
            arrayList.add("OEM_DCFAILCAUSE_5");
            i2 |= 4101;
        }
        if ((i & 4102) == 4102) {
            arrayList.add("OEM_DCFAILCAUSE_6");
            i2 |= 4102;
        }
        if ((i & 4103) == 4103) {
            arrayList.add("OEM_DCFAILCAUSE_7");
            i2 |= 4103;
        }
        if ((i & 4104) == 4104) {
            arrayList.add("OEM_DCFAILCAUSE_8");
            i2 |= 4104;
        }
        if ((i & 4105) == 4105) {
            arrayList.add("OEM_DCFAILCAUSE_9");
            i2 |= 4105;
        }
        if ((i & 4106) == 4106) {
            arrayList.add("OEM_DCFAILCAUSE_10");
            i2 |= 4106;
        }
        if ((i & 4107) == 4107) {
            arrayList.add("OEM_DCFAILCAUSE_11");
            i2 |= 4107;
        }
        if ((i & 4108) == 4108) {
            arrayList.add("OEM_DCFAILCAUSE_12");
            i2 |= 4108;
        }
        if ((i & 4109) == 4109) {
            arrayList.add("OEM_DCFAILCAUSE_13");
            i2 |= 4109;
        }
        if ((i & 4110) == 4110) {
            arrayList.add("OEM_DCFAILCAUSE_14");
            i2 |= 4110;
        }
        if ((i & 4111) == 4111) {
            arrayList.add("OEM_DCFAILCAUSE_15");
            i2 |= 4111;
        }
        if (i == -1) {
            arrayList.add("VOICE_REGISTRATION_FAIL");
            i2 = -1;
        }
        if ((i & (-2)) == -2) {
            arrayList.add("DATA_REGISTRATION_FAIL");
            i2 |= -2;
        }
        if ((i & (-3)) == -3) {
            arrayList.add("SIGNAL_LOST");
            i2 |= -3;
        }
        if ((i & (-4)) == -4) {
            arrayList.add("PREF_RADIO_TECH_CHANGED");
            i2 |= -4;
        }
        if ((i & (-5)) == -5) {
            arrayList.add("RADIO_POWER_OFF");
            i2 |= -5;
        }
        if ((i & (-6)) == -6) {
            arrayList.add("TETHERED_CALL_ACTIVE");
            i2 |= -6;
        }
        if ((65535 & i) == 65535) {
            arrayList.add("ERROR_UNSPECIFIED");
            i2 |= 65535;
        }
        if ((i & 25) == 25) {
            arrayList.add("LLC_SNDCP");
            i2 |= 25;
        }
        if ((i & 48) == 48) {
            arrayList.add("ACTIVATION_REJECTED_BCM_VIOLATION");
            i2 |= 48;
        }
        if ((i & 56) == 56) {
            arrayList.add("COLLISION_WITH_NETWORK_INITIATED_REQUEST");
            i2 |= 56;
        }
        if ((i & 57) == 57) {
            arrayList.add("ONLY_IPV4V6_ALLOWED");
            i2 |= 57;
        }
        if ((i & 58) == 58) {
            arrayList.add("ONLY_NON_IP_ALLOWED");
            i2 |= 58;
        }
        if ((i & 59) == 59) {
            arrayList.add("UNSUPPORTED_QCI_VALUE");
            i2 |= 59;
        }
        if ((i & 60) == 60) {
            arrayList.add("BEARER_HANDLING_NOT_SUPPORTED");
            i2 |= 60;
        }
        if ((i & 123) == 123) {
            arrayList.add("INVALID_DNS_ADDR");
            i2 |= 123;
        }
        if ((i & 124) == 124) {
            arrayList.add("INVALID_PCSCF_OR_DNS_ADDRESS");
            i2 |= 124;
        }
        if ((i & 127) == 127) {
            arrayList.add("CALL_PREEMPT_BY_EMERGENCY_APN");
            i2 |= 127;
        }
        if ((i & 128) == 128) {
            arrayList.add("UE_INITIATED_DETACH_OR_DISCONNECT");
            i2 |= 128;
        }
        if ((i & 2000) == 2000) {
            arrayList.add("MIP_FA_REASON_UNSPECIFIED");
            i2 |= 2000;
        }
        if ((i & 2001) == 2001) {
            arrayList.add("MIP_FA_ADMIN_PROHIBITED");
            i2 |= 2001;
        }
        if ((i & 2002) == 2002) {
            arrayList.add("MIP_FA_INSUFFICIENT_RESOURCES");
            i2 |= 2002;
        }
        if ((i & 2003) == 2003) {
            arrayList.add("MIP_FA_MOBILE_NODE_AUTHENTICATION_FAILURE");
            i2 |= 2003;
        }
        if ((i & 2004) == 2004) {
            arrayList.add("MIP_FA_HOME_AGENT_AUTHENTICATION_FAILURE");
            i2 |= 2004;
        }
        if ((i & 2005) == 2005) {
            arrayList.add("MIP_FA_REQUESTED_LIFETIME_TOO_LONG");
            i2 |= 2005;
        }
        if ((i & 2006) == 2006) {
            arrayList.add("MIP_FA_MALFORMED_REQUEST");
            i2 |= 2006;
        }
        if ((i & 2007) == 2007) {
            arrayList.add("MIP_FA_MALFORMED_REPLY");
            i2 |= 2007;
        }
        if ((i & 2008) == 2008) {
            arrayList.add("MIP_FA_ENCAPSULATION_UNAVAILABLE");
            i2 |= 2008;
        }
        if ((i & 2009) == 2009) {
            arrayList.add("MIP_FA_VJ_HEADER_COMPRESSION_UNAVAILABLE");
            i2 |= 2009;
        }
        if ((i & 2010) == 2010) {
            arrayList.add("MIP_FA_REVERSE_TUNNEL_UNAVAILABLE");
            i2 |= 2010;
        }
        if ((i & 2011) == 2011) {
            arrayList.add("MIP_FA_REVERSE_TUNNEL_IS_MANDATORY");
            i2 |= 2011;
        }
        if ((i & 2012) == 2012) {
            arrayList.add("MIP_FA_DELIVERY_STYLE_NOT_SUPPORTED");
            i2 |= 2012;
        }
        if ((i & 2013) == 2013) {
            arrayList.add("MIP_FA_MISSING_NAI");
            i2 |= 2013;
        }
        if ((i & 2014) == 2014) {
            arrayList.add("MIP_FA_MISSING_HOME_AGENT");
            i2 |= 2014;
        }
        if ((i & 2015) == 2015) {
            arrayList.add("MIP_FA_MISSING_HOME_ADDRESS");
            i2 |= 2015;
        }
        if ((i & 2016) == 2016) {
            arrayList.add("MIP_FA_UNKNOWN_CHALLENGE");
            i2 |= 2016;
        }
        if ((i & 2017) == 2017) {
            arrayList.add("MIP_FA_MISSING_CHALLENGE");
            i2 |= 2017;
        }
        if ((i & 2018) == 2018) {
            arrayList.add("MIP_FA_STALE_CHALLENGE");
            i2 |= 2018;
        }
        if ((i & 2019) == 2019) {
            arrayList.add("MIP_HA_REASON_UNSPECIFIED");
            i2 |= 2019;
        }
        if ((i & 2020) == 2020) {
            arrayList.add("MIP_HA_ADMIN_PROHIBITED");
            i2 |= 2020;
        }
        if ((i & 2021) == 2021) {
            arrayList.add("MIP_HA_INSUFFICIENT_RESOURCES");
            i2 |= 2021;
        }
        if ((i & 2022) == 2022) {
            arrayList.add("MIP_HA_MOBILE_NODE_AUTHENTICATION_FAILURE");
            i2 |= 2022;
        }
        if ((i & 2023) == 2023) {
            arrayList.add("MIP_HA_FOREIGN_AGENT_AUTHENTICATION_FAILURE");
            i2 |= 2023;
        }
        if ((i & 2024) == 2024) {
            arrayList.add("MIP_HA_REGISTRATION_ID_MISMATCH");
            i2 |= 2024;
        }
        if ((i & 2025) == 2025) {
            arrayList.add("MIP_HA_MALFORMED_REQUEST");
            i2 |= 2025;
        }
        if ((i & 2026) == 2026) {
            arrayList.add("MIP_HA_UNKNOWN_HOME_AGENT_ADDRESS");
            i2 |= 2026;
        }
        if ((i & 2027) == 2027) {
            arrayList.add("MIP_HA_REVERSE_TUNNEL_UNAVAILABLE");
            i2 |= 2027;
        }
        if ((i & 2028) == 2028) {
            arrayList.add("MIP_HA_REVERSE_TUNNEL_IS_MANDATORY");
            i2 |= 2028;
        }
        if ((i & 2029) == 2029) {
            arrayList.add("MIP_HA_ENCAPSULATION_UNAVAILABLE");
            i2 |= 2029;
        }
        if ((i & 2030) == 2030) {
            arrayList.add("CLOSE_IN_PROGRESS");
            i2 |= 2030;
        }
        if ((i & 2031) == 2031) {
            arrayList.add("NETWORK_INITIATED_TERMINATION");
            i2 |= 2031;
        }
        if ((i & 2032) == 2032) {
            arrayList.add("MODEM_APP_PREEMPTED");
            i2 |= 2032;
        }
        if ((i & 2033) == 2033) {
            arrayList.add("PDN_IPV4_CALL_DISALLOWED");
            i2 |= 2033;
        }
        if ((i & 2034) == 2034) {
            arrayList.add("PDN_IPV4_CALL_THROTTLED");
            i2 |= 2034;
        }
        if ((i & 2035) == 2035) {
            arrayList.add("PDN_IPV6_CALL_DISALLOWED");
            i2 |= 2035;
        }
        if ((i & 2036) == 2036) {
            arrayList.add("PDN_IPV6_CALL_THROTTLED");
            i2 |= 2036;
        }
        if ((i & 2037) == 2037) {
            arrayList.add("MODEM_RESTART");
            i2 |= 2037;
        }
        if ((i & 2038) == 2038) {
            arrayList.add("PDP_PPP_NOT_SUPPORTED");
            i2 |= 2038;
        }
        if ((i & 2039) == 2039) {
            arrayList.add("UNPREFERRED_RAT");
            i2 |= 2039;
        }
        if ((i & 2040) == 2040) {
            arrayList.add("PHYSICAL_LINK_CLOSE_IN_PROGRESS");
            i2 |= 2040;
        }
        if ((i & 2041) == 2041) {
            arrayList.add("APN_PENDING_HANDOVER");
            i2 |= 2041;
        }
        if ((i & 2042) == 2042) {
            arrayList.add("PROFILE_BEARER_INCOMPATIBLE");
            i2 |= 2042;
        }
        if ((i & 2043) == 2043) {
            arrayList.add("SIM_CARD_CHANGED");
            i2 |= 2043;
        }
        if ((i & 2044) == 2044) {
            arrayList.add("LOW_POWER_MODE_OR_POWERING_DOWN");
            i2 |= 2044;
        }
        if ((i & 2045) == 2045) {
            arrayList.add("APN_DISABLED");
            i2 |= 2045;
        }
        if ((i & 2046) == 2046) {
            arrayList.add("MAX_PPP_INACTIVITY_TIMER_EXPIRED");
            i2 |= 2046;
        }
        if ((i & 2047) == 2047) {
            arrayList.add("IPV6_ADDRESS_TRANSFER_FAILED");
            i2 |= 2047;
        }
        if ((i & 2048) == 2048) {
            arrayList.add("TRAT_SWAP_FAILED");
            i2 |= 2048;
        }
        if ((i & 2049) == 2049) {
            arrayList.add("EHRPD_TO_HRPD_FALLBACK");
            i2 |= 2049;
        }
        if ((i & 2050) == 2050) {
            arrayList.add("MIP_CONFIG_FAILURE");
            i2 |= 2050;
        }
        if ((i & 2051) == 2051) {
            arrayList.add("PDN_INACTIVITY_TIMER_EXPIRED");
            i2 |= 2051;
        }
        if ((i & 2052) == 2052) {
            arrayList.add("MAX_IPV4_CONNECTIONS");
            i2 |= 2052;
        }
        if ((i & 2053) == 2053) {
            arrayList.add("MAX_IPV6_CONNECTIONS");
            i2 |= 2053;
        }
        if ((i & 2054) == 2054) {
            arrayList.add("APN_MISMATCH");
            i2 |= 2054;
        }
        if ((i & 2055) == 2055) {
            arrayList.add("IP_VERSION_MISMATCH");
            i2 |= 2055;
        }
        if ((i & 2056) == 2056) {
            arrayList.add("DUN_CALL_DISALLOWED");
            i2 |= 2056;
        }
        if ((i & 2057) == 2057) {
            arrayList.add("INTERNAL_EPC_NONEPC_TRANSITION");
            i2 |= 2057;
        }
        if ((i & 2058) == 2058) {
            arrayList.add("INTERFACE_IN_USE");
            i2 |= 2058;
        }
        if ((i & 2059) == 2059) {
            arrayList.add("APN_DISALLOWED_ON_ROAMING");
            i2 |= 2059;
        }
        if ((i & 2060) == 2060) {
            arrayList.add("APN_PARAMETERS_CHANGED");
            i2 |= 2060;
        }
        if ((i & 2061) == 2061) {
            arrayList.add("NULL_APN_DISALLOWED");
            i2 |= 2061;
        }
        if ((i & 2062) == 2062) {
            arrayList.add("THERMAL_MITIGATION");
            i2 |= 2062;
        }
        if ((i & 2063) == 2063) {
            arrayList.add("DATA_SETTINGS_DISABLED");
            i2 |= 2063;
        }
        if ((i & 2064) == 2064) {
            arrayList.add("DATA_ROAMING_SETTINGS_DISABLED");
            i2 |= 2064;
        }
        if ((i & 2065) == 2065) {
            arrayList.add("DDS_SWITCHED");
            i2 |= 2065;
        }
        if ((i & 2066) == 2066) {
            arrayList.add("FORBIDDEN_APN_NAME");
            i2 |= 2066;
        }
        if ((i & 2067) == 2067) {
            arrayList.add("DDS_SWITCH_IN_PROGRESS");
            i2 |= 2067;
        }
        if ((i & 2068) == 2068) {
            arrayList.add("CALL_DISALLOWED_IN_ROAMING");
            i2 |= 2068;
        }
        if ((i & 2069) == 2069) {
            arrayList.add("NON_IP_NOT_SUPPORTED");
            i2 |= 2069;
        }
        if ((i & 2070) == 2070) {
            arrayList.add("PDN_NON_IP_CALL_THROTTLED");
            i2 |= 2070;
        }
        if ((i & 2071) == 2071) {
            arrayList.add("PDN_NON_IP_CALL_DISALLOWED");
            i2 |= 2071;
        }
        if ((i & 2072) == 2072) {
            arrayList.add("CDMA_LOCK");
            i2 |= 2072;
        }
        if ((i & 2073) == 2073) {
            arrayList.add("CDMA_INTERCEPT");
            i2 |= 2073;
        }
        if ((i & 2074) == 2074) {
            arrayList.add("CDMA_REORDER");
            i2 |= 2074;
        }
        if ((i & 2075) == 2075) {
            arrayList.add("CDMA_RELEASE_DUE_TO_SO_REJECTION");
            i2 |= 2075;
        }
        if ((i & 2076) == 2076) {
            arrayList.add("CDMA_INCOMING_CALL");
            i2 |= 2076;
        }
        if ((i & 2077) == 2077) {
            arrayList.add("CDMA_ALERT_STOP");
            i2 |= 2077;
        }
        if ((i & 2078) == 2078) {
            arrayList.add("CHANNEL_ACQUISITION_FAILURE");
            i2 |= 2078;
        }
        if ((i & 2079) == 2079) {
            arrayList.add("MAX_ACCESS_PROBE");
            i2 |= 2079;
        }
        if ((i & 2080) == 2080) {
            arrayList.add("CONCURRENT_SERVICE_NOT_SUPPORTED_BY_BASE_STATION");
            i2 |= 2080;
        }
        if ((i & 2081) == 2081) {
            arrayList.add("NO_RESPONSE_FROM_BASE_STATION");
            i2 |= 2081;
        }
        if ((i & 2082) == 2082) {
            arrayList.add("REJECTED_BY_BASE_STATION");
            i2 |= 2082;
        }
        if ((i & 2083) == 2083) {
            arrayList.add("CONCURRENT_SERVICES_INCOMPATIBLE");
            i2 |= 2083;
        }
        if ((i & 2084) == 2084) {
            arrayList.add("NO_CDMA_SERVICE");
            i2 |= 2084;
        }
        if ((i & 2085) == 2085) {
            arrayList.add("RUIM_NOT_PRESENT");
            i2 |= 2085;
        }
        if ((i & 2086) == 2086) {
            arrayList.add("CDMA_RETRY_ORDER");
            i2 |= 2086;
        }
        if ((i & 2087) == 2087) {
            arrayList.add("ACCESS_BLOCK");
            i2 |= 2087;
        }
        if ((i & 2088) == 2088) {
            arrayList.add("ACCESS_BLOCK_ALL");
            i2 |= 2088;
        }
        if ((i & 2089) == 2089) {
            arrayList.add("IS707B_MAX_ACCESS_PROBES");
            i2 |= 2089;
        }
        if ((i & 2090) == 2090) {
            arrayList.add("THERMAL_EMERGENCY");
            i2 |= 2090;
        }
        if ((i & 2091) == 2091) {
            arrayList.add("CONCURRENT_SERVICES_NOT_ALLOWED");
            i2 |= 2091;
        }
        if ((i & 2092) == 2092) {
            arrayList.add("INCOMING_CALL_REJECTED");
            i2 |= 2092;
        }
        if ((i & 2093) == 2093) {
            arrayList.add("NO_SERVICE_ON_GATEWAY");
            i2 |= 2093;
        }
        if ((i & 2094) == 2094) {
            arrayList.add("NO_GPRS_CONTEXT");
            i2 |= 2094;
        }
        if ((i & 2095) == 2095) {
            arrayList.add("ILLEGAL_MS");
            i2 |= 2095;
        }
        if ((i & 2096) == 2096) {
            arrayList.add("ILLEGAL_ME");
            i2 |= 2096;
        }
        if ((i & 2097) == 2097) {
            arrayList.add("GPRS_SERVICES_AND_NON_GPRS_SERVICES_NOT_ALLOWED");
            i2 |= 2097;
        }
        if ((i & 2098) == 2098) {
            arrayList.add("GPRS_SERVICES_NOT_ALLOWED");
            i2 |= 2098;
        }
        if ((i & 2099) == 2099) {
            arrayList.add("MS_IDENTITY_CANNOT_BE_DERIVED_BY_THE_NETWORK");
            i2 |= 2099;
        }
        if ((i & 2100) == 2100) {
            arrayList.add("IMPLICITLY_DETACHED");
            i2 |= 2100;
        }
        if ((i & 2101) == 2101) {
            arrayList.add("PLMN_NOT_ALLOWED");
            i2 |= 2101;
        }
        if ((i & 2102) == 2102) {
            arrayList.add("LOCATION_AREA_NOT_ALLOWED");
            i2 |= 2102;
        }
        if ((i & 2103) == 2103) {
            arrayList.add("GPRS_SERVICES_NOT_ALLOWED_IN_THIS_PLMN");
            i2 |= 2103;
        }
        if ((i & 2104) == 2104) {
            arrayList.add("PDP_DUPLICATE");
            i2 |= 2104;
        }
        if ((i & 2105) == 2105) {
            arrayList.add("UE_RAT_CHANGE");
            i2 |= 2105;
        }
        if ((i & 2106) == 2106) {
            arrayList.add("CONGESTION");
            i2 |= 2106;
        }
        if ((i & 2107) == 2107) {
            arrayList.add("NO_PDP_CONTEXT_ACTIVATED");
            i2 |= 2107;
        }
        if ((i & 2108) == 2108) {
            arrayList.add("ACCESS_CLASS_DSAC_REJECTION");
            i2 |= 2108;
        }
        if ((i & 2109) == 2109) {
            arrayList.add("PDP_ACTIVATE_MAX_RETRY_FAILED");
            i2 |= 2109;
        }
        if ((i & 2110) == 2110) {
            arrayList.add("RADIO_ACCESS_BEARER_FAILURE");
            i2 |= 2110;
        }
        if ((i & 2111) == 2111) {
            arrayList.add("ESM_UNKNOWN_EPS_BEARER_CONTEXT");
            i2 |= 2111;
        }
        if ((i & 2112) == 2112) {
            arrayList.add("DRB_RELEASED_BY_RRC");
            i2 |= 2112;
        }
        if ((i & 2113) == 2113) {
            arrayList.add("CONNECTION_RELEASED");
            i2 |= 2113;
        }
        if ((i & 2114) == 2114) {
            arrayList.add("EMM_DETACHED");
            i2 |= 2114;
        }
        if ((i & 2115) == 2115) {
            arrayList.add("EMM_ATTACH_FAILED");
            i2 |= 2115;
        }
        if ((i & 2116) == 2116) {
            arrayList.add("EMM_ATTACH_STARTED");
            i2 |= 2116;
        }
        if ((i & 2117) == 2117) {
            arrayList.add("LTE_NAS_SERVICE_REQUEST_FAILED");
            i2 |= 2117;
        }
        if ((i & 2118) == 2118) {
            arrayList.add("DUPLICATE_BEARER_ID");
            i2 |= 2118;
        }
        if ((i & 2119) == 2119) {
            arrayList.add("ESM_COLLISION_SCENARIOS");
            i2 |= 2119;
        }
        if ((i & 2120) == 2120) {
            arrayList.add("ESM_BEARER_DEACTIVATED_TO_SYNC_WITH_NETWORK");
            i2 |= 2120;
        }
        if ((i & 2121) == 2121) {
            arrayList.add("ESM_NW_ACTIVATED_DED_BEARER_WITH_ID_OF_DEF_BEARER");
            i2 |= 2121;
        }
        if ((i & 2122) == 2122) {
            arrayList.add("ESM_BAD_OTA_MESSAGE");
            i2 |= 2122;
        }
        if ((i & 2123) == 2123) {
            arrayList.add("ESM_DOWNLOAD_SERVER_REJECTED_THE_CALL");
            i2 |= 2123;
        }
        if ((i & 2124) == 2124) {
            arrayList.add("ESM_CONTEXT_TRANSFERRED_DUE_TO_IRAT");
            i2 |= 2124;
        }
        if ((i & 2125) == 2125) {
            arrayList.add("DS_EXPLICIT_DEACTIVATION");
            i2 |= 2125;
        }
        if ((i & 2126) == 2126) {
            arrayList.add("ESM_LOCAL_CAUSE_NONE");
            i2 |= 2126;
        }
        if ((i & 2127) == 2127) {
            arrayList.add("LTE_THROTTLING_NOT_REQUIRED");
            i2 |= 2127;
        }
        if ((i & 2128) == 2128) {
            arrayList.add("ACCESS_CONTROL_LIST_CHECK_FAILURE");
            i2 |= 2128;
        }
        if ((i & 2129) == 2129) {
            arrayList.add("SERVICE_NOT_ALLOWED_ON_PLMN");
            i2 |= 2129;
        }
        if ((i & 2130) == 2130) {
            arrayList.add("EMM_T3417_EXPIRED");
            i2 |= 2130;
        }
        if ((i & 2131) == 2131) {
            arrayList.add("EMM_T3417_EXT_EXPIRED");
            i2 |= 2131;
        }
        if ((i & 2132) == 2132) {
            arrayList.add("RRC_UPLINK_DATA_TRANSMISSION_FAILURE");
            i2 |= 2132;
        }
        if ((i & 2133) == 2133) {
            arrayList.add("RRC_UPLINK_DELIVERY_FAILED_DUE_TO_HANDOVER");
            i2 |= 2133;
        }
        if ((i & 2134) == 2134) {
            arrayList.add("RRC_UPLINK_CONNECTION_RELEASE");
            i2 |= 2134;
        }
        if ((i & 2135) == 2135) {
            arrayList.add("RRC_UPLINK_RADIO_LINK_FAILURE");
            i2 |= 2135;
        }
        if ((i & 2136) == 2136) {
            arrayList.add("RRC_UPLINK_ERROR_REQUEST_FROM_NAS");
            i2 |= 2136;
        }
        if ((i & 2137) == 2137) {
            arrayList.add("RRC_CONNECTION_ACCESS_STRATUM_FAILURE");
            i2 |= 2137;
        }
        if ((i & 2138) == 2138) {
            arrayList.add("RRC_CONNECTION_ANOTHER_PROCEDURE_IN_PROGRESS");
            i2 |= 2138;
        }
        if ((i & 2139) == 2139) {
            arrayList.add("RRC_CONNECTION_ACCESS_BARRED");
            i2 |= 2139;
        }
        if ((i & 2140) == 2140) {
            arrayList.add("RRC_CONNECTION_CELL_RESELECTION");
            i2 |= 2140;
        }
        if ((i & 2141) == 2141) {
            arrayList.add("RRC_CONNECTION_CONFIG_FAILURE");
            i2 |= 2141;
        }
        if ((i & 2142) == 2142) {
            arrayList.add("RRC_CONNECTION_TIMER_EXPIRED");
            i2 |= 2142;
        }
        if ((i & 2143) == 2143) {
            arrayList.add("RRC_CONNECTION_LINK_FAILURE");
            i2 |= 2143;
        }
        if ((i & 2144) == 2144) {
            arrayList.add("RRC_CONNECTION_CELL_NOT_CAMPED");
            i2 |= 2144;
        }
        if ((i & 2145) == 2145) {
            arrayList.add("RRC_CONNECTION_SYSTEM_INTERVAL_FAILURE");
            i2 |= 2145;
        }
        if ((i & 2146) == 2146) {
            arrayList.add("RRC_CONNECTION_REJECT_BY_NETWORK");
            i2 |= 2146;
        }
        if ((i & 2147) == 2147) {
            arrayList.add("RRC_CONNECTION_NORMAL_RELEASE");
            i2 |= 2147;
        }
        if ((i & 2148) == 2148) {
            arrayList.add("RRC_CONNECTION_RADIO_LINK_FAILURE");
            i2 |= 2148;
        }
        if ((i & 2149) == 2149) {
            arrayList.add("RRC_CONNECTION_REESTABLISHMENT_FAILURE");
            i2 |= 2149;
        }
        if ((i & 2150) == 2150) {
            arrayList.add("RRC_CONNECTION_OUT_OF_SERVICE_DURING_CELL_REGISTER");
            i2 |= 2150;
        }
        if ((i & 2151) == 2151) {
            arrayList.add("RRC_CONNECTION_ABORT_REQUEST");
            i2 |= 2151;
        }
        if ((i & 2152) == 2152) {
            arrayList.add("RRC_CONNECTION_SYSTEM_INFORMATION_BLOCK_READ_ERROR");
            i2 |= 2152;
        }
        if ((i & 2153) == 2153) {
            arrayList.add("NETWORK_INITIATED_DETACH_WITH_AUTO_REATTACH");
            i2 |= 2153;
        }
        if ((i & 2154) == 2154) {
            arrayList.add("NETWORK_INITIATED_DETACH_NO_AUTO_REATTACH");
            i2 |= 2154;
        }
        if ((i & 2155) == 2155) {
            arrayList.add("ESM_PROCEDURE_TIME_OUT");
            i2 |= 2155;
        }
        if ((i & 2156) == 2156) {
            arrayList.add("INVALID_CONNECTION_ID");
            i2 |= 2156;
        }
        if ((i & 2157) == 2157) {
            arrayList.add("MAXIMIUM_NSAPIS_EXCEEDED");
            i2 |= 2157;
        }
        if ((i & 2158) == 2158) {
            arrayList.add("INVALID_PRIMARY_NSAPI");
            i2 |= 2158;
        }
        if ((i & 2159) == 2159) {
            arrayList.add("CANNOT_ENCODE_OTA_MESSAGE");
            i2 |= 2159;
        }
        if ((i & 2160) == 2160) {
            arrayList.add("RADIO_ACCESS_BEARER_SETUP_FAILURE");
            i2 |= 2160;
        }
        if ((i & 2161) == 2161) {
            arrayList.add("PDP_ESTABLISH_TIMEOUT_EXPIRED");
            i2 |= 2161;
        }
        if ((i & 2162) == 2162) {
            arrayList.add("PDP_MODIFY_TIMEOUT_EXPIRED");
            i2 |= 2162;
        }
        if ((i & 2163) == 2163) {
            arrayList.add("PDP_INACTIVE_TIMEOUT_EXPIRED");
            i2 |= 2163;
        }
        if ((i & 2164) == 2164) {
            arrayList.add("PDP_LOWERLAYER_ERROR");
            i2 |= 2164;
        }
        if ((i & 2165) == 2165) {
            arrayList.add("PDP_MODIFY_COLLISION");
            i2 |= 2165;
        }
        if ((i & 2166) == 2166) {
            arrayList.add("MAXINUM_SIZE_OF_L2_MESSAGE_EXCEEDED");
            i2 |= 2166;
        }
        if ((i & 2167) == 2167) {
            arrayList.add("NAS_REQUEST_REJECTED_BY_NETWORK");
            i2 |= 2167;
        }
        if ((i & 2168) == 2168) {
            arrayList.add("RRC_CONNECTION_INVALID_REQUEST");
            i2 |= 2168;
        }
        if ((i & 2169) == 2169) {
            arrayList.add("RRC_CONNECTION_TRACKING_AREA_ID_CHANGED");
            i2 |= 2169;
        }
        if ((i & 2170) == 2170) {
            arrayList.add("RRC_CONNECTION_RF_UNAVAILABLE");
            i2 |= 2170;
        }
        if ((i & 2171) == 2171) {
            arrayList.add("RRC_CONNECTION_ABORTED_DUE_TO_IRAT_CHANGE");
            i2 |= 2171;
        }
        if ((i & 2172) == 2172) {
            arrayList.add("RRC_CONNECTION_RELEASED_SECURITY_NOT_ACTIVE");
            i2 |= 2172;
        }
        if ((i & 2173) == 2173) {
            arrayList.add("RRC_CONNECTION_ABORTED_AFTER_HANDOVER");
            i2 |= 2173;
        }
        if ((i & 2174) == 2174) {
            arrayList.add("RRC_CONNECTION_ABORTED_AFTER_IRAT_CELL_CHANGE");
            i2 |= 2174;
        }
        if ((i & 2175) == 2175) {
            arrayList.add("RRC_CONNECTION_ABORTED_DURING_IRAT_CELL_CHANGE");
            i2 |= 2175;
        }
        if ((i & 2176) == 2176) {
            arrayList.add("IMSI_UNKNOWN_IN_HOME_SUBSCRIBER_SERVER");
            i2 |= 2176;
        }
        if ((i & 2177) == 2177) {
            arrayList.add("IMEI_NOT_ACCEPTED");
            i2 |= 2177;
        }
        if ((i & 2178) == 2178) {
            arrayList.add("EPS_SERVICES_AND_NON_EPS_SERVICES_NOT_ALLOWED");
            i2 |= 2178;
        }
        if ((i & 2179) == 2179) {
            arrayList.add("EPS_SERVICES_NOT_ALLOWED_IN_PLMN");
            i2 |= 2179;
        }
        if ((i & 2180) == 2180) {
            arrayList.add("MSC_TEMPORARILY_NOT_REACHABLE");
            i2 |= 2180;
        }
        if ((i & 2181) == 2181) {
            arrayList.add("CS_DOMAIN_NOT_AVAILABLE");
            i2 |= 2181;
        }
        if ((i & 2182) == 2182) {
            arrayList.add("ESM_FAILURE");
            i2 |= 2182;
        }
        if ((i & 2183) == 2183) {
            arrayList.add("MAC_FAILURE");
            i2 |= 2183;
        }
        if ((i & 2184) == 2184) {
            arrayList.add("SYNCHRONIZATION_FAILURE");
            i2 |= 2184;
        }
        if ((i & 2185) == 2185) {
            arrayList.add("UE_SECURITY_CAPABILITIES_MISMATCH");
            i2 |= 2185;
        }
        if ((i & 2186) == 2186) {
            arrayList.add("SECURITY_MODE_REJECTED");
            i2 |= 2186;
        }
        if ((i & 2187) == 2187) {
            arrayList.add("UNACCEPTABLE_NON_EPS_AUTHENTICATION");
            i2 |= 2187;
        }
        if ((i & 2188) == 2188) {
            arrayList.add("CS_FALLBACK_CALL_ESTABLISHMENT_NOT_ALLOWED");
            i2 |= 2188;
        }
        if ((i & 2189) == 2189) {
            arrayList.add("NO_EPS_BEARER_CONTEXT_ACTIVATED");
            i2 |= 2189;
        }
        if ((i & 2190) == 2190) {
            arrayList.add("INVALID_EMM_STATE");
            i2 |= 2190;
        }
        if ((i & 2191) == 2191) {
            arrayList.add("NAS_LAYER_FAILURE");
            i2 |= 2191;
        }
        if ((i & 2192) == 2192) {
            arrayList.add("MULTIPLE_PDP_CALL_NOT_ALLOWED");
            i2 |= 2192;
        }
        if ((i & 2193) == 2193) {
            arrayList.add("EMBMS_NOT_ENABLED");
            i2 |= 2193;
        }
        if ((i & 2194) == 2194) {
            arrayList.add("IRAT_HANDOVER_FAILED");
            i2 |= 2194;
        }
        if ((i & 2195) == 2195) {
            arrayList.add("EMBMS_REGULAR_DEACTIVATION");
            i2 |= 2195;
        }
        if ((i & 2196) == 2196) {
            arrayList.add("TEST_LOOPBACK_REGULAR_DEACTIVATION");
            i2 |= 2196;
        }
        if ((i & 2197) == 2197) {
            arrayList.add("LOWER_LAYER_REGISTRATION_FAILURE");
            i2 |= 2197;
        }
        if ((i & 2198) == 2198) {
            arrayList.add("DATA_PLAN_EXPIRED");
            i2 |= 2198;
        }
        if ((i & 2199) == 2199) {
            arrayList.add("UMTS_HANDOVER_TO_IWLAN");
            i2 |= 2199;
        }
        if ((i & 2200) == 2200) {
            arrayList.add("EVDO_CONNECTION_DENY_BY_GENERAL_OR_NETWORK_BUSY");
            i2 |= 2200;
        }
        if ((i & 2201) == 2201) {
            arrayList.add("EVDO_CONNECTION_DENY_BY_BILLING_OR_AUTHENTICATION_FAILURE");
            i2 |= 2201;
        }
        if ((i & 2202) == 2202) {
            arrayList.add("EVDO_HDR_CHANGED");
            i2 |= 2202;
        }
        if ((i & 2203) == 2203) {
            arrayList.add("EVDO_HDR_EXITED");
            i2 |= 2203;
        }
        if ((i & 2204) == 2204) {
            arrayList.add("EVDO_HDR_NO_SESSION");
            i2 |= 2204;
        }
        if ((i & 2205) == 2205) {
            arrayList.add("EVDO_USING_GPS_FIX_INSTEAD_OF_HDR_CALL");
            i2 |= 2205;
        }
        if ((i & 2206) == 2206) {
            arrayList.add("EVDO_HDR_CONNECTION_SETUP_TIMEOUT");
            i2 |= 2206;
        }
        if ((i & 2207) == 2207) {
            arrayList.add("FAILED_TO_ACQUIRE_COLOCATED_HDR");
            i2 |= 2207;
        }
        if ((i & 2208) == 2208) {
            arrayList.add("OTASP_COMMIT_IN_PROGRESS");
            i2 |= 2208;
        }
        if ((i & 2209) == 2209) {
            arrayList.add("NO_HYBRID_HDR_SERVICE");
            i2 |= 2209;
        }
        if ((i & 2210) == 2210) {
            arrayList.add("HDR_NO_LOCK_GRANTED");
            i2 |= 2210;
        }
        if ((i & 2211) == 2211) {
            arrayList.add("DBM_OR_SMS_IN_PROGRESS");
            i2 |= 2211;
        }
        if ((i & 2212) == 2212) {
            arrayList.add("HDR_FADE");
            i2 |= 2212;
        }
        if ((i & 2213) == 2213) {
            arrayList.add("HDR_ACCESS_FAILURE");
            i2 |= 2213;
        }
        if ((i & 2214) == 2214) {
            arrayList.add("UNSUPPORTED_1X_PREV");
            i2 |= 2214;
        }
        if ((i & 2215) == 2215) {
            arrayList.add("LOCAL_END");
            i2 |= 2215;
        }
        if ((i & 2216) == 2216) {
            arrayList.add("NO_SERVICE");
            i2 |= 2216;
        }
        if ((i & 2217) == 2217) {
            arrayList.add("FADE");
            i2 |= 2217;
        }
        if ((i & 2218) == 2218) {
            arrayList.add("NORMAL_RELEASE");
            i2 |= 2218;
        }
        if ((i & 2219) == 2219) {
            arrayList.add("ACCESS_ATTEMPT_ALREADY_IN_PROGRESS");
            i2 |= 2219;
        }
        if ((i & 2220) == 2220) {
            arrayList.add("REDIRECTION_OR_HANDOFF_IN_PROGRESS");
            i2 |= 2220;
        }
        if ((i & 2221) == 2221) {
            arrayList.add("EMERGENCY_MODE");
            i2 |= 2221;
        }
        if ((i & 2222) == 2222) {
            arrayList.add("PHONE_IN_USE");
            i2 |= 2222;
        }
        if ((i & 2223) == 2223) {
            arrayList.add("INVALID_MODE");
            i2 |= 2223;
        }
        if ((i & 2224) == 2224) {
            arrayList.add("INVALID_SIM_STATE");
            i2 |= 2224;
        }
        if ((i & 2225) == 2225) {
            arrayList.add("NO_COLLOCATED_HDR");
            i2 |= 2225;
        }
        if ((i & 2226) == 2226) {
            arrayList.add("UE_IS_ENTERING_POWERSAVE_MODE");
            i2 |= 2226;
        }
        if ((i & 2227) == 2227) {
            arrayList.add("DUAL_SWITCH");
            i2 |= 2227;
        }
        if ((i & 2228) == 2228) {
            arrayList.add("PPP_TIMEOUT");
            i2 |= 2228;
        }
        if ((i & 2229) == 2229) {
            arrayList.add("PPP_AUTH_FAILURE");
            i2 |= 2229;
        }
        if ((i & 2230) == 2230) {
            arrayList.add("PPP_OPTION_MISMATCH");
            i2 |= 2230;
        }
        if ((i & 2231) == 2231) {
            arrayList.add("PPP_PAP_FAILURE");
            i2 |= 2231;
        }
        if ((i & 2232) == 2232) {
            arrayList.add("PPP_CHAP_FAILURE");
            i2 |= 2232;
        }
        if ((i & 2233) == 2233) {
            arrayList.add("PPP_CLOSE_IN_PROGRESS");
            i2 |= 2233;
        }
        if ((i & 2234) == 2234) {
            arrayList.add("LIMITED_TO_IPV4");
            i2 |= 2234;
        }
        if ((i & 2235) == 2235) {
            arrayList.add("LIMITED_TO_IPV6");
            i2 |= 2235;
        }
        if ((i & 2236) == 2236) {
            arrayList.add("VSNCP_TIMEOUT");
            i2 |= 2236;
        }
        if ((i & 2237) == 2237) {
            arrayList.add("VSNCP_GEN_ERROR");
            i2 |= 2237;
        }
        if ((i & 2238) == 2238) {
            arrayList.add("VSNCP_APN_UNATHORIZED");
            i2 |= 2238;
        }
        if ((i & 2239) == 2239) {
            arrayList.add("VSNCP_PDN_LIMIT_EXCEEDED");
            i2 |= 2239;
        }
        if ((i & 2240) == 2240) {
            arrayList.add("VSNCP_NO_PDN_GATEWAY_ADDRESS");
            i2 |= 2240;
        }
        if ((i & 2241) == 2241) {
            arrayList.add("VSNCP_PDN_GATEWAY_UNREACHABLE");
            i2 |= 2241;
        }
        if ((i & 2242) == 2242) {
            arrayList.add("VSNCP_PDN_GATEWAY_REJECT");
            i2 |= 2242;
        }
        if ((i & 2243) == 2243) {
            arrayList.add("VSNCP_INSUFFICIENT_PARAMETERS");
            i2 |= 2243;
        }
        if ((i & 2244) == 2244) {
            arrayList.add("VSNCP_RESOURCE_UNAVAILABLE");
            i2 |= 2244;
        }
        if ((i & 2245) == 2245) {
            arrayList.add("VSNCP_ADMINISTRATIVELY_PROHIBITED");
            i2 |= 2245;
        }
        if ((i & 2246) == 2246) {
            arrayList.add("VSNCP_PDN_ID_IN_USE");
            i2 |= 2246;
        }
        if ((i & 2247) == 2247) {
            arrayList.add("VSNCP_SUBSCRIBER_LIMITATION");
            i2 |= 2247;
        }
        if ((i & 2248) == 2248) {
            arrayList.add("VSNCP_PDN_EXISTS_FOR_THIS_APN");
            i2 |= 2248;
        }
        if ((i & 2249) == 2249) {
            arrayList.add("VSNCP_RECONNECT_NOT_ALLOWED");
            i2 |= 2249;
        }
        if ((i & 2250) == 2250) {
            arrayList.add("IPV6_PREFIX_UNAVAILABLE");
            i2 |= 2250;
        }
        if ((i & 2251) == 2251) {
            arrayList.add("HANDOFF_PREFERENCE_CHANGED");
            i2 |= 2251;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
