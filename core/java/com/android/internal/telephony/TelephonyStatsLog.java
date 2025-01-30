package com.android.internal.telephony;

import android.util.StatsEvent;
import android.util.StatsLog;

/* loaded from: classes5.dex */
public final class TelephonyStatsLog {
    public static final int AIRPLANE_MODE = 311;
    public static final byte ANNOTATION_ID_DEFAULT_STATE = 6;
    public static final byte ANNOTATION_ID_EXCLUSIVE_STATE = 4;
    public static final byte ANNOTATION_ID_IS_UID = 1;
    public static final byte ANNOTATION_ID_PRIMARY_FIELD = 3;
    public static final byte ANNOTATION_ID_PRIMARY_FIELD_FIRST_UID = 5;
    public static final byte ANNOTATION_ID_STATE_NESTED = 8;
    public static final byte ANNOTATION_ID_TRIGGER_STATE_RESET = 7;
    public static final byte ANNOTATION_ID_TRUNCATE_TIMESTAMP = 2;
    public static final int CARRIER_ID_MISMATCH_REPORTED = 313;
    public static final int CARRIER_ID_TABLE_UPDATED = 314;
    public static final int CARRIER_ID_TABLE_VERSION = 10088;
    public static final int CELLULAR_DATA_SERVICE_SWITCH = 10091;
    public static final int CELLULAR_DATA_SERVICE_SWITCH__RAT_FROM__NETWORK_TYPE_1XRTT = 7;
    public static final int CELLULAR_DATA_SERVICE_SWITCH__RAT_FROM__NETWORK_TYPE_CDMA = 4;
    public static final int CELLULAR_DATA_SERVICE_SWITCH__RAT_FROM__NETWORK_TYPE_EDGE = 2;
    public static final int CELLULAR_DATA_SERVICE_SWITCH__RAT_FROM__NETWORK_TYPE_EHRPD = 14;
    public static final int CELLULAR_DATA_SERVICE_SWITCH__RAT_FROM__NETWORK_TYPE_EVDO_0 = 5;
    public static final int CELLULAR_DATA_SERVICE_SWITCH__RAT_FROM__NETWORK_TYPE_EVDO_A = 6;
    public static final int CELLULAR_DATA_SERVICE_SWITCH__RAT_FROM__NETWORK_TYPE_EVDO_B = 12;
    public static final int CELLULAR_DATA_SERVICE_SWITCH__RAT_FROM__NETWORK_TYPE_GPRS = 1;
    public static final int CELLULAR_DATA_SERVICE_SWITCH__RAT_FROM__NETWORK_TYPE_GSM = 16;
    public static final int CELLULAR_DATA_SERVICE_SWITCH__RAT_FROM__NETWORK_TYPE_HSDPA = 8;
    public static final int CELLULAR_DATA_SERVICE_SWITCH__RAT_FROM__NETWORK_TYPE_HSPA = 10;
    public static final int CELLULAR_DATA_SERVICE_SWITCH__RAT_FROM__NETWORK_TYPE_HSPAP = 15;
    public static final int CELLULAR_DATA_SERVICE_SWITCH__RAT_FROM__NETWORK_TYPE_HSUPA = 9;
    public static final int CELLULAR_DATA_SERVICE_SWITCH__RAT_FROM__NETWORK_TYPE_IDEN = 11;
    public static final int CELLULAR_DATA_SERVICE_SWITCH__RAT_FROM__NETWORK_TYPE_IWLAN = 18;
    public static final int CELLULAR_DATA_SERVICE_SWITCH__RAT_FROM__NETWORK_TYPE_LTE = 13;
    public static final int CELLULAR_DATA_SERVICE_SWITCH__RAT_FROM__NETWORK_TYPE_LTE_CA = 19;
    public static final int CELLULAR_DATA_SERVICE_SWITCH__RAT_FROM__NETWORK_TYPE_NR = 20;
    public static final int CELLULAR_DATA_SERVICE_SWITCH__RAT_FROM__NETWORK_TYPE_TD_SCDMA = 17;
    public static final int CELLULAR_DATA_SERVICE_SWITCH__RAT_FROM__NETWORK_TYPE_UMTS = 3;
    public static final int CELLULAR_DATA_SERVICE_SWITCH__RAT_FROM__NETWORK_TYPE_UNKNOWN = 0;
    public static final int CELLULAR_DATA_SERVICE_SWITCH__RAT_TO__NETWORK_TYPE_1XRTT = 7;
    public static final int CELLULAR_DATA_SERVICE_SWITCH__RAT_TO__NETWORK_TYPE_CDMA = 4;
    public static final int CELLULAR_DATA_SERVICE_SWITCH__RAT_TO__NETWORK_TYPE_EDGE = 2;
    public static final int CELLULAR_DATA_SERVICE_SWITCH__RAT_TO__NETWORK_TYPE_EHRPD = 14;
    public static final int CELLULAR_DATA_SERVICE_SWITCH__RAT_TO__NETWORK_TYPE_EVDO_0 = 5;
    public static final int CELLULAR_DATA_SERVICE_SWITCH__RAT_TO__NETWORK_TYPE_EVDO_A = 6;
    public static final int CELLULAR_DATA_SERVICE_SWITCH__RAT_TO__NETWORK_TYPE_EVDO_B = 12;
    public static final int CELLULAR_DATA_SERVICE_SWITCH__RAT_TO__NETWORK_TYPE_GPRS = 1;
    public static final int CELLULAR_DATA_SERVICE_SWITCH__RAT_TO__NETWORK_TYPE_GSM = 16;
    public static final int CELLULAR_DATA_SERVICE_SWITCH__RAT_TO__NETWORK_TYPE_HSDPA = 8;
    public static final int CELLULAR_DATA_SERVICE_SWITCH__RAT_TO__NETWORK_TYPE_HSPA = 10;
    public static final int CELLULAR_DATA_SERVICE_SWITCH__RAT_TO__NETWORK_TYPE_HSPAP = 15;
    public static final int CELLULAR_DATA_SERVICE_SWITCH__RAT_TO__NETWORK_TYPE_HSUPA = 9;
    public static final int CELLULAR_DATA_SERVICE_SWITCH__RAT_TO__NETWORK_TYPE_IDEN = 11;
    public static final int CELLULAR_DATA_SERVICE_SWITCH__RAT_TO__NETWORK_TYPE_IWLAN = 18;
    public static final int CELLULAR_DATA_SERVICE_SWITCH__RAT_TO__NETWORK_TYPE_LTE = 13;
    public static final int CELLULAR_DATA_SERVICE_SWITCH__RAT_TO__NETWORK_TYPE_LTE_CA = 19;
    public static final int CELLULAR_DATA_SERVICE_SWITCH__RAT_TO__NETWORK_TYPE_NR = 20;
    public static final int CELLULAR_DATA_SERVICE_SWITCH__RAT_TO__NETWORK_TYPE_TD_SCDMA = 17;
    public static final int CELLULAR_DATA_SERVICE_SWITCH__RAT_TO__NETWORK_TYPE_UMTS = 3;
    public static final int CELLULAR_DATA_SERVICE_SWITCH__RAT_TO__NETWORK_TYPE_UNKNOWN = 0;
    public static final int CELLULAR_SERVICE_STATE = 10090;
    public static final int CELLULAR_SERVICE_STATE__DATA_RAT__NETWORK_TYPE_1XRTT = 7;
    public static final int CELLULAR_SERVICE_STATE__DATA_RAT__NETWORK_TYPE_CDMA = 4;
    public static final int CELLULAR_SERVICE_STATE__DATA_RAT__NETWORK_TYPE_EDGE = 2;
    public static final int CELLULAR_SERVICE_STATE__DATA_RAT__NETWORK_TYPE_EHRPD = 14;
    public static final int CELLULAR_SERVICE_STATE__DATA_RAT__NETWORK_TYPE_EVDO_0 = 5;
    public static final int CELLULAR_SERVICE_STATE__DATA_RAT__NETWORK_TYPE_EVDO_A = 6;
    public static final int CELLULAR_SERVICE_STATE__DATA_RAT__NETWORK_TYPE_EVDO_B = 12;
    public static final int CELLULAR_SERVICE_STATE__DATA_RAT__NETWORK_TYPE_GPRS = 1;
    public static final int CELLULAR_SERVICE_STATE__DATA_RAT__NETWORK_TYPE_GSM = 16;
    public static final int CELLULAR_SERVICE_STATE__DATA_RAT__NETWORK_TYPE_HSDPA = 8;
    public static final int CELLULAR_SERVICE_STATE__DATA_RAT__NETWORK_TYPE_HSPA = 10;
    public static final int CELLULAR_SERVICE_STATE__DATA_RAT__NETWORK_TYPE_HSPAP = 15;
    public static final int CELLULAR_SERVICE_STATE__DATA_RAT__NETWORK_TYPE_HSUPA = 9;
    public static final int CELLULAR_SERVICE_STATE__DATA_RAT__NETWORK_TYPE_IDEN = 11;
    public static final int CELLULAR_SERVICE_STATE__DATA_RAT__NETWORK_TYPE_IWLAN = 18;
    public static final int CELLULAR_SERVICE_STATE__DATA_RAT__NETWORK_TYPE_LTE = 13;
    public static final int CELLULAR_SERVICE_STATE__DATA_RAT__NETWORK_TYPE_LTE_CA = 19;
    public static final int CELLULAR_SERVICE_STATE__DATA_RAT__NETWORK_TYPE_NR = 20;
    public static final int CELLULAR_SERVICE_STATE__DATA_RAT__NETWORK_TYPE_TD_SCDMA = 17;
    public static final int CELLULAR_SERVICE_STATE__DATA_RAT__NETWORK_TYPE_UMTS = 3;
    public static final int CELLULAR_SERVICE_STATE__DATA_RAT__NETWORK_TYPE_UNKNOWN = 0;

    /* renamed from: CELLULAR_SERVICE_STATE__DATA_ROAMING_TYPE__ROAMING_TYPE_NOT_ROAMING */
    public static final int f1009xec4db371 = 0;
    public static final int CELLULAR_SERVICE_STATE__DATA_ROAMING_TYPE__ROAMING_TYPE_ROAMING = 1;

    /* renamed from: CELLULAR_SERVICE_STATE__DATA_ROAMING_TYPE__ROAMING_TYPE_ROAMING_DOMESTIC */
    public static final int f1010x75ce8c20 = 2;

    /* renamed from: CELLULAR_SERVICE_STATE__DATA_ROAMING_TYPE__ROAMING_TYPE_ROAMING_INTERNATIONAL */
    public static final int f1011x41913cac = 3;
    public static final int CELLULAR_SERVICE_STATE__FOLD_STATE__STATE_CLOSED = 1;
    public static final int CELLULAR_SERVICE_STATE__FOLD_STATE__STATE_FLIPPED = 4;
    public static final int CELLULAR_SERVICE_STATE__FOLD_STATE__STATE_HALF_OPENED = 2;
    public static final int CELLULAR_SERVICE_STATE__FOLD_STATE__STATE_OPENED = 3;
    public static final int CELLULAR_SERVICE_STATE__FOLD_STATE__STATE_UNKNOWN = 0;
    public static final int CELLULAR_SERVICE_STATE__VOICE_RAT__NETWORK_TYPE_1XRTT = 7;
    public static final int CELLULAR_SERVICE_STATE__VOICE_RAT__NETWORK_TYPE_CDMA = 4;
    public static final int CELLULAR_SERVICE_STATE__VOICE_RAT__NETWORK_TYPE_EDGE = 2;
    public static final int CELLULAR_SERVICE_STATE__VOICE_RAT__NETWORK_TYPE_EHRPD = 14;
    public static final int CELLULAR_SERVICE_STATE__VOICE_RAT__NETWORK_TYPE_EVDO_0 = 5;
    public static final int CELLULAR_SERVICE_STATE__VOICE_RAT__NETWORK_TYPE_EVDO_A = 6;
    public static final int CELLULAR_SERVICE_STATE__VOICE_RAT__NETWORK_TYPE_EVDO_B = 12;
    public static final int CELLULAR_SERVICE_STATE__VOICE_RAT__NETWORK_TYPE_GPRS = 1;
    public static final int CELLULAR_SERVICE_STATE__VOICE_RAT__NETWORK_TYPE_GSM = 16;
    public static final int CELLULAR_SERVICE_STATE__VOICE_RAT__NETWORK_TYPE_HSDPA = 8;
    public static final int CELLULAR_SERVICE_STATE__VOICE_RAT__NETWORK_TYPE_HSPA = 10;
    public static final int CELLULAR_SERVICE_STATE__VOICE_RAT__NETWORK_TYPE_HSPAP = 15;
    public static final int CELLULAR_SERVICE_STATE__VOICE_RAT__NETWORK_TYPE_HSUPA = 9;
    public static final int CELLULAR_SERVICE_STATE__VOICE_RAT__NETWORK_TYPE_IDEN = 11;
    public static final int CELLULAR_SERVICE_STATE__VOICE_RAT__NETWORK_TYPE_IWLAN = 18;
    public static final int CELLULAR_SERVICE_STATE__VOICE_RAT__NETWORK_TYPE_LTE = 13;
    public static final int CELLULAR_SERVICE_STATE__VOICE_RAT__NETWORK_TYPE_LTE_CA = 19;
    public static final int CELLULAR_SERVICE_STATE__VOICE_RAT__NETWORK_TYPE_NR = 20;
    public static final int CELLULAR_SERVICE_STATE__VOICE_RAT__NETWORK_TYPE_TD_SCDMA = 17;
    public static final int CELLULAR_SERVICE_STATE__VOICE_RAT__NETWORK_TYPE_UMTS = 3;
    public static final int CELLULAR_SERVICE_STATE__VOICE_RAT__NETWORK_TYPE_UNKNOWN = 0;

    /* renamed from: CELLULAR_SERVICE_STATE__VOICE_ROAMING_TYPE__ROAMING_TYPE_NOT_ROAMING */
    public static final int f1012x7971606d = 0;
    public static final int CELLULAR_SERVICE_STATE__VOICE_ROAMING_TYPE__ROAMING_TYPE_ROAMING = 1;

    /* renamed from: CELLULAR_SERVICE_STATE__VOICE_ROAMING_TYPE__ROAMING_TYPE_ROAMING_DOMESTIC */
    public static final int f1013xd61b9ca4 = 2;

    /* renamed from: CELLULAR_SERVICE_STATE__VOICE_ROAMING_TYPE__ROAMING_TYPE_ROAMING_INTERNATIONAL */
    public static final int f1014x382ddea8 = 3;
    public static final int DATA_CALL_SESSION = 10089;

    /* renamed from: DATA_CALL_SESSION__DEACTIVATE_REASON__DEACTIVATE_REASON_AIRPLANE_MODE_ON */
    public static final int f1015x47df5d83 = 7;

    /* renamed from: DATA_CALL_SESSION__DEACTIVATE_REASON__DEACTIVATE_REASON_CDMA_EMERGENCY_CALLBACK_MODE */
    public static final int f1016xf2075a7a = 26;

    /* renamed from: DATA_CALL_SESSION__DEACTIVATE_REASON__DEACTIVATE_REASON_CONCURRENT_VOICE_DATA_NOT_ALLOWED */
    public static final int f1017xa6c07b27 = 12;

    /* renamed from: DATA_CALL_SESSION__DEACTIVATE_REASON__DEACTIVATE_REASON_CONNECTIVITY_SERVICE_UNWANTED */
    public static final int f1018x34413125 = 5;

    /* renamed from: DATA_CALL_SESSION__DEACTIVATE_REASON__DEACTIVATE_REASON_DATA_CONFIG_NOT_READY */
    public static final int f1019xfd2aa9da = 23;

    /* renamed from: DATA_CALL_SESSION__DEACTIVATE_REASON__DEACTIVATE_REASON_DATA_DISABLED */
    public static final int f1020x129f4c1c = 8;

    /* renamed from: DATA_CALL_SESSION__DEACTIVATE_REASON__DEACTIVATE_REASON_DATA_LIMIT_REACHED */
    public static final int f1021x97ef2ee = 35;

    /* renamed from: DATA_CALL_SESSION__DEACTIVATE_REASON__DEACTIVATE_REASON_DATA_NETWORK_TRANSPORT_NOT_ALLOWED */
    public static final int f1022x77494ab5 = 36;

    /* renamed from: DATA_CALL_SESSION__DEACTIVATE_REASON__DEACTIVATE_REASON_DATA_PROFILE_INVALID */
    public static final int f1023xfc916b81 = 29;

    /* renamed from: DATA_CALL_SESSION__DEACTIVATE_REASON__DEACTIVATE_REASON_DATA_PROFILE_NOT_PREFERRED */
    public static final int f1024x3df447ff = 30;

    /* renamed from: DATA_CALL_SESSION__DEACTIVATE_REASON__DEACTIVATE_REASON_DATA_SERVICE_NOT_READY */
    public static final int f1025x9898ea2d = 14;

    /* renamed from: DATA_CALL_SESSION__DEACTIVATE_REASON__DEACTIVATE_REASON_DATA_STALL */
    public static final int f1026x88720de0 = 16;

    /* renamed from: DATA_CALL_SESSION__DEACTIVATE_REASON__DEACTIVATE_REASON_DATA_THROTTLED */
    public static final int f1027x1aefcba = 28;

    /* renamed from: DATA_CALL_SESSION__DEACTIVATE_REASON__DEACTIVATE_REASON_DEFAULT_DATA_UNSELECTED */
    public static final int f1028x49c4b8b6 = 21;
    public static final int DATA_CALL_SESSION__DEACTIVATE_REASON__DEACTIVATE_REASON_HANDOVER = 3;

    /* renamed from: DATA_CALL_SESSION__DEACTIVATE_REASON__DEACTIVATE_REASON_HANDOVER_FAILED */
    public static final int f1029x2e8e5024 = 17;

    /* renamed from: DATA_CALL_SESSION__DEACTIVATE_REASON__DEACTIVATE_REASON_HANDOVER_NOT_ALLOWED */
    public static final int f1030xd1851075 = 18;

    /* renamed from: DATA_CALL_SESSION__DEACTIVATE_REASON__DEACTIVATE_REASON_ILLEGAL_STATE */
    public static final int f1031xcff54ef3 = 32;
    public static final int DATA_CALL_SESSION__DEACTIVATE_REASON__DEACTIVATE_REASON_NONE = 4;
    public static final int DATA_CALL_SESSION__DEACTIVATE_REASON__DEACTIVATE_REASON_NORMAL = 1;

    /* renamed from: DATA_CALL_SESSION__DEACTIVATE_REASON__DEACTIVATE_REASON_NOT_ALLOWED_BY_POLICY */
    public static final int f1032xbe94fb42 = 31;

    /* renamed from: DATA_CALL_SESSION__DEACTIVATE_REASON__DEACTIVATE_REASON_NOT_IN_SERVICE */
    public static final int f1033x535b533c = 22;

    /* renamed from: DATA_CALL_SESSION__DEACTIVATE_REASON__DEACTIVATE_REASON_NO_LIVE_REQUEST */
    public static final int f1034xd0a87dc5 = 9;

    /* renamed from: DATA_CALL_SESSION__DEACTIVATE_REASON__DEACTIVATE_REASON_NO_SUITABLE_DATA_PROFILE */
    public static final int f1035xf8f8c3c3 = 25;

    /* renamed from: DATA_CALL_SESSION__DEACTIVATE_REASON__DEACTIVATE_REASON_ONLY_ALLOWED_SINGLE_NETWORK */
    public static final int f1036x1864140c = 33;

    /* renamed from: DATA_CALL_SESSION__DEACTIVATE_REASON__DEACTIVATE_REASON_PENDING_TEAR_DOWN_ALL */
    public static final int f1037xf4f83684 = 24;

    /* renamed from: DATA_CALL_SESSION__DEACTIVATE_REASON__DEACTIVATE_REASON_POWER_OFF_BY_CARRIER */
    public static final int f1038xf170d76f = 15;

    /* renamed from: DATA_CALL_SESSION__DEACTIVATE_REASON__DEACTIVATE_REASON_PREFERRED_DATA_SWITCHED */
    public static final int f1039xe9c06f55 = 34;

