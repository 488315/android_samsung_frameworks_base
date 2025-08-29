package kotlinx.coroutines.channels;

import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.ChannelResult;

/* loaded from: classes4.dex */
final class ChannelsKt__ChannelsKt$trySendBlocking$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Object $element;
    final /* synthetic */ SendChannel $this_trySendBlocking;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ChannelsKt__ChannelsKt$trySendBlocking$2(SendChannel sendChannel, Object obj, Continuation continuation) {
        super(2, continuation);
        this.$this_trySendBlocking = sendChannel;
        this.$element = obj;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ChannelsKt__ChannelsKt$trySendBlocking$2 channelsKt__ChannelsKt$trySendBlocking$2 = new ChannelsKt__ChannelsKt$trySendBlocking$2(this.$this_trySendBlocking, this.$element, continuation);
        channelsKt__ChannelsKt$trySendBlocking$2.L$0 = obj;
        return channelsKt__ChannelsKt$trySendBlocking$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ChannelsKt__ChannelsKt$trySendBlocking$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object failure;
        Object objM3479closedJP2dKIU;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        try {
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                SendChannel sendChannel = this.$this_trySendBlocking;
                Object obj2 = this.$element;
                int i2 = Result.$r8$clinit;
                this.label = 1;
                if (sendChannel.send(obj2, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            failure = Unit.INSTANCE;
            int i3 = Result.$r8$clinit;
        } catch (Throwable th) {
            int i4 = Result.$r8$clinit;
            failure = new Result.Failure(th);
        }
        if (failure instanceof Result.Failure) {
            ChannelResult.Companion companion = ChannelResult.Companion;
            Throwable thM3441exceptionOrNullimpl = Result.m3441exceptionOrNullimpl(failure);
            companion.getClass();
            objM3479closedJP2dKIU = ChannelResult.Companion.m3479closedJP2dKIU(thM3441exceptionOrNullimpl);
        } else {
            ChannelResult.Companion companion2 = ChannelResult.Companion;
            objM3479closedJP2dKIU = Unit.INSTANCE;
            companion2.getClass();
        }
        return ChannelResult.m3476boximpl(objM3479closedJP2dKIU);
    }
}
