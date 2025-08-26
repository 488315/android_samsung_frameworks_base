package androidx.datastore.core;

import androidx.datastore.core.DataStoreImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes.dex */
final class DataStoreImpl$incrementCollector$2$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ DataStoreImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DataStoreImpl$incrementCollector$2$1(DataStoreImpl dataStoreImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = dataStoreImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DataStoreImpl$incrementCollector$2$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DataStoreImpl$incrementCollector$2$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0051, code lost:
    
        if (r5.collect(r1, r4) == r0) goto L18;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            DataStoreImpl.InitDataStore initDataStore = this.this$0.readAndInit;
            this.label = 1;
            Object objAwaitInternal = initDataStore.didRun.awaitInternal(this);
            if (objAwaitInternal != coroutineSingletons) {
                objAwaitInternal = Unit.INSTANCE;
            }
            if (objAwaitInternal != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i != 1) {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        ResultKt.throwOnFailure(obj);
        DataStoreImpl dataStoreImpl = this.this$0;
        int i2 = DataStoreImpl.$r8$clinit;
        Flow flowBuffer$default = FlowKt.buffer$default(((SingleProcessCoordinator) dataStoreImpl.getCoordinator()).updateNotifications, -1, 2);
        final DataStoreImpl dataStoreImpl2 = this.this$0;
        FlowCollector flowCollector = new FlowCollector() { // from class: androidx.datastore.core.DataStoreImpl$incrementCollector$2$1.1
            @Override // kotlinx.coroutines.flow.FlowCollector
            public final Object emit(Object obj2, Continuation continuation) {
                DataStoreImpl dataStoreImpl3 = dataStoreImpl2;
                if (dataStoreImpl3.inMemoryCache.getCurrentState() instanceof Final) {
                    return Unit.INSTANCE;
                }
                Object objAccess$readDataAndUpdateCache = DataStoreImpl.access$readDataAndUpdateCache(dataStoreImpl3, true, continuation);
                return objAccess$readDataAndUpdateCache == CoroutineSingletons.COROUTINE_SUSPENDED ? objAccess$readDataAndUpdateCache : Unit.INSTANCE;
            }
        };
        this.label = 2;
    }
}
