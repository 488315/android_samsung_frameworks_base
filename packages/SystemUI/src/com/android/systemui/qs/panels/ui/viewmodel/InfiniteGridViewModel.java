package com.android.systemui.qs.panels.ui.viewmodel;

import com.android.systemui.qs.panels.ui.dialog.QSResetDialogDelegate;
import com.android.systemui.qs.panels.ui.viewmodel.DynamicIconTilesViewModel;
import com.android.systemui.qs.panels.ui.viewmodel.QSColumnsViewModel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class InfiniteGridViewModel {
    public final QSColumnsViewModel.Factory columnsWithMediaViewModelFactory;
    public final DynamicIconTilesViewModel.Factory dynamicIconTilesViewModelFactory;
    public final QSResetDialogDelegate resetDialogDelegate;
    public final TileSquishinessViewModel squishinessViewModel;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
