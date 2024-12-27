package android.database.sqlite;

import android.database.SQLException;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Log;

public class SQLiteException extends SQLException {
    public SQLiteException() {}

    public SQLiteException(String error) {
        super(error);
    }

    public SQLiteException(String error, Throwable cause) {
        super(error, cause);
    }

    private static void waitIfDeviceOnShutdown() {
        for (int i = 0; i < 2; i++) {
            if (SQLiteGlobal.isDeviceOnShutdown()) {
                try {
                    Log.d(
                            "waitIfOnShutdown",
                            "shutdown process is ongoing...freezing for a second.");
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    protected int parseCode(int code, String message) {
        int endIndex;
        if (message != null) {
            try {
                int startIndex = message.indexOf(NavigationBarInflaterView.SIZE_MOD_START);
                if (startIndex > 0 && (endIndex = message.indexOf("])")) > 0) {
                    String errString = message.substring(startIndex + 1, endIndex);
                    return Integer.parseInt(errString);
                }
                return code;
            } catch (Exception e) {
                return code;
            }
        }
        return code;
    }
}
