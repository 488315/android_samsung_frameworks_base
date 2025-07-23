package kotlinx.coroutines.flow.internal;

import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SubscriptionCountStateFlow extends SharedFlowImpl implements StateFlow {
    public SubscriptionCountStateFlow(int i) {
        super(1, Integer.MAX_VALUE, BufferOverflow.DROP_OLDEST);
        tryEmit(Integer.valueOf(i));
    }

    @Override // kotlinx.coroutines.flow.StateFlow
    public final Object getValue() {
        Integer valueOf;
        synchronized (this) {
            valueOf = Integer.valueOf(((Number) getLastReplayedLocked()).intValue());
        }
        return valueOf;
    }

    public final void increment(int i) {
        synchronized (this) {
            tryEmit(Integer.valueOf(((Number) getLastReplayedLocked()).intValue() + i));
        }
    }
}
