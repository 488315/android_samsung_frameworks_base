package kotlinx.coroutines.flow;

import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.internal.ChannelFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class CallbackFlowBuilder extends ChannelFlowBuilder {
    public final Function2 block;

    public /* synthetic */ CallbackFlowBuilder(Function2 function2, CoroutineContext coroutineContext, int i, BufferOverflow bufferOverflow, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(function2, (i2 & 2) != 0 ? EmptyCoroutineContext.INSTANCE : coroutineContext, (i2 & 4) != 0 ? -2 : i, (i2 & 8) != 0 ? BufferOverflow.SUSPEND : bufferOverflow);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // kotlinx.coroutines.flow.ChannelFlowBuilder, kotlinx.coroutines.flow.internal.ChannelFlow
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object collectTo(ProducerScope producerScope, Continuation continuation) {
        CallbackFlowBuilder$collectTo$1 callbackFlowBuilder$collectTo$1;
        int i;
        Object obj;
        if (continuation instanceof CallbackFlowBuilder$collectTo$1) {
            callbackFlowBuilder$collectTo$1 = (CallbackFlowBuilder$collectTo$1) continuation;
            int i2 = callbackFlowBuilder$collectTo$1.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                callbackFlowBuilder$collectTo$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj2 = callbackFlowBuilder$collectTo$1.result;
                Object obj3 = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = callbackFlowBuilder$collectTo$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj2);
                    callbackFlowBuilder$collectTo$1.L$0 = producerScope;
                    callbackFlowBuilder$collectTo$1.label = 1;
                    Object collectTo = super.collectTo(producerScope, callbackFlowBuilder$collectTo$1);
                    obj = producerScope;
                    if (collectTo == obj3) {
                        return obj3;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ProducerScope producerScope2 = (ProducerScope) callbackFlowBuilder$collectTo$1.L$0;
                    ResultKt.throwOnFailure(obj2);
                    obj = producerScope2;
                }
                if (((ChannelCoroutine) obj).isClosedForSend()) {
                    throw new IllegalStateException("'awaitClose { yourCallbackOrListener.cancel() }' should be used in the end of callbackFlow block.\nOtherwise, a callback/listener may leak in case of external cancellation.\nSee callbackFlow API documentation for the details.");
                }
                return Unit.INSTANCE;
            }
        }
        callbackFlowBuilder$collectTo$1 = new CallbackFlowBuilder$collectTo$1(this, continuation);
        Object obj22 = callbackFlowBuilder$collectTo$1.result;
        Object obj32 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = callbackFlowBuilder$collectTo$1.label;
        if (i != 0) {
        }
        if (((ChannelCoroutine) obj).isClosedForSend()) {
        }
    }

    @Override // kotlinx.coroutines.flow.ChannelFlowBuilder, kotlinx.coroutines.flow.internal.ChannelFlow
    public final ChannelFlow create(CoroutineContext coroutineContext, int i, BufferOverflow bufferOverflow) {
        return new CallbackFlowBuilder(this.block, coroutineContext, i, bufferOverflow);
    }

    public CallbackFlowBuilder(Function2 function2, CoroutineContext coroutineContext, int i, BufferOverflow bufferOverflow) {
        super(function2, coroutineContext, i, bufferOverflow);
        this.block = function2;
    }
}
