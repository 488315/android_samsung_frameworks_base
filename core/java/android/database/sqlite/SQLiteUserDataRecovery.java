package android.database.sqlite;

import android.database.DefaultDatabaseErrorHandler;
import android.util.Log;

import java.io.File;

public final class SQLiteUserDataRecovery {
    private static final String RECOVERY_POSTFIX = "-recover";
    private static final int SQLITE_UDR_DUPLICATE = 768;
    public static final String TAG = "SQLiteUDR";
    private SQLiteDump mDbDump;
    private final Object mLock = new Object();
    private boolean isWorking = false;

    private static native int nativeDoRecovery(String str, String str2, byte[] bArr, String str3);

    private static native boolean nativeIsDbUdrRecovered(String str);

    public SQLiteUserDataRecovery(SQLiteDump dump) {
        this.mDbDump = SQLiteDump.DUMMY_DB_DUMP;
        this.mDbDump = dump;
    }

    private boolean doRecoveryInner(String path, byte[] password, String locale) {
        String recoverPath = path + RECOVERY_POSTFIX;
        try {
            int rc = nativeDoRecovery(path, recoverPath, password, locale);
            if (rc == 0) {
                this.mDbDump.addDumpLog(TAG, "!@ Back up corrupted DB File : " + path);
                DefaultDatabaseErrorHandler.backupDatabaseFile(path);
                File recoverdb = new File(recoverPath);
                File db = new File(path);
                return recoverdb.renameTo(db);
            }
            if (rc == 768) {
                this.mDbDump.logAndDump(TAG, "Another udr is worked.");
                return true;
            }
            return false;
        } catch (SQLiteException e) {
            if (Log.isLoggable(TAG, 3)) {
                this.mDbDump.logAndDump(TAG, "Failed to recover database.");
                e.printStackTrace();
            }
            SQLiteDatabase.deleteDatabaseFile(recoverPath);
            return false;
        }
    }

    public boolean doRecovery(String path, byte[] password, String locale) {
        if (path == null || path.equalsIgnoreCase(SQLiteDatabaseConfiguration.MEMORY_DB_PATH)) {
            return false;
        }
        synchronized (this.mLock) {
            if (this.isWorking) {
                return false;
            }
            this.isWorking = true;
            try {
                boolean doRecoveryInner = doRecoveryInner(path, password, locale);
                synchronized (this.mLock) {
                    this.isWorking = false;
                }
                return doRecoveryInner;
            } catch (Throwable th) {
                synchronized (this.mLock) {
                    this.isWorking = false;
                    throw th;
                }
            }
        }
    }

    public static boolean isDbUdrRecovered(String path) {
        if (path != null && !path.equalsIgnoreCase(SQLiteDatabaseConfiguration.MEMORY_DB_PATH)) {
            return nativeIsDbUdrRecovered(path);
        }
        return false;
    }
}
