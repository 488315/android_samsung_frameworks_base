package kotlinx.coroutines.flow;

import kotlin.coroutines.EmptyCoroutineContext;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.flow.internal.ChannelFlow;

/* loaded from: classes4.dex */
public abstract /* synthetic */ class FlowKt__ShareKt {
    /* JADX WARN: Removed duplicated region for block: B:19:0x0030  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final SharingConfig configureSharing$FlowKt__ShareKt(Flow flow, int i) {
        ChannelFlow channelFlow;
        Flow flowDropChannelOperators;
        Channel.Factory.getClass();
        int i2 = Channel.Factory.CHANNEL_DEFAULT_CAPACITY;
        if (i >= i2) {
            i2 = i;
        }
        int i3 = i2 - i;
        if (!(flow instanceof ChannelFlow) || (flowDropChannelOperators = (channelFlow = (ChannelFlow) flow).dropChannelOperators()) == null) {
            return new SharingConfig(flow, i3, BufferOverflow.SUSPEND, EmptyCoroutineContext.INSTANCE);
        }
        BufferOverflow bufferOverflow = channelFlow.onBufferOverflow;
        int i4 = channelFlow.capacity;
        if (i4 != -3 && i4 != -2 && i4 != 0) {
            i3 = i4;
        } else if (bufferOverflow == BufferOverflow.SUSPEND) {
            if (i4 == 0) {
                i3 = 0;
            }
        } else if (i == 0) {
            i3 = 1;
        }
        return new SharingConfig(flowDropChannelOperators, i3, bufferOverflow, channelFlow.context);
    }
}
