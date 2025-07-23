package android.database.sqlite;

import android.database.CursorWindow;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteConnection;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayDeque;

/* loaded from: classes.dex */
public final class SQLiteSession {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int TRANSACTION_MODE_DEFERRED = 0;
    public static final int TRANSACTION_MODE_EXCLUSIVE = 2;
    public static final int TRANSACTION_MODE_IMMEDIATE = 1;
    private SQLiteConnection mConnection;
    private int mConnectionFlags;
    private final SQLiteConnectionPool mConnectionPool;
    private int mConnectionUseCount;
    private final ArrayDeque<Closeable> mOpenDependents = new ArrayDeque<>();
    private Transaction mTransactionPool;
    private Transaction mTransactionStack;

    public SQLiteSession(SQLiteConnectionPool sQLiteConnectionPool) {
        if (sQLiteConnectionPool == null) {
            throw new IllegalArgumentException("connectionPool must not be null");
        }
        this.mConnectionPool = sQLiteConnectionPool;
    }

    public boolean hasTransaction() {
        return this.mTransactionStack != null;
    }

    public boolean hasNestedTransaction() {
        Transaction transaction = this.mTransactionStack;
        return (transaction == null || transaction.mParent == null) ? false : true;
    }

    public boolean hasConnection() {
        return this.mConnection != null;
    }

    public void beginTransaction(int i, SQLiteTransactionListener sQLiteTransactionListener, int i2, CancellationSignal cancellationSignal) {
        throwIfTransactionMarkedSuccessful();
        beginTransactionUnchecked(i, sQLiteTransactionListener, i2, cancellationSignal);
    }

    private String modeString(int i) {
        if (i == 0) {
            return "TRANSACTION-DEFERRED";
        }
        if (i == 1) {
            return "TRANSACTION-IMMEDIATE";
        }
        if (i == 2) {
            return "TRANSACTION-EXCLUSIVE";
        }
        return "TRANSACTION";
    }

