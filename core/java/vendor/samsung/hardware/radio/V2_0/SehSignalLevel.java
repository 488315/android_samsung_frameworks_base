package vendor.samsung.hardware.radio.V2_0;

import android.security.keystore.KeyProperties;

import java.util.ArrayList;

public final class SehSignalLevel {
    public static final int EXCELLLENT = 5;
    public static final int GOOD = 3;
    public static final int GREAT = 4;
    public static final int MODERATE = 2;
    public static final int NONE = 0;
    public static final int POOR = 1;

    public static final String toString(int o) {
        if (o == 0) {
            return KeyProperties.DIGEST_NONE;
        }
        if (o == 1) {
            return "POOR";
        }
        if (o == 2) {
            return "MODERATE";
        }
        if (o == 3) {
            return "GOOD";
        }
        if (o == 4) {
            return "GREAT";
        }
        if (o == 5) {
            return "EXCELLLENT";
        }
        return "0x" + Integer.toHexString(o);
    }

    public static final String dumpBitfield(int o) {
        ArrayList<String> list = new ArrayList<>();
        int flipped = 0;
        list.add(KeyProperties.DIGEST_NONE);
        if ((o & 1) == 1) {
            list.add("POOR");
            flipped = 0 | 1;
        }
        if ((o & 2) == 2) {
            list.add("MODERATE");
            flipped |= 2;
        }
        if ((o & 3) == 3) {
            list.add("GOOD");
            flipped |= 3;
        }
        if ((o & 4) == 4) {
            list.add("GREAT");
            flipped |= 4;
        }
        if ((o & 5) == 5) {
            list.add("EXCELLLENT");
            flipped |= 5;
        }
        if (o != flipped) {
            list.add("0x" + Integer.toHexString((~flipped) & o));
        }
        return String.join(" | ", list);
    }
}
