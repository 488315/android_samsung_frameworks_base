package com.android.systemui.qs.panels.ui.viewmodel;

import com.android.systemui.qs.panels.domain.interactor.InfiniteGridSizeInteractor;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

public final class InfiniteGridSizeViewModelImpl implements InfiniteGridSizeViewModel {
    public final ReadonlyStateFlow columns;

    public InfiniteGridSizeViewModelImpl(InfiniteGridSizeInteractor infiniteGridSizeInteractor) {
        ReadonlyStateFlow readonlyStateFlow = infiniteGridSizeInteractor.columns;
    }
}
