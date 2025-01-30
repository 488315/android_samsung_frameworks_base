package kotlinx.coroutines.flow;

import com.samsung.android.nexus.video.VideoPlayer;
import java.util.concurrent.CancellationException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlinx.coroutines.channels.ChannelResult;
import kotlinx.coroutines.channels.ReceiveChannel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract /* synthetic */ class FlowKt__ChannelsKt {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0067 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0071 A[Catch: all -> 0x007e, TryCatch #2 {all -> 0x007e, blocks: (B:12:0x0031, B:20:0x006b, B:22:0x0071, B:24:0x0075, B:26:0x007b, B:32:0x008b, B:36:0x008c, B:57:0x004a), top: B:7:0x0021 }] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x008c A[Catch: all -> 0x007e, TRY_LEAVE, TryCatch #2 {all -> 0x007e, blocks: (B:12:0x0031, B:20:0x006b, B:22:0x0071, B:24:0x0075, B:26:0x007b, B:32:0x008b, B:36:0x008c, B:57:0x004a), top: B:7:0x0021 }] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0023  */
    /* JADX WARN: Type inference failed for: r7v0, types: [kotlinx.coroutines.flow.FlowCollector] */
    /* JADX WARN: Type inference failed for: r7v1 */
    /* JADX WARN: Type inference failed for: r7v11 */
    /* JADX WARN: Type inference failed for: r7v12 */
    /* JADX WARN: Type inference failed for: r7v2 */
    /* JADX WARN: Type inference failed for: r7v21 */
    /* JADX WARN: Type inference failed for: r7v7, types: [boolean] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:37:0x009b -> B:13:0x0034). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object emitAllImpl$FlowKt__ChannelsKt(FlowCollector flowCollector, ReceiveChannel receiveChannel, boolean z, Continuation continuation) {
        FlowKt__ChannelsKt$emitAllImpl$1 flowKt__ChannelsKt$emitAllImpl$1;
        int i;
        Object obj;
        FlowCollector flowCollector2;
        FlowCollector flowCollector3;
        try {
            if (continuation instanceof FlowKt__ChannelsKt$emitAllImpl$1) {
                flowKt__ChannelsKt$emitAllImpl$1 = (FlowKt__ChannelsKt$emitAllImpl$1) continuation;
                int i2 = flowKt__ChannelsKt$emitAllImpl$1.label;
                if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                    flowKt__ChannelsKt$emitAllImpl$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                    Object obj2 = flowKt__ChannelsKt$emitAllImpl$1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = flowKt__ChannelsKt$emitAllImpl$1.label;
                    if (i != 0) {
                        ResultKt.throwOnFailure(obj2);
                        boolean z2 = flowCollector instanceof ThrowingCollector;
                        flowCollector3 = flowCollector;
                        if (z2) {
                            throw ((ThrowingCollector) flowCollector).f668e;
                        }
                        flowKt__ChannelsKt$emitAllImpl$1.L$0 = flowCollector3;
                        flowKt__ChannelsKt$emitAllImpl$1.L$1 = receiveChannel;
                        flowKt__ChannelsKt$emitAllImpl$1.Z$0 = z;
                        flowKt__ChannelsKt$emitAllImpl$1.label = 1;
                        obj = receiveChannel.mo2870receiveCatchingJP2dKIU(flowKt__ChannelsKt$emitAllImpl$1);
                        if (obj == coroutineSingletons) {
                        }
                    } else if (i == 1) {
                        boolean z3 = flowKt__ChannelsKt$emitAllImpl$1.Z$0;
                        receiveChannel = (ReceiveChannel) flowKt__ChannelsKt$emitAllImpl$1.L$1;
                        flowCollector2 = (FlowCollector) flowKt__ChannelsKt$emitAllImpl$1.L$0;
                        ResultKt.throwOnFailure(obj2);
                        obj = ((ChannelResult) obj2).holder;
                        flowCollector = z3;
                        ChannelResult.Companion companion = ChannelResult.Companion;
                        if (!(obj instanceof ChannelResult.Closed)) {
                        }
                    } else {
                        if (i != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        boolean z4 = flowKt__ChannelsKt$emitAllImpl$1.Z$0;
                        receiveChannel = (ReceiveChannel) flowKt__ChannelsKt$emitAllImpl$1.L$1;
                        flowCollector2 = (FlowCollector) flowKt__ChannelsKt$emitAllImpl$1.L$0;
                        ResultKt.throwOnFailure(obj2);
                        boolean z5 = z4;
                        FlowCollector flowCollector4 = flowCollector2;
                        z = z5;
                        flowCollector3 = flowCollector4;
                        try {
                            flowKt__ChannelsKt$emitAllImpl$1.L$0 = flowCollector3;
                            flowKt__ChannelsKt$emitAllImpl$1.L$1 = receiveChannel;
                            flowKt__ChannelsKt$emitAllImpl$1.Z$0 = z;
                            flowKt__ChannelsKt$emitAllImpl$1.label = 1;
                            obj = receiveChannel.mo2870receiveCatchingJP2dKIU(flowKt__ChannelsKt$emitAllImpl$1);
                            if (obj == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                            boolean z6 = z;
                            flowCollector2 = flowCollector3;
                            flowCollector = z6;
                            ChannelResult.Companion companion2 = ChannelResult.Companion;
                            if (!(obj instanceof ChannelResult.Closed)) {
                                ChannelResult.Closed closed = obj instanceof ChannelResult.Closed ? (ChannelResult.Closed) obj : null;
                                Throwable th = closed != null ? closed.cause : null;
                                if (th != null) {
                                    throw th;
                                }
                                if (flowCollector != 0) {
                                    receiveChannel.cancel(null);
                                }
                                return Unit.INSTANCE;
                            }
                            ChannelResult.m2874getOrThrowimpl(obj);
                            flowKt__ChannelsKt$emitAllImpl$1.L$0 = flowCollector2;
                            flowKt__ChannelsKt$emitAllImpl$1.L$1 = receiveChannel;
                            flowKt__ChannelsKt$emitAllImpl$1.Z$0 = flowCollector;
                            flowKt__ChannelsKt$emitAllImpl$1.label = 2;
                            z5 = flowCollector;
                            if (flowCollector2.emit(obj, flowKt__ChannelsKt$emitAllImpl$1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                            FlowCollector flowCollector42 = flowCollector2;
                            z = z5;
                            flowCollector3 = flowCollector42;
                            flowKt__ChannelsKt$emitAllImpl$1.L$0 = flowCollector3;
                            flowKt__ChannelsKt$emitAllImpl$1.L$1 = receiveChannel;
                            flowKt__ChannelsKt$emitAllImpl$1.Z$0 = z;
                            flowKt__ChannelsKt$emitAllImpl$1.label = 1;
                            obj = receiveChannel.mo2870receiveCatchingJP2dKIU(flowKt__ChannelsKt$emitAllImpl$1);
                            if (obj == coroutineSingletons) {
                            }
                        } catch (Throwable th2) {
                            boolean z7 = z;
                            th = th2;
                            flowCollector = z7;
                            try {
                                throw th;
                            } catch (Throwable th3) {
                                if (flowCollector != 0) {
                                    CancellationException cancellationException = th instanceof CancellationException ? th : null;
                                    if (cancellationException == null) {
                                        cancellationException = new CancellationException("Channel was consumed, consumer had failed");
                                        cancellationException.initCause(th);
                                    }
                                    receiveChannel.cancel(cancellationException);
                                }
                                throw th3;
                            }
                        }
                    }
                }
            }
            if (i != 0) {
            }
        } catch (Throwable th4) {
            th = th4;
        }
        flowKt__ChannelsKt$emitAllImpl$1 = new FlowKt__ChannelsKt$emitAllImpl$1(continuation);
        Object obj22 = flowKt__ChannelsKt$emitAllImpl$1.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = flowKt__ChannelsKt$emitAllImpl$1.label;
    }
}
