package vendor.samsung.hardware.radio.V2_0;

import java.util.ArrayList;

/* loaded from: classes6.dex */
public final class SehBearerStatus {
    public static final int NR_BEARER_STATUS_ALLOCATED = 1;
    public static final int NR_BEARER_STATUS_MMW_ALLOCATED = 2;
    public static final int NR_BEARER_STATUS_NOT_ALLOCATED = 0;

    public static final String toString(int i) {
        if (i == 0) {
            return "NR_BEARER_STATUS_NOT_ALLOCATED";
        }
        if (i == 1) {
            return "NR_BEARER_STATUS_ALLOCATED";
        }
        if (i == 2) {
            return "NR_BEARER_STATUS_MMW_ALLOCATED";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("NR_BEARER_STATUS_NOT_ALLOCATED");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("NR_BEARER_STATUS_ALLOCATED");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("NR_BEARER_STATUS_MMW_ALLOCATED");
            i2 |= 2;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
