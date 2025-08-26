package androidx.room;

import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public interface Transactor extends PooledConnection {

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class SQLiteTransactionType {
        public static final /* synthetic */ SQLiteTransactionType[] $VALUES;
        public static final SQLiteTransactionType DEFERRED;
        public static final SQLiteTransactionType EXCLUSIVE;
        public static final SQLiteTransactionType IMMEDIATE;

        static {
            SQLiteTransactionType sQLiteTransactionType = new SQLiteTransactionType("DEFERRED", 0);
            DEFERRED = sQLiteTransactionType;
            SQLiteTransactionType sQLiteTransactionType2 = new SQLiteTransactionType("IMMEDIATE", 1);
            IMMEDIATE = sQLiteTransactionType2;
            SQLiteTransactionType sQLiteTransactionType3 = new SQLiteTransactionType("EXCLUSIVE", 2);
            EXCLUSIVE = sQLiteTransactionType3;
            SQLiteTransactionType[] sQLiteTransactionTypeArr = {sQLiteTransactionType, sQLiteTransactionType2, sQLiteTransactionType3};
            $VALUES = sQLiteTransactionTypeArr;
            EnumEntriesKt.enumEntries(sQLiteTransactionTypeArr);
        }

        private SQLiteTransactionType(String str, int i) {
        }

        public static SQLiteTransactionType valueOf(String str) {
            return (SQLiteTransactionType) Enum.valueOf(SQLiteTransactionType.class, str);
        }

        public static SQLiteTransactionType[] values() {
            return (SQLiteTransactionType[]) $VALUES.clone();
        }
    }

    Object inTransaction(SuspendLambda suspendLambda);

    Object withTransaction(SQLiteTransactionType sQLiteTransactionType, Function2 function2, SuspendLambda suspendLambda);
}
