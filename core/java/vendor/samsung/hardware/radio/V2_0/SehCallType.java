package vendor.samsung.hardware.radio.V2_0;

import java.util.ArrayList;

public final class SehCallType {
    public static final int VOICE = 0;
    public static final int VS_RX = 2;
    public static final int VS_TX = 1;
    public static final int VT = 3;

    public static final String toString(int o) {
        if (o == 0) {
            return "VOICE";
        }
        if (o == 1) {
            return "VS_TX";
        }
        if (o == 2) {
            return "VS_RX";
        }
        if (o == 3) {
            return "VT";
        }
        return "0x" + Integer.toHexString(o);
    }

    public static final String dumpBitfield(int o) {
        ArrayList<String> list = new ArrayList<>();
        int flipped = 0;
        list.add("VOICE");
        if ((o & 1) == 1) {
            list.add("VS_TX");
            flipped = 0 | 1;
        }
        if ((o & 2) == 2) {
            list.add("VS_RX");
            flipped |= 2;
        }
        if ((o & 3) == 3) {
            list.add("VT");
            flipped |= 3;
        }
        if (o != flipped) {
            list.add("0x" + Integer.toHexString((~flipped) & o));
        }
        return String.join(" | ", list);
    }
}
