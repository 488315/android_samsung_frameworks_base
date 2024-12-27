package vendor.samsung.hardware.radio.V2_0;

import java.util.ArrayList;

public final class SehHandoverState {
    public static final int INTER_HANDOVER_FAILED = 1;
    public static final int INTER_HANDOVER_STARTED = 0;
    public static final int INTER_HANDOVER_SUCCESS = 2;
    public static final int INTRA_HANDOVER_FAILED = 4;
    public static final int INTRA_HANDOVER_STARTED = 3;
    public static final int INTRA_HANDOVER_SUCCESS = 5;
    public static final int MEASUREMENT_REPORT_DELIVERED = 6;
    public static final int UNKNOWN = -1;

    public static final String toString(int o) {
        if (o == -1) {
            return "UNKNOWN";
        }
        if (o == 0) {
            return "INTER_HANDOVER_STARTED";
        }
        if (o == 1) {
            return "INTER_HANDOVER_FAILED";
        }
        if (o == 2) {
            return "INTER_HANDOVER_SUCCESS";
        }
        if (o == 3) {
            return "INTRA_HANDOVER_STARTED";
        }
        if (o == 4) {
            return "INTRA_HANDOVER_FAILED";
        }
        if (o == 5) {
            return "INTRA_HANDOVER_SUCCESS";
        }
        if (o == 6) {
            return "MEASUREMENT_REPORT_DELIVERED";
        }
        return "0x" + Integer.toHexString(o);
    }

    public static final String dumpBitfield(int o) {
        ArrayList<String> list = new ArrayList<>();
        int flipped = 0;
        if ((o & (-1)) == -1) {
            list.add("UNKNOWN");
            flipped = 0 | (-1);
        }
        list.add("INTER_HANDOVER_STARTED");
        if ((o & 1) == 1) {
            list.add("INTER_HANDOVER_FAILED");
            flipped |= 1;
        }
        if ((o & 2) == 2) {
            list.add("INTER_HANDOVER_SUCCESS");
            flipped |= 2;
        }
        if ((o & 3) == 3) {
            list.add("INTRA_HANDOVER_STARTED");
            flipped |= 3;
        }
        if ((o & 4) == 4) {
            list.add("INTRA_HANDOVER_FAILED");
            flipped |= 4;
        }
        if ((o & 5) == 5) {
            list.add("INTRA_HANDOVER_SUCCESS");
            flipped |= 5;
        }
        if ((o & 6) == 6) {
            list.add("MEASUREMENT_REPORT_DELIVERED");
            flipped |= 6;
        }
        if (o != flipped) {
            list.add("0x" + Integer.toHexString((~flipped) & o));
        }
        return String.join(" | ", list);
    }
}
