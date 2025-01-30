package kotlinx.coroutines.flow;

import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.internal.ChannelFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract /* synthetic */ class FlowKt__ShareKt {
    /* JADX WARN: Code restructure failed: missing block: B:19:0x002e, code lost:
    
        if (r3 == 0) goto L22;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final SharingConfig configureSharing$FlowKt__ShareKt(Flow flow, int i) {
        ChannelFlow channelFlow;
        Flow dropChannelOperators;
        Channel.Factory.getClass();
        int i2 = Channel.Factory.CHANNEL_DEFAULT_CAPACITY;
        if (i >= i2) {
            i2 = i;
        }
        int i3 = i2 - i;
        if (!(flow instanceof ChannelFlow) || (dropChannelOperators = (channelFlow = (ChannelFlow) flow).dropChannelOperators()) == null) {
            return new SharingConfig(flow, i3, BufferOverflow.SUSPEND, EmptyCoroutineContext.INSTANCE);
        }
        int i4 = channelFlow.capacity;
        if (i4 != -3 && i4 != -2 && i4 != 0) {
            i3 = i4;
        } else if (channelFlow.onBufferOverflow != BufferOverflow.SUSPEND) {
            if (i == 0) {
                i3 = 1;
            }
            i3 = 0;
        }
        return new SharingConfig(dropChannelOperators, i3, channelFlow.onBufferOverflow, channelFlow.context);
    }

    public static final StandaloneCoroutine launchSharing$FlowKt__ShareKt(CoroutineScope coroutineScope, CoroutineContext coroutineContext, Flow flow, MutableSharedFlow mutableSharedFlow, SharingStarted sharingStarted, Object obj) {
        SharingStarted.Companion.getClass();
        return BuildersKt.launch(coroutineScope, coroutineContext, Intrinsics.areEqual(sharingStarted, SharingStarted.Companion.Eagerly) ? CoroutineStart.DEFAULT : CoroutineStart.UNDISPATCHED, new FlowKt__ShareKt$launchSharing$1(sharingStarted, flow, mutableSharedFlow, obj, null));
    }
}
