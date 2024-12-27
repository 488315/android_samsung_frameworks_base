package com.android.systemui.unfold.data.repository;

import com.android.systemui.unfold.updates.FoldStateProvider;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
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
