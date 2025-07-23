package android.hardware.radio;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.security.keystore.KeyProperties;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.lang.reflect.Array;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public interface RadioError$$ {
    static String toString(int i) {
        if (i == 0) {
            return KeyProperties.DIGEST_NONE;
        }
        if (i == 1) {
            return "RADIO_NOT_AVAILABLE";
        }
        if (i == 2) {
            return "GENERIC_FAILURE";
        }
        if (i == 3) {
            return "PASSWORD_INCORRECT";
        }
        if (i == 4) {
            return "SIM_PIN2";
        }
        if (i == 5) {
            return "SIM_PUK2";
        }
        if (i == 6) {
            return "REQUEST_NOT_SUPPORTED";
        }
        if (i == 7) {
            return "CANCELLED";
        }
        if (i == 8) {
            return "OP_NOT_ALLOWED_DURING_VOICE_CALL";
        }
        if (i == 9) {
            return "OP_NOT_ALLOWED_BEFORE_REG_TO_NW";
        }
        if (i == 10) {
            return "SMS_SEND_FAIL_RETRY";
        }
        if (i == 11) {
            return "SIM_ABSENT";
        }
        if (i == 12) {
            return "SUBSCRIPTION_NOT_AVAILABLE";
        }
        if (i == 13) {
            return "MODE_NOT_SUPPORTED";
        }
        if (i == 14) {
            return "FDN_CHECK_FAILURE";
        }
        if (i == 15) {
            return "ILLEGAL_SIM_OR_ME";
        }
        if (i == 16) {
            return "MISSING_RESOURCE";
        }
        if (i == 17) {
            return "NO_SUCH_ELEMENT";
        }
        if (i == 18) {
            return "DIAL_MODIFIED_TO_USSD";
        }
        if (i == 19) {
            return "DIAL_MODIFIED_TO_SS";
        }
        if (i == 20) {
            return "DIAL_MODIFIED_TO_DIAL";
        }
        if (i == 21) {
            return "USSD_MODIFIED_TO_DIAL";
        }
        if (i == 22) {
            return "USSD_MODIFIED_TO_SS";
        }
        if (i == 23) {
            return "USSD_MODIFIED_TO_USSD";
        }
        if (i == 24) {
            return "SS_MODIFIED_TO_DIAL";
        }
        if (i == 25) {
            return "SS_MODIFIED_TO_USSD";
        }
        if (i == 26) {
            return "SUBSCRIPTION_NOT_SUPPORTED";
        }
        if (i == 27) {
            return "SS_MODIFIED_TO_SS";
        }
        if (i == 36) {
            return "LCE_NOT_SUPPORTED";
        }
        if (i == 37) {
            return "NO_MEMORY";
        }
        if (i == 38) {
            return "INTERNAL_ERR";
        }
        if (i == 39) {
            return "SYSTEM_ERR";
        }
        if (i == 40) {
            return "MODEM_ERR";
        }
        if (i == 41) {
            return "INVALID_STATE";
        }
        if (i == 42) {
            return "NO_RESOURCES";
        }
        if (i == 43) {
            return "SIM_ERR";
        }
        if (i == 44) {
            return "INVALID_ARGUMENTS";
        }
        if (i == 45) {
            return "INVALID_SIM_STATE";
        }
        if (i == 46) {
            return "INVALID_MODEM_STATE";
        }
        if (i == 47) {
            return "INVALID_CALL_ID";
        }
        if (i == 48) {
            return "NO_SMS_TO_ACK";
        }
        if (i == 49) {
            return "NETWORK_ERR";
        }
        if (i == 50) {
            return "REQUEST_RATE_LIMITED";
        }
        if (i == 51) {
            return "SIM_BUSY";
        }
        if (i == 52) {
            return "SIM_FULL";
        }
        if (i == 53) {
            return "NETWORK_REJECT";
        }
        if (i == 54) {
            return "OPERATION_NOT_ALLOWED";
        }
        if (i == 55) {
            return "EMPTY_RECORD";
        }
        if (i == 56) {
            return "INVALID_SMS_FORMAT";
        }
        if (i == 57) {
            return "ENCODING_ERR";
        }
        if (i == 58) {
            return "INVALID_SMSC_ADDRESS";
        }
        if (i == 59) {
            return "NO_SUCH_ENTRY";
        }
        if (i == 60) {
            return "NETWORK_NOT_READY";
        }
        if (i == 61) {
            return "NOT_PROVISIONED";
        }
        if (i == 62) {
            return "NO_SUBSCRIPTION";
        }
        if (i == 63) {
            return "NO_NETWORK_FOUND";
        }
        if (i == 64) {
            return "DEVICE_IN_USE";
        }
        if (i == 65) {
            return "ABORTED";
        }
        if (i == 66) {
            return "INVALID_RESPONSE";
        }
        if (i == 501) {
            return "OEM_ERROR_1";
        }
        if (i == 502) {
            return "OEM_ERROR_2";
        }
        if (i == 503) {
            return "OEM_ERROR_3";
        }
        if (i == 504) {
            return "OEM_ERROR_4";
        }
        if (i == 505) {
            return "OEM_ERROR_5";
        }
        if (i == 506) {
            return "OEM_ERROR_6";
        }
        if (i == 507) {
            return "OEM_ERROR_7";
        }
        if (i == 508) {
            return "OEM_ERROR_8";
        }
        if (i == 509) {
            return "OEM_ERROR_9";
        }
        if (i == 510) {
            return "OEM_ERROR_10";
        }
        if (i == 511) {
            return "OEM_ERROR_11";
        }
        if (i == 512) {
            return "OEM_ERROR_12";
        }
        if (i == 513) {
            return "OEM_ERROR_13";
        }
        if (i == 514) {
            return "OEM_ERROR_14";
        }
        if (i == 515) {
            return "OEM_ERROR_15";
        }
        if (i == 516) {
            return "OEM_ERROR_16";
        }
        if (i == 517) {
            return "OEM_ERROR_17";
        }
        if (i == 518) {
            return "OEM_ERROR_18";
        }
        if (i == 519) {
            return "OEM_ERROR_19";
        }
        if (i == 520) {
            return "OEM_ERROR_20";
        }
        if (i == 521) {
            return "OEM_ERROR_21";
        }
        if (i == 522) {
            return "OEM_ERROR_22";
        }
        if (i == 523) {
            return "OEM_ERROR_23";
        }
        if (i == 524) {
            return "OEM_ERROR_24";
        }
        if (i == 525) {
            return "OEM_ERROR_25";
        }
        if (i == 67) {
            return "SIMULTANEOUS_SMS_AND_CALL_NOT_ALLOWED";
        }
        if (i == 68) {
            return "ACCESS_BARRED";
        }
        if (i == 69) {
            return "BLOCKED_DUE_TO_CALL";
        }
        if (i == 70) {
            return "RF_HARDWARE_ISSUE";
        }
        if (i == 71) {
            return "NO_RF_CALIBRATION_INFO";
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
