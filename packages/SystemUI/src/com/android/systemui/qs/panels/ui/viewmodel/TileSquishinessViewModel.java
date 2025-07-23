package com.android.systemui.qs.panels.ui.viewmodel;

import com.android.systemui.qs.panels.domain.interactor.TileSquishinessInteractor;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class TileSquishinessViewModel {
    public final ReadonlyStateFlow squishiness;

    public TileSquishinessViewModel(TileSquishinessInteractor tileSquishinessInteractor) {
        this.squishiness = tileSquishinessInteractor.squishiness;
    }
}
