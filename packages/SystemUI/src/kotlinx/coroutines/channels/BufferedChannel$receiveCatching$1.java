package kotlinx.coroutines.channels;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
final class BufferedChannel$receiveCatching$1<E> extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ BufferedChannel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BufferedChannel$receiveCatching$1(BufferedChannel bufferedChannel, Continuation continuation) {
        super(continuation);
        this.this$0 = bufferedChannel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        Object m3452receiveCatchingJP2dKIU$suspendImpl = BufferedChannel.m3452receiveCatchingJP2dKIU$suspendImpl(this.this$0, this);
        return m3452receiveCatchingJP2dKIU$suspendImpl == CoroutineSingletons.COROUTINE_SUSPENDED ? m3452receiveCatchingJP2dKIU$suspendImpl : ChannelResult.m3457boximpl(m3452receiveCatchingJP2dKIU$suspendImpl);
    }
}
