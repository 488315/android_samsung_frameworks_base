package com.android.systemui.qs.panels.domain.interactor;

import com.android.systemui.qs.panels.data.repository.GridLayoutTypeRepository;
import com.android.systemui.qs.panels.data.repository.GridLayoutTypeRepositoryImpl;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

public final class GridLayoutTypeInteractor {
    public final ReadonlyStateFlow layout;

    public GridLayoutTypeInteractor(GridLayoutTypeRepository gridLayoutTypeRepository) {
        this.layout = ((GridLayoutTypeRepositoryImpl) gridLayoutTypeRepository).layout;
    }
}
