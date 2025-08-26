package android.database.sqlite;

import android.database.DefaultDatabaseErrorHandler;
import android.util.Log;
import java.io.File;

/* loaded from: classes.dex */
public final class SQLiteUserDataRecovery {
    private static final String RECOVERY_POSTFIX = "-recover";
    private static final int SQLITE_UDR_DUPLICATE = 768;
    public static final String TAG = "SQLiteUDR";
    private SQLiteDump mDbDump;
    private final Object mLock = new Object();
    private boolean isWorking = false;

    private static native int nativeDoRecovery(String str, String str2, byte[] bArr, String str3);

    private static native boolean nativeIsDbUdrRecovered(String str);

    public SQLiteUserDataRecovery(SQLiteDump sQLiteDump) {
        SQLiteDump sQLiteDump2 = SQLiteDump.DUMMY_DB_DUMP;
        this.mDbDump = sQLiteDump;
    }

    private boolean doRecoveryInner(String str, byte[] bArr, String str2) {
        String str3 = str + RECOVERY_POSTFIX;
        try {
            int iNativeDoRecovery = nativeDoRecovery(str, str3, bArr, str2);
            if (iNativeDoRecovery != 0) {
                if (iNativeDoRecovery != 768) {
                    return false;
                }
                this.mDbDump.logAndDump(TAG, "Another udr is worked.");
                return true;
            }
            this.mDbDump.addDumpLog(TAG, "!@ Back up corrupted DB File : " + str);
            DefaultDatabaseErrorHandler.backupDatabaseFile(str);
            return new File(str3).renameTo(new File(str));
        } catch (SQLiteException e) {
            if (Log.isLoggable(TAG, 3)) {
                this.mDbDump.logAndDump(TAG, "Failed to recover database.");
                e.printStackTrace();
            }
            SQLiteDatabase.deleteDatabaseFile(str3);
            return false;
        }
    }

    public boolean doRecovery(String str, byte[] bArr, String str2) {
        if (str == null || str.equalsIgnoreCase(SQLiteDatabaseConfiguration.MEMORY_DB_PATH)) {
            return false;
        }
        synchronized (this.mLock) {
            if (this.isWorking) {
                return false;
            }
            this.isWorking = true;
            try {
                boolean zDoRecoveryInner = doRecoveryInner(str, bArr, str2);
                synchronized (this.mLock) {
                    this.isWorking = false;
                }
                return zDoRecoveryInner;
            } catch (Throwable th) {
                synchronized (this.mLock) {
                    this.isWorking = false;
                    throw th;
                }
            }
        }
    }

    public static boolean isDbUdrRecovered(String str) {
        if (str == null || str.equalsIgnoreCase(SQLiteDatabaseConfiguration.MEMORY_DB_PATH)) {
            return false;
        }
        return nativeIsDbUdrRecovered(str);
    }
}
