package androidx.datastore.core;

import androidx.datastore.core.Message;
import androidx.datastore.core.UpdatingDataContextElement;
import androidx.datastore.core.handlers.NoOpCorruptionHandler;
import java.util.List;
import kotlin.ExceptionsKt__ExceptionsKt;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref$IntRef;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CompletableDeferredImpl;
import kotlinx.coroutines.CompletableDeferredKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.SupervisorJobImpl;
import kotlinx.coroutines.SupervisorKt;
import kotlinx.coroutines.channels.ChannelResult;
import kotlinx.coroutines.channels.ClosedSendChannelException;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.scheduling.DefaultIoScheduler;
import kotlinx.coroutines.scheduling.DefaultScheduler;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexImpl;
import kotlinx.coroutines.sync.MutexKt;

/* loaded from: classes.dex */
public final class DataStoreImpl implements DataStore {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int collectorCounter;
    public StandaloneCoroutine collectorJob;
    public final MutexImpl collectorMutex;
    public final Lazy coordinator$delegate;
    public final CorruptionHandler corruptionHandler;
    public final SafeFlow data;
    public final DataStoreInMemoryCache inMemoryCache;
    public final InitDataStore readAndInit;
    public final CoroutineScope scope;
    public final Storage storage;
    public final Lazy storageConnectionDelegate;
    public final SimpleActor writeActor;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class InitDataStore extends RunOnce {
        public List initTasks;

        public InitDataStore(List<? extends Function2> list) {
            this.initTasks = CollectionsKt___CollectionsKt.toList(list);
        }

        /* JADX WARN: Code restructure failed: missing block: B:22:0x0064, code lost:
        
            if (r7 == r1) goto L27;
         */
        /* JADX WARN: Code restructure failed: missing block: B:26:0x0073, code lost:
        
            if (r7 == r1) goto L27;
         */
        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
        @Override // androidx.datastore.core.RunOnce
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object doRun(ContinuationImpl continuationImpl) throws CorruptionException {
            DataStoreImpl$InitDataStore$doRun$1 dataStoreImpl$InitDataStore$doRun$1;
            Data data;
            if (continuationImpl instanceof DataStoreImpl$InitDataStore$doRun$1) {
                dataStoreImpl$InitDataStore$doRun$1 = (DataStoreImpl$InitDataStore$doRun$1) continuationImpl;
                int i = dataStoreImpl$InitDataStore$doRun$1.label;
                if ((i & Integer.MIN_VALUE) != 0) {
                    dataStoreImpl$InitDataStore$doRun$1.label = i - Integer.MIN_VALUE;
                } else {
                    dataStoreImpl$InitDataStore$doRun$1 = new DataStoreImpl$InitDataStore$doRun$1(this, continuationImpl);
                }
            }
            Object objAccess$readDataOrHandleCorruption = dataStoreImpl$InitDataStore$doRun$1.result;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i2 = dataStoreImpl$InitDataStore$doRun$1.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(objAccess$readDataOrHandleCorruption);
                List list = this.initTasks;
                DataStoreImpl dataStoreImpl = DataStoreImpl.this;
                if (list == null || list.isEmpty()) {
                    dataStoreImpl$InitDataStore$doRun$1.L$0 = this;
                    dataStoreImpl$InitDataStore$doRun$1.label = 1;
                    objAccess$readDataOrHandleCorruption = DataStoreImpl.access$readDataOrHandleCorruption(dataStoreImpl, false, dataStoreImpl$InitDataStore$doRun$1);
                } else {
                    int i3 = DataStoreImpl.$r8$clinit;
                    InterProcessCoordinator coordinator = dataStoreImpl.getCoordinator();
                    DataStoreImpl$InitDataStore$doRun$initData$1 dataStoreImpl$InitDataStore$doRun$initData$1 = new DataStoreImpl$InitDataStore$doRun$initData$1(dataStoreImpl, this, null);
                    dataStoreImpl$InitDataStore$doRun$1.L$0 = this;
                    dataStoreImpl$InitDataStore$doRun$1.label = 2;
                    objAccess$readDataOrHandleCorruption = ((SingleProcessCoordinator) coordinator).lock(dataStoreImpl$InitDataStore$doRun$initData$1, dataStoreImpl$InitDataStore$doRun$1);
                }
                return coroutineSingletons;
            }
            if (i2 == 1) {
                this = (InitDataStore) dataStoreImpl$InitDataStore$doRun$1.L$0;
                ResultKt.throwOnFailure(objAccess$readDataOrHandleCorruption);
                data = (Data) objAccess$readDataOrHandleCorruption;
            } else {
                if (i2 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                this = (InitDataStore) dataStoreImpl$InitDataStore$doRun$1.L$0;
                ResultKt.throwOnFailure(objAccess$readDataOrHandleCorruption);
                data = (Data) objAccess$readDataOrHandleCorruption;
            }
            DataStoreImpl.this.inMemoryCache.tryUpdate(data);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: androidx.datastore.core.DataStoreImpl$readAndInitOrPropagateAndThrowFailure$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        int I$0;
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            DataStoreImpl dataStoreImpl = DataStoreImpl.this;
            int i = DataStoreImpl.$r8$clinit;
            return dataStoreImpl.readAndInitOrPropagateAndThrowFailure(this);
        }
    }

