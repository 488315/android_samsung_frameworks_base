package androidx.room.util;

import androidx.sqlite.SQLiteStatement;
import java.util.ArrayList;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class SQLiteStatementUtil {
    public static final int columnIndexOf(SQLiteStatement sQLiteStatement, String str) {
        int columnIndexOfCommon = columnIndexOfCommon(sQLiteStatement, str);
        if (columnIndexOfCommon >= 0) {
            return columnIndexOfCommon;
        }
        int columnIndexOfCommon2 = columnIndexOfCommon(sQLiteStatement, "`" + str + '`');
        if (columnIndexOfCommon2 >= 0) {
            return columnIndexOfCommon2;
        }
        return -1;
    }

    public static final int columnIndexOfCommon(SQLiteStatement sQLiteStatement, String str) {
        if (sQLiteStatement instanceof MappedColumnsSQLiteStatementWrapper) {
            Integer num = (Integer) ((MappedColumnsSQLiteStatementWrapper) sQLiteStatement).columnNameToIndexMap.get(str);
            if (num != null) {
                return num.intValue();
            }
            return -1;
        }
        int columnCount = sQLiteStatement.getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            if (Intrinsics.areEqual(str, sQLiteStatement.getColumnName(i))) {
                return i;
            }
        }
        return -1;
    }

    public static final int getColumnIndexOrThrow(SQLiteStatement sQLiteStatement, String str) {
        int columnIndexOf = columnIndexOf(sQLiteStatement, str);
        if (columnIndexOf >= 0) {
            return columnIndexOf;
        }
        int columnCount = sQLiteStatement.getColumnCount();
        ArrayList arrayList = new ArrayList(columnCount);
        for (int i = 0; i < columnCount; i++) {
            arrayList.add(sQLiteStatement.getColumnName(i));
        }
        throw new IllegalArgumentException("Column '" + str + "' does not exist. Available columns: [" + CollectionsKt___CollectionsKt.joinToString$default(arrayList, null, null, null, null, 63) + ']');
    }
}
