package androidx.room.coroutines;

import androidx.room.TransactionScope;
import androidx.room.Transactor;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteStatement;
import androidx.sqlite.driver.AndroidSQLiteConnection;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AndroidSQLiteDriverPooledConnection implements Transactor, RawConnectionAccessor {
    public final AndroidSQLiteConnection delegate;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class AndroidSQLiteDriverTransactor implements TransactionScope, RawConnectionAccessor {
        public AndroidSQLiteDriverTransactor() {
        }

        @Override // androidx.room.coroutines.RawConnectionAccessor
        public final SQLiteConnection getRawConnection() {
            return AndroidSQLiteDriverPooledConnection.this.delegate;
        }

        @Override // androidx.room.PooledConnection
        public final Object usePrepared(String str, Function1 function1, ContinuationImpl continuationImpl) {
            return AndroidSQLiteDriverPooledConnection.this.usePrepared(str, function1, continuationImpl);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Transactor.SQLiteTransactionType.values().length];
            try {
                iArr[Transactor.SQLiteTransactionType.DEFERRED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Transactor.SQLiteTransactionType.IMMEDIATE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Transactor.SQLiteTransactionType.EXCLUSIVE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public AndroidSQLiteDriverPooledConnection(AndroidSQLiteConnection androidSQLiteConnection) {
        this.delegate = androidSQLiteConnection;
    }

    @Override // androidx.room.coroutines.RawConnectionAccessor
    public final SQLiteConnection getRawConnection() {
        return this.delegate;
    }

    @Override // androidx.room.Transactor
    public final Object inTransaction(SuspendLambda suspendLambda) {
        return Boolean.valueOf(this.delegate.db.inTransaction());
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object transaction(androidx.room.Transactor.SQLiteTransactionType r6, kotlin.jvm.functions.Function2 r7, kotlin.coroutines.jvm.internal.ContinuationImpl r8) {
        /*
            r5 = this;
            boolean r0 = r8 instanceof androidx.room.coroutines.AndroidSQLiteDriverPooledConnection$transaction$1
            if (r0 == 0) goto L13
            r0 = r8
            androidx.room.coroutines.AndroidSQLiteDriverPooledConnection$transaction$1 r0 = (androidx.room.coroutines.AndroidSQLiteDriverPooledConnection$transaction$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.room.coroutines.AndroidSQLiteDriverPooledConnection$transaction$1 r0 = new androidx.room.coroutines.AndroidSQLiteDriverPooledConnection$transaction$1
            r0.<init>(r5, r8)
        L18:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L3c
            if (r2 != r3) goto L34
            java.lang.Object r5 = r0.L$1
            android.database.sqlite.SQLiteDatabase r5 = (android.database.sqlite.SQLiteDatabase) r5
            java.lang.Object r6 = r0.L$0
            androidx.room.coroutines.AndroidSQLiteDriverPooledConnection r6 = (androidx.room.coroutines.AndroidSQLiteDriverPooledConnection) r6
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: java.lang.Throwable -> L2f androidx.room.coroutines.ConnectionPool.RollbackException -> L32
            goto L7d
        L2f:
            r7 = move-exception
            goto La5
        L32:
            r7 = move-exception
            goto L94
        L34:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L3c:
            kotlin.ResultKt.throwOnFailure(r8)
            androidx.sqlite.driver.AndroidSQLiteConnection r8 = r5.delegate
            android.database.sqlite.SQLiteDatabase r8 = r8.db
            r8.inTransaction()
            int[] r2 = androidx.room.coroutines.AndroidSQLiteDriverPooledConnection.WhenMappings.$EnumSwitchMapping$0
            int r6 = r6.ordinal()
            r6 = r2[r6]
            if (r6 == r3) goto L64
            r2 = 2
            if (r6 == r2) goto L60
            r2 = 3
            if (r6 != r2) goto L5a
            r8.beginTransaction()
            goto L67
        L5a:
            kotlin.NoWhenBranchMatchedException r5 = new kotlin.NoWhenBranchMatchedException
            r5.<init>()
            throw r5
        L60:
            r8.beginTransactionNonExclusive()
            goto L67
        L64:
            r8.beginTransactionNonExclusive()
        L67:
            androidx.room.coroutines.AndroidSQLiteDriverPooledConnection$AndroidSQLiteDriverTransactor r6 = new androidx.room.coroutines.AndroidSQLiteDriverPooledConnection$AndroidSQLiteDriverTransactor     // Catch: java.lang.Throwable -> L8d androidx.room.coroutines.ConnectionPool.RollbackException -> L91
            r6.<init>()     // Catch: java.lang.Throwable -> L8d androidx.room.coroutines.ConnectionPool.RollbackException -> L91
            r0.L$0 = r5     // Catch: java.lang.Throwable -> L8d androidx.room.coroutines.ConnectionPool.RollbackException -> L91
            r0.L$1 = r8     // Catch: java.lang.Throwable -> L8d androidx.room.coroutines.ConnectionPool.RollbackException -> L91
            r0.label = r3     // Catch: java.lang.Throwable -> L8d androidx.room.coroutines.ConnectionPool.RollbackException -> L91
            java.lang.Object r6 = r7.invoke(r6, r0)     // Catch: java.lang.Throwable -> L8d androidx.room.coroutines.ConnectionPool.RollbackException -> L91
            if (r6 != r1) goto L79
            return r1
        L79:
            r4 = r6
            r6 = r5
            r5 = r8
            r8 = r4
        L7d:
            r5.setTransactionSuccessful()     // Catch: java.lang.Throwable -> L2f androidx.room.coroutines.ConnectionPool.RollbackException -> L32
            r5.endTransaction()
            boolean r5 = r5.inTransaction()
            if (r5 != 0) goto L8c
            r6.getClass()
        L8c:
            return r8
        L8d:
            r7 = move-exception
            r6 = r5
            r5 = r8
            goto La5
        L91:
            r7 = move-exception
            r6 = r5
            r5 = r8
        L94:
            java.lang.Object r7 = r7.getResult()     // Catch: java.lang.Throwable -> L2f
            r5.endTransaction()
            boolean r5 = r5.inTransaction()
            if (r5 != 0) goto La4
            r6.getClass()
        La4:
            return r7
        La5:
            r5.endTransaction()
            boolean r5 = r5.inTransaction()
            if (r5 != 0) goto Lb1
            r6.getClass()
        Lb1:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.room.coroutines.AndroidSQLiteDriverPooledConnection.transaction(androidx.room.Transactor$SQLiteTransactionType, kotlin.jvm.functions.Function2, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    @Override // androidx.room.PooledConnection
    public final Object usePrepared(String str, Function1 function1, ContinuationImpl continuationImpl) {
        SQLiteStatement prepare = this.delegate.prepare(str);
        try {
            Object mo779invoke = function1.mo779invoke(prepare);
            prepare.close();
            return mo779invoke;
        } finally {
        }
    }

    @Override // androidx.room.Transactor
    public final Object withTransaction(Transactor.SQLiteTransactionType sQLiteTransactionType, Function2 function2, SuspendLambda suspendLambda) {
        return transaction(sQLiteTransactionType, function2, suspendLambda);
    }
}
