package android.hardware.radio.V1_0;

import com.android.internal.telephony.DctConstants;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class RadioTechnology {
    public static final int EDGE = 2;
    public static final int EHRPD = 13;
    public static final int EVDO_0 = 7;
    public static final int EVDO_A = 8;
    public static final int EVDO_B = 12;
    public static final int GPRS = 1;
    public static final int GSM = 16;
    public static final int HSDPA = 9;
    public static final int HSPA = 11;
    public static final int HSPAP = 15;
    public static final int HSUPA = 10;
    public static final int IS95A = 4;
    public static final int IS95B = 5;
    public static final int IWLAN = 18;
    public static final int LTE = 14;
    public static final int LTE_CA = 19;
    public static final int ONE_X_RTT = 6;
    public static final int TD_SCDMA = 17;
    public static final int UMTS = 3;
    public static final int UNKNOWN = 0;

    public static final String toString(int i) {
        if (i == 0) {
            return "UNKNOWN";
        }
        if (i == 1) {
            return "GPRS";
        }
        if (i == 2) {
            return "EDGE";
        }
        if (i == 3) {
            return "UMTS";
        }
        if (i == 4) {
            return "IS95A";
        }
        if (i == 5) {
            return "IS95B";
        }
        if (i == 6) {
            return "ONE_X_RTT";
        }
        if (i == 7) {
            return "EVDO_0";
        }
        if (i == 8) {
            return "EVDO_A";
        }
        if (i == 9) {
            return "HSDPA";
        }
        if (i == 10) {
            return "HSUPA";
        }
        if (i == 11) {
            return "HSPA";
        }
        if (i == 12) {
            return "EVDO_B";
        }
        if (i == 13) {
            return "EHRPD";
        }
        if (i == 14) {
            return DctConstants.RAT_NAME_LTE;
        }
        if (i == 15) {
            return "HSPAP";
        }
        if (i == 16) {
            return "GSM";
        }
        if (i == 17) {
            return "TD_SCDMA";
        }
        if (i == 18) {
            return "IWLAN";
        }
        if (i == 19) {
            return "LTE_CA";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("UNKNOWN");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("GPRS");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("EDGE");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("UMTS");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("IS95A");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("IS95B");
            i2 |= 5;
        }
        if ((i & 6) == 6) {
            arrayList.add("ONE_X_RTT");
            i2 |= 6;
        }
        if ((i & 7) == 7) {
            arrayList.add("EVDO_0");
            i2 = 7;
        }
        if ((i & 8) == 8) {
            arrayList.add("EVDO_A");
            i2 |= 8;
        }
        if ((i & 9) == 9) {
            arrayList.add("HSDPA");
            i2 |= 9;
        }
        if ((i & 10) == 10) {
            arrayList.add("HSUPA");
            i2 |= 10;
        }
        if ((i & 11) == 11) {
            arrayList.add("HSPA");
            i2 |= 11;
        }
        if ((i & 12) == 12) {
            arrayList.add("EVDO_B");
            i2 |= 12;
        }
        if ((i & 13) == 13) {
            arrayList.add("EHRPD");
            i2 |= 13;
        }
        if ((i & 14) == 14) {
            arrayList.add(DctConstants.RAT_NAME_LTE);
            i2 |= 14;
        }
        if ((i & 15) == 15) {
            arrayList.add("HSPAP");
            i2 = 15;
        }
        if ((i & 16) == 16) {
            arrayList.add("GSM");
            i2 |= 16;
        }
        if ((i & 17) == 17) {
            arrayList.add("TD_SCDMA");
            i2 |= 17;
        }
        if ((i & 18) == 18) {
            arrayList.add("IWLAN");
            i2 |= 18;
        }
        if ((i & 19) == 19) {
            arrayList.add("LTE_CA");
            i2 |= 19;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
