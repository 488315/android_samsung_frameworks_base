package android.database.sqlite;

import android.database.SQLException;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Log;

/* loaded from: classes.dex */
public class SQLiteException extends SQLException {
    public SQLiteException() {
    }

    public SQLiteException(String str) {
        super(str);
    }

    public SQLiteException(String str, Throwable th) {
        super(str, th);
    }

    private static void waitIfDeviceOnShutdown() {
        for (int i = 0; i < 2; i++) {
            if (SQLiteGlobal.isDeviceOnShutdown()) {
                try {
                    Log.d("waitIfOnShutdown", "shutdown process is ongoing...freezing for a second.");
                    Thread.sleep(1000L);
                } catch (InterruptedException unused) {
                }
            }
        }
    }

    protected int parseCode(int i, String str) {
        int indexOf;
        if (str != null) {
            try {
                int indexOf2 = str.indexOf(NavigationBarInflaterView.SIZE_MOD_START);
                if (indexOf2 > 0 && (indexOf = str.indexOf("])")) > 0) {
                    return Integer.parseInt(str.substring(indexOf2 + 1, indexOf));
                }
            } catch (Exception unused) {
            }
        }
        return i;
    }
}
