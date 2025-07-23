package android.hardware.radio.network;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.security.keystore.KeyProperties;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.lang.reflect.Array;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public interface RegistrationFailCause$$ {
    static String toString(int i) {
        if (i == 0) {
            return KeyProperties.DIGEST_NONE;
        }
        if (i == 2) {
            return "IMSI_UNKNOWN_IN_HLR";
        }
        if (i == 3) {
            return "ILLEGAL_MS";
        }
        if (i == 4) {
            return "IMSI_UNKNOWN_IN_VLR";
        }
        if (i == 5) {
            return "IMEI_NOT_ACCEPTED";
        }
        if (i == 6) {
            return "ILLEGAL_ME";
        }
        if (i == 7) {
            return "GPRS_SERVICES_NOT_ALLOWED";
        }
        if (i == 8) {
            return "GPRS_AND_NON_GPRS_SERVICES_NOT_ALLOWED";
        }
        if (i == 9) {
            return "MS_IDENTITY_CANNOT_BE_DERIVED_BY_NETWORK";
        }
        if (i == 10) {
            return "IMPLICITLY_DETACHED";
        }
        if (i == 11) {
            return "PLMN_NOT_ALLOWED";
        }
        if (i == 12) {
            return "LOCATION_AREA_NOT_ALLOWED";
        }
        if (i == 13) {
            return "ROAMING_NOT_ALLOWED";
        }
        if (i == 14) {
            return "GPRS_SERVICES_NOT_ALLOWED_IN_PLMN";
        }
        if (i == 15) {
            return "NO_SUITABLE_CELLS";
        }
        if (i == 15) {
            return "MSC_TEMPORARILY_NOT_REACHABLE";
        }
        if (i == 16) {
            return "MSC_TEMP_NOT_REACHABLE";
        }
        if (i == 17) {
            return "NETWORK_FAILURE";
        }
        if (i == 20) {
            return "MAC_FAILURE";
        }
        if (i == 21) {
            return "SYNC_FAILURE";
        }
        if (i == 22) {
            return "CONGESTION";
        }
        if (i == 23) {
            return "GSM_AUTHENTICATION_UNACCEPTABLE";
        }
        if (i == 25) {
            return "NOT_AUTHORIZED_FOR_THIS_CSG";
        }
        if (i == 26) {
            return "SMS_PROVIDED_BY_GPRS_IN_ROUTING_AREA";
        }
        if (i == 32) {
            return "SERVICE_OPTION_NOT_SUPPORTED";
        }
        if (i == 33) {
            return "SERVICE_OPTION_NOT_SUBSCRIBED";
        }
        if (i == 34) {
            return "SERVICE_OPTION_TEMPORARILY_OUT_OF_ORDER";
        }
        if (i == 38) {
            return "CALL_CANNOT_BE_IDENTIFIED";
        }
        if (i == 40) {
            return "NO_PDP_CONTEXT_ACTIVATED";
        }
        if (i == 48) {
            return "RETRY_UPON_ENTRY_INTO_NEW_CELL_1";
        }
        if (i == 49) {
            return "RETRY_UPON_ENTRY_INTO_NEW_CELL_2";
        }
        if (i == 50) {
            return "RETRY_UPON_ENTRY_INTO_NEW_CELL_3";
        }
        if (i == 51) {
            return "RETRY_UPON_ENTRY_INTO_NEW_CELL_4";
        }
        if (i == 52) {
            return "RETRY_UPON_ENTRY_INTO_NEW_CELL_5";
        }
        if (i == 53) {
            return "RETRY_UPON_ENTRY_INTO_NEW_CELL_6";
        }
        if (i == 54) {
            return "RETRY_UPON_ENTRY_INTO_NEW_CELL_7";
        }
        if (i == 55) {
            return "RETRY_UPON_ENTRY_INTO_NEW_CELL_8";
        }
        if (i == 56) {
            return "RETRY_UPON_ENTRY_INTO_NEW_CELL_9";
        }
        if (i == 57) {
            return "RETRY_UPON_ENTRY_INTO_NEW_CELL_10";
        }
        if (i == 58) {
            return "RETRY_UPON_ENTRY_INTO_NEW_CELL_11";
        }
        if (i == 59) {
            return "RETRY_UPON_ENTRY_INTO_NEW_CELL_12";
        }
        if (i == 60) {
            return "RETRY_UPON_ENTRY_INTO_NEW_CELL_13";
        }
        if (i == 61) {
            return "RETRY_UPON_ENTRY_INTO_NEW_CELL_14";
        }
        if (i == 62) {
            return "RETRY_UPON_ENTRY_INTO_NEW_CELL_15";
        }
        if (i == 63) {
            return "RETRY_UPON_ENTRY_INTO_NEW_CELL_16";
        }
        if (i == 95) {
            return "SEMANTICALLY_INCORRECT_MESSAGE";
        }
        if (i == 96) {
            return "INVALID_MANDATORY_INFORMATION";
        }
        if (i == 97) {
            return "MESSAGE_TYPE_NON_EXISTENT_OR_NOT_IMPLEMENTED";
        }
        if (i == 98) {
            return "MESSAGE_TYPE_NOT_COMPATIBLE_WITH_PROTOCOL_STATE";
        }
        if (i == 99) {
            return "INFORMATION_ELEMENT_NON_EXISTENT_OR_NOT_IMPLEMENTED";
        }
        if (i == 100) {
            return "CONDITIONAL_IE_ERROR";
        }
        if (i == 101) {
            return "MESSAGE_NOT_COMPATIBLE_WITH_PROTOCOL_STATE";
        }
        if (i == 111) {
            return "PROTOCOL_ERROR_UNSPECIFIED";
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
