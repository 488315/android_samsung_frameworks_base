package vendor.samsung.hardware.radio.V2_1;

import java.util.ArrayList;

public final class SehNrMode {
    public static final int DISABLE_NONE = 0;
    public static final int DISABLE_NSA = 2;
    public static final int DISABLE_SA = 1;

    public static final String toString(int o) {
        if (o == 0) {
            return "DISABLE_NONE";
        }
        if (o == 1) {
            return "DISABLE_SA";
        }
        if (o == 2) {
            return "DISABLE_NSA";
        }
        return "0x" + Integer.toHexString(o);
    }

    public static final String dumpBitfield(int o) {
        ArrayList<String> list = new ArrayList<>();
        int flipped = 0;
        list.add("DISABLE_NONE");
        if ((o & 1) == 1) {
            list.add("DISABLE_SA");
            flipped = 0 | 1;
        }
        if ((o & 2) == 2) {
            list.add("DISABLE_NSA");
            flipped |= 2;
        }
        if (o != flipped) {
            list.add("0x" + Integer.toHexString((~flipped) & o));
        }
        return String.join(" | ", list);
    }
}
