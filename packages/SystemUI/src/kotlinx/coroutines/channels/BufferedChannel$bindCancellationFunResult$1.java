package kotlinx.coroutines.channels;

import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlinx.coroutines.internal.OnUndeliveredElementKt;

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
        Object objM3478getOrNullimpl = ChannelResult.m3478getOrNullimpl(obj4);
        objM3478getOrNullimpl.getClass();
        OnUndeliveredElementKt.callUndeliveredElement(function1, objM3478getOrNullimpl, (CoroutineContext) obj3);
        return Unit.INSTANCE;
    }
}
