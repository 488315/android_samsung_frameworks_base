package com.android.systemui.qs.panels.domain.interactor;

import com.android.systemui.qs.panels.data.repository.InfiniteGridSizeRepository;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

public final class InfiniteGridSizeInteractor {
    public final ReadonlyStateFlow columns;

    public InfiniteGridSizeInteractor(InfiniteGridSizeRepository infiniteGridSizeRepository) {
        this.columns = infiniteGridSizeRepository.columns;
    }
}
