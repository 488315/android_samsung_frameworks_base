package androidx.datastore.core;

import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class DataStoreImpl$readDataAndUpdateCache$3 extends SuspendLambda implements Function1 {
    Object L$0;
    int label;
    final /* synthetic */ DataStoreImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DataStoreImpl$readDataAndUpdateCache$3(DataStoreImpl dataStoreImpl, Continuation continuation) {
        super(1, continuation);
        this.this$0 = dataStoreImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Continuation continuation) {
        return new DataStoreImpl$readDataAndUpdateCache$3(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        return ((DataStoreImpl$readDataAndUpdateCache$3) create((Continuation) obj)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Throwable th;
        State state;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        try {
        } catch (Throwable th2) {
            DataStoreImpl dataStoreImpl = this.this$0;
            int i2 = DataStoreImpl.$r8$clinit;
            InterProcessCoordinator coordinator = dataStoreImpl.getCoordinator();
            this.L$0 = th2;
            this.label = 2;
            Object version = ((SingleProcessCoordinator) coordinator).getVersion();
            if (version != coroutineSingletons) {
                obj = version;
                th = th2;
            }
        }
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            DataStoreImpl dataStoreImpl2 = this.this$0;
            this.label = 1;
            obj = DataStoreImpl.access$readDataOrHandleCorruption(dataStoreImpl2, true, this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                th = (Throwable) this.L$0;
                ResultKt.throwOnFailure(obj);
                state = new ReadException(th, ((Number) obj).intValue());
                return new Pair(state, Boolean.TRUE);
            }
            ResultKt.throwOnFailure(obj);
        }
        state = (State) obj;
        return new Pair(state, Boolean.TRUE);
    }
}
