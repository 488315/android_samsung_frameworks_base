package androidx.room.util;

import androidx.sqlite.SQLiteStatement;
import java.util.List;
import kotlin.collections.builders.MapBuilder;

/* loaded from: classes.dex */
public final class MappedColumnsSQLiteStatementWrapper implements SQLiteStatement {
    public final MapBuilder columnNameToIndexMap;
    public final SQLiteStatement delegate;
    public final int[] mapping;

    public MappedColumnsSQLiteStatementWrapper(SQLiteStatement sQLiteStatement, String[] strArr, int[] iArr) {
        this.delegate = sQLiteStatement;
        this.mapping = iArr;
        if (strArr.length != iArr.length) {
            throw new IllegalArgumentException("Expected columnNames.size == mapping.size");
        }
        MapBuilder mapBuilder = new MapBuilder();
        int length = strArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            mapBuilder.put(strArr[i], Integer.valueOf(this.mapping[i2]));
            i++;
            i2++;
        }
        int columnCount = this.delegate.getColumnCount();
        for (int i3 = 0; i3 < columnCount; i3++) {
            if (!mapBuilder.containsKey(this.delegate.getColumnName(i3))) {
                mapBuilder.put(this.delegate.getColumnName(i3), Integer.valueOf(i3));
            }
        }
        this.columnNameToIndexMap = mapBuilder.build();
    }

    @Override // androidx.sqlite.SQLiteStatement
    public final void bindLong(int i, long j) {
        this.delegate.bindLong(i, j);
    }

    @Override // androidx.sqlite.SQLiteStatement
    public final void bindNull(int i) {
        this.delegate.bindNull(i);
    }

    @Override // androidx.sqlite.SQLiteStatement
    public final void bindText(int i, String str) {
        this.delegate.bindText(i, str);
    }

    @Override // java.lang.AutoCloseable
    public final void close() throws Exception {
        this.delegate.close();
    }

    @Override // androidx.sqlite.SQLiteStatement
    public final boolean getBoolean() {
        return this.delegate.getBoolean();
    }

    @Override // androidx.sqlite.SQLiteStatement
    public final int getColumnCount() {
        return this.delegate.getColumnCount();
    }

    @Override // androidx.sqlite.SQLiteStatement
    public final String getColumnName(int i) {
        return this.delegate.getColumnName(i);
    }

    @Override // androidx.sqlite.SQLiteStatement
    public final List getColumnNames() {
        return this.delegate.getColumnNames();
    }

    @Override // androidx.sqlite.SQLiteStatement
    public final long getLong(int i) {
        return this.delegate.getLong(i);
    }

    @Override // androidx.sqlite.SQLiteStatement
    public final String getText(int i) {
        return this.delegate.getText(i);
    }

    @Override // androidx.sqlite.SQLiteStatement
    public final boolean isNull(int i) {
        return this.delegate.isNull(i);
    }

    @Override // androidx.sqlite.SQLiteStatement
    public final void reset() {
        this.delegate.reset();
    }

    @Override // androidx.sqlite.SQLiteStatement
    public final boolean step() {
        return this.delegate.step();
    }
}
