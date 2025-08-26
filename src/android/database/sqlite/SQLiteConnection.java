package android.database.sqlite;

import android.database.CursorWindow;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteConnection;
import android.database.sqlite.SQLiteDebug;
import android.database.sqlite.trace.SQLiteTrace;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Binder;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.SystemClock;
import android.os.Trace;
import android.provider.Telephony;
import android.telecom.ParcelableCallAnalytics;
import android.telephony.ims.RcsContactPresenceTuple;
import android.text.TextUtils;
import android.util.Log;
import android.util.LruCache;
import android.util.NtpTrustedTime;
import android.util.Pair;
import android.util.Printer;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.android.internal.util.RingBuffer;
import dalvik.system.BlockGuard;
import dalvik.system.CloseGuard;
import java.io.File;
import java.io.IOException;
import java.lang.ref.Reference;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/* loaded from: classes.dex */
public final class SQLiteConnection implements CancellationSignal.OnCancelListener {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final boolean DEBUG = false;
    private static final String TAG = "SQLiteConnection";
    private int mCancellationSignalAttachCount;
    private final CloseGuard mCloseGuard;
    private final SQLiteDatabaseConfiguration mConfiguration;
    private final int mConnectionId;
    private long mConnectionPtr;
    private long mExpertPtr;
    private boolean mIsOpen;
    private final boolean mIsPrimaryConnection;
    private final boolean mIsReadOnlyConnection;
    private boolean mOnlyAllowReadOnlyOperations;
    private final SQLiteConnectionPool mPool;
    private final PreparedStatementCache mPreparedStatementCache;
    private PreparedStatement mPreparedStatementPool;
    private final OperationLog mRecentOperations;
    private static final String[] EMPTY_STRING_ARRAY = new String[0];
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

    private static boolean isCacheable(int i) {
        return i == 2 || i == 1 || i == 100;
    }

    public static native int nativeBackupDatabaseFile(String str, String str2);

    public static native int nativeBackupSecureDatabaseFile(String str, String str2, byte[] bArr);

    private static native void nativeBindBlob(long j, long j2, int i, byte[] bArr);

    private static native void nativeBindDouble(long j, long j2, int i, double d);

    private static native void nativeBindLong(long j, long j2, int i, long j3);

    private static native void nativeBindNull(long j, long j2, int i);

    private static native void nativeBindString(long j, long j2, int i, String str);

    private static native void nativeCancel(long j);

    private static native byte[] nativeChangePassword(long j, byte[] bArr);

    private static native long nativeChanges(long j);

    public static native int nativeCleanDatabaseFile(String str);

    private static native void nativeClose(long j, boolean z);

    private static native long nativeCreateExpert(String str, String str2, byte[] bArr);

    private static native void nativeDestroyExpert(long j);

    private static native void nativeExecute(long j, long j2, boolean z);

    private static native int nativeExecuteForBlobFileDescriptor(long j, long j2);

    private static native int nativeExecuteForChangedRowCount(long j, long j2);

    private static native long nativeExecuteForCursorWindow(long j, long j2, long j3, int i, int i2, boolean z);

    private static native long nativeExecuteForLastInsertedRowId(long j, long j2);

    private static native long nativeExecuteForLong(long j, long j2);

    private static native String nativeExecuteForString(long j, long j2);

    private static native String nativeExpertAnalyze(long j, String str);

    private static native void nativeExportDB(long j, String str);

    private static native void nativeFinalizeStatement(long j, long j2);

    private static native int nativeGetColumnCount(long j, long j2);

    private static native String nativeGetColumnName(long j, long j2, int i);

    private static native int nativeGetDbLookaside(long j);

    private static native int nativeGetParameterCount(long j, long j2);

    private static native boolean nativeIsForcedReadOnly(long j);

    private static native boolean nativeIsReadOnly(long j, long j2);

    private static native int nativeLastInsertRowId(long j);

    private static native long nativeOpen(String str, int i, String str2, boolean z, boolean z2, int i2, int i3);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativePrepareStatement(long j, String str);

    private static native void nativeRegisterCustomAggregateFunction(long j, String str, BinaryOperator<String> binaryOperator);

    private static native void nativeRegisterCustomScalarFunction(long j, String str, UnaryOperator<String> unaryOperator);

    private static native void nativeRegisterLocalizedCollators(long j, String str);

    private static native void nativeResetCancel(long j, boolean z);

    private static native void nativeResetStatementAndClearBindings(long j, long j2);

    public static native int nativeRestoreDatabaseFile(String str, String str2);

    public static native int nativeRestoreSecureDatabaseFile(String str, String str2, byte[] bArr);

    private static native void nativeSetCheckpointOnClose(long j, boolean z);

    private static native byte[] nativeSetPassword(long j, byte[] bArr);

    private static native long nativeTotalChanges(long j);

    private static native boolean nativeUpdatesTempOnly(long j, long j2);

    private SQLiteConnection(SQLiteConnectionPool sQLiteConnectionPool, SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration, int i, boolean z) {
        CloseGuard closeGuard = CloseGuard.get();
        this.mCloseGuard = closeGuard;
        this.mPool = sQLiteConnectionPool;
        SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration2 = new SQLiteDatabaseConfiguration(sQLiteDatabaseConfiguration);
        this.mConfiguration = sQLiteDatabaseConfiguration2;
        this.mConnectionId = i;
        this.mIsPrimaryConnection = z;
        this.mIsReadOnlyConnection = sQLiteDatabaseConfiguration2.isReadOnlyDatabase();
        this.mPreparedStatementCache = new PreparedStatementCache(sQLiteDatabaseConfiguration2.maxSqlCacheSize);
        this.mRecentOperations = new OperationLog(sQLiteConnectionPool, this, sQLiteDatabaseConfiguration2);
        closeGuard.open("SQLiteConnection.close");
    }

    protected void finalize() throws Throwable {
        try {
            SQLiteConnectionPool sQLiteConnectionPool = this.mPool;
            if (sQLiteConnectionPool != null && this.mConnectionPtr != 0) {
                sQLiteConnectionPool.onConnectionLeaked();
            }
            dispose(true);
        } finally {
            super.finalize();
        }
    }

    static SQLiteConnection open(SQLiteConnectionPool sQLiteConnectionPool, SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration, int i, boolean z) throws IOException {
        SQLiteConnection sQLiteConnection = new SQLiteConnection(sQLiteConnectionPool, sQLiteDatabaseConfiguration, i, z);
        try {
            sQLiteConnection.open();
            return sQLiteConnection;
        } catch (SQLiteException e) {
            if (e instanceof SQLiteDatabaseCorruptException) {
                sQLiteConnection.setCheckpointOnClose(false);
            }
            sQLiteConnection.dispose(false);
            throw e;
        }
    }

    static SQLiteConnection openSecure(SQLiteConnectionPool sQLiteConnectionPool, SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration, int i, boolean z, byte[] bArr) throws IOException {
        SQLiteConnection sQLiteConnection = new SQLiteConnection(sQLiteConnectionPool, sQLiteDatabaseConfiguration, i, z);
        try {
            sQLiteConnection.open(bArr);
            return sQLiteConnection;
        } catch (SQLiteException e) {
            if (e instanceof SQLiteDatabaseCorruptException) {
                sQLiteConnection.setCheckpointOnClose(false);
            }
            sQLiteConnection.dispose(false);
            throw e;
        }
    }

    void close() {
        dispose(false);
    }

    private void open(byte[] bArr) throws IOException {
        String str = this.mConfiguration.path;
        int iBeginOperation = this.mRecentOperations.beginOperation(RcsContactPresenceTuple.TUPLE_BASIC_STATUS_OPEN, null, null);
        try {
            try {
                this.mConnectionPtr = nativeOpen(str, this.mConfiguration.openFlags, this.mConfiguration.label, SQLiteDebug.NoPreloadHolder.DEBUG_SQL_STATEMENTS, SQLiteDebug.NoPreloadHolder.DEBUG_SQL_TIME, this.mConfiguration.lookasideSlotSize, this.mConfiguration.lookasideSlotCount);
                this.mRecentOperations.endOperation(iBeginOperation);
                if (this.mConnectionPtr == 0) {
                    return;
                }
                byte[] password = setPassword(bArr);
                try {
                    setPageIntegrityLevel(Telephony.Mms.Part.MSG_ID);
                    setForeignKeyModeFromConfiguration();
                    setJournalFromConfiguration();
                    setSyncModeFromConfiguration();
                    setJournalSizeLimit();
                    setAutoCheckpointInterval();
                    setAutomaticIndexFromConfiguration();
                    setBusyTimeoutFromConfiguration();
                    setCacheSizeFromConfiguration();
                    setCaseSensitiveLikeFromConfiguration();
                    setAssertionLog();
                    setLocaleFromConfiguration();
                    setCustomFunctionsFromConfiguration();
                    executePerConnectionSqlFromConfiguration(0);
                    setUserDataRecovery();
                } catch (SQLiteReadOnlyDatabaseException e) {
                    if (isForcedReadOnlyConnection()) {
                        Log.i(TAG, "This connection is forced to be a read-only connection. Ignore SQLiteReadOnlyDatabaseException.");
                    } else {
                        throw e;
                    }
                }
                this.mPool.saveConnectionKey(password);
                this.mIsOpen = true;
            } catch (SQLiteCantOpenDatabaseException e2) {
                StringBuilder sb = new StringBuilder("Cannot open database [");
                sb.append(e2.getMessage());
                sb.append("] '");
                sb.append(str);
                sb.append("' with flags 0x");
                sb.append(Integer.toHexString(this.mConfiguration.openFlags));
                try {
                    Path path = FileSystems.getDefault().getPath(str, new String[0]);
                    Path parent = path.getParent();
                    if (parent == null) {
                        sb.append(": Directory not specified in the file path");
                    } else if (!Files.isDirectory(parent, new LinkOption[0])) {
                        sb.append(": Directory ");
                        sb.append(parent);
                        sb.append(" doesn't exist");
                    } else if (!Files.exists(path, new LinkOption[0])) {
                        sb.append(": File ");
                        sb.append(path);
                        sb.append(" doesn't exist");
                        if ((this.mConfiguration.openFlags & 268435456) != 0) {
                            sb.append(" and CREATE_IF_NECESSARY is set, check directory permissions");
                        }
                    } else if (!Files.isReadable(path)) {
                        sb.append(": File ");
                        sb.append(path);
                        sb.append(" is not readable");
                    } else if (Files.isDirectory(path, new LinkOption[0])) {
                        sb.append(": Path ");
                        sb.append(path);
                        sb.append(" is a directory");
                    }
                } catch (Throwable unused) {
                }
                throw new SQLiteCantOpenDatabaseException(sb.toString(), e2);
            }
        } catch (Throwable th) {
            this.mRecentOperations.endOperation(iBeginOperation);
            throw th;
        }
    }

