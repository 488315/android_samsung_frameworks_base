package kotlinx.coroutines.channels;

import kotlin.Unit;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.channels.ChannelResult;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class ChannelsKt {
    /* JADX WARN: Multi-variable type inference failed */
    public static final void trySendBlocking(ProducerScope producerScope, Object obj) {
        Object mo2872trySendJP2dKIU = ((ChannelCoroutine) producerScope).mo2872trySendJP2dKIU(obj);
        if (mo2872trySendJP2dKIU instanceof ChannelResult.Failed) {
            Object obj2 = ((ChannelResult) BuildersKt.runBlocking(EmptyCoroutineContext.INSTANCE, new ChannelsKt__ChannelsKt$trySendBlocking$2(producerScope, obj, null))).holder;
        } else {
            ChannelResult.Companion companion = ChannelResult.Companion;
            Unit unit = Unit.INSTANCE;
            companion.getClass();
        }
    }
}
