package androidx.sqlite;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public interface SQLiteStatement extends AutoCloseable {
    void bindLong(int i, long j);

    void bindNull(int i);

    void bindText(int i, String str);

    default boolean getBoolean() {
        return getLong(0) != 0;
    }

    int getColumnCount();

    String getColumnName(int i);

    default List getColumnNames() {
        int columnCount = getColumnCount();
        ArrayList arrayList = new ArrayList(columnCount);
        for (int i = 0; i < columnCount; i++) {
            arrayList.add(getColumnName(i));
        }
        return arrayList;
    }

    long getLong(int i);

    String getText(int i);

    boolean isNull(int i);

    void reset();

    boolean step();
}
