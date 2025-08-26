package androidx.room.coroutines;

import android.database.sqlite.SQLiteDatabase;
import androidx.room.TransactionScope;
import androidx.room.Transactor;
import androidx.room.coroutines.ConnectionPool;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteStatement;
import androidx.sqlite.driver.AndroidSQLiteConnection;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public final class AndroidSQLiteDriverPooledConnection implements Transactor, RawConnectionAccessor {
    public final AndroidSQLiteConnection delegate;

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

    /* renamed from: androidx.room.coroutines.AndroidSQLiteDriverPooledConnection$transaction$1, reason: invalid class name */
    final class AnonymousClass1<R> extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AndroidSQLiteDriverPooledConnection.this.transaction(null, null, this);
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

    /* JADX WARN: Removed duplicated region for block: B:47:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object transaction(Transactor.SQLiteTransactionType sQLiteTransactionType, Function2 function2, ContinuationImpl continuationImpl) throws Throwable {
        AnonymousClass1 anonymousClass1;
        AndroidSQLiteDriverPooledConnection androidSQLiteDriverPooledConnection;
        SQLiteDatabase sQLiteDatabase;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object obj = anonymousClass1.result;
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            SQLiteDatabase sQLiteDatabase2 = this.delegate.db;
            sQLiteDatabase2.inTransaction();
            int i3 = WhenMappings.$EnumSwitchMapping$0[sQLiteTransactionType.ordinal()];
            if (i3 == 1 || i3 == 2) {
                sQLiteDatabase2.beginTransactionNonExclusive();
            } else {
                if (i3 != 3) {
                    throw new NoWhenBranchMatchedException();
                }
                sQLiteDatabase2.beginTransaction();
            }
            try {
                Object androidSQLiteDriverTransactor = new AndroidSQLiteDriverTransactor();
                anonymousClass1.L$0 = this;
                anonymousClass1.L$1 = sQLiteDatabase2;
                anonymousClass1.label = 1;
                Object objInvoke = function2.invoke(androidSQLiteDriverTransactor, anonymousClass1);
                if (objInvoke == obj2) {
                    return obj2;
                }
                androidSQLiteDriverPooledConnection = this;
                sQLiteDatabase = sQLiteDatabase2;
                obj = objInvoke;
            } catch (ConnectionPool.RollbackException e) {
                e = e;
                androidSQLiteDriverPooledConnection = this;
                sQLiteDatabase = sQLiteDatabase2;
                Object result = e.getResult();
                sQLiteDatabase.endTransaction();
                if (!sQLiteDatabase.inTransaction()) {
                }
                return result;
            } catch (Throwable th) {
                th = th;
                androidSQLiteDriverPooledConnection = this;
                sQLiteDatabase = sQLiteDatabase2;
                sQLiteDatabase.endTransaction();
                if (!sQLiteDatabase.inTransaction()) {
                }
                throw th;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            sQLiteDatabase = (SQLiteDatabase) anonymousClass1.L$1;
            androidSQLiteDriverPooledConnection = (AndroidSQLiteDriverPooledConnection) anonymousClass1.L$0;
            try {
                try {
                    ResultKt.throwOnFailure(obj);
                } catch (ConnectionPool.RollbackException e2) {
                    e = e2;
                    Object result2 = e.getResult();
                    sQLiteDatabase.endTransaction();
                    if (!sQLiteDatabase.inTransaction()) {
                        androidSQLiteDriverPooledConnection.getClass();
                    }
                    return result2;
                }
            } catch (Throwable th2) {
                th = th2;
                sQLiteDatabase.endTransaction();
                if (!sQLiteDatabase.inTransaction()) {
                    androidSQLiteDriverPooledConnection.getClass();
                }
                throw th;
            }
        }
        sQLiteDatabase.setTransactionSuccessful();
        sQLiteDatabase.endTransaction();
        if (!sQLiteDatabase.inTransaction()) {
            androidSQLiteDriverPooledConnection.getClass();
        }
        return obj;
    }

    @Override // androidx.room.PooledConnection
    public final Object usePrepared(String str, Function1 function1, ContinuationImpl continuationImpl) throws Exception {
        SQLiteStatement sQLiteStatementPrepare = this.delegate.prepare(str);
        try {
            Object objMo781invoke = function1.mo781invoke(sQLiteStatementPrepare);
            sQLiteStatementPrepare.close();
            return objMo781invoke;
        } finally {
        }
    }

    @Override // androidx.room.Transactor
    public final Object withTransaction(Transactor.SQLiteTransactionType sQLiteTransactionType, Function2 function2, SuspendLambda suspendLambda) {
        return transaction(sQLiteTransactionType, function2, suspendLambda);
    }
}
