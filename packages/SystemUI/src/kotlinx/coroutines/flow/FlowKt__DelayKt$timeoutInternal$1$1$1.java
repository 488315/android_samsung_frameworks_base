package kotlinx.coroutines.flow;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelResult;

/* loaded from: classes4.dex */
final class FlowKt__DelayKt$timeoutInternal$1$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ FlowCollector $downStream;
    /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FlowKt__DelayKt$timeoutInternal$1$1$1(FlowCollector flowCollector, Continuation continuation) {
        super(2, continuation);
        this.$downStream = flowCollector;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        FlowKt__DelayKt$timeoutInternal$1$1$1 flowKt__DelayKt$timeoutInternal$1$1$1 = new FlowKt__DelayKt$timeoutInternal$1$1$1(this.$downStream, continuation);
        flowKt__DelayKt$timeoutInternal$1$1$1.L$0 = obj;
        return flowKt__DelayKt$timeoutInternal$1$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FlowKt__DelayKt$timeoutInternal$1$1$1) create(ChannelResult.m3476boximpl(((ChannelResult) obj).holder), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0043  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        Object obj2;
        Object obj3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            obj2 = ((ChannelResult) this.L$0).holder;
            FlowCollector flowCollector = this.$downStream;
            if (!(obj2 instanceof ChannelResult.Failed)) {
                this.L$0 = obj2;
                this.label = 1;
                if (flowCollector.emit(obj2, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                obj3 = obj2;
            }
            if (obj2 instanceof ChannelResult.Closed) {
                return Boolean.TRUE;
            }
            Throwable thM3477exceptionOrNullimpl = ChannelResult.m3477exceptionOrNullimpl((ChannelResult.Failed) obj2);
            if (thM3477exceptionOrNullimpl == null) {
                return Boolean.FALSE;
            }
            throw thM3477exceptionOrNullimpl;
        }
        if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        obj3 = this.L$0;
        ResultKt.throwOnFailure(obj);
        obj2 = obj3;
        if (obj2 instanceof ChannelResult.Closed) {
        }
    }
}
