package android.hardware.radio.V1_2;

import android.database.sqlite.SQLiteDatabase;
import com.samsung.android.globalactions.presentation.strategies.WindowManagerFunctionStrategy;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class DataRequestReason {
    public static final int HANDOVER = 3;
    public static final int NORMAL = 1;
    public static final int SHUTDOWN = 2;

    public static final String toString(int i) {
        if (i == 1) {
            return SQLiteDatabase.SYNC_MODE_NORMAL;
        }
        if (i == 2) {
            return WindowManagerFunctionStrategy.SHUTDOWN;
        }
        if (i == 3) {
            return "HANDOVER";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add(SQLiteDatabase.SYNC_MODE_NORMAL);
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add(WindowManagerFunctionStrategy.SHUTDOWN);
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("HANDOVER");
            i2 = 3;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
