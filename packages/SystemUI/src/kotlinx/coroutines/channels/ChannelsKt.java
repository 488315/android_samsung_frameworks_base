package kotlinx.coroutines.channels;

import java.util.concurrent.CancellationException;
import kotlin.Unit;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.ExceptionsKt;
import kotlinx.coroutines.channels.ChannelResult;

/* loaded from: classes4.dex */
public abstract class ChannelsKt {
    public static final void cancelConsumed(ReceiveChannel receiveChannel, Throwable th) {
        CancellationException cancellationException = th instanceof CancellationException ? (CancellationException) th : null;
        if (cancellationException == null) {
            cancellationException = ExceptionsKt.CancellationException("Channel was consumed, consumer had failed", th);
        }
        receiveChannel.cancel(cancellationException);
    }

    public static final void trySendBlocking(SendChannel sendChannel, Object obj) {
        Object mo3456trySendJP2dKIU = sendChannel.mo3456trySendJP2dKIU(obj);
        if (mo3456trySendJP2dKIU instanceof ChannelResult.Failed) {
            Object obj2 = ((ChannelResult) BuildersKt.runBlocking(EmptyCoroutineContext.INSTANCE, new ChannelsKt__ChannelsKt$trySendBlocking$2(sendChannel, obj, null))).holder;
        } else {
            ChannelResult.Companion companion = ChannelResult.Companion;
            Unit unit = Unit.INSTANCE;
            companion.getClass();
        }
    }
}
