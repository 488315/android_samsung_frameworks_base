package androidx.datastore.core;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$IntRef;

/* loaded from: classes.dex */
final class DataStoreImpl$writeData$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Object $newData;
    final /* synthetic */ Ref$IntRef $newVersion;
    final /* synthetic */ boolean $updateCache;
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ DataStoreImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DataStoreImpl$writeData$2(Ref$IntRef ref$IntRef, DataStoreImpl dataStoreImpl, Object obj, boolean z, Continuation continuation) {
        super(2, continuation);
        this.$newVersion = ref$IntRef;
        this.this$0 = dataStoreImpl;
        this.$newData = obj;
        this.$updateCache = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DataStoreImpl$writeData$2 dataStoreImpl$writeData$2 = new DataStoreImpl$writeData$2(this.$newVersion, this.this$0, this.$newData, this.$updateCache, continuation);
        dataStoreImpl$writeData$2.L$0 = obj;
        return dataStoreImpl$writeData$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DataStoreImpl$writeData$2) create((WriteScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0064, code lost:
    
        if (r3.writeData(r7, r6) == r0) goto L16;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        Ref$IntRef ref$IntRef;
        WriteScope writeScope;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            WriteScope writeScope2 = (WriteScope) this.L$0;
            ref$IntRef = this.$newVersion;
            DataStoreImpl dataStoreImpl = this.this$0;
            int i2 = DataStoreImpl.$r8$clinit;
            InterProcessCoordinator coordinator = dataStoreImpl.getCoordinator();
            this.L$0 = writeScope2;
            this.L$1 = ref$IntRef;
            this.label = 1;
            Integer num = new Integer(((SingleProcessCoordinator) coordinator).version.delegate.incrementAndGet());
            if (num != coroutineSingletons) {
                writeScope = writeScope2;
                obj = num;
            }
            return coroutineSingletons;
        }
        if (i != 1) {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            if (this.$updateCache) {
                DataStoreInMemoryCache dataStoreInMemoryCache = this.this$0.inMemoryCache;
                Object obj2 = this.$newData;
                dataStoreInMemoryCache.tryUpdate(new Data(obj2, obj2 != null ? obj2.hashCode() : 0, this.$newVersion.element));
            }
            return Unit.INSTANCE;
        }
        ref$IntRef = (Ref$IntRef) this.L$1;
        writeScope = (WriteScope) this.L$0;
        ResultKt.throwOnFailure(obj);
        ref$IntRef.element = ((Number) obj).intValue();
        Object obj3 = this.$newData;
        this.L$0 = null;
        this.L$1 = null;
        this.label = 2;
    }
}
