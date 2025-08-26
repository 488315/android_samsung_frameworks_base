package androidx.room.coroutines;

import android.database.SQLException;
import androidx.room.TransactionScope;
import androidx.room.Transactor;
import androidx.room.concurrent.ThreadLocal_jvmAndroidKt;
import androidx.room.coroutines.ConnectionPool;
import androidx.sqlite.SQLite;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteStatement;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.ExceptionsKt__ExceptionsKt;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.sync.Mutex;

/* loaded from: classes.dex */
public final class PooledConnectionImpl implements Transactor, RawConnectionAccessor {
    public final ConnectionWithLock delegate;
    public final boolean isReadOnly;
    public final ArrayDeque transactionStack = new ArrayDeque();
    public final AtomicBoolean _isRecycled = new AtomicBoolean(false);

    public final class StatementWrapper implements SQLiteStatement {
        public final SQLiteStatement delegate;
        public final long threadId = ThreadLocal_jvmAndroidKt.currentThreadId();

        public StatementWrapper(SQLiteStatement sQLiteStatement) {
            this.delegate = sQLiteStatement;
        }

        @Override // androidx.sqlite.SQLiteStatement
        public final void bindLong(int i, long j) {
            if (PooledConnectionImpl.this._isRecycled.get()) {
                SQLite.throwSQLiteException(21, "Statement is recycled");
                throw null;
            }
            if (this.threadId == ThreadLocal_jvmAndroidKt.currentThreadId()) {
                this.delegate.bindLong(i, j);
            } else {
                SQLite.throwSQLiteException(21, "Attempted to use statement on a different thread");
                throw null;
            }
        }

        @Override // androidx.sqlite.SQLiteStatement
        public final void bindNull(int i) {
            if (PooledConnectionImpl.this._isRecycled.get()) {
                SQLite.throwSQLiteException(21, "Statement is recycled");
                throw null;
            }
            if (this.threadId == ThreadLocal_jvmAndroidKt.currentThreadId()) {
                this.delegate.bindNull(i);
            } else {
                SQLite.throwSQLiteException(21, "Attempted to use statement on a different thread");
                throw null;
            }
        }

        @Override // androidx.sqlite.SQLiteStatement
        public final void bindText(int i, String str) {
            if (PooledConnectionImpl.this._isRecycled.get()) {
                SQLite.throwSQLiteException(21, "Statement is recycled");
                throw null;
            }
            if (this.threadId == ThreadLocal_jvmAndroidKt.currentThreadId()) {
                this.delegate.bindText(i, str);
            } else {
                SQLite.throwSQLiteException(21, "Attempted to use statement on a different thread");
                throw null;
            }
        }

        @Override // java.lang.AutoCloseable
        public final void close() throws Exception {
            if (PooledConnectionImpl.this._isRecycled.get()) {
                SQLite.throwSQLiteException(21, "Statement is recycled");
                throw null;
            }
            if (this.threadId == ThreadLocal_jvmAndroidKt.currentThreadId()) {
                this.delegate.close();
            } else {
                SQLite.throwSQLiteException(21, "Attempted to use statement on a different thread");
                throw null;
            }
        }

        @Override // androidx.sqlite.SQLiteStatement
        public final int getColumnCount() {
            if (PooledConnectionImpl.this._isRecycled.get()) {
                SQLite.throwSQLiteException(21, "Statement is recycled");
                throw null;
            }
            if (this.threadId == ThreadLocal_jvmAndroidKt.currentThreadId()) {
                return this.delegate.getColumnCount();
            }
            SQLite.throwSQLiteException(21, "Attempted to use statement on a different thread");
            throw null;
        }

        @Override // androidx.sqlite.SQLiteStatement
        public final String getColumnName(int i) {
            if (PooledConnectionImpl.this._isRecycled.get()) {
                SQLite.throwSQLiteException(21, "Statement is recycled");
                throw null;
            }
            if (this.threadId == ThreadLocal_jvmAndroidKt.currentThreadId()) {
                return this.delegate.getColumnName(i);
            }
            SQLite.throwSQLiteException(21, "Attempted to use statement on a different thread");
            throw null;
        }

        @Override // androidx.sqlite.SQLiteStatement
        public final long getLong(int i) {
            if (PooledConnectionImpl.this._isRecycled.get()) {
                SQLite.throwSQLiteException(21, "Statement is recycled");
                throw null;
            }
            if (this.threadId == ThreadLocal_jvmAndroidKt.currentThreadId()) {
                return this.delegate.getLong(i);
            }
            SQLite.throwSQLiteException(21, "Attempted to use statement on a different thread");
            throw null;
        }

