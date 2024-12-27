package vendor.samsung.hardware.thermal.V1_0;

import java.util.ArrayList;

/* loaded from: classes6.dex */
public final class SehTempScope {
    public static final int VALID_MAX = 900;
    public static final int VALID_MIN = -300;

    public static final String toString(int o) {
        if (o == -300) {
            return "VALID_MIN";
        }
        if (o == 900) {
            return "VALID_MAX";
        }
        return "0x" + Integer.toHexString(o);
    }

    public static final String dumpBitfield(int o) {
        ArrayList<String> list = new ArrayList<>();
        int flipped = 0;
        if ((o & VALID_MIN) == -300) {
            list.add("VALID_MIN");
            flipped = 0 | VALID_MIN;
        }
        if ((o & 900) == 900) {
            list.add("VALID_MAX");
            flipped |= 900;
        }
        if (o != flipped) {
            list.add("0x" + Integer.toHexString((~flipped) & o));
        }
        return String.join(" | ", list);
    }
}
