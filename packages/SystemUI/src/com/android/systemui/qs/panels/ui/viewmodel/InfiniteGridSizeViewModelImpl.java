package com.android.systemui.qs.panels.ui.viewmodel;

import com.android.systemui.qs.panels.domain.interactor.InfiniteGridSizeInteractor;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class InfiniteGridSizeViewModelImpl implements InfiniteGridSizeViewModel {
    public final ReadonlyStateFlow columns;

    public InfiniteGridSizeViewModelImpl(InfiniteGridSizeInteractor infiniteGridSizeInteractor) {
        ReadonlyStateFlow readonlyStateFlow = infiniteGridSizeInteractor.columns;
    }
}
