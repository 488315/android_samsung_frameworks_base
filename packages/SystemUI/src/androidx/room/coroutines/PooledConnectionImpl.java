package androidx.room.coroutines;

import androidx.room.TransactionScope;
import androidx.room.Transactor;
import androidx.room.concurrent.ThreadLocal_jvmAndroidKt;
import androidx.sqlite.SQLite;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteStatement;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.collections.ArrayDeque;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PooledConnectionImpl implements Transactor, RawConnectionAccessor {
    public final ConnectionWithLock delegate;
    public final boolean isReadOnly;
    public final ArrayDeque transactionStack = new ArrayDeque();
    public final AtomicBoolean _isRecycled = new AtomicBoolean(false);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        public final void close() {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class TransactionItem {
        public final int id;
        public final boolean shouldRollback;

        public TransactionItem(int i, boolean z) {
            this.id = i;
            this.shouldRollback = z;
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

    public PooledConnectionImpl(ConnectionWithLock connectionWithLock, boolean z) {
        this.delegate = connectionWithLock;
        this.isReadOnly = z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0062 A[Catch: all -> 0x0078, TRY_ENTER, TryCatch #0 {all -> 0x0078, blocks: (B:12:0x0056, B:15:0x0062, B:21:0x0072, B:22:0x00a0, B:26:0x007a, B:27:0x007f, B:28:0x0080, B:29:0x0086, B:30:0x008c), top: B:11:0x0056 }] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x008c A[Catch: all -> 0x0078, TryCatch #0 {all -> 0x0078, blocks: (B:12:0x0056, B:15:0x0062, B:21:0x0072, B:22:0x00a0, B:26:0x007a, B:27:0x007f, B:28:0x0080, B:29:0x0086, B:30:0x008c), top: B:11:0x0056 }] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
    /* JADX WARN: Type inference failed for: r6v9, types: [kotlinx.coroutines.sync.Mutex] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object beginTransaction(androidx.room.Transactor.SQLiteTransactionType r7, kotlin.coroutines.jvm.internal.ContinuationImpl r8) {
        /*
            r6 = this;
            java.lang.String r0 = "SAVEPOINT '"
            boolean r1 = r8 instanceof androidx.room.coroutines.PooledConnectionImpl$beginTransaction$1
            if (r1 == 0) goto L15
            r1 = r8
            androidx.room.coroutines.PooledConnectionImpl$beginTransaction$1 r1 = (androidx.room.coroutines.PooledConnectionImpl$beginTransaction$1) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L15
            int r2 = r2 - r3
            r1.label = r2
            goto L1a
        L15:
            androidx.room.coroutines.PooledConnectionImpl$beginTransaction$1 r1 = new androidx.room.coroutines.PooledConnectionImpl$beginTransaction$1
            r1.<init>(r6, r8)
        L1a:
            java.lang.Object r8 = r1.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r2 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r3 = r1.label
            r4 = 1
            if (r3 == 0) goto L3f
            if (r3 != r4) goto L37
            java.lang.Object r6 = r1.L$2
            kotlinx.coroutines.sync.Mutex r6 = (kotlinx.coroutines.sync.Mutex) r6
            java.lang.Object r7 = r1.L$1
            androidx.room.Transactor$SQLiteTransactionType r7 = (androidx.room.Transactor.SQLiteTransactionType) r7
            java.lang.Object r1 = r1.L$0
            androidx.room.coroutines.PooledConnectionImpl r1 = (androidx.room.coroutines.PooledConnectionImpl) r1
            kotlin.ResultKt.throwOnFailure(r8)
            r8 = r6
            r6 = r1
            goto L55
        L37:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L3f:
            kotlin.ResultKt.throwOnFailure(r8)
            r1.L$0 = r6
            r1.L$1 = r7
            androidx.room.coroutines.ConnectionWithLock r8 = r6.delegate
            r1.L$2 = r8
            r1.label = r4
            kotlinx.coroutines.sync.Mutex r3 = r8.lock
            java.lang.Object r1 = r3.lock(r1)
            if (r1 != r2) goto L55
            return r2
        L55:
            r1 = 0
            kotlin.collections.ArrayDeque r2 = r6.transactionStack     // Catch: java.lang.Throwable -> L78
            int r3 = r2.size     // Catch: java.lang.Throwable -> L78
            boolean r5 = r2.isEmpty()     // Catch: java.lang.Throwable -> L78
            androidx.room.coroutines.ConnectionWithLock r6 = r6.delegate
            if (r5 == 0) goto L8c
            int[] r0 = androidx.room.coroutines.PooledConnectionImpl.WhenMappings.$EnumSwitchMapping$0     // Catch: java.lang.Throwable -> L78
            int r7 = r7.ordinal()     // Catch: java.lang.Throwable -> L78
            r7 = r0[r7]     // Catch: java.lang.Throwable -> L78
            if (r7 == r4) goto L86
            r0 = 2
            if (r7 == r0) goto L80
            r0 = 3
            if (r7 != r0) goto L7a
            java.lang.String r7 = "BEGIN EXCLUSIVE TRANSACTION"
            androidx.sqlite.SQLite.execSQL(r6, r7)     // Catch: java.lang.Throwable -> L78
            goto La0
        L78:
            r6 = move-exception
            goto Laf
        L7a:
            kotlin.NoWhenBranchMatchedException r6 = new kotlin.NoWhenBranchMatchedException     // Catch: java.lang.Throwable -> L78
            r6.<init>()     // Catch: java.lang.Throwable -> L78
            throw r6     // Catch: java.lang.Throwable -> L78
        L80:
            java.lang.String r7 = "BEGIN IMMEDIATE TRANSACTION"
            androidx.sqlite.SQLite.execSQL(r6, r7)     // Catch: java.lang.Throwable -> L78
            goto La0
        L86:
            java.lang.String r7 = "BEGIN DEFERRED TRANSACTION"
            androidx.sqlite.SQLite.execSQL(r6, r7)     // Catch: java.lang.Throwable -> L78
            goto La0
        L8c:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L78
            r7.<init>(r0)     // Catch: java.lang.Throwable -> L78
            r7.append(r3)     // Catch: java.lang.Throwable -> L78
            r0 = 39
            r7.append(r0)     // Catch: java.lang.Throwable -> L78
            java.lang.String r7 = r7.toString()     // Catch: java.lang.Throwable -> L78
            androidx.sqlite.SQLite.execSQL(r6, r7)     // Catch: java.lang.Throwable -> L78
        La0:
            androidx.room.coroutines.PooledConnectionImpl$TransactionItem r6 = new androidx.room.coroutines.PooledConnectionImpl$TransactionItem     // Catch: java.lang.Throwable -> L78
            r7 = 0
            r6.<init>(r3, r7)     // Catch: java.lang.Throwable -> L78
            r2.addLast(r6)     // Catch: java.lang.Throwable -> L78
            kotlin.Unit r6 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L78
            r8.unlock(r1)
            return r6
        Laf:
            r8.unlock(r1)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.room.coroutines.PooledConnectionImpl.beginTransaction(androidx.room.Transactor$SQLiteTransactionType, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:14:0x005e A[Catch: all -> 0x0085, TryCatch #0 {all -> 0x0085, blocks: (B:12:0x0056, B:14:0x005e, B:16:0x0064, B:19:0x0075, B:21:0x0079, B:23:0x007f, B:24:0x00bc, B:28:0x0087, B:29:0x009c, B:31:0x00a2, B:32:0x00a8, B:33:0x00c2, B:34:0x00c9, B:35:0x00ca, B:36:0x00d1), top: B:11:0x0056 }] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00ca A[Catch: all -> 0x0085, TryCatch #0 {all -> 0x0085, blocks: (B:12:0x0056, B:14:0x005e, B:16:0x0064, B:19:0x0075, B:21:0x0079, B:23:0x007f, B:24:0x00bc, B:28:0x0087, B:29:0x009c, B:31:0x00a2, B:32:0x00a8, B:33:0x00c2, B:34:0x00c9, B:35:0x00ca, B:36:0x00d1), top: B:11:0x0056 }] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /* JADX WARN: Type inference failed for: r6v9, types: [kotlinx.coroutines.sync.Mutex] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object endTransaction(boolean r7, kotlin.coroutines.jvm.internal.ContinuationImpl r8) {
        /*
            r6 = this;
            java.lang.String r0 = "ROLLBACK TRANSACTION TO SAVEPOINT '"
            java.lang.String r1 = "RELEASE SAVEPOINT '"
            boolean r2 = r8 instanceof androidx.room.coroutines.PooledConnectionImpl$endTransaction$1
            if (r2 == 0) goto L17
            r2 = r8
            androidx.room.coroutines.PooledConnectionImpl$endTransaction$1 r2 = (androidx.room.coroutines.PooledConnectionImpl$endTransaction$1) r2
            int r3 = r2.label
            r4 = -2147483648(0xffffffff80000000, float:-0.0)
            r5 = r3 & r4
            if (r5 == 0) goto L17
            int r3 = r3 - r4
            r2.label = r3
            goto L1c
        L17:
            androidx.room.coroutines.PooledConnectionImpl$endTransaction$1 r2 = new androidx.room.coroutines.PooledConnectionImpl$endTransaction$1
            r2.<init>(r6, r8)
        L1c:
            java.lang.Object r8 = r2.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r3 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r4 = r2.label
            r5 = 1
            if (r4 == 0) goto L3f
            if (r4 != r5) goto L37
            boolean r7 = r2.Z$0
            java.lang.Object r6 = r2.L$1
            kotlinx.coroutines.sync.Mutex r6 = (kotlinx.coroutines.sync.Mutex) r6
            java.lang.Object r2 = r2.L$0
            androidx.room.coroutines.PooledConnectionImpl r2 = (androidx.room.coroutines.PooledConnectionImpl) r2
            kotlin.ResultKt.throwOnFailure(r8)
            r8 = r6
            r6 = r2
            goto L55
        L37:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L3f:
            kotlin.ResultKt.throwOnFailure(r8)
            r2.L$0 = r6
            androidx.room.coroutines.ConnectionWithLock r8 = r6.delegate
            r2.L$1 = r8
            r2.Z$0 = r7
            r2.label = r5
            kotlinx.coroutines.sync.Mutex r4 = r8.lock
            java.lang.Object r2 = r4.lock(r2)
            if (r2 != r3) goto L55
            return r3
        L55:
            r2 = 0
            kotlin.collections.ArrayDeque r3 = r6.transactionStack     // Catch: java.lang.Throwable -> L85
            boolean r4 = r3.isEmpty()     // Catch: java.lang.Throwable -> L85
            if (r4 != 0) goto Lca
            boolean r4 = r3.isEmpty()     // Catch: java.lang.Throwable -> L85
            if (r4 != 0) goto Lc2
            int r4 = r3.size()     // Catch: java.lang.Throwable -> L85
            int r4 = r4 - r5
            java.lang.Object r4 = r3.remove(r4)     // Catch: java.lang.Throwable -> L85
            androidx.room.coroutines.PooledConnectionImpl$TransactionItem r4 = (androidx.room.coroutines.PooledConnectionImpl.TransactionItem) r4     // Catch: java.lang.Throwable -> L85
            r5 = 39
            androidx.room.coroutines.ConnectionWithLock r6 = r6.delegate
            if (r7 == 0) goto L9c
            boolean r7 = r4.shouldRollback     // Catch: java.lang.Throwable -> L85
            if (r7 != 0) goto L9c
            boolean r7 = r3.isEmpty()     // Catch: java.lang.Throwable -> L85
            if (r7 == 0) goto L87
            java.lang.String r7 = "END TRANSACTION"
            androidx.sqlite.SQLite.execSQL(r6, r7)     // Catch: java.lang.Throwable -> L85
            goto Lbc
        L85:
            r6 = move-exception
            goto Ld2
        L87:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L85
            r7.<init>(r1)     // Catch: java.lang.Throwable -> L85
            int r0 = r4.id     // Catch: java.lang.Throwable -> L85
            r7.append(r0)     // Catch: java.lang.Throwable -> L85
            r7.append(r5)     // Catch: java.lang.Throwable -> L85
            java.lang.String r7 = r7.toString()     // Catch: java.lang.Throwable -> L85
            androidx.sqlite.SQLite.execSQL(r6, r7)     // Catch: java.lang.Throwable -> L85
            goto Lbc
        L9c:
            boolean r7 = r3.isEmpty()     // Catch: java.lang.Throwable -> L85
            if (r7 == 0) goto La8
            java.lang.String r7 = "ROLLBACK TRANSACTION"
            androidx.sqlite.SQLite.execSQL(r6, r7)     // Catch: java.lang.Throwable -> L85
            goto Lbc
        La8:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L85
            r7.<init>(r0)     // Catch: java.lang.Throwable -> L85
            int r0 = r4.id     // Catch: java.lang.Throwable -> L85
            r7.append(r0)     // Catch: java.lang.Throwable -> L85
            r7.append(r5)     // Catch: java.lang.Throwable -> L85
            java.lang.String r7 = r7.toString()     // Catch: java.lang.Throwable -> L85
            androidx.sqlite.SQLite.execSQL(r6, r7)     // Catch: java.lang.Throwable -> L85
        Lbc:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L85
            r8.unlock(r2)
            return r6
        Lc2:
            java.util.NoSuchElementException r6 = new java.util.NoSuchElementException     // Catch: java.lang.Throwable -> L85
            java.lang.String r7 = "List is empty."
            r6.<init>(r7)     // Catch: java.lang.Throwable -> L85
            throw r6     // Catch: java.lang.Throwable -> L85
        Lca:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> L85
            java.lang.String r7 = "Not in a transaction"
            r6.<init>(r7)     // Catch: java.lang.Throwable -> L85
            throw r6     // Catch: java.lang.Throwable -> L85
        Ld2:
            r8.unlock(r2)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.room.coroutines.PooledConnectionImpl.endTransaction(boolean, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
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

    /* JADX WARN: Can't wrap try/catch for region: R(11:0|1|(2:3|(7:5|6|(1:(1:(1:(1:(1:(2:13|14)(4:16|17|18|19))(2:25|26))(2:27|28))(6:29|30|31|(1:33)|34|(1:37)(1:36)))(1:60))(3:68|(1:70)|71)|61|62|(4:64|(0)|34|(0))|37))|73|6|(0)(0)|61|62|(0)|37|(2:(1:56)|(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00af, code lost:
    
        r11 = r11.getResult();
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00b5, code lost:
    
        r0.L$0 = r11;
        r0.L$1 = null;
        r0.label = 4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00bf, code lost:
    
        if (r12.endTransaction(false, r0) == r1) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00c2, code lost:
    
        return r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00c5, code lost:
    
        throw r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00c6, code lost:
    
        r13 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00c7, code lost:
    
        r9 = r11;
        r11 = r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00c9, code lost:
    
        r0.L$0 = r9;
        r0.L$1 = r11;
        r0.label = 5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00d3, code lost:
    
        if (r12.endTransaction(false, r0) != r1) goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00d8, code lost:
    
        r13 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00d6, code lost:
    
        r12 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x00a7, code lost:
    
        r12 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00a8, code lost:
    
        r12 = r11;
        r11 = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x007f, code lost:
    
        if (beginTransaction(r12, r0) == r1) goto L64;
     */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00a6 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00d5 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00af A[Catch: all -> 0x00c3, TRY_LEAVE, TryCatch #0 {all -> 0x00c3, blocks: (B:41:0x00ab, B:43:0x00af), top: B:40:0x00ab }] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00c5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0027  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object transaction$1(androidx.room.Transactor.SQLiteTransactionType r12, kotlin.jvm.functions.Function2 r13, kotlin.coroutines.jvm.internal.ContinuationImpl r14) {
        /*
            Method dump skipped, instructions count: 225
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.room.coroutines.PooledConnectionImpl.transaction$1(androidx.room.Transactor$SQLiteTransactionType, kotlin.jvm.functions.Function2, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0043  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /* JADX WARN: Type inference failed for: r6v9, types: [kotlinx.coroutines.sync.Mutex] */
    @Override // androidx.room.PooledConnection
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object usePrepared(java.lang.String r7, kotlin.jvm.functions.Function1 r8, kotlin.coroutines.jvm.internal.ContinuationImpl r9) {
        /*
            r6 = this;
            boolean r0 = r9 instanceof androidx.room.coroutines.PooledConnectionImpl$usePrepared$1
            if (r0 == 0) goto L13
            r0 = r9
            androidx.room.coroutines.PooledConnectionImpl$usePrepared$1 r0 = (androidx.room.coroutines.PooledConnectionImpl$usePrepared$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.room.coroutines.PooledConnectionImpl$usePrepared$1 r0 = new androidx.room.coroutines.PooledConnectionImpl$usePrepared$1
            r0.<init>(r6, r9)
        L18:
            java.lang.Object r9 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L43
            if (r2 != r4) goto L3b
            java.lang.Object r6 = r0.L$3
            kotlinx.coroutines.sync.Mutex r6 = (kotlinx.coroutines.sync.Mutex) r6
            java.lang.Object r7 = r0.L$2
            r8 = r7
            kotlin.jvm.functions.Function1 r8 = (kotlin.jvm.functions.Function1) r8
            java.lang.Object r7 = r0.L$1
            java.lang.String r7 = (java.lang.String) r7
            java.lang.Object r0 = r0.L$0
            androidx.room.coroutines.PooledConnectionImpl r0 = (androidx.room.coroutines.PooledConnectionImpl) r0
            kotlin.ResultKt.throwOnFailure(r9)
            r9 = r6
            r6 = r0
            goto L77
        L3b:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L43:
            kotlin.ResultKt.throwOnFailure(r9)
            java.util.concurrent.atomic.AtomicBoolean r9 = r6._isRecycled
            boolean r9 = r9.get()
            r2 = 21
            if (r9 != 0) goto La1
            kotlin.coroutines.CoroutineContext r9 = r0.getContext()
            androidx.room.coroutines.ConnectionElement$Key r5 = androidx.room.coroutines.ConnectionElement.Key
            kotlin.coroutines.CoroutineContext$Element r9 = r9.get(r5)
            androidx.room.coroutines.ConnectionElement r9 = (androidx.room.coroutines.ConnectionElement) r9
            if (r9 == 0) goto L9b
            androidx.room.coroutines.PooledConnectionImpl r9 = r9.connectionWrapper
            if (r9 != r6) goto L9b
            r0.L$0 = r6
            r0.L$1 = r7
            r0.L$2 = r8
            androidx.room.coroutines.ConnectionWithLock r9 = r6.delegate
            r0.L$3 = r9
            r0.label = r4
            kotlinx.coroutines.sync.Mutex r2 = r9.lock
            java.lang.Object r0 = r2.lock(r0)
            if (r0 != r1) goto L77
            return r1
        L77:
            androidx.room.coroutines.PooledConnectionImpl$StatementWrapper r0 = new androidx.room.coroutines.PooledConnectionImpl$StatementWrapper     // Catch: java.lang.Throwable -> L96
            androidx.room.coroutines.ConnectionWithLock r1 = r6.delegate     // Catch: java.lang.Throwable -> L96
            androidx.sqlite.SQLiteConnection r1 = r1.delegate     // Catch: java.lang.Throwable -> L96
            androidx.sqlite.SQLiteStatement r7 = r1.prepare(r7)     // Catch: java.lang.Throwable -> L96
            r0.<init>(r7)     // Catch: java.lang.Throwable -> L96
            java.lang.Object r6 = r8.mo779invoke(r0)     // Catch: java.lang.Throwable -> L8f
            r0.close()     // Catch: java.lang.Throwable -> L96
            r9.unlock(r3)
            return r6
        L8f:
            r6 = move-exception
            throw r6     // Catch: java.lang.Throwable -> L91
        L91:
            r7 = move-exception
            kotlin.jdk7.AutoCloseableKt.closeFinally(r0, r6)     // Catch: java.lang.Throwable -> L96
            throw r7     // Catch: java.lang.Throwable -> L96
        L96:
            r6 = move-exception
            r9.unlock(r3)
            throw r6
        L9b:
            java.lang.String r6 = "Attempted to use connection on a different coroutine"
            androidx.sqlite.SQLite.throwSQLiteException(r2, r6)
            throw r3
        La1:
            java.lang.String r6 = "Connection is recycled"
            androidx.sqlite.SQLite.throwSQLiteException(r2, r6)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.room.coroutines.PooledConnectionImpl.usePrepared(java.lang.String, kotlin.jvm.functions.Function1, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
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
