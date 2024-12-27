package android.database.sqlite;

import android.hardware.scontext.SContextConstants;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.OperationCanceledException;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.util.PrefixPrinter;
import android.util.Printer;

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
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public final class SQLiteConnectionPool implements Closeable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int CONNECTION_FLAG_INTERACTIVE = 4;
    public static final int CONNECTION_FLAG_PRIMARY_CONNECTION_AFFINITY = 2;
    public static final int CONNECTION_FLAG_READ_ONLY = 1;
    private static final long CONNECTION_POOL_BUSY_MILLIS = 4000;
    private static final String TAG = "SQLiteConnectionPool";
    private SQLiteConnection mAvailablePrimaryConnection;
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
    private final WeakHashMap<SQLiteConnection, AcquiredConnectionStatus> mAcquiredConnections =
            new WeakHashMap<>();

    enum AcquiredConnectionStatus {
        NORMAL,
        RECONFIGURE,
        DISCARD,
        SHRINK
    }

    private SQLiteConnectionPool(SQLiteDatabase db, SQLiteDatabaseConfiguration configuration) {
        this.mDatabase = db;
        this.mConfiguration = new SQLiteDatabaseConfiguration(configuration);
        setMaxConnectionPoolSizeLocked();
        if (this.mConfiguration.idleConnectionTimeoutMs != Long.MAX_VALUE) {
            setupIdleConnectionHandler(
                    Looper.getMainLooper(), this.mConfiguration.idleConnectionTimeoutMs, null);
        } else if (this.mConfiguration.idleConnectionShrinkTimeoutMs != Long.MAX_VALUE) {
            setupIdleConnectionShrinkHandler(
                    Looper.getMainLooper(),
                    this.mConfiguration.idleConnectionShrinkTimeoutMs,
                    null);
        }
        if (this.mConfiguration.sharedConfig.isSecureDb) {
            try {
                this.mRandArrPtr = SQLiteGlobal.createRandArray();
                if (this.mRandArrPtr != 0) {
                    this.mConnectionKey = new SecureData(this.mConfiguration);
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

    public static SQLiteConnectionPool open(
            SQLiteDatabase db, SQLiteDatabaseConfiguration configuration) {
        if (db == null) {
            throw new IllegalArgumentException("database must not be null.");
        }
        if (configuration == null) {
            throw new IllegalArgumentException("configuration must not be null.");
        }
        SQLiteConnectionPool pool = new SQLiteConnectionPool(db, configuration);
        pool.open();
        return pool;
    }

    public static SQLiteConnectionPool openSecure(
            SQLiteDatabase db, SQLiteDatabaseConfiguration configuration, byte[] password) {
        if (db == null) {
            throw new IllegalArgumentException("database must not be null.");
        }
        if (configuration == null) {
            throw new IllegalArgumentException("configuration must not be null.");
        }
        SQLiteConnectionPool pool = new SQLiteConnectionPool(db, configuration);
        pool.openSecure(password);
        return pool;
    }

    private void open() {
        this.mAvailablePrimaryConnection = openConnectionLocked(this.mConfiguration, true);
        synchronized (this.mLock) {
            if (this.mIdleConnectionHandler != null) {
                this.mIdleConnectionHandler.connectionReleased(this.mAvailablePrimaryConnection);
            }
        }
        this.mIsOpen = true;
        this.mCloseGuard.open("SQLiteConnectionPool.close");
    }

    private void openSecure(byte[] password) {
        this.mAvailablePrimaryConnection =
                openSecureConnectionLocked(this.mConfiguration, true, password);
        synchronized (this.mLock) {
            if (this.mIdleConnectionHandler != null) {
                this.mIdleConnectionHandler.connectionReleased(this.mAvailablePrimaryConnection);
            }
        }
        this.mIsOpen = true;
        this.mCloseGuard.open("SQLiteConnectionPool.close");
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        dispose(false);
    }

    private void dispose(boolean finalized) {
        if (this.mCloseGuard != null) {
            if (finalized) {
                this.mCloseGuard.warnIfOpen();
            }
            this.mCloseGuard.close();
        }
        this.mDatabase = null;
        if (!finalized) {
            synchronized (this.mLock) {
                throwIfClosedLocked();
                this.mIsOpen = false;
                closeAvailableConnectionsAndLogExceptionsLocked();
                int pendingCount = this.mAcquiredConnections.size();
                if (pendingCount != 0) {
                    Log.i(
                            TAG,
                            "The connection pool for "
                                    + this.mConfiguration.label
                                    + " has been closed but there are still "
                                    + pendingCount
                                    + " connections in use.  They will be closed as they are"
                                    + " released back to the pool.");
                }
                wakeConnectionWaitersLocked();
            }
        }
        if (this.mConfiguration.sharedConfig.isSecureDb) {
            synchronized (this.mLock) {
                if (this.mRandArrPtr != 0) {
                    SQLiteGlobal.clearRandArray(this.mRandArrPtr);
                    this.mRandArrPtr = 0L;
                }
            }
        }
    }

    public void saveConnectionKey(byte[] key) {
        if (this.mConfiguration.sharedConfig.isSecureDb && this.mConnectionKey != null) {
            try {
                char[] password = SQLiteGlobal.getRandArray(this.mRandArrPtr);
                if (password != null) {
                    this.mConnectionKey.encryptAndSave(password, key);
                }
            } catch (Exception e) {
                Log.e(TAG, "Could not save ConnectionKey");
                if (SQLiteDebug.NoPreloadHolder.DEBUG_ENABLE) {
                    throw e;
                }
            }
        }
    }

    byte[] getConnectionKey() {
        if (this.mConfiguration.sharedConfig.isSecureDb && this.mConnectionKey != null) {
            try {
                return this.mConnectionKey.decryptAndGet(
                        SQLiteGlobal.getRandArray(this.mRandArrPtr));
            } catch (Exception e) {
                Log.e(TAG, "Could not get ConnectionKey");
                return null;
            }
        }
        return null;
    }

    public void changePassword(SQLiteConnection connection, byte[] newPassword) {
        Log.i(TAG, "changePassword...");
        synchronized (this.mLock) {
            if (!this.mConfiguration.sharedConfig.isSecureDb) {
                Log.e(TAG, "Could not change password of normal db" + this.mConfiguration.label);
                throw new IllegalStateException(
                        "Could not change password of normal db" + this.mConfiguration.label);
            }
            markAcquiredConnectionsLocked(AcquiredConnectionStatus.DISCARD, false);
            closeAvailableNonPrimaryConnectionsAndLogExceptionsLocked();
            byte[] key = connection.changePassword(newPassword);
            if (this.mConnectionKey != null) {
                try {
                    this.mConnectionKey.clear();
                    if (key != null) {
                        saveConnectionKey(key);
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

    public void setCheckpointOnClose(boolean set) {
        synchronized (this.mLock) {
            throwIfClosedLocked();
            if (this.mAvailablePrimaryConnection != null) {
                this.mAvailablePrimaryConnection.setCheckpointOnClose(set);
            } else {
                Log.e(
                        TAG,
                        "Could not change 'checkpointOnClose' to "
                                + set
                                + " because the primary connection is on used.");
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x0084, code lost:

       closeAvailableConnectionsAndLogExceptionsLocked();
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void reconfigure(android.database.sqlite.SQLiteDatabaseConfiguration r12) {
        /*
            Method dump skipped, instructions count: 214
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.database.sqlite.SQLiteConnectionPool.reconfigure(android.database.sqlite.SQLiteDatabaseConfiguration):void");
    }

    public void exportDB(String attachedDB) {
        synchronized (this.mLock) {
            throwIfClosedLocked();
            if (!this.mAcquiredConnections.isEmpty()) {
                throw new IllegalStateException(
                        "Release all active connections before starting DB export");
            }
            closeAvailableNonPrimaryConnectionsAndLogExceptionsLocked();
            this.mAvailablePrimaryConnection.exportDB(attachedDB);
        }
    }

    public SQLiteConnection acquireConnection(
            String sql, int connectionFlags, CancellationSignal cancellationSignal) {
        SQLiteConnection con = waitForConnection(sql, connectionFlags, cancellationSignal);
        synchronized (this.mLock) {
            if (this.mIdleConnectionHandler != null) {
                this.mIdleConnectionHandler.connectionAcquired(con);
            }
        }
        return con;
    }

    public void releaseConnection(SQLiteConnection connection) {
        synchronized (this.mLock) {
            if (this.mIdleConnectionHandler != null) {
                this.mIdleConnectionHandler.connectionReleased(connection);
            }
            AcquiredConnectionStatus status = this.mAcquiredConnections.remove(connection);
            if (status == null) {
                throw new IllegalStateException(
                        "Cannot perform this operation because the specified connection was not"
                                + " acquired from this pool or has already been released.");
            }
            if (!this.mIsOpen) {
                closeConnectionAndLogExceptionsLocked(connection);
            } else if (connection.isPrimaryConnection()) {
                if (recycleConnectionLocked(connection, status)) {
                    this.mAvailablePrimaryConnection = connection;
                }
                wakeConnectionWaitersLocked();
            } else if (this.mAvailableNonPrimaryConnections.size()
                    >= this.mMaxConnectionPoolSize - 1) {
                closeConnectionAndLogExceptionsLocked(connection);
            } else {
                if (recycleConnectionLocked(connection, status)) {
                    this.mAvailableNonPrimaryConnections.add(connection);
                }
                wakeConnectionWaitersLocked();
            }
        }
    }

    private boolean recycleConnectionLocked(
            SQLiteConnection connection, AcquiredConnectionStatus status) {
        if (status == AcquiredConnectionStatus.RECONFIGURE) {
            try {
                connection.reconfigure(this.mConfiguration);
            } catch (RuntimeException ex) {
                Log.e(
                        TAG,
                        "Failed to reconfigure released connection, closing it: " + connection,
                        ex);
                status = AcquiredConnectionStatus.DISCARD;
            }
        }
        if (status == AcquiredConnectionStatus.DISCARD) {
            closeConnectionAndLogExceptionsLocked(connection);
            return false;
        }
        if (status == AcquiredConnectionStatus.SHRINK) {
            connection.releaseConnectionMemory();
            return true;
        }
        return true;
    }

    public boolean hasAnyAvailableNonPrimaryConnection() {
        return this.mAvailableNonPrimaryConnections.size() > 0;
    }

    public boolean shouldYieldConnection(SQLiteConnection connection, int connectionFlags) {
        synchronized (this.mLock) {
            if (!this.mAcquiredConnections.containsKey(connection)) {
                throw new IllegalStateException(
                        "Cannot perform this operation because the specified connection was not"
                                + " acquired from this pool or has already been released.");
            }
            if (!this.mIsOpen) {
                return false;
            }
            return isSessionBlockingImportantConnectionWaitersLocked(
                    connection.isPrimaryConnection(), connectionFlags);
        }
    }

    public void collectDbStats(ArrayList<SQLiteDebug.DbStats> dbStatsList) {
        synchronized (this.mLock) {
            if (this.mAvailablePrimaryConnection != null) {
                this.mAvailablePrimaryConnection.collectDbStats(dbStatsList);
            }
            Iterator<SQLiteConnection> it = this.mAvailableNonPrimaryConnections.iterator();
            while (it.hasNext()) {
                SQLiteConnection connection = it.next();
                connection.collectDbStats(dbStatsList);
            }
            for (SQLiteConnection connection2 : this.mAcquiredConnections.keySet()) {
                connection2.collectDbStatsUnsafe(dbStatsList);
            }
            SQLiteDebug.DbStats poolStats =
                    new SQLiteDebug.DbStats(
                            this.mConfiguration.path,
                            0L,
                            0L,
                            0,
                            this.mTotalPrepareStatements - this.mTotalPrepareStatementCacheMiss,
                            this.mTotalPrepareStatementCacheMiss,
                            this.mTotalPrepareStatements,
                            true);
            dbStatsList.add(poolStats);
        }
    }

    private SQLiteConnection openConnectionLocked(
            SQLiteDatabaseConfiguration configuration, boolean primaryConnection) {
        int connectionId = this.mNextConnectionId;
        this.mNextConnectionId = connectionId + 1;
        SQLiteConnection connection =
                SQLiteConnection.open(this, configuration, connectionId, primaryConnection);
        if (primaryConnection && connection.isForcedReadOnlyConnection()) {
            this.mMaxConnectionPoolSize = 1;
            this.mIsPoolSizeFixed = true;
            closeAvailableNonPrimaryConnectionsAndLogExceptionsLocked();
        }
        return connection;
    }

    private SQLiteConnection openSecureConnectionLocked(
            SQLiteDatabaseConfiguration configuration, boolean primaryConnection, byte[] password) {
        int connectionId = this.mNextConnectionId;
        this.mNextConnectionId = connectionId + 1;
        SQLiteConnection connection =
                SQLiteConnection.openSecure(
                        this, configuration, connectionId, primaryConnection, password);
        if (primaryConnection && connection.isForcedReadOnlyConnection()) {
            this.mMaxConnectionPoolSize = 1;
            this.mIsPoolSizeFixed = true;
            closeAvailableNonPrimaryConnectionsAndLogExceptionsLocked();
        }
        return connection;
    }

    void onConnectionLeaked() {
        Log.w(
                TAG,
                "A SQLiteConnection object for database '"
                        + this.mConfiguration.label
                        + "' was leaked!  Please fix your application to end transactions in"
                        + " progress properly and to close the database when it is no longer"
                        + " needed.");
        this.mConnectionLeaked.set(true);
    }

    void onStatementExecuted(long executionTimeMs) {
        this.mTotalStatementsTime.addAndGet(executionTimeMs);
        this.mTotalStatementsCount.incrementAndGet();
    }

    private void closeAvailableConnectionsAndLogExceptionsLocked() {
        closeAvailableNonPrimaryConnectionsAndLogExceptionsLocked();
        if (this.mAvailablePrimaryConnection != null) {
            closeConnectionAndLogExceptionsLocked(this.mAvailablePrimaryConnection);
            this.mAvailablePrimaryConnection = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean closeAvailableConnectionLocked(int connectionId) {
        int count = this.mAvailableNonPrimaryConnections.size();
        for (int i = count - 1; i >= 0; i--) {
            SQLiteConnection c = this.mAvailableNonPrimaryConnections.get(i);
            if (c.getConnectionId() == connectionId) {
                closeConnectionAndLogExceptionsLocked(c);
                this.mAvailableNonPrimaryConnections.remove(i);
                return true;
            }
        }
        if (this.mAvailablePrimaryConnection != null
                && this.mAvailablePrimaryConnection.getConnectionId() == connectionId) {
            closeConnectionAndLogExceptionsLocked(this.mAvailablePrimaryConnection);
            this.mAvailablePrimaryConnection = null;
            return true;
        }
        return false;
    }

    private void closeAvailableNonPrimaryConnectionsAndLogExceptionsLocked() {
        int count = this.mAvailableNonPrimaryConnections.size();
        for (int i = 0; i < count; i++) {
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
        int availableCount = this.mAvailableNonPrimaryConnections.size();
        while (true) {
            int availableCount2 = availableCount - 1;
            if (availableCount > this.mMaxConnectionPoolSize - 1) {
                SQLiteConnection connection =
                        this.mAvailableNonPrimaryConnections.remove(availableCount2);
                closeConnectionAndLogExceptionsLocked(connection);
                availableCount = availableCount2;
            } else {
                return;
            }
        }
    }

    private void closeConnectionAndLogExceptionsLocked(SQLiteConnection connection) {
        try {
            connection.close();
            if (this.mIdleConnectionHandler != null) {
                this.mIdleConnectionHandler.connectionClosed(connection);
            }
        } catch (RuntimeException ex) {
            Log.e(
                    TAG,
                    "Failed to close connection, its fate is now in the hands of the merciful GC: "
                            + connection,
                    ex);
        }
    }

    private void discardAcquiredConnectionsLocked() {
        markAcquiredConnectionsLocked(AcquiredConnectionStatus.DISCARD);
    }

    private void reconfigureAllConnectionsLocked() {
        if (this.mAvailablePrimaryConnection != null) {
            try {
                this.mAvailablePrimaryConnection.reconfigure(this.mConfiguration);
            } catch (RuntimeException ex) {
                Log.e(
                        TAG,
                        "Failed to reconfigure available primary connection, closing it: "
                                + this.mAvailablePrimaryConnection,
                        ex);
                closeConnectionAndLogExceptionsLocked(this.mAvailablePrimaryConnection);
                this.mAvailablePrimaryConnection = null;
            }
        }
        int count = this.mAvailableNonPrimaryConnections.size();
        int i = 0;
        while (i < count) {
            SQLiteConnection connection = this.mAvailableNonPrimaryConnections.get(i);
            try {
                connection.reconfigure(this.mConfiguration);
            } catch (RuntimeException ex2) {
                Log.e(
                        TAG,
                        "Failed to reconfigure available non-primary connection, closing it: "
                                + connection,
                        ex2);
                closeConnectionAndLogExceptionsLocked(connection);
                this.mAvailableNonPrimaryConnections.remove(i);
                count--;
                i--;
            }
            i++;
        }
        markAcquiredConnectionsLocked(AcquiredConnectionStatus.RECONFIGURE);
    }

    private void markAcquiredConnectionsLocked(AcquiredConnectionStatus status) {
        markAcquiredConnectionsLocked(status, true);
    }

    private void markAcquiredConnectionsLocked(
            AcquiredConnectionStatus status, boolean markPrimaryConnection) {
        if (!this.mAcquiredConnections.isEmpty()) {
            ArrayList<SQLiteConnection> keysToUpdate =
                    new ArrayList<>(this.mAcquiredConnections.size());
            for (Map.Entry<SQLiteConnection, AcquiredConnectionStatus> entry :
                    this.mAcquiredConnections.entrySet()) {
                AcquiredConnectionStatus oldStatus = entry.getValue();
                if (status != oldStatus
                        && oldStatus != AcquiredConnectionStatus.DISCARD
                        && (!entry.getKey().isPrimaryConnection() || markPrimaryConnection)) {
                    keysToUpdate.add(entry.getKey());
                }
            }
            int updateCount = keysToUpdate.size();
            for (int i = 0; i < updateCount; i++) {
                this.mAcquiredConnections.put(keysToUpdate.get(i), status);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:68:0x00d2  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x00ea  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:62:? -> B:58:0x00e2). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.database.sqlite.SQLiteConnection waitForConnection(
            java.lang.String r22, int r23, android.os.CancellationSignal r24) {
        /*
            Method dump skipped, instructions count: 245
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.database.sqlite.SQLiteConnectionPool.waitForConnection(java.lang.String,"
                    + " int,"
                    + " android.os.CancellationSignal):android.database.sqlite.SQLiteConnection");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelConnectionWaiterLocked(ConnectionWaiter waiter) {
        if (waiter.mAssignedConnection != null || waiter.mException != null) {
            return;
        }
        ConnectionWaiter predecessor = null;
        for (ConnectionWaiter current = this.mConnectionWaiterQueue;
                current != waiter;
                current = current.mNext) {
            predecessor = current;
        }
        if (predecessor != null) {
            predecessor.mNext = waiter.mNext;
        } else {
            this.mConnectionWaiterQueue = waiter.mNext;
        }
        waiter.mException = new OperationCanceledException();
        LockSupport.unpark(waiter.mThread);
        wakeConnectionWaitersLocked();
    }

    private void logConnectionPoolBusyLocked(long waitMillis, int connectionFlags) {
        Thread thread = Thread.currentThread();
        StringBuilder msg = new StringBuilder();
        msg.append("The connection pool for database '").append(this.mConfiguration.label);
        msg.append("' has been unable to grant a connection to thread ");
        msg.append(thread.getId()).append(" (").append(thread.getName()).append(") ");
        msg.append("with flags 0x").append(Integer.toHexString(connectionFlags));
        msg.append(" for ").append(waitMillis * 0.001f).append(" seconds.\n");
        ArrayList<String> requests = new ArrayList<>();
        int activeConnections = 0;
        int idleConnections = 0;
        if (!this.mAcquiredConnections.isEmpty()) {
            for (SQLiteConnection connection : this.mAcquiredConnections.keySet()) {
                String description = connection.describeCurrentOperationUnsafe();
                if (description != null) {
                    requests.add(description);
                    activeConnections++;
                } else {
                    idleConnections++;
                }
            }
        }
        int availableConnections = this.mAvailableNonPrimaryConnections.size();
        if (this.mAvailablePrimaryConnection != null) {
            availableConnections++;
        }
        msg.append("Connections: ").append(activeConnections).append(" active, ");
        msg.append(idleConnections).append(" idle, ");
        msg.append(availableConnections).append(" available.\n");
        if (!requests.isEmpty()) {
            msg.append("\nRequests in progress:\n");
            Iterator<String> it = requests.iterator();
            while (it.hasNext()) {
                String request = it.next();
                msg.append("  ").append(request).append("\n");
            }
        }
        Log.w(TAG, msg.toString());
    }

    private void wakeConnectionWaitersLocked() {
        ConnectionWaiter predecessor = null;
        ConnectionWaiter waiter = this.mConnectionWaiterQueue;
        boolean primaryConnectionNotAvailable = false;
        boolean nonPrimaryConnectionNotAvailable = false;
        while (waiter != null) {
            boolean unpark = false;
            if (!this.mIsOpen) {
                unpark = true;
            } else {
                SQLiteConnection connection = null;
                try {
                    if (!waiter.mWantPrimaryConnection
                            && !nonPrimaryConnectionNotAvailable
                            && (connection =
                                            tryAcquireNonPrimaryConnectionLocked(
                                                    waiter.mSql, waiter.mConnectionFlags))
                                    == null
                            && isPrimaryConnectionExistsLocked()) {
                        nonPrimaryConnectionNotAvailable = true;
                    }
                    if (connection == null
                            && !primaryConnectionNotAvailable
                            && (connection =
                                            tryAcquirePrimaryConnectionLocked(
                                                    waiter.mConnectionFlags))
                                    == null) {
                        primaryConnectionNotAvailable = true;
                    }
                    if (connection != null) {
                        waiter.mAssignedConnection = connection;
                        unpark = true;
                    } else if (nonPrimaryConnectionNotAvailable && primaryConnectionNotAvailable) {
                        return;
                    }
                } catch (RuntimeException ex) {
                    waiter.mException = ex;
                    unpark = true;
                }
            }
            ConnectionWaiter successor = waiter.mNext;
            if (unpark) {
                if (predecessor != null) {
                    predecessor.mNext = successor;
                } else {
                    this.mConnectionWaiterQueue = successor;
                }
                waiter.mNext = null;
                LockSupport.unpark(waiter.mThread);
            } else {
                predecessor = waiter;
            }
            waiter = successor;
        }
    }

    public void dumpAllConnections(SQLiteDump dbDump) {
        if (dbDump == null || dbDump.mTeePrinter == null) {
            return;
        }
        try {
            synchronized (this.mLock) {
                if (this.mAvailablePrimaryConnection != null) {
                    dbDump.mTeePrinter.println(
                            "The recent request on avilable primary connection for corruption"
                                    + " debug.");
                    this.mAvailablePrimaryConnection.dump(dbDump.mTeePrinter, false);
                }
                if (!this.mAvailableNonPrimaryConnections.isEmpty()) {
                    int count = this.mAvailableNonPrimaryConnections.size();
                    for (int i = 0; i < count; i++) {
                        dbDump.mTeePrinter.println(
                                "The recent request on avilable connection for corruption debug.");
                        this.mAvailableNonPrimaryConnections.get(i).dump(dbDump.mTeePrinter, false);
                    }
                }
                if (!this.mAcquiredConnections.isEmpty()) {
                    for (Map.Entry<SQLiteConnection, AcquiredConnectionStatus> entry :
                            this.mAcquiredConnections.entrySet()) {
                        SQLiteConnection connection = entry.getKey();
                        dbDump.mTeePrinter.println(
                                "The recent request on acquired "
                                        + (connection.isPrimaryConnection() ? "primary" : "")
                                        + " connection for corruption debug.");
                        connection.dumpUnsafe(dbDump.mTeePrinter, false);
                    }
                }
                dbDump.mTeePrinter.println("");
            }
        } catch (Exception e) {
            Log.e(TAG, "dump all connections log failed.");
        }
    }

    private SQLiteConnection tryAcquirePrimaryConnectionLocked(int connectionFlags) {
        SQLiteConnection connection;
        SQLiteConnection connection2 = this.mAvailablePrimaryConnection;
        if (connection2 != null) {
            this.mAvailablePrimaryConnection = null;
            finishAcquireConnectionLocked(connection2, connectionFlags);
            return connection2;
        }
        for (SQLiteConnection acquiredConnection : this.mAcquiredConnections.keySet()) {
            if (acquiredConnection.isPrimaryConnection()) {
                return null;
            }
        }
        if (this.mConfiguration.sharedConfig.isSecureDb) {
            if (this.mConnectionKey == null) {
                throw new IllegalStateException(
                        "Could not open a new primary connection due to the lack of password.");
            }
            connection =
                    openSecureConnectionLocked(
                            this.mConfiguration,
                            true,
                            this.mConnectionKey.decryptAndGet(
                                    SQLiteGlobal.getRandArray(this.mRandArrPtr)));
        } else {
            connection = openConnectionLocked(this.mConfiguration, true);
        }
        finishAcquireConnectionLocked(connection, connectionFlags);
        return connection;
    }

    private boolean isPrimaryConnectionExistsLocked() {
        if (this.mAvailablePrimaryConnection != null) {
            return true;
        }
        if (!this.mAcquiredConnections.isEmpty()) {
            for (SQLiteConnection connection : this.mAcquiredConnections.keySet()) {
                if (connection.isPrimaryConnection()) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    private SQLiteConnection tryAcquireNonPrimaryConnectionLocked(String sql, int connectionFlags) {
        SQLiteConnection connection;
        int availableCount = this.mAvailableNonPrimaryConnections.size();
        if (availableCount > 1 && sql != null) {
            for (int i = 0; i < availableCount; i++) {
                SQLiteConnection connection2 = this.mAvailableNonPrimaryConnections.get(i);
                if (connection2.isPreparedStatementInCache(sql)) {
                    this.mAvailableNonPrimaryConnections.remove(i);
                    finishAcquireConnectionLocked(connection2, connectionFlags);
                    return connection2;
                }
            }
        }
        if (availableCount > 0) {
            SQLiteConnection connection3 =
                    this.mAvailableNonPrimaryConnections.remove(availableCount - 1);
            finishAcquireConnectionLocked(connection3, connectionFlags);
            return connection3;
        }
        int openConnections = this.mAcquiredConnections.size();
        if (this.mAvailablePrimaryConnection != null) {
            openConnections++;
        }
        if (openConnections >= this.mMaxConnectionPoolSize || !isPrimaryConnectionExistsLocked()) {
            return null;
        }
        if (this.mConfiguration.sharedConfig.isSecureDb) {
            try {
                connection =
                        openSecureConnectionLocked(
                                this.mConfiguration,
                                false,
                                this.mConnectionKey.decryptAndGet(
                                        SQLiteGlobal.getRandArray(this.mRandArrPtr)));
            } catch (SQLiteException se) {
                throw se;
            } catch (Exception e) {
                Log.w(TAG, "Unable to open new connection due to lack of key, go on");
                if (SQLiteDebug.NoPreloadHolder.DEBUG_ENABLE) {
                    throw e;
                }
                return null;
            }
        } else {
            connection = openConnectionLocked(this.mConfiguration, false);
        }
        finishAcquireConnectionLocked(connection, connectionFlags);
        return connection;
    }

    private void finishAcquireConnectionLocked(SQLiteConnection connection, int connectionFlags) {
        boolean readOnly = (connectionFlags & 1) != 0;
        try {
            connection.setOnlyAllowReadOnlyOperations(readOnly);
            this.mAcquiredConnections.put(connection, AcquiredConnectionStatus.NORMAL);
        } catch (RuntimeException ex) {
            Log.e(
                    TAG,
                    "Failed to prepare acquired connection for session, closing it: "
                            + connection
                            + ", connectionFlags="
                            + connectionFlags);
            closeConnectionAndLogExceptionsLocked(connection);
            throw ex;
        }
    }

    private boolean isSessionBlockingImportantConnectionWaitersLocked(
            boolean holdingPrimaryConnection, int connectionFlags) {
        ConnectionWaiter waiter = this.mConnectionWaiterQueue;
        if (waiter != null) {
            int priority = getPriority(connectionFlags);
            while (priority <= waiter.mPriority) {
                if (holdingPrimaryConnection || !waiter.mWantPrimaryConnection) {
                    return true;
                }
                waiter = waiter.mNext;
                if (waiter == null) {
                    return false;
                }
            }
            return false;
        }
        return false;
    }

    private static int getPriority(int connectionFlags) {
        return (connectionFlags & 4) != 0 ? 1 : 0;
    }

    private void setMaxConnectionPoolSizeLocked() {
        if (this.mIsPoolSizeFixed && this.mMaxConnectionPoolSize != 0) {
            return;
        }
        if (this.mConfiguration.sharedConfig.useSingleConnectionWal) {
            this.mMaxConnectionPoolSize = 1;
        } else if (this.mConfiguration
                .resolveJournalMode()
                .equalsIgnoreCase(SQLiteDatabase.JOURNAL_MODE_WAL)) {
            this.mMaxConnectionPoolSize = SQLiteGlobal.getWALConnectionPoolSize();
        } else {
            this.mMaxConnectionPoolSize = 1;
        }
    }

    public void setupIdleConnectionHandler(
            Looper looper, long timeoutMs, Runnable onAllConnectionsIdle) {
        synchronized (this.mLock) {
            this.mIdleConnectionHandler =
                    new IdleConnectionHandler(looper, timeoutMs, onAllConnectionsIdle);
        }
    }

    public void setupIdleConnectionShrinkHandler(
            Looper looper, long timeoutMs, Runnable onAllConnectionsIdle) {
        synchronized (this.mLock) {
            this.mIdleConnectionHandler =
                    new IdleConnectionShrinkHandler(looper, timeoutMs, onAllConnectionsIdle);
        }
    }

    void disableIdleConnectionHandler() {
        synchronized (this.mLock) {
            this.mIdleConnectionHandler = null;
        }
    }

    private void throwIfClosedLocked() {
        if (!this.mIsOpen) {
            throw new IllegalStateException(
                    "Cannot perform this operation because the connection pool has been closed.");
        }
    }

    private ConnectionWaiter obtainConnectionWaiterLocked(
            Thread thread,
            long startTime,
            int priority,
            boolean wantPrimaryConnection,
            String sql,
            int connectionFlags) {
        ConnectionWaiter waiter = this.mConnectionWaiterPool;
        if (waiter != null) {
            this.mConnectionWaiterPool = waiter.mNext;
            waiter.mNext = null;
        } else {
            waiter = new ConnectionWaiter();
        }
        waiter.mThread = thread;
        waiter.mStartTime = startTime;
        waiter.mPriority = priority;
        waiter.mWantPrimaryConnection = wantPrimaryConnection;
        waiter.mSql = sql;
        waiter.mConnectionFlags = connectionFlags;
        return waiter;
    }

    private void recycleConnectionWaiterLocked(ConnectionWaiter waiter) {
        waiter.mNext = this.mConnectionWaiterPool;
        waiter.mThread = null;
        waiter.mSql = null;
        waiter.mAssignedConnection = null;
        waiter.mException = null;
        waiter.mNonce++;
        this.mConnectionWaiterPool = waiter;
    }

    void clearAcquiredConnectionsPreparedStatementCache() {
        synchronized (this.mLock) {
            this.mDatabaseSeqNum++;
            if (!this.mAcquiredConnections.isEmpty()) {
                for (SQLiteConnection connection : this.mAcquiredConnections.keySet()) {
                    connection.setDatabaseSeqNum(this.mDatabaseSeqNum);
                }
            }
        }
    }

    public void closeAndDiscardNonPrimaryConnections(boolean needDiscard, boolean fixPoolSize) {
        synchronized (this.mLock) {
            closeAvailableNonPrimaryConnectionsAndLogExceptionsLocked();
            if (needDiscard) {
                markAcquiredConnectionsLocked(AcquiredConnectionStatus.DISCARD, false);
                this.mMaxConnectionPoolSize = 1;
                if (fixPoolSize) {
                    this.mIsPoolSizeFixed = true;
                }
            }
        }
    }

    public void releaseConnectionMemory() {
        synchronized (this.mLock) {
            if (this.mAvailablePrimaryConnection != null) {
                this.mAvailablePrimaryConnection.releaseConnectionMemory();
            }
            Iterator<SQLiteConnection> it = this.mAvailableNonPrimaryConnections.iterator();
            while (it.hasNext()) {
                SQLiteConnection connection = it.next();
                connection.releaseConnectionMemory();
            }
            markAcquiredConnectionsLocked(AcquiredConnectionStatus.SHRINK);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean releaseAvailableConnectionMemoryLocked(int connectionId) {
        int count = this.mAvailableNonPrimaryConnections.size();
        for (int i = count - 1; i >= 0; i--) {
            SQLiteConnection c = this.mAvailableNonPrimaryConnections.get(i);
            if (c.getConnectionId() == connectionId) {
                c.releaseConnectionMemory();
                return true;
            }
        }
        if (this.mAvailablePrimaryConnection != null
                && this.mAvailablePrimaryConnection.getConnectionId() == connectionId) {
            this.mAvailablePrimaryConnection.releaseConnectionMemory();
            return true;
        }
        return false;
    }

    protected SQLiteDatabase getDatabase() {
        return this.mDatabase;
    }

    public void reOpen() {
        SQLiteConnection newPrimaryConnection;
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
                    newPrimaryConnection =
                            openSecureConnectionLocked(
                                    this.mConfiguration,
                                    true,
                                    this.mConnectionKey.decryptAndGet(
                                            SQLiteGlobal.getRandArray(this.mRandArrPtr)));
                } else {
                    newPrimaryConnection = openConnectionLocked(this.mConfiguration, true);
                }
                this.mAvailablePrimaryConnection = newPrimaryConnection;
            }
        }
    }

    public int getMaxConnectionPoolSize() {
        int poolSize;
        synchronized (this.mLock) {
            poolSize = this.mMaxConnectionPoolSize;
        }
        return poolSize;
    }

    public void setMaxConnectionPoolSize(int size) {
        synchronized (this.mLock) {
            this.mMaxConnectionPoolSize = size;
        }
    }

    public boolean isConnectionAcquired() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mAcquiredConnections.size() != 0;
        }
        return z;
    }

    public void dump(Printer printer, boolean verbose, ArraySet<String> directories) {
        Printer indentedPrinter = PrefixPrinter.create(printer, "    ");
        synchronized (this.mLock) {
            if (directories != null) {
                String parent = new File(this.mConfiguration.path).getParent();
                if (parent != null) {
                    directories.add(parent);
                }
            }
            boolean isCompatibilityWalEnabled =
                    this.mConfiguration.isLegacyCompatibilityWalEnabled();
            printer.println("Connection pool for " + this.mConfiguration.path + ":");
            printer.println("  Open: " + this.mIsOpen);
            SQLiteDatabase db = getDatabase();
            if (db != null
                    && db.isUdrEnabled()
                    && SQLiteUserDataRecovery.isDbUdrRecovered(this.mConfiguration.path)) {
                printer.println("  UDR: true");
            }
            printer.println("  Max connections: " + this.mMaxConnectionPoolSize);
            printer.println("  Total execution time (ms): " + this.mTotalStatementsTime);
            printer.println("  Total statements executed: " + this.mTotalStatementsCount);
            if (this.mTotalStatementsCount.get() > 0) {
                printer.println(
                        "  Average time per statement (ms): "
                                + (this.mTotalStatementsTime.get()
                                        / this.mTotalStatementsCount.get()));
            }
            printer.println(
                    "  Configuration: openFlags="
                            + this.mConfiguration.openFlags
                            + ", isLegacyCompatibilityWalEnabled="
                            + isCompatibilityWalEnabled
                            + ", journalMode="
                            + TextUtils.emptyIfNull(this.mConfiguration.resolveJournalMode())
                            + ", syncMode="
                            + TextUtils.emptyIfNull(this.mConfiguration.resolveSyncMode()));
            printer.println("  IsReadOnlyDatabase=" + this.mConfiguration.isReadOnlyDatabase());
            if (isCompatibilityWalEnabled) {
                printer.println(
                        "  Compatibility WAL enabled: wal_syncmode="
                                + SQLiteCompatibilityWalFlags.getWALSyncMode());
            }
            if (this.mConfiguration.isLookasideConfigSet()) {
                printer.println(
                        "  Lookaside config: sz="
                                + this.mConfiguration.lookasideSlotSize
                                + " cnt="
                                + this.mConfiguration.lookasideSlotCount);
            }
            if (this.mConfiguration.idleConnectionTimeoutMs != Long.MAX_VALUE) {
                printer.println(
                        "  Idle connection timeout: "
                                + this.mConfiguration.idleConnectionTimeoutMs);
            }
            if (this.mConfiguration.idleConnectionShrinkTimeoutMs != Long.MAX_VALUE) {
                printer.println(
                        "  Idle connection shrink timeout: "
                                + this.mConfiguration.idleConnectionShrinkTimeoutMs);
            }
            printer.println("  Secure db: " + this.mConfiguration.sharedConfig.isSecureDb);
            if (this.mConfiguration
                    .resolveJournalMode()
                    .equalsIgnoreCase(SQLiteDatabase.JOURNAL_MODE_WAL)) {
                printer.println("  Use WAL mode. ");
            }
            printer.println("  Available primary connection:");
            if (this.mAvailablePrimaryConnection != null) {
                this.mAvailablePrimaryConnection.dump(indentedPrinter, verbose);
            } else {
                indentedPrinter.println("<none>");
            }
            printer.println("  Available non-primary connections:");
            if (!this.mAvailableNonPrimaryConnections.isEmpty()) {
                int count = this.mAvailableNonPrimaryConnections.size();
                for (int i = 0; i < count; i++) {
                    this.mAvailableNonPrimaryConnections.get(i).dump(indentedPrinter, verbose);
                }
            } else {
                indentedPrinter.println("<none>");
            }
            printer.println("  Acquired connections:");
            if (!this.mAcquiredConnections.isEmpty()) {
                for (Map.Entry<SQLiteConnection, AcquiredConnectionStatus> entry :
                        this.mAcquiredConnections.entrySet()) {
                    SQLiteConnection connection = entry.getKey();
                    connection.dumpUnsafe(indentedPrinter, verbose);
                    indentedPrinter.println("  Status: " + entry.getValue());
                }
            } else {
                indentedPrinter.println("<none>");
            }
            printer.println("  Connection waiters:");
            if (this.mConnectionWaiterQueue != null) {
                int i2 = 0;
                long now = SystemClock.uptimeMillis();
                ConnectionWaiter waiter = this.mConnectionWaiterQueue;
                while (waiter != null) {
                    indentedPrinter.println(
                            i2
                                    + ": waited for "
                                    + ((now - waiter.mStartTime) * 0.001f)
                                    + " ms - thread="
                                    + waiter.mThread
                                    + ", priority="
                                    + waiter.mPriority
                                    + ", sql='"
                                    + waiter.mSql
                                    + "'");
                    waiter = waiter.mNext;
                    i2++;
                }
            } else {
                indentedPrinter.println("<none>");
            }
        }
    }

    @NeverCompile
    public double getStatementCacheMissRate() {
        if (this.mTotalPrepareStatements == 0) {
            return SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        }
        return this.mTotalPrepareStatementCacheMiss / this.mTotalPrepareStatements;
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

        private ConnectionWaiter() {}
    }

    private class IdleConnectionHandler extends Handler {
        private final Runnable mOnAllConnectionsIdle;
        protected final long mTimeout;

        IdleConnectionHandler(Looper looper, long timeout, Runnable onAllConnectionsIdle) {
            super(looper);
            this.mTimeout = timeout;
            this.mOnAllConnectionsIdle = onAllConnectionsIdle;
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            synchronized (SQLiteConnectionPool.this.mLock) {
                if (this != SQLiteConnectionPool.this.mIdleConnectionHandler) {
                    return;
                }
                if (SQLiteConnectionPool.this.closeAvailableConnectionLocked(msg.what)
                        && Log.isLoggable(SQLiteConnectionPool.TAG, 3)) {
                    Log.d(
                            SQLiteConnectionPool.TAG,
                            "Closed idle connection "
                                    + SQLiteConnectionPool.this.mConfiguration.label
                                    + " "
                                    + msg.what
                                    + " after "
                                    + this.mTimeout);
                }
                if (this.mOnAllConnectionsIdle != null) {
                    this.mOnAllConnectionsIdle.run();
                }
            }
        }

        void connectionReleased(SQLiteConnection con) {
            sendEmptyMessageDelayed(con.getConnectionId(), this.mTimeout);
        }

        void connectionAcquired(SQLiteConnection con) {
            removeMessages(con.getConnectionId());
        }

        void connectionClosed(SQLiteConnection con) {
            removeMessages(con.getConnectionId());
        }
    }

    private class IdleConnectionShrinkHandler extends IdleConnectionHandler {
        IdleConnectionShrinkHandler(Looper looper, long timeout, Runnable onAllConnectionsIdle) {
            super(looper, timeout, onAllConnectionsIdle);
        }

        @Override // android.database.sqlite.SQLiteConnectionPool.IdleConnectionHandler,
        // android.os.Handler
        public void handleMessage(Message msg) {
            synchronized (SQLiteConnectionPool.this.mLock) {
                if (this != SQLiteConnectionPool.this.mIdleConnectionHandler) {
                    return;
                }
                if (SQLiteConnectionPool.this.releaseAvailableConnectionMemoryLocked(msg.what)
                        && Log.isLoggable(SQLiteConnectionPool.TAG, 3)) {
                    Log.d(
                            SQLiteConnectionPool.TAG,
                            "Released idle connection's memory "
                                    + SQLiteConnectionPool.this.mConfiguration.label
                                    + " "
                                    + msg.what
                                    + " after "
                                    + this.mTimeout);
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

        public SecureData(SQLiteDatabaseConfiguration configuration) {
            this.mConfiguration = configuration;
            SecureRandom sr = new SecureRandom();
            this.mSalt = new byte[16];
            sr.nextBytes(this.mSalt);
        }

        private SecretKeySpec generateKey(char[] password) {
            try {
                PBEKeySpec pbeKeySpec = new PBEKeySpec(password, this.mSalt, 1000, 128);
                SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
                SecretKey key = factory.generateSecret(pbeKeySpec);
                SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
                pbeKeySpec.clearPassword();
                return keySpec;
            } catch (Exception e) {
                throw new RuntimeException(
                        "Fail to generate the data of " + this.mConfiguration.label, e);
            }
        }

        public void encryptAndSave(char[] password, byte[] data) {
            synchronized (this.mLock) {
                if (password == null) {
                    throw new IllegalArgumentException("password should not be null");
                }
                if (data == null) {
                    throw new IllegalArgumentException(
                            "data that will be encrypted should not be null");
                }
                if (this.mEncryptedData != null) {
                    return;
                }
                try {
                    SecureRandom sr = new SecureRandom();
                    sr.nextBytes(this.mIV);
                    SecretKeySpec key = generateKey(password);
                    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                    cipher.init(1, key, new IvParameterSpec(this.mIV));
                    this.mEncryptedData = cipher.doFinal(data);
                } catch (Exception e) {
                    this.mEncryptedData = null;
                    Log.e(
                            SQLiteConnectionPool.TAG,
                            "Could not encrypt the data of " + this.mConfiguration.label);
                    throw new RuntimeException(
                            "Fail to encrpyt the data of " + this.mConfiguration.label, e);
                }
            }
        }

        public byte[] decryptAndGet(char[] password) {
            byte[] data;
            synchronized (this.mLock) {
                try {
                    if (password == null) {
                        throw new IllegalArgumentException("password should not be null");
                    }
                    if (this.mEncryptedData == null) {
                        throw new IllegalStateException("Please encrypt and save data first.");
                    }
                    try {
                        SecretKeySpec key = generateKey(password);
                        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                        cipher.init(2, key, new IvParameterSpec(this.mIV));
                        data = cipher.doFinal(this.mEncryptedData);
                    } catch (Exception e) {
                        Log.e(
                                SQLiteConnectionPool.TAG,
                                "Could not decrypt the data of " + this.mConfiguration.label);
                        throw new RuntimeException(
                                "Fail to decrypt the data of " + this.mConfiguration.label, e);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return data;
        }

        public void clear() {
            synchronized (this.mLock) {
                this.mEncryptedData = null;
            }
        }
    }
}
