package vendor.samsung.hardware.radio.V2_1;

import android.security.keystore.KeyProperties;

import java.util.ArrayList;

/* loaded from: classes6.dex */
public final class SehNrIconType {
    public static final int BASIC = 1;
    public static final int INVALID = -1;
    public static final int NONE = 0;
    public static final int UWB = 2;

    public static final String toString(int o) {
        if (o == -1) {
            return "INVALID";
        }
        if (o == 0) {
            return KeyProperties.DIGEST_NONE;
        }
        if (o == 1) {
            return "BASIC";
        }
        if (o == 2) {
            return "UWB";
        }
        return "0x" + Integer.toHexString(o);
    }

    public static final String dumpBitfield(int o) {
        ArrayList<String> list = new ArrayList<>();
        int flipped = 0;
        if ((o & (-1)) == -1) {
            list.add("INVALID");
            flipped = 0 | (-1);
        }
        list.add(KeyProperties.DIGEST_NONE);
        if ((o & 1) == 1) {
            list.add("BASIC");
            flipped |= 1;
        }
        if ((o & 2) == 2) {
            list.add("UWB");
            flipped |= 2;
        }
        if (o != flipped) {
            list.add("0x" + Integer.toHexString((~flipped) & o));
        }
        return String.join(" | ", list);
    }
}
