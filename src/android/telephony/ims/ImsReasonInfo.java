package android.telephony.ims;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public final class ImsReasonInfo implements Parcelable {
    public static final int CODE_ACCESS_CLASS_BLOCKED = 1512;
    public static final int CODE_ANSWERED_ELSEWHERE = 1014;
    public static final int CODE_BLACKLISTED_CALL_ID = 506;
    public static final int CODE_CALL_BARRED = 240;
    public static final int CODE_CALL_DROP_IWLAN_TO_LTE_UNAVAILABLE = 1100;
    public static final int CODE_CALL_END_CAUSE_CALL_PULL = 1016;
    public static final int CODE_CALL_PULL_OUT_OF_SYNC = 1015;
    public static final int CODE_CONF_ADD_USER_FAILURE = 3115;
    public static final int CODE_DATA_DISABLED = 1406;
    public static final int CODE_DATA_LIMIT_REACHED = 1405;
    public static final int CODE_DIAL_MODIFIED_TO_DIAL = 246;
    public static final int CODE_DIAL_MODIFIED_TO_DIAL_VIDEO = 247;
    public static final int CODE_DIAL_MODIFIED_TO_SS = 245;
    public static final int CODE_DIAL_MODIFIED_TO_USSD = 244;
    public static final int CODE_DIAL_VIDEO_MODIFIED_TO_DIAL = 248;
    public static final int CODE_DIAL_VIDEO_MODIFIED_TO_DIAL_VIDEO = 249;
    public static final int CODE_DIAL_VIDEO_MODIFIED_TO_SS = 250;
    public static final int CODE_DIAL_VIDEO_MODIFIED_TO_USSD = 251;
    public static final int CODE_ECBM_NOT_SUPPORTED = 901;
    public static final int CODE_EMERGENCY_CALL_OVER_WFC_NOT_AVAILABLE = 1622;
    public static final int CODE_EMERGENCY_PERM_FAILURE = 364;
    public static final int CODE_EMERGENCY_TEMP_FAILURE = 363;
    public static final int CODE_EPDG_TUNNEL_ESTABLISH_FAILURE = 1400;
    public static final int CODE_EPDG_TUNNEL_LOST_CONNECTION = 1402;
    public static final int CODE_EPDG_TUNNEL_REKEY_FAILURE = 1401;
    public static final int CODE_FDN_BLOCKED = 241;
    public static final int CODE_IKEV2_AUTH_FAILURE = 1408;
    public static final int CODE_IMEI_NOT_ACCEPTED = 243;
    public static final int CODE_IWLAN_DPD_FAILURE = 1300;
    public static final int CODE_LOCAL_CALL_BUSY = 142;
    public static final int CODE_LOCAL_CALL_CS_RETRY_REQUIRED = 146;
    public static final int CODE_LOCAL_CALL_DECLINE = 143;
    public static final int CODE_LOCAL_CALL_EXCEEDED = 141;
    public static final int CODE_LOCAL_CALL_RESOURCE_RESERVATION_FAILED = 145;
    public static final int CODE_LOCAL_CALL_TERMINATED = 148;
    public static final int CODE_LOCAL_CALL_VCC_ON_PROGRESSING = 144;
    public static final int CODE_LOCAL_CALL_VOLTE_RETRY_REQUIRED = 147;
    public static final int CODE_LOCAL_E911_RETRY_REQUIRED = 9000;
    public static final int CODE_LOCAL_ENDED_BY_CONFERENCE_MERGE = 108;
    public static final int CODE_LOCAL_HO_NOT_FEASIBLE = 149;
    public static final int CODE_LOCAL_ILLEGAL_ARGUMENT = 101;
    public static final int CODE_LOCAL_ILLEGAL_STATE = 102;
    public static final int CODE_LOCAL_IMS_NOT_SUPPORTED_ON_DEVICE = 150;
    public static final int CODE_LOCAL_IMS_SERVICE_DOWN = 106;
    public static final int CODE_LOCAL_INTERNAL_ERROR = 103;
    public static final int CODE_LOCAL_LOW_BATTERY = 112;
    public static final int CODE_LOCAL_NETWORK_IP_CHANGED = 124;
    public static final int CODE_LOCAL_NETWORK_NO_LTE_COVERAGE = 122;
    public static final int CODE_LOCAL_NETWORK_NO_SERVICE = 121;
    public static final int CODE_LOCAL_NETWORK_ROAMING = 123;
    public static final int CODE_LOCAL_NOT_REGISTERED = 132;
    public static final int CODE_LOCAL_NO_PENDING_CALL = 107;
    public static final int CODE_LOCAL_POWER_OFF = 111;
    public static final int CODE_LOCAL_SERVICE_UNAVAILABLE = 131;
    public static final int CODE_LOW_BATTERY = 505;
    public static final int CODE_MAXIMUM_NUMBER_OF_CALLS_REACHED = 1403;
    public static final int CODE_MEDIA_INIT_FAILED = 401;
    public static final int CODE_MEDIA_NOT_ACCEPTABLE = 403;
    public static final int CODE_MEDIA_NO_DATA = 402;
    public static final int CODE_MEDIA_UNSPECIFIED = 404;
    public static final int CODE_MULTIENDPOINT_NOT_SUPPORTED = 902;
    public static final int CODE_NETWORK_CONGESTION = 1624;
    public static final int CODE_NETWORK_DETACH = 1513;
    public static final int CODE_NETWORK_REJECT = 1504;
    public static final int CODE_NETWORK_RESP_TIMEOUT = 1503;
    public static final int CODE_NO_CSFB_IN_CS_ROAM = 1516;
    public static final int CODE_NO_VALID_SIM = 1501;
    public static final int CODE_OEM_CAUSE_1 = 61441;
    public static final int CODE_OEM_CAUSE_10 = 61450;
    public static final int CODE_OEM_CAUSE_11 = 61451;
    public static final int CODE_OEM_CAUSE_12 = 61452;
    public static final int CODE_OEM_CAUSE_13 = 61453;
    public static final int CODE_OEM_CAUSE_14 = 61454;
    public static final int CODE_OEM_CAUSE_15 = 61455;
    public static final int CODE_OEM_CAUSE_2 = 61442;
    public static final int CODE_OEM_CAUSE_3 = 61443;
    public static final int CODE_OEM_CAUSE_4 = 61444;
    public static final int CODE_OEM_CAUSE_5 = 61445;
    public static final int CODE_OEM_CAUSE_6 = 61446;
    public static final int CODE_OEM_CAUSE_7 = 61447;
    public static final int CODE_OEM_CAUSE_8 = 61448;
    public static final int CODE_OEM_CAUSE_9 = 61449;
    public static final int CODE_OEM_CMC_END_BY_REGULAR_CALL_RELEASE = 4005;
    public static final int CODE_OEM_CMC_PD_PULL = 4003;
    public static final int CODE_OEM_CMC_REJECT_E911_NOT_ALLOWED_ON_SD = 4007;
    public static final int CODE_RADIO_ACCESS_FAILURE = 1505;
    public static final int CODE_RADIO_INTERNAL_ERROR = 1502;
    public static final int CODE_RADIO_LINK_FAILURE = 1506;
    public static final int CODE_RADIO_LINK_LOST = 1507;
    public static final int CODE_RADIO_OFF = 1500;
    public static final int CODE_RADIO_RELEASE_ABNORMAL = 1511;
    public static final int CODE_RADIO_RELEASE_NORMAL = 1510;
    public static final int CODE_RADIO_SETUP_FAILURE = 1509;
    public static final int CODE_RADIO_UPLINK_FAILURE = 1508;
    public static final int CODE_REGISTRATION_ERROR = 1000;
    public static final int CODE_REJECTED_ELSEWHERE = 1017;
    public static final int CODE_REJECT_1X_COLLISION = 1603;
    public static final int CODE_REJECT_CALL_ON_OTHER_SUB = 1602;
    public static final int CODE_REJECT_CALL_TYPE_NOT_ALLOWED = 1605;
    public static final int CODE_REJECT_CONFERENCE_TTY_NOT_ALLOWED = 1617;
    public static final int CODE_REJECT_INTERNAL_ERROR = 1612;
    public static final int CODE_REJECT_MAX_CALL_LIMIT_REACHED = 1608;
    public static final int CODE_REJECT_ONGOING_CALL_SETUP = 1607;
    public static final int CODE_REJECT_ONGOING_CALL_TRANSFER = 1611;
    public static final int CODE_REJECT_ONGOING_CALL_UPGRADE = 1616;
    public static final int CODE_REJECT_ONGOING_CALL_WAITING_DISABLED = 1601;
    public static final int CODE_REJECT_ONGOING_CONFERENCE_CALL = 1618;
    public static final int CODE_REJECT_ONGOING_CS_CALL = 1621;
    public static final int CODE_REJECT_ONGOING_E911_CALL = 1606;
    public static final int CODE_REJECT_ONGOING_ENCRYPTED_CALL = 1620;
    public static final int CODE_REJECT_ONGOING_HANDOVER = 1614;
    public static final int CODE_REJECT_QOS_FAILURE = 1613;
    public static final int CODE_REJECT_SERVICE_NOT_REGISTERED = 1604;
    public static final int CODE_REJECT_UNKNOWN = 1600;
    public static final int CODE_REJECT_UNSUPPORTED_SDP_HEADERS = 1610;
    public static final int CODE_REJECT_UNSUPPORTED_SIP_HEADERS = 1609;
    public static final int CODE_REJECT_VT_AVPF_NOT_ALLOWED = 1619;
    public static final int CODE_REJECT_VT_TTY_NOT_ALLOWED = 1615;
    public static final int CODE_REMOTE_CALL_DECLINE = 1404;
    public static final int CODE_RETRY_ON_IMS_WITHOUT_RTT = 3001;
    public static final int CODE_SESSION_MODIFICATION_FAILED = 1517;
    public static final int CODE_SIP_ALTERNATE_EMERGENCY_CALL = 1514;
    public static final int CODE_SIP_AMBIGUOUS = 376;
    public static final int CODE_SIP_BAD_ADDRESS = 337;
    public static final int CODE_SIP_BAD_REQUEST = 331;
    public static final int CODE_SIP_BUSY = 338;
    public static final int CODE_SIP_CALL_OR_TRANS_DOES_NOT_EXIST = 372;
    public static final int CODE_SIP_CLIENT_ERROR = 342;
    public static final int CODE_SIP_EXTENSION_REQUIRED = 370;
    public static final int CODE_SIP_FORBIDDEN = 332;
    public static final int CODE_SIP_GLOBAL_ERROR = 362;
    public static final int CODE_SIP_INTERVAL_TOO_BRIEF = 371;
    public static final int CODE_SIP_LOOP_DETECTED = 373;
    public static final int CODE_SIP_METHOD_NOT_ALLOWED = 366;
    public static final int CODE_SIP_NOT_ACCEPTABLE = 340;
    public static final int CODE_SIP_NOT_FOUND = 333;
    public static final int CODE_SIP_NOT_REACHABLE = 341;
    public static final int CODE_SIP_NOT_SUPPORTED = 334;
    public static final int CODE_SIP_PROXY_AUTHENTICATION_REQUIRED = 367;
    public static final int CODE_SIP_REDIRECTED = 321;
    public static final int CODE_SIP_REQUEST_CANCELLED = 339;
    public static final int CODE_SIP_REQUEST_ENTITY_TOO_LARGE = 368;
    public static final int CODE_SIP_REQUEST_PENDING = 377;
    public static final int CODE_SIP_REQUEST_TIMEOUT = 335;
    public static final int CODE_SIP_REQUEST_URI_TOO_LARGE = 369;
    public static final int CODE_SIP_SERVER_ERROR = 354;
    public static final int CODE_SIP_SERVER_INTERNAL_ERROR = 351;
    public static final int CODE_SIP_SERVER_TIMEOUT = 353;
    public static final int CODE_SIP_SERVICE_UNAVAILABLE = 352;
    public static final int CODE_SIP_TEMPRARILY_UNAVAILABLE = 336;
    public static final int CODE_SIP_TOO_MANY_HOPS = 374;
    public static final int CODE_SIP_TRANSACTION_DOES_NOT_EXIST = 343;
    public static final int CODE_SIP_UNDECIPHERABLE = 378;
    public static final int CODE_SIP_USER_MARKED_UNWANTED = 365;
    public static final int CODE_SIP_USER_REJECTED = 361;
    public static final int CODE_SUPP_SVC_CANCELLED = 1202;
    public static final int CODE_SUPP_SVC_FAILED = 1201;
    public static final int CODE_SUPP_SVC_REINVITE_COLLISION = 1203;
    public static final int CODE_TIMEOUT_1XX_WAITING = 201;
    public static final int CODE_TIMEOUT_NO_ANSWER = 202;
    public static final int CODE_TIMEOUT_NO_ANSWER_CALL_UPDATE = 203;
    public static final int CODE_UNOBTAINABLE_NUMBER = 1515;
    public static final int CODE_UNSPECIFIED = 0;
    public static final int CODE_USER_CANCELLED_SESSION_MODIFICATION = 512;
    public static final int CODE_USER_DECLINE = 504;
    public static final int CODE_USER_DECLINE_DURING_QEC_CALL = 507;
    public static final int CODE_USER_IGNORE = 503;
    public static final int CODE_USER_NOANSWER = 502;
    public static final int CODE_USER_REJECTED_SESSION_MODIFICATION = 511;
    public static final int CODE_USER_TERMINATED = 501;
    public static final int CODE_USER_TERMINATED_BY_REMOTE = 510;
    public static final int CODE_UT_CB_PASSWORD_MISMATCH = 821;
    public static final int CODE_UT_NETWORK_ERROR = 804;
    public static final int CODE_UT_NOT_SUPPORTED = 801;
    public static final int CODE_UT_OPERATION_NOT_ALLOWED = 803;
    public static final int CODE_UT_RETRY_TO_CDMA_DIAL = 805;
    public static final int CODE_UT_SERVICE_UNAVAILABLE = 802;
    public static final int CODE_UT_SS_MODIFIED_TO_DIAL = 822;
    public static final int CODE_UT_SS_MODIFIED_TO_DIAL_VIDEO = 825;
    public static final int CODE_UT_SS_MODIFIED_TO_SS = 824;
    public static final int CODE_UT_SS_MODIFIED_TO_USSD = 823;
    public static final int CODE_WFC_SERVICE_NOT_AVAILABLE_IN_THIS_LOCATION = 1623;
    public static final int CODE_WIFI_LOST = 1407;
    public static final Parcelable.Creator<ImsReasonInfo> CREATOR;
    public static final int EXTRA_CODE_CALL_RETRY_BY_SETTINGS = 3;
    public static final int EXTRA_CODE_CALL_RETRY_EMERGENCY = 4;
    public static final int EXTRA_CODE_CALL_RETRY_NORMAL = 1;
    public static final int EXTRA_CODE_CALL_RETRY_SILENT_REDIAL = 2;
    public static final int EXTRA_CODE_CMC_END_BY_REGULAR_CALL_RELEASE = 6009;
    public static final int EXTRA_CODE_E911_RETRY_CS = 382;
    public static final int EXTRA_CODE_E911_RETRY_SEARCH = 381;
    public static final int EXTRA_CODE_SIP_USER_REJECTED = 603;
    public static final int EXTRA_CODE_USER_TERMINATED = 200;
    public static final int EXTRA_CODE_USER_TERMINATED_BY_REMOTE = 210;

    @SystemApi
    public static final String EXTRA_MSG_SERVICE_NOT_AUTHORIZED = "Forbidden. Not Authorized for Service";
    private static final Map<Integer, String> sImsCodeMap;
    public int mCode;
    public int mExtraCode;
    public String mExtraMessage;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ImsCode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface UtReason {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    static {
        HashMap map = new HashMap();
        sImsCodeMap = map;
        map.put(0, "CODE_UNSPECIFIED");
        map.put(101, "CODE_LOCAL_ILLEGAL_ARGUMENT");
        map.put(102, "CODE_LOCAL_ILLEGAL_STATE");
        map.put(103, "CODE_LOCAL_INTERNAL_ERROR");
        map.put(106, "CODE_LOCAL_IMS_SERVICE_DOWN");
        map.put(107, "CODE_LOCAL_NO_PENDING_CALL");
        map.put(108, "CODE_LOCAL_ENDED_BY_CONFERENCE_MERGE");
        map.put(111, "CODE_LOCAL_POWER_OFF");
        map.put(112, "CODE_LOCAL_LOW_BATTERY");
        map.put(121, "CODE_LOCAL_NETWORK_NO_SERVICE");
        map.put(122, "CODE_LOCAL_NETWORK_NO_LTE_COVERAGE");
        map.put(123, "CODE_LOCAL_NETWORK_ROAMING");
        map.put(124, "CODE_LOCAL_NETWORK_IP_CHANGED");
        map.put(131, "CODE_LOCAL_SERVICE_UNAVAILABLE");
        map.put(132, "CODE_LOCAL_NOT_REGISTERED");
        map.put(141, "CODE_LOCAL_CALL_EXCEEDED");
        map.put(142, "CODE_LOCAL_CALL_BUSY");
        map.put(143, "CODE_LOCAL_CALL_DECLINE");
        map.put(144, "CODE_LOCAL_CALL_VCC_ON_PROGRESSING");
        map.put(145, "CODE_LOCAL_CALL_RESOURCE_RESERVATION_FAILED");
        map.put(146, "CODE_LOCAL_CALL_CS_RETRY_REQUIRED");
        map.put(147, "CODE_LOCAL_CALL_VOLTE_RETRY_REQUIRED");
        map.put(148, "CODE_LOCAL_CALL_TERMINATED");
        map.put(149, "CODE_LOCAL_HO_NOT_FEASIBLE");
        map.put(201, "CODE_TIMEOUT_1XX_WAITING");
        map.put(202, "CODE_TIMEOUT_NO_ANSWER");
        map.put(203, "CODE_TIMEOUT_NO_ANSWER_CALL_UPDATE");
        map.put(240, "CODE_CALL_BARRED");
        map.put(241, "CODE_FDN_BLOCKED");
        map.put(243, "CODE_IMEI_NOT_ACCEPTED");
        map.put(244, "CODE_DIAL_MODIFIED_TO_USSD");
        map.put(245, "CODE_DIAL_MODIFIED_TO_SS");
        map.put(246, "CODE_DIAL_MODIFIED_TO_DIAL");
        map.put(247, "CODE_DIAL_MODIFIED_TO_DIAL_VIDEO");
        map.put(248, "CODE_DIAL_VIDEO_MODIFIED_TO_DIAL");
        map.put(249, "CODE_DIAL_VIDEO_MODIFIED_TO_DIAL_VIDEO");
        map.put(250, "CODE_DIAL_VIDEO_MODIFIED_TO_SS");
        map.put(251, "CODE_DIAL_VIDEO_MODIFIED_TO_USSD");
        map.put(321, "CODE_SIP_REDIRECTED");
        map.put(331, "CODE_SIP_BAD_REQUEST");
        map.put(332, "CODE_SIP_FORBIDDEN");
        map.put(333, "CODE_SIP_NOT_FOUND");
        map.put(334, "CODE_SIP_NOT_SUPPORTED");
        map.put(335, "CODE_SIP_REQUEST_TIMEOUT");
        map.put(336, "CODE_SIP_TEMPRARILY_UNAVAILABLE");
        map.put(337, "CODE_SIP_BAD_ADDRESS");
        map.put(338, "CODE_SIP_BUSY");
        map.put(339, "CODE_SIP_REQUEST_CANCELLED");
        map.put(340, "CODE_SIP_NOT_ACCEPTABLE");
        map.put(341, "CODE_SIP_NOT_REACHABLE");
        map.put(342, "CODE_SIP_CLIENT_ERROR");
        map.put(343, "CODE_SIP_TRANSACTION_DOES_NOT_EXIST");
        map.put(351, "CODE_SIP_SERVER_INTERNAL_ERROR");
        map.put(352, "CODE_SIP_SERVICE_UNAVAILABLE");
        map.put(353, "CODE_SIP_SERVER_TIMEOUT");
        map.put(354, "CODE_SIP_SERVER_ERROR");
        map.put(361, "CODE_SIP_USER_REJECTED");
        map.put(362, "CODE_SIP_GLOBAL_ERROR");
        map.put(363, "CODE_EMERGENCY_TEMP_FAILURE");
        map.put(364, "CODE_EMERGENCY_PERM_FAILURE");
        map.put(365, "CODE_SIP_USER_MARKED_UNWANTED");
        map.put(366, "CODE_SIP_METHOD_NOT_ALLOWED");
        map.put(367, "CODE_SIP_PROXY_AUTHENTICATION_REQUIRED");
        map.put(368, "CODE_SIP_REQUEST_ENTITY_TOO_LARGE");
        map.put(369, "CODE_SIP_REQUEST_URI_TOO_LARGE");
        map.put(370, "CODE_SIP_EXTENSION_REQUIRED");
        map.put(371, "CODE_SIP_INTERVAL_TOO_BRIEF");
        map.put(372, "CODE_SIP_CALL_OR_TRANS_DOES_NOT_EXIST");
        map.put(373, "CODE_SIP_LOOP_DETECTED");
        map.put(374, "CODE_SIP_TOO_MANY_HOPS");
        map.put(376, "CODE_SIP_AMBIGUOUS");
        map.put(377, "CODE_SIP_REQUEST_PENDING");
        map.put(378, "CODE_SIP_UNDECIPHERABLE");
        map.put(401, "CODE_MEDIA_INIT_FAILED");
        map.put(402, "CODE_MEDIA_NO_DATA");
        map.put(403, "CODE_MEDIA_NOT_ACCEPTABLE");
        map.put(404, "CODE_MEDIA_UNSPECIFIED");
        map.put(501, "CODE_USER_TERMINATED");
        map.put(502, "CODE_USER_NOANSWER");
        map.put(503, "CODE_USER_IGNORE");
        map.put(504, "CODE_USER_DECLINE");
        map.put(505, "CODE_LOW_BATTERY");
        map.put(506, "CODE_BLACKLISTED_CALL_ID");
        map.put(507, "CODE_USER_DECLINE_DURING_QEC_CALL");
        map.put(510, "CODE_USER_TERMINATED_BY_REMOTE");
        map.put(511, "CODE_USER_REJECTED_SESSION_MODIFICATION");
        map.put(512, "CODE_USER_CANCELLED_SESSION_MODIFICATION");
        map.put(1517, "CODE_SESSION_MODIFICATION_FAILED");
        map.put(801, "CODE_UT_NOT_SUPPORTED");
        map.put(802, "CODE_UT_SERVICE_UNAVAILABLE");
        map.put(803, "CODE_UT_OPERATION_NOT_ALLOWED");
        map.put(804, "CODE_UT_NETWORK_ERROR");
        map.put(821, "CODE_UT_CB_PASSWORD_MISMATCH");
        map.put(822, "CODE_UT_SS_MODIFIED_TO_DIAL");
        map.put(823, "CODE_UT_SS_MODIFIED_TO_USSD");
        map.put(824, "CODE_UT_SS_MODIFIED_TO_SS");
        map.put(825, "CODE_UT_SS_MODIFIED_TO_DIAL_VIDEO");
        map.put(901, "CODE_ECBM_NOT_SUPPORTED");
        map.put(902, "CODE_MULTIENDPOINT_NOT_SUPPORTED");
        map.put(1000, "CODE_REGISTRATION_ERROR");
        map.put(1014, "CODE_ANSWERED_ELSEWHERE");
        map.put(1015, "CODE_CALL_PULL_OUT_OF_SYNC");
        map.put(1016, "CODE_CALL_END_CAUSE_CALL_PULL");
        map.put(1100, "CODE_CALL_DROP_IWLAN_TO_LTE_UNAVAILABLE");
        map.put(1017, "CODE_REJECTED_ELSEWHERE");
        map.put(1201, "CODE_SUPP_SVC_FAILED");
        map.put(1202, "CODE_SUPP_SVC_CANCELLED");
        map.put(1203, "CODE_SUPP_SVC_REINVITE_COLLISION");
        map.put(1300, "CODE_IWLAN_DPD_FAILURE");
        map.put(1400, "CODE_EPDG_TUNNEL_ESTABLISH_FAILURE");
        map.put(1401, "CODE_EPDG_TUNNEL_REKEY_FAILURE");
        map.put(1402, "CODE_EPDG_TUNNEL_LOST_CONNECTION");
        map.put(1403, "CODE_MAXIMUM_NUMBER_OF_CALLS_REACHED");
        map.put(1404, "CODE_REMOTE_CALL_DECLINE");
        map.put(1405, "CODE_DATA_LIMIT_REACHED");
        map.put(1406, "CODE_DATA_DISABLED");
        map.put(1407, "CODE_WIFI_LOST");
        map.put(1408, "CODE_IKEV2_AUTH_FAILURE");
        map.put(1500, "CODE_RADIO_OFF");
        map.put(1501, "CODE_NO_VALID_SIM");
        map.put(1502, "CODE_RADIO_INTERNAL_ERROR");
        map.put(1503, "CODE_NETWORK_RESP_TIMEOUT");
        map.put(1504, "CODE_NETWORK_REJECT");
        map.put(1505, "CODE_RADIO_ACCESS_FAILURE");
        map.put(1506, "CODE_RADIO_LINK_FAILURE");
        map.put(1507, "CODE_RADIO_LINK_LOST");
        map.put(1508, "CODE_RADIO_UPLINK_FAILURE");
        map.put(1509, "CODE_RADIO_SETUP_FAILURE");
        map.put(1510, "CODE_RADIO_RELEASE_NORMAL");
        map.put(1511, "CODE_RADIO_RELEASE_ABNORMAL");
        map.put(1512, "CODE_ACCESS_CLASS_BLOCKED");
        map.put(1513, "CODE_NETWORK_DETACH");
        map.put(1514, "CODE_SIP_ALTERNATE_EMERGENCY_CALL");
        map.put(1515, "CODE_UNOBTAINABLE_NUMBER");
        map.put(1516, "CODE_NO_CSFB_IN_CS_ROAM");
        map.put(1600, "CODE_REJECT_UNKNOWN");
        map.put(1601, "CODE_REJECT_ONGOING_CALL_WAITING_DISABLED");
        map.put(1602, "CODE_REJECT_CALL_ON_OTHER_SUB");
        map.put(1603, "CODE_REJECT_1X_COLLISION");
        map.put(1604, "CODE_REJECT_SERVICE_NOT_REGISTERED");
        map.put(1605, "CODE_REJECT_CALL_TYPE_NOT_ALLOWED");
        map.put(1606, "CODE_REJECT_ONGOING_E911_CALL");
        map.put(1607, "CODE_REJECT_ONGOING_CALL_SETUP");
        map.put(1608, "CODE_REJECT_MAX_CALL_LIMIT_REACHED");
        map.put(1609, "CODE_REJECT_UNSUPPORTED_SIP_HEADERS");
        map.put(1610, "CODE_REJECT_UNSUPPORTED_SDP_HEADERS");
        map.put(1611, "CODE_REJECT_ONGOING_CALL_TRANSFER");
        map.put(1612, "CODE_REJECT_INTERNAL_ERROR");
        map.put(1613, "CODE_REJECT_QOS_FAILURE");
        map.put(1614, "CODE_REJECT_ONGOING_HANDOVER");
        map.put(1615, "CODE_REJECT_VT_TTY_NOT_ALLOWED");
        map.put(1616, "CODE_REJECT_ONGOING_CALL_UPGRADE");
        map.put(1617, "CODE_REJECT_CONFERENCE_TTY_NOT_ALLOWED");
        map.put(1618, "CODE_REJECT_ONGOING_CONFERENCE_CALL");
        map.put(1619, "CODE_REJECT_VT_AVPF_NOT_ALLOWED");
        map.put(1620, "CODE_REJECT_ONGOING_ENCRYPTED_CALL");
        map.put(1621, "CODE_REJECT_ONGOING_CS_CALL");
        map.put(1624, "CODE_NETWORK_CONGESTION");
        map.put(3001, "CODE_RETRY_ON_IMS_WITHOUT_RTT");
        map.put(61441, "CODE_OEM_CAUSE_1");
        map.put(61442, "CODE_OEM_CAUSE_2");
        map.put(61443, "CODE_OEM_CAUSE_3");
        map.put(61444, "CODE_OEM_CAUSE_4");
        map.put(61445, "CODE_OEM_CAUSE_5");
        map.put(61446, "CODE_OEM_CAUSE_6");
        map.put(61447, "CODE_OEM_CAUSE_7");
        map.put(61448, "CODE_OEM_CAUSE_8");
        map.put(61449, "CODE_OEM_CAUSE_9");
        map.put(61450, "CODE_OEM_CAUSE_10");
        map.put(61451, "CODE_OEM_CAUSE_11");
        map.put(61452, "CODE_OEM_CAUSE_12");
        map.put(61453, "CODE_OEM_CAUSE_13");
        map.put(61454, "CODE_OEM_CAUSE_14");
        map.put(61455, "CODE_OEM_CAUSE_15");
        map.put(9000, "CODE_LOCAL_E911_RETRY_REQUIRED");
        CREATOR = new Parcelable.Creator<ImsReasonInfo>() { // from class: android.telephony.ims.ImsReasonInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ImsReasonInfo createFromParcel(Parcel parcel) {
                return new ImsReasonInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ImsReasonInfo[] newArray(int i) {
                return new ImsReasonInfo[i];
            }
        };
    }

    public ImsReasonInfo() {
        this.mCode = 0;
        this.mExtraCode = 0;
        this.mExtraMessage = null;
    }

    private ImsReasonInfo(Parcel parcel) {
        this.mCode = parcel.readInt();
        this.mExtraCode = parcel.readInt();
        this.mExtraMessage = parcel.readString();
    }

    public ImsReasonInfo(int i, int i2) {
        this.mCode = i;
        this.mExtraCode = i2;
        this.mExtraMessage = null;
    }

    public ImsReasonInfo(int i, int i2, String str) {
        this.mCode = i;
        this.mExtraCode = i2;
        this.mExtraMessage = str;
    }

    public int getCode() {
        return this.mCode;
    }

    public int getExtraCode() {
        return this.mExtraCode;
    }

    public String getExtraMessage() {
        return this.mExtraMessage;
    }

    public String toString() {
        Map<Integer, String> map = sImsCodeMap;
        return "ImsReasonInfo :: {" + this.mCode + " : " + (map.containsKey(Integer.valueOf(this.mCode)) ? map.get(Integer.valueOf(this.mCode)) : "UNKNOWN_CODE") + ", " + this.mExtraCode + ", " + this.mExtraMessage + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mCode);
        parcel.writeInt(this.mExtraCode);
        parcel.writeString(this.mExtraMessage);
    }
}
