package vendor.samsung.hardware.thermal.V1_0;

import java.util.ArrayList;

/* loaded from: classes6.dex */
public final class SehTempStatus {
    public static final int DISABLED = -777;
    public static final int NOT_EXIST = -999;
    public static final int NOT_READABLE = -888;

    public static final String toString(int o) {
        if (o == -999) {
            return "NOT_EXIST";
        }
        if (o == -888) {
            return "NOT_READABLE";
        }
        if (o == -777) {
            return "DISABLED";
        }
        return "0x" + Integer.toHexString(o);
    }

    public static final String dumpBitfield(int o) {
        ArrayList<String> list = new ArrayList<>();
        int flipped = 0;
        if ((o & (-999)) == -999) {
            list.add("NOT_EXIST");
            flipped = 0 | (-999);
        }
        if ((o & NOT_READABLE) == -888) {
            list.add("NOT_READABLE");
            flipped |= NOT_READABLE;
        }
        if ((o & DISABLED) == -777) {
            list.add("DISABLED");
            flipped |= DISABLED;
        }
        if (o != flipped) {
            list.add("0x" + Integer.toHexString((~flipped) & o));
        }
        return String.join(" | ", list);
    }
}
