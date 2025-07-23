package android.hardware.radio.V1_0;

import android.security.keystore.KeyProperties;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class RadioError {
    public static final int ABORTED = 65;
    public static final int CANCELLED = 7;
    public static final int DEVICE_IN_USE = 64;
    public static final int DIAL_MODIFIED_TO_DIAL = 20;
    public static final int DIAL_MODIFIED_TO_SS = 19;
    public static final int DIAL_MODIFIED_TO_USSD = 18;
    public static final int EMPTY_RECORD = 55;
    public static final int ENCODING_ERR = 57;
    public static final int FDN_CHECK_FAILURE = 14;
    public static final int GENERIC_FAILURE = 2;
    public static final int ILLEGAL_SIM_OR_ME = 15;
    public static final int INTERNAL_ERR = 38;
    public static final int INVALID_ARGUMENTS = 44;
    public static final int INVALID_CALL_ID = 47;
    public static final int INVALID_MODEM_STATE = 46;
    public static final int INVALID_RESPONSE = 66;
    public static final int INVALID_SIM_STATE = 45;
    public static final int INVALID_SMSC_ADDRESS = 58;
    public static final int INVALID_SMS_FORMAT = 56;
    public static final int INVALID_STATE = 41;
    public static final int LCE_NOT_SUPPORTED = 36;
    public static final int MISSING_RESOURCE = 16;
    public static final int MODEM_ERR = 40;
    public static final int MODE_NOT_SUPPORTED = 13;
    public static final int NETWORK_ERR = 49;
    public static final int NETWORK_NOT_READY = 60;
    public static final int NETWORK_REJECT = 53;
    public static final int NONE = 0;
    public static final int NOT_PROVISIONED = 61;
    public static final int NO_MEMORY = 37;
    public static final int NO_NETWORK_FOUND = 63;
    public static final int NO_RESOURCES = 42;
    public static final int NO_SMS_TO_ACK = 48;
    public static final int NO_SUBSCRIPTION = 62;
    public static final int NO_SUCH_ELEMENT = 17;
    public static final int NO_SUCH_ENTRY = 59;
    public static final int OEM_ERROR_1 = 501;
    public static final int OEM_ERROR_10 = 510;
    public static final int OEM_ERROR_11 = 511;
    public static final int OEM_ERROR_12 = 512;
    public static final int OEM_ERROR_13 = 513;
    public static final int OEM_ERROR_14 = 514;
    public static final int OEM_ERROR_15 = 515;
    public static final int OEM_ERROR_16 = 516;
    public static final int OEM_ERROR_17 = 517;
    public static final int OEM_ERROR_18 = 518;
    public static final int OEM_ERROR_19 = 519;
    public static final int OEM_ERROR_2 = 502;
    public static final int OEM_ERROR_20 = 520;
    public static final int OEM_ERROR_21 = 521;
    public static final int OEM_ERROR_22 = 522;
    public static final int OEM_ERROR_23 = 523;
    public static final int OEM_ERROR_24 = 524;
    public static final int OEM_ERROR_25 = 525;
    public static final int OEM_ERROR_3 = 503;
    public static final int OEM_ERROR_4 = 504;
    public static final int OEM_ERROR_5 = 505;
    public static final int OEM_ERROR_6 = 506;
    public static final int OEM_ERROR_7 = 507;
    public static final int OEM_ERROR_8 = 508;
    public static final int OEM_ERROR_9 = 509;
    public static final int OPERATION_NOT_ALLOWED = 54;
    public static final int OP_NOT_ALLOWED_BEFORE_REG_TO_NW = 9;
    public static final int OP_NOT_ALLOWED_DURING_VOICE_CALL = 8;
    public static final int PASSWORD_INCORRECT = 3;
    public static final int RADIO_NOT_AVAILABLE = 1;
    public static final int REQUEST_NOT_SUPPORTED = 6;
    public static final int REQUEST_RATE_LIMITED = 50;
    public static final int SIM_ABSENT = 11;
    public static final int SIM_BUSY = 51;
    public static final int SIM_ERR = 43;
    public static final int SIM_FULL = 52;
    public static final int SIM_PIN2 = 4;
    public static final int SIM_PUK2 = 5;
    public static final int SMS_SEND_FAIL_RETRY = 10;
    public static final int SS_MODIFIED_TO_DIAL = 24;
    public static final int SS_MODIFIED_TO_SS = 27;
    public static final int SS_MODIFIED_TO_USSD = 25;
    public static final int SUBSCRIPTION_NOT_AVAILABLE = 12;
    public static final int SUBSCRIPTION_NOT_SUPPORTED = 26;
    public static final int SYSTEM_ERR = 39;
    public static final int USSD_MODIFIED_TO_DIAL = 21;
    public static final int USSD_MODIFIED_TO_SS = 22;
    public static final int USSD_MODIFIED_TO_USSD = 23;

    public static final String toString(int i) {
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
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(KeyProperties.DIGEST_NONE);
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("RADIO_NOT_AVAILABLE");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("GENERIC_FAILURE");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("PASSWORD_INCORRECT");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("SIM_PIN2");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("SIM_PUK2");
            i2 |= 5;
        }
        if ((i & 6) == 6) {
            arrayList.add("REQUEST_NOT_SUPPORTED");
            i2 |= 6;
        }
        if ((i & 7) == 7) {
            arrayList.add("CANCELLED");
            i2 = 7;
        }
        if ((i & 8) == 8) {
            arrayList.add("OP_NOT_ALLOWED_DURING_VOICE_CALL");
            i2 |= 8;
        }
        if ((i & 9) == 9) {
            arrayList.add("OP_NOT_ALLOWED_BEFORE_REG_TO_NW");
            i2 |= 9;
        }
        if ((i & 10) == 10) {
            arrayList.add("SMS_SEND_FAIL_RETRY");
            i2 |= 10;
        }
        if ((i & 11) == 11) {
            arrayList.add("SIM_ABSENT");
            i2 |= 11;
        }
        if ((i & 12) == 12) {
            arrayList.add("SUBSCRIPTION_NOT_AVAILABLE");
            i2 |= 12;
        }
        if ((i & 13) == 13) {
            arrayList.add("MODE_NOT_SUPPORTED");
            i2 |= 13;
        }
        if ((i & 14) == 14) {
            arrayList.add("FDN_CHECK_FAILURE");
            i2 |= 14;
        }
        if ((i & 15) == 15) {
            arrayList.add("ILLEGAL_SIM_OR_ME");
            i2 = 15;
        }
        if ((i & 16) == 16) {
            arrayList.add("MISSING_RESOURCE");
            i2 |= 16;
        }
        if ((i & 17) == 17) {
            arrayList.add("NO_SUCH_ELEMENT");
            i2 |= 17;
        }
        if ((i & 18) == 18) {
            arrayList.add("DIAL_MODIFIED_TO_USSD");
            i2 |= 18;
        }
        if ((i & 19) == 19) {
            arrayList.add("DIAL_MODIFIED_TO_SS");
            i2 |= 19;
        }
        if ((i & 20) == 20) {
            arrayList.add("DIAL_MODIFIED_TO_DIAL");
            i2 |= 20;
        }
        if ((i & 21) == 21) {
            arrayList.add("USSD_MODIFIED_TO_DIAL");
            i2 |= 21;
        }
        if ((i & 22) == 22) {
            arrayList.add("USSD_MODIFIED_TO_SS");
            i2 |= 22;
        }
        if ((i & 23) == 23) {
            arrayList.add("USSD_MODIFIED_TO_USSD");
            i2 |= 23;
        }
        if ((i & 24) == 24) {
            arrayList.add("SS_MODIFIED_TO_DIAL");
            i2 |= 24;
        }
        if ((i & 25) == 25) {
            arrayList.add("SS_MODIFIED_TO_USSD");
            i2 |= 25;
        }
        if ((i & 26) == 26) {
            arrayList.add("SUBSCRIPTION_NOT_SUPPORTED");
            i2 |= 26;
        }
        if ((i & 27) == 27) {
            arrayList.add("SS_MODIFIED_TO_SS");
            i2 |= 27;
        }
        if ((i & 36) == 36) {
            arrayList.add("LCE_NOT_SUPPORTED");
            i2 |= 36;
        }
        if ((i & 37) == 37) {
            arrayList.add("NO_MEMORY");
            i2 |= 37;
        }
        if ((i & 38) == 38) {
            arrayList.add("INTERNAL_ERR");
            i2 |= 38;
        }
        if ((i & 39) == 39) {
            arrayList.add("SYSTEM_ERR");
            i2 |= 39;
        }
        if ((i & 40) == 40) {
            arrayList.add("MODEM_ERR");
            i2 |= 40;
        }
        if ((i & 41) == 41) {
            arrayList.add("INVALID_STATE");
            i2 |= 41;
        }
        if ((i & 42) == 42) {
            arrayList.add("NO_RESOURCES");
            i2 |= 42;
        }
        if ((i & 43) == 43) {
            arrayList.add("SIM_ERR");
            i2 |= 43;
        }
        if ((i & 44) == 44) {
            arrayList.add("INVALID_ARGUMENTS");
            i2 |= 44;
        }
        if ((i & 45) == 45) {
            arrayList.add("INVALID_SIM_STATE");
            i2 |= 45;
        }
        if ((i & 46) == 46) {
            arrayList.add("INVALID_MODEM_STATE");
            i2 |= 46;
        }
        if ((i & 47) == 47) {
            arrayList.add("INVALID_CALL_ID");
            i2 |= 47;
        }
        if ((i & 48) == 48) {
            arrayList.add("NO_SMS_TO_ACK");
            i2 |= 48;
        }
        if ((i & 49) == 49) {
            arrayList.add("NETWORK_ERR");
            i2 |= 49;
        }
        if ((i & 50) == 50) {
            arrayList.add("REQUEST_RATE_LIMITED");
            i2 |= 50;
        }
        if ((i & 51) == 51) {
            arrayList.add("SIM_BUSY");
            i2 |= 51;
        }
        if ((i & 52) == 52) {
            arrayList.add("SIM_FULL");
            i2 |= 52;
        }
        if ((i & 53) == 53) {
            arrayList.add("NETWORK_REJECT");
            i2 |= 53;
        }
        if ((i & 54) == 54) {
            arrayList.add("OPERATION_NOT_ALLOWED");
            i2 |= 54;
        }
        if ((i & 55) == 55) {
            arrayList.add("EMPTY_RECORD");
            i2 |= 55;
        }
        if ((i & 56) == 56) {
            arrayList.add("INVALID_SMS_FORMAT");
            i2 |= 56;
        }
        if ((i & 57) == 57) {
            arrayList.add("ENCODING_ERR");
            i2 |= 57;
        }
        if ((i & 58) == 58) {
            arrayList.add("INVALID_SMSC_ADDRESS");
            i2 |= 58;
        }
        if ((i & 59) == 59) {
            arrayList.add("NO_SUCH_ENTRY");
            i2 |= 59;
        }
        if ((i & 60) == 60) {
            arrayList.add("NETWORK_NOT_READY");
            i2 |= 60;
        }
        if ((i & 61) == 61) {
            arrayList.add("NOT_PROVISIONED");
            i2 |= 61;
        }
        if ((i & 62) == 62) {
            arrayList.add("NO_SUBSCRIPTION");
            i2 |= 62;
        }
        if ((i & 63) == 63) {
            arrayList.add("NO_NETWORK_FOUND");
            i2 = 63;
        }
        if ((i & 64) == 64) {
            arrayList.add("DEVICE_IN_USE");
            i2 |= 64;
        }
        if ((i & 65) == 65) {
            arrayList.add("ABORTED");
            i2 |= 65;
        }
        if ((i & 66) == 66) {
            arrayList.add("INVALID_RESPONSE");
            i2 |= 66;
        }
        if ((i & 501) == 501) {
            arrayList.add("OEM_ERROR_1");
            i2 |= 501;
        }
        if ((i & 502) == 502) {
            arrayList.add("OEM_ERROR_2");
            i2 |= 502;
        }
        if ((i & 503) == 503) {
            arrayList.add("OEM_ERROR_3");
            i2 |= 503;
        }
        if ((i & 504) == 504) {
            arrayList.add("OEM_ERROR_4");
            i2 |= 504;
        }
        if ((i & 505) == 505) {
            arrayList.add("OEM_ERROR_5");
            i2 |= 505;
        }
        if ((i & 506) == 506) {
            arrayList.add("OEM_ERROR_6");
            i2 |= 506;
        }
        if ((i & 507) == 507) {
            arrayList.add("OEM_ERROR_7");
            i2 |= 507;
        }
        if ((i & 508) == 508) {
            arrayList.add("OEM_ERROR_8");
            i2 |= 508;
        }
        if ((i & 509) == 509) {
            arrayList.add("OEM_ERROR_9");
            i2 |= 509;
        }
        if ((i & 510) == 510) {
            arrayList.add("OEM_ERROR_10");
            i2 |= 510;
        }
        if ((i & 511) == 511) {
            arrayList.add("OEM_ERROR_11");
            i2 = 511;
        }
        if ((i & 512) == 512) {
            arrayList.add("OEM_ERROR_12");
            i2 |= 512;
        }
        if ((i & 513) == 513) {
            arrayList.add("OEM_ERROR_13");
            i2 |= 513;
        }
        if ((i & 514) == 514) {
            arrayList.add("OEM_ERROR_14");
            i2 |= 514;
        }
        if ((i & 515) == 515) {
            arrayList.add("OEM_ERROR_15");
            i2 |= 515;
        }
        if ((i & 516) == 516) {
            arrayList.add("OEM_ERROR_16");
            i2 |= 516;
        }
        if ((i & 517) == 517) {
            arrayList.add("OEM_ERROR_17");
            i2 |= 517;
        }
        if ((i & 518) == 518) {
            arrayList.add("OEM_ERROR_18");
            i2 |= 518;
        }
        if ((i & 519) == 519) {
            arrayList.add("OEM_ERROR_19");
            i2 |= 519;
        }
        if ((i & 520) == 520) {
            arrayList.add("OEM_ERROR_20");
            i2 |= 520;
        }
        if ((i & 521) == 521) {
            arrayList.add("OEM_ERROR_21");
            i2 |= 521;
        }
        if ((i & 522) == 522) {
            arrayList.add("OEM_ERROR_22");
            i2 |= 522;
        }
        if ((i & 523) == 523) {
            arrayList.add("OEM_ERROR_23");
            i2 |= 523;
        }
        if ((i & 524) == 524) {
            arrayList.add("OEM_ERROR_24");
            i2 |= 524;
        }
        if ((i & 525) == 525) {
            arrayList.add("OEM_ERROR_25");
            i2 |= 525;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