        @Override // androidx.sqlite.SQLiteStatement
        public final String getText(int i) {
            if (PooledConnectionImpl.this._isRecycled.get()) {
                SQLite.throwSQLiteException(21, "Statement is recycled");
                throw null;
            }
            if (this.threadId == ThreadLocal_jvmAndroidKt.currentThreadId()) {
                return this.delegate.getText(i);
            }
            SQLite.throwSQLiteException(21, "Attempted to use statement on a different thread");
            throw null;
        }

        @Override // androidx.sqlite.SQLiteStatement
        public final boolean isNull(int i) {
            if (PooledConnectionImpl.this._isRecycled.get()) {
                SQLite.throwSQLiteException(21, "Statement is recycled");
                throw null;
            }
            if (this.threadId == ThreadLocal_jvmAndroidKt.currentThreadId()) {
                return this.delegate.isNull(i);
            }
            SQLite.throwSQLiteException(21, "Attempted to use statement on a different thread");
            throw null;
        }

        @Override // androidx.sqlite.SQLiteStatement
        public final void reset() {
            if (PooledConnectionImpl.this._isRecycled.get()) {
                SQLite.throwSQLiteException(21, "Statement is recycled");
                throw null;
            }
            if (this.threadId == ThreadLocal_jvmAndroidKt.currentThreadId()) {
                this.delegate.reset();
            } else {
                SQLite.throwSQLiteException(21, "Attempted to use statement on a different thread");
                throw null;
            }
        }

        @Override // androidx.sqlite.SQLiteStatement
        public final boolean step() {
            if (PooledConnectionImpl.this._isRecycled.get()) {
                SQLite.throwSQLiteException(21, "Statement is recycled");
                throw null;
            }
            if (this.threadId == ThreadLocal_jvmAndroidKt.currentThreadId()) {
                return this.delegate.step();
            }
            SQLite.throwSQLiteException(21, "Attempted to use statement on a different thread");
            throw null;
        }
    }

    public final class TransactionImpl implements TransactionScope, RawConnectionAccessor {
        public TransactionImpl() {
        }

        @Override // androidx.room.coroutines.RawConnectionAccessor
        public final SQLiteConnection getRawConnection() {
            return PooledConnectionImpl.this.delegate;
        }

        @Override // androidx.room.PooledConnection
        public final Object usePrepared(String str, Function1 function1, ContinuationImpl continuationImpl) {
            return PooledConnectionImpl.this.usePrepared(str, function1, continuationImpl);
        }
    }

    public final class TransactionItem {
        public final int id;
        public final boolean shouldRollback;

        public TransactionItem(int i, boolean z) {
            this.id = i;
            this.shouldRollback = z;
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

    /* renamed from: androidx.room.coroutines.PooledConnectionImpl$beginTransaction$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return PooledConnectionImpl.this.beginTransaction(null, this);
        }
    }

    /* renamed from: androidx.room.coroutines.PooledConnectionImpl$endTransaction$1, reason: invalid class name and case insensitive filesystem */
    final class C07651 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        boolean Z$0;
        int label;
        /* synthetic */ Object result;

        public C07651(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return PooledConnectionImpl.this.endTransaction(false, this);
        }
    }

    /* renamed from: androidx.room.coroutines.PooledConnectionImpl$usePrepared$1, reason: invalid class name and case insensitive filesystem */
    final class C07661<R> extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;

        public C07661(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return PooledConnectionImpl.this.usePrepared(null, null, this);
        }
    }