    /* renamed from: DATA_CALL_SESSION__DEACTIVATE_REASON__DEACTIVATE_REASON_RADIO_OFF */
    public static final int f1040x7330fd16 = 2;

    /* renamed from: DATA_CALL_SESSION__DEACTIVATE_REASON__DEACTIVATE_REASON_RAT_NOT_ALLOWED */
    public static final int f1041xa35a14ad = 10;

    /* renamed from: DATA_CALL_SESSION__DEACTIVATE_REASON__DEACTIVATE_REASON_RETRY_SCHEDULED */
    public static final int f1042xe63cc581 = 27;

    /* renamed from: DATA_CALL_SESSION__DEACTIVATE_REASON__DEACTIVATE_REASON_ROAMING_DISABLED */
    public static final int f1043xaa383f77 = 11;

    /* renamed from: DATA_CALL_SESSION__DEACTIVATE_REASON__DEACTIVATE_REASON_SIM_REMOVAL */
    public static final int f1044x3814ebaf = 6;
    public static final int DATA_CALL_SESSION__DEACTIVATE_REASON__DEACTIVATE_REASON_UNKNOWN = 0;

    /* renamed from: DATA_CALL_SESSION__DEACTIVATE_REASON__DEACTIVATE_REASON_VCN_REQUESTED */
    public static final int f1045x95bb687b = 19;

    /* renamed from: DATA_CALL_SESSION__DEACTIVATE_REASON__DEACTIVATE_REASON_VOPS_NOT_SUPPORTED */
    public static final int f1046x32a677b4 = 20;
    public static final int DATA_CALL_SESSION__IP_TYPE__APN_PROTOCOL_IPV4 = 0;
    public static final int DATA_CALL_SESSION__IP_TYPE__APN_PROTOCOL_IPV4V6 = 2;
    public static final int DATA_CALL_SESSION__IP_TYPE__APN_PROTOCOL_IPV6 = 1;
    public static final int DATA_CALL_SESSION__IP_TYPE__APN_PROTOCOL_PPP = 3;
    public static final int DATA_CALL_SESSION__PROFILE__DATA_PROFILE_CBS = 4;
    public static final int DATA_CALL_SESSION__PROFILE__DATA_PROFILE_DEFAULT = 0;
    public static final int DATA_CALL_SESSION__PROFILE__DATA_PROFILE_FOTA = 3;
    public static final int DATA_CALL_SESSION__PROFILE__DATA_PROFILE_IMS = 2;
    public static final int DATA_CALL_SESSION__PROFILE__DATA_PROFILE_INVALID = -1;
    public static final int DATA_CALL_SESSION__PROFILE__DATA_PROFILE_OEM_BASE = 1000;
    public static final int DATA_CALL_SESSION__PROFILE__DATA_PROFILE_TETHERED = 1;
    public static final int DATA_CALL_SESSION__RAT_AT_END__NETWORK_TYPE_1XRTT = 7;
    public static final int DATA_CALL_SESSION__RAT_AT_END__NETWORK_TYPE_CDMA = 4;
    public static final int DATA_CALL_SESSION__RAT_AT_END__NETWORK_TYPE_EDGE = 2;
    public static final int DATA_CALL_SESSION__RAT_AT_END__NETWORK_TYPE_EHRPD = 14;
    public static final int DATA_CALL_SESSION__RAT_AT_END__NETWORK_TYPE_EVDO_0 = 5;
    public static final int DATA_CALL_SESSION__RAT_AT_END__NETWORK_TYPE_EVDO_A = 6;
    public static final int DATA_CALL_SESSION__RAT_AT_END__NETWORK_TYPE_EVDO_B = 12;
    public static final int DATA_CALL_SESSION__RAT_AT_END__NETWORK_TYPE_GPRS = 1;
    public static final int DATA_CALL_SESSION__RAT_AT_END__NETWORK_TYPE_GSM = 16;
    public static final int DATA_CALL_SESSION__RAT_AT_END__NETWORK_TYPE_HSDPA = 8;
    public static final int DATA_CALL_SESSION__RAT_AT_END__NETWORK_TYPE_HSPA = 10;
    public static final int DATA_CALL_SESSION__RAT_AT_END__NETWORK_TYPE_HSPAP = 15;
    public static final int DATA_CALL_SESSION__RAT_AT_END__NETWORK_TYPE_HSUPA = 9;
    public static final int DATA_CALL_SESSION__RAT_AT_END__NETWORK_TYPE_IDEN = 11;
    public static final int DATA_CALL_SESSION__RAT_AT_END__NETWORK_TYPE_IWLAN = 18;
    public static final int DATA_CALL_SESSION__RAT_AT_END__NETWORK_TYPE_LTE = 13;
    public static final int DATA_CALL_SESSION__RAT_AT_END__NETWORK_TYPE_LTE_CA = 19;
    public static final int DATA_CALL_SESSION__RAT_AT_END__NETWORK_TYPE_NR = 20;
    public static final int DATA_CALL_SESSION__RAT_AT_END__NETWORK_TYPE_TD_SCDMA = 17;
    public static final int DATA_CALL_SESSION__RAT_AT_END__NETWORK_TYPE_UMTS = 3;
    public static final int DATA_CALL_SESSION__RAT_AT_END__NETWORK_TYPE_UNKNOWN = 0;
    public static final int DATA_STALL_RECOVERY_REPORTED = 315;
    public static final int DATA_STALL_RECOVERY_REPORTED__ACTION__RECOVERY_ACTION_CLEANUP = 1;

    /* renamed from: DATA_STALL_RECOVERY_REPORTED__ACTION__RECOVERY_ACTION_GET_DATA_CALL_LIST */
    public static final int f1047xd109db51 = 0;

    /* renamed from: DATA_STALL_RECOVERY_REPORTED__ACTION__RECOVERY_ACTION_RADIO_RESTART */
    public static final int f1048x3116132d = 3;
    public static final int DATA_STALL_RECOVERY_REPORTED__ACTION__RECOVERY_ACTION_REREGISTER = 2;

    /* renamed from: DATA_STALL_RECOVERY_REPORTED__ACTION__RECOVERY_ACTION_RESET_MODEM */
    public static final int f1049x8829de1c = 4;

    /* renamed from: DATA_STALL_RECOVERY_REPORTED__OTHER_PHONE_REG_STATE__REGISTRATION_STATE_DENIED */
    public static final int f1050x967036b2 = 3;

    /* renamed from: DATA_STALL_RECOVERY_REPORTED__OTHER_PHONE_REG_STATE__REGISTRATION_STATE_HOME */
    public static final int f1051x5abc8db6 = 1;

    /* renamed from: DATA_STALL_RECOVERY_REPORTED__OTHER_PHONE_REG_STATE__REGISTRATION_STATE_NOT_REGISTERED_OR_SEARCHING */
    public static final int f1052x24263018 = 0;

    /* renamed from: DATA_STALL_RECOVERY_REPORTED__OTHER_PHONE_REG_STATE__REGISTRATION_STATE_NOT_REGISTERED_SEARCHING */
    public static final int f1053x1796c500 = 2;

    /* renamed from: DATA_STALL_RECOVERY_REPORTED__OTHER_PHONE_REG_STATE__REGISTRATION_STATE_ROAMING */
    public static final int f1054x2c8903c2 = 5;

    /* renamed from: DATA_STALL_RECOVERY_REPORTED__OTHER_PHONE_REG_STATE__REGISTRATION_STATE_UNKNOWN */
    public static final int f1055xca1453f3 = 4;

    /* renamed from: DATA_STALL_RECOVERY_REPORTED__OTHER_PHONE_SIGNAL_STRENGTH__SIGNAL_STRENGTH_GOOD */
    public static final int f1056x63633953 = 3;

    /* renamed from: DATA_STALL_RECOVERY_REPORTED__OTHER_PHONE_SIGNAL_STRENGTH__SIGNAL_STRENGTH_GREAT */
    public static final int f1057x9052897 = 4;

    /* renamed from: DATA_STALL_RECOVERY_REPORTED__OTHER_PHONE_SIGNAL_STRENGTH__SIGNAL_STRENGTH_MODERATE */
    public static final int f1058x7e667299 = 2;

    /* renamed from: DATA_STALL_RECOVERY_REPORTED__OTHER_PHONE_SIGNAL_STRENGTH__SIGNAL_STRENGTH_NONE_OR_UNKNOWN */
    public static final int f1059x132af6df = 0;

    /* renamed from: DATA_STALL_RECOVERY_REPORTED__OTHER_PHONE_SIGNAL_STRENGTH__SIGNAL_STRENGTH_POOR */
    public static final int f1060x636750b8 = 1;

    /* renamed from: DATA_STALL_RECOVERY_REPORTED__PHONE_REG_STATE__REGISTRATION_STATE_DENIED */
    public static final int f1061x67ae84c3 = 3;

    /* renamed from: DATA_STALL_RECOVERY_REPORTED__PHONE_REG_STATE__REGISTRATION_STATE_HOME */
    public static final int f1062xf4a92c07 = 1;

    /* renamed from: DATA_STALL_RECOVERY_REPORTED__PHONE_REG_STATE__REGISTRATION_STATE_NOT_REGISTERED_OR_SEARCHING */
    public static final int f1063xd36606a7 = 0;

    /* renamed from: DATA_STALL_RECOVERY_REPORTED__PHONE_REG_STATE__REGISTRATION_STATE_NOT_REGISTERED_SEARCHING */
    public static final int f1064xe76e10d1 = 2;

    /* renamed from: DATA_STALL_RECOVERY_REPORTED__PHONE_REG_STATE__REGISTRATION_STATE_ROAMING */
    public static final int f1065x831477d1 = 5;

    /* renamed from: DATA_STALL_RECOVERY_REPORTED__PHONE_REG_STATE__REGISTRATION_STATE_UNKNOWN */
    public static final int f1066x209fc802 = 4;
    public static final int DATA_STALL_RECOVERY_REPORTED__RAT__NETWORK_TYPE_1XRTT = 7;
    public static final int DATA_STALL_RECOVERY_REPORTED__RAT__NETWORK_TYPE_CDMA = 4;
    public static final int DATA_STALL_RECOVERY_REPORTED__RAT__NETWORK_TYPE_EDGE = 2;
    public static final int DATA_STALL_RECOVERY_REPORTED__RAT__NETWORK_TYPE_EHRPD = 14;
    public static final int DATA_STALL_RECOVERY_REPORTED__RAT__NETWORK_TYPE_EVDO_0 = 5;
    public static final int DATA_STALL_RECOVERY_REPORTED__RAT__NETWORK_TYPE_EVDO_A = 6;
    public static final int DATA_STALL_RECOVERY_REPORTED__RAT__NETWORK_TYPE_EVDO_B = 12;
    public static final int DATA_STALL_RECOVERY_REPORTED__RAT__NETWORK_TYPE_GPRS = 1;
    public static final int DATA_STALL_RECOVERY_REPORTED__RAT__NETWORK_TYPE_GSM = 16;
    public static final int DATA_STALL_RECOVERY_REPORTED__RAT__NETWORK_TYPE_HSDPA = 8;
    public static final int DATA_STALL_RECOVERY_REPORTED__RAT__NETWORK_TYPE_HSPA = 10;
    public static final int DATA_STALL_RECOVERY_REPORTED__RAT__NETWORK_TYPE_HSPAP = 15;
    public static final int DATA_STALL_RECOVERY_REPORTED__RAT__NETWORK_TYPE_HSUPA = 9;
    public static final int DATA_STALL_RECOVERY_REPORTED__RAT__NETWORK_TYPE_IDEN = 11;
    public static final int DATA_STALL_RECOVERY_REPORTED__RAT__NETWORK_TYPE_IWLAN = 18;
    public static final int DATA_STALL_RECOVERY_REPORTED__RAT__NETWORK_TYPE_LTE = 13;
    public static final int DATA_STALL_RECOVERY_REPORTED__RAT__NETWORK_TYPE_LTE_CA = 19;
    public static final int DATA_STALL_RECOVERY_REPORTED__RAT__NETWORK_TYPE_NR = 20;
    public static final int DATA_STALL_RECOVERY_REPORTED__RAT__NETWORK_TYPE_TD_SCDMA = 17;
    public static final int DATA_STALL_RECOVERY_REPORTED__RAT__NETWORK_TYPE_UMTS = 3;
    public static final int DATA_STALL_RECOVERY_REPORTED__RAT__NETWORK_TYPE_UNKNOWN = 0;
    public static final int DATA_STALL_RECOVERY_REPORTED__REASON__RECOVERED_REASON_DSRM = 1;
    public static final int DATA_STALL_RECOVERY_REPORTED__REASON__RECOVERED_REASON_MODEM = 2;
    public static final int DATA_STALL_RECOVERY_REPORTED__REASON__RECOVERED_REASON_NONE = 0;
    public static final int DATA_STALL_RECOVERY_REPORTED__REASON__RECOVERED_REASON_USER = 3;

    /* renamed from: DATA_STALL_RECOVERY_REPORTED__SIGNAL_STRENGTH__SIGNAL_STRENGTH_GOOD */
    public static final int f1067x2b0510d3 = 3;

    /* renamed from: DATA_STALL_RECOVERY_REPORTED__SIGNAL_STRENGTH__SIGNAL_STRENGTH_GREAT */
    public static final int f1068x359e4117 = 4;

    /* renamed from: DATA_STALL_RECOVERY_REPORTED__SIGNAL_STRENGTH__SIGNAL_STRENGTH_MODERATE */
    public static final int f1069x6a508a19 = 2;

    /* renamed from: DATA_STALL_RECOVERY_REPORTED__SIGNAL_STRENGTH__SIGNAL_STRENGTH_NONE_OR_UNKNOWN */
    public static final int f1070xf79f6f5f = 0;

    /* renamed from: DATA_STALL_RECOVERY_REPORTED__SIGNAL_STRENGTH__SIGNAL_STRENGTH_POOR */
    public static final int f1071x2b092838 = 1;
    public static final int DEVICE_TELEPHONY_PROPERTIES = 10154;
    public static final int EMERGENCY_NUMBERS_INFO = 10180;
    public static final int EMERGENCY_NUMBERS_INFO__ROUTE__EMERGENCY_CALL_ROUTE_EMERGENCY = 1;
    public static final int EMERGENCY_NUMBERS_INFO__ROUTE__EMERGENCY_CALL_ROUTE_NORMAL = 2;
    public static final int EMERGENCY_NUMBERS_INFO__ROUTE__EMERGENCY_CALL_ROUTE_UNKNOWN = 0;

    /* renamed from: EMERGENCY_NUMBERS_INFO__SERVICE_CATEGORIES__EMERGENCY_SERVICE_CATEGORY_AIEC */
    public static final int f1072x61e70ed8 = 7;

    /* renamed from: EMERGENCY_NUMBERS_INFO__SERVICE_CATEGORIES__EMERGENCY_SERVICE_CATEGORY_AMBULANCE */
    public static final int f1073x2ebf68aa = 2;

    /* renamed from: EMERGENCY_NUMBERS_INFO__SERVICE_CATEGORIES__EMERGENCY_SERVICE_CATEGORY_FIRE_BRIGADE */
    public static final int f1074xd970d55d = 3;

    /* renamed from: EMERGENCY_NUMBERS_INFO__SERVICE_CATEGORIES__EMERGENCY_SERVICE_CATEGORY_MARINE_GUARD */
    public static final int f1075x881067da = 4;

    /* renamed from: EMERGENCY_NUMBERS_INFO__SERVICE_CATEGORIES__EMERGENCY_SERVICE_CATEGORY_MIEC */
    public static final int f1076x61ec834c = 6;

    /* renamed from: EMERGENCY_NUMBERS_INFO__SERVICE_CATEGORIES__EMERGENCY_SERVICE_CATEGORY_MOUNTAIN_RESCUE */
    public static final int f1077x2c161c33 = 5;

    /* renamed from: EMERGENCY_NUMBERS_INFO__SERVICE_CATEGORIES__EMERGENCY_SERVICE_CATEGORY_POLICE */
    public static final int f1078x9e4f44b0 = 1;

    /* renamed from: EMERGENCY_NUMBERS_INFO__SERVICE_CATEGORIES__EMERGENCY_SERVICE_CATEGORY_UNSPECIFIED */
    public static final int f1079x74addec5 = 0;

    /* renamed from: EMERGENCY_NUMBERS_INFO__SOURCES__EMERGENCY_NUMBER_SOURCE_DATABASE */
    public static final int f1080x834f91fe = 2;
    public static final int EMERGENCY_NUMBERS_INFO__SOURCES__EMERGENCY_NUMBER_SOURCE_DEFAULT = 4;

    /* renamed from: EMERGENCY_NUMBERS_INFO__SOURCES__EMERGENCY_NUMBER_SOURCE_MODEM_CONFIG */
    public static final int f1081x5261691a = 3;

    /* renamed from: EMERGENCY_NUMBERS_INFO__SOURCES__EMERGENCY_NUMBER_SOURCE_NETWORK_SIGNALING */
    public static final int f1082x83505206 = 0;
    public static final int EMERGENCY_NUMBERS_INFO__SOURCES__EMERGENCY_NUMBER_SOURCE_SIM = 1;
    public static final int GBA_EVENT = 10145;
    public static final int GBA_EVENT__FAILED_REASON__FEATURE_NOT_READY = 2;
    public static final int GBA_EVENT__FAILED_REASON__FEATURE_NOT_SUPPORTED = 1;
    public static final int GBA_EVENT__FAILED_REASON__INCORRECT_NAF_ID = 4;
    public static final int GBA_EVENT__FAILED_REASON__NETWORK_FAILURE = 3;
    public static final int GBA_EVENT__FAILED_REASON__SECURITY_PROTOCOL_NOT_SUPPORTED = 5;
    public static final int GBA_EVENT__FAILED_REASON__UNKNOWN = 0;
    public static final int IMS_DEDICATED_BEARER_EVENT = 10141;
    public static final int IMS_DEDICATED_BEARER_EVENT__BEARER_STATE__STATE_ADDED = 1;
    public static final int IMS_DEDICATED_BEARER_EVENT__BEARER_STATE__STATE_DELETED = 3;
    public static final int IMS_DEDICATED_BEARER_EVENT__BEARER_STATE__STATE_MODIFIED = 2;
    public static final int IMS_DEDICATED_BEARER_EVENT__BEARER_STATE__STATE_UNSPECIFIED = 0;
    public static final int IMS_DEDICATED_BEARER_EVENT__RAT_AT_END__NETWORK_TYPE_1XRTT = 7;
    public static final int IMS_DEDICATED_BEARER_EVENT__RAT_AT_END__NETWORK_TYPE_CDMA = 4;
    public static final int IMS_DEDICATED_BEARER_EVENT__RAT_AT_END__NETWORK_TYPE_EDGE = 2;
    public static final int IMS_DEDICATED_BEARER_EVENT__RAT_AT_END__NETWORK_TYPE_EHRPD = 14;
    public static final int IMS_DEDICATED_BEARER_EVENT__RAT_AT_END__NETWORK_TYPE_EVDO_0 = 5;
    public static final int IMS_DEDICATED_BEARER_EVENT__RAT_AT_END__NETWORK_TYPE_EVDO_A = 6;
    public static final int IMS_DEDICATED_BEARER_EVENT__RAT_AT_END__NETWORK_TYPE_EVDO_B = 12;
    public static final int IMS_DEDICATED_BEARER_EVENT__RAT_AT_END__NETWORK_TYPE_GPRS = 1;
    public static final int IMS_DEDICATED_BEARER_EVENT__RAT_AT_END__NETWORK_TYPE_GSM = 16;
    public static final int IMS_DEDICATED_BEARER_EVENT__RAT_AT_END__NETWORK_TYPE_HSDPA = 8;
    public static final int IMS_DEDICATED_BEARER_EVENT__RAT_AT_END__NETWORK_TYPE_HSPA = 10;
    public static final int IMS_DEDICATED_BEARER_EVENT__RAT_AT_END__NETWORK_TYPE_HSPAP = 15;
    public static final int IMS_DEDICATED_BEARER_EVENT__RAT_AT_END__NETWORK_TYPE_HSUPA = 9;
    public static final int IMS_DEDICATED_BEARER_EVENT__RAT_AT_END__NETWORK_TYPE_IDEN = 11;
    public static final int IMS_DEDICATED_BEARER_EVENT__RAT_AT_END__NETWORK_TYPE_IWLAN = 18;
    public static final int IMS_DEDICATED_BEARER_EVENT__RAT_AT_END__NETWORK_TYPE_LTE = 13;
    public static final int IMS_DEDICATED_BEARER_EVENT__RAT_AT_END__NETWORK_TYPE_LTE_CA = 19;
    public static final int IMS_DEDICATED_BEARER_EVENT__RAT_AT_END__NETWORK_TYPE_NR = 20;
    public static final int IMS_DEDICATED_BEARER_EVENT__RAT_AT_END__NETWORK_TYPE_TD_SCDMA = 17;
    public static final int IMS_DEDICATED_BEARER_EVENT__RAT_AT_END__NETWORK_TYPE_UMTS = 3;
    public static final int IMS_DEDICATED_BEARER_EVENT__RAT_AT_END__NETWORK_TYPE_UNKNOWN = 0;
    public static final int IMS_DEDICATED_BEARER_LISTENER_EVENT = 10140;