    private void open() throws IOException {
        String str = this.mConfiguration.path;
        int iBeginOperation = this.mRecentOperations.beginOperation(RcsContactPresenceTuple.TUPLE_BASIC_STATUS_OPEN, null, null);
        try {
            try {
                this.mConnectionPtr = nativeOpen(str, this.mConfiguration.openFlags, this.mConfiguration.label, SQLiteDebug.NoPreloadHolder.DEBUG_SQL_STATEMENTS, SQLiteDebug.NoPreloadHolder.DEBUG_SQL_TIME, this.mConfiguration.lookasideSlotSize, this.mConfiguration.lookasideSlotCount);
                this.mRecentOperations.endOperation(iBeginOperation);
                if (this.mConnectionPtr == 0) {
                    return;
                }
                try {
                    setPageSize();
                    setForeignKeyModeFromConfiguration();
                    setJournalFromConfiguration();
                    setSyncModeFromConfiguration();
                    setJournalSizeLimit();
                    setAutoCheckpointInterval();
                    setAutomaticIndexFromConfiguration();
                    setBusyTimeoutFromConfiguration();
                    setCacheSizeFromConfiguration();
                    setCaseSensitiveLikeFromConfiguration();
                    setAssertionLog();
                    setLocaleFromConfiguration();
                    setCustomFunctionsFromConfiguration();
                    executePerConnectionSqlFromConfiguration(0);
                    setUserDataRecovery();
                } catch (SQLiteReadOnlyDatabaseException e) {
                    if (isForcedReadOnlyConnection()) {
                        Log.i(TAG, "This connection is forced to be a read-only connection. Ignore SQLiteReadOnlyDatabaseException.");
                    } else {
                        throw e;
                    }
                }
                this.mIsOpen = true;
            } catch (SQLiteCantOpenDatabaseException e2) {
                StringBuilder sb = new StringBuilder("Cannot open database [");
                sb.append(e2.getMessage());
                sb.append("] '");
                sb.append(str);
                sb.append("' with flags 0x");
                sb.append(Integer.toHexString(this.mConfiguration.openFlags));
                try {
                    Path path = FileSystems.getDefault().getPath(str, new String[0]);
                    Path parent = path.getParent();
                    if (parent == null) {
                        sb.append(": Directory not specified in the file path");
                    } else if (!Files.isDirectory(parent, new LinkOption[0])) {
                        sb.append(": Directory ");
                        sb.append(parent);
                        sb.append(" doesn't exist");
                    } else if (!Files.exists(path, new LinkOption[0])) {
                        sb.append(": File ");
                        sb.append(path);
                        sb.append(" doesn't exist");
                        if ((this.mConfiguration.openFlags & 268435456) != 0) {
                            sb.append(" and CREATE_IF_NECESSARY is set, check directory permissions");
                        }
                    } else if (!Files.isReadable(path)) {
                        sb.append(": File ");
                        sb.append(path);
                        sb.append(" is not readable");
                    } else if (Files.isDirectory(path, new LinkOption[0])) {
                        sb.append(": Path ");
                        sb.append(path);
                        sb.append(" is a directory");
                    }
                } catch (Throwable unused) {
                }
                throw new SQLiteCantOpenDatabaseException(sb.toString(), e2);
            }
        } catch (Throwable th) {
            this.mRecentOperations.endOperation(iBeginOperation);
            throw th;
        }
    }

    private void dispose(boolean z) {
        CloseGuard closeGuard = this.mCloseGuard;
        if (closeGuard != null) {
            if (z) {
                closeGuard.warnIfOpen();
            }
            this.mCloseGuard.close();
        }
        if (this.mConnectionPtr != 0) {
            int iBeginOperation = this.mRecentOperations.beginOperation("close", null, null);
            try {
                this.mPreparedStatementCache.evictAll();
                nativeClose(this.mConnectionPtr, z && Flags.noCheckpointOnFinalize());
                this.mConnectionPtr = 0L;
                this.mIsOpen = false;
                if (SQLiteDebug.NoPreloadHolder.DEBUG_ENABLE) {
                    destroyExpert();
                }
            } finally {
                if (this.mRecentOperations.endOperationDeferLog(iBeginOperation)) {
                    this.mRecentOperations.logOperation(iBeginOperation, "window='" + this.mConfiguration.path + "'");
                }
            }
        }
    }

    void recordBeginTransaction(String str) {
        this.mRecentOperations.beginTransaction(str);
    }

    void recordEndTransaction(boolean z) {
        this.mRecentOperations.endTransaction(z);
    }

    private void setPageSize() {
        if (this.mConfiguration.isInMemoryDb() || this.mIsReadOnlyConnection) {
            return;
        }
        long defaultPageSize = SQLiteGlobal.getDefaultPageSize();
        if (executeForLong("PRAGMA page_size", null, null) != defaultPageSize) {
            execute("PRAGMA page_size=" + defaultPageSize, null, null);
        }
    }

    private void setAutoCheckpointInterval() {
        if (this.mConfiguration.isInMemoryDb() || this.mIsReadOnlyConnection) {
            return;
        }
        long autoCheckpoint = this.mConfiguration.sharedConfig.getAutoCheckpoint();
        if (executeForLong("PRAGMA wal_autocheckpoint", null, null) != autoCheckpoint) {
            executeForLong("PRAGMA wal_autocheckpoint=" + autoCheckpoint, null, null);
        }
    }

    private void setJournalSizeLimit() {
        if (this.mConfiguration.isInMemoryDb() || this.mIsReadOnlyConnection) {
            return;
        }
        long journalSizeLimit = this.mConfiguration.sharedConfig.getJournalSizeLimit();
        if (executeForLong("PRAGMA journal_size_limit", null, null) != journalSizeLimit) {
            executeForLong("PRAGMA journal_size_limit=" + journalSizeLimit, null, null);
        }
    }

    private void setAutomaticIndexFromConfiguration() {
        long j = this.mConfiguration.automaticIndexEnabled ? 1L : 0L;
        if (executeForLong("PRAGMA automatic_index", null, null) != j) {
            execute("PRAGMA automatic_index=" + j, null, null);
        }
    }

    private void setCaseSensitiveLikeFromConfiguration() {
        long jExecuteForLong;
        long j = this.mConfiguration.caseSensitiveLikeEnabled ? 1L : 0L;
        try {
            jExecuteForLong = executeForLong("PRAGMA case_sensitive_like", null, null);
        } catch (SQLiteException unused) {
            jExecuteForLong = -1;
        }
        if (jExecuteForLong != j) {
            execute("PRAGMA case_sensitive_like=" + j, null, null);
        }
    }

    private void setBusyTimeoutFromConfiguration() {
        long jExecuteForLong;
        long j = this.mConfiguration.busyTimeout;
        try {
            jExecuteForLong = executeForLong("PRAGMA busy_timeout", null, null);
        } catch (SQLiteException e) {
            Log.e(TAG, "Error getting busy_timeout", e);
            jExecuteForLong = 2500;
        }
        if (jExecuteForLong != j) {
            try {
                executeForLong("PRAGMA busy_timeout=" + j, null, null);
            } catch (SQLiteException e2) {
                Log.e(TAG, "Error setting busy_timeout", e2);
            }
        }
    }

    private void setCacheSizeFromConfiguration() {
        long defaultCacheSize = this.mConfiguration.cacheSize == 0 ? SQLiteGlobal.getDefaultCacheSize() : this.mConfiguration.cacheSize;
        if (executeForLong("PRAGMA cache_size", null, null) != defaultCacheSize) {
            execute("PRAGMA cache_size=" + defaultCacheSize, null, null);
        }
    }

    private void setForeignKeyModeFromConfiguration() {
        if (this.mIsReadOnlyConnection) {
            return;
        }
        long j = this.mConfiguration.foreignKeyConstraintsEnabled ? 1L : 0L;
        if (executeForLong("PRAGMA foreign_keys", null, null) != j) {
            execute("PRAGMA foreign_keys=" + j, null, null);
        }
    }

    private void setJournalFromConfiguration() {
        if (!this.mIsReadOnlyConnection && this.mIsPrimaryConnection) {
            setJournalMode(this.mConfiguration.resolveJournalMode());
            if (this.mConfiguration.sharedConfig.isMediaStoreDb) {
                return;
            }
            maybeTruncateWalFile();
            return;
        }
        this.mConfiguration.shouldTruncateWalFile = false;
    }

    private void setSyncModeFromConfiguration() {
        if (this.mIsReadOnlyConnection || !this.mIsPrimaryConnection) {
            return;
        }
        setSyncMode(this.mConfiguration.resolveSyncMode());
    }

    private void setPageIntegrityLevel(String str) {
        try {
            if (executeForString("PRAGMA page_integrity_level", null, null).equalsIgnoreCase(str)) {
                return;
            }
            execute("PRAGMA page_integrity_level=" + str, null, null);
        } catch (SQLiteException unused) {
        }
    }

    private void maybeTruncateWalFile() {
        if (this.mConfiguration.shouldTruncateWalFile) {
            long wALTruncateSize = SQLiteGlobal.getWALTruncateSize();
            if (wALTruncateSize == 0) {
                return;
            }
            if (this.mConfiguration.sharedConfig.isSecureDb) {
                wALTruncateSize = 4096;
            }
            File file = new File(this.mConfiguration.path + "-wal");
            if (file.isFile() && file.length() >= wALTruncateSize) {
                try {
                    executeForString("PRAGMA wal_checkpoint(TRUNCATE)", null, null);
                    this.mConfiguration.shouldTruncateWalFile = false;
                } catch (SQLiteException e) {
                    Log.w(TAG, "Failed to truncate the -wal file", e);
                }
            }
        }
    }

    private void setSyncMode(String str) {
        if (TextUtils.isEmpty(str) || canonicalizeSyncMode(executeForString("PRAGMA synchronous", null, null)).equalsIgnoreCase(canonicalizeSyncMode(str))) {
            return;
        }
        execute("PRAGMA synchronous=" + str, null, null);
    }

    private static String canonicalizeSyncMode(String str) {
        str.hashCode();
        switch (str) {
            case "0":
                return "OFF";
            case "1":
                return SQLiteDatabase.SYNC_MODE_NORMAL;
            case "2":
                return "FULL";
            case "3":
                return "EXTRA";
            default:
                return str;
        }
    }

    public boolean isForcedReadOnlyConnection() {
        return nativeIsForcedReadOnly(this.mConnectionPtr);
    }

    private void setUserDataRecovery() {
        if (this.mConfiguration.sharedConfig.useUserDataRecovery) {
            execute("PRAGMA udr_recovery=1", null, null);
        }
    }

    private void setAssertionLog() {
        if (this.mConfiguration.sharedConfig.useAssertionLog && this.mIsPrimaryConnection) {
            try {
                if (executeForLong("PRAGMA enable_assert_log=1", null, null) == 0) {
                    Log.d(TAG, "Failed to enable assert log for " + this.mConfiguration.path);
                }
            } catch (SQLiteException unused) {
            }
        }
    }

    private void setJournalMode(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        String strExecuteForString = executeForString("PRAGMA journal_mode", null, null);
        if (strExecuteForString.equalsIgnoreCase(str)) {
            return;
        }
        try {
            if (executeForString("PRAGMA journal_mode=" + str, null, null).equalsIgnoreCase(str)) {
                return;
            }
        } catch (SQLiteDatabaseLockedException unused) {
        }
        Log.w(TAG, "Could not change the database journal mode of '" + this.mConfiguration.label + "' from '" + strExecuteForString + "' to '" + str + "' because the database is locked.  This usually means that there are other open connections to the database which prevents the database from enabling or disabling write-ahead logging mode.  Proceeding without changing the journal mode.");
    }

