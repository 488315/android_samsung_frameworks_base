package android.telephony;

/* loaded from: classes4.dex */
public class SemTelephonyConstants {
    public static final int ASSISTED_DIAL_FROM_CONTACT_LIST = 1;
    public static final int ASSISTED_DIAL_FROM_DIAL_PAD = 2;
    public static final int ASSISTED_DIAL_FROM_NONE = 0;
    public static final String EXTRA_LMS_TOKEN_CTC = "lms_token_ctc";
    public static final int RESULT_SMS_DSAC_FAIL = 20;
    public static final int RESULT_SMS_MDM_DISCARDED = 23;
    public static final int RESULT_SMS_SEGMENT = 21;
    public static final String SEM_CALL_EXTRA_CMC_CALL_STATE =
            "com.samsung.telephony.extra.CMC_CALL_STATE";
    public static final String SEM_CALL_EXTRA_CMC_EXTERNAL_CALL =
            "com.samsung.telephony.extra.CMC_EXTERNAL_CALL";
    public static final String SEM_CALL_EXTRA_CMC_PRIMARY_DEVICE_CALL_CONNECT_TIME =
            "com.samsung.telephony.extra.CMC_PD_CALL_CONNECT_TIME";
    public static final String SEM_CALL_EXTRA_CMC_PULLABLE =
            "com.samsung.telephony.extra.CMC_PULLABLE";
    public static final String SEM_CALL_EXTRA_SEM_CMC_TYPE = "com.samsung.telephony.extra.CMC_TYPE";
    public static final String SEM_EVENT_CALL_CMC_PRIMARY_DEVICE_CONNECTED_TIME =
            "com.samsung.telephony.event.EVENT_CALL_CMC_PRIMARY_DEVICE_CONNECTED_TIME";
    public static final String SEM_EVENT_CALL_CMC_SECONDARY_DEVICE_PULL_COMPLETED =
            "com.samsung.telephony.event.EVENT_CALL_CMC_SECONDARY_DEVICE_PULL_COMPLETED";
    public static final String SEM_EVENT_CALL_CMC_SECONDARY_DEVICE_REQUEST_TYPE =
            "com.samsung.telephony.event.EVENT_CALL_CMC_SECONDARY_DEVICE_REQUEST_TYPE";
    public static final String SEM_EXTRA_ASSISTED_DIAL_FROM =
            "com.samsung.telephony.extra.EXTRA_ASSISTED_DIAL_FROM";
    public static final String SEM_EXTRA_CAN_TRANSFER_CALL =
            "com.samsung.telephony.extra.CAN_TRANSFER_CALL";
    public static final String SEM_EXTRA_CMC_BOUND_SESSION_ID =
            "com.samsung.telephony.extra.CMC_BOUND_SESSION_ID";
    public static final String SEM_EXTRA_CMC_CALL_SD_REQUEST_TYPE =
            "com.samsung.telephony.extra.CMC_CALL_REQUEST_TYPE";
    public static final String SEM_EXTRA_CMC_CALL_TYPE =
            "com.samsung.telephony.extra.CMC_CALL_TYPE";
    public static final String SEM_EXTRA_CMC_DEVICE_ID =
            "com.samsung.telephony.extra.CMC_DEVICE_ID";
    public static final String SEM_EXTRA_CMC_DIAL_FROM =
            "com.samsung.telephony.extra.CMC_DIAL_FROM";
    public static final String SEM_EXTRA_CMC_DIAL_TO = "com.samsung.telephony.extra.CMC_DIAL_TO";
    public static final String SEM_EXTRA_CMC_PHONE_ID = "com.samsung.telephony.extra.CMC_PHONE_ID";
    public static final String SEM_EXTRA_CMC_REPLACE_CALL_ID =
            "com.samsung.telephony.extra.CMC_REPLACE_CALL_ID";
    public static final String SEM_EXTRA_CMC_SD_CALL_MANAGE =
            "com.samsung.telephony.extra.CMC_CALL_MANAGE";
    public static final String SEM_EXTRA_CMC_SD_DTMF_KEY =
            "com.samsung.telephony.extra.CMC_CS_DTMF_KEY";
    public static final String SEM_EXTRA_CMC_SERVICE_TYPE =
            "com.samsung.telephony.extra.CMC_SERVICE_TYPE";
    public static final String SEM_EXTRA_CMC_SESSION_ID =
            "com.samsung.telephony.extra.CMC_SESSION_ID";
    public static final String SEM_EXTRA_DIAL_CONFERENCE_CALL =
            "com.samsung.telephony.extra.DIAL_CONFERENCE_CALL";
    public static final String SEM_EXTRA_FORWARDED_CALL =
            "com.samsung.telephony.extra.SEM_EXTRA_FORWARDED_CALL";
    public static final String SEM_EXTRA_SKT_CONFERENCE_CALL_SUPPORT =
            "com.samsung.telephony.extra.SKT_CONFERENCE_CALL_SUPPORT";
    public static final String SEM_EXTRA_START_CALL_WITH_EMERGENCY_SERVICE_CATEGORY =
            "com.samsung.telephony.extra.START_CALL_WITH_EMERGENCY_SERVICE_CATEGORY";
    public static final String SEM_EXTRA_VCRBT_CAUSE = "com.samsung.telephony.extra.VCRBT_CAUSE";
    public static final String SEM_EXTRA_VCRBT_REASON_PROTOCOL =
            "com.samsung.telephony.extra.VCRBT_REASON_PROTOCOL";
    public static final String SEM_EXTRA_VCRBT_TEXT_DESCRIPTION =
            "com.samsung.telephony.extra.TEXT_DESCRIPTION";
    public static final String SEM_EXTRA_VIDEO_CRBT = "com.samsung.telephony.extra.VIDEO_CRBT";
    public static final String SEM_EXTRA_VIDEO_CRT_IS_ALERTING =
            "com.samsung.telephony.extra.VIDEO_CRT_IS_ALERTING";
    public static final String SEM_EXTRA_VIDEO_CRT_MT = "com.samsung.telephony.extra.VIDEO_CRT_MT";