    /* renamed from: IMS_DEDICATED_BEARER_LISTENER_EVENT__RAT_AT_END__NETWORK_TYPE_1XRTT */
    public static final int f1083xe54f0a6d = 7;

    /* renamed from: IMS_DEDICATED_BEARER_LISTENER_EVENT__RAT_AT_END__NETWORK_TYPE_CDMA */
    public static final int f1084x9c12b1b3 = 4;

    /* renamed from: IMS_DEDICATED_BEARER_LISTENER_EVENT__RAT_AT_END__NETWORK_TYPE_EDGE */
    public static final int f1085x9c1399bb = 2;

    /* renamed from: IMS_DEDICATED_BEARER_LISTENER_EVENT__RAT_AT_END__NETWORK_TYPE_EHRPD */
    public static final int f1086xe6619a05 = 14;

    /* renamed from: IMS_DEDICATED_BEARER_LISTENER_EVENT__RAT_AT_END__NETWORK_TYPE_EVDO_0 */
    public static final int f1087xe690922b = 5;

    /* renamed from: IMS_DEDICATED_BEARER_LISTENER_EVENT__RAT_AT_END__NETWORK_TYPE_EVDO_A */
    public static final int f1088xe690923c = 6;

    /* renamed from: IMS_DEDICATED_BEARER_LISTENER_EVENT__RAT_AT_END__NETWORK_TYPE_EVDO_B */
    public static final int f1089xe690923d = 12;

    /* renamed from: IMS_DEDICATED_BEARER_LISTENER_EVENT__RAT_AT_END__NETWORK_TYPE_GPRS */
    public static final int f1090x9c14b0e8 = 1;

    /* renamed from: IMS_DEDICATED_BEARER_LISTENER_EVENT__RAT_AT_END__NETWORK_TYPE_GSM */
    public static final int f1091xc2f86923 = 16;

    /* renamed from: IMS_DEDICATED_BEARER_LISTENER_EVENT__RAT_AT_END__NETWORK_TYPE_HSDPA */
    public static final int f1092xe690ac0c = 8;

    /* renamed from: IMS_DEDICATED_BEARER_LISTENER_EVENT__RAT_AT_END__NETWORK_TYPE_HSPA */
    public static final int f1093x9c15303a = 10;

    /* renamed from: IMS_DEDICATED_BEARER_LISTENER_EVENT__RAT_AT_END__NETWORK_TYPE_HSPAP */
    public static final int f1094xe690d756 = 15;

    /* renamed from: IMS_DEDICATED_BEARER_LISTENER_EVENT__RAT_AT_END__NETWORK_TYPE_HSUPA */
    public static final int f1095xe690ebdd = 9;

    /* renamed from: IMS_DEDICATED_BEARER_LISTENER_EVENT__RAT_AT_END__NETWORK_TYPE_IDEN */
    public static final int f1096x9c156b02 = 11;

    /* renamed from: IMS_DEDICATED_BEARER_LISTENER_EVENT__RAT_AT_END__NETWORK_TYPE_IWLAN */
    public static final int f1097xe6a0b14d = 18;

    /* renamed from: IMS_DEDICATED_BEARER_LISTENER_EVENT__RAT_AT_END__NETWORK_TYPE_LTE */
    public static final int f1098xc2f87bff = 13;

    /* renamed from: IMS_DEDICATED_BEARER_LISTENER_EVENT__RAT_AT_END__NETWORK_TYPE_LTE_CA */
    public static final int f1099xf266fc9e = 19;
    public static final int IMS_DEDICATED_BEARER_LISTENER_EVENT__RAT_AT_END__NETWORK_TYPE_NR = 20;

    /* renamed from: IMS_DEDICATED_BEARER_LISTENER_EVENT__RAT_AT_END__NETWORK_TYPE_TD_SCDMA */
    public static final int f1100x10f4faf7 = 17;

    /* renamed from: IMS_DEDICATED_BEARER_LISTENER_EVENT__RAT_AT_END__NETWORK_TYPE_UMTS */
    public static final int f1101x9c1b0315 = 3;

    /* renamed from: IMS_DEDICATED_BEARER_LISTENER_EVENT__RAT_AT_END__NETWORK_TYPE_UNKNOWN */
    public static final int f1102x2ca09b6c = 0;
    public static final int IMS_REGISTRATION_FEATURE_TAG_STATS = 10133;

    /* renamed from: IMS_REGISTRATION_FEATURE_TAG_STATS__FEATURE_TAG_NAME__IMS_FEATURE_TAG_CALL_COMPOSER_ENRICHED_CALLING */
    public static final int f1103x29632cb9 = 7;

    /* renamed from: IMS_REGISTRATION_FEATURE_TAG_STATS__FEATURE_TAG_NAME__IMS_FEATURE_TAG_CALL_COMPOSER_VIA_TELEPHONY */
    public static final int f1104x3d4fe1f7 = 8;

    /* renamed from: IMS_REGISTRATION_FEATURE_TAG_STATS__FEATURE_TAG_NAME__IMS_FEATURE_TAG_CHATBOT_COMMUNICATION_USING_SESSION */
    public static final int f1105x24fcfcd0 = 14;

    /* renamed from: IMS_REGISTRATION_FEATURE_TAG_STATS__FEATURE_TAG_NAME__IMS_FEATURE_TAG_CHATBOT_COMMUNICATION_USING_STANDALONE_MSG */
    public static final int f1106x488b94cd = 15;

    /* renamed from: IMS_REGISTRATION_FEATURE_TAG_STATS__FEATURE_TAG_NAME__IMS_FEATURE_TAG_CHATBOT_ROLE */
    public static final int f1107x4965a38 = 17;

    /* renamed from: IMS_REGISTRATION_FEATURE_TAG_STATS__FEATURE_TAG_NAME__IMS_FEATURE_TAG_CHATBOT_VERSION_SUPPORTED */
    public static final int f1108xf5d28b45 = 16;

    /* renamed from: IMS_REGISTRATION_FEATURE_TAG_STATS__FEATURE_TAG_NAME__IMS_FEATURE_TAG_CHAT_IM */
    public static final int f1109xe9580159 = 3;

    /* renamed from: IMS_REGISTRATION_FEATURE_TAG_STATS__FEATURE_TAG_NAME__IMS_FEATURE_TAG_CHAT_SESSION */
    public static final int f1110x826fef61 = 4;

    /* renamed from: IMS_REGISTRATION_FEATURE_TAG_STATS__FEATURE_TAG_NAME__IMS_FEATURE_TAG_CUSTOM */
    public static final int f1111x631d0bc3 = 1;

    /* renamed from: IMS_REGISTRATION_FEATURE_TAG_STATS__FEATURE_TAG_NAME__IMS_FEATURE_TAG_FILE_TRANSFER */
    public static final int f1112xace7aabc = 5;

    /* renamed from: IMS_REGISTRATION_FEATURE_TAG_STATS__FEATURE_TAG_NAME__IMS_FEATURE_TAG_FILE_TRANSFER_VIA_SMS */
    public static final int f1113xfc18eae5 = 6;

    /* renamed from: IMS_REGISTRATION_FEATURE_TAG_STATS__FEATURE_TAG_NAME__IMS_FEATURE_TAG_GEO_PUSH */
    public static final int f1114x5af2633a = 12;

    /* renamed from: IMS_REGISTRATION_FEATURE_TAG_STATS__FEATURE_TAG_NAME__IMS_FEATURE_TAG_GEO_PUSH_VIA_SMS */
    public static final int f1115x299a4563 = 13;

    /* renamed from: IMS_REGISTRATION_FEATURE_TAG_STATS__FEATURE_TAG_NAME__IMS_FEATURE_TAG_MMTEL */
    public static final int f1116x1c81f7c9 = 18;

    /* renamed from: IMS_REGISTRATION_FEATURE_TAG_STATS__FEATURE_TAG_NAME__IMS_FEATURE_TAG_POST_CALL */
    public static final int f1117xa2c390b = 9;

    /* renamed from: IMS_REGISTRATION_FEATURE_TAG_STATS__FEATURE_TAG_NAME__IMS_FEATURE_TAG_PRESENCE */
    public static final int f1118x9fcf4ccd = 20;

    /* renamed from: IMS_REGISTRATION_FEATURE_TAG_STATS__FEATURE_TAG_NAME__IMS_FEATURE_TAG_SHARED_MAP */
    public static final int f1119x267451b4 = 10;

    /* renamed from: IMS_REGISTRATION_FEATURE_TAG_STATS__FEATURE_TAG_NAME__IMS_FEATURE_TAG_SHARED_SKETCH */
    public static final int f1120x4f62434 = 11;

    /* renamed from: IMS_REGISTRATION_FEATURE_TAG_STATS__FEATURE_TAG_NAME__IMS_FEATURE_TAG_STANDALONE_MSG */
    public static final int f1121x3f1d96b9 = 2;

    /* renamed from: IMS_REGISTRATION_FEATURE_TAG_STATS__FEATURE_TAG_NAME__IMS_FEATURE_TAG_UNSPECIFIED */
    public static final int f1122x25b77605 = 0;

    /* renamed from: IMS_REGISTRATION_FEATURE_TAG_STATS__FEATURE_TAG_NAME__IMS_FEATURE_TAG_VIDEO */
    public static final int f1123x1cfebdc9 = 19;
    public static final int IMS_REGISTRATION_SERVICE_DESC_STATS = 10142;

    /* renamed from: IMS_REGISTRATION_SERVICE_DESC_STATS__SERVICE_ID_NAME__SERVICE_ID_CALL_COMPOSER */
    public static final int f1124x17c8fadb = 9;

    /* renamed from: IMS_REGISTRATION_SERVICE_DESC_STATS__SERVICE_ID_NAME__SERVICE_ID_CHATBOT */
    public static final int f1125x5eb31bc9 = 13;

    /* renamed from: IMS_REGISTRATION_SERVICE_DESC_STATS__SERVICE_ID_NAME__SERVICE_ID_CHATBOT_ROLE */
    public static final int f1126x48650b2c = 15;

    /* renamed from: IMS_REGISTRATION_SERVICE_DESC_STATS__SERVICE_ID_NAME__SERVICE_ID_CHATBOT_STANDALONE */
    public static final int f1127x5b8efe7b = 14;

    /* renamed from: IMS_REGISTRATION_SERVICE_DESC_STATS__SERVICE_ID_NAME__SERVICE_ID_CHAT_V1 */
    public static final int f1128x5eb3895c = 3;

    /* renamed from: IMS_REGISTRATION_SERVICE_DESC_STATS__SERVICE_ID_NAME__SERVICE_ID_CHAT_V2 */
    public static final int f1129x5eb3895d = 4;

    /* renamed from: IMS_REGISTRATION_SERVICE_DESC_STATS__SERVICE_ID_NAME__SERVICE_ID_CUSTOM */
    public static final int f1130x903083b7 = 1;

    /* renamed from: IMS_REGISTRATION_SERVICE_DESC_STATS__SERVICE_ID_NAME__SERVICE_ID_FT */
    public static final int f1131xd9102014 = 5;

    /* renamed from: IMS_REGISTRATION_SERVICE_DESC_STATS__SERVICE_ID_NAME__SERVICE_ID_FT_OVER_SMS */
    public static final int f1132xf0790319 = 6;

    /* renamed from: IMS_REGISTRATION_SERVICE_DESC_STATS__SERVICE_ID_NAME__SERVICE_ID_GEO_PUSH */
    public static final int f1133x9107ae2e = 7;

    /* renamed from: IMS_REGISTRATION_SERVICE_DESC_STATS__SERVICE_ID_NAME__SERVICE_ID_GEO_PUSH_VIA_SMS */
    public static final int f1134x93ff5c57 = 8;

    /* renamed from: IMS_REGISTRATION_SERVICE_DESC_STATS__SERVICE_ID_NAME__SERVICE_ID_MMTEL */
    public static final int f1135xdbe5b155 = 2;

    /* renamed from: IMS_REGISTRATION_SERVICE_DESC_STATS__SERVICE_ID_NAME__SERVICE_ID_POST_CALL */
    public static final int f1136x96c04c97 = 10;

    /* renamed from: IMS_REGISTRATION_SERVICE_DESC_STATS__SERVICE_ID_NAME__SERVICE_ID_SHARED_MAP */
    public static final int f1137x2c62afa8 = 11;

    /* renamed from: IMS_REGISTRATION_SERVICE_DESC_STATS__SERVICE_ID_NAME__SERVICE_ID_SHARED_SKETCH */
    public static final int f1138x3afd91c0 = 12;

    /* renamed from: IMS_REGISTRATION_SERVICE_DESC_STATS__SERVICE_ID_NAME__SERVICE_ID_UNSPECIFIED */
    public static final int f1139xdd94d691 = 0;
    public static final int IMS_REGISTRATION_STATS = 10094;
    public static final int IMS_REGISTRATION_STATS__RAT__NETWORK_TYPE_1XRTT = 7;
    public static final int IMS_REGISTRATION_STATS__RAT__NETWORK_TYPE_CDMA = 4;
    public static final int IMS_REGISTRATION_STATS__RAT__NETWORK_TYPE_EDGE = 2;
    public static final int IMS_REGISTRATION_STATS__RAT__NETWORK_TYPE_EHRPD = 14;
    public static final int IMS_REGISTRATION_STATS__RAT__NETWORK_TYPE_EVDO_0 = 5;
    public static final int IMS_REGISTRATION_STATS__RAT__NETWORK_TYPE_EVDO_A = 6;
    public static final int IMS_REGISTRATION_STATS__RAT__NETWORK_TYPE_EVDO_B = 12;
    public static final int IMS_REGISTRATION_STATS__RAT__NETWORK_TYPE_GPRS = 1;
    public static final int IMS_REGISTRATION_STATS__RAT__NETWORK_TYPE_GSM = 16;
    public static final int IMS_REGISTRATION_STATS__RAT__NETWORK_TYPE_HSDPA = 8;
    public static final int IMS_REGISTRATION_STATS__RAT__NETWORK_TYPE_HSPA = 10;
    public static final int IMS_REGISTRATION_STATS__RAT__NETWORK_TYPE_HSPAP = 15;
    public static final int IMS_REGISTRATION_STATS__RAT__NETWORK_TYPE_HSUPA = 9;
    public static final int IMS_REGISTRATION_STATS__RAT__NETWORK_TYPE_IDEN = 11;
    public static final int IMS_REGISTRATION_STATS__RAT__NETWORK_TYPE_IWLAN = 18;
    public static final int IMS_REGISTRATION_STATS__RAT__NETWORK_TYPE_LTE = 13;
    public static final int IMS_REGISTRATION_STATS__RAT__NETWORK_TYPE_LTE_CA = 19;
    public static final int IMS_REGISTRATION_STATS__RAT__NETWORK_TYPE_NR = 20;
    public static final int IMS_REGISTRATION_STATS__RAT__NETWORK_TYPE_TD_SCDMA = 17;
    public static final int IMS_REGISTRATION_STATS__RAT__NETWORK_TYPE_UMTS = 3;
    public static final int IMS_REGISTRATION_STATS__RAT__NETWORK_TYPE_UNKNOWN = 0;
    public static final int IMS_REGISTRATION_TERMINATION = 10093;
    public static final int IMS_REGISTRATION_TERMINATION__RAT_AT_END__NETWORK_TYPE_1XRTT = 7;
    public static final int IMS_REGISTRATION_TERMINATION__RAT_AT_END__NETWORK_TYPE_CDMA = 4;
    public static final int IMS_REGISTRATION_TERMINATION__RAT_AT_END__NETWORK_TYPE_EDGE = 2;
    public static final int IMS_REGISTRATION_TERMINATION__RAT_AT_END__NETWORK_TYPE_EHRPD = 14;
    public static final int IMS_REGISTRATION_TERMINATION__RAT_AT_END__NETWORK_TYPE_EVDO_0 = 5;
    public static final int IMS_REGISTRATION_TERMINATION__RAT_AT_END__NETWORK_TYPE_EVDO_A = 6;
    public static final int IMS_REGISTRATION_TERMINATION__RAT_AT_END__NETWORK_TYPE_EVDO_B = 12;
    public static final int IMS_REGISTRATION_TERMINATION__RAT_AT_END__NETWORK_TYPE_GPRS = 1;
    public static final int IMS_REGISTRATION_TERMINATION__RAT_AT_END__NETWORK_TYPE_GSM = 16;
    public static final int IMS_REGISTRATION_TERMINATION__RAT_AT_END__NETWORK_TYPE_HSDPA = 8;
    public static final int IMS_REGISTRATION_TERMINATION__RAT_AT_END__NETWORK_TYPE_HSPA = 10;
    public static final int IMS_REGISTRATION_TERMINATION__RAT_AT_END__NETWORK_TYPE_HSPAP = 15;
    public static final int IMS_REGISTRATION_TERMINATION__RAT_AT_END__NETWORK_TYPE_HSUPA = 9;
    public static final int IMS_REGISTRATION_TERMINATION__RAT_AT_END__NETWORK_TYPE_IDEN = 11;
    public static final int IMS_REGISTRATION_TERMINATION__RAT_AT_END__NETWORK_TYPE_IWLAN = 18;
    public static final int IMS_REGISTRATION_TERMINATION__RAT_AT_END__NETWORK_TYPE_LTE = 13;
    public static final int IMS_REGISTRATION_TERMINATION__RAT_AT_END__NETWORK_TYPE_LTE_CA = 19;
    public static final int IMS_REGISTRATION_TERMINATION__RAT_AT_END__NETWORK_TYPE_NR = 20;
    public static final int IMS_REGISTRATION_TERMINATION__RAT_AT_END__NETWORK_TYPE_TD_SCDMA = 17;
    public static final int IMS_REGISTRATION_TERMINATION__RAT_AT_END__NETWORK_TYPE_UMTS = 3;
    public static final int IMS_REGISTRATION_TERMINATION__RAT_AT_END__NETWORK_TYPE_UNKNOWN = 0;
    public static final int INCOMING_SMS = 10086;
    public static final int INCOMING_SMS__ERROR__SMS_ERROR_GENERIC = 1;
    public static final int INCOMING_SMS__ERROR__SMS_ERROR_NOT_SUPPORTED = 3;
    public static final int INCOMING_SMS__ERROR__SMS_ERROR_NO_MEMORY = 2;
    public static final int INCOMING_SMS__ERROR__SMS_SUCCESS = 0;
    public static final int INCOMING_SMS__RAT__NETWORK_TYPE_1XRTT = 7;
    public static final int INCOMING_SMS__RAT__NETWORK_TYPE_CDMA = 4;
    public static final int INCOMING_SMS__RAT__NETWORK_TYPE_EDGE = 2;
    public static final int INCOMING_SMS__RAT__NETWORK_TYPE_EHRPD = 14;
    public static final int INCOMING_SMS__RAT__NETWORK_TYPE_EVDO_0 = 5;
    public static final int INCOMING_SMS__RAT__NETWORK_TYPE_EVDO_A = 6;
    public static final int INCOMING_SMS__RAT__NETWORK_TYPE_EVDO_B = 12;
    public static final int INCOMING_SMS__RAT__NETWORK_TYPE_GPRS = 1;
    public static final int INCOMING_SMS__RAT__NETWORK_TYPE_GSM = 16;
    public static final int INCOMING_SMS__RAT__NETWORK_TYPE_HSDPA = 8;
    public static final int INCOMING_SMS__RAT__NETWORK_TYPE_HSPA = 10;
    public static final int INCOMING_SMS__RAT__NETWORK_TYPE_HSPAP = 15;
    public static final int INCOMING_SMS__RAT__NETWORK_TYPE_HSUPA = 9;
    public static final int INCOMING_SMS__RAT__NETWORK_TYPE_IDEN = 11;
    public static final int INCOMING_SMS__RAT__NETWORK_TYPE_IWLAN = 18;
    public static final int INCOMING_SMS__RAT__NETWORK_TYPE_LTE = 13;
    public static final int INCOMING_SMS__RAT__NETWORK_TYPE_LTE_CA = 19;
    public static final int INCOMING_SMS__RAT__NETWORK_TYPE_NR = 20;
    public static final int INCOMING_SMS__RAT__NETWORK_TYPE_TD_SCDMA = 17;
    public static final int INCOMING_SMS__RAT__NETWORK_TYPE_UMTS = 3;
    public static final int INCOMING_SMS__RAT__NETWORK_TYPE_UNKNOWN = 0;
    public static final int INCOMING_SMS__SMS_FORMAT__SMS_FORMAT_3GPP = 1;
    public static final int INCOMING_SMS__SMS_FORMAT__SMS_FORMAT_3GPP2 = 2;
    public static final int INCOMING_SMS__SMS_FORMAT__SMS_FORMAT_UNKNOWN = 0;
    public static final int INCOMING_SMS__SMS_TECH__SMS_TECH_CS_3GPP = 1;
    public static final int INCOMING_SMS__SMS_TECH__SMS_TECH_CS_3GPP2 = 2;
    public static final int INCOMING_SMS__SMS_TECH__SMS_TECH_IMS = 3;
    public static final int INCOMING_SMS__SMS_TECH__SMS_TECH_UNKNOWN = 0;
    public static final int INCOMING_SMS__SMS_TYPE__SMS_TYPE_NORMAL = 0;
    public static final int INCOMING_SMS__SMS_TYPE__SMS_TYPE_SMS_PP = 1;
    public static final int INCOMING_SMS__SMS_TYPE__SMS_TYPE_VOICEMAIL_INDICATION = 2;
    public static final int INCOMING_SMS__SMS_TYPE__SMS_TYPE_WAP_PUSH = 4;
    public static final int INCOMING_SMS__SMS_TYPE__SMS_TYPE_ZERO = 3;
    public static final int MMS_SMS_DATABASE_HELPER_ON_UPGRADE_FAILED = 443;

