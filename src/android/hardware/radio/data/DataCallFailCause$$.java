package android.hardware.radio.data;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.security.keystore.KeyProperties;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.lang.reflect.Array;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public interface DataCallFailCause$$ {
    static String toString(int i) {
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
            return "MISSING_UNKNOWN_APN";
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
        if (i == 2166) {
            return "MAXIMUM_SIZE_OF_L2_MESSAGE_EXCEEDED";
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
            return "VSNCP_APN_UNAUTHORIZED";
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
        if (i == 2252) {
            return "SLICE_REJECTED";
        }
        if (i == 2253) {
            return "MATCH_ALL_RULE_NOT_ALLOWED";
        }
        if (i == 2254) {
            return "ALL_MATCHING_RULES_FAILED";
        }
        return Integer.toString(i);
    }

    static String arrayToString(Object obj) {
        if (obj == null) {
            return PerfettoProtoLogImpl.NULL_STRING;
        }
        Class<?> cls = obj.getClass();
        if (!cls.isArray()) {
            throw new IllegalArgumentException("not an array: " + obj);
        }
        Class<?> componentType = cls.getComponentType();
        StringJoiner stringJoiner = new StringJoiner(", ", NavigationBarInflaterView.SIZE_MOD_START, NavigationBarInflaterView.SIZE_MOD_END);
        int i = 0;
        if (componentType.isArray()) {
            while (i < Array.getLength(obj)) {
                stringJoiner.add(arrayToString(Array.get(obj, i)));
                i++;
            }
        } else {
            if (cls != int[].class) {
                throw new IllegalArgumentException("wrong type: " + cls);
            }
            int[] iArr = (int[]) obj;
            int length = iArr.length;
            while (i < length) {
                stringJoiner.add(toString(iArr[i]));
                i++;
            }
        }
        return stringJoiner.toString();
    }
}