    private void setLocaleFromConfiguration() throws IOException {
        if ((this.mConfiguration.openFlags & 16) != 0) {
            return;
        }
        String string = this.mConfiguration.locale.toString();
        nativeRegisterLocalizedCollators(this.mConnectionPtr, string);
        if (!this.mConfiguration.isInMemoryDb()) {
            checkDatabaseWiped();
        }
        if (this.mIsReadOnlyConnection || !this.mIsPrimaryConnection) {
            return;
        }
        try {
            try {
                execute("CREATE TABLE IF NOT EXISTS android_metadata (locale TEXT)", null, null);
                String strExecuteForString = executeForString("SELECT locale FROM android_metadata UNION SELECT NULL ORDER BY locale DESC LIMIT 1", null, null);
                if (strExecuteForString == null || !strExecuteForString.equals(string)) {
                    execute("BEGIN", null, null);
                    try {
                        execute("DELETE FROM android_metadata", null, null);
                        execute("INSERT INTO android_metadata (locale) VALUES(?)", new Object[]{string}, null);
                        execute("REINDEX LOCALIZED", null, null);
                        execute("COMMIT", null, null);
                    } catch (Throwable th) {
                        execute("ROLLBACK", null, null);
                        throw th;
                    }
                }
            } catch (SQLiteDatabaseCorruptException | SQLiteFullException | SQLiteReadOnlyDatabaseException e) {
                Log.e(TAG, "Failed to change locale for db'" + this.mConfiguration.label + "' to '" + string + "'.");
                throw e;
            }
        } catch (RuntimeException e2) {
            throw new SQLiteException("Failed to change locale for db '" + this.mConfiguration.label + "' to '" + string + "'.", e2);
        }
    }

    void setCheckpointOnClose(boolean z) {
        long j = this.mConnectionPtr;
        if (j != 0) {
            nativeSetCheckpointOnClose(j, z);
        }
    }

    private void setCustomFunctionsFromConfiguration() {
        for (int i = 0; i < this.mConfiguration.customScalarFunctions.size(); i++) {
            nativeRegisterCustomScalarFunction(this.mConnectionPtr, this.mConfiguration.customScalarFunctions.keyAt(i), this.mConfiguration.customScalarFunctions.valueAt(i));
        }
        for (int i2 = 0; i2 < this.mConfiguration.customAggregateFunctions.size(); i2++) {
            nativeRegisterCustomAggregateFunction(this.mConnectionPtr, this.mConfiguration.customAggregateFunctions.keyAt(i2), this.mConfiguration.customAggregateFunctions.valueAt(i2));
        }
    }

    private void executePerConnectionSqlFromConfiguration(int i) {
        while (i < this.mConfiguration.perConnectionSql.size()) {
            Pair<String, Object[]> pair = this.mConfiguration.perConnectionSql.get(i);
            int sqlStatementType = DatabaseUtils.getSqlStatementType(pair.first);
            if (sqlStatementType == 1) {
                executeForString(pair.first, pair.second, null);
            } else if (sqlStatementType == 7) {
                execute(pair.first, pair.second, null);
            } else {
                throw new IllegalArgumentException("Unsupported configuration statement: " + pair);
            }
            i++;
        }
    }

    private void checkDatabaseWiped() throws IOException {
        if (SQLiteGlobal.checkDbWipe()) {
            try {
                File file = new File(this.mConfiguration.path + "-wipecheck");
                boolean z = executeForLong("SELECT count(*) FROM sqlite_master WHERE type='table' AND name='android_metadata'", null, null) > 0;
                boolean zExists = file.exists();
                if (!this.mIsReadOnlyConnection && !zExists) {
                    file.createNewFile();
                }
                if (z || !zExists) {
                    return;
                }
                SQLiteDatabase.wipeDetected(this.mConfiguration.path, "unknown");
            } catch (IOException | RuntimeException e) {
                SQLiteDatabase.wtfAsSystemServer(TAG, "Unexpected exception while checking for wipe", e);
            }
        }
    }

    void reconfigure(SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration) throws IOException {
        this.mOnlyAllowReadOnlyOperations = false;
        boolean z = sQLiteDatabaseConfiguration.foreignKeyConstraintsEnabled != this.mConfiguration.foreignKeyConstraintsEnabled;
        boolean zEquals = sQLiteDatabaseConfiguration.locale.equals(this.mConfiguration.locale);
        boolean z2 = sQLiteDatabaseConfiguration.automaticIndexEnabled != this.mConfiguration.automaticIndexEnabled;
        boolean z3 = sQLiteDatabaseConfiguration.busyTimeout != this.mConfiguration.busyTimeout;
        boolean z4 = sQLiteDatabaseConfiguration.cacheSize != this.mConfiguration.cacheSize;
        boolean z5 = sQLiteDatabaseConfiguration.caseSensitiveLikeEnabled != this.mConfiguration.caseSensitiveLikeEnabled;
        boolean zEquals2 = sQLiteDatabaseConfiguration.customScalarFunctions.equals(this.mConfiguration.customScalarFunctions);
        boolean zEquals3 = sQLiteDatabaseConfiguration.customAggregateFunctions.equals(this.mConfiguration.customAggregateFunctions);
        int size = this.mConfiguration.perConnectionSql.size();
        boolean z6 = sQLiteDatabaseConfiguration.perConnectionSql.size() > size;
        this.mConfiguration.updateParametersFrom(sQLiteDatabaseConfiguration);
        this.mPreparedStatementCache.resize(sQLiteDatabaseConfiguration.maxSqlCacheSize);
        if (z) {
            setForeignKeyModeFromConfiguration();
        }
        if (!sQLiteDatabaseConfiguration.resolveJournalMode().equalsIgnoreCase(this.mConfiguration.resolveJournalMode())) {
            setJournalFromConfiguration();
        }
        if (!sQLiteDatabaseConfiguration.resolveSyncMode().equalsIgnoreCase(this.mConfiguration.resolveSyncMode())) {
            setSyncModeFromConfiguration();
        }
        if (!zEquals) {
            setLocaleFromConfiguration();
        }
        if (z2) {
            setAutomaticIndexFromConfiguration();
        }
        if (z3) {
            setBusyTimeoutFromConfiguration();
        }
        if (z4) {
            setCacheSizeFromConfiguration();
        }
        if (z5) {
            setCaseSensitiveLikeFromConfiguration();
        }
        if (!zEquals2 || !zEquals3) {
            setCustomFunctionsFromConfiguration();
        }
        if (z6) {
            executePerConnectionSqlFromConfiguration(size);
        }
    }

    void setOnlyAllowReadOnlyOperations(boolean z) {
        this.mOnlyAllowReadOnlyOperations = z;
    }

    boolean isPreparedStatementInCache(String str) {
        return this.mPreparedStatementCache.get(str) != null;
    }

    public int getConnectionId() {
        return this.mConnectionId;
    }

    public boolean isPrimaryConnection() {
        return this.mIsPrimaryConnection;
    }

    public void releaseConnectionMemory() {
        try {
            execute("PRAGMA shrink_memory", null, null);
        } catch (Exception unused) {
        }
    }

    public void prepare(String str, SQLiteStatementInfo sQLiteStatementInfo) {
        if (str == null) {
            throw new IllegalArgumentException("sql must not be null.");
        }
        int iBeginOperation = this.mRecentOperations.beginOperation("prepare", str, null);
        try {
            try {
                PreparedStatement preparedStatementAcquirePreparedStatement = acquirePreparedStatement(str);
                if (sQLiteStatementInfo != null) {
                    try {
                        sQLiteStatementInfo.numParameters = preparedStatementAcquirePreparedStatement.mNumParameters;
                        sQLiteStatementInfo.readOnly = preparedStatementAcquirePreparedStatement.mReadOnly;
                        int iNativeGetColumnCount = nativeGetColumnCount(this.mConnectionPtr, preparedStatementAcquirePreparedStatement.mStatementPtr);
                        if (iNativeGetColumnCount == 0) {
                            sQLiteStatementInfo.columnNames = EMPTY_STRING_ARRAY;
                        } else {
                            sQLiteStatementInfo.columnNames = new String[iNativeGetColumnCount];
                            for (int i = 0; i < iNativeGetColumnCount; i++) {
                                sQLiteStatementInfo.columnNames[i] = nativeGetColumnName(this.mConnectionPtr, preparedStatementAcquirePreparedStatement.mStatementPtr, i);
                            }
                        }
                    } finally {
                        releasePreparedStatement(preparedStatementAcquirePreparedStatement);
                    }
                }
            } catch (RuntimeException e) {
                this.mRecentOperations.failOperation(iBeginOperation, e);
                try {
                    if (e.getMessage() != null && (e.getMessage().contains("no such table") || e.getMessage().contains("no such column"))) {
                        long jExecuteForLong = executeForLong("PRAGMA user_version", null, null);
                        if (jExecuteForLong != -1) {
                            Log.e(TAG, "DB version: " + jExecuteForLong);
                        }
                    }
                } catch (Exception unused) {
                    Log.e(TAG, "Error getting user version");
                }
                throw e;
            }
        } finally {
            if (this.mRecentOperations.endOperationDeferLog(iBeginOperation)) {
                this.mRecentOperations.logOperation(iBeginOperation, "window='" + this.mConfiguration.path + "'");
            }
        }
    }

    public void execute(String str, Object[] objArr, CancellationSignal cancellationSignal) {
        if (str == null) {
            throw new IllegalArgumentException("sql must not be null.");
        }
        int iBeginOperation = this.mRecentOperations.beginOperation("execute", str, objArr);
        try {
            try {
                boolean z = DatabaseUtils.getSqlStatementType(str) == 7;
                PreparedStatement preparedStatementAcquirePreparedStatement = acquirePreparedStatement(str);
                try {
                    throwIfStatementForbidden(preparedStatementAcquirePreparedStatement);
                    bindArguments(preparedStatementAcquirePreparedStatement, objArr);
                    applyBlockGuardPolicy(preparedStatementAcquirePreparedStatement);
                    attachCancellationSignal(cancellationSignal);
                    try {
                        nativeExecute(this.mConnectionPtr, preparedStatementAcquirePreparedStatement.mStatementPtr, z);
                    } finally {
                        detachCancellationSignal(cancellationSignal);
                    }
                } finally {
                    releasePreparedStatement(preparedStatementAcquirePreparedStatement);
                }
            } finally {
                if (this.mRecentOperations.endOperationDeferLog(iBeginOperation)) {
                    this.mRecentOperations.logOperation(iBeginOperation, "window='" + this.mConfiguration.path + "'");
                }
            }
        } catch (RuntimeException e) {
            this.mRecentOperations.failOperation(iBeginOperation, e);
            throw e;
        }
    }

