package androidx.room.util;

import androidx.room.util.TableInfo;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteStatement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.builders.ListBuilder;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jdk7.AutoCloseableKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class SchemaInfoUtilKt {
    public static final List readForeignKeyFieldMappings(SQLiteStatement sQLiteStatement) {
        int columnIndexOf = SQLiteStatementUtil.columnIndexOf(sQLiteStatement, "id");
        int columnIndexOf2 = SQLiteStatementUtil.columnIndexOf(sQLiteStatement, "seq");
        int columnIndexOf3 = SQLiteStatementUtil.columnIndexOf(sQLiteStatement, "from");
        int columnIndexOf4 = SQLiteStatementUtil.columnIndexOf(sQLiteStatement, "to");
        ListBuilder createListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
        while (sQLiteStatement.step()) {
            createListBuilder.add(new ForeignKeyWithSequence((int) sQLiteStatement.getLong(columnIndexOf), (int) sQLiteStatement.getLong(columnIndexOf2), sQLiteStatement.getText(columnIndexOf3), sQLiteStatement.getText(columnIndexOf4)));
        }
        return CollectionsKt___CollectionsKt.sorted(createListBuilder.build());
    }

    public static final TableInfo.Index readIndex(SQLiteConnection sQLiteConnection, String str, boolean z) {
        SQLiteStatement prepare = sQLiteConnection.prepare("PRAGMA index_xinfo(`" + str + "`)");
        try {
            int columnIndexOf = SQLiteStatementUtil.columnIndexOf(prepare, "seqno");
            int columnIndexOf2 = SQLiteStatementUtil.columnIndexOf(prepare, "cid");
            int columnIndexOf3 = SQLiteStatementUtil.columnIndexOf(prepare, "name");
            int columnIndexOf4 = SQLiteStatementUtil.columnIndexOf(prepare, "desc");
            if (columnIndexOf != -1 && columnIndexOf2 != -1 && columnIndexOf3 != -1 && columnIndexOf4 != -1) {
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                LinkedHashMap linkedHashMap2 = new LinkedHashMap();
                while (prepare.step()) {
                    if (((int) prepare.getLong(columnIndexOf2)) >= 0) {
                        int i = (int) prepare.getLong(columnIndexOf);
                        String text = prepare.getText(columnIndexOf3);
                        String str2 = prepare.getLong(columnIndexOf4) > 0 ? "DESC" : "ASC";
                        linkedHashMap.put(Integer.valueOf(i), text);
                        linkedHashMap2.put(Integer.valueOf(i), str2);
                    }
                }
                List sortedWith = CollectionsKt___CollectionsKt.sortedWith(linkedHashMap.entrySet(), new Comparator() { // from class: androidx.room.util.SchemaInfoUtilKt$readIndex$lambda$13$$inlined$sortedBy$1
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        return ComparisonsKt__ComparisonsKt.compareValues((Integer) ((Map.Entry) obj).getKey(), (Integer) ((Map.Entry) obj2).getKey());
                    }
                });
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(sortedWith, 10));
                Iterator it = sortedWith.iterator();
                while (it.hasNext()) {
                    arrayList.add((String) ((Map.Entry) it.next()).getValue());
                }
                List list = CollectionsKt___CollectionsKt.toList(arrayList);
                List sortedWith2 = CollectionsKt___CollectionsKt.sortedWith(linkedHashMap2.entrySet(), new Comparator() { // from class: androidx.room.util.SchemaInfoUtilKt$readIndex$lambda$13$$inlined$sortedBy$2
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        return ComparisonsKt__ComparisonsKt.compareValues((Integer) ((Map.Entry) obj).getKey(), (Integer) ((Map.Entry) obj2).getKey());
                    }
                });
                ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(sortedWith2, 10));
                Iterator it2 = sortedWith2.iterator();
                while (it2.hasNext()) {
                    arrayList2.add((String) ((Map.Entry) it2.next()).getValue());
                }
                TableInfo.Index index = new TableInfo.Index(str, z, list, CollectionsKt___CollectionsKt.toList(arrayList2));
                AutoCloseableKt.closeFinally(prepare, null);
                return index;
            }
            AutoCloseableKt.closeFinally(prepare, null);
            return null;
        } finally {
        }
    }
}
