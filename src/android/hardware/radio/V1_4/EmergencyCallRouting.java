package android.hardware.radio.V1_4;

import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class EmergencyCallRouting {
    public static final int EMERGENCY = 1;
    public static final int NORMAL = 2;
    public static final int UNKNOWN = 0;

    public static final String toString(int i) {
        if (i == 0) {
            return "UNKNOWN";
        }
        if (i == 1) {
            return "EMERGENCY";
        }
        if (i == 2) {
            return SQLiteDatabase.SYNC_MODE_NORMAL;
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("UNKNOWN");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("EMERGENCY");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add(SQLiteDatabase.SYNC_MODE_NORMAL);
            i2 |= 2;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
