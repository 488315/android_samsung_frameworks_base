package android.hardware.radio.V1_0;

import android.security.keystore.KeyProperties;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class DataCallFailCause {
    public static final int ACTIVATION_REJECT_GGSN = 30;
    public static final int ACTIVATION_REJECT_UNSPECIFIED = 31;
    public static final int APN_TYPE_CONFLICT = 112;
    public static final int AUTH_FAILURE_ON_EMERGENCY_CALL = 122;
    public static final int COMPANION_IFACE_IN_USE = 118;
    public static final int CONDITIONAL_IE_ERROR = 100;
    public static final int DATA_REGISTRATION_FAIL = -2;
    public static final int EMERGENCY_IFACE_ONLY = 116;
    public static final int EMM_ACCESS_BARRED = 115;
    public static final int EMM_ACCESS_BARRED_INFINITE_RETRY = 121;
    public static final int ERROR_UNSPECIFIED = 65535;
    public static final int ESM_INFO_NOT_RECEIVED = 53;
    public static final int FEATURE_NOT_SUPP = 40;
    public static final int FILTER_SEMANTIC_ERROR = 44;
    public static final int FILTER_SYTAX_ERROR = 45;
    public static final int IFACE_AND_POL_FAMILY_MISMATCH = 120;
    public static final int IFACE_MISMATCH = 117;
    public static final int INSUFFICIENT_RESOURCES = 26;
    public static final int INTERNAL_CALL_PREEMPT_BY_HIGH_PRIO_APN = 114;
    public static final int INVALID_MANDATORY_INFO = 96;
    public static final int INVALID_PCSCF_ADDR = 113;
    public static final int INVALID_TRANSACTION_ID = 81;
    public static final int IP_ADDRESS_MISMATCH = 119;
    public static final int MAX_ACTIVE_PDP_CONTEXT_REACHED = 65;
    public static final int MESSAGE_INCORRECT_SEMANTIC = 95;
    public static final int MESSAGE_TYPE_UNSUPPORTED = 97;
    public static final int MISSING_UKNOWN_APN = 27;
    public static final int MSG_AND_PROTOCOL_STATE_UNCOMPATIBLE = 101;
    public static final int MSG_TYPE_NONCOMPATIBLE_STATE = 98;
    public static final int MULTI_CONN_TO_SAME_PDN_NOT_ALLOWED = 55;
    public static final int NAS_SIGNALLING = 14;
    public static final int NETWORK_FAILURE = 38;
    public static final int NONE = 0;
    public static final int NSAPI_IN_USE = 35;
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
    public static final int ONLY_IPV4_ALLOWED = 50;
    public static final int ONLY_IPV6_ALLOWED = 51;
    public static final int ONLY_SINGLE_BEARER_ALLOWED = 52;
    public static final int OPERATOR_BARRED = 8;
    public static final int PDN_CONN_DOES_NOT_EXIST = 54;
    public static final int PDP_WITHOUT_ACTIVE_TFT = 46;
    public static final int PREF_RADIO_TECH_CHANGED = -4;
    public static final int PROTOCOL_ERRORS = 111;
    public static final int QOS_NOT_ACCEPTED = 37;
    public static final int RADIO_POWER_OFF = -5;
    public static final int REGULAR_DEACTIVATION = 36;
    public static final int SERVICE_OPTION_NOT_SUBSCRIBED = 33;
    public static final int SERVICE_OPTION_NOT_SUPPORTED = 32;
    public static final int SERVICE_OPTION_OUT_OF_ORDER = 34;
    public static final int SIGNAL_LOST = -3;
    public static final int TETHERED_CALL_ACTIVE = -6;
    public static final int TFT_SEMANTIC_ERROR = 41;
    public static final int TFT_SYTAX_ERROR = 42;
    public static final int UMTS_REACTIVATION_REQ = 39;
    public static final int UNKNOWN_INFO_ELEMENT = 99;
    public static final int UNKNOWN_PDP_ADDRESS_TYPE = 28;
    public static final int UNKNOWN_PDP_CONTEXT = 43;
    public static final int UNSUPPORTED_APN_IN_CURRENT_PLMN = 66;
    public static final int USER_AUTHENTICATION = 29;
    public static final int VOICE_REGISTRATION_FAIL = -1;

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
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
