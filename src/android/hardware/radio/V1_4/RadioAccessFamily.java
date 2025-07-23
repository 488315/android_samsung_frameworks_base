package android.hardware.radio.V1_4;

import com.android.internal.telephony.DctConstants;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class RadioAccessFamily {
    public static final int EDGE = 4;
    public static final int EHRPD = 8192;
    public static final int EVDO_0 = 128;
    public static final int EVDO_A = 256;
    public static final int EVDO_B = 4096;
    public static final int GPRS = 2;
    public static final int GSM = 65536;
    public static final int HSDPA = 512;
    public static final int HSPA = 2048;
    public static final int HSPAP = 32768;
    public static final int HSUPA = 1024;
    public static final int IS95A = 16;
    public static final int IS95B = 32;
    public static final int LTE = 16384;
    public static final int LTE_CA = 524288;
    public static final int NR = 1048576;
    public static final int ONE_X_RTT = 64;
    public static final int TD_SCDMA = 131072;
    public static final int UMTS = 8;
    public static final int UNKNOWN = 1;

    public static final String toString(int i) {
        if (i == 1) {
            return "UNKNOWN";
        }
        if (i == 2) {
            return "GPRS";
        }
        if (i == 4) {
            return "EDGE";
        }
        if (i == 8) {
            return "UMTS";
        }
        if (i == 16) {
            return "IS95A";
        }
        if (i == 32) {
            return "IS95B";
        }
        if (i == 64) {
            return "ONE_X_RTT";
        }
        if (i == 128) {
            return "EVDO_0";
        }
        if (i == 256) {
            return "EVDO_A";
        }
        if (i == 512) {
            return "HSDPA";
        }
        if (i == 1024) {
            return "HSUPA";
        }
        if (i == 2048) {
            return "HSPA";
        }
        if (i == 4096) {
            return "EVDO_B";
        }
        if (i == 8192) {
            return "EHRPD";
        }
        if (i == 16384) {
            return DctConstants.RAT_NAME_LTE;
        }
        if (i == 32768) {
            return "HSPAP";
        }
        if (i == 65536) {
            return "GSM";
        }
        if (i == 131072) {
            return "TD_SCDMA";
        }
        if (i == 524288) {
            return "LTE_CA";
        }
        if (i == 1048576) {
            return "NR";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("UNKNOWN");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("GPRS");
            i2 |= 2;
        }
        if ((i & 4) == 4) {
            arrayList.add("EDGE");
            i2 |= 4;
        }
        if ((i & 8) == 8) {
            arrayList.add("UMTS");
            i2 |= 8;
        }
        if ((i & 16) == 16) {
            arrayList.add("IS95A");
            i2 |= 16;
        }
        if ((i & 32) == 32) {
            arrayList.add("IS95B");
            i2 |= 32;
        }
        if ((i & 64) == 64) {
            arrayList.add("ONE_X_RTT");
            i2 |= 64;
        }
        if ((i & 128) == 128) {
            arrayList.add("EVDO_0");
            i2 |= 128;
        }
        if ((i & 256) == 256) {
            arrayList.add("EVDO_A");
            i2 |= 256;
        }
        if ((i & 512) == 512) {
            arrayList.add("HSDPA");
            i2 |= 512;
        }
        if ((i & 1024) == 1024) {
            arrayList.add("HSUPA");
            i2 |= 1024;
        }
        if ((i & 2048) == 2048) {
            arrayList.add("HSPA");
            i2 |= 2048;
        }
        if ((i & 4096) == 4096) {
            arrayList.add("EVDO_B");
            i2 |= 4096;
        }
        if ((i & 8192) == 8192) {
            arrayList.add("EHRPD");
            i2 |= 8192;
        }
        if ((i & 16384) == 16384) {
            arrayList.add(DctConstants.RAT_NAME_LTE);
            i2 |= 16384;
        }
        if ((i & 32768) == 32768) {
            arrayList.add("HSPAP");
            i2 |= 32768;
        }
        if ((i & 65536) == 65536) {
            arrayList.add("GSM");
            i2 |= 65536;
        }
        if ((i & 131072) == 131072) {
            arrayList.add("TD_SCDMA");
            i2 |= 131072;
        }
        if ((i & 524288) == 524288) {
            arrayList.add("LTE_CA");
            i2 |= 524288;
        }
        if ((i & 1048576) == 1048576) {
            arrayList.add("NR");
            i2 |= 1048576;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
