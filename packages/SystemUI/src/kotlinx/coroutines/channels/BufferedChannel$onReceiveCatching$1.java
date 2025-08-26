package kotlinx.coroutines.channels;

import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlinx.coroutines.Waiter;
import kotlinx.coroutines.selects.SelectImplementation;
import kotlinx.coroutines.selects.SelectInstance;

/* loaded from: classes4.dex */
final /* synthetic */ class BufferedChannel$onReceiveCatching$1 extends FunctionReferenceImpl implements Function3 {
    public static final BufferedChannel$onReceiveCatching$1 INSTANCE = new BufferedChannel$onReceiveCatching$1();

    public BufferedChannel$onReceiveCatching$1() {
        super(3, BufferedChannel.class, "registerSelectForReceive", "registerSelectForReceive(Lkotlinx/coroutines/selects/SelectInstance;Ljava/lang/Object;)V", 0);
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x006e, code lost:
    
        return kotlin.Unit.INSTANCE;
     */
    @Override // kotlin.jvm.functions.Function3
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        ChannelSegment channelSegment;
        BufferedChannel bufferedChannel = (BufferedChannel) obj;
        SelectInstance selectInstance = (SelectInstance) obj2;
        ChannelSegment channelSegment2 = (ChannelSegment) bufferedChannel.receiveSegment.value;
        while (true) {
            if (bufferedChannel.isClosedForReceive()) {
                ((SelectImplementation) selectInstance).internalResult = BufferedChannelKt.CHANNEL_CLOSED;
                break;
            }
            long andIncrement = bufferedChannel.receivers.getAndIncrement();
            long j = BufferedChannelKt.SEGMENT_SIZE;
            long j2 = andIncrement / j;
            int i = (int) (andIncrement % j);
            if (channelSegment2.id != j2) {
                ChannelSegment channelSegmentFindSegmentReceive = bufferedChannel.findSegmentReceive(j2, channelSegment2);
                if (channelSegmentFindSegmentReceive == null) {
                    continue;
                } else {
                    channelSegment = channelSegmentFindSegmentReceive;
                }
            } else {
                channelSegment = channelSegment2;
            }
            Object objUpdateCellReceive = bufferedChannel.updateCellReceive(channelSegment, i, andIncrement, selectInstance);
            ChannelSegment channelSegment3 = channelSegment;
            if (objUpdateCellReceive == BufferedChannelKt.SUSPEND) {
                Waiter waiter = selectInstance instanceof Waiter ? (Waiter) selectInstance : null;
                if (waiter != null) {
                    waiter.invokeOnCancellation(channelSegment3, i);
                }
            } else if (objUpdateCellReceive == BufferedChannelKt.FAILED) {
                if (andIncrement < bufferedChannel.getSendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                    channelSegment3.cleanPrev();
                }
                channelSegment2 = channelSegment3;
            } else {
                if (objUpdateCellReceive == BufferedChannelKt.SUSPEND_NO_WAITER) {
                    throw new IllegalStateException("unexpected");
                }
                channelSegment3.cleanPrev();
                ((SelectImplementation) selectInstance).internalResult = objUpdateCellReceive;
            }
        }
    }
}