    /* renamed from: MMS_SMS_DATABASE_HELPER_ON_UPGRADE_FAILED__FAILURE_CODE__FAILURE_IO_EXCEPTION */
    public static final int f1140xc7d328e = 1;

    /* renamed from: MMS_SMS_DATABASE_HELPER_ON_UPGRADE_FAILED__FAILURE_CODE__FAILURE_SECURITY_EXCEPTION */
    public static final int f1141xc792ae28 = 2;

    /* renamed from: MMS_SMS_DATABASE_HELPER_ON_UPGRADE_FAILED__FAILURE_CODE__FAILURE_SQL_EXCEPTION */
    public static final int f1142xadc61ba6 = 3;

    /* renamed from: MMS_SMS_DATABASE_HELPER_ON_UPGRADE_FAILED__FAILURE_CODE__FAILURE_UNKNOWN */
    public static final int f1143x184b2ef2 = 0;
    public static final int MMS_SMS_PROVIDER_GET_THREAD_ID_FAILED = 442;

    /* renamed from: MMS_SMS_PROVIDER_GET_THREAD_ID_FAILED__FAILURE_CODE__FAILURE_FIND_OR_CREATE_THREAD_ID_SQL */
    public static final int f1144x8b64e91a = 2;

    /* renamed from: MMS_SMS_PROVIDER_GET_THREAD_ID_FAILED__FAILURE_CODE__FAILURE_MULTIPLE_THREAD_IDS_FOUND */
    public static final int f1145x83a593ed = 3;

    /* renamed from: MMS_SMS_PROVIDER_GET_THREAD_ID_FAILED__FAILURE_CODE__FAILURE_NO_RECIPIENTS */
    public static final int f1146x18bfdd50 = 1;

    /* renamed from: MMS_SMS_PROVIDER_GET_THREAD_ID_FAILED__FAILURE_CODE__FAILURE_UNKNOWN */
    public static final int f1147x661cd0c2 = 0;
    public static final int MOBILE_CONNECTION_STATE_CHANGED = 75;
    public static final int MOBILE_CONNECTION_STATE_CHANGED__STATE__ACTIVATING = 2;
    public static final int MOBILE_CONNECTION_STATE_CHANGED__STATE__ACTIVE = 3;
    public static final int MOBILE_CONNECTION_STATE_CHANGED__STATE__DISCONNECTING = 4;

    /* renamed from: MOBILE_CONNECTION_STATE_CHANGED__STATE__DISCONNECTION_ERROR_CREATING_CONNECTION */
    public static final int f1148x2f245b6b = 5;
    public static final int MOBILE_CONNECTION_STATE_CHANGED__STATE__INACTIVE = 1;
    public static final int MOBILE_CONNECTION_STATE_CHANGED__STATE__UNKNOWN = 0;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED = 76;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED__STATE__NETWORK_TYPE_1XRTT = 7;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED__STATE__NETWORK_TYPE_CDMA = 4;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED__STATE__NETWORK_TYPE_EDGE = 2;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED__STATE__NETWORK_TYPE_EHRPD = 14;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED__STATE__NETWORK_TYPE_EVDO_0 = 5;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED__STATE__NETWORK_TYPE_EVDO_A = 6;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED__STATE__NETWORK_TYPE_EVDO_B = 12;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED__STATE__NETWORK_TYPE_GPRS = 1;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED__STATE__NETWORK_TYPE_GSM = 16;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED__STATE__NETWORK_TYPE_HSDPA = 8;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED__STATE__NETWORK_TYPE_HSPA = 10;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED__STATE__NETWORK_TYPE_HSPAP = 15;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED__STATE__NETWORK_TYPE_HSUPA = 9;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED__STATE__NETWORK_TYPE_IDEN = 11;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED__STATE__NETWORK_TYPE_IWLAN = 18;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED__STATE__NETWORK_TYPE_LTE = 13;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED__STATE__NETWORK_TYPE_LTE_CA = 19;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED__STATE__NETWORK_TYPE_NR = 20;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED__STATE__NETWORK_TYPE_TD_SCDMA = 17;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED__STATE__NETWORK_TYPE_UMTS = 3;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED__STATE__NETWORK_TYPE_UNKNOWN = 0;
    public static final int MODEM_RESTART = 312;
    public static final int OUTGOING_SHORT_CODE_SMS = 10162;
    public static final int OUTGOING_SHORT_CODE_SMS__CATEGORY__SMS_CATEGORY_FREE_SHORT_CODE = 1;
    public static final int OUTGOING_SHORT_CODE_SMS__CATEGORY__SMS_CATEGORY_NOT_SHORT_CODE = 0;

    /* renamed from: OUTGOING_SHORT_CODE_SMS__CATEGORY__SMS_CATEGORY_POSSIBLE_PREMIUM_SHORT_CODE */
    public static final int f1149x4c71cc66 = 3;

    /* renamed from: OUTGOING_SHORT_CODE_SMS__CATEGORY__SMS_CATEGORY_PREMIUM_SHORT_CODE */
    public static final int f1150x54d8a318 = 4;

    /* renamed from: OUTGOING_SHORT_CODE_SMS__CATEGORY__SMS_CATEGORY_STANDARD_SHORT_CODE */
    public static final int f1151x2d01a92 = 2;
    public static final int OUTGOING_SMS = 10087;
    public static final int OUTGOING_SMS__RAT__NETWORK_TYPE_1XRTT = 7;
    public static final int OUTGOING_SMS__RAT__NETWORK_TYPE_CDMA = 4;
    public static final int OUTGOING_SMS__RAT__NETWORK_TYPE_EDGE = 2;
    public static final int OUTGOING_SMS__RAT__NETWORK_TYPE_EHRPD = 14;
    public static final int OUTGOING_SMS__RAT__NETWORK_TYPE_EVDO_0 = 5;
    public static final int OUTGOING_SMS__RAT__NETWORK_TYPE_EVDO_A = 6;
    public static final int OUTGOING_SMS__RAT__NETWORK_TYPE_EVDO_B = 12;
    public static final int OUTGOING_SMS__RAT__NETWORK_TYPE_GPRS = 1;
    public static final int OUTGOING_SMS__RAT__NETWORK_TYPE_GSM = 16;
    public static final int OUTGOING_SMS__RAT__NETWORK_TYPE_HSDPA = 8;
    public static final int OUTGOING_SMS__RAT__NETWORK_TYPE_HSPA = 10;
    public static final int OUTGOING_SMS__RAT__NETWORK_TYPE_HSPAP = 15;
    public static final int OUTGOING_SMS__RAT__NETWORK_TYPE_HSUPA = 9;
    public static final int OUTGOING_SMS__RAT__NETWORK_TYPE_IDEN = 11;
    public static final int OUTGOING_SMS__RAT__NETWORK_TYPE_IWLAN = 18;
    public static final int OUTGOING_SMS__RAT__NETWORK_TYPE_LTE = 13;
    public static final int OUTGOING_SMS__RAT__NETWORK_TYPE_LTE_CA = 19;
    public static final int OUTGOING_SMS__RAT__NETWORK_TYPE_NR = 20;
    public static final int OUTGOING_SMS__RAT__NETWORK_TYPE_TD_SCDMA = 17;
    public static final int OUTGOING_SMS__RAT__NETWORK_TYPE_UMTS = 3;
    public static final int OUTGOING_SMS__RAT__NETWORK_TYPE_UNKNOWN = 0;

    /* renamed from: OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_BLUETOOTH_DISCONNECTED */
    public static final int f1152x3499f4df = 27;
    public static final int OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_CANCELLED = 23;
    public static final int OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_FDN_CHECK_FAILURE = 6;
    public static final int OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_GENERIC_FAILURE = 1;
    public static final int OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_INTERNAL_ERROR = 21;
    public static final int OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_INVALID_ARGUMENTS = 11;

    /* renamed from: OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_INVALID_BLUETOOTH_ADDRESS */
    public static final int f1153x37e59548 = 26;

    /* renamed from: OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_INVALID_SMSC_ADDRESS */
    public static final int f1154x296c057a = 19;
    public static final int OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_INVALID_SMS_FORMAT = 14;
    public static final int OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_INVALID_STATE = 12;
    public static final int OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_LIMIT_EXCEEDED = 5;
    public static final int OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_MODEM_ERROR = 16;
    public static final int OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_NETWORK_ERROR = 17;
    public static final int OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_NETWORK_REJECT = 10;
    public static final int OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_NONE = 0;

    /* renamed from: OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_NO_BLUETOOTH_SERVICE */
    public static final int f1155x2ede7c39 = 25;
    public static final int OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_NO_DEFAULT_SMS_APP = 32;
    public static final int OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_NO_MEMORY = 13;
    public static final int OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_NO_RESOURCES = 22;
    public static final int OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_NO_SERVICE = 4;
    public static final int OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_NULL_PDU = 3;

    /* renamed from: OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_OPERATION_NOT_ALLOWED */
    public static final int f1156xab10211 = 20;

    /* renamed from: OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_RADIO_NOT_AVAILABLE */
    public static final int f1157x68e32306 = 9;
    public static final int OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_RADIO_OFF = 2;
    public static final int OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_REMOTE_EXCEPTION = 31;

    /* renamed from: OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_REQUEST_NOT_SUPPORTED */
    public static final int f1158x4ddedd3f = 24;
    public static final int OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_RIL_ABORTED = 137;
    public static final int OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_RIL_ACCESS_BARRED = 122;

    /* renamed from: OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_RIL_BLOCKED_DUE_TO_CALL */
    public static final int f1159x6a2fabc7 = 123;
    public static final int OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_RIL_CANCELLED = 119;
    public static final int OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_RIL_DEVICE_IN_USE = 136;
    public static final int OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_RIL_ENCODING_ERR = 109;
    public static final int OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_RIL_GENERIC_ERROR = 124;
    public static final int OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_RIL_INTERNAL_ERR = 113;

    /* renamed from: OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_RIL_INVALID_ARGUMENTS */
    public static final int f1160xdaac0511 = 104;

    /* renamed from: OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_RIL_INVALID_MODEM_STATE */
    public static final int f1161xef5f3417 = 115;

    /* renamed from: OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_RIL_INVALID_RESPONSE */
    public static final int f1162x1ff92106 = 125;

    /* renamed from: OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_RIL_INVALID_SIM_STATE */
    public static final int f1163xef6246c4 = 130;

    /* renamed from: OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_RIL_INVALID_SMSC_ADDRESS */
    public static final int f1164x29141d04 = 110;

    /* renamed from: OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_RIL_INVALID_SMS_FORMAT */
    public static final int f1165xa6d18542 = 107;
    public static final int OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_RIL_INVALID_STATE = 103;
    public static final int OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_RIL_MODEM_ERR = 111;
    public static final int OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_RIL_NETWORK_ERR = 112;

    /* renamed from: OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_RIL_NETWORK_NOT_READY */
    public static final int f1166x691bac49 = 116;
    public static final int OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_RIL_NETWORK_REJECT = 102;
    public static final int OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_RIL_NO_MEMORY = 105;

    /* renamed from: OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_RIL_NO_NETWORK_FOUND */
    public static final int f1167x4d98a350 = 135;
    public static final int OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_RIL_NO_RESOURCES = 118;
    public static final int OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_RIL_NO_SMS_TO_ACK = 131;

    /* renamed from: OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_RIL_NO_SUBSCRIPTION */
    public static final int f1168x16439bde = 134;

    /* renamed from: OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_RIL_OPERATION_NOT_ALLOWED */
    public static final int f1169xbdbc7 = 117;

    /* renamed from: OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_RIL_RADIO_NOT_AVAILABLE */
    public static final int f1170xbb74f23c = 100;

    /* renamed from: OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_RIL_REQUEST_NOT_SUPPORTED */
    public static final int f1171x4339b6f5 = 114;

    /* renamed from: OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_RIL_REQUEST_RATE_LIMITED */
    public static final int f1172x67f4d5c8 = 106;

    /* renamed from: OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_RIL_SIMULTANEOUS_SMS_AND_CALL_NOT_ALLOWED */
    public static final int f1173xbc559670 = 121;
    public static final int OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_RIL_SIM_ABSENT = 120;
    public static final int OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_RIL_SIM_BUSY = 132;
    public static final int OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_RIL_SIM_ERR = 129;
    public static final int OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_RIL_SIM_FULL = 133;
    public static final int OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_RIL_SIM_PIN2 = 126;
    public static final int OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_RIL_SIM_PUK2 = 127;

    /* renamed from: OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_RIL_SMS_SEND_FAIL_RETRY */
    public static final int f1174xaeb63bbb = 101;

    /* renamed from: OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_RIL_SUBSCRIPTION_NOT_AVAILABLE */
    public static final int f1175x80b9398 = 128;
    public static final int OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_RIL_SYSTEM_ERR = 108;

    /* renamed from: OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_SHORT_CODE_NEVER_ALLOWED */
    public static final int f1176xf3880c79 = 8;

    /* renamed from: OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_SHORT_CODE_NOT_ALLOWED */
    public static final int f1177x75a5d2c0 = 7;

    /* renamed from: OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_SMS_BLOCKED_DURING_EMERGENCY */
    public static final int f1178x6540dd9f = 29;

    /* renamed from: OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_SMS_SEND_RETRY_FAILED */
    public static final int f1179xce7330b2 = 30;
    public static final int OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_SYSTEM_ERROR = 15;

    /* renamed from: OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_UNEXPECTED_EVENT_STOP_SENDING */
    public static final int f1180x5ca5285d = 28;
    public static final int OUTGOING_SMS__SEND_ERROR_CODE__SMS_SEND_ERROR_USER_NOT_ALLOWED = 33;
    public static final int OUTGOING_SMS__SEND_RESULT__SMS_SEND_RESULT_ERROR = 2;
    public static final int OUTGOING_SMS__SEND_RESULT__SMS_SEND_RESULT_ERROR_FALLBACK = 4;
    public static final int OUTGOING_SMS__SEND_RESULT__SMS_SEND_RESULT_ERROR_RETRY = 3;
    public static final int OUTGOING_SMS__SEND_RESULT__SMS_SEND_RESULT_SUCCESS = 1;
    public static final int OUTGOING_SMS__SEND_RESULT__SMS_SEND_RESULT_UNKNOWN = 0;
    public static final int OUTGOING_SMS__SMS_FORMAT__SMS_FORMAT_3GPP = 1;
    public static final int OUTGOING_SMS__SMS_FORMAT__SMS_FORMAT_3GPP2 = 2;
    public static final int OUTGOING_SMS__SMS_FORMAT__SMS_FORMAT_UNKNOWN = 0;
    public static final int OUTGOING_SMS__SMS_TECH__SMS_TECH_CS_3GPP = 1;
    public static final int OUTGOING_SMS__SMS_TECH__SMS_TECH_CS_3GPP2 = 2;
    public static final int OUTGOING_SMS__SMS_TECH__SMS_TECH_IMS = 3;
    public static final int OUTGOING_SMS__SMS_TECH__SMS_TECH_UNKNOWN = 0;
    public static final int PER_SIM_STATUS = 10146;
    public static final int PER_SIM_STATUS__SIM_VOLTAGE_CLASS__VOLTAGE_CLASS_A = 1;
    public static final int PER_SIM_STATUS__SIM_VOLTAGE_CLASS__VOLTAGE_CLASS_B = 2;
    public static final int PER_SIM_STATUS__SIM_VOLTAGE_CLASS__VOLTAGE_CLASS_C = 3;
    public static final int PER_SIM_STATUS__SIM_VOLTAGE_CLASS__VOLTAGE_CLASS_UNKNOWN = 0;
    public static final int PER_SIM_STATUS__WFC_MODE__CELLULAR_PREFERRED = 2;
    public static final int PER_SIM_STATUS__WFC_MODE__UNKNOWN = 0;
    public static final int PER_SIM_STATUS__WFC_MODE__WIFI_ONLY = 1;
    public static final int PER_SIM_STATUS__WFC_MODE__WIFI_PREFERRED = 3;
    public static final int PER_SIM_STATUS__WFC_ROAMING_MODE__CELLULAR_PREFERRED = 2;
    public static final int PER_SIM_STATUS__WFC_ROAMING_MODE__UNKNOWN = 0;
    public static final int PER_SIM_STATUS__WFC_ROAMING_MODE__WIFI_ONLY = 1;
    public static final int PER_SIM_STATUS__WFC_ROAMING_MODE__WIFI_PREFERRED = 3;
    public static final int PIN_STORAGE_EVENT = 336;
    public static final int PIN_STORAGE_EVENT__EVENT__CACHED_PIN_DISCARDED = 3;
    public static final int PIN_STORAGE_EVENT__EVENT__PIN_COUNT_NOT_MATCHING_AFTER_REBOOT = 7;
    public static final int PIN_STORAGE_EVENT__EVENT__PIN_DECRYPTION_ERROR = 8;
    public static final int PIN_STORAGE_EVENT__EVENT__PIN_ENCRYPTION_ERROR = 9;
    public static final int PIN_STORAGE_EVENT__EVENT__PIN_ENCRYPTION_KEY_MISSING = 10;
    public static final int PIN_STORAGE_EVENT__EVENT__PIN_REQUIRED_AFTER_REBOOT = 5;
    public static final int PIN_STORAGE_EVENT__EVENT__PIN_STORED_FOR_VERIFICATION = 4;
    public static final int PIN_STORAGE_EVENT__EVENT__PIN_VERIFICATION_FAILURE = 2;

