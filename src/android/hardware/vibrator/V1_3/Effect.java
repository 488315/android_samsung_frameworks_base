package android.hardware.vibrator.V1_3;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class Effect {
    public static final int CLICK = 0;
    public static final int DOUBLE_CLICK = 1;
    public static final int HEAVY_CLICK = 5;
    public static final int POP = 4;
    public static final int RINGTONE_1 = 6;
    public static final int RINGTONE_10 = 15;
    public static final int RINGTONE_11 = 16;
    public static final int RINGTONE_12 = 17;
    public static final int RINGTONE_13 = 18;
    public static final int RINGTONE_14 = 19;
    public static final int RINGTONE_15 = 20;
    public static final int RINGTONE_2 = 7;
    public static final int RINGTONE_3 = 8;
    public static final int RINGTONE_4 = 9;
    public static final int RINGTONE_5 = 10;
    public static final int RINGTONE_6 = 11;
    public static final int RINGTONE_7 = 12;
    public static final int RINGTONE_8 = 13;
    public static final int RINGTONE_9 = 14;
    public static final int TEXTURE_TICK = 21;
    public static final int THUD = 3;
    public static final int TICK = 2;

    public static final String toString(int i) {
        if (i == 0) {
            return "CLICK";
        }
        if (i == 1) {
            return "DOUBLE_CLICK";
        }
        if (i == 2) {
            return "TICK";
        }
        if (i == 3) {
            return "THUD";
        }
        if (i == 4) {
            return "POP";
        }
        if (i == 5) {
            return "HEAVY_CLICK";
        }
        if (i == 6) {
            return "RINGTONE_1";
        }
        if (i == 7) {
            return "RINGTONE_2";
        }
        if (i == 8) {
            return "RINGTONE_3";
        }
        if (i == 9) {
            return "RINGTONE_4";
        }
        if (i == 10) {
            return "RINGTONE_5";
        }
        if (i == 11) {
            return "RINGTONE_6";
        }
        if (i == 12) {
            return "RINGTONE_7";
        }
        if (i == 13) {
            return "RINGTONE_8";
        }
        if (i == 14) {
            return "RINGTONE_9";
        }
        if (i == 15) {
            return "RINGTONE_10";
        }
        if (i == 16) {
            return "RINGTONE_11";
        }
        if (i == 17) {
            return "RINGTONE_12";
        }
        if (i == 18) {
            return "RINGTONE_13";
        }
        if (i == 19) {
            return "RINGTONE_14";
        }
        if (i == 20) {
            return "RINGTONE_15";
        }
        if (i == 21) {
            return "TEXTURE_TICK";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("CLICK");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("DOUBLE_CLICK");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("TICK");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("THUD");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("POP");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("HEAVY_CLICK");
            i2 |= 5;
        }
        if ((i & 6) == 6) {
            arrayList.add("RINGTONE_1");
            i2 |= 6;
        }
        if ((i & 7) == 7) {
            arrayList.add("RINGTONE_2");
            i2 = 7;
        }
        if ((i & 8) == 8) {
            arrayList.add("RINGTONE_3");
            i2 |= 8;
        }
        if ((i & 9) == 9) {
            arrayList.add("RINGTONE_4");
            i2 |= 9;
        }
        if ((i & 10) == 10) {
            arrayList.add("RINGTONE_5");
            i2 |= 10;
        }
        if ((i & 11) == 11) {
            arrayList.add("RINGTONE_6");
            i2 |= 11;
        }
        if ((i & 12) == 12) {
            arrayList.add("RINGTONE_7");
            i2 |= 12;
        }
        if ((i & 13) == 13) {
            arrayList.add("RINGTONE_8");
            i2 |= 13;
        }
        if ((i & 14) == 14) {
            arrayList.add("RINGTONE_9");
            i2 |= 14;
        }
        if ((i & 15) == 15) {
            arrayList.add("RINGTONE_10");
            i2 = 15;
        }
        if ((i & 16) == 16) {
            arrayList.add("RINGTONE_11");
            i2 |= 16;
        }
        if ((i & 17) == 17) {
            arrayList.add("RINGTONE_12");
            i2 |= 17;
        }
        if ((i & 18) == 18) {
            arrayList.add("RINGTONE_13");
            i2 |= 18;
        }
        if ((i & 19) == 19) {
            arrayList.add("RINGTONE_14");
            i2 |= 19;
        }
        if ((i & 20) == 20) {
            arrayList.add("RINGTONE_15");
            i2 |= 20;
        }
        if ((i & 21) == 21) {
            arrayList.add("TEXTURE_TICK");
            i2 |= 21;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