    public interface EmergencyNumberSource {
        public static final int EMERGENCY_NUMBER_SOURCE_AOSP_MAX = 32;
        public static final int EMERGENCY_NUMBER_SOURCE_HIGH_PRIORITY = 256;
        public static final int EMERGENCY_NUMBER_SOURCE_OVER_DATABASE = 128;
    }

    public interface ImsCallProfile {
        public static final int CMC_TYPE_PD = 1;
        public static final int CMC_TYPE_SD = 2;
        public static final String EMERGENCY_CALL_RAT_IWLAN = "VoWIFI";
        public static final String EMERGENCY_CALL_RAT_LTE = "VoLTE";
        public static final String EMERGENCY_CALL_RAT_NR = "VoLTE";
        public static final String EXTRA_FEATURE_CAPABILITY = "feature_caps";
    }

    public interface ImsReasonInfo {

        public interface Code {
            public static final int CODE_OEM_CMC_END_BY_REGULAR_CALL_RELEASE = 4005;
            public static final int CODE_OEM_CMC_PD_PULL = 4003;
            public static final int CODE_OEM_CMC_REJECT_E911_NOT_ALLOWED_ON_SD = 4007;
        }

        public interface ExtraCode {
            public static final int EXTRA_CODE_CMC_END_BY_REGULAR_CALL_RELEASE = 6009;
            public static final int EXTRA_CODE_SIP_USER_REJECTED = 603;
            public static final int EXTRA_CODE_USER_TERMINATED = 200;
            public static final int EXTRA_CODE_USER_TERMINATED_BY_REMOTE = 210;
        }
    }

    public interface Telecom {

        public interface Connection {
            public static final String SEM_EVENT_CALL_CMC_SECONDARY_DEVICE_PULL =
                    "com.samsung.telecom.event.CALL_SECONDARY_DEVICE_PULL";
        }
    }

    public interface TelephonyManager {
        public static final String SEM_CALL_EXTRA_CALL_FORWARDING_NUMBER_PRESENTATION =
                "com.samsung.telephony.extra.CALL_FORWARDING_PRESENTATION";
        public static final String SEM_CALL_EXTRA_IS_TWO_PHONE_MODE =
                "com.samsung.telephony.extra.IS_TWO_PHONE_MODE";
        public static final String SEM_CALL_EXTRA_WAITING_TONE_PLAY_TYPE =
                "com.samsung.telephony.extra.CALL_WAITING_TONE_SIGNAL";
    }
}
