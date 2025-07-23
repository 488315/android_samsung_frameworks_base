package androidx.datastore.core;

import androidx.datastore.core.UpdatingDataContextElement;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.sync.MutexImpl;
import kotlinx.coroutines.sync.MutexKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class InitDataStore extends RunOnce {
        public List initTasks;

        public InitDataStore(List<? extends Function2> list) {
            this.initTasks = CollectionsKt___CollectionsKt.toList(list);
        }

        /* JADX WARN: Code restructure failed: missing block: B:24:0x0064, code lost:
        
            if (r7 == r1) goto L27;
         */
        /* JADX WARN: Code restructure failed: missing block: B:27:0x0073, code lost:
        
            if (r7 == r1) goto L27;
         */
        /* JADX WARN: Removed duplicated region for block: B:19:0x003e  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
        @Override // androidx.datastore.core.RunOnce
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object doRun(kotlin.coroutines.jvm.internal.ContinuationImpl r7) {
            /*
                r6 = this;
                boolean r0 = r7 instanceof androidx.datastore.core.DataStoreImpl$InitDataStore$doRun$1
                if (r0 == 0) goto L13
                r0 = r7
                androidx.datastore.core.DataStoreImpl$InitDataStore$doRun$1 r0 = (androidx.datastore.core.DataStoreImpl$InitDataStore$doRun$1) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L13
                int r1 = r1 - r2
                r0.label = r1
                goto L18
            L13:
                androidx.datastore.core.DataStoreImpl$InitDataStore$doRun$1 r0 = new androidx.datastore.core.DataStoreImpl$InitDataStore$doRun$1
                r0.<init>(r6, r7)
            L18:
                java.lang.Object r7 = r0.result
                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r2 = r0.label
                r3 = 2
                r4 = 1
                if (r2 == 0) goto L3e
                if (r2 == r4) goto L36
                if (r2 != r3) goto L2e
                java.lang.Object r6 = r0.L$0
                androidx.datastore.core.DataStoreImpl$InitDataStore r6 = (androidx.datastore.core.DataStoreImpl.InitDataStore) r6
                kotlin.ResultKt.throwOnFailure(r7)
                goto L67
            L2e:
                java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                r6.<init>(r7)
                throw r6
            L36:
                java.lang.Object r6 = r0.L$0
                androidx.datastore.core.DataStoreImpl$InitDataStore r6 = (androidx.datastore.core.DataStoreImpl.InitDataStore) r6
                kotlin.ResultKt.throwOnFailure(r7)
                goto L76
            L3e:
                kotlin.ResultKt.throwOnFailure(r7)
                java.util.List r7 = r6.initTasks
                androidx.datastore.core.DataStoreImpl r2 = androidx.datastore.core.DataStoreImpl.this
                if (r7 == 0) goto L6a
                boolean r7 = r7.isEmpty()
                if (r7 == 0) goto L4e
                goto L6a
            L4e:
                int r7 = androidx.datastore.core.DataStoreImpl.$r8$clinit
                androidx.datastore.core.InterProcessCoordinator r7 = r2.getCoordinator()
                androidx.datastore.core.DataStoreImpl$InitDataStore$doRun$initData$1 r4 = new androidx.datastore.core.DataStoreImpl$InitDataStore$doRun$initData$1
                r5 = 0
                r4.<init>(r2, r6, r5)
                r0.L$0 = r6
                r0.label = r3
                androidx.datastore.core.SingleProcessCoordinator r7 = (androidx.datastore.core.SingleProcessCoordinator) r7
                java.lang.Object r7 = r7.lock(r4, r0)
                if (r7 != r1) goto L67
                goto L75
            L67:
                androidx.datastore.core.Data r7 = (androidx.datastore.core.Data) r7
                goto L78
            L6a:
                r0.L$0 = r6
                r0.label = r4
                r7 = 0
                java.lang.Object r7 = androidx.datastore.core.DataStoreImpl.access$readDataOrHandleCorruption(r2, r7, r0)
                if (r7 != r1) goto L76
            L75:
                return r1
            L76:
                androidx.datastore.core.Data r7 = (androidx.datastore.core.Data) r7
            L78:
                androidx.datastore.core.DataStoreImpl r6 = androidx.datastore.core.DataStoreImpl.this
                androidx.datastore.core.DataStoreInMemoryCache r6 = r6.inMemoryCache
                r6.tryUpdate(r7)
                kotlin.Unit r6 = kotlin.Unit.INSTANCE
                return r6
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.DataStoreImpl.InitDataStore.doRun(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
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
            public final Object mo779invoke(Object obj) {
                Throwable th = (Throwable) obj;
                DataStoreImpl dataStoreImpl = DataStoreImpl.this;
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
    /* JADX WARN: Removed duplicated region for block: B:14:0x0057 A[Catch: all -> 0x0061, TryCatch #0 {all -> 0x0061, blocks: (B:12:0x004f, B:14:0x0057, B:16:0x005b, B:17:0x005e, B:18:0x0063), top: B:11:0x004f }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /* JADX WARN: Type inference failed for: r4v7, types: [kotlinx.coroutines.sync.Mutex] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$decrementCollector(androidx.datastore.core.DataStoreImpl r4, kotlin.coroutines.jvm.internal.ContinuationImpl r5) {
        /*
            r4.getClass()
            boolean r0 = r5 instanceof androidx.datastore.core.DataStoreImpl$decrementCollector$1
            if (r0 == 0) goto L16
            r0 = r5
            androidx.datastore.core.DataStoreImpl$decrementCollector$1 r0 = (androidx.datastore.core.DataStoreImpl$decrementCollector$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            androidx.datastore.core.DataStoreImpl$decrementCollector$1 r0 = new androidx.datastore.core.DataStoreImpl$decrementCollector$1
            r0.<init>(r4, r5)
        L1b:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L3c
            if (r2 != r3) goto L34
            java.lang.Object r4 = r0.L$1
            kotlinx.coroutines.sync.Mutex r4 = (kotlinx.coroutines.sync.Mutex) r4
            java.lang.Object r0 = r0.L$0
            androidx.datastore.core.DataStoreImpl r0 = (androidx.datastore.core.DataStoreImpl) r0
            kotlin.ResultKt.throwOnFailure(r5)
            r5 = r4
            r4 = r0
            goto L4e
        L34:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L3c:
            kotlin.ResultKt.throwOnFailure(r5)
            kotlinx.coroutines.sync.MutexImpl r5 = r4.collectorMutex
            r0.L$0 = r4
            r0.L$1 = r5
            r0.label = r3
            java.lang.Object r0 = r5.lock(r0)
            if (r0 != r1) goto L4e
            return r1
        L4e:
            r0 = 0
            int r1 = r4.collectorCounter     // Catch: java.lang.Throwable -> L61
            int r1 = r1 + (-1)
            r4.collectorCounter = r1     // Catch: java.lang.Throwable -> L61
            if (r1 != 0) goto L63
            kotlinx.coroutines.StandaloneCoroutine r1 = r4.collectorJob     // Catch: java.lang.Throwable -> L61
            if (r1 == 0) goto L5e
            r1.cancel(r0)     // Catch: java.lang.Throwable -> L61
        L5e:
            r4.collectorJob = r0     // Catch: java.lang.Throwable -> L61
            goto L63
        L61:
            r4 = move-exception
            goto L6b
        L63:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L61
            r5.unlock(r0)
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        L6b:
            r5.unlock(r0)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.DataStoreImpl.access$decrementCollector(androidx.datastore.core.DataStoreImpl, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(11:0|1|(2:3|(7:5|6|7|(7:(1:(1:(1:12)(2:23|24))(3:25|26|27))(1:39)|13|14|15|(1:17)(1:21)|18|19)(5:40|41|42|(3:44|45|46)(4:50|(2:55|(2:57|58)(2:59|60))|61|(2:63|(1:65))(2:66|67))|33)|28|29|30))|70|6|7|(0)(0)|28|29|30|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00d7, code lost:
    
        if (r9 != r1) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0085, code lost:
    
        r8 = r11;
        r11 = r9;
        r9 = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00dd, code lost:
    
        r9 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0082, code lost:
    
        if (r9 == r1) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0036, code lost:
    
        r10 = th;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00f7  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0027  */
    /* JADX WARN: Type inference failed for: r9v0, types: [androidx.datastore.core.DataStoreImpl, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r9v1 */
    /* JADX WARN: Type inference failed for: r9v12 */
    /* JADX WARN: Type inference failed for: r9v15 */
    /* JADX WARN: Type inference failed for: r9v19 */
    /* JADX WARN: Type inference failed for: r9v2 */
    /* JADX WARN: Type inference failed for: r9v31 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$handleUpdate(androidx.datastore.core.DataStoreImpl r9, androidx.datastore.core.Message.Update r10, kotlin.coroutines.jvm.internal.ContinuationImpl r11) {
        /*
            Method dump skipped, instructions count: 253
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.DataStoreImpl.access$handleUpdate(androidx.datastore.core.DataStoreImpl, androidx.datastore.core.Message$Update, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0056 A[Catch: all -> 0x0065, TryCatch #0 {all -> 0x0065, blocks: (B:12:0x004f, B:14:0x0056, B:15:0x0067), top: B:11:0x004f }] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /* JADX WARN: Type inference failed for: r4v7, types: [kotlinx.coroutines.sync.Mutex] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$incrementCollector(androidx.datastore.core.DataStoreImpl r4, kotlin.coroutines.jvm.internal.ContinuationImpl r5) {
        /*
            r4.getClass()
            boolean r0 = r5 instanceof androidx.datastore.core.DataStoreImpl$incrementCollector$1
            if (r0 == 0) goto L16
            r0 = r5
            androidx.datastore.core.DataStoreImpl$incrementCollector$1 r0 = (androidx.datastore.core.DataStoreImpl$incrementCollector$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            androidx.datastore.core.DataStoreImpl$incrementCollector$1 r0 = new androidx.datastore.core.DataStoreImpl$incrementCollector$1
            r0.<init>(r4, r5)
        L1b:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L3c
            if (r2 != r3) goto L34
            java.lang.Object r4 = r0.L$1
            kotlinx.coroutines.sync.Mutex r4 = (kotlinx.coroutines.sync.Mutex) r4
            java.lang.Object r0 = r0.L$0
            androidx.datastore.core.DataStoreImpl r0 = (androidx.datastore.core.DataStoreImpl) r0
            kotlin.ResultKt.throwOnFailure(r5)
            r5 = r4
            r4 = r0
            goto L4e
        L34:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L3c:
            kotlin.ResultKt.throwOnFailure(r5)
            kotlinx.coroutines.sync.MutexImpl r5 = r4.collectorMutex
            r0.L$0 = r4
            r0.L$1 = r5
            r0.label = r3
            java.lang.Object r0 = r5.lock(r0)
            if (r0 != r1) goto L4e
            return r1
        L4e:
            r0 = 0
            int r1 = r4.collectorCounter     // Catch: java.lang.Throwable -> L65
            int r1 = r1 + r3
            r4.collectorCounter = r1     // Catch: java.lang.Throwable -> L65
            if (r1 != r3) goto L67
            kotlinx.coroutines.CoroutineScope r1 = r4.scope     // Catch: java.lang.Throwable -> L65
            androidx.datastore.core.DataStoreImpl$incrementCollector$2$1 r2 = new androidx.datastore.core.DataStoreImpl$incrementCollector$2$1     // Catch: java.lang.Throwable -> L65
            r2.<init>(r4, r0)     // Catch: java.lang.Throwable -> L65
            r3 = 3
            kotlinx.coroutines.StandaloneCoroutine r1 = kotlinx.coroutines.BuildersKt.launch$default(r1, r0, r0, r2, r3)     // Catch: java.lang.Throwable -> L65
            r4.collectorJob = r1     // Catch: java.lang.Throwable -> L65
            goto L67
        L65:
            r4 = move-exception
            goto L6f
        L67:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L65
            r5.unlock(r0)
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        L6f:
            r5.unlock(r0)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.DataStoreImpl.access$incrementCollector(androidx.datastore.core.DataStoreImpl, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$readDataAndUpdateCache(androidx.datastore.core.DataStoreImpl r8, boolean r9, kotlin.coroutines.Continuation r10) {
        /*
            Method dump skipped, instructions count: 234
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.DataStoreImpl.access$readDataAndUpdateCache(androidx.datastore.core.DataStoreImpl, boolean, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(8:0|1|(2:3|(4:5|6|7|8))|72|6|7|8|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0064, code lost:
    
        r11 = e;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002e  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0168  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0146 A[Catch: all -> 0x0174, TryCatch #0 {all -> 0x0174, blocks: (B:27:0x0134, B:29:0x0146, B:32:0x014e), top: B:26:0x0134 }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0160  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x014e A[Catch: all -> 0x0174, TRY_LEAVE, TryCatch #0 {all -> 0x0174, blocks: (B:27:0x0134, B:29:0x0146, B:32:0x014e), top: B:26:0x0134 }] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0107  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00a4 A[Catch: CorruptionException -> 0x0064, TryCatch #1 {CorruptionException -> 0x0064, blocks: (B:36:0x005f, B:37:0x0109, B:40:0x006d, B:41:0x00e9, B:56:0x008a, B:58:0x00a4, B:59:0x00aa, B:65:0x0093, B:68:0x00d4), top: B:7:0x0023 }] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00c3  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$readDataOrHandleCorruption(androidx.datastore.core.DataStoreImpl r9, boolean r10, kotlin.coroutines.jvm.internal.ContinuationImpl r11) {
        /*
            Method dump skipped, instructions count: 396
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.DataStoreImpl.access$readDataOrHandleCorruption(androidx.datastore.core.DataStoreImpl, boolean, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public final InterProcessCoordinator getCoordinator() {
        return (InterProcessCoordinator) this.coordinator$delegate.getValue();
    }

    @Override // androidx.datastore.core.DataStore
    public final Flow getData() {
        return this.data;
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0068, code lost:
    
        if (r2.runIfNeeded(r0) != r1) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x006a, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0053, code lost:
    
        if (r7 == r1) goto L26;
     */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object readAndInitOrPropagateAndThrowFailure(kotlin.coroutines.jvm.internal.ContinuationImpl r7) {
        /*
            r6 = this;
            boolean r0 = r7 instanceof androidx.datastore.core.DataStoreImpl$readAndInitOrPropagateAndThrowFailure$1
            if (r0 == 0) goto L13
            r0 = r7
            androidx.datastore.core.DataStoreImpl$readAndInitOrPropagateAndThrowFailure$1 r0 = (androidx.datastore.core.DataStoreImpl$readAndInitOrPropagateAndThrowFailure$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.datastore.core.DataStoreImpl$readAndInitOrPropagateAndThrowFailure$1 r0 = new androidx.datastore.core.DataStoreImpl$readAndInitOrPropagateAndThrowFailure$1
            r0.<init>(r6, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L42
            if (r2 == r4) goto L3a
            if (r2 != r3) goto L32
            int r6 = r0.I$0
            java.lang.Object r0 = r0.L$0
            androidx.datastore.core.DataStoreImpl r0 = (androidx.datastore.core.DataStoreImpl) r0
            kotlin.ResultKt.throwOnFailure(r7)     // Catch: java.lang.Throwable -> L30
            goto L6b
        L30:
            r7 = move-exception
            goto L75
        L32:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L3a:
            java.lang.Object r6 = r0.L$0
            androidx.datastore.core.DataStoreImpl r6 = (androidx.datastore.core.DataStoreImpl) r6
            kotlin.ResultKt.throwOnFailure(r7)
            goto L56
        L42:
            kotlin.ResultKt.throwOnFailure(r7)
            androidx.datastore.core.InterProcessCoordinator r7 = r6.getCoordinator()
            r0.L$0 = r6
            r0.label = r4
            androidx.datastore.core.SingleProcessCoordinator r7 = (androidx.datastore.core.SingleProcessCoordinator) r7
            java.lang.Object r7 = r7.getVersion()
            if (r7 != r1) goto L56
            goto L6a
        L56:
            java.lang.Number r7 = (java.lang.Number) r7
            int r7 = r7.intValue()
            androidx.datastore.core.DataStoreImpl$InitDataStore r2 = r6.readAndInit     // Catch: java.lang.Throwable -> L73
            r0.L$0 = r6     // Catch: java.lang.Throwable -> L73
            r0.I$0 = r7     // Catch: java.lang.Throwable -> L73
            r0.label = r3     // Catch: java.lang.Throwable -> L73
            java.lang.Object r6 = r2.runIfNeeded(r0)     // Catch: java.lang.Throwable -> L73
            if (r6 != r1) goto L6b
        L6a:
            return r1
        L6b:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        L6e:
            r5 = r0
            r0 = r6
            r6 = r7
            r7 = r5
            goto L75
        L73:
            r0 = move-exception
            goto L6e
        L75:
            androidx.datastore.core.DataStoreInMemoryCache r0 = r0.inMemoryCache
            androidx.datastore.core.ReadException r1 = new androidx.datastore.core.ReadException
            r1.<init>(r7, r6)
            r0.tryUpdate(r1)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.DataStoreImpl.readAndInitOrPropagateAndThrowFailure(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
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
        return BuildersKt.withContext(new UpdatingDataContextElement(updatingDataContextElement, this), new DataStoreImpl$updateData$2(this, function2, null), continuation);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object writeData$datastore_core_release(java.lang.Object r11, boolean r12, kotlin.coroutines.jvm.internal.ContinuationImpl r13) {
        /*
            r10 = this;
            boolean r0 = r13 instanceof androidx.datastore.core.DataStoreImpl$writeData$1
            if (r0 == 0) goto L13
            r0 = r13
            androidx.datastore.core.DataStoreImpl$writeData$1 r0 = (androidx.datastore.core.DataStoreImpl$writeData$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.datastore.core.DataStoreImpl$writeData$1 r0 = new androidx.datastore.core.DataStoreImpl$writeData$1
            r0.<init>(r10, r13)
        L18:
            java.lang.Object r13 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L33
            if (r2 != r3) goto L2b
            java.lang.Object r10 = r0.L$0
            kotlin.jvm.internal.Ref$IntRef r10 = (kotlin.jvm.internal.Ref$IntRef) r10
            kotlin.ResultKt.throwOnFailure(r13)
            goto L58
        L2b:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L33:
            kotlin.ResultKt.throwOnFailure(r13)
            kotlin.jvm.internal.Ref$IntRef r5 = new kotlin.jvm.internal.Ref$IntRef
            r5.<init>()
            kotlin.Lazy r13 = r10.storageConnectionDelegate
            java.lang.Object r13 = r13.getValue()
            androidx.datastore.core.StorageConnection r13 = (androidx.datastore.core.StorageConnection) r13
            androidx.datastore.core.DataStoreImpl$writeData$2 r4 = new androidx.datastore.core.DataStoreImpl$writeData$2
            r9 = 0
            r6 = r10
            r7 = r11
            r8 = r12
            r4.<init>(r5, r6, r7, r8, r9)
            r0.L$0 = r5
            r0.label = r3
            java.lang.Object r10 = r13.writeScope(r4, r0)
            if (r10 != r1) goto L57
            return r1
        L57:
            r10 = r5
        L58:
            int r10 = r10.element
            java.lang.Integer r11 = new java.lang.Integer
            r11.<init>(r10)
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.DataStoreImpl.writeData$datastore_core_release(java.lang.Object, boolean, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public DataStoreImpl(androidx.datastore.core.Storage r1, java.util.List r2, androidx.datastore.core.CorruptionHandler r3, kotlinx.coroutines.CoroutineScope r4, int r5, kotlin.jvm.internal.DefaultConstructorMarker r6) {
        /*
            r0 = this;
            r6 = r5 & 2
            if (r6 == 0) goto L6
            kotlin.collections.EmptyList r2 = kotlin.collections.EmptyList.INSTANCE
        L6:
            r6 = r5 & 4
            if (r6 == 0) goto Lf
            androidx.datastore.core.handlers.NoOpCorruptionHandler r3 = new androidx.datastore.core.handlers.NoOpCorruptionHandler
            r3.<init>()
        Lf:
            r5 = r5 & 8
            if (r5 == 0) goto L26
            kotlinx.coroutines.scheduling.DefaultScheduler r4 = kotlinx.coroutines.Dispatchers.Default
            kotlinx.coroutines.scheduling.DefaultIoScheduler r4 = kotlinx.coroutines.scheduling.DefaultIoScheduler.INSTANCE
            kotlinx.coroutines.SupervisorJobImpl r5 = kotlinx.coroutines.SupervisorKt.SupervisorJob$default()
            r4.getClass()
            kotlin.coroutines.CoroutineContext r4 = kotlin.coroutines.CoroutineContext.DefaultImpls.plus(r4, r5)
            kotlinx.coroutines.internal.ContextScope r4 = kotlinx.coroutines.CoroutineScopeKt.CoroutineScope(r4)
        L26:
            r0.<init>(r1, r2, r3, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.DataStoreImpl.<init>(androidx.datastore.core.Storage, java.util.List, androidx.datastore.core.CorruptionHandler, kotlinx.coroutines.CoroutineScope, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
