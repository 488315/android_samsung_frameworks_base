package android.database.sqlite;

import android.database.CursorWindow;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDebug;
import android.database.sqlite.SQLiteSlowQueryCollector;
import android.database.sqlite.trace.SQLiteTrace;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Binder;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.SystemClock;
import android.os.Trace;
import android.provider.Telephony;
import android.telecom.TelecomManager;
import android.telephony.ims.RcsContactPresenceTuple;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.LruCache;
import android.util.NtpTrustedTime;
import android.util.Pair;
import android.util.Printer;
import com.samsung.android.ims.options.SemCapabilities;
import dalvik.system.BlockGuard;
import dalvik.system.CloseGuard;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.function.BinaryOperator;
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

    public static native int nativeBackupDatabaseFile(String str, String str2);

    public static native int nativeBackupSecureDatabaseFile(String str, String str2, byte[] bArr);

    private static native void nativeBindBlob(long j, long j2, int i, byte[] bArr);

    private static native void nativeBindDouble(long j, long j2, int i, double d);

    private static native void nativeBindLong(long j, long j2, int i, long j3);

    private static native void nativeBindNull(long j, long j2, int i);

    private static native void nativeBindString(long j, long j2, int i, String str);

    private static native void nativeCancel(long j);

    private static native byte[] nativeChangePassword(long j, byte[] bArr);

    public static native int nativeCleanDatabaseFile(String str);

    private static native void nativeClose(long j);

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

    private static native long nativeOpen(String str, int i, String str2, boolean z, boolean z2, int i2, int i3);

    private static native long nativePrepareStatement(long j, String str);

    private static native void nativeRegisterCustomAggregateFunction(long j, String str, BinaryOperator<String> binaryOperator);

    private static native void nativeRegisterCustomScalarFunction(long j, String str, UnaryOperator<String> unaryOperator);

    private static native void nativeRegisterLocalizedCollators(long j, String str);

    private static native void nativeResetCancel(long j, boolean z);

    private static native void nativeResetStatementAndClearBindings(long j, long j2);

    public static native int nativeRestoreDatabaseFile(String str, String str2);

    public static native int nativeRestoreSecureDatabaseFile(String str, String str2, byte[] bArr);

    private static native void nativeSetCheckpointOnClose(long j, boolean z);

    private static native byte[] nativeSetPassword(long j, byte[] bArr);

    private SQLiteConnection(SQLiteConnectionPool pool, SQLiteDatabaseConfiguration configuration, int connectionId, boolean primaryConnection) {
        CloseGuard closeGuard = CloseGuard.get();
        this.mCloseGuard = closeGuard;
        this.mPool = pool;
        SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration = new SQLiteDatabaseConfiguration(configuration);
        this.mConfiguration = sQLiteDatabaseConfiguration;
        this.mConnectionId = connectionId;
        this.mIsPrimaryConnection = primaryConnection;
        this.mIsReadOnlyConnection = sQLiteDatabaseConfiguration.isReadOnlyDatabase();
        this.mPreparedStatementCache = new PreparedStatementCache(sQLiteDatabaseConfiguration.maxSqlCacheSize);
        this.mRecentOperations = new OperationLog(pool, this, sQLiteDatabaseConfiguration);
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

    static SQLiteConnection open(SQLiteConnectionPool pool, SQLiteDatabaseConfiguration configuration, int connectionId, boolean primaryConnection) {
        SQLiteConnection connection = new SQLiteConnection(pool, configuration, connectionId, primaryConnection);
        try {
            connection.open();
            return connection;
        } catch (SQLiteException ex) {
            if (ex instanceof SQLiteDatabaseCorruptException) {
                connection.setCheckpointOnClose(false);
            }
            connection.dispose(false);
            throw ex;
        }
    }

    static SQLiteConnection openSecure(SQLiteConnectionPool pool, SQLiteDatabaseConfiguration configuration, int connectionId, boolean primaryConnection, byte[] password) {
        SQLiteConnection connection = new SQLiteConnection(pool, configuration, connectionId, primaryConnection);
        try {
            connection.open(password);
            return connection;
        } catch (SQLiteException ex) {
            if (ex instanceof SQLiteDatabaseCorruptException) {
                connection.setCheckpointOnClose(false);
            }
            connection.dispose(false);
            throw ex;
        }
    }

    void close() {
        dispose(false);
    }

    private void open(byte[] password) {
        String file = this.mConfiguration.path;
        int cookie = this.mRecentOperations.beginOperation(RcsContactPresenceTuple.TUPLE_BASIC_STATUS_OPEN, null, null);
        try {
            try {
                this.mConnectionPtr = nativeOpen(file, this.mConfiguration.openFlags, this.mConfiguration.label, SQLiteDebug.NoPreloadHolder.DEBUG_SQL_STATEMENTS, SQLiteDebug.NoPreloadHolder.DEBUG_SQL_TIME, this.mConfiguration.lookasideSlotSize, this.mConfiguration.lookasideSlotCount);
                this.mRecentOperations.endOperation(cookie);
                if (this.mConnectionPtr == 0) {
                    return;
                }
                byte[] key = setPassword(password);
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
                } catch (SQLiteReadOnlyDatabaseException ex) {
                    if (isForcedReadOnlyConnection()) {
                        Log.m98i(TAG, "This connection is forced to be a read-only connection. Ignore SQLiteReadOnlyDatabaseException.");
                    } else {
                        throw ex;
                    }
                }
                this.mPool.saveConnectionKey(key);
                this.mIsOpen = true;
            } catch (SQLiteCantOpenDatabaseException e) {
                StringBuilder message = new StringBuilder("Cannot open database '").append(file).append(DateFormat.QUOTE).append(" with flags 0x").append(Integer.toHexString(this.mConfiguration.openFlags));
                try {
                    Path path = FileSystems.getDefault().getPath(file, new String[0]);
                    Path dir = path.getParent();
                    if (dir == null) {
                        message.append(": Directory not specified in the file path");
                    } else if (!Files.isDirectory(dir, new LinkOption[0])) {
                        message.append(": Directory ").append(dir).append(" doesn't exist");
                    } else if (!Files.exists(path, new LinkOption[0])) {
                        message.append(": File ").append(path).append(" doesn't exist");
                        if ((this.mConfiguration.openFlags & 268435456) != 0) {
                            message.append(" and CREATE_IF_NECESSARY is set, check directory permissions");
                        }
                    } else if (!Files.isReadable(path)) {
                        message.append(": File ").append(path).append(" is not readable");
                    } else if (Files.isDirectory(path, new LinkOption[0])) {
                        message.append(": Path ").append(path).append(" is a directory");
                    } else {
                        message.append(": Unable to deduct failure reason");
                    }
                } catch (Throwable th) {
                    message.append(": Unable to deduct failure reason because filesystem couldn't be examined: ").append(th.getMessage());
                }
                throw new SQLiteCantOpenDatabaseException(message.toString(), e);
            }
        } catch (Throwable th2) {
            this.mRecentOperations.endOperation(cookie);
            throw th2;
        }
    }

    private void open() {
        String file = this.mConfiguration.path;
        int cookie = this.mRecentOperations.beginOperation(RcsContactPresenceTuple.TUPLE_BASIC_STATUS_OPEN, null, null);
        try {
            try {
                this.mConnectionPtr = nativeOpen(file, this.mConfiguration.openFlags, this.mConfiguration.label, SQLiteDebug.NoPreloadHolder.DEBUG_SQL_STATEMENTS, SQLiteDebug.NoPreloadHolder.DEBUG_SQL_TIME, this.mConfiguration.lookasideSlotSize, this.mConfiguration.lookasideSlotCount);
                this.mRecentOperations.endOperation(cookie);
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
                } catch (SQLiteReadOnlyDatabaseException ex) {
                    if (isForcedReadOnlyConnection()) {
                        Log.m98i(TAG, "This connection is forced to be a read-only connection. Ignore SQLiteReadOnlyDatabaseException.");
                    } else {
                        throw ex;
                    }
                }
                this.mIsOpen = true;
            } catch (SQLiteCantOpenDatabaseException e) {
                StringBuilder message = new StringBuilder("Cannot open database '").append(file).append(DateFormat.QUOTE).append(" with flags 0x").append(Integer.toHexString(this.mConfiguration.openFlags));
                try {
                    Path path = FileSystems.getDefault().getPath(file, new String[0]);
                    Path dir = path.getParent();
                    if (dir == null) {
                        message.append(": Directory not specified in the file path");
                    } else if (!Files.isDirectory(dir, new LinkOption[0])) {
                        message.append(": Directory ").append(dir).append(" doesn't exist");
                    } else if (!Files.exists(path, new LinkOption[0])) {
                        message.append(": File ").append(path).append(" doesn't exist");
                        if ((this.mConfiguration.openFlags & 268435456) != 0) {
                            message.append(" and CREATE_IF_NECESSARY is set, check directory permissions");
                        }
                    } else if (!Files.isReadable(path)) {
                        message.append(": File ").append(path).append(" is not readable");
                    } else if (Files.isDirectory(path, new LinkOption[0])) {
                        message.append(": Path ").append(path).append(" is a directory");
                    } else {
                        message.append(": Unable to deduct failure reason");
                    }
                } catch (Throwable th) {
                    message.append(": Unable to deduct failure reason because filesystem couldn't be examined: ").append(th.getMessage());
                }
                throw new SQLiteCantOpenDatabaseException(message.toString(), e);
            }
        } catch (Throwable ex2) {
            this.mRecentOperations.endOperation(cookie);
            throw ex2;
        }
    }

    private void dispose(boolean finalized) {
        CloseGuard closeGuard = this.mCloseGuard;
        if (closeGuard != null) {
            if (finalized) {
                closeGuard.warnIfOpen();
            }
            this.mCloseGuard.close();
        }
        if (this.mConnectionPtr != 0) {
            int cookie = this.mRecentOperations.beginOperation("close", null, null);
            try {
                this.mPreparedStatementCache.evictAll();
                nativeClose(this.mConnectionPtr);
                this.mConnectionPtr = 0L;
                this.mIsOpen = false;
                if (SQLiteDebug.NoPreloadHolder.DEBUG_ENABLE) {
                    destroyExpert();
                }
            } finally {
                if (this.mRecentOperations.endOperationDeferLog(cookie)) {
                    this.mRecentOperations.logOperation(cookie, "window='" + this.mConfiguration.path + "'");
                }
            }
        }
    }

    private void setPageSize() {
        if (!this.mConfiguration.isInMemoryDb() && !this.mIsReadOnlyConnection) {
            long newValue = SQLiteGlobal.getDefaultPageSize();
            long value = executeForLong("PRAGMA page_size", null, null);
            if (value != newValue) {
                execute("PRAGMA page_size=" + newValue, null, null);
            }
        }
    }

    private void setAutoCheckpointInterval() {
        if (!this.mConfiguration.isInMemoryDb() && !this.mIsReadOnlyConnection) {
            long newValue = this.mConfiguration.sharedConfig.getAutoCheckpoint();
            long value = executeForLong("PRAGMA wal_autocheckpoint", null, null);
            if (value != newValue) {
                executeForLong("PRAGMA wal_autocheckpoint=" + newValue, null, null);
            }
        }
    }

    private void setJournalSizeLimit() {
        if (!this.mConfiguration.isInMemoryDb() && !this.mIsReadOnlyConnection) {
            long newValue = this.mConfiguration.sharedConfig.getJournalSizeLimit();
            long value = executeForLong("PRAGMA journal_size_limit", null, null);
            if (value != newValue) {
                executeForLong("PRAGMA journal_size_limit=" + newValue, null, null);
            }
        }
    }

    private void setAutomaticIndexFromConfiguration() {
        long newValue = this.mConfiguration.automaticIndexEnabled ? 1L : 0L;
        long value = executeForLong("PRAGMA automatic_index", null, null);
        if (value != newValue) {
            execute("PRAGMA automatic_index=" + newValue, null, null);
        }
    }

    private void setCaseSensitiveLikeFromConfiguration() {
        long newValue = this.mConfiguration.caseSensitiveLikeEnabled ? 1L : 0L;
        long value = -1;
        try {
            value = executeForLong("PRAGMA case_sensitive_like", null, null);
        } catch (SQLiteException ex) {
            Log.m97e(TAG, "Error getting case_sensitive_like", ex);
        }
        if (value != newValue) {
            execute("PRAGMA case_sensitive_like=" + newValue, null, null);
        }
    }

    private void setBusyTimeoutFromConfiguration() {
        long newValue = this.mConfiguration.busyTimeout;
        long value = 2500;
        try {
            value = executeForLong("PRAGMA busy_timeout", null, null);
        } catch (SQLiteException ex) {
            Log.m97e(TAG, "Error getting busy_timeout", ex);
        }
        if (value != newValue) {
            try {
                executeForLong("PRAGMA busy_timeout=" + newValue, null, null);
            } catch (SQLiteException ex2) {
                Log.m97e(TAG, "Error setting busy_timeout", ex2);
            }
        }
    }

    private void setCacheSizeFromConfiguration() {
        long newValue = this.mConfiguration.cacheSize == 0 ? SQLiteGlobal.getDefaultCacheSize() : this.mConfiguration.cacheSize;
        long value = executeForLong("PRAGMA cache_size", null, null);
        if (value != newValue) {
            execute("PRAGMA cache_size=" + newValue, null, null);
        }
    }

    private void setForeignKeyModeFromConfiguration() {
        if (!this.mIsReadOnlyConnection) {
            long newValue = this.mConfiguration.foreignKeyConstraintsEnabled ? 1L : 0L;
            long value = executeForLong("PRAGMA foreign_keys", null, null);
            if (value != newValue) {
                execute("PRAGMA foreign_keys=" + newValue, null, null);
            }
        }
    }

    private void setJournalFromConfiguration() {
        if (!this.mIsReadOnlyConnection && this.mIsPrimaryConnection) {
            setJournalMode(this.mConfiguration.resolveJournalMode());
            if (!this.mConfiguration.sharedConfig.isMediaStoreDb) {
                maybeTruncateWalFile();
                return;
            }
            return;
        }
        this.mConfiguration.shouldTruncateWalFile = false;
    }

    private void setSyncModeFromConfiguration() {
        if (!this.mIsReadOnlyConnection && this.mIsPrimaryConnection) {
            setSyncMode(this.mConfiguration.resolveSyncMode());
        }
    }

    private void setPageIntegrityLevel(String newLevel) {
        try {
            String level = executeForString("PRAGMA page_integrity_level", null, null);
            if (!level.equalsIgnoreCase(newLevel)) {
                execute("PRAGMA page_integrity_level=" + newLevel, null, null);
            }
        } catch (SQLiteException e) {
        }
    }

    private void maybeTruncateWalFile() {
        if (!this.mConfiguration.shouldTruncateWalFile) {
            return;
        }
        long threshold = SQLiteGlobal.getWALTruncateSize();
        if (threshold == 0) {
            return;
        }
        File walFile = new File(this.mConfiguration.path + "-wal");
        if (!walFile.isFile()) {
            return;
        }
        long size = walFile.length();
        if (size < threshold) {
            return;
        }
        Log.m98i(TAG, walFile.getAbsolutePath() + " " + size + " bytes: Bigger than " + threshold + "; truncating");
        try {
            executeForString("PRAGMA wal_checkpoint(TRUNCATE)", null, null);
            this.mConfiguration.shouldTruncateWalFile = false;
        } catch (SQLiteException e) {
            Log.m103w(TAG, "Failed to truncate the -wal file", e);
        }
    }

    private void setSyncMode(String newValue) {
        if (TextUtils.isEmpty(newValue)) {
            return;
        }
        String value = executeForString("PRAGMA synchronous", null, null);
        if (!canonicalizeSyncMode(value).equalsIgnoreCase(canonicalizeSyncMode(newValue))) {
            execute("PRAGMA synchronous=" + newValue, null, null);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static String canonicalizeSyncMode(String value) {
        char c;
        switch (value.hashCode()) {
            case 48:
                if (value.equals("0")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 49:
                if (value.equals("1")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 50:
                if (value.equals("2")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 51:
                if (value.equals("3")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return "OFF";
            case 1:
                return SQLiteDatabase.SYNC_MODE_NORMAL;
            case 2:
                return "FULL";
            case 3:
                return "EXTRA";
            default:
                return value;
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
                long value = executeForLong("PRAGMA enable_assert_log=1", null, null);
                if (value == 0) {
                    Log.m94d(TAG, "Failed to enable assert log for " + this.mConfiguration.path);
                }
            } catch (SQLiteException e) {
            }
        }
    }

    private void setJournalMode(String newValue) {
        if (TextUtils.isEmpty(newValue)) {
            return;
        }
        String value = executeForString("PRAGMA journal_mode", null, null);
        if (!value.equalsIgnoreCase(newValue)) {
            try {
                String result = executeForString("PRAGMA journal_mode=" + newValue, null, null);
                if (result.equalsIgnoreCase(newValue)) {
                    return;
                }
            } catch (SQLiteDatabaseLockedException e) {
            }
            Log.m102w(TAG, "Could not change the database journal mode of '" + this.mConfiguration.label + "' from '" + value + "' to '" + newValue + "' because the database is locked.  This usually means that there are other open connections to the database which prevents the database from enabling or disabling write-ahead logging mode.  Proceeding without changing the journal mode.");
        }
    }

    private void setLocaleFromConfiguration() {
        String str;
        str = "COMMIT";
        if ((this.mConfiguration.openFlags & 16) != 0) {
            return;
        }
        String newLocale = this.mConfiguration.locale.toString();
        nativeRegisterLocalizedCollators(this.mConnectionPtr, newLocale);
        if (!this.mConfiguration.isInMemoryDb()) {
            checkDatabaseWiped();
        }
        if (this.mIsReadOnlyConnection || !this.mIsPrimaryConnection) {
            return;
        }
        try {
            try {
                execute("CREATE TABLE IF NOT EXISTS android_metadata (locale TEXT)", null, null);
                String oldLocale = executeForString("SELECT locale FROM android_metadata UNION SELECT NULL ORDER BY locale DESC LIMIT 1", null, null);
                if (oldLocale != null && oldLocale.equals(newLocale)) {
                    return;
                }
                execute("BEGIN", null, null);
                try {
                    execute("DELETE FROM android_metadata", null, null);
                    execute("INSERT INTO android_metadata (locale) VALUES(?)", new Object[]{newLocale}, null);
                    execute("REINDEX LOCALIZED", null, null);
                    execute(1 == 0 ? "ROLLBACK" : "COMMIT", null, null);
                } catch (Throwable th) {
                    if (0 == 0) {
                        str = "ROLLBACK";
                    }
                    execute(str, null, null);
                    throw th;
                }
            } catch (RuntimeException ex) {
                throw new SQLiteException("Failed to change locale for db '" + this.mConfiguration.label + "' to '" + newLocale + "'.", ex);
            }
        } catch (SQLiteDatabaseCorruptException | SQLiteFullException | SQLiteReadOnlyDatabaseException ex2) {
            Log.m96e(TAG, "Failed to change locale for db'" + this.mConfiguration.label + "' to '" + newLocale + "'.");
            throw ex2;
        }
    }

    void setCheckpointOnClose(boolean set) {
        long j = this.mConnectionPtr;
        if (j != 0) {
            nativeSetCheckpointOnClose(j, set);
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

    private void executePerConnectionSqlFromConfiguration(int startIndex) {
        for (int i = startIndex; i < this.mConfiguration.perConnectionSql.size(); i++) {
            Pair<String, Object[]> statement = this.mConfiguration.perConnectionSql.get(i);
            int type = DatabaseUtils.getSqlStatementType(statement.first);
            switch (type) {
                case 1:
                    executeForString(statement.first, statement.second, null);
                    break;
                case 7:
                    execute(statement.first, statement.second, null);
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported configuration statement: " + statement);
            }
        }
    }

    private void checkDatabaseWiped() {
        if (!SQLiteGlobal.checkDbWipe()) {
            return;
        }
        try {
            File checkFile = new File(this.mConfiguration.path + "-wipecheck");
            boolean hasMetadataTable = executeForLong("SELECT count(*) FROM sqlite_master WHERE type='table' AND name='android_metadata'", null, null) > 0;
            boolean hasCheckFile = checkFile.exists();
            if (!this.mIsReadOnlyConnection && !hasCheckFile) {
                checkFile.createNewFile();
            }
            if (!hasMetadataTable && hasCheckFile) {
                SQLiteDatabase.wipeDetected(this.mConfiguration.path, "unknown");
            }
        } catch (IOException | RuntimeException ex) {
            SQLiteDatabase.wtfAsSystemServer(TAG, "Unexpected exception while checking for wipe", ex);
        }
    }

    void reconfigure(SQLiteDatabaseConfiguration configuration) {
        this.mOnlyAllowReadOnlyOperations = false;
        boolean foreignKeyModeChanged = configuration.foreignKeyConstraintsEnabled != this.mConfiguration.foreignKeyConstraintsEnabled;
        boolean localeChanged = !configuration.locale.equals(this.mConfiguration.locale);
        boolean automaticIndexChanged = configuration.automaticIndexEnabled != this.mConfiguration.automaticIndexEnabled;
        boolean busyTimeoutChanged = configuration.busyTimeout != this.mConfiguration.busyTimeout;
        boolean cacheSizeChanged = configuration.cacheSize != this.mConfiguration.cacheSize;
        boolean caseSensitiveLikeChanged = configuration.caseSensitiveLikeEnabled != this.mConfiguration.caseSensitiveLikeEnabled;
        boolean customScalarFunctionsChanged = !configuration.customScalarFunctions.equals(this.mConfiguration.customScalarFunctions);
        boolean customAggregateFunctionsChanged = !configuration.customAggregateFunctions.equals(this.mConfiguration.customAggregateFunctions);
        int oldSize = this.mConfiguration.perConnectionSql.size();
        int newSize = configuration.perConnectionSql.size();
        boolean perConnectionSqlChanged = newSize > oldSize;
        this.mConfiguration.updateParametersFrom(configuration);
        this.mPreparedStatementCache.resize(configuration.maxSqlCacheSize);
        if (foreignKeyModeChanged) {
            setForeignKeyModeFromConfiguration();
        }
        boolean journalModeChanged = !configuration.resolveJournalMode().equalsIgnoreCase(this.mConfiguration.resolveJournalMode());
        if (journalModeChanged) {
            setJournalFromConfiguration();
        }
        boolean syncModeChanged = !configuration.resolveSyncMode().equalsIgnoreCase(this.mConfiguration.resolveSyncMode());
        if (syncModeChanged) {
            setSyncModeFromConfiguration();
        }
        if (localeChanged) {
            setLocaleFromConfiguration();
        }
        if (automaticIndexChanged) {
            setAutomaticIndexFromConfiguration();
        }
        if (busyTimeoutChanged) {
            setBusyTimeoutFromConfiguration();
        }
        if (cacheSizeChanged) {
            setCacheSizeFromConfiguration();
        }
        if (caseSensitiveLikeChanged) {
            setCaseSensitiveLikeFromConfiguration();
        }
        if (customScalarFunctionsChanged || customAggregateFunctionsChanged) {
            setCustomFunctionsFromConfiguration();
        }
        if (perConnectionSqlChanged) {
            executePerConnectionSqlFromConfiguration(oldSize);
        }
    }

    void setOnlyAllowReadOnlyOperations(boolean readOnly) {
        this.mOnlyAllowReadOnlyOperations = readOnly;
    }

    boolean isPreparedStatementInCache(String sql) {
        return this.mPreparedStatementCache.get(sql) != null;
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
        } catch (Exception e) {
        }
    }

    public void prepare(String sql, SQLiteStatementInfo outStatementInfo) {
        if (sql == null) {
            throw new IllegalArgumentException("sql must not be null.");
        }
        int cookie = this.mRecentOperations.beginOperation("prepare", sql, null);
        try {
            try {
                PreparedStatement statement = acquirePreparedStatement(sql);
                if (outStatementInfo != null) {
                    try {
                        outStatementInfo.numParameters = statement.mNumParameters;
                        outStatementInfo.readOnly = statement.mReadOnly;
                        int columnCount = nativeGetColumnCount(this.mConnectionPtr, statement.mStatementPtr);
                        if (columnCount == 0) {
                            outStatementInfo.columnNames = EMPTY_STRING_ARRAY;
                        } else {
                            outStatementInfo.columnNames = new String[columnCount];
                            for (int i = 0; i < columnCount; i++) {
                                outStatementInfo.columnNames[i] = nativeGetColumnName(this.mConnectionPtr, statement.mStatementPtr, i);
                            }
                        }
                    } finally {
                        releasePreparedStatement(statement);
                    }
                }
            } catch (RuntimeException ex) {
                this.mRecentOperations.failOperation(cookie, ex);
                throw ex;
            }
        } finally {
            if (this.mRecentOperations.endOperationDeferLog(cookie)) {
                this.mRecentOperations.logOperation(cookie, "window='" + this.mConfiguration.path + "'");
            }
        }
    }

    public void execute(String sql, Object[] bindArgs, CancellationSignal cancellationSignal) {
        if (sql == null) {
            throw new IllegalArgumentException("sql must not be null.");
        }
        int cookie = this.mRecentOperations.beginOperation("execute", sql, bindArgs);
        try {
            try {
                boolean isPragmaStmt = DatabaseUtils.getSqlStatementType(sql) == 7;
                PreparedStatement statement = acquirePreparedStatement(sql);
                try {
                    throwIfStatementForbidden(statement);
                    bindArguments(statement, bindArgs);
                    applyBlockGuardPolicy(statement);
                    attachCancellationSignal(cancellationSignal);
                    try {
                        nativeExecute(this.mConnectionPtr, statement.mStatementPtr, isPragmaStmt);
                    } finally {
                        detachCancellationSignal(cancellationSignal);
                    }
                } finally {
                    releasePreparedStatement(statement);
                }
            } finally {
                int ret = this.mRecentOperations.endOperationDeferLogOrCollect(cookie);
                if ((ret & 1) != 0) {
                    this.mRecentOperations.logOperation(cookie, "window='" + this.mConfiguration.path + "'");
                }
                if ((ret & 2) != 0) {
                    this.mRecentOperations.collectOperation(cookie);
                }
            }
        } catch (RuntimeException ex) {
            this.mRecentOperations.failOperation(cookie, ex);
            throw ex;
        }
    }

    public long executeForLong(String sql, Object[] bindArgs, CancellationSignal cancellationSignal) {
        if (sql == null) {
            throw new IllegalArgumentException("sql must not be null.");
        }
        int cookie = this.mRecentOperations.beginOperation("executeForLong", sql, bindArgs);
        try {
            try {
                PreparedStatement statement = acquirePreparedStatement(sql);
                try {
                    throwIfStatementForbidden(statement);
                    bindArguments(statement, bindArgs);
                    applyBlockGuardPolicy(statement);
                    attachCancellationSignal(cancellationSignal);
                    try {
                        long ret = nativeExecuteForLong(this.mConnectionPtr, statement.mStatementPtr);
                        this.mRecentOperations.setResult(ret);
                        return ret;
                    } finally {
                        detachCancellationSignal(cancellationSignal);
                    }
                } finally {
                    releasePreparedStatement(statement);
                }
            } finally {
                int ret2 = this.mRecentOperations.endOperationDeferLogOrCollect(cookie);
                if ((ret2 & 1) != 0) {
                    this.mRecentOperations.logOperation(cookie, "window='" + this.mConfiguration.path + "'");
                }
                if ((ret2 & 2) != 0) {
                    this.mRecentOperations.collectOperation(cookie);
                }
            }
        } catch (RuntimeException ex) {
            this.mRecentOperations.failOperation(cookie, ex);
            throw ex;
        }
    }

    public String executeForString(String sql, Object[] bindArgs, CancellationSignal cancellationSignal) {
        if (sql == null) {
            throw new IllegalArgumentException("sql must not be null.");
        }
        int cookie = this.mRecentOperations.beginOperation("executeForString", sql, bindArgs);
        try {
            try {
                PreparedStatement statement = acquirePreparedStatement(sql);
                try {
                    throwIfStatementForbidden(statement);
                    bindArguments(statement, bindArgs);
                    applyBlockGuardPolicy(statement);
                    attachCancellationSignal(cancellationSignal);
                    try {
                        String ret = nativeExecuteForString(this.mConnectionPtr, statement.mStatementPtr);
                        this.mRecentOperations.setResult(ret);
                        return ret;
                    } finally {
                        detachCancellationSignal(cancellationSignal);
                    }
                } finally {
                    releasePreparedStatement(statement);
                }
            } finally {
                int ret2 = this.mRecentOperations.endOperationDeferLogOrCollect(cookie);
                if ((ret2 & 1) != 0) {
                    this.mRecentOperations.logOperation(cookie, "window='" + this.mConfiguration.path + "'");
                }
                if ((ret2 & 2) != 0) {
                    this.mRecentOperations.collectOperation(cookie);
                }
            }
        } catch (RuntimeException ex) {
            this.mRecentOperations.failOperation(cookie, ex);
            throw ex;
        }
    }

    public ParcelFileDescriptor executeForBlobFileDescriptor(String sql, Object[] bindArgs, CancellationSignal cancellationSignal) {
        if (sql == null) {
            throw new IllegalArgumentException("sql must not be null.");
        }
        int cookie = this.mRecentOperations.beginOperation("executeForBlobFileDescriptor", sql, bindArgs);
        try {
            try {
                PreparedStatement statement = acquirePreparedStatement(sql);
                try {
                    throwIfStatementForbidden(statement);
                    bindArguments(statement, bindArgs);
                    applyBlockGuardPolicy(statement);
                    attachCancellationSignal(cancellationSignal);
                    try {
                        int fd = nativeExecuteForBlobFileDescriptor(this.mConnectionPtr, statement.mStatementPtr);
                        return fd >= 0 ? ParcelFileDescriptor.adoptFd(fd) : null;
                    } finally {
                        detachCancellationSignal(cancellationSignal);
                    }
                } finally {
                    releasePreparedStatement(statement);
                }
            } catch (RuntimeException ex) {
                this.mRecentOperations.failOperation(cookie, ex);
                throw ex;
            }
        } finally {
            int ret = this.mRecentOperations.endOperationDeferLogOrCollect(cookie);
            if ((ret & 1) != 0) {
                this.mRecentOperations.logOperation(cookie, "window='" + this.mConfiguration.path + "'");
            }
            if ((ret & 2) != 0) {
                this.mRecentOperations.collectOperation(cookie);
            }
        }
    }

    public int executeForChangedRowCount(String sql, Object[] bindArgs, CancellationSignal cancellationSignal) {
        if (sql == null) {
            throw new IllegalArgumentException("sql must not be null.");
        }
        int changedRows = 0;
        int cookie = this.mRecentOperations.beginOperation("executeForChangedRowCount", sql, bindArgs);
        try {
            try {
                PreparedStatement statement = acquirePreparedStatement(sql);
                try {
                    throwIfStatementForbidden(statement);
                    bindArguments(statement, bindArgs);
                    applyBlockGuardPolicy(statement);
                    attachCancellationSignal(cancellationSignal);
                    try {
                        changedRows = nativeExecuteForChangedRowCount(this.mConnectionPtr, statement.mStatementPtr);
                        return changedRows;
                    } finally {
                        detachCancellationSignal(cancellationSignal);
                    }
                } finally {
                    releasePreparedStatement(statement);
                }
            } catch (RuntimeException ex) {
                this.mRecentOperations.failOperation(cookie, ex);
                throw ex;
            }
        } finally {
            int ret = this.mRecentOperations.endOperationDeferLogOrCollect(cookie);
            if ((ret & 1) != 0) {
                this.mRecentOperations.logOperation(cookie, "window='" + this.mConfiguration.path + "', changedRows=" + changedRows);
            }
            if ((ret & 2) != 0) {
                this.mRecentOperations.collectOperation(cookie, changedRows, 0);
            }
        }
    }

    public long executeForLastInsertedRowId(String sql, Object[] bindArgs, CancellationSignal cancellationSignal) {
        if (sql == null) {
            throw new IllegalArgumentException("sql must not be null.");
        }
        int cookie = this.mRecentOperations.beginOperation("executeForLastInsertedRowId", sql, bindArgs);
        try {
            try {
                PreparedStatement statement = acquirePreparedStatement(sql);
                try {
                    throwIfStatementForbidden(statement);
                    bindArguments(statement, bindArgs);
                    applyBlockGuardPolicy(statement);
                    attachCancellationSignal(cancellationSignal);
                    try {
                        return nativeExecuteForLastInsertedRowId(this.mConnectionPtr, statement.mStatementPtr);
                    } finally {
                        detachCancellationSignal(cancellationSignal);
                    }
                } finally {
                    releasePreparedStatement(statement);
                }
            } catch (RuntimeException ex) {
                this.mRecentOperations.failOperation(cookie, ex);
                throw ex;
            }
        } finally {
            int ret = this.mRecentOperations.endOperationDeferLogOrCollect(cookie);
            if ((ret & 1) != 0) {
                this.mRecentOperations.logOperation(cookie, "window='" + this.mConfiguration.path + "'");
            }
            if ((ret & 2) != 0) {
                this.mRecentOperations.collectOperation(cookie);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v1, types: [android.database.sqlite.SQLiteConnection$OperationLog] */
    /* JADX WARN: Type inference failed for: r11v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r12v1 */
    /* JADX WARN: Type inference failed for: r12v14 */
    /* JADX WARN: Type inference failed for: r12v21 */
    /* JADX WARN: Type inference failed for: r12v3, types: [int] */
    /* JADX WARN: Type inference failed for: r14v3, types: [android.database.sqlite.SQLiteConnection$OperationLog] */
    /* JADX WARN: Type inference failed for: r2v9, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v10 */
    /* JADX WARN: Type inference failed for: r3v11 */
    /* JADX WARN: Type inference failed for: r3v3, types: [java.lang.String] */
    public int executeForCursorWindow(String str, Object[] objArr, CursorWindow cursorWindow, int i, int i2, boolean z, CancellationSignal cancellationSignal) {
        ?? r3;
        String str2;
        ?? r12;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        PreparedStatement preparedStatement;
        String str3;
        int i8;
        String str4 = str;
        Object[] objArr2 = objArr;
        String str5 = ", countedRows=";
        String str6 = ", filledRows=";
        ?? r11 = ", actualPos=";
        if (str4 == null) {
            throw new IllegalArgumentException("sql must not be null.");
        }
        if (cursorWindow == null) {
            throw new IllegalArgumentException("window must not be null.");
        }
        cursorWindow.acquireReference();
        try {
            String str7 = "executeForCursorWindow";
            int beginOperation = this.mRecentOperations.beginOperation("executeForCursorWindow", str4, objArr2);
            try {
                try {
                    try {
                        PreparedStatement acquirePreparedStatement = acquirePreparedStatement(str);
                        try {
                            throwIfStatementForbidden(acquirePreparedStatement);
                            bindArguments(acquirePreparedStatement, objArr2);
                            applyBlockGuardPolicy(acquirePreparedStatement);
                            attachCancellationSignal(cancellationSignal);
                            try {
                            } catch (Throwable th) {
                                th = th;
                            }
                            try {
                                preparedStatement = acquirePreparedStatement;
                                str3 = "', startPos=";
                                str4 = "window='";
                                try {
                                    long nativeExecuteForCursorWindow = nativeExecuteForCursorWindow(this.mConnectionPtr, acquirePreparedStatement.mStatementPtr, cursorWindow.mWindowPtr, i, i2, z);
                                    i4 = (int) (nativeExecuteForCursorWindow >> 32);
                                    i5 = (int) nativeExecuteForCursorWindow;
                                    try {
                                        i6 = cursorWindow.getNumRows();
                                        try {
                                            cursorWindow.setStartPosition(i4);
                                            cursorWindow.setFilledRows(i6);
                                            if (z) {
                                                try {
                                                    cursorWindow.setTotalRows(i5);
                                                } catch (Throwable th2) {
                                                    th = th2;
                                                    i7 = beginOperation;
                                                    try {
                                                        detachCancellationSignal(cancellationSignal);
                                                        throw th;
                                                    } catch (Throwable th3) {
                                                        th = th3;
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
                                            } catch (Throwable th4) {
                                                th = th4;
                                                i7 = beginOperation;
                                                releasePreparedStatement(preparedStatement);
                                                throw th;
                                            }
                                        } catch (Throwable th5) {
                                            th = th5;
                                            i7 = beginOperation;
                                        }
                                    } catch (Throwable th6) {
                                        th = th6;
                                        i7 = beginOperation;
                                    }
                                } catch (Throwable th7) {
                                    th = th7;
                                    i7 = beginOperation;
                                }
                            } catch (Throwable th8) {
                                th = th8;
                                i7 = beginOperation;
                                preparedStatement = acquirePreparedStatement;
                                detachCancellationSignal(cancellationSignal);
                                throw th;
                            }
                        } catch (Throwable th9) {
                            th = th9;
                            i7 = beginOperation;
                            preparedStatement = acquirePreparedStatement;
                        }
                    } catch (Throwable th10) {
                        th = th10;
                        i4 = -1;
                        i5 = -1;
                        i6 = -1;
                        r3 = objArr2;
                        i3 = r11;
                        r12 = str6;
                    }
                } catch (RuntimeException e2) {
                    e = e2;
                    i7 = beginOperation;
                } catch (Throwable th11) {
                    th = th11;
                    r3 = "', startPos=";
                    str4 = "window='";
                    str7 = ", filledRows=";
                    str2 = ", countedRows=";
                    r12 = beginOperation;
                    str5 = ", actualPos=";
                    i3 = i;
                    i4 = -1;
                    i5 = -1;
                    i6 = -1;
                }
                try {
                    releasePreparedStatement(preparedStatement);
                    try {
                        int endOperationDeferLogOrCollect = this.mRecentOperations.endOperationDeferLogOrCollect(beginOperation, i6, i5, cursorWindow.getTotalRows());
                        if ((endOperationDeferLogOrCollect & 1) != 0) {
                            i8 = beginOperation;
                            this.mRecentOperations.logOperation(i8, str4 + cursorWindow + str3 + i + ", actualPos=" + i4 + ", filledRows=" + i6 + ", countedRows=" + i5);
                        } else {
                            i8 = beginOperation;
                        }
                        if ((endOperationDeferLogOrCollect & 2) != 0) {
                            this.mRecentOperations.collectOperation(i8, i6, cursorWindow.getTotalRows());
                        }
                        cursorWindow.releaseReference();
                        return i5;
                    } catch (Throwable th12) {
                        th = th12;
                        cursorWindow.releaseReference();
                        throw th;
                    }
                } catch (RuntimeException e3) {
                    e = e3;
                    i7 = beginOperation;
                    this.mRecentOperations.failOperation(i7, e);
                    throw e;
                } catch (Throwable th13) {
                    th = th13;
                    i3 = i;
                    r12 = beginOperation;
                    str5 = ", actualPos=";
                    str7 = ", filledRows=";
                    str2 = ", countedRows=";
                    r3 = str3;
                    int endOperationDeferLogOrCollect2 = this.mRecentOperations.endOperationDeferLogOrCollect(r12, i6, i5, cursorWindow.getTotalRows());
                    if ((endOperationDeferLogOrCollect2 & 1) != 0) {
                        this.mRecentOperations.logOperation(r12, str4 + cursorWindow + r3 + i3 + str5 + i4 + str7 + i6 + str2 + i5);
                    }
                    if ((endOperationDeferLogOrCollect2 & 2) != 0) {
                        this.mRecentOperations.collectOperation(r12, i6, cursorWindow.getTotalRows());
                    }
                    throw th;
                }
            } catch (Throwable th14) {
                th = th14;
            }
        } catch (Throwable th15) {
            th = th15;
        }
    }

    public byte[] setPassword(byte[] password) {
        return nativeSetPassword(this.mConnectionPtr, password);
    }

    public byte[] changePassword(byte[] newPassword) {
        return nativeChangePassword(this.mConnectionPtr, newPassword);
    }

    public void exportDB(String attachedDB) {
        nativeExportDB(this.mConnectionPtr, attachedDB);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void printQueryPlan(String sql) {
        long result;
        if (this.mIsOpen) {
            long statementPtr = 0;
            CursorWindow window = null;
            try {
                try {
                    window = new CursorWindow("QueryPlan-" + Thread.currentThread().getId());
                    statementPtr = nativePrepareStatement(this.mConnectionPtr, "EXPLAIN QUERY PLAN " + sql);
                    result = nativeExecuteForCursorWindow(this.mConnectionPtr, statementPtr, window.mWindowPtr, 0, 0, true);
                } catch (RuntimeException ex) {
                    Log.m96e(TAG, "Failed to explain query plan : " + sql + " - " + ex.getMessage());
                    ex.printStackTrace();
                    if (window != null) {
                        window.close();
                    }
                    if (statementPtr == 0) {
                        return;
                    }
                }
                if (result == 0) {
                    window.close();
                    if (statementPtr != 0) {
                        nativeFinalizeStatement(this.mConnectionPtr, statementPtr);
                        return;
                    }
                    return;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("plan=\"");
                for (int i = 0; i < result; i++) {
                    int selectId = window.getInt(i, 0);
                    int order = window.getInt(i, 1);
                    int from = window.getInt(i, 2);
                    String detail = window.getString(i, 3);
                    sb.append(selectId + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + order + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + from + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + detail + " * ");
                }
                String hash = Integer.toHexString((sql + this.mConfiguration.path).hashCode());
                String log = sb.toString() + "\", sql=\"" + trimSqlForDisplay(sql) + "\", window='" + this.mConfiguration.path + "', hash=" + hash;
                Log.m94d("SQLiteQueryPlan", log);
                window.close();
                if (statementPtr == 0) {
                    return;
                }
                nativeFinalizeStatement(this.mConnectionPtr, statementPtr);
            } catch (Throwable th) {
                if (window != null) {
                    window.close();
                }
                if (statementPtr != 0) {
                    nativeFinalizeStatement(this.mConnectionPtr, statementPtr);
                }
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String analyzeSql(String sql) {
        synchronized (this) {
            if (!this.mIsOpen) {
                return null;
            }
            if (this.mExpertPtr == 0) {
                byte[] key = this.mPool.getConnectionKey();
                if ((this.mConfiguration.openFlags & 512) != 0 && key == null) {
                    Log.m96e(TAG, "Could not use expert without the key.");
                    return null;
                }
                this.mExpertPtr = nativeCreateExpert(this.mConfiguration.path, this.mConfiguration.locale.toString(), key);
            }
            long j = this.mExpertPtr;
            if (j == 0) {
                Log.m96e(TAG, "Could not use expert to analyze. No pointer.");
                return null;
            }
            return nativeExpertAnalyze(j, sql);
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

    private PreparedStatement acquirePreparedStatement(String sql) {
        this.mPool.mTotalPrepareStatements++;
        PreparedStatement statement = this.mPreparedStatementCache.get(sql);
        boolean skipCache = false;
        if (statement != null) {
            if (!statement.mInUse) {
                return statement;
            }
            skipCache = true;
        }
        this.mPool.mTotalPrepareStatementCacheMiss++;
        long statementPtr = nativePrepareStatement(this.mConnectionPtr, sql);
        try {
            int numParameters = nativeGetParameterCount(this.mConnectionPtr, statementPtr);
            int type = DatabaseUtils.getSqlStatementType(sql);
            boolean readOnly = nativeIsReadOnly(this.mConnectionPtr, statementPtr);
            statement = obtainPreparedStatement(sql, statementPtr, numParameters, type, readOnly);
            if (!skipCache && isCacheable(type)) {
                this.mPreparedStatementCache.put(sql, statement);
                statement.mInCache = true;
            }
            statement.mInUse = true;
            return statement;
        } catch (RuntimeException ex) {
            if (statement == null || !statement.mInCache) {
                nativeFinalizeStatement(this.mConnectionPtr, statementPtr);
            }
            throw ex;
        }
    }

    private void releasePreparedStatement(PreparedStatement statement) {
        statement.mInUse = false;
        if (statement.mInCache) {
            try {
                nativeResetStatementAndClearBindings(this.mConnectionPtr, statement.mStatementPtr);
                return;
            } catch (SQLiteException e) {
                this.mPreparedStatementCache.remove(statement.mSql);
                return;
            }
        }
        finalizePreparedStatement(statement);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finalizePreparedStatement(PreparedStatement statement) {
        nativeFinalizeStatement(this.mConnectionPtr, statement.mStatementPtr);
        recyclePreparedStatement(statement);
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

    private void bindArguments(PreparedStatement statement, Object[] bindArgs) {
        int count = bindArgs != null ? bindArgs.length : 0;
        if (count != statement.mNumParameters) {
            throw new SQLiteBindOrColumnIndexOutOfRangeException("Expected " + statement.mNumParameters + " bind arguments but " + count + " were provided.");
        }
        if (count == 0) {
            return;
        }
        long statementPtr = statement.mStatementPtr;
        for (int i = 0; i < count; i++) {
            Object arg = bindArgs[i];
            switch (DatabaseUtils.getTypeOfObject(arg)) {
                case 0:
                    nativeBindNull(this.mConnectionPtr, statementPtr, i + 1);
                    break;
                case 1:
                    nativeBindLong(this.mConnectionPtr, statementPtr, i + 1, ((Number) arg).longValue());
                    break;
                case 2:
                    nativeBindDouble(this.mConnectionPtr, statementPtr, i + 1, ((Number) arg).doubleValue());
                    break;
                case 3:
                default:
                    if (arg instanceof Boolean) {
                        nativeBindLong(this.mConnectionPtr, statementPtr, i + 1, ((Boolean) arg).booleanValue() ? 1L : 0L);
                        break;
                    } else {
                        nativeBindString(this.mConnectionPtr, statementPtr, i + 1, arg.toString());
                        break;
                    }
                case 4:
                    nativeBindBlob(this.mConnectionPtr, statementPtr, i + 1, (byte[]) arg);
                    break;
            }
        }
    }

    private void throwIfStatementForbidden(PreparedStatement statement) {
        if (this.mOnlyAllowReadOnlyOperations && !statement.mReadOnly) {
            throw new SQLiteException("Cannot execute this statement because it might modify the database but the connection is read-only.");
        }
    }

    private static boolean isCacheable(int statementType) {
        return statementType == 2 || statementType == 1;
    }

    private void applyBlockGuardPolicy(PreparedStatement statement) {
        if (!this.mConfiguration.isInMemoryDb()) {
            if (statement.mReadOnly) {
                BlockGuard.getThreadPolicy().onReadFromDisk();
            } else {
                BlockGuard.getThreadPolicy().onWriteToDisk();
            }
        }
    }

    public void dump(Printer printer, boolean verbose) {
        dumpUnsafe(printer, verbose);
    }

    void dumpUnsafe(Printer printer, boolean verbose) {
        printer.println("Connection #" + this.mConnectionId + ":");
        if (verbose) {
            printer.println("  connectionPtr: 0x" + Long.toHexString(this.mConnectionPtr));
        }
        printer.println("  isPrimaryConnection: " + this.mIsPrimaryConnection);
        printer.println("  onlyAllowReadOnlyOperations: " + this.mOnlyAllowReadOnlyOperations);
        this.mRecentOperations.dump(printer);
        if (verbose) {
            this.mPreparedStatementCache.dump(printer);
        }
    }

    String describeCurrentOperationUnsafe() {
        return this.mRecentOperations.describeCurrentOperation();
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    void collectDbStats(ArrayList<SQLiteDebug.DbStats> dbStatsList) {
        long pageCount;
        long pageSize;
        CursorWindow window;
        int lookaside = nativeGetDbLookaside(this.mConnectionPtr);
        long pageCount2 = 0;
        try {
            pageCount2 = executeForLong("PRAGMA page_count;", null, null);
            long pageSize2 = executeForLong("PRAGMA page_size;", null, null);
            pageCount = pageCount2;
            pageSize = pageSize2;
        } catch (SQLiteException e) {
            pageCount = pageCount2;
            pageSize = 0;
        }
        dbStatsList.add(getMainDbStatsUnsafe(lookaside, pageCount, pageSize));
        CursorWindow window2 = new CursorWindow("collectDbStats");
        CursorWindow window3 = window2;
        try {
            executeForCursorWindow("PRAGMA database_list;", null, window2, 0, 0, false, null);
            int i = 1;
            while (i < window3.getNumRows()) {
                window = window3;
                try {
                    try {
                        String name = window.getString(i, 1);
                        String path = window.getString(i, 2);
                        long pageCount3 = 0;
                        long pageSize3 = 0;
                        try {
                            pageCount3 = executeForLong("PRAGMA " + name + ".page_count;", null, null);
                            long pageSize4 = executeForLong("PRAGMA " + name + ".page_size;", null, null);
                            pageSize3 = pageSize4;
                        } catch (SQLiteException e2) {
                        }
                        StringBuilder label = new StringBuilder("  (attached) ").append(name);
                        if (!path.isEmpty()) {
                            label.append(": ").append(path);
                        }
                        dbStatsList.add(new SQLiteDebug.DbStats(label.toString(), pageCount3, pageSize3, 0, 0, 0, 0, false));
                        i++;
                        window3 = window;
                    } catch (SQLiteException e3) {
                    }
                } catch (Throwable th) {
                    th = th;
                    window.close();
                    throw th;
                }
            }
            window = window3;
        } catch (SQLiteException e4) {
            window = window3;
        } catch (Throwable th2) {
            th = th2;
            window = window3;
        }
        window.close();
    }

    void collectDbStatsUnsafe(ArrayList<SQLiteDebug.DbStats> dbStatsList) {
        dbStatsList.add(getMainDbStatsUnsafe(0, 0L, 0L));
    }

    private SQLiteDebug.DbStats getMainDbStatsUnsafe(int lookaside, long pageCount, long pageSize) {
        String label;
        if (!this.mIsPrimaryConnection) {
            label = this.mConfiguration.path + " (" + this.mConnectionId + NavigationBarInflaterView.KEY_CODE_END;
        } else {
            label = this.mConfiguration.path;
        }
        return new SQLiteDebug.DbStats(label, pageCount, pageSize, lookaside, this.mPreparedStatementCache.hitCount(), this.mPreparedStatementCache.missCount(), this.mPreparedStatementCache.size(), false);
    }

    public String toString() {
        return "SQLiteConnection: " + this.mConfiguration.path + " (" + this.mConnectionId + NavigationBarInflaterView.KEY_CODE_END;
    }

    private PreparedStatement obtainPreparedStatement(String sql, long statementPtr, int numParameters, int type, boolean readOnly) {
        PreparedStatement statement = this.mPreparedStatementPool;
        if (statement != null) {
            this.mPreparedStatementPool = statement.mPoolNext;
            statement.mPoolNext = null;
            statement.mInCache = false;
        } else {
            statement = new PreparedStatement();
        }
        statement.mSql = sql;
        statement.mStatementPtr = statementPtr;
        statement.mNumParameters = numParameters;
        statement.mType = type;
        statement.mReadOnly = readOnly;
        return statement;
    }

    private void recyclePreparedStatement(PreparedStatement statement) {
        statement.mSql = null;
        statement.mPoolNext = this.mPreparedStatementPool;
        this.mPreparedStatementPool = statement;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String trimSqlForDisplay(String sql) {
        return sql.replaceAll("[\\s]*\\n+[\\s]*", " ");
    }

    private static final class PreparedStatement {
        public boolean mInCache;
        public boolean mInUse;
        public int mNumParameters;
        public PreparedStatement mPoolNext;
        public boolean mReadOnly;
        public String mSql;
        public long mStatementPtr;
        public int mType;

        private PreparedStatement() {
        }
    }

    private final class PreparedStatementCache extends LruCache<String, PreparedStatement> {
        public PreparedStatementCache(int size) {
            super(size);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.util.LruCache
        public void entryRemoved(boolean evicted, String key, PreparedStatement oldValue, PreparedStatement newValue) {
            oldValue.mInCache = false;
            if (!oldValue.mInUse) {
                SQLiteConnection.this.finalizePreparedStatement(oldValue);
            }
        }

        public void dump(Printer printer) {
            printer.println("  Prepared statement cache:");
            Map<String, PreparedStatement> cache = snapshot();
            if (!cache.isEmpty()) {
                int i = 0;
                for (Map.Entry<String, PreparedStatement> entry : cache.entrySet()) {
                    PreparedStatement statement = entry.getValue();
                    if (statement.mInCache) {
                        String sql = entry.getKey();
                        printer.println("    " + i + ": statementPtr=0x" + Long.toHexString(statement.mStatementPtr) + ", numParameters=" + statement.mNumParameters + ", type=" + statement.mType + ", readOnly=" + statement.mReadOnly + ", sql=\"" + SQLiteConnection.trimSqlForDisplay(sql) + "\"");
                    }
                    i++;
                }
                return;
            }
            printer.println("    <none>");
        }
    }

    private static final class OperationLog {
        public static final int COLLECT_OPERATION = 2;
        private static final int COOKIE_GENERATION_SHIFT = 8;
        private static final int COOKIE_INDEX_MASK = 255;
        public static final int LOG_OPERATION = 1;
        private static final int MAX_RECENT_OPERATIONS = 30;
        private final SQLiteDatabaseConfiguration mConfiguration;
        private final SQLiteConnection mConnection;
        private int mGeneration;
        private int mIndex;
        private final SQLiteConnectionPool mPool;
        private String mResultString;
        private final Operation[] mOperations = new Operation[30];
        private long mResultLong = Long.MIN_VALUE;
        private long mLastCheckTime = -1;

        public OperationLog(SQLiteConnectionPool pool, SQLiteConnection connection, SQLiteDatabaseConfiguration configuration) {
            this.mPool = pool;
            this.mConnection = connection;
            this.mConfiguration = configuration;
        }

        public int beginOperation(String kind, String sql, Object[] bindArgs) {
            int i;
            this.mResultLong = Long.MIN_VALUE;
            this.mResultString = null;
            synchronized (this.mOperations) {
                int index = (this.mIndex + 1) % 30;
                Operation operation = this.mOperations[index];
                if (operation == null) {
                    operation = new Operation();
                    this.mOperations[index] = operation;
                } else {
                    operation.mFinished = false;
                    operation.mException = null;
                    if (operation.mBindArgs != null) {
                        operation.mBindArgs.clear();
                    }
                }
                operation.mStartWallTime = System.currentTimeMillis();
                operation.mStartTime = SystemClock.uptimeMillis();
                operation.mExecutionTime = 0L;
                operation.mKind = kind;
                operation.mSql = sql;
                operation.mCallingPid = Binder.getCallingPid();
                operation.mConnectionId = this.mConnection.mConnectionId;
                operation.mPath = this.mPool.getPath();
                operation.mResultLong = Long.MIN_VALUE;
                operation.mResultString = null;
                if (bindArgs != null) {
                    if (operation.mBindArgs == null) {
                        operation.mBindArgs = new ArrayList<>();
                    } else {
                        operation.mBindArgs.clear();
                    }
                    for (Object arg : bindArgs) {
                        if (!SQLiteDebug.NoPreloadHolder.DEBUG_ENABLE && arg != null && (arg instanceof byte[])) {
                            operation.mBindArgs.add(SQLiteConnection.EMPTY_BYTE_ARRAY);
                        } else {
                            operation.mBindArgs.add(arg);
                        }
                    }
                }
                int i2 = newOperationCookieLocked(index);
                operation.mCookie = i2;
                if (Trace.isTagEnabled(1048576L)) {
                    Trace.asyncTraceBegin(1048576L, operation.getTraceMethodName(), operation.mCookie);
                }
                this.mIndex = index;
                i = operation.mCookie;
            }
            return i;
        }

        public void failOperation(int cookie, Exception ex) {
            synchronized (this.mOperations) {
                Operation operation = getOperationLocked(cookie);
                if (operation != null) {
                    operation.mException = ex;
                }
                if (ex != null && (ex instanceof SQLiteDatabaseCorruptException)) {
                    Log.m94d(SQLiteConnection.TAG, "Corruption detected - isPrimary: " + this.mConnection.isPrimaryConnection() + ", address: @" + Integer.toHexString(System.identityHashCode(this.mConnection)));
                }
            }
        }

        public void endOperation(int cookie) {
            synchronized (this.mOperations) {
                if (endOperationDeferLogLocked(cookie)) {
                    logOperationLocked(cookie, null);
                }
            }
        }

        public boolean endOperationDeferLog(int cookie) {
            boolean endOperationDeferLogLocked;
            synchronized (this.mOperations) {
                endOperationDeferLogLocked = endOperationDeferLogLocked(cookie);
            }
            return endOperationDeferLogLocked;
        }

        public void logOperation(int cookie, String detail) {
            synchronized (this.mOperations) {
                logOperationLocked(cookie, detail);
            }
        }

        public void setResult(long longResult) {
            this.mResultLong = longResult;
        }

        public void setResult(String stringResult) {
            this.mResultString = stringResult;
        }

        private boolean endOperationDeferLogLocked(int cookie) {
            Operation operation = getOperationLocked(cookie);
            if (operation == null) {
                return false;
            }
            if (Trace.isTagEnabled(1048576L)) {
                Trace.asyncTraceEnd(1048576L, operation.getTraceMethodName(), operation.mCookie);
            }
            operation.mEndTime = SystemClock.uptimeMillis();
            operation.mExecutionTime = operation.mEndTime - operation.mStartTime;
            operation.mFinished = true;
            this.mPool.onStatementExecuted(operation.mExecutionTime);
            if (!SQLiteDebug.NoPreloadHolder.DEBUG_LOG_SLOW_QUERIES || !SQLiteDebug.shouldLogSlowQuery(operation.mExecutionTime)) {
                return false;
            }
            return true;
        }

        private void logOperationLocked(int cookie, String detail) {
            Operation operation = getOperationLocked(cookie);
            operation.mResultLong = this.mResultLong;
            operation.mResultString = this.mResultString;
            StringBuilder msg = new StringBuilder();
            operation.describe(msg, true);
            if (detail != null) {
                msg.append(", ").append(detail);
            }
            if (operation.mSql != null) {
                msg.append(", hash=" + Integer.toHexString((operation.mSql + this.mConfiguration.path).hashCode()));
            }
            Log.m94d(SQLiteConnection.TAG, msg.toString());
            if (operation.mSql != null && operation.mException == null) {
                int type = DatabaseUtils.getSqlStatementType(operation.mSql);
                if (type == 2 || type == 1) {
                    if (SQLiteDebug.shouldLogQueryPlan()) {
                        this.mConnection.printQueryPlan(operation.mSql);
                    }
                    if (SQLiteDebug.shouldLogIndexRecommendation()) {
                        try {
                            SQLiteExpertModule expertModule = new SQLiteExpertModule(this.mConnection, operation.mSql, this.mConfiguration.path);
                            expertModule.start();
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }

        private int newOperationCookieLocked(int index) {
            int generation = this.mGeneration;
            this.mGeneration = generation + 1;
            return (generation << 8) | index;
        }

        private Operation getOperationLocked(int cookie) {
            int index = cookie & 255;
            Operation operation = this.mOperations[index];
            if (operation.mCookie == cookie) {
                return operation;
            }
            return null;
        }

        public String describeCurrentOperation() {
            synchronized (this.mOperations) {
                Operation operation = this.mOperations[this.mIndex];
                if (operation == null || operation.mFinished) {
                    return null;
                }
                StringBuilder msg = new StringBuilder();
                operation.describe(msg, false);
                return msg.toString();
            }
        }

        public int endOperationDeferLogOrCollect(int cookie) {
            int endOperationDeferLogOrCollectLocked;
            synchronized (this.mOperations) {
                endOperationDeferLogOrCollectLocked = endOperationDeferLogOrCollectLocked(cookie, -1, -1, -1);
            }
            return endOperationDeferLogOrCollectLocked;
        }

        public int endOperationDeferLogOrCollect(int cookie, int filledRows, int countedRows, int totalRows) {
            int endOperationDeferLogOrCollectLocked;
            synchronized (this.mOperations) {
                endOperationDeferLogOrCollectLocked = endOperationDeferLogOrCollectLocked(cookie, filledRows, countedRows, totalRows);
            }
            return endOperationDeferLogOrCollectLocked;
        }

        private int endOperationDeferLogOrCollectLocked(int cookie, int filledRows, int countedRows, int totalRows) {
            int type;
            Operation operation = getOperationLocked(cookie);
            int ret = 0;
            if (operation == null) {
                return 0;
            }
            if (Trace.isTagEnabled(1048576L)) {
                Trace.asyncTraceEnd(1048576L, operation.getTraceMethodName(), operation.mCookie);
            }
            operation.mEndTime = SystemClock.uptimeMillis();
            operation.mExecutionTime = operation.mEndTime - operation.mStartTime;
            operation.mFilledRows = filledRows;
            operation.mCountedRows = countedRows;
            operation.mTotalRows = totalRows;
            operation.mFinished = true;
            this.mPool.onStatementExecuted(operation.mExecutionTime);
            if (SQLiteUtils.isIssueTrackerOn(this.mPool.getDatabase()) && this.mConnection.isPrimaryConnection() && operation.mSql != null && !this.mConfiguration.isInMemoryDb()) {
                long j = this.mLastCheckTime;
                if ((j < 0 || j + 3600000 < operation.mEndTime) && ((type = DatabaseUtils.getSqlStatementType(operation.mSql)) == 2 || type == 5)) {
                    SQLiteUtils.checkAbnormalDBSize(this.mPool.getDatabase(), this.mConfiguration.path);
                    this.mLastCheckTime = operation.mEndTime;
                }
            }
            if (SQLiteDebug.NoPreloadHolder.DEBUG_LOG_SLOW_QUERIES && SQLiteDebug.shouldLogSlowQuery(operation.mExecutionTime)) {
                ret = 1;
            }
            if (operation.mException == null && operation.mExecutionTime > TelecomManager.VERY_SHORT_CALL_TIME_MS && this.mConfiguration.isQueryCollectDb()) {
                ret |= 2;
            }
            if (SQLiteTrace.isEnabled(this.mConfiguration.path)) {
                operation.mTid = Process.myTid();
                SQLiteTrace.trace(operation, this.mConfiguration.path);
            }
            return ret;
        }

        public void collectOperation(int cookie) {
            collectOperation(cookie, 0, 0);
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Removed duplicated region for block: B:15:0x0053 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:22:? A[RETURN, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void collectOperation(int cookie, int currentRows, int totalRows) {
            int pid;
            SQLiteSlowQueryCollector.SlowQueryParams params = null;
            int pid2 = 0;
            synchronized (this.mOperations) {
                try {
                    try {
                        Operation operation = getOperationLocked(cookie);
                        if (operation != null) {
                            pid = 0;
                            try {
                                params = new SQLiteSlowQueryCollector.SlowQueryParams(this.mConfiguration.label, operation.mStartTime, operation.mExecutionTime, operation.mSql, currentRows, totalRows);
                            } catch (Exception e) {
                                params = null;
                            } catch (Throwable th) {
                                th = th;
                            }
                            try {
                                pid2 = operation.mCallingPid;
                            } catch (Exception e2) {
                                pid2 = pid;
                                if (params == null) {
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                throw th;
                            }
                        }
                    } catch (Throwable th3) {
                        th = th3;
                    }
                } catch (Exception e3) {
                    pid = 0;
                } catch (Throwable th4) {
                    th = th4;
                }
                if (params == null) {
                    try {
                        SQLiteSlowQueryCollector.sendSlowQueryLog(this.mPool, pid2, params);
                    } catch (Exception e4) {
                    }
                }
            }
        }

        public void dump(Printer printer) {
            synchronized (this.mOperations) {
                printer.println("  Most recently executed operations:");
                int index = this.mIndex;
                Operation operation = this.mOperations[index];
                if (operation != null) {
                    SimpleDateFormat opDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                    int n = 0;
                    do {
                        StringBuilder msg = new StringBuilder();
                        msg.append("    ").append(n).append(": [");
                        String formattedStartTime = opDF.format(new Date(operation.mStartWallTime));
                        msg.append(formattedStartTime);
                        msg.append("] ");
                        msg.append("[Pid:(" + operation.mCallingPid + ")]");
                        operation.describe(msg, false);
                        printer.println(msg.toString());
                        if (index > 0) {
                            index--;
                        } else {
                            index = 29;
                        }
                        n++;
                        operation = this.mOperations[index];
                        if (operation == null) {
                            break;
                        }
                    } while (n < 30);
                } else {
                    printer.println("    <none>");
                }
            }
        }
    }

    public static final class Operation {
        private static final int MAX_TRACE_METHOD_NAME_LEN = 256;
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
        public String mPath;
        public long mResultLong;
        public String mResultString;
        public String mSql;
        public long mStartTime;
        public long mStartWallTime;
        public int mTid;
        public int mTotalRows;

        public void describe(StringBuilder msg, boolean allowDetailedLog) {
            ArrayList<Object> arrayList;
            msg.append(this.mKind);
            if (this.mFinished) {
                msg.append(" took ").append(this.mExecutionTime).append("ms");
            } else {
                msg.append(" started ").append(System.currentTimeMillis() - this.mStartWallTime).append("ms ago");
            }
            msg.append(" - ").append(getStatus());
            if (this.mSql != null) {
                msg.append(", sql=\"").append(SQLiteConnection.trimSqlForDisplay(this.mSql)).append("\"");
            }
            boolean dumpDetails = allowDetailedLog && SQLiteDebug.NoPreloadHolder.DEBUG_LOG_DETAILED && (arrayList = this.mBindArgs) != null && arrayList.size() != 0;
            if (dumpDetails) {
                msg.append(", bindArgs=[");
                int count = this.mBindArgs.size();
                for (int i = 0; i < count; i++) {
                    Object arg = this.mBindArgs.get(i);
                    if (i != 0) {
                        msg.append(", ");
                    }
                    if (arg == null) {
                        msg.append(SemCapabilities.FEATURE_TAG_NULL);
                    } else if (arg instanceof byte[]) {
                        msg.append("<byte[]>");
                    } else if (arg instanceof String) {
                        msg.append("\"").append((String) arg).append("\"");
                    } else {
                        msg.append(arg);
                    }
                }
                msg.append(NavigationBarInflaterView.SIZE_MOD_END);
            }
            msg.append(", path=").append(this.mPath);
            if (this.mTotalRows >= 0) {
                msg.append(", filledRows=").append(this.mFilledRows);
                msg.append(", countedRows=").append(this.mCountedRows);
                msg.append(", totalRows=").append(this.mTotalRows);
            }
            if (this.mException != null) {
                msg.append(", exception=\"").append(this.mException.getMessage()).append("\"");
            }
            if (this.mResultLong != Long.MIN_VALUE) {
                msg.append(", result=").append(this.mResultLong);
            }
            if (this.mResultString != null) {
                msg.append(", result=\"").append(this.mResultString).append("\"");
            }
        }

        private String getStatus() {
            if (this.mFinished) {
                return this.mException != null ? "failed" : "succeeded";
            }
            return "running";
        }

        /* JADX INFO: Access modifiers changed from: private */
        public String getTraceMethodName() {
            String methodName = this.mKind + " " + this.mSql;
            if (methodName.length() > 256) {
                return methodName.substring(0, 256);
            }
            return methodName;
        }
    }

    private static final class SQLiteExpertModule extends Thread {
        private static final String TAG = "SQLiteIndexRecommendation";
        private SQLiteConnection mConnection;
        private String mPath;
        private String mSql;

        public SQLiteExpertModule(SQLiteConnection connection, String sql, String path) {
            this.mConnection = connection;
            this.mSql = sql;
            this.mPath = path;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            String result = this.mConnection.analyzeSql(this.mSql);
            if (result != null) {
                String log = "newIndex=\"" + result + "\", sql=\"" + SQLiteConnection.trimSqlForDisplay(this.mSql) + "\", window='" + this.mPath + "', hash=" + Integer.toHexString((this.mSql + this.mPath).hashCode());
                Log.m94d(TAG, log);
            }
        }
    }
}
