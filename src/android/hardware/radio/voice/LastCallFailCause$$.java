package android.hardware.radio.voice;

import android.database.sqlite.SQLiteDatabase;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.lang.reflect.Array;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public interface LastCallFailCause$$ {
    static String toString(int i) {
        if (i == 0) {
            return "INVALID";
        }
        if (i == 1) {
            return "UNOBTAINABLE_NUMBER";
        }
        if (i == 3) {
            return "NO_ROUTE_TO_DESTINATION";
        }
        if (i == 6) {
            return "CHANNEL_UNACCEPTABLE";
        }
        if (i == 8) {
            return "OPERATOR_DETERMINED_BARRING";
        }
        if (i == 16) {
            return SQLiteDatabase.SYNC_MODE_NORMAL;
        }
        if (i == 17) {
            return "BUSY";
        }
        if (i == 18) {
            return "NO_USER_RESPONDING";
        }
        if (i == 19) {
            return "NO_ANSWER_FROM_USER";
        }
        if (i == 21) {
            return "CALL_REJECTED";
        }
        if (i == 22) {
            return "NUMBER_CHANGED";
        }
        if (i == 25) {
            return "PREEMPTION";
        }
        if (i == 27) {
            return "DESTINATION_OUT_OF_ORDER";
        }
        if (i == 28) {
            return "INVALID_NUMBER_FORMAT";
        }
        if (i == 29) {
            return "FACILITY_REJECTED";
        }
        if (i == 30) {
            return "RESP_TO_STATUS_ENQUIRY";
        }
        if (i == 31) {
            return "NORMAL_UNSPECIFIED";
        }
        if (i == 34) {
            return "CONGESTION";
        }
        if (i == 38) {
            return "NETWORK_OUT_OF_ORDER";
        }
        if (i == 41) {
            return "TEMPORARY_FAILURE";
        }
        if (i == 42) {
            return "SWITCHING_EQUIPMENT_CONGESTION";
        }
        if (i == 43) {
            return "ACCESS_INFORMATION_DISCARDED";
        }
        if (i == 44) {
            return "REQUESTED_CIRCUIT_OR_CHANNEL_NOT_AVAILABLE";
        }
        if (i == 47) {
            return "RESOURCES_UNAVAILABLE_OR_UNSPECIFIED";
        }
        if (i == 49) {
            return "QOS_UNAVAILABLE";
        }
        if (i == 50) {
            return "REQUESTED_FACILITY_NOT_SUBSCRIBED";
        }
        if (i == 55) {
            return "INCOMING_CALLS_BARRED_WITHIN_CUG";
        }
        if (i == 57) {
            return "BEARER_CAPABILITY_NOT_AUTHORIZED";
        }
        if (i == 58) {
            return "BEARER_CAPABILITY_UNAVAILABLE";
        }
        if (i == 63) {
            return "SERVICE_OPTION_NOT_AVAILABLE";
        }
        if (i == 65) {
            return "BEARER_SERVICE_NOT_IMPLEMENTED";
        }
        if (i == 68) {
            return "ACM_LIMIT_EXCEEDED";
        }
        if (i == 69) {
            return "REQUESTED_FACILITY_NOT_IMPLEMENTED";
        }
        if (i == 70) {
            return "ONLY_DIGITAL_INFORMATION_BEARER_AVAILABLE";
        }
        if (i == 79) {
            return "SERVICE_OR_OPTION_NOT_IMPLEMENTED";
        }
        if (i == 81) {
            return "INVALID_TRANSACTION_IDENTIFIER";
        }
        if (i == 87) {
            return "USER_NOT_MEMBER_OF_CUG";
        }
        if (i == 88) {
            return "INCOMPATIBLE_DESTINATION";
        }
        if (i == 91) {
            return "INVALID_TRANSIT_NW_SELECTION";
        }
        if (i == 95) {
            return "SEMANTICALLY_INCORRECT_MESSAGE";
        }
        if (i == 96) {
            return "INVALID_MANDATORY_INFORMATION";
        }
        if (i == 97) {
            return "MESSAGE_TYPE_NON_IMPLEMENTED";
        }
        if (i == 98) {
            return "MESSAGE_TYPE_NOT_COMPATIBLE_WITH_PROTOCOL_STATE";
        }
        if (i == 99) {
            return "INFORMATION_ELEMENT_NON_EXISTENT";
        }
        if (i == 100) {
            return "CONDITIONAL_IE_ERROR";
        }
        if (i == 101) {
            return "MESSAGE_NOT_COMPATIBLE_WITH_PROTOCOL_STATE";
        }
        if (i == 102) {
            return "RECOVERY_ON_TIMER_EXPIRED";
        }
        if (i == 111) {
            return "PROTOCOL_ERROR_UNSPECIFIED";
        }
        if (i == 127) {
            return "INTERWORKING_UNSPECIFIED";
        }
        if (i == 240) {
            return "CALL_BARRED";
        }
        if (i == 241) {
            return "FDN_BLOCKED";
        }
        if (i == 242) {
            return "IMSI_UNKNOWN_IN_VLR";
        }
        if (i == 243) {
            return "IMEI_NOT_ACCEPTED";
        }
        if (i == 244) {
            return "DIAL_MODIFIED_TO_USSD";
        }
        if (i == 245) {
            return "DIAL_MODIFIED_TO_SS";
        }
        if (i == 246) {
            return "DIAL_MODIFIED_TO_DIAL";
        }
        if (i == 247) {
            return "RADIO_OFF";
        }
        if (i == 248) {
            return "OUT_OF_SERVICE";
        }
        if (i == 249) {
            return "NO_VALID_SIM";
        }
        if (i == 250) {
            return "RADIO_INTERNAL_ERROR";
        }
        if (i == 251) {
            return "NETWORK_RESP_TIMEOUT";
        }
        if (i == 252) {
            return "NETWORK_REJECT";
        }
        if (i == 253) {
            return "RADIO_ACCESS_FAILURE";
        }
        if (i == 254) {
            return "RADIO_LINK_FAILURE";
        }
        if (i == 255) {
            return "RADIO_LINK_LOST";
        }
        if (i == 256) {
            return "RADIO_UPLINK_FAILURE";
        }
        if (i == 257) {
            return "RADIO_SETUP_FAILURE";
        }
        if (i == 258) {
            return "RADIO_RELEASE_NORMAL";
        }
        if (i == 259) {
            return "RADIO_RELEASE_ABNORMAL";
        }
        if (i == 260) {
            return "ACCESS_CLASS_BLOCKED";
        }
        if (i == 261) {
            return "NETWORK_DETACH";
        }
        if (i == 1000) {
            return "CDMA_LOCKED_UNTIL_POWER_CYCLE";
        }
        if (i == 1001) {
            return "CDMA_DROP";
        }
        if (i == 1002) {
            return "CDMA_INTERCEPT";
        }
        if (i == 1003) {
            return "CDMA_REORDER";
        }
        if (i == 1004) {
            return "CDMA_SO_REJECT";
        }
        if (i == 1005) {
            return "CDMA_RETRY_ORDER";
        }
        if (i == 1006) {
            return "CDMA_ACCESS_FAILURE";
        }
        if (i == 1007) {
            return "CDMA_PREEMPTED";
        }
        if (i == 1008) {
            return "CDMA_NOT_EMERGENCY";
        }
        if (i == 1009) {
            return "CDMA_ACCESS_BLOCKED";
        }
        if (i == 61441) {
            return "OEM_CAUSE_1";
        }
        if (i == 61442) {
            return "OEM_CAUSE_2";
        }
        if (i == 61443) {
            return "OEM_CAUSE_3";
        }
        if (i == 61444) {
            return "OEM_CAUSE_4";
        }
        if (i == 61445) {
            return "OEM_CAUSE_5";
        }
        if (i == 61446) {
            return "OEM_CAUSE_6";
        }
        if (i == 61447) {
            return "OEM_CAUSE_7";
        }
        if (i == 61448) {
            return "OEM_CAUSE_8";
        }
        if (i == 61449) {
            return "OEM_CAUSE_9";
        }
        if (i == 61450) {
            return "OEM_CAUSE_10";
        }
        if (i == 61451) {
            return "OEM_CAUSE_11";
        }
        if (i == 61452) {
            return "OEM_CAUSE_12";
        }
        if (i == 61453) {
            return "OEM_CAUSE_13";
        }
        if (i == 61454) {
            return "OEM_CAUSE_14";
        }
        if (i == 61455) {
            return "OEM_CAUSE_15";
        }
        if (i == 65535) {
            return "ERROR_UNSPECIFIED";
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
