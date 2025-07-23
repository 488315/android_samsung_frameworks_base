package androidx.datastore.core;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Ref$IntRef;
import kotlin.jvm.internal.Ref$ObjectRef;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class DataStoreImpl$readDataOrHandleCorruption$3 extends SuspendLambda implements Function1 {
    final /* synthetic */ Ref$ObjectRef<Object> $newData;
    final /* synthetic */ Ref$IntRef $version;
    Object L$0;
    int label;
    final /* synthetic */ DataStoreImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DataStoreImpl$readDataOrHandleCorruption$3(Ref$ObjectRef<Object> ref$ObjectRef, DataStoreImpl dataStoreImpl, Ref$IntRef ref$IntRef, Continuation continuation) {
        super(1, continuation);
        this.$newData = ref$ObjectRef;
        this.this$0 = dataStoreImpl;
        this.$version = ref$IntRef;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Continuation continuation) {
        return new DataStoreImpl$readDataOrHandleCorruption$3(this.$newData, this.this$0, this.$version, continuation);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        return ((DataStoreImpl$readDataOrHandleCorruption$3) create((Continuation) obj)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object obj2;
        Ref$IntRef ref$IntRef;
        Ref$ObjectRef<Object> ref$ObjectRef;
        T t;
        Ref$IntRef ref$IntRef2;
        Object obj3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        try {
        } catch (CorruptionException unused) {
            Ref$IntRef ref$IntRef3 = this.$version;
            DataStoreImpl dataStoreImpl = this.this$0;
            Object obj4 = this.$newData.element;
            this.L$0 = ref$IntRef3;
            this.label = 3;
            Object writeData$datastore_core_release = dataStoreImpl.writeData$datastore_core_release(obj4, true, this);
            if (writeData$datastore_core_release != coroutineSingletons) {
                obj2 = writeData$datastore_core_release;
                ref$IntRef = ref$IntRef3;
            }
        }
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ref$ObjectRef = this.$newData;
            DataStoreImpl dataStoreImpl2 = this.this$0;
            this.L$0 = ref$ObjectRef;
            this.label = 1;
            int i2 = DataStoreImpl.$r8$clinit;
            Object readDataFromFileOrDefault = dataStoreImpl2.readDataFromFileOrDefault(this);
            t = readDataFromFileOrDefault;
            if (readDataFromFileOrDefault == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                if (i == 2) {
                    ref$IntRef2 = (Ref$IntRef) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    obj3 = obj;
                    ref$IntRef2.element = ((Number) obj3).intValue();
                    return Unit.INSTANCE;
                }
                if (i != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ref$IntRef = (Ref$IntRef) this.L$0;
                ResultKt.throwOnFailure(obj);
                obj2 = obj;
                ref$IntRef.element = ((Number) obj2).intValue();
                return Unit.INSTANCE;
            }
            ref$ObjectRef = (Ref$ObjectRef) this.L$0;
            ResultKt.throwOnFailure(obj);
            t = obj;
        }
        ref$ObjectRef.element = t;
        ref$IntRef2 = this.$version;
        DataStoreImpl dataStoreImpl3 = this.this$0;
        int i3 = DataStoreImpl.$r8$clinit;
        InterProcessCoordinator coordinator = dataStoreImpl3.getCoordinator();
        this.L$0 = ref$IntRef2;
        this.label = 2;
        Object version = ((SingleProcessCoordinator) coordinator).getVersion();
        obj3 = version;
        if (version == coroutineSingletons) {
            return coroutineSingletons;
        }
        ref$IntRef2.element = ((Number) obj3).intValue();
        return Unit.INSTANCE;
    }
}
