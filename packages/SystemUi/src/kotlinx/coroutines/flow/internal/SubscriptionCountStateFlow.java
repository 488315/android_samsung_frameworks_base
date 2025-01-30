package kotlinx.coroutines.flow.internal;

import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.internal.Symbol;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class SubscriptionCountStateFlow extends SharedFlowImpl implements StateFlow {
    public SubscriptionCountStateFlow(int i) {
        super(1, Integer.MAX_VALUE, BufferOverflow.DROP_OLDEST);
        tryEmit(Integer.valueOf(i));
    }

    @Override // kotlinx.coroutines.flow.StateFlow
    public final Object getValue() {
        Integer valueOf;
        synchronized (this) {
            Object[] objArr = this.buffer;
            Intrinsics.checkNotNull(objArr);
            long head = (this.replayIndex + ((int) ((getHead() + this.bufferSize) - this.replayIndex))) - 1;
            Symbol symbol = SharedFlowKt.NO_VALUE;
            valueOf = Integer.valueOf(((Number) objArr[((int) head) & (objArr.length - 1)]).intValue());
        }
        return valueOf;
    }
}
