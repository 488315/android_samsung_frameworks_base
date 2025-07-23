package android.hardware.cas.V1_0;

import com.android.server.SecureKeyConst;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class Status {
    public static final int BAD_VALUE = 6;
    public static final int ERROR_CAS_CANNOT_HANDLE = 4;
    public static final int ERROR_CAS_DECRYPT = 13;
    public static final int ERROR_CAS_DECRYPT_UNIT_NOT_INITIALIZED = 12;
    public static final int ERROR_CAS_DEVICE_REVOKED = 11;
    public static final int ERROR_CAS_INSUFFICIENT_OUTPUT_PROTECTION = 9;
    public static final int ERROR_CAS_INVALID_STATE = 5;
    public static final int ERROR_CAS_LICENSE_EXPIRED = 2;
    public static final int ERROR_CAS_NOT_PROVISIONED = 7;
    public static final int ERROR_CAS_NO_LICENSE = 1;
    public static final int ERROR_CAS_RESOURCE_BUSY = 8;
    public static final int ERROR_CAS_SESSION_NOT_OPENED = 3;
    public static final int ERROR_CAS_TAMPER_DETECTED = 10;
    public static final int ERROR_CAS_UNKNOWN = 14;
    public static final int OK = 0;

    public static final String toString(int i) {
        if (i == 0) {
            return SecureKeyConst.AT_RESPONSE_OK;
        }
        if (i == 1) {
            return "ERROR_CAS_NO_LICENSE";
        }
        if (i == 2) {
            return "ERROR_CAS_LICENSE_EXPIRED";
        }
        if (i == 3) {
            return "ERROR_CAS_SESSION_NOT_OPENED";
        }
        if (i == 4) {
            return "ERROR_CAS_CANNOT_HANDLE";
        }
        if (i == 5) {
            return "ERROR_CAS_INVALID_STATE";
        }
        if (i == 6) {
            return "BAD_VALUE";
        }
        if (i == 7) {
            return "ERROR_CAS_NOT_PROVISIONED";
        }
        if (i == 8) {
            return "ERROR_CAS_RESOURCE_BUSY";
        }
        if (i == 9) {
            return "ERROR_CAS_INSUFFICIENT_OUTPUT_PROTECTION";
        }
        if (i == 10) {
            return "ERROR_CAS_TAMPER_DETECTED";
        }
        if (i == 11) {
            return "ERROR_CAS_DEVICE_REVOKED";
        }
        if (i == 12) {
            return "ERROR_CAS_DECRYPT_UNIT_NOT_INITIALIZED";
        }
        if (i == 13) {
            return "ERROR_CAS_DECRYPT";
        }
        if (i == 14) {
            return "ERROR_CAS_UNKNOWN";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(SecureKeyConst.AT_RESPONSE_OK);
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("ERROR_CAS_NO_LICENSE");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("ERROR_CAS_LICENSE_EXPIRED");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("ERROR_CAS_SESSION_NOT_OPENED");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("ERROR_CAS_CANNOT_HANDLE");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("ERROR_CAS_INVALID_STATE");
            i2 |= 5;
        }
        if ((i & 6) == 6) {
            arrayList.add("BAD_VALUE");
            i2 |= 6;
        }
        if ((i & 7) == 7) {
            arrayList.add("ERROR_CAS_NOT_PROVISIONED");
            i2 = 7;
        }
        if ((i & 8) == 8) {
            arrayList.add("ERROR_CAS_RESOURCE_BUSY");
            i2 |= 8;
        }
        if ((i & 9) == 9) {
            arrayList.add("ERROR_CAS_INSUFFICIENT_OUTPUT_PROTECTION");
            i2 |= 9;
        }
        if ((i & 10) == 10) {
            arrayList.add("ERROR_CAS_TAMPER_DETECTED");
            i2 |= 10;
        }
        if ((i & 11) == 11) {
            arrayList.add("ERROR_CAS_DEVICE_REVOKED");
            i2 |= 11;
        }
        if ((i & 12) == 12) {
            arrayList.add("ERROR_CAS_DECRYPT_UNIT_NOT_INITIALIZED");
            i2 |= 12;
        }
        if ((i & 13) == 13) {
            arrayList.add("ERROR_CAS_DECRYPT");
            i2 |= 13;
        }
        if ((i & 14) == 14) {
            arrayList.add("ERROR_CAS_UNKNOWN");
            i2 |= 14;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
