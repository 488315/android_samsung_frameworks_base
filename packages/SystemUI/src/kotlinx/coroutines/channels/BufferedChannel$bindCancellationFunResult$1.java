package kotlinx.coroutines.channels;

import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlinx.coroutines.internal.OnUndeliveredElementKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
final /* synthetic */ class BufferedChannel$bindCancellationFunResult$1 extends FunctionReferenceImpl implements Function3 {
    public BufferedChannel$bindCancellationFunResult$1(Object obj) {
        super(3, obj, BufferedChannel.class, "onCancellationChannelResultImplDoNotCall", "onCancellationChannelResultImplDoNotCall-5_sEAP8(Ljava/lang/Throwable;Ljava/lang/Object;Lkotlin/coroutines/CoroutineContext;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        Object obj4 = ((ChannelResult) obj2).holder;
        Function1 function1 = ((BufferedChannel) this.receiver).onUndeliveredElement;
        function1.getClass();
        Object m3459getOrNullimpl = ChannelResult.m3459getOrNullimpl(obj4);
        m3459getOrNullimpl.getClass();
        OnUndeliveredElementKt.callUndeliveredElement(function1, m3459getOrNullimpl, (CoroutineContext) obj3);
        return Unit.INSTANCE;
    }
}
