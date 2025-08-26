package androidx.sqlite.driver;

import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import androidx.sqlite.SQLite;
import androidx.sqlite.SQLiteStatement;
import androidx.sqlite.driver.AndroidSQLiteStatement;
import java.util.Arrays;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public abstract class AndroidSQLiteStatement implements SQLiteStatement {
    public static final Companion Companion = new Companion(null);
    public final SQLiteDatabase db;
    public boolean isClosed;
    public final String sql;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class SelectAndroidSQLiteStatement extends AndroidSQLiteStatement {
        public static final /* synthetic */ int $r8$clinit = 0;
        public int[] bindingTypes;
        public byte[][] blobBindings;
        public Cursor cursor;
        public double[] doubleBindings;
        public long[] longBindings;
        public String[] stringBindings;

        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        static {
            new Companion(null);
        }

        public SelectAndroidSQLiteStatement(SQLiteDatabase sQLiteDatabase, String str) {
            super(sQLiteDatabase, str, null);
            this.bindingTypes = new int[0];
            this.longBindings = new long[0];
            this.doubleBindings = new double[0];
            this.stringBindings = new String[0];
            this.blobBindings = new byte[0][];
        }

        public static void throwIfInvalidColumn$1(Cursor cursor, int i) {
            if (i < 0 || i >= cursor.getColumnCount()) {
                SQLite.throwSQLiteException(25, "column index out of range");
                throw null;
            }
        }

        @Override // androidx.sqlite.SQLiteStatement
        public final void bindLong(int i, long j) {
            throwIfClosed();
            ensureCapacity$1(1, i);
            this.bindingTypes[i] = 1;
            this.longBindings[i] = j;
        }

        @Override // androidx.sqlite.SQLiteStatement
        public final void bindNull(int i) {
            throwIfClosed();
            ensureCapacity$1(5, i);
            this.bindingTypes[i] = 5;
        }

        @Override // androidx.sqlite.SQLiteStatement
        public final void bindText(int i, String str) {
            throwIfClosed();
            ensureCapacity$1(3, i);
            this.bindingTypes[i] = 3;
            this.stringBindings[i] = str;
        }

        @Override // java.lang.AutoCloseable
        public final void close() {
            if (!this.isClosed) {
                reset();
            }
            this.isClosed = true;
        }

        public final void ensureCapacity$1(int i, int i2) {
            int i3 = i2 + 1;
            int[] iArr = this.bindingTypes;
            if (iArr.length < i3) {
                this.bindingTypes = Arrays.copyOf(iArr, i3);
            }
            if (i == 1) {
                long[] jArr = this.longBindings;
                if (jArr.length < i3) {
                    this.longBindings = Arrays.copyOf(jArr, i3);
                    return;
                }
                return;
            }
            if (i == 2) {
                double[] dArr = this.doubleBindings;
                if (dArr.length < i3) {
                    this.doubleBindings = Arrays.copyOf(dArr, i3);
                    return;
                }
                return;
            }
            if (i == 3) {
                String[] strArr = this.stringBindings;
                if (strArr.length < i3) {
                    this.stringBindings = (String[]) Arrays.copyOf(strArr, i3);
                    return;
                }
                return;
            }
            if (i != 4) {
                return;
            }
            byte[][] bArr = this.blobBindings;
            if (bArr.length < i3) {
                this.blobBindings = (byte[][]) Arrays.copyOf(bArr, i3);
            }
        }

        public final void ensureCursor$1() {
            if (this.cursor == null) {
                this.cursor = this.db.rawQueryWithFactory(new SQLiteDatabase.CursorFactory() { // from class: androidx.sqlite.driver.AndroidSQLiteStatement$SelectAndroidSQLiteStatement$$ExternalSyntheticLambda0
                    @Override // android.database.sqlite.SQLiteDatabase.CursorFactory
                    public final Cursor newCursor(SQLiteDatabase sQLiteDatabase, SQLiteCursorDriver sQLiteCursorDriver, String str, SQLiteQuery sQLiteQuery) {
                        AndroidSQLiteStatement.SelectAndroidSQLiteStatement selectAndroidSQLiteStatement = this.f$0;
                        int i = AndroidSQLiteStatement.SelectAndroidSQLiteStatement.$r8$clinit;
                        sQLiteQuery.getClass();
                        int length = selectAndroidSQLiteStatement.bindingTypes.length;
                        for (int i2 = 1; i2 < length; i2++) {
                            int i3 = selectAndroidSQLiteStatement.bindingTypes[i2];
                            if (i3 == 1) {
                                sQLiteQuery.bindLong(i2, selectAndroidSQLiteStatement.longBindings[i2]);
                            } else if (i3 == 2) {
                                sQLiteQuery.bindDouble(i2, selectAndroidSQLiteStatement.doubleBindings[i2]);
                            } else if (i3 == 3) {
                                sQLiteQuery.bindString(i2, selectAndroidSQLiteStatement.stringBindings[i2]);
                            } else if (i3 == 4) {
                                sQLiteQuery.bindBlob(i2, selectAndroidSQLiteStatement.blobBindings[i2]);
                            } else if (i3 == 5) {
                                sQLiteQuery.bindNull(i2);
                            }
                        }
                        return new SQLiteCursor(sQLiteCursorDriver, str, sQLiteQuery);
                    }
                }, this.sql, new String[0], null);
            }
        }

        @Override // androidx.sqlite.SQLiteStatement
        public final int getColumnCount() {
            throwIfClosed();
            ensureCursor$1();
            Cursor cursor = this.cursor;
            if (cursor != null) {
                return cursor.getColumnCount();
            }
            return 0;
        }

        @Override // androidx.sqlite.SQLiteStatement
        public final String getColumnName(int i) {
            throwIfClosed();
            ensureCursor$1();
            Cursor cursor = this.cursor;
            if (cursor == null) {
                throw new IllegalStateException("Required value was null.");
            }
            throwIfInvalidColumn$1(cursor, i);
            return cursor.getColumnName(i);
        }

        @Override // androidx.sqlite.SQLiteStatement
        public final long getLong(int i) {
            throwIfClosed();
            Cursor cursor = this.cursor;
            if (cursor != null) {
                throwIfInvalidColumn$1(cursor, i);
                return cursor.getLong(i);
            }
            SQLite.throwSQLiteException(21, "no row");
            throw null;
        }

        @Override // androidx.sqlite.SQLiteStatement
        public final String getText(int i) {
            throwIfClosed();
            Cursor cursor = this.cursor;
            if (cursor != null) {
                throwIfInvalidColumn$1(cursor, i);
                return cursor.getString(i);
            }
            SQLite.throwSQLiteException(21, "no row");
            throw null;
        }

        @Override // androidx.sqlite.SQLiteStatement
        public final boolean isNull(int i) {
            throwIfClosed();
            Cursor cursor = this.cursor;
            if (cursor != null) {
                throwIfInvalidColumn$1(cursor, i);
                return cursor.isNull(i);
            }
            SQLite.throwSQLiteException(21, "no row");
            throw null;
        }

        @Override // androidx.sqlite.SQLiteStatement
        public final void reset() {
            throwIfClosed();
            Cursor cursor = this.cursor;
            if (cursor != null) {
                cursor.close();
            }
            this.cursor = null;
        }

        @Override // androidx.sqlite.SQLiteStatement
        public final boolean step() {
            throwIfClosed();
            ensureCursor$1();
            Cursor cursor = this.cursor;
            if (cursor != null) {
                return cursor.moveToNext();
            }
            throw new IllegalStateException("Required value was null.");
        }
    }

    public /* synthetic */ AndroidSQLiteStatement(SQLiteDatabase sQLiteDatabase, String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(sQLiteDatabase, str);
    }

    public final void throwIfClosed() {
        if (this.isClosed) {
            SQLite.throwSQLiteException(21, "statement is closed");
            throw null;
        }
    }

    private AndroidSQLiteStatement(SQLiteDatabase sQLiteDatabase, String str) {
        this.db = sQLiteDatabase;
        this.sql = str;
    }

    public final class OtherAndroidSQLiteStatement extends AndroidSQLiteStatement {
        public final android.database.sqlite.SQLiteStatement delegate;

        public OtherAndroidSQLiteStatement(SQLiteDatabase sQLiteDatabase, String str) {
            super(sQLiteDatabase, str, null);
            this.delegate = sQLiteDatabase.compileStatement(str);
        }

        @Override // androidx.sqlite.SQLiteStatement
        public final void bindLong(int i, long j) {
            throwIfClosed();
            this.delegate.bindLong(i, j);
        }

        @Override // androidx.sqlite.SQLiteStatement
        public final void bindNull(int i) {
            throwIfClosed();
            this.delegate.bindNull(i);
        }

        @Override // androidx.sqlite.SQLiteStatement
        public final void bindText(int i, String str) {
            throwIfClosed();
            this.delegate.bindString(i, str);
        }

        @Override // java.lang.AutoCloseable
        public final void close() {
            this.delegate.close();
            this.isClosed = true;
        }

        @Override // androidx.sqlite.SQLiteStatement
        public final int getColumnCount() {
            throwIfClosed();
            return 0;
        }

        @Override // androidx.sqlite.SQLiteStatement
        public final String getColumnName(int i) {
            throwIfClosed();
            SQLite.throwSQLiteException(21, "no row");
            throw null;
        }

        @Override // androidx.sqlite.SQLiteStatement
        public final long getLong(int i) {
            throwIfClosed();
            SQLite.throwSQLiteException(21, "no row");
            throw null;
        }

        @Override // androidx.sqlite.SQLiteStatement
        public final String getText(int i) {
            throwIfClosed();
            SQLite.throwSQLiteException(21, "no row");
            throw null;
        }

        @Override // androidx.sqlite.SQLiteStatement
        public final boolean isNull(int i) {
            throwIfClosed();
            SQLite.throwSQLiteException(21, "no row");
            throw null;
        }

        @Override // androidx.sqlite.SQLiteStatement
        public final boolean step() {
            throwIfClosed();
            this.delegate.execute();
            return false;
        }

        @Override // androidx.sqlite.SQLiteStatement
        public final void reset() {
        }
    }
}
