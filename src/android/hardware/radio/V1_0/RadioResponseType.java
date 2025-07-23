package android.hardware.radio.V1_0;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class RadioResponseType {
    public static final int SOLICITED = 0;
    public static final int SOLICITED_ACK = 1;
    public static final int SOLICITED_ACK_EXP = 2;

    public static final String toString(int i) {
        if (i == 0) {
            return "SOLICITED";
        }
        if (i == 1) {
            return "SOLICITED_ACK";
        }
        if (i == 2) {
            return "SOLICITED_ACK_EXP";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("SOLICITED");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("SOLICITED_ACK");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("SOLICITED_ACK_EXP");
            i2 |= 2;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
