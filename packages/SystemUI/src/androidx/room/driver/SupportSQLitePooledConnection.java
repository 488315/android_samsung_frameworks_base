package androidx.room.driver;

import androidx.room.TransactionScope;
import androidx.room.Transactor;
import androidx.room.coroutines.ConnectionPool;
import androidx.room.coroutines.RawConnectionAccessor;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.db.SupportSQLiteDatabase;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public final class SupportSQLitePooledConnection implements Transactor, RawConnectionAccessor {
    public final SupportSQLiteConnection delegate;

    public final class SupportSQLiteTransactor implements TransactionScope, RawConnectionAccessor {
        public SupportSQLiteTransactor() {
        }

        @Override // androidx.room.coroutines.RawConnectionAccessor
        public final SQLiteConnection getRawConnection() {
            return SupportSQLitePooledConnection.this.delegate;
        }

        @Override // androidx.room.PooledConnection
        public final Object usePrepared(String str, Function1 function1, ContinuationImpl continuationImpl) {
            return SupportSQLitePooledConnection.this.usePrepared(str, function1, continuationImpl);
        }
    }

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

    public SupportSQLitePooledConnection(SupportSQLiteConnection supportSQLiteConnection) {
        this.delegate = supportSQLiteConnection;
    }

    @Override // androidx.room.coroutines.RawConnectionAccessor
    public final SQLiteConnection getRawConnection() {
        return this.delegate;
    }

    @Override // androidx.room.Transactor
    public final Object inTransaction(SuspendLambda suspendLambda) {
        return Boolean.valueOf(this.delegate.db.inTransaction());
    }

    /* JADX WARN: Removed duplicated region for block: B:47:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object transaction$2(Transactor.SQLiteTransactionType sQLiteTransactionType, Function2 function2, ContinuationImpl continuationImpl) throws Throwable {
        SupportSQLitePooledConnection$transaction$1 supportSQLitePooledConnection$transaction$1;
        SupportSQLitePooledConnection supportSQLitePooledConnection;
        SupportSQLiteDatabase supportSQLiteDatabase;
        if (continuationImpl instanceof SupportSQLitePooledConnection$transaction$1) {
            supportSQLitePooledConnection$transaction$1 = (SupportSQLitePooledConnection$transaction$1) continuationImpl;
            int i = supportSQLitePooledConnection$transaction$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                supportSQLitePooledConnection$transaction$1.label = i - Integer.MIN_VALUE;
            } else {
                supportSQLitePooledConnection$transaction$1 = new SupportSQLitePooledConnection$transaction$1(this, continuationImpl);
            }
        }
        Object obj = supportSQLitePooledConnection$transaction$1.result;
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = supportSQLitePooledConnection$transaction$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            SupportSQLiteDatabase supportSQLiteDatabase2 = this.delegate.db;
            supportSQLiteDatabase2.inTransaction();
            int i3 = WhenMappings.$EnumSwitchMapping$0[sQLiteTransactionType.ordinal()];
            if (i3 == 1) {
                supportSQLiteDatabase2.beginTransactionReadOnly();
            } else if (i3 == 2) {
                supportSQLiteDatabase2.beginTransactionNonExclusive();
            } else {
                if (i3 != 3) {
                    throw new NoWhenBranchMatchedException();
                }
                supportSQLiteDatabase2.beginTransaction();
            }
            try {
                Object supportSQLiteTransactor = new SupportSQLiteTransactor();
                supportSQLitePooledConnection$transaction$1.L$0 = this;
                supportSQLitePooledConnection$transaction$1.L$1 = supportSQLiteDatabase2;
                supportSQLitePooledConnection$transaction$1.label = 1;
                Object objInvoke = function2.invoke(supportSQLiteTransactor, supportSQLitePooledConnection$transaction$1);
                if (objInvoke == obj2) {
                    return obj2;
                }
                supportSQLitePooledConnection = this;
                supportSQLiteDatabase = supportSQLiteDatabase2;
                obj = objInvoke;
            } catch (ConnectionPool.RollbackException e) {
                e = e;
                supportSQLitePooledConnection = this;
                supportSQLiteDatabase = supportSQLiteDatabase2;
                Object result = e.getResult();
                supportSQLiteDatabase.endTransaction();
                if (!supportSQLiteDatabase.inTransaction()) {
                }
                return result;
            } catch (Throwable th) {
                th = th;
                supportSQLitePooledConnection = this;
                supportSQLiteDatabase = supportSQLiteDatabase2;
                supportSQLiteDatabase.endTransaction();
                if (!supportSQLiteDatabase.inTransaction()) {
                }
                throw th;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            supportSQLiteDatabase = (SupportSQLiteDatabase) supportSQLitePooledConnection$transaction$1.L$1;
            supportSQLitePooledConnection = (SupportSQLitePooledConnection) supportSQLitePooledConnection$transaction$1.L$0;
            try {
                try {
                    ResultKt.throwOnFailure(obj);
                } catch (ConnectionPool.RollbackException e2) {
                    e = e2;
                    Object result2 = e.getResult();
                    supportSQLiteDatabase.endTransaction();
                    if (!supportSQLiteDatabase.inTransaction()) {
                        supportSQLitePooledConnection.getClass();
                    }
                    return result2;
                }
            } catch (Throwable th2) {
                th = th2;
                supportSQLiteDatabase.endTransaction();
                if (!supportSQLiteDatabase.inTransaction()) {
                    supportSQLitePooledConnection.getClass();
                }
                throw th;
            }
        }
        supportSQLiteDatabase.setTransactionSuccessful();
        supportSQLiteDatabase.endTransaction();
        if (!supportSQLiteDatabase.inTransaction()) {
            supportSQLitePooledConnection.getClass();
        }
        return obj;
    }

    @Override // androidx.room.PooledConnection
    public final Object usePrepared(String str, Function1 function1, ContinuationImpl continuationImpl) throws Exception {
        SupportSQLiteStatement supportSQLiteStatementPrepare = this.delegate.prepare(str);
        try {
            Object objMo781invoke = function1.mo781invoke(supportSQLiteStatementPrepare);
            supportSQLiteStatementPrepare.close();
            return objMo781invoke;
        } finally {
        }
    }

    @Override // androidx.room.Transactor
    public final Object withTransaction(Transactor.SQLiteTransactionType sQLiteTransactionType, Function2 function2, SuspendLambda suspendLambda) {
        return transaction$2(sQLiteTransactionType, function2, suspendLambda);
    }
}
