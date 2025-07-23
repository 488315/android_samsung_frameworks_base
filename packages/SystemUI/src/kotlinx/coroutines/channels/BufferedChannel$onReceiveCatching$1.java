package kotlinx.coroutines.channels;

import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
final /* synthetic */ class BufferedChannel$onReceiveCatching$1 extends FunctionReferenceImpl implements Function3 {
    public static final BufferedChannel$onReceiveCatching$1 INSTANCE = new BufferedChannel$onReceiveCatching$1();

    public BufferedChannel$onReceiveCatching$1() {
        super(3, BufferedChannel.class, "registerSelectForReceive", "registerSelectForReceive(Lkotlinx/coroutines/selects/SelectInstance;Ljava/lang/Object;)V", 0);
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x006e, code lost:
    
        return kotlin.Unit.INSTANCE;
     */
    @Override // kotlin.jvm.functions.Function3
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invoke(java.lang.Object r7, java.lang.Object r8, java.lang.Object r9) {
        /*
            r6 = this;
            r0 = r7
            kotlinx.coroutines.channels.BufferedChannel r0 = (kotlinx.coroutines.channels.BufferedChannel) r0
            r5 = r8
            kotlinx.coroutines.selects.SelectInstance r5 = (kotlinx.coroutines.selects.SelectInstance) r5
            kotlinx.atomicfu.AtomicRef r6 = r0.receiveSegment
            java.lang.Object r6 = r6.value
            kotlinx.coroutines.channels.ChannelSegment r6 = (kotlinx.coroutines.channels.ChannelSegment) r6
        Lc:
            boolean r7 = r0.isClosedForReceive()
            if (r7 == 0) goto L19
            kotlinx.coroutines.internal.Symbol r6 = kotlinx.coroutines.channels.BufferedChannelKt.CHANNEL_CLOSED
            kotlinx.coroutines.selects.SelectImplementation r5 = (kotlinx.coroutines.selects.SelectImplementation) r5
            r5.internalResult = r6
            goto L6c
        L19:
            kotlinx.atomicfu.AtomicLong r7 = r0.receivers
            long r3 = r7.getAndIncrement()
            int r7 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE
            long r7 = (long) r7
            long r1 = r3 / r7
            long r7 = r3 % r7
            int r7 = (int) r7
            long r8 = r6.id
            int r8 = (r8 > r1 ? 1 : (r8 == r1 ? 0 : -1))
            if (r8 == 0) goto L37
            kotlinx.coroutines.channels.ChannelSegment r8 = r0.findSegmentReceive(r1, r6)
            if (r8 != 0) goto L34
            goto Lc
        L34:
            r1 = r8
        L35:
            r2 = r7
            goto L39
        L37:
            r1 = r6
            goto L35
        L39:
            java.lang.Object r6 = r0.updateCellReceive(r1, r2, r3, r5)
            r8 = r1
            kotlinx.coroutines.internal.Symbol r7 = kotlinx.coroutines.channels.BufferedChannelKt.SUSPEND
            if (r6 != r7) goto L50
            boolean r6 = r5 instanceof kotlinx.coroutines.Waiter
            if (r6 == 0) goto L49
            kotlinx.coroutines.Waiter r5 = (kotlinx.coroutines.Waiter) r5
            goto L4a
        L49:
            r5 = 0
        L4a:
            if (r5 == 0) goto L6c
            r5.invokeOnCancellation(r8, r2)
            goto L6c
        L50:
            kotlinx.coroutines.internal.Symbol r7 = kotlinx.coroutines.channels.BufferedChannelKt.FAILED
            if (r6 != r7) goto L61
            long r6 = r0.getSendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()
            int r6 = (r3 > r6 ? 1 : (r3 == r6 ? 0 : -1))
            if (r6 >= 0) goto L5f
            r8.cleanPrev()
        L5f:
            r6 = r8
            goto Lc
        L61:
            kotlinx.coroutines.internal.Symbol r7 = kotlinx.coroutines.channels.BufferedChannelKt.SUSPEND_NO_WAITER
            if (r6 == r7) goto L6f
            r8.cleanPrev()
            kotlinx.coroutines.selects.SelectImplementation r5 = (kotlinx.coroutines.selects.SelectImplementation) r5
            r5.internalResult = r6
        L6c:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        L6f:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "unexpected"
            r6.<init>(r7)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel$onReceiveCatching$1.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
    }
}
