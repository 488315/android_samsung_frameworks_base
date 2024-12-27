package com.android.systemui.qs.panels.ui.compose;

import com.android.systemui.qs.panels.ui.viewmodel.IconTilesViewModel;
import com.android.systemui.qs.panels.ui.viewmodel.InfiniteGridSizeViewModel;

public final class StretchedGridLayout implements GridLayout {
    public final InfiniteGridSizeViewModel gridSizeViewModel;
    public final IconTilesViewModel iconTilesViewModel;

    public StretchedGridLayout(IconTilesViewModel iconTilesViewModel, InfiniteGridSizeViewModel infiniteGridSizeViewModel) {
    }
}
