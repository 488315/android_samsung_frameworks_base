package kotlinx.coroutines.flow;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.ReceiveChannel;

/* loaded from: classes4.dex */
public abstract /* synthetic */ class FlowKt__ChannelsKt {
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0090, code lost:
    
        if (r2.emit(r9, r0) == r1) goto L33;
     */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x007e A[Catch: all -> 0x003a, TRY_LEAVE, TryCatch #0 {all -> 0x003a, blocks: (B:13:0x0034, B:25:0x0061, B:29:0x0076, B:31:0x007e, B:20:0x0052, B:24:0x005d), top: B:45:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:32:0x0090 -> B:14:0x0037). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object emitAllImpl$FlowKt__ChannelsKt(FlowCollector flowCollector, ReceiveChannel receiveChannel, boolean z, Continuation continuation) throws Throwable {
        FlowKt__ChannelsKt$emitAllImpl$1 flowKt__ChannelsKt$emitAllImpl$1;
        BufferedChannel.BufferedChannelIterator it;
        BufferedChannel.BufferedChannelIterator bufferedChannelIterator;
        FlowCollector flowCollector2;
        Object objHasNext;
        if (continuation instanceof FlowKt__ChannelsKt$emitAllImpl$1) {
            flowKt__ChannelsKt$emitAllImpl$1 = (FlowKt__ChannelsKt$emitAllImpl$1) continuation;
            int i = flowKt__ChannelsKt$emitAllImpl$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                flowKt__ChannelsKt$emitAllImpl$1.label = i - Integer.MIN_VALUE;
            } else {
                flowKt__ChannelsKt$emitAllImpl$1 = new FlowKt__ChannelsKt$emitAllImpl$1(continuation);
            }
        }
        Object obj = flowKt__ChannelsKt$emitAllImpl$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = flowKt__ChannelsKt$emitAllImpl$1.label;
        try {
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                if (flowCollector instanceof ThrowingCollector) {
                    throw ((ThrowingCollector) flowCollector).e;
                }
                it = receiveChannel.iterator();
                flowKt__ChannelsKt$emitAllImpl$1.L$0 = flowCollector;
                flowKt__ChannelsKt$emitAllImpl$1.L$1 = receiveChannel;
                flowKt__ChannelsKt$emitAllImpl$1.L$2 = it;
                flowKt__ChannelsKt$emitAllImpl$1.Z$0 = z;
                flowKt__ChannelsKt$emitAllImpl$1.label = 1;
                objHasNext = it.hasNext(flowKt__ChannelsKt$emitAllImpl$1);
                if (objHasNext != coroutineSingletons) {
                }
            } else if (i2 == 1) {
                z = flowKt__ChannelsKt$emitAllImpl$1.Z$0;
                bufferedChannelIterator = (BufferedChannel.BufferedChannelIterator) flowKt__ChannelsKt$emitAllImpl$1.L$2;
                receiveChannel = (ReceiveChannel) flowKt__ChannelsKt$emitAllImpl$1.L$1;
                flowCollector2 = (FlowCollector) flowKt__ChannelsKt$emitAllImpl$1.L$0;
                ResultKt.throwOnFailure(obj);
                if (((Boolean) obj).booleanValue()) {
                }
            } else {
                if (i2 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                z = flowKt__ChannelsKt$emitAllImpl$1.Z$0;
                bufferedChannelIterator = (BufferedChannel.BufferedChannelIterator) flowKt__ChannelsKt$emitAllImpl$1.L$2;
                receiveChannel = (ReceiveChannel) flowKt__ChannelsKt$emitAllImpl$1.L$1;
                flowCollector2 = (FlowCollector) flowKt__ChannelsKt$emitAllImpl$1.L$0;
                ResultKt.throwOnFailure(obj);
                it = bufferedChannelIterator;
                flowCollector = flowCollector2;
                flowKt__ChannelsKt$emitAllImpl$1.L$0 = flowCollector;
                flowKt__ChannelsKt$emitAllImpl$1.L$1 = receiveChannel;
                flowKt__ChannelsKt$emitAllImpl$1.L$2 = it;
                flowKt__ChannelsKt$emitAllImpl$1.Z$0 = z;
                flowKt__ChannelsKt$emitAllImpl$1.label = 1;
                objHasNext = it.hasNext(flowKt__ChannelsKt$emitAllImpl$1);
                if (objHasNext != coroutineSingletons) {
                    return coroutineSingletons;
                }
                flowCollector2 = flowCollector;
                bufferedChannelIterator = it;
                obj = objHasNext;
                if (((Boolean) obj).booleanValue()) {
                    if (z) {
                        receiveChannel.cancel(null);
                    }
                    return Unit.INSTANCE;
                }
                Object next = bufferedChannelIterator.next();
                flowKt__ChannelsKt$emitAllImpl$1.L$0 = flowCollector2;
                flowKt__ChannelsKt$emitAllImpl$1.L$1 = receiveChannel;
                flowKt__ChannelsKt$emitAllImpl$1.L$2 = bufferedChannelIterator;
                flowKt__ChannelsKt$emitAllImpl$1.Z$0 = z;
                flowKt__ChannelsKt$emitAllImpl$1.label = 2;
            }
        } finally {
        }
    }
}