    /* renamed from: PIN_STORAGE_EVENT__EVENT__PIN_VERIFICATION_SKIPPED_SIM_CARD_MISMATCH */
    public static final int f1181x7cc5fba = 6;
    public static final int PIN_STORAGE_EVENT__EVENT__PIN_VERIFICATION_SUCCESS = 1;
    public static final int PIN_STORAGE_EVENT__EVENT__UNKNOWN = 0;
    public static final int PRESENCE_NOTIFY_EVENT = 10144;
    public static final int PRESENCE_NOTIFY_EVENT__REASON__REASON_CUSTOM = 1;
    public static final int PRESENCE_NOTIFY_EVENT__REASON__REASON_DEACTIVATED = 2;
    public static final int PRESENCE_NOTIFY_EVENT__REASON__REASON_GIVEUP = 6;
    public static final int PRESENCE_NOTIFY_EVENT__REASON__REASON_NORESOURCE = 7;
    public static final int PRESENCE_NOTIFY_EVENT__REASON__REASON_PROBATION = 3;
    public static final int PRESENCE_NOTIFY_EVENT__REASON__REASON_REJECTED = 4;
    public static final int PRESENCE_NOTIFY_EVENT__REASON__REASON_TIMEOUT = 5;
    public static final int PRESENCE_NOTIFY_EVENT__REASON__REASON_UNSPECIFIED = 0;
    public static final int RCS_ACS_PROVISIONING_STATS = 10135;
    public static final int RCS_ACS_PROVISIONING_STATS__RESPONSE_TYPE__ERROR = 1;
    public static final int RCS_ACS_PROVISIONING_STATS__RESPONSE_TYPE__PRE_PROVISIONING_XML = 3;
    public static final int RCS_ACS_PROVISIONING_STATS__RESPONSE_TYPE__PROVISIONING_XML = 2;

    /* renamed from: RCS_ACS_PROVISIONING_STATS__RESPONSE_TYPE__RESPONSE_TYPE_UNSPECIFIED */
    public static final int f1182x5c0e9430 = 0;
    public static final int RCS_CLIENT_PROVISIONING_STATS = 10134;
    public static final int RCS_CLIENT_PROVISIONING_STATS__EVENT__CLIENT_PARAMS_SENT = 1;
    public static final int RCS_CLIENT_PROVISIONING_STATS__EVENT__DMA_CHANGED = 3;
    public static final int RCS_CLIENT_PROVISIONING_STATS__EVENT__EVENT_TYPE_UNSPECIFIED = 0;

    /* renamed from: RCS_CLIENT_PROVISIONING_STATS__EVENT__TRIGGER_RCS_RECONFIGURATION */
    public static final int f1183xee3a2009 = 2;
    public static final int SATELLITE_CONTROLLER = 10182;
    public static final int SATELLITE_INCOMING_DATAGRAM = 10184;

    /* renamed from: SATELLITE_INCOMING_DATAGRAM__RESULT_CODE__SATELLITE_ACCESS_BARRED */
    public static final int f1184x37ec071c = 16;
    public static final int SATELLITE_INCOMING_DATAGRAM__RESULT_CODE__SATELLITE_ERROR = 1;
    public static final int SATELLITE_INCOMING_DATAGRAM__RESULT_CODE__SATELLITE_ERROR_NONE = 0;

    /* renamed from: SATELLITE_INCOMING_DATAGRAM__RESULT_CODE__SATELLITE_INVALID_ARGUMENTS */
    public static final int f1185x1c35ff91 = 8;

    /* renamed from: SATELLITE_INCOMING_DATAGRAM__RESULT_CODE__SATELLITE_INVALID_MODEM_STATE */
    public static final int f1186xf6548e97 = 7;

    /* renamed from: SATELLITE_INCOMING_DATAGRAM__RESULT_CODE__SATELLITE_INVALID_TELEPHONY_STATE */
    public static final int f1187xb11f1f85 = 6;
    public static final int SATELLITE_INCOMING_DATAGRAM__RESULT_CODE__SATELLITE_MODEM_ERROR = 4;

    /* renamed from: SATELLITE_INCOMING_DATAGRAM__RESULT_CODE__SATELLITE_NETWORK_ERROR */
    public static final int f1188xcb15d8ba = 5;

    /* renamed from: SATELLITE_INCOMING_DATAGRAM__RESULT_CODE__SATELLITE_NETWORK_TIMEOUT */
    public static final int f1189x66d96b93 = 17;

    /* renamed from: SATELLITE_INCOMING_DATAGRAM__RESULT_CODE__SATELLITE_NOT_AUTHORIZED */
    public static final int f1190xe7f9fac4 = 19;

    /* renamed from: SATELLITE_INCOMING_DATAGRAM__RESULT_CODE__SATELLITE_NOT_REACHABLE */
    public static final int f1191xf00c2ae4 = 18;

    /* renamed from: SATELLITE_INCOMING_DATAGRAM__RESULT_CODE__SATELLITE_NOT_SUPPORTED */
    public static final int f1192x327a8245 = 20;
    public static final int SATELLITE_INCOMING_DATAGRAM__RESULT_CODE__SATELLITE_NO_RESOURCES = 12;

    /* renamed from: SATELLITE_INCOMING_DATAGRAM__RESULT_CODE__SATELLITE_RADIO_NOT_AVAILABLE */
    public static final int f1193xc26a4cbc = 10;

    /* renamed from: SATELLITE_INCOMING_DATAGRAM__RESULT_CODE__SATELLITE_REQUEST_ABORTED */
    public static final int f1194x5436e962 = 15;

    /* renamed from: SATELLITE_INCOMING_DATAGRAM__RESULT_CODE__SATELLITE_REQUEST_FAILED */
    public static final int f1195x8f4fcc2a = 9;

    /* renamed from: SATELLITE_INCOMING_DATAGRAM__RESULT_CODE__SATELLITE_REQUEST_NOT_SUPPORTED */
    public static final int f1196x62427175 = 11;
    public static final int SATELLITE_INCOMING_DATAGRAM__RESULT_CODE__SATELLITE_SERVER_ERROR = 2;

    /* renamed from: SATELLITE_INCOMING_DATAGRAM__RESULT_CODE__SATELLITE_SERVICE_ERROR */
    public static final int f1197x88608401 = 3;

    /* renamed from: SATELLITE_INCOMING_DATAGRAM__RESULT_CODE__SATELLITE_SERVICE_NOT_PROVISIONED */
    public static final int f1198x50a10f61 = 13;

    /* renamed from: SATELLITE_INCOMING_DATAGRAM__RESULT_CODE__SATELLITE_SERVICE_PROVISION_IN_PROGRESS */
    public static final int f1199x33374b96 = 14;
    public static final int SATELLITE_OUTGOING_DATAGRAM = 10185;

    /* renamed from: SATELLITE_OUTGOING_DATAGRAM__RESULT_CODE__SATELLITE_ACCESS_BARRED */
    public static final int f1200x5fddaf16 = 16;
    public static final int SATELLITE_OUTGOING_DATAGRAM__RESULT_CODE__SATELLITE_ERROR = 1;
    public static final int SATELLITE_OUTGOING_DATAGRAM__RESULT_CODE__SATELLITE_ERROR_NONE = 0;

    /* renamed from: SATELLITE_OUTGOING_DATAGRAM__RESULT_CODE__SATELLITE_INVALID_ARGUMENTS */
    public static final int f1201x22bf1a8b = 8;

    /* renamed from: SATELLITE_OUTGOING_DATAGRAM__RESULT_CODE__SATELLITE_INVALID_MODEM_STATE */
    public static final int f1202x7f02d311 = 7;

    /* renamed from: SATELLITE_OUTGOING_DATAGRAM__RESULT_CODE__SATELLITE_INVALID_TELEPHONY_STATE */
    public static final int f1203xf7c296ff = 6;
    public static final int SATELLITE_OUTGOING_DATAGRAM__RESULT_CODE__SATELLITE_MODEM_ERROR = 4;

    /* renamed from: SATELLITE_OUTGOING_DATAGRAM__RESULT_CODE__SATELLITE_NETWORK_ERROR */
    public static final int f1204xf30780b4 = 5;

    /* renamed from: SATELLITE_OUTGOING_DATAGRAM__RESULT_CODE__SATELLITE_NETWORK_TIMEOUT */
    public static final int f1205x5900fd0d = 17;

    /* renamed from: SATELLITE_OUTGOING_DATAGRAM__RESULT_CODE__SATELLITE_NOT_AUTHORIZED */
    public static final int f1206xbe3d520a = 19;

    /* renamed from: SATELLITE_OUTGOING_DATAGRAM__RESULT_CODE__SATELLITE_NOT_REACHABLE */
    public static final int f1207x17fdd2de = 18;

    /* renamed from: SATELLITE_OUTGOING_DATAGRAM__RESULT_CODE__SATELLITE_NOT_SUPPORTED */
    public static final int f1208x5a6c2a3f = 20;
    public static final int SATELLITE_OUTGOING_DATAGRAM__RESULT_CODE__SATELLITE_NO_RESOURCES = 12;

    /* renamed from: SATELLITE_OUTGOING_DATAGRAM__RESULT_CODE__SATELLITE_RADIO_NOT_AVAILABLE */
    public static final int f1209x4b189136 = 10;

    /* renamed from: SATELLITE_OUTGOING_DATAGRAM__RESULT_CODE__SATELLITE_REQUEST_ABORTED */
    public static final int f1210x465e7adc = 15;

    /* renamed from: SATELLITE_OUTGOING_DATAGRAM__RESULT_CODE__SATELLITE_REQUEST_FAILED */
    public static final int f1211x65932370 = 9;

    /* renamed from: SATELLITE_OUTGOING_DATAGRAM__RESULT_CODE__SATELLITE_REQUEST_NOT_SUPPORTED */
    public static final int f1212x78717f6f = 11;
    public static final int SATELLITE_OUTGOING_DATAGRAM__RESULT_CODE__SATELLITE_SERVER_ERROR = 2;

    /* renamed from: SATELLITE_OUTGOING_DATAGRAM__RESULT_CODE__SATELLITE_SERVICE_ERROR */
    public static final int f1213xb0522bfb = 3;

    /* renamed from: SATELLITE_OUTGOING_DATAGRAM__RESULT_CODE__SATELLITE_SERVICE_NOT_PROVISIONED */
    public static final int f1214x974486db = 13;

    /* renamed from: SATELLITE_OUTGOING_DATAGRAM__RESULT_CODE__SATELLITE_SERVICE_PROVISION_IN_PROGRESS */
    public static final int f1215xeeddbf90 = 14;

    /* renamed from: SATELLITE_OUTGOING_DATAGRAM__SATELLITE_DATAGRAM_TYPE__DATAGRAM_TYPE_LOCATION_SHARING */
    public static final int f1216xf3c1df1d = 2;

    /* renamed from: SATELLITE_OUTGOING_DATAGRAM__SATELLITE_DATAGRAM_TYPE__DATAGRAM_TYPE_SOS_MESSAGE */
    public static final int f1217xc7133554 = 1;

    /* renamed from: SATELLITE_OUTGOING_DATAGRAM__SATELLITE_DATAGRAM_TYPE__DATAGRAM_TYPE_UNKNOWN */
    public static final int f1218xe07cc2df = 0;
    public static final int SATELLITE_PROVISION = 10186;
    public static final int SATELLITE_PROVISION__RESULT_CODE__SATELLITE_ACCESS_BARRED = 16;
    public static final int SATELLITE_PROVISION__RESULT_CODE__SATELLITE_ERROR = 1;
    public static final int SATELLITE_PROVISION__RESULT_CODE__SATELLITE_ERROR_NONE = 0;
    public static final int SATELLITE_PROVISION__RESULT_CODE__SATELLITE_INVALID_ARGUMENTS = 8;
    public static final int SATELLITE_PROVISION__RESULT_CODE__SATELLITE_INVALID_MODEM_STATE = 7;

    /* renamed from: SATELLITE_PROVISION__RESULT_CODE__SATELLITE_INVALID_TELEPHONY_STATE */
    public static final int f1219x52554940 = 6;
    public static final int SATELLITE_PROVISION__RESULT_CODE__SATELLITE_MODEM_ERROR = 4;
    public static final int SATELLITE_PROVISION__RESULT_CODE__SATELLITE_NETWORK_ERROR = 5;
    public static final int SATELLITE_PROVISION__RESULT_CODE__SATELLITE_NETWORK_TIMEOUT = 17;
    public static final int SATELLITE_PROVISION__RESULT_CODE__SATELLITE_NOT_AUTHORIZED = 19;
    public static final int SATELLITE_PROVISION__RESULT_CODE__SATELLITE_NOT_REACHABLE = 18;
    public static final int SATELLITE_PROVISION__RESULT_CODE__SATELLITE_NOT_SUPPORTED = 20;
    public static final int SATELLITE_PROVISION__RESULT_CODE__SATELLITE_NO_RESOURCES = 12;
    public static final int SATELLITE_PROVISION__RESULT_CODE__SATELLITE_RADIO_NOT_AVAILABLE = 10;
    public static final int SATELLITE_PROVISION__RESULT_CODE__SATELLITE_REQUEST_ABORTED = 15;
    public static final int SATELLITE_PROVISION__RESULT_CODE__SATELLITE_REQUEST_FAILED = 9;

    /* renamed from: SATELLITE_PROVISION__RESULT_CODE__SATELLITE_REQUEST_NOT_SUPPORTED */
    public static final int f1220x3cddcdf0 = 11;
    public static final int SATELLITE_PROVISION__RESULT_CODE__SATELLITE_SERVER_ERROR = 2;
    public static final int SATELLITE_PROVISION__RESULT_CODE__SATELLITE_SERVICE_ERROR = 3;

    /* renamed from: SATELLITE_PROVISION__RESULT_CODE__SATELLITE_SERVICE_NOT_PROVISIONED */
    public static final int f1221xf1d7391c = 13;

    /* renamed from: SATELLITE_PROVISION__RESULT_CODE__SATELLITE_SERVICE_PROVISION_IN_PROGRESS */
    public static final int f1222x1797fd11 = 14;
    public static final int SATELLITE_SESSION = 10183;

    /* renamed from: SATELLITE_SESSION__SATELLITE_SERVICE_INITIALIZATION_RESULT__SATELLITE_ACCESS_BARRED */
    public static final int f1223x8332f6f9 = 16;

    /* renamed from: SATELLITE_SESSION__SATELLITE_SERVICE_INITIALIZATION_RESULT__SATELLITE_ERROR */
    public static final int f1224xeabae208 = 1;

    /* renamed from: SATELLITE_SESSION__SATELLITE_SERVICE_INITIALIZATION_RESULT__SATELLITE_ERROR_NONE */
    public static final int f1225x73c8830f = 0;

    /* renamed from: SATELLITE_SESSION__SATELLITE_SERVICE_INITIALIZATION_RESULT__SATELLITE_INVALID_ARGUMENTS */
    public static final int f1226x8897b8ee = 8;

    /* renamed from: SATELLITE_SESSION__SATELLITE_SERVICE_INITIALIZATION_RESULT__SATELLITE_INVALID_MODEM_STATE */
    public static final int f1227xd12d64b4 = 7;

    /* renamed from: SATELLITE_SESSION__SATELLITE_SERVICE_INITIALIZATION_RESULT__SATELLITE_INVALID_TELEPHONY_STATE */
    public static final int f1228x29359f22 = 6;

    /* renamed from: SATELLITE_SESSION__SATELLITE_SERVICE_INITIALIZATION_RESULT__SATELLITE_MODEM_ERROR */
    public static final int f1229x9c0ac093 = 4;

    /* renamed from: SATELLITE_SESSION__SATELLITE_SERVICE_INITIALIZATION_RESULT__SATELLITE_NETWORK_ERROR */
    public static final int f1230x165cc897 = 5;

    /* renamed from: SATELLITE_SESSION__SATELLITE_SERVICE_INITIALIZATION_RESULT__SATELLITE_NETWORK_TIMEOUT */
    public static final int f1231xfc23d830 = 17;

    /* renamed from: SATELLITE_SESSION__SATELLITE_SERVICE_INITIALIZATION_RESULT__SATELLITE_NOT_AUTHORIZED */
    public static final int f1232x5910687 = 19;

    /* renamed from: SATELLITE_SESSION__SATELLITE_SERVICE_INITIALIZATION_RESULT__SATELLITE_NOT_REACHABLE */
    public static final int f1233x3b531ac1 = 18;

    /* renamed from: SATELLITE_SESSION__SATELLITE_SERVICE_INITIALIZATION_RESULT__SATELLITE_NOT_SUPPORTED */
    public static final int f1234x7dc17222 = 20;

    /* renamed from: SATELLITE_SESSION__SATELLITE_SERVICE_INITIALIZATION_RESULT__SATELLITE_NO_RESOURCES */
    public static final int f1235x8f9de8e7 = 12;

    /* renamed from: SATELLITE_SESSION__SATELLITE_SERVICE_INITIALIZATION_RESULT__SATELLITE_RADIO_NOT_AVAILABLE */
    public static final int f1236x9d4322d9 = 10;

    /* renamed from: SATELLITE_SESSION__SATELLITE_SERVICE_INITIALIZATION_RESULT__SATELLITE_REQUEST_ABORTED */
    public static final int f1237xe98155ff = 15;

    /* renamed from: SATELLITE_SESSION__SATELLITE_SERVICE_INITIALIZATION_RESULT__SATELLITE_REQUEST_FAILED */
    public static final int f1238xace6d7ed = 9;

    /* renamed from: SATELLITE_SESSION__SATELLITE_SERVICE_INITIALIZATION_RESULT__SATELLITE_REQUEST_NOT_SUPPORTED */
    public static final int f1239xea3e3452 = 11;

    /* renamed from: SATELLITE_SESSION__SATELLITE_SERVICE_INITIALIZATION_RESULT__SATELLITE_SERVER_ERROR */
    public static final int f1240xb40435cc = 2;

    /* renamed from: SATELLITE_SESSION__SATELLITE_SERVICE_INITIALIZATION_RESULT__SATELLITE_SERVICE_ERROR */
    public static final int f1241xd3a773de = 3;

    /* renamed from: SATELLITE_SESSION__SATELLITE_SERVICE_INITIALIZATION_RESULT__SATELLITE_SERVICE_NOT_PROVISIONED */
    public static final int f1242xc8b78efe = 13;

    /* renamed from: SATELLITE_SESSION__SATELLITE_SERVICE_INITIALIZATION_RESULT__SATELLITE_SERVICE_PROVISION_IN_PROGRESS */
    public static final int f1243x6b64e173 = 14;

    /* renamed from: SATELLITE_SESSION__SATELLITE_TECHNOLOGY__NT_RADIO_TECHNOLOGY_EMTC_NTN */
    public static final int f1244x3f91cf18 = 3;

    /* renamed from: SATELLITE_SESSION__SATELLITE_TECHNOLOGY__NT_RADIO_TECHNOLOGY_NB_IOT_NTN */
    public static final int f1245x731cfce4 = 1;

    /* renamed from: SATELLITE_SESSION__SATELLITE_TECHNOLOGY__NT_RADIO_TECHNOLOGY_NR_NTN */
    public static final int f1246xeb7d8f45 = 2;

    /* renamed from: SATELLITE_SESSION__SATELLITE_TECHNOLOGY__NT_RADIO_TECHNOLOGY_PROPRIETARY */
    public static final int f1247x7c45d4a7 = 4;

    /* renamed from: SATELLITE_SESSION__SATELLITE_TECHNOLOGY__NT_RADIO_TECHNOLOGY_UNKNOWN */
    public static final int f1248xee92ae32 = 0;
    public static final int SATELLITE_SOS_MESSAGE_RECOMMENDER = 10187;

    /* renamed from: SATELLITE_SOS_MESSAGE_RECOMMENDER__CELLULAR_SERVICE_STATE__SERVICE_STATE_EMERGENCY_ONLY */
    public static final int f1249xee490d69 = 2;