    public long executeForLong(String str, Object[] objArr, CancellationSignal cancellationSignal) {
        if (str == null) {
            throw new IllegalArgumentException("sql must not be null.");
        }
        int iBeginOperation = this.mRecentOperations.beginOperation("executeForLong", str, objArr);
        try {
            try {
                PreparedStatement preparedStatementAcquirePreparedStatement = acquirePreparedStatement(str);
                try {
                    throwIfStatementForbidden(preparedStatementAcquirePreparedStatement);
                    bindArguments(preparedStatementAcquirePreparedStatement, objArr);
                    applyBlockGuardPolicy(preparedStatementAcquirePreparedStatement);
                    attachCancellationSignal(cancellationSignal);
                    try {
                        long jNativeExecuteForLong = nativeExecuteForLong(this.mConnectionPtr, preparedStatementAcquirePreparedStatement.mStatementPtr);
                        this.mRecentOperations.setResult(jNativeExecuteForLong);
                        return jNativeExecuteForLong;
                    } finally {
                        detachCancellationSignal(cancellationSignal);
                    }
                } finally {
                    releasePreparedStatement(preparedStatementAcquirePreparedStatement);
                }
            } catch (RuntimeException e) {
                this.mRecentOperations.failOperation(iBeginOperation, e);
                throw e;
            }
        } finally {
            if (this.mRecentOperations.endOperationDeferLog(iBeginOperation)) {
                this.mRecentOperations.logOperation(iBeginOperation, "window='" + this.mConfiguration.path + "'");
            }
        }
    }

    public String executeForString(String str, Object[] objArr, CancellationSignal cancellationSignal) {
        if (str == null) {
            throw new IllegalArgumentException("sql must not be null.");
        }
        int iBeginOperation = this.mRecentOperations.beginOperation("executeForString", str, objArr);
        try {
            try {
                PreparedStatement preparedStatementAcquirePreparedStatement = acquirePreparedStatement(str);
                try {
                    throwIfStatementForbidden(preparedStatementAcquirePreparedStatement);
                    bindArguments(preparedStatementAcquirePreparedStatement, objArr);
                    applyBlockGuardPolicy(preparedStatementAcquirePreparedStatement);
                    attachCancellationSignal(cancellationSignal);
                    try {
                        String strNativeExecuteForString = nativeExecuteForString(this.mConnectionPtr, preparedStatementAcquirePreparedStatement.mStatementPtr);
                        this.mRecentOperations.setResult(strNativeExecuteForString);
                        return strNativeExecuteForString;
                    } finally {
                        detachCancellationSignal(cancellationSignal);
                    }
                } finally {
                    releasePreparedStatement(preparedStatementAcquirePreparedStatement);
                }
            } catch (RuntimeException e) {
                this.mRecentOperations.failOperation(iBeginOperation, e);
                throw e;
            }
        } finally {
            if (this.mRecentOperations.endOperationDeferLog(iBeginOperation)) {
                this.mRecentOperations.logOperation(iBeginOperation, "window='" + this.mConfiguration.path + "'");
            }
        }
    }

    public ParcelFileDescriptor executeForBlobFileDescriptor(String str, Object[] objArr, CancellationSignal cancellationSignal) {
        if (str == null) {
            throw new IllegalArgumentException("sql must not be null.");
        }
        int iBeginOperation = this.mRecentOperations.beginOperation("executeForBlobFileDescriptor", str, objArr);
        try {
            try {
                PreparedStatement preparedStatementAcquirePreparedStatement = acquirePreparedStatement(str);
                try {
                    throwIfStatementForbidden(preparedStatementAcquirePreparedStatement);
                    bindArguments(preparedStatementAcquirePreparedStatement, objArr);
                    applyBlockGuardPolicy(preparedStatementAcquirePreparedStatement);
                    attachCancellationSignal(cancellationSignal);
                    try {
                        int iNativeExecuteForBlobFileDescriptor = nativeExecuteForBlobFileDescriptor(this.mConnectionPtr, preparedStatementAcquirePreparedStatement.mStatementPtr);
                        return iNativeExecuteForBlobFileDescriptor >= 0 ? ParcelFileDescriptor.adoptFd(iNativeExecuteForBlobFileDescriptor) : null;
                    } finally {
                        detachCancellationSignal(cancellationSignal);
                    }
                } finally {
                    releasePreparedStatement(preparedStatementAcquirePreparedStatement);
                }
            } catch (RuntimeException e) {
                this.mRecentOperations.failOperation(iBeginOperation, e);
                throw e;
            }
        } finally {
            if (this.mRecentOperations.endOperationDeferLog(iBeginOperation)) {
                this.mRecentOperations.logOperation(iBeginOperation, "window='" + this.mConfiguration.path + "'");
            }
        }
    }

    public int executeForChangedRowCount(String str, Object[] objArr, CancellationSignal cancellationSignal) {
        if (str == null) {
            throw new IllegalArgumentException("sql must not be null.");
        }
        int iBeginOperation = this.mRecentOperations.beginOperation("executeForChangedRowCount", str, objArr);
        try {
            try {
                PreparedStatement preparedStatementAcquirePreparedStatement = acquirePreparedStatement(str);
                try {
                    throwIfStatementForbidden(preparedStatementAcquirePreparedStatement);
                    bindArguments(preparedStatementAcquirePreparedStatement, objArr);
                    applyBlockGuardPolicy(preparedStatementAcquirePreparedStatement);
                    attachCancellationSignal(cancellationSignal);
                    try {
                        int iNativeExecuteForChangedRowCount = nativeExecuteForChangedRowCount(this.mConnectionPtr, preparedStatementAcquirePreparedStatement.mStatementPtr);
                        if (this.mRecentOperations.endOperationDeferLog(iBeginOperation)) {
                            this.mRecentOperations.logOperation(iBeginOperation, "window='" + this.mConfiguration.path + "', changedRows=" + iNativeExecuteForChangedRowCount);
                        }
                        return iNativeExecuteForChangedRowCount;
                    } finally {
                        detachCancellationSignal(cancellationSignal);
                    }
                } finally {
                    releasePreparedStatement(preparedStatementAcquirePreparedStatement);
                }
            } catch (RuntimeException e) {
                this.mRecentOperations.failOperation(iBeginOperation, e);
                throw e;
            }
        } catch (Throwable th) {
            if (this.mRecentOperations.endOperationDeferLog(iBeginOperation)) {
                this.mRecentOperations.logOperation(iBeginOperation, "window='" + this.mConfiguration.path + "', changedRows=0");
            }
            throw th;
        }
    }

