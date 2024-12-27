package com.android.systemui.unfold.data.repository;

import com.android.systemui.unfold.updates.FoldStateProvider;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

public final class FoldStateRepositoryImpl implements FoldStateRepository {
    public final FoldStateProvider foldStateProvider;

    public FoldStateRepositoryImpl(FoldStateProvider foldStateProvider) {
        this.foldStateProvider = foldStateProvider;
    }

    public final Flow getFoldUpdate() {
        return FlowKt.buffer$default(FlowKt.callbackFlow(new FoldStateRepositoryImpl$foldUpdate$1(this, null)), -1);
    }

    public final Flow getHingeAngle() {
        return FlowKt.buffer$default(FlowKt.callbackFlow(new FoldStateRepositoryImpl$hingeAngle$1(this, null)), -1);
    }
}