    private void beginTransactionUnchecked(int i, SQLiteTransactionListener sQLiteTransactionListener, int i2, CancellationSignal cancellationSignal) {
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
        }
        if (this.mTransactionStack == null) {
            acquireConnection(null, i2, cancellationSignal);
            this.mConnection.recordBeginTransaction(modeString(i));
        }
        try {
            if (this.mTransactionStack == null) {
                if (i == 0) {
                    this.mConnection.execute("BEGIN DEFERRED;", null, cancellationSignal);
                } else if (i == 1) {
                    this.mConnection.execute("BEGIN IMMEDIATE;", null, cancellationSignal);
                } else if (i == 2) {
                    this.mConnection.execute("BEGIN EXCLUSIVE;", null, cancellationSignal);
                } else {
                    this.mConnection.execute("BEGIN;", null, cancellationSignal);
                }
            }
            if (sQLiteTransactionListener != null) {
                try {
                    sQLiteTransactionListener.onBegin();
                } catch (RuntimeException e) {
                    if (this.mTransactionStack == null) {
                        this.mConnection.execute("ROLLBACK;", null, cancellationSignal);
                    }
                    throw e;
                }
            }
            Transaction obtainTransaction = obtainTransaction(i, sQLiteTransactionListener);
            obtainTransaction.mParent = this.mTransactionStack;
            this.mTransactionStack = obtainTransaction;
            if (obtainTransaction == null) {
                releaseConnection();
            }
        } catch (Throwable th) {
            if (this.mTransactionStack == null) {
                releaseConnection();
            }
            throw th;
        }
    }

    public void setTransactionSuccessful() {
        throwIfNoTransaction();
        throwIfTransactionMarkedSuccessful();
        this.mTransactionStack.mMarkedSuccessful = true;
        closeOpenDependents();
    }

    public void endTransaction(CancellationSignal cancellationSignal) {
        throwIfNoTransaction();
        endTransactionUnchecked(cancellationSignal, false);
    }

    private void endTransactionUnchecked(CancellationSignal cancellationSignal, boolean z) {
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
        }
        Transaction transaction = this.mTransactionStack;
        boolean z2 = false;
        boolean z3 = (transaction.mMarkedSuccessful || z) && !transaction.mChildFailed;
        SQLiteTransactionListener sQLiteTransactionListener = transaction.mListener;
        if (sQLiteTransactionListener != null) {
            try {
                if (z3) {
                    sQLiteTransactionListener.onCommit();
                } else {
                    sQLiteTransactionListener.onRollback();
                }
            } catch (RuntimeException e) {
                e = e;
            }
        }
        z2 = z3;
        e = null;
        this.mTransactionStack = transaction.mParent;
        recycleTransaction(transaction);
        Transaction transaction2 = this.mTransactionStack;
        if (transaction2 == null) {
            closeOpenDependents();
            try {
                if (z2) {
                    this.mConnection.execute("COMMIT;", null, cancellationSignal);
                } else {
                    this.mConnection.execute("ROLLBACK;", null, cancellationSignal);
                }
            } finally {
                this.mConnection.recordEndTransaction(z2);
                releaseConnection();
            }
        } else if (!z2) {
            transaction2.mChildFailed = true;
        }
        if (e != null) {
            throw e;
        }
    }

    public boolean yieldTransaction(long j, boolean z, CancellationSignal cancellationSignal) {
        if (z) {
            throwIfNoTransaction();
            throwIfTransactionMarkedSuccessful();
            throwIfNestedTransaction();
        } else {
            Transaction transaction = this.mTransactionStack;
            if (transaction == null || transaction.mMarkedSuccessful || this.mTransactionStack.mParent != null) {
                return false;
            }
        }
        if (this.mTransactionStack.mChildFailed) {
            return false;
        }
        return yieldTransactionUnchecked(j, cancellationSignal);
    }

    private boolean yieldTransactionUnchecked(long j, CancellationSignal cancellationSignal) {
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
        }
        if (!this.mConnectionPool.shouldYieldConnection(this.mConnection, this.mConnectionFlags)) {
            return false;
        }
        int i = this.mTransactionStack.mMode;
        SQLiteTransactionListener sQLiteTransactionListener = this.mTransactionStack.mListener;
        int i2 = this.mConnectionFlags;
        endTransactionUnchecked(cancellationSignal, true);
        if (j > 0) {
            try {
                Thread.sleep(j);
            } catch (InterruptedException unused) {
            }
        }
        beginTransactionUnchecked(i, sQLiteTransactionListener, i2, cancellationSignal);
        return true;
    }

    public void prepare(String str, int i, CancellationSignal cancellationSignal, SQLiteStatementInfo sQLiteStatementInfo) {
        if (str == null) {
            throw new IllegalArgumentException("sql must not be null.");
        }
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
        }
        acquireConnection(str, i, cancellationSignal);
        try {
            this.mConnection.prepare(str, sQLiteStatementInfo);
        } finally {
            releaseConnection();
        }
    }

    public void execute(String str, Object[] objArr, int i, CancellationSignal cancellationSignal) {
        if (str == null) {
            throw new IllegalArgumentException("sql must not be null.");
        }
        if (executeSpecial(str, objArr, i, cancellationSignal)) {
            return;
        }
        acquireConnection(str, i, cancellationSignal);
        try {
            this.mConnection.execute(str, objArr, cancellationSignal);
        } finally {
            releaseConnection();
        }
    }

    public long executeForLong(String str, Object[] objArr, int i, CancellationSignal cancellationSignal) {
        if (str == null) {
            throw new IllegalArgumentException("sql must not be null.");
        }
        if (executeSpecial(str, objArr, i, cancellationSignal)) {
            return 0L;
        }
        acquireConnection(str, i, cancellationSignal);
        try {
            return this.mConnection.executeForLong(str, objArr, cancellationSignal);
        } finally {
            releaseConnection();
        }
    }

    public String executeForString(String str, Object[] objArr, int i, CancellationSignal cancellationSignal) {
        if (str == null) {
            throw new IllegalArgumentException("sql must not be null.");
        }
        if (executeSpecial(str, objArr, i, cancellationSignal)) {
            return null;
        }
        acquireConnection(str, i, cancellationSignal);
        try {
            return this.mConnection.executeForString(str, objArr, cancellationSignal);
        } finally {
            releaseConnection();
        }
    }

    public ParcelFileDescriptor executeForBlobFileDescriptor(String str, Object[] objArr, int i, CancellationSignal cancellationSignal) {
        if (str == null) {
            throw new IllegalArgumentException("sql must not be null.");
        }
        if (executeSpecial(str, objArr, i, cancellationSignal)) {
            return null;
        }
        acquireConnection(str, i, cancellationSignal);
        try {
            return this.mConnection.executeForBlobFileDescriptor(str, objArr, cancellationSignal);
        } finally {
            releaseConnection();
        }
    }

    public int executeForChangedRowCount(String str, Object[] objArr, int i, CancellationSignal cancellationSignal) {
        if (str == null) {
            throw new IllegalArgumentException("sql must not be null.");
        }
        if (executeSpecial(str, objArr, i, cancellationSignal)) {
            return 0;
        }
        acquireConnection(str, i, cancellationSignal);
        try {
            return this.mConnection.executeForChangedRowCount(str, objArr, cancellationSignal);
        } finally {
            releaseConnection();
        }
    }

    public long executeForLastInsertedRowId(String str, Object[] objArr, int i, CancellationSignal cancellationSignal) {
        if (str == null) {
            throw new IllegalArgumentException("sql must not be null.");
        }
        if (executeSpecial(str, objArr, i, cancellationSignal)) {
            return 0L;
        }
        acquireConnection(str, i, cancellationSignal);
        try {
            return this.mConnection.executeForLastInsertedRowId(str, objArr, cancellationSignal);
        } finally {
            releaseConnection();
        }
    }

    public int executeForCursorWindow(String str, Object[] objArr, CursorWindow cursorWindow, int i, int i2, boolean z, int i3, CancellationSignal cancellationSignal) {
        if (str == null) {
            throw new IllegalArgumentException("sql must not be null.");
        }
        if (cursorWindow == null) {
            throw new IllegalArgumentException("window must not be null.");
        }
        if (executeSpecial(str, objArr, i3, cancellationSignal)) {
            cursorWindow.clear();
            return 0;
        }
        acquireConnection(str, i3, cancellationSignal);
        try {
            return this.mConnection.executeForCursorWindow(str, objArr, cursorWindow, i, i2, z, cancellationSignal);
        } finally {
            releaseConnection();
        }
    }

    public void changePassword(byte[] bArr) {
        acquireConnection(null, 2, null);
        try {
            this.mConnectionPool.changePassword(this.mConnection, bArr);
        } finally {
            releaseConnection();
        }
    }

    public boolean isForcedReadOnlyDatabase() {
        acquireConnection(null, 2, null);
        try {
            return this.mConnection.isForcedReadOnlyConnection();
        } finally {
            releaseConnection();
        }
    }

    private boolean executeSpecial(String str, Object[] objArr, int i, CancellationSignal cancellationSignal) {
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
        }
        int sqlStatementType = DatabaseUtils.getSqlStatementType(str);
        if (sqlStatementType == 4) {
            beginTransaction(2, null, i, cancellationSignal);
            return true;
        }
        if (sqlStatementType == 5) {
            setTransactionSuccessful();
            endTransaction(cancellationSignal);
            return true;
        }
        if (sqlStatementType != 6) {
            return false;
        }
        endTransaction(cancellationSignal);
        return true;
    }

    private void acquireConnection(String str, int i, CancellationSignal cancellationSignal) {
        if (this.mConnection == null) {
            this.mConnection = this.mConnectionPool.acquireConnection(str, i, cancellationSignal);
            this.mConnectionFlags = i;
        }
        this.mConnectionUseCount++;
    }

    private void releaseConnection() {
        int i = this.mConnectionUseCount - 1;
        this.mConnectionUseCount = i;
        if (i == 0) {
            try {
                this.mConnectionPool.releaseConnection(this.mConnection);
            } finally {
                this.mConnection = null;
            }
        }
    }

    SQLiteConnection.PreparedStatement acquirePersistentStatement(String str, Closeable closeable) {
        throwIfNoTransaction();
        throwIfTransactionMarkedSuccessful();
        this.mOpenDependents.addFirst(closeable);
        try {
            return this.mConnection.acquirePersistentStatement(str);
        } catch (Throwable th) {
            this.mOpenDependents.remove(closeable);
            throw th;
        }
    }

    void releasePersistentStatement(SQLiteConnection.PreparedStatement preparedStatement, Closeable closeable) {
        this.mConnection.releasePreparedStatement(preparedStatement);
        this.mOpenDependents.remove(closeable);
    }

    void closeOpenDependents() {
        while (this.mOpenDependents.size() > 0) {
            Closeable pollFirst = this.mOpenDependents.pollFirst();
            if (pollFirst != null) {
                try {
                    pollFirst.close();
                } catch (IOException unused) {
                }
            }
        }
    }

    long getLastInsertRowId() {
        throwIfNoTransaction();
        return this.mConnection.getLastInsertRowId();
    }

    long getLastChangedRowCount() {
        throwIfNoTransaction();
        return this.mConnection.getLastChangedRowCount();
    }

    long getTotalChangedRowCount() {
        throwIfNoTransaction();
        return this.mConnection.getTotalChangedRowCount();
    }

    void throwIfNoTransaction() {
        if (this.mTransactionStack == null) {
            throw new IllegalStateException("Cannot perform this operation because there is no current transaction.");
        }
    }

    private void throwIfTransactionMarkedSuccessful() {
        Transaction transaction = this.mTransactionStack;
        if (transaction != null && transaction.mMarkedSuccessful) {
            throw new IllegalStateException("Cannot perform this operation because the transaction has already been marked successful.  The only thing you can do now is call endTransaction().");
        }
    }

    private void throwIfNestedTransaction() {
        if (hasNestedTransaction()) {
            throw new IllegalStateException("Cannot perform this operation because a nested transaction is in progress.");
        }
    }

    private Transaction obtainTransaction(int i, SQLiteTransactionListener sQLiteTransactionListener) {
        Transaction transaction = this.mTransactionPool;
        if (transaction != null) {
            this.mTransactionPool = transaction.mParent;
            transaction.mParent = null;
            transaction.mMarkedSuccessful = false;
            transaction.mChildFailed = false;
        } else {
            transaction = new Transaction();
        }
        transaction.mMode = i;
        transaction.mListener = sQLiteTransactionListener;
        return transaction;
    }

    private void recycleTransaction(Transaction transaction) {
        transaction.mParent = this.mTransactionPool;
        transaction.mListener = null;
        this.mTransactionPool = transaction;
    }

    private static final class Transaction {
        public boolean mChildFailed;
        public SQLiteTransactionListener mListener;
        public boolean mMarkedSuccessful;
        public int mMode;
        public Transaction mParent;

        private Transaction() {
        }
    }
}
