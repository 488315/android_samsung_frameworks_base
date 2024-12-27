package android.database.sqlite;

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
    public @interface SQLiteDataType {}

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
    private static native int nativeColumnBuffer(
            long j, int i, byte[] bArr, int i2, int i3, int i4);

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

    SQLiteRawStatement(SQLiteDatabase db, String sql) {
        this.mDatabase = db;
        this.mSession = this.mDatabase.getThreadSession();
        this.mSession.throwIfNoTransaction();
        this.mSql = sql;
        this.mPreparedStatement = this.mSession.acquirePersistentStatement(this.mSql, this);
        this.mStatement = this.mPreparedStatement.mStatementPtr;
    }

    private void throwIfInvalid() {
        if (this.mThread != Thread.currentThread()) {
            if (this.mThread == null) {
                throw new IllegalStateException("method called on a closed statement");
            }
            throw new IllegalStateException("method called on a foreign thread: " + this.mThread);
        }
    }

    private void throwIfInvalidBounds(int arrayLength, int offset, int length) {
        if (arrayLength < 0) {
            throw new IllegalArgumentException("invalid array length " + arrayLength);
        }
        if (offset < 0 || offset >= arrayLength) {
            throw new IllegalArgumentException(
                    "invalid offset " + offset + " for array length " + arrayLength);
        }
        if (length <= 0 || arrayLength - offset < length) {
            throw new IllegalArgumentException(
                    "invalid offset "
                            + offset
                            + " and length "
                            + length
                            + " for array length "
                            + arrayLength);
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
            int err = nativeStep(this.mStatement, true);
            switch (err) {
                case 5:
                    throw new SQLiteDatabaseLockedException("database " + this.mDatabase + " busy");
                case 6:
                    throw new SQLiteDatabaseLockedException(
                            "database " + this.mDatabase + " locked");
                case 100:
                    return true;
                case 101:
                    Reference.reachabilityFence(this);
                    return false;
                default:
                    throw new SQLiteException("unknown error " + err);
            }
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public int stepNoThrow() {
        throwIfInvalid();
        try {
            int err = nativeStep(this.mStatement, false);
            if (err != 100 && err != 101 && err != 0) {
                Log.e(TAG, "stepNoThrow() got error " + err + " for SQL: " + this.mSql);
            }
            return err;
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

    public int getParameterIndex(String name) {
        Objects.requireNonNull(name);
        throwIfInvalid();
        try {
            return nativeBindParameterIndex(this.mStatement, name);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public String getParameterName(int parameterIndex) {
        throwIfInvalid();
        try {
            return nativeBindParameterName(this.mStatement, parameterIndex);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public void bindBlob(int parameterIndex, byte[] value) {
        Objects.requireNonNull(value);
        throwIfInvalid();
        try {
            nativeBindBlob(this.mStatement, parameterIndex, value, 0, value.length);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public void bindBlob(int parameterIndex, byte[] value, int offset, int length) {
        Objects.requireNonNull(value);
        throwIfInvalid();
        throwIfInvalidBounds(value.length, offset, length);
        try {
            nativeBindBlob(this.mStatement, parameterIndex, value, offset, length);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public void bindDouble(int parameterIndex, double value) {
        throwIfInvalid();
        try {
            nativeBindDouble(this.mStatement, parameterIndex, value);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public void bindInt(int parameterIndex, int value) {
        throwIfInvalid();
        try {
            nativeBindInt(this.mStatement, parameterIndex, value);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public void bindLong(int parameterIndex, long value) {
        throwIfInvalid();
        try {
            nativeBindLong(this.mStatement, parameterIndex, value);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public void bindNull(int parameterIndex) {
        throwIfInvalid();
        try {
            nativeBindNull(this.mStatement, parameterIndex);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public void bindText(int parameterIndex, String value) {
        Objects.requireNonNull(value);
        throwIfInvalid();
        try {
            nativeBindText(this.mStatement, parameterIndex, value);
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

    public int getColumnType(int columnIndex) {
        throwIfInvalid();
        try {
            return nativeColumnType(this.mStatement, columnIndex);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public String getColumnName(int columnIndex) {
        throwIfInvalid();
        try {
            return nativeColumnName(this.mStatement, columnIndex);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public int getColumnLength(int columnIndex) {
        throwIfInvalid();
        try {
            return nativeColumnBytes(this.mStatement, columnIndex);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public byte[] getColumnBlob(int columnIndex) {
        throwIfInvalid();
        try {
            return nativeColumnBlob(this.mStatement, columnIndex);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public int readColumnBlob(
            int columnIndex, byte[] buffer, int offset, int length, int srcOffset) {
        Objects.requireNonNull(buffer);
        throwIfInvalid();
        throwIfInvalidBounds(buffer.length, offset, length);
        try {
            return nativeColumnBuffer(
                    this.mStatement, columnIndex, buffer, offset, length, srcOffset);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public double getColumnDouble(int columnIndex) {
        throwIfInvalid();
        try {
            return nativeColumnDouble(this.mStatement, columnIndex);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public int getColumnInt(int columnIndex) {
        throwIfInvalid();
        try {
            return nativeColumnInt(this.mStatement, columnIndex);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public long getColumnLong(int columnIndex) {
        throwIfInvalid();
        try {
            return nativeColumnLong(this.mStatement, columnIndex);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public String getColumnText(int columnIndex) {
        throwIfInvalid();
        try {
            return nativeColumnText(this.mStatement, columnIndex);
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
