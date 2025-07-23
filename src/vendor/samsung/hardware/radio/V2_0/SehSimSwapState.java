package vendor.samsung.hardware.radio.V2_0;

import java.util.ArrayList;

/* loaded from: classes6.dex */
public final class SehSimSwapState {
    public static final int SIM_SWAP_ADDED = 1;
    public static final int SIM_SWAP_REMOVED = 0;
    public static final int SIM_TRAY_ADDED = 3;
    public static final int SIM_TRAY_REMOVED = 2;

    public static final String toString(int i) {
        if (i == 0) {
            return "SIM_SWAP_REMOVED";
        }
        if (i == 1) {
            return "SIM_SWAP_ADDED";
        }
        if (i == 2) {
            return "SIM_TRAY_REMOVED";
        }
        if (i == 3) {
            return "SIM_TRAY_ADDED";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("SIM_SWAP_REMOVED");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("SIM_SWAP_ADDED");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("SIM_TRAY_REMOVED");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("SIM_TRAY_ADDED");
            i2 = 3;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
