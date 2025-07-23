package androidx.room.util;

import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.room.util.TableInfo;
import com.sec.ims.settings.ImsSettings;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.collections.EmptySet;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.TransformingSequence;
import kotlin.text.StringsKt__IndentKt;
import kotlin.text.StringsKt__IndentKt$$ExternalSyntheticLambda0;
import kotlin.text.StringsKt__StringsKt;
import kotlin.text.StringsKt__StringsKt$lineSequence$$inlined$Sequence$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TableInfo {
    public static final Companion Companion = new Companion(null);
    public final Map columns;
    public final Set foreignKeys;
    public final Set indices;
    public final String name;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ForeignKey {
        public final List columnNames;
        public final String onDelete;
        public final String onUpdate;
        public final List referenceColumnNames;
        public final String referenceTable;

        public ForeignKey(String str, String str2, String str3, List<String> list, List<String> list2) {
            this.referenceTable = str;
            this.onDelete = str2;
            this.onUpdate = str3;
            this.columnNames = list;
            this.referenceColumnNames = list2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ForeignKey)) {
                return false;
            }
            ForeignKey foreignKey = (ForeignKey) obj;
            if (Intrinsics.areEqual(this.referenceTable, foreignKey.referenceTable) && Intrinsics.areEqual(this.onDelete, foreignKey.onDelete) && Intrinsics.areEqual(this.onUpdate, foreignKey.onUpdate) && Intrinsics.areEqual(this.columnNames, foreignKey.columnNames)) {
                return Intrinsics.areEqual(this.referenceColumnNames, foreignKey.referenceColumnNames);
            }
            return false;
        }

        public final int hashCode() {
            return this.referenceColumnNames.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.columnNames, PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.referenceTable.hashCode() * 31, 31, this.onDelete), 31, this.onUpdate), 31);
        }

        public final String toString() {
            String joinToString$default;
            StringBuilder sb = new StringBuilder("\n            |ForeignKey {\n            |   referenceTable = '");
            sb.append(this.referenceTable);
            sb.append("',\n            |   onDelete = '");
            sb.append(this.onDelete);
            sb.append("',\n            |   onUpdate = '");
            sb.append(this.onUpdate);
            sb.append("',\n            |   columnNames = {");
            SequencesKt___SequencesKt.joinToString$default(new TransformingSequence(new StringsKt__StringsKt$lineSequence$$inlined$Sequence$1(CollectionsKt___CollectionsKt.joinToString$default(CollectionsKt___CollectionsKt.sorted(this.columnNames), ",", null, null, null, 62)), new StringsKt__IndentKt$$ExternalSyntheticLambda0()), "\n", null, 62);
            SequencesKt___SequencesKt.joinToString$default(new TransformingSequence(new StringsKt__StringsKt$lineSequence$$inlined$Sequence$1("},"), new StringsKt__IndentKt$$ExternalSyntheticLambda0()), "\n", null, 62);
            Unit unit = Unit.INSTANCE;
            sb.append(unit);
            sb.append("\n            |   referenceColumnNames = {");
            SequencesKt___SequencesKt.joinToString$default(new TransformingSequence(new StringsKt__StringsKt$lineSequence$$inlined$Sequence$1(CollectionsKt___CollectionsKt.joinToString$default(CollectionsKt___CollectionsKt.sorted(this.referenceColumnNames), ",", null, null, null, 62)), new StringsKt__IndentKt$$ExternalSyntheticLambda0()), "\n", null, 62);
            SequencesKt___SequencesKt.joinToString$default(new TransformingSequence(new StringsKt__StringsKt$lineSequence$$inlined$Sequence$1(" }"), new StringsKt__IndentKt$$ExternalSyntheticLambda0()), "\n", null, 62);
            sb.append(unit);
            sb.append("\n            |}\n        ");
            joinToString$default = SequencesKt___SequencesKt.joinToString$default(new TransformingSequence(new StringsKt__StringsKt$lineSequence$$inlined$Sequence$1(StringsKt__IndentKt.trimMargin$default(sb.toString())), new StringsKt__IndentKt$$ExternalSyntheticLambda0()), "\n", null, 62);
            return joinToString$default;
        }
    }

    public TableInfo(String str, Map<String, Column> map, Set<ForeignKey> set) {
        this(str, map, set, EmptySet.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:68:0x01e7, code lost:
    
        r10 = r8.build();
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x01eb, code lost:
    
        r2.close();
     */
    /* JADX WARN: Finally extract failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final androidx.room.util.TableInfo read(androidx.sqlite.SQLiteConnection r31, java.lang.String r32) {
        /*
            Method dump skipped, instructions count: 527
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.room.util.TableInfo.read(androidx.sqlite.SQLiteConnection, java.lang.String):androidx.room.util.TableInfo");
    }

    public final boolean equals(Object obj) {
        Set set;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TableInfo)) {
            return false;
        }
        TableInfo tableInfo = (TableInfo) obj;
        if (!Intrinsics.areEqual(this.name, tableInfo.name) || !Intrinsics.areEqual(this.columns, tableInfo.columns) || !Intrinsics.areEqual(this.foreignKeys, tableInfo.foreignKeys)) {
            return false;
        }
        Set set2 = this.indices;
        if (set2 == null || (set = tableInfo.indices) == null) {
            return true;
        }
        return Intrinsics.areEqual(set2, set);
    }

    public final int hashCode() {
        return this.foreignKeys.hashCode() + ((this.columns.hashCode() + (this.name.hashCode() * 31)) * 31);
    }

    public final String toString() {
        Collection collection;
        StringBuilder sb = new StringBuilder("\n            |TableInfo {\n            |    name = '");
        sb.append(this.name);
        sb.append("',\n            |    columns = {");
        sb.append(TableInfoKt.formatString(CollectionsKt___CollectionsKt.sortedWith(this.columns.values(), new Comparator() { // from class: androidx.room.util.TableInfoKt$toStringCommon$$inlined$sortedBy$1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ComparisonsKt__ComparisonsKt.compareValues(((TableInfo.Column) obj).name, ((TableInfo.Column) obj2).name);
            }
        })));
        sb.append("\n            |    foreignKeys = {");
        sb.append(TableInfoKt.formatString(this.foreignKeys));
        sb.append("\n            |    indices = {");
        Set set = this.indices;
        if (set == null || (collection = CollectionsKt___CollectionsKt.sortedWith(set, new Comparator() { // from class: androidx.room.util.TableInfoKt$toStringCommon$$inlined$sortedBy$2
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ComparisonsKt__ComparisonsKt.compareValues(((TableInfo.Index) obj).name, ((TableInfo.Index) obj2).name);
            }
        })) == null) {
            collection = EmptyList.INSTANCE;
        }
        sb.append(TableInfoKt.formatString(collection));
        sb.append("\n            |}\n        ");
        return StringsKt__IndentKt.trimMargin$default(sb.toString());
    }

    public TableInfo(String str, Map<String, Column> map, Set<ForeignKey> set, Set<Index> set2) {
        this.name = str;
        this.columns = map;
        this.foreignKeys = set;
        this.indices = set2;
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Index {
        public final List columns;
        public final String name;
        public final List orders;
        public final boolean unique;

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

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r4v1, types: [java.util.Collection] */
        /* JADX WARN: Type inference failed for: r4v2 */
        /* JADX WARN: Type inference failed for: r4v4, types: [java.util.ArrayList] */
        public Index(String str, boolean z, List<String> list, List<String> list2) {
            this.name = str;
            this.unique = z;
            this.columns = list;
            this.orders = list2;
            List<String> list3 = list2;
            if (list3.isEmpty()) {
                int size = list.size();
                list3 = new ArrayList(size);
                for (int i = 0; i < size; i++) {
                    list3.add("ASC");
                }
            }
            this.orders = (List) list3;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Index)) {
                return false;
            }
            Index index = (Index) obj;
            if (this.unique != index.unique || !Intrinsics.areEqual(this.columns, index.columns) || !Intrinsics.areEqual(this.orders, index.orders)) {
                return false;
            }
            String str = this.name;
            boolean startsWith = str.startsWith("index_");
            String str2 = index.name;
            return startsWith ? str2.startsWith("index_") : str.equals(str2);
        }

        public final int hashCode() {
            String str = this.name;
            return this.orders.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.columns, (((str.startsWith("index_") ? -1184239155 : str.hashCode()) * 31) + (this.unique ? 1 : 0)) * 31, 31);
        }

        public final String toString() {
            String joinToString$default;
            StringBuilder sb = new StringBuilder("\n            |Index {\n            |   name = '");
            sb.append(this.name);
            sb.append("',\n            |   unique = '");
            sb.append(this.unique);
            sb.append("',\n            |   columns = {");
            SequencesKt___SequencesKt.joinToString$default(new TransformingSequence(new StringsKt__StringsKt$lineSequence$$inlined$Sequence$1(CollectionsKt___CollectionsKt.joinToString$default(this.columns, ",", null, null, null, 62)), new StringsKt__IndentKt$$ExternalSyntheticLambda0()), "\n", null, 62);
            SequencesKt___SequencesKt.joinToString$default(new TransformingSequence(new StringsKt__StringsKt$lineSequence$$inlined$Sequence$1("},"), new StringsKt__IndentKt$$ExternalSyntheticLambda0()), "\n", null, 62);
            Unit unit = Unit.INSTANCE;
            sb.append(unit);
            sb.append("\n            |   orders = {");
            SequencesKt___SequencesKt.joinToString$default(new TransformingSequence(new StringsKt__StringsKt$lineSequence$$inlined$Sequence$1(CollectionsKt___CollectionsKt.joinToString$default(this.orders, ",", null, null, null, 62)), new StringsKt__IndentKt$$ExternalSyntheticLambda0()), "\n", null, 62);
            SequencesKt___SequencesKt.joinToString$default(new TransformingSequence(new StringsKt__StringsKt$lineSequence$$inlined$Sequence$1(" }"), new StringsKt__IndentKt$$ExternalSyntheticLambda0()), "\n", null, 62);
            sb.append(unit);
            sb.append("\n            |}\n        ");
            joinToString$default = SequencesKt___SequencesKt.joinToString$default(new TransformingSequence(new StringsKt__StringsKt$lineSequence$$inlined$Sequence$1(StringsKt__IndentKt.trimMargin$default(sb.toString())), new StringsKt__IndentKt$$ExternalSyntheticLambda0()), "\n", null, 62);
            return joinToString$default;
        }

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public Index(java.lang.String r5, boolean r6, java.util.List<java.lang.String> r7) {
            /*
                r4 = this;
                int r0 = r7.size()
                java.util.ArrayList r1 = new java.util.ArrayList
                r1.<init>(r0)
                r2 = 0
            La:
                if (r2 >= r0) goto L14
                java.lang.String r3 = "ASC"
                r1.add(r3)
                int r2 = r2 + 1
                goto La
            L14:
                r4.<init>(r5, r6, r7, r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.room.util.TableInfo.Index.<init>(java.lang.String, boolean, java.util.List):void");
        }
    }

    public /* synthetic */ TableInfo(String str, Map map, Set set, Set set2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, map, set, (i & 8) != 0 ? null : set2);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Column {
        public final int affinity;
        public final int createdFrom;
        public final String defaultValue;
        public final String name;
        public final boolean notNull;
        public final int primaryKeyPosition;
        public final String type;

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

        public Column(String str, String str2, boolean z, int i, String str3, int i2) {
            this.name = str;
            this.type = str2;
            this.notNull = z;
            this.primaryKeyPosition = i;
            this.defaultValue = str3;
            this.createdFrom = i2;
            int i3 = 5;
            if (str2 != null) {
                String upperCase = str2.toUpperCase(Locale.ROOT);
                if (StringsKt__StringsKt.contains(upperCase, ImsSettings.TYPE_INT, false)) {
                    i3 = 3;
                } else if (StringsKt__StringsKt.contains(upperCase, "CHAR", false) || StringsKt__StringsKt.contains(upperCase, "CLOB", false) || StringsKt__StringsKt.contains(upperCase, ImsSettings.TYPE_TEXT, false)) {
                    i3 = 2;
                } else if (!StringsKt__StringsKt.contains(upperCase, "BLOB", false)) {
                    i3 = (StringsKt__StringsKt.contains(upperCase, "REAL", false) || StringsKt__StringsKt.contains(upperCase, "FLOA", false) || StringsKt__StringsKt.contains(upperCase, "DOUB", false)) ? 4 : 1;
                }
            }
            this.affinity = i3;
        }

        public final boolean equals(Object obj) {
            if (this != obj) {
                if (obj instanceof Column) {
                    Column column = (Column) obj;
                    if ((this.primaryKeyPosition > 0) == (column.primaryKeyPosition > 0) && Intrinsics.areEqual(this.name, column.name) && this.notNull == column.notNull) {
                        int i = column.createdFrom;
                        String str = column.defaultValue;
                        int i2 = this.createdFrom;
                        String str2 = this.defaultValue;
                        if ((i2 != 1 || i != 2 || str2 == null || TableInfoKt.defaultValueEqualsCommon(str2, str)) && ((i2 != 2 || i != 1 || str == null || TableInfoKt.defaultValueEqualsCommon(str, str2)) && ((i2 == 0 || i2 != i || (str2 == null ? str == null : TableInfoKt.defaultValueEqualsCommon(str2, str))) && this.affinity == column.affinity))) {
                        }
                    }
                }
                return false;
            }
            return true;
        }

        public final int hashCode() {
            return (((((this.name.hashCode() * 31) + this.affinity) * 31) + (this.notNull ? 1231 : 1237)) * 31) + this.primaryKeyPosition;
        }

        public final String toString() {
            String joinToString$default;
            StringBuilder sb = new StringBuilder("\n            |Column {\n            |   name = '");
            sb.append(this.name);
            sb.append("',\n            |   type = '");
            sb.append(this.type);
            sb.append("',\n            |   affinity = '");
            sb.append(this.affinity);
            sb.append("',\n            |   notNull = '");
            sb.append(this.notNull);
            sb.append("',\n            |   primaryKeyPosition = '");
            sb.append(this.primaryKeyPosition);
            sb.append("',\n            |   defaultValue = '");
            String str = this.defaultValue;
            if (str == null) {
                str = "undefined";
            }
            sb.append(str);
            sb.append("'\n            |}\n        ");
            joinToString$default = SequencesKt___SequencesKt.joinToString$default(new TransformingSequence(new StringsKt__StringsKt$lineSequence$$inlined$Sequence$1(StringsKt__IndentKt.trimMargin$default(sb.toString())), new StringsKt__IndentKt$$ExternalSyntheticLambda0()), "\n", null, 62);
            return joinToString$default;
        }

        public Column(String str, String str2, boolean z, int i) {
            this(str, str2, z, i, null, 0);
        }
    }
}