    public PooledConnectionImpl(ConnectionWithLock connectionWithLock, boolean z) {
        this.delegate = connectionWithLock;
        this.isReadOnly = z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0015  */
    /* JADX WARN: Type inference failed for: r6v9, types: [kotlinx.coroutines.sync.Mutex] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object beginTransaction(Transactor.SQLiteTransactionType sQLiteTransactionType, ContinuationImpl continuationImpl) {
        AnonymousClass1 anonymousClass1;
        ConnectionWithLock connectionWithLock;
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
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            anonymousClass1.L$0 = this;
            anonymousClass1.L$1 = sQLiteTransactionType;
            connectionWithLock = this.delegate;
            anonymousClass1.L$2 = connectionWithLock;
            anonymousClass1.label = 1;
            if (connectionWithLock.lock.lock(anonymousClass1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ?? r6 = (Mutex) anonymousClass1.L$2;
            sQLiteTransactionType = (Transactor.SQLiteTransactionType) anonymousClass1.L$1;
            PooledConnectionImpl pooledConnectionImpl = (PooledConnectionImpl) anonymousClass1.L$0;
            ResultKt.throwOnFailure(obj);
            connectionWithLock = r6;
            this = pooledConnectionImpl;
        }
        try {
            ArrayDeque arrayDeque = this.transactionStack;
            int i3 = arrayDeque.size;
            boolean zIsEmpty = arrayDeque.isEmpty();
            ConnectionWithLock connectionWithLock2 = this.delegate;
            if (zIsEmpty) {
                int i4 = WhenMappings.$EnumSwitchMapping$0[sQLiteTransactionType.ordinal()];
                if (i4 == 1) {
                    SQLite.execSQL(connectionWithLock2, "BEGIN DEFERRED TRANSACTION");
                } else if (i4 == 2) {
                    SQLite.execSQL(connectionWithLock2, "BEGIN IMMEDIATE TRANSACTION");
                } else {
                    if (i4 != 3) {
                        throw new NoWhenBranchMatchedException();
                    }
                    SQLite.execSQL(connectionWithLock2, "BEGIN EXCLUSIVE TRANSACTION");
                }
            } else {
                SQLite.execSQL(connectionWithLock2, "SAVEPOINT '" + i3 + '\'');
            }
            arrayDeque.addLast(new TransactionItem(i3, false));
            Unit unit = Unit.INSTANCE;
            connectionWithLock.unlock(null);
            return unit;
        } catch (Throwable th) {
            connectionWithLock.unlock(null);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Type inference failed for: r6v9, types: [kotlinx.coroutines.sync.Mutex] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object endTransaction(boolean z, ContinuationImpl continuationImpl) {
        C07651 c07651;
        ConnectionWithLock connectionWithLock;
        if (continuationImpl instanceof C07651) {
            c07651 = (C07651) continuationImpl;
            int i = c07651.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c07651.label = i - Integer.MIN_VALUE;
            } else {
                c07651 = new C07651(continuationImpl);
            }
        }
        Object obj = c07651.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c07651.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            c07651.L$0 = this;
            connectionWithLock = this.delegate;
            c07651.L$1 = connectionWithLock;
            c07651.Z$0 = z;
            c07651.label = 1;
            if (connectionWithLock.lock.lock(c07651) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            z = c07651.Z$0;
            ?? r6 = (Mutex) c07651.L$1;
            PooledConnectionImpl pooledConnectionImpl = (PooledConnectionImpl) c07651.L$0;
            ResultKt.throwOnFailure(obj);
            connectionWithLock = r6;
            this = pooledConnectionImpl;
        }
        try {
            ArrayDeque arrayDeque = this.transactionStack;
            if (arrayDeque.isEmpty()) {
                throw new IllegalStateException("Not in a transaction");
            }
            if (arrayDeque.isEmpty()) {
                throw new NoSuchElementException("List is empty.");
            }
            TransactionItem transactionItem = (TransactionItem) arrayDeque.remove(arrayDeque.size() - 1);
            ConnectionWithLock connectionWithLock2 = this.delegate;
            if (!z || transactionItem.shouldRollback) {
                if (arrayDeque.isEmpty()) {
                    SQLite.execSQL(connectionWithLock2, "ROLLBACK TRANSACTION");
                } else {
                    SQLite.execSQL(connectionWithLock2, "ROLLBACK TRANSACTION TO SAVEPOINT '" + transactionItem.id + '\'');
                }
            } else if (arrayDeque.isEmpty()) {
                SQLite.execSQL(connectionWithLock2, "END TRANSACTION");
            } else {
                SQLite.execSQL(connectionWithLock2, "RELEASE SAVEPOINT '" + transactionItem.id + '\'');
            }
            Unit unit = Unit.INSTANCE;
            connectionWithLock.unlock(null);
            return unit;
        } catch (Throwable th) {
            connectionWithLock.unlock(null);
            throw th;
        }
    }

    @Override // androidx.room.coroutines.RawConnectionAccessor
    public final SQLiteConnection getRawConnection() {
        return this.delegate;
    }

    @Override // androidx.room.Transactor
    public final Object inTransaction(SuspendLambda suspendLambda) {
        if (this._isRecycled.get()) {
            SQLite.throwSQLiteException(21, "Connection is recycled");
            throw null;
        }
        ConnectionElement connectionElement = (ConnectionElement) suspendLambda.getContext().get(ConnectionElement.Key);
        if (connectionElement != null && connectionElement.connectionWrapper == this) {
            return Boolean.valueOf(!this.transactionStack.isEmpty());
        }
        SQLite.throwSQLiteException(21, "Attempted to use connection on a different coroutine");
        throw null;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(10:0|2|(2:4|(1:6)(1:7))(0)|8|(1:(1:(1:(1:(1:(2:15|16)(4:17|74|18|70))(2:22|23))(2:24|25))(6:26|82|27|(1:43)|44|(1:64)(1:47)))(1:31))(5:32|(1:34)|35|(0)|64)|80|38|(4:41|(0)|44|(0))|64|(2:(1:77)|(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00a7, code lost:
    
        r12 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00a8, code lost:
    
        r12 = r11;
        r11 = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00af, code lost:
    
        r11 = r11.getResult();
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00b5, code lost:
    
        r0.L$0 = r11;
        r0.L$1 = null;
        r0.label = 4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00bf, code lost:
    
        if (r12.endTransaction(false, r0) == r1) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00c2, code lost:
    
        return r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00c5, code lost:
    
        throw r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00c6, code lost:
    
        r13 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00c7, code lost:
    
        r9 = r11;
        r11 = r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00c9, code lost:
    
        r0.L$0 = r9;
        r0.L$1 = r11;
        r0.label = 5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00d3, code lost:
    
        if (r12.endTransaction(false, r0) != r1) goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00d6, code lost:
    
        r12 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x00d8, code lost:
    
        r13 = e;
     */
    /* JADX WARN: Removed duplicated region for block: B:43:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00a6 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00af A[Catch: all -> 0x00c3, TRY_LEAVE, TryCatch #0 {all -> 0x00c3, blocks: (B:50:0x00ab, B:52:0x00af), top: B:72:0x00ab }] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00d5 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x00c5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object transaction$1(Transactor.SQLiteTransactionType sQLiteTransactionType, Function2 function2, ContinuationImpl continuationImpl) throws Throwable {
        PooledConnectionImpl$transaction$1 pooledConnectionImpl$transaction$1;
        PooledConnectionImpl pooledConnectionImpl;
        int i;
        boolean z;
        if (continuationImpl instanceof PooledConnectionImpl$transaction$1) {
            pooledConnectionImpl$transaction$1 = (PooledConnectionImpl$transaction$1) continuationImpl;
            int i2 = pooledConnectionImpl$transaction$1.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                pooledConnectionImpl$transaction$1.label = i2 - Integer.MIN_VALUE;
            } else {
                pooledConnectionImpl$transaction$1 = new PooledConnectionImpl$transaction$1(this, continuationImpl);
            }
        }
        Object objInvoke = pooledConnectionImpl$transaction$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = pooledConnectionImpl$transaction$1.label;
        ConnectionPool.RollbackException rollbackException = null;
        if (i3 == 0) {
            ResultKt.throwOnFailure(objInvoke);
            if (sQLiteTransactionType == null) {
                sQLiteTransactionType = Transactor.SQLiteTransactionType.DEFERRED;
            }
            pooledConnectionImpl$transaction$1.L$0 = this;
            pooledConnectionImpl$transaction$1.L$1 = function2;
            pooledConnectionImpl$transaction$1.label = 1;
            if (beginTransaction(sQLiteTransactionType, pooledConnectionImpl$transaction$1) != coroutineSingletons) {
            }
        }
        if (i3 != 1) {
            if (i3 == 2) {
                i = pooledConnectionImpl$transaction$1.I$0;
                pooledConnectionImpl = (PooledConnectionImpl) pooledConnectionImpl$transaction$1.L$0;
                try {
                    ResultKt.throwOnFailure(objInvoke);
                    z = i != 0;
                    pooledConnectionImpl$transaction$1.L$0 = objInvoke;
                    pooledConnectionImpl$transaction$1.label = 3;
                } catch (Throwable th) {
                    ConnectionPool.RollbackException th2 = th;
                    try {
                        if (!(th2 instanceof ConnectionPool.RollbackException)) {
                        }
                    } catch (Throwable th3) {
                        th = th3;
                    }
                }
                return pooledConnectionImpl.endTransaction(z, pooledConnectionImpl$transaction$1) != coroutineSingletons ? coroutineSingletons : objInvoke;
            }
            if (i3 == 3) {
                Object obj = pooledConnectionImpl$transaction$1.L$0;
                ResultKt.throwOnFailure(objInvoke);
                return obj;
            }
            if (i3 == 4) {
                Object obj2 = pooledConnectionImpl$transaction$1.L$0;
                ResultKt.throwOnFailure(objInvoke);
                return obj2;
            }
            if (i3 != 5) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            th = (Throwable) pooledConnectionImpl$transaction$1.L$1;
            Throwable th4 = (Throwable) pooledConnectionImpl$transaction$1.L$0;
            try {
                ResultKt.throwOnFailure(objInvoke);
            } catch (SQLException e) {
                e = e;
                if (th4 != null) {
                    throw e;
                }
                ExceptionsKt__ExceptionsKt.addSuppressed(th4, e);
                throw th;
            }
            throw th;
        }
        function2 = (Function2) pooledConnectionImpl$transaction$1.L$1;
        this = (PooledConnectionImpl) pooledConnectionImpl$transaction$1.L$0;
        ResultKt.throwOnFailure(objInvoke);
        TransactionImpl transactionImpl = this.new TransactionImpl();
        pooledConnectionImpl$transaction$1.L$0 = this;
        pooledConnectionImpl$transaction$1.L$1 = null;
        pooledConnectionImpl$transaction$1.I$0 = 1;
        pooledConnectionImpl$transaction$1.label = 2;
        objInvoke = function2.invoke(transactionImpl, pooledConnectionImpl$transaction$1);
        if (objInvoke != coroutineSingletons) {
            pooledConnectionImpl = this;
            i = 1;
            if (i != 0) {
            }
            pooledConnectionImpl$transaction$1.L$0 = objInvoke;
            pooledConnectionImpl$transaction$1.label = 3;
            if (pooledConnectionImpl.endTransaction(z, pooledConnectionImpl$transaction$1) != coroutineSingletons) {
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Type inference failed for: r6v9, types: [kotlinx.coroutines.sync.Mutex] */
    @Override // androidx.room.PooledConnection
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object usePrepared(String str, Function1 function1, ContinuationImpl continuationImpl) {
        C07661 c07661;
        ConnectionWithLock connectionWithLock;
        if (continuationImpl instanceof C07661) {
            c07661 = (C07661) continuationImpl;
            int i = c07661.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c07661.label = i - Integer.MIN_VALUE;
            } else {
                c07661 = new C07661(continuationImpl);
            }
        }
        Object obj = c07661.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c07661.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            if (this._isRecycled.get()) {
                SQLite.throwSQLiteException(21, "Connection is recycled");
                throw null;
            }
            ConnectionElement connectionElement = (ConnectionElement) c07661.getContext().get(ConnectionElement.Key);
            if (connectionElement == null || connectionElement.connectionWrapper != this) {
                SQLite.throwSQLiteException(21, "Attempted to use connection on a different coroutine");
                throw null;
            }
            c07661.L$0 = this;
            c07661.L$1 = str;
            c07661.L$2 = function1;
            connectionWithLock = this.delegate;
            c07661.L$3 = connectionWithLock;
            c07661.label = 1;
            if (connectionWithLock.lock.lock(c07661) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ?? r6 = (Mutex) c07661.L$3;
            function1 = (Function1) c07661.L$2;
            str = (String) c07661.L$1;
            PooledConnectionImpl pooledConnectionImpl = (PooledConnectionImpl) c07661.L$0;
            ResultKt.throwOnFailure(obj);
            connectionWithLock = r6;
            this = pooledConnectionImpl;
        }
        try {
            StatementWrapper statementWrapper = this.new StatementWrapper(this.delegate.delegate.prepare(str));
            try {
                Object objMo781invoke = function1.mo781invoke(statementWrapper);
                statementWrapper.close();
                return objMo781invoke;
            } finally {
            }
        } finally {
            connectionWithLock.unlock(null);
        }
    }

    @Override // androidx.room.Transactor
    public final Object withTransaction(Transactor.SQLiteTransactionType sQLiteTransactionType, Function2 function2, SuspendLambda suspendLambda) {
        if (this._isRecycled.get()) {
            SQLite.throwSQLiteException(21, "Connection is recycled");
            throw null;
        }
        ConnectionElement connectionElement = (ConnectionElement) suspendLambda.getContext().get(ConnectionElement.Key);
        if (connectionElement != null && connectionElement.connectionWrapper == this) {
            return transaction$1(sQLiteTransactionType, function2, suspendLambda);
        }
        SQLite.throwSQLiteException(21, "Attempted to use connection on a different coroutine");
        throw null;
    }
}
