package android.database.sqlite;

import android.app.ActivityThread;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDebug;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.CancellationSignal;
import android.os.Looper;
import android.os.SystemProperties;
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
    public static final int NO_DOUBLE_QUOTED_STRS = 32;
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
    private final ThreadLocal<SQLiteSession> mThreadSession = ThreadLocal.withInitial(new Supplier() { // from class: android.database.sqlite.SQLiteDatabase$$ExternalSyntheticLambda3
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

    @Deprecated
    public boolean isDbLockedByOtherThreads() {
        return false;
    }

    @Deprecated
    public void markTableSyncable(String str, String str2) {
    }

    @Deprecated
    public void markTableSyncable(String str, String str2, String str3) {
    }

    @Deprecated
    public void setLockingEnabled(boolean z) {
    }

    public void setSdpDatabase() {
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00c1  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private SQLiteDatabase(java.lang.String r4, int r5, android.database.sqlite.SQLiteDatabase.CursorFactory r6, android.database.DatabaseErrorHandler r7, int r8, int r9, long r10, long r12, java.lang.String r14, java.lang.String r15, int r16, boolean r17) {
        /*
            r3 = this;
            r3.<init>()
            android.database.sqlite.SQLiteDatabase$$ExternalSyntheticLambda3 r0 = new android.database.sqlite.SQLiteDatabase$$ExternalSyntheticLambda3
            r0.<init>()
            java.lang.ThreadLocal r0 = java.lang.ThreadLocal.withInitial(r0)
            r3.mThreadSession = r0
            r0 = 0
            r3.mInCorruptionHandling = r0
            r3.mIsDatabaseCorrupted = r0
            r1 = 0
            r3.mIntegrityErrorString = r1
            r3.mPassword = r1
            java.lang.Object r2 = new java.lang.Object
            r2.<init>()
            r3.mLock = r2
            dalvik.system.CloseGuard r2 = dalvik.system.CloseGuard.get()
            r3.mCloseGuardLocked = r2
            r3.udr = r1
            android.database.sqlite.SQLiteDump r2 = android.database.sqlite.SQLiteDump.DUMMY_DB_DUMP
            r3.mDbDump = r2
            r3.mSdpHelper = r1
            r3.mCorruptCode = r0
            r1 = 1
            r3.mTrackClosure = r1
            r3.mCursorFactory = r6
            r3.mErrorHandler = r7
            android.database.sqlite.SQLiteDatabaseConfiguration r6 = new android.database.sqlite.SQLiteDatabaseConfiguration
            r6.<init>(r4, r5)
            r3.mConfigurationLocked = r6
            r6.lookasideSlotSize = r8
            r6.lookasideSlotCount = r9
            boolean r5 = r6.isInMemoryDb()
            if (r5 != 0) goto L54
            android.database.sqlite.SQLiteDatabaseSharedConfiguration r5 = r6.sharedConfig
            boolean r5 = r5.useDumpCorruptByDefault
            if (r5 == 0) goto L54
            android.database.sqlite.SQLiteDump r5 = new android.database.sqlite.SQLiteDump
            r5.<init>(r4)
            r3.mDbDump = r5
        L54:
            android.database.DefaultDatabaseErrorHandler r4 = new android.database.DefaultDatabaseErrorHandler
            android.database.sqlite.SQLiteDump r5 = r3.mDbDump
            r4.<init>(r5)
            if (r7 != 0) goto L5f
            r5 = r1
            goto L60
        L5f:
            r5 = r0
        L60:
            r4.setDeleteDatabaseIfCorrupted(r5)
            r3.mDefaultErrorHandler = r4
            boolean r4 = android.app.ActivityManager.isLowRamDeviceStatic()
            if (r4 == 0) goto L6f
            r6.lookasideSlotCount = r0
            r6.lookasideSlotSize = r0
        L6f:
            boolean r4 = r6.isInMemoryDb()
            r7 = 0
            if (r4 != 0) goto L87
            int r4 = (r10 > r7 ? 1 : (r10 == r7 ? 0 : -1))
            if (r4 < 0) goto L7d
            r4 = r10
            goto L8c
        L7d:
            boolean r4 = android.database.sqlite.SQLiteDatabase.DEBUG_CLOSE_IDLE_CONNECTIONS
            if (r4 == 0) goto L87
            int r4 = android.database.sqlite.SQLiteGlobal.getIdleConnectionTimeout()
            long r4 = (long) r4
            goto L8c
        L87:
            r4 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
        L8c:
            int r7 = (r12 > r7 ? 1 : (r12 == r7 ? 0 : -1))
            if (r7 < 0) goto L92
            r6.idleConnectionShrinkTimeoutMs = r12
        L92:
            r6.idleConnectionTimeoutMs = r4
            boolean r4 = android.database.sqlite.SQLiteCompatibilityWalFlags.isLegacyCompatibilityWalEnabled()
            if (r4 == 0) goto La1
            int r4 = r6.openFlags
            r5 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r4 | r5
            r6.openFlags = r4
        La1:
            boolean r4 = android.database.sqlite.SQLiteDebug.NoPreloadHolder.NO_DOUBLE_QUOTED_STRS
            if (r4 == 0) goto Lab
            int r4 = r6.openFlags
            r4 = r4 | 32
            r6.openFlags = r4
        Lab:
            r4 = r14
            r6.journalMode = r4
            r4 = r15
            r6.syncMode = r4
            r4 = r16
            r6.cacheSize = r4
            java.lang.String r4 = r6.resolveJournalMode()
            java.lang.String r5 = "WAL"
            boolean r4 = r4.equalsIgnoreCase(r5)
            if (r4 == 0) goto Lc8
            android.database.sqlite.SQLiteWalBackgroundCheckpoint r4 = new android.database.sqlite.SQLiteWalBackgroundCheckpoint
            r4.<init>()
            r3.mBackgroundCheckpoint = r4
        Lc8:
            if (r17 == 0) goto Ld8
            android.database.sqlite.SQLiteUserDataRecovery r4 = new android.database.sqlite.SQLiteUserDataRecovery
            android.database.sqlite.SQLiteDump r5 = r3.mDbDump
            r4.<init>(r5)
            r3.udr = r4
            android.database.sqlite.SQLiteDatabaseSharedConfiguration r3 = r6.sharedConfig
            r3.setUserDataRecovery(r1)
        Ld8:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.database.sqlite.SQLiteDatabase.<init>(java.lang.String, int, android.database.sqlite.SQLiteDatabase$CursorFactory, android.database.DatabaseErrorHandler, int, int, long, long, java.lang.String, java.lang.String, int, boolean):void");
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

    private void dispose(boolean z) {
        SQLiteConnectionPool sQLiteConnectionPool;
        synchronized (this.mLock) {
            CloseGuard closeGuard = this.mCloseGuardLocked;
            if (closeGuard != null) {
                if (z) {
                    closeGuard.warnIfOpen();
                }
                this.mCloseGuardLocked.close();
            }
            sQLiteConnectionPool = this.mConnectionPoolLocked;
            this.mConnectionPoolLocked = null;
        }
        if (z) {
            return;
        }
        synchronized (sActiveDatabases) {
            sActiveDatabases.remove(this);
        }
        if (sQLiteConnectionPool != null) {
            sQLiteConnectionPool.close();
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
            it.next().releaseConnectionMemory();
        }
        return SQLiteGlobal.releaseMemory();
    }

    String getLabel() {
        String str;
        synchronized (this.mLock) {
            str = this.mConfigurationLocked.label;
        }
        return str;
    }

    void onCorruption() {
        int i;
        synchronized (this.mLock) {
            if (this.mInCorruptionHandling) {
                Log.d(TAG, "Database corruption is already handling.");
                return;
            }
            this.mInCorruptionHandling = true;
            this.mDbDump.prepareDumpFile();
            SQLiteDump sQLiteDump = this.mDbDump;
            sQLiteDump.addDumpLog(TAG, sQLiteDump.getSQLiteDumpLogs(true));
            if (isOpen()) {
                this.mConnectionPoolLocked.dumpAllConnections(this.mDbDump);
                i = getMaxConnectionPoolSize();
                closeAndDiscardNonPrimaryConnections(true, false);
            } else {
                i = 0;
            }
            try {
                EventLog.writeEvent(EVENT_DB_CORRUPT, getLabel());
                this.mDefaultErrorHandler.onCorruption(this);
                DatabaseErrorHandler databaseErrorHandler = this.mErrorHandler;
                if (databaseErrorHandler != null) {
                    databaseErrorHandler.onCorruption(this);
                }
                synchronized (this.mLock) {
                    if (i > 0) {
                        if (isOpen()) {
                            this.mConnectionPoolLocked.setMaxConnectionPoolSize(i);
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
                    if (i > 0) {
                        if (isOpen()) {
                            this.mConnectionPoolLocked.setMaxConnectionPoolSize(i);
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

    public void setDatabaseIsCorrupted(boolean z) {
        synchronized (this.mLock) {
            this.mIsDatabaseCorrupted = z;
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
    public static boolean semDeleteAllDatabases(SQLiteDatabase sQLiteDatabase) {
        if (sQLiteDatabase == null) {
            throw new IllegalArgumentException("SQLiteDatabase should not be null.");
        }
        try {
            List<Pair<String, String>> attachedDbs = sQLiteDatabase.getAttachedDbs();
            String path = sQLiteDatabase.getPath();
            if (sQLiteDatabase.isOpen()) {
                sQLiteDatabase.close();
            }
            if (attachedDbs == null) {
                Log.e(TAG, "!@ Failed to get attachedDbs, just delete " + path);
                deleteDatabase(new File(path));
                return true;
            }
            for (Pair<String, String> pair : attachedDbs) {
                Log.e(TAG, "!@ Delete DB File : " + pair.second);
                deleteDatabase(new File(pair.second));
            }
            return true;
        } catch (Exception unused) {
            Log.e(TAG, "!@ semDeleteAllDatabase - Exception during deleting");
            return false;
        }
    }

    SQLiteSession getThreadSession() {
        return this.mThreadSession.get();
    }

    SQLiteSession createSession() {
        SQLiteConnectionPool sQLiteConnectionPool;
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            sQLiteConnectionPool = this.mConnectionPoolLocked;
        }
        return new SQLiteSession(sQLiteConnectionPool);
    }

    int getThreadDefaultConnectionFlags(boolean z) {
        int i = z ? 1 : 2;
        return isMainThread() ? i | 4 : i;
    }

    private static boolean isMainThread() {
        Looper myLooper = Looper.myLooper();
        return myLooper != null && myLooper == Looper.getMainLooper();
    }

    public void beginTransaction() {
        beginTransaction((SQLiteTransactionListener) null, true);
    }

    public void beginTransactionNonExclusive() {
        beginTransaction((SQLiteTransactionListener) null, false);
    }

    public void beginTransactionReadOnly() {
        beginTransactionWithListenerReadOnly(null);
    }

    public void beginTransactionWithListener(SQLiteTransactionListener sQLiteTransactionListener) {
        beginTransaction(sQLiteTransactionListener, true);
    }

    public void beginTransactionWithListenerNonExclusive(SQLiteTransactionListener sQLiteTransactionListener) {
        beginTransaction(sQLiteTransactionListener, false);
    }

    public void beginTransactionWithListenerReadOnly(SQLiteTransactionListener sQLiteTransactionListener) {
        beginTransaction(sQLiteTransactionListener, 0);
    }

    private void beginTransaction(SQLiteTransactionListener sQLiteTransactionListener, boolean z) {
        beginTransaction(sQLiteTransactionListener, z ? 2 : 1);
    }

    private void beginTransaction(SQLiteTransactionListener sQLiteTransactionListener, int i) {
        acquireReference();
        try {
            try {
                getThreadSession().beginTransaction(i, sQLiteTransactionListener, getThreadDefaultConnectionFlags(i == 0), null);
            } catch (SQLiteDatabaseCorruptException e) {
                onCorruption(e.getCorruptCode());
                throw e;
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
            } catch (SQLiteDatabaseCorruptException e) {
                onCorruption(e.getCorruptCode());
                throw e;
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
    public boolean yieldIfContended() {
        return yieldIfContendedHelper(false, -1L);
    }

    public boolean yieldIfContendedSafely() {
        return yieldIfContendedHelper(true, -1L);
    }

    public boolean yieldIfContendedSafely(long j) {
        return yieldIfContendedHelper(true, j);
    }

    private boolean yieldIfContendedHelper(boolean z, long j) {
        acquireReference();
        try {
            try {
                return getThreadSession().yieldTransaction(j, z, null);
            } catch (SQLiteDatabaseCorruptException e) {
                onCorruption(e.getCorruptCode());
                throw e;
            }
        } finally {
            releaseReference();
        }
    }

    @Deprecated
    public Map<String, String> getSyncedTables() {
        return new HashMap(0);
    }

    public static SQLiteDatabase openDatabase(String str, CursorFactory cursorFactory, int i) {
        return openDatabase(str, cursorFactory, i, null);
    }

    public static SQLiteDatabase openDatabase(File file, OpenParams openParams) {
        return openDatabase(file.getPath(), openParams, (Context) null);
    }

    private static SQLiteDatabase openDatabase(String str, OpenParams openParams) {
        return openDatabase(str, openParams, (Context) null);
    }

    public static SQLiteDatabase openDatabase(String str, OpenParams openParams, Context context) {
        Preconditions.checkArgument(openParams != null, "OpenParams cannot be null");
        SQLiteDatabase sQLiteDatabase = new SQLiteDatabase(str, openParams.mOpenFlags, openParams.mCursorFactory, openParams.mErrorHandler, openParams.mLookasideSlotSize, openParams.mLookasideSlotCount, openParams.mIdleConnectionTimeout, openParams.mIdleConnectionShrinkTimeout, openParams.mJournalMode, openParams.mSyncMode, openParams.mCacheSize, openParams.mUserDataRecovery);
        sQLiteDatabase.setContext(context);
        sQLiteDatabase.open();
        return sQLiteDatabase;
    }

    public static SQLiteDatabase openDatabase(String str, CursorFactory cursorFactory, int i, DatabaseErrorHandler databaseErrorHandler) {
        SQLiteDatabase sQLiteDatabase = new SQLiteDatabase(str, i, cursorFactory, databaseErrorHandler, -1, -1, -1L, -1L, null, null, 0, false);
        sQLiteDatabase.open();
        return sQLiteDatabase;
    }

    public static SQLiteDatabase openOrCreateDatabase(File file, CursorFactory cursorFactory) {
        return openOrCreateDatabase(file.getPath(), cursorFactory);
    }

    public static SQLiteDatabase openOrCreateDatabase(String str, CursorFactory cursorFactory) {
        return openDatabase(str, cursorFactory, 268435456, null);
    }

    public static SQLiteDatabase openOrCreateDatabase(String str, CursorFactory cursorFactory, DatabaseErrorHandler databaseErrorHandler) {
        return openDatabase(str, cursorFactory, 268435456, databaseErrorHandler);
    }

    public static SQLiteDatabase openSecureDatabase(String str, OpenParams openParams, byte[] bArr, Context context) {
        Preconditions.checkArgument(openParams != null, "OpenParams cannot be null");
        if (bArr == null) {
            Log.e(TAG, "Could not open a secure database with null password.");
            throw new IllegalArgumentException("Could not open a secure database with null password.");
        }
        if (bArr.length == 0) {
            Log.e(TAG, "Could not open a secure database with empty password.");
            throw new IllegalArgumentException("Could not open a secure database with empty password.");
        }
        SQLiteDatabase sQLiteDatabase = new SQLiteDatabase(str, openParams.mOpenFlags | 768, openParams.mCursorFactory, openParams.mErrorHandler, openParams.mLookasideSlotSize, openParams.mLookasideSlotCount, openParams.mIdleConnectionTimeout, openParams.mIdleConnectionShrinkTimeout, openParams.mJournalMode, openParams.mSyncMode, openParams.mCacheSize, openParams.mUserDataRecovery);
        sQLiteDatabase.setContext(context);
        sQLiteDatabase.openSecureDatabase(bArr);
        return sQLiteDatabase;
    }

    public static SQLiteDatabase openSecureDatabase(File file, OpenParams openParams, byte[] bArr, Context context) {
        return openSecureDatabase(file.getPath(), openParams, bArr, context);
    }

    public static SQLiteDatabase openSecureDatabase(String str, CursorFactory cursorFactory, int i, DatabaseErrorHandler databaseErrorHandler, byte[] bArr) {
        if (bArr == null) {
            Log.e(TAG, "Could not open a secure database with null password.");
            throw new IllegalArgumentException("Could not open a secure database with null password.");
        }
        if (bArr.length == 0) {
            Log.e(TAG, "Could not open a secure database with empty password.");
            throw new IllegalArgumentException("Could not open a secure database with empty password.");
        }
        SQLiteDatabase sQLiteDatabase = new SQLiteDatabase(str, i | 768, cursorFactory, databaseErrorHandler, -1, -1, -1L, -1L, null, null, 0, false);
        sQLiteDatabase.openSecureDatabase(bArr);
        return sQLiteDatabase;
    }

    private static String convertByte2HexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        if (bArr != null) {
            for (int i = 0; i < bArr.length; i++) {
                byte b = bArr[i];
                if (b >= 0 && b < 16) {
                    sb.append("0");
                }
                sb.append(Integer.toHexString(bArr[i] & 255));
            }
        }
        return sb.toString();
    }

    public static void convertToPlainDatabase(File file, File file2, byte[] bArr) throws Exception {
        if (file == null || file2 == null || bArr == null) {
            Log.e(TAG, "convertToPlainDatabase() parameters should not be null.");
            throw new IllegalStateException("parameters should not be null.");
        }
        synchronized (mSecureLock) {
            Log.d(TAG, "convertToPlainDatabase() start...");
            if (file2.exists() && file2.length() > 0) {
                Log.e(TAG, "convertToPlainDatabase() Destination file exists, so delete it");
                deleteDatabase(file2);
            }
            SQLiteDatabase sQLiteDatabase = null;
            SQLiteDatabase openSecureDatabase = openSecureDatabase(file.getAbsolutePath(), null, 16, null, bArr);
            try {
                int version = openSecureDatabase.getVersion();
                try {
                    try {
                        sQLiteDatabase = openDatabase(file2.getAbsolutePath(), null, 268435472, null);
                        sQLiteDatabase.execSQL(String.format("attach database '%s' as secureDb key x'%s'", file.getAbsolutePath(), convertByte2HexString(bArr)));
                        sQLiteDatabase.exportDB("secureDb");
                        sQLiteDatabase.execSQL("detach database secureDb");
                        int version2 = sQLiteDatabase.getVersion();
                        if (version2 != version) {
                            Log.w(TAG, "Note: sourceDb version was changed during conversion (" + version + " -> " + version2 + NavigationBarInflaterView.KEY_CODE_END);
                        }
                        Log.d(TAG, "convertToPlainDatabase() finished");
                    } finally {
                        if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                            sQLiteDatabase.close();
                        }
                    }
                } catch (SQLiteException e) {
                    deleteDatabase(file2);
                    Log.e(TAG, "convertToPlainDatabase() failed", e);
                    throw e;
                }
            } finally {
                openSecureDatabase.close();
            }
        }
    }

    public static void convertToSecureDatabase(File file, File file2, byte[] bArr) throws Exception {
        if (file == null || file2 == null || bArr == null) {
            Log.e(TAG, "convertToSecureDatabase() parameters should not be null.");
            throw new IllegalStateException("parameters should not be null.");
        }
        synchronized (mSecureLock) {
            Log.d(TAG, "convertToSecureDatabase() start...");
            if (file2.exists() && file2.length() > 0) {
                Log.e(TAG, "convertToSecureDatabase() Destination file exists, so delete it");
                deleteDatabase(file2);
            }
            SQLiteDatabase sQLiteDatabase = null;
            SQLiteDatabase openDatabase = openDatabase(file.getAbsolutePath(), null, 16, null);
            try {
                int version = openDatabase.getVersion();
                try {
                    try {
                        sQLiteDatabase = openSecureDatabase(file2.getAbsolutePath(), null, 268435472, null, bArr);
                        sQLiteDatabase.execSQL(String.format("attach database '%s' as plainDb key ''", file.getAbsolutePath()));
                        sQLiteDatabase.exportDB("plainDb");
                        sQLiteDatabase.execSQL("detach database plainDb");
                        int version2 = sQLiteDatabase.getVersion();
                        if (version2 != version) {
                            Log.w(TAG, "Note: sourceDb version was changed during conversion (" + version + " -> " + version2 + NavigationBarInflaterView.KEY_CODE_END);
                        }
                    } finally {
                        if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                            sQLiteDatabase.close();
                        }
                    }
                } catch (SQLiteException e) {
                    deleteDatabase(file2);
                    Log.e(TAG, "convertToSecureDatabase() failed", e);
                    throw e;
                }
            } finally {
                openDatabase.close();
            }
        }
        Log.d(TAG, "convertToSecureDatabase() finished");
    }

    public int changeDBPassword(byte[] bArr) {
        if (!isOpen()) {
            Log.e(TAG, "changeDBPassword() DB is not open");
            throw new IllegalStateException("DB is not open");
        }
        if (bArr == null) {
            Log.e(TAG, "Could not use null password to a secure database.");
            throw new IllegalArgumentException("Could not use null password to a secure database.");
        }
        acquireReference();
        try {
            try {
                synchronized (mSecureLock) {
                    Log.d(TAG, "changeDBPassword() start...");
                    getThreadSession().changePassword(bArr);
                    Log.d(TAG, "changeDBPassword() finished");
                }
                releaseReference();
                return 0;
            } catch (SQLiteDatabaseCorruptException e) {
                onCorruption(e.getCorruptCode());
                throw e;
            }
        } catch (Throwable th) {
            releaseReference();
            throw th;
        }
    }

    public static boolean deleteDatabase(File file) {
        return deleteDatabase(file, true);
    }

    public static boolean deleteDatabase(File file, boolean z) {
        if (file == null) {
            throw new IllegalArgumentException("file must not be null");
        }
        boolean delete = file.delete() | new File(file.getPath() + "-journal").delete() | new File(file.getPath() + "-shm").delete() | new File(file.getPath() + "-wal").delete() | new File(file.getPath() + "-se").delete() | new File(file.getPath() + "-udr").delete();
        new File(file.getPath() + "-wipecheck").delete();
        File parentFile = file.getParentFile();
        if (parentFile != null) {
            final String str = file.getName() + "-mj";
            File[] listFiles = parentFile.listFiles(new FileFilter() { // from class: android.database.sqlite.SQLiteDatabase.1
                @Override // java.io.FileFilter
                public boolean accept(File file2) {
                    return file2.getName().startsWith(str);
                }
            });
            if (listFiles != null) {
                for (File file2 : listFiles) {
                    delete |= file2.delete();
                }
            }
        }
        return delete;
    }

    public void reopenReadWrite() {
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            if (isReadOnlyLocked()) {
                int i = this.mConfigurationLocked.openFlags;
                this.mConfigurationLocked.openFlags &= -2;
                try {
                    this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
                } catch (RuntimeException e) {
                    this.mConfigurationLocked.openFlags = i;
                    throw e;
                }
            }
        }
    }

    private void open() {
        try {
            try {
                openInner();
            } catch (RuntimeException e) {
                if (SQLiteDatabaseCorruptException.isCorruptException(e)) {
                    Log.e(TAG, "Database corruption detected in open()", e);
                    onCorruption(((SQLiteDatabaseCorruptException) e).getCorruptCode());
                    openInner();
                    return;
                }
                throw e;
            }
        } catch (SQLiteException e2) {
            Log.e(TAG, "Failed to open database '" + getLabel() + "'.", e2);
            close();
            throw e2;
        }
    }

    private void openSecureDatabase(byte[] bArr) {
        try {
            try {
                this.mPassword = bArr;
                openInnerSecureDatabase(bArr);
            } catch (RuntimeException e) {
                if (SQLiteDatabaseCorruptException.isCorruptException(e)) {
                    Log.e(TAG, "Database corruption detected in openSecureDatabase()", e);
                    onCorruption(((SQLiteDatabaseCorruptException) e).getCorruptCode());
                    openInnerSecureDatabase(bArr);
                    return;
                }
                throw e;
            } finally {
                this.mPassword = null;
            }
        } catch (SQLiteException e2) {
            Log.e(TAG, "Failed to open database '" + getLabel() + "'.", e2);
            close();
            throw e2;
        }
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

    private void openInnerSecureDatabase(byte[] bArr) {
        synchronized (sDbDirectories) {
            sDbDirectories.add(new File(this.mConfigurationLocked.path).getParent());
        }
        synchronized (this.mLock) {
            this.mConnectionPoolLocked = SQLiteConnectionPool.openSecure(this, this.mConfigurationLocked, bArr);
            this.mCloseGuardLocked.open("close");
        }
        synchronized (sActiveDatabases) {
            sActiveDatabases.put(this, null);
        }
    }

    private void exportDB(String str) {
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            this.mConnectionPoolLocked.exportDB(str);
        }
    }

    public static SQLiteDatabase create(CursorFactory cursorFactory) {
        return openDatabase(SQLiteDatabaseConfiguration.MEMORY_DB_PATH, cursorFactory, 268435456);
    }

    public static SQLiteDatabase createInMemory(OpenParams openParams) {
        return openDatabase(SQLiteDatabaseConfiguration.MEMORY_DB_PATH, openParams.toBuilder().addOpenFlags(268435456).build());
    }

    public static SQLiteDatabase createSecureDatabase(CursorFactory cursorFactory, byte[] bArr) {
        return openSecureDatabase(SQLiteDatabaseConfiguration.MEMORY_DB_PATH, cursorFactory, 268435456, null, bArr);
    }

    public void setCustomScalarFunction(String str, UnaryOperator<String> unaryOperator) throws SQLiteException {
        Objects.requireNonNull(str);
        Objects.requireNonNull(unaryOperator);
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            this.mConfigurationLocked.customScalarFunctions.put(str, unaryOperator);
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
            } catch (RuntimeException e) {
                this.mConfigurationLocked.customScalarFunctions.remove(str);
                throw e;
            }
        }
    }

    public void setCustomAggregateFunction(String str, BinaryOperator<String> binaryOperator) throws SQLiteException {
        Objects.requireNonNull(str);
        Objects.requireNonNull(binaryOperator);
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            this.mConfigurationLocked.customAggregateFunctions.put(str, binaryOperator);
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
            } catch (RuntimeException e) {
                this.mConfigurationLocked.customAggregateFunctions.remove(str);
                throw e;
            }
        }
    }

    public void execPerConnectionSQL(String str, Object[] objArr) throws SQLException {
        Objects.requireNonNull(str);
        Object[] deepCopyOf = DatabaseUtils.deepCopyOf(objArr);
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            int size = this.mConfigurationLocked.perConnectionSql.size();
            this.mConfigurationLocked.perConnectionSql.add(Pair.create(str, deepCopyOf));
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
            } catch (RuntimeException e) {
                this.mConfigurationLocked.perConnectionSql.remove(size);
                throw e;
            }
        }
    }

    public int getVersion() {
        return Long.valueOf(DatabaseUtils.longForQuery(this, "PRAGMA user_version;", null)).intValue();
    }

    public void setVersion(int i) {
        execSQL("PRAGMA user_version = " + i);
    }

    public long getMaximumSize() {
        return DatabaseUtils.longForQuery(this, "PRAGMA max_page_count;", null) * getPageSize();
    }

    public long setMaximumSize(long j) {
        long pageSize = getPageSize();
        long j2 = j / pageSize;
        if (j % pageSize != 0) {
            j2++;
        }
        return DatabaseUtils.longForQuery(this, "PRAGMA max_page_count = " + j2, null) * pageSize;
    }

    public long getPageSize() {
        return DatabaseUtils.longForQuery(this, "PRAGMA page_size;", null);
    }

    public void setPageSize(long j) {
        execSQL("PRAGMA page_size = " + j);
    }

    public static String findEditTable(String str) {
        if (!TextUtils.isEmpty(str)) {
            int indexOf = str.indexOf(32);
            int indexOf2 = str.indexOf(44);
            if (indexOf <= 0 || (indexOf >= indexOf2 && indexOf2 >= 0)) {
                return indexOf2 > 0 ? (indexOf2 < indexOf || indexOf < 0) ? str.substring(0, indexOf2) : str : str;
            }
            return str.substring(0, indexOf);
        }
        throw new IllegalStateException("Invalid tables");
    }

    public SQLiteStatement compileStatement(String str) throws SQLException {
        acquireReference();
        try {
            return new SQLiteStatement(this, str, null);
        } finally {
            releaseReference();
        }
    }

    public Cursor query(boolean z, String str, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5, String str6) {
        return queryWithFactory(null, z, str, strArr, str2, strArr2, str3, str4, str5, str6, null);
    }

    public Cursor query(boolean z, String str, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5, String str6, CancellationSignal cancellationSignal) {
        return queryWithFactory(null, z, str, strArr, str2, strArr2, str3, str4, str5, str6, cancellationSignal);
    }

    public Cursor queryWithFactory(CursorFactory cursorFactory, boolean z, String str, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5, String str6) {
        return queryWithFactory(cursorFactory, z, str, strArr, str2, strArr2, str3, str4, str5, str6, null);
    }

    public Cursor queryWithFactory(CursorFactory cursorFactory, boolean z, String str, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5, String str6, CancellationSignal cancellationSignal) {
        acquireReference();
        try {
            return rawQueryWithFactory(cursorFactory, SQLiteQueryBuilder.buildQueryString(z, str, strArr, str2, str3, str4, str5, str6), strArr2, findEditTable(str), cancellationSignal);
        } finally {
            releaseReference();
        }
    }

    public Cursor query(String str, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5) {
        return query(false, str, strArr, str2, strArr2, str3, str4, str5, null);
    }

    public Cursor query(String str, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5, String str6) {
        return query(false, str, strArr, str2, strArr2, str3, str4, str5, str6);
    }

    public Cursor rawQuery(String str, String[] strArr) {
        return rawQueryWithFactory(null, str, strArr, null, null);
    }

    public Cursor rawQuery(String str, String[] strArr, CancellationSignal cancellationSignal) {
        return rawQueryWithFactory(null, str, strArr, null, cancellationSignal);
    }

    public Cursor rawQueryWithFactory(CursorFactory cursorFactory, String str, String[] strArr, String str2) {
        return rawQueryWithFactory(cursorFactory, str, strArr, str2, null);
    }

    public Cursor rawQueryWithFactory(CursorFactory cursorFactory, String str, String[] strArr, String str2, CancellationSignal cancellationSignal) {
        acquireReference();
        try {
            SQLiteDirectCursorDriver sQLiteDirectCursorDriver = new SQLiteDirectCursorDriver(this, str, str2, cancellationSignal);
            if (cursorFactory == null) {
                cursorFactory = this.mCursorFactory;
            }
            return sQLiteDirectCursorDriver.query(cursorFactory, strArr);
        } finally {
            releaseReference();
        }
    }

    public long insert(String str, String str2, ContentValues contentValues) {
        try {
            return insertWithOnConflict(str, str2, contentValues, 0);
        } catch (SQLException e) {
            Log.e(TAG, "Error inserting " + contentValues, e);
            return -1L;
        }
    }

    public long insertOrThrow(String str, String str2, ContentValues contentValues) throws SQLException {
        return insertWithOnConflict(str, str2, contentValues, 0);
    }

    public long replace(String str, String str2, ContentValues contentValues) {
        try {
            return insertWithOnConflict(str, str2, contentValues, 5);
        } catch (SQLException e) {
            Log.e(TAG, "Error inserting " + contentValues, e);
            return -1L;
        }
    }

    public long replaceOrThrow(String str, String str2, ContentValues contentValues) throws SQLException {
        return insertWithOnConflict(str, str2, contentValues, 5);
    }

    public long insertWithOnConflict(String str, String str2, ContentValues contentValues, int i) {
        Object[] objArr;
        acquireReference();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT");
            sb.append(CONFLICT_VALUES[i]);
            sb.append(" INTO ");
            sb.append(str);
            sb.append('(');
            int i2 = 0;
            int size = (contentValues == null || contentValues.isEmpty()) ? 0 : contentValues.size();
            if (size > 0) {
                objArr = new Object[size];
                int i3 = 0;
                for (String str3 : contentValues.keySet()) {
                    sb.append(i3 > 0 ? "," : "");
                    sb.append(str3);
                    objArr[i3] = contentValues.get(str3);
                    i3++;
                }
                sb.append(')');
                sb.append(" VALUES (");
                while (i2 < size) {
                    sb.append(i2 > 0 ? ",?" : "?");
                    i2++;
                }
            } else {
                sb.append(str2);
                sb.append(") VALUES (NULL");
                objArr = null;
            }
            sb.append(')');
            SQLiteStatement sQLiteStatement = new SQLiteStatement(this, sb.toString(), objArr);
            try {
                return sQLiteStatement.executeInsert();
            } finally {
                sQLiteStatement.close();
            }
        } finally {
            releaseReference();
        }
    }

    public int delete(String str, String str2, String[] strArr) {
        String str3;
        acquireReference();
        try {
            StringBuilder sb = new StringBuilder("DELETE FROM ");
            sb.append(str);
            if (TextUtils.isEmpty(str2)) {
                str3 = "";
            } else {
                str3 = " WHERE " + str2;
            }
            sb.append(str3);
            SQLiteStatement sQLiteStatement = new SQLiteStatement(this, sb.toString(), strArr);
            try {
                return sQLiteStatement.executeUpdateDelete();
            } finally {
                sQLiteStatement.close();
            }
        } finally {
            releaseReference();
        }
    }

    public int update(String str, ContentValues contentValues, String str2, String[] strArr) {
        return updateWithOnConflict(str, contentValues, str2, strArr, 0);
    }

    public int updateWithOnConflict(String str, ContentValues contentValues, String str2, String[] strArr, int i) {
        if (contentValues == null || contentValues.isEmpty()) {
            throw new IllegalArgumentException("Empty values");
        }
        acquireReference();
        try {
            StringBuilder sb = new StringBuilder(120);
            sb.append("UPDATE ");
            sb.append(CONFLICT_VALUES[i]);
            sb.append(str);
            sb.append(" SET ");
            int size = contentValues.size();
            int length = strArr == null ? size : strArr.length + size;
            Object[] objArr = new Object[length];
            int i2 = 0;
            for (String str3 : contentValues.keySet()) {
                sb.append(i2 > 0 ? "," : "");
                sb.append(str3);
                objArr[i2] = contentValues.get(str3);
                sb.append("=?");
                i2++;
            }
            if (strArr != null) {
                for (int i3 = size; i3 < length; i3++) {
                    objArr[i3] = strArr[i3 - size];
                }
            }
            if (!TextUtils.isEmpty(str2)) {
                sb.append(" WHERE ");
                sb.append(str2);
            }
            SQLiteStatement sQLiteStatement = new SQLiteStatement(this, sb.toString(), objArr);
            try {
                return sQLiteStatement.executeUpdateDelete();
            } finally {
                sQLiteStatement.close();
            }
        } finally {
            releaseReference();
        }
    }

    public void execSQL(String str) throws SQLException {
        executeSql(str, null);
    }

    public void execSQL(String str, Object[] objArr) throws SQLException {
        if (objArr == null) {
            throw new IllegalArgumentException("Empty bindArgs");
        }
        executeSql(str, objArr);
    }

    public int executeSql(String str, Object[] objArr) throws SQLException {
        boolean z;
        acquireReference();
        try {
            int sqlStatementType = DatabaseUtils.getSqlStatementType(str);
            if (sqlStatementType == 3) {
                synchronized (this.mLock) {
                    if (this.mHasAttachedDbsLocked) {
                        z = false;
                    } else {
                        z = true;
                        this.mHasAttachedDbsLocked = true;
                        this.mConnectionPoolLocked.disableIdleConnectionHandler();
                    }
                }
                if (z) {
                    disableWriteAheadLogging();
                }
            }
            try {
                SQLiteStatement sQLiteStatement = new SQLiteStatement(this, str, objArr);
                try {
                    int executeUpdateDelete = sQLiteStatement.executeUpdateDelete();
                    sQLiteStatement.close();
                    return executeUpdateDelete;
                } finally {
                }
            } finally {
                if (sqlStatementType == 8) {
                    this.mConnectionPoolLocked.closeAvailableNonPrimaryConnectionsAndLogExceptions();
                    this.mConnectionPoolLocked.clearAcquiredConnectionsPreparedStatementCache();
                }
            }
        } finally {
            releaseReference();
        }
    }

    public SQLiteRawStatement createRawStatement(String str) {
        Objects.requireNonNull(str);
        return new SQLiteRawStatement(this, str);
    }

    public long getLastInsertRowId() {
        return getThreadSession().getLastInsertRowId();
    }

    public long getLastChangedRowCount() {
        return getThreadSession().getLastChangedRowCount();
    }

    public long getTotalChangedRowCount() {
        return getThreadSession().getTotalChangedRowCount();
    }

    public void validateSql(String str, CancellationSignal cancellationSignal) {
        getThreadSession().prepare(str, getThreadDefaultConnectionFlags(true), cancellationSignal, null);
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

    public boolean needUpgrade(int i) {
        return i > getVersion();
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
            Locale locale2 = this.mConfigurationLocked.locale;
            this.mConfigurationLocked.locale = locale;
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
            } catch (RuntimeException e) {
                this.mConfigurationLocked.locale = locale2;
                throw e;
            }
        }
    }

    public void setMaxSqlCacheSize(int i) {
        if (i > 100 || i < 0) {
            throw new IllegalStateException("expected value between 0 and 100");
        }
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            int i2 = this.mConfigurationLocked.maxSqlCacheSize;
            this.mConfigurationLocked.maxSqlCacheSize = i;
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
            } catch (RuntimeException e) {
                this.mConfigurationLocked.maxSqlCacheSize = i2;
                throw e;
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

    public void setForeignKeyConstraintsEnabled(boolean z) {
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            if (this.mConfigurationLocked.foreignKeyConstraintsEnabled == z) {
                return;
            }
            this.mConfigurationLocked.foreignKeyConstraintsEnabled = z;
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
            } catch (RuntimeException e) {
                this.mConfigurationLocked.foreignKeyConstraintsEnabled = !z;
                throw e;
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
                Log.i(TAG, "can't enable WAL for memory databases.");
                return false;
            }
            if (this.mHasAttachedDbsLocked) {
                if (Log.isLoggable(TAG, 3)) {
                    Log.d(TAG, "this database: " + this.mConfigurationLocked.label + " has attached databases. can't  enable WAL.");
                }
                return false;
            }
            this.mConfigurationLocked.openFlags |= 536870912;
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
                this.mBackgroundCheckpoint = new SQLiteWalBackgroundCheckpoint();
                return true;
            } catch (RuntimeException e) {
                this.mConfigurationLocked.openFlags &= -536870913;
                throw e;
            }
        }
    }

    public void disableWriteAheadLogging() {
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            int i = this.mConfigurationLocked.openFlags;
            if (this.mConfigurationLocked.resolveJournalMode().equalsIgnoreCase(JOURNAL_MODE_WAL)) {
                this.mConfigurationLocked.openFlags &= -536870913;
                this.mConfigurationLocked.openFlags &= Integer.MAX_VALUE;
                try {
                    this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
                    this.mBackgroundCheckpoint = null;
                } catch (RuntimeException e) {
                    this.mConfigurationLocked.openFlags = i;
                    throw e;
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

    public static int semBackupDatabaseFile(String str, String str2) {
        if (str == null || str2 == null) {
            Log.e(TAG, "path should not be null.");
            return -1;
        }
        return SQLiteConnection.nativeBackupDatabaseFile(str, str2);
    }

    @Deprecated
    public static int semBackupSecureDatabaseFile(String str, String str2, byte[] bArr) {
        int nativeBackupSecureDatabaseFile;
        if (str == null || str2 == null || bArr == null) {
            Log.e(TAG, "path and password should not be null.");
            return -1;
        }
        synchronized (mSecureLock) {
            nativeBackupSecureDatabaseFile = SQLiteConnection.nativeBackupSecureDatabaseFile(str, str2, bArr);
        }
        return nativeBackupSecureDatabaseFile;
    }

    public static int semRestoreDatabaseFile(String str, String str2) {
        if (str == null || str2 == null) {
            Log.e(TAG, "path should not be null.");
            return -1;
        }
        return SQLiteConnection.nativeRestoreDatabaseFile(str, str2);
    }

    @Deprecated
    public static int semRestoreSecureDatabaseFile(String str, String str2, byte[] bArr) {
        int nativeRestoreSecureDatabaseFile;
        if (str == null || str2 == null || bArr == null) {
            Log.e(TAG, "path and password should not be null.");
            return -1;
        }
        synchronized (mSecureLock) {
            nativeRestoreSecureDatabaseFile = SQLiteConnection.nativeRestoreSecureDatabaseFile(str, str2, bArr);
        }
        return nativeRestoreSecureDatabaseFile;
    }

    public static int cleanDatabaseFile(String str) {
        return SQLiteConnection.nativeCleanDatabaseFile(str);
    }

    public int getCorruptCode() {
        return this.mCorruptCode;
    }

    void onCorruption(int i) {
        int i2;
        int i3;
        boolean z;
        synchronized (this.mLock) {
            i2 = 0;
            if (this.mInCorruptionHandling) {
                z = true;
                i3 = 0;
            } else {
                this.mInCorruptionHandling = true;
                this.mDbDump.prepareDumpFile();
                SQLiteDump sQLiteDump = this.mDbDump;
                sQLiteDump.addDumpLog(TAG, sQLiteDump.getSQLiteDumpLogs(true));
                if (isOpen()) {
                    this.mConnectionPoolLocked.dumpAllConnections(this.mDbDump);
                    i3 = getMaxConnectionPoolSize();
                    closeAndDiscardNonPrimaryConnections(true, false);
                    z = false;
                } else {
                    i3 = 0;
                    z = false;
                }
            }
        }
        if (z) {
            Log.d(TAG, "Database corruption is already handling, wait.");
            do {
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!this.mInCorruptionHandling) {
                    break;
                } else {
                    i2++;
                }
            } while (i2 < 20);
            Log.d(TAG, "Exit onCorruption.");
            return;
        }
        try {
            this.mCorruptCode = i;
            this.mDefaultErrorHandler.onCorruption(this);
            EventLog.writeEvent(EVENT_DB_CORRUPT, getLabel());
            DatabaseErrorHandler databaseErrorHandler = this.mErrorHandler;
            if (databaseErrorHandler != null) {
                databaseErrorHandler.onCorruption(this);
            }
            synchronized (this.mLock) {
                if (i3 > 0) {
                    if (isOpen()) {
                        this.mConnectionPoolLocked.setMaxConnectionPoolSize(i3);
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
                if (i3 > 0) {
                    if (isOpen()) {
                        this.mConnectionPoolLocked.setMaxConnectionPoolSize(i3);
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
        } catch (SQLiteException unused) {
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
        ArrayList<SQLiteDebug.DbStats> arrayList = new ArrayList<>();
        Iterator<SQLiteDatabase> it = getActiveDatabases().iterator();
        while (it.hasNext()) {
            it.next().collectDbStats(arrayList);
        }
        return arrayList;
    }

    private void collectDbStats(ArrayList<SQLiteDebug.DbStats> arrayList) {
        synchronized (this.mLock) {
            SQLiteConnectionPool sQLiteConnectionPool = this.mConnectionPoolLocked;
            if (sQLiteConnectionPool != null) {
                sQLiteConnectionPool.collectDbStats(arrayList);
            }
        }
    }

    private static ArrayList<SQLiteDatabase> getActiveDatabases() {
        ArrayList<SQLiteDatabase> arrayList = new ArrayList<>();
        synchronized (sActiveDatabases) {
            arrayList.addAll(sActiveDatabases.keySet());
        }
        return arrayList;
    }

    private static ArrayList<SQLiteConnectionPool> getActiveDatabasePools() {
        ArrayList<SQLiteConnectionPool> arrayList = new ArrayList<>();
        synchronized (sActiveDatabases) {
            for (SQLiteDatabase sQLiteDatabase : sActiveDatabases.keySet()) {
                synchronized (sQLiteDatabase.mLock) {
                    SQLiteConnectionPool sQLiteConnectionPool = sQLiteDatabase.mConnectionPoolLocked;
                    if (sQLiteConnectionPool != null) {
                        arrayList.add(sQLiteConnectionPool);
                    }
                }
            }
        }
        return arrayList;
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

    private static void printCorruptionDumpFiles(Printer printer, String str) {
        File[] listFiles;
        if (new File(str).exists() && (listFiles = new File(str).listFiles()) != null) {
            for (File file : listFiles) {
                if (file.isFile()) {
                    try {
                        BufferedReader newBufferedReader = Files.newBufferedReader(Paths.get(file.getAbsolutePath(), new String[0]));
                        try {
                            printer.println("");
                            while (true) {
                                String readLine = newBufferedReader.readLine();
                                if (readLine == null) {
                                    break;
                                } else {
                                    printer.println(readLine);
                                }
                            }
                            printer.println("");
                            if (newBufferedReader != null) {
                                newBufferedReader.close();
                            }
                        } catch (Throwable th) {
                            if (newBufferedReader != null) {
                                try {
                                    newBufferedReader.close();
                                } catch (Throwable th2) {
                                    th.addSuppressed(th2);
                                }
                            }
                            throw th;
                        }
                    } catch (Exception unused) {
                        continue;
                    }
                }
            }
        }
    }

    static void dumpAll(Printer printer, boolean z, boolean z2) {
        ArraySet<String> arraySet = new ArraySet<>();
        ArrayList<SQLiteConnectionPool> activeDatabasePools = getActiveDatabasePools();
        activeDatabasePools.sort(new Comparator() { // from class: android.database.sqlite.SQLiteDatabase$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int compare;
                compare = Long.compare(((SQLiteConnectionPool) obj2).getTotalStatementsCount(), ((SQLiteConnectionPool) obj).getTotalStatementsCount());
                return compare;
            }
        });
        Iterator<SQLiteConnectionPool> it = activeDatabasePools.iterator();
        long j = 0;
        long j2 = 0;
        while (it.hasNext()) {
            SQLiteConnectionPool next = it.next();
            SQLiteDatabase database = next.getDatabase();
            if (database != null) {
                printer.println("  Attached db: " + database.hasAttachedDbsLocked());
                next.dump(printer, z, arraySet);
                j2 += next.getTotalStatementsTime();
                j += next.getTotalStatementsCount();
            }
        }
        if (j > 0) {
            printer.println("Statements Executed per Database");
            Iterator<SQLiteConnectionPool> it2 = activeDatabasePools.iterator();
            while (it2.hasNext()) {
                SQLiteConnectionPool next2 = it2.next();
                printer.println("  " + next2.getPath() + " :    " + next2.getTotalStatementsCount());
            }
            printer.println("");
            printer.println("Total Statements Executed for all Active Databases: " + j);
            activeDatabasePools.sort(new Comparator() { // from class: android.database.sqlite.SQLiteDatabase$$ExternalSyntheticLambda1
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
            Iterator<SQLiteConnectionPool> it3 = activeDatabasePools.iterator();
            while (it3.hasNext()) {
                SQLiteConnectionPool next3 = it3.next();
                printer.println("  " + next3.getPath() + " :    " + next3.getTotalStatementsTime());
            }
            printer.println("Total Statements Time for all Active Databases (ms): " + j2);
        }
        if (arraySet.size() > 0) {
            String[] strArr = (String[]) arraySet.toArray(new String[arraySet.size()]);
            Arrays.sort(strArr);
            for (String str : strArr) {
                dumpDatabaseDirectory(printer, new File(str), z2);
            }
        }
        ArrayList<String> dbDirectories = getDbDirectories();
        if (dbDirectories.size() > 0) {
            Iterator<String> it4 = dbDirectories.iterator();
            while (it4.hasNext()) {
                try {
                    printCorruptionDumpFiles(printer, new File(it4.next(), SQLiteDump.DB_INFO_DUMP_DIR_NAME).getAbsolutePath());
                } catch (Exception unused) {
                }
            }
        }
    }

    private static ArrayList<String> getDbDirectories() {
        ArrayList<String> arrayList;
        synchronized (sDbDirectories) {
            arrayList = new ArrayList<>(sDbDirectories);
        }
        return arrayList;
    }

    private static void dumpDatabaseDirectory(Printer printer, File file, boolean z) {
        int i;
        printer.println("");
        printer.println("Database files in " + file.getAbsolutePath() + ":");
        File[] listFiles = file.listFiles();
        if (listFiles == null || listFiles.length == 0) {
            printer.println("  [none]");
            return;
        }
        Arrays.sort(listFiles, new Comparator() { // from class: android.database.sqlite.SQLiteDatabase$$ExternalSyntheticLambda2
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int compareTo;
                compareTo = ((File) obj).getName().compareTo(((File) obj2).getName());
                return compareTo;
            }
        });
        int length = listFiles.length;
        while (i < length) {
            File file2 = listFiles[i];
            if (z) {
                String name = file2.getName();
                i = (name.endsWith(".db") || name.endsWith(".db-wal") || name.endsWith(".db-journal") || name.endsWith("-wipecheck")) ? 0 : i + 1;
            }
            printer.println(String.format("  %-40s %7db %s%s", file2.getName(), Long.valueOf(file2.length()), getFileTimestamps(file2.getAbsolutePath()), SQLiteUserDataRecovery.isDbUdrRecovered(file2.getAbsolutePath()) ? " (R)" : ""));
        }
    }

    public List<Pair<String, String>> getAttachedDbs() {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mLock) {
            Cursor cursor = null;
            if (this.mConnectionPoolLocked == null) {
                return null;
            }
            if (!this.mHasAttachedDbsLocked) {
                arrayList.add(new Pair("main", this.mConfigurationLocked.path));
                return arrayList;
            }
            acquireReference();
            try {
                try {
                    cursor = rawQuery("pragma database_list;", null);
                    while (cursor.moveToNext()) {
                        arrayList.add(new Pair(cursor.getString(1), cursor.getString(2)));
                    }
                    return arrayList;
                } finally {
                    if (cursor != null) {
                        cursor.close();
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

    /* JADX WARN: Removed duplicated region for block: B:34:0x0100 A[Catch: all -> 0x0045, TRY_ENTER, TRY_LEAVE, TryCatch #3 {all -> 0x0045, blocks: (B:3:0x0009, B:7:0x005e, B:9:0x0064, B:21:0x00e3, B:27:0x00ec, B:40:0x0109, B:41:0x010c, B:34:0x0100, B:51:0x0010, B:52:0x0044, B:54:0x0048), top: B:2:0x0009, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0109 A[Catch: all -> 0x0045, TRY_ENTER, TryCatch #3 {all -> 0x0045, blocks: (B:3:0x0009, B:7:0x005e, B:9:0x0064, B:21:0x00e3, B:27:0x00ec, B:40:0x0109, B:41:0x010c, B:34:0x0100, B:51:0x0010, B:52:0x0044, B:54:0x0048), top: B:2:0x0009, inners: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean isDatabaseIntegrityOk(boolean r9) {
        /*
            Method dump skipped, instructions count: 278
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.database.sqlite.SQLiteDatabase.isDatabaseIntegrityOk(boolean):boolean");
    }

    public String getIntegrityErrorInfo() {
        return this.mIntegrityErrorString;
    }

    public void closeAndDiscardNonPrimaryConnections(boolean z, boolean z2) {
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            this.mConnectionPoolLocked.closeAndDiscardNonPrimaryConnections(z, z2);
        }
    }

    public void reOpen() {
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            this.mConnectionPoolLocked.reOpen();
        }
    }

    public void setCheckpointOnClose(boolean z) {
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            this.mConnectionPoolLocked.setCheckpointOnClose(z);
        }
    }

    public boolean diagnoseError() {
        try {
            cleanCacheAndReOpen();
            if (isDatabaseIntegrityOk(getCorruptCode() == 1035)) {
                this.mDbDump.logAndDump(TAG, "!@ Integrity Check for corrupted DB file gets OK as result");
                return true;
            }
        } catch (SQLiteException unused) {
        }
        return false;
    }

    private void cleanCacheAndReOpen() {
        if (inTransaction()) {
            endTransaction();
        }
        execSQL("PRAGMA drop_db_fs_cache;");
        List<Pair<String, String>> attachedDbs = getAttachedDbs();
        if (attachedDbs == null || attachedDbs.size() > 1) {
            return;
        }
        setCheckpointOnClose(false);
        reOpen();
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
        if (this.mConnectionPoolLocked != null) {
            return;
        }
        throw new IllegalStateException("The database '" + this.mConfigurationLocked.label + "' is not open.");
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

        private OpenParams(int i, CursorFactory cursorFactory, DatabaseErrorHandler databaseErrorHandler, int i2, int i3, long j, long j2, String str, String str2, int i4, boolean z) {
            this.mCursorFactory = cursorFactory;
            this.mErrorHandler = databaseErrorHandler;
            this.mLookasideSlotSize = i2;
            this.mLookasideSlotCount = i3;
            this.mIdleConnectionTimeout = j;
            this.mIdleConnectionShrinkTimeout = j2;
            this.mJournalMode = str;
            this.mSyncMode = str2;
            this.mCacheSize = i4;
            this.mUserDataRecovery = z;
            if ((536870912 & i) != 0) {
                this.mOpenFlags = i & (-1025);
            } else if (str != null && !str.equalsIgnoreCase(SQLiteDatabase.JOURNAL_MODE_WAL)) {
                this.mOpenFlags = i | 1024;
            } else {
                this.mOpenFlags = i;
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

            public Builder(OpenParams openParams) {
                this.mLookasideSlotSize = -1;
                this.mLookasideSlotCount = -1;
                this.mIdleConnectionTimeout = -1L;
                this.mIdleConnectionShrinkTimeout = -1L;
                this.mLookasideSlotSize = openParams.mLookasideSlotSize;
                this.mLookasideSlotCount = openParams.mLookasideSlotCount;
                this.mIdleConnectionTimeout = openParams.mIdleConnectionTimeout;
                this.mIdleConnectionShrinkTimeout = openParams.mIdleConnectionShrinkTimeout;
                this.mOpenFlags = openParams.mOpenFlags;
                this.mCursorFactory = openParams.mCursorFactory;
                this.mErrorHandler = openParams.mErrorHandler;
                this.mJournalMode = openParams.mJournalMode;
                this.mSyncMode = openParams.mSyncMode;
                this.mCacheSize = openParams.mCacheSize;
                this.mUserDataRecovery = openParams.mUserDataRecovery;
            }

            public Builder setLookasideConfig(int i, int i2) {
                boolean z = true;
                Preconditions.checkArgument(i >= 0, "lookasideSlotCount cannot be negative");
                Preconditions.checkArgument(i2 >= 0, "lookasideSlotSize cannot be negative");
                if ((i <= 0 || i2 <= 0) && (i2 != 0 || i != 0)) {
                    z = false;
                }
                Preconditions.checkArgument(z, "Invalid configuration: %d, %d", Integer.valueOf(i), Integer.valueOf(i2));
                this.mLookasideSlotSize = i;
                this.mLookasideSlotCount = i2;
                return this;
            }

            public boolean isWriteAheadLoggingEnabled() {
                return (this.mOpenFlags & 536870912) != 0;
            }

            public Builder setOpenFlags(int i) {
                this.mOpenFlags = i;
                return this;
            }

            public Builder addOpenFlags(int i) {
                this.mOpenFlags = i | this.mOpenFlags;
                return this;
            }

            public Builder removeOpenFlags(int i) {
                int i2 = this.mOpenFlags & (~i);
                this.mOpenFlags = i2;
                if ((i & 536870912) != 0) {
                    this.mOpenFlags = i2 | 1024;
                }
                return this;
            }

            public void setWriteAheadLoggingEnabled(boolean z) {
                if (z) {
                    addOpenFlags(536870912);
                } else {
                    removeOpenFlags(536870912);
                }
            }

            public Builder setCursorFactory(CursorFactory cursorFactory) {
                this.mCursorFactory = cursorFactory;
                return this;
            }

            public Builder setErrorHandler(DatabaseErrorHandler databaseErrorHandler) {
                this.mErrorHandler = databaseErrorHandler;
                return this;
            }

            @Deprecated
            public Builder setIdleConnectionTimeout(long j) {
                Preconditions.checkArgument(j >= 0, "idle connection timeout cannot be negative");
                this.mIdleConnectionTimeout = j;
                return this;
            }

            public Builder setJournalMode(String str) {
                Objects.requireNonNull(str);
                this.mJournalMode = str;
                return this;
            }

            public Builder setSynchronousMode(String str) {
                Objects.requireNonNull(str);
                this.mSyncMode = str;
                return this;
            }

            @Deprecated
            public Builder semSetCacheSize(int i) {
                if (i < 0 || i > 8388608) {
                    throw new IllegalArgumentException("The cache size should not be negative value. Also, it should be less than soft heap size (8M). Now: " + i);
                }
                this.mCacheSize = i / SQLiteGlobal.getDefaultPageSize();
                return this;
            }

            public Builder semSetSeparateCacheModeEnabled(boolean z) {
                if (z) {
                    addOpenFlags(4096);
                    return this;
                }
                removeOpenFlags(4096);
                return this;
            }

            public Builder semSetIdleConnectionShrinkTimeout(long j) {
                Preconditions.checkArgument(j >= 0, "idle connection shrink timeout cannot be negative");
                this.mIdleConnectionShrinkTimeout = j;
                return this;
            }

            public Builder setUserDataRecoveryEnabled(boolean z) {
                this.mUserDataRecovery = z;
                return this;
            }

            public OpenParams build() {
                return new OpenParams(this.mOpenFlags, this.mCursorFactory, this.mErrorHandler, this.mLookasideSlotSize, this.mLookasideSlotCount, this.mIdleConnectionTimeout, this.mIdleConnectionShrinkTimeout, this.mJournalMode, this.mSyncMode, this.mCacheSize, this.mUserDataRecovery);
            }
        }
    }

    public static void wipeDetected(String str, String str2) {
        StringBuilder sb = new StringBuilder("DB wipe detected: package=");
        sb.append(ActivityThread.currentPackageName());
        sb.append(" reason=");
        sb.append(str2);
        sb.append(" file=");
        sb.append(str);
        sb.append(" ");
        sb.append(getFileTimestamps(str));
        sb.append(" checkfile ");
        sb.append(getFileTimestamps(str + "-wipecheck"));
        wtfAsSystemServer(TAG, sb.toString(), new Throwable("STACKTRACE"));
    }

    public static String getFileTimestamps(String str) {
        try {
            BasicFileAttributes readAttributes = Files.readAttributes(FileSystems.getDefault().getPath(str, new String[0]), (Class<BasicFileAttributes>) BasicFileAttributes.class, new LinkOption[0]);
            return "ctime=" + readAttributes.creationTime() + " mtime=" + readAttributes.lastModifiedTime() + " atime=" + readAttributes.lastAccessTime();
        } catch (IOException unused) {
            return "[unable to obtain timestamp]";
        }
    }

    static void wtfAsSystemServer(String str, String str2, Throwable th) {
        Log.e(str, str2, th);
        ContentResolver.onDbCorruption(str, str2, th);
    }

    public static void renameDatabaseFile(String str, String str2) {
        new File(str).renameTo(new File(str2));
        File file = new File(str + "-journal");
        if (file.exists()) {
            file.renameTo(new File(str2 + "-journal"));
        }
        File file2 = new File(str + "-wal");
        if (file2.exists()) {
            file2.renameTo(new File(str2 + "-wal"));
        }
        File file3 = new File(str + "-shm");
        if (file3.exists()) {
            file3.renameTo(new File(str2 + "-shm"));
        }
        File file4 = new File(str + "-se");
        if (file4.exists()) {
            file4.renameTo(new File(str2 + "-se"));
        }
        File file5 = new File(str + "-udr");
        if (file5.exists()) {
            file5.renameTo(new File(str2 + "-udr"));
        }
        new File(str + "-wipecheck").delete();
    }

    public static boolean deleteDatabaseFile(String str) {
        boolean delete = new File(str).delete();
        if (delete) {
            new File(str + "-journal").delete();
            new File(str + "-wal").delete();
            new File(str + "-shm").delete();
            new File(str + "-se").delete();
            new File(str + "-udr").delete();
            new File(str + "-wipecheck").delete();
        }
        return delete;
    }

    public void setAutomaticIndexEnabled(boolean z) {
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            if (this.mConfigurationLocked.automaticIndexEnabled == z) {
                return;
            }
            this.mConfigurationLocked.automaticIndexEnabled = z;
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
            } catch (RuntimeException e) {
                this.mConfigurationLocked.automaticIndexEnabled = !z;
                throw e;
            }
        }
    }

    public void setCaseSensitiveLikeEnabled(boolean z) {
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            if (this.mConfigurationLocked.caseSensitiveLikeEnabled == z) {
                return;
            }
            this.mConfigurationLocked.caseSensitiveLikeEnabled = z;
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
            } catch (RuntimeException e) {
                this.mConfigurationLocked.caseSensitiveLikeEnabled = !z;
                throw e;
            }
        }
    }

    public void setBusyTimeout(long j) {
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            if (this.mConfigurationLocked.busyTimeout == j) {
                return;
            }
            long j2 = this.mConfigurationLocked.busyTimeout;
            this.mConfigurationLocked.busyTimeout = j;
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
            } catch (RuntimeException e) {
                this.mConfigurationLocked.busyTimeout = j2;
                throw e;
            }
        }
    }

    public void setCacheSize(int i) {
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            if (this.mConfigurationLocked.cacheSize == i) {
                return;
            }
            int i2 = this.mConfigurationLocked.cacheSize;
            this.mConfigurationLocked.cacheSize = i;
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
            } catch (RuntimeException e) {
                this.mConfigurationLocked.cacheSize = i2;
                throw e;
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
        } catch (Exception unused) {
        }
    }

    public int getMaxConnectionPoolSize() {
        int maxConnectionPoolSize;
        synchronized (this.mLock) {
            maxConnectionPoolSize = this.mConnectionPoolLocked.getMaxConnectionPoolSize();
        }
        return maxConnectionPoolSize;
    }

    public void setContext(Context context) {
        if (context == null) {
            return;
        }
        try {
            this.mContext = context.getApplicationContext();
        } catch (Exception unused) {
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
                    if (DatabaseUtils.longForQuery(this, "PRAGMA wal_reserve_space=1", null) < 0) {
                        Log.d(TAG, "Failed to set wal reserve size");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean doRecovery() {
        boolean z = this.mConfigurationLocked.sharedConfig.isSecureDb;
        boolean z2 = (this.mConfigurationLocked.openFlags & 16) != 0;
        SQLiteUserDataRecovery sQLiteUserDataRecovery = this.udr;
        if (sQLiteUserDataRecovery != null) {
            return sQLiteUserDataRecovery.doRecovery(getPath(), z ? getConnectionKey() : null, z2 ? null : this.mConfigurationLocked.locale.toString());
        }
        return false;
    }
}
