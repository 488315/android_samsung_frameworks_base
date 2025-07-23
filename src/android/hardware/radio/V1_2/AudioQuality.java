package android.hardware.radio.V1_2;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class AudioQuality {
    public static final int AMR = 1;
    public static final int AMR_WB = 2;
    public static final int EVRC = 6;
    public static final int EVRC_B = 7;
    public static final int EVRC_NW = 9;
    public static final int EVRC_WB = 8;
    public static final int GSM_EFR = 3;
    public static final int GSM_FR = 4;
    public static final int GSM_HR = 5;
    public static final int UNSPECIFIED = 0;

    public static final String toString(int i) {
        if (i == 0) {
            return "UNSPECIFIED";
        }
        if (i == 1) {
            return "AMR";
        }
        if (i == 2) {
            return "AMR_WB";
        }
        if (i == 3) {
            return "GSM_EFR";
        }
        if (i == 4) {
            return "GSM_FR";
        }
        if (i == 5) {
            return "GSM_HR";
        }
        if (i == 6) {
            return "EVRC";
        }
        if (i == 7) {
            return "EVRC_B";
        }
        if (i == 8) {
            return "EVRC_WB";
        }
        if (i == 9) {
            return "EVRC_NW";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("UNSPECIFIED");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("AMR");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("AMR_WB");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("GSM_EFR");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("GSM_FR");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("GSM_HR");
            i2 |= 5;
        }
        if ((i & 6) == 6) {
            arrayList.add("EVRC");
            i2 |= 6;
        }
        if ((i & 7) == 7) {
            arrayList.add("EVRC_B");
            i2 = 7;
        }
        if ((i & 8) == 8) {
            arrayList.add("EVRC_WB");
            i2 |= 8;
        }
        if ((i & 9) == 9) {
            arrayList.add("EVRC_NW");
            i2 |= 9;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
