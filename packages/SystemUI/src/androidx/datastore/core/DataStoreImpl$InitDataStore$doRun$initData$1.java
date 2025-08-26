package androidx.datastore.core;

import androidx.datastore.core.DataStoreImpl;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexKt;

/* loaded from: classes.dex */
final class DataStoreImpl$InitDataStore$doRun$initData$1 extends SuspendLambda implements Function1 {
    int I$0;
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    Object L$4;
    int label;
    final /* synthetic */ DataStoreImpl this$0;
    final /* synthetic */ DataStoreImpl.InitDataStore this$1;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DataStoreImpl$InitDataStore$doRun$initData$1(DataStoreImpl dataStoreImpl, DataStoreImpl.InitDataStore initDataStore, Continuation continuation) {
        super(1, continuation);
        this.this$0 = dataStoreImpl;
        this.this$1 = initDataStore;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Continuation continuation) {
        return new DataStoreImpl$InitDataStore$doRun$initData$1(this.this$0, this.this$1, continuation);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        return ((DataStoreImpl$InitDataStore$doRun$initData$1) create((Continuation) obj)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00f1  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x010d  */
    /* JADX WARN: Type inference failed for: r12v5, types: [T, java.lang.Object] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws CorruptionException {
        Mutex mutexMutex$default;
        Ref$BooleanRef ref$BooleanRef;
        Ref$ObjectRef ref$ObjectRef;
        Ref$ObjectRef ref$ObjectRef2;
        Ref$BooleanRef ref$BooleanRef2;
        Mutex mutex;
        Iterator it;
        Mutex mutex2;
        Ref$BooleanRef ref$BooleanRef3;
        Ref$ObjectRef ref$ObjectRef3;
        DataStoreImpl$InitDataStore$doRun$initData$1$api$1 dataStoreImpl$InitDataStore$doRun$initData$1$api$1;
        Ref$ObjectRef ref$ObjectRef4;
        Object version;
        Object obj2;
        int i;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = this.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            mutexMutex$default = MutexKt.Mutex$default();
            ref$BooleanRef = new Ref$BooleanRef();
            ref$ObjectRef = new Ref$ObjectRef();
            DataStoreImpl dataStoreImpl = this.this$0;
            this.L$0 = mutexMutex$default;
            this.L$1 = ref$BooleanRef;
            this.L$2 = ref$ObjectRef;
            this.L$3 = ref$ObjectRef;
            this.label = 1;
            obj = DataStoreImpl.access$readDataOrHandleCorruption(dataStoreImpl, true, this);
            if (obj != coroutineSingletons) {
                ref$ObjectRef2 = ref$ObjectRef;
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                if (i2 != 3) {
                    if (i2 != 4) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    i = this.I$0;
                    obj2 = this.L$0;
                    ResultKt.throwOnFailure(obj);
                    return new Data(obj2, i, ((Number) obj).intValue());
                }
                mutex = (Mutex) this.L$2;
                ref$ObjectRef4 = (Ref$ObjectRef) this.L$1;
                ref$BooleanRef2 = (Ref$BooleanRef) this.L$0;
                ResultKt.throwOnFailure(obj);
                try {
                    ref$BooleanRef2.element = true;
                    Unit unit = Unit.INSTANCE;
                    mutex.unlock(null);
                    Object obj3 = ref$ObjectRef4.element;
                    int iHashCode = obj3 == null ? obj3.hashCode() : 0;
                    DataStoreImpl dataStoreImpl2 = this.this$0;
                    int i3 = DataStoreImpl.$r8$clinit;
                    InterProcessCoordinator coordinator = dataStoreImpl2.getCoordinator();
                    this.L$0 = obj3;
                    this.L$1 = null;
                    this.L$2 = null;
                    this.I$0 = iHashCode;
                    this.label = 4;
                    version = ((SingleProcessCoordinator) coordinator).getVersion();
                    if (version != coroutineSingletons) {
                        obj = version;
                        obj2 = obj3;
                        i = iHashCode;
                        return new Data(obj2, i, ((Number) obj).intValue());
                    }
                    return coroutineSingletons;
                } catch (Throwable th) {
                    mutex.unlock(null);
                    throw th;
                }
            }
            it = (Iterator) this.L$4;
            dataStoreImpl$InitDataStore$doRun$initData$1$api$1 = (DataStoreImpl$InitDataStore$doRun$initData$1$api$1) this.L$3;
            ref$ObjectRef3 = (Ref$ObjectRef) this.L$2;
            ref$BooleanRef3 = (Ref$BooleanRef) this.L$1;
            mutex2 = (Mutex) this.L$0;
            ResultKt.throwOnFailure(obj);
            while (it.hasNext()) {
                Function2 function2 = (Function2) it.next();
                this.L$0 = mutex2;
                this.L$1 = ref$BooleanRef3;
                this.L$2 = ref$ObjectRef3;
                this.L$3 = dataStoreImpl$InitDataStore$doRun$initData$1$api$1;
                this.L$4 = it;
                this.label = 2;
                if (function2.invoke(dataStoreImpl$InitDataStore$doRun$initData$1$api$1, this) == coroutineSingletons) {
                    break;
                }
            }
            ref$ObjectRef2 = ref$ObjectRef3;
            ref$BooleanRef2 = ref$BooleanRef3;
            mutex = mutex2;
            this.this$1.initTasks = null;
            this.L$0 = ref$BooleanRef2;
            this.L$1 = ref$ObjectRef2;
            this.L$2 = mutex;
            this.L$3 = null;
            this.L$4 = null;
            this.label = 3;
            if (mutex.lock(this) != coroutineSingletons) {
                ref$ObjectRef4 = ref$ObjectRef2;
                ref$BooleanRef2.element = true;
                Unit unit2 = Unit.INSTANCE;
                mutex.unlock(null);
                Object obj32 = ref$ObjectRef4.element;
                if (obj32 == null) {
                }
                DataStoreImpl dataStoreImpl22 = this.this$0;
                int i32 = DataStoreImpl.$r8$clinit;
                InterProcessCoordinator coordinator2 = dataStoreImpl22.getCoordinator();
                this.L$0 = obj32;
                this.L$1 = null;
                this.L$2 = null;
                this.I$0 = iHashCode;
                this.label = 4;
                version = ((SingleProcessCoordinator) coordinator2).getVersion();
                if (version != coroutineSingletons) {
                }
            }
            return coroutineSingletons;
        }
        ref$ObjectRef = (Ref$ObjectRef) this.L$3;
        ref$ObjectRef2 = (Ref$ObjectRef) this.L$2;
        ref$BooleanRef = (Ref$BooleanRef) this.L$1;
        mutexMutex$default = (Mutex) this.L$0;
        ResultKt.throwOnFailure(obj);
        ref$ObjectRef.element = ((Data) obj).value;
        DataStoreImpl$InitDataStore$doRun$initData$1$api$1 dataStoreImpl$InitDataStore$doRun$initData$1$api$12 = new DataStoreImpl$InitDataStore$doRun$initData$1$api$1(mutexMutex$default, ref$BooleanRef, ref$ObjectRef2, this.this$0);
        List list = this.this$1.initTasks;
        if (list == null) {
            ref$BooleanRef2 = ref$BooleanRef;
            mutex = mutexMutex$default;
            this.this$1.initTasks = null;
            this.L$0 = ref$BooleanRef2;
            this.L$1 = ref$ObjectRef2;
            this.L$2 = mutex;
            this.L$3 = null;
            this.L$4 = null;
            this.label = 3;
            if (mutex.lock(this) != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        it = list.iterator();
        mutex2 = mutexMutex$default;
        ref$BooleanRef3 = ref$BooleanRef;
        ref$ObjectRef3 = ref$ObjectRef2;
        dataStoreImpl$InitDataStore$doRun$initData$1$api$1 = dataStoreImpl$InitDataStore$doRun$initData$1$api$12;
        while (it.hasNext()) {
        }
        ref$ObjectRef2 = ref$ObjectRef3;
        ref$BooleanRef2 = ref$BooleanRef3;
        mutex = mutex2;
        this.this$1.initTasks = null;
        this.L$0 = ref$BooleanRef2;
        this.L$1 = ref$ObjectRef2;
        this.L$2 = mutex;
        this.L$3 = null;
        this.L$4 = null;
        this.label = 3;
        if (mutex.lock(this) != coroutineSingletons) {
        }
        return coroutineSingletons;
    }
}
