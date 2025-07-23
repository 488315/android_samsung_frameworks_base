package android.hardware.radio.V1_0;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class PreferredNetworkType {
    public static final int CDMA_EVDO_AUTO = 4;
    public static final int CDMA_ONLY = 5;
    public static final int EVDO_ONLY = 6;
    public static final int GSM_ONLY = 1;
    public static final int GSM_WCDMA = 0;
    public static final int GSM_WCDMA_AUTO = 3;
    public static final int GSM_WCDMA_CDMA_EVDO_AUTO = 7;
    public static final int LTE_CDMA_EVDO = 8;
    public static final int LTE_CMDA_EVDO_GSM_WCDMA = 10;
    public static final int LTE_GSM_WCDMA = 9;
    public static final int LTE_ONLY = 11;
    public static final int LTE_WCDMA = 12;
    public static final int TD_SCDMA_GSM = 16;
    public static final int TD_SCDMA_GSM_LTE = 17;
    public static final int TD_SCDMA_GSM_WCDMA = 18;
    public static final int TD_SCDMA_GSM_WCDMA_CDMA_EVDO_AUTO = 21;
    public static final int TD_SCDMA_GSM_WCDMA_LTE = 20;
    public static final int TD_SCDMA_LTE = 15;
    public static final int TD_SCDMA_LTE_CDMA_EVDO_GSM_WCDMA = 22;
    public static final int TD_SCDMA_ONLY = 13;
    public static final int TD_SCDMA_WCDMA = 14;
    public static final int TD_SCDMA_WCDMA_LTE = 19;
    public static final int WCDMA = 2;

    public static final String toString(int i) {
        if (i == 0) {
            return "GSM_WCDMA";
        }
        if (i == 1) {
            return "GSM_ONLY";
        }
        if (i == 2) {
            return "WCDMA";
        }
        if (i == 3) {
            return "GSM_WCDMA_AUTO";
        }
        if (i == 4) {
            return "CDMA_EVDO_AUTO";
        }
        if (i == 5) {
            return "CDMA_ONLY";
        }
        if (i == 6) {
            return "EVDO_ONLY";
        }
        if (i == 7) {
            return "GSM_WCDMA_CDMA_EVDO_AUTO";
        }
        if (i == 8) {
            return "LTE_CDMA_EVDO";
        }
        if (i == 9) {
            return "LTE_GSM_WCDMA";
        }
        if (i == 10) {
            return "LTE_CMDA_EVDO_GSM_WCDMA";
        }
        if (i == 11) {
            return "LTE_ONLY";
        }
        if (i == 12) {
            return "LTE_WCDMA";
        }
        if (i == 13) {
            return "TD_SCDMA_ONLY";
        }
        if (i == 14) {
            return "TD_SCDMA_WCDMA";
        }
        if (i == 15) {
            return "TD_SCDMA_LTE";
        }
        if (i == 16) {
            return "TD_SCDMA_GSM";
        }
        if (i == 17) {
            return "TD_SCDMA_GSM_LTE";
        }
        if (i == 18) {
            return "TD_SCDMA_GSM_WCDMA";
        }
        if (i == 19) {
            return "TD_SCDMA_WCDMA_LTE";
        }
        if (i == 20) {
            return "TD_SCDMA_GSM_WCDMA_LTE";
        }
        if (i == 21) {
            return "TD_SCDMA_GSM_WCDMA_CDMA_EVDO_AUTO";
        }
        if (i == 22) {
            return "TD_SCDMA_LTE_CDMA_EVDO_GSM_WCDMA";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("GSM_WCDMA");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("GSM_ONLY");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("WCDMA");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("GSM_WCDMA_AUTO");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("CDMA_EVDO_AUTO");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("CDMA_ONLY");
            i2 |= 5;
        }
        if ((i & 6) == 6) {
            arrayList.add("EVDO_ONLY");
            i2 |= 6;
        }
        if ((i & 7) == 7) {
            arrayList.add("GSM_WCDMA_CDMA_EVDO_AUTO");
            i2 = 7;
        }
        if ((i & 8) == 8) {
            arrayList.add("LTE_CDMA_EVDO");
            i2 |= 8;
        }
        if ((i & 9) == 9) {
            arrayList.add("LTE_GSM_WCDMA");
            i2 |= 9;
        }
        if ((i & 10) == 10) {
            arrayList.add("LTE_CMDA_EVDO_GSM_WCDMA");
            i2 |= 10;
        }
        if ((i & 11) == 11) {
            arrayList.add("LTE_ONLY");
            i2 |= 11;
        }
        if ((i & 12) == 12) {
            arrayList.add("LTE_WCDMA");
            i2 |= 12;
        }
        if ((i & 13) == 13) {
            arrayList.add("TD_SCDMA_ONLY");
            i2 |= 13;
        }
        if ((i & 14) == 14) {
            arrayList.add("TD_SCDMA_WCDMA");
            i2 |= 14;
        }
        if ((i & 15) == 15) {
            arrayList.add("TD_SCDMA_LTE");
            i2 = 15;
        }
        if ((i & 16) == 16) {
            arrayList.add("TD_SCDMA_GSM");
            i2 |= 16;
        }
        if ((i & 17) == 17) {
            arrayList.add("TD_SCDMA_GSM_LTE");
            i2 |= 17;
        }
        if ((i & 18) == 18) {
            arrayList.add("TD_SCDMA_GSM_WCDMA");
            i2 |= 18;
        }
        if ((i & 19) == 19) {
            arrayList.add("TD_SCDMA_WCDMA_LTE");
            i2 |= 19;
        }
        if ((i & 20) == 20) {
            arrayList.add("TD_SCDMA_GSM_WCDMA_LTE");
            i2 |= 20;
        }
        if ((i & 21) == 21) {
            arrayList.add("TD_SCDMA_GSM_WCDMA_CDMA_EVDO_AUTO");
            i2 |= 21;
        }
        if ((i & 22) == 22) {
            arrayList.add("TD_SCDMA_LTE_CDMA_EVDO_GSM_WCDMA");
            i2 |= 22;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
