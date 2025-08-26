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

    private static void waitIfDeviceOnShutdown() throws InterruptedException {
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
        int iIndexOf;
        if (str != null) {
            try {
                int iIndexOf2 = str.indexOf(NavigationBarInflaterView.SIZE_MOD_START);
                if (iIndexOf2 > 0 && (iIndexOf = str.indexOf("])")) > 0) {
                    return Integer.parseInt(str.substring(iIndexOf2 + 1, iIndexOf));
                }
            } catch (Exception unused) {
            }
        }
        return i;
    }
}
