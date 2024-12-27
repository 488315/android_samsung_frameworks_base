package vendor.samsung.hardware.radio.V2_0;

import java.util.ArrayList;

/* loaded from: classes6.dex */
public final class SehBearerStatus {
    public static final int NR_BEARER_STATUS_ALLOCATED = 1;
    public static final int NR_BEARER_STATUS_MMW_ALLOCATED = 2;
    public static final int NR_BEARER_STATUS_NOT_ALLOCATED = 0;

    public static final String toString(int o) {
        if (o == 0) {
            return "NR_BEARER_STATUS_NOT_ALLOCATED";
        }
        if (o == 1) {
            return "NR_BEARER_STATUS_ALLOCATED";
        }
        if (o == 2) {
            return "NR_BEARER_STATUS_MMW_ALLOCATED";
        }
        return "0x" + Integer.toHexString(o);
    }

    public static final String dumpBitfield(int o) {
        ArrayList<String> list = new ArrayList<>();
        int flipped = 0;
        list.add("NR_BEARER_STATUS_NOT_ALLOCATED");
        if ((o & 1) == 1) {
            list.add("NR_BEARER_STATUS_ALLOCATED");
            flipped = 0 | 1;
        }
        if ((o & 2) == 2) {
            list.add("NR_BEARER_STATUS_MMW_ALLOCATED");
            flipped |= 2;
        }
        if (o != flipped) {
            list.add("0x" + Integer.toHexString((~flipped) & o));
        }
        return String.join(" | ", list);
    }
}
