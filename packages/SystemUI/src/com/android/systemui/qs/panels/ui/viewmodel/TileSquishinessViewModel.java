package com.android.systemui.qs.panels.ui.viewmodel;

import com.android.systemui.qs.panels.domain.interactor.TileSquishinessInteractor;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes2.dex */
public final class TileSquishinessViewModel {
    public final ReadonlyStateFlow squishiness;

    public TileSquishinessViewModel(TileSquishinessInteractor tileSquishinessInteractor) {
        this.squishiness = tileSquishinessInteractor.squishiness;
    }
}