    /* renamed from: SATELLITE_SOS_MESSAGE_RECOMMENDER__CELLULAR_SERVICE_STATE__SERVICE_STATE_IN_SERVICE */
    public static final int f1250x99d690aa = 0;

    /* renamed from: SATELLITE_SOS_MESSAGE_RECOMMENDER__CELLULAR_SERVICE_STATE__SERVICE_STATE_OUT_OF_SERVICE */
    public static final int f1251x8023898d = 1;

    /* renamed from: SATELLITE_SOS_MESSAGE_RECOMMENDER__CELLULAR_SERVICE_STATE__SERVICE_STATE_POWER_OFF */
    public static final int f1252x2ffad1e6 = 3;

    /* renamed from: SATELLITE_SOS_MESSAGE_RECOMMENDER__RECOMMENDING_HANDOVER_TYPE__RECOMMENDING_HANDOVER_TYPE_SOS */
    public static final int f1253xdc24c737 = 1;

    /* renamed from: SATELLITE_SOS_MESSAGE_RECOMMENDER__RECOMMENDING_HANDOVER_TYPE__RECOMMENDING_HANDOVER_TYPE_T911 */
    public static final int f1254xa8743d85 = 2;

    /* renamed from: SATELLITE_SOS_MESSAGE_RECOMMENDER__RECOMMENDING_HANDOVER_TYPE__RECOMMENDING_HANDOVER_TYPE_UNKNOWN */
    public static final int f1255x893bddea = 0;
    public static final int SIM_SLOT_STATE = 10078;
    public static final int SIM_SPECIFIC_SETTINGS_RESTORED = 334;

    /* renamed from: SIM_SPECIFIC_SETTINGS_RESTORED__MATCHING_CRITERIA__SIM_RESTORE_MATCHING_CRITERIA_CARRIER_ID_AND_PHONE_NUMBER */
    public static final int f1256xb4bc7e54 = 3;

    /* renamed from: SIM_SPECIFIC_SETTINGS_RESTORED__MATCHING_CRITERIA__SIM_RESTORE_MATCHING_CRITERIA_CARRIER_ID_ONLY */
    public static final int f1257xa779701e = 4;

    /* renamed from: SIM_SPECIFIC_SETTINGS_RESTORED__MATCHING_CRITERIA__SIM_RESTORE_MATCHING_CRITERIA_ICCID */
    public static final int f1258x9692f1b9 = 2;

    /* renamed from: SIM_SPECIFIC_SETTINGS_RESTORED__MATCHING_CRITERIA__SIM_RESTORE_MATCHING_CRITERIA_NONE */
    public static final int f1259x91410023 = 1;

    /* renamed from: SIM_SPECIFIC_SETTINGS_RESTORED__MATCHING_CRITERIA__SIM_RESTORE_MATCHING_CRITERIA_UNSET */
    public static final int f1260x9741477e = 0;

    /* renamed from: SIM_SPECIFIC_SETTINGS_RESTORED__RESTORED_FROM_SUW__SIM_RESTORE_CASE_SIM_INSERTED */
    public static final int f1261x156fc11a = 2;

    /* renamed from: SIM_SPECIFIC_SETTINGS_RESTORED__RESTORED_FROM_SUW__SIM_RESTORE_CASE_SUW */
    public static final int f1262x6f06537b = 1;

    /* renamed from: SIM_SPECIFIC_SETTINGS_RESTORED__RESTORED_FROM_SUW__SIM_RESTORE_CASE_UNDEFINED_USE_CASE */
    public static final int f1263xa73d3b31 = 0;

    /* renamed from: SIM_SPECIFIC_SETTINGS_RESTORED__RESULT__SIM_RESTORE_RESULT_NONE_MATCH */
    public static final int f1264x39882f1d = 2;

    /* renamed from: SIM_SPECIFIC_SETTINGS_RESTORED__RESULT__SIM_RESTORE_RESULT_SUCCESS */
    public static final int f1265xf13b5f04 = 1;

    /* renamed from: SIM_SPECIFIC_SETTINGS_RESTORED__RESULT__SIM_RESTORE_RESULT_UNKNOWN */
    public static final int f1266x4f8bcb4b = 0;

    /* renamed from: SIM_SPECIFIC_SETTINGS_RESTORED__RESULT__SIM_RESTORE_RESULT_ZERO_SIM_IN_BACKUP */
    public static final int f1267x71c19d7c = 3;
    public static final int SIP_DELEGATE_STATS = 10136;
    public static final int SIP_MESSAGE_RESPONSE = 10138;
    public static final int SIP_MESSAGE_RESPONSE__SIP_MESSAGE_DIRECTION__INCOMING = 1;
    public static final int SIP_MESSAGE_RESPONSE__SIP_MESSAGE_DIRECTION__OUTGOING = 2;
    public static final int SIP_MESSAGE_RESPONSE__SIP_MESSAGE_DIRECTION__UNKNOWN = 0;
    public static final int SIP_MESSAGE_RESPONSE__SIP_MESSAGE_METHOD__SIP_REQUEST_ACK = 3;
    public static final int SIP_MESSAGE_RESPONSE__SIP_MESSAGE_METHOD__SIP_REQUEST_BYE = 5;
    public static final int SIP_MESSAGE_RESPONSE__SIP_MESSAGE_METHOD__SIP_REQUEST_CANCEL = 6;
    public static final int SIP_MESSAGE_RESPONSE__SIP_MESSAGE_METHOD__SIP_REQUEST_CUSTOM = 1;
    public static final int SIP_MESSAGE_RESPONSE__SIP_MESSAGE_METHOD__SIP_REQUEST_INFO = 12;
    public static final int SIP_MESSAGE_RESPONSE__SIP_MESSAGE_METHOD__SIP_REQUEST_INVITE = 2;
    public static final int SIP_MESSAGE_RESPONSE__SIP_MESSAGE_METHOD__SIP_REQUEST_MESSAGE = 14;
    public static final int SIP_MESSAGE_RESPONSE__SIP_MESSAGE_METHOD__SIP_REQUEST_NOTIFY = 10;
    public static final int SIP_MESSAGE_RESPONSE__SIP_MESSAGE_METHOD__SIP_REQUEST_OPTIONS = 4;
    public static final int SIP_MESSAGE_RESPONSE__SIP_MESSAGE_METHOD__SIP_REQUEST_PRACK = 8;
    public static final int SIP_MESSAGE_RESPONSE__SIP_MESSAGE_METHOD__SIP_REQUEST_PUBLISH = 11;
    public static final int SIP_MESSAGE_RESPONSE__SIP_MESSAGE_METHOD__SIP_REQUEST_REFER = 13;
    public static final int SIP_MESSAGE_RESPONSE__SIP_MESSAGE_METHOD__SIP_REQUEST_REGISTER = 7;
    public static final int SIP_MESSAGE_RESPONSE__SIP_MESSAGE_METHOD__SIP_REQUEST_SUBSCRIBE = 9;

    /* renamed from: SIP_MESSAGE_RESPONSE__SIP_MESSAGE_METHOD__SIP_REQUEST_UNSPECIFIED */
    public static final int f1268xee31d426 = 0;
    public static final int SIP_MESSAGE_RESPONSE__SIP_MESSAGE_METHOD__SIP_REQUEST_UPDATE = 15;
    public static final int SIP_TRANSPORT_FEATURE_TAG_STATS = 10137;

    /* renamed from: SIP_TRANSPORT_FEATURE_TAG_STATS__FEATURE_TAG_NAME__IMS_FEATURE_TAG_CALL_COMPOSER_ENRICHED_CALLING */
    public static final int f1269xa5ce17b4 = 7;

    /* renamed from: SIP_TRANSPORT_FEATURE_TAG_STATS__FEATURE_TAG_NAME__IMS_FEATURE_TAG_CALL_COMPOSER_VIA_TELEPHONY */
    public static final int f1270x28e50dc = 8;

    /* renamed from: SIP_TRANSPORT_FEATURE_TAG_STATS__FEATURE_TAG_NAME__IMS_FEATURE_TAG_CHATBOT_COMMUNICATION_USING_SESSION */
    public static final int f1271xd524b6b5 = 14;

    /* renamed from: SIP_TRANSPORT_FEATURE_TAG_STATS__FEATURE_TAG_NAME__IMS_FEATURE_TAG_CHATBOT_COMMUNICATION_USING_STANDALONE_MSG */
    public static final int f1272x145edf48 = 15;

    /* renamed from: SIP_TRANSPORT_FEATURE_TAG_STATS__FEATURE_TAG_NAME__IMS_FEATURE_TAG_CHATBOT_ROLE */
    public static final int f1273x45f69df3 = 17;

    /* renamed from: SIP_TRANSPORT_FEATURE_TAG_STATS__FEATURE_TAG_NAME__IMS_FEATURE_TAG_CHATBOT_VERSION_SUPPORTED */
    public static final int f1274xf9396f6a = 16;

    /* renamed from: SIP_TRANSPORT_FEATURE_TAG_STATS__FEATURE_TAG_NAME__IMS_FEATURE_TAG_CHAT_IM */
    public static final int f1275xb48514be = 3;

    /* renamed from: SIP_TRANSPORT_FEATURE_TAG_STATS__FEATURE_TAG_NAME__IMS_FEATURE_TAG_CHAT_SESSION */
    public static final int f1276xc3d0331c = 4;

    /* renamed from: SIP_TRANSPORT_FEATURE_TAG_STATS__FEATURE_TAG_NAME__IMS_FEATURE_TAG_CUSTOM */
    public static final int f1277xb3fd77be = 1;

    /* renamed from: SIP_TRANSPORT_FEATURE_TAG_STATS__FEATURE_TAG_NAME__IMS_FEATURE_TAG_FILE_TRANSFER */
    public static final int f1278x978fde61 = 5;

    /* renamed from: SIP_TRANSPORT_FEATURE_TAG_STATS__FEATURE_TAG_NAME__IMS_FEATURE_TAG_FILE_TRANSFER_VIA_SMS */
    public static final int f1279xf0f9a98a = 6;

    /* renamed from: SIP_TRANSPORT_FEATURE_TAG_STATS__FEATURE_TAG_NAME__IMS_FEATURE_TAG_GEO_PUSH */
    public static final int f1280xf567bc75 = 12;

    /* renamed from: SIP_TRANSPORT_FEATURE_TAG_STATS__FEATURE_TAG_NAME__IMS_FEATURE_TAG_GEO_PUSH_VIA_SMS */
    public static final int f1281x756c339e = 13;

    /* renamed from: SIP_TRANSPORT_FEATURE_TAG_STATS__FEATURE_TAG_NAME__IMS_FEATURE_TAG_MMTEL */
    public static final int f1282x8236a06e = 18;

    /* renamed from: SIP_TRANSPORT_FEATURE_TAG_STATS__FEATURE_TAG_NAME__IMS_FEATURE_TAG_POST_CALL */
    public static final int f1283xbe620730 = 9;

    /* renamed from: SIP_TRANSPORT_FEATURE_TAG_STATS__FEATURE_TAG_NAME__IMS_FEATURE_TAG_PRESENCE */
    public static final int f1284x3a44a608 = 20;

    /* renamed from: SIP_TRANSPORT_FEATURE_TAG_STATS__FEATURE_TAG_NAME__IMS_FEATURE_TAG_SHARED_MAP */
    public static final int f1285xf8f8482f = 10;

    /* renamed from: SIP_TRANSPORT_FEATURE_TAG_STATS__FEATURE_TAG_NAME__IMS_FEATURE_TAG_SHARED_SKETCH */
    public static final int f1286xef9e57d9 = 11;

    /* renamed from: SIP_TRANSPORT_FEATURE_TAG_STATS__FEATURE_TAG_NAME__IMS_FEATURE_TAG_STANDALONE_MSG */
    public static final int f1287xa97bd7b4 = 2;

    /* renamed from: SIP_TRANSPORT_FEATURE_TAG_STATS__FEATURE_TAG_NAME__IMS_FEATURE_TAG_UNSPECIFIED */
    public static final int f1288xa3b24eea = 0;

    /* renamed from: SIP_TRANSPORT_FEATURE_TAG_STATS__FEATURE_TAG_NAME__IMS_FEATURE_TAG_VIDEO */
    public static final int f1289x82b3666e = 19;
    public static final int SIP_TRANSPORT_SESSION = 10139;
    public static final int SIP_TRANSPORT_SESSION__SESSION_METHOD__SIP_REQUEST_ACK = 3;
    public static final int SIP_TRANSPORT_SESSION__SESSION_METHOD__SIP_REQUEST_BYE = 5;
    public static final int SIP_TRANSPORT_SESSION__SESSION_METHOD__SIP_REQUEST_CANCEL = 6;
    public static final int SIP_TRANSPORT_SESSION__SESSION_METHOD__SIP_REQUEST_CUSTOM = 1;
    public static final int SIP_TRANSPORT_SESSION__SESSION_METHOD__SIP_REQUEST_INFO = 12;
    public static final int SIP_TRANSPORT_SESSION__SESSION_METHOD__SIP_REQUEST_INVITE = 2;
    public static final int SIP_TRANSPORT_SESSION__SESSION_METHOD__SIP_REQUEST_MESSAGE = 14;
    public static final int SIP_TRANSPORT_SESSION__SESSION_METHOD__SIP_REQUEST_NOTIFY = 10;
    public static final int SIP_TRANSPORT_SESSION__SESSION_METHOD__SIP_REQUEST_OPTIONS = 4;
    public static final int SIP_TRANSPORT_SESSION__SESSION_METHOD__SIP_REQUEST_PRACK = 8;
    public static final int SIP_TRANSPORT_SESSION__SESSION_METHOD__SIP_REQUEST_PUBLISH = 11;
    public static final int SIP_TRANSPORT_SESSION__SESSION_METHOD__SIP_REQUEST_REFER = 13;
    public static final int SIP_TRANSPORT_SESSION__SESSION_METHOD__SIP_REQUEST_REGISTER = 7;
    public static final int SIP_TRANSPORT_SESSION__SESSION_METHOD__SIP_REQUEST_SUBSCRIBE = 9;
    public static final int SIP_TRANSPORT_SESSION__SESSION_METHOD__SIP_REQUEST_UNSPECIFIED = 0;
    public static final int SIP_TRANSPORT_SESSION__SESSION_METHOD__SIP_REQUEST_UPDATE = 15;
    public static final int SIP_TRANSPORT_SESSION__SIP_MESSAGE_DIRECTION__INCOMING = 1;
    public static final int SIP_TRANSPORT_SESSION__SIP_MESSAGE_DIRECTION__OUTGOING = 2;
    public static final int SIP_TRANSPORT_SESSION__SIP_MESSAGE_DIRECTION__UNKNOWN = 0;
    public static final int SUPPORTED_RADIO_ACCESS_FAMILY = 10079;
    public static final int TELEPHONY_ANOMALY_DETECTED = 461;
    public static final int TELEPHONY_NETWORK_REQUESTS = 10115;
    public static final int TELEPHONY_NETWORK_REQUESTS_V2 = 10153;
    public static final int TELEPHONY_NETWORK_REQUESTS_V2__CAPABILITY__CBS = 3;
    public static final int TELEPHONY_NETWORK_REQUESTS_V2__CAPABILITY__ENTERPRISE = 4;
    public static final int TELEPHONY_NETWORK_REQUESTS_V2__CAPABILITY__PRIORITIZE_BANDWIDTH = 2;
    public static final int TELEPHONY_NETWORK_REQUESTS_V2__CAPABILITY__PRIORITIZE_LATENCY = 1;
    public static final int TELEPHONY_NETWORK_REQUESTS_V2__CAPABILITY__UNKNOWN = 0;
    public static final int UCE_EVENT_STATS = 10143;
    public static final int UCE_EVENT_STATS__COMMAND_CODE__FETCH_ERROR = 3;
    public static final int UCE_EVENT_STATS__COMMAND_CODE__GENERIC_FAILURE = 1;
    public static final int UCE_EVENT_STATS__COMMAND_CODE__INSUFFICIENT_MEMORY = 5;
    public static final int UCE_EVENT_STATS__COMMAND_CODE__INVALID_PARAM = 2;
    public static final int UCE_EVENT_STATS__COMMAND_CODE__LOST_NETWORK_CONNECTION = 6;
    public static final int UCE_EVENT_STATS__COMMAND_CODE__NOT_FOUND = 8;
    public static final int UCE_EVENT_STATS__COMMAND_CODE__NOT_SUPPORTED = 7;
    public static final int UCE_EVENT_STATS__COMMAND_CODE__NO_CHANGE = 10;
    public static final int UCE_EVENT_STATS__COMMAND_CODE__REQUEST_TIMEOUT = 4;
    public static final int UCE_EVENT_STATS__COMMAND_CODE__SERVICE_UNAVAILABLE = 9;
    public static final int UCE_EVENT_STATS__COMMAND_CODE__SERVICE_UNKNOWN = 0;
    public static final int UCE_EVENT_STATS__TYPE__INCOMING_OPTION = 3;
    public static final int UCE_EVENT_STATS__TYPE__MESSAGE_TYPE_UNSPECIFIED = 0;
    public static final int UCE_EVENT_STATS__TYPE__OUTGOING_OPTION = 4;
    public static final int UCE_EVENT_STATS__TYPE__PUBLISH = 1;
    public static final int UCE_EVENT_STATS__TYPE__SUBSCRIBE = 2;
    public static final int VOICE_CALL_RAT_USAGE = 10077;
    public static final int VOICE_CALL_RAT_USAGE__RAT__NETWORK_TYPE_1XRTT = 7;
    public static final int VOICE_CALL_RAT_USAGE__RAT__NETWORK_TYPE_CDMA = 4;
    public static final int VOICE_CALL_RAT_USAGE__RAT__NETWORK_TYPE_EDGE = 2;
    public static final int VOICE_CALL_RAT_USAGE__RAT__NETWORK_TYPE_EHRPD = 14;
    public static final int VOICE_CALL_RAT_USAGE__RAT__NETWORK_TYPE_EVDO_0 = 5;
    public static final int VOICE_CALL_RAT_USAGE__RAT__NETWORK_TYPE_EVDO_A = 6;
    public static final int VOICE_CALL_RAT_USAGE__RAT__NETWORK_TYPE_EVDO_B = 12;
    public static final int VOICE_CALL_RAT_USAGE__RAT__NETWORK_TYPE_GPRS = 1;
    public static final int VOICE_CALL_RAT_USAGE__RAT__NETWORK_TYPE_GSM = 16;
    public static final int VOICE_CALL_RAT_USAGE__RAT__NETWORK_TYPE_HSDPA = 8;
    public static final int VOICE_CALL_RAT_USAGE__RAT__NETWORK_TYPE_HSPA = 10;
    public static final int VOICE_CALL_RAT_USAGE__RAT__NETWORK_TYPE_HSPAP = 15;
    public static final int VOICE_CALL_RAT_USAGE__RAT__NETWORK_TYPE_HSUPA = 9;
    public static final int VOICE_CALL_RAT_USAGE__RAT__NETWORK_TYPE_IDEN = 11;
    public static final int VOICE_CALL_RAT_USAGE__RAT__NETWORK_TYPE_IWLAN = 18;
    public static final int VOICE_CALL_RAT_USAGE__RAT__NETWORK_TYPE_LTE = 13;
    public static final int VOICE_CALL_RAT_USAGE__RAT__NETWORK_TYPE_LTE_CA = 19;
    public static final int VOICE_CALL_RAT_USAGE__RAT__NETWORK_TYPE_NR = 20;
    public static final int VOICE_CALL_RAT_USAGE__RAT__NETWORK_TYPE_TD_SCDMA = 17;
    public static final int VOICE_CALL_RAT_USAGE__RAT__NETWORK_TYPE_UMTS = 3;
    public static final int VOICE_CALL_RAT_USAGE__RAT__NETWORK_TYPE_UNKNOWN = 0;
    public static final int VOICE_CALL_SESSION = 10076;
    public static final int VOICE_CALL_SESSION__BEARER_AT_END__CALL_BEARER_CS = 1;
    public static final int VOICE_CALL_SESSION__BEARER_AT_END__CALL_BEARER_IMS = 2;
    public static final int VOICE_CALL_SESSION__BEARER_AT_END__CALL_BEARER_UNKNOWN = 0;
    public static final int VOICE_CALL_SESSION__BEARER_AT_START__CALL_BEARER_CS = 1;
    public static final int VOICE_CALL_SESSION__BEARER_AT_START__CALL_BEARER_IMS = 2;
    public static final int VOICE_CALL_SESSION__BEARER_AT_START__CALL_BEARER_UNKNOWN = 0;

