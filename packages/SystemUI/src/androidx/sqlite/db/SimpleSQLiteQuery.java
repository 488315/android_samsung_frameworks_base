package androidx.sqlite.db;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SimpleSQLiteQuery implements SupportSQLiteQuery {
    public static final Companion Companion = new Companion(null);
    public final Object[] bindArgs;
    public final String query;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public SimpleSQLiteQuery(String str, Object[] objArr) {
        this.query = str;
        this.bindArgs = objArr;
    }

    @Override // androidx.sqlite.db.SupportSQLiteQuery
    public final void bindTo(SupportSQLiteProgram supportSQLiteProgram) {
        Companion.getClass();
        Object[] objArr = this.bindArgs;
        if (objArr == null) {
            return;
        }
        int length = objArr.length;
        int i = 0;
        while (i < length) {
            Object obj = objArr[i];
            i++;
            if (obj == null) {
                supportSQLiteProgram.bindNull(i);
            } else if (obj instanceof byte[]) {
                supportSQLiteProgram.bindBlob(i, (byte[]) obj);
            } else if (obj instanceof Float) {
                supportSQLiteProgram.bindDouble(((Number) obj).floatValue(), i);
            } else if (obj instanceof Double) {
                supportSQLiteProgram.bindDouble(((Number) obj).doubleValue(), i);
            } else if (obj instanceof Long) {
                supportSQLiteProgram.bindLong(i, ((Number) obj).longValue());
            } else if (obj instanceof Integer) {
                supportSQLiteProgram.bindLong(i, ((Number) obj).intValue());
            } else if (obj instanceof Short) {
                supportSQLiteProgram.bindLong(i, ((Number) obj).shortValue());
            } else if (obj instanceof Byte) {
                supportSQLiteProgram.bindLong(i, ((Number) obj).byteValue());
            } else if (obj instanceof String) {
                supportSQLiteProgram.bindString(i, (String) obj);
            } else {
                if (!(obj instanceof Boolean)) {
                    throw new IllegalArgumentException("Cannot bind " + obj + " at index " + i + " Supported types: Null, ByteArray, Float, Double, Long, Int, Short, Byte, String");
                }
                supportSQLiteProgram.bindLong(i, ((Boolean) obj).booleanValue() ? 1L : 0L);
            }
        }
    }

    @Override // androidx.sqlite.db.SupportSQLiteQuery
    public final String getSql() {
        return this.query;
    }

    public SimpleSQLiteQuery(String str) {
        this(str, null);
    }
}
