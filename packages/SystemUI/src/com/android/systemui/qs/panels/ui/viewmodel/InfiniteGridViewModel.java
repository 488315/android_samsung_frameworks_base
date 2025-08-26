package com.android.systemui.qs.panels.ui.viewmodel;

import com.android.systemui.qs.panels.ui.dialog.QSResetDialogDelegate;
import com.android.systemui.qs.panels.ui.viewmodel.DynamicIconTilesViewModel;
import com.android.systemui.qs.panels.ui.viewmodel.QSColumnsViewModel;

/* loaded from: classes2.dex */
public final class InfiniteGridViewModel {
    public final QSColumnsViewModel.Factory columnsWithMediaViewModelFactory;
    public final DynamicIconTilesViewModel.Factory dynamicIconTilesViewModelFactory;
    public final QSResetDialogDelegate resetDialogDelegate;
    public final TileSquishinessViewModel squishinessViewModel;

    public interface Factory {
        InfiniteGridViewModel create();
    }

    public InfiniteGridViewModel(DynamicIconTilesViewModel.Factory factory, QSColumnsViewModel.Factory factory2, TileSquishinessViewModel tileSquishinessViewModel, QSResetDialogDelegate qSResetDialogDelegate) {
        this.dynamicIconTilesViewModelFactory = factory;
        this.columnsWithMediaViewModelFactory = factory2;
        this.squishinessViewModel = tileSquishinessViewModel;
        this.resetDialogDelegate = qSResetDialogDelegate;
    }
}
