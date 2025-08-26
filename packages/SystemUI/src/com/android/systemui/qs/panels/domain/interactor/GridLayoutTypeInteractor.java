package com.android.systemui.qs.panels.domain.interactor;

import com.android.systemui.qs.panels.data.repository.GridLayoutTypeRepository;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractorImpl;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes2.dex */
public final class GridLayoutTypeInteractor {
    public final ChannelFlowTransformLatest layout;
    public final GridLayoutTypeRepository repo;

    public GridLayoutTypeInteractor(GridLayoutTypeRepository gridLayoutTypeRepository, ShadeModeInteractor shadeModeInteractor) {
        this.repo = gridLayoutTypeRepository;
        this.layout = FlowKt.transformLatest(((ShadeModeInteractorImpl) shadeModeInteractor).shadeMode, new GridLayoutTypeInteractor$special$$inlined$flatMapLatest$1(null, this));
    }
}
