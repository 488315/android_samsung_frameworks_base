package vendor.samsung.hardware.radio.V2_0;

import java.util.ArrayList;

public final class SehFallBackState {
    public static final int FALLBACK_TO_CS = 1;
    public static final int FALLBACK_TO_VOLTE = 2;
    public static final int FALLBACK_TO_VOWIFI = 3;

    public static final String toString(int o) {
        if (o == 1) {
            return "FALLBACK_TO_CS";
        }
        if (o == 2) {
            return "FALLBACK_TO_VOLTE";
        }
        if (o == 3) {
            return "FALLBACK_TO_VOWIFI";
        }
        return "0x" + Integer.toHexString(o);
    }

    public static final String dumpBitfield(int o) {
        ArrayList<String> list = new ArrayList<>();
        int flipped = 0;
        if ((o & 1) == 1) {
            list.add("FALLBACK_TO_CS");
            flipped = 0 | 1;
        }
        if ((o & 2) == 2) {
            list.add("FALLBACK_TO_VOLTE");
            flipped |= 2;
        }
        if ((o & 3) == 3) {
            list.add("FALLBACK_TO_VOWIFI");
            flipped |= 3;
        }
        if (o != flipped) {
            list.add("0x" + Integer.toHexString((~flipped) & o));
        }
        return String.join(" | ", list);
    }
}
