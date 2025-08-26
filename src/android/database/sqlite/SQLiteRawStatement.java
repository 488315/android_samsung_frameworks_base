package android.database.sqlite;

import android.database.sqlite.SQLiteConnection;
import android.util.Log;
import dalvik.annotation.optimization.FastNative;
import java.io.Closeable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.Reference;
import java.util.Objects;

/* loaded from: classes.dex */
public final class SQLiteRawStatement implements Closeable {
    private static final int SQLITE_BUSY = 5;
    public static final int SQLITE_DATA_TYPE_BLOB = 4;
    public static final int SQLITE_DATA_TYPE_FLOAT = 2;
    public static final int SQLITE_DATA_TYPE_INTEGER = 1;
    public static final int SQLITE_DATA_TYPE_NULL = 5;
    public static final int SQLITE_DATA_TYPE_TEXT = 3;
    private static final int SQLITE_DONE = 101;
    private static final int SQLITE_LOCKED = 6;
    private static final int SQLITE_OK = 0;
    private static final int SQLITE_ROW = 100;
    private static final String TAG = "SQLiteRawStatement";
    private final SQLiteDatabase mDatabase;
    private SQLiteConnection.PreparedStatement mPreparedStatement;
    private final SQLiteSession mSession;
    private final String mSql;
    private final long mStatement;
    private Thread mThread = Thread.currentThread();

    @Retention(RetentionPolicy.SOURCE)
    public @interface SQLiteDataType {
    }

    @FastNative
    private static native void nativeBindBlob(long j, int i, byte[] bArr, int i2, int i3);

    @FastNative
    private static native void nativeBindDouble(long j, int i, double d);

    @FastNative
    private static native void nativeBindInt(long j, int i, int i2);

    @FastNative
    private static native void nativeBindLong(long j, int i, long j2);

    @FastNative
    private static native void nativeBindNull(long j, int i);

    @FastNative
    private static native int nativeBindParameterCount(long j);

    @FastNative
    private static native int nativeBindParameterIndex(long j, String str);

    @FastNative
    private static native String nativeBindParameterName(long j, int i);

    @FastNative
    private static native void nativeBindText(long j, int i, String str);

    @FastNative
    private static native void nativeClearBindings(long j);

    @FastNative
    private static native byte[] nativeColumnBlob(long j, int i);

    @FastNative
    private static native int nativeColumnBuffer(long j, int i, byte[] bArr, int i2, int i3, int i4);

    @FastNative
    private static native int nativeColumnBytes(long j, int i);

    @FastNative
    private static native int nativeColumnCount(long j);

    @FastNative
    private static native double nativeColumnDouble(long j, int i);

    @FastNative
    private static native int nativeColumnInt(long j, int i);

    @FastNative
    private static native long nativeColumnLong(long j, int i);

    @FastNative
    private static native String nativeColumnName(long j, int i);

    @FastNative
    private static native String nativeColumnText(long j, int i);

    @FastNative
    private static native int nativeColumnType(long j, int i);

    private static native void nativeReset(long j, boolean z);

    private static native int nativeStep(long j, boolean z);

    SQLiteRawStatement(SQLiteDatabase sQLiteDatabase, String str) {
        this.mDatabase = sQLiteDatabase;
        SQLiteSession threadSession = sQLiteDatabase.getThreadSession();
        this.mSession = threadSession;
        threadSession.throwIfNoTransaction();
        this.mSql = str;
        SQLiteConnection.PreparedStatement preparedStatementAcquirePersistentStatement = threadSession.acquirePersistentStatement(str, this);
        this.mPreparedStatement = preparedStatementAcquirePersistentStatement;
        this.mStatement = preparedStatementAcquirePersistentStatement.mStatementPtr;
    }

    private void throwIfInvalid() {
        if (this.mThread != Thread.currentThread()) {
            if (this.mThread == null) {
                throw new IllegalStateException("method called on a closed statement");
            }
            throw new IllegalStateException("method called on a foreign thread: " + this.mThread);
        }
    }

