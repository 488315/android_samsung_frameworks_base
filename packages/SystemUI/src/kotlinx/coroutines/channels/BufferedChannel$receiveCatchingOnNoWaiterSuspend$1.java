package kotlinx.coroutines.channels;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
final class BufferedChannel$receiveCatchingOnNoWaiterSuspend$1 extends ContinuationImpl {
    int I$0;
    long J$0;
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ BufferedChannel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BufferedChannel$receiveCatchingOnNoWaiterSuspend$1(BufferedChannel bufferedChannel, Continuation continuation) {
        super(continuation);
        this.this$0 = bufferedChannel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        Object m3454receiveCatchingOnNoWaiterSuspendGKJJFZk = this.this$0.m3454receiveCatchingOnNoWaiterSuspendGKJJFZk(null, 0, 0L, this);
        return m3454receiveCatchingOnNoWaiterSuspendGKJJFZk == CoroutineSingletons.COROUTINE_SUSPENDED ? m3454receiveCatchingOnNoWaiterSuspendGKJJFZk : ChannelResult.m3457boximpl(m3454receiveCatchingOnNoWaiterSuspendGKJJFZk);
    }
}
