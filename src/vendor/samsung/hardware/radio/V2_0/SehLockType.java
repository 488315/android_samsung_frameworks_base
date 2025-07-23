package vendor.samsung.hardware.radio.V2_0;

import android.security.keystore.KeyProperties;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public final class SehLockType {
    public static final int ACL = 11;
    public static final int FD = 4;
    public static final int NONE = 0;
    public static final int NO_SIM = 16;
    public static final int ONE = 13;
    public static final int PC = 8;
    public static final int PF = 2;
    public static final int PN = 5;
    public static final int POLICY = 15;
    public static final int PP = 7;
    public static final int PS = 1;
    public static final int PU = 6;
    public static final int PUK2 = 10;
    public static final int REG = 12;
    public static final int SC = 3;
    public static final int SC2 = 9;
    public static final int SEP = 14;
    public static final int UNAVAIL = 17;
    public static final int UNSPECIFIED = -1;

    public static final String toString(int i) {
        if (i == -1) {
            return "UNSPECIFIED";
        }
        if (i == 0) {
            return KeyProperties.DIGEST_NONE;
        }
        if (i == 1) {
            return "PS";
        }
        if (i == 2) {
            return "PF";
        }
        if (i == 3) {
            return "SC";
        }
        if (i == 4) {
            return "FD";
        }
        if (i == 5) {
            return "PN";
        }
        if (i == 6) {
            return "PU";
        }
        if (i == 7) {
            return "PP";
        }
        if (i == 8) {
            return "PC";
        }
        if (i == 9) {
            return "SC2";
        }
        if (i == 10) {
            return "PUK2";
        }
        if (i == 11) {
            return "ACL";
        }
        if (i == 12) {
            return "REG";
        }
        if (i == 13) {
            return "ONE";
        }
        if (i == 14) {
            return "SEP";
        }
        if (i == 15) {
            return "POLICY";
        }
        if (i == 16) {
            return "NO_SIM";
        }
        if (i == 17) {
            return "UNAVAIL";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        int i2 = -1;
        if (i == -1) {
            arrayList.add("UNSPECIFIED");
        } else {
            i2 = 0;
        }
        arrayList.add(KeyProperties.DIGEST_NONE);
        if ((i & 1) == 1) {
            arrayList.add("PS");
            i2 |= 1;
        }
        if ((i & 2) == 2) {
            arrayList.add("PF");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("SC");
            i2 |= 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("FD");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("PN");
            i2 |= 5;
        }
        if ((i & 6) == 6) {
            arrayList.add("PU");
            i2 |= 6;
        }
        if ((i & 7) == 7) {
            arrayList.add("PP");
            i2 |= 7;
        }
        if ((i & 8) == 8) {
            arrayList.add("PC");
            i2 |= 8;
        }
        if ((i & 9) == 9) {
            arrayList.add("SC2");
            i2 |= 9;
        }
        if ((i & 10) == 10) {
            arrayList.add("PUK2");
            i2 |= 10;
        }
        if ((i & 11) == 11) {
            arrayList.add("ACL");
            i2 |= 11;
        }
        if ((i & 12) == 12) {
            arrayList.add("REG");
            i2 |= 12;
        }
        if ((i & 13) == 13) {
            arrayList.add("ONE");
            i2 |= 13;
        }
        if ((i & 14) == 14) {
            arrayList.add("SEP");
            i2 |= 14;
        }
        if ((i & 15) == 15) {
            arrayList.add("POLICY");
            i2 |= 15;
        }
        if ((i & 16) == 16) {
            arrayList.add("NO_SIM");
            i2 |= 16;
        }
        if ((i & 17) == 17) {
            arrayList.add("UNAVAIL");
            i2 |= 17;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
