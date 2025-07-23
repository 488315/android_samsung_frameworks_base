package android.hardware.contexthub.V1_1;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class Setting {
    public static final byte LOCATION = 0;

    public static final String toString(byte b) {
        if (b == 0) {
            return "LOCATION";
        }
        return "0x" + Integer.toHexString(Byte.toUnsignedInt(b));
    }

    public static final String dumpBitfield(byte b) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("LOCATION");
        if (b != 0) {
            arrayList.add("0x" + Integer.toHexString(Byte.toUnsignedInt(b)));
        }
        return String.join(" | ", arrayList);
    }
}
