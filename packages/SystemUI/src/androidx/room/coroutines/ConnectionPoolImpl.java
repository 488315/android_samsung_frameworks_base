package androidx.room.coroutines;

import android.database.SQLException;
import androidx.sqlite.SQLite;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteDriver;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.TimeoutCancellationException;
import kotlinx.coroutines.TimeoutKt;
import kotlinx.coroutines.internal.ThreadLocalElement;

/* loaded from: classes.dex */
public final class ConnectionPoolImpl implements ConnectionPool {
    public final Pool readers;
    public final long timeout;
    public final Pool writers;
    public final ThreadLocal threadLocal = new ThreadLocal();
    public final AtomicBoolean _isClosed = new AtomicBoolean(false);

    /* renamed from: androidx.room.coroutines.ConnectionPoolImpl$useConnection$1, reason: invalid class name */
    final class AnonymousClass1<R> extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        boolean Z$0;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ConnectionPoolImpl.this.useConnection(false, null, this);
        }
    }

    /* renamed from: androidx.room.coroutines.ConnectionPoolImpl$useConnection$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function2 $block;
        final /* synthetic */ PooledConnectionImpl $confinedConnection;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Function2 function2, PooledConnectionImpl pooledConnectionImpl, Continuation continuation) {
            super(2, continuation);
            this.$block = function2;
            this.$confinedConnection = pooledConnectionImpl;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.$block, this.$confinedConnection, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i != 0) {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return obj;
            }
            ResultKt.throwOnFailure(obj);
            Function2 function2 = this.$block;
            PooledConnectionImpl pooledConnectionImpl = this.$confinedConnection;
            this.label = 1;
            Object objInvoke = function2.invoke(pooledConnectionImpl, this);
            return objInvoke == coroutineSingletons ? coroutineSingletons : objInvoke;
        }
    }

    /* renamed from: androidx.room.coroutines.ConnectionPoolImpl$useConnection$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function2 $block;
        final /* synthetic */ Ref$ObjectRef<PooledConnectionImpl> $connection;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass4(Function2 function2, Ref$ObjectRef<PooledConnectionImpl> ref$ObjectRef, Continuation continuation) {
            super(2, continuation);
            this.$block = function2;
            this.$connection = ref$ObjectRef;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass4(this.$block, this.$connection, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass4) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i != 0) {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return obj;
            }
            ResultKt.throwOnFailure(obj);
            Function2 function2 = this.$block;
            PooledConnectionImpl pooledConnectionImpl = this.$connection.element;
            this.label = 1;
            Object objInvoke = function2.invoke(pooledConnectionImpl, this);
            return objInvoke == coroutineSingletons ? coroutineSingletons : objInvoke;
        }
    }

    public ConnectionPoolImpl(final SQLiteDriver sQLiteDriver, final String str) {
        Duration.Companion companion = Duration.Companion;
        this.timeout = DurationKt.toDuration(30, DurationUnit.SECONDS);
        final int i = 2;
        Pool pool = new Pool(1, new Function0() { // from class: androidx.room.coroutines.ConnectionPoolImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws Exception {
                switch (i) {
                    case 0:
                        SQLiteConnection sQLiteConnectionOpen = sQLiteDriver.open(str);
                        SQLite.execSQL(sQLiteConnectionOpen, "PRAGMA query_only = 1");
                        break;
                }
                return sQLiteDriver.open(str);
            }
        });
        this.readers = pool;
        this.writers = pool;
    }

    @Override // java.lang.AutoCloseable
    public final void close() throws Exception {
        if (this._isClosed.compareAndSet(false, true)) {
            Pool pool = this.readers;
            pool.channel.close(null);
            for (ConnectionWithLock connectionWithLock : pool.connections) {
                if (connectionWithLock != null) {
                    connectionWithLock.close();
                }
            }
            Pool pool2 = this.writers;
            pool2.channel.close(null);
            for (ConnectionWithLock connectionWithLock2 : pool2.connections) {
                if (connectionWithLock2 != null) {
                    connectionWithLock2.close();
                }
            }
        }
    }

    public final void throwTimeoutException(boolean z) {
        String str = z ? "reader" : "writer";
        StringBuilder sb = new StringBuilder();
        sb.append("Timed out attempting to acquire a " + str + " connection.");
        sb.append("\n\nWriter pool:\n");
        this.writers.dump(sb);
        sb.append("Reader pool:");
        sb.append('\n');
        this.readers.dump(sb);
        SQLite.throwSQLiteException(5, sb.toString());
        throw null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:71:0x013d A[Catch: all -> 0x015b, TryCatch #6 {all -> 0x015b, blocks: (B:69:0x0128, B:71:0x013d, B:76:0x0157, B:80:0x0161, B:84:0x016b, B:96:0x01b4, B:97:0x01bb, B:98:0x01bc, B:99:0x01bd, B:100:0x01c0), top: B:134:0x0128 }] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0160  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001b  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0167  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x019d  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x01bd A[Catch: all -> 0x015b, TryCatch #6 {all -> 0x015b, blocks: (B:69:0x0128, B:71:0x013d, B:76:0x0157, B:80:0x0161, B:84:0x016b, B:96:0x01b4, B:97:0x01bb, B:98:0x01bc, B:99:0x01bd, B:100:0x01c0), top: B:134:0x0128 }] */
    /* JADX WARN: Type inference failed for: r8v2 */
    /* JADX WARN: Type inference failed for: r8v3, types: [T, androidx.room.coroutines.PooledConnectionImpl, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r8v5 */
    @Override // androidx.room.coroutines.ConnectionPool
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object useConnection(boolean z, Function2 function2, ContinuationImpl continuationImpl) throws Throwable {
        AnonymousClass1 anonymousClass1;
        Pool pool;
        Ref$ObjectRef ref$ObjectRef;
        Throwable th;
        Pool pool2;
        Ref$ObjectRef ref$ObjectRef2;
        Function2 function22;
        Ref$ObjectRef ref$ObjectRef3;
        ConnectionPoolImpl connectionPoolImpl;
        boolean z2;
        Ref$ObjectRef ref$ObjectRef4;
        Ref$ObjectRef ref$ObjectRef5;
        ConnectionWithLock connectionWithLock;
        Throwable th2;
        ?? pooledConnectionImpl;
        PooledConnectionImpl pooledConnectionImpl2;
        ConnectionPoolImpl connectionPoolImpl2 = this;
        boolean z3 = z;
        Function2 function23 = function2;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = connectionPoolImpl2.new AnonymousClass1(continuationImpl);
            }
        }
        Object objWithContext = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        try {
            if (i2 == 0) {
                ResultKt.throwOnFailure(objWithContext);
                if (connectionPoolImpl2._isClosed.get()) {
                    SQLite.throwSQLiteException(21, "Connection pool is closed");
                    throw null;
                }
                PooledConnectionImpl pooledConnectionImpl3 = (PooledConnectionImpl) connectionPoolImpl2.threadLocal.get();
                if (pooledConnectionImpl3 == null) {
                    ConnectionElement connectionElement = (ConnectionElement) anonymousClass1.getContext().get(ConnectionElement.Key);
                    pooledConnectionImpl3 = connectionElement != null ? connectionElement.connectionWrapper : null;
                }
                if (pooledConnectionImpl3 == null) {
                    pool = z3 ? connectionPoolImpl2.readers : connectionPoolImpl2.writers;
                    ref$ObjectRef = new Ref$ObjectRef();
                    try {
                        ref$ObjectRef2 = new Ref$ObjectRef();
                        try {
                            long j = connectionPoolImpl2.timeout;
                            ConnectionPoolImpl$acquireWithTimeout$2 connectionPoolImpl$acquireWithTimeout$2 = new ConnectionPoolImpl$acquireWithTimeout$2(ref$ObjectRef2, pool, null);
                            anonymousClass1.L$0 = connectionPoolImpl2;
                            anonymousClass1.L$1 = function23;
                            anonymousClass1.L$2 = pool;
                            anonymousClass1.L$3 = ref$ObjectRef;
                            anonymousClass1.L$4 = ref$ObjectRef2;
                            anonymousClass1.Z$0 = z3;
                            anonymousClass1.label = 3;
                            if (TimeoutKt.m3470withTimeoutKLykuaI(j, connectionPoolImpl$acquireWithTimeout$2, anonymousClass1) != coroutineSingletons) {
                                function22 = function23;
                                ref$ObjectRef3 = ref$ObjectRef;
                                connectionPoolImpl = connectionPoolImpl2;
                                z2 = z3;
                                ref$ObjectRef4 = ref$ObjectRef2;
                                ref$ObjectRef2 = ref$ObjectRef4;
                                th = null;
                                z3 = z2;
                                ref$ObjectRef5 = ref$ObjectRef3;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            Ref$ObjectRef ref$ObjectRef6 = ref$ObjectRef;
                            connectionPoolImpl = connectionPoolImpl2;
                            ref$ObjectRef5 = ref$ObjectRef6;
                            function22 = function23;
                            Pair pair = new Pair(ref$ObjectRef2.element, th);
                            connectionWithLock = (ConnectionWithLock) pair.component1();
                            th2 = (Throwable) pair.component2();
                            if (connectionWithLock != null) {
                            }
                            ref$ObjectRef5.element = pooledConnectionImpl;
                            if (th2 instanceof TimeoutCancellationException) {
                            }
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        pool2 = pool;
                        throw th;
                    }
                } else {
                    if (!z3 && pooledConnectionImpl3.isReadOnly) {
                        SQLite.throwSQLiteException(1, "Cannot upgrade connection from reader to writer");
                        throw null;
                    }
                    if (anonymousClass1.getContext().get(ConnectionElement.Key) == null) {
                        CoroutineContext coroutineContextPlus = CoroutineContext.DefaultImpls.plus(new ConnectionElement(pooledConnectionImpl3), new ThreadLocalElement(pooledConnectionImpl3, connectionPoolImpl2.threadLocal));
                        AnonymousClass2 anonymousClass2 = new AnonymousClass2(function23, pooledConnectionImpl3, null);
                        anonymousClass1.label = 1;
                        Object objWithContext2 = BuildersKt.withContext(coroutineContextPlus, anonymousClass2, anonymousClass1);
                        if (objWithContext2 != coroutineSingletons) {
                            return objWithContext2;
                        }
                    } else {
                        anonymousClass1.label = 2;
                        Object objInvoke = function23.invoke(pooledConnectionImpl3, anonymousClass1);
                        if (objInvoke != coroutineSingletons) {
                            return objInvoke;
                        }
                    }
                }
                return coroutineSingletons;
            }
            if (i2 == 1) {
                ResultKt.throwOnFailure(objWithContext);
                return objWithContext;
            }
            if (i2 == 2) {
                ResultKt.throwOnFailure(objWithContext);
                return objWithContext;
            }
            if (i2 == 3) {
                z2 = anonymousClass1.Z$0;
                ref$ObjectRef4 = (Ref$ObjectRef) anonymousClass1.L$4;
                ref$ObjectRef3 = (Ref$ObjectRef) anonymousClass1.L$3;
                pool = (Pool) anonymousClass1.L$2;
                function22 = (Function2) anonymousClass1.L$1;
                connectionPoolImpl = (ConnectionPoolImpl) anonymousClass1.L$0;
                try {
                    ResultKt.throwOnFailure(objWithContext);
                    ref$ObjectRef2 = ref$ObjectRef4;
                    th = null;
                    z3 = z2;
                    ref$ObjectRef5 = ref$ObjectRef3;
                } catch (Throwable th5) {
                    th = th5;
                    ref$ObjectRef2 = ref$ObjectRef4;
                    z3 = z2;
                    connectionPoolImpl2 = connectionPoolImpl;
                    ref$ObjectRef = ref$ObjectRef3;
                    function23 = function22;
                    Ref$ObjectRef ref$ObjectRef62 = ref$ObjectRef;
                    connectionPoolImpl = connectionPoolImpl2;
                    ref$ObjectRef5 = ref$ObjectRef62;
                    function22 = function23;
                    Pair pair2 = new Pair(ref$ObjectRef2.element, th);
                    connectionWithLock = (ConnectionWithLock) pair2.component1();
                    th2 = (Throwable) pair2.component2();
                    if (connectionWithLock != null) {
                    }
                    ref$ObjectRef5.element = pooledConnectionImpl;
                    if (th2 instanceof TimeoutCancellationException) {
                    }
                }
            } else {
                if (i2 != 4) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ref$ObjectRef5 = (Ref$ObjectRef) anonymousClass1.L$1;
                pool2 = (Pool) anonymousClass1.L$0;
                try {
                    ResultKt.throwOnFailure(objWithContext);
                    try {
                        pooledConnectionImpl2 = (PooledConnectionImpl) ref$ObjectRef5.element;
                        if (pooledConnectionImpl2 != null) {
                            ConnectionWithLock connectionWithLock2 = pooledConnectionImpl2.delegate;
                            connectionWithLock2.acquireCoroutineContext = null;
                            connectionWithLock2.acquireThrowable = null;
                            if (pooledConnectionImpl2._isRecycled.compareAndSet(false, true)) {
                                try {
                                    SQLite.execSQL(connectionWithLock2, "ROLLBACK TRANSACTION");
                                } catch (SQLException unused) {
                                }
                            }
                            pool2.recycle(connectionWithLock2);
                        }
                    } catch (Throwable unused2) {
                    }
                    return objWithContext;
                } catch (Throwable th6) {
                    th = th6;
                    ref$ObjectRef = ref$ObjectRef5;
                    th = th;
                    try {
                        throw th;
                    } finally {
                    }
                }
            }
            Pair pair22 = new Pair(ref$ObjectRef2.element, th);
            connectionWithLock = (ConnectionWithLock) pair22.component1();
            th2 = (Throwable) pair22.component2();
            if (connectionWithLock != null) {
                connectionWithLock.acquireCoroutineContext = anonymousClass1.getContext();
                connectionWithLock.acquireThrowable = new Throwable();
                pooledConnectionImpl = new PooledConnectionImpl(connectionWithLock, connectionPoolImpl.readers != connectionPoolImpl.writers && z3);
            } else {
                pooledConnectionImpl = 0;
            }
            ref$ObjectRef5.element = pooledConnectionImpl;
            if (th2 instanceof TimeoutCancellationException) {
                connectionPoolImpl.throwTimeoutException(z3);
                throw null;
            }
            if (th2 != null) {
                throw th2;
            }
            if (pooledConnectionImpl == 0) {
                throw new IllegalArgumentException("Required value was null.");
            }
            connectionPoolImpl.getClass();
            CoroutineContext coroutineContextPlus2 = CoroutineContext.DefaultImpls.plus(new ConnectionElement(pooledConnectionImpl), new ThreadLocalElement(pooledConnectionImpl, connectionPoolImpl.threadLocal));
            AnonymousClass4 anonymousClass4 = new AnonymousClass4(function22, ref$ObjectRef5, null);
            anonymousClass1.L$0 = pool;
            anonymousClass1.L$1 = ref$ObjectRef5;
            anonymousClass1.L$2 = null;
            anonymousClass1.L$3 = null;
            anonymousClass1.L$4 = null;
            anonymousClass1.label = 4;
            objWithContext = BuildersKt.withContext(coroutineContextPlus2, anonymousClass4, anonymousClass1);
            if (objWithContext != coroutineSingletons) {
                pool2 = pool;
                pooledConnectionImpl2 = (PooledConnectionImpl) ref$ObjectRef5.element;
                if (pooledConnectionImpl2 != null) {
                }
                return objWithContext;
            }
            return coroutineSingletons;
        } catch (Throwable th7) {
            th = th7;
            ref$ObjectRef = ref$ObjectRef5;
            pool2 = pool;
            th = th;
            throw th;
        }
    }

    public ConnectionPoolImpl(final SQLiteDriver sQLiteDriver, final String str, int i, int i2) {
        final int i3 = 0;
        Duration.Companion companion = Duration.Companion;
        this.timeout = DurationKt.toDuration(30, DurationUnit.SECONDS);
        if (i <= 0) {
            throw new IllegalArgumentException("Maximum number of readers must be greater than 0");
        }
        if (i2 > 0) {
            this.readers = new Pool(i, new Function0() { // from class: androidx.room.coroutines.ConnectionPoolImpl$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() throws Exception {
                    switch (i3) {
                        case 0:
                            SQLiteConnection sQLiteConnectionOpen = sQLiteDriver.open(str);
                            SQLite.execSQL(sQLiteConnectionOpen, "PRAGMA query_only = 1");
                            break;
                    }
                    return sQLiteDriver.open(str);
                }
            });
            final int i4 = 1;
            this.writers = new Pool(i2, new Function0() { // from class: androidx.room.coroutines.ConnectionPoolImpl$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() throws Exception {
                    switch (i4) {
                        case 0:
                            SQLiteConnection sQLiteConnectionOpen = sQLiteDriver.open(str);
                            SQLite.execSQL(sQLiteConnectionOpen, "PRAGMA query_only = 1");
                            break;
                    }
                    return sQLiteDriver.open(str);
                }
            });
            return;
        }
        throw new IllegalArgumentException("Maximum number of writers must be greater than 0");
    }
}
