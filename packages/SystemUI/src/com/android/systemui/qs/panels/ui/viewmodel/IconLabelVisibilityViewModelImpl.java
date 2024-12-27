package com.android.systemui.qs.panels.ui.viewmodel;

import com.android.systemui.qs.panels.domain.interactor.IconLabelVisibilityInteractor;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

public final class IconLabelVisibilityViewModelImpl implements IconLabelVisibilityViewModel {
    public final IconLabelVisibilityInteractor interactor;
    public final ReadonlyStateFlow showLabels;

    public IconLabelVisibilityViewModelImpl(IconLabelVisibilityInteractor iconLabelVisibilityInteractor) {
        ReadonlyStateFlow readonlyStateFlow = iconLabelVisibilityInteractor.showLabels;
    }
}
