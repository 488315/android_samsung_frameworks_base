package android.hardware.radio.V1_0;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class SsServiceType {
    public static final int ALL_BARRING = 16;
    public static final int BAIC = 14;
    public static final int BAIC_ROAMING = 15;
    public static final int BAOC = 11;
    public static final int BAOIC = 12;
    public static final int BAOIC_EXC_HOME = 13;
    public static final int CFU = 0;
    public static final int CF_ALL = 4;
    public static final int CF_ALL_CONDITIONAL = 5;
    public static final int CF_BUSY = 1;
    public static final int CF_NOT_REACHABLE = 3;
    public static final int CF_NO_REPLY = 2;
    public static final int CLIP = 6;
    public static final int CLIR = 7;
    public static final int COLP = 8;
    public static final int COLR = 9;
    public static final int INCOMING_BARRING = 18;
    public static final int OUTGOING_BARRING = 17;
    public static final int WAIT = 10;

    public static final String toString(int i) {
        if (i == 0) {
            return "CFU";
        }
        if (i == 1) {
            return "CF_BUSY";
        }
        if (i == 2) {
            return "CF_NO_REPLY";
        }
        if (i == 3) {
            return "CF_NOT_REACHABLE";
        }
        if (i == 4) {
            return "CF_ALL";
        }
        if (i == 5) {
            return "CF_ALL_CONDITIONAL";
        }
        if (i == 6) {
            return "CLIP";
        }
        if (i == 7) {
            return "CLIR";
        }
        if (i == 8) {
            return "COLP";
        }
        if (i == 9) {
            return "COLR";
        }
        if (i == 10) {
            return "WAIT";
        }
        if (i == 11) {
            return "BAOC";
        }
        if (i == 12) {
            return "BAOIC";
        }
        if (i == 13) {
            return "BAOIC_EXC_HOME";
        }
        if (i == 14) {
            return "BAIC";
        }
        if (i == 15) {
            return "BAIC_ROAMING";
        }
        if (i == 16) {
            return "ALL_BARRING";
        }
        if (i == 17) {
            return "OUTGOING_BARRING";
        }
        if (i == 18) {
            return "INCOMING_BARRING";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("CFU");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("CF_BUSY");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("CF_NO_REPLY");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("CF_NOT_REACHABLE");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("CF_ALL");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("CF_ALL_CONDITIONAL");
            i2 |= 5;
        }
        if ((i & 6) == 6) {
            arrayList.add("CLIP");
            i2 |= 6;
        }
        if ((i & 7) == 7) {
            arrayList.add("CLIR");
            i2 = 7;
        }
        if ((i & 8) == 8) {
            arrayList.add("COLP");
            i2 |= 8;
        }
        if ((i & 9) == 9) {
            arrayList.add("COLR");
            i2 |= 9;
        }
        if ((i & 10) == 10) {
            arrayList.add("WAIT");
            i2 |= 10;
        }
        if ((i & 11) == 11) {
            arrayList.add("BAOC");
            i2 |= 11;
        }
        if ((i & 12) == 12) {
            arrayList.add("BAOIC");
            i2 |= 12;
        }
        if ((i & 13) == 13) {
            arrayList.add("BAOIC_EXC_HOME");
            i2 |= 13;
        }
        if ((i & 14) == 14) {
            arrayList.add("BAIC");
            i2 |= 14;
        }
        if ((i & 15) == 15) {
            arrayList.add("BAIC_ROAMING");
            i2 = 15;
        }
        if ((i & 16) == 16) {
            arrayList.add("ALL_BARRING");
            i2 |= 16;
        }
        if ((i & 17) == 17) {
            arrayList.add("OUTGOING_BARRING");
            i2 |= 17;
        }
        if ((i & 18) == 18) {
            arrayList.add("INCOMING_BARRING");
            i2 |= 18;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
