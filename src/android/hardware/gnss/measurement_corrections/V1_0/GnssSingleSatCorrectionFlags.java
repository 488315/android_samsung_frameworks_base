package android.hardware.gnss.measurement_corrections.V1_0;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class GnssSingleSatCorrectionFlags {
    public static final short HAS_EXCESS_PATH_LENGTH = 2;
    public static final short HAS_EXCESS_PATH_LENGTH_UNC = 4;
    public static final short HAS_REFLECTING_PLANE = 8;
    public static final short HAS_SAT_IS_LOS_PROBABILITY = 1;

    public static final String toString(short s) {
        if (s == 1) {
            return "HAS_SAT_IS_LOS_PROBABILITY";
        }
        if (s == 2) {
            return "HAS_EXCESS_PATH_LENGTH";
        }
        if (s == 4) {
            return "HAS_EXCESS_PATH_LENGTH_UNC";
        }
        if (s == 8) {
            return "HAS_REFLECTING_PLANE";
        }
        return "0x" + Integer.toHexString(Short.toUnsignedInt(s));
    }

    public static final String dumpBitfield(short s) {
        short s2;
        ArrayList arrayList = new ArrayList();
        if ((s & 1) == 1) {
            arrayList.add("HAS_SAT_IS_LOS_PROBABILITY");
            s2 = (short) 1;
        } else {
            s2 = 0;
        }
        if ((s & 2) == 2) {
            arrayList.add("HAS_EXCESS_PATH_LENGTH");
            s2 = (short) (s2 | 2);
        }
        if ((s & 4) == 4) {
            arrayList.add("HAS_EXCESS_PATH_LENGTH_UNC");
            s2 = (short) (s2 | 4);
        }
        if ((s & 8) == 8) {
            arrayList.add("HAS_REFLECTING_PLANE");
            s2 = (short) (s2 | 8);
        }
        if (s != s2) {
            arrayList.add("0x" + Integer.toHexString(Short.toUnsignedInt((short) (s & (~s2)))));
        }
        return String.join(" | ", arrayList);
    }
}
