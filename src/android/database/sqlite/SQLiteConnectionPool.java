package android.database.sqlite;

import android.database.sqlite.SQLiteDebug;
import android.database.sqlite.SQLiteDump;
import android.hardware.scontext.SContextConstants;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.OperationCanceledException;
import android.os.SystemClock;
import android.telecom.ParcelableCallAnalytics;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.util.PrefixPrinter;
import android.util.Printer;
import com.android.internal.os.BackgroundThread;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import dalvik.annotation.optimization.NeverCompile;
import dalvik.system.CloseGuard;
import java.io.Closeable;
import java.io.File;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.LockSupport;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes.dex */
public final class SQLiteConnectionPool implements Closeable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int CONNECTION_FLAG_INTERACTIVE = 4;
    public static final int CONNECTION_FLAG_PRIMARY_CONNECTION_AFFINITY = 2;
    public static final int CONNECTION_FLAG_READ_ONLY = 1;
    private static final long CONNECTION_POOL_BUSY_MILLIS = 4000;
    private static final String TAG = "SQLiteConnectionPool";
    private SQLiteConnection mAvailablePrimaryConnection;
    private Throwable mClosedBy;
    private final SQLiteDatabaseConfiguration mConfiguration;
    private SecureData mConnectionKey;
    private ConnectionWaiter mConnectionWaiterPool;
    private ConnectionWaiter mConnectionWaiterQueue;
    private SQLiteDatabase mDatabase;
    private IdleConnectionHandler mIdleConnectionHandler;
    private boolean mIsOpen;
    private boolean mIsPoolSizeFixed;
    private int mMaxConnectionPoolSize;
    private int mNextConnectionId;
    private long mRandArrPtr;
    private final CloseGuard mCloseGuard = CloseGuard.get();
    private final Object mLock = new Object();
    private final AtomicBoolean mConnectionLeaked = new AtomicBoolean();
    private final ArrayList<SQLiteConnection> mAvailableNonPrimaryConnections = new ArrayList<>();
    public int mTotalPrepareStatementCacheMiss = 0;
    public int mTotalPrepareStatements = 0;
    private long mDatabaseSeqNum = 1;
    private final AtomicLong mTotalStatementsTime = new AtomicLong(0);
    private final AtomicLong mTotalStatementsCount = new AtomicLong(0);
    private final WeakHashMap<SQLiteConnection, AcquiredConnectionStatus> mAcquiredConnections = new WeakHashMap<>();
    private long mDbSizeCheckTime = 0;

    enum AcquiredConnectionStatus {
        NORMAL,
        RECONFIGURE,
        DISCARD,
        SHRINK
    }

    private static int getPriority(int i) {
        return (i & 4) != 0 ? 1 : 0;
    }

    private SQLiteConnectionPool(SQLiteDatabase sQLiteDatabase, SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration) {
        this.mDatabase = sQLiteDatabase;
        SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration2 = new SQLiteDatabaseConfiguration(sQLiteDatabaseConfiguration);
        this.mConfiguration = sQLiteDatabaseConfiguration2;
        setMaxConnectionPoolSizeLocked();
        if (sQLiteDatabaseConfiguration2.idleConnectionTimeoutMs != Long.MAX_VALUE) {
            setupIdleConnectionHandler(BackgroundThread.getHandler().getLooper(), sQLiteDatabaseConfiguration2.idleConnectionTimeoutMs, null);
        } else if (sQLiteDatabaseConfiguration2.idleConnectionShrinkTimeoutMs != Long.MAX_VALUE) {
            setupIdleConnectionShrinkHandler(BackgroundThread.getHandler().getLooper(), sQLiteDatabaseConfiguration2.idleConnectionShrinkTimeoutMs, null);
        }
        if (sQLiteDatabaseConfiguration2.sharedConfig.isSecureDb) {
            try {
                long createRandArray = SQLiteGlobal.createRandArray();
                this.mRandArrPtr = createRandArray;
                if (createRandArray != 0) {
                    this.mConnectionKey = new SecureData(this, sQLiteDatabaseConfiguration2);
                }
            } catch (Exception e) {
                Log.e(TAG, "Could not generate SQLiteConnectionPool");
                if (SQLiteDebug.NoPreloadHolder.DEBUG_ENABLE) {
                    throw e;
                }
            }
        }
    }

    protected void finalize() throws Throwable {
        try {
            dispose(true);
        } finally {
            super.finalize();
        }
    }

    public static SQLiteConnectionPool open(SQLiteDatabase sQLiteDatabase, SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration) {
        if (sQLiteDatabase == null) {
            throw new IllegalArgumentException("database must not be null.");
        }
        if (sQLiteDatabaseConfiguration == null) {
            throw new IllegalArgumentException("configuration must not be null.");
        }
        SQLiteConnectionPool sQLiteConnectionPool = new SQLiteConnectionPool(sQLiteDatabase, sQLiteDatabaseConfiguration);
        sQLiteConnectionPool.open();
        return sQLiteConnectionPool;
    }

    public static SQLiteConnectionPool openSecure(SQLiteDatabase sQLiteDatabase, SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration, byte[] bArr) {
        if (sQLiteDatabase == null) {
            throw new IllegalArgumentException("database must not be null.");
        }
        if (sQLiteDatabaseConfiguration == null) {
            throw new IllegalArgumentException("configuration must not be null.");
        }
        SQLiteConnectionPool sQLiteConnectionPool = new SQLiteConnectionPool(sQLiteDatabase, sQLiteDatabaseConfiguration);
        sQLiteConnectionPool.openSecure(bArr);
        return sQLiteConnectionPool;
    }

    private void open() {
        this.mAvailablePrimaryConnection = openConnectionLocked(this.mConfiguration, true);
        synchronized (this.mLock) {
            IdleConnectionHandler idleConnectionHandler = this.mIdleConnectionHandler;
            if (idleConnectionHandler != null) {
                idleConnectionHandler.connectionReleased(this.mAvailablePrimaryConnection);
            }
        }
        this.mIsOpen = true;
        this.mCloseGuard.open("SQLiteConnectionPool.close");
    }

    private void openSecure(byte[] bArr) {
        this.mAvailablePrimaryConnection = openSecureConnectionLocked(this.mConfiguration, true, bArr);
        synchronized (this.mLock) {
            IdleConnectionHandler idleConnectionHandler = this.mIdleConnectionHandler;
            if (idleConnectionHandler != null) {
                idleConnectionHandler.connectionReleased(this.mAvailablePrimaryConnection);
            }
        }
        this.mIsOpen = true;
        this.mCloseGuard.open("SQLiteConnectionPool.close");
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        dispose(false);
    }

    private void dispose(boolean z) {
        CloseGuard closeGuard = this.mCloseGuard;
        if (closeGuard != null) {
            if (z) {
                closeGuard.warnIfOpen();
            }
            this.mCloseGuard.close();
        }
        this.mDatabase = null;
        if (!z) {
            synchronized (this.mLock) {
                throwIfClosedLocked();
                this.mIsOpen = false;
                this.mClosedBy = new Exception("SQLiteConnectionPool.close()").fillInStackTrace();
                closeAvailableConnectionsAndLogExceptionsLocked();
                int size = this.mAcquiredConnections.size();
                if (size != 0) {
                    Log.i(TAG, "The connection pool for " + this.mConfiguration.label + " has been closed but there are still " + size + " connections in use.  They will be closed as they are released back to the pool.");
                }
                wakeConnectionWaitersLocked();
            }
        }
        if (this.mConfiguration.sharedConfig.isSecureDb) {
            synchronized (this.mLock) {
                long j = this.mRandArrPtr;
                if (j != 0) {
                    SQLiteGlobal.clearRandArray(j);
                    this.mRandArrPtr = 0L;
                }
            }
        }
    }

    public void saveConnectionKey(byte[] bArr) {
        if (!this.mConfiguration.sharedConfig.isSecureDb || this.mConnectionKey == null) {
            return;
        }
        try {
            char[] randArray = SQLiteGlobal.getRandArray(this.mRandArrPtr);
            if (randArray != null) {
                this.mConnectionKey.encryptAndSave(randArray, bArr);
            }
        } catch (Exception e) {
            Log.e(TAG, "Could not save ConnectionKey");
            if (SQLiteDebug.NoPreloadHolder.DEBUG_ENABLE) {
                throw e;
            }
        }
    }

    byte[] getConnectionKey() {
        SecureData secureData;
        if (!this.mConfiguration.sharedConfig.isSecureDb || (secureData = this.mConnectionKey) == null) {
            return null;
        }
        try {
            return secureData.decryptAndGet(SQLiteGlobal.getRandArray(this.mRandArrPtr));
        } catch (Exception unused) {
            Log.e(TAG, "Could not get ConnectionKey");
            return null;
        }
    }

    public void changePassword(SQLiteConnection sQLiteConnection, byte[] bArr) {
        Log.i(TAG, "changePassword...");
        synchronized (this.mLock) {
            if (!this.mConfiguration.sharedConfig.isSecureDb) {
                Log.e(TAG, "Could not change password of normal db" + this.mConfiguration.label);
                throw new IllegalStateException("Could not change password of normal db" + this.mConfiguration.label);
            }
            markAcquiredConnectionsLocked(AcquiredConnectionStatus.DISCARD, false);
            closeAvailableNonPrimaryConnectionsAndLogExceptionsLocked();
            byte[] changePassword = sQLiteConnection.changePassword(bArr);
            SecureData secureData = this.mConnectionKey;
            if (secureData != null) {
                try {
                    secureData.clear();
                    if (changePassword != null) {
                        saveConnectionKey(changePassword);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Could not change Password");
                    if (SQLiteDebug.NoPreloadHolder.DEBUG_ENABLE) {
                        throw e;
                    }
                }
            }
        }
    }

    public void setCheckpointOnClose(boolean z) {
        synchronized (this.mLock) {
            throwIfClosedLocked();
            SQLiteConnection sQLiteConnection = this.mAvailablePrimaryConnection;
            if (sQLiteConnection != null) {
                sQLiteConnection.setCheckpointOnClose(z);
            } else {
                Log.e(TAG, "Could not change 'checkpointOnClose' to " + z + " because the primary connection is on used.");
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x0079, code lost:
    
        closeAvailableConnectionsAndLogExceptionsLocked();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void reconfigure(android.database.sqlite.SQLiteDatabaseConfiguration r6) {
        /*
            r5 = this;
            if (r6 == 0) goto Lc1
            java.lang.Object r0 = r5.mLock
            monitor-enter(r0)
            r5.throwIfClosedLocked()     // Catch: java.lang.Throwable -> Lbe
            android.database.sqlite.SQLiteDatabaseConfiguration r1 = r5.mConfiguration     // Catch: java.lang.Throwable -> Lbe
            java.lang.String r1 = r1.resolveJournalMode()     // Catch: java.lang.Throwable -> Lbe
            java.lang.String r2 = "WAL"
            boolean r1 = r1.equalsIgnoreCase(r2)     // Catch: java.lang.Throwable -> Lbe
            java.lang.String r2 = r6.resolveJournalMode()     // Catch: java.lang.Throwable -> Lbe
            java.lang.String r3 = "WAL"
            boolean r2 = r2.equalsIgnoreCase(r3)     // Catch: java.lang.Throwable -> Lbe
            r1 = r1 ^ r2
            if (r1 == 0) goto L35
            java.util.WeakHashMap<android.database.sqlite.SQLiteConnection, android.database.sqlite.SQLiteConnectionPool$AcquiredConnectionStatus> r2 = r5.mAcquiredConnections     // Catch: java.lang.Throwable -> Lbe
            boolean r2 = r2.isEmpty()     // Catch: java.lang.Throwable -> Lbe
            if (r2 == 0) goto L2d
            r5.closeAvailableNonPrimaryConnectionsAndLogExceptionsLocked()     // Catch: java.lang.Throwable -> Lbe
            goto L35
        L2d:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> Lbe
            java.lang.String r6 = "Write Ahead Logging (WAL) mode cannot be enabled or disabled while there are transactions in progress.  Finish all transactions and release all active database connections first."
            r5.<init>(r6)     // Catch: java.lang.Throwable -> Lbe
            throw r5     // Catch: java.lang.Throwable -> Lbe
        L35:
            boolean r2 = r6.foreignKeyConstraintsEnabled     // Catch: java.lang.Throwable -> Lbe
            android.database.sqlite.SQLiteDatabaseConfiguration r3 = r5.mConfiguration     // Catch: java.lang.Throwable -> Lbe
            boolean r3 = r3.foreignKeyConstraintsEnabled     // Catch: java.lang.Throwable -> Lbe
            if (r2 == r3) goto L4e
            java.util.WeakHashMap<android.database.sqlite.SQLiteConnection, android.database.sqlite.SQLiteConnectionPool$AcquiredConnectionStatus> r2 = r5.mAcquiredConnections     // Catch: java.lang.Throwable -> Lbe
            boolean r2 = r2.isEmpty()     // Catch: java.lang.Throwable -> Lbe
            if (r2 == 0) goto L46
            goto L4e
        L46:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> Lbe
            java.lang.String r6 = "Foreign Key Constraints cannot be enabled or disabled while there are transactions in progress.  Finish all transactions and release all active database connections first."
            r5.<init>(r6)     // Catch: java.lang.Throwable -> Lbe
            throw r5     // Catch: java.lang.Throwable -> Lbe
        L4e:
            android.database.sqlite.SQLiteDatabaseConfiguration r2 = r5.mConfiguration     // Catch: java.lang.Throwable -> Lbe
            int r2 = r2.openFlags     // Catch: java.lang.Throwable -> Lbe
            int r3 = r6.openFlags     // Catch: java.lang.Throwable -> Lbe
            r2 = r2 ^ r3
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r2 != r3) goto L5a
            goto Lab
        L5a:
            android.database.sqlite.SQLiteDatabaseConfiguration r2 = r5.mConfiguration     // Catch: java.lang.Throwable -> Lbe
            int r2 = r2.openFlags     // Catch: java.lang.Throwable -> Lbe
            int r3 = r6.openFlags     // Catch: java.lang.Throwable -> Lbe
            if (r2 == r3) goto Lab
            android.database.sqlite.SQLiteDatabaseConfiguration r2 = r5.mConfiguration     // Catch: java.lang.Throwable -> Lbe
            android.database.sqlite.SQLiteDatabaseSharedConfiguration r2 = r2.sharedConfig     // Catch: java.lang.Throwable -> Lbe
            boolean r2 = r2.isSecureDb     // Catch: java.lang.Throwable -> Lbe
            if (r2 == 0) goto L77
            android.database.sqlite.SQLiteConnectionPool$SecureData r2 = r5.mConnectionKey     // Catch: java.lang.Throwable -> Lbe
            if (r2 == 0) goto L6f
            goto L77
        L6f:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> Lbe
            java.lang.String r6 = "Could not reconfigure SQLiteConnectionPool due to the lack of password,"
            r5.<init>(r6)     // Catch: java.lang.Throwable -> Lbe
            throw r5     // Catch: java.lang.Throwable -> Lbe
        L77:
            if (r1 == 0) goto L7c
            r5.closeAvailableConnectionsAndLogExceptionsLocked()     // Catch: java.lang.Throwable -> Lbe
        L7c:
            android.database.sqlite.SQLiteDatabaseConfiguration r1 = r5.mConfiguration     // Catch: java.lang.Throwable -> Lbe
            android.database.sqlite.SQLiteDatabaseSharedConfiguration r1 = r1.sharedConfig     // Catch: java.lang.Throwable -> Lbe
            boolean r1 = r1.isSecureDb     // Catch: java.lang.Throwable -> Lbe
            r2 = 1
            if (r1 == 0) goto L96
            android.database.sqlite.SQLiteConnectionPool$SecureData r1 = r5.mConnectionKey     // Catch: java.lang.Throwable -> Lbe
            long r3 = r5.mRandArrPtr     // Catch: java.lang.Throwable -> Lbe
            char[] r3 = android.database.sqlite.SQLiteGlobal.getRandArray(r3)     // Catch: java.lang.Throwable -> Lbe
            byte[] r1 = r1.decryptAndGet(r3)     // Catch: java.lang.Throwable -> Lbe
            android.database.sqlite.SQLiteConnection r1 = r5.openSecureConnectionLocked(r6, r2, r1)     // Catch: java.lang.Throwable -> Lbe
            goto L9a
        L96:
            android.database.sqlite.SQLiteConnection r1 = r5.openConnectionLocked(r6, r2)     // Catch: java.lang.Throwable -> Lbe
        L9a:
            r5.closeAvailableConnectionsAndLogExceptionsLocked()     // Catch: java.lang.Throwable -> Lbe
            r5.discardAcquiredConnectionsLocked()     // Catch: java.lang.Throwable -> Lbe
            r5.mAvailablePrimaryConnection = r1     // Catch: java.lang.Throwable -> Lbe
            android.database.sqlite.SQLiteDatabaseConfiguration r1 = r5.mConfiguration     // Catch: java.lang.Throwable -> Lbe
            r1.updateParametersFrom(r6)     // Catch: java.lang.Throwable -> Lbe
            r5.setMaxConnectionPoolSizeLocked()     // Catch: java.lang.Throwable -> Lbe
            goto Lb9
        Lab:
            android.database.sqlite.SQLiteDatabaseConfiguration r1 = r5.mConfiguration     // Catch: java.lang.Throwable -> Lbe
            r1.updateParametersFrom(r6)     // Catch: java.lang.Throwable -> Lbe
            r5.setMaxConnectionPoolSizeLocked()     // Catch: java.lang.Throwable -> Lbe
            r5.closeExcessConnectionsAndLogExceptionsLocked()     // Catch: java.lang.Throwable -> Lbe
            r5.reconfigureAllConnectionsLocked()     // Catch: java.lang.Throwable -> Lbe
        Lb9:
            r5.wakeConnectionWaitersLocked()     // Catch: java.lang.Throwable -> Lbe
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lbe
            return
        Lbe:
            r5 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lbe
            throw r5
        Lc1:
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException
            java.lang.String r6 = "configuration must not be null."
            r5.<init>(r6)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: android.database.sqlite.SQLiteConnectionPool.reconfigure(android.database.sqlite.SQLiteDatabaseConfiguration):void");
    }

    public void exportDB(String str) {
        synchronized (this.mLock) {
            throwIfClosedLocked();
            if (!this.mAcquiredConnections.isEmpty()) {
                throw new IllegalStateException("Release all active connections before starting DB export");
            }
            closeAvailableNonPrimaryConnectionsAndLogExceptionsLocked();
            this.mAvailablePrimaryConnection.exportDB(str);
        }
    }

    public SQLiteConnection acquireConnection(String str, int i, CancellationSignal cancellationSignal) {
        SQLiteConnection waitForConnection = waitForConnection(str, i, cancellationSignal);
        synchronized (this.mLock) {
            IdleConnectionHandler idleConnectionHandler = this.mIdleConnectionHandler;
            if (idleConnectionHandler != null) {
                idleConnectionHandler.connectionAcquired(waitForConnection);
            }
        }
        return waitForConnection;
    }

    public void releaseConnection(SQLiteConnection sQLiteConnection) {
        synchronized (this.mLock) {
            IdleConnectionHandler idleConnectionHandler = this.mIdleConnectionHandler;
            if (idleConnectionHandler != null) {
                idleConnectionHandler.connectionReleased(sQLiteConnection);
            }
            AcquiredConnectionStatus remove = this.mAcquiredConnections.remove(sQLiteConnection);
            if (remove == null) {
                throw new IllegalStateException("Cannot perform this operation because the specified connection was not acquired from this pool or has already been released.");
            }
            if (!this.mIsOpen) {
                closeConnectionAndLogExceptionsLocked(sQLiteConnection);
            } else if (sQLiteConnection.isPrimaryConnection()) {
                if (recycleConnectionLocked(sQLiteConnection, remove)) {
                    this.mAvailablePrimaryConnection = sQLiteConnection;
                }
                wakeConnectionWaitersLocked();
            } else if (this.mAvailableNonPrimaryConnections.size() >= this.mMaxConnectionPoolSize - 1) {
                closeConnectionAndLogExceptionsLocked(sQLiteConnection);
            } else {
                if (recycleConnectionLocked(sQLiteConnection, remove)) {
                    this.mAvailableNonPrimaryConnections.add(sQLiteConnection);
                }
                wakeConnectionWaitersLocked();
            }
        }
    }

    private boolean recycleConnectionLocked(SQLiteConnection sQLiteConnection, AcquiredConnectionStatus acquiredConnectionStatus) {
        if (acquiredConnectionStatus == AcquiredConnectionStatus.RECONFIGURE) {
            try {
                sQLiteConnection.reconfigure(this.mConfiguration);
            } catch (RuntimeException e) {
                Log.e(TAG, "Failed to reconfigure released connection, closing it: " + sQLiteConnection, e);
                acquiredConnectionStatus = AcquiredConnectionStatus.DISCARD;
            }
        }
        if (acquiredConnectionStatus == AcquiredConnectionStatus.DISCARD) {
            closeConnectionAndLogExceptionsLocked(sQLiteConnection);
            return false;
        }
        if (acquiredConnectionStatus != AcquiredConnectionStatus.SHRINK) {
            return true;
        }
        sQLiteConnection.releaseConnectionMemory();
        return true;
    }

    public boolean hasAnyAvailableNonPrimaryConnection() {
        return this.mAvailableNonPrimaryConnections.size() > 0;
    }

    public boolean shouldYieldConnection(SQLiteConnection sQLiteConnection, int i) {
        synchronized (this.mLock) {
            if (!this.mAcquiredConnections.containsKey(sQLiteConnection)) {
                throw new IllegalStateException("Cannot perform this operation because the specified connection was not acquired from this pool or has already been released.");
            }
            if (!this.mIsOpen) {
                return false;
            }
            return isSessionBlockingImportantConnectionWaitersLocked(sQLiteConnection.isPrimaryConnection(), i);
        }
    }

    public void collectDbStats(ArrayList<SQLiteDebug.DbStats> arrayList) {
        boolean z;
        synchronized (this.mLock) {
            SQLiteConnection sQLiteConnection = this.mAvailablePrimaryConnection;
            if (sQLiteConnection != null) {
                sQLiteConnection.collectDbStats(arrayList);
                z = true;
            } else {
                z = false;
            }
            Iterator<SQLiteConnection> it = this.mAvailableNonPrimaryConnections.iterator();
            while (it.hasNext()) {
                it.next().collectDbStats(arrayList);
                z = true;
            }
            for (SQLiteConnection sQLiteConnection2 : this.mAcquiredConnections.keySet()) {
                if (!z && !this.mConfiguration.isInMemoryDb()) {
                    sQLiteConnection2.collectDbStatsUnsafeWithFileSize(arrayList);
                    z = true;
                } else {
                    sQLiteConnection2.collectDbStatsUnsafe(arrayList);
                }
            }
            String str = this.mConfiguration.path;
            int i = this.mTotalPrepareStatements;
            int i2 = this.mTotalPrepareStatementCacheMiss;
            arrayList.add(new SQLiteDebug.DbStats(str, 0L, 0L, 0, i - i2, i2, i, true));
        }
    }

    private SQLiteConnection openConnectionLocked(SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration, boolean z) {
        int i = this.mNextConnectionId;
        this.mNextConnectionId = i + 1;
        SQLiteConnection open = SQLiteConnection.open(this, sQLiteDatabaseConfiguration, i, z);
        if (z && open.isForcedReadOnlyConnection()) {
            this.mMaxConnectionPoolSize = 1;
            this.mIsPoolSizeFixed = true;
            closeAvailableNonPrimaryConnectionsAndLogExceptionsLocked();
        }
        return open;
    }

    private SQLiteConnection openSecureConnectionLocked(SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration, boolean z, byte[] bArr) {
        int i = this.mNextConnectionId;
        this.mNextConnectionId = i + 1;
        SQLiteConnection openSecure = SQLiteConnection.openSecure(this, sQLiteDatabaseConfiguration, i, z, bArr);
        if (z && openSecure.isForcedReadOnlyConnection()) {
            this.mMaxConnectionPoolSize = 1;
            this.mIsPoolSizeFixed = true;
            closeAvailableNonPrimaryConnectionsAndLogExceptionsLocked();
        }
        return openSecure;
    }

    void onConnectionLeaked() {
        Log.w(TAG, "A SQLiteConnection object for database '" + this.mConfiguration.label + "' was leaked!  Please fix your application to end transactions in progress properly and to close the database when it is no longer needed.");
        this.mConnectionLeaked.set(true);
    }

    void onStatementExecuted(long j) {
        this.mTotalStatementsTime.addAndGet(j);
        this.mTotalStatementsCount.incrementAndGet();
    }

    private void closeAvailableConnectionsAndLogExceptionsLocked() {
        closeAvailableNonPrimaryConnectionsAndLogExceptionsLocked();
        SQLiteConnection sQLiteConnection = this.mAvailablePrimaryConnection;
        if (sQLiteConnection != null) {
            closeConnectionAndLogExceptionsLocked(sQLiteConnection);
            this.mAvailablePrimaryConnection = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean closeAvailableConnectionLocked(int i) {
        for (int size = this.mAvailableNonPrimaryConnections.size() - 1; size >= 0; size--) {
            SQLiteConnection sQLiteConnection = this.mAvailableNonPrimaryConnections.get(size);
            if (sQLiteConnection.getConnectionId() == i) {
                closeConnectionAndLogExceptionsLocked(sQLiteConnection);
                this.mAvailableNonPrimaryConnections.remove(size);
                return true;
            }
        }
        SQLiteConnection sQLiteConnection2 = this.mAvailablePrimaryConnection;
        if (sQLiteConnection2 == null || sQLiteConnection2.getConnectionId() != i) {
            return false;
        }
        closeConnectionAndLogExceptionsLocked(this.mAvailablePrimaryConnection);
        this.mAvailablePrimaryConnection = null;
        return true;
    }

    private void closeAvailableNonPrimaryConnectionsAndLogExceptionsLocked() {
        int size = this.mAvailableNonPrimaryConnections.size();
        for (int i = 0; i < size; i++) {
            closeConnectionAndLogExceptionsLocked(this.mAvailableNonPrimaryConnections.get(i));
        }
        this.mAvailableNonPrimaryConnections.clear();
    }

    void closeAvailableNonPrimaryConnectionsAndLogExceptions() {
        synchronized (this.mLock) {
            closeAvailableNonPrimaryConnectionsAndLogExceptionsLocked();
        }
    }

    private void closeExcessConnectionsAndLogExceptionsLocked() {
        int size = this.mAvailableNonPrimaryConnections.size();
        while (true) {
            int i = size - 1;
            if (size <= this.mMaxConnectionPoolSize - 1) {
                return;
            }
            closeConnectionAndLogExceptionsLocked(this.mAvailableNonPrimaryConnections.remove(i));
            size = i;
        }
    }

    private void closeConnectionAndLogExceptionsLocked(SQLiteConnection sQLiteConnection) {
        try {
            sQLiteConnection.close();
            IdleConnectionHandler idleConnectionHandler = this.mIdleConnectionHandler;
            if (idleConnectionHandler != null) {
                idleConnectionHandler.connectionClosed(sQLiteConnection);
            }
        } catch (RuntimeException e) {
            Log.e(TAG, "Failed to close connection, its fate is now in the hands of the merciful GC: " + sQLiteConnection, e);
        }
    }

    private void discardAcquiredConnectionsLocked() {
        markAcquiredConnectionsLocked(AcquiredConnectionStatus.DISCARD);
    }

    private void reconfigureAllConnectionsLocked() {
        SQLiteConnection sQLiteConnection = this.mAvailablePrimaryConnection;
        if (sQLiteConnection != null) {
            try {
                sQLiteConnection.reconfigure(this.mConfiguration);
            } catch (RuntimeException e) {
                Log.e(TAG, "Failed to reconfigure available primary connection, closing it: " + this.mAvailablePrimaryConnection, e);
                closeConnectionAndLogExceptionsLocked(this.mAvailablePrimaryConnection);
                this.mAvailablePrimaryConnection = null;
            }
        }
        int size = this.mAvailableNonPrimaryConnections.size();
        int i = 0;
        while (i < size) {
            SQLiteConnection sQLiteConnection2 = this.mAvailableNonPrimaryConnections.get(i);
            try {
                sQLiteConnection2.reconfigure(this.mConfiguration);
            } catch (RuntimeException e2) {
                Log.e(TAG, "Failed to reconfigure available non-primary connection, closing it: " + sQLiteConnection2, e2);
                closeConnectionAndLogExceptionsLocked(sQLiteConnection2);
                this.mAvailableNonPrimaryConnections.remove(i);
                size--;
                i--;
            }
            i++;
        }
        markAcquiredConnectionsLocked(AcquiredConnectionStatus.RECONFIGURE);
    }

    private void markAcquiredConnectionsLocked(AcquiredConnectionStatus acquiredConnectionStatus) {
        markAcquiredConnectionsLocked(acquiredConnectionStatus, true);
    }

    private void markAcquiredConnectionsLocked(AcquiredConnectionStatus acquiredConnectionStatus, boolean z) {
        if (this.mAcquiredConnections.isEmpty()) {
            return;
        }
        ArrayList arrayList = new ArrayList(this.mAcquiredConnections.size());
        for (Map.Entry<SQLiteConnection, AcquiredConnectionStatus> entry : this.mAcquiredConnections.entrySet()) {
            AcquiredConnectionStatus value = entry.getValue();
            if (acquiredConnectionStatus != value && value != AcquiredConnectionStatus.DISCARD && (!entry.getKey().isPrimaryConnection() || z)) {
                arrayList.add(entry.getKey());
            }
        }
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            this.mAcquiredConnections.put((SQLiteConnection) arrayList.get(i), acquiredConnectionStatus);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:64:0x00b8 A[DONT_GENERATE] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.database.sqlite.SQLiteConnection waitForConnection(java.lang.String r19, int r20, android.os.CancellationSignal r21) {
        /*
            Method dump skipped, instructions count: 202
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.database.sqlite.SQLiteConnectionPool.waitForConnection(java.lang.String, int, android.os.CancellationSignal):android.database.sqlite.SQLiteConnection");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelConnectionWaiterLocked(ConnectionWaiter connectionWaiter) {
        if (connectionWaiter.mAssignedConnection == null && connectionWaiter.mException == null) {
            ConnectionWaiter connectionWaiter2 = null;
            for (ConnectionWaiter connectionWaiter3 = this.mConnectionWaiterQueue; connectionWaiter3 != connectionWaiter; connectionWaiter3 = connectionWaiter3.mNext) {
                connectionWaiter2 = connectionWaiter3;
            }
            if (connectionWaiter2 != null) {
                connectionWaiter2.mNext = connectionWaiter.mNext;
            } else {
                this.mConnectionWaiterQueue = connectionWaiter.mNext;
            }
            connectionWaiter.mException = new OperationCanceledException();
            LockSupport.unpark(connectionWaiter.mThread);
            wakeConnectionWaitersLocked();
        }
    }

    private void logDbSize(StringBuilder sb, String str) {
        if (SystemClock.uptimeMillis() - this.mDbSizeCheckTime > ParcelableCallAnalytics.MILLIS_IN_5_MINUTES) {
            this.mDbSizeCheckTime = SystemClock.uptimeMillis();
            long fileSize = SQLiteUtils.getFileSize(str);
            if (fileSize == -1) {
                return;
            }
            long fileSize2 = SQLiteUtils.getFileSize(str + "-wal");
            if (fileSize > 209715200 || fileSize2 > 209715200) {
                sb.append("DB size: " + ((fileSize / 1024) / 1024));
                if (fileSize2 > 0) {
                    sb.append("/" + ((fileSize2 / 1024) / 1024));
                }
                sb.append(" MB\n");
            }
        }
    }

    private void logConnectionPoolBusyLocked(long j, int i) {
        int i2;
        Thread currentThread = Thread.currentThread();
        StringBuilder sb = new StringBuilder();
        sb.append("The connection pool for database '");
        sb.append(this.mConfiguration.label);
        sb.append("' has been unable to grant a connection to thread ");
        sb.append(currentThread.getId());
        sb.append(" (");
        sb.append(currentThread.getName());
        sb.append(") with flags 0x");
        sb.append(Integer.toHexString(i));
        sb.append(" for ");
        sb.append(j * 0.001f);
        sb.append(" seconds.\n");
        if (!this.mConfiguration.isInMemoryDb()) {
            logDbSize(sb, this.mConfiguration.path);
        }
        ArrayList arrayList = new ArrayList();
        int i3 = 0;
        if (this.mAcquiredConnections.isEmpty()) {
            i2 = 0;
        } else {
            Iterator<SQLiteConnection> it = this.mAcquiredConnections.keySet().iterator();
            i2 = 0;
            while (it.hasNext()) {
                String describeCurrentOperationUnsafe = it.next().describeCurrentOperationUnsafe();
                if (describeCurrentOperationUnsafe != null) {
                    arrayList.add(describeCurrentOperationUnsafe);
                    i3++;
                } else {
                    i2++;
                }
            }
        }
        int size = this.mAvailableNonPrimaryConnections.size();
        if (this.mAvailablePrimaryConnection != null) {
            size++;
        }
        sb.append("Connections: ");
        sb.append(i3);
        sb.append(" active, ");
        sb.append(i2);
        sb.append(" idle, ");
        sb.append(size);
        sb.append(" available.\n");
        if (!arrayList.isEmpty()) {
            sb.append("\nRequests in progress:\n");
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                String str = (String) it2.next();
                sb.append("  ");
                sb.append(str);
                sb.append(ShaderAssembler.NEWLINE);
            }
        }
        Log.w(TAG, sb.toString());
    }

    private void wakeConnectionWaitersLocked() {
        SQLiteConnection sQLiteConnection;
        ConnectionWaiter connectionWaiter = this.mConnectionWaiterQueue;
        ConnectionWaiter connectionWaiter2 = null;
        boolean z = false;
        boolean z2 = false;
        while (connectionWaiter != null) {
            boolean z3 = true;
            if (this.mIsOpen) {
                try {
                    if (connectionWaiter.mWantPrimaryConnection || z) {
                        sQLiteConnection = null;
                    } else {
                        sQLiteConnection = tryAcquireNonPrimaryConnectionLocked(connectionWaiter.mSql, connectionWaiter.mConnectionFlags);
                        if (sQLiteConnection == null && isPrimaryConnectionExistsLocked()) {
                            z = true;
                        }
                    }
                    if (sQLiteConnection == null && !z2 && (sQLiteConnection = tryAcquirePrimaryConnectionLocked(connectionWaiter.mConnectionFlags)) == null) {
                        z2 = true;
                    }
                    if (sQLiteConnection != null) {
                        connectionWaiter.mAssignedConnection = sQLiteConnection;
                    } else if (z && z2) {
                        return;
                    } else {
                        z3 = false;
                    }
                } catch (RuntimeException e) {
                    connectionWaiter.mException = e;
                }
            }
            ConnectionWaiter connectionWaiter3 = connectionWaiter.mNext;
            if (z3) {
                if (connectionWaiter2 != null) {
                    connectionWaiter2.mNext = connectionWaiter3;
                } else {
                    this.mConnectionWaiterQueue = connectionWaiter3;
                }
                connectionWaiter.mNext = null;
                LockSupport.unpark(connectionWaiter.mThread);
            } else {
                connectionWaiter2 = connectionWaiter;
            }
            connectionWaiter = connectionWaiter3;
        }
    }

    public void dumpAllConnections(SQLiteDump sQLiteDump) {
        if (sQLiteDump == null || sQLiteDump.mTeePrinter == null) {
            return;
        }
        try {
            synchronized (this.mLock) {
                if (this.mAvailablePrimaryConnection != null) {
                    sQLiteDump.mTeePrinter.println("The recent request on avilable primary connection for corruption debug.");
                    this.mAvailablePrimaryConnection.dump(sQLiteDump.mTeePrinter, false);
                }
                if (!this.mAvailableNonPrimaryConnections.isEmpty()) {
                    int size = this.mAvailableNonPrimaryConnections.size();
                    for (int i = 0; i < size; i++) {
                        sQLiteDump.mTeePrinter.println("The recent request on avilable connection for corruption debug.");
                        this.mAvailableNonPrimaryConnections.get(i).dump(sQLiteDump.mTeePrinter, false);
                    }
                }
                if (!this.mAcquiredConnections.isEmpty()) {
                    Iterator<Map.Entry<SQLiteConnection, AcquiredConnectionStatus>> it = this.mAcquiredConnections.entrySet().iterator();
                    while (it.hasNext()) {
                        SQLiteConnection key = it.next().getKey();
                        SQLiteDump.TeePrinter teePrinter = sQLiteDump.mTeePrinter;
                        StringBuilder sb = new StringBuilder();
                        sb.append("The recent request on acquired ");
                        sb.append(key.isPrimaryConnection() ? "primary" : "");
                        sb.append(" connection for corruption debug.");
                        teePrinter.println(sb.toString());
                        key.dumpUnsafe(sQLiteDump.mTeePrinter, false);
                    }
                }
                sQLiteDump.mTeePrinter.println("");
            }
        } catch (Exception unused) {
            Log.e(TAG, "dump all connections log failed.");
        }
    }

    private SQLiteConnection tryAcquirePrimaryConnectionLocked(int i) {
        SQLiteConnection openConnectionLocked;
        SQLiteConnection sQLiteConnection = this.mAvailablePrimaryConnection;
        if (sQLiteConnection != null) {
            this.mAvailablePrimaryConnection = null;
            finishAcquireConnectionLocked(sQLiteConnection, i);
            return sQLiteConnection;
        }
        Iterator<SQLiteConnection> it = this.mAcquiredConnections.keySet().iterator();
        while (it.hasNext()) {
            if (it.next().isPrimaryConnection()) {
                return null;
            }
        }
        if (this.mConfiguration.sharedConfig.isSecureDb) {
            SecureData secureData = this.mConnectionKey;
            if (secureData == null) {
                throw new IllegalStateException("Could not open a new primary connection due to the lack of password.");
            }
            openConnectionLocked = openSecureConnectionLocked(this.mConfiguration, true, secureData.decryptAndGet(SQLiteGlobal.getRandArray(this.mRandArrPtr)));
        } else {
            openConnectionLocked = openConnectionLocked(this.mConfiguration, true);
        }
        finishAcquireConnectionLocked(openConnectionLocked, i);
        return openConnectionLocked;
    }

    private boolean isPrimaryConnectionExistsLocked() {
        if (this.mAvailablePrimaryConnection != null) {
            return true;
        }
        if (this.mAcquiredConnections.isEmpty()) {
            return false;
        }
        Iterator<SQLiteConnection> it = this.mAcquiredConnections.keySet().iterator();
        while (it.hasNext()) {
            if (it.next().isPrimaryConnection()) {
                return true;
            }
        }
        return false;
    }

    private SQLiteConnection tryAcquireNonPrimaryConnectionLocked(String str, int i) {
        SQLiteConnection openSecureConnectionLocked;
        int size = this.mAvailableNonPrimaryConnections.size();
        if (size > 1 && str != null) {
            for (int i2 = 0; i2 < size; i2++) {
                SQLiteConnection sQLiteConnection = this.mAvailableNonPrimaryConnections.get(i2);
                if (sQLiteConnection.isPreparedStatementInCache(str)) {
                    this.mAvailableNonPrimaryConnections.remove(i2);
                    finishAcquireConnectionLocked(sQLiteConnection, i);
                    return sQLiteConnection;
                }
            }
        }
        if (size > 0) {
            SQLiteConnection remove = this.mAvailableNonPrimaryConnections.remove(size - 1);
            finishAcquireConnectionLocked(remove, i);
            return remove;
        }
        int size2 = this.mAcquiredConnections.size();
        if (this.mAvailablePrimaryConnection != null) {
            size2++;
        }
        if (size2 >= this.mMaxConnectionPoolSize || !isPrimaryConnectionExistsLocked()) {
            return null;
        }
        if (this.mConfiguration.sharedConfig.isSecureDb) {
            try {
                openSecureConnectionLocked = openSecureConnectionLocked(this.mConfiguration, false, this.mConnectionKey.decryptAndGet(SQLiteGlobal.getRandArray(this.mRandArrPtr)));
            } catch (SQLiteException e) {
                throw e;
            } catch (Exception e2) {
                Log.w(TAG, "Unable to open new connection due to lack of key, go on");
                if (SQLiteDebug.NoPreloadHolder.DEBUG_ENABLE) {
                    throw e2;
                }
                return null;
            }
        } else {
            openSecureConnectionLocked = openConnectionLocked(this.mConfiguration, false);
        }
        finishAcquireConnectionLocked(openSecureConnectionLocked, i);
        return openSecureConnectionLocked;
    }

    private void finishAcquireConnectionLocked(SQLiteConnection sQLiteConnection, int i) {
        try {
            sQLiteConnection.setOnlyAllowReadOnlyOperations((i & 1) != 0);
            this.mAcquiredConnections.put(sQLiteConnection, AcquiredConnectionStatus.NORMAL);
        } catch (RuntimeException e) {
            Log.e(TAG, "Failed to prepare acquired connection for session, closing it: " + sQLiteConnection + ", connectionFlags=" + i);
            closeConnectionAndLogExceptionsLocked(sQLiteConnection);
            throw e;
        }
    }

    private boolean isSessionBlockingImportantConnectionWaitersLocked(boolean z, int i) {
        ConnectionWaiter connectionWaiter = this.mConnectionWaiterQueue;
        if (connectionWaiter == null) {
            return false;
        }
        int priority = getPriority(i);
        while (priority <= connectionWaiter.mPriority) {
            if (z || !connectionWaiter.mWantPrimaryConnection) {
                return true;
            }
            connectionWaiter = connectionWaiter.mNext;
            if (connectionWaiter == null) {
                return false;
            }
        }
        return false;
    }

    private void setMaxConnectionPoolSizeLocked() {
        if (!this.mIsPoolSizeFixed || this.mMaxConnectionPoolSize == 0) {
            if (this.mConfiguration.sharedConfig.useSingleConnectionWal) {
                this.mMaxConnectionPoolSize = 1;
            } else if (this.mConfiguration.resolveJournalMode().equalsIgnoreCase(SQLiteDatabase.JOURNAL_MODE_WAL)) {
                this.mMaxConnectionPoolSize = SQLiteGlobal.getWALConnectionPoolSize();
            } else {
                this.mMaxConnectionPoolSize = 1;
            }
        }
    }

    public void setupIdleConnectionHandler(Looper looper, long j, Runnable runnable) {
        synchronized (this.mLock) {
            this.mIdleConnectionHandler = new IdleConnectionHandler(looper, j, runnable);
        }
    }

    public void setupIdleConnectionShrinkHandler(Looper looper, long j, Runnable runnable) {
        synchronized (this.mLock) {
            this.mIdleConnectionHandler = new IdleConnectionShrinkHandler(looper, j, runnable);
        }
    }

    void disableIdleConnectionHandler() {
        synchronized (this.mLock) {
            this.mIdleConnectionHandler = null;
        }
    }

    private void throwIfClosedLocked() {
        if (!this.mIsOpen) {
            throw new IllegalStateException("Cannot perform this operation because the connection pool has been closed.", this.mClosedBy);
        }
    }

    private ConnectionWaiter obtainConnectionWaiterLocked(Thread thread, long j, int i, boolean z, String str, int i2) {
        ConnectionWaiter connectionWaiter = this.mConnectionWaiterPool;
        if (connectionWaiter != null) {
            this.mConnectionWaiterPool = connectionWaiter.mNext;
            connectionWaiter.mNext = null;
        } else {
            connectionWaiter = new ConnectionWaiter();
        }
        connectionWaiter.mThread = thread;
        connectionWaiter.mStartTime = j;
        connectionWaiter.mPriority = i;
        connectionWaiter.mWantPrimaryConnection = z;
        connectionWaiter.mSql = str;
        connectionWaiter.mConnectionFlags = i2;
        return connectionWaiter;
    }

    private void recycleConnectionWaiterLocked(ConnectionWaiter connectionWaiter) {
        connectionWaiter.mNext = this.mConnectionWaiterPool;
        connectionWaiter.mThread = null;
        connectionWaiter.mSql = null;
        connectionWaiter.mAssignedConnection = null;
        connectionWaiter.mException = null;
        connectionWaiter.mNonce++;
        this.mConnectionWaiterPool = connectionWaiter;
    }

    void clearAcquiredConnectionsPreparedStatementCache() {
        synchronized (this.mLock) {
            this.mDatabaseSeqNum++;
            if (!this.mAcquiredConnections.isEmpty()) {
                Iterator<SQLiteConnection> it = this.mAcquiredConnections.keySet().iterator();
                while (it.hasNext()) {
                    it.next().setDatabaseSeqNum(this.mDatabaseSeqNum);
                }
            }
        }
    }

    public void closeAndDiscardNonPrimaryConnections(boolean z, boolean z2) {
        synchronized (this.mLock) {
            closeAvailableNonPrimaryConnectionsAndLogExceptionsLocked();
            if (z) {
                markAcquiredConnectionsLocked(AcquiredConnectionStatus.DISCARD, false);
                this.mMaxConnectionPoolSize = 1;
                if (z2) {
                    this.mIsPoolSizeFixed = true;
                }
            }
        }
    }

    public void releaseConnectionMemory() {
        synchronized (this.mLock) {
            SQLiteConnection sQLiteConnection = this.mAvailablePrimaryConnection;
            if (sQLiteConnection != null) {
                sQLiteConnection.releaseConnectionMemory();
            }
            Iterator<SQLiteConnection> it = this.mAvailableNonPrimaryConnections.iterator();
            while (it.hasNext()) {
                it.next().releaseConnectionMemory();
            }
            markAcquiredConnectionsLocked(AcquiredConnectionStatus.SHRINK);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean releaseAvailableConnectionMemoryLocked(int i) {
        for (int size = this.mAvailableNonPrimaryConnections.size() - 1; size >= 0; size--) {
            SQLiteConnection sQLiteConnection = this.mAvailableNonPrimaryConnections.get(size);
            if (sQLiteConnection.getConnectionId() == i) {
                sQLiteConnection.releaseConnectionMemory();
                return true;
            }
        }
        SQLiteConnection sQLiteConnection2 = this.mAvailablePrimaryConnection;
        if (sQLiteConnection2 == null || sQLiteConnection2.getConnectionId() != i) {
            return false;
        }
        this.mAvailablePrimaryConnection.releaseConnectionMemory();
        return true;
    }

    protected SQLiteDatabase getDatabase() {
        return this.mDatabase;
    }

    public void reOpen() {
        SQLiteConnection openConnectionLocked;
        if (this.mIsOpen) {
            Log.i(TAG, "try reOpen connection...");
            synchronized (this.mLock) {
                if (this.mConfiguration.sharedConfig.isSecureDb && this.mConnectionKey == null) {
                    Log.e(TAG, "Could not re-open connection due to the lack of password");
                    return;
                }
                closeAvailableNonPrimaryConnectionsAndLogExceptionsLocked();
                markAcquiredConnectionsLocked(AcquiredConnectionStatus.DISCARD, false);
                closeConnectionAndLogExceptionsLocked(this.mAvailablePrimaryConnection);
                this.mAvailablePrimaryConnection = null;
                discardAcquiredConnectionsLocked();
                if (this.mConfiguration.sharedConfig.isSecureDb) {
                    openConnectionLocked = openSecureConnectionLocked(this.mConfiguration, true, this.mConnectionKey.decryptAndGet(SQLiteGlobal.getRandArray(this.mRandArrPtr)));
                } else {
                    openConnectionLocked = openConnectionLocked(this.mConfiguration, true);
                }
                this.mAvailablePrimaryConnection = openConnectionLocked;
            }
        }
    }

    public int getMaxConnectionPoolSize() {
        int i;
        synchronized (this.mLock) {
            i = this.mMaxConnectionPoolSize;
        }
        return i;
    }

    public void setMaxConnectionPoolSize(int i) {
        synchronized (this.mLock) {
            this.mMaxConnectionPoolSize = i;
        }
    }

    public boolean isConnectionAcquired() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mAcquiredConnections.size() != 0;
        }
        return z;
    }

    public void dump(Printer printer, boolean z, ArraySet<String> arraySet) {
        String str;
        String str2;
        boolean z2;
        Printer printer2;
        Printer create = PrefixPrinter.create(printer, "    ");
        synchronized (this.mLock) {
            if (arraySet != null) {
                str = "  Idle connection shrink timeout: ";
                str2 = "  Idle connection timeout: ";
                String parent = new File(this.mConfiguration.path).getParent();
                if (parent != null) {
                    arraySet.add(parent);
                }
            } else {
                str = "  Idle connection shrink timeout: ";
                str2 = "  Idle connection timeout: ";
            }
            boolean isLegacyCompatibilityWalEnabled = this.mConfiguration.isLegacyCompatibilityWalEnabled();
            printer.println("Connection pool for " + this.mConfiguration.path + ":");
            StringBuilder sb = new StringBuilder("  Open: ");
            sb.append(this.mIsOpen);
            printer.println(sb.toString());
            SQLiteDatabase database = getDatabase();
            if (database != null && database.isUdrEnabled() && SQLiteUserDataRecovery.isDbUdrRecovered(this.mConfiguration.path)) {
                printer.println("  UDR: true");
            }
            printer.println("  Max connections: " + this.mMaxConnectionPoolSize);
            printer.println("  Total execution time (ms): " + this.mTotalStatementsTime);
            printer.println("  Total statements executed: " + this.mTotalStatementsCount);
            if (this.mTotalStatementsCount.get() > 0) {
                printer.println("  Average time per statement (ms): " + (this.mTotalStatementsTime.get() / this.mTotalStatementsCount.get()));
            }
            printer.println("  Configuration: openFlags=" + this.mConfiguration.openFlags + ", isLegacyCompatibilityWalEnabled=" + isLegacyCompatibilityWalEnabled + ", journalMode=" + TextUtils.emptyIfNull(this.mConfiguration.resolveJournalMode()) + ", syncMode=" + TextUtils.emptyIfNull(this.mConfiguration.resolveSyncMode()));
            StringBuilder sb2 = new StringBuilder("  IsReadOnlyDatabase: ");
            sb2.append(this.mConfiguration.isReadOnlyDatabase());
            printer.println(sb2.toString());
            if (isLegacyCompatibilityWalEnabled) {
                printer.println("  Compatibility WAL enabled: wal_syncmode=" + SQLiteCompatibilityWalFlags.getWALSyncMode());
            }
            if (this.mConfiguration.isLookasideConfigSet()) {
                printer.println("  Lookaside config: sz=" + this.mConfiguration.lookasideSlotSize + " cnt=" + this.mConfiguration.lookasideSlotCount);
            }
            if (this.mConfiguration.idleConnectionTimeoutMs != Long.MAX_VALUE) {
                printer.println(str2 + this.mConfiguration.idleConnectionTimeoutMs);
            }
            if (this.mConfiguration.idleConnectionShrinkTimeoutMs != Long.MAX_VALUE) {
                printer.println(str + this.mConfiguration.idleConnectionShrinkTimeoutMs);
            }
            printer.println("  Secure db: " + this.mConfiguration.sharedConfig.isSecureDb);
            if (this.mConfiguration.resolveJournalMode().equalsIgnoreCase(SQLiteDatabase.JOURNAL_MODE_WAL)) {
                printer.println("  Use WAL mode. ");
            }
            printer.println("  Available primary connection:");
            SQLiteConnection sQLiteConnection = this.mAvailablePrimaryConnection;
            if (sQLiteConnection != null) {
                z2 = z;
                printer2 = create;
                sQLiteConnection.dump(printer2, z2);
            } else {
                z2 = z;
                printer2 = create;
                printer2.println("<none>");
            }
            printer.println("  Available non-primary connections:");
            int i = 0;
            if (!this.mAvailableNonPrimaryConnections.isEmpty()) {
                int size = this.mAvailableNonPrimaryConnections.size();
                for (int i2 = 0; i2 < size; i2++) {
                    this.mAvailableNonPrimaryConnections.get(i2).dump(printer2, z2);
                }
            } else {
                printer2.println("<none>");
            }
            printer.println("  Acquired connections:");
            if (!this.mAcquiredConnections.isEmpty()) {
                for (Map.Entry<SQLiteConnection, AcquiredConnectionStatus> entry : this.mAcquiredConnections.entrySet()) {
                    entry.getKey().dumpUnsafe(printer2, z2);
                    printer2.println("  Status: " + entry.getValue());
                }
            } else {
                printer2.println("<none>");
            }
            printer.println("  Connection waiters:");
            if (this.mConnectionWaiterQueue != null) {
                long uptimeMillis = SystemClock.uptimeMillis();
                ConnectionWaiter connectionWaiter = this.mConnectionWaiterQueue;
                while (connectionWaiter != null) {
                    printer2.println(i + ": waited for " + ((uptimeMillis - connectionWaiter.mStartTime) * 0.001f) + " ms - thread=" + connectionWaiter.mThread + ", priority=" + connectionWaiter.mPriority + ", sql='" + connectionWaiter.mSql + "'");
                    connectionWaiter = connectionWaiter.mNext;
                    i++;
                }
            } else {
                printer2.println("<none>");
            }
        }
    }

    @NeverCompile
    public double getStatementCacheMissRate() {
        int i = this.mTotalPrepareStatements;
        return i == 0 ? SContextConstants.ENVIRONMENT_VALUE_UNKNOWN : this.mTotalPrepareStatementCacheMiss / i;
    }

    public long getTotalStatementsTime() {
        return this.mTotalStatementsTime.get();
    }

    public long getTotalStatementsCount() {
        return this.mTotalStatementsCount.get();
    }

    public String toString() {
        return "SQLiteConnectionPool: " + this.mConfiguration.path;
    }

    public String getPath() {
        return this.mConfiguration.path;
    }

    private static final class ConnectionWaiter {
        public SQLiteConnection mAssignedConnection;
        public int mConnectionFlags;
        public RuntimeException mException;
        public ConnectionWaiter mNext;
        public int mNonce;
        public int mPriority;
        public String mSql;
        public long mStartTime;
        public Thread mThread;
        public boolean mWantPrimaryConnection;

        private ConnectionWaiter() {
        }
    }

    private class IdleConnectionHandler extends Handler {
        private final Runnable mOnAllConnectionsIdle;
        protected final long mTimeout;

        IdleConnectionHandler(Looper looper, long j, Runnable runnable) {
            super(looper);
            this.mTimeout = j;
            this.mOnAllConnectionsIdle = runnable;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            synchronized (SQLiteConnectionPool.this.mLock) {
                if (this != SQLiteConnectionPool.this.mIdleConnectionHandler) {
                    return;
                }
                if (SQLiteConnectionPool.this.closeAvailableConnectionLocked(message.what) && Log.isLoggable(SQLiteConnectionPool.TAG, 3)) {
                    Log.d(SQLiteConnectionPool.TAG, "Closed idle connection " + SQLiteConnectionPool.this.mConfiguration.label + " " + message.what + " after " + this.mTimeout);
                }
                Runnable runnable = this.mOnAllConnectionsIdle;
                if (runnable != null) {
                    runnable.run();
                }
            }
        }

        void connectionReleased(SQLiteConnection sQLiteConnection) {
            sendEmptyMessageDelayed(sQLiteConnection.getConnectionId(), this.mTimeout);
        }

        void connectionAcquired(SQLiteConnection sQLiteConnection) {
            removeMessages(sQLiteConnection.getConnectionId());
        }

        void connectionClosed(SQLiteConnection sQLiteConnection) {
            removeMessages(sQLiteConnection.getConnectionId());
        }
    }

    private class IdleConnectionShrinkHandler extends IdleConnectionHandler {
        IdleConnectionShrinkHandler(Looper looper, long j, Runnable runnable) {
            super(looper, j, runnable);
        }

        @Override // android.database.sqlite.SQLiteConnectionPool.IdleConnectionHandler, android.os.Handler
        public void handleMessage(Message message) {
            synchronized (SQLiteConnectionPool.this.mLock) {
                if (this != SQLiteConnectionPool.this.mIdleConnectionHandler) {
                    return;
                }
                if (SQLiteConnectionPool.this.releaseAvailableConnectionMemoryLocked(message.what) && Log.isLoggable(SQLiteConnectionPool.TAG, 3)) {
                    Log.d(SQLiteConnectionPool.TAG, "Released idle connection's memory " + SQLiteConnectionPool.this.mConfiguration.label + " " + message.what + " after " + this.mTimeout);
                }
            }
        }
    }

    private final class SecureData {
        private static final int DEFAULT_ITER_COUNT = 1000;
        private static final int DEFAULT_KEY_LENGTH = 128;
        private static final int DEFAULT_SALT_IV_SIZE = 16;
        private SQLiteDatabaseConfiguration mConfiguration;
        private byte[] mSalt;
        private final Object mLock = new Object();
        private byte[] mEncryptedData = null;
        private byte[] mIV = new byte[16];

        public SecureData(SQLiteConnectionPool sQLiteConnectionPool, SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration) {
            this.mConfiguration = sQLiteDatabaseConfiguration;
            SecureRandom secureRandom = new SecureRandom();
            byte[] bArr = new byte[16];
            this.mSalt = bArr;
            secureRandom.nextBytes(bArr);
        }

        private SecretKeySpec generateKey(char[] cArr) {
            try {
                PBEKeySpec pBEKeySpec = new PBEKeySpec(cArr, this.mSalt, 1000, 128);
                SecretKeySpec secretKeySpec = new SecretKeySpec(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(pBEKeySpec).getEncoded(), "AES");
                pBEKeySpec.clearPassword();
                return secretKeySpec;
            } catch (Exception e) {
                throw new RuntimeException("Fail to generate the data of " + this.mConfiguration.label, e);
            }
        }

        public void encryptAndSave(char[] cArr, byte[] bArr) {
            synchronized (this.mLock) {
                if (cArr == null) {
                    throw new IllegalArgumentException("password should not be null");
                }
                if (bArr == null) {
                    throw new IllegalArgumentException("data that will be encrypted should not be null");
                }
                if (this.mEncryptedData != null) {
                    return;
                }
                try {
                    new SecureRandom().nextBytes(this.mIV);
                    SecretKeySpec generateKey = generateKey(cArr);
                    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                    cipher.init(1, generateKey, new IvParameterSpec(this.mIV));
                    this.mEncryptedData = cipher.doFinal(bArr);
                } catch (Exception e) {
                    this.mEncryptedData = null;
                    Log.e(SQLiteConnectionPool.TAG, "Could not encrypt the data of " + this.mConfiguration.label);
                    throw new RuntimeException("Fail to encrpyt the data of " + this.mConfiguration.label, e);
                }
            }
        }

        public byte[] decryptAndGet(char[] cArr) {
            byte[] doFinal;
            synchronized (this.mLock) {
                try {
                    if (cArr == null) {
                        throw new IllegalArgumentException("password should not be null");
                    }
                    if (this.mEncryptedData == null) {
                        throw new IllegalStateException("Please encrypt and save data first.");
                    }
                    try {
                        SecretKeySpec generateKey = generateKey(cArr);
                        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                        cipher.init(2, generateKey, new IvParameterSpec(this.mIV));
                        doFinal = cipher.doFinal(this.mEncryptedData);
                    } catch (Exception e) {
                        Log.e(SQLiteConnectionPool.TAG, "Could not decrypt the data of " + this.mConfiguration.label);
                        throw new RuntimeException("Fail to decrypt the data of " + this.mConfiguration.label, e);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return doFinal;
        }

        public void clear() {
            synchronized (this.mLock) {
                this.mEncryptedData = null;
            }
        }
    }
}
