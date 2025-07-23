package android.database.sqlite;

import android.content.res.Resources;
import android.hardware.usb.UsbManager;
import android.os.StatFs;
import android.os.SystemProperties;
import com.android.internal.R;

/* loaded from: classes.dex */
public final class SQLiteGlobal {
    private static final int DEFAULT_CACHE_SIZE = -2000;
    public static final String SYNC_MODE_FULL = "FULL";
    private static final String TAG = "SQLiteGlobal";
    static final String WIPE_CHECK_FILE_SUFFIX = "-wipecheck";
    private static int sDefaultPageSize;
    public static volatile String sDefaultSyncMode;
    private static final Object sLock = new Object();

    public static boolean checkDbWipe() {
        return false;
    }

    public static int getDefaultCacheSize() {
        return -2000;
    }

    private static native void nativeClearRandArray(long j);

    private static native long nativeCreateRandArray();

    private static native void nativeEnableSQLiteLogDump(boolean z);

    private static native char[] nativeGetRandArray(long j);

    private static native String nativeGetSQLiteDumpLogs(boolean z);

    private static native int nativeReleaseMemory();

    private SQLiteGlobal() {
    }

    public static int releaseMemory() {
        return nativeReleaseMemory();
    }

    public static int getDefaultPageSize() {
        int i;
        synchronized (sLock) {
            if (sDefaultPageSize == 0) {
                sDefaultPageSize = new StatFs("/data").getBlockSize();
            }
            i = SystemProperties.getInt("debug.sqlite.pagesize", sDefaultPageSize);
        }
        return i;
    }

    public static String getDefaultJournalMode() {
        return SystemProperties.get("debug.sqlite.journalmode", Resources.getSystem().getString(R.string.db_default_journal_mode));
    }

    public static int getJournalSizeLimit() {
        return SystemProperties.getInt("debug.sqlite.journalsizelimit", Resources.getSystem().getInteger(R.integer.db_journal_size_limit));
    }

    public static String getDefaultSyncMode() {
        String str = sDefaultSyncMode;
        return str != null ? str : SystemProperties.get("debug.sqlite.syncmode", Resources.getSystem().getString(R.string.db_default_sync_mode));
    }

    public static String getWALSyncMode() {
        String str = sDefaultSyncMode;
        return str != null ? str : SystemProperties.get("debug.sqlite.wal.syncmode", Resources.getSystem().getString(R.string.db_wal_sync_mode));
    }

    public static int getWALAutoCheckpoint() {
        return Math.max(1, SystemProperties.getInt("debug.sqlite.wal.autocheckpoint", Resources.getSystem().getInteger(R.integer.db_wal_autocheckpoint)));
    }

    public static int getWALConnectionPoolSize() {
        return Math.max(2, SystemProperties.getInt("debug.sqlite.wal.poolsize", Resources.getSystem().getInteger(R.integer.db_connection_pool_size)));
    }

    public static int getIdleConnectionTimeout() {
        return SystemProperties.getInt("debug.sqlite.idle_connection_timeout", Resources.getSystem().getInteger(R.integer.db_default_idle_connection_timeout));
    }

    public static long getWALTruncateSize() {
        long truncateSize = SQLiteCompatibilityWalFlags.getTruncateSize();
        return truncateSize >= 0 ? truncateSize : SystemProperties.getInt("debug.sqlite.wal.truncatesize", Resources.getSystem().getInteger(R.integer.db_wal_truncate_size));
    }

    public static boolean isDeviceOnShutdown() {
        String str = SystemProperties.get("sys.powerctl", "none");
        return str.contains("reboot") || str.contains(UsbManager.USB_FUNCTION_SHUTDOWN);
    }

    public static long createRandArray() {
        return nativeCreateRandArray();
    }

    public static char[] getRandArray(long j) {
        return nativeGetRandArray(j);
    }

    public static void clearRandArray(long j) {
        nativeClearRandArray(j);
    }

    public static String getSQLiteDumpLogs(boolean z) {
        return nativeGetSQLiteDumpLogs(z);
    }

    public static void enableSQLiteDump(boolean z) {
        nativeEnableSQLiteLogDump(z);
    }
}
