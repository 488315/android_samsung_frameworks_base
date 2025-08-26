package com.android.systemui.kairos.internal;

import kotlin.Lazy;
import kotlin.Pair;

/* loaded from: classes2.dex */
public final class StateSource extends StateStore {
    public volatile Lazy _current;
    public NodeConnection upstreamConnection;
    public volatile long writeEpoch;

    public StateSource(Lazy lazy) {
        super(null);
        this._current = lazy;
    }

    @Override // com.android.systemui.kairos.internal.StateStore
    public final Pair getCurrentWithEpoch(EvalScope evalScope) {
        return new Pair(this._current.getValue(), Long.valueOf(this.writeEpoch));
    }

    public final String toString() {
        return "StateImpl(current=" + this._current + ", writeEpoch=" + this.writeEpoch + ")";
    }

    public StateSource(Object obj) {
        this((Lazy) new CompletableLazy(obj, null, 2, null));
    }
}
