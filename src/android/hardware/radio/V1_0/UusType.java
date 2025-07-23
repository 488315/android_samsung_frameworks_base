package android.hardware.radio.V1_0;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class UusType {
    public static final int TYPE1_IMPLICIT = 0;
    public static final int TYPE1_NOT_REQUIRED = 2;
    public static final int TYPE1_REQUIRED = 1;
    public static final int TYPE2_NOT_REQUIRED = 4;
    public static final int TYPE2_REQUIRED = 3;
    public static final int TYPE3_NOT_REQUIRED = 6;
    public static final int TYPE3_REQUIRED = 5;

    public static final String toString(int i) {
        if (i == 0) {
            return "TYPE1_IMPLICIT";
        }
        if (i == 1) {
            return "TYPE1_REQUIRED";
        }
        if (i == 2) {
            return "TYPE1_NOT_REQUIRED";
        }
        if (i == 3) {
            return "TYPE2_REQUIRED";
        }
        if (i == 4) {
            return "TYPE2_NOT_REQUIRED";
        }
        if (i == 5) {
            return "TYPE3_REQUIRED";
        }
        if (i == 6) {
            return "TYPE3_NOT_REQUIRED";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("TYPE1_IMPLICIT");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("TYPE1_REQUIRED");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("TYPE1_NOT_REQUIRED");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("TYPE2_REQUIRED");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("TYPE2_NOT_REQUIRED");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("TYPE3_REQUIRED");
            i2 |= 5;
        }
        if ((i & 6) == 6) {
            arrayList.add("TYPE3_NOT_REQUIRED");
            i2 |= 6;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
