package androidx.room.driver;

import android.database.Cursor;
import androidx.room.driver.SupportSQLiteStatement;
import androidx.sqlite.SQLite;
import androidx.sqlite.SQLiteStatement;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteProgram;
import androidx.sqlite.db.SupportSQLiteQuery;
import java.util.Arrays;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class SupportSQLiteStatement implements SQLiteStatement {
    public static final Companion Companion = new Companion(null);
    public final SupportSQLiteDatabase db;
    public boolean isClosed;
    public final String sql;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SupportAndroidSQLiteStatement extends SupportSQLiteStatement {
        public int[] bindingTypes;
        public byte[][] blobBindings;
        public Cursor cursor;
        public double[] doubleBindings;
        public long[] longBindings;
        public String[] stringBindings;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

        public SupportAndroidSQLiteStatement(SupportSQLiteDatabase supportSQLiteDatabase, String str) {
            super(supportSQLiteDatabase, str, null);
            this.bindingTypes = new int[0];
            this.longBindings = new long[0];
            this.doubleBindings = new double[0];
            this.stringBindings = new String[0];
            this.blobBindings = new byte[0][];
        }

        public static void throwIfInvalidColumn(Cursor cursor, int i) {
            if (i < 0 || i >= cursor.getColumnCount()) {
                SQLite.throwSQLiteException(25, "column index out of range");
                throw null;
            }
        }

        @Override // androidx.sqlite.SQLiteStatement
        public final void bindLong(int i, long j) {
            throwIfClosed();
            ensureCapacity(1, i);
            this.bindingTypes[i] = 1;
            this.longBindings[i] = j;
        }

        @Override // androidx.sqlite.SQLiteStatement
        public final void bindNull(int i) {
            throwIfClosed();
            ensureCapacity(5, i);
            this.bindingTypes[i] = 5;
        }

        @Override // androidx.sqlite.SQLiteStatement
        public final void bindText(int i, String str) {
            throwIfClosed();
            ensureCapacity(3, i);
            this.bindingTypes[i] = 3;
            this.stringBindings[i] = str;
        }

        @Override // java.lang.AutoCloseable
        public final void close() {
            if (!this.isClosed) {
                throwIfClosed();
                this.bindingTypes = new int[0];
                this.longBindings = new long[0];
                this.doubleBindings = new double[0];
                this.stringBindings = new String[0];
                this.blobBindings = new byte[0][];
                reset();
            }
            this.isClosed = true;
        }

        public final void ensureCapacity(int i, int i2) {
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

        public final void ensureCursor() {
            if (this.cursor == null) {
                this.cursor = this.db.query(new SupportSQLiteQuery() { // from class: androidx.room.driver.SupportSQLiteStatement$SupportAndroidSQLiteStatement$ensureCursor$1
                    @Override // androidx.sqlite.db.SupportSQLiteQuery
                    public final void bindTo(SupportSQLiteProgram supportSQLiteProgram) {
                        SupportSQLiteStatement.SupportAndroidSQLiteStatement supportAndroidSQLiteStatement = SupportSQLiteStatement.SupportAndroidSQLiteStatement.this;
                        int length = supportAndroidSQLiteStatement.bindingTypes.length;
                        for (int i = 1; i < length; i++) {
                            int i2 = supportAndroidSQLiteStatement.bindingTypes[i];
                            if (i2 == 1) {
                                supportSQLiteProgram.bindLong(i, supportAndroidSQLiteStatement.longBindings[i]);
                            } else if (i2 == 2) {
                                supportSQLiteProgram.bindDouble(supportAndroidSQLiteStatement.doubleBindings[i], i);
                            } else if (i2 == 3) {
                                String str = supportAndroidSQLiteStatement.stringBindings[i];
                                str.getClass();
                                supportSQLiteProgram.bindString(i, str);
                            } else if (i2 == 4) {
                                byte[] bArr = supportAndroidSQLiteStatement.blobBindings[i];
                                bArr.getClass();
                                supportSQLiteProgram.bindBlob(i, bArr);
                            } else if (i2 == 5) {
                                supportSQLiteProgram.bindNull(i);
                            }
                        }
                    }

                    @Override // androidx.sqlite.db.SupportSQLiteQuery
                    public final String getSql() {
                        return SupportSQLiteStatement.SupportAndroidSQLiteStatement.this.sql;
                    }
                });
            }
        }

        @Override // androidx.sqlite.SQLiteStatement
        public final int getColumnCount() {
            throwIfClosed();
            ensureCursor();
            Cursor cursor = this.cursor;
            if (cursor != null) {
                return cursor.getColumnCount();
            }
            return 0;
        }

        @Override // androidx.sqlite.SQLiteStatement
        public final String getColumnName(int i) {
            throwIfClosed();
            ensureCursor();
            Cursor cursor = this.cursor;
            if (cursor == null) {
                throw new IllegalStateException("Required value was null.");
            }
            throwIfInvalidColumn(cursor, i);
            return cursor.getColumnName(i);
        }

        @Override // androidx.sqlite.SQLiteStatement
        public final long getLong(int i) {
            throwIfClosed();
            Cursor cursor = this.cursor;
            if (cursor != null) {
                throwIfInvalidColumn(cursor, i);
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
                throwIfInvalidColumn(cursor, i);
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
                throwIfInvalidColumn(cursor, i);
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
            ensureCursor();
            Cursor cursor = this.cursor;
            if (cursor != null) {
                return cursor.moveToNext();
            }
            throw new IllegalStateException("Required value was null.");
        }
    }

    public /* synthetic */ SupportSQLiteStatement(SupportSQLiteDatabase supportSQLiteDatabase, String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(supportSQLiteDatabase, str);
    }

    public final void throwIfClosed() {
        if (this.isClosed) {
            SQLite.throwSQLiteException(21, "statement is closed");
            throw null;
        }
    }

    private SupportSQLiteStatement(SupportSQLiteDatabase supportSQLiteDatabase, String str) {
        this.db = supportSQLiteDatabase;
        this.sql = str;
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SupportOtherAndroidSQLiteStatement extends SupportSQLiteStatement {
        public final androidx.sqlite.db.SupportSQLiteStatement delegate;

        public SupportOtherAndroidSQLiteStatement(SupportSQLiteDatabase supportSQLiteDatabase, String str) {
            super(supportSQLiteDatabase, str, null);
            this.delegate = supportSQLiteDatabase.compileStatement(str);
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
