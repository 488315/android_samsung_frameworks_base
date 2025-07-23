package com.android.systemui.unfold.data.repository;

import com.android.systemui.unfold.updates.FoldStateProvider;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class FoldStateRepositoryImpl implements FoldStateRepository {
    public final FoldStateProvider foldStateProvider;

    public FoldStateRepositoryImpl(FoldStateProvider foldStateProvider) {
        this.foldStateProvider = foldStateProvider;
    }

    public final Flow getFoldUpdate() {
        return FlowKt.buffer$default(FlowKt.callbackFlow(new FoldStateRepositoryImpl$foldUpdate$1(this, null)), -1, 2);
    }

    public final Flow getHingeAngle() {
        return FlowKt.buffer$default(FlowKt.callbackFlow(new FoldStateRepositoryImpl$hingeAngle$1(this, null)), -1, 2);
    }
}
