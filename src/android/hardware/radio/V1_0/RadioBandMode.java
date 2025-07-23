package android.hardware.radio.V1_0;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class RadioBandMode {
    public static final int BAND_MODE_10_800M_2 = 15;
    public static final int BAND_MODE_5_450M = 10;
    public static final int BAND_MODE_7_700M_2 = 12;
    public static final int BAND_MODE_8_1800M = 13;
    public static final int BAND_MODE_9_900M = 14;
    public static final int BAND_MODE_AUS = 4;
    public static final int BAND_MODE_AUS_2 = 5;
    public static final int BAND_MODE_AWS = 17;
    public static final int BAND_MODE_CELL_800 = 6;
    public static final int BAND_MODE_EURO = 1;
    public static final int BAND_MODE_EURO_PAMR_400M = 16;
    public static final int BAND_MODE_IMT2000 = 11;
    public static final int BAND_MODE_JPN = 3;
    public static final int BAND_MODE_JTACS = 8;
    public static final int BAND_MODE_KOREA_PCS = 9;
    public static final int BAND_MODE_PCS = 7;
    public static final int BAND_MODE_UNSPECIFIED = 0;
    public static final int BAND_MODE_USA = 2;
    public static final int BAND_MODE_USA_2500M = 18;

    public static final String toString(int i) {
        if (i == 0) {
            return "BAND_MODE_UNSPECIFIED";
        }
        if (i == 1) {
            return "BAND_MODE_EURO";
        }
        if (i == 2) {
            return "BAND_MODE_USA";
        }
        if (i == 3) {
            return "BAND_MODE_JPN";
        }
        if (i == 4) {
            return "BAND_MODE_AUS";
        }
        if (i == 5) {
            return "BAND_MODE_AUS_2";
        }
        if (i == 6) {
            return "BAND_MODE_CELL_800";
        }
        if (i == 7) {
            return "BAND_MODE_PCS";
        }
        if (i == 8) {
            return "BAND_MODE_JTACS";
        }
        if (i == 9) {
            return "BAND_MODE_KOREA_PCS";
        }
        if (i == 10) {
            return "BAND_MODE_5_450M";
        }
        if (i == 11) {
            return "BAND_MODE_IMT2000";
        }
        if (i == 12) {
            return "BAND_MODE_7_700M_2";
        }
        if (i == 13) {
            return "BAND_MODE_8_1800M";
        }
        if (i == 14) {
            return "BAND_MODE_9_900M";
        }
        if (i == 15) {
            return "BAND_MODE_10_800M_2";
        }
        if (i == 16) {
            return "BAND_MODE_EURO_PAMR_400M";
        }
        if (i == 17) {
            return "BAND_MODE_AWS";
        }
        if (i == 18) {
            return "BAND_MODE_USA_2500M";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("BAND_MODE_UNSPECIFIED");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("BAND_MODE_EURO");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("BAND_MODE_USA");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("BAND_MODE_JPN");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("BAND_MODE_AUS");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("BAND_MODE_AUS_2");
            i2 |= 5;
        }
        if ((i & 6) == 6) {
            arrayList.add("BAND_MODE_CELL_800");
            i2 |= 6;
        }
        if ((i & 7) == 7) {
            arrayList.add("BAND_MODE_PCS");
            i2 = 7;
        }
        if ((i & 8) == 8) {
            arrayList.add("BAND_MODE_JTACS");
            i2 |= 8;
        }
        if ((i & 9) == 9) {
            arrayList.add("BAND_MODE_KOREA_PCS");
            i2 |= 9;
        }
        if ((i & 10) == 10) {
            arrayList.add("BAND_MODE_5_450M");
            i2 |= 10;
        }
        if ((i & 11) == 11) {
            arrayList.add("BAND_MODE_IMT2000");
            i2 |= 11;
        }
        if ((i & 12) == 12) {
            arrayList.add("BAND_MODE_7_700M_2");
            i2 |= 12;
        }
        if ((i & 13) == 13) {
            arrayList.add("BAND_MODE_8_1800M");
            i2 |= 13;
        }
        if ((i & 14) == 14) {
            arrayList.add("BAND_MODE_9_900M");
            i2 |= 14;
        }
        if ((i & 15) == 15) {
            arrayList.add("BAND_MODE_10_800M_2");
            i2 = 15;
        }
        if ((i & 16) == 16) {
            arrayList.add("BAND_MODE_EURO_PAMR_400M");
            i2 |= 16;
        }
        if ((i & 17) == 17) {
            arrayList.add("BAND_MODE_AWS");
            i2 |= 17;
        }
        if ((i & 18) == 18) {
            arrayList.add("BAND_MODE_USA_2500M");
            i2 |= 18;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