    /* renamed from: androidx.datastore.core.DataStoreImpl$updateData$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function2 $transform;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Function2 function2, Continuation continuation) {
            super(2, continuation);
            this.$transform = function2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = DataStoreImpl.this.new AnonymousClass2(this.$transform, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
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
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            CompletableDeferredImpl completableDeferredImplCompletableDeferred$default = CompletableDeferredKt.CompletableDeferred$default();
            Message.Update update = new Message.Update(this.$transform, completableDeferredImplCompletableDeferred$default, DataStoreImpl.this.inMemoryCache.getCurrentState(), coroutineScope.getCoroutineContext());
            SimpleActor simpleActor = DataStoreImpl.this.writeActor;
            Object objMo3476trySendJP2dKIU = simpleActor.messageQueue.mo3476trySendJP2dKIU(update);
            if (objMo3476trySendJP2dKIU instanceof ChannelResult.Closed) {
                Throwable thM3478exceptionOrNullimpl = ChannelResult.m3478exceptionOrNullimpl((ChannelResult.Failed) objMo3476trySendJP2dKIU);
                if (thM3478exceptionOrNullimpl == null) {
                    throw new ClosedSendChannelException("Channel was closed normally");
                }
                throw thM3478exceptionOrNullimpl;
            }
            ChannelResult.Companion companion = ChannelResult.Companion;
            if (objMo3476trySendJP2dKIU instanceof ChannelResult.Failed) {
                throw new IllegalStateException("Check failed.");
            }
            if (simpleActor.remainingMessages.delegate.getAndIncrement() == 0) {
                BuildersKt.launch$default(simpleActor.scope, null, null, new SimpleActor$offer$2(simpleActor, null), 3);
            }
            this.label = 1;
            Object objAwaitInternal = completableDeferredImplCompletableDeferred$default.awaitInternal(this);
            return objAwaitInternal == coroutineSingletons ? coroutineSingletons : objAwaitInternal;
        }
    }

    static {
        new Companion(null);
    }

    public DataStoreImpl(Storage storage, List<? extends Function2> list, CorruptionHandler corruptionHandler, CoroutineScope coroutineScope) {
        this.storage = storage;
        this.corruptionHandler = corruptionHandler;
        this.scope = coroutineScope;
        this.data = new SafeFlow(new DataStoreImpl$data$1(this, null));
        this.collectorMutex = MutexKt.Mutex$default();
        this.inMemoryCache = new DataStoreInMemoryCache();
        this.readAndInit = new InitDataStore(list);
        final int i = 0;
        this.storageConnectionDelegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: androidx.datastore.core.DataStoreImpl$$ExternalSyntheticLambda0
            public final /* synthetic */ DataStoreImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                DataStoreImpl dataStoreImpl = this.f$0;
                switch (i) {
                    case 0:
                        return dataStoreImpl.storage.createConnection();
                    default:
                        int i2 = DataStoreImpl.$r8$clinit;
                        return ((StorageConnection) dataStoreImpl.storageConnectionDelegate.getValue()).getCoordinator();
                }
            }
        });
        final int i2 = 1;
        this.coordinator$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: androidx.datastore.core.DataStoreImpl$$ExternalSyntheticLambda0
            public final /* synthetic */ DataStoreImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                DataStoreImpl dataStoreImpl = this.f$0;
                switch (i2) {
                    case 0:
                        return dataStoreImpl.storage.createConnection();
                    default:
                        int i22 = DataStoreImpl.$r8$clinit;
                        return ((StorageConnection) dataStoreImpl.storageConnectionDelegate.getValue()).getCoordinator();
                }
            }
        });
        this.writeActor = new SimpleActor(coroutineScope, new Function1() { // from class: androidx.datastore.core.DataStoreImpl$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Throwable th = (Throwable) obj;
                DataStoreImpl dataStoreImpl = this.f$0;
                if (th != null) {
                    dataStoreImpl.inMemoryCache.tryUpdate(new Final(th));
                }
                Lazy lazy = dataStoreImpl.storageConnectionDelegate;
                if (lazy.isInitialized()) {
                    ((StorageConnection) lazy.getValue()).close();
                }
                return Unit.INSTANCE;
            }
        }, new DataStoreImpl$$ExternalSyntheticLambda3(), new DataStoreImpl$writeActor$3(this, null));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /* JADX WARN: Type inference failed for: r4v7, types: [kotlinx.coroutines.sync.Mutex] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$decrementCollector(DataStoreImpl dataStoreImpl, ContinuationImpl continuationImpl) {
        DataStoreImpl$decrementCollector$1 dataStoreImpl$decrementCollector$1;
        MutexImpl mutexImpl;
        dataStoreImpl.getClass();
        if (continuationImpl instanceof DataStoreImpl$decrementCollector$1) {
            dataStoreImpl$decrementCollector$1 = (DataStoreImpl$decrementCollector$1) continuationImpl;
            int i = dataStoreImpl$decrementCollector$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                dataStoreImpl$decrementCollector$1.label = i - Integer.MIN_VALUE;
            } else {
                dataStoreImpl$decrementCollector$1 = new DataStoreImpl$decrementCollector$1(dataStoreImpl, continuationImpl);
            }
        }
        Object obj = dataStoreImpl$decrementCollector$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = dataStoreImpl$decrementCollector$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            mutexImpl = dataStoreImpl.collectorMutex;
            dataStoreImpl$decrementCollector$1.L$0 = dataStoreImpl;
            dataStoreImpl$decrementCollector$1.L$1 = mutexImpl;
            dataStoreImpl$decrementCollector$1.label = 1;
            if (mutexImpl.lock(dataStoreImpl$decrementCollector$1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ?? r4 = (Mutex) dataStoreImpl$decrementCollector$1.L$1;
            DataStoreImpl dataStoreImpl2 = (DataStoreImpl) dataStoreImpl$decrementCollector$1.L$0;
            ResultKt.throwOnFailure(obj);
            mutexImpl = r4;
            dataStoreImpl = dataStoreImpl2;
        }
        try {
            int i3 = dataStoreImpl.collectorCounter - 1;
            dataStoreImpl.collectorCounter = i3;
            if (i3 == 0) {
                StandaloneCoroutine standaloneCoroutine = dataStoreImpl.collectorJob;
                if (standaloneCoroutine != null) {
                    standaloneCoroutine.cancel(null);
                }
                dataStoreImpl.collectorJob = null;
            }
            Unit unit = Unit.INSTANCE;
            mutexImpl.unlock(null);
            return Unit.INSTANCE;
        } catch (Throwable th) {
            mutexImpl.unlock(null);
            throw th;
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(10:0|2|(2:4|(1:6)(1:7))(0)|8|71|(7:(1:(1:(1:13)(2:18|19))(3:20|21|22))(1:23)|14|58|65|(1:67)(1:68)|69|70)(5:24|77|25|(3:27|73|28)(4:38|(2:43|(2:45|46)(2:47|48))|49|(2:51|(1:53))(2:62|63))|57)|54|75|55|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0082, code lost:
    
        if (r9 == r1) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0085, code lost:
    
        r8 = r11;
        r11 = r9;
        r9 = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00d7, code lost:
    
        if (r9 == r1) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00dd, code lost:
    
        r9 = th;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00f7  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /* JADX WARN: Type inference failed for: r9v0, types: [androidx.datastore.core.DataStoreImpl, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r9v1 */
    /* JADX WARN: Type inference failed for: r9v12 */
    /* JADX WARN: Type inference failed for: r9v15 */
    /* JADX WARN: Type inference failed for: r9v19 */
    /* JADX WARN: Type inference failed for: r9v2 */
    /* JADX WARN: Type inference failed for: r9v31 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$handleUpdate(DataStoreImpl dataStoreImpl, Message.Update update, ContinuationImpl continuationImpl) {
        DataStoreImpl$handleUpdate$1 dataStoreImpl$handleUpdate$1;
        CompletableDeferredImpl completableDeferredImpl;
        Throwable thM3442exceptionOrNullimpl;
        CompletableDeferred completableDeferred;
        Object objLock;
        DataStoreImpl dataStoreImpl2;
        CompletableDeferred completableDeferred2;
        dataStoreImpl.getClass();
        if (continuationImpl instanceof DataStoreImpl$handleUpdate$1) {
            dataStoreImpl$handleUpdate$1 = (DataStoreImpl$handleUpdate$1) continuationImpl;
            int i = dataStoreImpl$handleUpdate$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                dataStoreImpl$handleUpdate$1.label = i - Integer.MIN_VALUE;
            } else {
                dataStoreImpl$handleUpdate$1 = new DataStoreImpl$handleUpdate$1(dataStoreImpl, continuationImpl);
            }
        }
        Object failure = dataStoreImpl$handleUpdate$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = dataStoreImpl$handleUpdate$1.label;
        try {
        } catch (Throwable th) {
            th = th;
        }
        if (i2 != 0) {
            if (i2 == 1) {
                completableDeferred2 = (CompletableDeferred) dataStoreImpl$handleUpdate$1.L$0;
            } else if (i2 == 2) {
                CompletableDeferred completableDeferred3 = (CompletableDeferred) dataStoreImpl$handleUpdate$1.L$2;
                DataStoreImpl dataStoreImpl3 = (DataStoreImpl) dataStoreImpl$handleUpdate$1.L$1;
                Message.Update update2 = (Message.Update) dataStoreImpl$handleUpdate$1.L$0;
                ResultKt.throwOnFailure(failure);
                completableDeferred = completableDeferred3;
                dataStoreImpl2 = dataStoreImpl3;
                update = update2;
            } else {
                if (i2 != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                completableDeferred2 = (CompletableDeferred) dataStoreImpl$handleUpdate$1.L$0;
            }
            ResultKt.throwOnFailure(failure);
            dataStoreImpl = completableDeferred2;
            int i3 = Result.$r8$clinit;
            completableDeferredImpl = dataStoreImpl;
            thM3442exceptionOrNullimpl = Result.m3442exceptionOrNullimpl(failure);
            CompletableDeferredImpl completableDeferredImpl2 = completableDeferredImpl;
            if (thM3442exceptionOrNullimpl != null) {
                completableDeferredImpl2.makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(failure);
            } else {
                completableDeferredImpl2.completeExceptionally(thM3442exceptionOrNullimpl);
            }
            return Unit.INSTANCE;
        }
        ResultKt.throwOnFailure(failure);
        completableDeferred = update.ack;
        try {
            int i4 = Result.$r8$clinit;
            State currentState = dataStoreImpl.inMemoryCache.getCurrentState();
            if (currentState instanceof Data) {
                Function2 function2 = update.transform;
                CoroutineContext coroutineContext = update.callerContext;
                dataStoreImpl$handleUpdate$1.L$0 = completableDeferred;
                dataStoreImpl$handleUpdate$1.label = 1;
                try {
                    objLock = ((SingleProcessCoordinator) dataStoreImpl.getCoordinator()).lock(new DataStoreImpl$transformAndWrite$2(dataStoreImpl, coroutineContext, function2, null), dataStoreImpl$handleUpdate$1);
                } catch (Throwable th2) {
                    th = th2;
                    th = th;
                    dataStoreImpl = completableDeferred;
                    int i5 = Result.$r8$clinit;
                    failure = new Result.Failure(th);
                    completableDeferredImpl = dataStoreImpl;
                    thM3442exceptionOrNullimpl = Result.m3442exceptionOrNullimpl(failure);
                    CompletableDeferredImpl completableDeferredImpl22 = completableDeferredImpl;
                    if (thM3442exceptionOrNullimpl != null) {
                    }
                    return Unit.INSTANCE;
                }
            } else {
                if (!(currentState instanceof ReadException) && !(currentState instanceof UnInitialized)) {
                    if (currentState instanceof Final) {
                        throw ((Final) currentState).finalException;
                    }
                    throw new NoWhenBranchMatchedException();
                }
                if (currentState != update.lastState) {
                    throw ((ReadException) currentState).readException;
                }
                dataStoreImpl$handleUpdate$1.L$0 = update;
                dataStoreImpl$handleUpdate$1.L$1 = dataStoreImpl;
                dataStoreImpl$handleUpdate$1.L$2 = completableDeferred;
                dataStoreImpl$handleUpdate$1.label = 2;
                Object andInitOrPropagateAndThrowFailure = dataStoreImpl.readAndInitOrPropagateAndThrowFailure(dataStoreImpl$handleUpdate$1);
                dataStoreImpl2 = dataStoreImpl;
                if (andInitOrPropagateAndThrowFailure == coroutineSingletons) {
                }
            }
            return coroutineSingletons;
        } catch (Throwable th3) {
            th = th3;
            dataStoreImpl = completableDeferred;
            int i52 = Result.$r8$clinit;
            failure = new Result.Failure(th);
            completableDeferredImpl = dataStoreImpl;
            thM3442exceptionOrNullimpl = Result.m3442exceptionOrNullimpl(failure);
            CompletableDeferredImpl completableDeferredImpl222 = completableDeferredImpl;
            if (thM3442exceptionOrNullimpl != null) {
            }
            return Unit.INSTANCE;
        }
        Function2 function22 = update.transform;
        CoroutineContext coroutineContext2 = update.callerContext;
        dataStoreImpl$handleUpdate$1.L$0 = completableDeferred;
        dataStoreImpl$handleUpdate$1.L$1 = null;
        dataStoreImpl$handleUpdate$1.L$2 = null;
        dataStoreImpl$handleUpdate$1.label = 3;
        objLock = ((SingleProcessCoordinator) dataStoreImpl2.getCoordinator()).lock(new DataStoreImpl$transformAndWrite$2(dataStoreImpl2, coroutineContext2, function22, null), dataStoreImpl$handleUpdate$1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /* JADX WARN: Type inference failed for: r4v7, types: [kotlinx.coroutines.sync.Mutex] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$incrementCollector(DataStoreImpl dataStoreImpl, ContinuationImpl continuationImpl) {
        DataStoreImpl$incrementCollector$1 dataStoreImpl$incrementCollector$1;
        MutexImpl mutexImpl;
        dataStoreImpl.getClass();
        if (continuationImpl instanceof DataStoreImpl$incrementCollector$1) {
            dataStoreImpl$incrementCollector$1 = (DataStoreImpl$incrementCollector$1) continuationImpl;
            int i = dataStoreImpl$incrementCollector$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                dataStoreImpl$incrementCollector$1.label = i - Integer.MIN_VALUE;
            } else {
                dataStoreImpl$incrementCollector$1 = new DataStoreImpl$incrementCollector$1(dataStoreImpl, continuationImpl);
            }
        }
        Object obj = dataStoreImpl$incrementCollector$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = dataStoreImpl$incrementCollector$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            mutexImpl = dataStoreImpl.collectorMutex;
            dataStoreImpl$incrementCollector$1.L$0 = dataStoreImpl;
            dataStoreImpl$incrementCollector$1.L$1 = mutexImpl;
            dataStoreImpl$incrementCollector$1.label = 1;
            if (mutexImpl.lock(dataStoreImpl$incrementCollector$1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ?? r4 = (Mutex) dataStoreImpl$incrementCollector$1.L$1;
            DataStoreImpl dataStoreImpl2 = (DataStoreImpl) dataStoreImpl$incrementCollector$1.L$0;
            ResultKt.throwOnFailure(obj);
            mutexImpl = r4;
            dataStoreImpl = dataStoreImpl2;
        }
        try {
            int i3 = dataStoreImpl.collectorCounter + 1;
            dataStoreImpl.collectorCounter = i3;
            if (i3 == 1) {
                dataStoreImpl.collectorJob = BuildersKt.launch$default(dataStoreImpl.scope, null, null, new DataStoreImpl$incrementCollector$2$1(dataStoreImpl, null), 3);
            }
            Unit unit = Unit.INSTANCE;
            mutexImpl.unlock(null);
            return Unit.INSTANCE;
        } catch (Throwable th) {
            mutexImpl.unlock(null);
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$readDataAndUpdateCache(DataStoreImpl dataStoreImpl, boolean z, Continuation continuation) {
        DataStoreImpl$readDataAndUpdateCache$1 dataStoreImpl$readDataAndUpdateCache$1;
        DataStoreImpl dataStoreImpl2;
        State state;
        DataStoreImpl dataStoreImpl3;
        Pair pair;
        dataStoreImpl.getClass();
        if (continuation instanceof DataStoreImpl$readDataAndUpdateCache$1) {
            dataStoreImpl$readDataAndUpdateCache$1 = (DataStoreImpl$readDataAndUpdateCache$1) continuation;
            int i = dataStoreImpl$readDataAndUpdateCache$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                dataStoreImpl$readDataAndUpdateCache$1.label = i - Integer.MIN_VALUE;
            } else {
                dataStoreImpl$readDataAndUpdateCache$1 = new DataStoreImpl$readDataAndUpdateCache$1(dataStoreImpl, continuation);
            }
        }
        Object objTryLock = dataStoreImpl$readDataAndUpdateCache$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = dataStoreImpl$readDataAndUpdateCache$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objTryLock);
            State currentState = dataStoreImpl.inMemoryCache.getCurrentState();
            if (currentState instanceof UnInitialized) {
                throw new IllegalStateException("This is a bug in DataStore. Please file a bug at: https://issuetracker.google.com/issues/new?component=907884&template=1466542");
            }
            InterProcessCoordinator coordinator = dataStoreImpl.getCoordinator();
            dataStoreImpl$readDataAndUpdateCache$1.L$0 = dataStoreImpl;
            dataStoreImpl$readDataAndUpdateCache$1.L$1 = currentState;
            dataStoreImpl$readDataAndUpdateCache$1.Z$0 = z;
            dataStoreImpl$readDataAndUpdateCache$1.label = 1;
            Object version = ((SingleProcessCoordinator) coordinator).getVersion();
            if (version != coroutineSingletons) {
                dataStoreImpl2 = dataStoreImpl;
                state = currentState;
                objTryLock = version;
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 == 2) {
                dataStoreImpl3 = (DataStoreImpl) dataStoreImpl$readDataAndUpdateCache$1.L$0;
                ResultKt.throwOnFailure(objTryLock);
                pair = (Pair) objTryLock;
                State state2 = (State) pair.component1();
                if (((Boolean) pair.component2()).booleanValue()) {
                }
                return state2;
            }
            if (i2 != 3) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            dataStoreImpl3 = (DataStoreImpl) dataStoreImpl$readDataAndUpdateCache$1.L$0;
            ResultKt.throwOnFailure(objTryLock);
            pair = (Pair) objTryLock;
            State state22 = (State) pair.component1();
            if (((Boolean) pair.component2()).booleanValue()) {
                dataStoreImpl3.inMemoryCache.tryUpdate(state22);
            }
            return state22;
        }
        z = dataStoreImpl$readDataAndUpdateCache$1.Z$0;
        state = (State) dataStoreImpl$readDataAndUpdateCache$1.L$1;
        dataStoreImpl2 = (DataStoreImpl) dataStoreImpl$readDataAndUpdateCache$1.L$0;
        ResultKt.throwOnFailure(objTryLock);
        int iIntValue = ((Number) objTryLock).intValue();
        boolean z2 = state instanceof Data;
        int i3 = z2 ? ((Data) state).version : -1;
        if (z2 && iIntValue == i3) {
            return state;
        }
        if (z) {
            InterProcessCoordinator coordinator2 = dataStoreImpl2.getCoordinator();
            DataStoreImpl$readDataAndUpdateCache$3 dataStoreImpl$readDataAndUpdateCache$3 = new DataStoreImpl$readDataAndUpdateCache$3(dataStoreImpl2, null);
            dataStoreImpl$readDataAndUpdateCache$1.L$0 = dataStoreImpl2;
            dataStoreImpl$readDataAndUpdateCache$1.L$1 = null;
            dataStoreImpl$readDataAndUpdateCache$1.label = 2;
            objTryLock = ((SingleProcessCoordinator) coordinator2).lock(dataStoreImpl$readDataAndUpdateCache$3, dataStoreImpl$readDataAndUpdateCache$1);
            if (objTryLock != coroutineSingletons) {
                dataStoreImpl3 = dataStoreImpl2;
                pair = (Pair) objTryLock;
                State state222 = (State) pair.component1();
                if (((Boolean) pair.component2()).booleanValue()) {
                }
                return state222;
            }
        } else {
            InterProcessCoordinator coordinator3 = dataStoreImpl2.getCoordinator();
            DataStoreImpl$readDataAndUpdateCache$4 dataStoreImpl$readDataAndUpdateCache$4 = new DataStoreImpl$readDataAndUpdateCache$4(dataStoreImpl2, i3, null);
            dataStoreImpl$readDataAndUpdateCache$1.L$0 = dataStoreImpl2;
            dataStoreImpl$readDataAndUpdateCache$1.L$1 = null;
            dataStoreImpl$readDataAndUpdateCache$1.label = 3;
            objTryLock = ((SingleProcessCoordinator) coordinator3).tryLock(dataStoreImpl$readDataAndUpdateCache$4, dataStoreImpl$readDataAndUpdateCache$1);
            if (objTryLock != coroutineSingletons) {
                dataStoreImpl3 = dataStoreImpl2;
                pair = (Pair) objTryLock;
                State state2222 = (State) pair.component1();
                if (((Boolean) pair.component2()).booleanValue()) {
                }
                return state2222;
            }
        }
        return coroutineSingletons;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00a4 A[Catch: CorruptionException -> 0x0064, TryCatch #1 {CorruptionException -> 0x0064, blocks: (B:19:0x005f, B:54:0x0109, B:24:0x006d, B:51:0x00e9, B:32:0x008a, B:40:0x00a4, B:42:0x00aa, B:36:0x0093, B:48:0x00d4), top: B:79:0x0023 }] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00c3  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0107  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0146 A[Catch: all -> 0x0174, TryCatch #0 {all -> 0x0174, blocks: (B:61:0x0134, B:63:0x0146, B:64:0x014e), top: B:78:0x0134 }] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x014e A[Catch: all -> 0x0174, TRY_LEAVE, TryCatch #0 {all -> 0x0174, blocks: (B:61:0x0134, B:63:0x0146, B:64:0x014e), top: B:78:0x0134 }] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0160  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0168  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$readDataOrHandleCorruption(DataStoreImpl dataStoreImpl, boolean z, ContinuationImpl continuationImpl) throws CorruptionException {
        DataStoreImpl$readDataOrHandleCorruption$1 dataStoreImpl$readDataOrHandleCorruption$1;
        Ref$ObjectRef ref$ObjectRef;
        CorruptionException corruptionException;
        T t;
        DataStoreImpl dataStoreImpl2;
        boolean z2;
        Ref$ObjectRef ref$ObjectRef2;
        CorruptionException corruptionException2;
        Object objLock;
        Ref$IntRef ref$IntRef;
        Ref$ObjectRef ref$ObjectRef3;
        Object obj;
        Object version;
        DataStoreImpl dataStoreImpl3;
        int i;
        Object obj2;
        Object obj3;
        Object obj4;
        Object objTryLock;
        Object obj5;
        dataStoreImpl.getClass();
        if (continuationImpl instanceof DataStoreImpl$readDataOrHandleCorruption$1) {
            dataStoreImpl$readDataOrHandleCorruption$1 = (DataStoreImpl$readDataOrHandleCorruption$1) continuationImpl;
            int i2 = dataStoreImpl$readDataOrHandleCorruption$1.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                dataStoreImpl$readDataOrHandleCorruption$1.label = i2 - Integer.MIN_VALUE;
            } else {
                dataStoreImpl$readDataOrHandleCorruption$1 = new DataStoreImpl$readDataOrHandleCorruption$1(dataStoreImpl, continuationImpl);
            }
        }
        Object obj6 = dataStoreImpl$readDataOrHandleCorruption$1.result;
        Object data = CoroutineSingletons.COROUTINE_SUSPENDED;
        try {
        } catch (CorruptionException e) {
            e = e;
        }
        switch (dataStoreImpl$readDataOrHandleCorruption$1.label) {
            case 0:
                ResultKt.throwOnFailure(obj6);
                if (z) {
                    dataStoreImpl$readDataOrHandleCorruption$1.L$0 = dataStoreImpl;
                    dataStoreImpl$readDataOrHandleCorruption$1.Z$0 = z;
                    dataStoreImpl$readDataOrHandleCorruption$1.label = 1;
                    Object dataFromFileOrDefault = dataStoreImpl.readDataFromFileOrDefault(dataStoreImpl$readDataOrHandleCorruption$1);
                    obj = dataFromFileOrDefault;
                    if (dataFromFileOrDefault != data) {
                        int iHashCode = obj == null ? obj.hashCode() : 0;
                        InterProcessCoordinator coordinator = dataStoreImpl.getCoordinator();
                        dataStoreImpl$readDataOrHandleCorruption$1.L$0 = dataStoreImpl;
                        dataStoreImpl$readDataOrHandleCorruption$1.L$1 = obj;
                        dataStoreImpl$readDataOrHandleCorruption$1.Z$0 = z;
                        dataStoreImpl$readDataOrHandleCorruption$1.I$0 = iHashCode;
                        dataStoreImpl$readDataOrHandleCorruption$1.label = 2;
                        version = ((SingleProcessCoordinator) coordinator).getVersion();
                        if (version != data) {
                            dataStoreImpl3 = dataStoreImpl;
                            i = iHashCode;
                            obj2 = obj;
                            obj3 = version;
                            return new Data(obj2, i, ((Number) obj3).intValue());
                        }
                    }
                } else {
                    InterProcessCoordinator coordinator2 = dataStoreImpl.getCoordinator();
                    dataStoreImpl$readDataOrHandleCorruption$1.L$0 = dataStoreImpl;
                    dataStoreImpl$readDataOrHandleCorruption$1.Z$0 = z;
                    dataStoreImpl$readDataOrHandleCorruption$1.label = 3;
                    Object version2 = ((SingleProcessCoordinator) coordinator2).getVersion();
                    obj4 = version2;
                    if (version2 != data) {
                        int iIntValue = ((Number) obj4).intValue();
                        InterProcessCoordinator coordinator3 = dataStoreImpl.getCoordinator();
                        DataStoreImpl$readDataOrHandleCorruption$2 dataStoreImpl$readDataOrHandleCorruption$2 = new DataStoreImpl$readDataOrHandleCorruption$2(dataStoreImpl, iIntValue, null);
                        dataStoreImpl$readDataOrHandleCorruption$1.L$0 = dataStoreImpl;
                        dataStoreImpl$readDataOrHandleCorruption$1.Z$0 = z;
                        dataStoreImpl$readDataOrHandleCorruption$1.label = 4;
                        objTryLock = ((SingleProcessCoordinator) coordinator3).tryLock(dataStoreImpl$readDataOrHandleCorruption$2, dataStoreImpl$readDataOrHandleCorruption$1);
                        obj5 = objTryLock;
                        if (objTryLock == data) {
                        }
                        return (Data) obj5;
                    }
                }
                return data;
            case 1:
                z = dataStoreImpl$readDataOrHandleCorruption$1.Z$0;
                dataStoreImpl = (DataStoreImpl) dataStoreImpl$readDataOrHandleCorruption$1.L$0;
                ResultKt.throwOnFailure(obj6);
                obj = obj6;
                if (obj == null) {
                }
                InterProcessCoordinator coordinator4 = dataStoreImpl.getCoordinator();
                dataStoreImpl$readDataOrHandleCorruption$1.L$0 = dataStoreImpl;
                dataStoreImpl$readDataOrHandleCorruption$1.L$1 = obj;
                dataStoreImpl$readDataOrHandleCorruption$1.Z$0 = z;
                dataStoreImpl$readDataOrHandleCorruption$1.I$0 = iHashCode;
                dataStoreImpl$readDataOrHandleCorruption$1.label = 2;
                version = ((SingleProcessCoordinator) coordinator4).getVersion();
                if (version != data) {
                }
                return data;
            case 2:
                i = dataStoreImpl$readDataOrHandleCorruption$1.I$0;
                z = dataStoreImpl$readDataOrHandleCorruption$1.Z$0;
                obj2 = dataStoreImpl$readDataOrHandleCorruption$1.L$1;
                dataStoreImpl3 = (DataStoreImpl) dataStoreImpl$readDataOrHandleCorruption$1.L$0;
                try {
                    ResultKt.throwOnFailure(obj6);
                    obj3 = obj6;
                    return new Data(obj2, i, ((Number) obj3).intValue());
                } catch (CorruptionException e2) {
                    e = e2;
                    dataStoreImpl = dataStoreImpl3;
                    ref$ObjectRef = new Ref$ObjectRef();
                    CorruptionHandler corruptionHandler = dataStoreImpl.corruptionHandler;
                    dataStoreImpl$readDataOrHandleCorruption$1.L$0 = dataStoreImpl;
                    dataStoreImpl$readDataOrHandleCorruption$1.L$1 = e;
                    dataStoreImpl$readDataOrHandleCorruption$1.L$2 = ref$ObjectRef;
                    dataStoreImpl$readDataOrHandleCorruption$1.L$3 = ref$ObjectRef;
                    dataStoreImpl$readDataOrHandleCorruption$1.Z$0 = z;
                    dataStoreImpl$readDataOrHandleCorruption$1.label = 5;
                    Object objHandleCorruption = corruptionHandler.handleCorruption(e);
                    if (objHandleCorruption != data) {
                        corruptionException = e;
                        t = objHandleCorruption;
                        dataStoreImpl2 = dataStoreImpl;
                        z2 = z;
                        ref$ObjectRef2 = ref$ObjectRef;
                        ref$ObjectRef2.element = t;
                        Ref$IntRef ref$IntRef2 = new Ref$IntRef();
                        try {
                            DataStoreImpl$readDataOrHandleCorruption$3 dataStoreImpl$readDataOrHandleCorruption$3 = new DataStoreImpl$readDataOrHandleCorruption$3(ref$ObjectRef, dataStoreImpl2, ref$IntRef2, null);
                            dataStoreImpl$readDataOrHandleCorruption$1.L$0 = corruptionException;
                            dataStoreImpl$readDataOrHandleCorruption$1.L$1 = ref$ObjectRef;
                            dataStoreImpl$readDataOrHandleCorruption$1.L$2 = ref$IntRef2;
                            dataStoreImpl$readDataOrHandleCorruption$1.L$3 = null;
                            dataStoreImpl$readDataOrHandleCorruption$1.label = 6;
                            if (z2) {
                            }
                            if (objLock != data) {
                            }
                        } catch (Throwable th) {
                            th = th;
                            corruptionException2 = corruptionException;
                            ExceptionsKt__ExceptionsKt.addSuppressed(corruptionException2, th);
                            throw corruptionException2;
                        }
                    }
                    return data;
                }
            case 3:
                z = dataStoreImpl$readDataOrHandleCorruption$1.Z$0;
                dataStoreImpl = (DataStoreImpl) dataStoreImpl$readDataOrHandleCorruption$1.L$0;
                ResultKt.throwOnFailure(obj6);
                obj4 = obj6;
                int iIntValue2 = ((Number) obj4).intValue();
                InterProcessCoordinator coordinator32 = dataStoreImpl.getCoordinator();
                DataStoreImpl$readDataOrHandleCorruption$2 dataStoreImpl$readDataOrHandleCorruption$22 = new DataStoreImpl$readDataOrHandleCorruption$2(dataStoreImpl, iIntValue2, null);
                dataStoreImpl$readDataOrHandleCorruption$1.L$0 = dataStoreImpl;
                dataStoreImpl$readDataOrHandleCorruption$1.Z$0 = z;
                dataStoreImpl$readDataOrHandleCorruption$1.label = 4;
                objTryLock = ((SingleProcessCoordinator) coordinator32).tryLock(dataStoreImpl$readDataOrHandleCorruption$22, dataStoreImpl$readDataOrHandleCorruption$1);
                obj5 = objTryLock;
                if (objTryLock == data) {
                }
                return (Data) obj5;
            case 4:
                boolean z3 = dataStoreImpl$readDataOrHandleCorruption$1.Z$0;
                ResultKt.throwOnFailure(obj6);
                obj5 = obj6;
                return (Data) obj5;
            case 5:
                z2 = dataStoreImpl$readDataOrHandleCorruption$1.Z$0;
                ref$ObjectRef2 = (Ref$ObjectRef) dataStoreImpl$readDataOrHandleCorruption$1.L$3;
                ref$ObjectRef = (Ref$ObjectRef) dataStoreImpl$readDataOrHandleCorruption$1.L$2;
                corruptionException = (CorruptionException) dataStoreImpl$readDataOrHandleCorruption$1.L$1;
                dataStoreImpl2 = (DataStoreImpl) dataStoreImpl$readDataOrHandleCorruption$1.L$0;
                ResultKt.throwOnFailure(obj6);
                t = obj6;
                ref$ObjectRef2.element = t;
                Ref$IntRef ref$IntRef22 = new Ref$IntRef();
                DataStoreImpl$readDataOrHandleCorruption$3 dataStoreImpl$readDataOrHandleCorruption$32 = new DataStoreImpl$readDataOrHandleCorruption$3(ref$ObjectRef, dataStoreImpl2, ref$IntRef22, null);
                dataStoreImpl$readDataOrHandleCorruption$1.L$0 = corruptionException;
                dataStoreImpl$readDataOrHandleCorruption$1.L$1 = ref$ObjectRef;
                dataStoreImpl$readDataOrHandleCorruption$1.L$2 = ref$IntRef22;
                dataStoreImpl$readDataOrHandleCorruption$1.L$3 = null;
                dataStoreImpl$readDataOrHandleCorruption$1.label = 6;
                if (z2) {
                    objLock = ((SingleProcessCoordinator) dataStoreImpl2.getCoordinator()).lock(new DataStoreImpl$doWithWriteFileLock$2(dataStoreImpl$readDataOrHandleCorruption$32, null), dataStoreImpl$readDataOrHandleCorruption$1);
                } else {
                    dataStoreImpl2.getClass();
                    objLock = dataStoreImpl$readDataOrHandleCorruption$32.mo781invoke(dataStoreImpl$readDataOrHandleCorruption$1);
                }
                if (objLock != data) {
                    ref$IntRef = ref$IntRef22;
                    ref$ObjectRef3 = ref$ObjectRef;
                    T t2 = ref$ObjectRef3.element;
                    data = new Data(t2, t2 != 0 ? t2.hashCode() : 0, ref$IntRef.element);
                }
                return data;
            case 6:
                ref$IntRef = (Ref$IntRef) dataStoreImpl$readDataOrHandleCorruption$1.L$2;
                ref$ObjectRef3 = (Ref$ObjectRef) dataStoreImpl$readDataOrHandleCorruption$1.L$1;
                corruptionException2 = (CorruptionException) dataStoreImpl$readDataOrHandleCorruption$1.L$0;
                try {
                    ResultKt.throwOnFailure(obj6);
                    T t22 = ref$ObjectRef3.element;
                    data = new Data(t22, t22 != 0 ? t22.hashCode() : 0, ref$IntRef.element);
                    return data;
                } catch (Throwable th2) {
                    th = th2;
                    ExceptionsKt__ExceptionsKt.addSuppressed(corruptionException2, th);
                    throw corruptionException2;
                }
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    public final InterProcessCoordinator getCoordinator() {
        return (InterProcessCoordinator) this.coordinator$delegate.getValue();
    }

    @Override // androidx.datastore.core.DataStore
    public final Flow getData() {
        return this.data;
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0068, code lost:
    
        if (r2.runIfNeeded(r0) == r1) goto L26;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object readAndInitOrPropagateAndThrowFailure(ContinuationImpl continuationImpl) throws Throwable {
        AnonymousClass1 anonymousClass1;
        int iIntValue;
        DataStoreImpl dataStoreImpl;
        int i;
        Throwable th;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i2 = anonymousClass1.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i2 - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object version = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = anonymousClass1.label;
        try {
            if (i3 == 0) {
                ResultKt.throwOnFailure(version);
                InterProcessCoordinator coordinator = getCoordinator();
                anonymousClass1.L$0 = this;
                anonymousClass1.label = 1;
                version = ((SingleProcessCoordinator) coordinator).getVersion();
                if (version != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i3 != 1) {
                if (i3 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                i = anonymousClass1.I$0;
                dataStoreImpl = (DataStoreImpl) anonymousClass1.L$0;
                try {
                    ResultKt.throwOnFailure(version);
                    return Unit.INSTANCE;
                } catch (Throwable th2) {
                    th = th2;
                    dataStoreImpl.inMemoryCache.tryUpdate(new ReadException(th, i));
                    throw th;
                }
            }
            this = (DataStoreImpl) anonymousClass1.L$0;
            ResultKt.throwOnFailure(version);
            InitDataStore initDataStore = this.readAndInit;
            anonymousClass1.L$0 = this;
            anonymousClass1.I$0 = iIntValue;
            anonymousClass1.label = 2;
        } catch (Throwable th3) {
            dataStoreImpl = this;
            i = iIntValue;
            th = th3;
            dataStoreImpl.inMemoryCache.tryUpdate(new ReadException(th, i));
            throw th;
        }
        iIntValue = ((Number) version).intValue();
    }

    public final Object readDataFromFileOrDefault(ContinuationImpl continuationImpl) {
        return ((StorageConnection) this.storageConnectionDelegate.getValue()).readScope(new StorageConnectionKt$readData$2(null), continuationImpl);
    }

    @Override // androidx.datastore.core.DataStore
    public final Object updateData(Function2 function2, Continuation continuation) {
        UpdatingDataContextElement updatingDataContextElement = (UpdatingDataContextElement) continuation.getContext().get(UpdatingDataContextElement.Companion.Key.INSTANCE);
        if (updatingDataContextElement != null) {
            updatingDataContextElement.checkNotUpdating(this);
        }
        return BuildersKt.withContext(new UpdatingDataContextElement(updatingDataContextElement, this), new AnonymousClass2(function2, null), continuation);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object writeData$datastore_core_release(Object obj, boolean z, ContinuationImpl continuationImpl) {
        DataStoreImpl$writeData$1 dataStoreImpl$writeData$1;
        Ref$IntRef ref$IntRef;
        if (continuationImpl instanceof DataStoreImpl$writeData$1) {
            dataStoreImpl$writeData$1 = (DataStoreImpl$writeData$1) continuationImpl;
            int i = dataStoreImpl$writeData$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                dataStoreImpl$writeData$1.label = i - Integer.MIN_VALUE;
            } else {
                dataStoreImpl$writeData$1 = new DataStoreImpl$writeData$1(this, continuationImpl);
            }
        }
        Object obj2 = dataStoreImpl$writeData$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = dataStoreImpl$writeData$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj2);
            Ref$IntRef ref$IntRef2 = new Ref$IntRef();
            StorageConnection storageConnection = (StorageConnection) this.storageConnectionDelegate.getValue();
            DataStoreImpl$writeData$2 dataStoreImpl$writeData$2 = new DataStoreImpl$writeData$2(ref$IntRef2, this, obj, z, null);
            dataStoreImpl$writeData$1.L$0 = ref$IntRef2;
            dataStoreImpl$writeData$1.label = 1;
            if (storageConnection.writeScope(dataStoreImpl$writeData$2, dataStoreImpl$writeData$1) == coroutineSingletons) {
                return coroutineSingletons;
            }
            ref$IntRef = ref$IntRef2;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ref$IntRef = (Ref$IntRef) dataStoreImpl$writeData$1.L$0;
            ResultKt.throwOnFailure(obj2);
        }
        return new Integer(ref$IntRef.element);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public DataStoreImpl(Storage storage, List list, CorruptionHandler corruptionHandler, CoroutineScope coroutineScope, int i, DefaultConstructorMarker defaultConstructorMarker) {
        list = (i & 2) != 0 ? EmptyList.INSTANCE : list;
        corruptionHandler = (i & 4) != 0 ? new NoOpCorruptionHandler() : corruptionHandler;
        if ((i & 8) != 0) {
            DefaultScheduler defaultScheduler = Dispatchers.Default;
            DefaultIoScheduler defaultIoScheduler = DefaultIoScheduler.INSTANCE;
            SupervisorJobImpl supervisorJobImplSupervisorJob$default = SupervisorKt.SupervisorJob$default();
            defaultIoScheduler.getClass();
            coroutineScope = CoroutineScopeKt.CoroutineScope(CoroutineContext.DefaultImpls.plus(defaultIoScheduler, supervisorJobImplSupervisorJob$default));
        }
        this(storage, list, corruptionHandler, coroutineScope);
    }
}