    /* renamed from: VOICE_CALL_SESSION__CALL_DURATION__CALL_DURATION_LESS_THAN_FIVE_MINUTES */
    public static final int f1290xd26a6247 = 2;

    /* renamed from: VOICE_CALL_SESSION__CALL_DURATION__CALL_DURATION_LESS_THAN_ONE_HOUR */
    public static final int f1291x8c9c38d2 = 5;

    /* renamed from: VOICE_CALL_SESSION__CALL_DURATION__CALL_DURATION_LESS_THAN_ONE_MINUTE */
    public static final int f1292xdea1e182 = 1;

    /* renamed from: VOICE_CALL_SESSION__CALL_DURATION__CALL_DURATION_LESS_THAN_TEN_MINUTES */
    public static final int f1293x7de4b9c8 = 3;

    /* renamed from: VOICE_CALL_SESSION__CALL_DURATION__CALL_DURATION_LESS_THAN_THIRTY_MINUTES */
    public static final int f1294x904a3037 = 4;

    /* renamed from: VOICE_CALL_SESSION__CALL_DURATION__CALL_DURATION_MORE_THAN_ONE_HOUR */
    public static final int f1295xac3f198e = 6;
    public static final int VOICE_CALL_SESSION__CALL_DURATION__CALL_DURATION_UNKNOWN = 0;
    public static final int VOICE_CALL_SESSION__DIRECTION__CALL_DIRECTION_MO = 1;
    public static final int VOICE_CALL_SESSION__DIRECTION__CALL_DIRECTION_MT = 2;
    public static final int VOICE_CALL_SESSION__DIRECTION__CALL_DIRECTION_UNKNOWN = 0;
    public static final int VOICE_CALL_SESSION__FOLD_STATE__STATE_CLOSED = 1;
    public static final int VOICE_CALL_SESSION__FOLD_STATE__STATE_FLIPPED = 4;
    public static final int VOICE_CALL_SESSION__FOLD_STATE__STATE_HALF_OPENED = 2;
    public static final int VOICE_CALL_SESSION__FOLD_STATE__STATE_OPENED = 3;
    public static final int VOICE_CALL_SESSION__FOLD_STATE__STATE_UNKNOWN = 0;
    public static final int VOICE_CALL_SESSION__LAST_KNOWN_RAT__NETWORK_TYPE_1XRTT = 7;
    public static final int VOICE_CALL_SESSION__LAST_KNOWN_RAT__NETWORK_TYPE_CDMA = 4;
    public static final int VOICE_CALL_SESSION__LAST_KNOWN_RAT__NETWORK_TYPE_EDGE = 2;
    public static final int VOICE_CALL_SESSION__LAST_KNOWN_RAT__NETWORK_TYPE_EHRPD = 14;
    public static final int VOICE_CALL_SESSION__LAST_KNOWN_RAT__NETWORK_TYPE_EVDO_0 = 5;
    public static final int VOICE_CALL_SESSION__LAST_KNOWN_RAT__NETWORK_TYPE_EVDO_A = 6;
    public static final int VOICE_CALL_SESSION__LAST_KNOWN_RAT__NETWORK_TYPE_EVDO_B = 12;
    public static final int VOICE_CALL_SESSION__LAST_KNOWN_RAT__NETWORK_TYPE_GPRS = 1;
    public static final int VOICE_CALL_SESSION__LAST_KNOWN_RAT__NETWORK_TYPE_GSM = 16;
    public static final int VOICE_CALL_SESSION__LAST_KNOWN_RAT__NETWORK_TYPE_HSDPA = 8;
    public static final int VOICE_CALL_SESSION__LAST_KNOWN_RAT__NETWORK_TYPE_HSPA = 10;
    public static final int VOICE_CALL_SESSION__LAST_KNOWN_RAT__NETWORK_TYPE_HSPAP = 15;
    public static final int VOICE_CALL_SESSION__LAST_KNOWN_RAT__NETWORK_TYPE_HSUPA = 9;
    public static final int VOICE_CALL_SESSION__LAST_KNOWN_RAT__NETWORK_TYPE_IDEN = 11;
    public static final int VOICE_CALL_SESSION__LAST_KNOWN_RAT__NETWORK_TYPE_IWLAN = 18;
    public static final int VOICE_CALL_SESSION__LAST_KNOWN_RAT__NETWORK_TYPE_LTE = 13;
    public static final int VOICE_CALL_SESSION__LAST_KNOWN_RAT__NETWORK_TYPE_LTE_CA = 19;
    public static final int VOICE_CALL_SESSION__LAST_KNOWN_RAT__NETWORK_TYPE_NR = 20;
    public static final int VOICE_CALL_SESSION__LAST_KNOWN_RAT__NETWORK_TYPE_TD_SCDMA = 17;
    public static final int VOICE_CALL_SESSION__LAST_KNOWN_RAT__NETWORK_TYPE_UMTS = 3;
    public static final int VOICE_CALL_SESSION__LAST_KNOWN_RAT__NETWORK_TYPE_UNKNOWN = 0;
    public static final int VOICE_CALL_SESSION__MAIN_CODEC_QUALITY__CODEC_QUALITY_FULLBAND = 4;
    public static final int VOICE_CALL_SESSION__MAIN_CODEC_QUALITY__CODEC_QUALITY_NARROWBAND = 1;

    /* renamed from: VOICE_CALL_SESSION__MAIN_CODEC_QUALITY__CODEC_QUALITY_SUPER_WIDEBAND */
    public static final int f1296xb33ff227 = 3;
    public static final int VOICE_CALL_SESSION__MAIN_CODEC_QUALITY__CODEC_QUALITY_UNKNOWN = 0;
    public static final int VOICE_CALL_SESSION__MAIN_CODEC_QUALITY__CODEC_QUALITY_WIDEBAND = 2;
    public static final int VOICE_CALL_SESSION__RAT_AT_CONNECTED__NETWORK_TYPE_1XRTT = 7;
    public static final int VOICE_CALL_SESSION__RAT_AT_CONNECTED__NETWORK_TYPE_CDMA = 4;
    public static final int VOICE_CALL_SESSION__RAT_AT_CONNECTED__NETWORK_TYPE_EDGE = 2;
    public static final int VOICE_CALL_SESSION__RAT_AT_CONNECTED__NETWORK_TYPE_EHRPD = 14;
    public static final int VOICE_CALL_SESSION__RAT_AT_CONNECTED__NETWORK_TYPE_EVDO_0 = 5;
    public static final int VOICE_CALL_SESSION__RAT_AT_CONNECTED__NETWORK_TYPE_EVDO_A = 6;
    public static final int VOICE_CALL_SESSION__RAT_AT_CONNECTED__NETWORK_TYPE_EVDO_B = 12;
    public static final int VOICE_CALL_SESSION__RAT_AT_CONNECTED__NETWORK_TYPE_GPRS = 1;
    public static final int VOICE_CALL_SESSION__RAT_AT_CONNECTED__NETWORK_TYPE_GSM = 16;
    public static final int VOICE_CALL_SESSION__RAT_AT_CONNECTED__NETWORK_TYPE_HSDPA = 8;
    public static final int VOICE_CALL_SESSION__RAT_AT_CONNECTED__NETWORK_TYPE_HSPA = 10;
    public static final int VOICE_CALL_SESSION__RAT_AT_CONNECTED__NETWORK_TYPE_HSPAP = 15;
    public static final int VOICE_CALL_SESSION__RAT_AT_CONNECTED__NETWORK_TYPE_HSUPA = 9;
    public static final int VOICE_CALL_SESSION__RAT_AT_CONNECTED__NETWORK_TYPE_IDEN = 11;
    public static final int VOICE_CALL_SESSION__RAT_AT_CONNECTED__NETWORK_TYPE_IWLAN = 18;
    public static final int VOICE_CALL_SESSION__RAT_AT_CONNECTED__NETWORK_TYPE_LTE = 13;
    public static final int VOICE_CALL_SESSION__RAT_AT_CONNECTED__NETWORK_TYPE_LTE_CA = 19;
    public static final int VOICE_CALL_SESSION__RAT_AT_CONNECTED__NETWORK_TYPE_NR = 20;
    public static final int VOICE_CALL_SESSION__RAT_AT_CONNECTED__NETWORK_TYPE_TD_SCDMA = 17;
    public static final int VOICE_CALL_SESSION__RAT_AT_CONNECTED__NETWORK_TYPE_UMTS = 3;
    public static final int VOICE_CALL_SESSION__RAT_AT_CONNECTED__NETWORK_TYPE_UNKNOWN = 0;
    public static final int VOICE_CALL_SESSION__RAT_AT_END__NETWORK_TYPE_1XRTT = 7;
    public static final int VOICE_CALL_SESSION__RAT_AT_END__NETWORK_TYPE_CDMA = 4;
    public static final int VOICE_CALL_SESSION__RAT_AT_END__NETWORK_TYPE_EDGE = 2;
    public static final int VOICE_CALL_SESSION__RAT_AT_END__NETWORK_TYPE_EHRPD = 14;
    public static final int VOICE_CALL_SESSION__RAT_AT_END__NETWORK_TYPE_EVDO_0 = 5;
    public static final int VOICE_CALL_SESSION__RAT_AT_END__NETWORK_TYPE_EVDO_A = 6;
    public static final int VOICE_CALL_SESSION__RAT_AT_END__NETWORK_TYPE_EVDO_B = 12;
    public static final int VOICE_CALL_SESSION__RAT_AT_END__NETWORK_TYPE_GPRS = 1;
    public static final int VOICE_CALL_SESSION__RAT_AT_END__NETWORK_TYPE_GSM = 16;
    public static final int VOICE_CALL_SESSION__RAT_AT_END__NETWORK_TYPE_HSDPA = 8;
    public static final int VOICE_CALL_SESSION__RAT_AT_END__NETWORK_TYPE_HSPA = 10;
    public static final int VOICE_CALL_SESSION__RAT_AT_END__NETWORK_TYPE_HSPAP = 15;
    public static final int VOICE_CALL_SESSION__RAT_AT_END__NETWORK_TYPE_HSUPA = 9;
    public static final int VOICE_CALL_SESSION__RAT_AT_END__NETWORK_TYPE_IDEN = 11;
    public static final int VOICE_CALL_SESSION__RAT_AT_END__NETWORK_TYPE_IWLAN = 18;
    public static final int VOICE_CALL_SESSION__RAT_AT_END__NETWORK_TYPE_LTE = 13;
    public static final int VOICE_CALL_SESSION__RAT_AT_END__NETWORK_TYPE_LTE_CA = 19;
    public static final int VOICE_CALL_SESSION__RAT_AT_END__NETWORK_TYPE_NR = 20;
    public static final int VOICE_CALL_SESSION__RAT_AT_END__NETWORK_TYPE_TD_SCDMA = 17;
    public static final int VOICE_CALL_SESSION__RAT_AT_END__NETWORK_TYPE_UMTS = 3;
    public static final int VOICE_CALL_SESSION__RAT_AT_END__NETWORK_TYPE_UNKNOWN = 0;
    public static final int VOICE_CALL_SESSION__RAT_AT_START__NETWORK_TYPE_1XRTT = 7;
    public static final int VOICE_CALL_SESSION__RAT_AT_START__NETWORK_TYPE_CDMA = 4;
    public static final int VOICE_CALL_SESSION__RAT_AT_START__NETWORK_TYPE_EDGE = 2;
    public static final int VOICE_CALL_SESSION__RAT_AT_START__NETWORK_TYPE_EHRPD = 14;
    public static final int VOICE_CALL_SESSION__RAT_AT_START__NETWORK_TYPE_EVDO_0 = 5;
    public static final int VOICE_CALL_SESSION__RAT_AT_START__NETWORK_TYPE_EVDO_A = 6;
    public static final int VOICE_CALL_SESSION__RAT_AT_START__NETWORK_TYPE_EVDO_B = 12;
    public static final int VOICE_CALL_SESSION__RAT_AT_START__NETWORK_TYPE_GPRS = 1;
    public static final int VOICE_CALL_SESSION__RAT_AT_START__NETWORK_TYPE_GSM = 16;
    public static final int VOICE_CALL_SESSION__RAT_AT_START__NETWORK_TYPE_HSDPA = 8;
    public static final int VOICE_CALL_SESSION__RAT_AT_START__NETWORK_TYPE_HSPA = 10;
    public static final int VOICE_CALL_SESSION__RAT_AT_START__NETWORK_TYPE_HSPAP = 15;
    public static final int VOICE_CALL_SESSION__RAT_AT_START__NETWORK_TYPE_HSUPA = 9;
    public static final int VOICE_CALL_SESSION__RAT_AT_START__NETWORK_TYPE_IDEN = 11;
    public static final int VOICE_CALL_SESSION__RAT_AT_START__NETWORK_TYPE_IWLAN = 18;
    public static final int VOICE_CALL_SESSION__RAT_AT_START__NETWORK_TYPE_LTE = 13;
    public static final int VOICE_CALL_SESSION__RAT_AT_START__NETWORK_TYPE_LTE_CA = 19;
    public static final int VOICE_CALL_SESSION__RAT_AT_START__NETWORK_TYPE_NR = 20;
    public static final int VOICE_CALL_SESSION__RAT_AT_START__NETWORK_TYPE_TD_SCDMA = 17;
    public static final int VOICE_CALL_SESSION__RAT_AT_START__NETWORK_TYPE_UMTS = 3;
    public static final int VOICE_CALL_SESSION__RAT_AT_START__NETWORK_TYPE_UNKNOWN = 0;

    /* renamed from: VOICE_CALL_SESSION__SETUP_DURATION__CALL_SETUP_DURATION_EXTREMELY_FAST */
    public static final int f1297x4d1bd662 = 1;

    /* renamed from: VOICE_CALL_SESSION__SETUP_DURATION__CALL_SETUP_DURATION_EXTREMELY_SLOW */
    public static final int f1298x4d21e807 = 9;
    public static final int VOICE_CALL_SESSION__SETUP_DURATION__CALL_SETUP_DURATION_FAST = 4;
    public static final int VOICE_CALL_SESSION__SETUP_DURATION__CALL_SETUP_DURATION_NORMAL = 5;
    public static final int VOICE_CALL_SESSION__SETUP_DURATION__CALL_SETUP_DURATION_SLOW = 6;

    /* renamed from: VOICE_CALL_SESSION__SETUP_DURATION__CALL_SETUP_DURATION_ULTRA_FAST */
    public static final int f1299x585765cf = 2;

    /* renamed from: VOICE_CALL_SESSION__SETUP_DURATION__CALL_SETUP_DURATION_ULTRA_SLOW */
    public static final int f1300x585d7774 = 8;
    public static final int VOICE_CALL_SESSION__SETUP_DURATION__CALL_SETUP_DURATION_UNKNOWN = 0;

    /* renamed from: VOICE_CALL_SESSION__SETUP_DURATION__CALL_SETUP_DURATION_VERY_FAST */
    public static final int f1301xdf957d25 = 3;

    /* renamed from: VOICE_CALL_SESSION__SETUP_DURATION__CALL_SETUP_DURATION_VERY_SLOW */
    public static final int f1302xdf9b8eca = 7;
    public static final int VOICE_CALL_SESSION__SIGNAL_STRENGTH_AT_END__SIGNAL_STRENGTH_GOOD = 3;

    /* renamed from: VOICE_CALL_SESSION__SIGNAL_STRENGTH_AT_END__SIGNAL_STRENGTH_GREAT */
    public static final int f1303x793962e = 4;

    /* renamed from: VOICE_CALL_SESSION__SIGNAL_STRENGTH_AT_END__SIGNAL_STRENGTH_MODERATE */
    public static final int f1304x7ee989a2 = 2;

    /* renamed from: VOICE_CALL_SESSION__SIGNAL_STRENGTH_AT_END__SIGNAL_STRENGTH_NONE_OR_UNKNOWN */
    public static final int f1305xeb3a93b6 = 0;
    public static final int VOICE_CALL_SESSION__SIGNAL_STRENGTH_AT_END__SIGNAL_STRENGTH_POOR = 1;

    public static void write(int code, boolean arg1, boolean arg2, int arg3) {
        StatsEvent.Builder builder = StatsEvent.newBuilder();
        builder.setAtomId(code);
        builder.writeBoolean(arg1);
        builder.writeBoolean(arg2);
        builder.writeInt(arg3);
        builder.usePooledBuffer();
        StatsLog.write(builder.build());
    }

    public static void write(int code, int arg1) {
        StatsEvent.Builder builder = StatsEvent.newBuilder();
        builder.setAtomId(code);
        builder.writeInt(arg1);
        builder.usePooledBuffer();
        StatsLog.write(builder.build());
    }

    public static void write(int code, int arg1, int arg2) {
        StatsEvent.Builder builder = StatsEvent.newBuilder();
        builder.setAtomId(code);
        builder.writeInt(arg1);
        builder.writeInt(arg2);
        builder.usePooledBuffer();
        StatsLog.write(builder.build());
    }

    public static void write(int code, int arg1, int arg2, int arg3) {
        StatsEvent.Builder builder = StatsEvent.newBuilder();
        builder.setAtomId(code);
        builder.writeInt(arg1);
        builder.writeInt(arg2);
        builder.writeInt(arg3);
        builder.usePooledBuffer();
        StatsLog.write(builder.build());
    }

    public static void write(int code, int arg1, int arg2, int arg3, int arg4) {
        StatsEvent.Builder builder = StatsEvent.newBuilder();
        builder.setAtomId(code);
        builder.writeInt(arg1);
        builder.writeInt(arg2);
        builder.writeInt(arg3);
        builder.writeInt(arg4);
        builder.usePooledBuffer();
        StatsLog.write(builder.build());
    }

    public static void write(int code, int arg1, int arg2, int arg3, int arg4, boolean arg5, boolean arg6, int arg7, boolean arg8, int arg9, int arg10, int arg11, int arg12, int arg13, boolean arg14, int arg15, int arg16) {
        StatsEvent.Builder builder = StatsEvent.newBuilder();
        builder.setAtomId(code);
        builder.writeInt(arg1);
        builder.writeInt(arg2);
        builder.writeInt(arg3);
        builder.writeInt(arg4);
        builder.writeBoolean(arg5);
        builder.writeBoolean(arg6);
        builder.writeInt(arg7);
        builder.writeBoolean(arg8);
        builder.writeInt(arg9);
        builder.writeInt(arg10);
        builder.writeInt(arg11);
        builder.writeInt(arg12);
        builder.writeInt(arg13);
        builder.writeBoolean(arg14);
        builder.writeInt(arg15);
        builder.writeInt(arg16);
        builder.usePooledBuffer();
        StatsLog.write(builder.build());
    }

    public static void write(int code, int arg1, int arg2, int arg3, long arg4, boolean arg5) {
        StatsEvent.Builder builder = StatsEvent.newBuilder();
        builder.setAtomId(code);
        builder.writeInt(arg1);
        builder.writeInt(arg2);
        builder.writeInt(arg3);
        builder.writeLong(arg4);
        builder.writeBoolean(arg5);
        builder.usePooledBuffer();
        StatsLog.write(builder.build());
    }

    public static void write(int code, int arg1, int arg2, String arg3) {
        StatsEvent.Builder builder = StatsEvent.newBuilder();
        builder.setAtomId(code);
        builder.writeInt(arg1);
        builder.writeInt(arg2);
        builder.writeString(arg3);
        builder.usePooledBuffer();
        StatsLog.write(builder.build());
    }

    public static void write(int code, int arg1, long arg2, long arg3) {
        StatsEvent.Builder builder = StatsEvent.newBuilder();
        builder.setAtomId(code);
        builder.writeInt(arg1);
        builder.writeLong(arg2);
        builder.writeLong(arg3);
        builder.usePooledBuffer();
        StatsLog.write(builder.build());
    }

