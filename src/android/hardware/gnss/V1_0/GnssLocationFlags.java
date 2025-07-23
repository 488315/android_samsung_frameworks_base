package android.hardware.gnss.V1_0;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class GnssLocationFlags {
    public static final short HAS_ALTITUDE = 2;
    public static final short HAS_BEARING = 8;
    public static final short HAS_BEARING_ACCURACY = 128;
    public static final short HAS_HORIZONTAL_ACCURACY = 16;
    public static final short HAS_LAT_LONG = 1;
    public static final short HAS_SPEED = 4;
    public static final short HAS_SPEED_ACCURACY = 64;
    public static final short HAS_VERTICAL_ACCURACY = 32;

    public static final String toString(short s) {
        if (s == 1) {
            return "HAS_LAT_LONG";
        }
        if (s == 2) {
            return "HAS_ALTITUDE";
        }
        if (s == 4) {
            return "HAS_SPEED";
        }
        if (s == 8) {
            return "HAS_BEARING";
        }
        if (s == 16) {
            return "HAS_HORIZONTAL_ACCURACY";
        }
        if (s == 32) {
            return "HAS_VERTICAL_ACCURACY";
        }
        if (s == 64) {
            return "HAS_SPEED_ACCURACY";
        }
        if (s == 128) {
            return "HAS_BEARING_ACCURACY";
        }
        return "0x" + Integer.toHexString(Short.toUnsignedInt(s));
    }

    public static final String dumpBitfield(short s) {
        short s2;
        ArrayList arrayList = new ArrayList();
        if ((s & 1) == 1) {
            arrayList.add("HAS_LAT_LONG");
            s2 = (short) 1;
        } else {
            s2 = 0;
        }
        if ((s & 2) == 2) {
            arrayList.add("HAS_ALTITUDE");
            s2 = (short) (s2 | 2);
        }
        if ((s & 4) == 4) {
            arrayList.add("HAS_SPEED");
            s2 = (short) (s2 | 4);
        }
        if ((s & 8) == 8) {
            arrayList.add("HAS_BEARING");
            s2 = (short) (s2 | 8);
        }
        if ((s & 16) == 16) {
            arrayList.add("HAS_HORIZONTAL_ACCURACY");
            s2 = (short) (s2 | 16);
        }
        if ((s & 32) == 32) {
            arrayList.add("HAS_VERTICAL_ACCURACY");
            s2 = (short) (s2 | 32);
        }
        if ((s & 64) == 64) {
            arrayList.add("HAS_SPEED_ACCURACY");
            s2 = (short) (s2 | 64);
        }
        if ((s & 128) == 128) {
            arrayList.add("HAS_BEARING_ACCURACY");
            s2 = (short) (s2 | 128);
        }
        if (s != s2) {
            arrayList.add("0x" + Integer.toHexString(Short.toUnsignedInt((short) (s & (~s2)))));
        }
        return String.join(" | ", arrayList);
    }
}
