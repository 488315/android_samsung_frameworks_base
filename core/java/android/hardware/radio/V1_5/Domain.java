package android.hardware.radio.V1_5;

import java.util.ArrayList;

public final class Domain {
    public static final int CS = 1;
    public static final int PS = 2;

    public static final String toString(int o) {
        if (o == 1) {
            return "CS";
        }
        if (o == 2) {
            return "PS";
        }
        return "0x" + Integer.toHexString(o);
    }

    public static final String dumpBitfield(int o) {
        ArrayList<String> list = new ArrayList<>();
        int flipped = 0;
        if ((o & 1) == 1) {
            list.add("CS");
            flipped = 0 | 1;
        }
        if ((o & 2) == 2) {
            list.add("PS");
            flipped |= 2;
        }
        if (o != flipped) {
            list.add("0x" + Integer.toHexString((~flipped) & o));
        }
        return String.join(" | ", list);
    }
}