    public static void write(int code, int arg1, String arg2, String arg3, String arg4, String arg5) {
        StatsEvent.Builder builder = StatsEvent.newBuilder();
        builder.setAtomId(code);
        builder.writeInt(arg1);
        builder.writeString(arg2);
        builder.writeString(arg3);
        builder.writeString(arg4);
        builder.writeString(arg5);
        builder.usePooledBuffer();
        StatsLog.write(builder.build());
    }

    public static void write(int code, String arg1, String arg2, int arg3) {
        StatsEvent.Builder builder = StatsEvent.newBuilder();
        builder.setAtomId(code);
        builder.writeString(arg1);
        builder.writeString(arg2);
        builder.writeInt(arg3);
        builder.usePooledBuffer();
        StatsLog.write(builder.build());
    }

    public static StatsEvent buildStatsEvent(int code, boolean arg1, boolean arg2, int arg3, boolean arg4) {
        StatsEvent.Builder builder = StatsEvent.newBuilder();
        builder.setAtomId(code);
        builder.writeBoolean(arg1);
        builder.writeBoolean(arg2);
        builder.writeInt(arg3);
        builder.writeBoolean(arg4);
        return builder.build();
    }

    public static StatsEvent buildStatsEvent(int code, boolean arg1, int arg2, boolean arg3, int arg4, int arg5, boolean arg6, int arg7, boolean arg8) {
        StatsEvent.Builder builder = StatsEvent.newBuilder();
        builder.setAtomId(code);
        builder.writeBoolean(arg1);
        builder.writeInt(arg2);
        builder.writeBoolean(arg3);
        builder.writeInt(arg4);
        builder.writeInt(arg5);
        builder.writeBoolean(arg6);
        builder.writeInt(arg7);
        builder.writeBoolean(arg8);
        return builder.build();
    }

    public static StatsEvent buildStatsEvent(int code, boolean arg1, int arg2, int arg3, String arg4, String arg5, String arg6, int arg7, String[] arg8, int[] arg9, int[] arg10) {
        StatsEvent.Builder builder = StatsEvent.newBuilder();
        builder.setAtomId(code);
        builder.writeBoolean(arg1);
        builder.writeInt(arg2);
        builder.writeInt(arg3);
        builder.writeString(arg4);
        builder.writeString(arg5);
        builder.writeString(arg6);
        builder.writeInt(arg7);
        builder.writeStringArray(arg8 == null ? new String[0] : arg8);
        builder.writeIntArray(arg9 == null ? new int[0] : arg9);
        builder.writeIntArray(arg10 == null ? new int[0] : arg10);
        return builder.build();
    }

    public static StatsEvent buildStatsEvent(int code, int arg1) {
        StatsEvent.Builder builder = StatsEvent.newBuilder();
        builder.setAtomId(code);
        builder.writeInt(arg1);
        return builder.build();
    }

    public static StatsEvent buildStatsEvent(int code, int arg1, boolean arg2, boolean arg3, int arg4, int arg5, int arg6, boolean arg7, int arg8, boolean arg9, long arg10, boolean arg11, int arg12, boolean arg13, int arg14, int arg15, int arg16, long arg17, boolean arg18, int arg19, int[] arg20, int[] arg21, boolean arg22, boolean arg23) {
        StatsEvent.Builder builder = StatsEvent.newBuilder();
        builder.setAtomId(code);
        builder.writeInt(arg1);
        builder.writeBoolean(arg2);
        builder.writeBoolean(arg3);
        builder.writeInt(arg4);
        builder.writeInt(arg5);
        builder.writeInt(arg6);
        builder.writeBoolean(arg7);
        builder.writeInt(arg8);
        builder.writeBoolean(arg9);
        builder.writeLong(arg10);
        builder.writeBoolean(arg11);
        builder.writeInt(arg12);
        builder.writeBoolean(arg13);
        builder.writeInt(arg14);
        builder.writeInt(arg15);
        builder.writeInt(arg16);
        builder.writeLong(arg17);
        builder.writeBoolean(arg18);
        builder.writeInt(arg19);
        builder.writeIntArray(arg20 == null ? new int[0] : arg20);
        builder.writeIntArray(arg21 == null ? new int[0] : arg21);
        builder.writeBoolean(arg22);
        builder.writeBoolean(arg23);
        return builder.build();
    }

    public static StatsEvent buildStatsEvent(int code, int arg1, boolean arg2, int arg3, boolean arg4, int arg5, int arg6, String arg7, int arg8) {
        StatsEvent.Builder builder = StatsEvent.newBuilder();
        builder.setAtomId(code);
        builder.writeInt(arg1);
        builder.writeBoolean(arg2);
        builder.writeInt(arg3);
        builder.writeBoolean(arg4);
        builder.writeInt(arg5);
        builder.writeInt(arg6);
        builder.writeString(arg7);
        builder.writeInt(arg8);
        return builder.build();
    }

    public static StatsEvent buildStatsEvent(int code, int arg1, int arg2, boolean arg3, boolean arg4) {
        StatsEvent.Builder builder = StatsEvent.newBuilder();
        builder.setAtomId(code);
        builder.writeInt(arg1);
        builder.writeInt(arg2);
        builder.writeBoolean(arg3);
        builder.writeBoolean(arg4);
        return builder.build();
    }

    public static StatsEvent buildStatsEvent(int code, int arg1, int arg2, boolean arg3, int arg4, int arg5) {
        StatsEvent.Builder builder = StatsEvent.newBuilder();
        builder.setAtomId(code);
        builder.writeInt(arg1);
        builder.writeInt(arg2);
        builder.writeBoolean(arg3);
        builder.writeInt(arg4);
        builder.writeInt(arg5);
        return builder.build();
    }

    public static StatsEvent buildStatsEvent(int code, int arg1, int arg2, int arg3) {
        StatsEvent.Builder builder = StatsEvent.newBuilder();
        builder.setAtomId(code);
        builder.writeInt(arg1);
        builder.writeInt(arg2);
        builder.writeInt(arg3);
        return builder.build();
    }

    public static StatsEvent buildStatsEvent(int code, int arg1, int arg2, int arg3, boolean arg4, int arg5, int arg6) {
        StatsEvent.Builder builder = StatsEvent.newBuilder();
        builder.setAtomId(code);
        builder.writeInt(arg1);
        builder.writeInt(arg2);
        builder.writeInt(arg3);
        builder.writeBoolean(arg4);
        builder.writeInt(arg5);
        builder.writeInt(arg6);
        return builder.build();
    }

    public static StatsEvent buildStatsEvent(int code, int arg1, int arg2, int arg3, boolean arg4, int arg5, int arg6, int arg7) {
        StatsEvent.Builder builder = StatsEvent.newBuilder();
        builder.setAtomId(code);
        builder.writeInt(arg1);
        builder.writeInt(arg2);
        builder.writeInt(arg3);
        builder.writeBoolean(arg4);
        builder.writeInt(arg5);
        builder.writeInt(arg6);
        builder.writeInt(arg7);
        return builder.build();
    }

    public static StatsEvent buildStatsEvent(int code, int arg1, int arg2, int arg3, boolean arg4, int arg5, int arg6, int arg7, int arg8) {
        StatsEvent.Builder builder = StatsEvent.newBuilder();
        builder.setAtomId(code);
        builder.writeInt(arg1);
        builder.writeInt(arg2);
        builder.writeInt(arg3);
        builder.writeBoolean(arg4);
        builder.writeInt(arg5);
        builder.writeInt(arg6);
        builder.writeInt(arg7);
        builder.writeInt(arg8);
        return builder.build();
    }

    public static StatsEvent buildStatsEvent(int code, int arg1, int arg2, int arg3, int arg4) {
        StatsEvent.Builder builder = StatsEvent.newBuilder();
        builder.setAtomId(code);
        builder.writeInt(arg1);
        builder.writeInt(arg2);
        builder.writeInt(arg3);
        builder.writeInt(arg4);
        return builder.build();
    }

    public static StatsEvent buildStatsEvent(int code, int arg1, int arg2, int arg3, int arg4, boolean arg5, int arg6) {
        StatsEvent.Builder builder = StatsEvent.newBuilder();
        builder.setAtomId(code);
        builder.writeInt(arg1);
        builder.writeInt(arg2);
        builder.writeInt(arg3);
        builder.writeInt(arg4);
        builder.writeBoolean(arg5);
        builder.writeInt(arg6);
        return builder.build();
    }

    public static StatsEvent buildStatsEvent(int code, int arg1, int arg2, int arg3, int arg4, boolean arg5, int arg6, boolean arg7, int arg8, int arg9, boolean arg10, boolean arg11, int arg12, boolean arg13) {
        StatsEvent.Builder builder = StatsEvent.newBuilder();
        builder.setAtomId(code);
        builder.writeInt(arg1);
        builder.writeInt(arg2);
        builder.writeInt(arg3);
        builder.writeInt(arg4);
        builder.writeBoolean(arg5);
        builder.writeInt(arg6);
        builder.writeBoolean(arg7);
        builder.writeInt(arg8);
        builder.writeInt(arg9);
        builder.writeBoolean(arg10);
        builder.writeBoolean(arg11);
        builder.writeInt(arg12);
        builder.writeBoolean(arg13);
        return builder.build();
    }

    public static StatsEvent buildStatsEvent(int code, int arg1, int arg2, int arg3, int arg4, boolean arg5, int arg6, int arg7) {
        StatsEvent.Builder builder = StatsEvent.newBuilder();
        builder.setAtomId(code);
        builder.writeInt(arg1);
        builder.writeInt(arg2);
        builder.writeInt(arg3);
        builder.writeInt(arg4);
        builder.writeBoolean(arg5);
        builder.writeInt(arg6);
        builder.writeInt(arg7);
        return builder.build();
    }

    public static StatsEvent buildStatsEvent(int code, int arg1, int arg2, int arg3, int arg4, boolean arg5, int arg6, int arg7, String arg8, int arg9, int arg10, long arg11, long arg12, int arg13, int arg14, int arg15, boolean arg16, boolean arg17, int arg18, boolean arg19, long arg20, long arg21, boolean arg22, boolean arg23, boolean arg24, int arg25, int arg26, int arg27, int arg28, int arg29, boolean arg30, int arg31, boolean arg32, int arg33, int arg34, int arg35, boolean arg36) {
        StatsEvent.Builder builder = StatsEvent.newBuilder();
        builder.setAtomId(code);
        builder.writeInt(arg1);
        builder.writeInt(arg2);
        builder.writeInt(arg3);
        builder.writeInt(arg4);
        builder.writeBoolean(arg5);
        builder.writeInt(arg6);
        builder.writeInt(arg7);
        builder.writeString(arg8);
        builder.writeInt(arg9);
        builder.writeInt(arg10);
        builder.writeLong(arg11);
        builder.writeLong(arg12);
        builder.writeInt(arg13);
        builder.writeInt(arg14);
        builder.writeInt(arg15);
        builder.writeBoolean(arg16);
        builder.writeBoolean(arg17);
        builder.writeInt(arg18);
        builder.writeBoolean(arg19);
        builder.writeLong(arg20);
        builder.writeLong(arg21);
        builder.writeBoolean(arg22);
        builder.writeBoolean(arg23);
        builder.writeBoolean(arg24);
        builder.writeInt(arg25);
        builder.writeInt(arg26);
        builder.writeInt(arg27);
        builder.writeInt(arg28);
        builder.writeInt(arg29);
        builder.writeBoolean(arg30);
        builder.writeInt(arg31);
        builder.writeBoolean(arg32);
        builder.writeInt(arg33);
        builder.writeInt(arg34);
        builder.writeInt(arg35);
        builder.writeBoolean(arg36);
        return builder.build();
    }

    public static StatsEvent buildStatsEvent(int code, int arg1, int arg2, int arg3, int arg4, int arg5) {
        StatsEvent.Builder builder = StatsEvent.newBuilder();
        builder.setAtomId(code);
        builder.writeInt(arg1);
        builder.writeInt(arg2);
        builder.writeInt(arg3);
        builder.writeInt(arg4);
        builder.writeInt(arg5);
        return builder.build();
    }

    public static StatsEvent buildStatsEvent(int code, int arg1, int arg2, int arg3, int arg4, int arg5, boolean arg6, boolean arg7, boolean arg8, int arg9) {
        StatsEvent.Builder builder = StatsEvent.newBuilder();
        builder.setAtomId(code);
        builder.writeInt(arg1);
        builder.writeInt(arg2);
        builder.writeInt(arg3);
        builder.writeInt(arg4);
        builder.writeInt(arg5);
        builder.writeBoolean(arg6);
        builder.writeBoolean(arg7);
        builder.writeBoolean(arg8);
        builder.writeInt(arg9);
        return builder.build();
    }

    public static StatsEvent buildStatsEvent(int code, int arg1, int arg2, int arg3, int arg4, int arg5, boolean arg6, boolean arg7, int arg8, boolean arg9, boolean arg10, int arg11, long arg12, int arg13, long arg14, int arg15, int arg16, int arg17, boolean arg18, boolean arg19, boolean arg20) {
        StatsEvent.Builder builder = StatsEvent.newBuilder();
        builder.setAtomId(code);
        builder.writeInt(arg1);
        builder.writeInt(arg2);
        builder.writeInt(arg3);
        builder.writeInt(arg4);
        builder.writeInt(arg5);
        builder.writeBoolean(arg6);
        builder.writeBoolean(arg7);
        builder.writeInt(arg8);
        builder.writeBoolean(arg9);
        builder.writeBoolean(arg10);
        builder.writeInt(arg11);
        builder.writeLong(arg12);
        builder.writeInt(arg13);
        builder.writeLong(arg14);
        builder.writeInt(arg15);
        builder.writeInt(arg16);
        builder.writeInt(arg17);
        builder.writeBoolean(arg18);
        builder.writeBoolean(arg19);
        builder.writeBoolean(arg20);
        return builder.build();
    }

    public static StatsEvent buildStatsEvent(int code, int arg1, int arg2, int arg3, int arg4, int arg5, boolean arg6, boolean arg7, int arg8, int arg9, boolean arg10, boolean arg11, long arg12, boolean arg13, boolean arg14, int arg15, int arg16, long arg17, boolean arg18) {
        StatsEvent.Builder builder = StatsEvent.newBuilder();
        builder.setAtomId(code);
        builder.writeInt(arg1);
        builder.writeInt(arg2);
        builder.writeInt(arg3);
        builder.writeInt(arg4);
        builder.writeInt(arg5);
        builder.writeBoolean(arg6);
        builder.writeBoolean(arg7);
        builder.writeInt(arg8);
        builder.writeInt(arg9);
        builder.writeBoolean(arg10);
        builder.writeBoolean(arg11);
        builder.writeLong(arg12);
        builder.writeBoolean(arg13);
        builder.writeBoolean(arg14);
        builder.writeInt(arg15);
        builder.writeInt(arg16);
        builder.writeLong(arg17);
        builder.writeBoolean(arg18);
        return builder.build();
    }

    public static StatsEvent buildStatsEvent(int code, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6) {
        StatsEvent.Builder builder = StatsEvent.newBuilder();
        builder.setAtomId(code);
        builder.writeInt(arg1);
        builder.writeInt(arg2);
        builder.writeInt(arg3);
        builder.writeInt(arg4);
        builder.writeInt(arg5);
        builder.writeInt(arg6);
        return builder.build();
    }

    public static StatsEvent buildStatsEvent(int code, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6, boolean arg7, int arg8, boolean arg9, int arg10, boolean arg11, boolean arg12, int arg13, long arg14, int arg15, boolean arg16, boolean arg17) {
        StatsEvent.Builder builder = StatsEvent.newBuilder();
        builder.setAtomId(code);
        builder.writeInt(arg1);
        builder.writeInt(arg2);
        builder.writeInt(arg3);
        builder.writeInt(arg4);
        builder.writeInt(arg5);
        builder.writeInt(arg6);
        builder.writeBoolean(arg7);
        builder.writeInt(arg8);
        builder.writeBoolean(arg9);
        builder.writeInt(arg10);
        builder.writeBoolean(arg11);
        builder.writeBoolean(arg12);
        builder.writeInt(arg13);
        builder.writeLong(arg14);
        builder.writeInt(arg15);
        builder.writeBoolean(arg16);
        builder.writeBoolean(arg17);
        return builder.build();
    }

    public static StatsEvent buildStatsEvent(int code, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6, int arg7) {
        StatsEvent.Builder builder = StatsEvent.newBuilder();
        builder.setAtomId(code);
        builder.writeInt(arg1);
        builder.writeInt(arg2);
        builder.writeInt(arg3);
        builder.writeInt(arg4);
        builder.writeInt(arg5);
        builder.writeInt(arg6);
        builder.writeInt(arg7);
        return builder.build();
    }

    public static StatsEvent buildStatsEvent(int code, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6, int arg7, int arg8, int arg9, int arg10, int arg11, int arg12) {
        StatsEvent.Builder builder = StatsEvent.newBuilder();
        builder.setAtomId(code);
        builder.writeInt(arg1);
        builder.writeInt(arg2);
        builder.writeInt(arg3);
        builder.writeInt(arg4);
        builder.writeInt(arg5);
        builder.writeInt(arg6);
        builder.writeInt(arg7);
        builder.writeInt(arg8);
        builder.writeInt(arg9);
        builder.writeInt(arg10);
        builder.writeInt(arg11);
        builder.writeInt(arg12);
        return builder.build();
    }

    public static StatsEvent buildStatsEvent(int code, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6, int arg7, int arg8, int arg9, int arg10, int arg11, int arg12, int arg13, int arg14, int arg15, int arg16, int arg17) {
        StatsEvent.Builder builder = StatsEvent.newBuilder();
        builder.setAtomId(code);
        builder.writeInt(arg1);
        builder.writeInt(arg2);
        builder.writeInt(arg3);
        builder.writeInt(arg4);
        builder.writeInt(arg5);
        builder.writeInt(arg6);
        builder.writeInt(arg7);
        builder.writeInt(arg8);
        builder.writeInt(arg9);
        builder.writeInt(arg10);
        builder.writeInt(arg11);
        builder.writeInt(arg12);
        builder.writeInt(arg13);
        builder.writeInt(arg14);
        builder.writeInt(arg15);
        builder.writeInt(arg16);
        builder.writeInt(arg17);
        return builder.build();
    }

    public static StatsEvent buildStatsEvent(int code, int arg1, int arg2, int arg3, long arg4) {
        StatsEvent.Builder builder = StatsEvent.newBuilder();
        builder.setAtomId(code);
        builder.writeInt(arg1);
        builder.writeInt(arg2);
        builder.writeInt(arg3);
        builder.writeLong(arg4);
        return builder.build();
    }

    public static StatsEvent buildStatsEvent(int code, int arg1, int arg2, int arg3, float arg4, int arg5, int arg6) {
        StatsEvent.Builder builder = StatsEvent.newBuilder();
        builder.setAtomId(code);
        builder.writeInt(arg1);
        builder.writeInt(arg2);
        builder.writeInt(arg3);
        builder.writeFloat(arg4);
        builder.writeInt(arg5);
        builder.writeInt(arg6);
        return builder.build();
    }

    public static StatsEvent buildStatsEvent(int code, int arg1, int arg2, long arg3) {
        StatsEvent.Builder builder = StatsEvent.newBuilder();
        builder.setAtomId(code);
        builder.writeInt(arg1);
        builder.writeInt(arg2);
        builder.writeLong(arg3);
        return builder.build();
    }

    public static StatsEvent buildStatsEvent(int code, int arg1, int arg2, long arg3, long arg4) {
        StatsEvent.Builder builder = StatsEvent.newBuilder();
        builder.setAtomId(code);
        builder.writeInt(arg1);
        builder.writeInt(arg2);
        builder.writeLong(arg3);
        builder.writeLong(arg4);
        return builder.build();
    }

    public static StatsEvent buildStatsEvent(int code, long arg1) {
        StatsEvent.Builder builder = StatsEvent.newBuilder();
        builder.setAtomId(code);
        builder.writeLong(arg1);
        return builder.build();
    }
}
