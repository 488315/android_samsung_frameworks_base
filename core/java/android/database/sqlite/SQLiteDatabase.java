package android.database.sqlite;

import android.app.ActivityManager;
import android.app.ActivityThread;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.DatabaseUtils;
import android.database.DefaultDatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDebug;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.p009os.CancellationSignal;
import android.p009os.Looper;
import android.p009os.SystemProperties;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.EventLog;
import android.util.Log;
import android.util.Pair;
import android.util.Printer;
import com.android.internal.util.Preconditions;
import dalvik.annotation.optimization.NeverCompile;
import dalvik.system.CloseGuard;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/* loaded from: classes.dex */
public final class SQLiteDatabase extends SQLiteClosable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int CONFLICT_ABORT = 2;
    public static final int CONFLICT_FAIL = 3;
    public static final int CONFLICT_IGNORE = 4;
    public static final int CONFLICT_NONE = 0;
    public static final int CONFLICT_REPLACE = 5;
    public static final int CONFLICT_ROLLBACK = 1;
    public static final int CREATE_IF_NECESSARY = 268435456;
    public static final int ENABLE_LEGACY_COMPATIBILITY_WAL = Integer.MIN_VALUE;
    public static final int ENABLE_ROLLBACK_JOURNAL = 1024;
    public static final int ENABLE_WRITE_AHEAD_LOGGING = 536870912;
    private static final int EVENT_DB_CORRUPT = 75004;
    public static final String JOURNAL_MODE_DELETE = "DELETE";
    public static final String JOURNAL_MODE_MEMORY = "MEMORY";
    public static final String JOURNAL_MODE_OFF = "OFF";
    public static final String JOURNAL_MODE_PERSIST = "PERSIST";
    public static final String JOURNAL_MODE_TRUNCATE = "TRUNCATE";
    public static final String JOURNAL_MODE_WAL = "WAL";
    public static final int MAX_SQL_CACHE_SIZE = 100;
    public static final int NO_LOCALIZED_COLLATORS = 16;
    public static final int OPEN_FULLMUTEX = 256;
    public static final int OPEN_READONLY = 1;
    public static final int OPEN_READWRITE = 0;
    private static final int OPEN_READ_MASK = 1;
    public static final int OPEN_SECURE = 512;
    public static final int SEM_OPEN_SEPARATECACHE = 4096;
    public static final int SQLITE_MAX_LIKE_PATTERN_LENGTH = 50000;
    private static final boolean SUPPORT_KNOX_SDP_SQLITE = false;
    public static final String SYNC_MODE_EXTRA = "EXTRA";
    public static final String SYNC_MODE_FULL = "FULL";
    public static final String SYNC_MODE_NORMAL = "NORMAL";
    public static final String SYNC_MODE_OFF = "OFF";
    private static final String TAG = "SQLiteDatabase";
    private SQLiteWalBackgroundCheckpoint mBackgroundCheckpoint;
    private final SQLiteDatabaseConfiguration mConfigurationLocked;
    private SQLiteConnectionPool mConnectionPoolLocked;
    private Context mContext;
    private final CursorFactory mCursorFactory;
    private SQLiteDump mDbDump;
    private final DatabaseErrorHandler mDefaultErrorHandler;
    private final DatabaseErrorHandler mErrorHandler;
    private boolean mHasAttachedDbsLocked;
    private SQLiteUserDataRecovery udr;
    private static final boolean DEBUG_CLOSE_IDLE_CONNECTIONS = SystemProperties.getBoolean("persist.debug.sqlite.close_idle_connections", false);
    private static WeakHashMap<SQLiteDatabase, Object> sActiveDatabases = new WeakHashMap<>();
    private static HashSet<String> sDbDirectories = new HashSet<>();
    private static final Object mSecureLock = new Object();
    public static final String[] CONFLICT_VALUES = {"", " OR ROLLBACK ", " OR ABORT ", " OR FAIL ", " OR IGNORE ", " OR REPLACE "};
    private final ThreadLocal<SQLiteSession> mThreadSession = ThreadLocal.withInitial(new Supplier() { // from class: android.database.sqlite.SQLiteDatabase$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final Object get() {
            return SQLiteDatabase.this.createSession();
        }
    });
    private boolean mInCorruptionHandling = false;
    private boolean mIsDatabaseCorrupted = false;
    private String mIntegrityErrorString = null;
    private byte[] mPassword = null;
    private final Object mLock = new Object();
    private final CloseGuard mCloseGuardLocked = CloseGuard.get();
    private SQLiteSdpHelper mSdpHelper = null;
    private int mCorruptCode = 0;

    public interface CursorFactory {
        Cursor newCursor(SQLiteDatabase sQLiteDatabase, SQLiteCursorDriver sQLiteCursorDriver, String str, SQLiteQuery sQLiteQuery);
    }

    public interface CustomFunction {
        void callback(String[] strArr);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DatabaseOpenFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface JournalMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SyncMode {
    }

    private SQLiteDatabase(String path, int openFlags, CursorFactory cursorFactory, DatabaseErrorHandler errorHandler, int lookasideSlotSize, int lookasideSlotCount, long idleConnectionTimeoutMs, long idleConnectionShrinkTimeoutMs, String journalMode, String syncMode, int cacheSize, boolean userDataRecovery) {
        this.udr = null;
        this.mDbDump = SQLiteDump.DUMMY_DB_DUMP;
        this.mCursorFactory = cursorFactory;
        this.mErrorHandler = errorHandler;
        SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration = new SQLiteDatabaseConfiguration(path, openFlags);
        this.mConfigurationLocked = sQLiteDatabaseConfiguration;
        sQLiteDatabaseConfiguration.lookasideSlotSize = lookasideSlotSize;
        sQLiteDatabaseConfiguration.lookasideSlotCount = lookasideSlotCount;
        if (!sQLiteDatabaseConfiguration.isInMemoryDb() && sQLiteDatabaseConfiguration.sharedConfig.useDumpCorruptByDefault) {
            this.mDbDump = new SQLiteDump(path);
        }
        DefaultDatabaseErrorHandler defaultErrorHandler = new DefaultDatabaseErrorHandler(this.mDbDump);
        defaultErrorHandler.setDeleteDatabaseIfCorrupted(errorHandler == null);
        this.mDefaultErrorHandler = defaultErrorHandler;
        if (ActivityManager.isLowRamDeviceStatic()) {
            sQLiteDatabaseConfiguration.lookasideSlotCount = 0;
            sQLiteDatabaseConfiguration.lookasideSlotSize = 0;
        }
        long effectiveTimeoutMs = Long.MAX_VALUE;
        if (!sQLiteDatabaseConfiguration.isInMemoryDb()) {
            if (idleConnectionTimeoutMs >= 0) {
                effectiveTimeoutMs = idleConnectionTimeoutMs;
            } else if (DEBUG_CLOSE_IDLE_CONNECTIONS) {
                effectiveTimeoutMs = SQLiteGlobal.getIdleConnectionTimeout();
            }
        }
        if (idleConnectionShrinkTimeoutMs >= 0) {
            sQLiteDatabaseConfiguration.idleConnectionShrinkTimeoutMs = idleConnectionShrinkTimeoutMs;
        }
        sQLiteDatabaseConfiguration.idleConnectionTimeoutMs = effectiveTimeoutMs;
        if (SQLiteCompatibilityWalFlags.isLegacyCompatibilityWalEnabled()) {
            sQLiteDatabaseConfiguration.openFlags |= Integer.MIN_VALUE;
        }
        sQLiteDatabaseConfiguration.journalMode = journalMode;
        sQLiteDatabaseConfiguration.syncMode = syncMode;
        sQLiteDatabaseConfiguration.cacheSize = cacheSize;
        if (sQLiteDatabaseConfiguration.resolveJournalMode().equalsIgnoreCase(JOURNAL_MODE_WAL)) {
            this.mBackgroundCheckpoint = new SQLiteWalBackgroundCheckpoint();
        }
        if (userDataRecovery) {
            this.udr = new SQLiteUserDataRecovery(this.mDbDump);
            sQLiteDatabaseConfiguration.sharedConfig.setUserDataRecovery(true);
        }
    }

    protected void finalize() throws Throwable {
        try {
            dispose(true);
        } finally {
            super.finalize();
        }
    }

    @Override // android.database.sqlite.SQLiteClosable
    protected void onAllReferencesReleased() {
        dispose(false);
    }

    private void dispose(boolean finalized) {
        SQLiteConnectionPool pool;
        synchronized (this.mLock) {
            CloseGuard closeGuard = this.mCloseGuardLocked;
            if (closeGuard != null) {
                if (finalized) {
                    closeGuard.warnIfOpen();
                }
                this.mCloseGuardLocked.close();
            }
            pool = this.mConnectionPoolLocked;
            this.mConnectionPoolLocked = null;
        }
        if (!finalized) {
            synchronized (sActiveDatabases) {
                sActiveDatabases.remove(this);
            }
            if (pool != null) {
                pool.close();
            }
        }
    }

    private void releaseConnectionMemory() {
        SQLiteConnectionPool sQLiteConnectionPool;
        synchronized (this.mLock) {
            if ((this.mConfigurationLocked.openFlags & 4096) != 0 && (sQLiteConnectionPool = this.mConnectionPoolLocked) != null) {
                sQLiteConnectionPool.releaseConnectionMemory();
            }
        }
    }

    public static int releaseMemory() {
        Iterator<SQLiteDatabase> it = getActiveDatabases().iterator();
        while (it.hasNext()) {
            SQLiteDatabase db = it.next();
            db.releaseConnectionMemory();
        }
        return SQLiteGlobal.releaseMemory();
    }

    @Deprecated
    public void setLockingEnabled(boolean lockingEnabled) {
    }

    String getLabel() {
        String str;
        synchronized (this.mLock) {
            str = this.mConfigurationLocked.label;
        }
        return str;
    }

    void onCorruption() {
        int poolSize = 0;
        synchronized (this.mLock) {
            if (this.mInCorruptionHandling) {
                Log.m94d(TAG, "Database corruption is already handling.");
                return;
            }
            this.mInCorruptionHandling = true;
            this.mDbDump.prepareDumpFile();
            SQLiteDump sQLiteDump = this.mDbDump;
            sQLiteDump.addDumpLog(TAG, sQLiteDump.getSQLiteDumpLogs(true));
            if (isOpen()) {
                this.mConnectionPoolLocked.dumpAllConnections(this.mDbDump);
                poolSize = getMaxConnectionPoolSize();
                closeAndDiscardNonPrimaryConnections(true, false);
            }
            try {
                EventLog.writeEvent(EVENT_DB_CORRUPT, getLabel());
                this.mDefaultErrorHandler.onCorruption(this);
                DatabaseErrorHandler databaseErrorHandler = this.mErrorHandler;
                if (databaseErrorHandler != null) {
                    databaseErrorHandler.onCorruption(this);
                }
                synchronized (this.mLock) {
                    if (poolSize > 0) {
                        if (isOpen()) {
                            this.mConnectionPoolLocked.setMaxConnectionPoolSize(poolSize);
                        }
                    }
                    SQLiteDump sQLiteDump2 = this.mDbDump;
                    sQLiteDump2.addDumpLog(TAG, sQLiteDump2.getSQLiteDumpLogs(true));
                    this.mDbDump.finishDump();
                    this.mInCorruptionHandling = false;
                    this.mIsDatabaseCorrupted = false;
                }
            } catch (Throwable th) {
                synchronized (this.mLock) {
                    if (poolSize > 0) {
                        if (isOpen()) {
                            this.mConnectionPoolLocked.setMaxConnectionPoolSize(poolSize);
                        }
                    }
                    SQLiteDump sQLiteDump3 = this.mDbDump;
                    sQLiteDump3.addDumpLog(TAG, sQLiteDump3.getSQLiteDumpLogs(true));
                    this.mDbDump.finishDump();
                    this.mInCorruptionHandling = false;
                    this.mIsDatabaseCorrupted = false;
                    throw th;
                }
            }
        }
    }

    public void setDatabaseIsCorrupted(boolean result) {
        synchronized (this.mLock) {
            this.mIsDatabaseCorrupted = result;
        }
    }

    public boolean semIsDatabaseCorrupted() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mIsDatabaseCorrupted;
        }
        return z;
    }

    @Deprecated
    public static boolean semDeleteAllDatabases(SQLiteDatabase dbObj) {
        if (dbObj == null) {
            throw new IllegalArgumentException("SQLiteDatabase should not be null.");
        }
        try {
            List<Pair<String, String>> attachedDbs = dbObj.getAttachedDbs();
            String path = dbObj.getPath();
            if (dbObj.isOpen()) {
                dbObj.close();
            }
            if (attachedDbs == null) {
                Log.m96e(TAG, "!@ Failed to get attachedDbs, just delete " + path);
                deleteDatabase(new File(path));
                return true;
            }
            for (Pair<String, String> p : attachedDbs) {
                Log.m96e(TAG, "!@ Delete DB File : " + p.second);
                deleteDatabase(new File(p.second));
            }
            return true;
        } catch (Exception e) {
            Log.m96e(TAG, "!@ semDeleteAllDatabase - Exception during deleting");
            return false;
        }
    }

    SQLiteSession getThreadSession() {
        return this.mThreadSession.get();
    }

    SQLiteSession createSession() {
        SQLiteConnectionPool pool;
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            pool = this.mConnectionPoolLocked;
        }
        return new SQLiteSession(pool);
    }

    int getThreadDefaultConnectionFlags(boolean readOnly) {
        int flags = readOnly ? 1 : 2;
        if (isMainThread()) {
            return flags | 4;
        }
        return flags;
    }

    private static boolean isMainThread() {
        Looper looper = Looper.myLooper();
        return looper != null && looper == Looper.getMainLooper();
    }

    public void beginTransaction() {
        beginTransaction(null, true);
    }

    public void beginTransactionNonExclusive() {
        beginTransaction(null, false);
    }

    public void beginTransactionWithListener(SQLiteTransactionListener transactionListener) {
        beginTransaction(transactionListener, true);
    }

    public void beginTransactionWithListenerNonExclusive(SQLiteTransactionListener transactionListener) {
        beginTransaction(transactionListener, false);
    }

    private void beginTransaction(SQLiteTransactionListener transactionListener, boolean exclusive) {
        acquireReference();
        try {
            try {
                getThreadSession().beginTransaction(exclusive ? 2 : 1, transactionListener, getThreadDefaultConnectionFlags(false), null);
            } catch (SQLiteDatabaseCorruptException ex) {
                onCorruption(ex.getCorruptCode());
                throw ex;
            }
        } finally {
            releaseReference();
        }
    }

    public void endTransaction() {
        acquireReference();
        try {
            try {
                getThreadSession().endTransaction(null);
            } catch (SQLiteDatabaseCorruptException ex) {
                onCorruption(ex.getCorruptCode());
                throw ex;
            }
        } finally {
            releaseReference();
        }
    }

    public void setTransactionSuccessful() {
        acquireReference();
        try {
            getThreadSession().setTransactionSuccessful();
        } finally {
            releaseReference();
        }
    }

    public boolean inTransaction() {
        acquireReference();
        try {
            return getThreadSession().hasTransaction();
        } finally {
            releaseReference();
        }
    }

    public boolean isDbLockedByCurrentThread() {
        acquireReference();
        try {
            return getThreadSession().hasConnection();
        } finally {
            releaseReference();
        }
    }

    @Deprecated
    public boolean isDbLockedByOtherThreads() {
        return false;
    }

    @Deprecated
    public boolean yieldIfContended() {
        return yieldIfContendedHelper(false, -1L);
    }

    public boolean yieldIfContendedSafely() {
        return yieldIfContendedHelper(true, -1L);
    }

    public boolean yieldIfContendedSafely(long sleepAfterYieldDelay) {
        return yieldIfContendedHelper(true, sleepAfterYieldDelay);
    }

    private boolean yieldIfContendedHelper(boolean throwIfUnsafe, long sleepAfterYieldDelay) {
        acquireReference();
        try {
            try {
                return getThreadSession().yieldTransaction(sleepAfterYieldDelay, throwIfUnsafe, null);
            } catch (SQLiteDatabaseCorruptException ex) {
                onCorruption(ex.getCorruptCode());
                throw ex;
            }
        } finally {
            releaseReference();
        }
    }

    @Deprecated
    public Map<String, String> getSyncedTables() {
        return new HashMap(0);
    }

    public static SQLiteDatabase openDatabase(String path, CursorFactory factory, int flags) {
        return openDatabase(path, factory, flags, null);
    }

    public static SQLiteDatabase openDatabase(File path, OpenParams openParams) {
        return openDatabase(path.getPath(), openParams, (Context) null);
    }

    private static SQLiteDatabase openDatabase(String path, OpenParams openParams) {
        return openDatabase(path, openParams, (Context) null);
    }

    public static SQLiteDatabase openDatabase(String path, OpenParams openParams, Context context) {
        Preconditions.checkArgument(openParams != null, "OpenParams cannot be null");
        SQLiteDatabase db = new SQLiteDatabase(path, openParams.mOpenFlags, openParams.mCursorFactory, openParams.mErrorHandler, openParams.mLookasideSlotSize, openParams.mLookasideSlotCount, openParams.mIdleConnectionTimeout, openParams.mIdleConnectionShrinkTimeout, openParams.mJournalMode, openParams.mSyncMode, openParams.mCacheSize, openParams.mUserDataRecovery);
        db.setContext(context);
        db.open();
        return db;
    }

    public static SQLiteDatabase openDatabase(String path, CursorFactory factory, int flags, DatabaseErrorHandler errorHandler) {
        SQLiteDatabase db = new SQLiteDatabase(path, flags, factory, errorHandler, -1, -1, -1L, -1L, null, null, 0, false);
        db.open();
        return db;
    }

    public static SQLiteDatabase openOrCreateDatabase(File file, CursorFactory factory) {
        return openOrCreateDatabase(file.getPath(), factory);
    }

    public static SQLiteDatabase openOrCreateDatabase(String path, CursorFactory factory) {
        return openDatabase(path, factory, 268435456, null);
    }

    public static SQLiteDatabase openOrCreateDatabase(String path, CursorFactory factory, DatabaseErrorHandler errorHandler) {
        return openDatabase(path, factory, 268435456, errorHandler);
    }

    public static SQLiteDatabase openSecureDatabase(String path, OpenParams openParams, byte[] password, Context context) {
        Preconditions.checkArgument(openParams != null, "OpenParams cannot be null");
        if (password == null) {
            Log.m96e(TAG, "Could not open a secure database with null password.");
            throw new IllegalArgumentException("Could not open a secure database with null password.");
        }
        if (password.length == 0) {
            Log.m96e(TAG, "Could not open a secure database with empty password.");
            throw new IllegalArgumentException("Could not open a secure database with empty password.");
        }
        SQLiteDatabase db = new SQLiteDatabase(path, openParams.mOpenFlags | 256 | 512, openParams.mCursorFactory, openParams.mErrorHandler, openParams.mLookasideSlotSize, openParams.mLookasideSlotCount, openParams.mIdleConnectionTimeout, openParams.mIdleConnectionShrinkTimeout, openParams.mJournalMode, openParams.mSyncMode, openParams.mCacheSize, openParams.mUserDataRecovery);
        db.setContext(context);
        db.openSecureDatabase(password);
        return db;
    }

    public static SQLiteDatabase openSecureDatabase(File file, OpenParams openParams, byte[] password, Context context) {
        return openSecureDatabase(file.getPath(), openParams, password, context);
    }

    public static SQLiteDatabase openSecureDatabase(String path, CursorFactory factory, int flags, DatabaseErrorHandler errorHandler, byte[] password) {
        if (password == null) {
            Log.m96e(TAG, "Could not open a secure database with null password.");
            throw new IllegalArgumentException("Could not open a secure database with null password.");
        }
        if (password.length == 0) {
            Log.m96e(TAG, "Could not open a secure database with empty password.");
            throw new IllegalArgumentException("Could not open a secure database with empty password.");
        }
        SQLiteDatabase db = new SQLiteDatabase(path, flags | 768, factory, errorHandler, -1, -1, -1L, -1L, null, null, 0, false);
        db.openSecureDatabase(password);
        return db;
    }

    private static String convertByte2HexString(byte[] password) {
        StringBuilder sb = new StringBuilder();
        if (password != null) {
            for (int i = 0; i < password.length; i++) {
                if (password[i] >= 0 && password[i] < 16) {
                    sb.append("0");
                }
                sb.append(Integer.toHexString(password[i] & 255));
            }
        }
        return sb.toString();
    }

    public static void convertToPlainDatabase(File sourceDbFile, File destDbFile, byte[] password) throws Exception {
        if (sourceDbFile == null || destDbFile == null || password == null) {
            Log.m96e(TAG, "convertToPlainDatabase() parameters should not be null.");
            throw new IllegalStateException("parameters should not be null.");
        }
        synchronized (mSecureLock) {
            Log.m94d(TAG, "convertToPlainDatabase() start...");
            if (destDbFile.exists() && destDbFile.length() > 0) {
                Log.m96e(TAG, "convertToPlainDatabase() Destination file exists, so delete it");
                deleteDatabase(destDbFile);
            }
            SQLiteDatabase destDb = null;
            SQLiteDatabase sourceDb = openSecureDatabase(sourceDbFile.getAbsolutePath(), null, 16, null, password);
            try {
                int sourceDbVer = sourceDb.getVersion();
                try {
                    try {
                        destDb = openDatabase(destDbFile.getAbsolutePath(), null, 268435472, null);
                        destDb.execSQL(String.format("attach database '%s' as secureDb key x'%s'", sourceDbFile.getAbsolutePath(), convertByte2HexString(password)));
                        destDb.exportDB("secureDb");
                        destDb.execSQL("detach database secureDb");
                        int destDbVer = destDb.getVersion();
                        if (destDbVer != sourceDbVer) {
                            Log.m102w(TAG, "Note: sourceDb version was changed during conversion (" + sourceDbVer + " -> " + destDbVer + NavigationBarInflaterView.KEY_CODE_END);
                        }
                        Log.m94d(TAG, "convertToPlainDatabase() finished");
                    } finally {
                        if (destDb != null && destDb.isOpen()) {
                            destDb.close();
                        }
                    }
                } catch (SQLiteException e) {
                    deleteDatabase(destDbFile);
                    Log.m97e(TAG, "convertToPlainDatabase() failed", e);
                    throw e;
                }
            } finally {
                sourceDb.close();
            }
        }
    }

    public static void convertToSecureDatabase(File sourceDbFile, File destDbFile, byte[] password) throws Exception {
        if (sourceDbFile == null || destDbFile == null || password == null) {
            Log.m96e(TAG, "convertToSecureDatabase() parameters should not be null.");
            throw new IllegalStateException("parameters should not be null.");
        }
        synchronized (mSecureLock) {
            Log.m94d(TAG, "convertToSecureDatabase() start...");
            if (destDbFile.exists() && destDbFile.length() > 0) {
                Log.m96e(TAG, "convertToSecureDatabase() Destination file exists, so delete it");
                deleteDatabase(destDbFile);
            }
            SQLiteDatabase destDb = null;
            SQLiteDatabase sourceDb = openDatabase(sourceDbFile.getAbsolutePath(), null, 16, null);
            try {
                int sourceDbVer = sourceDb.getVersion();
                try {
                    try {
                        destDb = openSecureDatabase(destDbFile.getAbsolutePath(), null, 268435472, null, password);
                        destDb.execSQL(String.format("attach database '%s' as plainDb key ''", sourceDbFile.getAbsolutePath()));
                        destDb.exportDB("plainDb");
                        destDb.execSQL("detach database plainDb");
                        int destDbVer = destDb.getVersion();
                        if (destDbVer != sourceDbVer) {
                            Log.m102w(TAG, "Note: sourceDb version was changed during conversion (" + sourceDbVer + " -> " + destDbVer + NavigationBarInflaterView.KEY_CODE_END);
                        }
                    } finally {
                        if (destDb != null && destDb.isOpen()) {
                            destDb.close();
                        }
                    }
                } catch (SQLiteException e) {
                    deleteDatabase(destDbFile);
                    Log.m97e(TAG, "convertToSecureDatabase() failed", e);
                    throw e;
                }
            } finally {
                sourceDb.close();
            }
        }
        Log.m94d(TAG, "convertToSecureDatabase() finished");
    }

    public int changeDBPassword(byte[] newPassword) {
        if (!isOpen()) {
            Log.m96e(TAG, "changeDBPassword() DB is not open");
            throw new IllegalStateException("DB is not open");
        }
        if (newPassword == null) {
            Log.m96e(TAG, "Could not use null password to a secure database.");
            throw new IllegalArgumentException("Could not use null password to a secure database.");
        }
        acquireReference();
        try {
            try {
                synchronized (mSecureLock) {
                    Log.m94d(TAG, "changeDBPassword() start...");
                    getThreadSession().changePassword(newPassword);
                    Log.m94d(TAG, "changeDBPassword() finished");
                }
                releaseReference();
                return 0;
            } catch (SQLiteDatabaseCorruptException ex) {
                onCorruption(ex.getCorruptCode());
                throw ex;
            }
        } catch (Throwable th) {
            releaseReference();
            throw th;
        }
    }

    public static boolean deleteDatabase(File file) {
        return deleteDatabase(file, true);
    }

    public static boolean deleteDatabase(File file, boolean removeCheckFile) {
        if (file == null) {
            throw new IllegalArgumentException("file must not be null");
        }
        boolean deleted = false | file.delete() | new File(file.getPath() + "-journal").delete() | new File(file.getPath() + "-shm").delete() | new File(file.getPath() + "-wal").delete() | new File(file.getPath() + "-se").delete() | new File(file.getPath() + "-udr").delete();
        new File(file.getPath() + "-wipecheck").delete();
        File dir = file.getParentFile();
        if (dir != null) {
            final String prefix = file.getName() + "-mj";
            File[] files = dir.listFiles(new FileFilter() { // from class: android.database.sqlite.SQLiteDatabase.1
                @Override // java.io.FileFilter
                public boolean accept(File candidate) {
                    return candidate.getName().startsWith(prefix);
                }
            });
            if (files != null) {
                for (File masterJournal : files) {
                    deleted |= masterJournal.delete();
                }
            }
        }
        return deleted;
    }

    public void reopenReadWrite() {
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            if (isReadOnlyLocked()) {
                int oldOpenFlags = this.mConfigurationLocked.openFlags;
                SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration = this.mConfigurationLocked;
                sQLiteDatabaseConfiguration.openFlags = (sQLiteDatabaseConfiguration.openFlags & (-2)) | 0;
                try {
                    this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
                } catch (RuntimeException ex) {
                    this.mConfigurationLocked.openFlags = oldOpenFlags;
                    throw ex;
                }
            }
        }
    }

    private void open() {
        try {
            try {
                openInner();
            } catch (RuntimeException ex) {
                if (!SQLiteDatabaseCorruptException.isCorruptException(ex)) {
                    throw ex;
                }
                Log.m97e(TAG, "Database corruption detected in open()", ex);
                onCorruption(((SQLiteDatabaseCorruptException) ex).getCorruptCode());
                openInner();
            }
        } catch (SQLiteException ex2) {
            Log.m97e(TAG, "Failed to open database '" + getLabel() + "'.", ex2);
            close();
            throw ex2;
        }
    }

    private void openSecureDatabase(byte[] password) {
        try {
            try {
                try {
                    this.mPassword = password;
                    openInnerSecureDatabase(password);
                } catch (SQLiteException ex) {
                    Log.m97e(TAG, "Failed to open database '" + getLabel() + "'.", ex);
                    close();
                    throw ex;
                }
            } catch (RuntimeException ex2) {
                if (!SQLiteDatabaseCorruptException.isCorruptException(ex2)) {
                    throw ex2;
                }
                Log.m97e(TAG, "Database corruption detected in openSecureDatabase()", ex2);
                onCorruption(((SQLiteDatabaseCorruptException) ex2).getCorruptCode());
                openInnerSecureDatabase(password);
            }
        } finally {
            this.mPassword = null;
        }
    }

    public void setSdpDatabase() {
    }

    private void openInner() {
        synchronized (sDbDirectories) {
            sDbDirectories.add(new File(this.mConfigurationLocked.path).getParent());
        }
        synchronized (this.mLock) {
            this.mConnectionPoolLocked = SQLiteConnectionPool.open(this, this.mConfigurationLocked);
            this.mCloseGuardLocked.open("close");
        }
        synchronized (sActiveDatabases) {
            sActiveDatabases.put(this, null);
        }
    }

    private void openInnerSecureDatabase(byte[] password) {
        synchronized (sDbDirectories) {
            sDbDirectories.add(new File(this.mConfigurationLocked.path).getParent());
        }
        synchronized (this.mLock) {
            this.mConnectionPoolLocked = SQLiteConnectionPool.openSecure(this, this.mConfigurationLocked, password);
            this.mCloseGuardLocked.open("close");
        }
        synchronized (sActiveDatabases) {
            sActiveDatabases.put(this, null);
        }
    }

    private void exportDB(String attachedDB) {
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            this.mConnectionPoolLocked.exportDB(attachedDB);
        }
    }

    public static SQLiteDatabase create(CursorFactory factory) {
        return openDatabase(SQLiteDatabaseConfiguration.MEMORY_DB_PATH, factory, 268435456);
    }

    public static SQLiteDatabase createInMemory(OpenParams openParams) {
        return openDatabase(SQLiteDatabaseConfiguration.MEMORY_DB_PATH, openParams.toBuilder().addOpenFlags(268435456).build());
    }

    public static SQLiteDatabase createSecureDatabase(CursorFactory factory, byte[] password) {
        return openSecureDatabase(SQLiteDatabaseConfiguration.MEMORY_DB_PATH, factory, 268435456, null, password);
    }

    public void setCustomScalarFunction(String functionName, UnaryOperator<String> scalarFunction) throws SQLiteException {
        Objects.requireNonNull(functionName);
        Objects.requireNonNull(scalarFunction);
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            this.mConfigurationLocked.customScalarFunctions.put(functionName, scalarFunction);
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
            } catch (RuntimeException ex) {
                this.mConfigurationLocked.customScalarFunctions.remove(functionName);
                throw ex;
            }
        }
    }

    public void setCustomAggregateFunction(String functionName, BinaryOperator<String> aggregateFunction) throws SQLiteException {
        Objects.requireNonNull(functionName);
        Objects.requireNonNull(aggregateFunction);
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            this.mConfigurationLocked.customAggregateFunctions.put(functionName, aggregateFunction);
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
            } catch (RuntimeException ex) {
                this.mConfigurationLocked.customAggregateFunctions.remove(functionName);
                throw ex;
            }
        }
    }

    public void execPerConnectionSQL(String sql, Object[] bindArgs) throws SQLException {
        Objects.requireNonNull(sql);
        Object[] bindArgs2 = DatabaseUtils.deepCopyOf(bindArgs);
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            int index = this.mConfigurationLocked.perConnectionSql.size();
            this.mConfigurationLocked.perConnectionSql.add(Pair.create(sql, bindArgs2));
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
            } catch (RuntimeException ex) {
                this.mConfigurationLocked.perConnectionSql.remove(index);
                throw ex;
            }
        }
    }

    public int getVersion() {
        return Long.valueOf(DatabaseUtils.longForQuery(this, "PRAGMA user_version;", null)).intValue();
    }

    public void setVersion(int version) {
        execSQL("PRAGMA user_version = " + version);
    }

    public long getMaximumSize() {
        long pageCount = DatabaseUtils.longForQuery(this, "PRAGMA max_page_count;", null);
        return getPageSize() * pageCount;
    }

    public long setMaximumSize(long numBytes) {
        long pageSize = getPageSize();
        long numPages = numBytes / pageSize;
        if (numBytes % pageSize != 0) {
            numPages++;
        }
        long newPageCount = DatabaseUtils.longForQuery(this, "PRAGMA max_page_count = " + numPages, null);
        return newPageCount * pageSize;
    }

    public long getPageSize() {
        return DatabaseUtils.longForQuery(this, "PRAGMA page_size;", null);
    }

    public void setPageSize(long numBytes) {
        execSQL("PRAGMA page_size = " + numBytes);
    }

    @Deprecated
    public void markTableSyncable(String table, String deletedTable) {
    }

    @Deprecated
    public void markTableSyncable(String table, String foreignKey, String updateTable) {
    }

    public static String findEditTable(String tables) {
        if (!TextUtils.isEmpty(tables)) {
            int spacepos = tables.indexOf(32);
            int commapos = tables.indexOf(44);
            if (spacepos > 0 && (spacepos < commapos || commapos < 0)) {
                return tables.substring(0, spacepos);
            }
            if (commapos > 0 && (commapos < spacepos || spacepos < 0)) {
                return tables.substring(0, commapos);
            }
            return tables;
        }
        throw new IllegalStateException("Invalid tables");
    }

    public SQLiteStatement compileStatement(String sql) throws SQLException {
        acquireReference();
        try {
            return new SQLiteStatement(this, sql, null);
        } finally {
            releaseReference();
        }
    }

    public Cursor query(boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return queryWithFactory(null, distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit, null);
    }

    public Cursor query(boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit, CancellationSignal cancellationSignal) {
        return queryWithFactory(null, distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit, cancellationSignal);
    }

    public Cursor queryWithFactory(CursorFactory cursorFactory, boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return queryWithFactory(cursorFactory, distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit, null);
    }

    public Cursor queryWithFactory(CursorFactory cursorFactory, boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit, CancellationSignal cancellationSignal) {
        acquireReference();
        try {
            String sql = SQLiteQueryBuilder.buildQueryString(distinct, table, columns, selection, groupBy, having, orderBy, limit);
            return rawQueryWithFactory(cursorFactory, sql, selectionArgs, findEditTable(table), cancellationSignal);
        } finally {
            releaseReference();
        }
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return query(false, table, columns, selection, selectionArgs, groupBy, having, orderBy, null);
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return query(false, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
    }

    public Cursor rawQuery(String sql, String[] selectionArgs) {
        return rawQueryWithFactory(null, sql, selectionArgs, null, null);
    }

    public Cursor rawQuery(String sql, String[] selectionArgs, CancellationSignal cancellationSignal) {
        return rawQueryWithFactory(null, sql, selectionArgs, null, cancellationSignal);
    }

    public Cursor rawQueryWithFactory(CursorFactory cursorFactory, String sql, String[] selectionArgs, String editTable) {
        return rawQueryWithFactory(cursorFactory, sql, selectionArgs, editTable, null);
    }

    public Cursor rawQueryWithFactory(CursorFactory cursorFactory, String sql, String[] selectionArgs, String editTable, CancellationSignal cancellationSignal) {
        acquireReference();
        try {
            SQLiteCursorDriver driver = new SQLiteDirectCursorDriver(this, sql, editTable, cancellationSignal);
            return driver.query(cursorFactory != null ? cursorFactory : this.mCursorFactory, selectionArgs);
        } finally {
            releaseReference();
        }
    }

    public long insert(String table, String nullColumnHack, ContentValues values) {
        try {
            return insertWithOnConflict(table, nullColumnHack, values, 0);
        } catch (SQLException e) {
            Log.m97e(TAG, "Error inserting " + values, e);
            return -1L;
        }
    }

    public long insertOrThrow(String table, String nullColumnHack, ContentValues values) throws SQLException {
        return insertWithOnConflict(table, nullColumnHack, values, 0);
    }

    public long replace(String table, String nullColumnHack, ContentValues initialValues) {
        try {
            return insertWithOnConflict(table, nullColumnHack, initialValues, 5);
        } catch (SQLException e) {
            Log.m97e(TAG, "Error inserting " + initialValues, e);
            return -1L;
        }
    }

    public long replaceOrThrow(String table, String nullColumnHack, ContentValues initialValues) throws SQLException {
        return insertWithOnConflict(table, nullColumnHack, initialValues, 5);
    }

    public long insertWithOnConflict(String table, String nullColumnHack, ContentValues initialValues, int conflictAlgorithm) {
        acquireReference();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT");
            sql.append(CONFLICT_VALUES[conflictAlgorithm]);
            sql.append(" INTO ");
            sql.append(table);
            sql.append('(');
            Object[] bindArgs = null;
            int size = (initialValues == null || initialValues.isEmpty()) ? 0 : initialValues.size();
            if (size > 0) {
                bindArgs = new Object[size];
                int i = 0;
                for (String colName : initialValues.keySet()) {
                    sql.append(i > 0 ? "," : "");
                    sql.append(colName);
                    bindArgs[i] = initialValues.get(colName);
                    i++;
                }
                sql.append(')');
                sql.append(" VALUES (");
                int i2 = 0;
                while (i2 < size) {
                    sql.append(i2 > 0 ? ",?" : "?");
                    i2++;
                }
            } else {
                sql.append(nullColumnHack).append(") VALUES (NULL");
            }
            sql.append(')');
            SQLiteStatement statement = new SQLiteStatement(this, sql.toString(), bindArgs);
            try {
                return statement.executeInsert();
            } finally {
                statement.close();
            }
        } finally {
            releaseReference();
        }
    }

    public int delete(String table, String whereClause, String[] whereArgs) {
        acquireReference();
        try {
            SQLiteStatement statement = new SQLiteStatement(this, "DELETE FROM " + table + (!TextUtils.isEmpty(whereClause) ? " WHERE " + whereClause : ""), whereArgs);
            try {
                return statement.executeUpdateDelete();
            } finally {
                statement.close();
            }
        } finally {
            releaseReference();
        }
    }

    public int update(String table, ContentValues values, String whereClause, String[] whereArgs) {
        return updateWithOnConflict(table, values, whereClause, whereArgs, 0);
    }

    public int updateWithOnConflict(String table, ContentValues values, String whereClause, String[] whereArgs, int conflictAlgorithm) {
        if (values == null || values.isEmpty()) {
            throw new IllegalArgumentException("Empty values");
        }
        acquireReference();
        try {
            StringBuilder sql = new StringBuilder(120);
            sql.append("UPDATE ");
            sql.append(CONFLICT_VALUES[conflictAlgorithm]);
            sql.append(table);
            sql.append(" SET ");
            int setValuesSize = values.size();
            int bindArgsSize = whereArgs == null ? setValuesSize : whereArgs.length + setValuesSize;
            Object[] bindArgs = new Object[bindArgsSize];
            int i = 0;
            for (String colName : values.keySet()) {
                sql.append(i > 0 ? "," : "");
                sql.append(colName);
                bindArgs[i] = values.get(colName);
                sql.append("=?");
                i++;
            }
            if (whereArgs != null) {
                for (int i2 = setValuesSize; i2 < bindArgsSize; i2++) {
                    bindArgs[i2] = whereArgs[i2 - setValuesSize];
                }
            }
            if (!TextUtils.isEmpty(whereClause)) {
                sql.append(" WHERE ");
                sql.append(whereClause);
            }
            SQLiteStatement statement = new SQLiteStatement(this, sql.toString(), bindArgs);
            try {
                return statement.executeUpdateDelete();
            } finally {
                statement.close();
            }
        } finally {
            releaseReference();
        }
    }

    public void execSQL(String sql) throws SQLException {
        executeSql(sql, null);
    }

    public void execSQL(String sql, Object[] bindArgs) throws SQLException {
        if (bindArgs == null) {
            throw new IllegalArgumentException("Empty bindArgs");
        }
        executeSql(sql, bindArgs);
    }

    public int executeSql(String sql, Object[] bindArgs) throws SQLException {
        acquireReference();
        try {
            int statementType = DatabaseUtils.getSqlStatementType(sql);
            if (statementType == 3) {
                boolean disableWal = false;
                synchronized (this.mLock) {
                    if (!this.mHasAttachedDbsLocked) {
                        this.mHasAttachedDbsLocked = true;
                        disableWal = true;
                        this.mConnectionPoolLocked.disableIdleConnectionHandler();
                    }
                }
                if (disableWal) {
                    disableWriteAheadLogging();
                }
            }
            try {
                SQLiteStatement statement = new SQLiteStatement(this, sql, bindArgs);
                try {
                    int executeUpdateDelete = statement.executeUpdateDelete();
                    statement.close();
                    return executeUpdateDelete;
                } finally {
                }
            } finally {
                if (statementType == 8) {
                    this.mConnectionPoolLocked.closeAvailableNonPrimaryConnectionsAndLogExceptions();
                }
            }
        } finally {
            releaseReference();
        }
    }

    public void validateSql(String sql, CancellationSignal cancellationSignal) {
        getThreadSession().prepare(sql, getThreadDefaultConnectionFlags(true), cancellationSignal, null);
    }

    public boolean isReadOnly() {
        boolean isReadOnlyLocked;
        synchronized (this.mLock) {
            isReadOnlyLocked = isReadOnlyLocked();
        }
        return isReadOnlyLocked;
    }

    private boolean isReadOnlyLocked() {
        return (this.mConfigurationLocked.openFlags & 1) == 1;
    }

    public boolean isInMemoryDatabase() {
        boolean isInMemoryDb;
        synchronized (this.mLock) {
            isInMemoryDb = this.mConfigurationLocked.isInMemoryDb();
        }
        return isInMemoryDb;
    }

    public boolean isOpen() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mConnectionPoolLocked != null;
        }
        return z;
    }

    public boolean needUpgrade(int newVersion) {
        return newVersion > getVersion();
    }

    public final String getPath() {
        String str;
        synchronized (this.mLock) {
            str = this.mConfigurationLocked.path;
        }
        return str;
    }

    public void setLocale(Locale locale) {
        if (locale == null) {
            throw new IllegalArgumentException("locale must not be null.");
        }
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            Locale oldLocale = this.mConfigurationLocked.locale;
            this.mConfigurationLocked.locale = locale;
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
            } catch (RuntimeException ex) {
                this.mConfigurationLocked.locale = oldLocale;
                throw ex;
            }
        }
    }

    public void setMaxSqlCacheSize(int cacheSize) {
        if (cacheSize > 100 || cacheSize < 0) {
            throw new IllegalStateException("expected value between 0 and 100");
        }
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            int oldMaxSqlCacheSize = this.mConfigurationLocked.maxSqlCacheSize;
            this.mConfigurationLocked.maxSqlCacheSize = cacheSize;
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
            } catch (RuntimeException ex) {
                this.mConfigurationLocked.maxSqlCacheSize = oldMaxSqlCacheSize;
                throw ex;
            }
        }
    }

    @NeverCompile
    public double getStatementCacheMissRate() {
        double statementCacheMissRate;
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            statementCacheMissRate = this.mConnectionPoolLocked.getStatementCacheMissRate();
        }
        return statementCacheMissRate;
    }

    public void setForeignKeyConstraintsEnabled(boolean enable) {
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            if (this.mConfigurationLocked.foreignKeyConstraintsEnabled == enable) {
                return;
            }
            this.mConfigurationLocked.foreignKeyConstraintsEnabled = enable;
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
            } catch (RuntimeException ex) {
                this.mConfigurationLocked.foreignKeyConstraintsEnabled = !enable;
                throw ex;
            }
        }
    }

    public boolean enableWriteAheadLogging() {
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            if (this.mConfigurationLocked.resolveJournalMode().equalsIgnoreCase(JOURNAL_MODE_WAL)) {
                return true;
            }
            if (isReadOnlyLocked()) {
                return false;
            }
            if (this.mConfigurationLocked.isInMemoryDb()) {
                Log.m98i(TAG, "can't enable WAL for memory databases.");
                return false;
            }
            if (this.mHasAttachedDbsLocked) {
                if (Log.isLoggable(TAG, 3)) {
                    Log.m94d(TAG, "this database: " + this.mConfigurationLocked.label + " has attached databases. can't  enable WAL.");
                }
                return false;
            }
            this.mConfigurationLocked.openFlags |= 536870912;
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
                this.mBackgroundCheckpoint = new SQLiteWalBackgroundCheckpoint();
                return true;
            } catch (RuntimeException ex) {
                this.mConfigurationLocked.openFlags &= -536870913;
                throw ex;
            }
        }
    }

    public void disableWriteAheadLogging() {
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            int oldFlags = this.mConfigurationLocked.openFlags;
            if (this.mConfigurationLocked.resolveJournalMode().equalsIgnoreCase(JOURNAL_MODE_WAL)) {
                this.mConfigurationLocked.openFlags &= -536870913;
                this.mConfigurationLocked.openFlags &= Integer.MAX_VALUE;
                try {
                    this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
                    this.mBackgroundCheckpoint = null;
                } catch (RuntimeException ex) {
                    this.mConfigurationLocked.openFlags = oldFlags;
                    throw ex;
                }
            }
        }
    }

    public boolean isWriteAheadLoggingEnabled() {
        boolean equalsIgnoreCase;
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            equalsIgnoreCase = this.mConfigurationLocked.resolveJournalMode().equalsIgnoreCase(JOURNAL_MODE_WAL);
        }
        return equalsIgnoreCase;
    }

    public static int semBackupDatabaseFile(String srcPath, String dstPath) {
        if (srcPath == null || dstPath == null) {
            Log.m96e(TAG, "path should not be null.");
            return -1;
        }
        return SQLiteConnection.nativeBackupDatabaseFile(srcPath, dstPath);
    }

    @Deprecated
    public static int semBackupSecureDatabaseFile(String srcPath, String dstPath, byte[] password) {
        int rc;
        if (srcPath == null || dstPath == null || password == null) {
            Log.m96e(TAG, "path and password should not be null.");
            return -1;
        }
        synchronized (mSecureLock) {
            rc = SQLiteConnection.nativeBackupSecureDatabaseFile(srcPath, dstPath, password);
        }
        return rc;
    }

    public static int semRestoreDatabaseFile(String srcPath, String dstPath) {
        if (srcPath == null || dstPath == null) {
            Log.m96e(TAG, "path should not be null.");
            return -1;
        }
        return SQLiteConnection.nativeRestoreDatabaseFile(srcPath, dstPath);
    }

    @Deprecated
    public static int semRestoreSecureDatabaseFile(String srcPath, String dstPath, byte[] password) {
        int rc;
        if (srcPath == null || dstPath == null || password == null) {
            Log.m96e(TAG, "path and password should not be null.");
            return -1;
        }
        synchronized (mSecureLock) {
            rc = SQLiteConnection.nativeRestoreSecureDatabaseFile(srcPath, dstPath, password);
        }
        return rc;
    }

    public static int cleanDatabaseFile(String dbPath) {
        return SQLiteConnection.nativeCleanDatabaseFile(dbPath);
    }

    public int getCorruptCode() {
        return this.mCorruptCode;
    }

    void onCorruption(int errorCode) {
        int poolSize = 0;
        boolean waitCorruptionHandlingAndExit = false;
        synchronized (this.mLock) {
            if (this.mInCorruptionHandling) {
                waitCorruptionHandlingAndExit = true;
            } else {
                this.mInCorruptionHandling = true;
                this.mDbDump.prepareDumpFile();
                SQLiteDump sQLiteDump = this.mDbDump;
                sQLiteDump.addDumpLog(TAG, sQLiteDump.getSQLiteDumpLogs(true));
                if (isOpen()) {
                    this.mConnectionPoolLocked.dumpAllConnections(this.mDbDump);
                    poolSize = getMaxConnectionPoolSize();
                    closeAndDiscardNonPrimaryConnections(true, false);
                }
            }
        }
        if (waitCorruptionHandlingAndExit) {
            int tryCount = 0;
            Log.m94d(TAG, "Database corruption is already handling, wait.");
            do {
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!this.mInCorruptionHandling) {
                    break;
                } else {
                    tryCount++;
                }
            } while (tryCount < 20);
            Log.m94d(TAG, "Exit onCorruption.");
            return;
        }
        try {
            this.mCorruptCode = errorCode;
            this.mDefaultErrorHandler.onCorruption(this);
            EventLog.writeEvent(EVENT_DB_CORRUPT, getLabel());
            DatabaseErrorHandler databaseErrorHandler = this.mErrorHandler;
            if (databaseErrorHandler != null) {
                databaseErrorHandler.onCorruption(this);
            }
            synchronized (this.mLock) {
                if (poolSize > 0) {
                    if (isOpen()) {
                        this.mConnectionPoolLocked.setMaxConnectionPoolSize(poolSize);
                    }
                }
                this.mInCorruptionHandling = false;
                this.mIsDatabaseCorrupted = false;
                SQLiteDump sQLiteDump2 = this.mDbDump;
                sQLiteDump2.addDumpLog(TAG, sQLiteDump2.getSQLiteDumpLogs(true));
                this.mDbDump.finishDump();
            }
        } catch (Throwable th) {
            synchronized (this.mLock) {
                if (poolSize > 0) {
                    if (isOpen()) {
                        this.mConnectionPoolLocked.setMaxConnectionPoolSize(poolSize);
                    }
                }
                this.mInCorruptionHandling = false;
                this.mIsDatabaseCorrupted = false;
                SQLiteDump sQLiteDump3 = this.mDbDump;
                sQLiteDump3.addDumpLog(TAG, sQLiteDump3.getSQLiteDumpLogs(true));
                this.mDbDump.finishDump();
                throw th;
            }
        }
    }

    public boolean isForcedReadOnlyDatabase() {
        acquireReference();
        try {
            boolean isForcedReadOnlyDatabase = getThreadSession().isForcedReadOnlyDatabase();
            releaseReference();
            return isForcedReadOnlyDatabase;
        } catch (SQLiteException e) {
            releaseReference();
            return false;
        } catch (Throwable th) {
            releaseReference();
            throw th;
        }
    }

    public boolean isUdrEnabled() {
        return this.udr != null;
    }

    public boolean hasAttachedDbsLocked() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mHasAttachedDbsLocked;
        }
        return z;
    }

    static ArrayList<SQLiteDebug.DbStats> getDbStats() {
        ArrayList<SQLiteDebug.DbStats> dbStatsList = new ArrayList<>();
        Iterator<SQLiteDatabase> it = getActiveDatabases().iterator();
        while (it.hasNext()) {
            SQLiteDatabase db = it.next();
            db.collectDbStats(dbStatsList);
        }
        return dbStatsList;
    }

    private void collectDbStats(ArrayList<SQLiteDebug.DbStats> dbStatsList) {
        synchronized (this.mLock) {
            SQLiteConnectionPool sQLiteConnectionPool = this.mConnectionPoolLocked;
            if (sQLiteConnectionPool != null) {
                sQLiteConnectionPool.collectDbStats(dbStatsList);
            }
        }
    }

    private static ArrayList<SQLiteDatabase> getActiveDatabases() {
        ArrayList<SQLiteDatabase> databases = new ArrayList<>();
        synchronized (sActiveDatabases) {
            databases.addAll(sActiveDatabases.keySet());
        }
        return databases;
    }

    private static ArrayList<SQLiteConnectionPool> getActiveDatabasePools() {
        ArrayList<SQLiteConnectionPool> connectionPools = new ArrayList<>();
        synchronized (sActiveDatabases) {
            for (SQLiteDatabase db : sActiveDatabases.keySet()) {
                synchronized (db.mLock) {
                    SQLiteConnectionPool sQLiteConnectionPool = db.mConnectionPoolLocked;
                    if (sQLiteConnectionPool != null) {
                        connectionPools.add(sQLiteConnectionPool);
                    }
                }
            }
        }
        return connectionPools;
    }

    @NeverCompile
    public int getTotalPreparedStatements() {
        throwIfNotOpenLocked();
        return this.mConnectionPoolLocked.mTotalPrepareStatements;
    }

    @NeverCompile
    public int getTotalStatementCacheMisses() {
        throwIfNotOpenLocked();
        return this.mConnectionPoolLocked.mTotalPrepareStatementCacheMiss;
    }

    private static void printCorruptionDumpFiles(Printer printer, String dumpDir) {
        File[] dumpFiles;
        if (new File(dumpDir).exists() && (dumpFiles = new File(dumpDir).listFiles()) != null) {
            for (File file : dumpFiles) {
                if (file.isFile()) {
                    try {
                        BufferedReader br = Files.newBufferedReader(Paths.get(file.getAbsolutePath(), new String[0]));
                        try {
                            printer.println("");
                            while (true) {
                                String line = br.readLine();
                                if (line == null) {
                                    break;
                                } else {
                                    printer.println(line);
                                }
                            }
                            printer.println("");
                            if (br != null) {
                                br.close();
                            }
                        } catch (Throwable th) {
                            if (br != null) {
                                try {
                                    br.close();
                                } catch (Throwable th2) {
                                    th.addSuppressed(th2);
                                }
                            }
                            throw th;
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }
    }

    static void dumpAll(Printer printer, boolean verbose, boolean isSystem) {
        ArraySet<String> directories = new ArraySet<>();
        long totalStatementsTimeInMs = 0;
        long totalStatementsCount = 0;
        ArrayList<SQLiteConnectionPool> activeConnectionPools = getActiveDatabasePools();
        activeConnectionPools.sort(new Comparator() { // from class: android.database.sqlite.SQLiteDatabase$$ExternalSyntheticLambda2
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int compare;
                compare = Long.compare(((SQLiteConnectionPool) obj2).getTotalStatementsCount(), ((SQLiteConnectionPool) obj).getTotalStatementsCount());
                return compare;
            }
        });
        Iterator<SQLiteConnectionPool> it = activeConnectionPools.iterator();
        while (it.hasNext()) {
            SQLiteConnectionPool dbPool = it.next();
            SQLiteDatabase db = dbPool.getDatabase();
            if (db != null) {
                printer.println("  Attached db: " + db.hasAttachedDbsLocked());
                dbPool.dump(printer, verbose, directories);
                totalStatementsTimeInMs += dbPool.getTotalStatementsTime();
                totalStatementsCount += dbPool.getTotalStatementsCount();
            }
        }
        if (totalStatementsCount > 0) {
            printer.println("Statements Executed per Database");
            Iterator<SQLiteConnectionPool> it2 = activeConnectionPools.iterator();
            while (it2.hasNext()) {
                SQLiteConnectionPool dbPool2 = it2.next();
                printer.println("  " + dbPool2.getPath() + " :    " + dbPool2.getTotalStatementsCount());
            }
            printer.println("");
            printer.println("Total Statements Executed for all Active Databases: " + totalStatementsCount);
            activeConnectionPools.sort(new Comparator() { // from class: android.database.sqlite.SQLiteDatabase$$ExternalSyntheticLambda3
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    int compare;
                    compare = Long.compare(((SQLiteConnectionPool) obj2).getTotalStatementsTime(), ((SQLiteConnectionPool) obj).getTotalStatementsTime());
                    return compare;
                }
            });
            printer.println("");
            printer.println("");
            printer.println("Statement Time per Database (ms)");
            Iterator<SQLiteConnectionPool> it3 = activeConnectionPools.iterator();
            while (it3.hasNext()) {
                SQLiteConnectionPool dbPool3 = it3.next();
                printer.println("  " + dbPool3.getPath() + " :    " + dbPool3.getTotalStatementsTime());
            }
            printer.println("Total Statements Time for all Active Databases (ms): " + totalStatementsTimeInMs);
        }
        if (directories.size() > 0) {
            String[] dirs = (String[]) directories.toArray(new String[directories.size()]);
            Arrays.sort(dirs);
            for (String dir : dirs) {
                dumpDatabaseDirectory(printer, new File(dir), isSystem);
            }
        }
        ArrayList<String> dbDirs = getDbDirectories();
        if (dbDirs.size() > 0) {
            Iterator<String> it4 = dbDirs.iterator();
            while (it4.hasNext()) {
                String dir2 = it4.next();
                File dumpDir = new File(dir2, SQLiteDump.DB_INFO_DUMP_DIR_NAME);
                try {
                    printCorruptionDumpFiles(printer, dumpDir.getAbsolutePath());
                } catch (Exception e) {
                }
            }
        }
    }

    private static ArrayList<String> getDbDirectories() {
        ArrayList<String> dbDirs;
        synchronized (sDbDirectories) {
            dbDirs = new ArrayList<>(sDbDirectories);
        }
        return dbDirs;
    }

    private static void dumpDatabaseDirectory(Printer pw, File dir, boolean isSystem) {
        int i;
        pw.println("");
        pw.println("Database files in " + dir.getAbsolutePath() + ":");
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            pw.println("  [none]");
            return;
        }
        Arrays.sort(files, new Comparator() { // from class: android.database.sqlite.SQLiteDatabase$$ExternalSyntheticLambda1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int compareTo;
                compareTo = ((File) obj).getName().compareTo(((File) obj2).getName());
                return compareTo;
            }
        });
        for (File f : files) {
            if (isSystem) {
                String name = f.getName();
                i = (name.endsWith(".db") || name.endsWith(".db-wal") || name.endsWith(".db-journal") || name.endsWith("-wipecheck")) ? 0 : i + 1;
            }
            Object[] objArr = new Object[4];
            objArr[0] = f.getName();
            objArr[1] = Long.valueOf(f.length());
            objArr[2] = getFileTimestamps(f.getAbsolutePath());
            objArr[3] = SQLiteUserDataRecovery.isDbUdrRecovered(f.getAbsolutePath()) ? " (R)" : "";
            pw.println(String.format("  %-40s %7db %s%s", objArr));
        }
    }

    public List<Pair<String, String>> getAttachedDbs() {
        ArrayList<Pair<String, String>> attachedDbs = new ArrayList<>();
        synchronized (this.mLock) {
            if (this.mConnectionPoolLocked == null) {
                return null;
            }
            if (!this.mHasAttachedDbsLocked) {
                attachedDbs.add(new Pair<>("main", this.mConfigurationLocked.path));
                return attachedDbs;
            }
            acquireReference();
            Cursor c = null;
            try {
                try {
                    c = rawQuery("pragma database_list;", null);
                    while (c.moveToNext()) {
                        attachedDbs.add(new Pair<>(c.getString(1), c.getString(2)));
                    }
                    return attachedDbs;
                } finally {
                    if (c != null) {
                        c.close();
                    }
                }
            } finally {
                releaseReference();
            }
        }
    }

    public boolean isDatabaseIntegrityOk() {
        return isDatabaseIntegrityOk(false);
    }

    public boolean isDatabaseIntegrityOk(boolean extraCheck) {
        List<Pair<String, String>> attachedDbs;
        acquireReference();
        try {
            try {
                attachedDbs = getAttachedDbs();
            } catch (SQLiteException e) {
                attachedDbs = new ArrayList();
                attachedDbs.add(new Pair<>("main", getPath()));
            }
            if (attachedDbs == null) {
                this.mDbDump.addDumpLog(TAG, "databaselist for: " + getPath() + " couldn't be retrieved. probably because the database is closed");
                throw new IllegalStateException("databaselist for: " + getPath() + " couldn't be retrieved. probably because the database is closed");
            }
            for (int i = 0; i < attachedDbs.size(); i++) {
                Pair<String, String> p = attachedDbs.get(i);
                SQLiteStatement prog = null;
                if (extraCheck) {
                    prog = compileStatement("PRAGMA " + p.first + ".extra_integrity_check(1);");
                } else {
                    try {
                        try {
                            prog = compileStatement("PRAGMA " + p.first + ".integrity_check(1);");
                        } catch (Throwable th) {
                            if (prog != null) {
                                prog.close();
                            }
                            throw th;
                        }
                    } catch (SQLiteDatabaseCorruptException e2) {
                        this.mIntegrityErrorString = "false";
                        if (prog != null) {
                            prog.close();
                        }
                        return false;
                    }
                }
                String rslt = prog.simpleQueryForIntegrityCheck();
                this.mIntegrityErrorString = null;
                if (rslt != null && !rslt.equalsIgnoreCase("ok")) {
                    this.mIntegrityErrorString = rslt;
                    this.mDbDump.logAndDump(TAG, "PRAGMA integrity_check on " + p.second + " returned: " + rslt);
                    if (prog != null) {
                        prog.close();
                    }
                    return false;
                }
                if (prog != null) {
                    prog.close();
                }
            }
            return true;
        } finally {
            releaseReference();
        }
    }

    public String getIntegrityErrorInfo() {
        return this.mIntegrityErrorString;
    }

    public void closeAndDiscardNonPrimaryConnections(boolean needDiscard, boolean fixPoolSize) {
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            this.mConnectionPoolLocked.closeAndDiscardNonPrimaryConnections(needDiscard, fixPoolSize);
        }
    }

    public void reOpen() {
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            this.mConnectionPoolLocked.reOpen();
        }
    }

    public void setCheckpointOnClose(boolean set) {
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            this.mConnectionPoolLocked.setCheckpointOnClose(set);
        }
    }

    public boolean diagnoseError() {
        try {
            cleanCacheAndReOpen();
            if (isDatabaseIntegrityOk(getCorruptCode() == 1035)) {
                this.mDbDump.logAndDump(TAG, "!@ Integrity Check for corrupted DB file gets OK as result");
                return true;
            }
        } catch (SQLiteException e) {
        }
        return false;
    }

    private void cleanCacheAndReOpen() {
        if (inTransaction()) {
            endTransaction();
        }
        execSQL("PRAGMA drop_db_fs_cache;");
        List<Pair<String, String>> attachedDbs = getAttachedDbs();
        if (attachedDbs != null && attachedDbs.size() <= 1) {
            setCheckpointOnClose(false);
            reOpen();
        }
    }

    private byte[] getConnectionKey() {
        synchronized (this.mLock) {
            SQLiteConnectionPool sQLiteConnectionPool = this.mConnectionPoolLocked;
            if (sQLiteConnectionPool != null) {
                return sQLiteConnectionPool.getConnectionKey();
            }
            return this.mPassword;
        }
    }

    public String toString() {
        return "SQLiteDatabase: " + getPath();
    }

    private void throwIfNotOpenLocked() {
        if (this.mConnectionPoolLocked == null) {
            throw new IllegalStateException("The database '" + this.mConfigurationLocked.label + "' is not open.");
        }
    }

    public static final class OpenParams {
        private final int mCacheSize;
        private final CursorFactory mCursorFactory;
        private final DatabaseErrorHandler mErrorHandler;
        private final long mIdleConnectionShrinkTimeout;
        private final long mIdleConnectionTimeout;
        private final String mJournalMode;
        private final int mLookasideSlotCount;
        private final int mLookasideSlotSize;
        private final int mOpenFlags;
        private final String mSyncMode;
        private final boolean mUserDataRecovery;

        private OpenParams(int openFlags, CursorFactory cursorFactory, DatabaseErrorHandler errorHandler, int lookasideSlotSize, int lookasideSlotCount, long idleConnectionTimeout, long idleConnectionShrinkTimeout, String journalMode, String syncMode, int cacheSize, boolean userDataRecovery) {
            this.mCursorFactory = cursorFactory;
            this.mErrorHandler = errorHandler;
            this.mLookasideSlotSize = lookasideSlotSize;
            this.mLookasideSlotCount = lookasideSlotCount;
            this.mIdleConnectionTimeout = idleConnectionTimeout;
            this.mIdleConnectionShrinkTimeout = idleConnectionShrinkTimeout;
            this.mJournalMode = journalMode;
            this.mSyncMode = syncMode;
            this.mCacheSize = cacheSize;
            this.mUserDataRecovery = userDataRecovery;
            if ((536870912 & openFlags) != 0) {
                this.mOpenFlags = openFlags & (-1025);
            } else if (journalMode != null && !journalMode.equalsIgnoreCase(SQLiteDatabase.JOURNAL_MODE_WAL)) {
                this.mOpenFlags = openFlags | 1024;
            } else {
                this.mOpenFlags = openFlags;
            }
        }

        public int getLookasideSlotSize() {
            return this.mLookasideSlotSize;
        }

        public int getLookasideSlotCount() {
            return this.mLookasideSlotCount;
        }

        public int getOpenFlags() {
            return this.mOpenFlags;
        }

        public CursorFactory getCursorFactory() {
            return this.mCursorFactory;
        }

        public DatabaseErrorHandler getErrorHandler() {
            return this.mErrorHandler;
        }

        public long getIdleConnectionTimeout() {
            return this.mIdleConnectionTimeout;
        }

        public String getJournalMode() {
            return this.mJournalMode;
        }

        public String getSynchronousMode() {
            return this.mSyncMode;
        }

        public Builder toBuilder() {
            return new Builder(this);
        }

        public static final class Builder {
            private int mCacheSize;
            private CursorFactory mCursorFactory;
            private DatabaseErrorHandler mErrorHandler;
            private long mIdleConnectionShrinkTimeout;
            private long mIdleConnectionTimeout;
            private String mJournalMode;
            private int mLookasideSlotCount;
            private int mLookasideSlotSize;
            private int mOpenFlags;
            private String mSyncMode;
            private boolean mUserDataRecovery;

            public Builder() {
                this.mLookasideSlotSize = -1;
                this.mLookasideSlotCount = -1;
                this.mIdleConnectionTimeout = -1L;
                this.mIdleConnectionShrinkTimeout = -1L;
            }

            public Builder(OpenParams params) {
                this.mLookasideSlotSize = -1;
                this.mLookasideSlotCount = -1;
                this.mIdleConnectionTimeout = -1L;
                this.mIdleConnectionShrinkTimeout = -1L;
                this.mLookasideSlotSize = params.mLookasideSlotSize;
                this.mLookasideSlotCount = params.mLookasideSlotCount;
                this.mIdleConnectionTimeout = params.mIdleConnectionTimeout;
                this.mIdleConnectionShrinkTimeout = params.mIdleConnectionShrinkTimeout;
                this.mOpenFlags = params.mOpenFlags;
                this.mCursorFactory = params.mCursorFactory;
                this.mErrorHandler = params.mErrorHandler;
                this.mJournalMode = params.mJournalMode;
                this.mSyncMode = params.mSyncMode;
                this.mCacheSize = params.mCacheSize;
                this.mUserDataRecovery = params.mUserDataRecovery;
            }

            public Builder setLookasideConfig(int slotSize, int slotCount) {
                boolean z = true;
                Preconditions.checkArgument(slotSize >= 0, "lookasideSlotCount cannot be negative");
                Preconditions.checkArgument(slotCount >= 0, "lookasideSlotSize cannot be negative");
                if ((slotSize <= 0 || slotCount <= 0) && (slotCount != 0 || slotSize != 0)) {
                    z = false;
                }
                Preconditions.checkArgument(z, "Invalid configuration: %d, %d", Integer.valueOf(slotSize), Integer.valueOf(slotCount));
                this.mLookasideSlotSize = slotSize;
                this.mLookasideSlotCount = slotCount;
                return this;
            }

            public boolean isWriteAheadLoggingEnabled() {
                return (this.mOpenFlags & 536870912) != 0;
            }

            public Builder setOpenFlags(int openFlags) {
                this.mOpenFlags = openFlags;
                return this;
            }

            public Builder addOpenFlags(int openFlags) {
                this.mOpenFlags |= openFlags;
                return this;
            }

            public Builder removeOpenFlags(int openFlags) {
                int i = this.mOpenFlags & (~openFlags);
                this.mOpenFlags = i;
                if ((536870912 & openFlags) != 0) {
                    this.mOpenFlags = i | 1024;
                }
                return this;
            }

            public void setWriteAheadLoggingEnabled(boolean enabled) {
                if (enabled) {
                    addOpenFlags(536870912);
                } else {
                    removeOpenFlags(536870912);
                }
            }

            public Builder setCursorFactory(CursorFactory cursorFactory) {
                this.mCursorFactory = cursorFactory;
                return this;
            }

            public Builder setErrorHandler(DatabaseErrorHandler errorHandler) {
                this.mErrorHandler = errorHandler;
                return this;
            }

            @Deprecated
            public Builder setIdleConnectionTimeout(long idleConnectionTimeoutMs) {
                Preconditions.checkArgument(idleConnectionTimeoutMs >= 0, "idle connection timeout cannot be negative");
                this.mIdleConnectionTimeout = idleConnectionTimeoutMs;
                return this;
            }

            public Builder setJournalMode(String journalMode) {
                Objects.requireNonNull(journalMode);
                this.mJournalMode = journalMode;
                return this;
            }

            public Builder setSynchronousMode(String syncMode) {
                Objects.requireNonNull(syncMode);
                this.mSyncMode = syncMode;
                return this;
            }

            @Deprecated
            public Builder semSetCacheSize(int cacheSizeByte) {
                if (cacheSizeByte < 0 || cacheSizeByte > 8388608) {
                    throw new IllegalArgumentException("The cache size should not be negative value. Also, it should be less than soft heap size (8M). Now: " + cacheSizeByte);
                }
                this.mCacheSize = cacheSizeByte / SQLiteGlobal.getDefaultPageSize();
                return this;
            }

            public Builder semSetSeparateCacheModeEnabled(boolean enabled) {
                if (enabled) {
                    addOpenFlags(4096);
                } else {
                    removeOpenFlags(4096);
                }
                return this;
            }

            public Builder semSetIdleConnectionShrinkTimeout(long idleConnectionShrinkTimeoutMs) {
                Preconditions.checkArgument(idleConnectionShrinkTimeoutMs >= 0, "idle connection shrink timeout cannot be negative");
                this.mIdleConnectionShrinkTimeout = idleConnectionShrinkTimeoutMs;
                return this;
            }

            public Builder setUserDataRecoveryEnabled(boolean enabled) {
                this.mUserDataRecovery = enabled;
                return this;
            }

            public OpenParams build() {
                return new OpenParams(this.mOpenFlags, this.mCursorFactory, this.mErrorHandler, this.mLookasideSlotSize, this.mLookasideSlotCount, this.mIdleConnectionTimeout, this.mIdleConnectionShrinkTimeout, this.mJournalMode, this.mSyncMode, this.mCacheSize, this.mUserDataRecovery);
            }
        }
    }

    public static void wipeDetected(String filename, String reason) {
        wtfAsSystemServer(TAG, "DB wipe detected: package=" + ActivityThread.currentPackageName() + " reason=" + reason + " file=" + filename + " " + getFileTimestamps(filename) + " checkfile " + getFileTimestamps(filename + "-wipecheck"), new Throwable("STACKTRACE"));
    }

    public static String getFileTimestamps(String path) {
        try {
            BasicFileAttributes attr = Files.readAttributes(FileSystems.getDefault().getPath(path, new String[0]), (Class<BasicFileAttributes>) BasicFileAttributes.class, new LinkOption[0]);
            return "ctime=" + attr.creationTime() + " mtime=" + attr.lastModifiedTime() + " atime=" + attr.lastAccessTime();
        } catch (IOException e) {
            return "[unable to obtain timestamp]";
        }
    }

    static void wtfAsSystemServer(String tag, String message, Throwable stacktrace) {
        Log.m97e(tag, message, stacktrace);
        ContentResolver.onDbCorruption(tag, message, stacktrace);
    }

    public static void renameDatabaseFile(String from, String to) {
        new File(from).renameTo(new File(to));
        File fi = new File(from + "-journal");
        if (fi.exists()) {
            fi.renameTo(new File(to + "-journal"));
        }
        File fi2 = new File(from + "-wal");
        if (fi2.exists()) {
            fi2.renameTo(new File(to + "-wal"));
        }
        File fi3 = new File(from + "-shm");
        if (fi3.exists()) {
            fi3.renameTo(new File(to + "-shm"));
        }
        File fi4 = new File(from + "-se");
        if (fi4.exists()) {
            fi4.renameTo(new File(to + "-se"));
        }
        File fi5 = new File(from + "-udr");
        if (fi5.exists()) {
            fi5.renameTo(new File(to + "-udr"));
        }
        new File(from + "-wipecheck").delete();
    }

    public static boolean deleteDatabaseFile(String file) {
        File fi = new File(file);
        boolean ret = fi.delete();
        if (ret) {
            new File(file + "-journal").delete();
            new File(file + "-wal").delete();
            new File(file + "-shm").delete();
            new File(file + "-se").delete();
            new File(file + "-udr").delete();
            new File(file + "-wipecheck").delete();
        }
        return ret;
    }

    public void setAutomaticIndexEnabled(boolean enable) {
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            if (this.mConfigurationLocked.automaticIndexEnabled == enable) {
                return;
            }
            this.mConfigurationLocked.automaticIndexEnabled = enable;
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
            } catch (RuntimeException ex) {
                this.mConfigurationLocked.automaticIndexEnabled = !enable;
                throw ex;
            }
        }
    }

    public void setCaseSensitiveLikeEnabled(boolean enable) {
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            if (this.mConfigurationLocked.caseSensitiveLikeEnabled == enable) {
                return;
            }
            this.mConfigurationLocked.caseSensitiveLikeEnabled = enable;
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
            } catch (RuntimeException ex) {
                this.mConfigurationLocked.caseSensitiveLikeEnabled = !enable;
                throw ex;
            }
        }
    }

    public void setBusyTimeout(long timeout) {
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            if (this.mConfigurationLocked.busyTimeout == timeout) {
                return;
            }
            long busyTimeout = this.mConfigurationLocked.busyTimeout;
            this.mConfigurationLocked.busyTimeout = timeout;
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
            } catch (RuntimeException ex) {
                this.mConfigurationLocked.busyTimeout = busyTimeout;
                throw ex;
            }
        }
    }

    public void setCacheSize(int size) {
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            if (this.mConfigurationLocked.cacheSize == size) {
                return;
            }
            int cacheSize = this.mConfigurationLocked.cacheSize;
            this.mConfigurationLocked.cacheSize = size;
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
            } catch (RuntimeException ex) {
                this.mConfigurationLocked.cacheSize = cacheSize;
                throw ex;
            }
        }
    }

    public void tryWalBackgroundCheckpoint() {
        try {
            if (this.mBackgroundCheckpoint == null) {
                return;
            }
            synchronized (this.mLock) {
                SQLiteConnectionPool sQLiteConnectionPool = this.mConnectionPoolLocked;
                if (sQLiteConnectionPool != null && !sQLiteConnectionPool.isConnectionAcquired()) {
                    this.mBackgroundCheckpoint.tryBackgroundCheckpoint(this, new File(getPath() + "-wal"));
                }
            }
        } catch (Exception e) {
        }
    }

    public int getMaxConnectionPoolSize() {
        int poolSize;
        synchronized (this.mLock) {
            poolSize = this.mConnectionPoolLocked.getMaxConnectionPoolSize();
        }
        return poolSize;
    }

    public void setContext(Context context) {
        if (context == null) {
            return;
        }
        try {
            this.mContext = context.getApplicationContext();
        } catch (Exception e) {
            this.mContext = null;
        }
    }

    public Context getContext() {
        return this.mContext;
    }

    public void setReserveSpace() {
        synchronized (this.mLock) {
            if (this.mConfigurationLocked.sharedConfig.isMediaStoreDb) {
                try {
                    long reservedSpace = DatabaseUtils.longForQuery(this, "PRAGMA wal_reserve_space=1", null);
                    if (reservedSpace < 0) {
                        Log.m94d(TAG, "Failed to set wal reserve size");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean doRecovery() {
        boolean isSecureDb = this.mConfigurationLocked.sharedConfig.isSecureDb;
        boolean isNoLocalized = (this.mConfigurationLocked.openFlags & 16) != 0;
        SQLiteUserDataRecovery sQLiteUserDataRecovery = this.udr;
        if (sQLiteUserDataRecovery != null) {
            return sQLiteUserDataRecovery.doRecovery(getPath(), isSecureDb ? getConnectionKey() : null, isNoLocalized ? null : this.mConfigurationLocked.locale.toString());
        }
        return false;
    }
}
