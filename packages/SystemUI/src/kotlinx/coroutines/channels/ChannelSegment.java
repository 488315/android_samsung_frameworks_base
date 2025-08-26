package kotlinx.coroutines.channels;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlinx.atomicfu.AtomicArray;
import kotlinx.coroutines.Waiter;
import kotlinx.coroutines.internal.OnUndeliveredElementKt;
import kotlinx.coroutines.internal.Segment;
import kotlinx.coroutines.internal.Symbol;

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

    /* JADX WARN: Code restructure failed: missing block: B:34:0x0056, code lost:
    
        setElementLazy(r8, null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0059, code lost:
    
        if (r1 == false) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x005b, code lost:
    
        r4.getClass();
        r6 = r4.onUndeliveredElement;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0060, code lost:
    
        if (r6 == null) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0062, code lost:
    
        kotlinx.coroutines.internal.OnUndeliveredElementKt.callUndeliveredElement(r6, r0, r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0065, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:?, code lost:
    
        return;
     */
    @Override // kotlinx.coroutines.internal.Segment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onCancellation(CoroutineContext coroutineContext, int i) {
        int i2 = BufferedChannelKt.SEGMENT_SIZE;
        boolean z = i >= i2;
        if (z) {
            i -= i2;
        }
        Object element$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = getElement$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i);
        while (true) {
            Object state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i);
            boolean z2 = state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof Waiter;
            BufferedChannel bufferedChannel = this._channel;
            if (z2 || (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof WaiterEB)) {
                if (casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host, z ? BufferedChannelKt.INTERRUPTED_SEND : BufferedChannelKt.INTERRUPTED_RCV)) {
                    setElementLazy(i, null);
                    onCancelledRequest(i, !z);
                    if (z) {
                        bufferedChannel.getClass();
                        Function1 function1 = bufferedChannel.onUndeliveredElement;
                        if (function1 != null) {
                            OnUndeliveredElementKt.callUndeliveredElement(function1, element$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host, coroutineContext);
                            return;
                        }
                        return;
                    }
                    return;
                }
            } else {
                if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == BufferedChannelKt.INTERRUPTED_SEND || state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == BufferedChannelKt.INTERRUPTED_RCV) {
                    break;
                }
                if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host != BufferedChannelKt.RESUMING_BY_EB && state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host != BufferedChannelKt.RESUMING_BY_RCV) {
                    if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == BufferedChannelKt.DONE_RCV || state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == BufferedChannelKt.BUFFERED || state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == BufferedChannelKt.CHANNEL_CLOSED) {
                        return;
                    }
                    throw new IllegalStateException(("unexpected state: " + state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host).toString());
                }
            }
        }
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
