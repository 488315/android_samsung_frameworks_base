package vendor.samsung.hardware.sysinput.V1_3;

import java.util.ArrayList;

/* loaded from: classes.dex */
public final class Result {
    public static final int BAD_STATUS = -4;
    public static final int BAD_VALUE = -8;
    public static final int INVALID_OPERATION = -12;
    public static final int NOK = -1;
    public static final int OK = 0;

    public static final String toString(int o) {
        if (o == 0) {
            return "OK";
        }
        if (o == -1) {
            return "NOK";
        }
        if (o == -4) {
            return "BAD_STATUS";
        }
        if (o == -8) {
            return "BAD_VALUE";
        }
        if (o == -12) {
            return "INVALID_OPERATION";
        }
        return "0x" + Integer.toHexString(o);
    }

    public static final String dumpBitfield(int o) {
        ArrayList<String> list = new ArrayList<>();
        int flipped = 0;
        list.add("OK");
        if ((o & (-1)) == -1) {
            list.add("NOK");
            flipped = 0 | (-1);
        }
        if ((o & (-4)) == -4) {
            list.add("BAD_STATUS");
            flipped |= -4;
        }
        if ((o & (-8)) == -8) {
            list.add("BAD_VALUE");
            flipped |= -8;
        }
        if ((o & (-12)) == -12) {
            list.add("INVALID_OPERATION");
            flipped |= -12;
        }
        if (o != flipped) {
            list.add("0x" + Integer.toHexString((~flipped) & o));
        }
        return String.join(" | ", list);
    }
}
