package androidx.datastore.core;

import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes.dex */
public final class DataStoreInMemoryCache {
    public final StateFlowImpl cachedValue = StateFlowKt.MutableStateFlow(UnInitialized.INSTANCE);

    public final State getCurrentState() {
        return (State) this.cachedValue.getValue();
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x002f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void tryUpdate(State state) {
        StateFlowImpl stateFlowImpl;
        Object value;
        State state2;
        do {
            stateFlowImpl = this.cachedValue;
            value = stateFlowImpl.getValue();
            state2 = (State) value;
            if ((state2 instanceof ReadException) || Intrinsics.areEqual(state2, UnInitialized.INSTANCE)) {
                state2 = state;
            } else if (state2 instanceof Data) {
                if (state.version > ((Data) state2).version) {
                }
            } else if (!(state2 instanceof Final)) {
                throw new NoWhenBranchMatchedException();
            }
        } while (!stateFlowImpl.compareAndSet(value, state2));
    }
}
