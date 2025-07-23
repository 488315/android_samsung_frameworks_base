package android.hardware.radio.V1_0;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class CdmaSmsNumberPlan {
    public static final int DATA = 3;
    public static final int PRIVATE = 9;
    public static final int RESERVED_10 = 10;
    public static final int RESERVED_11 = 11;
    public static final int RESERVED_12 = 12;
    public static final int RESERVED_13 = 13;
    public static final int RESERVED_14 = 14;
    public static final int RESERVED_15 = 15;
    public static final int RESERVED_2 = 2;
    public static final int RESERVED_5 = 5;
    public static final int RESERVED_6 = 6;
    public static final int RESERVED_7 = 7;
    public static final int RESERVED_8 = 8;
    public static final int TELEPHONY = 1;
    public static final int TELEX = 4;
    public static final int UNKNOWN = 0;

    public static final String toString(int i) {
        if (i == 0) {
            return "UNKNOWN";
        }
        if (i == 1) {
            return "TELEPHONY";
        }
        if (i == 2) {
            return "RESERVED_2";
        }
        if (i == 3) {
            return "DATA";
        }
        if (i == 4) {
            return "TELEX";
        }
        if (i == 5) {
            return "RESERVED_5";
        }
        if (i == 6) {
            return "RESERVED_6";
        }
        if (i == 7) {
            return "RESERVED_7";
        }
        if (i == 8) {
            return "RESERVED_8";
        }
        if (i == 9) {
            return "PRIVATE";
        }
        if (i == 10) {
            return "RESERVED_10";
        }
        if (i == 11) {
            return "RESERVED_11";
        }
        if (i == 12) {
            return "RESERVED_12";
        }
        if (i == 13) {
            return "RESERVED_13";
        }
        if (i == 14) {
            return "RESERVED_14";
        }
        if (i == 15) {
            return "RESERVED_15";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("UNKNOWN");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("TELEPHONY");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("RESERVED_2");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("DATA");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("TELEX");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("RESERVED_5");
            i2 |= 5;
        }
        if ((i & 6) == 6) {
            arrayList.add("RESERVED_6");
            i2 |= 6;
        }
        if ((i & 7) == 7) {
            arrayList.add("RESERVED_7");
            i2 = 7;
        }
        if ((i & 8) == 8) {
            arrayList.add("RESERVED_8");
            i2 |= 8;
        }
        if ((i & 9) == 9) {
            arrayList.add("PRIVATE");
            i2 |= 9;
        }
        if ((i & 10) == 10) {
            arrayList.add("RESERVED_10");
            i2 |= 10;
        }
        if ((i & 11) == 11) {
            arrayList.add("RESERVED_11");
            i2 |= 11;
        }
        if ((i & 12) == 12) {
            arrayList.add("RESERVED_12");
            i2 |= 12;
        }
        if ((i & 13) == 13) {
            arrayList.add("RESERVED_13");
            i2 |= 13;
        }
        if ((i & 14) == 14) {
            arrayList.add("RESERVED_14");
            i2 |= 14;
        }
        if ((i & 15) == 15) {
            arrayList.add("RESERVED_15");
            i2 = 15;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
