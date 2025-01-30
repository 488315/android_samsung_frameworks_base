package vendor.samsung.hardware.radio.V2_0;

import java.util.ArrayList;

/* loaded from: classes6.dex */
public final class SehEnableStatus {
    public static final int NR_NETWORK_TYPE_EXCLUDED = 0;
    public static final int NR_NETWORK_TYPE_INCLUDED = 1;

    public static final String toString(int o) {
        if (o == 0) {
            return "NR_NETWORK_TYPE_EXCLUDED";
        }
        if (o == 1) {
            return "NR_NETWORK_TYPE_INCLUDED";
        }
        return "0x" + Integer.toHexString(o);
    }

    public static final String dumpBitfield(int o) {
        ArrayList<String> list = new ArrayList<>();
        int flipped = 0;
        list.add("NR_NETWORK_TYPE_EXCLUDED");
        if ((o & 1) == 1) {
            list.add("NR_NETWORK_TYPE_INCLUDED");
            flipped = 0 | 1;
        }
        if (o != flipped) {
            list.add("0x" + Integer.toHexString((~flipped) & o));
        }
        return String.join(" | ", list);
    }
}
