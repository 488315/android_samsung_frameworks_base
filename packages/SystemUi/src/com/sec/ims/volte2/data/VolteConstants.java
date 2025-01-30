package com.sec.ims.volte2.data;

import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class VolteConstants {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum AudioCodecType {
        AUDIO_CODEC_NONE(PeripheralBarcodeConstants.Symbology.Type.TYPE_NONE),
        AUDIO_CODEC_AMRWB("AMR-WB"),
        AUDIO_CODEC_AMRNB("AMR-NB"),
        AUDIO_CODEC_EVSNB("EVS-NB"),
        AUDIO_CODEC_EVSWB("EVS-WB"),
        AUDIO_CODEC_EVSSWB("EVS-SWB"),
        AUDIO_CODEC_EVSFB("EVS-FB"),
        AUDIO_CODEC_EVS("EVS");

        private static final Map<String, AudioCodecType> stringToEnum = new HashMap();
        private final String mCodec;

        static {
            for (AudioCodecType audioCodecType : values()) {
                stringToEnum.put(audioCodecType.toString(), audioCodecType);
            }
        }

        AudioCodecType(String str) {
            this.mCodec = str;
        }

        public static AudioCodecType fromString(String str) {
            return stringToEnum.get(str);
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.mCodec;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class AudioInterfaceMode {
        public static final int AUTO = 3;
        public static final int CMC_AUTO = 4;
        public static final int CMC_CS_RELAY = 5;
        public static final int CPVE = 1;
        public static final int DELAYED_MEDIA = 7;
        public static final int DELAYED_MEDIA_CMC = 8;
        public static final int SAE = 0;
        public static final int STOP = 2;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class BearerState {
        public static final int BEARER_STATE_CLOSED = 3;
        public static final int BEARER_STATE_ESTABLISHED = 1;
        public static final int BEARER_STATE_MODIFIED = 2;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class CAMERA_STATE {
        public static final int DISABLED_ERROR = 7;
        public static final int READY = 0;
        public static final int START_FAIL = 2;
        public static final int START_SUCCESS = 1;
        public static final int STOP_FAIL = 4;
        public static final int STOP_SUCCESS = 3;
        public static final int SWITCH_FAIL = 6;
        public static final int SWITCH_SUCCESS = 5;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class CMC_RECORD_INFO_EVENT {
        public static final int CMC_RECORD_EVENT_START = 1;
        public static final int CMC_RECORD_EVENT_STOP_ERROR_UNKNOWN = 100;
        public static final int CMC_RECORD_EVENT_STOP_MAX_DURATION_REACHED = 3;
        public static final int CMC_RECORD_EVENT_STOP_MAX_FILESIZE_REACHED = 4;
        public static final int CMC_RECORD_EVENT_STOP_NORMAL = 2;
        public static final int CMC_RECORD_EVENT_UNKNOWN = 0;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class CMC_RECORD_STATE {
        public static final int CMC_RECORDER_ERROR_UNKNOWN = 1;
        public static final int CMC_RECORDER_INFO_DURATION_IN_PROGRESS = 901;
        public static final int CMC_RECORDER_INFO_FILESIZE_IN_PROGRESS = 900;
        public static final int CMC_RECORDER_INFO_MAX_DURATION_REACHED = 800;
        public static final int CMC_RECORDER_INFO_MAX_FILESIZE_REACHED = 801;
        public static final int CMC_RECORDER_INFO_START_SUCCESS = 701;
        public static final int CMC_RECORDER_INFO_STOP_SUCCESS = 702;
        public static final int CMC_RECORDER_SUCCESS = 0;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class CallType {
        public static final int CALL_TYPE_AUDIO = 1;
        public static final int CALL_TYPE_AUDIO_CONFERENCE = 5;
        public static final int CALL_TYPE_E911_AUDIO = 7;
        public static final int CALL_TYPE_E911_VIDEO = 8;
        public static final int CALL_TYPE_RTT_AUDIO_CONFERENCE = 16;
        public static final int CALL_TYPE_RTT_E911_AUDIO = 18;
        public static final int CALL_TYPE_RTT_E911_VIDEO = 19;
        public static final int CALL_TYPE_RTT_VIDEO = 15;
        public static final int CALL_TYPE_RTT_VIDEO_CONFERENCE = 17;
        public static final int CALL_TYPE_RTT_VOICE = 14;
        public static final int CALL_TYPE_SOFTPHONE_E911_AUDIO = 13;
        public static final int CALL_TYPE_TTY_FULL = 9;
        public static final int CALL_TYPE_TTY_HCO = 10;
        public static final int CALL_TYPE_TTY_VCO = 11;
        public static final int CALL_TYPE_UNKNOWN = 0;
        public static final int CALL_TYPE_USSD = 12;
        public static final int CALL_TYPE_VIDEO = 2;
        public static final int CALL_TYPE_VIDEO_CONFERENCE = 6;
        public static final int CALL_TYPE_VIDEO_SHARE_RX = 4;
        public static final int CALL_TYPE_VIDEO_SHARE_TX = 3;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class CameraState {
        public static final int ACTIVE = 2;
        public static final int INACTIVE = 3;
        public static final int OFF = 1;

        /* renamed from: ON */
        public static final int f650ON = 0;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class ConfUriType {
        public static final int PROFILE = 0;
        public static final int PROFILE_RAW = 1;
        public static final int SIM = 2;
        public static final int SIM_RAW = 3;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class ECMP_MODE {
        public static final int ECMP_CS = 0;
        public static final int ECMP_IMS = 1;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class EMOJI_STATE {
        public static final int START_FAILURE = 1;
        public static final int START_SUCCESS = 0;
        public static final int STOP_FAILURE = 3;
        public static final int STOP_SUCCESS = 2;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class EccCategory {
        public static final int ECC_CATEGORY_AMBULANCE = 2;
        public static final int ECC_CATEGORY_CYBER_TERROR = 19;
        public static final int ECC_CATEGORY_DECONFIGURED = 255;
        public static final int ECC_CATEGORY_DEFAULT = 0;
        public static final int ECC_CATEGORY_FIRE_BRIDGE = 4;
        public static final int ECC_CATEGORY_MARINE_GUARD = 8;
        public static final int ECC_CATEGORY_MOUNTAIN_RESCUE = 16;
        public static final int ECC_CATEGORY_NATIONAL_INTELLIGENCE_KT = 7;
        public static final int ECC_CATEGORY_NATIONAL_INTELLIGENCE_SKT = 6;
        public static final int ECC_CATEGORY_POLICE = 1;
        public static final int ECC_CATEGORY_SCHOOL_VIOLENCE = 18;
        public static final int ECC_CATEGORY_SMUGGLING_REPORT = 9;
        public static final int ECC_CATEGORY_SPY_REPORT = 3;
        public static final int ECC_CATEGORY_TRAFFIC = 20;
        public static final int ECC_CATEGORY_UNSPECIFIED = 254;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class EccCategoryUrn {
        public static final String URN_ECC_CATEGORY_AMBULANCE = "urn:service:sos.ambulance";
        public static final String URN_ECC_CATEGORY_CYBER_TERROR = "urn:service:sos.country-specific.kr.118";
        public static final String URN_ECC_CATEGORY_DEFAULT = "urn:service:sos";
        public static final String URN_ECC_CATEGORY_FIRE_BRIDGE = "urn:service:sos.fire";
        public static final String URN_ECC_CATEGORY_MARINE_GUARD = "urn:service:sos.marine";
        public static final String URN_ECC_CATEGORY_MOUNTAIN_RESCUE = "urn:service:sos.mountain";
        public static final String URN_ECC_CATEGORY_NATIONAL_INTELLIGENCE = "urn:service:sos.country-specific.kr.111";
        public static final String URN_ECC_CATEGORY_POLICE = "urn:service:sos.police";
        public static final String URN_ECC_CATEGORY_SCHOOL_VIOLENCE = "urn:service:sos.country-specific.kr.117";
        public static final String URN_ECC_CATEGORY_SMUGGLING_REPORT = "urn:service:sos.country-specific.kr.125";
        public static final String URN_ECC_CATEGORY_SPY_REPORT = "urn:service:sos.country-specific.kr.113";
        public static final String URN_ECC_CATEGORY_TRAFFIC = "urn:service:sos.traffic";
        public static final String URN_ECC_CATEGORY_UNSPECIFIED = "urn:service:unspecified";
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class ErrorCode {
        public static final int ACTIVE_CALL_ON_ANOTHER_SOFTPHONE = 3007;
        public static final int ADDRESS_INCOMPLETE = 484;
        public static final int ALREADY_CALL_RELEASED = 230;
        public static final int ALTERNATIVE_SERVICES = 380;
        public static final int ALTERNATIVE_SERVICES_EMERGENCY = 381;
        public static final int ALTERNATIVE_SERVICES_EMERGENCY_CSFB = 382;
        public static final int ALTERNATIVE_SERVICE_ONLY_ERROR_CAUSE = 0;
        public static final int ALTERNATIVE_SERVICE_ONLY_ERROR_COUNTS = 3;
        public static final int ALTERNATIVE_SERVICE_ONLY_ERROR_TEXT = 1;
        public static final int ALTERNATIVE_SERVICE_ONLY_ERROR_TYPE = 2;
        public static final int BAD_EXTENSION = 420;
        public static final int BAD_GATEWAY = 502;
        public static final int BAD_REQUEST = 400;
        public static final int BUSY_EVERYWHERE = 600;
        public static final int BUSY_HERE = 486;
        public static final int CALL_18X_RETRANSMISSION_TIMEOUT = 1124;
        public static final int CALL_5XX_RESPONSE = 2201;
        public static final int CALL_BARRED_BY_NETWORK = 2801;
        public static final int CALL_BARRED_DUE_TO_SSAC = 1116;
        public static final int CALL_CANCEL_MODIFY_REQUESTED = 1122;
        public static final int CALL_CANCEL_TRANSFER_FAILED = 1121;
        public static final int CALL_CANCEL_TRANSFER_SUCCESS = 1120;
        public static final int CALL_ENDED_BY_NW_HANDOVER_BEFORE_100_TRYING = 1117;
        public static final int CALL_END_CALL_NW_HANDOVER = 1107;
        public static final int CALL_END_REASON_IMS_DEREGISTRATION = 1115;
        public static final int CALL_END_REASON_TELEPHONY_NOT_RESPONDING = 1123;
        public static final int CALL_FAILED = 2301;
        public static final int CALL_FAILED_SERVICE_UNAVAILABLE_NO_POPUP = 2306;
        public static final int CALL_FORBIDDEN = 2001;
        public static final int CALL_FORBIDDEN_RSN_EXPIRED = 2302;
        public static final int CALL_FORBIDDEN_RSN_GROUP_CALL_SERVICE_UNAVAILABLE = 2303;
        public static final int CALL_FORBIDDEN_RSN_OUTGOING_CALLS_IMPOSSIBLE = 2305;
        public static final int CALL_FORBIDDEN_RSN_TEMPORARY_DISABILITY = 2304;
        public static final int CALL_HAS_BEEN_TRANSFERRED_TO_ANOTHER_DEVICE = 3002;
        public static final int CALL_HOLD_FAILED = 1111;
        public static final int CALL_INVITE_TIMEOUT = 1114;
        public static final int CALL_INVITE_TIMEOUT_NR = 1125;
        public static final int CALL_NOT_ACCEPTABLE_DIVERT = 2101;
        public static final int CALL_REJECT_REASON_USR_BUSY_CS_CALL = 1108;
        public static final int CALL_RESUME_FAILED = 1112;
        public static final int CALL_RING_TIMER_EXPIRED = 1802;
        public static final int CALL_SESSION_ABORT = 1101;
        public static final int CALL_SESSION_TERMINATED = 1102;
        public static final int CALL_SESSION_TIMEOUT = 1103;
        public static final int CALL_STATUS_CONF_ADD_USER_TO_SESSION_FAILURE = 1105;
        public static final int CALL_STATUS_CONF_REMOVE_USER_FROM_SESSION_FAILURE = 1106;
        public static final int CALL_STATUS_CONF_START_SESSION_FAILURE = 1104;
        public static final int CALL_SWITCH_FAILURE = 1109;
        public static final int CALL_SWITCH_REJECTED = 1110;
        public static final int CALL_TEMP_UNAVAILABLE_415_CAUSE = 1113;
        public static final int CALL_TEMP_UNAVAILABLE_WITH_380_CAUSE = 2202;
        public static final int CALL_TEMP_UNAVAILABLE_WITH_415_CAUSE = 2203;
        public static final int CALL_TRANSFER_FAILED = 1119;
        public static final int CALL_TRANSFER_SUCCESS = 1118;
        public static final int CANCEL_CALL_BUSY = 2505;
        public static final int CANCEL_CALL_COMPLETED_ELSEWHERE = 3001;
        public static final int CANCEL_CALL_COMPLETED_ELSEWHERE_FORKED = 3010;
        public static final int CANCEL_CALL_COMPLETION = 2504;
        public static final int CANCEL_SERVICE_NOT_ALLOWED_IN_THIS_LOCATION = 3004;
        public static final int CANCEL_WITH_DECLINED_TEXT = 3009;
        public static final int CLIENT_ERROR = 1001;
        public static final int CLIENT_ERROR_AUDIO_CREATE_FAILURE = 1007;
        public static final int CLIENT_ERROR_NOT_ALLOWED_URI = 1004;
        public static final int CLIENT_ERROR_NOT_ENOUGH_PARTICIPANT = 1005;
        public static final int CLIENT_ERROR_NO_CALL_SESSION = 1006;
        public static final int CLIENT_ERROR_NO_REGISTRATION = 1003;
        public static final int CLIENT_ERROR_SESSION_CREATE_FAILURE = 1002;
        public static final int CMC_E911_NOT_ALLOWED_ON_SD = 6003;
        public static final int CMC_ERROR_CODE_MAX = 6999;
        public static final int CMC_PD_CALL_EXISTS_ON_THE_OTHER_SLOT = 6011;
        public static final int CMC_PD_NOT_REGISTERED = 6001;
        public static final int CMC_PD_UNREACHABLE = 6002;
        public static final int CMC_SD_CONNECTION_LOST = 6006;
        public static final int CMC_SD_FORCE_CSFB = 6010;
        public static final int CMC_SD_NOT_REGISTERED = 6004;
        public static final int CMC_SD_VT_NOT_SUPPORT = 6005;
        public static final int CMC_SERVER_RELAY_RESTRICTED = 6012;
        public static final int DATA_CONNECTION_LOST = 1701;
        public static final int DECLINE = 603;
        public static final int DEREG_SUCCEEDED = 1604;
        public static final int DIAL_ALTERNATIVE_NUMBER = 2412;
        public static final int DNS_FAILURE_HOST = 1503;
        public static final int DNS_FAILURE_NAPTR = 1504;
        public static final int DNS_FAILURE_SVC = 1505;
        public static final int DNS_QUERY_RETRY_FAILED = 2403;
        public static final int DNS_QUERY_RETRY_START = 2402;
        public static final int DOES_NOT_EXIST_ANYWHERE = 604;
        public static final int DSDA_FALLBACK_DSDS = 1508;
        public static final int EMERGENCY_CALLS_OVER_WIFI_NOT_ALLOWED = 3008;
        public static final int EMERGENCY_PERM_FAILURE = 2697;
        public static final int EMERGENCY_TEMP_FAILURE = 2696;
        public static final int END_BY_REGULAR_CALL_RELEASE = 6009;
        public static final int FAILED_TO_GO_READY = 1801;
        public static final int FORBIDDEN = 403;
        public static final int FORBIDDEN_MULTI_CALL_LIMITATION = 2510;
        public static final int FORBIDDEN_SERVICE_NOT_ALLOWED_IN_THIS_LOCATION = 3003;
        public static final int IDC_ADC_REQUEST_FAILED = 7001;
        public static final int IMEI_NOT_ACCEPTED = 2698;
        public static final int INTERVAL_TOO_BRIEF = 423;
        public static final int KDDI_INVITE_FAIL = 2699;
        public static final int LINE_IN_USE_ON_OTHER_DEVICE = 2413;
        public static final int LOCAL_OK = 220;
        public static final int LOST_LTE_AND_WIFI_CONNECTION = 2503;
        public static final int LTE_911_FAIL = 2507;
        public static final int MAKECALL_REG_FAILURE_GENERAL = 2005;
        public static final int MAKECALL_REG_FAILURE_REG_403 = 2003;
        public static final int MAKECALL_REG_FAILURE_REG_423 = 2004;
        public static final int MAKECALL_REG_FAILURE_TIMER_F = 2002;
        public static final int MDMN_CALL_FORWARDED = 4001;
        public static final int MDMN_PULLCALL_BY_PRIMARY = 6007;
        public static final int MDMN_PULLCALL_BY_SECONDARY = 6008;
        public static final int MDMN_PUSHCALL_TO_PRIMARY = 4002;
        public static final int METHOD_NOT_ALLOWED = 405;
        public static final int MISSED_CALL_NOTIFICATION = 1803;
        public static final int NETWORK_UNREACHABLE = 2102;
        public static final int NON_STANDARD_ERROR_CODE_BASE_CALL = 1100;
        public static final int NOT_ACCEPTABLE = 406;
        public static final int NOT_ACCEPTABLE2 = 606;
        public static final int NOT_ACCEPTABLE_HERE = 488;
        public static final int NOT_FOUND = 404;
        public static final int NOT_IMPLEMENTED = 501;
        public static final int NO_ERROR = 1000;

        /* renamed from: OK */
        public static final int f651OK = 200;
        public static final int OTHER_SECONDARY_DEVICE_IN_USE = 3006;
        public static final int PPP_OPEN_FAILURE = 1302;
        public static final int PPP_STATUS_CLOSE_EVENT = 1301;
        public static final int PRECONDITION_FAILURE = 580;
        public static final int PULLED_BY_ANOTHER_DEVICE = 2506;
        public static final int QOS_FAILURE = 1201;
        public static final int QOS_INCALL_SUSPEND = 1203;
        public static final int QOS_INCALL_UNAWARE = 1204;
        public static final int QOS_NW_UNAWARE = 1202;
        public static final int REG_NOT_SUBSCRIBED = 2409;
        public static final int REG_NOT_SUBSCRIBED_NON_403 = 2410;
        public static final int REG_NOT_SUBSCRIBED_REASON = 2411;
        public static final int REG_RETRY_FAILED = 2405;
        public static final int REG_RETRY_START = 2404;
        public static final int REG_SSL_CERTIFICATE_FAILURE = 2401;
        public static final int REG_SUBSCRIBED = 2408;
        public static final int REG_SUSPENDED = 2511;
        public static final int REMOTE_OK = 210;
        public static final int REQUEST_TERMINATED = 487;
        public static final int REQUEST_TIMEOUT = 408;
        public static final int RRC_CONNECTION_REJECT = 1702;
        public static final int RTP_TIME_OUT = 1401;
        public static final int RTT_E911_CALL_FAIL = 2414;
        public static final int SDP_PROCESSING_FAILED = 1506;
        public static final int SERVER_ERROR = 1502;
        public static final int SERVER_INTERNAL_ERROR = 500;
        public static final int SERVER_INTERNAL_ERROR_WTH_BEARER_CAPABILITY = 2702;
        public static final int SERVER_INTERNAL_ERROR_WTH_INCOMPATIBLE_DESTINATION = 2701;
        public static final int SERVER_INTERNAL_ERROR_WTH_RESTORATION = 2205;
        public static final int SERVER_TIME_OUT = 504;
        public static final int SERVER_UNREACHABLE = 1501;
        public static final int SERVICE_UNAVAILABLE = 503;
        public static final int SERVICE_UNAVAILABLE_WITH_IMS_OUTAGE = 2502;
        public static final int SESSION_EXPIRED = 2106;
        public static final int SESSION_INTERVAL_TOO_SMALL = 422;
        public static final int SIMULTANEOUS_CALL_LIMIT_HAS_ALREADY_BEEN_REACHED = 3005;
        public static final int SIP_REG_FAILURE = 1601;
        public static final int SUBSCRIBE_RETRY_FAILED = 2407;
        public static final int SUBSCRIBE_RETRY_START = 2406;
        public static final int TEMPORARILY_UNAVAILABLE = 480;
        public static final int TIMER_VZW_EXPIRED = 2501;
        public static final int UNKNOWN = -1;
        public static final int UNSUPPORTED_MEDIA_TYPE = 415;
        public static final int UNSUPPORTED_URI_SCHEME = 416;
        public static final int UT_RETRY_TO_CDMA_DIAL = 5001;
        public static final int VERSION_NOT_SUPPORTED = 505;
        public static final int VONR_NOT_POSSIBLE = 1507;
        public static final int WIFI_CONNECTION_LOST = 1703;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class ErrorDesc {
            public static final String ERROR_DESC_IMS_OUTAGE = "IMS OUTAGE";
        }

        public static String toString(int i) {
            if (i == 1000) {
                return "No Error";
            }
            if (i == 1001) {
                return "Client error";
            }
            if (i == 2101) {
                return "Call not acceptable divert";
            }
            if (i == 2102) {
                return "Network Unreachable";
            }
            if (i == 2501) {
                return "Timer_VZW expired";
            }
            if (i == 2502) {
                return "SERVICE_UNAVAILABLE With IMS OUTAGE";
            }
            switch (i) {
                case ALTERNATIVE_SERVICES /* 380 */:
                    break;
                case 400:
                    break;
                case REQUEST_TIMEOUT /* 408 */:
                    break;
                case UNSUPPORTED_MEDIA_TYPE /* 415 */:
                    break;
                case TEMPORARILY_UNAVAILABLE /* 480 */:
                    break;
                case ADDRESS_INCOMPLETE /* 484 */:
                    break;
                case 500:
                    break;
                case 503:
                    break;
                case DECLINE /* 603 */:
                    break;
                case NOT_ACCEPTABLE2 /* 606 */:
                    break;
                case CALL_SESSION_ABORT /* 1101 */:
                    break;
                case CALL_SESSION_TERMINATED /* 1102 */:
                    break;
                case CALL_SESSION_TIMEOUT /* 1103 */:
                    break;
                case CALL_STATUS_CONF_START_SESSION_FAILURE /* 1104 */:
                    break;
                case CALL_STATUS_CONF_ADD_USER_TO_SESSION_FAILURE /* 1105 */:
                    break;
                case CALL_STATUS_CONF_REMOVE_USER_FROM_SESSION_FAILURE /* 1106 */:
                    break;
                case CALL_END_CALL_NW_HANDOVER /* 1107 */:
                    break;
                case CALL_REJECT_REASON_USR_BUSY_CS_CALL /* 1108 */:
                    break;
                case CALL_SWITCH_FAILURE /* 1109 */:
                    break;
                case CALL_SWITCH_REJECTED /* 1110 */:
                    break;
                case CALL_HOLD_FAILED /* 1111 */:
                    break;
                case CALL_RESUME_FAILED /* 1112 */:
                    break;
                case CALL_TEMP_UNAVAILABLE_415_CAUSE /* 1113 */:
                    break;
                case CALL_INVITE_TIMEOUT /* 1114 */:
                    break;
                case CALL_INVITE_TIMEOUT_NR /* 1125 */:
                    break;
                case SERVER_INTERNAL_ERROR_WTH_RESTORATION /* 2205 */:
                    break;
                case REG_NOT_SUBSCRIBED /* 2409 */:
                    break;
                case FORBIDDEN_MULTI_CALL_LIMITATION /* 2510 */:
                    break;
                case SERVER_INTERNAL_ERROR_WTH_INCOMPATIBLE_DESTINATION /* 2701 */:
                    break;
                case CALL_BARRED_BY_NETWORK /* 2801 */:
                    break;
                default:
                    switch (i) {
                        case 403:
                            break;
                        case 404:
                            break;
                        case 405:
                            break;
                        case NOT_ACCEPTABLE /* 406 */:
                            break;
                        default:
                            switch (i) {
                                case BUSY_HERE /* 486 */:
                                    break;
                                case REQUEST_TERMINATED /* 487 */:
                                    break;
                                case NOT_ACCEPTABLE_HERE /* 488 */:
                                    break;
                                default:
                                    switch (i) {
                                        case CALL_5XX_RESPONSE /* 2201 */:
                                            break;
                                        case CALL_TEMP_UNAVAILABLE_WITH_380_CAUSE /* 2202 */:
                                            break;
                                        case CALL_TEMP_UNAVAILABLE_WITH_415_CAUSE /* 2203 */:
                                            break;
                                        default:
                                            switch (i) {
                                                case CALL_FAILED /* 2301 */:
                                                    break;
                                                case CALL_FORBIDDEN_RSN_EXPIRED /* 2302 */:
                                                case CALL_FORBIDDEN_RSN_GROUP_CALL_SERVICE_UNAVAILABLE /* 2303 */:
                                                case CALL_FORBIDDEN_RSN_TEMPORARY_DISABILITY /* 2304 */:
                                                case CALL_FORBIDDEN_RSN_OUTGOING_CALLS_IMPOSSIBLE /* 2305 */:
                                                    break;
                                                case CALL_FAILED_SERVICE_UNAVAILABLE_NO_POPUP /* 2306 */:
                                                    break;
                                                default:
                                                    switch (i) {
                                                        case CANCEL_CALL_COMPLETION /* 2504 */:
                                                            break;
                                                        case CANCEL_CALL_BUSY /* 2505 */:
                                                            break;
                                                        case PULLED_BY_ANOTHER_DEVICE /* 2506 */:
                                                            break;
                                                        default:
                                                            switch (i) {
                                                            }
                                                    }
                                            }
                                    }
                            }
                    }
            }
            return "Call not allowed(Invite Failure)";
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class GENERAL_EVENT {
        public static final int SCREEN_SHARING_EVENT = 100;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class MediaDirection {
        public static final int INACTIVE = 1;
        public static final int RECVONLY = 3;
        public static final int SENDONLY = 2;
        public static final int SENDRECV = 4;
        public static final int UNKNOWN = 0;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class MediaQuality {
        public static final int UNKNOWN = -1;
        public static final int VIDEO = 11;
        public static final int VIDEO_CIF = 14;
        public static final int VIDEO_HD = 15;
        public static final int VIDEO_HD720 = 16;
        public static final int VIDEO_QCIF = 12;
        public static final int VIDEO_QVGA = 13;
        public static final int VOICE = 0;
        public static final int VOICE_HD = 1;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class NR_MODE {
        public static final int NR_MODE_NSA_ONLY = 1;
        public static final int NR_MODE_SA_NSA = 2;
        public static final int NR_MODE_SA_ONLY = 0;
        public static final int NR_MODE_TEMP_SA_DISABLE = 4;
        public static final int NR_MODE_TEMP_SA_ENABLE = 3;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class ParticipantState {
        public static final int ACTIVE = 2;
        public static final int ALERTING = 5;
        public static final int INVALID = 0;
        public static final int INVITING = 1;
        public static final int MAX = 7;
        public static final int NON_ACTIVE = 4;
        public static final int ONHOLD = 6;
        public static final int REMOVING = 3;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Qci {
        public static final int QCI_AUDIO = 1;
        public static final int QCI_VIDEO_GBR = 2;
        public static final int QCI_VIDEO_NGBR = 8;
        public static final int QCI_VIDEO_NGBR_7 = 7;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class RECORD_STATE {
        public static final int START_FAILURE = 1;
        public static final int START_FAILURE_NO_SPACE = 2;
        public static final int START_SUCCESS = 0;
        public static final int STOP_FAILURE = 4;
        public static final int STOP_NO_SPACE = 5;
        public static final int STOP_SUCCESS = 3;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum STATE {
        Idle,
        ReadyToCall,
        IncomingCall,
        OutGoingCall,
        AlertingCall,
        InCall,
        HoldingCall,
        HeldCall,
        ResumingCall,
        ModifyingCall,
        ModifyRequested,
        HoldingVideo,
        VideoHeld,
        ResumingVideo,
        EndingCall,
        EndedCall
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class USSDDCS {
        public static final int USSD_DCS_7_BIT = 0;
        public static final int USSD_DCS_8_BIT = 4;
        public static final int USSD_DCS_ASCII = 148;
        public static final int USSD_DCS_UCS2 = 8;
        public static final int USSD_DCS_UCS2_PBL = 17;
        public static final int USSD_DCS_UNSPECIFIED = 15;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class USSDStatus {
        public static final int SS_USSD_ACTION_REQUIRE = 2;
        public static final int SS_USSD_NOT_SUPPORT = 5;
        public static final int SS_USSD_NO_ACTION_REQUIRE = 1;
        public static final int SS_USSD_OTHER_CLIENT = 4;
        public static final int SS_USSD_TERMINATED_BY_NET = 3;
        public static final int SS_USSD_TIME_OUT = 6;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class USSDType {
        public static final int SS_USSD_TYPE_NOTIFY_RES = 4;
        public static final int SS_USSD_TYPE_USER_INITIATED = 1;
        public static final int SS_USSD_TYPE_USER_RELEASE = 3;
        public static final int SS_USSD_TYPE_USER_RES = 2;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class VCS_EXTRA {
        public static final String ACTION = "com.samsung.telephony.extra.ims.VCS_ACTION";
        public static final String DURATION = "com.samsung.telephony.extra.ims.VCS_DURATION";
        public static final String SLIDING_STAGE = "com.samsung.telephony.extra.ims.VCS_SLIDING_STAGE";
        public static final String TIMESTAMP = "com.samsung.telephony.extra.ims.VCS_TIMESTAMP";
        public static final String X_POS = "com.samsung.telephony.extra.ims.VCS_X_POS";
        public static final String Y_POS = "com.samsung.telephony.extra.ims.VCS_Y_POS";
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class VIDEO_QUALITY {
        public static final int HIGH = 2;
        public static final int LOW = 0;
        public static final int MEDIUM = 1;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class VIDEO_STATE {
        public static final int DOWNGRADED = 3;
        public static final int HELD = 1;
        public static final int READY = 0;
        public static final int RESUMED = 2;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class VideoOrientation {
        public static final int LAND = 1;
        public static final int PORT = 0;
    }
}