    private void throwIfInvalidBounds(int i, int i2, int i3) {
        if (i < 0) {
            throw new IllegalArgumentException("invalid array length " + i);
        }
        if (i2 < 0 || i2 >= i) {
            throw new IllegalArgumentException("invalid offset " + i2 + " for array length " + i);
        }
        if (i3 <= 0 || i - i2 < i3) {
            throw new IllegalArgumentException("invalid offset " + i2 + " and length " + i3 + " for array length " + i);
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        if (this.mThread != null) {
            throwIfInvalid();
            this.mSession.releasePersistentStatement(this.mPreparedStatement, this);
            this.mThread = null;
        }
    }

    public boolean isOpen() {
        return this.mThread != null;
    }

    public boolean step() {
        throwIfInvalid();
        try {
            int iNativeStep = nativeStep(this.mStatement, true);
            if (iNativeStep == 5) {
                throw new SQLiteDatabaseLockedException("database " + this.mDatabase + " busy");
            }
            if (iNativeStep == 6) {
                throw new SQLiteDatabaseLockedException("database " + this.mDatabase + " locked");
            }
            if (iNativeStep == 100) {
                return true;
            }
            if (iNativeStep != 101) {
                throw new SQLiteException("unknown error " + iNativeStep);
            }
            Reference.reachabilityFence(this);
            return false;
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public int stepNoThrow() {
        throwIfInvalid();
        try {
            int iNativeStep = nativeStep(this.mStatement, false);
            if (iNativeStep != 100 && iNativeStep != 101 && iNativeStep != 0) {
                Log.e(TAG, "stepNoThrow() got error " + iNativeStep + " for SQL: " + this.mSql);
            }
            return iNativeStep;
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public void reset() {
        throwIfInvalid();
        try {
            nativeReset(this.mStatement, false);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public void clearBindings() {
        throwIfInvalid();
        try {
            nativeClearBindings(this.mStatement);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public int getParameterCount() {
        throwIfInvalid();
        try {
            return nativeBindParameterCount(this.mStatement);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public int getParameterIndex(String str) {
        Objects.requireNonNull(str);
        throwIfInvalid();
        try {
            return nativeBindParameterIndex(this.mStatement, str);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public String getParameterName(int i) {
        throwIfInvalid();
        try {
            return nativeBindParameterName(this.mStatement, i);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public void bindBlob(int i, byte[] bArr) {
        Objects.requireNonNull(bArr);
        throwIfInvalid();
        try {
            nativeBindBlob(this.mStatement, i, bArr, 0, bArr.length);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public void bindBlob(int i, byte[] bArr, int i2, int i3) {
        Objects.requireNonNull(bArr);
        throwIfInvalid();
        throwIfInvalidBounds(bArr.length, i2, i3);
        try {
            nativeBindBlob(this.mStatement, i, bArr, i2, i3);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public void bindDouble(int i, double d) {
        throwIfInvalid();
        try {
            nativeBindDouble(this.mStatement, i, d);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public void bindInt(int i, int i2) {
        throwIfInvalid();
        try {
            nativeBindInt(this.mStatement, i, i2);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public void bindLong(int i, long j) {
        throwIfInvalid();
        try {
            nativeBindLong(this.mStatement, i, j);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public void bindNull(int i) {
        throwIfInvalid();
        try {
            nativeBindNull(this.mStatement, i);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public void bindText(int i, String str) {
        Objects.requireNonNull(str);
        throwIfInvalid();
        try {
            nativeBindText(this.mStatement, i, str);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public int getResultColumnCount() {
        throwIfInvalid();
        try {
            return nativeColumnCount(this.mStatement);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public int getColumnType(int i) {
        throwIfInvalid();
        try {
            return nativeColumnType(this.mStatement, i);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public String getColumnName(int i) {
        throwIfInvalid();
        try {
            return nativeColumnName(this.mStatement, i);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public int getColumnLength(int i) {
        throwIfInvalid();
        try {
            return nativeColumnBytes(this.mStatement, i);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public byte[] getColumnBlob(int i) {
        throwIfInvalid();
        try {
            return nativeColumnBlob(this.mStatement, i);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public int readColumnBlob(int i, byte[] bArr, int i2, int i3, int i4) {
        Objects.requireNonNull(bArr);
        throwIfInvalid();
        throwIfInvalidBounds(bArr.length, i2, i3);
        try {
            return nativeColumnBuffer(this.mStatement, i, bArr, i2, i3, i4);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public double getColumnDouble(int i) {
        throwIfInvalid();
        try {
            return nativeColumnDouble(this.mStatement, i);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public int getColumnInt(int i) {
        throwIfInvalid();
        try {
            return nativeColumnInt(this.mStatement, i);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public long getColumnLong(int i) {
        throwIfInvalid();
        try {
            return nativeColumnLong(this.mStatement, i);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public String getColumnText(int i) {
        throwIfInvalid();
        try {
            return nativeColumnText(this.mStatement, i);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public String toString() {
        if (isOpen()) {
            return "SQLiteRawStatement: " + this.mSql;
        }
        return "SQLiteRawStatement: (closed) " + this.mSql;
    }
}
