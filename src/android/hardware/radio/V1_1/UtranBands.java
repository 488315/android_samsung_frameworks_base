package android.hardware.radio.V1_1;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class UtranBands {
    public static final int BAND_1 = 1;
    public static final int BAND_10 = 10;
    public static final int BAND_11 = 11;
    public static final int BAND_12 = 12;
    public static final int BAND_13 = 13;
    public static final int BAND_14 = 14;
    public static final int BAND_19 = 19;
    public static final int BAND_2 = 2;
    public static final int BAND_20 = 20;
    public static final int BAND_21 = 21;
    public static final int BAND_22 = 22;
    public static final int BAND_25 = 25;
    public static final int BAND_26 = 26;
    public static final int BAND_3 = 3;
    public static final int BAND_4 = 4;
    public static final int BAND_5 = 5;
    public static final int BAND_6 = 6;
    public static final int BAND_7 = 7;
    public static final int BAND_8 = 8;
    public static final int BAND_9 = 9;

    public static final String toString(int i) {
        if (i == 1) {
            return "BAND_1";
        }
        if (i == 2) {
            return "BAND_2";
        }
        if (i == 3) {
            return "BAND_3";
        }
        if (i == 4) {
            return "BAND_4";
        }
        if (i == 5) {
            return "BAND_5";
        }
        if (i == 6) {
            return "BAND_6";
        }
        if (i == 7) {
            return "BAND_7";
        }
        if (i == 8) {
            return "BAND_8";
        }
        if (i == 9) {
            return "BAND_9";
        }
        if (i == 10) {
            return "BAND_10";
        }
        if (i == 11) {
            return "BAND_11";
        }
        if (i == 12) {
            return "BAND_12";
        }
        if (i == 13) {
            return "BAND_13";
        }
        if (i == 14) {
            return "BAND_14";
        }
        if (i == 19) {
            return "BAND_19";
        }
        if (i == 20) {
            return "BAND_20";
        }
        if (i == 21) {
            return "BAND_21";
        }
        if (i == 22) {
            return "BAND_22";
        }
        if (i == 25) {
            return "BAND_25";
        }
        if (i == 26) {
            return "BAND_26";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("BAND_1");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("BAND_2");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("BAND_3");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("BAND_4");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("BAND_5");
            i2 |= 5;
        }
        if ((i & 6) == 6) {
            arrayList.add("BAND_6");
            i2 |= 6;
        }
        if ((i & 7) == 7) {
            arrayList.add("BAND_7");
            i2 = 7;
        }
        if ((i & 8) == 8) {
            arrayList.add("BAND_8");
            i2 |= 8;
        }
        if ((i & 9) == 9) {
            arrayList.add("BAND_9");
            i2 |= 9;
        }
        if ((i & 10) == 10) {
            arrayList.add("BAND_10");
            i2 |= 10;
        }
        if ((i & 11) == 11) {
            arrayList.add("BAND_11");
            i2 |= 11;
        }
        if ((i & 12) == 12) {
            arrayList.add("BAND_12");
            i2 |= 12;
        }
        if ((i & 13) == 13) {
            arrayList.add("BAND_13");
            i2 |= 13;
        }
        if ((i & 14) == 14) {
            arrayList.add("BAND_14");
            i2 |= 14;
        }
        if ((i & 19) == 19) {
            arrayList.add("BAND_19");
            i2 |= 19;
        }
        if ((i & 20) == 20) {
            arrayList.add("BAND_20");
            i2 |= 20;
        }
        if ((i & 21) == 21) {
            arrayList.add("BAND_21");
            i2 |= 21;
        }
        if ((i & 22) == 22) {
            arrayList.add("BAND_22");
            i2 |= 22;
        }
        if ((i & 25) == 25) {
            arrayList.add("BAND_25");
            i2 |= 25;
        }
        if ((i & 26) == 26) {
            arrayList.add("BAND_26");
            i2 |= 26;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