    public long executeForLastInsertedRowId(String str, Object[] objArr, CancellationSignal cancellationSignal) {
        if (str == null) {
            throw new IllegalArgumentException("sql must not be null.");
        }
        int iBeginOperation = this.mRecentOperations.beginOperation("executeForLastInsertedRowId", str, objArr);
        try {
            try {
                PreparedStatement preparedStatementAcquirePreparedStatement = acquirePreparedStatement(str);
                try {
                    throwIfStatementForbidden(preparedStatementAcquirePreparedStatement);
                    bindArguments(preparedStatementAcquirePreparedStatement, objArr);
                    applyBlockGuardPolicy(preparedStatementAcquirePreparedStatement);
                    attachCancellationSignal(cancellationSignal);
                    try {
                        return nativeExecuteForLastInsertedRowId(this.mConnectionPtr, preparedStatementAcquirePreparedStatement.mStatementPtr);
                    } finally {
                        detachCancellationSignal(cancellationSignal);
                    }
                } finally {
                    releasePreparedStatement(preparedStatementAcquirePreparedStatement);
                }
            } catch (RuntimeException e) {
                this.mRecentOperations.failOperation(iBeginOperation, e);
                throw e;
            }
        } finally {
            if (this.mRecentOperations.endOperationDeferLog(iBeginOperation)) {
                this.mRecentOperations.logOperation(iBeginOperation, "window='" + this.mConfiguration.path + "'");
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v0, types: [android.database.sqlite.SQLiteConnection$OperationLog] */
    /* JADX WARN: Type inference failed for: r12v2, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r1v3, types: [android.database.sqlite.SQLiteConnection$OperationLog] */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v17 */
    /* JADX WARN: Type inference failed for: r2v18 */
    /* JADX WARN: Type inference failed for: r2v3, types: [int] */
    /* JADX WARN: Type inference failed for: r5v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r6v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r7v14 */
    /* JADX WARN: Type inference failed for: r7v17 */
    /* JADX WARN: Type inference failed for: r7v2 */
    /* JADX WARN: Type inference failed for: r7v4, types: [java.lang.String] */
    public int executeForCursorWindow(String str, Object[] objArr, CursorWindow cursorWindow, int i, int i2, boolean z, CancellationSignal cancellationSignal) {
        int i3;
        String str2;
        ?? r2;
        String str3;
        ?? r7;
        int i4;
        int i5;
        int i6;
        int i7;
        PreparedStatement preparedStatementAcquirePreparedStatement;
        PreparedStatement preparedStatement;
        int i8;
        Object[] objArr2 = objArr;
        String str4 = ", filledRows=";
        ?? r5 = "', startPos=";
        ?? r6 = "window='";
        if (str == null) {
            throw new IllegalArgumentException("sql must not be null.");
        }
        if (cursorWindow == null) {
            throw new IllegalArgumentException("window must not be null.");
        }
        cursorWindow.acquireReference();
        try {
            String str5 = "executeForCursorWindow";
            int iBeginOperation = this.mRecentOperations.beginOperation("executeForCursorWindow", str, objArr2);
            try {
                try {
                    preparedStatementAcquirePreparedStatement = acquirePreparedStatement(str);
                } catch (Throwable th) {
                    th = th;
                    i4 = -1;
                    r2 = objArr2;
                    i5 = r5;
                    i6 = r6;
                    r7 = iBeginOperation;
                }
                try {
                    throwIfStatementForbidden(preparedStatementAcquirePreparedStatement);
                    bindArguments(preparedStatementAcquirePreparedStatement, objArr2);
                    applyBlockGuardPolicy(preparedStatementAcquirePreparedStatement);
                    attachCancellationSignal(cancellationSignal);
                    try {
                        try {
                            try {
                                i7 = iBeginOperation;
                                i3 = i;
                                str4 = "window='";
                                preparedStatement = preparedStatementAcquirePreparedStatement;
                            } catch (Throwable th2) {
                                th = th2;
                                i8 = iBeginOperation;
                                preparedStatement = preparedStatementAcquirePreparedStatement;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            preparedStatement = preparedStatementAcquirePreparedStatement;
                            i8 = iBeginOperation;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        i8 = iBeginOperation;
                        preparedStatement = preparedStatementAcquirePreparedStatement;
                    }
                    try {
                        long jNativeExecuteForCursorWindow = nativeExecuteForCursorWindow(this.mConnectionPtr, preparedStatementAcquirePreparedStatement.mStatementPtr, cursorWindow.mWindowPtr, i3, i2, z);
                        int i9 = (int) (jNativeExecuteForCursorWindow >> 32);
                        i4 = (int) jNativeExecuteForCursorWindow;
                        try {
                            int numRows = cursorWindow.getNumRows();
                            try {
                                cursorWindow.setStartPosition(i9);
                                cursorWindow.setFilledRows(numRows);
                                if (z) {
                                    try {
                                        cursorWindow.setTotalRows(i4);
                                    } catch (Throwable th5) {
                                        th = th5;
                                        i7 = i7;
                                        try {
                                            detachCancellationSignal(cancellationSignal);
                                            throw th;
                                        } catch (Throwable th6) {
                                            th = th6;
                                            try {
                                                releasePreparedStatement(preparedStatement);
                                                throw th;
                                            } catch (RuntimeException e) {
                                                e = e;
                                                this.mRecentOperations.failOperation(i7, e);
                                                throw e;
                                            }
                                        }
                                    }
                                }
                                try {
                                    detachCancellationSignal(cancellationSignal);
                                    try {
                                        releasePreparedStatement(preparedStatement);
                                        if (this.mRecentOperations.endOperationDeferLog(i7, numRows, i4, cursorWindow.getTotalRows())) {
                                            this.mRecentOperations.logOperation(i7, str4 + cursorWindow + "', startPos=" + i3 + ", actualPos=" + i9 + ", filledRows=" + numRows + ", countedRows=" + i4);
                                        }
                                        return i4;
                                    } catch (RuntimeException e2) {
                                        e = e2;
                                        this.mRecentOperations.failOperation(i7, e);
                                        throw e;
                                    } catch (Throwable th7) {
                                        th = th7;
                                        str3 = ", countedRows=";
                                        str5 = ", filledRows=";
                                        r7 = "', startPos=";
                                        str2 = ", actualPos=";
                                        r2 = i7;
                                        i5 = numRows;
                                        i6 = i9;
                                        if (this.mRecentOperations.endOperationDeferLog(r2, i5, i4, cursorWindow.getTotalRows())) {
                                            this.mRecentOperations.logOperation(r2, str4 + cursorWindow + r7 + i3 + str2 + i6 + str5 + i5 + str3 + i4);
                                        }
                                        throw th;
                                    }
                                } catch (Throwable th8) {
                                    th = th8;
                                    releasePreparedStatement(preparedStatement);
                                    throw th;
                                }
                            } catch (Throwable th9) {
                                th = th9;
                                i7 = i7;
                                detachCancellationSignal(cancellationSignal);
                                throw th;
                            }
                        } catch (Throwable th10) {
                            th = th10;
                        }
                    } catch (Throwable th11) {
                        th = th11;
                        i8 = i7;
                        i7 = i8;
                        detachCancellationSignal(cancellationSignal);
                        throw th;
                    }
                } catch (Throwable th12) {
                    th = th12;
                    i7 = iBeginOperation;
                    preparedStatement = preparedStatementAcquirePreparedStatement;
                }
            } catch (RuntimeException e3) {
                e = e3;
                i7 = iBeginOperation;
            } catch (Throwable th13) {
                th = th13;
                i3 = i;
                str2 = ", actualPos=";
                r2 = iBeginOperation;
                str3 = ", countedRows=";
                str5 = ", filledRows=";
                r7 = "', startPos=";
                str4 = "window='";
                i4 = -1;
                i5 = -1;
                i6 = -1;
            }
        } finally {
            cursorWindow.releaseReference();
        }
    }

    public byte[] setPassword(byte[] bArr) {
        return nativeSetPassword(this.mConnectionPtr, bArr);
    }

    public byte[] changePassword(byte[] bArr) {
        return nativeChangePassword(this.mConnectionPtr, bArr);
    }

    public void exportDB(String str) {
        nativeExportDB(this.mConnectionPtr, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0156  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x015d  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0166  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x016d  */
    /* JADX WARN: Removed duplicated region for block: B:72:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void printQueryPlan(String str) throws Throwable {
        long j;
        long jNativePrepareStatement;
        CursorWindow cursorWindow;
        if (!this.mIsOpen) {
            return;
        }
        long j2 = 0;
        CursorWindow cursorWindow2 = null;
        try {
            cursorWindow = new CursorWindow("QueryPlan-" + Thread.currentThread().getId());
        } catch (RuntimeException e) {
            e = e;
            j = 0;
        } catch (Throwable th) {
            th = th;
            j = 0;
        }
        try {
            jNativePrepareStatement = nativePrepareStatement(this.mConnectionPtr, "EXPLAIN QUERY PLAN " + str);
            try {
                long jNativeExecuteForCursorWindow = nativeExecuteForCursorWindow(this.mConnectionPtr, jNativePrepareStatement, cursorWindow.mWindowPtr, 0, 0, true);
                if (jNativeExecuteForCursorWindow == 0) {
                    cursorWindow.close();
                    if (jNativePrepareStatement == 0) {
                        return;
                    }
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("plan=\"");
                    int i = 0;
                    while (i < jNativeExecuteForCursorWindow) {
                        int i2 = cursorWindow.getInt(i, 0);
                        int i3 = cursorWindow.getInt(i, 1);
                        int i4 = cursorWindow.getInt(i, 2);
                        j = j2;
                        try {
                            sb.append(i2 + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + i3 + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + i4 + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + cursorWindow.getString(i, 3) + " * ");
                            i++;
                            j2 = j;
                        } catch (RuntimeException e2) {
                            e = e2;
                            cursorWindow2 = cursorWindow;
                            try {
                                Log.e(TAG, "Failed to explain query plan : " + str + " - " + e.getMessage());
                                e.printStackTrace();
                                if (cursorWindow2 != null) {
                                    cursorWindow2.close();
                                }
                                if (jNativePrepareStatement == j) {
                                    nativeFinalizeStatement(this.mConnectionPtr, jNativePrepareStatement);
                                    return;
                                }
                                return;
                            } catch (Throwable th2) {
                                th = th2;
                                if (cursorWindow2 != null) {
                                    cursorWindow2.close();
                                }
                                if (jNativePrepareStatement != j) {
                                    nativeFinalizeStatement(this.mConnectionPtr, jNativePrepareStatement);
                                }
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            cursorWindow2 = cursorWindow;
                            if (cursorWindow2 != null) {
                            }
                            if (jNativePrepareStatement != j) {
                            }
                            throw th;
                        }
                    }
                    j = j2;
                    Log.d("SQLiteQueryPlan", sb.toString() + "\", sql=\"" + trimSqlForDisplay(str) + "\", window='" + this.mConfiguration.path + "', hash=" + Integer.toHexString((str + this.mConfiguration.path).hashCode()));
                    cursorWindow.close();
                    if (jNativePrepareStatement == j) {
                        return;
                    }
                }
                nativeFinalizeStatement(this.mConnectionPtr, jNativePrepareStatement);
            } catch (RuntimeException e3) {
                e = e3;
                j = j2;
            } catch (Throwable th4) {
                th = th4;
                j = j2;
            }
        } catch (RuntimeException e4) {
            e = e4;
            j = 0;
            cursorWindow2 = cursorWindow;
            jNativePrepareStatement = j;
            Log.e(TAG, "Failed to explain query plan : " + str + " - " + e.getMessage());
            e.printStackTrace();
            if (cursorWindow2 != null) {
            }
            if (jNativePrepareStatement == j) {
            }
        } catch (Throwable th5) {
            th = th5;
            j = 0;
            cursorWindow2 = cursorWindow;
            jNativePrepareStatement = j;
            if (cursorWindow2 != null) {
            }
            if (jNativePrepareStatement != j) {
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String analyzeSql(String str) {
        synchronized (this) {
            if (!this.mIsOpen) {
                return null;
            }
            if (this.mExpertPtr == 0) {
                byte[] connectionKey = this.mPool.getConnectionKey();
                if ((this.mConfiguration.openFlags & 512) != 0 && connectionKey == null) {
                    Log.e(TAG, "Could not use expert without the key.");
                    return null;
                }
                this.mExpertPtr = nativeCreateExpert(this.mConfiguration.path, this.mConfiguration.locale.toString(), connectionKey);
            }
            long j = this.mExpertPtr;
            if (j == 0) {
                Log.e(TAG, "Could not use expert to analyze. No pointer.");
                return null;
            }
            return nativeExpertAnalyze(j, str);
        }
    }

    private void destroyExpert() {
        synchronized (this) {
            long j = this.mExpertPtr;
            if (j != 0) {
                nativeDestroyExpert(j);
                this.mExpertPtr = 0L;
            }
        }
    }

    private PreparedStatement acquirePreparedStatementLI(String str) {
        PreparedStatement preparedStatementObtainPreparedStatement;
        boolean z;
        SQLiteConnection sQLiteConnection;
        int iNativeGetParameterCount;
        int sqlStatementTypeExtended;
        this.mPool.mTotalPrepareStatements++;
        PreparedStatement statement = this.mPreparedStatementCache.getStatement(str);
        long lastSeqNum = this.mPreparedStatementCache.getLastSeqNum();
        if (statement == null) {
            preparedStatementObtainPreparedStatement = statement;
            z = false;
        } else if (statement.mInUse) {
            preparedStatementObtainPreparedStatement = statement;
            z = true;
        } else {
            if (statement.mSeqNum == lastSeqNum) {
                statement.mInUse = true;
                return statement;
            }
            this.mPreparedStatementCache.remove(str);
            statement = null;
            preparedStatementObtainPreparedStatement = statement;
            z = false;
        }
        this.mPool.mTotalPrepareStatementCacheMiss++;
        long jCreateStatement = this.mPreparedStatementCache.createStatement(str);
        long lastSeqNum2 = this.mPreparedStatementCache.getLastSeqNum();
        try {
            iNativeGetParameterCount = nativeGetParameterCount(this.mConnectionPtr, jCreateStatement);
            sqlStatementTypeExtended = DatabaseUtils.getSqlStatementTypeExtended(str);
            sQLiteConnection = this;
        } catch (RuntimeException e) {
            e = e;
            sQLiteConnection = this;
        }
        try {
            preparedStatementObtainPreparedStatement = sQLiteConnection.obtainPreparedStatement(str, jCreateStatement, iNativeGetParameterCount, sqlStatementTypeExtended, nativeIsReadOnly(this.mConnectionPtr, jCreateStatement), lastSeqNum2);
            if (!z && isCacheable(sqlStatementTypeExtended)) {
                sQLiteConnection.mPreparedStatementCache.put(str, preparedStatementObtainPreparedStatement);
                preparedStatementObtainPreparedStatement.mInCache = true;
            }
            preparedStatementObtainPreparedStatement.mInUse = true;
            return preparedStatementObtainPreparedStatement;
        } catch (RuntimeException e2) {
            e = e2;
            RuntimeException runtimeException = e;
            if (preparedStatementObtainPreparedStatement == null || !preparedStatementObtainPreparedStatement.mInCache) {
                nativeFinalizeStatement(sQLiteConnection.mConnectionPtr, jCreateStatement);
                throw runtimeException;
            }
            throw runtimeException;
        }
    }

    PreparedStatement acquirePreparedStatement(String str) {
        return acquirePreparedStatementLI(str);
    }

    private void releasePreparedStatementLI(PreparedStatement preparedStatement) {
        preparedStatement.mInUse = false;
        if (preparedStatement.mInCache) {
            try {
                nativeResetStatementAndClearBindings(this.mConnectionPtr, preparedStatement.mStatementPtr);
                return;
            } catch (SQLiteException unused) {
                this.mPreparedStatementCache.remove(preparedStatement.mSql);
                return;
            }
        }
        finalizePreparedStatement(preparedStatement);
    }

    void releasePreparedStatement(PreparedStatement preparedStatement) {
        releasePreparedStatementLI(preparedStatement);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finalizePreparedStatement(PreparedStatement preparedStatement) {
        nativeFinalizeStatement(this.mConnectionPtr, preparedStatement.mStatementPtr);
        recyclePreparedStatement(preparedStatement);
    }

    PreparedStatement acquirePersistentStatement(String str) {
        int iBeginOperation = this.mRecentOperations.beginOperation("prepare", str, null);
        try {
            try {
                PreparedStatement preparedStatementAcquirePreparedStatement = acquirePreparedStatement(str);
                throwIfStatementForbidden(preparedStatementAcquirePreparedStatement);
                return preparedStatementAcquirePreparedStatement;
            } catch (RuntimeException e) {
                this.mRecentOperations.failOperation(iBeginOperation, e);
                throw e;
            }
        } finally {
            this.mRecentOperations.endOperation(iBeginOperation);
        }
    }

    private void attachCancellationSignal(CancellationSignal cancellationSignal) {
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
            int i = this.mCancellationSignalAttachCount + 1;
            this.mCancellationSignalAttachCount = i;
            if (i == 1) {
                nativeResetCancel(this.mConnectionPtr, true);
                cancellationSignal.setOnCancelListener(this);
            }
        }
    }

    private void detachCancellationSignal(CancellationSignal cancellationSignal) {
        if (cancellationSignal != null) {
            int i = this.mCancellationSignalAttachCount - 1;
            this.mCancellationSignalAttachCount = i;
            if (i == 0) {
                cancellationSignal.setOnCancelListener(null);
                nativeResetCancel(this.mConnectionPtr, false);
            }
        }
    }

    @Override // android.os.CancellationSignal.OnCancelListener
    public void onCancel() {
        nativeCancel(this.mConnectionPtr);
    }

    private void bindArguments(PreparedStatement preparedStatement, Object[] objArr) {
        int length = objArr != null ? objArr.length : 0;
        if (length != preparedStatement.mNumParameters) {
            throw new SQLiteBindOrColumnIndexOutOfRangeException("Expected " + preparedStatement.mNumParameters + " bind arguments but " + length + " were provided.");
        }
        if (length == 0) {
            return;
        }
        long j = preparedStatement.mStatementPtr;
        for (int i = 0; i < length; i++) {
            Object obj = objArr[i];
            int typeOfObject = DatabaseUtils.getTypeOfObject(obj);
            if (typeOfObject == 0) {
                nativeBindNull(this.mConnectionPtr, j, i + 1);
            } else if (typeOfObject == 1) {
                nativeBindLong(this.mConnectionPtr, j, i + 1, ((Number) obj).longValue());
            } else if (typeOfObject == 2) {
                nativeBindDouble(this.mConnectionPtr, j, i + 1, ((Number) obj).doubleValue());
            } else if (typeOfObject == 4) {
                nativeBindBlob(this.mConnectionPtr, j, i + 1, (byte[]) obj);
            } else if (obj instanceof Boolean) {
                nativeBindLong(this.mConnectionPtr, j, i + 1, ((Boolean) obj).booleanValue() ? 1L : 0L);
            } else {
                nativeBindString(this.mConnectionPtr, j, i + 1, obj.toString());
            }
        }
    }

    void throwIfStatementForbidden(PreparedStatement preparedStatement) {
        if (!this.mOnlyAllowReadOnlyOperations || preparedStatement.mReadOnly) {
            return;
        }
        preparedStatement.mReadOnly = nativeUpdatesTempOnly(this.mConnectionPtr, preparedStatement.mStatementPtr);
        if (!preparedStatement.mReadOnly) {
            throw new SQLiteException("Cannot execute this statement because it might modify the database but the connection is read-only.");
        }
    }

    private void applyBlockGuardPolicy(PreparedStatement preparedStatement) {
        if (this.mConfiguration.isInMemoryDb()) {
            return;
        }
        if (preparedStatement.mReadOnly) {
            BlockGuard.getThreadPolicy().onReadFromDisk();
        } else {
            BlockGuard.getThreadPolicy().onWriteToDisk();
        }
    }

    public void dump(Printer printer, boolean z) {
        dumpUnsafe(printer, z);
    }

    void dumpUnsafe(Printer printer, boolean z) {
        printer.println("Connection #" + this.mConnectionId + ":");
        if (z) {
            printer.println("  connectionPtr: 0x" + Long.toHexString(this.mConnectionPtr));
        }
        printer.println("  isPrimaryConnection: " + this.mIsPrimaryConnection);
        printer.println("  onlyAllowReadOnlyOperations: " + this.mOnlyAllowReadOnlyOperations);
        printer.println("  totalLongOperations: " + this.mRecentOperations.getTotalLongOperations());
        this.mRecentOperations.dump(printer);
        if (z) {
            this.mPreparedStatementCache.dump(printer);
        }
    }

    String describeCurrentOperationUnsafe() {
        return this.mRecentOperations.describeCurrentOperation();
    }

    private long getPageCountFromDb(String str, long j) {
        String str2;
        long fileSize = SQLiteUtils.getFileSize(str);
        long fileSize2 = SQLiteUtils.getFileSize(str + "-wal");
        long j2 = fileSize >= 0 ? fileSize / j : 0L;
        if (fileSize <= 209715200 && fileSize2 <= 209715200) {
            return j2;
        }
        StringBuilder sb = new StringBuilder("DB: ");
        sb.append(str);
        sb.append(" size: ");
        sb.append((fileSize / 1024) / 1024);
        if (fileSize2 >= 0) {
            str2 = ", " + ((fileSize2 / 1024) / 1024);
        } else {
            str2 = "";
        }
        sb.append(str2);
        sb.append(" MB");
        Log.d(TAG, sb.toString());
        return j2;
    }

    void collectDbStatsUnsafeWithFileSize(ArrayList<SQLiteDebug.DbStats> arrayList) {
        arrayList.add(getMainDbStatsUnsafe(0, getPageCountFromDb(this.mConfiguration.path, 4096L), 4096L));
    }

    /* JADX WARN: Can't wrap try/catch for region: R(12:0|2|(2:36|3)|(2:40|4)|8|32|9|(12:12|34|13|38|14|15|18|19|(1:21)|22|23|10)|42|27|28|(1:(0))) */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0040 A[Catch: all -> 0x00b1, SQLiteException -> 0x00b6, TRY_LEAVE, TryCatch #0 {all -> 0x00b1, blocks: (B:9:0x002c, B:10:0x003a, B:12:0x0040, B:13:0x0049, B:14:0x0061, B:19:0x0080, B:21:0x0090, B:22:0x0098), top: B:32:0x002c }] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0090 A[Catch: all -> 0x00b1, SQLiteException -> 0x00b6, TryCatch #0 {all -> 0x00b1, blocks: (B:9:0x002c, B:10:0x003a, B:12:0x0040, B:13:0x0049, B:14:0x0061, B:19:0x0080, B:21:0x0090, B:22:0x0098), top: B:32:0x002c }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void collectDbStats(ArrayList<SQLiteDebug.DbStats> arrayList) {
        long jExecuteForLong;
        long jExecuteForLong2;
        CursorWindow cursorWindow;
        int i;
        long jExecuteForLong3;
        long jExecuteForLong4;
        int iNativeGetDbLookaside = nativeGetDbLookaside(this.mConnectionPtr);
        try {
            jExecuteForLong = executeForLong("PRAGMA page_count;", null, null);
        } catch (SQLiteException unused) {
            jExecuteForLong = 0;
        }
        try {
            try {
                jExecuteForLong2 = executeForLong("PRAGMA page_size;", null, null);
            } catch (SQLiteException unused2) {
                jExecuteForLong2 = 0;
                arrayList.add(getMainDbStatsUnsafe(iNativeGetDbLookaside, jExecuteForLong, jExecuteForLong2));
                cursorWindow = new CursorWindow("collectDbStats");
                executeForCursorWindow("PRAGMA database_list;", null, cursorWindow, 0, 0, false, null);
                while (i < cursorWindow.getNumRows()) {
                }
                return;
            }
            executeForCursorWindow("PRAGMA database_list;", null, cursorWindow, 0, 0, false, null);
            for (i = 1; i < cursorWindow.getNumRows(); i++) {
                String string = cursorWindow.getString(i, 1);
                String string2 = cursorWindow.getString(i, 2);
                try {
                    jExecuteForLong3 = executeForLong("PRAGMA " + string + ".page_count;", null, null);
                } catch (SQLiteException unused3) {
                    jExecuteForLong3 = 0;
                }
                try {
                    jExecuteForLong4 = executeForLong("PRAGMA " + string + ".page_size;", null, null);
                } catch (SQLiteException unused4) {
                    jExecuteForLong4 = 0;
                    long j = jExecuteForLong3;
                    StringBuilder sb = new StringBuilder("  (attached) ");
                    sb.append(string);
                    if (!string2.isEmpty()) {
                    }
                    arrayList.add(new SQLiteDebug.DbStats(sb.toString(), j, jExecuteForLong4, 0, 0, 0, 0, false));
                }
                long j2 = jExecuteForLong3;
                StringBuilder sb2 = new StringBuilder("  (attached) ");
                sb2.append(string);
                if (!string2.isEmpty()) {
                    sb2.append(": ");
                    sb2.append(string2);
                }
                arrayList.add(new SQLiteDebug.DbStats(sb2.toString(), j2, jExecuteForLong4, 0, 0, 0, 0, false));
            }
            return;
        } finally {
            cursorWindow.close();
        }
        arrayList.add(getMainDbStatsUnsafe(iNativeGetDbLookaside, jExecuteForLong, jExecuteForLong2));
        cursorWindow = new CursorWindow("collectDbStats");
    }

    void collectDbStatsUnsafe(ArrayList<SQLiteDebug.DbStats> arrayList) {
        arrayList.add(getMainDbStatsUnsafe(0, 0L, 0L));
    }

    private SQLiteDebug.DbStats getMainDbStatsUnsafe(int i, long j, long j2) {
        String str;
        if (this.mIsPrimaryConnection) {
            str = this.mConfiguration.path;
        } else {
            str = this.mConfiguration.path + " (" + this.mConnectionId + NavigationBarInflaterView.KEY_CODE_END;
        }
        return new SQLiteDebug.DbStats(str, j, j2, i, this.mPreparedStatementCache.hitCount(), this.mPreparedStatementCache.missCount(), this.mPreparedStatementCache.size(), false);
    }

    public String toString() {
        return "SQLiteConnection: " + this.mConfiguration.path + " (" + this.mConnectionId + NavigationBarInflaterView.KEY_CODE_END;
    }

    private PreparedStatement obtainPreparedStatement(String str, long j, int i, int i2, boolean z, long j2) {
        PreparedStatement preparedStatement = this.mPreparedStatementPool;
        if (preparedStatement != null) {
            this.mPreparedStatementPool = preparedStatement.mPoolNext;
            preparedStatement.mPoolNext = null;
            preparedStatement.mInCache = false;
        } else {
            preparedStatement = new PreparedStatement();
        }
        preparedStatement.mSql = str;
        preparedStatement.mStatementPtr = j;
        preparedStatement.mNumParameters = i;
        preparedStatement.mType = i2;
        preparedStatement.mReadOnly = z;
        preparedStatement.mSeqNum = j2;
        return preparedStatement;
    }

    private void recyclePreparedStatement(PreparedStatement preparedStatement) {
        preparedStatement.mSql = null;
        preparedStatement.mPoolNext = this.mPreparedStatementPool;
        this.mPreparedStatementPool = preparedStatement;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String trimSqlForDisplay(String str) {
        return str.replaceAll("[\\s]*\\n+[\\s]*", " ");
    }

    void setDatabaseSeqNum(long j) {
        this.mPreparedStatementCache.setDatabaseSeqNum(j);
    }

    static final class PreparedStatement {
        public boolean mInCache;
        public boolean mInUse;
        public int mNumParameters;
        public PreparedStatement mPoolNext;
        public boolean mReadOnly;
        public long mSeqNum;
        public String mSql;
        public long mStatementPtr;
        public int mType;

        PreparedStatement() {
        }
    }

    private final class PreparedStatementCache extends LruCache<String, PreparedStatement> {
        private long mDatabaseSeqNum;
        private long mLastSeqNum;

        public PreparedStatementCache(int i) {
            super(i);
            this.mDatabaseSeqNum = 0L;
            this.mLastSeqNum = 0L;
        }

        public synchronized void setDatabaseSeqNum(long j) {
            this.mDatabaseSeqNum = j;
        }

        public long getLastSeqNum() {
            return this.mLastSeqNum;
        }

        public synchronized PreparedStatement getStatement(String str) {
            this.mLastSeqNum = this.mDatabaseSeqNum;
            return get(str);
        }

        public synchronized long createStatement(String str) {
            this.mLastSeqNum = this.mDatabaseSeqNum;
            return SQLiteConnection.nativePrepareStatement(SQLiteConnection.this.mConnectionPtr, str);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.util.LruCache
        public void entryRemoved(boolean z, String str, PreparedStatement preparedStatement, PreparedStatement preparedStatement2) {
            preparedStatement.mInCache = false;
            if (preparedStatement.mInUse) {
                return;
            }
            SQLiteConnection.this.finalizePreparedStatement(preparedStatement);
        }

        public void dump(Printer printer) {
            printer.println("  Prepared statement cache:");
            Map<String, PreparedStatement> mapSnapshot = snapshot();
            if (!mapSnapshot.isEmpty()) {
                int i = 0;
                for (Map.Entry<String, PreparedStatement> entry : mapSnapshot.entrySet()) {
                    PreparedStatement value = entry.getValue();
                    if (value.mInCache) {
                        printer.println("    " + i + ": statementPtr=0x" + Long.toHexString(value.mStatementPtr) + ", numParameters=" + value.mNumParameters + ", type=" + value.mType + ", readOnly=" + value.mReadOnly + ", sql=\"" + SQLiteConnection.trimSqlForDisplay(entry.getKey()) + "\"");
                    }
                    i++;
                }
                return;
            }
            printer.println("    <none>");
        }
    }

    private static class RateLimiter {
        private final long mCreationUptimeMs;
        private final int mMaxCredits;
        private final long mMsPerCredit;
        private long mSpent = 0;

        RateLimiter(long j, int i) {
            this.mMsPerCredit = j;
            this.mMaxCredits = i;
            this.mCreationUptimeMs = SystemClock.uptimeMillis() - (j * i);
        }

        boolean tryAcquire() {
            long jUptimeMillis = (SystemClock.uptimeMillis() - this.mCreationUptimeMs) / this.mMsPerCredit;
            long j = this.mSpent;
            long j2 = jUptimeMillis - j;
            int i = this.mMaxCredits;
            if (j2 > i) {
                long j3 = j + (j2 - i);
                this.mSpent = j3;
                j2 = jUptimeMillis - j3;
            }
            if (j2 <= 0) {
                return false;
            }
            this.mSpent++;
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class OperationLog {
        private static final int COOKIE_GENERATION_SHIFT = 8;
        private static final int COOKIE_INDEX_MASK = 255;
        private static final long LONG_OPERATION_THRESHOLD_MS = 2000;
        private static final int MAX_LONG_OPERATIONS = 10;
        private static final int MAX_RECENT_OPERATIONS = 30;
        private final SQLiteDatabaseConfiguration mConfiguration;
        private final SQLiteConnection mConnection;
        private String mResultString;
        private final Operation mTransaction;
        private final Operation[] mOperations = new Operation[30];
        private int mIndex = -1;
        private int mGeneration = 0;
        private long mResultLong = Long.MIN_VALUE;
        private final RingBuffer<Operation> mLongOperations = new RingBuffer<>(new Supplier() { // from class: android.database.sqlite.SQLiteConnection$OperationLog$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.lambda$new$0();
            }
        }, new IntFunction() { // from class: android.database.sqlite.SQLiteConnection$OperationLog$$ExternalSyntheticLambda1
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return SQLiteConnection.OperationLog.lambda$new$1(i);
            }
        }, 10);
        private int mTotalLongOperations = 0;
        private final RateLimiter mLongLimiter = new RateLimiter(ParcelableCallAnalytics.MILLIS_IN_5_MINUTES, 10);

        public OperationLog(SQLiteConnectionPool sQLiteConnectionPool, SQLiteConnection sQLiteConnection, SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration) {
            this.mTransaction = SQLiteConnection.this.new Operation();
            this.mConnection = sQLiteConnection;
            this.mConfiguration = sQLiteDatabaseConfiguration;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Operation lambda$new$0() {
            return SQLiteConnection.this.new Operation();
        }

        static /* synthetic */ Operation[] lambda$new$1(int i) {
            return new Operation[i];
        }

        public int beginOperation(String str, String str2, Object[] objArr) {
            int i;
            this.mResultLong = Long.MIN_VALUE;
            this.mResultString = null;
            synchronized (this.mOperations) {
                Operation operationNewOperationLocked = newOperationLocked();
                operationNewOperationLocked.mExecutionTime = 0L;
                operationNewOperationLocked.mKind = str;
                operationNewOperationLocked.mSql = str2;
                operationNewOperationLocked.mCallingPid = Binder.getCallingPid();
                operationNewOperationLocked.mConnectionId = this.mConnection.mConnectionId;
                if (objArr != null) {
                    if (operationNewOperationLocked.mBindArgs == null) {
                        operationNewOperationLocked.mBindArgs = new ArrayList<>();
                    }
                    for (Object obj : objArr) {
                        if (!SQLiteDebug.NoPreloadHolder.DEBUG_ENABLE && obj != null && (obj instanceof byte[])) {
                            operationNewOperationLocked.mBindArgs.add(SQLiteConnection.EMPTY_BYTE_ARRAY);
                        } else {
                            operationNewOperationLocked.mBindArgs.add(obj);
                        }
                    }
                }
                operationNewOperationLocked.mTraced = Trace.isTagEnabled(1048576L);
                if (operationNewOperationLocked.mTraced) {
                    Trace.asyncTraceBegin(1048576L, operationNewOperationLocked.getTraceMethodName(), operationNewOperationLocked.mCookie);
                }
                i = operationNewOperationLocked.mCookie;
            }
            return i;
        }

        public void beginTransaction(String str) {
            synchronized (this.mOperations) {
                Operation operationNewOperationLocked = newOperationLocked();
                operationNewOperationLocked.mKind = str;
                this.mTransaction.copyFrom(operationNewOperationLocked);
                if (operationNewOperationLocked.mTraced) {
                    Trace.asyncTraceBegin(1048576L, operationNewOperationLocked.getTraceMethodName(), operationNewOperationLocked.mCookie);
                }
            }
        }

        private Operation newOperationLocked() {
            int i = (this.mIndex + 1) % 30;
            Operation[] operationArr = this.mOperations;
            Operation operation = operationArr[i];
            if (operation == null) {
                operationArr[i] = SQLiteConnection.this.new Operation();
                operation = this.mOperations[i];
            }
            operation.start();
            operation.mCookie = newOperationCookieLocked(i);
            this.mIndex = i;
            return operation;
        }

        public void failOperation(int i, Exception exc) {
            synchronized (this.mOperations) {
                Operation operationLocked = getOperationLocked(i);
                if (operationLocked != null) {
                    operationLocked.mException = exc;
                }
                if (exc != null && (exc instanceof SQLiteDatabaseCorruptException)) {
                    Log.d(SQLiteConnection.TAG, "Corruption detected - isPrimary: " + this.mConnection.isPrimaryConnection() + ", address: @" + Integer.toHexString(System.identityHashCode(this.mConnection)));
                }
            }
        }

        public void endOperation(int i) {
            synchronized (this.mOperations) {
                if (endOperationDeferLogLocked(i, -1, -1, -1)) {
                    logOperationLocked(i, null);
                }
            }
        }

        public boolean endOperationDeferLog(int i) {
            boolean zEndOperationDeferLogLocked;
            synchronized (this.mOperations) {
                zEndOperationDeferLogLocked = endOperationDeferLogLocked(i, -1, -1, -1);
            }
            return zEndOperationDeferLogLocked;
        }

        public boolean endOperationDeferLog(int i, int i2, int i3, int i4) {
            boolean zEndOperationDeferLogLocked;
            synchronized (this.mOperations) {
                zEndOperationDeferLogLocked = endOperationDeferLogLocked(i, i2, i3, i4);
            }
            return zEndOperationDeferLogLocked;
        }

        public boolean endTransaction(boolean z) {
            boolean z2;
            synchronized (this.mOperations) {
                this.mTransaction.mResultLong = z ? 1L : 0L;
                long jFinishOperationLocked = finishOperationLocked(this.mTransaction);
                Operation operationLocked = getOperationLocked(this.mTransaction.mCookie);
                if (operationLocked != null) {
                    operationLocked.copyFrom(this.mTransaction);
                }
                this.mTransaction.setEmpty();
                z2 = SQLiteDebug.NoPreloadHolder.DEBUG_LOG_SLOW_QUERIES && SQLiteDebug.shouldLogSlowQuery(jFinishOperationLocked);
            }
            return z2;
        }

        public void logOperation(int i, String str) {
            synchronized (this.mOperations) {
                logOperationLocked(i, str);
            }
        }

        public void setResult(long j) {
            this.mResultLong = j;
        }

        public void setResult(String str) {
            this.mResultString = str;
        }

        private boolean endOperationDeferLogLocked(int i, int i2, int i3, int i4) {
            Operation operationLocked = getOperationLocked(i);
            if (operationLocked != null) {
                if (operationLocked.mTraced) {
                    Trace.asyncTraceEnd(1048576L, operationLocked.getTraceMethodName(), operationLocked.mCookie);
                }
                operationLocked.mExecutionTime = finishOperationLocked(operationLocked);
                operationLocked.mFilledRows = i2;
                operationLocked.mCountedRows = i3;
                operationLocked.mTotalRows = i4;
                SQLiteConnection.this.mPool.onStatementExecuted(operationLocked.mExecutionTime);
                if (SQLiteTrace.isEnabled(this.mConfiguration.path)) {
                    operationLocked.mTid = Process.myTid();
                    SQLiteTrace.trace(operationLocked, this.mConfiguration.path);
                }
                if (SQLiteDebug.NoPreloadHolder.DEBUG_LOG_SLOW_QUERIES && SQLiteDebug.shouldLogSlowQuery(operationLocked.mExecutionTime)) {
                    return true;
                }
            }
            return false;
        }

        private void logOperationLocked(int i, String str) throws Throwable {
            Operation operationLocked = getOperationLocked(i);
            operationLocked.mResultLong = this.mResultLong;
            operationLocked.mResultString = this.mResultString;
            StringBuilder sb = new StringBuilder();
            operationLocked.describe(sb, true);
            if (str != null) {
                sb.append(", ");
                sb.append(str);
            }
            if (operationLocked.mSql != null) {
                StringBuilder sb2 = new StringBuilder(", hash=");
                sb2.append(Integer.toHexString((operationLocked.mSql + this.mConfiguration.path).hashCode()));
                sb.append(sb2.toString());
            }
            Log.d(SQLiteConnection.TAG, sb.toString());
            if (operationLocked.mSql == null || operationLocked.mException != null) {
                return;
            }
            int sqlStatementType = DatabaseUtils.getSqlStatementType(operationLocked.mSql);
            if (sqlStatementType == 2 || sqlStatementType == 1) {
                if (SQLiteDebug.shouldLogQueryPlan()) {
                    this.mConnection.printQueryPlan(operationLocked.mSql);
                }
                if (SQLiteDebug.shouldLogIndexRecommendation()) {
                    try {
                        new SQLiteExpertModule(this.mConnection, operationLocked.mSql, this.mConfiguration.path).start();
                    } catch (Exception unused) {
                    }
                }
            }
        }

        private int newOperationCookieLocked(int i) {
            int i2 = this.mGeneration;
            this.mGeneration = i2 + 1;
            return (i2 << 8) | i;
        }

        private long finishOperationLocked(Operation operation) {
            operation.mEndTime = SystemClock.uptimeMillis();
            operation.mFinished = true;
            long j = operation.mEndTime - operation.mStartTime;
            if (j > LONG_OPERATION_THRESHOLD_MS) {
                operation.mExecutionTime = j;
                this.mLongOperations.getNextSlot().copyFrom(operation);
                this.mTotalLongOperations++;
            }
            return j;
        }

        private Operation getOperationLocked(int i) {
            Operation operation = this.mOperations[i & 255];
            if (operation == null || operation.mCookie != i) {
                return null;
            }
            return operation;
        }

        public String describeCurrentOperation() {
            synchronized (this.mOperations) {
                Operation operation = this.mOperations[this.mIndex];
                if (operation == null || operation.mFinished) {
                    return null;
                }
                StringBuilder sb = new StringBuilder();
                operation.describe(sb, false);
                return sb.toString();
            }
        }

        private int dumpIfNotRecentLocked(Printer printer, Operation operation, int i) {
            if (operation == null || operation.isEmpty() || getOperationLocked(operation.mCookie) != null) {
                return 0;
            }
            printer.println(operation.describe(i));
            return 1;
        }

        private void dumpRecentLocked(Printer printer) {
            synchronized (this.mOperations) {
                printer.println("  Most recently executed operations:");
                int i = this.mIndex;
                if (i == 0) {
                    printer.println("    <none>");
                    return;
                }
                Operation operation = this.mOperations[i];
                int i2 = 0;
                int i3 = 0;
                do {
                    printer.println(operation.describe(i2));
                    i = i > 0 ? i - 1 : 29;
                    i3++;
                    i2++;
                    operation = this.mOperations[i];
                    if (operation == null) {
                        break;
                    }
                } while (i3 < 30);
                dumpIfNotRecentLocked(printer, this.mTransaction, i2);
            }
        }

        private void dumpLongLocked(Printer printer) {
            printer.println("  Operations exceeding 2000ms:");
            if (this.mLongOperations.isEmpty()) {
                printer.println("    <none>");
                return;
            }
            Operation[] array = this.mLongOperations.toArray();
            for (int i = 0; i < array.length; i++) {
                Operation operation = array[i];
                if (operation != null) {
                    printer.println(operation.describe(i));
                }
            }
        }

        public long getTotalLongOperations() {
            return this.mTotalLongOperations;
        }

        public void dump(Printer printer) {
            synchronized (this.mOperations) {
                dumpRecentLocked(printer);
                dumpLongLocked(printer);
            }
        }
    }

    public final class Operation {
        private static final long EMPTY_OPERATION = -1;
        private static final int MAX_TRACE_METHOD_NAME_LEN = 256;
        private static final DateTimeFormatter sDateTime = DateTimeFormatter.ofPattern("MM-dd HH:mm:ss.SSS", Locale.US);
        public ArrayList<Object> mBindArgs;
        public int mCallingPid;
        public int mConnectionId;
        public int mCookie;
        public int mCountedRows;
        public long mEndTime;
        public Exception mException;
        public long mExecutionTime;
        public int mFilledRows;
        public boolean mFinished;
        public String mKind;
        public long mResultLong;
        public String mResultString;
        public String mSql;
        public long mStartTime;
        public long mStartWallTime;
        public int mTid;
        public int mTotalRows;
        public boolean mTraced;

        public Operation() {
        }

        void start() {
            this.mStartWallTime = System.currentTimeMillis();
            this.mStartTime = SystemClock.uptimeMillis();
            this.mEndTime = Long.MIN_VALUE;
            this.mKind = null;
            this.mSql = null;
            ArrayList<Object> arrayList = this.mBindArgs;
            if (arrayList != null) {
                arrayList.clear();
            }
            this.mFinished = false;
            this.mException = null;
            this.mCookie = -1;
            this.mResultLong = Long.MIN_VALUE;
            this.mResultString = null;
            this.mTraced = false;
        }

        void copyFrom(Operation operation) {
            this.mStartWallTime = operation.mStartWallTime;
            this.mStartTime = operation.mStartTime;
            this.mEndTime = operation.mEndTime;
            this.mExecutionTime = operation.mExecutionTime;
            this.mKind = operation.mKind;
            this.mSql = operation.mSql;
            this.mBindArgs = null;
            this.mCallingPid = operation.mCallingPid;
            this.mFinished = operation.mFinished;
            this.mException = operation.mException;
            this.mCookie = operation.mCookie;
            this.mResultLong = operation.mResultLong;
            this.mResultString = operation.mResultString;
            this.mTraced = operation.mTraced;
        }

        void setEmpty() {
            this.mStartWallTime = -1L;
        }

        boolean isEmpty() {
            return this.mStartWallTime == -1;
        }

        public void describe(StringBuilder sb, boolean z) {
            ArrayList<Object> arrayList;
            sb.append(this.mKind);
            if (this.mFinished) {
                sb.append(" took ");
                sb.append(this.mExecutionTime);
                sb.append("ms");
            } else {
                sb.append(" started ");
                sb.append(SystemClock.uptimeMillis() - this.mStartTime);
                sb.append("ms ago");
            }
            sb.append(" - ");
            sb.append(getStatus());
            if (this.mSql != null) {
                sb.append(", sql=\"");
                sb.append(SQLiteConnection.trimSqlForDisplay(this.mSql));
                sb.append("\"");
            }
            if (z && SQLiteDebug.NoPreloadHolder.DEBUG_LOG_DETAILED && (arrayList = this.mBindArgs) != null && arrayList.size() != 0) {
                sb.append(", bindArgs=[");
                int size = this.mBindArgs.size();
                for (int i = 0; i < size; i++) {
                    Object obj = this.mBindArgs.get(i);
                    if (i != 0) {
                        sb.append(", ");
                    }
                    if (obj == null) {
                        sb.append(PerfettoProtoLogImpl.NULL_STRING);
                    } else if (obj instanceof byte[]) {
                        sb.append("<byte[]>");
                    } else if (obj instanceof String) {
                        sb.append("\"");
                        sb.append((String) obj);
                        sb.append("\"");
                    } else {
                        sb.append(obj);
                    }
                }
                sb.append(NavigationBarInflaterView.SIZE_MOD_END);
            }
            sb.append(", path=");
            sb.append(SQLiteConnection.this.mPool.getPath());
            if (this.mTotalRows >= 0) {
                sb.append(", filledRows=");
                sb.append(this.mFilledRows);
                sb.append(", countedRows=");
                sb.append(this.mCountedRows);
                sb.append(", totalRows=");
                sb.append(this.mTotalRows);
            }
            if (this.mException != null) {
                sb.append(", exception=\"");
                sb.append(this.mException.getMessage());
                sb.append("\"");
            }
            if (this.mResultLong != Long.MIN_VALUE) {
                sb.append(", result=");
                sb.append(this.mResultLong);
            }
            if (this.mResultString != null) {
                sb.append(", result=\"");
                sb.append(this.mResultString);
                sb.append("\"");
            }
        }

        private String timeString(long j) {
            return sDateTime.withZone(ZoneId.systemDefault()).format(Instant.ofEpochMilli(j));
        }

        public String describe(int i) {
            StringBuilder sb = new StringBuilder();
            String strTimeString = timeString(this.mStartWallTime);
            sb.append("    ");
            sb.append(i);
            sb.append(": [");
            sb.append(strTimeString);
            sb.append("] ");
            sb.append("[Pid:(" + this.mCallingPid + ")]");
            describe(sb, false);
            return sb.toString();
        }

        private String getStatus() {
            if (this.mFinished) {
                return this.mException != null ? "failed" : "succeeded";
            }
            return "running";
        }

        /* JADX INFO: Access modifiers changed from: private */
        public String getTraceMethodName() {
            String str = this.mKind + " " + this.mSql;
            return str.length() > 256 ? str.substring(0, 256) : str;
        }
    }

    long getLastInsertRowId() {
        try {
            return nativeLastInsertRowId(this.mConnectionPtr);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    long getLastChangedRowCount() {
        try {
            return nativeChanges(this.mConnectionPtr);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    long getTotalChangedRowCount() {
        try {
            return nativeTotalChanges(this.mConnectionPtr);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    private static final class SQLiteExpertModule extends Thread {
        private static final String TAG = "SQLiteIndexRecommendation";
        private SQLiteConnection mConnection;
        private String mPath;
        private String mSql;

        public SQLiteExpertModule(SQLiteConnection sQLiteConnection, String str, String str2) {
            this.mConnection = sQLiteConnection;
            this.mSql = str;
            this.mPath = str2;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            String strAnalyzeSql = this.mConnection.analyzeSql(this.mSql);
            if (strAnalyzeSql != null) {
                StringBuilder sb = new StringBuilder("newIndex=\"");
                sb.append(strAnalyzeSql);
                sb.append("\", sql=\"");
                sb.append(SQLiteConnection.trimSqlForDisplay(this.mSql));
                sb.append("\", window='");
                sb.append(this.mPath);
                sb.append("', hash=");
                sb.append(Integer.toHexString((this.mSql + this.mPath).hashCode()));
                Log.d(TAG, sb.toString());
            }
        }
    }
}
