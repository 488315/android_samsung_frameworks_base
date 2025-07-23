package kotlinx.coroutines.channels;

import kotlinx.atomicfu.AtomicArray;
import kotlinx.coroutines.internal.Segment;
import kotlinx.coroutines.internal.Symbol;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ChannelSegment extends Segment {
    public final BufferedChannel _channel;
    public final AtomicArray data;

    public ChannelSegment(long j, ChannelSegment channelSegment, BufferedChannel bufferedChannel, int i) {
        super(j, channelSegment, i);
        this._channel = bufferedChannel;
        this.data = new AtomicArray(BufferedChannelKt.SEGMENT_SIZE * 2);
    }

    public final boolean casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(int i, Object obj, Object obj2) {
        return this.data.array[(i * 2) + 1].compareAndSet(obj, obj2);
    }

    public final Object getElement$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(int i) {
        return this.data.array[i * 2].value;
    }

    @Override // kotlinx.coroutines.internal.Segment
    public final int getNumberOfSlots() {
        return BufferedChannelKt.SEGMENT_SIZE;
    }

    public final Object getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(int i) {
        return this.data.array[(i * 2) + 1].value;
    }

    /* JADX WARN: Code restructure failed: missing block: B:54:0x0056, code lost:
    
        setElementLazy(r8, null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0059, code lost:
    
        if (r1 == false) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x005b, code lost:
    
        r4.getClass();
        r6 = r4.onUndeliveredElement;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0060, code lost:
    
        if (r6 == null) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0062, code lost:
    
        kotlinx.coroutines.internal.OnUndeliveredElementKt.callUndeliveredElement(r6, r0, r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0065, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:?, code lost:
    
        return;
     */
    @Override // kotlinx.coroutines.internal.Segment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onCancellation(kotlin.coroutines.CoroutineContext r7, int r8) {
        /*
            r6 = this;
            int r0 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE
            if (r8 < r0) goto L6
            r1 = 1
            goto L7
        L6:
            r1 = 0
        L7:
            if (r1 == 0) goto La
            int r8 = r8 - r0
        La:
            java.lang.Object r0 = r6.getElement$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(r8)
        Le:
            java.lang.Object r2 = r6.getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(r8)
            boolean r3 = r2 instanceof kotlinx.coroutines.Waiter
            kotlinx.coroutines.channels.BufferedChannel r4 = r6._channel
            r5 = 0
            if (r3 != 0) goto L66
            boolean r3 = r2 instanceof kotlinx.coroutines.channels.WaiterEB
            if (r3 == 0) goto L1e
            goto L66
        L1e:
            kotlinx.coroutines.internal.Symbol r3 = kotlinx.coroutines.channels.BufferedChannelKt.INTERRUPTED_SEND
            if (r2 == r3) goto L56
            kotlinx.coroutines.internal.Symbol r3 = kotlinx.coroutines.channels.BufferedChannelKt.INTERRUPTED_RCV
            if (r2 != r3) goto L27
            goto L56
        L27:
            kotlinx.coroutines.internal.Symbol r3 = kotlinx.coroutines.channels.BufferedChannelKt.RESUMING_BY_EB
            if (r2 == r3) goto Le
            kotlinx.coroutines.internal.Symbol r3 = kotlinx.coroutines.channels.BufferedChannelKt.RESUMING_BY_RCV
            if (r2 != r3) goto L30
            goto Le
        L30:
            kotlinx.coroutines.internal.Symbol r6 = kotlinx.coroutines.channels.BufferedChannelKt.DONE_RCV
            if (r2 == r6) goto L87
            kotlinx.coroutines.internal.Symbol r6 = kotlinx.coroutines.channels.BufferedChannelKt.BUFFERED
            if (r2 != r6) goto L39
            goto L87
        L39:
            kotlinx.coroutines.internal.Symbol r6 = kotlinx.coroutines.channels.BufferedChannelKt.CHANNEL_CLOSED
            if (r2 != r6) goto L3e
            goto L87
        L3e:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r8 = "unexpected state: "
            r7.<init>(r8)
            r7.append(r2)
            java.lang.String r7 = r7.toString()
            java.lang.String r7 = r7.toString()
            r6.<init>(r7)
            throw r6
        L56:
            r6.setElementLazy(r8, r5)
            if (r1 == 0) goto L87
            r4.getClass()
            kotlin.jvm.functions.Function1 r6 = r4.onUndeliveredElement
            if (r6 == 0) goto L87
            kotlinx.coroutines.internal.OnUndeliveredElementKt.callUndeliveredElement(r6, r0, r7)
            return
        L66:
            if (r1 == 0) goto L6b
            kotlinx.coroutines.internal.Symbol r3 = kotlinx.coroutines.channels.BufferedChannelKt.INTERRUPTED_SEND
            goto L6d
        L6b:
            kotlinx.coroutines.internal.Symbol r3 = kotlinx.coroutines.channels.BufferedChannelKt.INTERRUPTED_RCV
        L6d:
            boolean r2 = r6.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(r8, r2, r3)
            if (r2 == 0) goto Le
            r6.setElementLazy(r8, r5)
            r2 = r1 ^ 1
            r6.onCancelledRequest(r8, r2)
            if (r1 == 0) goto L87
            r4.getClass()
            kotlin.jvm.functions.Function1 r6 = r4.onUndeliveredElement
            if (r6 == 0) goto L87
            kotlinx.coroutines.internal.OnUndeliveredElementKt.callUndeliveredElement(r6, r0, r7)
        L87:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelSegment.onCancellation(kotlin.coroutines.CoroutineContext, int):void");
    }

    public final void onCancelledRequest(int i, boolean z) {
        if (z) {
            BufferedChannel bufferedChannel = this._channel;
            bufferedChannel.getClass();
            bufferedChannel.waitExpandBufferCompletion$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host((this.id * BufferedChannelKt.SEGMENT_SIZE) + i);
        }
        onSlotCleaned();
    }

    public final void setElementLazy(int i, Object obj) {
        this.data.array[i * 2].lazySet(obj);
    }

    public final void setState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(int i, Symbol symbol) {
        this.data.array[(i * 2) + 1].setValue(symbol);
    }
}
